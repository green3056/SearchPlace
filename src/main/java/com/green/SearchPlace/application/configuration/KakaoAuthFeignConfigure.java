package com.green.SearchPlace.application.configuration;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KakaoAuthFeignConfigure {
    @Bean
    public RequestInterceptor kakaoAuthFeignInterceptor(@Value("${restApi.kakao.restApiKey}") String restApiKey) {
        return requestTemplate -> requestTemplate.header("Authorization", restApiKey);
    }

}
