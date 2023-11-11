package org.egx.stocks.services;

import exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.egx.stocks.entity.UpdatedStock;
import org.egx.stocks.repos.EquityRepository;
import org.egx.stocks.repos.UpdatedStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StockService {
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
    @Autowired
    private UpdatedStockRepository updatedStockRepository;
    public Page<UpdatedStock> fetchAllStocks(String sectorFilter, String nameFilter, int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        return updatedStockRepository.findAllStocksByFilters(sectorFilter, nameFilter,  pageRequest);
    }

    public UpdatedStock getStockByReutersCode(String reutersCode) {
            return updatedStockRepository.findByEquity_reutersCode(reutersCode).orElseThrow(()->{
            log.error("No equity with code: "+reutersCode);
            return new ResourceNotFoundException("No equity with code: "+reutersCode);
        });

    }
}
