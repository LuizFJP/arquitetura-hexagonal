package br.com.attornatuschallenge.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.attornatuschallenge.error.ResourceNotFoundException;
import br.com.attornatuschallenge.error.ResourcesNotFoundDetails;

@ControllerAdvice
public class RestExceptionHandler {
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException exception) {
    ResourcesNotFoundDetails details = ResourcesNotFoundDetails.Builder.newBuilder()
    .timestamp(new Date().getTime())
    .status(HttpStatus.NOT_FOUND.value())
    .title("Resource not found")
    .detail(exception.getMessage())
    .developerMessage(exception.getClass().getName())
    .build();
  return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
  }
}
