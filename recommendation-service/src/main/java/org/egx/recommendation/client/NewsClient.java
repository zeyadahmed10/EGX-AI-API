package org.egx.recommendation.client;

import org.egx.clients.io.NewsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="news-service")
public interface NewsClient {
    @GetMapping("/ids")
    public Page<NewsDto> getNewsByIds(@RequestBody List<Integer> idsList, @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "5") int size);
}
