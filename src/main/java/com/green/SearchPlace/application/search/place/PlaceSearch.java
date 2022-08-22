package com.green.SearchPlace.application.search.place;

import com.green.SearchPlace.domain.keyword.Keyword;

import java.util.List;

public interface PlaceSearch <P> {

     List<P> placeList(Keyword keyword);
     Boolean isError();

}
