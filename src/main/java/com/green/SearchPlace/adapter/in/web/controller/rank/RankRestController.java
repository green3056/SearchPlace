package com.green.SearchPlace.adapter.in.web.controller.rank;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.green.SearchPlace.application.port.in.PlaceKeywordQueryRankUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RankRestController {

    private final PlaceKeywordQueryRankUseCase placeKeywordQueryRankService;

    @Autowired
    public RankRestController(PlaceKeywordQueryRankUseCase placeKeywordQueryRankService) {
        this.placeKeywordQueryRankService = placeKeywordQueryRankService;
    }

    @GetMapping("/v1/place/rank")
    public ResponseEntity<String> placeKeywordQueryCountTop10() throws JsonProcessingException {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(placeKeywordQueryRankService.keywordQueryCountTop10());
    }

}
