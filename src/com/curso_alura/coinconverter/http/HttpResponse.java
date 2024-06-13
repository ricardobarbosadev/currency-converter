package com.curso_alura.coinconverter.http;

import java.util.Map;

public interface HttpResponse {
    int getStatusCode();

    Map<String, String> getHeaders();

    String getBody();
}
