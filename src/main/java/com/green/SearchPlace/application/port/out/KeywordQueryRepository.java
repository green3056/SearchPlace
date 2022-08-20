package com.green.SearchPlace.application.port.out;

import com.green.SearchPlace.domain.KeywordQuery;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KeywordQueryRepository extends CrudRepository<KeywordQuery, String> {

    List<KeywordQuery> findTop10ByOrderByCountDesc();
    Optional<KeywordQuery> findByKeyword(String keyword);

}
