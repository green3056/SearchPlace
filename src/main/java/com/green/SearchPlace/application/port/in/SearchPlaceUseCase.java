package com.green.SearchPlace.application.port.in;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface SearchPlaceUseCase {

    String SearchPlace(String keyword) throws JsonProcessingException;

}
