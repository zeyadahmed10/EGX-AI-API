package org.egx.stocks.services;

import exceptions.ResourceNotFoundException;
import org.egx.stocks.entity.HistoricalStock;
import org.egx.stocks.entity.OHCLVStatistics;
import org.egx.stocks.repos.HistoricalStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HistoricalStockService {

    @Autowired
    private HistoricalStockRepository historicalStockRepository;

    public Page<OHCLVStatistics> getOHCLVStatistics(String reutersParam,
                                             String periodParam,
                                             String intervalParam,
                                             int size, int page){
        Pageable pageable = PageRequest.of(page, size);

        var data = historicalStockRepository.findOHCLVForEquity(reutersParam, periodParam,
                intervalParam, pageable);
        if(!historicalStockRepository.existsByEquity_reutersCode(reutersParam))
            throw new ResourceNotFoundException("Cannot find historical stock for equity with code: "+ reutersParam);
        Page<OHCLVStatistics> statistics = new PageImpl<OHCLVStatistics>(
                generateOHCLVStatistics(data.getContent()),
                data.getPageable(),
                data.getTotalElements());
        return statistics;
    }
    public ArrayList<OHCLVStatistics> generateOHCLVStatistics(List<HistoricalStock> data){
        ArrayList<OHCLVStatistics> result = new ArrayList<OHCLVStatistics>();
        for(var item: data){
            result.add(new OHCLVStatistics(item.getTime(), item.getCurrPrice(), item.getOpen(), item.getHighest(),
                    item.getPrevClose(), item.getLowest(), item.getVolume()));
        }
        return result;
    }
}
