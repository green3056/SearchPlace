package com.green.SearchPlace.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@JsonAutoDetect
public class Address {

    private final String value;

    public Address(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Address is can not be null.");
        }

        if (value.split(" ")[0].contains("광역시") ) {
            this.value = value.replaceFirst("광역시", "");
        } else if (value.split(" ")[0].contains("특별시")){
            this.value = value.replaceFirst("특별시", "");
        } else {
            this.value = value;
        }
    }

}
