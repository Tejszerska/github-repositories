package com.spring.zaddom0202.client;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class FeignConfig {
    @Value("${github.api.token:}")
    String token;
    @Bean
    public RequestInterceptor bearerTokenRequestInterceptor(){
        return requestTemplate -> {
            if(token != null && !token.isBlank()){
                requestTemplate.header("Authorization", token);
            }
            requestTemplate.header("Accept", "application/vnd.github+json");
        };
    }
}
