package org.egx.news.controlles;

import jakarta.annotation.PostConstruct;
import org.egx.news.entity.Equity;
import org.egx.news.entity.News;
import org.egx.news.repos.EquityRepository;
import org.egx.news.repos.NewsRepository;
import org.egx.news.services.EquityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/equities")
public class EquityController {
    @Autowired
    private EquityService equityService;

    @GetMapping
    public Page<Equity> fetchEquitiesAsList(@RequestParam(defaultValue = "") String sectorFilter,
                                        @RequestParam(defaultValue = "") String nameFilter,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        return equityService.fetchEquitiesAsList(sectorFilter, nameFilter, page, size);
    }


    @GetMapping("/{code}")
    public Equity getEquity(@PathVariable("code") String code){
        System.out.println(equityService.getEquityByReutersCode(code).getNews());
        return equityService.getEquityByReutersCode(code);
    }

    @DeleteMapping("/{code}")
    public void deleteEquity(@PathVariable("code") String code){
        equityService.deleteEquityByReutersCode(code);
    }

    @GetMapping("/{code}/news")
    public Page<News> getNews(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "5") int size, @PathVariable("code") String code){
        return equityService.getNewsByCode(code, page, size);
    }

}
