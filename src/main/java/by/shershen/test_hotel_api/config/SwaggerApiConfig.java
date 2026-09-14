package by.shershen.test_hotel_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SwaggerApiConfig {

    @Bean
    public OpenAPI hotelApiSwaggerAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Hotel API")
                        .description("RESTful API to work with hotels")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Uladzislau Shershan")));
    }
}
