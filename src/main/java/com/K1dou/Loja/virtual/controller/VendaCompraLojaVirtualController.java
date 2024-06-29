package com.K1dou.Loja.virtual.controller;

import com.K1dou.Loja.virtual.exceptions.ExceptionLojaVirtual;
import com.K1dou.Loja.virtual.model.Dtos.VendaCompraLojaVirtualDTO;
import com.K1dou.Loja.virtual.model.VendaCompraLojaVirtual;
import com.K1dou.Loja.virtual.service.VendaCompraLojaVirtualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/vendaCompra")
public class VendaCompraLojaVirtualController {


    @Autowired
    private VendaCompraLojaVirtualService vendaCompraService;

    @PostMapping("/salvaVendaCompra")
    public ResponseEntity<VendaCompraLojaVirtualDTO> salvaVendaCompra(@RequestBody VendaCompraLojaVirtual dto) throws MessagingException, UnsupportedEncodingException, ExceptionLojaVirtual {


        return new ResponseEntity<VendaCompraLojaVirtualDTO>(vendaCompraService.salvaVendaCompra(dto), HttpStatus.OK);
    }

    @DeleteMapping("/deleteVendaTotal/{idVenda}")
    public ResponseEntity<?> exclusaoTotalVendaBanco(@PathVariable Long idVenda) {
        vendaCompraService.exclusaoTotalVendaBanco(idVenda);

        return new ResponseEntity<>("Venda Deletada", HttpStatus.OK);
    }

    @GetMapping("/consultaVendaId/{id}")
    public ResponseEntity<VendaCompraLojaVirtualDTO> consultaVendaId(@PathVariable Long id) throws ExceptionLojaVirtual {


        return new ResponseEntity<VendaCompraLojaVirtualDTO>(vendaCompraService.consultaVendaId(id), HttpStatus.OK);
    }

    @DeleteMapping("/exclusaoLogicaVendaBanco/{idVenda}")
    public ResponseEntity<?> exclusaoLogicaVendaBanco(@PathVariable Long idVenda) {
        vendaCompraService.exclusaoLogicaVendaBanco(idVenda);

        return new ResponseEntity<>("Venda Deletada", HttpStatus.OK);
    }

    @PutMapping("/restaurarExclusaoLogica/{idVenda}")
    public ResponseEntity<?> restaurarExclusaoVendaBanco(@PathVariable Long idVenda) {
        vendaCompraService.restaurarExclusaoLogica(idVenda);

        return new ResponseEntity<>("Venda Restaurada", HttpStatus.OK);
    }


    @GetMapping("/vendaPorProduto/{idProduto}")
    public ResponseEntity<List<VendaCompraLojaVirtualDTO>> vendaPorProduto(@PathVariable Long idProduto) {


        return new ResponseEntity<List<VendaCompraLojaVirtualDTO>>(vendaCompraService.vendaPorProduto(idProduto), HttpStatus.OK);
    }


    @GetMapping("/consultaVendaDinamica/{valor}/{tipoConsulta}")
    public ResponseEntity<List<VendaCompraLojaVirtualDTO>>consultaVendaDinamica(@PathVariable String valor,@PathVariable String tipoConsulta){

        return new ResponseEntity<List<VendaCompraLojaVirtualDTO>>(vendaCompraService.consultaVendaDinamica(valor,tipoConsulta),HttpStatus.OK);
    }


}