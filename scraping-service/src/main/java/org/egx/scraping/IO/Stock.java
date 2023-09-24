package org.egx.scraping.IO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Data
@AllArgsConstructor
public class Stock {

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
    public Stock(double currPrice, double rateOfChange, double percentageOfChange, double open, double prevClose, double highest, double lowest, double volume, double value){
        this.currPrice = currPrice;
        this.rateOfChange = rateOfChange;
        this.percentageOfChange = percentageOfChange;
        this.open = open;
        this.prevClose = prevClose;
        this.highest = highest;
        this.lowest = lowest;
        this.volume = volume;
        this.value = value;
        try {
            String stringTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                    .format(Calendar.getInstance().getTime());
            this.time = Timestamp.valueOf(stringTime);
        } catch(Exception e) { //this generic but you can control another types of exception
            throw new IllegalStateException("Invalid time format: " +e.getMessage());
        }
    }
}
