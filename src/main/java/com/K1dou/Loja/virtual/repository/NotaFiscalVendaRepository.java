package com.K1dou.Loja.virtual.repository;

import com.K1dou.Loja.virtual.model.NotaFiscalVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotaFiscalVendaRepository extends JpaRepository<NotaFiscalVenda,Long> {

    @Query("select n from NotaFiscalVenda n where n.vendaCompraLojaVirtual.id = ?1")
    public List<NotaFiscalVenda> consultaPorVenda(Long idVenda);

}
