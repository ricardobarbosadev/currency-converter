package com.curso_alura.coinconverter.http;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.Map;

public class HttpJsonResponse implements HttpResponse{
    private int statusCode;
    private Map<String, String> headers;
    private String body;

    public HttpJsonResponse(int statusCode, Map<String, String> headers, String body) {
        this.statusCode = statusCode;
        this.headers = headers;
        this.body = body;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    public String getBody() {
        return body;
    }

   /* public JsonObject getJsonObject() {
        Gson gson = new Gson();
        return gson.fromJson(body, JsonObject.class);
    }*/


}
