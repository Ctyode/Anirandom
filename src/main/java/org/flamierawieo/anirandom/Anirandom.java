package org.flamierawieo.anirandom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

@SpringBootApplication
public class Anirandom extends SpringBootServletInitializer {

    // TODO: remove unused import statements
    // TODO: remove unnecessary code (classes, methods, etc.)
    // TODO: bundle controllers by target model

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Anirandom.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Anirandom.class, args);
    }

}
