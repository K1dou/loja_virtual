package com.K1dou.Loja.virtual.controller;

import com.K1dou.Loja.virtual.service.VendaCompraLojaVirtualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vendaCompra")
public class VendaCompraLojaVirtualController {

    @Autowired
    private VendaCompraLojaVirtualService vendaCompraService;




}
