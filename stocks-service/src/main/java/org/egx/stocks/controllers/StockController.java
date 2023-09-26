package org.egx.stocks.controllers;

import lombok.extern.slf4j.Slf4j;
import org.egx.stocks.entity.UpdatedStock;
import org.egx.stocks.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping
    public Page<UpdatedStock> fetchAllStocks(
            @RequestParam(defaultValue = "") String sectorFilter,
            @RequestParam(defaultValue = "") String nameFilter,
            @RequestParam(defaultValue = "0" ) int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return stockService.fetchAllStocks(sectorFilter, nameFilter,  page, size);
    }
    @GetMapping("{code}")
    public UpdatedStock getUpdatedStockByReutersCode(String reutersCode){
        return stockService.getStockByReutersCode(reutersCode);
    }
}
