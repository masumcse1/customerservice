package com.opt;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Customer Service  Rest API Documentation",
                description = "Customer Service REST API Documentation",
                version = "v41.0",
                contact = @Contact(
                        name = "masum",
                        email = "gulam.samdani.jee@gmail.com",
                        url = "www.abc.com"
                ),
                license = @License(
                        name = "Apache 25.0",
                        url = "https://www.gulam.net/license"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "Customer Management Documentation",
                url = "https://www.gulam.net/customer_management.html"
        )
)
public class CustomerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }

}
