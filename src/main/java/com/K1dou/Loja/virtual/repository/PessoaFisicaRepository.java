package com.K1dou.Loja.virtual.repository;

import com.K1dou.Loja.virtual.model.PessoaFisica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica,Long> {


    @Query("select pf from PessoaFisica pf where pf.cpf = ?1")
    PessoaFisica existeCpfCadastrado(String cpf);
}
