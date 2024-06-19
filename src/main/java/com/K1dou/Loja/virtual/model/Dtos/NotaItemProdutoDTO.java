package com.K1dou.Loja.virtual.model.Dtos;

import com.K1dou.Loja.virtual.model.NotaFiscalCompra;
import com.K1dou.Loja.virtual.model.PessoaJuridica;
import com.K1dou.Loja.virtual.model.Produto;

public class NotaItemProdutoDTO {


    private Long id;
    private Double quantidade;
    private NotaFiscalCompra notaFiscalCompra;
    private Produto produto;
    private PessoaJuridica empresa;

    public NotaItemProdutoDTO(Long id, Double quantidade, NotaFiscalCompra notaFiscalCompra, Produto produto, PessoaJuridica empresa) {
        this.id = id;
        this.quantidade = quantidade;
        this.notaFiscalCompra = notaFiscalCompra;
        this.produto = produto;
        this.empresa = empresa;
    }

    public PessoaJuridica getEmpresa() {
        return empresa;
    }

    public void setEmpresa(PessoaJuridica empresa) {
        this.empresa = empresa;
    }

    public NotaItemProdutoDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public NotaFiscalCompra getNotaFiscalCompra() {
        return notaFiscalCompra;
    }

    public void setNotaFiscalCompra(NotaFiscalCompra notaFiscalCompra) {
        this.notaFiscalCompra = notaFiscalCompra;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public PessoaJuridica getPessoa() {
        return empresa;
    }

    public void setPessoa(PessoaJuridica empresa) {
        this.empresa = empresa;
    }


}

