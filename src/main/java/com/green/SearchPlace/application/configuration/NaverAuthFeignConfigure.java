package com.green.SearchPlace.application.configuration;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NaverAuthFeignConfigure {

    @Bean
    RequestInterceptor naverAuthFeignInterceptor(@Value("${restApi.naver.clientId}") String clientId,
                                                 @Value("${restApi.naver.clientSecret}") String clientSecret) {
        return requestTemplate -> {
            requestTemplate.header("X-Naver-Client-Id", clientId);
            requestTemplate.header("X-Naver-Client-Secret", clientSecret);
        };
    }
}
