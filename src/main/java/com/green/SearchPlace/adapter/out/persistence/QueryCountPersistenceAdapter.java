package com.green.SearchPlace.adapter.out.persistence;

import com.green.SearchPlace.application.port.out.QueryCountPort;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class QueryCountPersistenceAdapter implements QueryCountPort {
    static final Map<String, Integer> searchCountMap = new HashMap<>();

    @Override
    public synchronized int incrementSearchCount(String query) {
        if (searchCountMap.containsKey(query)) {
            Integer count = searchCountMap.get(query);
            searchCountMap.put(query, count+1);
        } else {
            searchCountMap.put(query, 1);
        }
        return searchCountMap.get(query);
    }

    @Override
    public synchronized List<KeywordQuery> keywordQueryCountTop10() {
        Set<String> queryCountKeySet = searchCountMap.keySet();
        PriorityQueue<KeywordQuery> queryPriorityQueue = new PriorityQueue<>();

        for (String keyword : queryCountKeySet) {
            int queryCount = searchCountMap.get(keyword);
            queryPriorityQueue.add(new KeywordQuery(keyword, queryCount));
        }

        List<KeywordQuery> result = new ArrayList<>();
        for (int i=0; i<10; i++) {
            if (queryPriorityQueue.size() == 0)
                break;
            result.add(queryPriorityQueue.poll());
        }

        return result;
    }

}
