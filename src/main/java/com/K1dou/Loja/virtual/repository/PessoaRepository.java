package com.K1dou.Loja.virtual.repository;

import com.K1dou.Loja.virtual.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa,Long> {
}
