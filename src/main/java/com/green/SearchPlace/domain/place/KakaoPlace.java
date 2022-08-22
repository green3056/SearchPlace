package com.green.SearchPlace.domain.place;

import com.green.SearchPlace.domain.response.place.ResponsePlace;

public class KakaoPlace extends Place{

    @Override
    public ResponsePlace toResponsePlace() {
        return new ResponsePlace(getTitle(), getAddress(), "kakao");
    }

}
