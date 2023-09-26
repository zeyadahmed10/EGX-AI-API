package org.egx.news.services;

import exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.egx.news.entity.News;
import org.egx.news.repos.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    public Page<News> fetchNewsAsList(String equityCategoryFilter, String equityNameFilter, String equityReutersFilter,
                                                int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return newsRepository.findAllByFilters(equityCategoryFilter, equityNameFilter, equityReutersFilter, pageable);
    }
    public News getNewsById(Integer id) {
        return newsRepository.findById(id)
                .orElseThrow(()->{
                    log.error("Could not find news with id " + id);
                    return new ResourceNotFoundException("Could not find news with id " + id);
                }
        );
    }
    public void deleteNewsById(Integer id){
        // TODO add admin level permission
        newsRepository.deleteById(id);
    }
    public News createNews(News news){
        return newsRepository.save(news);
    }
}
