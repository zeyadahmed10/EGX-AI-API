package org.egx.news.controllers;

import org.egx.clients.io.NewsDto;
import org.egx.news.entity.Equity;
import org.egx.news.services.EquityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
        System.out.println(sectorFilter);
        return equityService.fetchEquitiesAsList(sectorFilter, nameFilter, page, size);
    }


    @GetMapping("/{code}")
    public Equity getEquity(@PathVariable("code") String code){
        return equityService.getEquityByReutersCode(code);
    }
    @PreAuthorize("hasAuthority('ROLE_admin')")
    @DeleteMapping("/{code}")
    public void deleteEquity(@PathVariable("code") String code){
        equityService.deleteEquityByReutersCode(code);
    }

    @GetMapping("/{code}/news")
    public Page<NewsDto> getNews(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "5") int size, @PathVariable("code") String code){
        return equityService.getNewsByCode(code, page, size);
    }

}
