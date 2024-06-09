package com.K1dou.Loja.virtual.repository;

import com.K1dou.Loja.virtual.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto,Long> {


    @Query(nativeQuery = true,value = "select count(1) > 0 from produto where upper(trim(nome)) = upper(trim(?1))")
    public boolean existeProduto(String nome);

    @Query(nativeQuery = true,value = "select count(1) > 0 from produto where upper(trim(nome)) = upper(trim(?1)) and empresa_id = ?2")
    public boolean existeProduto(String nome,Long idEmpresa);


    @Query("select p from Produto p where upper(trim(p.nome)) like upper(trim(concat('%',?1,'%')))")
    public List<Produto> findByNome(String nome);
    @Query("select p from Produto p where upper(trim(p.nome)) like upper(trim(concat('%',?1,'%'))) and empresa_id = ?2")
    public List<Produto> findByNome(String nome,Long idEmpresa);




}
