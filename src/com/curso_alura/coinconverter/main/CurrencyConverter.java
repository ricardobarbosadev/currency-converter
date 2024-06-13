package com.curso_alura.coinconverter.main;

import com.curso_alura.coinconverter.http.HttpJsonResponse;
import com.curso_alura.coinconverter.http.HttpRequest;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CurrencyConverter {

    private static final String API_URL = "https://v6.exchangerate-api.com/v6/fd999e531529daf2bbedf589/latest/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Map<String, String> currencyNames = getCurrencyNames();

        while (true) {
            // Exibir o menu de opções
            System.out.println("Escolha uma das seguintes moedas como origem:");
            for (Map.Entry<String, String> entry : currencyNames.entrySet()) {
                System.out.println(entry.getKey() + " - " + entry.getValue());
            }
            System.out.println("Sair - Sair do programa");
            System.out.print("Escolha uma opção: ");

            // Capturar a escolha do usuário
            String baseCurrency = scanner.nextLine().trim().toUpperCase();

            if (baseCurrency.equalsIgnoreCase("Sair")) {
                System.out.println("Saindo...");
                break;
            }

            if (!currencyNames.containsKey(baseCurrency)) {
                System.out.println("Opção inválida. Tente novamente.");
                continue;
            }

            System.out.print("Digite o valor a ser convertido: ");
            double amount = scanner.nextDouble();
            scanner.nextLine(); // Consumir a nova linha

            // Exibir o menu de opções para moeda de destino
            System.out.println("Escolha uma das seguintes moedas como destino:");
            for (Map.Entry<String, String> entry : currencyNames.entrySet()) {
                System.out.println(entry.getKey() + " - " + entry.getValue());
            }
            System.out.print("Escolha uma opção: ");

            // Capturar a escolha do usuário para moeda de destino
            String targetCurrency = scanner.nextLine().trim().toUpperCase();

            if (!currencyNames.containsKey(targetCurrency)) {
                System.out.println("Opção inválida. Tente novamente.");
                continue;
            }

            // Fazer a requisição à API com a moeda base escolhida
            try {
                // Construir a URL da API com a moeda base fornecida
                String apiUrlWithBase = API_URL + baseCurrency;

                // Fazer requisição HTTP
                HttpRequest request = new HttpRequest(apiUrlWithBase, "GET");
                String responseBody = request.execute();

                // Processar a resposta JSON
                HttpJsonResponse response = new HttpJsonResponse(200, null, responseBody);
                JsonObject jsonObject = response.getJsonObject();

                // Obter a taxa de câmbio da moeda de destino
                double exchangeRate = getExchangeRate(jsonObject, targetCurrency);

                if (exchangeRate != -1) {
                    double convertedAmount = amount * exchangeRate;
                    System.out.printf("%.2f %s = %.2f %s%n", amount, baseCurrency, convertedAmount, targetCurrency);
                } else {
                    System.out.println("Moeda de destino não encontrada.");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        scanner.close();
    }

    private static Map<String, String> getCurrencyNames() {
        Map<String, String> currencyNames = new HashMap<>();
        currencyNames.put("USD", "United States Dollar");
        currencyNames.put("EUR", "Euro");
        currencyNames.put("GBP", "Pound Sterling");
        currencyNames.put("JPY", "Japanese Yen");
        currencyNames.put("AUD", "Australian Dollar");
        currencyNames.put("CAD", "Canadian Dollar");
        return currencyNames;
    }

    private static double getExchangeRate(JsonObject jsonObject, String targetCurrency) {
        // Acessar o objeto JSON que contém as taxas de câmbio
        JsonObject rates = jsonObject.getAsJsonObject("rates");

        // Verificar se a moeda de destino está presente nas taxas de câmbio
        if (rates.has(targetCurrency)) {
            return rates.get(targetCurrency).getAsDouble();
        } else {
            return -1;
        }
    }
}
