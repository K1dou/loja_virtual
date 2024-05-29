package com.K1dou.Loja.virtual;

import com.K1dou.Loja.virtual.controller.AcessoController;
import com.K1dou.Loja.virtual.model.Acesso;
import com.K1dou.Loja.virtual.repository.AcessoRepository;
import com.K1dou.Loja.virtual.service.AcessoService;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@SpringBootTest(classes = LojaVirtualApplication.class)
@Configuration
class LojaVirtualApplicationTests extends TestCase {


    @Autowired
    private AcessoService acessoService;

    @Autowired
    private AcessoRepository acessoRepository;

    @Autowired
    private AcessoController acessoController;


    @Test
    public void testCadastraAcesso() {

        Acesso acesso = new Acesso();

        acesso.setDescricao("ROLE_ADMIN");

        assertEquals(true, acesso.getId() == null);

        /*Gravou no banco de dados*/
        acesso = acessoController.salvarAcesso(acesso).getBody();

        assertEquals(true, acesso.getId() > 0);

        /*Validar dados salvos da forma correta*/
        assertEquals("ROLE_ADMIN", acesso.getDescricao());

        /*Teste de carregamento*/

        Acesso acesso2 = acessoRepository.findById(acesso.getId()).get();

        assertEquals(acesso.getId(), acesso2.getId());


        /*Teste de delete*/

        acessoRepository.deleteById(acesso2.getId());

        acessoRepository.flush(); /*Roda esse SQL de delete no banco de dados*/

        Acesso acesso3 = acessoRepository.findById(acesso2.getId()).orElse(null);

        assertEquals(true, acesso3 == null);


        /*Teste de query*/

        acesso = new Acesso();

        acesso.setDescricao("ROLE_ALUNO");

        acesso = acessoController.salvarAcesso(acesso).getBody();

        List<Acesso> acessos = acessoRepository.buscarAcessoDesc("ALUNO");

        assertEquals(1, acessos.size());

        acessoRepository.deleteById(acesso.getId());







    }





}
