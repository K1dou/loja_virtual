package com.K1dou.Loja.virtual.model.Dtos;

import com.K1dou.Loja.virtual.model.Acesso;

import java.util.Date;
import java.util.List;

public record UserRegister(String login, String password, Date dataAtualSenha,Long idDaPessoa, List<Acesso> acessos) {
}
