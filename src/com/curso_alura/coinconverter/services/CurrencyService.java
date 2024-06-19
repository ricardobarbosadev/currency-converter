package com.curso_alura.coinconverter.services;

import com.curso_alura.coinconverter.http.HttpClient;
import com.curso_alura.coinconverter.http.HttpResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrencyService {

    private static final String EXCHANGE_RATE_API_URL = "https://v6.exchangerate-api.com/v6/fd999e531529daf2bbedf589/pair/";
    private static final String OPEN_EXCHANGE_RATES_URL = "https://openexchangerates.org/api/currencies.json?app_id=da773b022be34e93a6c713704ca34f81";
    private static final List<String> ALLOWED_CURRENCIES = List.of("USD", "EUR", "BRL", "JPY", "CAD", "CZK", "INR", "CNY", "CUP", "AOA");

    private final HttpClient httpClient;

    public CurrencyService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Map<String, String> getSupportedCurrencies() throws Exception {
        Map<String, String> currencyNames = new HashMap<>();

        HttpResponse httpResponse = httpClient.sendGetRequest(OPEN_EXCHANGE_RATES_URL);
        String responseBody = httpResponse.getBody();

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

    public double convertCurrency(String baseCurrency, String targetCurrency, double amount) throws Exception {
        String apiUrlWithBaseAndTarget = EXCHANGE_RATE_API_URL + baseCurrency + "/" + targetCurrency + "/" + amount;
        HttpResponse httpResponse = httpClient.sendGetRequest(apiUrlWithBaseAndTarget);
        String responseBody = httpResponse.getBody();

        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        if (jsonObject == null || !jsonObject.has("conversion_rate")) {
            throw new RuntimeException("Falha ao obter as taxas de câmbio. Verifique a resposta da API.");
        }

        return jsonObject.get("conversion_result").getAsDouble();
    }
}