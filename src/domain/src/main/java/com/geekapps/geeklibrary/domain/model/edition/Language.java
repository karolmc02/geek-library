package com.geekapps.geeklibrary.domain.model.edition;

import com.geekapps.geeklibrary.domain.validation.DomainValidator;

public record Language(String isoCode) {

  public Language {
    DomainValidator.validateLanguageIsoCode(isoCode);
  }

  @Override
  public String toString() {
    return this.isoCode;
  }
}
