package org.egx.news.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.restassured.path.json.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.egx.clients.io.NewsDto;
import org.egx.clients.io.UserBehaviorEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
@Slf4j
public class UserBehaviorAspect {
    public List<NewsDto> getNewsList(Object result) throws JsonProcessingException {
        List<NewsDto> newsList = new ArrayList<NewsDto>();
        if(result instanceof NewsDto)
            newsList.add((NewsDto)result);
        else{
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(result);
            newsList.addAll(JsonPath.from(json).getList("content",NewsDto.class));
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
    private KafkaTemplate<String, UserBehaviorEvent> kafkaUserBehaviorTemplate;

    @AfterReturning(pointcut = "( execution(* org.egx.news.controllers.NewsController.fetchNewsAsList (..)) "
    +"|| execution(* org.egx.news.controllers.NewsController.getNews (..)))", returning = "result")
    public void after(JoinPoint joinPoint, Object result) throws JsonProcessingException {
        String userEmail = getUserEmail(joinPoint);
        List<NewsDto> newsList = getNewsList(result);
        for(var news: newsList){
            kafkaUserBehaviorTemplate.send("user-behavior", new UserBehaviorEvent(userEmail, news));
            log.info("User Behavior sent for news with id: " + news.getId());
        }
    }
}
