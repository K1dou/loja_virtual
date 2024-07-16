package com.K1dou.Loja.virtual;

import com.K1dou.Loja.virtual.exceptions.ExceptionLojaVirtual;
import com.K1dou.Loja.virtual.model.Dtos.ObjetoPostCarneAssas;
import com.K1dou.Loja.virtual.service.BoletoAssasService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
 class TesteApiAssas {

    @Autowired
    private BoletoAssasService boletoAssasService;

    @Test
     void testBuscaClintePessoaApiAssas() throws IOException, ExceptionLojaVirtual {

        ObjetoPostCarneAssas dados = new ObjetoPostCarneAssas();
        dados.setEmail("hique1276@gmail.com");
        dados.setPayerName("Marcelo henrique");
        dados.setPayerCpfCnpj("25410399056");
        dados.setPayerPhone("545845484");

        boletoAssasService.buscaClientePessoaApiAssas(dados);


    }

}
