package com.green.SearchPlace.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.SearchPlace.application.port.in.QueryCountListUseCase;
import com.green.SearchPlace.application.port.out.KeywordQueryRepository;
import org.springframework.stereotype.Service;

@Service
public class QueryCountListService implements QueryCountListUseCase {

    private final KeywordQueryRepository keywordQueryRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public QueryCountListService(KeywordQueryRepository keywordQueryRepository) {
        this.keywordQueryRepository = keywordQueryRepository;
    }

    @Override
    public String keywordQueryCountTop10() throws JsonProcessingException {
        return objectMapper
                .writer()
                .withRootName("keywordQueryCountList")
                .writeValueAsString(keywordQueryRepository.findTop10ByOrderByCountDesc());
    }
}
