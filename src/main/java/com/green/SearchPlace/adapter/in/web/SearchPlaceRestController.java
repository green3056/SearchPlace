package com.green.SearchPlace.adapter.in.web;

import com.green.SearchPlace.application.port.in.SearchUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchPlaceRestController {
    private final SearchUseCase searchService;

    public SearchPlaceRestController(SearchUseCase searchUseCase) {
        this.searchService = searchUseCase;
    }

    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword) {
        return searchService.places(keyword);
    }
}
