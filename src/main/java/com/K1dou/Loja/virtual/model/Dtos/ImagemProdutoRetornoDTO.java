package com.K1dou.Loja.virtual.model.Dtos;

public class ImagemProdutoRetornoDTO {

    private Long id;
    private String imagemOriginal;
    private String imagemMiniatura;
    private Long produto;
    private Long empresa;

    public ImagemProdutoRetornoDTO() {
    }

    public ImagemProdutoRetornoDTO(Long id, String imagemOriginal, String imagemMiniatura, Long produto, Long empresa) {
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
