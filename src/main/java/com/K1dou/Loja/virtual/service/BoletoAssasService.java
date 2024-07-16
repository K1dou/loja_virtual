package com.K1dou.Loja.virtual.service;


import com.K1dou.Loja.virtual.enums.ApiTokenIntegracao;
import com.K1dou.Loja.virtual.exceptions.ExceptionLojaVirtual;
import com.K1dou.Loja.virtual.model.Dtos.ObjetoPostCarneAssas;
import com.K1dou.Loja.virtual.util.ValidaCNPJ;
import com.K1dou.Loja.virtual.util.ValidarCPF;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BoletoAssasService {


    public String buscaClientePessoaApiAssas(ObjetoPostCarneAssas dados) throws IOException, ExceptionLojaVirtual {


        //LISTACLIENTES
        OkHttpClient clientLista = new OkHttpClient();
        Request requestLista = new Request.Builder()
                .url(ApiTokenIntegracao.URL_ASSAS_SAND_BOX+"/customers?cpfCnpj="+dados.getPayerCpfCnpj()+"&limit=1")
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
            RequestBody body = RequestBody.create(mediaType, "{\"name\":\""+dados.getPayerName()+"\",\"email\":\""+dados.getEmail()+"\",\"phone\":\""+dados.getPayerPhone()+"\",\"cpfCnpj\":\""+dados.getPayerCpfCnpj()+"\",\"notificationDisabled\":false}");
            Request request = new Request.Builder()
                    .url(ApiTokenIntegracao.URL_ASSAS_SAND_BOX+"/customers")
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
