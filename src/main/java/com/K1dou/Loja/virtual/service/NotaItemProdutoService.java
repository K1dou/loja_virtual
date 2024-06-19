package com.K1dou.Loja.virtual.service;

import com.K1dou.Loja.virtual.exceptions.ExceptionLojaVirtual;
import com.K1dou.Loja.virtual.model.Dtos.NotaItemProdutoDTO;
import com.K1dou.Loja.virtual.model.NotaFiscalCompra;
import com.K1dou.Loja.virtual.model.NotaItemProduto;
import com.K1dou.Loja.virtual.model.PessoaJuridica;
import com.K1dou.Loja.virtual.model.Produto;
import com.K1dou.Loja.virtual.repository.NotaFiscalCompraRepository;
import com.K1dou.Loja.virtual.repository.NotaItemProdutoRepository;
import com.K1dou.Loja.virtual.repository.PessoaJuridicaRepository;
import com.K1dou.Loja.virtual.repository.ProdutoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotaItemProdutoService {

    @Autowired
    private NotaItemProdutoRepository notaItemProdutoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private NotaFiscalCompraRepository notaFiscalCompraRepository;

    public NotaItemProdutoDTO cadastrarNotaItemProduto(NotaItemProdutoDTO dto) throws ExceptionLojaVirtual {

        NotaFiscalCompra notaFiscalCompra = notaFiscalCompraRepository.findById(dto.getNotaFiscalCompra().getId()).orElseThrow(() -> new ExceptionLojaVirtual("Id da nota fiscal esta invalido ou não existe.(" + dto.getNotaFiscalCompra().getId() + ")"));
        Produto produto = produtoRepository.findById(dto.getProduto().getId()).orElseThrow(() -> new ExceptionLojaVirtual("Id do produto esta invalido ou não existe.(" + dto.getProduto().getId() + ")"));
        PessoaJuridica pessoaJuridica = pessoaJuridicaRepository.findById(dto.getPessoa().getId()).orElseThrow(() -> new ExceptionLojaVirtual("Id da empresa esta invalido ou não existe.(" + dto.getPessoa().getId() + ")"));


        NotaItemProduto notaItemProduto = modelMapper.map(dto, NotaItemProduto.class);
        notaItemProdutoRepository.save(notaItemProduto);
        NotaItemProdutoDTO notaItemProdutoDTO = modelMapper.map(notaItemProduto, NotaItemProdutoDTO.class);
        return notaItemProdutoDTO;
    }

    public List<NotaItemProdutoDTO> findAll() {
        List<NotaItemProduto> itemProdutos = notaItemProdutoRepository.findAll();
        List<NotaItemProdutoDTO> notaItemProdutoDTOS = itemProdutos.stream()
                .map(item -> modelMapper.map(item, NotaItemProdutoDTO.class))
                .collect(Collectors.toList());
        return notaItemProdutoDTOS;
    }

    public NotaItemProdutoDTO findById(Long id) throws ExceptionLojaVirtual {
        NotaItemProduto notaItemProduto = notaItemProdutoRepository.findById(id).orElseThrow(() -> new ExceptionLojaVirtual("Id da nota item produto está invalido ou não existe"));
        return modelMapper.map(notaItemProduto, NotaItemProdutoDTO.class);
    }

    public NotaItemProdutoDTO update(NotaItemProdutoDTO dto) throws ExceptionLojaVirtual {
        NotaItemProduto notaItemProduto = notaItemProdutoRepository.findById(dto.getId()).orElseThrow(() -> new ExceptionLojaVirtual("O Id da nota Item Produto está invalido ou não existe"));

        modelMapper.map(dto, notaItemProduto);
        notaItemProdutoRepository.save(notaItemProduto);
        return modelMapper.map(notaItemProduto, NotaItemProdutoDTO.class);
    }

    public void deleteNotaItemProdutoById(Long id) throws ExceptionLojaVirtual {
        NotaItemProduto notaItemProduto = notaItemProdutoRepository.findById(id).orElseThrow(() -> new ExceptionLojaVirtual("O Id da nota Item Produto está invalido ou não existe"));
        notaItemProdutoRepository.deleteById(id);
    }


}
