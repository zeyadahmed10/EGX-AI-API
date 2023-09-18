package org.egx.news.services;

import org.egx.news.entity.News;
import org.egx.news.repos.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    public Page<News> fetchNewsAsList(String equityCategoryFilter, String equityNameFilter, String equityReutersFilter,
                                      int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return newsRepository.findAllByFilters(equityCategoryFilter, equityNameFilter, equityReutersFilter, pageable);
    }
}
