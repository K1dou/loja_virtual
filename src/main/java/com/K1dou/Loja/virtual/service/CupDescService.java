package com.K1dou.Loja.virtual.service;

import com.K1dou.Loja.virtual.model.CupDesc;
import com.K1dou.Loja.virtual.model.Dtos.CupDescDTO;
import com.K1dou.Loja.virtual.repository.CupDescRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CupDescService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CupDescRepository cupDescRepository;

    public CupDesc salvaCupDesc(CupDesc cupDesc){

        cupDescRepository.save(cupDesc);
        return cupDesc;
    }

    public List<CupDescDTO> consultaCupPorEmpresa(Long empresaId){
        modelMapper.typeMap(CupDesc.class, CupDescDTO.class).addMapping(item->item.getEmpresa().getId(),(dest,v)->dest.setEmpresaId((Long) v));

        List<CupDesc>cupDescs = cupDescRepository.cupDescPorEmpresa(empresaId);
        List<CupDescDTO>cupDescDTOS = cupDescs.stream().map(item->modelMapper.map(item, CupDescDTO.class)).collect(Collectors.toList());

        return cupDescDTOS;
    }

    public List<CupDescDTO>allCupDesc(){
        List<CupDesc> cupDescs = cupDescRepository.findAll();
        List<CupDescDTO>cupDescDTOS = cupDescs.stream().map(item->modelMapper.map(item, CupDescDTO.class)).collect(Collectors.toList());

        return cupDescDTOS;
    }

}
