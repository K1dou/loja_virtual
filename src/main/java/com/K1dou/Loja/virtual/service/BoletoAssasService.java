package com.K1dou.Loja.virtual.service;


import com.K1dou.Loja.virtual.enums.ApiTokenIntegracao;
import com.K1dou.Loja.virtual.exceptions.ExceptionLojaVirtual;
import com.K1dou.Loja.virtual.model.BoletoAssas;
import com.K1dou.Loja.virtual.model.Dtos.DataRetornoCobrancaDTO;
import com.K1dou.Loja.virtual.model.Dtos.ObjetoPostCarneAssasDTO;
import com.K1dou.Loja.virtual.model.Dtos.ObjetoRequisicaoCobrancaDTO;
import com.K1dou.Loja.virtual.model.Dtos.ObjetoRetornoCobrancaDTO;
import com.K1dou.Loja.virtual.model.VendaCompraLojaVirtual;
import com.K1dou.Loja.virtual.repository.BoletoAssasRepository;
import com.K1dou.Loja.virtual.repository.VendaCompraLojaVirtualRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class BoletoAssasService {

    @Autowired
    private VendaCompraLojaVirtualRepository vendaCompraLojaVirtualRepository;

    @Autowired
    private BoletoAssasRepository boletoAssasRepository;

    public String gerarCarneApiAssas(ObjetoPostCarneAssasDTO objetoPostCarneAssasDTO) throws ExceptionLojaVirtual, IOException, ParseException, java.text.ParseException {

        VendaCompraLojaVirtual vendaCompraLojaVirtual = vendaCompraLojaVirtualRepository.findById(objetoPostCarneAssasDTO.getIdVenda()).orElseThrow(() -> new ExceptionLojaVirtual("Id da venda esta invalido ou não existe"));
        ObjetoRequisicaoCobrancaDTO requisicao = new ObjetoRequisicaoCobrancaDTO();
        requisicao.setCustomer(this.buscaClientePessoaApiAssas(objetoPostCarneAssasDTO));
        requisicao.setBillingType("UNDEFINED");
        requisicao.setDescription("Pix ou Boleto gerado para a cobrança, cod: " + vendaCompraLojaVirtual.getId());
        requisicao.setInstallmentValue(vendaCompraLojaVirtual.getValorTotal().floatValue());
        requisicao.setInstallmentCount(1);

        Calendar daVencimento = Calendar.getInstance();
        daVencimento.add(Calendar.DAY_OF_MONTH, 7);
        requisicao.setDueDate(new SimpleDateFormat("yyyy-MM-dd").format(daVencimento.getTime()));

        requisicao.getInterest().setValue(1F);
        requisicao.getFine().setValue(1F);

        String json = new ObjectMapper().writeValueAsString(requisicao);

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody bodyGeraCarne = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url(ApiTokenIntegracao.URL_ASSAS_SAND_BOX + "/payments")
                .post(bodyGeraCarne)
                .addHeader("User-Agent", "Loja-Virtual")
                .addHeader("access_token", ApiTokenIntegracao.TOKEN_ASSAS_SAND_BOX)
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .build();

        Response response = client.newCall(request).execute();

        String stringRetorno = response.body().string();

        //BUSCANDO PARCELAS GERADAS

        LinkedHashMap<String, Object> parser = new JSONParser(stringRetorno).parseObject();
        String installment = parser.get("installment").toString();

        //=================================================
        OkHttpClient clientParcelas = new OkHttpClient();

        Request requestParcelas = new Request.Builder()
                .url(ApiTokenIntegracao.URL_ASSAS_SAND_BOX +"/payments?installments=" + installment)
                .get()
                .addHeader("access_token", ApiTokenIntegracao.TOKEN_ASSAS_SAND_BOX)
                .addHeader("User-Agent", "Loja-Virtual")
                .addHeader("accept", "application/json")
                .build();

        Response responseParcelas = clientParcelas.newCall(requestParcelas).execute();

        //chegou obj
        String retornoCobrancas = responseParcelas.body().string();

        //BUSCANDO PARCELAS GERADAS

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        ObjetoRetornoCobrancaDTO listaCobranca = objectMapper.readValue(retornoCobrancas, new TypeReference<ObjetoRetornoCobrancaDTO>() {
        });

        int recorrencia = 1;
        List<BoletoAssas> boletoAssas = new ArrayList<>();
        for (DataRetornoCobrancaDTO data : listaCobranca.getData()) {

            BoletoAssas boletoAssas1 = new BoletoAssas();
            boletoAssas1.setEmpresa(vendaCompraLojaVirtual.getEmpresa());
            boletoAssas1.setVendaCompraLojaVirtual(vendaCompraLojaVirtual);
            boletoAssas1.setCode(data.getId());
            boletoAssas1.setLink(data.getInvoiceUrl());
            boletoAssas1.setDataVencimento(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(data.getDueDate())));
            boletoAssas1.setCheckoutUrl(data.getInvoiceUrl());
            boletoAssas1.setValor(new BigDecimal(data.getValue()));
            boletoAssas1.setIdChrBoleto(data.getId());
            boletoAssas1.setInstallmentLink(data.getInvoiceUrl());
//            boletoAssas1.setIdPix(data.getId());
//            boletoAssas1.setPayloadInBase64(data.);
//            boletoAssas1.setImageInBase64();
            boletoAssas1.setRecorrencia(recorrencia);

            boletoAssas.add(boletoAssas1);

            recorrencia++;
        }
        boletoAssasRepository.saveAllAndFlush(boletoAssas);


        return boletoAssas.get(0).getCheckoutUrl();
    }


    public String buscaClientePessoaApiAssas(ObjetoPostCarneAssasDTO dados) throws IOException, ExceptionLojaVirtual {


        //LISTACLIENTES
        OkHttpClient clientLista = new OkHttpClient();
        Request requestLista = new Request.Builder()
                .url(ApiTokenIntegracao.URL_ASSAS_SAND_BOX + "/customers?cpfCnpj=" + dados.getPayerCpfCnpj() + "&limit=1")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("access_token", ApiTokenIntegracao.TOKEN_ASSAS_SAND_BOX)
                .build();

        Response responseLista = clientLista.newCall(requestLista).execute();

        String responseBody = responseLista.body().string();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        JsonNode dataNode = jsonNode.get("data");

        String idCliente = null;

        if (dataNode.isEmpty()) {
            // Cria novo cliente

            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\"name\":\"" + dados.getPayerName() + "\",\"email\":\"" + dados.getEmail() + "\",\"phone\":\"" + dados.getPayerPhone() + "\",\"cpfCnpj\":\"" + dados.getPayerCpfCnpj() + "\",\"notificationDisabled\":false}");
            Request request = new Request.Builder()
                    .url(ApiTokenIntegracao.URL_ASSAS_SAND_BOX + "/customers")
                    .post(body)
                    .addHeader("accept", "application/json")
                    .addHeader("content-type", "application/json")
                    .addHeader("access_token", ApiTokenIntegracao.TOKEN_ASSAS_SAND_BOX)
                    .build();

            Response response = client.newCall(request).execute();

            JsonNode jsonNodeCriaCliente = objectMapper.readTree(response.body().string());
            JsonNode idClienteNode = jsonNodeCriaCliente.get("id");

            idCliente = idClienteNode.asText();

        } else {
            System.out.println("Clientes encontrados: " + dataNode.size());

            idCliente = dataNode.get(0).get("id").asText();

        }

        return idCliente;
    }


}
