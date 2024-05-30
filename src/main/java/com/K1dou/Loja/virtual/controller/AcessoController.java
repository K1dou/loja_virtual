package com.K1dou.Loja.virtual.controller;

import com.K1dou.Loja.virtual.model.Acesso;
import com.K1dou.Loja.virtual.repository.AcessoRepository;
import com.K1dou.Loja.virtual.service.AcessoService;
import com.sun.net.httpserver.HttpsServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/acesso")
public class AcessoController {

    @Autowired
    private AcessoService acessoService;

    @Autowired
    private AcessoRepository acessoRepository;

    @ResponseBody
    @PostMapping("/salvarAcesso")
    public ResponseEntity<Acesso> salvarAcesso(@RequestBody Acesso acesso){

        Acesso acessoSalvo = acessoService.save(acesso);

        return new ResponseEntity<Acesso>(acessoSalvo, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/deleteAcesso")
    public ResponseEntity<?> deleteAcesso(@RequestBody Acesso acesso){

        acessoRepository.deleteById(acesso.getId());

        return new ResponseEntity("Acesso removido",HttpStatus.OK);

    }

    @ResponseBody
    @DeleteMapping("/deleteAcessoPorId/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){

        acessoRepository.deleteById(id);

        return new ResponseEntity("Acesso removido",HttpStatus.OK);

    }

    @ResponseBody
    @GetMapping("/obterAcesso/{id}")
    public ResponseEntity<Acesso> obterAcesso(@PathVariable Long id){

      Acesso acesso= acessoRepository.findById(id).get();

        return new ResponseEntity<Acesso>(acesso,HttpStatus.OK);

    }

    @ResponseBody
    @GetMapping("/buscarPorDesc/{desc}")
    public ResponseEntity<List<Acesso>>buscarPorDesc(@PathVariable String desc){

        List<Acesso> acessos = acessoRepository.buscarAcessoDesc(desc);

        return new ResponseEntity<List<Acesso>>(acessos,HttpStatus.OK);
    }





}
