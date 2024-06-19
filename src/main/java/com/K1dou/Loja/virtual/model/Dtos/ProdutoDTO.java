package com.K1dou.Loja.virtual.model.Dtos;

import com.K1dou.Loja.virtual.model.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDTO {

    private Long id;
    private String tipoUnidade;
    private String nome;
    private Boolean ativo;
    private String descricao;
    private Double peso;
    private Double largura;
    private Double altura;
    private CategoriaProduto categoriaProduto;
    private MarcaProduto marcaProduto;
    private PessoaJuridica empresa;
    private Double profundidade;
    private BigDecimal valorVenda;
    private Integer qtdEstoque;
    private Integer qtdeAlertaEstoque;
    private String linkYoutube;
    private Boolean alertaQtdEstoque;
    private Integer qtdClique;
    private List<ImagemProduto> imagens = new ArrayList<>();

    public ProdutoDTO() {
    }


    public ProdutoDTO(Long id, String tipoUnidade, String nome, Boolean ativo, String descricao, Double peso, Double largura, Double altura, CategoriaProduto categoriaProduto, MarcaProduto marcaProduto, PessoaJuridica empresa, Double profundidade, BigDecimal valorVenda, Integer qtdEstoque, Integer qtdeAlertaEstoque, String linkYoutube, Boolean alertaQtdEstoque, Integer qtdClique, List<ImagemProduto> imagens) {
        this.id = id;
        this.tipoUnidade = tipoUnidade;
        this.nome = nome;
        this.ativo = ativo;
        this.descricao = descricao;
        this.peso = peso;
        this.largura = largura;
        this.altura = altura;
        this.categoriaProduto = categoriaProduto;
        this.marcaProduto = marcaProduto;
        this.empresa = empresa;
        this.profundidade = profundidade;
        this.valorVenda = valorVenda;
        this.qtdEstoque = qtdEstoque;
        this.qtdeAlertaEstoque = qtdeAlertaEstoque;
        this.linkYoutube = linkYoutube;
        this.alertaQtdEstoque = alertaQtdEstoque;
        this.qtdClique = qtdClique;
        this.imagens = imagens;
    }

    public List<ImagemProduto> getImagens() {
        return imagens;
    }

    public void setImagens(List<ImagemProduto> imagens) {
        this.imagens = imagens;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoUnidade() {
        return tipoUnidade;
    }

    public void setTipoUnidade(String tipoUnidade) {
        this.tipoUnidade = tipoUnidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getLargura() {
        return largura;
    }

    public void setLargura(Double largura) {
        this.largura = largura;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public CategoriaProduto getCategoriaProduto() {
        return categoriaProduto;
    }

    public void setCategoriaProduto(CategoriaProduto categoriaProduto) {
        this.categoriaProduto = categoriaProduto;
    }

    public MarcaProduto getMarcaProduto() {
        return marcaProduto;
    }

    public void setMarcaProduto(MarcaProduto marcaProduto) {
        this.marcaProduto = marcaProduto;
    }

    public PessoaJuridica getEmpresa() {
        return empresa;
    }

    public void setEmpresa(PessoaJuridica empresa) {
        this.empresa = empresa;
    }

    public Double getProfundidade() {
        return profundidade;
    }

    public void setProfundidade(Double profundidade) {
        this.profundidade = profundidade;
    }

    public BigDecimal getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(BigDecimal valorVenda) {
        this.valorVenda = valorVenda;
    }

    public Integer getQtdEstoque() {
        return qtdEstoque;
    }

    public void setQtdEstoque(Integer qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }

    public Integer getQtdeAlertaEstoque() {
        return qtdeAlertaEstoque;
    }

    public void setQtdeAlertaEstoque(Integer qtdeAlertaEstoque) {
        this.qtdeAlertaEstoque = qtdeAlertaEstoque;
    }

    public String getLinkYoutube() {
        return linkYoutube;
    }

    public void setLinkYoutube(String linkYoutube) {
        this.linkYoutube = linkYoutube;
    }

    public Boolean getAlertaQtdEstoque() {
        return alertaQtdEstoque;
    }

    public void setAlertaQtdEstoque(Boolean alertaQtdEstoque) {
        this.alertaQtdEstoque = alertaQtdEstoque;
    }

    public Integer getQtdClique() {
        return qtdClique;
    }

    public void setQtdClique(Integer qtdClique) {
        this.qtdClique = qtdClique;
    }
}
