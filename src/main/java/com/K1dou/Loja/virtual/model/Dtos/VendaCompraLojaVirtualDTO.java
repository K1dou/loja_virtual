package com.K1dou.Loja.virtual.model.Dtos;

import com.K1dou.Loja.virtual.model.*;

import java.math.BigDecimal;
import java.util.Date;

public class VendaCompraLojaVirtualDTO {

    private Long id;
    private PessoaFisica pessoa;
    private Endereco enderecoEntrega;
    private Endereco enderecoCobranca;
    private BigDecimal valorTotal;
    private BigDecimal valorDesconto;
    private FormaPagamento formaPagamento;
    private NotaFiscalVenda notaFiscalVenda;
    private CupDesc cupDesc;
    private BigDecimal valorFrete;
    private Integer diaEntrega;
    private Date dataVenda;
    private Date dataEntrega;
    private PessoaJuridica empresa;

    public VendaCompraLojaVirtualDTO() {
    }

    public VendaCompraLojaVirtualDTO(Long id, PessoaFisica pessoa, Endereco enderecoEntrega, Endereco enderecoCobranca, BigDecimal valorTotal, BigDecimal valorDesconto, FormaPagamento formaPagamento, NotaFiscalVenda notaFiscalVenda, CupDesc cupDesc, BigDecimal valorFrete, Integer diaEntrega, Date dataVenda, Date dataEntrega, PessoaJuridica empresa) {
        this.id = id;
        this.pessoa = pessoa;
        this.enderecoEntrega = enderecoEntrega;
        this.enderecoCobranca = enderecoCobranca;
        this.valorTotal = valorTotal;
        this.valorDesconto = valorDesconto;
        this.formaPagamento = formaPagamento;
        this.notaFiscalVenda = notaFiscalVenda;
        this.cupDesc = cupDesc;
        this.valorFrete = valorFrete;
        this.diaEntrega = diaEntrega;
        this.dataVenda = dataVenda;
        this.dataEntrega = dataEntrega;
        this.empresa = empresa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PessoaFisica getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaFisica pessoa) {
        this.pessoa = pessoa;
    }

    public Endereco getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(Endereco enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public Endereco getEnderecoCobranca() {
        return enderecoCobranca;
    }

    public void setEnderecoCobranca(Endereco enderecoCobranca) {
        this.enderecoCobranca = enderecoCobranca;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public NotaFiscalVenda getNotaFiscalVenda() {
        return notaFiscalVenda;
    }

    public void setNotaFiscalVenda(NotaFiscalVenda notaFiscalVenda) {
        this.notaFiscalVenda = notaFiscalVenda;
    }

    public CupDesc getCupDesc() {
        return cupDesc;
    }

    public void setCupDesc(CupDesc cupDesc) {
        this.cupDesc = cupDesc;
    }

    public BigDecimal getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(BigDecimal valorFrete) {
        this.valorFrete = valorFrete;
    }

    public Integer getDiaEntrega() {
        return diaEntrega;
    }

    public void setDiaEntrega(Integer diaEntrega) {
        this.diaEntrega = diaEntrega;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public Date getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(Date dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public PessoaJuridica getEmpresa() {
        return empresa;
    }

    public void setEmpresa(PessoaJuridica empresa) {
        this.empresa = empresa;
    }
}
