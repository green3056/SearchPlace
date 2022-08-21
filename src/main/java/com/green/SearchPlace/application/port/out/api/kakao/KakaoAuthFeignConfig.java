package com.green.SearchPlace.application.port.out.api.kakao;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KakaoAuthFeignConfig {

    @Bean
    public RequestInterceptor kakaoAuthFeignInterceptor(@Value("${restApi.kakao.restApiKey}") String restApiKey) {
        return requestTemplate -> requestTemplate.header("Authorization", restApiKey);
    }

}
