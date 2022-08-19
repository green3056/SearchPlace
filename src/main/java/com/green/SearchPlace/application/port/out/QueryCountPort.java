package com.green.SearchPlace.application.port.out;

import com.green.SearchPlace.adapter.out.persistence.KeywordQuery;

import java.util.List;

public interface QueryCountPort {
    int incrementSearchCount(String query);
    List<KeywordQuery> keywordQueryCountTop10();
}
