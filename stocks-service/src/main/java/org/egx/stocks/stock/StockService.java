package org.egx.stocks.stock;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.egx.stocks.entity.UpdatedStock;
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
