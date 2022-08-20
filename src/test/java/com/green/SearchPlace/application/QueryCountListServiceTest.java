package com.green.SearchPlace.application;

import com.green.SearchPlace.application.port.out.KeywordQueryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class QueryCountListServiceTest {

    private final KeywordQueryRepository keywordQueryRepository;

    @Autowired
    public QueryCountListServiceTest(KeywordQueryRepository keywordQueryRepository) {
        this.keywordQueryRepository = keywordQueryRepository;
    }

    @Test
    public void keywordQueryCountTop10() {
        keywordQueryRepository.save(new KeywordQuery("수박화채",3));
        keywordQueryRepository.save(new KeywordQuery("삼계탕",2));
        keywordQueryRepository.save(new KeywordQuery("곱창",2));
        keywordQueryRepository.save(new KeywordQuery("탕수육",1));
        keywordQueryRepository.save(new KeywordQuery("계곡",1));

        List<KeywordQuery> list = keywordQueryRepository.findTop10ByOrderByCountDesc();

        Assertions.assertEquals("수박화채", list.get(0).getKeyword());
        Assertions.assertEquals(3, list.get(0).getCount());
        Assertions.assertEquals(2, list.get(1).getCount());
        Assertions.assertEquals(2, list.get(2).getCount());
        Assertions.assertEquals(1, list.get(3).getCount());
        Assertions.assertEquals(1, list.get(4).getCount());
    }

    @Test
    public void keywordQueryCount_Is_Over_10() {
        keywordQueryRepository.save(new KeywordQuery("대패삼겹살",8));
        keywordQueryRepository.save(new KeywordQuery("계곡",6));
        keywordQueryRepository.save(new KeywordQuery("삼계탕",6));
        keywordQueryRepository.save(new KeywordQuery("삼겹살",5));
        keywordQueryRepository.save(new KeywordQuery("물냉",4));
        keywordQueryRepository.save(new KeywordQuery("수박화채",3));
        keywordQueryRepository.save(new KeywordQuery("라면",3));
        keywordQueryRepository.save(new KeywordQuery("식물원",3));
        keywordQueryRepository.save(new KeywordQuery("곱창",2));
        keywordQueryRepository.save(new KeywordQuery("귤",2));
        keywordQueryRepository.save(new KeywordQuery("밀면",2));
        keywordQueryRepository.save(new KeywordQuery("탕수육",1));
        keywordQueryRepository.save(new KeywordQuery("오징어회",1));
        keywordQueryRepository.save(new KeywordQuery("오렌지",1));
        keywordQueryRepository.save(new KeywordQuery("비냉",1));

        List<KeywordQuery> list = keywordQueryRepository.findTop10ByOrderByCountDesc();

        Assertions.assertEquals("대패삼겹살", list.get(0).getKeyword());
        Assertions.assertEquals(8, list.get(0).getCount());
        Assertions.assertEquals(6, list.get(1).getCount());
        Assertions.assertEquals(6, list.get(2).getCount());
        Assertions.assertEquals(5, list.get(3).getCount());
        Assertions.assertEquals(4, list.get(4).getCount());
        Assertions.assertEquals(3, list.get(5).getCount());
        Assertions.assertEquals(3, list.get(6).getCount());
        Assertions.assertEquals(3, list.get(7).getCount());
        Assertions.assertEquals(2, list.get(8).getCount());
        Assertions.assertEquals(2, list.get(9).getCount());
    }
}