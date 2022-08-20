package com.green.SearchPlace.adapter.in.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.green.SearchPlace.application.port.in.KeywordQueryUseCase;
import com.green.SearchPlace.application.port.in.SearchPlaceUseCase;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchPlaceRestController {
    private final SearchPlaceUseCase searchPlaceService;
    private final KeywordQueryUseCase keywordQueryUseCase;

    public SearchPlaceRestController(SearchPlaceUseCase listPlaceService, KeywordQueryUseCase keywordQueryUseCase) {
        this.searchPlaceService = listPlaceService;
        this.keywordQueryUseCase = keywordQueryUseCase;
    }

    @GetMapping("/v1/place")
    public ResponseEntity<String> search(@RequestParam("keyword") String keyword) {
        try {
            keywordQueryUseCase.implementQueryCount(keyword);
            return ResponseEntity.
                    ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(searchPlaceService.SearchPlace(keyword));
        }
        catch (JsonProcessingException jpe) {
            jpe.printStackTrace();
            return ResponseEntity
                    .internalServerError()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("JsonProcessingException");
        }
    }

}
