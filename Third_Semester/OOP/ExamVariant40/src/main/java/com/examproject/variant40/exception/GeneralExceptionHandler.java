package com.examproject.variant40.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ResponseBody
@ControllerAdvice(basePackages = "com.examproject.variant40.controller")
public class GeneralExceptionHandler {

    private String UNKNOWN_ERROR_RESPONSE_MESSAGE = "Something went wrong...";

    @Order
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AlreadyExistsException.class)
    public String handleAlreadyExistsException(AlreadyExistsException exception, HttpServletRequest request) {
        return exception.getMessage();
    }

    @Order
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException(NotFoundException exception, HttpServletRequest request) {
        return exception.getMessage();
    }

    @Order
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CompetitionHasFinishedException.class)
    public String handleCompetitionHasFinishedException(CompetitionHasFinishedException exception, HttpServletRequest request) {
        return exception.getMessage();
    }

    @Order
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WrongDataException.class)
    public String handleCompetitionHasFinishedException(WrongDataException exception, HttpServletRequest request) {
        return exception.getMessage();
    }
}
