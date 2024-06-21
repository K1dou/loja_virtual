package com.K1dou.Loja.virtual.controller;

import com.K1dou.Loja.virtual.exceptions.ExceptionLojaVirtual;
import com.K1dou.Loja.virtual.model.Dtos.AvaliacaoProdutoDTO;
import com.K1dou.Loja.virtual.service.AvaliacaoProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/avaliacaoProduto")
public class AvaliacaoProdutoController {

    @Autowired
    private AvaliacaoProdutoService avaliacaoProdutoService;

    @PostMapping("/salvaAvaliacaoProduto")
    public ResponseEntity<AvaliacaoProdutoDTO> salvaAvaliacaoProduto(@RequestBody @Valid AvaliacaoProdutoDTO dto) throws ExceptionLojaVirtual, IOException {

        if (dto.getProduto().getId()==null||dto.getProduto().getId()<=0){
            throw new ExceptionLojaVirtual("Necessário informar o Produto");
        }
        if (dto.getEmpresa().getId()==null||dto.getEmpresa().getId()<=0){
            throw new ExceptionLojaVirtual("Necessário informar a Empresa");
        }
        if (dto.getPessoa().getId()==null||dto.getPessoa().getId()<=0){
            throw new ExceptionLojaVirtual("Necessário informar a Pessoa");
        }

        return new ResponseEntity<AvaliacaoProdutoDTO>(avaliacaoProdutoService.salvaAvaliacaoProduto(dto), HttpStatus.OK);
    }

    @GetMapping("/buscaAvaliacaoProdutoByIdProduto/{idProduto}")
    public ResponseEntity<List<AvaliacaoProdutoDTO>>buscaAvaliacaoProdutoByIdProduto(@PathVariable Long idProduto) throws ExceptionLojaVirtual {



        return new ResponseEntity<List<AvaliacaoProdutoDTO>>(avaliacaoProdutoService.buscaAvaliacaoProdutoByIdProduto(idProduto),HttpStatus.OK);
    }

    @GetMapping("/buscaAvaliacaoProdutoByIdPessoa/{idPessoa}")
    public ResponseEntity<List<AvaliacaoProdutoDTO>>buscaAvaliacaoProdutoByIdPessoa(@PathVariable Long idPessoa) throws ExceptionLojaVirtual {



        return new ResponseEntity<List<AvaliacaoProdutoDTO>>(avaliacaoProdutoService.buscaAvaliacaoProdutoByIdPessoa(idPessoa),HttpStatus.OK);
    }

    @GetMapping("/buscaAvaliacaoProdutoByIdPessoaAndIdProduto/{idPessoa}/{idProduto}")
    public ResponseEntity<List<AvaliacaoProdutoDTO>>buscaAvaliacaoProdutoByIdPessoaAndIdProduto(@PathVariable Long idPessoa,Long idProduto) throws ExceptionLojaVirtual {


        return new ResponseEntity<List<AvaliacaoProdutoDTO>>(avaliacaoProdutoService.buscaAvaliacaoProdutoByIdPessoaAndIdProduto(idPessoa,idProduto),HttpStatus.OK);
    }




}
