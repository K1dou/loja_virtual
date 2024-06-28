package com.K1dou.Loja.virtual.model.Dtos;

import com.K1dou.Loja.virtual.enums.TipoEndereco;
import com.K1dou.Loja.virtual.model.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class VendaCompraLojaVirtualDTO {

    private Pessoa pessoa;
    private BigDecimal valorTotal;
    private Endereco cobranca;
    private Endereco entrega;
    private BigDecimal valorDesc;
    private BigDecimal valorFrete;
    private List<ItemVendaDTO>itemVendaDTOS;


    public List<ItemVendaDTO> getItemVendaDTOS() {
        return itemVendaDTOS;
    }

    public void setItemVendaDTOS(List<ItemVendaDTO> itemVendaDTOS) {
        this.itemVendaDTOS = itemVendaDTOS;
    }

    public BigDecimal getValorDesc() {
        return valorDesc;
    }

    public void setValorDesc(BigDecimal valorDesc) {
        this.valorDesc = valorDesc;
    }

    public BigDecimal getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(BigDecimal valorFrete) {
        this.valorFrete = valorFrete;
    }

    public Endereco getCobranca() {
        return cobranca;
    }

    public void setCobranca(Endereco cobranca) {
        this.cobranca = cobranca;
    }

    public Endereco getEntrega() {
        return entrega;
    }

    public void setEntrega(Endereco entrega) {
        this.entrega = entrega;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }


    public BigDecimal getValorTotal() {
        return valorTotal;
    }
}
