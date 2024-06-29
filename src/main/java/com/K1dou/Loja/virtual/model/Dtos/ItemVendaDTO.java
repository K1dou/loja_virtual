package com.K1dou.Loja.virtual.model.Dtos;

import com.K1dou.Loja.virtual.model.Produto;

public class ItemVendaDTO {

    private Double quantidade;
    private Long produtoId;

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public Long getProduto() {
        return produtoId;
    }

    public void setProduto(Long produtoId) {
        this.produtoId = produtoId;
    }
}
