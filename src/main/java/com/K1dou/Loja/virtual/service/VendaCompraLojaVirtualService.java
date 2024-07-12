package com.K1dou.Loja.virtual.service;

import com.K1dou.Loja.virtual.controller.PessoaController;
import com.K1dou.Loja.virtual.enums.ApiTokenIntegracao;
import com.K1dou.Loja.virtual.enums.StatusContaReceber;
import com.K1dou.Loja.virtual.exceptions.ExceptionLojaVirtual;
import com.K1dou.Loja.virtual.model.*;
import com.K1dou.Loja.virtual.model.ContaReceber;
import com.K1dou.Loja.virtual.model.Dtos.ItemVendaDTO;
import com.K1dou.Loja.virtual.model.Dtos.VendaCompraLojaVirtualDTO;
import com.K1dou.Loja.virtual.model.Dtos.transporteDTO.ConsultaFreteDTO;
import com.K1dou.Loja.virtual.model.Dtos.transporteDTO.EmpresaTransporteDTO;
import com.K1dou.Loja.virtual.repository.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import okhttp3.*;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import javax.mail.MessagingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
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
    @Autowired
    private ContaReceberRepository contaReceberRepository;

    @Autowired
    private ServiceSendEmail serviceSendEmail;

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

        ContaReceber contaReceber = new ContaReceber();
        contaReceber.setDescricao("VendaLojaVirtual" + vendaCompraLojaVirtual.getId());
        contaReceber.setDtPagamento(Calendar.getInstance().getTime());
        contaReceber.setDtVencimento(Calendar.getInstance().getTime());
        contaReceber.setEmpresa(vendaCompraLojaVirtual.getEmpresa());
        contaReceber.setPessoa(vendaCompraLojaVirtual.getPessoa());
        contaReceber.setStatus(StatusContaReceber.QUITADA);
        contaReceber.setValorDesconto(vendaCompraLojaVirtual.getValorDesconto());
        contaReceber.setValorTotal(vendaCompraLojaVirtual.getValorTotal());
        contaReceberRepository.saveAndFlush(contaReceber);

        /*Emil para o comprador*/
        StringBuilder msgemail = new StringBuilder();
        msgemail.append("Olá, ").append(pessoaFisica.getNome()).append("</br>");
        msgemail.append("Você realizou a compra de nº: ").append(vendaCompraLojaVirtual.getId()).append("</br>");
        msgemail.append("Na loja ").append(vendaCompraLojaVirtual.getEmpresa().getNomeFantasia());
        /*assunto, msg, destino*/
        serviceSendEmail.enviarEmailHtml("Compra Realizada", msgemail.toString(), pessoaFisica.getEmail());

        /*Email para o vendedor*/
        msgemail = new StringBuilder();
        msgemail.append("Você realizou uma venda, nº " ).append(vendaCompraLojaVirtual.getId());
        serviceSendEmail.enviarEmailHtml("Venda Realizada", msgemail.toString(), vendaCompraLojaVirtual.getEmpresa().getEmail());




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

    public List<VendaCompraLojaVirtualDTO> consultaPorPessoaId(Long id) {

        modelMapper.typeMap(VendaCompraLojaVirtual.class, VendaCompraLojaVirtualDTO.class).addMapping(item -> item.getPessoa().getId(), (dest, v) -> dest.setPessoa((Long) v));
        modelMapper.typeMap(ItemVendaLoja.class, ItemVendaDTO.class).addMapping(item -> item.getProduto().getId(), (dest, v) -> dest.setProduto((Long) v));

        List<VendaCompraLojaVirtual> vendaCompraLojaVirtuals = vendaCompraRepository.consultaPorPessoaId(id);
        List<VendaCompraLojaVirtualDTO> vendaCompraLojaVirtualDTOS = vendaCompraLojaVirtuals.stream().map(item -> modelMapper.map(item, VendaCompraLojaVirtualDTO.class)).collect(Collectors.toList());

        return vendaCompraLojaVirtualDTOS;
    }

    public List<EmpresaTransporteDTO>consultaFrete(ConsultaFreteDTO dto) throws IOException {


        ObjectMapper objectMapper = new ObjectMapper();
        //transforma o dto em uma String do json
        String json = objectMapper.writeValueAsString(dto);

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(ApiTokenIntegracao.URL_MELHOR_ENVIO_SAND_BOX +"api/v2/me/shipment/calculate")
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " +ApiTokenIntegracao.TOKEN_MELHOR_ENVIO_SAND_BOX)
                .addHeader("User-Agent", "hique1276@gmail.com")
                .build();

        Response response = client.newCall(request).execute();

        //criaa um jsonNode, com o json em string com a resposta
        JsonNode jsonNode = new ObjectMapper().readTree(response.body().string());

        //Um iterador é criado para percorrer os nós filhos do JsonNode principal.
        Iterator<JsonNode> iterator = jsonNode.iterator();

        List<EmpresaTransporteDTO> empresaTransporteDTOs = new ArrayList<EmpresaTransporteDTO>();

        //Um loop while é usado para iterar através de todos os nós filhos no
        // jsonNode. O método iterator.hasNext() verifica se há mais nós para processar,
        // e iterator.next() retorna o próximo nó.
        while(iterator.hasNext()) {
            JsonNode node = iterator.next();

            EmpresaTransporteDTO empresaTransporteDTO = new EmpresaTransporteDTO();

            if (node.get("id") != null) {
                empresaTransporteDTO.setId(node.get("id").asText());
            }

            if (node.get("name") != null) {
                empresaTransporteDTO.setNome(node.get("name").asText());
            }

            if (node.get("price") != null) {
                empresaTransporteDTO.setValor(node.get("price").asText());
            }

            if (node.get("company") != null) {
                empresaTransporteDTO.setEmpresa(node.get("company").get("name").asText());
                empresaTransporteDTO.setPicture(node.get("company").get("picture").asText());
            }

            if (empresaTransporteDTO.dadosOK()) {
                empresaTransporteDTOs.add(empresaTransporteDTO);
            }
        }
      return empresaTransporteDTOs;

    }




}
