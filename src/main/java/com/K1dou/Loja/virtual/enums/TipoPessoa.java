package com.K1dou.Loja.virtual.enums;

public enum TipoPessoa {

    JURIDICA("Juridica"),
    JURIDICA_FORNECEDOR("Juridica e fornecedor"),
    FISICA("Fisica");

    private String descricao;

    TipoPessoa(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
