package com.K1dou.Loja.virtual.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PagamentoController {

    @GetMapping("/pagamento/{idVendaCompra}")
    public ModelAndView pagamentoComId(@PathVariable String idVendaCompra) {
        // Lógica para quando idVendaCompra é fornecido
        return new ModelAndView("pagamento");
    }

    @GetMapping("/pagamento")
    public ModelAndView pagamentoSemId() {
        // Lógica para quando idVendaCompra não é fornecido
        return new ModelAndView("pagamento");
    }

}
