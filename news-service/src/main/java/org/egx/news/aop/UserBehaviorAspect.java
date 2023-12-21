package org.egx.news.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.restassured.path.json.JsonPath;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.egx.clients.io.UserBehaviorEvent;
import org.egx.news.entity.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
public class UserBehaviorAspect {
    public List<News> getNewsList(Object result) throws JsonProcessingException {
        List<News> newsList = new ArrayList<News>();
        if(result instanceof News)
            newsList.add((News)result);
        else{
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(result);
            newsList.addAll(JsonPath.from(json).getList("content",News.class));
        }
        return newsList;
    }
    public String getUserEmail(JoinPoint joinPoint){
        for(var arg: joinPoint.getArgs()){
            if(arg instanceof Jwt){
                Jwt jwt = (Jwt) arg;
                return String.valueOf(jwt.getClaims().get("email"));
            }
        }
        return null;
    }
    @Autowired
    private KafkaTemplate<String, UserBehaviorEvent> kafkaTemplate;

    @AfterReturning(pointcut = "( execution(* org.egx.news.controllers.NewsController.fetchNewsAsList (..)) "
    +"|| execution(* org.egx.news.controllers.NewsController.getNews (..)))", returning = "result")
    public void after(JoinPoint joinPoint, Object result) throws JsonProcessingException {
        String userEmail = getUserEmail(joinPoint);
        List<News> newsList = getNewsList(result);
        for(var news: newsList){
            kafkaTemplate.send("user-behavior", new UserBehaviorEvent(userEmail, news));
        }
    }
}
