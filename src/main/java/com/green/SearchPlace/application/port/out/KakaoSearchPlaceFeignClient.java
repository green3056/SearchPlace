package com.green.SearchPlace.application.port.out;

import com.fasterxml.jackson.databind.JsonNode;
import com.green.SearchPlace.application.configuration.KakaoAuthFeignConfigure;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakaoSearchClient", url = "https://dapi.kakao.com/v2/local/search/keyword", configuration = KakaoAuthFeignConfigure.class)
public interface KakaoSearchPlaceFeignClient {
    @GetMapping
    JsonNode search(@RequestParam("query") String query);
}
