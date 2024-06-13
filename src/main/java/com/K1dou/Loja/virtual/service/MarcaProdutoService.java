package com.K1dou.Loja.virtual.service;

import com.K1dou.Loja.virtual.exceptions.ExceptionLojaVirtual;
import com.K1dou.Loja.virtual.model.MarcaProduto;
import com.K1dou.Loja.virtual.model.PessoaJuridica;
import com.K1dou.Loja.virtual.repository.MarcaProdutoRepository;
import com.K1dou.Loja.virtual.repository.PessoaJuridicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class MarcaProdutoService {

    @Autowired
    private MarcaProdutoRepository marcaProdutoRepository;


    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;


    public MarcaProduto salvarMarcaProduto(MarcaProduto marcaProduto) throws ExceptionLojaVirtual {
        MarcaProduto marcaProduto1 = marcaProduto;
        PessoaJuridica pessoaJuridica= pessoaJuridicaRepository.findById(marcaProduto.getEmpresa().getId()).orElseThrow(()->new ExceptionLojaVirtual("Id da empresa é invalido ou não existe"));
        marcaProduto1.setEmpresa(pessoaJuridica);
        marcaProdutoRepository.save(marcaProduto1);
        return marcaProduto1;
    }

    public List<MarcaProduto> findAllMarcas (){

        List<MarcaProduto>marcaProdutos = marcaProdutoRepository.findAll();
        return marcaProdutos;

    }

    public MarcaProduto findById(Long id) throws ExceptionLojaVirtual {

        MarcaProduto marcaProduto = marcaProdutoRepository.findById(id).orElseThrow(()->new ExceptionLojaVirtual("Id da marca produto invalido"));
        return marcaProduto;

    }

    public List<MarcaProduto> findByDescri(String desc){
        List<MarcaProduto>marcaProdutos = marcaProdutoRepository.findAllDesc(desc);
        return marcaProdutos;

    }

    public void deleteMarcaProduto(Long id){
        marcaProdutoRepository.deleteById(id);
    }

    public MarcaProduto updateMarcaProduto(MarcaProduto dto){
        MarcaProduto marcaProduto = dto;
        PessoaJuridica pessoaJuridica = pessoaJuridicaRepository.findById(dto.getEmpresa().getId()).get();
        marcaProduto.setEmpresa(pessoaJuridica);
        marcaProdutoRepository.save(marcaProduto);
        return marcaProduto;
    }



}
