package com.green.SearchPlace.application.search.place;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.SearchPlace.adapter.in.web.controller.place.PlaceSearchCommand;
import com.green.SearchPlace.application.search.rank.PlaceKeywordQueryRankService;
import com.green.SearchPlace.domain.keyword.Keyword;
import com.green.SearchPlace.application.port.in.PlaceSearchUseCase;
import com.green.SearchPlace.application.search.exception.APICallException;
import com.green.SearchPlace.domain.place.KakaoPlace;
import com.green.SearchPlace.domain.place.NaverPlace;
import com.green.SearchPlace.domain.place.PlaceListMergeEngine;
import com.green.SearchPlace.domain.response.place.ResponsePlace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PlaceSearchService implements PlaceSearchUseCase {

    private final PlaceSearch<KakaoPlace> kakaoPlaceSearch;
    private final PlaceSearch<NaverPlace> naverPlaceSearch;
    private final KakaoAddressSearch kakaoAddressSearch;
    private final PlaceKeywordQueryRankService placeKeywordQueryRankService;
    private final ObjectMapper objectMapper;

    @Autowired
    public PlaceSearchService(PlaceSearch<KakaoPlace> kakaoPlaceSearch, PlaceSearch<NaverPlace> naverPlaceSearch, KakaoAddressSearch kakaoAddressSearch, PlaceKeywordQueryRankService placeKeywordQueryRankService, ObjectMapper objectMapper) {
        this.kakaoPlaceSearch = kakaoPlaceSearch;
        this.naverPlaceSearch = naverPlaceSearch;
        this.kakaoAddressSearch = kakaoAddressSearch;
        this.placeKeywordQueryRankService = placeKeywordQueryRankService;
        this.objectMapper = objectMapper;
    }

    @Override
    public String responsePlaces(PlaceSearchCommand command) throws JsonProcessingException {
        log.info("START responsePlaces method, keyword="+command.getKeyword());
        Keyword keyword = command.getKeyword();
        List<KakaoPlace> kakaoPlaceList = kakaoPlaceSearch.placeList(keyword);
        List<NaverPlace> naverPlaceList = naverPlaceSearch.placeList(keyword);
        if (kakaoPlaceSearch.isError() && naverPlaceSearch.isError()) {
            throw new APICallException("Both kakao and naver API call were fail.");
        }

        // ?????? ?????? ?????? ???????????? ???????????? ?????? ????????? ?????? ?????? API V2??? ???????????? ???????????? ?????? ?????? ???????????? ??????
        new AddressConverter<>(naverPlaceList, kakaoAddressSearch).convertToKakaoAddress();

        // ????????? ???????????? ???????????? ?????? ???????????? ??????
        List<ResponsePlace> mergedPlaceList = merge(kakaoPlaceList, naverPlaceList);

        placeKeywordQueryRankService.implementKeywordQueryCount(command);

        log.info("END responsePlaces method");
        return objectMapper
                .writer()
                .withRootName("placeList")
                .writeValueAsString(mergedPlaceList);
    }

    private List<ResponsePlace> merge(List<KakaoPlace> mainPlaceList, List<NaverPlace> subPlaceList) {
        return new PlaceListMergeEngine<>(mainPlaceList, subPlaceList).mergedResponsePlaceList();
    }

}
