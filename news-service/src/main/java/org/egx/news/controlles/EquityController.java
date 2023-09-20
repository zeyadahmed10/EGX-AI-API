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
@RequestMapping("/api/v1/equity")
public class EquityController {
    List<Equity> equityList = new ArrayList<>();
    List<News> newsList = new ArrayList<>();
    String[] reutersCode, names, dates, ISNs, sectors;

    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private EquityRepository equityRepository;
    @PostConstruct
    public void init(){
        reutersCode = new String[]{"DCRC", "ATQA"};
        names = new String[]{"Delta Construction & Rebuilding","Misr National Steel - Ataqa"};
        dates = new String[]{"12/09/1994","24/05/2006"};
        ISNs = new String[]{"EGS21451C017","EGS3D0C1C018"};
        sectors = new String[]{"Real Estate","Basic Resources"};
        for(int i = 0; i< 2; i++){
            var equity = Equity.builder()
                    .ISN(ISNs[i])
                    .name(names[i])
                    .reutersCode(reutersCode[i])
                    .sector(sectors[i])
                    .listingDate(dates[i]).build();
            equityList.add(equity);
        }

        for(int i = 0; i<4; i++){
            int EqIdx = i>1 ? 1:0;
            var news = News.builder()
                    .article("article" + i)
                    .title("title" + i)
                    .newsDate("date"+i)
                    .newsTime("time"+i)
                    .equity(equityList.get(EqIdx)).build();
            newsList.add(news);
        }
        equityList.get(0).setNews(Arrays.asList(newsList.get(0), newsList.get(1)));
        equityList.get(1).setNews(Arrays.asList(newsList.get(2), newsList.get(3)));
        equityRepository.saveAll(equityList);
        //newsRepository.saveAll(newsList);
    }
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
    public List<News> getNews(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "5") int size, @PathVariable("code") String code){
        var equity = equityService.getEquityByReutersCode(code);
        return equity.getNews();
    }
}
