package com.K1dou.Loja.virtual.service;

import com.K1dou.Loja.virtual.model.Dtos.CepDTO;
import com.K1dou.Loja.virtual.model.Dtos.ConsultaCnpjDTO;
import com.K1dou.Loja.virtual.model.Endereco;
import com.K1dou.Loja.virtual.model.PessoaFisica;
import com.K1dou.Loja.virtual.model.PessoaJuridica;
import com.K1dou.Loja.virtual.model.Usuario;
import com.K1dou.Loja.virtual.repository.PessoaFisicaRepository;
import com.K1dou.Loja.virtual.repository.PessoaJuridicaRepository;
import com.K1dou.Loja.virtual.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;

@Service
public class PessoaUserService {

    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ServiceSendEmail serviceSendEmail;

    public PessoaJuridica salvarPessoaJuridica(PessoaJuridica pessoaJuridica) throws MessagingException, UnsupportedEncodingException {

        for (int i = 0; i < pessoaJuridica.getEnderecos().size(); i++) {
            pessoaJuridica.getEnderecos().get(i).setPessoa(pessoaJuridica);
            pessoaJuridica.getEnderecos().get(i).setEmpresa(pessoaJuridica);
        }

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

            usuarioRepository.InsereAcessoUser(usuarioPj.getId());
            usuarioRepository.InsereAcessoUser(usuarioPj.getId(), "ROLE_ADMIN");

            StringBuilder menssagemHtml = new StringBuilder();
            menssagemHtml.append("<b>Segue abaixo seus dados de acesso para a loja Virtual</b><br/>");
            menssagemHtml.append("<b>Login: " + pessoaJuridica.getEmail() + "</b><br/>");
            menssagemHtml.append("<b>Senha: ").append(senha).append("<br/><br/>");
            menssagemHtml.append("<b>Obrigado!</b>");


            serviceSendEmail.enviarEmailHtml("Acesso Gerado para Loja Virtual", menssagemHtml.toString(), pessoaJuridica.getEmail());

        }

        return pessoaJuridica;

    }

    public PessoaFisica salvarPessoaFisica(PessoaFisica pessoaFisica) throws MessagingException, UnsupportedEncodingException {

        for (Endereco endereco : pessoaFisica.getEnderecos()) {
            endereco.setPessoa(pessoaFisica);
//            endereco.setEmpresa(pessoaFisica);

        }
        pessoaFisicaRepository.save(pessoaFisica);

        Usuario usuariopf = usuarioRepository.findByPessoa(pessoaFisica.getId(), pessoaFisica.getEmail());
        if (usuariopf == null) {
            String constraint = usuarioRepository.consultaConstraintAcesso();
            if (constraint != null) {
                jdbcTemplate.execute("begin; alter table usuarios_acesso drop constraint " + constraint + "; commit;");
            }
            String senha = "" + Calendar.getInstance().getTimeInMillis();
            var senhaCrip = passwordEncoder.encode(senha);

            usuariopf = new Usuario();
            usuariopf.setEmpresa(pessoaFisica);
            usuariopf.setPessoa(pessoaFisica.getEmpresa());
            usuariopf.setLogin(pessoaFisica.getEmail());
            usuariopf.setSenha(senhaCrip);
            usuariopf.setDataAtualSenha(Calendar.getInstance().getTime());

            usuarioRepository.save(usuariopf);
            usuarioRepository.InsereAcessoUser(usuariopf.getId());

            StringBuilder menssagemHtml = new StringBuilder();
            menssagemHtml.append("<b>Segue abaixo seus dados de acesso para a loja Virtual</b><br/>");
            menssagemHtml.append("<b>Login: " + pessoaFisica.getEmail() + "</b><br/>");
            menssagemHtml.append("<b>Senha: ").append(senha).append("<br/><br/>");
            menssagemHtml.append("<b>Obrigado!</b>");

            serviceSendEmail.enviarEmailHtml("Acesso Gerado para Loja Virtual", menssagemHtml.toString(), pessoaFisica.getEmail());
        }


        return pessoaFisica;
    }

    public CepDTO consultaCep(String cep) {
        return new RestTemplate().getForEntity("https://viacep.com.br/ws/" + cep + "/json/", CepDTO.class).getBody();
    }

    public ConsultaCnpjDTO consultaCnpjReceitaWS(String cnpj){
        return new RestTemplate().getForEntity("https://receitaws.com.br/v1/cnpj/"+cnpj,ConsultaCnpjDTO.class).getBody();
    }

}
