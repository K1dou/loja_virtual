package com.K1dou.Loja.virtual.controller;

import com.K1dou.Loja.virtual.exceptions.ExceptionLojaVirtual;
import com.K1dou.Loja.virtual.model.Dtos.ProdutoDTO;
import com.K1dou.Loja.virtual.model.ImagemProduto;
import com.K1dou.Loja.virtual.model.PessoaJuridica;
import com.K1dou.Loja.virtual.model.Produto;
import com.K1dou.Loja.virtual.repository.ImagemProdutoRepository;
import com.K1dou.Loja.virtual.repository.PessoaJuridicaRepository;
import com.K1dou.Loja.virtual.repository.ProdutoRepository;
import com.K1dou.Loja.virtual.service.ProdutoService;
import com.K1dou.Loja.virtual.service.ServiceSendEmail;
import jakarta.validation.Valid;
import jakarta.xml.bind.DatatypeConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ImagemProdutoRepository imagemProdutoRepository;

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ServiceSendEmail serviceSendEmail;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/cadastrarProduto")
    public ResponseEntity<ProdutoDTO> cadastrarProduto(@RequestBody @Valid ProdutoDTO dto) throws ExceptionLojaVirtual, MessagingException, IOException {

        if (dto.getTipoUnidade() == null || dto.getTipoUnidade().trim().isEmpty()) {
            throw new ExceptionLojaVirtual("Tipo unidade deve ser informado");
        }
        if (dto.getQtdEstoque() < 1) {
            throw new ExceptionLojaVirtual("O produto deve ter no minimo 1 quantidade");
        }

        if (dto.getNome().length() < 10) {
            throw new ExceptionLojaVirtual("Nome do produto deve ter mais de 10 letras");
        }

        if (dto.getCategoriaProduto() == null || dto.getCategoriaProduto().getId() <= 0) {
            throw new ExceptionLojaVirtual("Categoria do produto deve ser informada");
        }
        if (dto.getEmpresa() == null || dto.getEmpresa().getId() <= 0) {
            throw new ExceptionLojaVirtual("Empresa Responsável deve ser informada");
        }
        if (dto.getMarcaProduto() == null || dto.getMarcaProduto().getId() <= 0) {
            throw new ExceptionLojaVirtual("Marca produto deve ser informada");
        }


        if (dto.getId() == null) {
            List<Produto> produtos = produtoRepository.findByNomes(dto.getNome().toUpperCase(), dto.getEmpresa().getId());

            if (!produtos.isEmpty()) {
                throw new ExceptionLojaVirtual("Já existe produto com esse nome " + dto.getNome());
            }

        }

        if (dto.getImagens().isEmpty() || dto.getImagens().size() == 0) {
            throw new ExceptionLojaVirtual("Deve ser informado imagens para o Produto");
        }


        if (dto.getImagens().size() < 3) {
            throw new ExceptionLojaVirtual("Deve ser informado pelo menos 3 imagens para o Produto");
        }

        if (dto.getImagens().size() > 6) {
            throw new ExceptionLojaVirtual("Deve ser informado no máximo 6 imagens ");
        }

        ProdutoDTO produtoDTO = produtoService.cadastrarProduto(dto);


        if (dto.getAlertaQtdEstoque() && dto.getQtdEstoque() <= 1) {
            StringBuilder email = new StringBuilder();
            email.append("<h2>")
                    .append("Produto: " + dto.getNome()).append("<h2>")
                    .append(" com estoque baixo: " + dto.getQtdEstoque());
            email.append("<p> Id Prod.: ").append(dto.getId()).append("</p>");

            if (dto.getEmpresa().getEmail() != null) {
                serviceSendEmail.enviarEmailHtml("Estoque baixo", email.toString(), dto.getEmpresa().getEmail());
            }

        }
        return new ResponseEntity<ProdutoDTO>(produtoDTO, HttpStatus.CREATED);
    }


    @GetMapping("/buscaTodosProdutos")
    public ResponseEntity<List<ProdutoDTO>> findAll() {


        return new ResponseEntity<List<ProdutoDTO>>(produtoService.findAllProduto(), HttpStatus.OK);
    }

    @GetMapping("/findProdutoByName/{nome}")
    public ResponseEntity<List<ProdutoDTO>> findProdutoByName(@PathVariable String nome) {


        return new ResponseEntity<List<ProdutoDTO>>(produtoService.findByNome(nome), HttpStatus.OK);
    }

    @GetMapping("/findProdutoById/{id}")
    public ResponseEntity<ProdutoDTO> findProdutoById(@PathVariable Long id) throws ExceptionLojaVirtual {


        return new ResponseEntity<ProdutoDTO>(produtoService.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/deleteProdutoById/{id}")
    public ResponseEntity<?> deleteProdutoById(@PathVariable Long id) throws ExceptionLojaVirtual {

        produtoService.deleteProdutoById(id);

        return new ResponseEntity<>("Produto deletado", HttpStatus.OK);
    }

    @PutMapping("/updateProduto")
    public ResponseEntity<ProdutoDTO> updateProduto(@RequestBody ProdutoDTO produto) throws ExceptionLojaVirtual {


        return new ResponseEntity<ProdutoDTO>(produtoService.updateProduto(produto), HttpStatus.OK);
    }


}
