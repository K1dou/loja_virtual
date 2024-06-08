package com.K1dou.Loja.virtual.model.Dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public record ConsultaCnpjDTO(List<AtividadeDTO>atividade_principal,String data_situacao,String tipo, String nome,String uf,String telefone, String email,
                              List<AtividadeDTO>atividades_secundarias, List<QsaDTO>qsa,String situacao,String bairro,String logradouro, String numero, String cep,String municipio, String porte,String abertura,String natureza_juridica,
                              String fantasia,String cnpj,String ultima_atualizacao,String status,String complemento,String efr, String motivo_situacao,String situacao_especial,String data_situacao_especial,
                              String capital_social, @JsonIgnore ExtraDTO extra,Billing billing) {
}
