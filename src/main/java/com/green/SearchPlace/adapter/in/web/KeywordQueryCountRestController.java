package com.green.SearchPlace.adapter.in.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.green.SearchPlace.application.port.in.QueryCountListUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KeywordQueryCountRestController {

    private final QueryCountListUseCase queryCountListService;

    @Autowired
    public KeywordQueryCountRestController(QueryCountListUseCase queryCountListService) {
        this.queryCountListService = queryCountListService;
    }

    @GetMapping("/v1/place/rank")
    public ResponseEntity<String> keywordQueryCountTop10() {
        try {
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(queryCountListService.keywordQueryCountTop10());
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity
                    .internalServerError()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("JsonProcessingException");
        }
    }
}
