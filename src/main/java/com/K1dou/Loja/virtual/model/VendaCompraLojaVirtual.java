package com.K1dou.Loja.virtual.model;


import com.K1dou.Loja.virtual.enums.StatusVendaLojaVirtual;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "vd_cp_loja_virt")
@SequenceGenerator(name = "seq_vd_cp_loja_virt",sequenceName = "seq_vd_cp_loja_virt",initialValue = 1,allocationSize = 1)
public class VendaCompraLojaVirtual {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_vd_cp_loja_virt")
    private Long id;

    @ManyToOne(targetEntity = Pessoa.class,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "pessoa_id",nullable = false,foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,name = "pessoa_fk"))
    private PessoaFisica pessoa;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "endereco_entrega_id",nullable = false,foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,name = "endereco_entrega_fk"))
    private Endereco enderecoEntrega;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "endereco_cobranca_id",nullable = false,foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,name = "endereco_cobranca_fk"))
    private Endereco enderecoCobranca;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    private BigDecimal valorDesconto;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "forma_pagamento_id",nullable = false,foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,name = "forma_pagamento_fk"))
    private FormaPagamento formaPagamento;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "nota_fiscal_venda_id",nullable = false,foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,name = "nota_fiscal_venda_fk"))
    private NotaFiscalVenda notaFiscalVenda;

    @ManyToOne
    @JoinColumn(name = "cup_desc_id",foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,name = "cup_desc_fk"))
    private CupDesc cupDesc;

    @Column(nullable = false)
    private BigDecimal valorFrete;

    @Column(nullable = false)
    private Integer diaEntrega;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataVenda;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataEntrega;

    @ManyToOne(targetEntity = Pessoa.class)
    @JoinColumn(name = "empresa_id",nullable = false,foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,name = "pessoa_fk"))
    private PessoaJuridica empresa;

    @OneToMany(mappedBy = "vendaCompraLojaVirtual",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<ItemVendaLoja>itemVendaLojas;

    private Boolean excluido = Boolean.FALSE;

    @NotNull(message = "Status da venda ou compra deve ser informado")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusVendaLojaVirtual statusVendaLojaVirtual;

    private String codigoEtiqueta;

    private String urlImprimeEtiqueta;

    //Frete que foi escolhido pelo cliente momento da compra
    private String servicoTransportadora;

    public String getServicoTransportadora() {
        return servicoTransportadora;
    }

    public void setServicoTransportadora(String servicoTransportadora) {
        this.servicoTransportadora = servicoTransportadora;
    }

    public String getCodigoEtiqueta() {
        return codigoEtiqueta;
    }

    public void setCodigoEtiqueta(String codigoEtiqueta) {
        this.codigoEtiqueta = codigoEtiqueta;
    }

    public String getUrlImprimeEtiqueta() {
        return urlImprimeEtiqueta;
    }

    public void setUrlImprimeEtiqueta(String urlImprimeEtiqueta) {
        this.urlImprimeEtiqueta = urlImprimeEtiqueta;
    }

    public StatusVendaLojaVirtual getStatusVendaLojaVirtual() {
        return statusVendaLojaVirtual;
    }

    public void setStatusVendaLojaVirtual(StatusVendaLojaVirtual statusVendaLojaVirtual) {
        this.statusVendaLojaVirtual = statusVendaLojaVirtual;
    }

    public Boolean getExcluido() {
        return excluido;
    }

    public void setExcluido(Boolean excluido) {
        this.excluido = excluido;
    }

    public List<ItemVendaLoja> getItemVendaLojas() {
        return itemVendaLojas;
    }

    public void setItemVendaLojas(List<ItemVendaLoja> itemVendaLojas) {
        this.itemVendaLojas = itemVendaLojas;
    }

    public PessoaJuridica getEmpresa() {
        return empresa;
    }

    public void setEmpresa(PessoaJuridica empresa) {
        this.empresa = empresa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PessoaFisica getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaFisica pessoa) {
        this.pessoa = pessoa;
    }

    public Endereco getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(Endereco enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public Endereco getEnderecoCobranca() {
        return enderecoCobranca;
    }

    public void setEnderecoCobranca(Endereco enderecoCobranca) {
        this.enderecoCobranca = enderecoCobranca;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public NotaFiscalVenda getNotaFiscalVenda() {
        return notaFiscalVenda;
    }

    public void setNotaFiscalVenda(NotaFiscalVenda notaFiscalVenda) {
        this.notaFiscalVenda = notaFiscalVenda;
    }

    public CupDesc getCupDesc() {
        return cupDesc;
    }

    public void setCupDesc(CupDesc cupDesc) {
        this.cupDesc = cupDesc;
    }

    public BigDecimal getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(BigDecimal valorFrete) {
        this.valorFrete = valorFrete;
    }

    public Integer getDiaEntrega() {
        return diaEntrega;
    }

    public void setDiaEntrega(Integer diaEntrega) {
        this.diaEntrega = diaEntrega;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public Date getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(Date dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VendaCompraLojaVirtual that = (VendaCompraLojaVirtual) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
