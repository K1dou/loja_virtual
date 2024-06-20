package com.K1dou.Loja.virtual.controller;

import com.K1dou.Loja.virtual.exceptions.ExceptionLojaVirtual;
import com.K1dou.Loja.virtual.model.Dtos.ImagemProdutoDTO;
import com.K1dou.Loja.virtual.service.ImagemProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/imagemProduto")
public class ImagemProdutoController {

    @Autowired
    private ImagemProdutoService imagemProdutoService;

    @PostMapping("/salvaImagem")
    public ResponseEntity<ImagemProdutoDTO>salvaImagem(@RequestBody @Valid ImagemProdutoDTO dto) throws ExceptionLojaVirtual, IOException {

        if (dto.getProduto().getId()==null||dto.getProduto().getId()<=0){
            throw new ExceptionLojaVirtual("Necessário informar o Produto");
        }
        if (dto.getEmpresa().getId()==null||dto.getEmpresa().getId()<=0){
            throw new ExceptionLojaVirtual("Necessário informar a Empresa");
        }


        return new ResponseEntity<ImagemProdutoDTO>(imagemProdutoService.salvaImagemProduto(dto), HttpStatus.OK);
    }

    @GetMapping("/buscaImagensByIdProduto/{idProduto}")
    public ResponseEntity<List<ImagemProdutoDTO>>findImagensByProdutoId(@PathVariable Long idProduto) throws ExceptionLojaVirtual {


        return new ResponseEntity<List<ImagemProdutoDTO>>(imagemProdutoService.findImagensByProduto(idProduto),HttpStatus.OK);
    }

    @DeleteMapping("/deleteImagemProdutoByIdProduto/{idProduto}")
    public ResponseEntity<?> deleteImagemProdutoByIdProduto(@PathVariable Long idProduto) throws ExceptionLojaVirtual {
        imagemProdutoService.deleteImagemProdutoByIdProduto(idProduto);

        return new ResponseEntity<>("Imagens associadas ao produto deletadas",HttpStatus.OK);
    }

    @DeleteMapping("/deleteImagemById/{idImagem}")
    public ResponseEntity<?>deleteImagemProdutoById(@PathVariable Long idImagem)throws ExceptionLojaVirtual{

        imagemProdutoService.deleteImagemProdutoById(idImagem);
        return new ResponseEntity<>("Imagem deletada",HttpStatus.OK);
    }

    @GetMapping("/buscaImagemProdutoById/{idImagemProduto}")
    public ResponseEntity<ImagemProdutoDTO>buscaImagemProdutoById(@PathVariable Long idImagemProduto) throws ExceptionLojaVirtual {


        return new ResponseEntity<ImagemProdutoDTO>(imagemProdutoService.buscaImagemProdutoById(idImagemProduto),HttpStatus.OK);
    }

    @PutMapping("/updateImagemProduto")
    public ResponseEntity<ImagemProdutoDTO>updateImagemProduto(@RequestBody @Valid ImagemProdutoDTO imagemProdutoDTO) throws ExceptionLojaVirtual {



        return new ResponseEntity<ImagemProdutoDTO>(imagemProdutoService.updateImagemProduto(imagemProdutoDTO),HttpStatus.OK);
    }

}
