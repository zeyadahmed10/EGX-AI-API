package org.egx.auth;

import org.egx.auth.dto.SignUpDto;
import org.egx.auth.services.KeyCloakService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthApplication {

    public static void main(String[] args){
        SpringApplication.run(AuthApplication.class, args);
    }
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
//    @Bean
//    CommandLineRunner commandLineRunner(KeyCloakService keyCloakService){
//        return args -> {
//
//          keyCloakService.createUser(new SignUpDto("hesham", "zeyad","ahmed", "emial@gmail.com","password","password","user"));
//        };
//    }
}
