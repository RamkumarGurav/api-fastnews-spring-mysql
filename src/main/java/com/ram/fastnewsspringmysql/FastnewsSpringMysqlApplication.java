package com.ram.fastnewsspringmysql;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef ="auditAwareImpl")//bean name on AuditAwareImpl class
@OpenAPIDefinition(
		info = @Info(
				title = "fastnews API",
				version = "1.0.0",
				description = "It's an API for News website",
				termsOfService = "https://portfolio-ramkumargurav.vercel.app/",
				contact = @Contact(
						name = "Ramkumar Gurav",
						email = "ramkumarsgurav@gmail.com"
				),
				license = @License(
						name = "license",
						url = "https://portfolio-ramkumargurav.vercel.app/"

				)
		)
)
public class FastnewsSpringMysqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(FastnewsSpringMysqlApplication.class, args);
	}

}
