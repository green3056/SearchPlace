package com.green.SearchPlace.application.search.place;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.SearchPlace.application.port.out.api.naver.NaverSearchPlaceFeignClient;
import com.green.SearchPlace.domain.place.NaverPlace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NaverPlaceSearch {

    private final NaverSearchPlaceFeignClient naverSearchPlaceFeignClient;
    private final ObjectMapper objectMapper;

    @Autowired
    public NaverPlaceSearch(NaverSearchPlaceFeignClient naverSearchPlaceFeignClient, ObjectMapper objectMapper) {
        this.naverSearchPlaceFeignClient = naverSearchPlaceFeignClient;
        this.objectMapper = objectMapper;
    }

    public List<NaverPlace> placeList(String keyword) throws JsonProcessingException {
        JsonNode naverPlaceListNode = naverSearchPlaceFeignClient.search(keyword).path("items");
        return objectMapper.readValue(naverPlaceListNode.toString(), new TypeReference<List<NaverPlace>>() {});
    }

}
