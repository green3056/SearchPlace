package com.green.SearchPlace.adapter.in.web.controller.place;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.green.SearchPlace.application.port.in.PlaceKeywordQueryRankUseCase;
import com.green.SearchPlace.application.port.in.PlaceSearchUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class PlaceSearchRestController {
    private final PlaceSearchUseCase searchPlaceService;
    private final PlaceKeywordQueryRankUseCase keywordQueryService;

    public PlaceSearchRestController(PlaceSearchUseCase listPlaceService, PlaceKeywordQueryRankUseCase placeKeywordQueryRankUseCase) {
        this.searchPlaceService = listPlaceService;
        this.keywordQueryService = placeKeywordQueryRankUseCase;
    }

    @GetMapping("/v1/place")
    public ResponseEntity<String> search(@RequestParam("keyword") String keyword) throws JsonProcessingException {
        PlaceSearchCommand command = new PlaceSearchCommand(keyword);
        String response = searchPlaceService.SearchPlace(command);
        keywordQueryService.implementKeywordQueryCount(command);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

}
