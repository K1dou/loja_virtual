package com.K1dou.Loja.virtual.service;


import com.K1dou.Loja.virtual.exceptions.ExceptionLojaVirtual;
import com.K1dou.Loja.virtual.model.*;
import com.K1dou.Loja.virtual.model.Dtos.ProdutoDTO;
import com.K1dou.Loja.virtual.repository.*;
import jakarta.xml.bind.DatatypeConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ImagemProdutoRepository imagemProdutoRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;
    @Autowired
    private CategoriaProdutoRepository categoriaProdutoRepository;

    @Autowired
    private MarcaProdutoRepository marcaProdutoRepository;
    @Autowired
    private NotaItemProdutoRepository notaItemProdutoRepository;

    public ProdutoDTO cadastrarProduto(ProdutoDTO dto) throws ExceptionLojaVirtual, IOException {
        Produto produto = new Produto();
        MarcaProduto marcaProduto = marcaProdutoRepository.findById(dto.getMarcaProduto().getId()).orElseThrow(() -> new ExceptionLojaVirtual("Id da marca produto invalido"));
        PessoaJuridica empresa = pessoaJuridicaRepository.findById(dto.getEmpresa().getId()).orElseThrow(() -> new ExceptionLojaVirtual("Id da empresa invalido"));
        CategoriaProduto categoriaProduto = categoriaProdutoRepository.findById(dto.getCategoriaProduto().getId()).orElseThrow(() -> new ExceptionLojaVirtual("Id da categoria do produto invalido"));
        modelMapper.map(dto, produto);

        produto.setImagens(null);

        produtoRepository.save(produto);

        PessoaJuridica pessoaJuridica = pessoaJuridicaRepository.findById(dto.getEmpresa().getId()).get();

        for (ImagemProduto i : dto.getImagens()) {
            i.setProduto(produto);
            i.setEmpresa(pessoaJuridica);

            String base64Image = "";

            if (i.getImagemOriginal().contains("data:image")) {
                base64Image = i.getImagemOriginal().split(",")[1];
            } else {
                base64Image = i.getImagemOriginal();
            }
            byte[] imageBytes = DatatypeConverter.parseBase64Binary(base64Image);

            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
            if (bufferedImage != null) {
                int type = bufferedImage.getType() == 0 ? bufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
                int largura = Integer.parseInt("800");
                int altura = Integer.parseInt("600");

                BufferedImage resizedImage = new BufferedImage(largura, altura, type);
                Graphics2D g = resizedImage.createGraphics();
                g.drawImage(bufferedImage, 0, 0, largura, altura, null);
                g.dispose();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(resizedImage, "png", baos);
                String miniImgBase64 = "data:image/png;base64," + DatatypeConverter.printBase64Binary(baos.toByteArray());
                i.setImagemMiniatura(miniImgBase64);

                ImagemProduto imagemProduto = modelMapper.map(i, ImagemProduto.class);

                imagemProduto.setProduto(produto);
                imagemProdutoRepository.save(imagemProduto);

                bufferedImage.flush();
                resizedImage.flush();
                baos.flush();
                baos.close();

            }

        }
        return modelMapper.map(produto, ProdutoDTO.class);
    }


    public List<ProdutoDTO> findAllProduto() {
        List<Produto> list = produtoRepository.findAll();
        List<ProdutoDTO> dtos = list.stream().map(item -> modelMapper.map(item, ProdutoDTO.class)).collect(Collectors.toList());
        return dtos;
    }

    public List<ProdutoDTO> findByNome(String nome) {
        List<Produto> nomes = produtoRepository.findByNome(nome);
        List<ProdutoDTO> dtos = nomes.stream().map(item -> modelMapper.map(item, ProdutoDTO.class)).collect(Collectors.toList());
        return dtos;
    }

    public ProdutoDTO findById(Long id) throws ExceptionLojaVirtual {
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new ExceptionLojaVirtual("Id do produto é invalido"));

        return modelMapper.map(produto, ProdutoDTO.class);
    }

    public void deleteProdutoById(Long id) throws ExceptionLojaVirtual {


        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new ExceptionLojaVirtual("O id do produto é invalido ou não existe"));
        produtoRepository.deleteNotaItemProdutoVinculadaProduto(id);
        produtoRepository.deleteById(id);
    }

    public ProdutoDTO updateProduto(ProdutoDTO produto) throws ExceptionLojaVirtual {
        Produto produtoo = produtoRepository.findById(produto.getId()).orElseThrow(() -> new ExceptionLojaVirtual("O id do produto é invalido ou não existe"));
        modelMapper.map(produto, produtoo);

        produtoRepository.save(produtoo);
        return modelMapper.map(produtoo, ProdutoDTO.class);
    }


}

