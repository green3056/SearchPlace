package com.green.SearchPlace.adapter.in.web.controller.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.green.SearchPlace.adapter.in.web.controller.rank.RankRestController;
import com.green.SearchPlace.adapter.in.web.controller.place.PlaceSearchRestController;
import com.green.SearchPlace.application.search.exception.APICallException;
import com.green.SearchPlace.domain.response.error.ErrorResultFactory;
import com.green.SearchPlace.application.search.exception.PlaceSearchKeywordBlankException;
import com.green.SearchPlace.domain.response.error.ErrorResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(basePackageClasses = {PlaceSearchRestController.class, RankRestController.class})
public class CustomControllerAdvice {

    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PlaceSearchKeywordBlankException.class)
    public ErrorResult placeSearchKeywordEmptyExceptionHandle(PlaceSearchKeywordBlankException e) {
        return ErrorResultFactory.create("E001", e.getMessage());
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(APICallException.class)
    public ErrorResult apiCallExceptionHandle(APICallException e) {
        return ErrorResultFactory.create("E002", e.getMessage());
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(JsonProcessingException.class)
    public ErrorResult JsonProcessingExceptionHandle(JsonProcessingException e) {
        return ErrorResultFactory.create("E003", e.getMessage());
    }

}

