package com.K1dou.Loja.virtual.model.Dtos.transporteDTO;

import java.util.ArrayList;
import java.util.List;

public class ConsultaFreteDTO {

    private FromDTO from;
    private ToDTO to;
    private List<ProductsDTO> products = new ArrayList<ProductsDTO>();

    public FromDTO getFrom() {
        return from;
    }

    public void setFrom(FromDTO from) {
        this.from = from;
    }

    public ToDTO getTo() {
        return to;
    }

    public void setTo(ToDTO to) {
        this.to = to;
    }

    public List<ProductsDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsDTO> products) {
        this.products = products;
    }
}
