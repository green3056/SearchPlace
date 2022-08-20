package com.green.SearchPlace.domain;

public class NaverPlace extends Place{

    @Override
    ResponsePlace toResponsePlace() {
        return new ResponsePlace(getTitle(), getAddress(), "naver");
    }

}
