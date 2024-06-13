package com.K1dou.Loja.virtual.service;

import com.K1dou.Loja.virtual.exceptions.ExceptionLojaVirtual;
import com.K1dou.Loja.virtual.model.ContaPagar;
import com.K1dou.Loja.virtual.model.Pessoa;
import com.K1dou.Loja.virtual.model.PessoaFisica;
import com.K1dou.Loja.virtual.model.PessoaJuridica;
import com.K1dou.Loja.virtual.repository.ContaPagarRepository;
import com.K1dou.Loja.virtual.repository.PessoaFisicaRepository;
import com.K1dou.Loja.virtual.repository.PessoaJuridicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContaPagarService {

    @Autowired
    private ContaPagarRepository contaPagarRepository;

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;
    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepositorya;

    public ContaPagar salvarContaPagar(ContaPagar contaPagar) throws ExceptionLojaVirtual {
        ContaPagar contaPagar1 = contaPagar;



        PessoaJuridica pessoaJuridica = pessoaJuridicaRepository.findById(contaPagar.getEmpresa().getId()).orElseThrow(() -> new ExceptionLojaVirtual("Id da empresa é invalido ou não existe"));
        PessoaFisica pessoaFisica= pessoaFisicaRepositorya.findById(contaPagar.getPessoa().getId()).orElseThrow(()->new ExceptionLojaVirtual("Id da pessoa invalido"));
        PessoaFisica pessoaFornecedor = pessoaFisicaRepositorya.findById(contaPagar.getPessoaFornecedor().getId()).orElseThrow(()->new ExceptionLojaVirtual("Id da pessoa fornecedora é invalido"));


        contaPagar1.setPessoaFornecedor(pessoaFornecedor);
        contaPagar1.setPessoa(pessoaFisica);
        contaPagar1.setEmpresa(pessoaJuridica);
        contaPagarRepository.save(contaPagar1);
        return contaPagar1;
    }

    public List<ContaPagar> findAllMarcas() {

        List<ContaPagar> contaPagar = contaPagarRepository.findAll();
        return contaPagar;

    }

    public ContaPagar findById(Long id) throws ExceptionLojaVirtual {

        ContaPagar contaPagar = contaPagarRepository.findById(id).orElseThrow(() -> new ExceptionLojaVirtual("Id da Conta a pagar invalido"));
        return contaPagar;

    }

    public List<ContaPagar> findByDescri(String desc) {
        List<ContaPagar> contaPagars = contaPagarRepository.findAllDesc(desc);
        return contaPagars;

    }

    public void deleteContaPagarById(Long id) {
        contaPagarRepository.deleteById(id);
    }

    public ContaPagar updateContaPagar(ContaPagar dto) {
        ContaPagar contaPagar = dto;
        PessoaJuridica pessoaJuridica = pessoaJuridicaRepository.findById(dto.getEmpresa().getId()).get();
        contaPagar.setEmpresa(pessoaJuridica);
        contaPagarRepository.save(contaPagar);
        return contaPagar;
    }


}
