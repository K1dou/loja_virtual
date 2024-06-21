package com.K1dou.Loja.virtual.model.Dtos;

import com.K1dou.Loja.virtual.model.PessoaFisica;
import com.K1dou.Loja.virtual.model.PessoaJuridica;
import com.K1dou.Loja.virtual.model.Produto;

public class AvaliacaoProdutoDTO {

    private Long id;
    private Integer nota;
    private String descricao;
    private PessoaFisica pessoa;
    private Produto produto;
    private PessoaJuridica empresa;

    public AvaliacaoProdutoDTO(Long id, Integer nota, String descricao, PessoaFisica pessoa, Produto produto, PessoaJuridica empresa) {
        this.id = id;
        this.nota = nota;
        this.descricao = descricao;
        this.pessoa = pessoa;
        this.produto = produto;
        this.empresa = empresa;
    }

    public AvaliacaoProdutoDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public PessoaFisica getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaFisica pessoa) {
        this.pessoa = pessoa;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public PessoaJuridica getEmpresa() {
        return empresa;
    }

    public void setEmpresa(PessoaJuridica empresa) {
        this.empresa = empresa;
    }
}
