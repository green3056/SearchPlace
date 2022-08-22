package com.green.SearchPlace.domain.keyword;

import com.green.SearchPlace.application.search.exception.PlaceSearchKeywordBlankException;

public class Keyword {

    private final String value;

    public Keyword(String value) {
        if (value == null || value.isEmpty()) {
            throw new PlaceSearchKeywordBlankException("keyword");
        }
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
