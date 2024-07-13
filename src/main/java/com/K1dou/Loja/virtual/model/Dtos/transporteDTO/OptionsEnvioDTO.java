package com.K1dou.Loja.virtual.model.Dtos.transporteDTO;

import java.util.List;

public class OptionsEnvioDTO {

    private String insurance_value;

    private Boolean receipt;
    private Boolean own_hand;
    private Boolean reverse;
    private Boolean non_commercial;

    private InvoiceEnvioDTO invoice;

    private String platform;

    private List<TagsEnvioDTO> tags;


    public String getInsurance_value() {
        return insurance_value;
    }

    public void setInsurance_value(String insurance_value) {
        this.insurance_value = insurance_value;
    }

    public Boolean getReceipt() {
        return receipt;
    }

    public void setReceipt(Boolean receipt) {
        this.receipt = receipt;
    }

    public Boolean getOwn_hand() {
        return own_hand;
    }

    public void setOwn_hand(Boolean own_hand) {
        this.own_hand = own_hand;
    }

    public Boolean getReverse() {
        return reverse;
    }

    public void setReverse(Boolean reverse) {
        this.reverse = reverse;
    }

    public Boolean getNon_commercial() {
        return non_commercial;
    }

    public void setNon_commercial(Boolean non_commercial) {
        this.non_commercial = non_commercial;
    }

    public InvoiceEnvioDTO getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceEnvioDTO invoice) {
        this.invoice = invoice;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public List<TagsEnvioDTO> getTags() {
        return tags;
    }

    public void setTags(List<TagsEnvioDTO> tags) {
        this.tags = tags;
    }
}
