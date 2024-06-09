package com.K1dou.Loja.virtual.controller;

import com.K1dou.Loja.virtual.exceptions.ExceptionLojaVirtual;
import com.K1dou.Loja.virtual.model.Dtos.ProdutoDTO;
import com.K1dou.Loja.virtual.model.Produto;
import com.K1dou.Loja.virtual.repository.ProdutoRepository;
import com.K1dou.Loja.virtual.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private ProdutoRepository produtoRepository;


    @PostMapping("/cadastrarProduto")
    public ResponseEntity<ProdutoDTO> cadastrarProduto(@RequestBody @Valid ProdutoDTO dto) throws ExceptionLojaVirtual {

        if (dto.categoriaProduto() == null || dto.categoriaProduto().getId() <= 0) {
            throw new ExceptionLojaVirtual("Categoria do produto deve ser informada");
        }
        if (dto.empresa() == null || dto.empresa().getId() <= 0) {
            throw new ExceptionLojaVirtual("Empresa Responsável deve ser informada");
        }
        if (dto.marcaProduto() == null || dto.marcaProduto().getId() <= 0) {
            throw new ExceptionLojaVirtual("Marca produto deve ser informada");
        }


        if (dto.id() == null) {
            List<Produto> produtos = produtoRepository.findByNome(dto.nome().toUpperCase(), dto.empresa().getId());

            if (!produtos.isEmpty()) {
                throw new ExceptionLojaVirtual("Já existe produto com esse nome " + dto.nome());
            }

        }


        ProdutoDTO produtoDTO = produtoService.cadastrarProduto(dto);
        return new ResponseEntity<ProdutoDTO>(produtoDTO, HttpStatus.CREATED);
    }


}
