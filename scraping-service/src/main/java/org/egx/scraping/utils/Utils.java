package org.egx.scraping.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Slf4j
public class Utils {
    Properties properties = new Properties();
    public static List<List<String>> readEquities(String path){
        List<List<String>> equities = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(ResourceUtils.getFile("classpath:"+path)
        ))) {
            for(String line; (line = br.readLine()) != null; ) {
                // process the line.
                String lineTrimmed = String.valueOf(line).trim();
                //ISN // name// sector// reutersCode// date
                var data= Arrays.asList(lineTrimmed.split("--"));
                equities.add(data);
            }
            // line is not visible here.
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return equities;
    }
}
