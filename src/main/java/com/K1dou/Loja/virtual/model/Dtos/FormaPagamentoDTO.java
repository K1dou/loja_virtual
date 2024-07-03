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
    private Long empresaId;

    public FormaPagamentoDTO() {
    }

    public FormaPagamentoDTO(Long id, String descricao, Long empresaId) {
        this.id = id;
        this.descricao = descricao;
        this.empresaId = empresaId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }
}
