package com.K1dou.Loja.virtual.controller;

import com.K1dou.Loja.virtual.exceptions.ExceptionLojaVirtual;
import com.K1dou.Loja.virtual.model.NotaFiscalCompra;
import com.K1dou.Loja.virtual.repository.NotaFiscalCompraRepository;
import com.K1dou.Loja.virtual.service.NotaFiscalCompraService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notaFiscalCompra")
public class NotaFiscalCompraController {

    @Autowired
    private NotaFiscalCompraService notaFiscalCompraService;

    @Autowired
    private NotaFiscalCompraRepository notaFiscalCompraRepository;


    @PostMapping
    public ResponseEntity<NotaFiscalCompra> cadastrarNotaFiscalCompra(@RequestBody @Valid NotaFiscalCompra dto) throws ExceptionLojaVirtual {

        if (dto.getId() == null || dto.getId() <= 0) {
            List<NotaFiscalCompra> notaFiscalCompras = notaFiscalCompraRepository.buscarPorDesc(dto.getDescricaoObs());

            if (!notaFiscalCompras.isEmpty()) {
                throw new ExceptionLojaVirtual("Ja existe Nota fiscal com essa descrição");
            }
        }

        if (dto.getEmpresa().getId() == null || dto.getEmpresa().getId() <= 0) {
            throw new ExceptionLojaVirtual("Necessário informa o id da empresa");
        }
        if (dto.getPessoa().getId() == null || dto.getPessoa().getId() <= 0) {
            throw new ExceptionLojaVirtual("Necessário informa o id da pessoa");
        }
        if (dto.getContaPagar().getId() == null || dto.getContaPagar().getId() <= 0) {
            throw new ExceptionLojaVirtual("Necessário informa o id da conta pagar");
        }

        return new ResponseEntity<NotaFiscalCompra>(notaFiscalCompraService.cadastrarNotaFiscal(dto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<NotaFiscalCompra>> todasNotaFiscalCompra() {


        return new ResponseEntity<List<NotaFiscalCompra>>(notaFiscalCompraService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/buscarNotaFiscalCompraPorDesc/{desc}")
    public ResponseEntity<List<NotaFiscalCompra>> buscaNotaFiscalDesc(@PathVariable String desc) {


        return new ResponseEntity<List<NotaFiscalCompra>>(notaFiscalCompraService.findByDesc(desc), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotaFiscalCompra> findById(@PathVariable Long id) throws ExceptionLojaVirtual {


        return new ResponseEntity<NotaFiscalCompra>(notaFiscalCompraService.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNotaFiscalCompraById(@PathVariable Long id) throws ExceptionLojaVirtual {
        notaFiscalCompraService.deleteById(id);

        return new ResponseEntity<>("Nota fiscal compra deletada", HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<NotaFiscalCompra> updateNotaFiscalCompra(@RequestBody NotaFiscalCompra notaFiscalCompra) throws ExceptionLojaVirtual {


        return new ResponseEntity<NotaFiscalCompra>(notaFiscalCompraService.updateNotaFiscalCompra(notaFiscalCompra), HttpStatus.OK);
    }


}
