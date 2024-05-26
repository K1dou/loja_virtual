package com.K1dou.Loja.virtual.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@SequenceGenerator(name = "seq_categoria_produto",sequenceName = "seq_categoria_produto",initialValue = 1,allocationSize = 1)
public class CategoriaProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_categoria_produto")
    private Long id;
    private String nomeDesc;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeDesc() {
        return nomeDesc;
    }

    public void setNomeDesc(String nomeDesc) {
        this.nomeDesc = nomeDesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoriaProduto that = (CategoriaProduto) o;
        return Objects.equals(id, that.id) && Objects.equals(nomeDesc, that.nomeDesc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nomeDesc);
    }
}
