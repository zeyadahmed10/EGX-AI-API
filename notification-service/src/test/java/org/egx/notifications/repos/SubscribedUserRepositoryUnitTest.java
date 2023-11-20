package org.egx.notifications.repos;

import org.egx.notifications.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SubscribedUserRepositoryUnitTest {
    @Autowired
    private SubscribedUserRepository subscribedUserRepository;
    @Autowired
    private EquityRepository equityRepository;
    @Autowired
    private StockSubscriptionRepository stockSubscriptionRepository;
    @Autowired
    private NewsSubscriptionRepository newsSubscriptionRepository;
    List<Equity> equityList = new ArrayList<>();
    List<SubscribedUser> usersList = new ArrayList<>();
    String[] reutersCode, names, dates, ISNs, sectors, userEmails, userNames;
    @BeforeEach
    void setup(){
        reutersCode = new String[]{"DCRC", "ATQA","SPMD"};
        names = new String[]{"Delta Construction & Rebuilding","Misr National Steel - Ataqa", "Speed Medical"};
        dates = new String[]{"12/09/1994","24/05/2006","03/12/2020"};
        ISNs = new String[]{"EGS21451C017","EGS3D0C1C018","EGS73BR1C013"};
        sectors = new String[]{"Real Estate","Basic Resources","Health Care & Pharmaceuticals"};
        userEmails = new String[]{"test1@gmail.com", "test2@gmail.com", "test3@gmail.com"};
        userNames = new String[]{"test1", "test2", "test3"};
        for(int i = 0; i< 3; i++){
            var equity = Equity.builder()
                    .ISN(ISNs[i])
                    .name(names[i])
                    .reutersCode(reutersCode[i])
                    .sector(sectors[i])
                    .listingDate(dates[i]).build();
            equityList.add(equity);
            var user = SubscribedUser.builder().name(userNames[i]).email(userEmails[i]).build();
            usersList.add(user);
        }
        equityRepository.saveAll(equityList);
        subscribedUserRepository.saveAll(usersList);
        //user 1 subscribed to two stocks DCRC and ATQA
        var user1EntryToDCRC = new SubscriptionId(equityList.get(0).getId(), usersList.get(0).getId());
        var user1EntryToATQA = new SubscriptionId(equityList.get(1).getId(), usersList.get(0).getId());
       stockSubscriptionRepository.saveAll(Arrays.asList(new StockSubscription(user1EntryToDCRC),
               new StockSubscription(user1EntryToATQA)));
        //user2 subscribed to DCRC only but for its stocks and newsletter
        var user2EntryToDCRC = new SubscriptionId(equityList.get(0).getId(), usersList.get(1).getId());
        stockSubscriptionRepository.save(new StockSubscription(user2EntryToDCRC));
        newsSubscriptionRepository.save(new NewsSubscription(user2EntryToDCRC));
        //user 3 not subscribed to any stocks or newsletter
    }

    @Test
    void testFindStockSubscribedUsersByReutersCode_whenReutersCodeProvided_shouldReturnExpectedUsers() {
        List<SubscribedUser> users = subscribedUserRepository.findStockSubscribedUsersByReutersCode("DCRC");
        List<String> expectedEmails = Arrays.asList(userEmails[0], userEmails[1]);
        List<String> actualEmails = new ArrayList<String>();
        for(var user: users){
            actualEmails.add(user.getEmail());
        }
        assertEquals(expectedEmails, actualEmails);
    }

    @Test
    void testFindNewsSubscribedUsersByReutersCode_whenReutersCodeProvided_shouldReturnExpectedUsers() {
        var users = subscribedUserRepository.findNewsSubscribedUsersByReutersCode("DCRC");
        List<String> expectedEmails = Arrays.asList(userEmails[1]);
        List<String> actualEmails = new ArrayList<String>();
        for(var user: users){
            actualEmails.add(user.getEmail());
        }
        assertEquals(expectedEmails, actualEmails);
    }
}