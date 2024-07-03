package com.K1dou.Loja.virtual.service;

import com.K1dou.Loja.virtual.exceptions.ExceptionLojaVirtual;
import com.K1dou.Loja.virtual.model.StatusRastreio;
import com.K1dou.Loja.virtual.repository.StatusRastreioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusRastreioService {

    @Autowired
    private StatusRastreioRepository statusRastreioRepository;


   public List<StatusRastreio> listaRastreioVenda(Long idVenda) throws ExceptionLojaVirtual {

       List<StatusRastreio> statusRastreios = statusRastreioRepository.listaRastreioVenda(idVenda);

       if (statusRastreios.isEmpty()){
           throw new ExceptionLojaVirtual("Nenhum Status de rastreio encontrado");
       }
       return statusRastreios;
   }






}
