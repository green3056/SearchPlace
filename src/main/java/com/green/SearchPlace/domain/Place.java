package com.green.SearchPlace.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
abstract public class Place {

    @JsonAlias({"title", "place_name"})
    private String title;
    @JsonAlias({"address", "address_name"})
    private String address;
    @JsonAlias({"roadAddress", "road_address_name"})
    private String roadAddress;

    abstract ResponsePlace toResponsePlace();

}
