package com.geekapps.geeklibrary.domain.model.edition;

import com.geekapps.geeklibrary.domain.validation.DomainValidator;

public record Country(String isoCode) {

  public Country {
    DomainValidator.validateCountryIsoCode(isoCode);
  }

  @Override
  public String toString() {
    return this.isoCode;
  }
}
