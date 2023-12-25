package com.main.keycloakstorageprovider.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.main.keycloakstorageprovider.model.UserDto;
import com.main.keycloakstorageprovider.model.VerifyPasswordResponse;
import jakarta.ws.rs.PathParam;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.keycloak.broker.provider.util.SimpleHttp;
import org.keycloak.models.KeycloakSession;
import org.springframework.beans.factory.annotation.Value;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Getter
@Setter
@Slf4j
public class UserLegacyService {

    KeycloakSession session;

    @Value("${authServiceUrl}")
    private String authServiceUrl;

    CloseableHttpClient client = HttpClients.custom().build();

    ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    public UserLegacyService(KeycloakSession session) {
        this.session = session;
    }
    UserDto getUserByUserName(@PathParam("username") String username) {
        try {
//            return SimpleHttp.doGet(authServiceUrl + "users/" +  username, this.session).asJson(UserDto.class);
            HttpGet httpGet = new HttpGet("http://localhost:8084/users/" + username);
            HttpResponse httpResponse = client.execute(httpGet);
            String string = EntityUtils.toString(httpResponse.getEntity());
           return  objectMapper.readValue(string, UserDto.class);
        } catch (IOException e) {
            log.warn("Error fetching user " + username + " from external service: " + e.getMessage(), e);
        }
        return null;
    }
    VerifyPasswordResponse verifyUserPassword(@PathParam("username") String username, String password) throws UnsupportedEncodingException {
//        SimpleHttp simpleHttp = SimpleHttp.doPost("http://localhost:8084/users/" + username + "/verify-password",
//                this.session);

        HttpPost httpPost = new HttpPost("http://localhost:8084/users/" + username + "/verify-password");
        String requestBody = "password=" + password;
        StringEntity entity = new StringEntity(requestBody);

        // Add any headers if needed
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        try {
            CloseableHttpResponse execute = client.execute(httpPost);
            String response = EntityUtils.toString(execute.getEntity());
            ObjectMapper mapper = new ObjectMapper();
            // Convert the JSON string to a VerifyPasswordResponse object
           return mapper.readValue(response, VerifyPasswordResponse.class);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
