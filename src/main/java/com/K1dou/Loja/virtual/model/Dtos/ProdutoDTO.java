package com.K1dou.Loja.virtual.model.Dtos;

import com.K1dou.Loja.virtual.model.CategoriaProduto;
import com.K1dou.Loja.virtual.model.MarcaProduto;
import com.K1dou.Loja.virtual.model.PessoaJuridica;

import java.math.BigDecimal;

public record ProdutoDTO(Long id,
                         String tipoUnidade,
                         String nome,
                         Boolean ativo,
                         String descricao,
                         Double peso,
                         Double largura,
                         Double altura,
                         CategoriaProduto categoriaProduto,
                         MarcaProduto marcaProduto,
                         PessoaJuridica empresa,
                         Double profundidade,
                         BigDecimal valorVenda,
                         Integer qtdEstoque,
                         Integer qtdeAlertaEstoque,
                         String linkYoutube,
                         Boolean alertaQtdEstoque,
                         Integer qtdClique) {
}
