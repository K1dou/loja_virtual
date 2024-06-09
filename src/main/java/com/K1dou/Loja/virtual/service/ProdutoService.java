package com.K1dou.Loja.virtual.service;


import com.K1dou.Loja.virtual.exceptions.ExceptionLojaVirtual;
import com.K1dou.Loja.virtual.model.CategoriaProduto;
import com.K1dou.Loja.virtual.model.Dtos.ProdutoDTO;
import com.K1dou.Loja.virtual.model.MarcaProduto;
import com.K1dou.Loja.virtual.model.PessoaJuridica;
import com.K1dou.Loja.virtual.model.Produto;
import com.K1dou.Loja.virtual.repository.CategoriaProdutoRepository;
import com.K1dou.Loja.virtual.repository.MarcaProdutoRepository;
import com.K1dou.Loja.virtual.repository.PessoaJuridicaRepository;
import com.K1dou.Loja.virtual.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;
    @Autowired
    private CategoriaProdutoRepository categoriaProdutoRepository;

    @Autowired
    private MarcaProdutoRepository marcaProdutoRepository;

    public ProdutoDTO cadastrarProduto(ProdutoDTO dto) throws ExceptionLojaVirtual {
        Produto produto = new Produto();

        MarcaProduto marcaProduto = marcaProdutoRepository.findById(dto.marcaProduto().getId()).orElseThrow(()->new ExceptionLojaVirtual("Id da marca produto invalido"));
        PessoaJuridica empresa = pessoaJuridicaRepository.findById(dto.empresa().getId()).orElseThrow(() -> new ExceptionLojaVirtual("Id da empresa invalido"));
        CategoriaProduto categoriaProduto =categoriaProdutoRepository.findById(dto.categoriaProduto().getId()).orElseThrow(()->new ExceptionLojaVirtual("Id da categoria do produto invalido"));
        produto.setCategoriaProduto(categoriaProduto);
        produto.setEmpresa(empresa);
        produto.setAltura(dto.altura());
        produto.setDescricao(dto.descricao());
        produto.setLargura(dto.largura());
        produto.setNome(dto.nome());
        produto.setProfundidade(dto.profundidade());
        produto.setTipoUnidade(dto.tipoUnidade());
        produto.setPeso(dto.peso());
        produto.setValorVenda(dto.valorVenda());
        produto.setQtdEstoque(dto.qtdEstoque());
        produto.setMarcaProduto(marcaProduto);
        produtoRepository.save(produto);

        ProdutoDTO produtoDTO = new ProdutoDTO(produto.getId(), produto.getTipoUnidade(), produto.getNome(), produto.getAtivo(), produto.getDescricao(), produto.getPeso(), produto.getLargura(), produto.getAltura(),produto.getCategoriaProduto(),produto.getMarcaProduto(), produto.getEmpresa(), produto.getProfundidade(), produto.getValorVenda(), produto.getQtdEstoque(), produto.getQtdeAlertaEstoque(), produto.getLinkYoutube(), produto.getAlertaQtdEstoque(), produto.getQtdClique());
        return produtoDTO;
    }


}
