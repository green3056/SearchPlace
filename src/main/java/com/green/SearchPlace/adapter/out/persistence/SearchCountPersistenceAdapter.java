package com.green.SearchPlace.adapter.out.persistence;

import com.green.SearchPlace.application.port.out.SearchCountPort;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SearchCountPersistenceAdapter implements SearchCountPort {
    static final Map<String, Integer> searchCountMap = new HashMap<>();

    @Override
    public int incrementSearchCount(String keyword) {
        if (searchCountMap.containsKey(keyword)) {
            Integer count = searchCountMap.get(keyword);
            searchCountMap.put(keyword, count+1);
        } else {
            searchCountMap.put(keyword, 1);
        }
        return searchCountMap.get(keyword);
    }
}
