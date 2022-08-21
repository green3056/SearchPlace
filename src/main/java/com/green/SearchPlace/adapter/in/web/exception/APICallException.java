package com.green.SearchPlace.adapter.in.web.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class APICallException extends RuntimeException {

    public APICallException(String message) {
        super(message);
    }

}
