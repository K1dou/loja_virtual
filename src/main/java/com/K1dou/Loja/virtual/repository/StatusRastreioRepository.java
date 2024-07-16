package com.K1dou.Loja.virtual.repository;

import com.K1dou.Loja.virtual.model.StatusRastreio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StatusRastreioRepository extends JpaRepository<StatusRastreio,Long> {



    @Query("select s from StatusRastreio s where s.vendaCompraLojaVirtual.id=?1 order by s.id")
    List<StatusRastreio>listaRastreioVenda(Long idVenda);

    @Modifying(flushAutomatically = true)
    @Query(nativeQuery = true,value = "update status_rastreio set url_rastreio =?1 where empresa_id= ?1")
    void salvaUrlRastreio(String url,Long idEmpresa);
}
