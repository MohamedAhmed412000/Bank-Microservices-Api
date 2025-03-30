package com.project.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class RateLimitingConfig {

    @Bean
    public RedisRateLimiter redisRateLimiter() {
        // First Param: Specifies the maximum number of requests to be allowed in 1 second
        // Second Param: Specifies the maximum number of requests to be allowed before refreshing the tokens
        // Third Param: Specifies the wight of requests in terms of tokens
        // In this case, we're allowing 1 request per second and resetting the limit after 1 second also
        return new RedisRateLimiter(1, 1, 1);
    }

    @Bean
    public KeyResolver userKeyResolver() {
        return exchange -> Mono.justOrEmpty(
            exchange.getRequest().getHeaders().getFirst("User-Agent"))
            .defaultIfEmpty("Anonymous");
    }

}
