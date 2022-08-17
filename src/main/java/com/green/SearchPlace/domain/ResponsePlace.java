package com.green.SearchPlace.domain;

import lombok.*;

@ToString
@Getter
@Setter
public class ResponsePlace {

    private String title;
    private Address address;
    private String roadAddress;

    public ResponsePlace(KakaoPlace kakaoPlace) {
        this.title = kakaoPlace.getPlace_name();
        this.address = new Address(kakaoPlace.getAddress_name());
        this.roadAddress = kakaoPlace.getRoad_address_name();
    }

    public ResponsePlace(NaverPlace naverPlace) {
        this.title = naverPlace.getTitle();
        this.address = new Address(naverPlace.getAddress());
        this.roadAddress = naverPlace.getRoadAddress();
    }

}
