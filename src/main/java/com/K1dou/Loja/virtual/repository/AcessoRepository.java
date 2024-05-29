package com.K1dou.Loja.virtual.repository;

import com.K1dou.Loja.virtual.model.Acesso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface AcessoRepository extends JpaRepository<Acesso,Long> {


    @Query("select a from Acesso a where upper(a.descricao) like %?1%")
    List<Acesso> buscarAcessoDesc(String descricao);

}
