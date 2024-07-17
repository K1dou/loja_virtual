package com.K1dou.Loja.virtual.model.Dtos;

public class ObjetoQrCodePixAssas {

    private String encodedImage;
    private String PayLoad;
    private String expirationDate;
    private String success;

    public String getEncodedImage() {
        return encodedImage;
    }

    public void setEncodedImage(String encodedImage) {
        this.encodedImage = encodedImage;
    }

    public String getPayLoad() {
        return PayLoad;
    }

    public void setPayLoad(String payLoad) {
        PayLoad = payLoad;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
