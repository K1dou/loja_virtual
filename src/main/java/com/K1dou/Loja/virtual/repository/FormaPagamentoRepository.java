package com.K1dou.Loja.virtual.repository;

import com.K1dou.Loja.virtual.model.FormaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento,Long> {


    @Query("select f from FormaPagamento f where f.empresa.id = ?1")
    List<FormaPagamento>formaPagamentoPorEmpresaId(Long empresaId);


}
