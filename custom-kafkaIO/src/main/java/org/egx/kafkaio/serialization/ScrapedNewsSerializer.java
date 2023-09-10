package org.egx.kafkaio.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;
import org.egx.clients.io.ScrapedNews;

import java.util.Map;

public class ScrapedNewsSerializer implements Serializer<ScrapedNews> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String s, ScrapedNews scrapedNews) {
        try{
            if(scrapedNews ==null)
                return null;
            return objectMapper.writeValueAsBytes(scrapedNews);
        }catch (Exception e){
            throw new SerializationException("Error when serializing ScrapedNews to byte[]");
        }
    }

    @Override
    public byte[] serialize(String topic, Headers headers, ScrapedNews data) {
        return Serializer.super.serialize(topic, headers, data);
    }

    @Override
    public void close() {
        Serializer.super.close();
    }
}
