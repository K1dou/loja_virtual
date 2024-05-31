package com.K1dou.Loja.virtual.model.Dtos;

import com.K1dou.Loja.virtual.model.Acesso;

import java.util.List;

public record UserRegister(String login, String password, List<Acesso> acessos) {
}
