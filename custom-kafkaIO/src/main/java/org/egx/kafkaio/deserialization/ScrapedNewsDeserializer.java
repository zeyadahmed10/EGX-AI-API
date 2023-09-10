package org.egx.kafkaio.deserialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.egx.clients.io.ScrapedNews;
import org.springframework.kafka.support.serializer.DeserializationException;

import java.util.Map;

public class ScrapedNewsDeserializer implements Deserializer<ScrapedNews> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public ScrapedNews deserialize(String s, byte[] bytes) {
        try{
            if(bytes==null)
                return null;
            return objectMapper.readValue(new String(bytes,"UTF-8"), ScrapedNews.class);
        }catch (Exception e){
            throw new SerializationException("Error when deserializing byte[] to ScrapedNews");
        }
    }

    @Override
    public ScrapedNews deserialize(String topic, Headers headers, byte[] data) {
        return Deserializer.super.deserialize(topic, headers, data);
    }

    @Override
    public void close() {
        Deserializer.super.close();
    }
}
