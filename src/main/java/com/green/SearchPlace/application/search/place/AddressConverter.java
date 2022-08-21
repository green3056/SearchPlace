package com.green.SearchPlace.application.search.place;

import com.green.SearchPlace.domain.address.KakaoAddress;
import com.green.SearchPlace.domain.place.Place;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class AddressConverter<P extends Place> {
    private final List<P> placeList;
    private final KakaoAddressSearch kakaoAddressSearch;

    public AddressConverter(List<P> placeList, KakaoAddressSearch kakaoAddressSearch) {
        this.placeList = placeList;
        this.kakaoAddressSearch = kakaoAddressSearch;
    }

    public void convertToKakaoAddress() {
        for (P place : placeList) {
            String address = place.getAddress();
            try {
                List<KakaoAddress> kakaoSearchedAddressList = kakaoAddressSearch.addressList(address);
                if (!kakaoSearchedAddressList.isEmpty()) {
                    String firstSearchAddress = kakaoSearchedAddressList.get(0).getAddress_name();
                    place.setAddress(firstSearchAddress);
                }
            }
            catch (Exception e) {
                log.error("KakaoAddressSearchAPIException(code:KE002), message={}", e.getMessage());
                break;
            }
        }
    }

}
