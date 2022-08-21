package com.green.SearchPlace.adapter.in.web.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PlaceSearchKeywordEmptyException extends RuntimeException {

    public PlaceSearchKeywordEmptyException(String message) {
        super(message);
    }

}
