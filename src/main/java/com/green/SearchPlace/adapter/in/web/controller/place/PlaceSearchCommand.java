package com.green.SearchPlace.adapter.in.web.controller.place;

import com.green.SearchPlace.domain.keyword.Keyword;
import lombok.Getter;

@Getter
public class PlaceSearchCommand {
    private final Keyword keyword;

    public PlaceSearchCommand(String keyword) {
        this.keyword = new Keyword(keyword);
    }

}
