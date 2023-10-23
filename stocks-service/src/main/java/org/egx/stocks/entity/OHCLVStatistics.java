package org.egx.stocks.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OHCLVStatistics {
    Timestamp time;
    Double currPrice;
    Double open;
    Double highest;
    Double prevClose;
    Double lowest;
    Double volume;



}
