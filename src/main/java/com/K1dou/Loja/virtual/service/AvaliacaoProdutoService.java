package com.K1dou.Loja.virtual.service;

import com.K1dou.Loja.virtual.exceptions.ExceptionLojaVirtual;
import com.K1dou.Loja.virtual.model.AvaliacaoProduto;
import com.K1dou.Loja.virtual.model.Dtos.AvaliacaoProdutoDTO;
import com.K1dou.Loja.virtual.model.PessoaFisica;
import com.K1dou.Loja.virtual.model.PessoaJuridica;
import com.K1dou.Loja.virtual.model.Produto;
import com.K1dou.Loja.virtual.repository.AvaliacaoProdutoRepository;
import com.K1dou.Loja.virtual.repository.PessoaFisicaRepository;
import com.K1dou.Loja.virtual.repository.PessoaJuridicaRepository;
import com.K1dou.Loja.virtual.repository.ProdutoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvaliacaoProdutoService {

    @Autowired
    private AvaliacaoProdutoRepository avaliacaoProdutoRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;
    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;


    public AvaliacaoProdutoDTO salvaAvaliacaoProduto(AvaliacaoProdutoDTO avaliacaoProdutoDTO) throws ExceptionLojaVirtual {
        //validar pessoa produto e empresa

        PessoaFisica pessoaFisica = pessoaFisicaRepository.findById(avaliacaoProdutoDTO.getPessoa().getId()).orElseThrow(() -> new ExceptionLojaVirtual("Id da Pessoa está invalido ou não existe"));
        PessoaJuridica pessoaJuridica = pessoaJuridicaRepository.findById(avaliacaoProdutoDTO.getEmpresa().getId()).orElseThrow(() -> new ExceptionLojaVirtual("Id da Empresa está invalido ou não existe"));
        Produto produto = produtoRepository.findById(avaliacaoProdutoDTO.getProduto().getId()).orElseThrow(() -> new ExceptionLojaVirtual("Id do Produto está invalido ou não existe"));


        AvaliacaoProduto avaliacaoProduto = modelMapper.map(avaliacaoProdutoDTO, AvaliacaoProduto.class);
        avaliacaoProdutoRepository.save(avaliacaoProduto);

        return modelMapper.map(avaliacaoProduto, AvaliacaoProdutoDTO.class);
    }


    public List<AvaliacaoProdutoDTO> buscaAvaliacaoProdutoByIdProduto(Long idProduto) throws ExceptionLojaVirtual {

        List<AvaliacaoProduto> avaliacaoProdutos = avaliacaoProdutoRepository.buscaAvaliacaoProdutoByIdProduto(idProduto);
        if (avaliacaoProdutos.isEmpty()) {
            throw new ExceptionLojaVirtual("Nenhuma avaliação encontrada");
        }
        List<AvaliacaoProdutoDTO> avaliacaoProdutoDTOS = avaliacaoProdutos.stream().map(item -> modelMapper.map(item, AvaliacaoProdutoDTO.class)).collect(Collectors.toList());

        return avaliacaoProdutoDTOS;
    }

    public List<AvaliacaoProdutoDTO> buscaAvaliacaoProdutoByIdPessoa(Long idPessoa) throws ExceptionLojaVirtual {

        List<AvaliacaoProduto> avaliacaoProdutos = avaliacaoProdutoRepository.buscaAvaliacaoProdutoByIdPessoa(idPessoa);
        if (avaliacaoProdutos.isEmpty()) {
            throw new ExceptionLojaVirtual("Nenhuma avaliação encontrada");
        }
        List<AvaliacaoProdutoDTO> avaliacaoProdutoDTOS = avaliacaoProdutos.stream().map(item -> modelMapper.map(item, AvaliacaoProdutoDTO.class)).collect(Collectors.toList());

        return avaliacaoProdutoDTOS;
    }

    public List<AvaliacaoProdutoDTO> buscaAvaliacaoProdutoByIdPessoaAndIdProduto(Long idPessoa, Long idProduto) throws ExceptionLojaVirtual {
        List<AvaliacaoProduto> avaliacaoProdutos = avaliacaoProdutoRepository.buscaAvaliacaoProdutoByIdPessoaAndIdProduto(idPessoa, idProduto);
        if (avaliacaoProdutos.isEmpty()) {
            throw new ExceptionLojaVirtual("Nenhuma avaliação encontrada");
        }
        List<AvaliacaoProdutoDTO> avaliacaoProdutoDTOS = avaliacaoProdutos.stream().map(item -> modelMapper.map(item, AvaliacaoProdutoDTO.class)).collect(Collectors.toList());

        return avaliacaoProdutoDTOS;
    }

    public void deleteAvaliacaoProdutoById(Long id) throws ExceptionLojaVirtual {
        AvaliacaoProduto avaliacaoProduto = avaliacaoProdutoRepository.findById(id).orElseThrow(() -> new ExceptionLojaVirtual("Id avalição produto está invalido ou não existe"));
        avaliacaoProdutoRepository.deleteById(id);
    }

    public AvaliacaoProdutoDTO updateAvaliacaoProduto(AvaliacaoProdutoDTO avaliacaoProdutoDTO) throws ExceptionLojaVirtual {
        AvaliacaoProduto avaliacaoProdutoo = avaliacaoProdutoRepository.findById(avaliacaoProdutoDTO.getId()).orElseThrow(() -> new ExceptionLojaVirtual("Id da avaliação do produto está invalida ou não existe"));

        AvaliacaoProduto avaliacaoProduto = modelMapper.map(avaliacaoProdutoDTO, AvaliacaoProduto.class);
        avaliacaoProdutoRepository.save(avaliacaoProduto);

        return modelMapper.map(avaliacaoProduto, AvaliacaoProdutoDTO.class);
    }

}
