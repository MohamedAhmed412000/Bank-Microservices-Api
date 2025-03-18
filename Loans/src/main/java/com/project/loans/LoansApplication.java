package com.project.loans;

import com.project.loans.dto.CustomerSupportInfoDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = CustomerSupportInfoDto.class)
@OpenAPIDefinition(
    info = @Info(
        title = "Loans Microservice API Documentation",
        version = "1.0",
        description = "A simple Rest API for managing loans.",
        contact = @Contact(
            name = "API Support",
            url = "https://example.com/support",
            email = "support@example.com"
        ),
        license = @License(
            name = "Apache 2.0",
            url = "https://www.apache.org/licenses/LICENSE-2.0.html"
        )
    ),
    externalDocs = @ExternalDocumentation(
        description = "A simple REST API for managing loans",
        url = "https://github.com/example/loans-api"
    )
)
public class LoansApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoansApplication.class, args);
    }

}
