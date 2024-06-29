package com.K1dou.Loja.virtual.model.Dtos;

import com.K1dou.Loja.virtual.enums.TipoEndereco;
import com.K1dou.Loja.virtual.model.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class VendaCompraLojaVirtualDTO {

    private Long id;
    private Long  idPessoa;
    private BigDecimal valorTotal;
    private Endereco enderecoCobranca;
    private Endereco enderecoEntrega;
    private BigDecimal valorDesconto;
    private BigDecimal valorFrete;
    private List<ItemVendaDTO>itemVendaLojas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Long idPessoa) {
        this.idPessoa = idPessoa;
    }

    public Long getPessoa() {
        return idPessoa;
    }

    public void setPessoa(Long idPessoa) {
        this.idPessoa = idPessoa;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Endereco getEnderecoCobranca() {
        return enderecoCobranca;
    }

    public void setEnderecoCobranca(Endereco enderecoCobranca) {
        this.enderecoCobranca = enderecoCobranca;
    }

    public Endereco getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(Endereco enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public BigDecimal getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public BigDecimal getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(BigDecimal valorFrete) {
        this.valorFrete = valorFrete;
    }

    public List<ItemVendaDTO> getItemVendaLojas() {
        return itemVendaLojas;
    }

    public void setItemVendaLojas(List<ItemVendaDTO> itemVendaLojas) {
        this.itemVendaLojas = itemVendaLojas;
    }
}
