package com.K1dou.Loja.virtual.repository;

import com.K1dou.Loja.virtual.model.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {


    @Transactional
    @Modifying
    @Query("delete from Usuario u where u.id =?1")
    void deleteUsuario(Long id);

    @Query(value = "SELECT u.* FROM Usuario u WHERE u.data_atual_senha <= CURRENT_DATE - 90 ", nativeQuery = true)
    List<Usuario> usuarioSenhaVencida();

    Usuario findByLogin(String login);

    //ver se a pessoa ja existe nos usuarios
    @Query(value = "select u from Usuario u where u.pessoa.id = ?1 or u.login = ?2")
    Usuario findByPessoa(Long id, String email);

    @Query(value = "select constraint_name from information_schema.constraint_column_usage where table_name = 'usuario_acesso' and column_name = 'acesso_id' and constraint_name <> 'unique_acesso_user';", nativeQuery = true)
    String consultaConstraintAcesso();

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "insert into usuarios_acessos(usuario_id, acesso_id) values (?1,(select id from acesso where descricao = 'ROLE_USER'))")
    void InsereAcessoUser(Long iduser);


    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "insert into usuarios_acessos(usuario_id, acesso_id) values (?1,(select id from acesso where descricao = ?2 limit 1))")
    void InsereAcessoUser(Long iduser, String acesso);
}
