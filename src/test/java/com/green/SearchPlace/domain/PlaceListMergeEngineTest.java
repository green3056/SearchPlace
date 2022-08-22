package com.green.SearchPlace.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.SearchPlace.domain.place.KakaoPlace;
import com.green.SearchPlace.domain.place.NaverPlace;
import com.green.SearchPlace.domain.place.PlaceListMergeEngine;
import com.green.SearchPlace.domain.response.place.ResponsePlace;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class PlaceListMergeEngineTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void Duplicate_Place_Is_First() throws JsonProcessingException {
        String kakaoPlaceListJson = "[" +
                "{\"place_name\":\"해성막창집 본점\",\"address_name\":\"부산 해운대구 중동 1732\",\"road_address_name\":\"부산 해운대구 중동1로19번길 29\"}," +
                "{\"place_name\":\"백화양곱창 6호\",\"address_name\":\"부산 중구 남포동6가 32\",\"road_address_name\":\"부산 중구 자갈치로23번길 6\"}," +
                "{\"place_name\":\"별미곱창 본점\",\"address_name\":\"서울 송파구 방이동 64-1\",\"road_address_name\":\"서울 송파구 오금로11길 14\"}," +
                "{\"place_name\":\"곱 마포점\",\"address_name\":\"서울 마포구 도화동 179-11\",\"road_address_name\":\"서울 마포구 도화길 31-1\"}," +
                "{\"place_name\":\"세광양대창 교대본점\",\"address_name\":\"서울 서초구 서초동 1571-19\",\"road_address_name\":\"서울 서초구 반포대로28길 79\"}" +
                "]";
        List<KakaoPlace> kakaoPlaceList = objectMapper.readValue(kakaoPlaceListJson, new TypeReference<List<KakaoPlace>>() {});
        String naverPlaceListJson = "[" +
                "{\"title\":\"백화양<b>곱창</b>\",\"address\":\"부산 중구 남포동6가 32\",\"roadAddress\":\"부산광역시 중구 자갈치로23번길 6\"}," +
                "{\"title\":\"청어람 망원점\",\"address\":\"서울특별시 마포구 망원동 482-3\",\"roadAddress\":\"서울특별시 마포구 망원로 97\"}," +
                "{\"title\":\"곱 마포점\",\"address\":\"서울 마포구 도화동 179-11\",\"roadAddress\":\"서울특별시 마포구 도화길 31-1\"}," +
                "{\"title\":\"해성막창집 본점\",\"address\":\"부산 해운대구 중동 1732\",\"roadAddress\":\"부산광역시 해운대구 중동1로19번길 29\"}," +
                "{\"title\":\"평양집\",\"address\":\"서울특별시 용산구 한강로1가 137-1\",\"roadAddress\":\"서울특별시 용산구 한강대로 186\"}" +
                "]";
        List<NaverPlace> naverPlaceList = objectMapper.readValue(naverPlaceListJson, new TypeReference<List<NaverPlace>>() {});

        PlaceListMergeEngine placeMergeEngine = new PlaceListMergeEngine(kakaoPlaceList, naverPlaceList);
        List<ResponsePlace> responsePlaceList = placeMergeEngine.mergedResponsePlaceList();

        Assertions.assertEquals("해성막창집 본점", responsePlaceList.get(0).getTitle());
        Assertions.assertEquals("백화양곱창 6호", responsePlaceList.get(1).getTitle());
        Assertions.assertEquals("곱 마포점", responsePlaceList.get(2).getTitle());
        Assertions.assertEquals(7, responsePlaceList.size());
    }

    @Test
    public void Kakao_Place_Is_First() throws JsonProcessingException {
        String kakaoPlaceListJson = "[" +
                "{\"place_name\":\"별미곱창 본점\",\"address_name\":\"서울 송파구 방이동 64-1\",\"road_address_name\":\"서울 송파구 오금로11길 14\"}," +
                "{\"place_name\":\"세광양대창 교대본점\",\"address_name\":\"서울 서초구 서초동 1571-19\",\"road_address_name\":\"서울 서초구 반포대로28길 79\"}" +
                "]";
        List<KakaoPlace> kakaoPlaceList = objectMapper.readValue(kakaoPlaceListJson, new TypeReference<List<KakaoPlace>>() {});
        String naverPlaceListJson = "[" +
                "{\"title\":\"백화양<b>곱창</b>\",\"address\":\"부산광역시 중구 남포동6가 32\",\"roadAddress\":\"부산광역시 중구 자갈치로23번길 6\"}," +
                "{\"title\":\"청어람 망원점\",\"address\":\"서울특별시 마포구 망원동 482-3\",\"roadAddress\":\"서울특별시 마포구 망원로 97\"}," +
                "{\"title\":\"곱 마포점\",\"address\":\"서울특별시 마포구 도화동 179-11\",\"roadAddress\":\"서울특별시 마포구 도화길 31-1\"}," +
                "{\"title\":\"평양집\",\"address\":\"서울특별시 용산구 한강로1가 137-1\",\"roadAddress\":\"서울특별시 용산구 한강대로 186\"}" +
                "]";
        List<NaverPlace> naverPlaceList = objectMapper.readValue(naverPlaceListJson, new TypeReference<List<NaverPlace>>() {});

        PlaceListMergeEngine placeMergeEngine = new PlaceListMergeEngine(kakaoPlaceList, naverPlaceList);
        List<ResponsePlace> responsePlaceList = placeMergeEngine.mergedResponsePlaceList();

        Assertions.assertEquals("별미곱창 본점", responsePlaceList.get(0).getTitle());
        Assertions.assertEquals("세광양대창 교대본점", responsePlaceList.get(1).getTitle());
        Assertions.assertEquals("백화양<b>곱창</b>", responsePlaceList.get(2).getTitle());
        Assertions.assertEquals(6, responsePlaceList.size());
    }

    @Test
    public void Kakao_Place_List_Is_Empty() throws JsonProcessingException {
        String kakaoPlaceListJson = "[" +
                "]";
        List<KakaoPlace> kakaoPlaceList = objectMapper.readValue(kakaoPlaceListJson, new TypeReference<List<KakaoPlace>>() {});
        String naverPlaceListJson = "[" +
                "{\"title\":\"백화양<b>곱창</b>\",\"address\":\"부산광역시 중구 남포동6가 32\",\"roadAddress\":\"부산광역시 중구 자갈치로23번길 6\"}," +
                "{\"title\":\"곱 마포점\",\"address\":\"서울특별시 마포구 도화동 179-11\",\"roadAddress\":\"서울특별시 마포구 도화길 31-1\"}," +
                "{\"title\":\"해성막창집 본점\",\"address\":\"부산광역시 해운대구 중동 1732\",\"roadAddress\":\"부산광역시 해운대구 중동1로19번길 29\"}," +
                "{\"title\":\"평양집\",\"address\":\"서울특별시 용산구 한강로1가 137-1\",\"roadAddress\":\"서울특별시 용산구 한강대로 186\"}" +
                "]";
        List<NaverPlace> naverPlaceList = objectMapper.readValue(naverPlaceListJson, new TypeReference<List<NaverPlace>>() {});

        PlaceListMergeEngine placeMergeEngine = new PlaceListMergeEngine(kakaoPlaceList, naverPlaceList);

        Assertions.assertEquals(4, placeMergeEngine.mergedResponsePlaceList().size());
    }

    @Test
    public void Naver_Place_List_Is_Empty() throws JsonProcessingException {
        String kakaoPlaceListJson = "[" +
                "{\"place_name\":\"해성막창집 본점\",\"address_name\":\"부산 해운대구 중동 1732\",\"road_address_name\":\"부산 해운대구 중동1로19번길 29\"}" +
                "]";
        List<KakaoPlace> kakaoPlaceList = objectMapper.readValue(kakaoPlaceListJson, new TypeReference<List<KakaoPlace>>() {});
        String naverPlaceListJson = "[" +
                "]";
        List<NaverPlace> naverPlaceList = objectMapper.readValue(naverPlaceListJson, new TypeReference<List<NaverPlace>>() {});

        PlaceListMergeEngine placeMergeEngine = new PlaceListMergeEngine(kakaoPlaceList, naverPlaceList);

        Assertions.assertEquals(1, placeMergeEngine.mergedResponsePlaceList().size());
    }

    @Test
    public void Place_Lists_Are_Empty() throws JsonProcessingException {
        String kakaoPlaceListJson = "[" +
                "]";
        List<KakaoPlace> kakaoPlaceList = objectMapper.readValue(kakaoPlaceListJson, new TypeReference<List<KakaoPlace>>() {});
        String naverPlaceListJson = "[" +
                "]";
        List<NaverPlace> naverPlaceList = objectMapper.readValue(naverPlaceListJson, new TypeReference<List<NaverPlace>>() {});

        PlaceListMergeEngine placeMergeEngine = new PlaceListMergeEngine(kakaoPlaceList, naverPlaceList);

        Assertions.assertEquals(0, placeMergeEngine.mergedResponsePlaceList().size());
    }

}
