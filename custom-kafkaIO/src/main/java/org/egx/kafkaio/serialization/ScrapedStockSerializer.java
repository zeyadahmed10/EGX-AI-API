package org.egx.kafkaio.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;
import org.egx.clients.io.ScrapedStock;

import java.util.Map;
public class ScrapedStockSerializer implements Serializer<ScrapedStock> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String s, ScrapedStock scrapedStock) {
        try {
            if (scrapedStock == null){
                System.out.println("Null received at serializing");
                return null;
            }
            return objectMapper.writeValueAsBytes(scrapedStock);
        } catch (Exception e) {
            throw new SerializationException("Error when serializing ScrapedStock to byte[]");
        }
    }

    @Override
    public byte[] serialize(String topic, Headers headers, ScrapedStock data) {
        return Serializer.super.serialize(topic, headers, data);
    }

    @Override
    public void close() {
        Serializer.super.close();
    }
}
