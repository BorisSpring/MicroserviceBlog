package com.main.gatewayblog.filters;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Component
@Order(1)
public class RequestTraceFilter implements GlobalFilter {

    private FilterUtility filterUtility;
    public RequestTraceFilter(FilterUtility filterUtility) {
        this.filterUtility = filterUtility;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String corelationId = filterUtility.getCorelationId(exchange.getRequest().getHeaders());


        if(corelationId != null){
            System.out.println("Corelation id is : " + corelationId);
        }else {
            String corelation_id = UUID.randomUUID().toString();
            System.out.println("Added corelation id header !");
            exchange = filterUtility.addHeader(exchange, FilterUtility.CORELATION_ID_HEADERS,corelation_id);
        }

        // Ispisivanje svih headera
        System.out.println("All headers:");
        ServerHttpRequest request = exchange.getRequest();
        request.getHeaders().forEach((key, values) -> {
            System.out.println(key + ": " + values);
        });

       return chain.filter(exchange);
    }
}
