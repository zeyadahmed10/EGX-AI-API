package org.egx.notifications.kafka;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egx.clients.io.ScrapedNews;
import org.egx.clients.io.ScrapedStock;
import org.egx.notifications.entity.SubscribedUser;
import org.egx.notifications.repos.SubscribedUserRepository;
import org.egx.notifications.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaListeners {
    @Autowired
    private final EmailService emailService;
    @Autowired
    private final SubscribedUserRepository subscribedUserRepository;

    String getNewsEmailBody(String reutersCode, String title, String name) {
        return "Dear " + name + ",\n" +
                "\n" +
                "We are writing to inform you about the latest news updates regarding the stock with Reuters code " + reutersCode + ". Here are the highlights: \n"
                + title + "\nFor more details and the complete news coverage, please visit our EGX-API or refer to attached news.";
    }
    String getStockEmailBody(ScrapedStock stock, String name){
        return "Dear "+name+",\n" +
                "\n" +
                "We would like to inform you about a recent price change in the stock with Reuters code: "+stock.getReutersCode()+". Here are the details:\n" +
                "\n" +
                "Current Price: "+String.valueOf(stock.getCurrPrice())+"\n" +
                "Change: "+String.valueOf(stock.getRateOfChange())+"\n" +
                "Percentage Change: "+String.valueOf(stock.getPercentageOfChange())+"\n" +
                "Please note that these values are based on the most recent updates and may be subject to further fluctuations.";
    }
    @KafkaListener(containerFactory = "stockKafkaListenerContainerFactory", topics = "scrapedStocks", groupId = "notification-service-stock-group", properties = {"spring.json.value.default.type=org.egx.clients.io.ScrapedStock"})
    void stockListener(ScrapedStock scrapedStock) throws JsonProcessingException {
        //TODO: notify subscribers
        if(Double.compare(0.0, scrapedStock.getRateOfChange())==0){
            return;
        }
        List<SubscribedUser> subscribers = subscribedUserRepository.
                findStockSubscribedUsersByReutersCode(scrapedStock.getReutersCode());
        for (var user : subscribers) {
            emailService.sendEmail(user.getEmail(), "New price change in: "+scrapedStock.getReutersCode(), getStockEmailBody(scrapedStock, user.getName()));
        }

    }

    @KafkaListener(containerFactory = "newsKafkaListenerContainerFactory", topics = "scrapedNews", groupId = "notification-service-news-group", properties = {"spring.json.value.default.type=org.egx.clients.io.ScrapedNews"})
    void newsListener(ScrapedNews scrapedNews) throws JsonProcessingException {
        //TODO: notify subscribers
        List<SubscribedUser> subscribers = subscribedUserRepository.
                findNewsSubscribedUsersByReutersCode(scrapedNews.getReutersCode());
        for (var user : subscribers) {
            emailService.sendEmail(user.getEmail(), "News Update for :"+scrapedNews.getReutersCode(), getNewsEmailBody(scrapedNews.getReutersCode(), scrapedNews.getTitle(), user.getName()));
        }
    }
}
