package com.green.SearchPlace.application.search.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PlaceSearchKeywordBlankException extends RuntimeException {

    public PlaceSearchKeywordBlankException(String value) {
        super(value + " can not be null or empty.");
    }

}
