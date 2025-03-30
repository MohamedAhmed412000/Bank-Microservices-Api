package com.project.loans.controllers;

import com.project.loans.dto.CustomerSupportInfoDto;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@Slf4j
@Hidden
@RestController
@RequiredArgsConstructor
public class DocsController {

    @Value("${build.version}")
    private String buildVersion;

    private final Environment environment;
    private final CustomerSupportInfoDto customerSupportInfoDto;

    @Retry(name = "getBuildVersion", fallbackMethod = "getBuildVersionFallback")
    @GetMapping(value = "/api/build-version", produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> getBuildVersion() throws TimeoutException {
        log.debug("getBuildVersion() method invoked");
        throw new TimeoutException();
//        return ResponseEntity.ok(buildVersion);
    }

    private ResponseEntity<String> getBuildVersionFallback(Throwable throwable) {
        log.debug("getBuildVersion() method fallback invoked");
        return ResponseEntity.ok("1.1.0");
    }

    @GetMapping(value = "/api/java-config", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> getJavaConfig() {
        Map<String, String> javaConf = new HashMap<>();
        javaConf.put("maven.home", environment.getProperty("MAVEN_HOME"));
        javaConf.put("java.version", environment.getProperty("JAVA_VERSION"));
        javaConf.put("java.home", environment.getProperty("JAVA_HOME"));
        return ResponseEntity.ok(javaConf);
    }

    @GetMapping(value = "/api/support-info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerSupportInfoDto> getCustomerSupportInfo() {
        return ResponseEntity.ok(customerSupportInfoDto);
    }

    @GetMapping("/")
    public RedirectView redirectToSwagger() {
        return new RedirectView("/swagger-ui.html");
    }

}
