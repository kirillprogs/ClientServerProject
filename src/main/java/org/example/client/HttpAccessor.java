package org.example.client;

import java.net.http.HttpClient;

public class HttpAccessor {

    private HttpClient httpClient;

    public HttpAccessor() {
        httpClient = HttpClient.newHttpClient();
    }
}