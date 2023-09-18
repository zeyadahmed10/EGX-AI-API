package org.egx.news.services;

import lombok.extern.slf4j.Slf4j;
import org.egx.news.entity.Equity;
import org.egx.news.exceptions.ResourceNotFoundException;
import org.egx.news.repos.EquityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EquityService {

    @Autowired
    private EquityRepository equityRepository;

    private Equity getEquityByCode(String reutersCode){
        return equityRepository.findByReutersCode(reutersCode).orElseThrow(()->{
            log.error("could not find equity by code: "+reutersCode);
            return new ResourceNotFoundException("Could not find equity by code: "+reutersCode);
        });
    }
}
