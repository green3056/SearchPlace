package com.green.SearchPlace.application.port.out.api.kakao;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakaoSearchPlaceClient", url = "https://dapi.kakao.com/v2/local/search/keyword?size=5", configuration = KakaoAuthFeignConfig.class)
public interface KakaoSearchPlaceFeignClient {

    @GetMapping
    JsonNode search(@RequestParam("query") String query);

}
