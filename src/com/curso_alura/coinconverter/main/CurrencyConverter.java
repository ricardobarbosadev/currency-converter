package com.curso_alura.coinconverter.main;

import com.curso_alura.coinconverter.http.HttpClient;
import com.curso_alura.coinconverter.services.CurrencyService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CurrencyConverter {

    private static final List<String> ALLOWED_CURRENCIES = List.of("USD", "EUR", "BRL", "JPY", "CAD", "CZK", "INR", "CNY", "CUP", "AOA");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HttpClient httpClient = new HttpClient();
        CurrencyService currencyService = new CurrencyService(httpClient);

        try {
            Map<String, String> currencyNames = currencyService.getSupportedCurrencies();

            if (currencyNames.isEmpty()) {
                System.out.println("Falha ao obter a lista de moedas suportadas.");
                return;
            }

            List<String> selectedCurrencies = new ArrayList<>();
            for (String currency : ALLOWED_CURRENCIES) {
                if (currencyNames.containsKey(currency)) {
                    selectedCurrencies.add(currency + " - " + currencyNames.get(currency));
                }
            }

            while (true) {
                System.out.println("Benvindo ao LuxCoin Converter! Escolha uma das seguintes moedas como origem:");
                for (int i = 0; i < selectedCurrencies.size(); i++) {
                    System.out.printf("%d. %s%n", i + 1, selectedCurrencies.get(i));
                }

                System.out.println("0 - Sair do programa");
                System.out.print("Escolha uma opção: ");

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

                System.out.println("Escolha uma das seguintes moedas como destino:");
                for (int i = 0; i < selectedCurrencies.size(); i++) {
                    System.out.printf("%d. %s%n", i + 1, selectedCurrencies.get(i));
                }
                System.out.print("Escolha uma opção: ");

                int targetChoice = scanner.nextInt();
                scanner.nextLine();

                if (targetChoice < 1 || targetChoice > selectedCurrencies.size()) {
                    System.out.println("Opção inválida. Tente novamente.");
                    continue;
                }

                String targetCurrency = selectedCurrencies.get(targetChoice - 1).split(" - ")[0];

                try {
                    double convertedAmount = currencyService.convertCurrency(selectedCurrency, targetCurrency, amount);
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
}