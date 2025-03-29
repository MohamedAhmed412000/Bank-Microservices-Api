package com.project.gateway.filters;

import com.project.gateway.constants.ApplicationConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class ResponseTraceFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            HttpHeaders headers = exchange.getRequest().getHeaders();
            String traceId = headers.getFirst(ApplicationConstants.REQUEST_HEADER_NAME);
            log.debug("Adding request trace id to the response : {}", traceId);
            exchange.getResponse().getHeaders().add(ApplicationConstants.REQUEST_HEADER_NAME, traceId);
        }));
    }
}
