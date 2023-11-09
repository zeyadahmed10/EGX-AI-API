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
    @KafkaListener(containerFactory ="stockKafkaListenerContainerFactory",topics="scrapedStocks", groupId = "notification-service-stock-group", properties = {"spring.json.value.default.type=org.egx.clients.io.ScrapedStock"})
    void stockListener(ScrapedStock scrapedStock) throws JsonProcessingException {
        //TODO: notify subscribers
        List<SubscribedUser> subscribers = subscribedUserRepository.
                findStockSubscribedUsersByReutersCode(scrapedStock.getReutersCode());
        for(var user: subscribers){
            emailService.sendEmail(user.getEmail(),"","");
        }

    }

    @KafkaListener(containerFactory ="newsKafkaListenerContainerFactory",topics="scrapedNews", groupId = "notification-service-news-group", properties = {"spring.json.value.default.type=org.egx.clients.io.ScrapedNews"})
    void newsListener(ScrapedNews scrapedNews) throws JsonProcessingException {
        //TODO: notify subscribers
        List<SubscribedUser> subscribers = subscribedUserRepository.
                findNewsSubscribedUsersByReutersCode(scrapedNews.getReutersCode());
        for(var user: subscribers){
            emailService.sendEmail(user.getEmail(),"","");
    }
}
