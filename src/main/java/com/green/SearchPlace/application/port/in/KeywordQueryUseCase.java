package com.green.SearchPlace.application.port.in;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface KeywordQueryUseCase {

    String keywordQueryCountTop10() throws JsonProcessingException;
    void implementQueryCount(String keyword);

}
