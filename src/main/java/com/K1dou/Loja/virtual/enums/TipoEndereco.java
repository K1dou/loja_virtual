package com.K1dou.Loja.virtual.enums;

public enum TipoEndereco {

    COBRANCA("Cobranca"),
    ENTREGA("Entrega");

    private String descricao;


    TipoEndereco(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return this.descricao;
    }
}
