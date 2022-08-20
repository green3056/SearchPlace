package com.green.SearchPlace.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.SearchPlace.application.port.in.KeywordQueryUseCase;
import com.green.SearchPlace.application.port.out.KeywordQueryRepository;
import com.green.SearchPlace.domain.KeywordQuery;
import org.springframework.stereotype.Service;

@Service
public class KeywordQueryService implements KeywordQueryUseCase {

    private final KeywordQueryRepository keywordQueryRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public KeywordQueryService(KeywordQueryRepository keywordQueryRepository) {
        this.keywordQueryRepository = keywordQueryRepository;
    }

    @Override
    public String keywordQueryCountTop10() throws JsonProcessingException {
        return objectMapper
                .writer()
                .withRootName("keywordQueryCountList")
                .writeValueAsString(keywordQueryRepository.findTop10ByOrderByCountDesc());
    }

    @Override
    public synchronized void implementQueryCount(String keyword) {
        KeywordQuery keywordQuery = keywordQueryRepository.findByKeyword(keyword).orElse(null);
        if (keywordQuery == null) {
            keywordQueryRepository.save(new KeywordQuery(keyword, 1L));
        } else {
            keywordQuery.implementCount();
            keywordQueryRepository.save(keywordQuery);
        }
    }

}