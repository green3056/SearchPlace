package com.green.SearchPlace.adapter.in.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.green.SearchPlace.application.port.in.PlaceListUseCase;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlaceListRestController {
    private final PlaceListUseCase listPlaceService;

    public PlaceListRestController(PlaceListUseCase listPlaceService) {
        this.listPlaceService = listPlaceService;
    }

    @GetMapping("/v1/place")
    public ResponseEntity<String> search(@RequestParam("keyword") String keyword) {
        try {
            return ResponseEntity.
                    ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(listPlaceService.places(keyword));
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
