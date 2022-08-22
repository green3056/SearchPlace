package com.green.SearchPlace.application.search.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PlaceSearchKeywordBlankException extends RuntimeException {

    public PlaceSearchKeywordBlankException(String keyword) {
        super(keyword + " can not be null or empty.");
    }

}
