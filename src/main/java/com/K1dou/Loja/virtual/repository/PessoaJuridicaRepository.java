package com.K1dou.Loja.virtual.repository;

import com.K1dou.Loja.virtual.model.PessoaJuridica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PessoaJuridicaRepository extends JpaRepository<PessoaJuridica,Long> {


    @Query("select pj from PessoaJuridica pj where lower(pj.nome) like lower(concat('%', ?1, '%'))")
    public List<PessoaJuridica> findByNome (String nome);


    @Query("select pj from PessoaJuridica pj where pj.cnpj = ?1")
    public PessoaJuridica existeCnpjCadastrado(String cnpj);

    @Query("SELECT pj FROM PessoaJuridica pj WHERE pj.inscEstadual = ?1")
    public PessoaJuridica existeInsEstadualCadastrado(String inscEstadual);


}
