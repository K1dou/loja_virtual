package com.K1dou.Loja.virtual.repository;

import com.K1dou.Loja.virtual.model.ContaPagar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContaPagarRepository extends JpaRepository<ContaPagar,Long> {


    @Query("select c from ContaPagar c where upper(trim(c.descricao)) like upper(trim(concat('%',?1,'%'))) ")
    List<ContaPagar> findAllDesc(String desc);
}
