package com.green.SearchPlace.application.search.rank;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.SearchPlace.application.port.in.KeywordQueryUseCase;
import com.green.SearchPlace.application.port.out.persistence.KeywordQueryRepository;
import com.green.SearchPlace.domain.KeywordQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KeywordQueryService implements KeywordQueryUseCase {

    private final KeywordQueryRepository keywordQueryRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public KeywordQueryService(KeywordQueryRepository keywordQueryRepository, ObjectMapper objectMapper) {
        this.keywordQueryRepository = keywordQueryRepository;
        this.objectMapper = objectMapper;
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
