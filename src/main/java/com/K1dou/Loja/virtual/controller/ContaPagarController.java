package com.K1dou.Loja.virtual.controller;

import com.K1dou.Loja.virtual.exceptions.ExceptionLojaVirtual;
import com.K1dou.Loja.virtual.model.ContaPagar;
import com.K1dou.Loja.virtual.model.PessoaJuridica;
import com.K1dou.Loja.virtual.repository.ContaPagarRepository;
import com.K1dou.Loja.virtual.repository.PessoaJuridicaRepository;
import com.K1dou.Loja.virtual.service.ContaPagarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contaPagar")
public class ContaPagarController {

    @Autowired
    private ContaPagarService contaPagarService;
    @Autowired
    private ContaPagarRepository contaPagarRepository;

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    @PostMapping("/cadastrarContaPagar")
    public ResponseEntity<ContaPagar> salvarContaPagar(@RequestBody ContaPagar contaPagar) throws ExceptionLojaVirtual {

        if (contaPagar.getId() == null) {
            List<ContaPagar> list = contaPagarRepository.findAllDesc(contaPagar.getDescricao());

            if (!list.isEmpty()) {

                throw new ExceptionLojaVirtual("Ja existe Marca com esse nome: " + contaPagar.getDescricao());
            }
        }

        return new ResponseEntity<ContaPagar>(contaPagarService.salvarContaPagar(contaPagar), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ContaPagar>> findAll() throws ExceptionLojaVirtual {

        List<ContaPagar> ContaPagars = contaPagarService.findAllMarcas();
        if (ContaPagars.isEmpty()) {
            throw new ExceptionLojaVirtual("Nenhuma Marca de produto encontrada");
        }

        return new ResponseEntity<List<ContaPagar>>(contaPagarService.findAllMarcas(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaPagar> findByIdContaPagar(@PathVariable Long id) throws ExceptionLojaVirtual {


        return new ResponseEntity<ContaPagar>(contaPagarService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/findByDesc/{desc}")
    public ResponseEntity<List<ContaPagar>> findByDesc(@PathVariable String desc) throws ExceptionLojaVirtual {

        List<ContaPagar> ContaPagars = contaPagarService.findByDescri(desc);
        if (ContaPagars.isEmpty()) {
            throw new ExceptionLojaVirtual("Nenhuma Marca de produto encontrada");
        }


        return new ResponseEntity<List<ContaPagar>>(contaPagarService.findByDescri(desc), HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContaPagarById(@PathVariable Long id) throws ExceptionLojaVirtual {
        ContaPagar ContaPagar = contaPagarRepository.findById(id).orElseThrow(() -> new ExceptionLojaVirtual("Id da conta a pagar do produto é invalido ou não existe"));
        contaPagarService.deleteContaPagarById(id);
        return new ResponseEntity<>("Marca do produto deletada", HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ContaPagar> updateContaPagar(@RequestBody ContaPagar ContaPagar) throws ExceptionLojaVirtual {

        PessoaJuridica pessoaJuridica = pessoaJuridicaRepository.findById(ContaPagar.getEmpresa().getId()).orElseThrow(() -> new ExceptionLojaVirtual("Id da empresa está invalido ou não existe"));
        ContaPagar ContaPagar1 = contaPagarRepository.findById(ContaPagar.getId()).orElseThrow(() -> new ExceptionLojaVirtual("Id da conta a pagar produto está invalido"));




        return new ResponseEntity<ContaPagar>(contaPagarService.updateContaPagar(ContaPagar), HttpStatus.OK);
    }



}
