package com.geekapps.geeklibrary.domain.model.common;

import java.math.BigDecimal;
import java.util.Objects;
import com.geekapps.geeklibrary.domain.validation.DomainValidator;

public record Money(String currency, BigDecimal amount) {

  public Money {
    DomainValidator.validateNotNullOrEmpty(currency, "Currency");
    DomainValidator.validatePositiveAmount(amount, "Amount");
  }

  @Override
  public String toString() {
    return this.amount + " " + this.currency;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null || this.getClass() != obj.getClass())
      return false;
    final Money money = (Money) obj;
    return Objects.equals(this.currency, money.currency)
        && Objects.equals(this.amount, money.amount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.currency, this.amount);
  }
}
