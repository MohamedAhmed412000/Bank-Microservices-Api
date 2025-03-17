package com.project.accounts;

import com.project.accounts.dto.CustomerSupportInfoDto;
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
@EnableConfigurationProperties(value = {CustomerSupportInfoDto.class})
@OpenAPIDefinition(
	info = @Info(
		title = "Accounts Microservice API Documentation",
		version = "1.0",
		description = "A simple Rest API for managing accounts.",
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
		description = "A simple REST API for managing accounts",
		url = "https://github.com/example/accounts-api"
	)
)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}


