package com.green.SearchPlace.adapter.in.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.green.SearchPlace.application.port.in.KeywordQueryUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KeywordQueryCountRestController {

    private final KeywordQueryUseCase keywordQueryService;

    @Autowired
    public KeywordQueryCountRestController(KeywordQueryUseCase keywordQueryService) {
        this.keywordQueryService = keywordQueryService;
    }

    @GetMapping("/v1/place/rank")
    public ResponseEntity<String> placeKeywordQueryCountTop10() throws JsonProcessingException {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(keywordQueryService.keywordQueryCountTop10());
    }

}
