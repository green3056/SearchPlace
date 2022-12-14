package com.green.SearchPlace.application.port.in;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.green.SearchPlace.adapter.in.web.controller.place.PlaceSearchCommand;

public interface PlaceSearchUseCase {

    String responsePlaces(PlaceSearchCommand command) throws JsonProcessingException;

}
