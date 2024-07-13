package com.K1dou.Loja.virtual.service;

import com.K1dou.Loja.virtual.controller.PessoaController;
import com.K1dou.Loja.virtual.enums.ApiTokenIntegracao;
import com.K1dou.Loja.virtual.enums.StatusContaReceber;
import com.K1dou.Loja.virtual.exceptions.ExceptionLojaVirtual;
import com.K1dou.Loja.virtual.model.*;
import com.K1dou.Loja.virtual.model.ContaReceber;
import com.K1dou.Loja.virtual.model.Dtos.ItemVendaDTO;
import com.K1dou.Loja.virtual.model.Dtos.VendaCompraLojaVirtualDTO;
import com.K1dou.Loja.virtual.model.Dtos.transporteDTO.*;
import com.K1dou.Loja.virtual.repository.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import okhttp3.*;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;


import javax.mail.MessagingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VendaCompraLojaVirtualService {

    @Autowired
    private StatusRastreioRepository statusRastreioRepository;

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    @Autowired
    private VendaCompraLojaVirtualRepository vendaCompraRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;

    @Autowired
    private PessoaUserService pessoaUserService;

    @Autowired
    private PessoaController pessoaController;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private NotaFiscalVendaRepository notaFiscalVendaRepository;

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private ContaReceberRepository contaReceberRepository;

    @Autowired
    private ServiceSendEmail serviceSendEmail;

    public VendaCompraLojaVirtualDTO salvaVendaCompra(VendaCompraLojaVirtual vendaCompraLojaVirtual) throws MessagingException, UnsupportedEncodingException, ExceptionLojaVirtual {


        vendaCompraLojaVirtual.getPessoa().setEmpresa(vendaCompraLojaVirtual.getEmpresa());
        PessoaFisica pessoaFisica = pessoaController.cadastroPessoaFisica(vendaCompraLojaVirtual.getPessoa()).getBody();
        vendaCompraLojaVirtual.setPessoa(pessoaFisica);

        vendaCompraLojaVirtual.getEnderecoCobranca().setPessoa(pessoaFisica);
        vendaCompraLojaVirtual.getEnderecoCobranca().setEmpresa(vendaCompraLojaVirtual.getEmpresa());
        Endereco enderecoCobranca = enderecoRepository.save(vendaCompraLojaVirtual.getEnderecoCobranca());
        vendaCompraLojaVirtual.setEnderecoCobranca(enderecoCobranca);

        vendaCompraLojaVirtual.getEnderecoEntrega().setPessoa(pessoaFisica);
        vendaCompraLojaVirtual.getEnderecoEntrega().setEmpresa(vendaCompraLojaVirtual.getEmpresa());
        Endereco enderecoEntrega = enderecoRepository.save(vendaCompraLojaVirtual.getEnderecoEntrega());
        vendaCompraLojaVirtual.setEnderecoEntrega(enderecoEntrega);

        vendaCompraLojaVirtual.getNotaFiscalVenda().setEmpresa(vendaCompraLojaVirtual.getEmpresa());

        for (ItemVendaLoja itemVenda : vendaCompraLojaVirtual.getItemVendaLojas()) {
            itemVenda.setEmpresa(vendaCompraLojaVirtual.getEmpresa());
            itemVenda.setVendaCompraLojaVirtual(vendaCompraLojaVirtual);
        }

        /*Salva primeiro a venda e todo os dados*/
        vendaCompraLojaVirtual = vendaCompraRepository.saveAndFlush(vendaCompraLojaVirtual);

        StatusRastreio statusRastreio = new StatusRastreio();
        statusRastreio.setCentroDistribuicao("Loja local");
        statusRastreio.setCidade("Local");
        statusRastreio.setEmpresa(vendaCompraLojaVirtual.getEmpresa());
        statusRastreio.setStatus("Inicio");
        statusRastreio.setEstado("Local");
        statusRastreio.setVendaCompraLojaVirtual(vendaCompraLojaVirtual);
        statusRastreioRepository.save(statusRastreio);

        /*Associa a venda gravada no banco com a nota fiscal*/
        vendaCompraLojaVirtual.getNotaFiscalVenda().setVendaCompraLojaVirtual(vendaCompraLojaVirtual);

        /*Persiste novamente as nota fiscal novamente pra ficar amarrada na venda*/
        notaFiscalVendaRepository.saveAndFlush(vendaCompraLojaVirtual.getNotaFiscalVenda());

        VendaCompraLojaVirtualDTO compraLojaVirtualDTO = new VendaCompraLojaVirtualDTO();
        compraLojaVirtualDTO.setValorTotal(vendaCompraLojaVirtual.getValorTotal());
        compraLojaVirtualDTO.setPessoa(vendaCompraLojaVirtual.getPessoa().getId());
        compraLojaVirtualDTO.setEnderecoCobranca(vendaCompraLojaVirtual.getEnderecoCobranca());
        compraLojaVirtualDTO.setEnderecoEntrega(vendaCompraLojaVirtual.getEnderecoEntrega());
        compraLojaVirtualDTO.setValorDesconto(vendaCompraLojaVirtual.getValorDesconto());
        compraLojaVirtualDTO.setValorFrete(vendaCompraLojaVirtual.getValorFrete());
        modelMapper.typeMap(ItemVendaLoja.class, ItemVendaDTO.class).addMapping(item -> item.getProduto().getId(), (dest, v) -> dest.setProduto((Long) v));

        List<ItemVendaDTO> itemVendaDTOS = vendaCompraLojaVirtual.getItemVendaLojas().stream().map(item -> modelMapper.map(item, ItemVendaDTO.class)).collect(Collectors.toList());
        compraLojaVirtualDTO.setItemVendaLojas(itemVendaDTOS);

        ContaReceber contaReceber = new ContaReceber();
        contaReceber.setDescricao("VendaLojaVirtual" + vendaCompraLojaVirtual.getId());
        contaReceber.setDtPagamento(Calendar.getInstance().getTime());
        contaReceber.setDtVencimento(Calendar.getInstance().getTime());
        contaReceber.setEmpresa(vendaCompraLojaVirtual.getEmpresa());
        contaReceber.setPessoa(vendaCompraLojaVirtual.getPessoa());
        contaReceber.setStatus(StatusContaReceber.QUITADA);
        contaReceber.setValorDesconto(vendaCompraLojaVirtual.getValorDesconto());
        contaReceber.setValorTotal(vendaCompraLojaVirtual.getValorTotal());
        contaReceberRepository.saveAndFlush(contaReceber);

        /*Emil para o comprador*/
        StringBuilder msgemail = new StringBuilder();
        msgemail.append("Olá, ").append(pessoaFisica.getNome()).append("</br>");
        msgemail.append("Você realizou a compra de nº: ").append(vendaCompraLojaVirtual.getId()).append("</br>");
        msgemail.append("Na loja ").append(vendaCompraLojaVirtual.getEmpresa().getNomeFantasia());
        /*assunto, msg, destino*/
        serviceSendEmail.enviarEmailHtml("Compra Realizada", msgemail.toString(), pessoaFisica.getEmail());

        /*Email para o vendedor*/
        msgemail = new StringBuilder();
        msgemail.append("Você realizou uma venda, nº ").append(vendaCompraLojaVirtual.getId());
        serviceSendEmail.enviarEmailHtml("Venda Realizada", msgemail.toString(), vendaCompraLojaVirtual.getEmpresa().getEmail());


        return compraLojaVirtualDTO;
    }


    public void exclusaoTotalVendaBanco(Long idVenda) {
        vendaCompraRepository.exclusaoTotalVendaBanco(idVenda);
    }

    public void exclusaoLogicaVendaBanco(Long idVenda) {
        vendaCompraRepository.exclusaoLogicaVendaBanco(idVenda);
    }

    public void restaurarExclusaoLogica(Long idVenda) {
        vendaCompraRepository.restaurarVendaBanco(idVenda);
    }


    public VendaCompraLojaVirtualDTO consultaVendaId(Long id) throws ExceptionLojaVirtual {

        Optional<VendaCompraLojaVirtual> vendaCompraLojaVirtual = Optional.ofNullable(Optional.ofNullable(vendaCompraRepository.consultaVendaId(id)).orElseThrow(() -> new ExceptionLojaVirtual("Id da venda está invalido ou não existe")));
        modelMapper.typeMap(VendaCompraLojaVirtual.class, VendaCompraLojaVirtualDTO.class).addMapping(vendaCompraLojaVirtual1 -> vendaCompraLojaVirtual1.getPessoa().getId(), (dest, v) -> dest.setPessoa((Long) v));
        modelMapper.typeMap(ItemVendaLoja.class, ItemVendaDTO.class).addMapping(item -> item.getProduto().getId(), (dest, v) -> dest.setProduto((Long) v));

        VendaCompraLojaVirtualDTO vendaCompraLojaVirtualDTO = modelMapper.map(vendaCompraLojaVirtual, VendaCompraLojaVirtualDTO.class);
        return vendaCompraLojaVirtualDTO;
    }

    public List<VendaCompraLojaVirtualDTO> vendaPorProduto(Long idProduto) {
        modelMapper.typeMap(ItemVendaLoja.class, ItemVendaDTO.class).addMapping(item -> item.getProduto().getId(), (dest, v) -> dest.setProduto((Long) v));
        modelMapper.typeMap(VendaCompraLojaVirtual.class, VendaCompraLojaVirtualDTO.class).addMapping(vendaCompraLojaVirtual1 -> vendaCompraLojaVirtual1.getPessoa().getId(), (dest, v) -> dest.setPessoa((Long) v));

        List<VendaCompraLojaVirtual> vendaCompraLojaVirtuals = vendaCompraRepository.vendaPorProduto(idProduto);
        List<VendaCompraLojaVirtualDTO> vendaCompraLojaVirtualDTOS = vendaCompraLojaVirtuals.stream().map(item -> modelMapper.map(item, VendaCompraLojaVirtualDTO.class)).collect(Collectors.toList());

        return vendaCompraLojaVirtualDTOS;
    }


    public List<VendaCompraLojaVirtualDTO> consultaVendaDinamica(String valor, String tipoConsulta) {
        modelMapper.typeMap(ItemVendaLoja.class, ItemVendaDTO.class).addMapping(item -> item.getProduto().getId(), (dest, v) -> dest.setProduto((Long) v));
        modelMapper.typeMap(VendaCompraLojaVirtual.class, VendaCompraLojaVirtualDTO.class).addMapping(vendaCompraLojaVirtual1 -> vendaCompraLojaVirtual1.getPessoa().getId(), (dest, v) -> dest.setPessoa((Long) v));

        List<VendaCompraLojaVirtual> vendaCompraLojaVirtuals = new ArrayList<>();

        if (tipoConsulta.equalsIgnoreCase("POR_ID_PROD")) {
            vendaCompraLojaVirtuals = vendaCompraRepository.vendaPorProduto(Long.valueOf(valor));
        } else if (tipoConsulta.equalsIgnoreCase("POR_NOME")) {
            vendaCompraLojaVirtuals = vendaCompraRepository.findVendaByNomeProduto(valor.toUpperCase().trim());
        } else if (tipoConsulta.equalsIgnoreCase("POR_ENDERECO_COBRANCA")) {
            vendaCompraLojaVirtuals = vendaCompraRepository.vendaPorEnderecoCobranca(Long.valueOf(valor));
        } else if (tipoConsulta.equalsIgnoreCase("POR_ENDERECO_ENTREGA")) {
            vendaCompraLojaVirtuals = vendaCompraRepository.vendaPorEnderecoEntrega(Long.valueOf(valor));

        }

        List<VendaCompraLojaVirtualDTO> vendaCompraLojaVirtualDTOS = vendaCompraLojaVirtuals.stream().map(item -> modelMapper.map(item, VendaCompraLojaVirtualDTO.class)).collect(Collectors.toList());

        return vendaCompraLojaVirtualDTOS;
    }

    public List<VendaCompraLojaVirtualDTO> consultaVendaPorDataJPA(String data1, String data2) throws ParseException {
        modelMapper.typeMap(ItemVendaLoja.class, ItemVendaDTO.class).addMapping(item -> item.getProduto().getId(), (dest, v) -> dest.setProduto((Long) v));
        modelMapper.typeMap(VendaCompraLojaVirtual.class, VendaCompraLojaVirtualDTO.class).addMapping(vendaCompraLojaVirtual1 -> vendaCompraLojaVirtual1.getPessoa().getId(), (dest, v) -> dest.setPessoa((Long) v));


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse(data1);
        Date endDate = sdf.parse(data2);


        List<VendaCompraLojaVirtual> vendas = vendaCompraRepository.consultaVendaPorData(startDate, endDate);

        List<VendaCompraLojaVirtualDTO> vendasDTO = vendas.stream()
                .map(item -> modelMapper.map(item, VendaCompraLojaVirtualDTO.class))
                .collect(Collectors.toList());

        return vendasDTO;
    }

    public List<VendaCompraLojaVirtualDTO> consultaPorCpfPessoa(String cpf) {
        modelMapper.typeMap(VendaCompraLojaVirtual.class, VendaCompraLojaVirtualDTO.class).addMapping(item -> item.getPessoa().getId(), (dest, v) -> dest.setPessoa((Long) v));
        modelMapper.typeMap(ItemVendaLoja.class, ItemVendaDTO.class).addMapping(item -> item.getProduto().getId(), (dest, v) -> dest.setProduto((Long) v));

        List<VendaCompraLojaVirtual> vendaCompraLojaVirtuals = vendaCompraRepository.consultaPorCpfPessoa(cpf);
        List<VendaCompraLojaVirtualDTO> vendaCompraLojaVirtualDTOS = vendaCompraLojaVirtuals.stream().map(item -> modelMapper.map(item, VendaCompraLojaVirtualDTO.class)).collect(Collectors.toList());

        return vendaCompraLojaVirtualDTOS;
    }

    public List<VendaCompraLojaVirtualDTO> consultaPorParteCpfPessoa(String cpf) {
        modelMapper.typeMap(VendaCompraLojaVirtual.class, VendaCompraLojaVirtualDTO.class).addMapping(item -> item.getPessoa().getId(), (dest, v) -> dest.setPessoa((Long) v));
        modelMapper.typeMap(ItemVendaLoja.class, ItemVendaDTO.class).addMapping(item -> item.getProduto().getId(), (dest, v) -> dest.setProduto((Long) v));

        List<VendaCompraLojaVirtual> vendaCompraLojaVirtuals = vendaCompraRepository.consultaPorParteCpfPessoa(cpf);
        List<VendaCompraLojaVirtualDTO> vendaCompraLojaVirtualDTOS = vendaCompraLojaVirtuals.stream().map(item -> modelMapper.map(item, VendaCompraLojaVirtualDTO.class)).collect(Collectors.toList());

        return vendaCompraLojaVirtualDTOS;
    }

    public List<VendaCompraLojaVirtualDTO> consultaPorPessoaId(Long id) {

        modelMapper.typeMap(VendaCompraLojaVirtual.class, VendaCompraLojaVirtualDTO.class).addMapping(item -> item.getPessoa().getId(), (dest, v) -> dest.setPessoa((Long) v));
        modelMapper.typeMap(ItemVendaLoja.class, ItemVendaDTO.class).addMapping(item -> item.getProduto().getId(), (dest, v) -> dest.setProduto((Long) v));

        List<VendaCompraLojaVirtual> vendaCompraLojaVirtuals = vendaCompraRepository.consultaPorPessoaId(id);
        List<VendaCompraLojaVirtualDTO> vendaCompraLojaVirtualDTOS = vendaCompraLojaVirtuals.stream().map(item -> modelMapper.map(item, VendaCompraLojaVirtualDTO.class)).collect(Collectors.toList());

        return vendaCompraLojaVirtualDTOS;
    }

    public List<EmpresaTransporteDTO> consultaFrete(ConsultaFreteDTO dto) throws IOException {


        ObjectMapper objectMapper = new ObjectMapper();
        //transforma o dto em uma String do json
        String json = objectMapper.writeValueAsString(dto);

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(ApiTokenIntegracao.URL_MELHOR_ENVIO_SAND_BOX + "api/v2/me/shipment/calculate")
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + ApiTokenIntegracao.TOKEN_MELHOR_ENVIO_SAND_BOX)
                .addHeader("User-Agent", "hique1276@gmail.com")
                .build();

        Response response = client.newCall(request).execute();

        //criaa um jsonNode, com o json em string com a resposta
        JsonNode jsonNode = new ObjectMapper().readTree(response.body().string());

        //Um iterador é criado para percorrer os nós filhos do JsonNode principal.
        Iterator<JsonNode> iterator = jsonNode.iterator();

        List<EmpresaTransporteDTO> empresaTransporteDTOs = new ArrayList<EmpresaTransporteDTO>();

        //Um loop while é usado para iterar através de todos os nós filhos no
        // jsonNode. O método iterator.hasNext() verifica se há mais nós para processar,
        // e iterator.next() retorna o próximo nó.
        while (iterator.hasNext()) {
            JsonNode node = iterator.next();

            EmpresaTransporteDTO empresaTransporteDTO = new EmpresaTransporteDTO();

            if (node.get("id") != null) {
                empresaTransporteDTO.setId(node.get("id").asText());
            }

            if (node.get("name") != null) {
                empresaTransporteDTO.setNome(node.get("name").asText());
            }

            if (node.get("price") != null) {
                empresaTransporteDTO.setValor(node.get("price").asText());
            }

            if (node.get("company") != null) {
                empresaTransporteDTO.setEmpresa(node.get("company").get("name").asText());
                empresaTransporteDTO.setPicture(node.get("company").get("picture").asText());
            }

            if (empresaTransporteDTO.dadosOK()) {
                empresaTransporteDTOs.add(empresaTransporteDTO);
            }
        }
        return empresaTransporteDTOs;

    }

    public String imprimeCompraEtiquetaFrete(IdVendaRequest idVendaRequest) throws ExceptionLojaVirtual, IOException {

        Long idVenda = idVendaRequest.getIdVenda();

        VendaCompraLojaVirtual vendaCompraLojaVirtual = vendaCompraRepository.findById(idVenda).orElseThrow(() -> new ExceptionLojaVirtual("Venda compra não encontrada"));

        EnvioEtiquetaDTO envioEtiqueta = new EnvioEtiquetaDTO();
        FromEnvioEtiquetaDTO fromEnvioEtiqueta = new FromEnvioEtiquetaDTO();
        ToEnvioEtiquetaDTO toEnvioEtiqueta = new ToEnvioEtiquetaDTO();
        OptionsEnvioDTO optionsEnvioDTO = new OptionsEnvioDTO();
        InvoiceEnvioDTO invoiceEnvioDTO = new InvoiceEnvioDTO();
        TagsEnvioDTO tagsEnvioDTO = new TagsEnvioDTO();

        List<TagsEnvioDTO> tagsEnvioDTOList = new ArrayList<>();
        optionsEnvioDTO.setTags(tagsEnvioDTOList);
        optionsEnvioDTO.setInvoice(invoiceEnvioDTO);

        //null
        envioEtiqueta.setService(vendaCompraLojaVirtual.getServicoTransportadora());
        envioEtiqueta.setAgency("49");
        envioEtiqueta.setFrom(fromEnvioEtiqueta);
        envioEtiqueta.setTo(toEnvioEtiqueta);
        envioEtiqueta.setOptions(optionsEnvioDTO);
        envioEtiqueta.getFrom().setName(vendaCompraLojaVirtual.getEmpresa().getNome());
        envioEtiqueta.getFrom().setPhone(vendaCompraLojaVirtual.getEmpresa().getTelefone());
        envioEtiqueta.getFrom().setEmail(vendaCompraLojaVirtual.getEmpresa().getEmail());
        envioEtiqueta.getFrom().setCompany_document(vendaCompraLojaVirtual.getEmpresa().getCnpj());
        envioEtiqueta.getFrom().setState_register(vendaCompraLojaVirtual.getEmpresa().getInscEstadual());
        envioEtiqueta.getFrom().setAddress(vendaCompraLojaVirtual.getEmpresa().getEnderecos().get(0).getRuaLogra());
        envioEtiqueta.getFrom().setComplement(vendaCompraLojaVirtual.getEmpresa().getEnderecos().get(0).getComplemento());
        envioEtiqueta.getFrom().setNumber(vendaCompraLojaVirtual.getEmpresa().getEnderecos().get(0).getNumero());
        envioEtiqueta.getFrom().setDistrict(vendaCompraLojaVirtual.getEmpresa().getEnderecos().get(0).getEstado());
        envioEtiqueta.getFrom().setCity(vendaCompraLojaVirtual.getEmpresa().getEnderecos().get(0).getCidade());
        envioEtiqueta.getFrom().setCountry_id(vendaCompraLojaVirtual.getEmpresa().getEnderecos().get(0).getUf());
        envioEtiqueta.getFrom().setPostal_code(vendaCompraLojaVirtual.getEmpresa().getEnderecos().get(0).getCep());
        envioEtiqueta.getFrom().setNote("");

        envioEtiqueta.getTo().setName(vendaCompraLojaVirtual.getPessoa().getNome());
        envioEtiqueta.getTo().setPhone(vendaCompraLojaVirtual.getPessoa().getTelefone());
        envioEtiqueta.getTo().setEmail(vendaCompraLojaVirtual.getPessoa().getEmail());
        envioEtiqueta.getTo().setDocument(vendaCompraLojaVirtual.getPessoa().getCpf());
        envioEtiqueta.getTo().setAddress(vendaCompraLojaVirtual.getPessoa().enderecoEntrega().getRuaLogra());
        envioEtiqueta.getTo().setComplement(vendaCompraLojaVirtual.getPessoa().enderecoEntrega().getComplemento());
        envioEtiqueta.getTo().setNumber(vendaCompraLojaVirtual.getPessoa().enderecoEntrega().getNumero());
        envioEtiqueta.getTo().setDistrict(vendaCompraLojaVirtual.getPessoa().enderecoEntrega().getEstado());
        envioEtiqueta.getTo().setCity(vendaCompraLojaVirtual.getPessoa().enderecoEntrega().getCidade());
        envioEtiqueta.getTo().setState_abbr(vendaCompraLojaVirtual.getPessoa().enderecoEntrega().getEstado());
        envioEtiqueta.getTo().setCountry_id(vendaCompraLojaVirtual.getPessoa().enderecoEntrega().getUf());
        envioEtiqueta.getTo().setPostal_code(vendaCompraLojaVirtual.getPessoa().enderecoEntrega().getCep());
        envioEtiqueta.getTo().setNote("");

        List<ProductsEnvioEtiquetaDTO> products = new ArrayList<>();
        List<VolumeEnvioEtiquetaDTO> volumes = new ArrayList<>();

        for (ItemVendaLoja i : vendaCompraLojaVirtual.getItemVendaLojas()) {
            ProductsEnvioEtiquetaDTO product = new ProductsEnvioEtiquetaDTO();
            product.setName(i.getProduto().getNome());
            product.setQuantity(i.getQuantidade().toString());
            product.setUnitary_value(i.getProduto().getValorVenda().toString());
            products.add(product);

            VolumeEnvioEtiquetaDTO volume = new VolumeEnvioEtiquetaDTO();
            volume.setHeight(i.getProduto().getAltura().toString());
            volume.setLength(i.getProduto().getProfundidade().toString());
            volume.setWidth(i.getProduto().getLargura().toString());
            volume.setWeight(i.getProduto().getPeso().toString());
            volumes.add(volume);
        }
        envioEtiqueta.setProducts(products);
        envioEtiqueta.setVolumes(volumes);

        envioEtiqueta.getOptions().setInsurance_value(vendaCompraLojaVirtual.getValorTotal().toString());
        envioEtiqueta.getOptions().setReceipt(false);
        envioEtiqueta.getOptions().setOwn_hand(false);
        envioEtiqueta.getOptions().setReverse(false);
        envioEtiqueta.getOptions().setNon_commercial(false);

        //A Nota fiscal deve ser modelo 55
        envioEtiqueta.getOptions().getInvoice().setKey(vendaCompraLojaVirtual.getNotaFiscalVenda().getNumero());
        envioEtiqueta.getOptions().setPlatform(vendaCompraLojaVirtual.getEmpresa().getNomeFantasia());

        tagsEnvioDTO.setTag("Indentificaçao do pedido na plataforma" + vendaCompraLojaVirtual.getId());
        tagsEnvioDTO.setUrl(null);
        envioEtiqueta.getOptions().getTags().add(tagsEnvioDTO);

        String jsonEnvio = new ObjectMapper().writeValueAsString(envioEtiqueta);


        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, jsonEnvio);
        Request request = new Request.Builder()
                .url(ApiTokenIntegracao.URL_MELHOR_ENVIO_SAND_BOX + "api/v2/me/cart")
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + ApiTokenIntegracao.TOKEN_MELHOR_ENVIO_SAND_BOX)
                .addHeader("User-Agent", "hique1276@gmail.com")
                .build();

        Response response = client.newCall(request).execute();

        JsonNode jsonNode = new ObjectMapper().readTree(response.body().string());
        Iterator<JsonNode> iterator = jsonNode.iterator();

        String idEtiqueta = null;

        JsonNode idNodee = jsonNode.get("id");
        if (idNodee != null) {
            idEtiqueta = idNodee.asText();
            System.out.println("ID: " + idEtiqueta);
        } else {
            System.out.println("O campo 'id' não foi encontrado.");
        }

        String etiqueta = idEtiqueta;

        vendaCompraRepository.updateEtiqueta(idEtiqueta, idVenda);


        OkHttpClient clientCompra = new OkHttpClient();

        MediaType mediaTypeCompra = MediaType.parse("application/json");
        RequestBody bodyCompra = RequestBody.create(mediaTypeCompra, "{\"orders\":[" + idEtiqueta + "] }");
        Request requestCompra = new Request.Builder()
                .url(ApiTokenIntegracao.URL_MELHOR_ENVIO_SAND_BOX + "api/v2/me/shipment/checkout")
                .post(bodyCompra)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + ApiTokenIntegracao.TOKEN_MELHOR_ENVIO_SAND_BOX)
                .addHeader("User-Agent", "hique1276@gmail.com")
                .build();

        Response responseCompra = clientCompra.newCall(requestCompra).execute();

        //Não foi possível realizar a compra da etiqueta
        if (!responseCompra.isSuccessful()) {
            System.out.println("Não foi possível realizar a compra da etiqueta");
        }

        OkHttpClient clientGera = new OkHttpClient();

        MediaType mediaTypeGera = MediaType.parse("application/json");
        RequestBody bodyGera = RequestBody.create(mediaTypeGera, "{\"orders\":[" + idEtiqueta + "]}");
        Request requestGera = new Request.Builder()
                .url(ApiTokenIntegracao.URL_MELHOR_ENVIO_SAND_BOX + "api/v2/me/shipment/generate")
                .post(bodyGera)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + ApiTokenIntegracao.TOKEN_MELHOR_ENVIO_SAND_BOX)
                .addHeader("User-Agent", "hique1276@gmail.com")
                .build();

        Response responseGera = clientGera.newCall(requestGera).execute();

        //caiu aq
        if (!responseGera.isSuccessful()) {
            throw new ExceptionLojaVirtual("Não foi possível gerar a etiqueta");
        }


        OkHttpClient clientImprime = new OkHttpClient();

        MediaType mediaTypeImprime = MediaType.parse("application/json");
        RequestBody bodyImprime = RequestBody.create(mediaTypeImprime, "{\"mode\":\"\",\"orders\":[\"9c81274c-9d16-4e63-b50a-fff7a9651318\"]}");
        Request requestImprime = new Request.Builder()
                .url(ApiTokenIntegracao.URL_MELHOR_ENVIO_SAND_BOX + "api/v2/me/shipment/print")
                .post(bodyImprime)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + ApiTokenIntegracao.TOKEN_MELHOR_ENVIO_SAND_BOX)
                .addHeader("User-Agent", "hique1276@gmail.com")
                .build();

        Response responseImprime = clientImprime.newCall(requestImprime).execute();

        if (!responseImprime.isSuccessful()) {
            throw new ExceptionLojaVirtual("Não foi possível imprimi a etiqueta");
        }
        String urlEtiqueta = responseImprime.body().string();

        urlEtiqueta = urlEtiqueta.replace("\\", "");


        vendaCompraRepository.updateUrlEtiqueta(urlEtiqueta, idVenda);

        return "Sucesso";
    }

    public String cancelaEtiqueta(String idEtiqueta, String descricao) throws IOException {


        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");

        RequestBody body = RequestBody.create(mediaType, "{\"order\":{\"id\":\"" + idEtiqueta + "\",\"reason_id\":\"2\",\"description\":\"" + descricao + "\"}}");
        Request request = new Request.Builder()
                .url(ApiTokenIntegracao.URL_MELHOR_ENVIO_SAND_BOX + "api/v2/me/shipment/cancel")
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + ApiTokenIntegracao.TOKEN_MELHOR_ENVIO_SAND_BOX)
                .addHeader("User-Agent", "hique1276@gmail.com")
                .build();

        Response response = client.newCall(request).execute();


        return response.body().string();
    }


}
