package com.K1dou.Loja.virtual.service;

import com.K1dou.Loja.virtual.model.PessoaJuridica;
import com.K1dou.Loja.virtual.model.Usuario;
import com.K1dou.Loja.virtual.repository.PessoaJuridicaRepository;
import com.K1dou.Loja.virtual.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class PessoaUserService {

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public PessoaJuridica salvarPessoaJuridica(PessoaJuridica pessoaJuridica) {

        pessoaJuridica = pessoaJuridicaRepository.save(pessoaJuridica);

        Usuario usuarioPj = usuarioRepository.findByPessoa(pessoaJuridica.getId(), pessoaJuridica.getEmail());

        if (usuarioPj == null) {

            String constraint = usuarioRepository.consultaConstraintAcesso();
            if (constraint != null) {
                jdbcTemplate.execute("begin; alter table usuarios_acesso drop constraint " + constraint + "; commit;");
            }
            usuarioPj = new Usuario();
            usuarioPj.setDataAtualSenha(Calendar.getInstance().getTime());
            usuarioPj.setEmpresa(pessoaJuridica);
            usuarioPj.setPessoa(pessoaJuridica);
            usuarioPj.setLogin(pessoaJuridica.getEmail());

            String senha = "" + Calendar.getInstance().getTimeInMillis();
            String senhaCrip = passwordEncoder.encode(senha);
            usuarioPj.setSenha(senhaCrip);
            usuarioRepository.save(usuarioPj);

            usuarioRepository.InsereAcessoUserPj(usuarioPj.getId());

        }

        return pessoaJuridica;

    }

}
