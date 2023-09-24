package org.egx.stocks.controllers;

import lombok.extern.slf4j.Slf4j;
import org.egx.stocks.entity.UpdatedStock;
import org.egx.stocks.stock.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping
    public Page<UpdatedStock> fetchAllStocks(){
        return null;
    }
}
