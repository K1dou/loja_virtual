package com.K1dou.Loja.virtual.service;

import com.K1dou.Loja.virtual.model.Acesso;
import com.K1dou.Loja.virtual.repository.AcessoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AcessoService {

    @Autowired
    private AcessoRepository acessoRepository;


    public Acesso save (Acesso acesso){

      return  acessoRepository.save(acesso);

    }




}
