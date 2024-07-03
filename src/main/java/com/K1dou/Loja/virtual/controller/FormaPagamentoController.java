package com.K1dou.Loja.virtual.controller;

import com.K1dou.Loja.virtual.exceptions.ExceptionLojaVirtual;
import com.K1dou.Loja.virtual.model.Dtos.FormaPagamentoDTO;
import com.K1dou.Loja.virtual.model.FormaPagamento;
import com.K1dou.Loja.virtual.service.FormaPagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/formaPagamento")
public class FormaPagamentoController {

    @Autowired
    private FormaPagamentoService formaPagamentoService;


    @PostMapping("/salvaFormaPagamento")
    public ResponseEntity<FormaPagamento> salvaFormaPagamento(@RequestBody @Valid FormaPagamento dto) throws ExceptionLojaVirtual {
        if (dto.getEmpresa().getId() == null || dto.getEmpresa().getId() <= 0) {
            throw new ExceptionLojaVirtual("É necessário informar a empresa");
        }
        return new ResponseEntity<FormaPagamento>(formaPagamentoService.salvaFormaPagamento(dto), HttpStatus.OK);
    }

    @GetMapping("/formaPagamentoPorEmpresaId/{empresaId}")
    public ResponseEntity<List<FormaPagamentoDTO>> formaPagamentoPorEmpresaId(@PathVariable Long empresaId) {

        return new ResponseEntity<List<FormaPagamentoDTO>>(formaPagamentoService.formaPagamentoPorEmpresaId(empresaId), HttpStatus.OK);
    }

    @GetMapping("/allFormaPagamentos")
    public ResponseEntity<List<FormaPagamentoDTO>> allFormaPagamentos() {

        return new ResponseEntity<List<FormaPagamentoDTO>>(formaPagamentoService.allFormaPagamentos(), HttpStatus.OK);
    }


}
