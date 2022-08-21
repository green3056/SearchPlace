package com.green.SearchPlace.domain.place;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class ResponsePlace {

    private String title;
    private String address;
    private String provider;

    public ResponsePlace(String title, String address, String provider) {
        if (title == null) {
            throw new IllegalArgumentException("title is can not be null.");
        }
        if (address == null) {
            throw new IllegalArgumentException("address is can not be null.");
        }
        if (provider == null) {
            throw new IllegalArgumentException("provider is can not be null.");
        }

        this.title = title;
        this.address = address;
        this.provider = provider;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ResponsePlace otherPlace = (ResponsePlace) o;

        return address.equals(otherPlace.address);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(address);
    }

}
