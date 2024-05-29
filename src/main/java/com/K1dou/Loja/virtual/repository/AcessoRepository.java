package com.K1dou.Loja.virtual.repository;

import com.K1dou.Loja.virtual.model.Acesso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AcessoRepository extends JpaRepository<Acesso,Long> {





}
