package com.green.SearchPlace.application.port.in;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface QueryCountListUseCase {
    String keywordQueryCountTop10() throws JsonProcessingException;
}
