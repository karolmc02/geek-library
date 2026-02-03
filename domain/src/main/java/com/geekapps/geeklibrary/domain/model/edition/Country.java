package com.geekapps.geeklibrary.domain.model.edition;

import com.geekapps.geeklibrary.domain.validation.DomainValidator;

public record Country(String name, String isoCode) {

  public Country {
    DomainValidator.validateNotNullOrEmpty(name, "Country name");
    DomainValidator.validateCountryIsoCode(isoCode);
  }

  @Override
  public String toString() {
    return this.name + " (" + this.isoCode + ")";
  }
}
