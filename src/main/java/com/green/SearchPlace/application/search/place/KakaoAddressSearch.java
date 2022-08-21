package com.green.SearchPlace.application.search.place;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.SearchPlace.application.port.out.api.kakao.KakaoSearchAddressFeignClient;
import com.green.SearchPlace.domain.address.KakaoAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KakaoAddressSearch {

    private final KakaoSearchAddressFeignClient kakaoSearchAddressFeignClient;
    private final ObjectMapper objectMapper;

    @Autowired
    public KakaoAddressSearch(KakaoSearchAddressFeignClient kakaoSearchAddressFeignClient, ObjectMapper objectMapper) {
        this.kakaoSearchAddressFeignClient = kakaoSearchAddressFeignClient;
        this.objectMapper = objectMapper;
    }

    List<KakaoAddress> addressList(String address) throws JsonProcessingException {
        JsonNode kakaoSearchAddressNode = kakaoSearchAddressFeignClient.search(address);
        return objectMapper.readValue(kakaoSearchAddressNode.path("documents").toString(), new TypeReference<List<KakaoAddress>>() {});
    }

}
