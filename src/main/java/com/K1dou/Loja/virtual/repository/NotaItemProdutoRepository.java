package com.K1dou.Loja.virtual.repository;

import com.K1dou.Loja.virtual.model.NotaItemProduto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface NotaItemProdutoRepository extends JpaRepository<NotaItemProduto, Long> {

    @Query("select a from NotaItemProduto a where a.produto.id = ?1 and a.notaFiscalCompra.id = ?2")
    List<NotaItemProduto> buscaNotaItemProdutoNota(Long idProduto, Long idNotaFiscal);


    @Query("select p from NotaItemProduto p where p.produto.id =?1")
    List<NotaItemProduto> buscaNotaItemPorProduto(Long idProduto);

    @Query("select n from NotaItemProduto n where n.notaFiscalCompra.id = ?1")
    List<NotaItemProduto> buscaNotaItemPorNotaFiscal(Long idNotaFiscal);

    @Query("select e from NotaItemProduto e where e.empresa.id =?1")
    List<NotaItemProduto> buscaNotaItemPorEmpresa(Long idEmpresa);


}
