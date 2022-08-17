package com.green.SearchPlace.adapter.out.persistence;

import com.green.SearchPlace.application.port.out.QueryCountPort;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class QueryCountPersistenceAdapter implements QueryCountPort {
    static final Map<String, Integer> searchCountMap = new HashMap<>();

    @Override
    public int incrementSearchCount(String query) {
        if (searchCountMap.containsKey(query)) {
            Integer count = searchCountMap.get(query);
            searchCountMap.put(query, count+1);
        } else {
            searchCountMap.put(query, 1);
        }
        return searchCountMap.get(query);
    }
}
