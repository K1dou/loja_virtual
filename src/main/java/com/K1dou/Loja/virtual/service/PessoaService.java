package com.K1dou.Loja.virtual.service;

import com.K1dou.Loja.virtual.model.Dtos.PessoaCadastroDTO;
import com.K1dou.Loja.virtual.model.Pessoa;
import com.K1dou.Loja.virtual.model.PessoaFisica;
import com.K1dou.Loja.virtual.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public PessoaCadastroDTO cadastroPessoaFisica(PessoaCadastroDTO dto){

        Pessoa pessoa = new PessoaFisica(dto.nome(),dto.email(),dto.telefone(), dto.tipoPessoa(),dto.cpf(),dto.dataNascimento());

        pessoaRepository.save(pessoa);

        return dto;

    }

}
