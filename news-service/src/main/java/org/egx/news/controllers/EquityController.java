package org.egx.news.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.egx.clients.io.NewsDto;
import org.egx.news.entity.Equity;
import org.egx.news.services.EquityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/equities")
@Tag(name = "Equity Controller", description = "APIs for managing equities")
public class EquityController {
    @Autowired
    private EquityService equityService;


    @Operation(
            summary = "Fetch equities",
            description = "Fetch a list of equities with optional filters."
    )
    @GetMapping
    @ApiResponse(responseCode = "200", description = "OK")
    public Page<Equity> fetchEquitiesAsList(
            @Parameter(description = "Filter by sector", example = "Real Estate") @RequestParam(defaultValue = "") String sectorFilter,
            @Parameter(description = "Filter by name", example = "Delta Construction") @RequestParam(defaultValue = "") String nameFilter,
            @Parameter(description = "Page number (starting from 0)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "10") int size
    ){
        return equityService.fetchEquitiesAsList(sectorFilter, nameFilter, page, size);
    }


    @Operation(
            summary = "Get equity by code",
            description = "Get detailed information about an equity using its code."
    )
    @GetMapping("/{code}")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "Not found")
    public Equity getEquity(
            @Parameter(description = "Equity code") @PathVariable("code") String code
    ) {
        return equityService.getEquityByReutersCode(code);
    }

    @Operation(
            summary = "Delete equity by code",
            description = "Delete an equity using its code. Requires admin role."
    )
    @PreAuthorize("hasAuthority('ROLE_admin')")
    @DeleteMapping("/{code}")
    @ApiResponse(responseCode = "204", description = "No Content")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    public void deleteEquity(
            @Parameter(description = "Equity code", example = "DCRC") @PathVariable("code") String code
    ){
        equityService.deleteEquityByReutersCode(code);
    }

    @Operation(
            summary = "Get news for an equity",
            description = "Get a list of news articles associated with an equity."
    )
    @GetMapping("/{code}/news")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "Equity not found")
    @ApiResponse(responseCode = "400", description = "Page Exceeding limit")
    public Page<NewsDto> getNews(
            @Parameter(description = "Page number (starting from 0)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "5") int size,
            @Parameter(description = "Equity code", example = "DCRC") @PathVariable("code") String code
    ){
        return equityService.getNewsByCode(code, page, size);
    }

}
