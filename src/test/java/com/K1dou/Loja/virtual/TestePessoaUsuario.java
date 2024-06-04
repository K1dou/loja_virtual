package com.K1dou.Loja.virtual;

import com.K1dou.Loja.virtual.controller.PessoaController;
import com.K1dou.Loja.virtual.exceptions.ExceptionLojaVirtual;
import com.K1dou.Loja.virtual.model.PessoaJuridica;
import com.K1dou.Loja.virtual.repository.PessoaJuridicaRepository;
import com.K1dou.Loja.virtual.service.PessoaJuridicaService;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;

@SpringBootTest()
public class TestePessoaUsuario extends TestCase{

    @Autowired
    private PessoaJuridicaService pessoaUserService;

    @Autowired
    private PessoaJuridicaRepository pesssoaJuridicaRepository;

    @Autowired
    private PessoaController pessoaController;




    @Test
   public void testCadPessoaJuridica() throws ExceptionLojaVirtual {

        PessoaJuridica pessoaJuridica = new PessoaJuridica();
        pessoaJuridica.setCnpj(""+ Calendar.getInstance().getTimeInMillis());
        pessoaJuridica.setNome("Alex fernando");
        pessoaJuridica.setEmail("alex.fernando.egidio@gmail.com");
        pessoaJuridica.setTelefone("45999795800");
        pessoaJuridica.setInscEstadual("65556565656665");
        pessoaJuridica.setInscMunicipal("55554565656565");
        pessoaJuridica.setNomeFantasia("54556565665");
        pessoaJuridica.setRazaoSocial("4656656566");

        pessoaController.cadastroPessoaJuridica(pessoaJuridica);

		/*
		PessoaFisica pessoaFisica = new PessoaFisica();

		pessoaFisica.setCpf("0597975788");
		pessoaFisica.setNome("Alex fernando");
		pessoaFisica.setEmail("alex.fernando.egidio@gmail.com");
		pessoaFisica.setTelefone("45999795800");
		pessoaFisica.setEmpresa(pessoaFisica);*/

    }



}
