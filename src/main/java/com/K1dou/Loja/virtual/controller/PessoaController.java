package com.K1dou.Loja.virtual.controller;

import com.K1dou.Loja.virtual.model.Dtos.PessoaCadastroDTO;
import com.K1dou.Loja.virtual.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;


    @PostMapping("/CadastroPessoaFisica")
    public ResponseEntity cadastroPessoa(@RequestBody PessoaCadastroDTO dto){
        pessoaService.cadastroPessoaFisica(dto);
        return ResponseEntity.ok().build();
    }

}
