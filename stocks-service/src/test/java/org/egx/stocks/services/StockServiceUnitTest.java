package org.egx.stocks.services;

import exceptions.ResourceNotFoundException;
import org.egx.stocks.entity.Equity;
import org.egx.stocks.entity.UpdatedStock;
import org.egx.stocks.repos.UpdatedStockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class StockServiceUnitTest {
    List<Equity> equityList = new ArrayList<>();
    List<UpdatedStock> stockList = new ArrayList<>();
    String[] reutersCode, names, dates, ISNs, sectors;
    @Mock
    private UpdatedStockRepository updatedStockRepository;

    @InjectMocks
    private StockService stockService;

    @BeforeEach
    void setUp() {
        reutersCode = new String[]{"DCRC", "ATQA"};
        names = new String[]{"Delta Construction & Rebuilding","Misr National Steel - Ataqa"};
        dates = new String[]{"12/09/1994","24/05/2006"};
        ISNs = new String[]{"EGS21451C017","EGS3D0C1C018"};
        sectors = new String[]{"Real Estate","Basic Resources"};
        for(int i = 0; i< 2; i++){
            var equity = Equity.builder()
                    .ISN(ISNs[i])
                    .name(names[i])
                    .reutersCode(reutersCode[i])
                    .sector(sectors[i])
                    .listingDate(dates[i]).build();
            equityList.add(equity);
        }
        var date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Calendar.getInstance().getTime());
        var dummyTimestamp = Timestamp.valueOf(date);
        for(int i = 0 ; i<2 ;i++){
            var stock = UpdatedStock.builder().
                    open(1.0)
                    .currPrice(1.0).prevClose(1.0).highest(1.0).lowest(1.0)
                    .rateOfChange(0.0).percentageOfChange(0.0).value(10.0).volume(10.0)
                    .time(dummyTimestamp)
                    .equity(equityList.get(i)).build();
            stockList.add(stock);
        }
    }

    @Test
    void testFetchAllStocks_whenAllFiltersDefault_shouldReturnAllExistedStocks() {
        String nameFilter = "";
        String sectorFilter = "";
        Pageable page = PageRequest.of(0,2);
        int start = (int) page.getOffset();
        int end = (int) Math.min(stockList.size(), start+ page.getPageSize());
        var pageContent = stockList.subList(start,end);
        var expected = new PageImpl<>(pageContent);
        doReturn(expected).when(updatedStockRepository).findAllStocksByFilters(any(String.class),any(String.class),any(Pageable.class));
        var actual = stockService.fetchAllStocks(sectorFilter, nameFilter, 0, 2);
        assertEquals(expected, actual);
    }

    @Test
    void testGetStockByReutersCode_whenExistedReutersCode_shouldReturnDesiredEquity() {
        String reutersCode = "DCRC";
        var expected = Optional.of(stockList.get(0));
        doReturn(expected).when(updatedStockRepository).findByEquity_reutersCode(any(String.class));
        var actual = stockService.getStockByReutersCode(reutersCode);
        assertEquals(expected.get(), actual);
    }
    @Test
    void testGetStockByReutersCode_whenNotExistedReutersCode_shouldThrowResourceNotFoundException() {
        String reutersCode = "NotExistedCode";
        doReturn(Optional.empty()).when(updatedStockRepository).findByEquity_reutersCode(any(String.class));
        assertThrows(ResourceNotFoundException.class, ()->stockService.getStockByReutersCode(reutersCode));
    }
}