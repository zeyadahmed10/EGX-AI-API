package org.egx.notifications.services;

import exceptions.ResourceNotFoundException;
import org.egx.notifications.dto.SubscriptionRequest;
import org.egx.notifications.entity.StockSubscription;
import org.egx.notifications.entity.SubscribedUser;
import org.egx.notifications.entity.SubscriptionId;
import org.egx.notifications.repos.EquityRepository;
import org.egx.notifications.repos.StockSubscriptionRepository;
import org.egx.notifications.repos.SubscribedUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockSubscriptionService implements SubscriptionService {
    @Autowired
    private SubscribedUserRepository subscribedUserRepository;
    @Autowired
    private EquityRepository equityRepository;
    @Autowired
    private StockSubscriptionRepository subscriptionRepository;

    public void subscribe(String userEmail, SubscriptionRequest subscriptionRequest){

        SubscribedUser subscribedUser = subscribedUserRepository.findByEmail(userEmail).orElseGet(() -> {
            return subscribedUserRepository
                    .save(SubscribedUser.builder().email(userEmail).build());
        });
        var equity = equityRepository.findByReutersCode(subscriptionRequest.getReutersCode()).orElseThrow(
                ()-> new ResourceNotFoundException("No equity found with code: "+ subscriptionRequest.getReutersCode())
        );
        //do subscription
        var subscriptionEntry = new SubscriptionId(equity.getId(), subscribedUser.getId());
        var isExistedSubscription = subscriptionRepository.findById(subscriptionEntry);
        if(isExistedSubscription.isEmpty()) {
            subscriptionRepository.save(new StockSubscription(subscriptionEntry));
        }
    }
    public void removeSubscription(String userEmail, SubscriptionRequest subscriptionRequest){
        SubscribedUser subscribedUser = subscribedUserRepository.findByEmail(userEmail).orElseThrow(() ->
            new ResourceNotFoundException("User not subscribed to any Equity with email: "+ userEmail)
        );
        var equity = equityRepository.findByReutersCode(subscriptionRequest.getReutersCode()).orElseThrow(
                ()-> new ResourceNotFoundException("No equity found with code: "+ subscriptionRequest.getReutersCode())
        );
        //do remove subscirption
        var subscriptionEntry = new SubscriptionId(equity.getId(), subscribedUser.getId());
        var isExistedSubscription = subscriptionRepository.findById(subscriptionEntry);
        if(isExistedSubscription.isPresent()) {
            subscriptionRepository.delete(new StockSubscription(subscriptionEntry));
        }
    }
}
