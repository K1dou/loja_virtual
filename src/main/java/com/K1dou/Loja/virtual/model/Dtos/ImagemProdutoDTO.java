package com.K1dou.Loja.virtual.model.Dtos;

import com.K1dou.Loja.virtual.model.PessoaJuridica;
import com.K1dou.Loja.virtual.model.Produto;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class ImagemProdutoDTO {

    private Long id;
    private String imagemOriginal;
    private String imagemMiniatura;
    private Produto produto;
    private PessoaJuridica empresa;

    public ImagemProdutoDTO() {
    }

    public ImagemProdutoDTO(Long id, String imagemOriginal, String imagemMiniatura, Produto produto, PessoaJuridica empresa) {
        this.id = id;
        this.imagemOriginal = imagemOriginal;
        this.imagemMiniatura = imagemMiniatura;
        this.produto = produto;
        this.empresa = empresa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImagemOriginal() {
        return imagemOriginal;
    }

    public void setImagemOriginal(String imagemOriginal) {
        this.imagemOriginal = imagemOriginal;
    }

    public String getImagemMiniatura() {
        return imagemMiniatura;
    }

    public void setImagemMiniatura(String imagemMiniatura) {
        this.imagemMiniatura = imagemMiniatura;
    }

    @JsonIgnore
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
