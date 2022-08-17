package com.green.SearchPlace.application.port.out;

import com.fasterxml.jackson.databind.JsonNode;
import com.green.SearchPlace.application.configuration.NaverAuthFeignConfigure;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "naverSearchClient", url = "https://openapi.naver.com/v1/search/local.json?display=5&sort=random", configuration = NaverAuthFeignConfigure.class)
public interface NaverSearchPlaceFeignClient {
    @GetMapping
    JsonNode search(@RequestParam("query") String query);

}
