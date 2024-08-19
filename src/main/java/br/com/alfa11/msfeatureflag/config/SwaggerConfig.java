package br.com.alfa11.msfeatureflag.config;



import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().info(new Info().title("MS-Feature-Flag Alfa11")
                .description("Está é uma aplcicação de referência para um microsserviço responsável por implementar a funcionalidad de Feature Flag. Além de permitir o CRUD completo de FEATURE, também implementa o conceito de FILTER, ou seja, uma allow list ou deny list. Também são implementadas as funcionalidades de TESTES A/B e migração de aplicações. Para mais detalhes consulte a documentação no repo GIT.")
                .version("v1.0")
                .contact(getContactDetails()));
    }

    private Contact getContactDetails() {
        return new Contact().name("Elcio Abrahão")
                .email("elcioabrahao@alumni.usp.br")
                .url("https://alfa11.com.br");
    }
}