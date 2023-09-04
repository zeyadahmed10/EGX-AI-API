package org.egx.scraping;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ScrapingApplication {
    public static void main(String[] args){

        SpringApplication.run(ScrapingApplication.class, args);
    }
}
