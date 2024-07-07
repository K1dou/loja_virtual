package com.K1dou.Loja.virtual.service;

import com.K1dou.Loja.virtual.exceptions.ExceptionLojaVirtual;
import com.K1dou.Loja.virtual.model.ContaPagar;
import com.K1dou.Loja.virtual.model.Dtos.ObjetoRequisicaoRelatorioProdCompraNotaFiscalDTO;
import com.K1dou.Loja.virtual.model.NotaFiscalCompra;
import com.K1dou.Loja.virtual.model.PessoaJuridica;
import com.K1dou.Loja.virtual.repository.ContaPagarRepository;
import com.K1dou.Loja.virtual.repository.NotaFiscalCompraRepository;
import com.K1dou.Loja.virtual.repository.PessoaJuridicaRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NotaFiscalCompraService {

    @Autowired
    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private NotaFiscalCompraRepository notaFiscalCompraRepository;

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;
    @Autowired
    ContaPagarRepository contaPagarRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<ObjetoRequisicaoRelatorioProdCompraNotaFiscalDTO> relatorioProdCompraNotaFiscal(ObjetoRequisicaoRelatorioProdCompraNotaFiscalDTO dto) {

        List<ObjetoRequisicaoRelatorioProdCompraNotaFiscalDTO> objetos = new ArrayList<>();

        String sql = "select p.id as codigoProduto, p.nome as nomeProduto, "
                + " p.valor_venda as valorVendaProduto, ntp.quantidade as quantidadeComprada, "
                + " pj.id as codigoFornecedor, pj.nome as nomeFornecedor,cfc.data_compra as dataCompra "
                + " from nota_fiscal_compra as cfc "
                + " inner join nota_item_produto as ntp on  cfc.id = nota_fiscal_compra_id "
                + " inner join produto as p on p.id = ntp.produto_id "
                + " inner join pessoa_juridica as pj on pj.id = cfc.pessoa_id where ";

        sql += " cfc.data_compra >='" + dto.getDataInicial() + "' and ";
        sql += " cfc.data_compra <= '" + dto.getDataFinal() + "' ";

        if (dto.getCodigoNota()!=null) {
            sql += " and cfc.id = " + dto.getCodigoNota() + " ";
        } else if (dto.getCodigoProduto()!=null) {
            sql += " and p.id = " + dto.getCodigoProduto() + " ";
        } else if (dto.getNomeProduto()!=null) {
            sql += " and upper(p.nome) like upper('%" + dto.getNomeProduto() + "%')";
        } else {
            sql += " and upper(pj.nome) like upper('%" + dto.getNomeFornecedor() + "'%)";
        }

        objetos = jdbcTemplate.query(sql, new BeanPropertyRowMapper(ObjetoRequisicaoRelatorioProdCompraNotaFiscalDTO.class));


        return objetos;
    }

    public NotaFiscalCompra cadastrarNotaFiscal(NotaFiscalCompra dto) throws ExceptionLojaVirtual {

        NotaFiscalCompra notaFiscalCompra = new NotaFiscalCompra();
        PessoaJuridica empresa = pessoaJuridicaRepository.findById(dto.getEmpresa().getId()).orElseThrow(() -> new ExceptionLojaVirtual("Id da empresa esta invalido ou não existe: " + dto.getEmpresa().getId()));
        PessoaJuridica pessoa = pessoaJuridicaRepository.findById(dto.getPessoa().getId()).orElseThrow(() -> new ExceptionLojaVirtual("Id da Pessoa esta invalido ou não existe: " + dto.getPessoa().getId()));
        ContaPagar contaPagar = contaPagarRepository.findById(dto.getContaPagar().getId()).orElseThrow(() -> new ExceptionLojaVirtual("Id da Conta pagar esta invalido ou não existe: " + dto.getContaPagar().getId()));

        notaFiscalCompra.setValorIcms(dto.getValorIcms());
        notaFiscalCompra.setValorDesconto(dto.getValorDesconto());
        notaFiscalCompra.setValorTotal(dto.getValorTotal());
        notaFiscalCompra.setSerieNota(dto.getSerieNota());
        notaFiscalCompra.setNumeroNota(dto.getNumeroNota());
        notaFiscalCompra.setDescricaoObs(dto.getDescricaoObs());
        notaFiscalCompra.setEmpresa(empresa);
        notaFiscalCompra.setPessoa(pessoa);
        notaFiscalCompra.setContaPagar(contaPagar);
        notaFiscalCompra.setDataCompra(Date.from(Instant.now()));
        notaFiscalCompraRepository.save(notaFiscalCompra);

        return notaFiscalCompra;

    }

    public List<NotaFiscalCompra> findByDesc(String desc) {
        List<NotaFiscalCompra> notaFiscalCompras = notaFiscalCompraRepository.buscarPorDesc(desc);
        return notaFiscalCompras;
    }

    public List<NotaFiscalCompra> findAll() {
        List<NotaFiscalCompra> notaFiscalCompras = notaFiscalCompraRepository.findAll();
        return notaFiscalCompras;
    }

    public NotaFiscalCompra findById(Long id) throws ExceptionLojaVirtual {
        NotaFiscalCompra notaFiscalCompra = notaFiscalCompraRepository.findById(id).orElseThrow(() -> new ExceptionLojaVirtual("Id da nota  fiscal: " + id + ". é invalido ou não existe"));
        return notaFiscalCompra;
    }

    public void deleteById(Long id) throws ExceptionLojaVirtual {
        NotaFiscalCompra notaFiscalCompra = notaFiscalCompraRepository.findById(id).orElseThrow(() -> new ExceptionLojaVirtual("Id da nota  fiscal: " + id + ". é invalido ou não existe"));

        notaFiscalCompraRepository.deleteById(id);
    }

    @Transactional
    @Modifying
    public NotaFiscalCompra updateNotaFiscalCompra(NotaFiscalCompra notaFiscalCompra) throws ExceptionLojaVirtual {

        NotaFiscalCompra notaFiscalCompra1 = notaFiscalCompraRepository.findById(notaFiscalCompra.getId()).orElseThrow(() -> new ExceptionLojaVirtual("Id não está criado"));
        NotaFiscalCompra notaFiscalCompra2 = notaFiscalCompra;
        notaFiscalCompraRepository.save(notaFiscalCompra2);
        return notaFiscalCompra2;
    }


}
