package com.K1dou.Loja.virtual.repository;

import com.K1dou.Loja.virtual.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    Usuario findByLogin(String login);
}
