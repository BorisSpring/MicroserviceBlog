package com.main.gatewayblog.filters;


import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

@Component
public class FilterUtility {

    public static final String CORELATION_ID_HEADERS = "Corelation-Id";

    public String getCorelationId(HttpHeaders httpHeaders){
        List<String> headers = httpHeaders.get(CORELATION_ID_HEADERS);

        if(headers == null || headers.isEmpty() )
            return null;

        return  headers.get(0);
    }

    public ServerWebExchange addHeader(ServerWebExchange exchange, String headerName, String headerValue){
        return  exchange.mutate().request(exchange.getRequest().mutate().header(headerName, headerValue).build()).build();
    }
}
