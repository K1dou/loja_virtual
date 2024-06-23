package com.K1dou.Loja.virtual.model.Dtos;

import com.K1dou.Loja.virtual.model.PessoaJuridica;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class FormaPagamentoDTO {


    private Long id;
    @NotEmpty(message = "Descrição necessária")
    @NotNull(message = "Descrição necessária")
    private String descricao;

    @NotNull(message = "Descrição necessária")
    private PessoaJuridica empresa;

    public FormaPagamentoDTO() {
    }

    public FormaPagamentoDTO(Long id, String descricao, PessoaJuridica empresa) {
        this.id = id;
        this.descricao = descricao;
        this.empresa = empresa;
    }

    public PessoaJuridica getEmpresa() {
        return empresa;
    }

    public void setEmpresa(PessoaJuridica empresa) {
        this.empresa = empresa;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
