package com.green.SearchPlace.application.search.place;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.SearchPlace.application.port.in.SearchPlaceUseCase;
import com.green.SearchPlace.domain.place.KakaoPlace;
import com.green.SearchPlace.domain.place.NaverPlace;
import com.green.SearchPlace.domain.place.PlaceListMergeEngine;
import com.green.SearchPlace.domain.place.ResponsePlace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceSerachService implements SearchPlaceUseCase {

    private final KakaoPlaceSearch kakaoPlaceSearch;
    private final NaverPlaceSearch naverPlaceSearch;
    private final KakaoAddressSearch kakaoAddressSearch;
    private final ObjectMapper objectMapper;

    @Autowired
    public PlaceSerachService(KakaoPlaceSearch kakaoPlaceSearch, NaverPlaceSearch naverPlaceSearch, KakaoAddressSearch kakaoAddressSearch, ObjectMapper objectMapper) {
        this.kakaoPlaceSearch = kakaoPlaceSearch;
        this.naverPlaceSearch = naverPlaceSearch;
        this.kakaoAddressSearch = kakaoAddressSearch;
        this.objectMapper = objectMapper;
    }

    @Override
    public String SearchPlace(String keyword) throws JsonProcessingException {
        List<KakaoPlace> kakaoPlaceList = kakaoPlaceSearch.placeList(keyword);
        List<NaverPlace> naverPlaceList = naverPlaceSearch.placeList(keyword);

        // 여러 개의 장소 리스트를 병합하기 위해 카카오 주소 검색 API V2를 사옹하여 카카오의 주소 표기 방식으로 통일
        new AddressConverter<>(naverPlaceList, kakaoAddressSearch).convertToKakaoAddress();

        // 주소를 기준으로 비교하여 장소 리스트를 병합
        List<ResponsePlace> mergedPlaceList = merge(kakaoPlaceList, naverPlaceList);

        return objectMapper
                .writer()
                .withRootName("placeList")
                .writeValueAsString(mergedPlaceList);
    }

    private List<ResponsePlace> merge(List<KakaoPlace> kakaoPlaceList, List<NaverPlace> naverPlaceList) {
        return new PlaceListMergeEngine(kakaoPlaceList, naverPlaceList).mergedResponsePlaceList();
    }

}
