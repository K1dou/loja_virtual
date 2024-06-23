package com.K1dou.Loja.virtual.service;

import com.K1dou.Loja.virtual.model.Dtos.FormaPagamentoDTO;
import com.K1dou.Loja.virtual.model.FormaPagamento;
import com.K1dou.Loja.virtual.repository.FormaPagamentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormaPagamentoService {

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;
    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamentoDTO salvaFormaPagamento(FormaPagamentoDTO dto){
        FormaPagamento formaPagamento = modelMapper.map(dto, FormaPagamento.class);

        formaPagamentoRepository.save(formaPagamento);

        return modelMapper.map(formaPagamento,FormaPagamentoDTO.class);
    }

}
