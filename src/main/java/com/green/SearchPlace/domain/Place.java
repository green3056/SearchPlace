package com.green.SearchPlace.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Objects;

@ToString
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Place {

    @JsonAlias({"title", "place_name"})
    private String title;
    @JsonAlias({"address", "address_name"})
    private String address;
    @JsonAlias({"roadAddress", "road_address_name"})
    private String roadAddress;

    public Place(String title, String address, String roadAddress) {
        if (title == null) {
            throw new IllegalArgumentException("title is can not be null.");
        }
        if (roadAddress == null) {
            throw new IllegalArgumentException("roadAddress is can not be null.");
        }
        if (address == null) {
            throw new IllegalArgumentException("address is can not be null.");
        }

        this.title = title;
        this.roadAddress = roadAddress;

        if (address.split(" ")[0].contains("광역시") ) {
            this.address = address.replaceFirst("광역시", "");
        } else if (address.split(" ")[0].contains("특별시")){
            this.address = address.replaceFirst("특별시", "");
        } else {
            this.address = address;
        }
    }

    public Place toResponsePlace() {
        return new Place(title, address, roadAddress);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Place otherPlace = (Place)o;

        return address.equals(otherPlace.address);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(address);
    }

}

