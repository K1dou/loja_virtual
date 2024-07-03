package com.K1dou.Loja.virtual.repository;

import com.K1dou.Loja.virtual.model.StatusRastreio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StatusRastreioRepository extends JpaRepository<StatusRastreio,Long> {



    @Query("select s from StatusRastreio s where s.vendaCompraLojaVirtual.id=?1 order by s.id")
    List<StatusRastreio>listaRastreioVenda(Long idVenda);

}
