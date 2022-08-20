package com.green.SearchPlace.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.SearchPlace.application.port.in.SearchPlaceUseCase;
import com.green.SearchPlace.application.port.out.KakaoSearchAddressFeignClient;
import com.green.SearchPlace.application.port.out.KakaoSearchPlaceFeignClient;
import com.green.SearchPlace.application.port.out.NaverSearchPlaceFeignClient;
import com.green.SearchPlace.domain.KakaoPlace;
import com.green.SearchPlace.domain.NaverPlace;
import com.green.SearchPlace.domain.PlaceListMergeEngine;
import com.green.SearchPlace.domain.ResponsePlace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class SearchPlaceService implements SearchPlaceUseCase {

    private final KakaoSearchPlaceFeignClient kakaoSearchFeignClient;
    private final NaverSearchPlaceFeignClient naverSearchPlaceFeignClient;
    private final KakaoSearchAddressFeignClient kakaoSearchAddressFeignClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    SearchPlaceService(KakaoSearchPlaceFeignClient kakaoSearchFeignClient, NaverSearchPlaceFeignClient naverSearchPlaceFeignClient, KakaoSearchAddressFeignClient kakaoSearchAddressFeignClient) {
        this.kakaoSearchFeignClient = kakaoSearchFeignClient;
        this.naverSearchPlaceFeignClient = naverSearchPlaceFeignClient;
        this.kakaoSearchAddressFeignClient = kakaoSearchAddressFeignClient;
    }

    @Override
    public String SearchPlace(String keyword) throws JsonProcessingException {
        List<KakaoPlace> kakaoPlaceList = kakaoPlaceList(keyword);
        List<NaverPlace> naverPlaceList = naverPlaceList(keyword);
        new AddressConverter<>(naverPlaceList, kakaoSearchAddressFeignClient, objectMapper)
                .convertToKakaoAddress();
        return objectMapper
                .writer()
                .withRootName("placeList")
                .writeValueAsString(responsePlaceList(kakaoPlaceList, naverPlaceList));
    }

    private List<KakaoPlace> kakaoPlaceList(String keyword) throws JsonProcessingException {
        JsonNode kakaoPlaceListNode = kakaoSearchFeignClient.search(keyword).path("documents");
        return objectMapper.readValue(kakaoPlaceListNode.toString(), new TypeReference<List<KakaoPlace>>() {});
    }

    private List<NaverPlace> naverPlaceList(String keyword) throws JsonProcessingException {
        JsonNode naverPlaceListNode = naverSearchPlaceFeignClient.search(keyword).path("items");
        return objectMapper.readValue(naverPlaceListNode.toString(), new TypeReference<List<NaverPlace>>() {});
    }

    private List<ResponsePlace> responsePlaceList(List<KakaoPlace> kakaoPlaceList, List<NaverPlace> naverPlaceList) {
        return new PlaceListMergeEngine(kakaoPlaceList, naverPlaceList).mergedResponsePlaceList();
    }

}
