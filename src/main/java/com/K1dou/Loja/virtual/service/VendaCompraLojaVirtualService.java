package com.K1dou.Loja.virtual.service;

import com.K1dou.Loja.virtual.controller.PessoaController;
import com.K1dou.Loja.virtual.exceptions.ExceptionLojaVirtual;
import com.K1dou.Loja.virtual.model.*;
import com.K1dou.Loja.virtual.model.Dtos.ItemVendaDTO;
import com.K1dou.Loja.virtual.model.Dtos.VendaCompraLojaVirtualDTO;
import com.K1dou.Loja.virtual.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VendaCompraLojaVirtualService {

    @Autowired
    private StatusRastreioRepository statusRastreioRepository;

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    @Autowired
    private VendaCompraLojaVirtualRepository vendaCompraRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;

    @Autowired
    private PessoaUserService pessoaUserService;

    @Autowired
    private PessoaController pessoaController;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private NotaFiscalVendaRepository notaFiscalVendaRepository;

    @PersistenceContext
    private EntityManager entityManager;


    public VendaCompraLojaVirtualDTO salvaVendaCompra(VendaCompraLojaVirtual vendaCompraLojaVirtual) throws MessagingException, UnsupportedEncodingException, ExceptionLojaVirtual {


        vendaCompraLojaVirtual.getPessoa().setEmpresa(vendaCompraLojaVirtual.getEmpresa());
        PessoaFisica pessoaFisica = pessoaController.cadastroPessoaFisica(vendaCompraLojaVirtual.getPessoa()).getBody();
        vendaCompraLojaVirtual.setPessoa(pessoaFisica);

        vendaCompraLojaVirtual.getEnderecoCobranca().setPessoa(pessoaFisica);
        vendaCompraLojaVirtual.getEnderecoCobranca().setEmpresa(vendaCompraLojaVirtual.getEmpresa());
        Endereco enderecoCobranca = enderecoRepository.save(vendaCompraLojaVirtual.getEnderecoCobranca());
        vendaCompraLojaVirtual.setEnderecoCobranca(enderecoCobranca);

        vendaCompraLojaVirtual.getEnderecoEntrega().setPessoa(pessoaFisica);
        vendaCompraLojaVirtual.getEnderecoEntrega().setEmpresa(vendaCompraLojaVirtual.getEmpresa());
        Endereco enderecoEntrega = enderecoRepository.save(vendaCompraLojaVirtual.getEnderecoEntrega());
        vendaCompraLojaVirtual.setEnderecoEntrega(enderecoEntrega);

        vendaCompraLojaVirtual.getNotaFiscalVenda().setEmpresa(vendaCompraLojaVirtual.getEmpresa());

        for (ItemVendaLoja itemVenda : vendaCompraLojaVirtual.getItemVendaLojas()) {
            itemVenda.setEmpresa(vendaCompraLojaVirtual.getEmpresa());
            itemVenda.setVendaCompraLojaVirtual(vendaCompraLojaVirtual);
        }

        /*Salva primeiro a venda e todo os dados*/
        vendaCompraLojaVirtual = vendaCompraRepository.saveAndFlush(vendaCompraLojaVirtual);

        StatusRastreio statusRastreio = new StatusRastreio();
        statusRastreio.setCentroDistribuicao("Loja local");
        statusRastreio.setCidade("Local");
        statusRastreio.setEmpresa(vendaCompraLojaVirtual.getEmpresa());
        statusRastreio.setStatus("Inicio");
        statusRastreio.setEstado("Local");
        statusRastreio.setVendaCompraLojaVirtual(vendaCompraLojaVirtual);
        statusRastreioRepository.save(statusRastreio);

        /*Associa a venda gravada no banco com a nota fiscal*/
        vendaCompraLojaVirtual.getNotaFiscalVenda().setVendaCompraLojaVirtual(vendaCompraLojaVirtual);

        /*Persiste novamente as nota fiscal novamente pra ficar amarrada na venda*/
        notaFiscalVendaRepository.saveAndFlush(vendaCompraLojaVirtual.getNotaFiscalVenda());

        VendaCompraLojaVirtualDTO compraLojaVirtualDTO = new VendaCompraLojaVirtualDTO();
        compraLojaVirtualDTO.setValorTotal(vendaCompraLojaVirtual.getValorTotal());
        compraLojaVirtualDTO.setPessoa(vendaCompraLojaVirtual.getPessoa().getId());
        compraLojaVirtualDTO.setEnderecoCobranca(vendaCompraLojaVirtual.getEnderecoCobranca());
        compraLojaVirtualDTO.setEnderecoEntrega(vendaCompraLojaVirtual.getEnderecoEntrega());
        compraLojaVirtualDTO.setValorDesconto(vendaCompraLojaVirtual.getValorDesconto());
        compraLojaVirtualDTO.setValorFrete(vendaCompraLojaVirtual.getValorFrete());
        modelMapper.typeMap(ItemVendaLoja.class, ItemVendaDTO.class).addMapping(item -> item.getProduto().getId(), (dest, v) -> dest.setProduto((Long) v));

        List<ItemVendaDTO> itemVendaDTOS = vendaCompraLojaVirtual.getItemVendaLojas().stream().map(item -> modelMapper.map(item, ItemVendaDTO.class)).collect(Collectors.toList());
        compraLojaVirtualDTO.setItemVendaLojas(itemVendaDTOS);

        return compraLojaVirtualDTO;
    }


    public void exclusaoTotalVendaBanco(Long idVenda) {
        vendaCompraRepository.exclusaoTotalVendaBanco(idVenda);
    }

    public void exclusaoLogicaVendaBanco(Long idVenda) {
        vendaCompraRepository.exclusaoLogicaVendaBanco(idVenda);
    }

    public void restaurarExclusaoLogica(Long idVenda) {
        vendaCompraRepository.restaurarVendaBanco(idVenda);
    }


    public VendaCompraLojaVirtualDTO consultaVendaId(Long id) throws ExceptionLojaVirtual {

        Optional<VendaCompraLojaVirtual> vendaCompraLojaVirtual = Optional.ofNullable(Optional.ofNullable(vendaCompraRepository.consultaVendaId(id)).orElseThrow(() -> new ExceptionLojaVirtual("Id da venda está invalido ou não existe")));
        modelMapper.typeMap(VendaCompraLojaVirtual.class, VendaCompraLojaVirtualDTO.class).addMapping(vendaCompraLojaVirtual1 -> vendaCompraLojaVirtual1.getPessoa().getId(), (dest, v) -> dest.setPessoa((Long) v));
        modelMapper.typeMap(ItemVendaLoja.class, ItemVendaDTO.class).addMapping(item -> item.getProduto().getId(), (dest, v) -> dest.setProduto((Long) v));

        VendaCompraLojaVirtualDTO vendaCompraLojaVirtualDTO = modelMapper.map(vendaCompraLojaVirtual, VendaCompraLojaVirtualDTO.class);
        return vendaCompraLojaVirtualDTO;
    }

    public List<VendaCompraLojaVirtualDTO> vendaPorProduto(Long idProduto) {
        modelMapper.typeMap(ItemVendaLoja.class, ItemVendaDTO.class).addMapping(item -> item.getProduto().getId(), (dest, v) -> dest.setProduto((Long) v));
        modelMapper.typeMap(VendaCompraLojaVirtual.class, VendaCompraLojaVirtualDTO.class).addMapping(vendaCompraLojaVirtual1 -> vendaCompraLojaVirtual1.getPessoa().getId(), (dest, v) -> dest.setPessoa((Long) v));

        List<VendaCompraLojaVirtual> vendaCompraLojaVirtuals = vendaCompraRepository.vendaPorProduto(idProduto);
        List<VendaCompraLojaVirtualDTO> vendaCompraLojaVirtualDTOS = vendaCompraLojaVirtuals.stream().map(item -> modelMapper.map(item, VendaCompraLojaVirtualDTO.class)).collect(Collectors.toList());

        return vendaCompraLojaVirtualDTOS;
    }


    public List<VendaCompraLojaVirtualDTO> consultaVendaDinamica(String valor, String tipoConsulta) {
        modelMapper.typeMap(ItemVendaLoja.class, ItemVendaDTO.class).addMapping(item -> item.getProduto().getId(), (dest, v) -> dest.setProduto((Long) v));
        modelMapper.typeMap(VendaCompraLojaVirtual.class, VendaCompraLojaVirtualDTO.class).addMapping(vendaCompraLojaVirtual1 -> vendaCompraLojaVirtual1.getPessoa().getId(), (dest, v) -> dest.setPessoa((Long) v));

        List<VendaCompraLojaVirtual> vendaCompraLojaVirtuals = new ArrayList<>();

        if (tipoConsulta.equalsIgnoreCase("POR_ID_PROD")) {
            vendaCompraLojaVirtuals = vendaCompraRepository.vendaPorProduto(Long.valueOf(valor));
        } else if (tipoConsulta.equalsIgnoreCase("POR_NOME")) {
            vendaCompraLojaVirtuals = vendaCompraRepository.findVendaByNomeProduto(valor.toUpperCase().trim());
        } else if (tipoConsulta.equalsIgnoreCase("POR_ENDERECO_COBRANCA")) {
            vendaCompraLojaVirtuals = vendaCompraRepository.vendaPorEnderecoCobranca(Long.valueOf(valor));
        } else if (tipoConsulta.equalsIgnoreCase("POR_ENDERECO_ENTREGA")) {
            vendaCompraLojaVirtuals = vendaCompraRepository.vendaPorEnderecoEntrega(Long.valueOf(valor));

        }

        List<VendaCompraLojaVirtualDTO> vendaCompraLojaVirtualDTOS = vendaCompraLojaVirtuals.stream().map(item -> modelMapper.map(item, VendaCompraLojaVirtualDTO.class)).collect(Collectors.toList());

        return vendaCompraLojaVirtualDTOS;
    }

    public List<VendaCompraLojaVirtualDTO> consultaVendaPorDataJPA(String data1, String data2) throws ParseException {
        modelMapper.typeMap(ItemVendaLoja.class, ItemVendaDTO.class).addMapping(item -> item.getProduto().getId(), (dest, v) -> dest.setProduto((Long) v));
        modelMapper.typeMap(VendaCompraLojaVirtual.class, VendaCompraLojaVirtualDTO.class).addMapping(vendaCompraLojaVirtual1 -> vendaCompraLojaVirtual1.getPessoa().getId(), (dest, v) -> dest.setPessoa((Long) v));


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse(data1);
        Date endDate = sdf.parse(data2);


        List<VendaCompraLojaVirtual> vendas = vendaCompraRepository.consultaVendaPorData(startDate, endDate);

        List<VendaCompraLojaVirtualDTO> vendasDTO = vendas.stream()
                .map(item -> modelMapper.map(item, VendaCompraLojaVirtualDTO.class))
                .collect(Collectors.toList());

        return vendasDTO;
    }

    //=========================================================================

    public List<VendaCompraLojaVirtualDTO> consultaPorCpfPessoa(String cpf) {
        modelMapper.typeMap(VendaCompraLojaVirtual.class, VendaCompraLojaVirtualDTO.class).addMapping(item -> item.getPessoa().getId(), (dest, v) -> dest.setPessoa((Long) v));
        modelMapper.typeMap(ItemVendaLoja.class, ItemVendaDTO.class).addMapping(item -> item.getProduto().getId(), (dest, v) -> dest.setProduto((Long) v));

        List<VendaCompraLojaVirtual> vendaCompraLojaVirtuals = vendaCompraRepository.consultaPorCpfPessoa(cpf);
        List<VendaCompraLojaVirtualDTO> vendaCompraLojaVirtualDTOS = vendaCompraLojaVirtuals.stream().map(item -> modelMapper.map(item, VendaCompraLojaVirtualDTO.class)).collect(Collectors.toList());

        return vendaCompraLojaVirtualDTOS;
    }

    public List<VendaCompraLojaVirtualDTO> consultaPorParteCpfPessoa(String cpf) {
        modelMapper.typeMap(VendaCompraLojaVirtual.class, VendaCompraLojaVirtualDTO.class).addMapping(item -> item.getPessoa().getId(), (dest, v) -> dest.setPessoa((Long) v));
        modelMapper.typeMap(ItemVendaLoja.class, ItemVendaDTO.class).addMapping(item -> item.getProduto().getId(), (dest, v) -> dest.setProduto((Long) v));

        List<VendaCompraLojaVirtual> vendaCompraLojaVirtuals = vendaCompraRepository.consultaPorParteCpfPessoa(cpf);
        List<VendaCompraLojaVirtualDTO> vendaCompraLojaVirtualDTOS = vendaCompraLojaVirtuals.stream().map(item -> modelMapper.map(item, VendaCompraLojaVirtualDTO.class)).collect(Collectors.toList());

        return vendaCompraLojaVirtualDTOS;
    }


    public List<VendaCompraLojaVirtualDTO> consultaPorPessoaId(Long id){

        modelMapper.typeMap(VendaCompraLojaVirtual.class, VendaCompraLojaVirtualDTO.class).addMapping(item -> item.getPessoa().getId(), (dest, v) -> dest.setPessoa((Long) v));
        modelMapper.typeMap(ItemVendaLoja.class, ItemVendaDTO.class).addMapping(item -> item.getProduto().getId(), (dest, v) -> dest.setProduto((Long) v));

        List<VendaCompraLojaVirtual> vendaCompraLojaVirtuals = vendaCompraRepository.consultaPorPessoaId(id);
        List<VendaCompraLojaVirtualDTO> vendaCompraLojaVirtualDTOS = vendaCompraLojaVirtuals.stream().map(item -> modelMapper.map(item, VendaCompraLojaVirtualDTO.class)).collect(Collectors.toList());

        return vendaCompraLojaVirtualDTOS;
    }

}
