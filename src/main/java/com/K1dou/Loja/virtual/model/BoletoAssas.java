package com.K1dou.Loja.virtual.model;

import jakarta.persistence.*;

@Entity
@SequenceGenerator(name = "seq_boleto_assas",sequenceName ="seq_boleto_assas",allocationSize = 1,initialValue = 1)
public class BoletoAssas {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_boleto_assas")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "venda_compra_loja_virtual_id",nullable = false,foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,name = "venda_compra_loja_virtual_fk"))
    private VendaCompraLojaVirtual vendaCompraLojaVirtual;

    @ManyToOne(targetEntity = PessoaJuridica.class)
    @JoinColumn(name = "empresa_id",nullable = false,foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,name = "empresa_fk"))
    private PessoaJuridica empresa;




}
