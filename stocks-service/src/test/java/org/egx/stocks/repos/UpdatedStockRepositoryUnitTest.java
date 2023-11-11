package org.egx.stocks.repos;

import org.egx.stocks.entity.Equity;
import org.egx.stocks.entity.UpdatedStock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UpdatedStockRepositoryUnitTest {
    List<Equity> equityList = new ArrayList<>();
    List<UpdatedStock> stockList = new ArrayList<>();
    String[] reutersCode, names, dates, ISNs, sectors;
    @Autowired
    private EquityRepository equityRepository;
    @Autowired
    private UpdatedStockRepository updatedStockRepository;
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
        equityRepository.saveAll(equityList);
        updatedStockRepository.saveAll(stockList);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void testFindAllStocksByFilters_whenNameParamIsPresented_shouldReturnDesiredStock() {
        String nameParam = "uction & Rebuild";
        String sectorParam = "";
        Pageable page = PageRequest.of(0,2);
        var realStock = updatedStockRepository.findAllStocksByFilters(sectorParam, nameParam, page)
                .stream().toList();
        var expectedStock = stockList.get(0);
        assertEquals(expectedStock, Optional.of(realStock.get(0)).orElse(null));
    }
    @Test
    void testFindAllStocksByFilters_whenSectorParamIsPresented_shouldReturnDesiredStock() {
        String nameParam = "";
        String sectorParam = " Est";
        Pageable page = PageRequest.of(0,2);
        var realStock = updatedStockRepository.findAllStocksByFilters(sectorParam, nameParam, page)
                .stream().toList();
        var expectedStock = stockList.get(0);
        assertEquals(expectedStock, Optional.of(realStock.get(0)).orElse(null));
    }
    @Test
    void testFindAllStocksByFilters_whenNoParamsPresented_shouldReturnAllStocks() {
        String nameParam = "";
        String sectorParam = "";
        Pageable page = PageRequest.of(0,2);
        var realStock = updatedStockRepository.findAllStocksByFilters(sectorParam, nameParam, page)
                .stream().toList();
        assertEquals(stockList, realStock);
    }

}