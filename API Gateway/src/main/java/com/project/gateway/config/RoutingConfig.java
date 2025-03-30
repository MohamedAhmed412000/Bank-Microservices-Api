package com.project.gateway.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.loadbalancer.reactive.ReactiveLoadBalancer;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.springframework.cloud.gateway.support.RouteMetadataUtils.CONNECT_TIMEOUT_ATTR;
import static org.springframework.cloud.gateway.support.RouteMetadataUtils.RESPONSE_TIMEOUT_ATTR;

@Configuration
public class RoutingConfig {

    @Bean
    RouteLocator generateRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
            .route(p -> p
                .path("/api/v1/accounts/**", "/api/v1/customer/**")
                .filters(f -> f
                    .rewritePath("/(?<api>.*)", "/${api}")
                    .addResponseHeader("X-RESPONSE-TIME", LocalDateTime.now().toString())
                )
                .uri("lb://ACCOUNTS")
            )
            .route(p -> p
                .path("/accounts/**")
                .filters(f -> f
                    .rewritePath("/accounts/(?<api>.*)", "/${api}")
                    .circuitBreaker(config -> config
                        .setName("accountsCircuitBreaker").setFallbackUri("forward:/contact-support")
                    )
                )
                .uri("lb://ACCOUNTS")
            )
            .route(p -> p
                .path("/api/v1/cards/**")
                .filters(f -> f
                    .rewritePath("/(?<api>.*)", "/${api}")
                    .addResponseHeader("X-RESPONSE-TIME", LocalDateTime.now().toString())
                    .retry(retryConfig -> retryConfig
                        .setRetries(3)
                        .setMethods(HttpMethod.GET)
                        .setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true)
                    )
                )
                .uri("lb://CARDS")
            )
            .route(p -> p
                .path("/cards/**")
                .filters(f -> f
                    .rewritePath("/cards/(?<api>.*)", "/${api}")
                )
                .uri("lb://CARDS")
            )
            .route(p -> p
                .path("/api/v1/loans/**")
                .filters(f -> f
                    .rewritePath("/(?<api>.*)", "/${api}")
                    .addResponseHeader("X-RESPONSE-TIME", LocalDateTime.now().toString())
                )
                // Configure custom timeout per loans route only
                .metadata(CONNECT_TIMEOUT_ATTR, 1000)
                .metadata(RESPONSE_TIMEOUT_ATTR, 5000)
                .uri("lb://LOANS")
            )
            .route(p -> p
                .path("/loans/**")
                .filters(f -> f
                    .rewritePath("/loans/(?<api>.*)", "/${api}")
                    .circuitBreaker(config -> config
                        .setName("loansCircuitBreaker").setFallbackUri("forward:/contact-support")
                    )
                )
                .uri("lb://LOANS")
            )
            .build();
    }

    // Configure circuit breaker default timeout duration to be 5s instead of 1s
    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> factory
            .configureDefault(id -> new Resilience4JConfigBuilder(id)
                .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(5)).build())
                .build()
            );
    }

}
