package com.K1dou.Loja.virtual;

import com.K1dou.Loja.virtual.controller.PessoaController;
import com.K1dou.Loja.virtual.enums.TipoEndereco;
import com.K1dou.Loja.virtual.exceptions.ExceptionLojaVirtual;
import com.K1dou.Loja.virtual.model.Endereco;
import com.K1dou.Loja.virtual.model.PessoaFisica;
import com.K1dou.Loja.virtual.model.PessoaJuridica;
import com.K1dou.Loja.virtual.repository.PessoaJuridicaRepository;
import com.K1dou.Loja.virtual.service.PessoaJuridicaService;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;

@SpringBootTest
public class TestePessoaUsuario extends TestCase {

    @Autowired
    private PessoaJuridicaService pessoaUserService;

    @Autowired
    private PessoaJuridicaRepository pesssoaJuridicaRepository;

    @Autowired
    private PessoaController pessoaController;

    @Test
    public void testCadPessoaJuridica() throws ExceptionLojaVirtual, MessagingException, UnsupportedEncodingException {

        PessoaJuridica pessoaJuridica = new PessoaJuridica();
        pessoaJuridica.setCnpj("07.653.542/0001-02");
        pessoaJuridica.setNome("Alex fernando");
        pessoaJuridica.setEmail("hique1276@gmail.com");
        pessoaJuridica.setTelefone("45999795800");
        pessoaJuridica.setInscEstadual(""+Calendar.getInstance().getTimeInMillis());
        pessoaJuridica.setInscMunicipal("55554565656565");
        pessoaJuridica.setNomeFantasia("54556565665");
        pessoaJuridica.setRazaoSocial("4656656566");

        Endereco endereco1 = new Endereco();
        endereco1.setBairro("Jd Dias");
        endereco1.setCep("556556565");
        endereco1.setComplemento("Casa cinza");
        endereco1.setEmpresa(pessoaJuridica);
        endereco1.setNumero("389");
        endereco1.setPessoa(pessoaJuridica);
        endereco1.setRuaLogra("Av. são joao sexto");
        endereco1.setTipoEndereco(TipoEndereco.COBRANCA);
        endereco1.setUf("PR");
        endereco1.setCidade("Curitiba");


        Endereco endereco2 = new Endereco();
        endereco2.setBairro("Jd Maracana");
        endereco2.setCep("7878778");
        endereco2.setComplemento("Andar 4");
        endereco2.setEmpresa(pessoaJuridica);
        endereco2.setNumero("555");
        endereco2.setPessoa(pessoaJuridica);
        endereco2.setRuaLogra("Av. maringá");
        endereco2.setTipoEndereco(TipoEndereco.ENTREGA);
        endereco2.setUf("PR");
        endereco2.setCidade("Curitiba");

        pessoaJuridica.getEnderecos().add(endereco2);
        pessoaJuridica.getEnderecos().add(endereco1);

        pessoaJuridica = pessoaController.cadastroPessoaJuridica(pessoaJuridica).getBody();

        assertEquals(true, pessoaJuridica.getId() > 0);

        for (Endereco endereco : pessoaJuridica.getEnderecos()) {
            assertEquals(true, endereco.getId() > 0);
        }

        assertEquals(2, pessoaJuridica.getEnderecos().size());

    }


    @Test
    public void testCadPessoaFisica() throws ExceptionLojaVirtual, MessagingException, UnsupportedEncodingException {

        PessoaJuridica pessoaJuridica = pesssoaJuridicaRepository.existeCnpjCadastrado("45458ddsssdsdsf88g488");

        PessoaFisica pessoaFisica = new PessoaFisica();
        pessoaFisica.setCpf("393.753.730-96");
        pessoaFisica.setNome("Alex fernando");
        pessoaFisica.setEmail("hique1276@gmail.com");
        pessoaFisica.setTelefone("45999795800");
        pessoaFisica.setEmpresa(pessoaJuridica);
        pessoaFisica.setDataNascimento(new Date());

        Endereco endereco1 = new Endereco();
        endereco1.setBairro("Jd Dias");
        endereco1.setCep("556556565");
        endereco1.setComplemento("Casa cinza");
        endereco1.setNumero("389");
        endereco1.setPessoa(pessoaFisica);
        endereco1.setRuaLogra("Av. são joao sexto");
        endereco1.setTipoEndereco(TipoEndereco.COBRANCA);
        endereco1.setUf("PR");
        endereco1.setCidade("Curitiba");
        endereco1.setEmpresa(pessoaJuridica.getEmpresa());


        Endereco endereco2 = new Endereco();
        endereco2.setBairro("Jd Maracana");
        endereco2.setCep("7878778");
        endereco2.setComplemento("Andar 4");
        endereco2.setNumero("555");
        endereco2.setPessoa(pessoaFisica);
        endereco2.setRuaLogra("Av. maringá");
        endereco2.setTipoEndereco(TipoEndereco.ENTREGA);
        endereco2.setUf("PR");
        endereco2.setCidade("Curitiba");
        endereco2.setEmpresa(pessoaJuridica);

        pessoaFisica.getEnderecos().add(endereco2);
        pessoaFisica.getEnderecos().add(endereco1);

        pessoaFisica = pessoaController.cadastroPessoa(pessoaFisica).getBody();

        assertEquals(true, pessoaFisica.getId() > 0);

        for (Endereco endereco : pessoaFisica.getEnderecos()) {
            assertEquals(true, endereco.getId() > 0);
        }

        assertEquals(2, pessoaFisica.getEnderecos().size());

    }


}
