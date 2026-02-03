package com.geekapps.geeklibrary.domain.validation;

import com.geekapps.geeklibrary.domain.exception.InvalidCountryCodeException;
import com.geekapps.geeklibrary.domain.exception.InvalidDimensionException;
import com.geekapps.geeklibrary.domain.exception.InvalidLanguageCodeException;
import com.geekapps.geeklibrary.domain.exception.NullOrEmptyFieldException;

public final class DomainValidator {

  private static final String LANGUAGE_ISO_CODE_PATTERN = "^[a-z]{2}(-[A-Z]{2})?$";
  private static final String COUNTRY_ISO_CODE_PATTERN = "^[A-Z]{2}$";

  private DomainValidator() {
    throw new UnsupportedOperationException("Utility class cannot be instantiated");
  }

  public static void validateNotNullOrEmpty(final String value, final String fieldName) {
    if (value == null || value.isBlank()) {
      throw new NullOrEmptyFieldException(fieldName);
    }
  }

  public static String requireNotNullOrEmpty(final String value, final String fieldName) {
    DomainValidator.validateNotNullOrEmpty(value, fieldName);
    return value;
  }

  public static void validateLanguageIsoCode(final String isoCode) {
    DomainValidator.validateNotNullOrEmpty(isoCode, "Language ISO code");
    if (!isoCode.matches(DomainValidator.LANGUAGE_ISO_CODE_PATTERN)) {
      throw new InvalidLanguageCodeException(isoCode);
    }
  }

  public static String requireValidLanguageIsoCode(final String isoCode) {
    DomainValidator.validateLanguageIsoCode(isoCode);
    return isoCode;
  }

  public static void validateCountryIsoCode(final String isoCode) {
    DomainValidator.validateNotNullOrEmpty(isoCode, "Country ISO code");
    if (!isoCode.matches(DomainValidator.COUNTRY_ISO_CODE_PATTERN)) {
      throw new InvalidCountryCodeException(isoCode);
    }
  }

  public static String requireValidCountryIsoCode(final String isoCode) {
    DomainValidator.validateCountryIsoCode(isoCode);
    return isoCode;
  }

  public static void validatePositiveDimension(final Double value, final String dimensionName) {
    if (value != null && value <= 0) {
      throw new InvalidDimensionException(dimensionName);
    }
  }

  public static Double requirePositiveDimension(final Double value, final String dimensionName) {
    DomainValidator.validatePositiveDimension(value, dimensionName);
    return value;
  }

}
