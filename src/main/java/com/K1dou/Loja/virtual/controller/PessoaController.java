package com.K1dou.Loja.virtual.controller;

import com.K1dou.Loja.virtual.enums.TipoPessoa;
import com.K1dou.Loja.virtual.exceptions.ExceptionLojaVirtual;
import com.K1dou.Loja.virtual.model.Dtos.CepDTO;
import com.K1dou.Loja.virtual.model.Dtos.ConsultaCnpjDTO;
import com.K1dou.Loja.virtual.model.Endereco;
import com.K1dou.Loja.virtual.model.PessoaFisica;
import com.K1dou.Loja.virtual.model.PessoaJuridica;
import com.K1dou.Loja.virtual.repository.EnderecoRepository;
import com.K1dou.Loja.virtual.repository.PessoaFisicaRepository;
import com.K1dou.Loja.virtual.repository.PessoaJuridicaRepository;
import com.K1dou.Loja.virtual.service.PessoaUserService;
import com.K1dou.Loja.virtual.util.ValidaCNPJ;
import com.K1dou.Loja.virtual.util.ValidarCPF;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {


    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    @Autowired
    private PessoaUserService pessoaUserService;
    @Autowired
    private EnderecoRepository enderecoRepository;


    @GetMapping("/consultaCnpjReceitaWs/{cnpj}")
    public ResponseEntity<ConsultaCnpjDTO> dto(@PathVariable String cnpj) {
        return new ResponseEntity<ConsultaCnpjDTO>(pessoaUserService.consultaCnpjReceitaWS(cnpj), HttpStatus.OK);
    }

    @GetMapping("/consultaPjNome/{nome}")
    public ResponseEntity<List<PessoaJuridica>> findBynomePj(@PathVariable String nome) {
        List<PessoaJuridica> pessoaJuridicas = pessoaJuridicaRepository.findByNome(nome);
        return new ResponseEntity<List<PessoaJuridica>>(pessoaJuridicas, HttpStatus.FOUND);
    }

    @GetMapping("/consultaPjCnpj/{cnpj}")
    public ResponseEntity<PessoaJuridica> findByCnpj(@PathVariable String cnpj) {
        PessoaJuridica pessoaJuridica = pessoaJuridicaRepository.existeCnpjCadastrado(cnpj);

        return ResponseEntity.ok().body(pessoaJuridica);
    }


    @GetMapping("/consultaPfNome/{nome}")
    public ResponseEntity<List<PessoaFisica>> findBynomePf(@PathVariable String nome) {
        List<PessoaFisica> pessoaFisicas = pessoaFisicaRepository.findBynomes(nome);
        return new ResponseEntity<List<PessoaFisica>>(pessoaFisicas, HttpStatus.FOUND);
    }

    @GetMapping("/consultaPfCpf/{cpf}")
    public ResponseEntity<PessoaFisica> findByCpf(@PathVariable String cpf) {
        PessoaFisica pessoaFisica = pessoaFisicaRepository.pesquisaPorCpf(cpf);
        return new ResponseEntity<PessoaFisica>(pessoaFisica, HttpStatus.FOUND);
    }


    @GetMapping("/consultaCep/{cep}")
    public ResponseEntity<CepDTO> consultaCep(@PathVariable String cep) {
        CepDTO cepDTO = pessoaUserService.consultaCep(cep);

        return new ResponseEntity<CepDTO>(cepDTO, HttpStatus.OK);
    }


    @PostMapping("/CadastroPessoaJuridica")
    public ResponseEntity<PessoaJuridica> cadastroPessoaJuridica(@RequestBody @Valid PessoaJuridica pessoaJuridica) throws ExceptionLojaVirtual, MessagingException, UnsupportedEncodingException {


        if (pessoaJuridica.getTipoPessoa() == null) {
            throw new ExceptionLojaVirtual("Informe o tipo Juridico ou Fornecedor da Loja");
        }


        if (pessoaJuridica == null) {
            throw new ExceptionLojaVirtual("Pessoa juridica não pode ser NULL");
        }
        if (pessoaJuridica.getId() == null && pessoaJuridicaRepository.existeCnpjCadastrado(pessoaJuridica.getCnpj()) != null) {
            throw new ExceptionLojaVirtual("Ja existe CNPJ cadastrado com o numero: " + pessoaJuridica.getCnpj());
        }
        if (pessoaJuridica.getId() == null && pessoaJuridicaRepository.existeInsEstadualCadastrado(pessoaJuridica.getInscEstadual()) != null) {
            throw new ExceptionLojaVirtual("Ja existe Inscrição Estadual cadastrado com o numero: " + pessoaJuridica.getInscEstadual());
        }
        if (!ValidaCNPJ.isCNPJ(pessoaJuridica.getCnpj())) {
            throw new ExceptionLojaVirtual("CNPJ: " + pessoaJuridica.getCnpj() + " está invalido.");
        }

        if (pessoaJuridica.getId() == null || pessoaJuridica.getId() <= 0) {
            for (int i = 0; i < pessoaJuridica.getEnderecos().size(); i++) {
                CepDTO cepDTO = pessoaUserService.consultaCep(pessoaJuridica.getEnderecos().get(i).getCep());
                pessoaJuridica.getEnderecos().get(i).setBairro(cepDTO.bairro());
                pessoaJuridica.getEnderecos().get(i).setCidade(cepDTO.localidade());
                pessoaJuridica.getEnderecos().get(i).setComplemento(cepDTO.complemento());
                pessoaJuridica.getEnderecos().get(i).setRuaLogra(cepDTO.logradouro());
                pessoaJuridica.getEnderecos().get(i).setUf(cepDTO.uf());
            }
        } else {
            for (int i = 0; i < pessoaJuridica.getEnderecos().size(); i++) {
                Endereco enderecoTemp = enderecoRepository.findById(pessoaJuridica.getEnderecos().get(i).getId()).get();

                if (!enderecoTemp.getCep().equals(pessoaJuridica.getEnderecos().get(i).getCep())) {

                    CepDTO cepDTO = pessoaUserService.consultaCep(pessoaJuridica.getEnderecos().get(i).getCep());
                    pessoaJuridica.getEnderecos().get(i).setBairro(cepDTO.bairro());
                    pessoaJuridica.getEnderecos().get(i).setCidade(cepDTO.localidade());
                    pessoaJuridica.getEnderecos().get(i).setComplemento(cepDTO.complemento());
                    pessoaJuridica.getEnderecos().get(i).setRuaLogra(cepDTO.logradouro());
                    pessoaJuridica.getEnderecos().get(i).setUf(cepDTO.uf());
                }
            }

        }


        pessoaJuridica = pessoaUserService.salvarPessoaJuridica(pessoaJuridica);


        return new ResponseEntity<PessoaJuridica>(pessoaJuridica, HttpStatus.OK);
    }


    @PostMapping("/CadastroPessoaFisica")
    public ResponseEntity<PessoaFisica> cadastroPessoaFisica(@RequestBody @Valid PessoaFisica pessoaFisica) throws ExceptionLojaVirtual, MessagingException, UnsupportedEncodingException {

        if (pessoaFisica.getTipoPessoa() == null) {
            pessoaFisica.setTipoPessoa(TipoPessoa.FISICA.name());
        }

        if (pessoaFisica == null) {
            throw new ExceptionLojaVirtual("Pessoa física não pode ser NULL");
        }
        if (pessoaFisica.getId() == null && pessoaFisicaRepository.pesquisaPorCpf(pessoaFisica.getCpf()) != null) {
            throw new ExceptionLojaVirtual("Ja existe CPF cadastrado com o numero: " + pessoaFisica.getCpf());
        }

        if (!ValidarCPF.isCPF(pessoaFisica.getCpf())) {
            throw new ExceptionLojaVirtual("CPF : " + pessoaFisica.getCpf() + " está invalido.");
        }

        pessoaFisica = pessoaUserService.salvarPessoaFisica(pessoaFisica);

        return new ResponseEntity<PessoaFisica>(pessoaFisica, HttpStatus.OK);
    }


}
