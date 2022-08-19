package com.green.SearchPlace.adapter.out.persistence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class KeywordQuery implements Comparable<KeywordQuery>{

    private String keyword;
    private Integer count;

    @Override
    public int compareTo(KeywordQuery otherKeywordQuery) {
        if (otherKeywordQuery == null || getClass() != otherKeywordQuery.getClass()) {
            return -1;
        }

        if (count < otherKeywordQuery.count) {
            return 1;
        } else if (count.equals(otherKeywordQuery.count)) {
            return 0;
        } else {
            return -1;
        }
    }
}
