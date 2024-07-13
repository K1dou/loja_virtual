package com.K1dou.Loja.virtual;

import com.K1dou.Loja.virtual.enums.ApiTokenIntegracao;
import okhttp3.*;

import java.io.IOException;

public class TesteApiMelhorEnvio {

    public static void main(String[] args) throws IOException {

        //*Insere as etiquetas de frete
        //OkHttpClient client = new OkHttpClient();
//
//        MediaType mediaType = MediaType.parse("application/json");
//        RequestBody body = RequestBody.create(mediaType, "{\"service\": 3,\"agency\": 49,\"from\":{\"name\": \"ome do remetente\",\"phone\": \"53984470102\",\"email\": \"cotato@melhorevio.com.br\",\"document\": \"16571478358\",\"company_document\": \"89794131000100\",\"state_register\": \"123456\",\"address\": \"Endereço do remetente\",\"complement\": \"Complemento\",\"number\": \"1\",\"district\": \"Bairro\",\"city\": \"São Paulo\",\"country_id\": \"BR\",\"postal_code\": \"01002001\",\"note\": \"observação\"},\"to\": {\"name\": \"ome do destinatário\",\"phone\": \"53984470102\",\"email\": \"cotato@melhorevio.com.br\",\"document\": \"25404918047\",\"company_document\": \"07595604000177\",\"state_register\": \"123456\",\"address\": \"Endereço do destinatário\",\"complement\": \"Complemento\",\"number\": \"2\",\"district\": \"Bairro\",\"city\": \"Porto Alegre\",\"state_abbr\": \"RS\",\"country_id\": \"BR\",\"postal_code\": \"90570020\",\"note\": \"observação\"},\"products\": [{\"name\": \"Papel adesivo para etiquetas 1\",\"quantity\": 3,\"unitary_value\": 100},{\"name\": \"Papel adesivo para etiquetas 2\",\"quantity\": 1,\"unitary_value\": 100}],\"volumes\": [{\"height\": 15,\"width\": 20,\"length\": 10,\"weight\": 3.5}],\"options\": {\"insurance_value\": 10,\"receipt\": false,\"own_hand\": false,\"reverse\": false,\"non_commercial\": false,\"invoice\": {\"key\": \"31190307586261000184550010000092481404848162\"},\"platform\": \"ome da Plataforma\",\"tags\": [{\"tag\": \"Identificação do pedido a plataforma, exemplo: 1000007\",\"url\": \"Lik direto para o pedido a plataforma, se possível, caso contrário pode ser passado o valor ull\"}]}}");
//        Request request = new Request.Builder()
//                .url(ApiTokenIntegracao.URL_MELHOR_ENVIO_SAND_BOX+"api/v2/me/cart")
//                .post(body)
//                .addHeader("Accept", "application/json")
//                .addHeader("Content-Type", "application/json")
//                .addHeader("Authorization", "Bearer "+ ApiTokenIntegracao.TOKEN_MELHOR_ENVIO_SAND_BOX)
//                .addHeader("User-Agent", "hique1276@gmail.com")
//                .build();
//
//        Response response = client.newCall(request).execute();
//
//        System.out.println(response.body().string());


        //*faz a compra do frete para a etiqueta
//        OkHttpClient client = new OkHttpClient();
//
//        MediaType mediaType = MediaType.parse("application/json");
//        RequestBody body = RequestBody.create(mediaType, "{\"orders\":[\"9c81274c-9d16-4e63-b50a-fff7a9651318\"]}");
//        Request request = new Request.Builder()
//                .url(ApiTokenIntegracao.URL_MELHOR_ENVIO_SAND_BOX + "api/v2/me/shipment/checkout")
//                .post(body)
//                .addHeader("Accept", "application/json")
//                .addHeader("Content-Type", "application/json")
//                .addHeader("Authorization", "Bearer " + ApiTokenIntegracao.TOKEN_MELHOR_ENVIO_SAND_BOX)
//                .addHeader("User-Agent", "hique1276@gmail.com")
//                .build();
//
//        Response response = client.newCall(request).execute();
//        System.out.println(response.body().string());


                 //*Gera as etiquetas
//        OkHttpClient client = new OkHttpClient();
//
//        MediaType mediaType = MediaType.parse("application/json");
//        RequestBody body = RequestBody.create(mediaType, "{\"orders\":[\"9c81274c-9d16-4e63-b50a-fff7a9651318\"]}");
//        Request request = new Request.Builder()
//                .url(ApiTokenIntegracao.URL_MELHOR_ENVIO_SAND_BOX+"api/v2/me/shipment/generate")
//                .post(body)
//                .addHeader("Accept", "application/json")
//                .addHeader("Content-Type", "application/json")
//                .addHeader("Authorization", "Bearer "+ApiTokenIntegracao.TOKEN_MELHOR_ENVIO_SAND_BOX)
//                .addHeader("User-Agent", "hique1276@gmail.com")
//                .build();
//
//        Response response = client.newCall(request).execute();
//        System.out.println(response.body().string());


        //impressao etiqueta
//        OkHttpClient client = new OkHttpClient();
//
//        MediaType mediaType = MediaType.parse("application/json");
//        RequestBody body = RequestBody.create(mediaType, "{\"mode\":\"\",\"orders\":[\"9c81274c-9d16-4e63-b50a-fff7a9651318\"]}");
//        Request request = new Request.Builder()
//                .url(ApiTokenIntegracao.URL_MELHOR_ENVIO_SAND_BOX+"api/v2/me/shipment/print")
//                .post(body)
//                .addHeader("Accept", "application/json")
//                .addHeader("Content-Type", "application/json")
//                .addHeader("Authorization", "Bearer "+ApiTokenIntegracao.TOKEN_MELHOR_ENVIO_SAND_BOX)
//                .addHeader("User-Agent", "hique1276@gmail.com")
//                .build();
//
//        Response response = client.newCall(request).execute();
//        System.out.println(response.body().string());


    }
}
