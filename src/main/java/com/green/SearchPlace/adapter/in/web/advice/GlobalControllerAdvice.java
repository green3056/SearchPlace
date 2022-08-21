package com.green.SearchPlace.adapter.in.web.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@EnableWebMvc
@ControllerAdvice
public class GlobalControllerAdvice {

    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ErrorResult badRequestHandle(HttpServletRequest request, Exception e) {
        String method = request.getMethod();
        String requestURI = request.getRequestURI();
        log.error("Bad request, message=" + e.getMessage());
        return new ErrorResult("BadRequest", "[" + method + " " + requestURI + "] " + e.getMessage());
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ErrorResult resourceNotFoundHandle(HttpServletRequest request, Exception e) {
        String method = request.getMethod();
        String requestURI = request.getRequestURI();
        log.error("Resource not found, message=" + e.getMessage());
        return new ErrorResult("ResourceNotFound", "[" + method + " " + requestURI + "] is not matched.");
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResult internalServerErrorHandle(HttpServletRequest request, Exception e) {
        String method = request.getMethod();
        String requestURI = request.getRequestURI();
        log.error("Internal server error, message=" + e.getMessage());
        return new ErrorResult("InternalServerError", "[" + method + " " + requestURI + "] " + e.getMessage());
    }

}
