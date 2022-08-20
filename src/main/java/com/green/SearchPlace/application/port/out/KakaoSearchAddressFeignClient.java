package com.green.SearchPlace.application.port.out;

import com.fasterxml.jackson.databind.JsonNode;
import com.green.SearchPlace.application.configuration.KakaoAuthFeignConfigure;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakaoSearchAddressClient", url = "https://dapi.kakao.com/v2/local/search/address.json?analyze_type=exact", configuration = KakaoAuthFeignConfigure.class)
public interface KakaoSearchAddressFeignClient {
    @GetMapping
    JsonNode search(@RequestParam("query") String query);
}
