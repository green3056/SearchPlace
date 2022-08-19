package com.green.SearchPlace.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.SearchPlace.application.port.in.PlaceListUseCase;
import com.green.SearchPlace.application.port.out.KakaoSearchPlaceFeignClient;
import com.green.SearchPlace.application.port.out.NaverSearchPlaceFeignClient;
import com.green.SearchPlace.application.port.out.QueryCountRepository;
import com.green.SearchPlace.domain.Place;
import com.green.SearchPlace.domain.PlaceListMergeEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class PlaceListService implements PlaceListUseCase {

    private final QueryCountRepository queryCountRepository;
    private final KakaoSearchPlaceFeignClient kakaoSearchFeignClient;
    private final NaverSearchPlaceFeignClient naverSearchPlaceFeignClient;
    private final ObjectMapper objectMapper;

    @Autowired
    PlaceListService(QueryCountRepository queryCountRepository, KakaoSearchPlaceFeignClient kakaoSearchFeignClient, NaverSearchPlaceFeignClient naverSearchPlaceFeignClient) {
        this.queryCountRepository = queryCountRepository;
        this.kakaoSearchFeignClient = kakaoSearchFeignClient;
        this.naverSearchPlaceFeignClient = naverSearchPlaceFeignClient;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public String places(String keyword) throws JsonProcessingException {
        KeywordQuery keywordQuery = queryCountRepository.findByKeyword(keyword).orElse(null);
        if (keywordQuery == null) {
           queryCountRepository.save(new KeywordQuery(keyword, 1));
        } else {
            keywordQuery.implementCount();
            queryCountRepository.save(keywordQuery);
        }

        JsonNode kakaoPlaceListNode = kakaoSearchFeignClient.search(keyword).path("documents");
        List<Place> kakaoPlaces = objectMapper.readValue(kakaoPlaceListNode.toString(), new TypeReference<List<Place>>() {});

        JsonNode naverPlaceListNode = naverSearchPlaceFeignClient.search(keyword).path("items");
        List<Place> naverPlaces = objectMapper.readValue(naverPlaceListNode.toString(), new TypeReference<List<Place>>() {});

        PlaceListMergeEngine placeMergeEngine = new PlaceListMergeEngine(kakaoPlaces, naverPlaces);
        placeMergeEngine.merge();

        List<Place> mergedPlaceList = placeMergeEngine.getMergedPlaceList();
        return objectMapper
                .writer()
                .withRootName("placeList")
                .writeValueAsString(mergedPlaceList);
    }

}
