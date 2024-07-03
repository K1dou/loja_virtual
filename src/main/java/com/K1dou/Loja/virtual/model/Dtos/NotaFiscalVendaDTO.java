package com.K1dou.Loja.virtual.model.Dtos;

import com.K1dou.Loja.virtual.model.PessoaJuridica;
import jakarta.persistence.Column;

public class NotaFiscalVendaDTO {

    private Long id;

    private String numero;

    private String serie;

    private String tipo;

    private String xml;

    private String pdf;

    //
    private Long vendaCompraLojaVirtualId;

    //
    private Long empresaId;

    public NotaFiscalVendaDTO() {
    }

    public NotaFiscalVendaDTO(Long id, String numero, String serie, String tipo, String xml, String pdf, Long vendaCompraLojaVirtualId, Long empresaId) {
        this.id = id;
        this.numero = numero;
        this.serie = serie;
        this.tipo = tipo;
        this.xml = xml;
        this.pdf = pdf;
        this.vendaCompraLojaVirtualId = vendaCompraLojaVirtualId;
        this.empresaId = empresaId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public Long getVendaCompraLojaVirtualId() {
        return vendaCompraLojaVirtualId;
    }

    public void setVendaCompraLojaVirtualId(Long vendaCompraLojaVirtualId) {
        this.vendaCompraLojaVirtualId = vendaCompraLojaVirtualId;
    }

    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }
}
