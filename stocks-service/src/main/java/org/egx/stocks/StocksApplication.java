package org.egx.stocks;

import org.egx.stocks.entity.UpdatedStock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class StocksApplication {

    public static void main(String[] args){
        SpringApplication.run(StocksApplication.class, args);
    }
}
