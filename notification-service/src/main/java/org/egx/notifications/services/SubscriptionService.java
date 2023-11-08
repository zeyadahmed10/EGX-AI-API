package org.egx.notifications.services;

import exceptions.ResourceNotFoundException;
import org.egx.notifications.dto.SubscriptionRequest;
import org.egx.notifications.entity.StockSubscription;
import org.egx.notifications.entity.StockSubscriptionId;
import org.egx.notifications.entity.SubscribedUser;
import org.egx.notifications.repos.EquityRepository;
import org.egx.notifications.repos.StockSubscriptionRepository;
import org.egx.notifications.repos.SubscribedUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {

    @Autowired
    private SubscribedUserRepository subscribedUserRepository;
    @Autowired
    private EquityRepository equityRepository;
    @Autowired
    private StockSubscriptionRepository stockSubscriptionRepository;
    public void subscribe(String userEmail, SubscriptionRequest subscriptionRequest) {
        SubscribedUser subscribedUser = subscribedUserRepository.findByEmail(userEmail).orElseGet(() -> {
            return subscribedUserRepository
                    .save(SubscribedUser.builder().email(userEmail).build());
        });
        var equity = equityRepository.findByReutersCode(subscriptionRequest.getReutersCode()).orElseThrow(
                ()-> new ResourceNotFoundException("No equity found with code: "+ subscriptionRequest.getReutersCode())
        );
        //do subscription
        StockSubscriptionId subscriptionEntry = new StockSubscriptionId(equity.getId(), subscribedUser.getId());
        var isExistedSubscription = stockSubscriptionRepository.findById(subscriptionEntry);
        if(isExistedSubscription.isEmpty()) {
            stockSubscriptionRepository.save(StockSubscription.builder().id(subscriptionEntry).build());
        }
    }
}
