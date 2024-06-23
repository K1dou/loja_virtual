package com.K1dou.Loja.virtual.controller;

import com.K1dou.Loja.virtual.exceptions.ExceptionLojaVirtual;
import com.K1dou.Loja.virtual.model.Dtos.FormaPagamentoDTO;
import com.K1dou.Loja.virtual.service.FormaPagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/formaPagamento")
public class FormaPagamentoController {

    @Autowired
    private FormaPagamentoService formaPagamentoService;


    @PostMapping("/salvaFormaPagamento")
    public ResponseEntity<FormaPagamentoDTO>salvaFormaPagamento(@RequestBody @Valid FormaPagamentoDTO dto) throws ExceptionLojaVirtual {

        if (dto.getEmpresa().getId()==null||dto.getEmpresa().getId()<=0){
            throw new ExceptionLojaVirtual("É necessário informar a empresa");
        }


        return new ResponseEntity<FormaPagamentoDTO>(formaPagamentoService.salvaFormaPagamento(dto), HttpStatus.OK);
    }


}
