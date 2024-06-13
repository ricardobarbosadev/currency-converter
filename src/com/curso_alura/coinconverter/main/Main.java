package com.curso_alura.coinconverter.main;

import com.curso_alura.coinconverter.http.HttpJsonResponse;
import com.curso_alura.coinconverter.http.HttpRequest;
import java.util.HashMap;


public class Main {
    public static void main(String[] args) {
        String apiUrl = " https://v6.exchangerate-api.com/v6/fd999e531529daf2bbedf589/latest/eur";

        try {
            // Criar uma nova requisição HTTP GET
            HttpRequest request = new HttpRequest(apiUrl, "GET");

            // Executar a requisição e obter a resposta
            String responseBody = request.execute();

            // Processar a resposta como um HttpJsonResponse

            HttpJsonResponse response = parseJsonResponse(responseBody);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static HttpJsonResponse parseJsonResponse(String responseBody) {
        return new HttpJsonResponse(200, new HashMap<>(), responseBody);
    }


}