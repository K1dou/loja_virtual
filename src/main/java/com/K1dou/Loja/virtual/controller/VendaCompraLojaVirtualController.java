package com.K1dou.Loja.virtual.controller;

import com.K1dou.Loja.virtual.exceptions.ExceptionLojaVirtual;
import com.K1dou.Loja.virtual.model.Dtos.VendaCompraLojaVirtualDTO;
import com.K1dou.Loja.virtual.model.VendaCompraLojaVirtual;
import com.K1dou.Loja.virtual.service.VendaCompraLojaVirtualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/vendaCompra")
public class VendaCompraLojaVirtualController {

    @Autowired
    private VendaCompraLojaVirtualService vendaCompraService;

    @PostMapping("/salvaVendaCompra")
    public ResponseEntity<VendaCompraLojaVirtualDTO>salvaVendaCompra(@RequestBody VendaCompraLojaVirtual dto) throws MessagingException, UnsupportedEncodingException, ExceptionLojaVirtual {



        return new ResponseEntity<VendaCompraLojaVirtualDTO>(vendaCompraService.salvaVendaCompra(dto), HttpStatus.OK);
    }



}
