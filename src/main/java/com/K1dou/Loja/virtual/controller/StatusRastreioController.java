package com.K1dou.Loja.virtual.controller;


import com.K1dou.Loja.virtual.exceptions.ExceptionLojaVirtual;
import com.K1dou.Loja.virtual.model.StatusRastreio;
import com.K1dou.Loja.virtual.service.StatusRastreioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statusRastreio")
public class StatusRastreioController {

    @Autowired
    private StatusRastreioService statusRastreioService;


    @GetMapping("/listaRastreioVenda/{idVenda}")
    public ResponseEntity<List<StatusRastreio>>listaRastreioVenda(@PathVariable Long idVenda) throws ExceptionLojaVirtual {


        return new ResponseEntity<List<StatusRastreio>>(statusRastreioService.listaRastreioVenda(idVenda), HttpStatus.OK);
    }


}
