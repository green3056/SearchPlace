package com.green.SearchPlace.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverPlace {
    private String title;
    private String address;
    private String roadAddress;

    public NaverPlace() {

    }

}