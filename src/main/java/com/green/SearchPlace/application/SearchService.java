package com.green.SearchPlace.application;

import com.green.SearchPlace.application.port.in.SearchUseCase;
import com.green.SearchPlace.application.port.out.SearchCountPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SearchService implements SearchUseCase {

    private final SearchCountPort searchCounter;

    @Autowired
    SearchService(SearchCountPort searchCounter) {
        this.searchCounter = searchCounter;
    }

    @Override
    public String places(String keyword) {
        int count = searchCounter.incrementSearchCount(keyword);

        return  "SearchCount of \""+keyword+"\" : " + count;
    }
}
