package org.egx.news.services;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.egx.news.entity.Equity;
import org.egx.news.entity.News;
import org.egx.news.exceptions.ResourceNotFoundException;
import org.egx.news.repos.EquityRepository;
import org.egx.news.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EquityService {

    @Autowired
    private EquityRepository equityRepository;
    //adding the equities
//    @PostConstruct
//    public void init() {
//        var count = equityRepository.count();
//        if(count!=0)
//            return;
//        var equities = Utils.readEquities("equities.txt");
//        List<Equity> entityEquities = new ArrayList<>();
//        for(var eq: equities){
//            var equity = Equity.builder()
//                    .ISN(eq.get(0))
//                    .name(eq.get(1))
//                    .sector(eq.get(2))
//                    .reutersCode(eq.get(3))
//                    .listingDate(eq.get(4)).build();
//            entityEquities.add(equity);
//        }
//        equityRepository.saveAll(entityEquities);
//        log.info("Successfully saved equities to DB");
//    }
    public Page<Equity> fetchEquitiesAsList(String sectorFilter, String nameFilter, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return equityRepository.findAllByFilters(sectorFilter, nameFilter, pageable);
    }
    public Equity getEquityById(Integer id){
        return equityRepository.findById(id).orElseThrow(()->{
            log.error("No equity with such id: "+id);
            return new ResourceNotFoundException("No such equity with such id: "+id);
        });
    }
    public Equity getEquityByReutersCode(String code){
        return equityRepository.findByReutersCode(code).orElseThrow(()->{
            log.error("No equity with such id: "+code);
            return new ResourceNotFoundException("No such equity with such code: "+code);
        });
    }

    public void deleteEquityById(Integer id) {
        equityRepository.deleteById(id);
    }

    public void deleteEquityByReutersCode(String code) {
        equityRepository.deleteByReutersCode(code);
    }

}
