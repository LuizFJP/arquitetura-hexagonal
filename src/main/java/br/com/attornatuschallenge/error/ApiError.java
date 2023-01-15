package br.com.attornatuschallenge.error;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ApiError extends Exception {
  private HttpStatus status;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDateTime timestamp;
  private String message;

  public ApiError(String message) {
    super(message);
    timestamp = LocalDateTime.now();
  }

  public ApiError( String message, HttpStatus status) {
    this(message);
    this.status = status;
  }
}
