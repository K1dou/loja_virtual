package com.K1dou.Loja.virtual.controller;

import com.K1dou.Loja.virtual.exceptions.ExceptionLojaVirtual;
import com.K1dou.Loja.virtual.model.CategoriaProduto;
import com.K1dou.Loja.virtual.model.Dtos.CategoriaProdutoDTO;
import com.K1dou.Loja.virtual.repository.CategoriaProdutoRepository;
import com.K1dou.Loja.virtual.service.CategoriaProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/categoriaProduto")
public class CategoriaProdutoController {

    @Autowired
    private CategoriaProdutoRepository categoriaProdutoRepository;
    @Autowired
    private CategoriaProdutoService categoriaProdutoService;


    @PostMapping("/salvarCategoria")
    public ResponseEntity<CategoriaProdutoDTO> saveCategoria(@RequestBody CategoriaProdutoDTO dto) throws ExceptionLojaVirtual {

        if (dto.idEmpresa() == null || dto.idEmpresa() <= 0) {
            throw new ExceptionLojaVirtual("A empresa deve ser informada.");
        }
        if (dto.idEmpresa() == null || categoriaProdutoRepository.existeCategoria(dto.nomeDesc().toUpperCase())) {
            throw new ExceptionLojaVirtual("Não pode cadastrar categoria com o mesmo nome.");
        }

        return new ResponseEntity<CategoriaProdutoDTO>(categoriaProdutoService.salvarCategoria(dto), HttpStatus.CREATED);
    }

    @GetMapping("/buscarCategoriaProdutoId/{id}")
    public ResponseEntity<CategoriaProdutoDTO> findById(@PathVariable Long id) throws ExceptionLojaVirtual {

        CategoriaProduto categoriaProduto = categoriaProdutoRepository.findById(id).orElseThrow(() -> new ExceptionLojaVirtual("Id da categoria de produtos invalido."));

        return new ResponseEntity<CategoriaProdutoDTO>(categoriaProdutoService.findById(id), HttpStatus.FOUND);
    }

    @DeleteMapping("/deleteCategoriaProdutoById/{id}")
    public ResponseEntity<?> deleteCategoriaProdutoById(@PathVariable Long id) throws ExceptionLojaVirtual {
        CategoriaProduto categoriaProduto = categoriaProdutoRepository.findById(id).orElseThrow(() -> new ExceptionLojaVirtual("Id da categoria de produtos invalido."));

        categoriaProdutoService.deleteById(id);

        return new ResponseEntity<>("Categoria removida",HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CategoriaProduto>> findAllCaregoriaProduto() {
        return new ResponseEntity<List<CategoriaProduto>>(categoriaProdutoService.findAll(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<CategoriaProdutoDTO>updateCategoriaProduto(@RequestBody CategoriaProdutoDTO dto) throws ExceptionLojaVirtual {
        return new ResponseEntity<CategoriaProdutoDTO>(categoriaProdutoService.updateCategoriaProduto(dto),HttpStatus.OK);
    }

}
