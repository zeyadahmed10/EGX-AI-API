package org.egx.news.utils;

import org.egx.clients.io.NewsDto;
import org.egx.news.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

public class NewsToDtoMapper {
    public static NewsDto map(News news){
        return new NewsDto(news.getId(), news.getTitle(), news.getArticle(), news.getTime(), news.getEquity().getReutersCode(), news.getEquity().getSector(), news.getEquity().getName());
    }
    public static Page<NewsDto> map(Page<News> newsPage){
        var newsPageList = newsPage.getContent();
        List<NewsDto> data = new ArrayList<NewsDto>();
        for(var news: newsPageList){
            data.add(NewsToDtoMapper.map(news));
        }
        return new PageImpl<NewsDto>(
                data,
                newsPage.getPageable(),
                newsPage.getTotalElements());
    }
}
