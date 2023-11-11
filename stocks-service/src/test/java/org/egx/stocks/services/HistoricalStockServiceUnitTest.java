package org.egx.stocks.services;

import exceptions.ResourceNotFoundException;
import org.egx.stocks.entity.Equity;
import org.egx.stocks.entity.HistoricalStock;
import org.egx.stocks.entity.OHCLVStatistics;
import org.egx.stocks.repos.HistoricalStockRepository;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class HistoricalStockServiceUnitTest {
    List<Equity> equityList = new ArrayList<>();
    List<HistoricalStock> stockList = new ArrayList<>();
    String[] reutersCode, names, dates, ISNs, sectors;
    String [] stocksDate = {"2023-11-11 09:15:52", "2023-10-11 09:15:52", "2023-09-11 09:15:52",
            "2023-08-11 09:15:52", "2023-07-11 09:15:52", "2023-06-11 09:15:52","2023-05-11 09:15:52",
            "2023-04-11 09:15:52", "2023-03-11 09:15:52", "2023-02-11 09:15:52", "2023-01-11 09:15:52"};

    @Mock
    private HistoricalStockRepository historicalStockRepository;
    @InjectMocks
    private HistoricalStockService historicalStockService;

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
        for(int i = 0 ; i<22 ;i++){
            var stock = HistoricalStock.builder().
                    open(1.0)
                    .currPrice(1.0).prevClose(1.0).highest(1.0).lowest(1.0)
                    .rateOfChange(0.0).percentageOfChange(0.0).value(10.0).volume(10.0)
                    .time(Timestamp.valueOf(stocksDate[i%11]))
                    .equity(equityList.get((i>10)?1:0)).build();
            stockList.add(stock);
        }
    }

    @Test
    void testGetOHCLVStatistics_whenNotExistedReutersCodeProvided_shouldThrowResourceNotFoundException() {
        String intervalParam = "1 weeks";
        String periodParam = "30 minutes";
        String reutersCode = "NotExisted";
        int page = 0;
        int size = 11;
        doReturn(null).when(historicalStockRepository).findOHCLVForEquity(anyString(), anyString(),
                anyString(), any(Pageable.class));
        doReturn(false).when(historicalStockRepository).existsByEquity_reutersCode(anyString());
        assertThrows(ResourceNotFoundException.class, ()-> historicalStockService.
                getOHCLVStatistics(reutersCode, periodParam, intervalParam, size, page));

    }
    @Test
    void testGetOHCLVStatistics_whenExistedReutersCodeProvided_shouldReturnOHCLVStatisticsForEquity() {
        String intervalParam = "1 weeks";
        String periodParam = "30 minutes";
        String reutersCode = "DCRC";
        int page = 0;
        int size = 5;
        var DCRCStockList = stockList.subList(0,11);
        DCRCStockList.sort((a,b)->
                b.getTime().compareTo(a.getTime()));
        Pageable pageable = PageRequest.of(page, size);
        var pageContent = new PageImpl<>(DCRCStockList.subList(0,5),pageable,11L);
        doReturn(pageContent).when(historicalStockRepository).findOHCLVForEquity(anyString(), anyString(),
                anyString(), any(Pageable.class));
        doReturn(true).when(historicalStockRepository).existsByEquity_reutersCode(anyString());
        var actual = new PageImpl<>(historicalStockService.generateOHCLVStatistics(DCRCStockList.subList(0,5)), pageable, 11L);
        var expected = historicalStockService.getOHCLVStatistics(reutersCode, periodParam,
                intervalParam, size, page);
        assertEquals(expected, actual);


    }

    @Test
    void testGenerateOHCLVStatistics_whenHistoricalStockListProvided_shouldReturnOHCLVStatisticsList() {
        List<OHCLVStatistics> expected = new ArrayList<OHCLVStatistics>();
        for(var item: stockList){
            expected.add(new OHCLVStatistics(item.getTime(), item.getCurrPrice(), item.getOpen(), item.getHighest(),
                    item.getPrevClose(), item.getLowest(), item.getVolume()));
        }
        var actual = historicalStockService.generateOHCLVStatistics(stockList);
        assertEquals(expected, actual);
    }
}