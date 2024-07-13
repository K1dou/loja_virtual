package com.K1dou.Loja.virtual.model.Dtos.transporteDTO;

import java.util.List;

public class EnvioEtiquetaDTO {

    private String service;
    private String agency;

    private FromEnvioEtiquetaDTO from;
    private ToEnvioEtiquetaDTO to;

    private List<ProductsEnvioEtiquetaDTO>products;
    private List<VolumeEnvioEtiquetaDTO> volumes;

    private OptionsEnvioDTO options;

    public String getService() {
        return service;
    }



    public void setService(String service) {
        this.service = service;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public FromEnvioEtiquetaDTO getFrom() {
        return from;
    }

    public void setFrom(FromEnvioEtiquetaDTO from) {
        this.from = from;
    }

    public ToEnvioEtiquetaDTO getTo() {
        return to;
    }

    public void setTo(ToEnvioEtiquetaDTO to) {
        this.to = to;
    }

    public List<ProductsEnvioEtiquetaDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsEnvioEtiquetaDTO> products) {
        this.products = products;
    }

    public List<VolumeEnvioEtiquetaDTO> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<VolumeEnvioEtiquetaDTO> volumes) {
        this.volumes = volumes;
    }

    public OptionsEnvioDTO getOptions() {
        return options;
    }

    public void setOptions(OptionsEnvioDTO options) {
        this.options = options;
    }
}