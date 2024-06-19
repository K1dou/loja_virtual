package com.K1dou.Loja.virtual.controller;

import com.K1dou.Loja.virtual.exceptions.ExceptionLojaVirtual;
import com.K1dou.Loja.virtual.model.Dtos.NotaItemProdutoDTO;
import com.K1dou.Loja.virtual.model.NotaItemProduto;
import com.K1dou.Loja.virtual.model.Pessoa;
import com.K1dou.Loja.virtual.repository.NotaItemProdutoRepository;
import com.K1dou.Loja.virtual.service.NotaItemProdutoService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notaItemProduto")
public class NotaItemProdutoController {

    @Autowired
    private NotaItemProdutoService notaItemProdutoService;

    @Autowired
    private NotaItemProdutoRepository notaItemProdutoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/salvarNotaItemProduto")
    public ResponseEntity<NotaItemProdutoDTO> salvarNotaItemProduto(@RequestBody @Valid NotaItemProdutoDTO dto) throws ExceptionLojaVirtual {


        if (dto.getPessoa().getId() == null || dto.getPessoa().getId() < 0) {
            throw new ExceptionLojaVirtual("É necessário informar o Id da Empresa");
        }
        if (dto.getNotaFiscalCompra().getId() == null || dto.getNotaFiscalCompra().getId() < 0) {
            throw new ExceptionLojaVirtual("É necessário informar o Id da Nota fiscal");
        }
        if (dto.getPessoa().getId() == null || dto.getPessoa().getId() < 0) {
            throw new ExceptionLojaVirtual("É necessário informar o Id da empresa");
        }
        if (dto.getEmpresa().getId() == null || dto.getEmpresa().getId() < 0) {
            throw new ExceptionLojaVirtual("É necessário informar o Id da empresa");
        }
        if (dto.getId()==null){
            List<NotaItemProduto>notaItemProdutos = notaItemProdutoRepository.buscaNotaItemProdutoNota(dto.getProduto().getId(),dto.getNotaFiscalCompra().getId());
            if (!notaItemProdutos.isEmpty()){
                throw new ExceptionLojaVirtual("Já existe esse produto cadastrado nessa nota");
            }
        }




        return new ResponseEntity<NotaItemProdutoDTO>(notaItemProdutoService.cadastrarNotaItemProduto(dto), HttpStatus.OK);
    }

    @GetMapping("/buscaTodosNotaItemProduto")
    public ResponseEntity<List<NotaItemProdutoDTO>>findAll(){

        List<NotaItemProdutoDTO>notaItemProdutoDTOS = notaItemProdutoService.findAll();

        return new ResponseEntity<List<NotaItemProdutoDTO>>(notaItemProdutoDTOS,HttpStatus.OK);
    }

    @GetMapping("/buscarNotaItemProdutoById/{id}")
    public ResponseEntity<NotaItemProdutoDTO>findById(@PathVariable Long id) throws ExceptionLojaVirtual {


        return new ResponseEntity<NotaItemProdutoDTO>(notaItemProdutoService.findById(id),HttpStatus.OK);
    }

    @PutMapping("/updateNotaItemProduto")
    public ResponseEntity<NotaItemProdutoDTO>update(@RequestBody NotaItemProdutoDTO dto) throws ExceptionLojaVirtual {

        if (dto.getId()==null||dto.getId()<=0){
            throw new ExceptionLojaVirtual("Necessário informar o Id da nota item produto para atualizar");
        }


        return new ResponseEntity<NotaItemProdutoDTO>(notaItemProdutoService.update(dto),HttpStatus.OK);
    }

    @DeleteMapping("/deleteNotaItemProdutoById/{id}")
    public ResponseEntity<?>deleteByIdd(@PathVariable Long id) throws ExceptionLojaVirtual {

        notaItemProdutoService.deleteNotaItemProdutoById(id);

        return new ResponseEntity<>("Nota item produto deletada",HttpStatus.OK);
    }

}
