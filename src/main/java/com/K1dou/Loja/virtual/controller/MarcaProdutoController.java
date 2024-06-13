package com.K1dou.Loja.virtual.controller;

import com.K1dou.Loja.virtual.exceptions.ExceptionLojaVirtual;
import com.K1dou.Loja.virtual.model.MarcaProduto;
import com.K1dou.Loja.virtual.model.PessoaJuridica;
import com.K1dou.Loja.virtual.repository.MarcaProdutoRepository;
import com.K1dou.Loja.virtual.repository.PessoaJuridicaRepository;
import com.K1dou.Loja.virtual.service.MarcaProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/marcaProduto")
public class MarcaProdutoController {

    @Autowired
    private MarcaProdutoService marcaProdutoService;
    @Autowired
    private MarcaProdutoRepository marcaProdutoRepository;

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    @PostMapping("/cadastrarMarcaProduto")
    public ResponseEntity<MarcaProduto> salvarMarcaProduto(@RequestBody MarcaProduto marcaProduto) throws ExceptionLojaVirtual {

//        PessoaJuridica empresa =pessoaJuridicaRepository.findById(marcaProduto.getEmpresa().getId()).orElseThrow(()->new ExceptionLojaVirtual("Id da empresa invalido"));


        if (marcaProduto.getId() == null) {
            List<MarcaProduto> list = marcaProdutoRepository.findAllDesc(marcaProduto.getNomeDesc());

            if (!list.isEmpty()) {

                throw new ExceptionLojaVirtual("Ja existe Marca com esse nome: " + marcaProduto.getNomeDesc());
            }
        }

        return new ResponseEntity<MarcaProduto>(marcaProdutoService.salvarMarcaProduto(marcaProduto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MarcaProduto>> findAll() throws ExceptionLojaVirtual {

        List<MarcaProduto> marcaProdutos = marcaProdutoService.findAllMarcas();
        if (marcaProdutos.isEmpty()) {
            throw new ExceptionLojaVirtual("Nenhuma Marca de produto encontrada");
        }

        return new ResponseEntity<List<MarcaProduto>>(marcaProdutoService.findAllMarcas(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarcaProduto> findByIdMarcaProduto(@PathVariable Long id) throws ExceptionLojaVirtual {


        return new ResponseEntity<MarcaProduto>(marcaProdutoService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/findByDesc/{desc}")
    public ResponseEntity<List<MarcaProduto>> findByDesc(@PathVariable String desc) throws ExceptionLojaVirtual {

        List<MarcaProduto> marcaProdutos = marcaProdutoService.findByDescri(desc);
        if (marcaProdutos.isEmpty()) {
            throw new ExceptionLojaVirtual("Nenhuma Marca de produto encontrada");
        }


        return new ResponseEntity<List<MarcaProduto>>(marcaProdutoService.findByDescri(desc), HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMarcaProdutoById(@PathVariable Long id) throws ExceptionLojaVirtual {
        MarcaProduto marcaProduto = marcaProdutoRepository.findById(id).orElseThrow(() -> new ExceptionLojaVirtual("Id da marca do produto é invalido ou não existe"));
        marcaProdutoService.deleteMarcaProduto(id);
        return new ResponseEntity<>("Marca do produto deletada", HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<MarcaProduto> updateMarcaProduto(@RequestBody MarcaProduto marcaProduto) throws ExceptionLojaVirtual {

        PessoaJuridica pessoaJuridica = pessoaJuridicaRepository.findById(marcaProduto.getEmpresa().getId()).orElseThrow(() -> new ExceptionLojaVirtual("Id da empresa está invalido ou não existe"));
        MarcaProduto marcaProduto1 = marcaProdutoRepository.findById(marcaProduto.getId()).orElseThrow(() -> new ExceptionLojaVirtual("Id da marca produto está invalido"));




        return new ResponseEntity<MarcaProduto>(marcaProdutoService.updateMarcaProduto(marcaProduto), HttpStatus.OK);
    }


}
