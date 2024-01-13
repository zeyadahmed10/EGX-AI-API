package org.egx.news.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.egx.clients.io.NewsDto;
import org.egx.news.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

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

    @Operation(
            summary = "Fetch news articles",
            description = "Fetch a list of news articles with optional filters."
    )
    @GetMapping
    @ApiResponse(responseCode = "200", description = "OK")
    public Page<NewsDto> fetchNewsAsList(
            @Parameter(description = "Filter by category", example = "Real Estate") @RequestParam(defaultValue = "") String categoryFilter,
            @Parameter(description = "Filter by name", example = "Delta Construction") @RequestParam(defaultValue = "") String nameFilter,
            @Parameter(description = "Filter by Reuters", example = "DRCR") @RequestParam(defaultValue = "") String reutersFilter,
            @Parameter(description = "Page number (starting from 0)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "10") int size,
            @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt
    )  {
        return newsService.fetchNewsAsList(categoryFilter, nameFilter, reutersFilter, page, size);
    }
    @Operation(
            summary = "Get news article by ID",
            description = "Get detailed information about a news article using its ID."
    )
    @GetMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "OK")
    public NewsDto getNews(
            @Parameter(description = "News article ID") @PathVariable Integer id,
            @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt
    ) {
        return newsService.getNewsById(id);
    }
    @Operation(
            summary = "Get news articles by IDs",
            description = "Get a list of news articles using their IDs."
    )
    @GetMapping("/ids")
    @ApiResponse(responseCode = "200", description = "OK")
    public Page<NewsDto> getNewsByIds(
            @Parameter(description = "List of news article IDs") @RequestParam List<Integer> values,
            @Parameter(description = "Page number (starting from 0)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "5") int size
    ){
        return newsService.findNewsByIdsList(values, page, size);
    }
    @Operation(
            summary = "Delete news article by ID",
            description = "Delete a news article using its ID. Requires admin role."
    )
    @PreAuthorize("hasAuthority('ROLE_admin')")
    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204", description = "No Content")
    public void deleteNews(
            @Parameter(description = "News article ID") @PathVariable Integer id
    ) {
        newsService.deleteNewsById(id);
    }
}
