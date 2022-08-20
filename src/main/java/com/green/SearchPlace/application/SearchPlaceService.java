package com.green.SearchPlace.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.SearchPlace.application.port.in.SearchPlaceUseCase;
import com.green.SearchPlace.application.port.out.KakaoSearchAddressFeignClient;
import com.green.SearchPlace.application.port.out.KakaoSearchPlaceFeignClient;
import com.green.SearchPlace.application.port.out.NaverSearchPlaceFeignClient;
import com.green.SearchPlace.application.port.out.QueryCountRepository;
import com.green.SearchPlace.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class SearchPlaceService implements SearchPlaceUseCase {

    private final QueryCountRepository queryCountRepository;
    private final KakaoSearchPlaceFeignClient kakaoSearchFeignClient;
    private final NaverSearchPlaceFeignClient naverSearchPlaceFeignClient;
    private final KakaoSearchAddressFeignClient kakaoSearchAddressFeignClient;
    private final ObjectMapper objectMapper;

    @Autowired
    SearchPlaceService(QueryCountRepository queryCountRepository, KakaoSearchPlaceFeignClient kakaoSearchFeignClient, NaverSearchPlaceFeignClient naverSearchPlaceFeignClient, KakaoSearchAddressFeignClient kakaoSearchAddressFeignClient) {
        this.queryCountRepository = queryCountRepository;
        this.kakaoSearchFeignClient = kakaoSearchFeignClient;
        this.naverSearchPlaceFeignClient = naverSearchPlaceFeignClient;
        this.kakaoSearchAddressFeignClient = kakaoSearchAddressFeignClient;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public String SearchPlace(String keyword) throws JsonProcessingException {
        KeywordQuery keywordQuery = queryCountRepository.findByKeyword(keyword).orElse(null);
        if (keywordQuery == null) {
           queryCountRepository.save(new KeywordQuery(keyword, 1L));
        } else {
            keywordQuery.implementCount();
            queryCountRepository.save(keywordQuery);
        }

        JsonNode kakaoPlaceListNode = kakaoSearchFeignClient.search(keyword).path("documents");
        List<KakaoPlace> kakaoPlaces = objectMapper.readValue(kakaoPlaceListNode.toString(), new TypeReference<List<KakaoPlace>>() {});

        JsonNode naverPlaceListNode = naverSearchPlaceFeignClient.search(keyword).path("items");
        List<NaverPlace> naverPlaces = objectMapper.readValue(naverPlaceListNode.toString(), new TypeReference<List<NaverPlace>>() {});

        for (Place naverPlace : naverPlaces) {
            String naverAddress = naverPlace.getAddress();
            JsonNode kakaoSearchAddressJson = kakaoSearchAddressFeignClient.search(naverAddress);
            List<KakaoAddress> kakaoSearchedAddressList = objectMapper.readValue(kakaoSearchAddressJson.path("documents").toString(), new TypeReference<List<KakaoAddress>>() {});
            if (!kakaoSearchedAddressList.isEmpty()) {
                String firstSearchAddress = kakaoSearchedAddressList.get(0).getAddress_name();
                naverPlace.setAddress(firstSearchAddress);
            }
        }

        PlaceListMergeEngine placeMergeEngine = new PlaceListMergeEngine(kakaoPlaces, naverPlaces);
        placeMergeEngine.merge();

        List<ResponsePlace> mergedPlaceList = placeMergeEngine.getMergedPlaceList();
        return objectMapper
                .writer()
                .withRootName("placeList")
                .writeValueAsString(mergedPlaceList);
    }

}
