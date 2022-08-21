package com.green.SearchPlace.domain.place;

public class KakaoPlace extends Place{

    @Override
    public ResponsePlace toResponsePlace() {
        return new ResponsePlace(getTitle(), getAddress(), "kakao");
    }

}
