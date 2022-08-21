package com.green.SearchPlace.application.search.place;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.green.SearchPlace.domain.address.KakaoAddress;
import com.green.SearchPlace.domain.place.Place;

import java.util.List;

public class AddressConverter<T extends Place> {
    private final List<T> placeList;
    private final KakaoAddressSearch kakaoAddressSearch;

    public AddressConverter(List<T> placeList, KakaoAddressSearch kakaoAddressSearch) {
        this.placeList = placeList;
        this.kakaoAddressSearch = kakaoAddressSearch;
    }

    public void convertToKakaoAddress() throws JsonProcessingException {
        for (T place : placeList) {
            String address = place.getAddress();
            List<KakaoAddress> kakaoSearchedAddressList = kakaoAddressSearch.addressList(address);
            if (!kakaoSearchedAddressList.isEmpty()) {
                String firstSearchAddress = kakaoSearchedAddressList.get(0).getAddress_name();
                place.setAddress(firstSearchAddress);
            }
        }
    }

}
