package com.K1dou.Loja.virtual.controller;

import com.K1dou.Loja.virtual.exceptions.ExceptionLojaVirtual;
import com.K1dou.Loja.virtual.model.Dtos.VendaCompraLojaVirtualDTO;
import com.K1dou.Loja.virtual.model.Dtos.transporteDTO.BodyRastreioDTO;
import com.K1dou.Loja.virtual.model.Dtos.transporteDTO.ConsultaFreteDTO;
import com.K1dou.Loja.virtual.model.Dtos.transporteDTO.EmpresaTransporteDTO;
import com.K1dou.Loja.virtual.model.Dtos.transporteDTO.IdVendaRequest;
import com.K1dou.Loja.virtual.model.VendaCompraLojaVirtual;
import com.K1dou.Loja.virtual.service.VendaCompraLojaVirtualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/vendaCompra")
public class VendaCompraLojaVirtualController {


    @Autowired
    private VendaCompraLojaVirtualService vendaCompraService;



    @PostMapping("/rastreioDaEtiqueta")
    public ResponseEntity<String>rastreioDaEtiqueta(@RequestBody BodyRastreioDTO bodyRastreio) throws IOException {


        return new ResponseEntity<String>(vendaCompraService.rastreioDaEtiqueta(bodyRastreio),HttpStatus.OK);
    }

    @GetMapping("/cancelaEtiqueta/{idEtiqueta}/{descricao}")
    public ResponseEntity<String> cancelaEtiqueta(@PathVariable String idEtiqueta, @PathVariable String descricao) throws IOException {


        return new ResponseEntity<String>(vendaCompraService.cancelaEtiqueta(idEtiqueta,descricao),HttpStatus.OK);
    }


    @PostMapping("/imprimeCompraEtiquetaFrete")
    public ResponseEntity<String> imprimeCompraEtiquetaFrete(@RequestBody IdVendaRequest idVendaRequest) throws IOException, ExceptionLojaVirtual {


        return new ResponseEntity<String>(vendaCompraService.imprimeCompraEtiquetaFrete(idVendaRequest), HttpStatus.OK);
    }

    @PostMapping("/consultaFrete")
    public ResponseEntity<List<EmpresaTransporteDTO>> consultaFrete(@RequestBody ConsultaFreteDTO dto) throws IOException {


        return new ResponseEntity<List<EmpresaTransporteDTO>>(vendaCompraService.consultaFrete(dto), HttpStatus.OK);
    }

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
    public ResponseEntity<List<VendaCompraLojaVirtualDTO>> consultaVendaDinamica(@PathVariable String valor, @PathVariable String tipoConsulta) {

        return new ResponseEntity<List<VendaCompraLojaVirtualDTO>>(vendaCompraService.consultaVendaDinamica(valor, tipoConsulta), HttpStatus.OK);
    }


    @GetMapping("/consultaVendaPorDataJPA/{data1}/{data2}")
    public ResponseEntity<List<VendaCompraLojaVirtualDTO>> consultaVendaPorDataJPA(@PathVariable String data1, @PathVariable String data2) throws ParseException {

        return new ResponseEntity<List<VendaCompraLojaVirtualDTO>>(vendaCompraService.consultaVendaPorDataJPA(data1, data2), HttpStatus.OK);
    }


    @GetMapping("/consultaPorCpfPessoa/{cpf}")
    public ResponseEntity<List<VendaCompraLojaVirtualDTO>> consultaPorCpfPessoa(@PathVariable String cpf) {


        return new ResponseEntity<List<VendaCompraLojaVirtualDTO>>(vendaCompraService.consultaPorParteCpfPessoa(cpf), HttpStatus.OK);
    }

    @GetMapping("/consultaPorParteCpfPessoa/{cpf}")
    public ResponseEntity<List<VendaCompraLojaVirtualDTO>> consultaPorParteCpfPessoa(@PathVariable String cpf) {


        return new ResponseEntity<List<VendaCompraLojaVirtualDTO>>(vendaCompraService.consultaPorParteCpfPessoa(cpf), HttpStatus.OK);
    }


    @GetMapping("/consultaPorPessoaId/{id}")
    public ResponseEntity<List<VendaCompraLojaVirtualDTO>> consultaPorPessoaId(@PathVariable Long id) {


        return new ResponseEntity<List<VendaCompraLojaVirtualDTO>>(vendaCompraService.consultaPorPessoaId(id), HttpStatus.OK);
    }


}
