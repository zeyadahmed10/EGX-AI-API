package org.egx.stocks.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.egx.stocks.entity.UpdatedStock;
import org.egx.stocks.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/v1/stocks")
@Tag(name = "Stocks Controller", description = "The Updated Stocks API")
public class StockController {

    @Autowired
    private StockService stockService;
    @Operation(
            summary = "Fetch all updated stocks",
            description = "Fetch all updated stocks entities with their specified equity"
    )
    @GetMapping
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)))
    public Page<UpdatedStock> fetchAllStocks(
            @Parameter(description = "Filter by sector", example = "Real Estate") @RequestParam(defaultValue = "") String sectorFilter,
            @Parameter(description = "Filter by name", example = "Delta Construction") @RequestParam(defaultValue = "") String nameFilter,
            @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size
    ){
        return stockService.fetchAllStocks(sectorFilter, nameFilter,  page, size);
    }
    @Operation(
            summary = "Get updated stock by Reuters code",
            description = "Get an updated stock entity by its Reuters code"
    )
    @GetMapping("/{reutersCode}")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UpdatedStock.class)))
    @ApiResponse(responseCode = "404", description = "Stock/Equity not found")
    public UpdatedStock getUpdatedStockByReutersCode(@Parameter(description = "Reuters code of the stock", example = "DCRC") @PathVariable String reutersCode
    ){
        return stockService.getStockByReutersCode(reutersCode);
    }
}
