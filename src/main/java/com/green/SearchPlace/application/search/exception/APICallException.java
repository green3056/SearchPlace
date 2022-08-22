package com.green.SearchPlace.application.search.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class APICallException extends RuntimeException {

    public APICallException(String message) {
        super(message);
    }

}
