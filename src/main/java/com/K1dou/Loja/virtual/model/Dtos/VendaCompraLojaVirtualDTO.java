package com.K1dou.Loja.virtual.model.Dtos;

import com.K1dou.Loja.virtual.enums.TipoEndereco;
import com.K1dou.Loja.virtual.model.*;

import java.math.BigDecimal;
import java.util.Date;

public class VendaCompraLojaVirtualDTO {

    private BigDecimal valorTotal;


    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }


    public BigDecimal getValorTotal() {
        return valorTotal;
    }
}
