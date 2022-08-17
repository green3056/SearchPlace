package com.green.SearchPlace.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoPlace {
    private String address_name;
    private String road_address_name;
    private String place_name;

    public KakaoPlace() {

    }

}