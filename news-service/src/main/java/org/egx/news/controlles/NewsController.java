package org.egx.news.controlles;

import org.egx.news.entity.News;
import org.egx.news.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public Page<News> fetchNewsAsList(@RequestParam(defaultValue = "") String categoryFilter,
                                      @RequestParam(defaultValue = "") String nameFilter,
                                      @RequestParam(defaultValue = "") String reutersFilter,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        return newsService.fetchNewsAsList(categoryFilter, nameFilter, reutersFilter, page, size);
    }
}
