package com.K1dou.Loja.virtual.controller;

import com.K1dou.Loja.virtual.exceptions.ExceptionLojaVirtual;
import com.K1dou.Loja.virtual.model.CupDesc;
import com.K1dou.Loja.virtual.model.Dtos.CupDescDTO;
import com.K1dou.Loja.virtual.repository.CupDescRepository;
import com.K1dou.Loja.virtual.service.CupDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cupDesc")
public class CupDescController {

    @Autowired
    private CupDescService cupDescService;
    @Autowired
    private CupDescRepository cupDescRepository;


    @PostMapping("/salvaCupDesc")
    public ResponseEntity<CupDesc>salvaCupDesc(@RequestBody CupDesc cupDesc) throws ExceptionLojaVirtual {

       boolean empresaJaTem = cupDescRepository.jaExisteCupEmpresa(cupDesc.getCodDesc(),cupDesc.getEmpresa().getId());
       if (empresaJaTem){
           throw new ExceptionLojaVirtual("Esta empresa ja contem esse cupom de desconto");
       }

        return new ResponseEntity<CupDesc>(cupDescService.salvaCupDesc(cupDesc), HttpStatus.OK);
    }

    @GetMapping("/consultaCupPorEmpresa/{empresaId}")
    public ResponseEntity<List<CupDescDTO>>consultaCupPorEmpresa(@PathVariable Long empresaId){

        return new ResponseEntity<List<CupDescDTO>>(cupDescService.consultaCupPorEmpresa(empresaId),HttpStatus.OK);
    }


    @GetMapping("/allCupDesc")
    public ResponseEntity<List<CupDescDTO>>allCupDesc(){


        return new ResponseEntity<List<CupDescDTO>>(cupDescService.allCupDesc(),HttpStatus.OK);
    }


}
