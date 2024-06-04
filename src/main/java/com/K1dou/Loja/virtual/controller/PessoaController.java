package com.K1dou.Loja.virtual.controller;

import com.K1dou.Loja.virtual.exceptions.ExceptionLojaVirtual;
import com.K1dou.Loja.virtual.model.Dtos.PessoaCadastroDTO;
import com.K1dou.Loja.virtual.model.PessoaJuridica;
import com.K1dou.Loja.virtual.repository.PessoaJuridicaRepository;
import com.K1dou.Loja.virtual.service.PessoaService;
import com.K1dou.Loja.virtual.service.PessoaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    @Autowired
    private PessoaUserService pessoaUserService;

    @PostMapping("/CadastroPessoaJuridica")
    public ResponseEntity<PessoaJuridica> cadastroPessoaJuridica(@RequestBody PessoaJuridica pessoaJuridica) throws ExceptionLojaVirtual {

        if (pessoaJuridica == null) {
            throw new ExceptionLojaVirtual("Pessoa juridica não pode ser NULL");
        }
        if (pessoaJuridica.getId() == null && pessoaJuridicaRepository.existeCnpjCadastrado(pessoaJuridica.getCnpj()) != null) {
            throw new ExceptionLojaVirtual("Ja existe CNPJ cadastrado com o numero: " + pessoaJuridica.getCnpj());
        }

        pessoaJuridica = pessoaUserService.salvarPessoaJuridica(pessoaJuridica);


        return new ResponseEntity<PessoaJuridica>(pessoaJuridica, HttpStatus.OK);
    }


    @PostMapping("/CadastroPessoaFisica")
    public ResponseEntity cadastroPessoa(@RequestBody PessoaCadastroDTO dto) {
        pessoaService.cadastroPessoaFisica(dto);
        return ResponseEntity.ok().build();
    }


}
