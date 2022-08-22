package com.green.SearchPlace.domain.response.error;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class ErrorResultFactory {

    public static ErrorResult create(String errorType, String message) {
        return new ErrorResult(errorType, message);
    }

    public static ErrorResult badRequestError(HttpServletRequest request, Exception e) {
        String method = request.getMethod();
        String requestURI = request.getRequestURI();
        return new ErrorResult("BadRequest", "[" + method + " " + requestURI + "] " + e.getMessage());
    }

    public static ErrorResult resourceNotFound(HttpServletRequest request, Exception e) {
        String method = request.getMethod();
        String requestURI = request.getRequestURI();
        return new ErrorResult("ResourceNotFound", "[" + method + " " + requestURI + "] is not matched.");
    }

    public static ErrorResult internalServerError(HttpServletRequest request, Exception e) {
        String method = request.getMethod();
        String requestURI = request.getRequestURI();
        return new ErrorResult("InternalServerError", "[" + method + " " + requestURI + "] " + e.getMessage());
    }

}
