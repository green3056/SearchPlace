package com.green.SearchPlace.application.port.out.api.naver;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "naverSearchPlaceClient", url = "https://openapi.naver.com/v1/search/local.json?display=5&sort=random", configuration = NaverAuthFeignConfig.class)
public interface NaverSearchPlaceFeignClient {

    @GetMapping
    JsonNode search(@RequestParam("query") String query);

}
