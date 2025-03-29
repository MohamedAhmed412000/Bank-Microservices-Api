package com.project.gateway.filters;

import com.project.gateway.constants.ApplicationConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Order(1)
@Component
@Slf4j
public class RequestTraceFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (isRequestContainsTraceId(exchange)) {
            log.debug("Request trace header found with value : {}", getTraceIdFromRequest(exchange));
        } else {
            String traceId = addTraceIdToRequest(exchange);
            log.debug("Generated new request trace header with value : {}", traceId);
        }
        return chain.filter(exchange);
    }

    private boolean isRequestContainsTraceId(ServerWebExchange exchange) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        return headers.get(ApplicationConstants.REQUEST_HEADER_NAME) != null;
    }

    private String getTraceIdFromRequest(ServerWebExchange exchange) {
        return exchange.getRequest().getHeaders().getFirst(ApplicationConstants.REQUEST_HEADER_NAME);
    }

    private String addTraceIdToRequest(ServerWebExchange exchange) {
        String traceId = generateTraceId();
        exchange.mutate().request(exchange.getRequest().mutate().header(
            ApplicationConstants.REQUEST_HEADER_NAME, traceId).build()).build();
        return traceId;
    }

    private String generateTraceId() {
        return UUID.randomUUID().toString();
    }

}
