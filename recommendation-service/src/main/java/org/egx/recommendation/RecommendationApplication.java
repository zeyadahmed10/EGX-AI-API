package org.egx.recommendation;

import ai.djl.inference.Predictor;
import org.egx.recommendation.repos.NewsEmbeddingRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class RecommendationApplication {

    public static void main(String[] args){
        SpringApplication.run(RecommendationApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(Predictor<String, float[]> sentenceTransformerPredictor, NewsEmbeddingRepository repo){
        return args ->{
            String text = "أين أسكن؟";
//            Criteria<String, float[]> criteria = Criteria.builder()
//                    .setTypes(String.class, float[].class)
//                    .optModelUrls("djl://ai.djl.huggingface.pytorch/sentence-transformers/paraphrase-multilingual-mpnet-base-v2")
//                    .optEngine("PyTorch")
//                    .optTranslatorFactory(new TextEmbeddingTranslatorFactory())
//                    .optProgress(new ProgressBar())
//                    .build();
//            try (ZooModel<String, float[]> model = criteria.loadModel();
//                 Predictor<String, float[]> predictor = model.newPredictor()) {
                float[] res = sentenceTransformerPredictor.predict(text);
                System.out.println(res.length);
                System.out.println("Embedding: " + Arrays.toString(res));

        };
    }
}
