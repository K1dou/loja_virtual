package com.K1dou.Loja.virtual.model.Dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

public class ObjetoRequisicaoCobrancaDTO {

    @NotNull
    private String customer;
    @NotNull
    private String billingType;
    @NotNull
    private float value;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String dueDate;
    private String description;
    private String externalReference;
    private float installmentValue;
    private Integer installmentCount;

    private DiscontCobrancaAssasDTO discount= new DiscontCobrancaAssasDTO();
    private FineCobrancaAssasDTO fine = new FineCobrancaAssasDTO();
    private InterestCobrancaAssasDTO interest = new InterestCobrancaAssasDTO();

    private boolean postalService = false;

    public float getInstallmentValue() {
        return installmentValue;
    }

    public Integer getInstallmentCount() {
        return installmentCount;
    }

    public void setInstallmentCount(Integer installmentCount) {
        this.installmentCount = installmentCount;
    }

    public void setInstallmentValue(float installmentValue) {
        this.installmentValue = installmentValue;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getBillingType() {
        return billingType;
    }

    public void setBillingType(String billingType) {
        this.billingType = billingType;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }

    public DiscontCobrancaAssasDTO getDiscount() {
        return discount;
    }

    public void setDiscount(DiscontCobrancaAssasDTO discount) {
        this.discount = discount;
    }

    public FineCobrancaAssasDTO getFine() {
        return fine;
    }

    public void setFine(FineCobrancaAssasDTO fine) {
        this.fine = fine;
    }

    public InterestCobrancaAssasDTO getInterest() {
        return interest;
    }

    public void setInterest(InterestCobrancaAssasDTO interest) {
        this.interest = interest;
    }

    public boolean isPostalService() {
        return postalService;
    }

    public void setPostalService(boolean postalService) {
        this.postalService = postalService;
    }

    //
//    /*Dias após o vencimento para cancelamento do registro (somente para boleto bancário)*/
//    private Integer daysAfterDueDateToRegistrationCancellation;
//
//    /*Número de parcelas (somente no caso de cobrança parcelada)*/
//    private Integer installmentCount;
//
//    /*Informe o valor total de uma cobrança que será parcelada (somente no caso de cobrança parcelada). Caso enviado este campo o installmentValue não é necessário, o cálculo por parcela será automático.*/
//    private Float totalValue;
//    /* Valor de cada parcela (somente no caso de cobrança parcelada). Envie este campo em caso de querer definir o valor de cada parcela.*/
//    private Float installmentValue;



}
