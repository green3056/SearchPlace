package com.green.SearchPlace.application.search.rank;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.SearchPlace.adapter.in.web.controller.place.PlaceSearchCommand;
import com.green.SearchPlace.application.port.in.PlaceKeywordQueryRankUseCase;
import com.green.SearchPlace.application.port.out.persistence.KeywordQueryRepository;
import com.green.SearchPlace.domain.keyword.Keyword;
import com.green.SearchPlace.domain.rank.KeywordQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaceKeywordQueryRankService implements PlaceKeywordQueryRankUseCase {

    private final KeywordQueryRepository keywordQueryRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public PlaceKeywordQueryRankService(KeywordQueryRepository keywordQueryRepository, ObjectMapper objectMapper) {
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
    public synchronized void implementKeywordQueryCount(PlaceSearchCommand command) {
        Keyword keyword = command.getKeyword();
        KeywordQuery keywordQuery = keywordQueryRepository.findByKeyword(keyword.toString()).orElse(null);
        if (keywordQuery == null) {
            keywordQueryRepository.save(new KeywordQuery(keyword.toString(), 1L));
        }
        else {
            keywordQuery.implementCount();
            keywordQueryRepository.save(keywordQuery);
        }
    }

}
