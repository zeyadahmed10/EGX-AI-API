package org.egx.news.services;

import exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.egx.clients.io.NewsDto;
import org.egx.news.entity.News;
import org.egx.news.repos.NewsRepository;
import org.egx.news.utils.NewsToDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    public Page<NewsDto> fetchNewsAsList(String equityCategoryFilter, String equityNameFilter, String equityReutersFilter,
                                         int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        var newsPage = newsRepository.findAllByFilters(equityCategoryFilter, equityNameFilter, equityReutersFilter, pageable);
        return NewsToDtoMapper.map(newsPage);
    }
    public NewsDto getNewsById(Integer id) {
        return NewsToDtoMapper.map(newsRepository.findById(id)
                .orElseThrow(()->{
                    log.error("Could not find news with id " + id);
                    return new ResourceNotFoundException("Could not find news with id " + id);
                }
        ));
    }
    public Page<NewsDto> findNewsByIdsList(List<Integer> idsList, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return NewsToDtoMapper.map(newsRepository.findByIdIn(idsList, pageable));
    }
    public void deleteNewsById(Integer id){
        // TODO add admin level permission
        newsRepository.deleteById(id);
    }
    public News createNews(News news){
        return newsRepository.save(news);
    }
}
