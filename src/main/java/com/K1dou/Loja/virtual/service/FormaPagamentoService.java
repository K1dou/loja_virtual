package com.K1dou.Loja.virtual.service;

import com.K1dou.Loja.virtual.model.Dtos.FormaPagamentoDTO;
import com.K1dou.Loja.virtual.model.Dtos.NotaFiscalVendaDTO;
import com.K1dou.Loja.virtual.model.FormaPagamento;
import com.K1dou.Loja.virtual.model.NotaFiscalVenda;
import com.K1dou.Loja.virtual.repository.FormaPagamentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FormaPagamentoService {

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;
    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamento salvaFormaPagamento(FormaPagamento dto) {
        FormaPagamento formaPagamento = modelMapper.map(dto, FormaPagamento.class);

        formaPagamentoRepository.save(formaPagamento);

        return formaPagamento;
    }

    public List<FormaPagamentoDTO> formaPagamentoPorEmpresaId(Long empresaId) {
        modelMapper.typeMap(FormaPagamento.class, FormaPagamentoDTO.class).addMapping(item->item.getEmpresa().getId(),(dest, v)->dest.setEmpresaId((Long) v));

        List<FormaPagamento> formaPagamentos = formaPagamentoRepository.formaPagamentoPorEmpresaId(empresaId);
        List<FormaPagamentoDTO>formaPagamentoDTOS=formaPagamentos.stream().map(item->modelMapper.map(item,FormaPagamentoDTO.class)).collect(Collectors.toList());
        return formaPagamentoDTOS;
    }

    public List<FormaPagamentoDTO> allFormaPagamentos() {
        modelMapper.typeMap(FormaPagamento.class, FormaPagamentoDTO.class).addMapping(item->item.getEmpresa().getId(),(dest, v)->dest.setEmpresaId((Long) v));

        List<FormaPagamento> formaPagamentos = formaPagamentoRepository.findAll();
        List<FormaPagamentoDTO>formaPagamentoDTOS=formaPagamentos.stream().map(item->modelMapper.map(item,FormaPagamentoDTO.class)).collect(Collectors.toList());
        return formaPagamentoDTOS;
    }

}
