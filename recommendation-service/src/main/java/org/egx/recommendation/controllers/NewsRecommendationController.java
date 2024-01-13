package org.egx.recommendation.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "News Recommendation Controller", description = "APIs for getting news recommendations")
public class NewsRecommendationController {
    @Autowired
    private NewsRecommendationService newsRecommendationService;
    @Operation(
            summary = "Get news recommendations",
            description = "Get news recommendations for users based on their reading behavior. If the user is new and has no reading history, it recommends popular news."
    )
    @GetMapping
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)))
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    Page<NewsDto> getNewsRecommendations(
            @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "5") int size,
            @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt
    ){
        String userEmail = String.valueOf(jwt.getClaims().get("email"));
        return newsRecommendationService.getNewsRecommendations(page, size, userEmail);
    }
}
