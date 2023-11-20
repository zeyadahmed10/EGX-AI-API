package org.egx.notifications.services;

import exceptions.ResourceNotFoundException;
import org.egx.notifications.dto.SubscriptionRequest;
import org.egx.notifications.entity.*;
import org.egx.notifications.repos.EquityRepository;
import org.egx.notifications.repos.NewsSubscriptionRepository;
import org.egx.notifications.repos.SubscribedUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("newsSubscriptionService")
public class NewsSubscriptionService implements SubscriptionService{
    @Autowired
    private SubscribedUserRepository subscribedUserRepository;
    @Autowired
    private EquityRepository equityRepository;
    @Autowired
    private NewsSubscriptionRepository subscriptionRepository;

    public boolean subscribe(String userEmail, String name, SubscriptionRequest subscriptionRequest) {

        SubscribedUser subscribedUser = subscribedUserRepository.findByEmail(userEmail).orElseGet(() -> {
            return subscribedUserRepository
                    .save(SubscribedUser.builder().email(userEmail).name(name).build());
        });
        var equity = equityRepository.findByReutersCode(subscriptionRequest.getReutersCode()).orElseThrow(
                ()-> new ResourceNotFoundException("No equity found with code: "+ subscriptionRequest.getReutersCode())
        );
        //do subscription
        var subscriptionEntry = new SubscriptionId(equity.getId(), subscribedUser.getId());
        var isExistedSubscription = subscriptionRepository.findById(subscriptionEntry);
        if(isExistedSubscription.isEmpty()) {
            subscriptionRepository.save(new NewsSubscription(subscriptionEntry));
        }
        return true;
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
        isExistedSubscription.ifPresent(newsSubscription -> subscriptionRepository.delete(newsSubscription));
    }
}
