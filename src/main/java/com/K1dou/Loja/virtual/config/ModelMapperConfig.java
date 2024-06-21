package com.K1dou.Loja.virtual.config;

import com.K1dou.Loja.virtual.model.Dtos.ImagemProdutoRetornoDTO;
import com.K1dou.Loja.virtual.model.ImagemProduto;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {


    @Bean
    public ModelMapper getModel(){

        return new ModelMapper();
    }



}
