package com.K1dou.Loja.virtual.service;

import com.K1dou.Loja.virtual.model.Dtos.NotaFiscalVendaDTO;
import com.K1dou.Loja.virtual.model.NotaFiscalVenda;
import com.K1dou.Loja.virtual.repository.NotaFiscalVendaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotaFiscalVendaService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private NotaFiscalVendaRepository notaFiscalVendaRepository;

    public List<NotaFiscalVendaDTO> consultaNotaFiscalVendaPorVenda(Long idVenda){
        modelMapper.typeMap(NotaFiscalVenda.class, NotaFiscalVendaDTO.class).addMapping(item->item.getEmpresa().getId(),(dest,v)->dest.setEmpresaId((Long) v));
        modelMapper.typeMap(NotaFiscalVenda.class, NotaFiscalVendaDTO.class).addMapping(item->item.getVendaCompraLojaVirtual().getId(),(dest,v)->dest.setVendaCompraLojaVirtualId((Long) v));
        List<NotaFiscalVenda> notaFiscalVendas = notaFiscalVendaRepository.consultaPorVenda(idVenda);
        List<NotaFiscalVendaDTO>notaFiscalVendaDTO = notaFiscalVendas.stream().map(item->modelMapper.map(item, NotaFiscalVendaDTO.class)).collect(Collectors.toList());

        return notaFiscalVendaDTO;
    }


}
