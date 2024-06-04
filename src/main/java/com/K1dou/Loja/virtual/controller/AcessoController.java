package com.K1dou.Loja.virtual.controller;

import com.K1dou.Loja.virtual.LojaVirtualApplication;
import com.K1dou.Loja.virtual.exceptions.ExceptionLojaVirtual;
import com.K1dou.Loja.virtual.model.Acesso;
import com.K1dou.Loja.virtual.repository.AcessoRepository;
import com.K1dou.Loja.virtual.service.AcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/acesso")
public class AcessoController {

    @Autowired
    private AcessoService acessoService;

    @Autowired
    private AcessoRepository acessoRepository;

    @PostMapping("/salvarAcesso")
    public ResponseEntity<Acesso> salvarAcesso(@RequestBody Acesso acesso) throws ExceptionLojaVirtual {

       if (acesso.getId()==null){
           List<Acesso> acessos = acessoRepository.buscarAcessoDesc(acesso.getDescricao().toUpperCase());
           if (!acessos.isEmpty()){
               throw new ExceptionLojaVirtual("Já existe Acesso com a descrição: "+acesso.getDescricao());
           }
       }



        Acesso acessoSalvo = acessoService.save(acesso);

        return new ResponseEntity<Acesso>(acessoSalvo, HttpStatus.OK);
    }


    @PostMapping("/deleteAcesso")
    public ResponseEntity deleteAcesso(@RequestBody Acesso acesso){

        acessoRepository.deleteById(acesso.getId());

        return new ResponseEntity("Acesso removido",HttpStatus.OK);

    }

    @DeleteMapping("/deleteAcessoPorId/{id}")
    public ResponseEntity deleteById(@PathVariable Long id){

        acessoRepository.deleteById(id);

        return new ResponseEntity("Acesso removido",HttpStatus.OK);

    }


    @GetMapping("/obterAcesso/{id}")
    public ResponseEntity<Acesso> obterAcesso(@PathVariable Long id) throws ExceptionLojaVirtual {

      Acesso acesso= acessoRepository.findById(id).orElseThrow(()->new ExceptionLojaVirtual("Não encontrado Acesso com código"));


        return new ResponseEntity<Acesso>(acesso,HttpStatus.OK);

    }



    @GetMapping("/buscarPorDesc/{desc}")
    public ResponseEntity<List<Acesso>>buscarPorDesc(@PathVariable String desc){

        List<Acesso> acessos = acessoRepository.buscarAcessoDesc(desc);

        return new ResponseEntity<List<Acesso>>(acessos,HttpStatus.OK);
    }





}
