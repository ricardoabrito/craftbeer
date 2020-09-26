package com.beerhouse.config;

import com.beerhouse.config.domain.ErrorResponse;
import com.beerhouse.config.domain.FieldError;
import com.beerhouse.web.rest.error.BeerHouseAPIException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getAllErrors().stream()
                .map(FieldError::new).collect(Collectors.toList());
        return new ResponseEntity<>(new ErrorResponse("Informações fornecidas não são válidas!", status, fieldErrors), status);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleBeerHouseException(BeerHouseAPIException ex) {
        return  new ResponseEntity<>(new ErrorResponse(ex.getMessage(), ex.getStatus()), ex.getStatus());
    }

}
