package com.K1dou.Loja.virtual.service;

import com.K1dou.Loja.virtual.exceptions.ExceptionLojaVirtual;
import com.K1dou.Loja.virtual.model.CategoriaProduto;
import com.K1dou.Loja.virtual.model.Dtos.CategoriaProdutoDTO;
import com.K1dou.Loja.virtual.model.PessoaJuridica;
import com.K1dou.Loja.virtual.model.Produto;
import com.K1dou.Loja.virtual.repository.CategoriaProdutoRepository;
import com.K1dou.Loja.virtual.repository.PessoaJuridicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaProdutoService {

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    @Autowired
    private CategoriaProdutoRepository categoriaProdutoRepository;

    public CategoriaProdutoDTO salvarCategoria(CategoriaProdutoDTO dto) {
        PessoaJuridica pessoaJuridica = pessoaJuridicaRepository.findById(dto.idEmpresa()).get();

        CategoriaProduto categoriaProduto = new CategoriaProduto();
        categoriaProduto.setEmpresa(pessoaJuridica);
        categoriaProduto.setNomeDesc(dto.nomeDesc());
        categoriaProdutoRepository.save(categoriaProduto);

        return dto;
    }

    public CategoriaProdutoDTO findById(Long id) {

        CategoriaProduto categoriaProduto = categoriaProdutoRepository.findById(id).get();
        CategoriaProdutoDTO categoriaProdutoDTO = new CategoriaProdutoDTO(categoriaProduto.getId(), categoriaProduto.getNomeDesc(), categoriaProduto.getEmpresa().getId());
        return categoriaProdutoDTO;
    }

    public void deleteById(Long id) {
        categoriaProdutoRepository.deleteById(id);
    }

    public List<CategoriaProduto> findAll() {
        List<CategoriaProduto> categoriaProduto = categoriaProdutoRepository.findAll();
        return categoriaProduto;
    }

    public CategoriaProdutoDTO updateCategoriaProduto(CategoriaProdutoDTO categoriaProdutoDTO) throws ExceptionLojaVirtual {
        CategoriaProduto categoriaProduto = categoriaProdutoRepository.findById(categoriaProdutoDTO.id()).orElseThrow(() -> new ExceptionLojaVirtual("Id Categoria Produto invalido"));
        categoriaProduto.setNomeDesc(categoriaProdutoDTO.nomeDesc());
        PessoaJuridica pessoaJuridica = pessoaJuridicaRepository.findById(categoriaProdutoDTO.idEmpresa()).orElseThrow(() -> new ExceptionLojaVirtual("Id da empresa invalido"));
        categoriaProduto.setEmpresa(pessoaJuridica);
        categoriaProdutoRepository.save(categoriaProduto);

        CategoriaProdutoDTO categoriaProdutoDTO1 = new CategoriaProdutoDTO(categoriaProduto.getId(), categoriaProduto.getNomeDesc(), categoriaProduto.getEmpresa().getId());
        return categoriaProdutoDTO1;
    }

}
