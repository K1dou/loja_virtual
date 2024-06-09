package com.K1dou.Loja.virtual.repository;

import com.K1dou.Loja.virtual.model.CategoriaProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoriaProdutoRepository extends JpaRepository<CategoriaProduto,Long> {


    @Query(nativeQuery = true,value = "select count(1)>0 from categoria_produto where upper(trim(nome_desc)) = ?1;")
    public boolean existeCategoria(String categoria);
}
