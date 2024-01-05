package org.egx.recommendation.controllers;

import org.egx.clients.io.NewsDto;
import org.egx.recommendation.services.NewsRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/recommendations")
public class NewsRecommendationController {
    @Autowired
    private NewsRecommendationService newsRecommendationService;
    @GetMapping
    Page<NewsDto> getNewsRecommendations(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "5") int size, @AuthenticationPrincipal Jwt jwt){
        String userEmail = String.valueOf(jwt.getClaims().get("email"));
        return newsRecommendationService.getNewsRecommendations(page, size, userEmail);
    }
}
