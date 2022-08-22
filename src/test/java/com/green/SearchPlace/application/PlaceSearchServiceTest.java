package com.green.SearchPlace.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.SearchPlace.adapter.in.web.controller.place.PlaceSearchCommand;
import com.green.SearchPlace.application.port.out.api.kakao.KakaoSearchAddressFeignClient;
import com.green.SearchPlace.application.port.out.api.kakao.KakaoSearchPlaceFeignClient;
import com.green.SearchPlace.application.port.out.api.naver.NaverSearchPlaceFeignClient;
import com.green.SearchPlace.application.port.out.persistence.KeywordQueryRepository;
import com.green.SearchPlace.application.search.place.KakaoAddressSearch;
import com.green.SearchPlace.application.search.place.KakaoPlaceSearch;
import com.green.SearchPlace.application.search.place.NaverPlaceSearch;
import com.green.SearchPlace.application.search.place.PlaceSearchService;
import com.green.SearchPlace.domain.rank.KeywordQuery;
import com.green.SearchPlace.domain.place.KakaoPlace;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class PlaceSearchServiceTest {

    @MockBean
    private KakaoSearchPlaceFeignClient kakaoSearchPlaceFeignClient;
    @MockBean
    private KakaoSearchAddressFeignClient kakaoSearchAddressFeignClient;
    @MockBean
    private NaverSearchPlaceFeignClient naverSearchPlaceFeignClient;
    @Autowired
    private KakaoPlaceSearch kakaoPlaceSearch;
    @Autowired
    private KakaoAddressSearch kakaoAddressSearch;
    @Autowired
    private NaverPlaceSearch naverPlaceSearch;
    @Autowired
    private KeywordQueryRepository queryCountRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void KakaoPlace_And_NaverPlace_Are_Merged() throws JsonProcessingException {
        String SEARCH_KEYWORD = "곱창";
        JsonNode kakaoJsonNode = objectMapper.readTree("{" +
                "\"documents\":["+
                    "{\"place_name\":\"해성막창집 본점\",\"address_name\":\"부산 해운대구 중동 1732\",\"road_address_name\":\"부산 해운대구 중동1로19번길 29\"},"+
                    "{\"place_name\":\"백화양곱창 6호\",\"address_name\":\"부산 중구 남포동6가 32\",\"road_address_name\":\"부산 중구 자갈치로23번길 6\"},"+
                    "{\"place_name\":\"별미곱창 본점\",\"address_name\":\"서울 송파구 방이동 64-1\",\"road_address_name\":\"서울 송파구 오금로11길 14\"},"+
                    "{\"place_name\":\"곱 마포점\",\"address_name\":\"서울 마포구 도화동 179-11\",\"road_address_name\":\"서울 마포구 도화길 31-1\"}," +
                    "{\"place_name\":\"세광양대창 교대본점\",\"address_name\":\"서울 서초구 서초동 1571-19\",\"road_address_name\":\"서울 서초구 반포대로28길 79\"}" +
                "]}");
        Mockito.when(kakaoSearchPlaceFeignClient.search(SEARCH_KEYWORD)).thenReturn(kakaoJsonNode);
        JsonNode naverJsonNode = objectMapper.readTree("{" +
                "\"items\":[" +
                    "{\"title\":\"백화양<b>곱창</b>\",\"address\":\"부산광역시 중구 남포동6가 32\",\"roadAddress\":\"부산광역시 중구 자갈치로23번길 6\"}," +
                    "{\"title\":\"청어람 망원점\",\"address\":\"서울특별시 마포구 망원동 482-3\",\"roadAddress\":\"서울특별시 마포구 망원로 97\"}," +
                    "{\"title\":\"곱 마포점\",\"address\":\"서울특별시 마포구 도화동 179-11\",\"roadAddress\":\"서울특별시 마포구 도화길 31-1\"}," +
                    "{\"title\":\"해성막창집 본점\",\"address\":\"부산광역시 해운대구 중동 1732\",\"roadAddress\":\"부산광역시 해운대구 중동1로19번길 29\"}," +
                    "{\"title\":\"평양집\",\"address\":\"서울특별시 용산구 한강로1가 137-1\",\"roadAddress\":\"서울특별시 용산구 한강대로 186\"}" +
                "]}");
        Mockito.when(naverSearchPlaceFeignClient.search(SEARCH_KEYWORD)).thenReturn(naverJsonNode);
        Mockito.when(kakaoSearchAddressFeignClient.search("부산광역시 해운대구 중동 1732")).thenReturn(objectMapper.readTree("{\"documents\": [{\"address_name\":\"부산 해운대구 중동 1732\"}]}"));
        Mockito.when(kakaoSearchAddressFeignClient.search("부산광역시 중구 남포동6가 32")).thenReturn(objectMapper.readTree("{\"documents\": [{\"address_name\":\"부산 중구 남포동6가 32\"}]}"));
        Mockito.when(kakaoSearchAddressFeignClient.search("서울특별시 마포구 도화동 179-11")).thenReturn(objectMapper.readTree("{\"documents\": [{\"address_name\":\"서울 마포구 도화동 179-11\"}]}"));
        Mockito.when(kakaoSearchAddressFeignClient.search("서울특별시 마포구 망원동 482-3")).thenReturn(objectMapper.readTree("{\"documents\": []}"));
        Mockito.when(kakaoSearchAddressFeignClient.search("서울특별시 용산구 한강로1가 137-1")).thenReturn(objectMapper.readTree("{\"documents\": []}"));

        PlaceSearchService placeListService = new PlaceSearchService(kakaoPlaceSearch, naverPlaceSearch, kakaoAddressSearch, objectMapper);
        String result = placeListService.SearchPlace(new PlaceSearchCommand(SEARCH_KEYWORD));

        // 키워드 검색 횟수가 카운팅되고 있는지 확인합니다.
        Optional<KeywordQuery> keywordQuery = queryCountRepository.findByKeyword(SEARCH_KEYWORD);
        keywordQuery.ifPresent(query -> Assertions.assertEquals(1, query.getCount()));
        // 카카오, 네이버 Place List 병합 결과를 확인합니다.
        String resultJson = objectMapper.readTree(result).path("placeList").toString();
        List<KakaoPlace> resultList = objectMapper.readValue(resultJson, new TypeReference<List<KakaoPlace>>() {});
        Assertions.assertEquals("해성막창집 본점", resultList.get(0).getTitle());
        Assertions.assertEquals("백화양곱창 6호", resultList.get(1).getTitle());
        Assertions.assertEquals("곱 마포점", resultList.get(2).getTitle());
        Assertions.assertEquals(7, resultList.size());
    }

}
