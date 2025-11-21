package com.coderhouse.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class ApiConfig {

    @Bean
     OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API REST Full | Java | Trabajo Final")
                        .description("La API REST proporciona endpoints para administrar clientes y "
                                + "productos. Permite realizar operaciones "
                                + "CRUD (Crear, Leer, Actualizar, Eliminar) tanto para clientes como "
                                + "para productos. Los endpoints permiten listar, agregar, mostrar, "
                                + "editar y eliminar productos y clientes. La API está documentada utilizando "
                                + "Swagger, lo que facilita la comprensión de los endpoints y su uso.")
                        .contact(new Contact()
                                .name("Castillo Mayco")
                                .email("maycoangelocastillo@gmail.com")
                        )
                )
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor Local")
                ))
                .externalDocs(new ExternalDocumentation()
                        .url("https://github.com/Maycomc01/java-segunda-entrega")
                );
    }
}
