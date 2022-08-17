package com.green.SearchPlace.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoPlace {

    private String place_name;
    private String address_name;
    private String road_address_name;

}
