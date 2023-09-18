package org.egx.news.services;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.egx.news.entity.Equity;
import org.egx.news.exceptions.ResourceNotFoundException;
import org.egx.news.repos.EquityRepository;
import org.egx.news.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EquityService {

    @Autowired
    private EquityRepository equityRepository;
    //adding the equities
    @PostConstruct
    public void init() {
        var count = equityRepository.count();
        if(count!=0)
            return;
        var equities = Utils.readEquities("equities.txt");
        List<Equity> entityEquities = new ArrayList<>();
        for(var eq: equities){
            var equity = Equity.builder()
                    .ISN(eq.get(0))
                    .name(eq.get(1))
                    .sector(eq.get(2))
                    .reutersCode(eq.get(3))
                    .listingDate(eq.get(4)).build();
            entityEquities.add(equity);
        }
        equityRepository.saveAll(entityEquities);
    }
    public Equity getEquityByCode(String reutersCode){
        return equityRepository.findByReutersCode(reutersCode).orElseThrow(()->{
            log.error("could not find equity by code: "+reutersCode);
            return new ResourceNotFoundException("Could not find equity by code: "+reutersCode);
        });
    }
}
