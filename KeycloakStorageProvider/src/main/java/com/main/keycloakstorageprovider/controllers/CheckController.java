package com.main.keycloakstorageprovider.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.main.keycloakstorageprovider.model.UserDto;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.keycloak.broker.provider.util.SimpleHttp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/check")
public class CheckController {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    @GetMapping
    public ResponseEntity<UserDto> check() throws IOException {

        CloseableHttpClient customHttpClient = HttpClients.custom().build();
        HttpGet httpGet = new HttpGet("http://localhost:8084/users/loreana@hotmail.com");
        HttpResponse httpResponse = customHttpClient.execute(httpGet);
        String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
        UserDto user = objectMapper.readValue(jsonResponse, UserDto.class);

        customHttpClient.close();
        return ResponseEntity.ok(user);
    }

}
