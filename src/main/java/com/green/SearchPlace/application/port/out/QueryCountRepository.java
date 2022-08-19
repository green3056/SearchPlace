package com.green.SearchPlace.application.port.out;

import com.green.SearchPlace.application.KeywordQuery;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QueryCountRepository extends CrudRepository<KeywordQuery, String> {
    Optional<KeywordQuery> findByKeyword(String keyword);
}
