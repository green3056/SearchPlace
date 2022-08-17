package com.green.SearchPlace.application.port.in;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface PlaceListUseCase {
    String places(String keyword) throws JsonProcessingException;
}
