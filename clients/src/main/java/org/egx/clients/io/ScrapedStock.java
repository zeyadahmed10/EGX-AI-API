package org.egx.clients.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
@Data
@AllArgsConstructor
@Builder
public class ScrapedStock {
    String reutersCode;
    double currPrice;
    double rateOfChange;
    double percentageOfChange;
    double open;
    double prevClose;
    double highest;
    double lowest;
    double volume;
    double value;
    Timestamp time;
}
