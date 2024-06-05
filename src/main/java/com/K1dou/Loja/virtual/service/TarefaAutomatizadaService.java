package com.K1dou.Loja.virtual.service;

import com.K1dou.Loja.virtual.model.Usuario;
import com.K1dou.Loja.virtual.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class TarefaAutomatizadaService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ServiceSendEmail serviceSendEmail;


    @Scheduled(cron = "0 0 11 * * *", zone = "America/Sao_Paulo")
    public void notificarUserTrocarSenha() throws MessagingException, UnsupportedEncodingException, InterruptedException {

        List<Usuario> usuarios = usuarioRepository.usuarioSenhaVencida();

        for (Usuario u : usuarios) {
            StringBuilder msg = new StringBuilder();
            msg.append("Olá, ").append(u.getPessoa().getNome()).append("<br/>");
            msg.append("Está na hora de trocar sua senha, já passou 90 dias de validade.").append("<br/>");
            msg.append("Troque sua senha a loja virtual do Marcelo");

            serviceSendEmail.enviarEmailHtml("Troca de senha", msg.toString(), u.getLogin());

            Thread.sleep(3000);
        }

    }


}
