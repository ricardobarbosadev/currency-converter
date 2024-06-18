package com.curso_alura.coinconverter.main;

import com.curso_alura.coinconverter.http.HttpClient;
import com.curso_alura.coinconverter.http.HttpJsonResponse;
import com.curso_alura.coinconverter.http.HttpRequest;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.*;

public class CurrencyConverter {

    private static final String EXCHANGE_RATE_API_URL = "https://v6.exchangerate-api.com/v6/fd999e531529daf2bbedf589/pair/";
   private static final String OPEN_EXCHANGE_RATES_URL= "https://openexchangerates.org/api/currencies.json?app_id=da773b022be34e93a6c713704ca34f811";
   private static final List<String> ALLOWED_CURRENCIES = Arrays.asList("USD", "EUR", "BRL", "JPY", "CAD", "CZK", "INR", "CNY", "CUP", "AOA");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HttpClient httpClient = new HttpClient();

        try {
            // Obter a lista de moedas suportadas
            Map<String, String> currencyNames = getSupportedCurrencies(httpClient);

            if (currencyNames == null || currencyNames.isEmpty()) {
                System.out.println("Falha ao obter a lista de moedas suportadas.");
                return;
            }

            // Selecionar as moedas permitidas com seus nomes completos
            List<String> selectedCurrencies = new ArrayList<>();
            for (String currency : ALLOWED_CURRENCIES) {
                if (currencyNames.containsKey(currency)) {
                    selectedCurrencies.add(currency + " - " + currencyNames.get(currency));
                }
            }

            while (true) {
                // Exibir o menu de opções
                System.out.println("Benvindo ao LuxCoin Converter! Escolha uma das seguintes moedas como origem:");
                for (int i = 0; i < selectedCurrencies.size(); i++) {
                    System.out.printf("%d. %s%n", i + 1, selectedCurrencies.get(i));
                }

                System.out.println("0 - Sair do programa");
                System.out.print("Escolha uma opção: ");

                // Capturar a escolha do usuário
                int baseChoice = scanner.nextInt();

                if (baseChoice == 0) {
                    System.out.println("Saindo...");
                    break;
                }

                if (baseChoice < 1 || baseChoice > selectedCurrencies.size()) {
                    System.out.println("Opção inválida. Tente novamente.");
                    continue;
                }

                String selectedCurrency = selectedCurrencies.get(baseChoice - 1).split(" - ")[0];

                System.out.print("Digite o valor a ser convertido: ");
                double amount = scanner.nextDouble();
                scanner.nextLine();

                // Exibir o menu de opções para moeda de destino
                System.out.println("Escolha uma das seguintes moedas como destino:");
                for (int i = 0; i < selectedCurrencies.size(); i++) {
                    System.out.printf("%d. %s%n", i + 1, selectedCurrencies.get(i));
                }
                System.out.print("Escolha uma opção: ");

                // Capturar a escolha do usuário para moeda de destino
                int targetChoice = scanner.nextInt();
                scanner.nextLine();

                if (targetChoice < 1 || targetChoice > selectedCurrencies.size()) {
                    System.out.println("Opção inválida. Tente novamente.");
                    continue;
                }

                String targetCurrency = selectedCurrencies.get(targetChoice - 1).split(" - ")[0];

                // Fazer a requisição à API com as moedas  escolhidas
                try {
                    // Construir a URL da API com a moeda base fornecida
                    String apiUrlWithBaseAndTarget = EXCHANGE_RATE_API_URL + "/" + selectedCurrency + "/" + targetCurrency + "/" + amount;
                    System.out.println("Requesting URL: " + apiUrlWithBaseAndTarget);

                    // Fazer requisição HTTP
                    HttpRequest request = new HttpRequest(apiUrlWithBaseAndTarget, "GET");
                    String responseBody = httpClient.sendGetRequest(apiUrlWithBaseAndTarget);
                    System.out.println("Response: " + responseBody);

                    // Processar a resposta JSON
                    JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();


                    if (jsonObject == null || !jsonObject.has("conversion_rate")) {
                        System.out.println("Falha ao obter as taxas de câmbio. Verifique a resposta da API.");
                        continue;
                    }

                    // Obter a taxa de câmbio da moeda de destino
                    double exchangeRate = jsonObject.get("conversion_rate").getAsDouble();
                    double convertedAmount = jsonObject.get("conversion_result").getAsDouble();
                    System.out.println("Moeda de origem: " + selectedCurrency);
                    System.out.println("Moeda de destino: " + targetCurrency);
                    System.out.printf("%.2f %s = %.2f %s%n", amount, selectedCurrency, convertedAmount, targetCurrency);



                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        scanner.close();

    }
        private static Map<String, String> getSupportedCurrencies(HttpClient httpClient) throws Exception {
            Map<String, String> currencyNames = new HashMap<>();



            // Fazer requisição HTTP
            String responseBody = httpClient.sendGetRequest(OPEN_EXCHANGE_RATES_URL);



            // Processar a resposta JSON  segunda alternativa
            JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
            if (jsonObject != null) {
                for (String currency : ALLOWED_CURRENCIES) {
                    if (jsonObject.has(currency)) {
                        String currencyName = jsonObject.get(currency).getAsString();
                        currencyNames.put(currency, currencyName);
                    }
                }
            } else {
                System.out.println("Falha ao obter as moedas suportadas do Open Exchange Rates.");
            }

            return currencyNames;
        }

        }

