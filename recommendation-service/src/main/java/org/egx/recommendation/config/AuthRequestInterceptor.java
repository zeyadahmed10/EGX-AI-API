package org.egx.recommendation.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class AuthRequestInterceptor implements RequestInterceptor {

    String accessToken;


    public AuthRequestInterceptor(@AuthenticationPrincipal Jwt jwt) {
        this.accessToken = jwt.getTokenValue();
    }

    @Override
    public void apply(RequestTemplate template) {
        template.header("Authorization", "Bearer "+accessToken);
    }
}

