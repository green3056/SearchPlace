package com.green.SearchPlace.adapter.in.web.advice;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResult {

    private String errorType;
    private String message;

}
