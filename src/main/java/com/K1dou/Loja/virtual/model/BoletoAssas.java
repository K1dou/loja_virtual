package com.K1dou.Loja.virtual.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@SequenceGenerator(name = "seq_boleto_assas",sequenceName ="seq_boleto_assas",allocationSize = 1,initialValue = 1)
public class BoletoAssas {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_boleto_assas")
    private Long id;
    /* Código de controle do boleto */
    private String code = "";

    /* Imprime o boleto completo com todas as parcelas */
    private String link;

    /*
     * Mostra um telinha de checkout da Juno com os boleto, pix e cartão pagos ou
     * vencidos
     */
    private String checkoutUrl = "";

    private boolean quitado = false;

    private String dataVencimento = "";

    private BigDecimal valor = BigDecimal.ZERO;

    private Integer recorrencia = 0;

    /* Id controle do boleto para poder cancelar pela api */
    private String idChrBoleto = "";

    /* Link da parcela do boleto */
    private String installmentLink = "";

    private String IdPix = "";

    @Column(columnDefinition = "text")
    private String payloadInBase64 = "";

    @Column(columnDefinition = "text")
    private String imageInBase64 = "";

    private String chargeICartao = "";


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCheckoutUrl() {
        return checkoutUrl;
    }

    public void setCheckoutUrl(String checkoutUrl) {
        this.checkoutUrl = checkoutUrl;
    }

    public boolean isQuitado() {
        return quitado;
    }

    public void setQuitado(boolean quitado) {
        this.quitado = quitado;
    }

    public String getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Integer getRecorrencia() {
        return recorrencia;
    }

    public void setRecorrencia(Integer recorrencia) {
        this.recorrencia = recorrencia;
    }

    public String getIdChrBoleto() {
        return idChrBoleto;
    }

    public void setIdChrBoleto(String idChrBoleto) {
        this.idChrBoleto = idChrBoleto;
    }

    public String getInstallmentLink() {
        return installmentLink;
    }

    public void setInstallmentLink(String installmentLink) {
        this.installmentLink = installmentLink;
    }

    public String getIdPix() {
        return IdPix;
    }

    public void setIdPix(String idPix) {
        IdPix = idPix;
    }

    public String getPayloadInBase64() {
        return payloadInBase64;
    }

    public void setPayloadInBase64(String payloadInBase64) {
        this.payloadInBase64 = payloadInBase64;
    }

    public String getImageInBase64() {
        return imageInBase64;
    }

    public void setImageInBase64(String imageInBase64) {
        this.imageInBase64 = imageInBase64;
    }

    public String getChargeICartao() {
        return chargeICartao;
    }

    public void setChargeICartao(String chargeICartao) {
        this.chargeICartao = chargeICartao;
    }

    public VendaCompraLojaVirtual getVendaCompraLojaVirtual() {
        return vendaCompraLojaVirtual;
    }

    public void setVendaCompraLojaVirtual(VendaCompraLojaVirtual vendaCompraLojaVirtual) {
        this.vendaCompraLojaVirtual = vendaCompraLojaVirtual;
    }

    public PessoaJuridica getEmpresa() {
        return empresa;
    }

    public void setEmpresa(PessoaJuridica empresa) {
        this.empresa = empresa;
    }

    @ManyToOne
    @JoinColumn(name = "venda_compra_loja_virtual_id",nullable = false,foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,name = "venda_compra_loja_virtual_fk"))
    private VendaCompraLojaVirtual vendaCompraLojaVirtual;

    @ManyToOne(targetEntity = PessoaJuridica.class)
    @JoinColumn(name = "empresa_id",nullable = false,foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,name = "empresa_fk"))
    private PessoaJuridica empresa;




}
