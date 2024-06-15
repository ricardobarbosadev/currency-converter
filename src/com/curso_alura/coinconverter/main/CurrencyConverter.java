package com.curso_alura.coinconverter.main;

import com.curso_alura.coinconverter.http.HttpJsonResponse;
import com.curso_alura.coinconverter.http.HttpRequest;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CurrencyConverter {

    private static final String API_URL = "https://v6.exchangerate-api.com/v6/fd999e531529daf2bbedf589/pair/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Obter a lista de moedas suportadas
            Map<String, String> currencyNames = getSupportedCurrencies();

            if (currencyNames == null || currencyNames.isEmpty()) {
                System.out.println("Falha ao obter a lista de moedas suportadas.");
                return;
            }

            while (true) {
                // Exibir o menu de opções
                System.out.println("Benvindo ao LuxCoin Converter! Escolha uma das seguintes moedas como origem:");
                for (Map.Entry<String, String> entry : currencyNames.entrySet()) {
                    System.out.println(entry.getKey() + " - " + entry.getValue());
                }
                System.out.println("0 - Sair do programa");
                System.out.print("Escolha uma opção: ");

                // Capturar a escolha do usuário
                String baseCurrency = scanner.nextLine().trim().toUpperCase();

                if (baseCurrency.equalsIgnoreCase("0")) {
                    System.out.println("Saindo...");
                    break;
                }

                if (!currencyNames.containsKey(baseCurrency)) {
                    System.out.println("Opção inválida. Tente novamente.");
                    continue;
                }

                System.out.print("Digite o valor a ser convertido: ");
                double amount = scanner.nextDouble();
                scanner.nextLine();

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

                // Fazer a requisição à API com as moedas  escolhidas
                try {
                    // Construir a URL da API com a moeda base fornecida
                    String apiUrlWithBaseAndTarget = API_URL + "/" + baseCurrency + "/" + targetCurrency + "/" + amount;

                    // Fazer requisição HTTP
                    HttpRequest request = new HttpRequest(apiUrlWithBaseAndTarget, "GET");
                    String responseBody = request.execute();
                    System.out.println("Response: " + responseBody);

                    // Processar a resposta JSON
                    HttpJsonResponse response = new HttpJsonResponse(200, null, responseBody);
                    JsonObject jsonObject = response.getJsonObject();

                    if (jsonObject == null || !jsonObject.has("conversion_rate")) {
                        System.out.println("Falha ao obter as taxas de câmbio. Verifique a resposta da API.");
                        continue;
                    }

                    // Obter a taxa de câmbio da moeda de destino
                    double exchangeRate = jsonObject.get("conversion_rate").getAsDouble();
                    double convertedAmount = amount * exchangeRate;

                    System.out.printf("%.2f %s = %.2f %s%n", amount, baseCurrency, convertedAmount, targetCurrency);



                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        scanner.close();

    }
        private static Map<String, String> getSupportedCurrencies() throws Exception {
            Map<String, String> currencyNames = new HashMap<>();

            // URL para obter as moedas disponíveis
            String apiUrl = "https://v6.exchangerate-api.com/v6/fd999e531529daf2bbedf589/latest/USD";

            // Fazer requisição HTTP
            HttpRequest request = new HttpRequest(apiUrl, "GET");
            String responseBody = request.execute();

            // Processar a resposta JSON
            HttpJsonResponse response = new HttpJsonResponse(200, null, responseBody);
            JsonObject jsonObject = response.getJsonObject();

            // Verificar se o objeto JSON contém "rates"
            if (jsonObject != null && jsonObject.has("conversion_rates")) {
                // Obter o objeto JSON que contém as taxas de câmbio
                JsonObject rates = jsonObject.getAsJsonObject("conversion_rates");

                if (rates != null) {
                    // Adicionar as moedas e seus nomes ao mapa (para simplificação, apenas o código da moeda é usado)
                    for (Map.Entry<String, com.google.gson.JsonElement> entry : rates.entrySet()) {
                        currencyNames.put(entry.getKey(), entry.getKey()); // Para obter o nome real, você pode precisar de outra API ou um mapeamento estático
                    }
                } else {
                    System.out.println("O objeto 'rates' é nulo na resposta da API.");
                }
            } else {
                System.out.println("Falha ao obter as taxas de câmbio. Verifique a resposta da API.");
            }

            return currencyNames;
        }

        }


