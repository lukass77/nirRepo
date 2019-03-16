package com.eci.nir.targil2;

import com.eci.nir.targil2.config.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ApplicationConfig.class})
public class Targil2Application {

    public static void main(String[] args) {
        SpringApplication.run(Targil2Application.class, args);
    }

}
