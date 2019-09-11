package fr.theia_land.in_situ.dataportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Annotation enabling three annotations - @EnableAutoConfiguration: enable Spring Boot’s auto-configuration mechanism
 * depending on the jar in the classpath - @ComponentScan: enable @Component scan on the package where the application
 * is located. Each @Component will wired in Spring IOC container. @ComponentScan also include @Configuration class. -
 *
 * @Configuration: allow to register extra beans in the context or import additional configuration classes
 */

@SpringBootApplication
public class DataportalApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(DataportalApplication.class, args);
    }

}
