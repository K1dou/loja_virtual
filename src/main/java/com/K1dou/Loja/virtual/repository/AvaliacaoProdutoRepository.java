package com.K1dou.Loja.virtual.repository;

import com.K1dou.Loja.virtual.model.AvaliacaoProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AvaliacaoProdutoRepository extends JpaRepository<AvaliacaoProduto,Long> {

    @Query("select a from AvaliacaoProduto a where a.produto.id = ?1")
    List<AvaliacaoProduto>buscaAvaliacaoProdutoByIdProduto(Long idProduto);

    @Query("select a from AvaliacaoProduto a where a.pessoa.id = ?1")
    List<AvaliacaoProduto>buscaAvaliacaoProdutoByIdPessoa(Long idPessoa);

    @Query("select a from AvaliacaoProduto a where a.pessoa.id = ?1 and a.produto.id = ?2")
    List<AvaliacaoProduto>buscaAvaliacaoProdutoByIdPessoaAndIdProduto(Long idPessoa,Long idProduto);


}
