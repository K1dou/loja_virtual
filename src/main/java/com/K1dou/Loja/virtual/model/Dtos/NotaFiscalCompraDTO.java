package com.K1dou.Loja.virtual.model.Dtos;

import com.K1dou.Loja.virtual.model.ContaPagar;
import com.K1dou.Loja.virtual.model.PessoaJuridica;

import java.math.BigDecimal;
import java.util.Date;

public record NotaFiscalCompraDTO(Long id,
                                  String numeroNota,
                                  String serieNota,
                                  String descricaoObs,
                                  BigDecimal valorTotal,
                                  BigDecimal valorDesconto,
                                  BigDecimal valorIcms,
                                  Date dataCompra,
                                  PessoaJuridica pessoa, // Usando IDs para evitar dependências de entidades JPA
                                  ContaPagar contaPagar,
                                  PessoaJuridica empresa) {
}

