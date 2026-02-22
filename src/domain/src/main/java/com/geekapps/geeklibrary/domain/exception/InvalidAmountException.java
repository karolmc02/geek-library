package com.geekapps.geeklibrary.domain.exception;

public class InvalidAmountException extends RuntimeException {

  public InvalidAmountException(final String fieldName) {
    super(String.format("Invalid amount for field '%s'. Amount must be positive.", fieldName));
  }
}
