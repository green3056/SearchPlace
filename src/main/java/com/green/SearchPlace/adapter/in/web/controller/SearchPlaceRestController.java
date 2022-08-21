package com.green.SearchPlace.adapter.in.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.green.SearchPlace.application.port.in.KeywordQueryUseCase;
import com.green.SearchPlace.application.port.in.SearchPlaceUseCase;
import com.green.SearchPlace.adapter.in.web.exception.PlaceSearchKeywordEmptyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SearchPlaceRestController {
    private final SearchPlaceUseCase searchPlaceService;
    private final KeywordQueryUseCase keywordQueryUseCase;

    public SearchPlaceRestController(SearchPlaceUseCase listPlaceService, KeywordQueryUseCase keywordQueryUseCase) {
        this.searchPlaceService = listPlaceService;
        this.keywordQueryUseCase = keywordQueryUseCase;
    }

    @GetMapping("/v1/place")
    public ResponseEntity<String> search(@RequestParam("keyword") String keyword) throws JsonProcessingException {
        if (keyword.isEmpty()) {
            throw new PlaceSearchKeywordEmptyException("keyword can not be empty string.");
        }

        keywordQueryUseCase.implementQueryCount(keyword);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(searchPlaceService.SearchPlace(keyword));
    }

}
