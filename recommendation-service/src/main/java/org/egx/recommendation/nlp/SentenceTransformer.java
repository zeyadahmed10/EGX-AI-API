package org.egx.recommendation.nlp;

import ai.djl.MalformedModelException;
import ai.djl.huggingface.translator.TextEmbeddingTranslatorFactory;
import ai.djl.inference.Predictor;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.training.util.ProgressBar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class SentenceTransformer {

    @Bean
    public ZooModel<String, float[]> sentenceTransformerModel() throws ModelNotFoundException, MalformedModelException, IOException {
        Criteria<String, float[]> criteria = Criteria.builder()
                .setTypes(String.class, float[].class)
                .optModelUrls("djl://ai.djl.huggingface.pytorch/sentence-transformers/paraphrase-multilingual-mpnet-base-v2")
                .optEngine("PyTorch")
                .optTranslatorFactory(new TextEmbeddingTranslatorFactory())
                .optProgress(new ProgressBar())
                .build();
        ZooModel<String, float[]> model;
        try{
        model = criteria.loadModel();
        }catch (Exception e){
            throw e;
        }
        return model;
    }
    @Bean
    public Predictor<String, float[]> sentenceTransformerPredictor(ZooModel<String, float[]> sentenceTransformerModel){
        return sentenceTransformerModel.newPredictor();
    }
}
