package com.K1dou.Loja.virtual.enums;

public enum StatusVendaLojaVirtual {

    FINALIZADA("Finalizada"),
    CANCELADA("Cancelada"),
    ABANDONOU_CARRINHO("Abandonou carrinho");

    private String desc;

    StatusVendaLojaVirtual(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
