package br.com.masterclass.superpecas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("SuperPecas API")
                        .description("API para o sistema Super Peças")
                        .version("1.0.0")
                        .contact(new Contact().name("Nome do Contato").email("contato@superpecas.com"))
                        .license(new License().name("MIT").url("https://opensource.org/licenses/MIT")));
    }
}
