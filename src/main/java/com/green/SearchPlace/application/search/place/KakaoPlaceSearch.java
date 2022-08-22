package com.green.SearchPlace.application.search.place;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.SearchPlace.domain.keyword.Keyword;
import com.green.SearchPlace.application.port.out.api.kakao.KakaoSearchPlaceFeignClient;
import com.green.SearchPlace.domain.place.KakaoPlace;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class KakaoPlaceSearch implements PlaceSearch<KakaoPlace> {

    private final KakaoSearchPlaceFeignClient kakaoSearchFeignClient;
    private final ObjectMapper objectMapper;
    private Boolean isError = false;

    @Autowired
    public KakaoPlaceSearch(KakaoSearchPlaceFeignClient kakaoSearchFeignClient, ObjectMapper objectMapper) {
        this.kakaoSearchFeignClient = kakaoSearchFeignClient;
        this.objectMapper = objectMapper;
    }

    public List<KakaoPlace> placeList(Keyword keyword) {
        try {
            JsonNode kakaoPlaceListNode = kakaoSearchFeignClient.search(keyword.toString()).path("documents");
            return objectMapper.readValue(kakaoPlaceListNode.toString(), new TypeReference<List<KakaoPlace>>() {});
        }
        catch (Exception e) {
            log.error("KakaoPlaceSearchAPIException(code:KE001), message={}", e.getMessage());
            isError = true;
            return new ArrayList<>();
        }
    }

    public Boolean isError() {
        return isError;
    }

}
