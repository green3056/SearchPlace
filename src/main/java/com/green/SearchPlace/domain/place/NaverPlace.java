package com.green.SearchPlace.domain.place;

public class NaverPlace extends Place{

    @Override
    public ResponsePlace toResponsePlace() {
        return new ResponsePlace(getTitle(), getAddress(), "naver");
    }

}
