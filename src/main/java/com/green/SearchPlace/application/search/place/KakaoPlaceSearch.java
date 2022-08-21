package com.green.SearchPlace.application.search.place;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.SearchPlace.application.port.out.api.kakao.KakaoSearchPlaceFeignClient;
import com.green.SearchPlace.domain.place.KakaoPlace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KakaoPlaceSearch {

    private final KakaoSearchPlaceFeignClient kakaoSearchFeignClient;
    private final ObjectMapper objectMapper;

    @Autowired
    public KakaoPlaceSearch(KakaoSearchPlaceFeignClient kakaoSearchFeignClient, ObjectMapper objectMapper) {
        this.kakaoSearchFeignClient = kakaoSearchFeignClient;
        this.objectMapper = objectMapper;
    }

    public List<KakaoPlace> placeList(String keyword) throws JsonProcessingException {
        JsonNode kakaoPlaceListNode = kakaoSearchFeignClient.search(keyword).path("documents");
        return objectMapper.readValue(kakaoPlaceListNode.toString(), new TypeReference<List<KakaoPlace>>() {});
    }

}
