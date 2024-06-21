package com.K1dou.Loja.virtual.model.Dtos;



public class AvaliacaoProdutoRespostaDTO {

    private Long id;
    private Integer nota;
    private String descricao;
    private Long pessoa;
    private Long produto;
    private Long empresa;

    public AvaliacaoProdutoRespostaDTO() {
    }

    public AvaliacaoProdutoRespostaDTO(Long id, Integer nota, String descricao, Long pessoa, Long produto, Long empresa) {
        this.id = id;
        this.nota = nota;
        this.descricao = descricao;
        this.pessoa = pessoa;
        this.produto = produto;
        this.empresa = empresa;
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

    public Long getPessoa() {
        return pessoa;
    }

    public void setPessoa(Long pessoa) {
        this.pessoa = pessoa;
    }

    public Long getProduto() {
        return produto;
    }

    public void setProduto(Long produto) {
        this.produto = produto;
    }

    public Long getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Long empresa) {
        this.empresa = empresa;
    }
}
