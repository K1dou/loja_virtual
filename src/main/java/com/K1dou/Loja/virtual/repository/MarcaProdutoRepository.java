package com.K1dou.Loja.virtual.repository;

import com.K1dou.Loja.virtual.model.MarcaProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MarcaProdutoRepository extends JpaRepository<MarcaProduto,Long> {


    @Query("select p from MarcaProduto p where upper(trim(p.nomeDesc)) like upper(trim(concat('%',?1,'%')))")
    List<MarcaProduto> findAllDesc(String descricao);
}
