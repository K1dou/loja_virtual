package com.K1dou.Loja.virtual.repository;

import com.K1dou.Loja.virtual.model.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {


    Usuario findByLogin(String login);

    @Query(value = "select u from Usuario u where u.pessoa.id = ?1 or u.login = ?2")
    Usuario findByPessoa(Long id, String email);

    @Query(value = "select constraint_name from information_schema.constraint_column_usage where table_name = 'usuario_acesso' and column_name = 'acesso_id' and constraint_name <> 'unique_acesso_user';", nativeQuery = true)
    String consultaConstraintAcesso();

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "insert into usuarios_acessos(usuario_id, acesso_id) values (?1,(select id from acesso where descricao = 'ROLE_USER'))")
    void InsereAcessoUserPj(Long iduser);
}
