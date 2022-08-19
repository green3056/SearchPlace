package com.green.SearchPlace.adapter.in.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.SearchPlace.adapter.out.persistence.KeywordQuery;
import com.green.SearchPlace.adapter.out.persistence.QueryCountPersistenceAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class KeywordQueryCountRestController {

    private final QueryCountPersistenceAdapter queryCounter;
    private final ObjectMapper objectMapper;

    @Autowired
    public KeywordQueryCountRestController(QueryCountPersistenceAdapter queryCounter) {
        this.queryCounter = queryCounter;
        this.objectMapper = new ObjectMapper();
    }

    @GetMapping("/v1/place/rank")
    public String keywordQueryCountTop10() throws JsonProcessingException {
        List<KeywordQuery> keywordQueryList = queryCounter.keywordQueryCountTop10();
        return objectMapper
                .writer()
                .withRootName("keywordQueryList")
                .writeValueAsString(keywordQueryList);
    }
}
