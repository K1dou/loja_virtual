package com.K1dou.Loja.virtual;

import com.K1dou.Loja.virtual.controller.AcessoController;
import com.K1dou.Loja.virtual.model.Acesso;
import com.K1dou.Loja.virtual.repository.AcessoRepository;
import com.K1dou.Loja.virtual.service.AcessoService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;


@SpringBootTest
@Configuration
class LojaVirtualApplicationTests extends TestCase {


    @Autowired
    private AcessoService acessoService;

    @Autowired
    private AcessoRepository acessoRepository;

    @Autowired
    private AcessoController acessoController;

    @Autowired
    private WebApplicationContext wac;


    @Test
    void contextLoads() {
        // Teste básico para verificar se o contexto carrega corretamente
    }


    @Test
    void testRestApiCadastroAcesso() throws Exception {

        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        MockMvc mockMvc = builder.build();

        Acesso acesso = new Acesso();
        acesso.setDescricao("ROLE_COMPRADOR");

        ObjectMapper objectMapper = new ObjectMapper();

        ResultActions retornoApi = mockMvc
                .perform(MockMvcRequestBuilders.post("/acesso/salvarAcesso")
                        .content(objectMapper.writeValueAsString(acesso))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));

        //conveter o retorno da API para um objeto de acesso
        Acesso objetoRetorno = objectMapper.readValue(retornoApi.andReturn().getResponse().getContentAsString(), Acesso.class);

        assertEquals(acesso.getDescricao(), objetoRetorno.getDescricao());


    }


    @Test
    void testRestApiDeletePorIDAcesso() throws Exception {

        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        MockMvc mockMvc = builder.build();

        Acesso acesso = new Acesso();
        acesso.setDescricao("ROLE_COMPRADOR");

        acessoRepository.save(acesso);

        ObjectMapper objectMapper = new ObjectMapper();

        ResultActions retornoApi = mockMvc
                .perform(MockMvcRequestBuilders.delete("/acesso/deleteAcessoPorId/" + acesso.getId())
                        .content(objectMapper.writeValueAsString(acesso))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));

        assertEquals("Acesso removido", retornoApi.andReturn().getResponse().getContentAsString());
        assertEquals(200, retornoApi.andReturn().getResponse().getStatus());

        acessoRepository.deleteById(acesso.getId());

    }


    @Test
    void testRestApiObterAcessoID() throws Exception {

        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        MockMvc mockMvc = builder.build();

        Acesso acesso = new Acesso();
        acesso.setDescricao("ROLE_COMPRADOR");

        acessoRepository.save(acesso);

        ObjectMapper objectMapper = new ObjectMapper();

        ResultActions retornoApi = mockMvc
                .perform(MockMvcRequestBuilders.get("/acesso/obterAcesso/" + acesso.getId())
                        .content(objectMapper.writeValueAsString(acesso))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));

        Acesso acesso1 = objectMapper.readValue(retornoApi.andReturn().getResponse().getContentAsString(), Acesso.class);


        assertEquals(acesso.getDescricao(), acesso1.getDescricao());
        assertEquals(200, retornoApi.andReturn().getResponse().getStatus());

        acessoRepository.deleteById(acesso.getId());

    }


    @Test
    void testRestApiObterAcessoDesc() throws Exception {

        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        MockMvc mockMvc = builder.build();

        Acesso acesso = new Acesso();
        acesso.setDescricao("ROLE_COMPRADOR");
        acessoRepository.save(acesso);

        ObjectMapper objectMapper = new ObjectMapper();

        ResultActions retornoApi = mockMvc
                .perform(MockMvcRequestBuilders.get("/acesso/buscarPorDesc/COMPRADOR")
                        .content(objectMapper.writeValueAsString(acesso))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));


        assertEquals(200, retornoApi.andReturn().getResponse().getStatus());

        List<Acesso> retornoApiList = objectMapper.readValue(retornoApi.andReturn().getResponse().getContentAsString(), new TypeReference<List<Acesso>>() {
        });


        assertEquals(2,retornoApiList.size());
        assertEquals(acesso.getDescricao(), retornoApiList.get(0).getDescricao());

        acessoRepository.deleteById(acesso.getId());

    }


//    @Test
//    void testCadastraAcesso() {
//
//        Acesso acesso = new Acesso();
//
//        acesso.setDescricao("ROLE_ADMIN");
//
//        assertEquals(true, acesso.getId() == null);
//
//        /*Gravou no banco de dados*/
//        acesso = acessoController.salvarAcesso(acesso).getBody();
//
//        assertEquals(true, acesso.getId() > 0);
//
//
//        /*Validar dados salvos da forma correta*/
//        assertEquals("ROLE_ADMIN", acesso.getDescricao());
//
//        /*Teste de carregamento*/
//
//        Acesso acesso2 = acessoRepository.findById(acesso.getId()).get();
//
//        assertEquals(acesso.getId(), acesso2.getId());
//
//
//        /*Teste de delete*/
//
//        acessoRepository.deleteById(acesso2.getId());
//
//        acessoRepository.flush(); /*Roda esse SQL de delete no banco de dados*/
//
//        Acesso acesso3 = acessoRepository.findById(acesso2.getId()).orElse(null);
//
//        assertEquals(true, acesso3 == null);
//
//
//        /*Teste de query*/
//
//        acesso = new Acesso();
//
//        acesso.setDescricao("ROLE_ALUNO");
//
//        acesso = acessoController.salvarAcesso(acesso).getBody();
//
//        List<Acesso> acessos = acessoRepository.buscarAcessoDesc("ALUNO");
//
//        assertEquals(1, acessos.size());
//
//        acessoRepository.deleteById(acesso.getId());
//
//    }


}

