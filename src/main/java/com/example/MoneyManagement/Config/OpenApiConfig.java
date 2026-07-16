package com.example.MoneyManagement.Config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    public OpenAPI moneyManagementOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Money Management REST API")
                        .description(
                                """
                                Enterprise-level REST API for the Money Management System.

                                Features:
                                • User Authentication (JWT)
                                • Income Management
                                • Expense Management
                                • Category Management
                                • Dashboard & Reports
                                • File Upload
                                • Email Notifications
                                """
                        )
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Money Management Development Team")
                                .email("support@moneymanagement.com")
                                .url("https://moneymanagement.com"))
                        .license(new License()
                                .name("Apache License 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))

                .externalDocs(new ExternalDocumentation()
                        .description("Project Documentation")
                        .url("https://moneymanagement.com/docs"));

    }
}
