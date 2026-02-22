package com.geekapps.geeklibrary.infraestructure.adapter.in.rest.advice;

import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import com.geekapps.geeklibrary.client.api.model.ErrorDTO;
import com.geekapps.geeklibrary.domain.exception.EntityNotFoundException;
import com.geekapps.geeklibrary.domain.exception.InvalidCountryCodeException;
import com.geekapps.geeklibrary.domain.exception.InvalidDimensionException;
import com.geekapps.geeklibrary.domain.exception.InvalidLanguageCodeException;
import com.geekapps.geeklibrary.domain.exception.NullOrEmptyFieldException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErrorDTO> handleEntityNotFoundException(final EntityNotFoundException ex,
      final WebRequest request) {
    return this.buildErrorResponse(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage(), request);
  }

  @ExceptionHandler({NullOrEmptyFieldException.class, InvalidLanguageCodeException.class,
      InvalidCountryCodeException.class, InvalidDimensionException.class})
  public ResponseEntity<ErrorDTO> handleValidationExceptions(final RuntimeException ex,
      final WebRequest request) {
    return this.buildErrorResponse(HttpStatus.BAD_REQUEST, "Bad Request", ex.getMessage(), request);
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ErrorDTO> handleRuntimeException(final RuntimeException ex,
      final WebRequest request) {
    return this.buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error",
        ex.getMessage(), request);
  }

  private ResponseEntity<ErrorDTO> buildErrorResponse(final HttpStatus status, final String title,
      final String detail, final WebRequest request) {
    final var errorResponse =
        new ErrorDTO().type(URI.create("about:blank")).title(title).status(status.value())
            .detail(detail).instance(URI.create(request.getDescription(false).replace("uri=", "")));
    return ResponseEntity.status(status).body(errorResponse);
  }

}
