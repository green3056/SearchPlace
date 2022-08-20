package com.green.SearchPlace.domain;

public class KakaoPlace extends Place{

    @Override
    ResponsePlace toResponsePlace() {
        return new ResponsePlace(getTitle(), getAddress(), "kakao");
    }

}
