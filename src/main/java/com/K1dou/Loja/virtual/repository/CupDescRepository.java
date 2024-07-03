package com.K1dou.Loja.virtual.repository;

import com.K1dou.Loja.virtual.model.CupDesc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CupDescRepository extends JpaRepository<CupDesc,Long> {

    @Query("select c from CupDesc c where c.empresa.id =?1")
    List<CupDesc>cupDescPorEmpresa(Long empresaId);

    @Query("select count(1)>0 from CupDesc c where c.codDesc = ?1 and c.empresa.id = ?2")
    boolean jaExisteCupEmpresa(String codDesc, Long EmpresaId);


}
