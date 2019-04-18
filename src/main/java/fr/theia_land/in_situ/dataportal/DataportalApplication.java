package fr.theia_land.in_situ.dataportal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Annotation enabling three annotations - @EnableAutoConfiguration: enable Spring Boot’s auto-configuration mechanism
 * depending on the jar in the classpath - @ComponentScan: enable @Component scan on the package where the application
 * is located. Each @Component will wired in Spring IOC container. @ComponentScan also include @Configuration class. -
 *
 * @Configuration: allow to register extra beans in the context or import additional configuration classes
 */
@RestController
@SpringBootApplication
public class DataportalApplication {

    @Value("${app.api_host}")
    public String apiHost;

    public static void main(String[] args) {
        SpringApplication.run(DataportalApplication.class, args);
    }

    @GetMapping("/message")
    String message() {
        
        return "Spring Boot api host : "+ apiHost;
    }
}
