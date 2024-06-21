package com.K1dou.Loja.virtual.repository;

import com.K1dou.Loja.virtual.model.ImagemProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ImagemProdutoRepository extends JpaRepository<ImagemProduto,Long> {


    @Query("select i from ImagemProduto i where i.produto.id = ?1")
    public List<ImagemProduto>findImagensByProduto(Long idProduto);

    @Transactional
    @Modifying
    @Query("delete from ImagemProduto i where i.produto.id = ?1")
    public void deleteImagemProdutoByIdProduto(Long idProduto);





}
