package com.K1dou.Loja.virtual.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "forma_pagamento")
@SequenceGenerator(name = "seq_forma_pagamento",sequenceName = "seq_forma_pagamento",initialValue = 1,allocationSize = 1)
public class FormaPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_forma_pagamento")
    private Long id;

    @Column(nullable = false)
    private String descricao;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FormaPagamento that = (FormaPagamento) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
