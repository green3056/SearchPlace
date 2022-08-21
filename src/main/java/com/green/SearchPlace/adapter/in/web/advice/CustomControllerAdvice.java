package com.green.SearchPlace.adapter.in.web.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.green.SearchPlace.adapter.in.web.controller.KeywordQueryCountRestController;
import com.green.SearchPlace.adapter.in.web.controller.SearchPlaceRestController;
import com.green.SearchPlace.adapter.in.web.exception.APICallException;
import com.green.SearchPlace.adapter.in.web.exception.PlaceSearchKeywordEmptyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(basePackageClasses = {SearchPlaceRestController.class, KeywordQueryCountRestController.class})
public class CustomControllerAdvice {

    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PlaceSearchKeywordEmptyException.class)
    public ErrorResult placeSearchKeywordEmptyExceptionHandle(PlaceSearchKeywordEmptyException e) {
        return new ErrorResult("E001", e.getMessage());
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(APICallException.class)
    public ErrorResult apiCallExceptionHandle(APICallException e) {
        return new ErrorResult("E002", e.getMessage());
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(JsonProcessingException.class)
    public ErrorResult JsonProcessingExceptionHandle(JsonProcessingException e) {
        return new ErrorResult("E003", e.getMessage());
    }

}

