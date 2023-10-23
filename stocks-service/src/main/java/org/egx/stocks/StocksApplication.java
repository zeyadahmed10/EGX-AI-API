package org.egx.stocks;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient
@SpringBootApplication
public class StocksApplication {

    public static void main(String[] args){
        SpringApplication.run(StocksApplication.class, args);
    }

//        @Bean
//        CommandLineRunner commandLineRunner(HistoricalStockRepository repo){
//        return args -> {
//            Pageable pageable = PageRequest.of(0, 10);
//            var data = repo.findOHCLVForEquity("ISMA", "10 minutes", "22 days", pageable);
//            System.out.println(data);
//            System.out.println(data.toList());
//            var x = data.toList();
//            System.out.println("we are here");
////            var y = repo.test("ISMA", "10 minutes", "22 days", pageable);
////            System.out.println(y.toList());
////            System.out.println("here");
////         var stats = data.stream().map( (x)-> new OHCLVStatistics(
////                    x.getTime(),
////                    x.getCurrPrice(),
////                    x.getOpen(),
////                    x.getHighest(),
////                    x.getPrevClose(),
////                    x.getLowest(),
////                    x.getVolume()
////            )).collect(Collectors.toList());
////            System.out.println(stats);
////            System.out.println(stats.size());
////        };
//        };

}

