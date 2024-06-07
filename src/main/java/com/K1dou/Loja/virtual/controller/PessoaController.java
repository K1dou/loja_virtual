package com.K1dou.Loja.virtual.controller;

import com.K1dou.Loja.virtual.exceptions.ExceptionLojaVirtual;
import com.K1dou.Loja.virtual.model.PessoaFisica;
import com.K1dou.Loja.virtual.model.PessoaJuridica;
import com.K1dou.Loja.virtual.repository.PessoaFisicaRepository;
import com.K1dou.Loja.virtual.repository.PessoaJuridicaRepository;
import com.K1dou.Loja.virtual.service.PessoaUserService;
import com.K1dou.Loja.virtual.util.ValidaCNPJ;
import com.K1dou.Loja.virtual.util.ValidarCPF;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {


    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    @Autowired
    private PessoaUserService pessoaUserService;

    @PostMapping("/CadastroPessoaJuridica")
    public ResponseEntity<PessoaJuridica> cadastroPessoaJuridica(@RequestBody @Valid PessoaJuridica pessoaJuridica) throws ExceptionLojaVirtual, MessagingException, UnsupportedEncodingException {

        if (pessoaJuridica == null) {
            throw new ExceptionLojaVirtual("Pessoa juridica não pode ser NULL");
        }
        if (pessoaJuridica.getId() == null && pessoaJuridicaRepository.existeCnpjCadastrado(pessoaJuridica.getCnpj()) != null) {
            throw new ExceptionLojaVirtual("Ja existe CNPJ cadastrado com o numero: " + pessoaJuridica.getCnpj());
        }
        if (pessoaJuridica.getId() == null && pessoaJuridicaRepository.existeInsEstadualCadastrado(pessoaJuridica.getInscEstadual()) != null) {
            throw new ExceptionLojaVirtual("Ja existe Inscrição Estadual cadastrado com o numero: " + pessoaJuridica.getInscEstadual());
        }
        if (!ValidaCNPJ.isCNPJ(pessoaJuridica.getCnpj())) {
            throw new ExceptionLojaVirtual("CNPJ: " + pessoaJuridica.getCnpj() + " está invalido.");
        }


        pessoaJuridica = pessoaUserService.salvarPessoaJuridica(pessoaJuridica);


        return new ResponseEntity<PessoaJuridica>(pessoaJuridica, HttpStatus.OK);
    }


    @PostMapping("/CadastroPessoaFisica")
    public ResponseEntity<PessoaFisica> cadastroPessoa(@RequestBody @Valid PessoaFisica pessoaFisica) throws ExceptionLojaVirtual, MessagingException, UnsupportedEncodingException {

        if (pessoaFisica == null) {
            throw new ExceptionLojaVirtual("Pessoa física não pode ser NULL");
        }
        if (pessoaFisica.getId() == null && pessoaFisicaRepository.existeCpfCadastrado(pessoaFisica.getCpf()) != null) {
            throw new ExceptionLojaVirtual("Ja existe CNPJ cadastrado com o numero: " + pessoaFisica.getCpf());
        }

        if (!ValidarCPF.isCPF(pessoaFisica.getCpf())) {
            throw new ExceptionLojaVirtual("CPF : " + pessoaFisica.getCpf() + " está invalido.");
        }

        pessoaFisica = pessoaUserService.salvarPessoaFisica(pessoaFisica);

        return new ResponseEntity<PessoaFisica>(pessoaFisica, HttpStatus.OK);
    }


}
