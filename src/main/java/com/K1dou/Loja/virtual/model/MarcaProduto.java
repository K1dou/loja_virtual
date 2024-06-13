package com.K1dou.Loja.virtual.model;

import jakarta.persistence.*;

@Entity
@Table(name = "marca_produto")
@SequenceGenerator(name = "seq_marca_produto",sequenceName ="seq_marca_produto",allocationSize = 1,initialValue = 1)
public class MarcaProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_marca_produto")
    private Long id;
    @Column(nullable = false)
    private String nomeDesc;

    @ManyToOne(targetEntity = Pessoa.class)
    @JoinColumn(name = "empresa_id",nullable = false,foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,name = "pessoa_fk"))
    private PessoaJuridica empresa;

    public Pessoa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(PessoaJuridica empresa) {
        this.empresa = empresa;
    }

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
}
