package com.K1dou.Loja.virtual.repository;

import com.K1dou.Loja.virtual.model.PessoaFisica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, Long> {

    @Query("select pf from PessoaFisica pf where lower(pf.nome) like lower(concat('%', ?1, '%'))")
     List<PessoaFisica> findBynomes(String nome);

    @Query("select pf from PessoaFisica pf where pf.cpf = ?1")
    PessoaFisica pesquisaPorCpf(String cpf);
}
