package com.geekapps.geeklibrary.infraestructure.adapter.in.rest.advice;

import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import com.geekapps.geeklibrary.client.api.model.ErrorDTO;
import com.geekapps.geeklibrary.domain.exception.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErrorDTO> handleEntityNotFoundException(final EntityNotFoundException ex,
      final WebRequest request) {
    final var errorResponse = new ErrorDTO().type(URI.create("about:blank")).title("Not Found")
        .status(HttpStatus.NOT_FOUND.value()).detail(ex.getMessage())
        .instance(URI.create(request.getDescription(false).replace("uri=", "")));
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

}
