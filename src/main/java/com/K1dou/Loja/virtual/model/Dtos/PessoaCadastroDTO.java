package com.K1dou.Loja.virtual.model.Dtos;

import com.K1dou.Loja.virtual.model.Endereco;

import java.util.Date;
import java.util.List;

public record PessoaCadastroDTO(String nome, String email, String telefone, String tipoPessoa, String cpf,
                                Date dataNascimento) {
}
