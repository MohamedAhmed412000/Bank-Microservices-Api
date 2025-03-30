package com.project.gateway.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallBackController {

    @GetMapping("/contact-support")
    public Mono<String> contactSupport() {
        return Mono.just("Service is currently unavailable. Please try again later.");
    }

}
