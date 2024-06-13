package com.curso_alura.coinconverter.main;

import com.curso_alura.coinconverter.http.HttpRequest;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String apiUrl = " https://v6.exchangerate-api.com/v6/fd999e531529daf2bbedf589/latest/eur";

        try {
            // Criar uma nova requisição HTTP GET
            HttpRequest request = new HttpRequest(apiUrl, "GET");

            // Executar a requisição e obter a resposta
            String response = request.execute();

            // Imprimir a resposta
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}