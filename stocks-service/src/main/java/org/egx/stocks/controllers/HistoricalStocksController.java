package org.egx.stocks.controllers;

import org.egx.stocks.entity.OHCLVStatistics;
import org.egx.stocks.services.HistoricalStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/historical-stocks")
public class HistoricalStocksController {

    @Autowired
    private HistoricalStockService historicalStockService;
    /*
    preferred parameters and precision
    1 day 5 minutes period
    1 week 30 minutes period
    1 month 1 day period
    * */
    @GetMapping("/{reutersCode}")
    Page<OHCLVStatistics> getOHCLVStatistics(
            @PathVariable("reutersCode") String reutersCode,
            @RequestParam(defaultValue = "30 minutes") String periodParam,
            @RequestParam(defaultValue = "1 weeks") String intervalParam,
            @RequestParam(defaultValue = "0" ) int page,
            @RequestParam(defaultValue = "10") int size){
        return historicalStockService.getOHCLVStatistics(reutersCode, periodParam, intervalParam, size, page);
    }
}
