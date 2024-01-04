package org.egx.news.controllers;

import org.egx.clients.io.NewsDto;
import org.egx.news.services.NewsService;
import org.egx.news.utils.NewsToDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/news")
public class NewsController {
    @Autowired
    private NewsService newsService;
    /**
     * @param categoryFilter Filter for the first Category if required
     * @param nameFilter  Filter for the Name if required
     * @param page            number of the page returned
     * @param size            number of entries in each page
     * @return Page object with news after filtering and sorting
     */
    @GetMapping
    public Page<NewsDto> fetchNewsAsList(@RequestParam(defaultValue = "") String categoryFilter,
                                         @RequestParam(defaultValue = "") String nameFilter,
                                         @RequestParam(defaultValue = "") String reutersFilter,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size, @AuthenticationPrincipal Jwt jwt) {
        var newsPage = newsService.fetchNewsAsList(categoryFilter, nameFilter, reutersFilter, page, size);
        return NewsToDtoMapper.map(newsPage);
    }
    @GetMapping("/{id}")
    public NewsDto getNews(@PathVariable Integer id, @AuthenticationPrincipal Jwt jwt){
        return NewsToDtoMapper.map(newsService.getNewsById(id));
    }
    @DeleteMapping("/{id}")
    public void deleteNews(@PathVariable Integer id){
        newsService.deleteNewsById(id);
    }
}
