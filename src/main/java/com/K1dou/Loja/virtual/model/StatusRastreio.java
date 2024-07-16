package com.K1dou.Loja.virtual.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "status_rastreio")
@SequenceGenerator(name = "seq_status_rastreio",sequenceName = "seq_status_rastreio",initialValue = 1,allocationSize = 1)
public class StatusRastreio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_status_rastreio")
    private Long id;

    private String urlRastreio;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "venda_compra_loja_virtual_id",nullable = false,foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,name = "venda_compra_loja_virtual_fk"))
    private VendaCompraLojaVirtual vendaCompraLojaVirtual;

    @JsonIgnore
    @ManyToOne(targetEntity = Pessoa.class)
    @JoinColumn(name = "empresa_id",nullable = false,foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,name = "pessoa_fk"))
    private PessoaJuridica empresa;

    public PessoaJuridica getEmpresa() {
        return empresa;
    }

    public void setEmpresa(PessoaJuridica empresa) {
        this.empresa = empresa;
    }

    public VendaCompraLojaVirtual getVendaCompraLojaVirtual() {
        return vendaCompraLojaVirtual;
    }

    public void setVendaCompraLojaVirtual(VendaCompraLojaVirtual vendaCompraLojaVirtual) {
        this.vendaCompraLojaVirtual = vendaCompraLojaVirtual;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrlRastreio() {
        return urlRastreio;
    }

    public void setUrlRastreio(String urlRastreio) {
        this.urlRastreio = urlRastreio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatusRastreio that = (StatusRastreio) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
