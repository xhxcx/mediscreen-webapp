package com.mediscreen.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.mediscreen.webapp")
public class MediScreenWebAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(MediScreenWebAppApplication.class, args);
    }
}
