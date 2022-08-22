package com.green.SearchPlace.application.search.place;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.SearchPlace.domain.keyword.Keyword;
import com.green.SearchPlace.application.port.out.api.naver.NaverSearchPlaceFeignClient;
import com.green.SearchPlace.domain.place.NaverPlace;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Getter
public class NaverPlaceSearch implements PlaceSearch<NaverPlace> {

    private final NaverSearchPlaceFeignClient naverSearchPlaceFeignClient;
    private final ObjectMapper objectMapper;
    private Boolean isError = false;

    @Autowired
    public NaverPlaceSearch(NaverSearchPlaceFeignClient naverSearchPlaceFeignClient, ObjectMapper objectMapper) {
        this.naverSearchPlaceFeignClient = naverSearchPlaceFeignClient;
        this.objectMapper = objectMapper;
    }

    public List<NaverPlace> placeList(Keyword keyword) {
        try {
            JsonNode naverPlaceListNode = naverSearchPlaceFeignClient.search(keyword.toString()).path("items");
            return objectMapper.readValue(naverPlaceListNode.toString(), new TypeReference<List<NaverPlace>>() {});
        }
        catch (Exception e) {
            log.error("NaverPlaceSearchAPIException(code:NE001), message={}", e.getMessage());
            isError = true;
            return new ArrayList<>();
        }
    }

    public Boolean isError() {
        return isError;
    }

}
