package com.K1dou.Loja.virtual.model.Dtos;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

public class CupDescDTO {

    private Long id;

    @Column(nullable = false)
    private String codDesc;

    private BigDecimal valorRealDesc;
    private BigDecimal valorPorcentDesc;

    @Column(nullable = false)
    private Date dataValidadeCupom;

    private Long empresaId;

    public CupDescDTO() {
    }

    public CupDescDTO(Long id, String codDesc, BigDecimal valorRealDesc, BigDecimal valorPorcentDesc, Date dataValidadeCupom, Long empresaId) {
        this.id = id;
        this.codDesc = codDesc;
        this.valorRealDesc = valorRealDesc;
        this.valorPorcentDesc = valorPorcentDesc;
        this.dataValidadeCupom = dataValidadeCupom;
        this.empresaId = empresaId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodDesc() {
        return codDesc;
    }

    public void setCodDesc(String codDesc) {
        this.codDesc = codDesc;
    }

    public BigDecimal getValorRealDesc() {
        return valorRealDesc;
    }

    public void setValorRealDesc(BigDecimal valorRealDesc) {
        this.valorRealDesc = valorRealDesc;
    }

    public BigDecimal getValorPorcentDesc() {
        return valorPorcentDesc;
    }

    public void setValorPorcentDesc(BigDecimal valorPorcentDesc) {
        this.valorPorcentDesc = valorPorcentDesc;
    }

    public Date getDataValidadeCupom() {
        return dataValidadeCupom;
    }

    public void setDataValidadeCupom(Date dataValidadeCupom) {
        this.dataValidadeCupom = dataValidadeCupom;
    }

    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }
}
