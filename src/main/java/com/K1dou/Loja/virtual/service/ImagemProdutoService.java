package com.K1dou.Loja.virtual.service;

import com.K1dou.Loja.virtual.exceptions.ExceptionLojaVirtual;
import com.K1dou.Loja.virtual.model.Dtos.ImagemProdutoDTO;
import com.K1dou.Loja.virtual.model.Dtos.ImagemProdutoRetornoDTO;
import com.K1dou.Loja.virtual.model.ImagemProduto;
import com.K1dou.Loja.virtual.model.PessoaJuridica;
import com.K1dou.Loja.virtual.model.Produto;
import com.K1dou.Loja.virtual.repository.ImagemProdutoRepository;
import com.K1dou.Loja.virtual.repository.PessoaJuridicaRepository;
import com.K1dou.Loja.virtual.repository.ProdutoRepository;
import jakarta.xml.bind.DatatypeConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ImagemProdutoService {

    @Autowired
    private ImagemProdutoRepository imagemProdutoRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    public ImagemProdutoDTO salvaImagemProduto(ImagemProdutoDTO dto) throws ExceptionLojaVirtual, IOException {

        PessoaJuridica pessoaJuridica = pessoaJuridicaRepository.findById(dto.getEmpresa().getId()).orElseThrow(() -> new ExceptionLojaVirtual("Id da empresa invalido ou não existe"));
        Produto produto = produtoRepository.findById(dto.getProduto().getId()).orElseThrow(() -> new ExceptionLojaVirtual("Id do produto invalido ou não existe"));

        String base64Image = "";

        ImagemProduto imagemProduto = modelMapper.map(dto, ImagemProduto.class);

        if (dto.getImagemOriginal().contains("data:image")) {
            base64Image = dto.getImagemOriginal().split(",")[1];
        } else {
            base64Image = dto.getImagemOriginal();
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
            dto.setImagemMiniatura(miniImgBase64);

            imagemProduto = modelMapper.map(dto, ImagemProduto.class);
            imagemProdutoRepository.save(imagemProduto);


            bufferedImage.flush();
            resizedImage.flush();
            baos.flush();
            baos.close();

        }

        return modelMapper.map(imagemProduto, ImagemProdutoDTO.class);
    }

    public List<ImagemProdutoRetornoDTO> findImagensByProduto(Long idProduto) throws ExceptionLojaVirtual {

        modelMapper.typeMap(ImagemProduto.class, ImagemProdutoRetornoDTO.class).addMapping(src -> src.getProduto().getId(), (dest, v) -> dest.setProduto((Long) v));
        modelMapper.typeMap(ImagemProduto.class, ImagemProdutoRetornoDTO.class).addMapping(src -> src.getEmpresa().getId(), (dest, v) -> dest.setEmpresa((Long) v));

        List<ImagemProduto> imagemProduto = imagemProdutoRepository.findImagensByProduto(idProduto);
        List<ImagemProdutoRetornoDTO> imagemProdutoDTOS = imagemProduto.stream().map(item -> modelMapper.map(item, ImagemProdutoRetornoDTO.class)).collect(Collectors.toList());

        if (imagemProduto.isEmpty()) {
            throw new ExceptionLojaVirtual("Nenhum item encontrado");
        }
        return imagemProdutoDTOS;
    }

    public void deleteImagemProdutoByIdProduto(Long idProduto) throws ExceptionLojaVirtual {
        List<ImagemProduto> imagemProduto = imagemProdutoRepository.findImagensByProduto(idProduto);
        if (imagemProduto.isEmpty()) {
            throw new ExceptionLojaVirtual("Imagens ja foram apagadas ou não existem");
        }
        imagemProdutoRepository.deleteImagemProdutoByIdProduto(idProduto);
    }

    public void deleteImagemProdutoById(Long idImagemProduto) throws ExceptionLojaVirtual {
        ImagemProduto imagemProduto = imagemProdutoRepository.findById(idImagemProduto).orElseThrow(() -> new ExceptionLojaVirtual("Imagem ja foi apagada ou não existe"));

        imagemProdutoRepository.deleteById(idImagemProduto);
    }

    public ImagemProdutoRetornoDTO buscaImagemProdutoById(Long idImagemProduto) throws ExceptionLojaVirtual {
        ImagemProduto imagemProduto = imagemProdutoRepository.findById(idImagemProduto).orElseThrow(() -> new ExceptionLojaVirtual("Id invalido ou imagem não existe"));
        modelMapper.typeMap(ImagemProduto.class, ImagemProdutoRetornoDTO.class).addMapping(src -> src.getEmpresa().getId(), (dest, v) -> dest.setEmpresa((Long) v));
        modelMapper.typeMap(ImagemProduto.class, ImagemProdutoRetornoDTO.class).addMapping(src -> src.getProduto().getId(), (dest, v) -> dest.setProduto((Long) v));

        return modelMapper.map(imagemProduto,ImagemProdutoRetornoDTO.class);
    }

    public ImagemProdutoDTO updateImagemProduto(ImagemProdutoDTO imagemProdutoDTO) throws ExceptionLojaVirtual {
        ImagemProduto imagemProdutoo = imagemProdutoRepository.findById(imagemProdutoDTO.getId()).orElseThrow(() -> new ExceptionLojaVirtual("Id invalido ou imagem não existe"));
        ImagemProduto imagemProduto = modelMapper.map(imagemProdutoDTO, ImagemProduto.class);
        imagemProdutoRepository.save(imagemProduto);

        return modelMapper.map(imagemProduto, ImagemProdutoDTO.class);
    }


}
