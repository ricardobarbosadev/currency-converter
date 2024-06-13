package com.curso_alura.coinconverter.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpRequest {
    private String url;
    private String method;
    private String requestBody;
    private Map<String, String> headers;
    private Map<String, String> queryParams;

    public HttpRequest(String url, String method) {
        this.url = url;
        this.method = method;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setQueryParams(Map<String, String> queryParams) {
        this.queryParams = queryParams;
    }

    public String execute() throws Exception {
        URL obj = new URL(buildUrlWithParams());
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod(method);

        // Set headers
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                con.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }

        // Handle request body for POST/PUT requests
        if (requestBody != null && (method.equals("POST") || method.equals("PUT"))) {
            con.setDoOutput(true);
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
        }

        int responseCode = con.getResponseCode();
        System.out.println("Sending '" + method + "' request to URL: " + url);
        System.out.println("Response Code: " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    private String buildUrlWithParams() {
        if (queryParams == null || queryParams.isEmpty()) {
            return url;
        }

        StringBuilder urlString = new StringBuilder(url);
        boolean first = true;
        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            if (first) {
                urlString.append("?");
                first = false;
            } else {
                urlString.append("&");
            }
            urlString.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return urlString.toString();
    }
}
