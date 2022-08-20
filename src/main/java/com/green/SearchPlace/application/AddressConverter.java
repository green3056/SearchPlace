package com.green.SearchPlace.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.SearchPlace.application.port.out.KakaoSearchAddressFeignClient;
import com.green.SearchPlace.domain.KakaoAddress;
import com.green.SearchPlace.domain.Place;

import java.util.List;

public class AddressConverter<T extends Place> {
    private final List<T> placeList;
    private final KakaoSearchAddressFeignClient kakaoSearchAddressFeignClient;
    private final ObjectMapper objectMapper;

    public AddressConverter(List<T> placeList, KakaoSearchAddressFeignClient kakaoSearchAddressFeignClient, ObjectMapper objectMapper) {
        this.placeList = placeList;
        this.kakaoSearchAddressFeignClient = kakaoSearchAddressFeignClient;
        this.objectMapper = objectMapper;
    }

    // 주소 표기를 카카오의 표기 방식과 일치시키기 위해 카카오 주소 검색 API V2를 사옹
    public void convertToKakaoAddress() throws JsonProcessingException {
        for (T place : placeList) {
            String address = place.getAddress();
            JsonNode kakaoSearchAddressNode = kakaoSearchAddressFeignClient.search(address);
            List<KakaoAddress> kakaoSearchedAddressList = objectMapper.readValue(kakaoSearchAddressNode.path("documents").toString(), new TypeReference<List<KakaoAddress>>() {});
            if (!kakaoSearchedAddressList.isEmpty()) {
                String firstSearchAddress = kakaoSearchedAddressList.get(0).getAddress_name();
                place.setAddress(firstSearchAddress);
            }
        }
    }
}
