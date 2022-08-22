package com.green.SearchPlace.domain.response.error;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResult {

    private String errorType;
    private String message;

}
