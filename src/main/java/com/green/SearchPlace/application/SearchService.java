package com.green.SearchPlace.application;

import com.green.SearchPlace.application.port.in.SearchUseCase;
import org.springframework.stereotype.Component;

@Component
public class SearchService implements SearchUseCase {

    @Override
    public String places() {
        return "Test Places";
    }
}
