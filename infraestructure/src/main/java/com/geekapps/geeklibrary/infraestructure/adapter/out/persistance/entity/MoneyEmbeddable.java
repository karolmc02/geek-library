package com.geekapps.geeklibrary.infraestructure.adapter.out.persistance.entity;

import java.math.BigDecimal;
import jakarta.persistence.Embeddable;

@Embeddable
public class MoneyEmbeddable {
  private String currency;
  private BigDecimal amount;

  public MoneyEmbeddable() {}

  public MoneyEmbeddable(final String currency, final BigDecimal amount) {
    this.currency = currency;
    this.amount = amount;
  }

  public String getCurrency() {
    return this.currency;
  }

  public void setCurrency(final String currency) {
    this.currency = currency;
  }

  public BigDecimal getAmount() {
    return this.amount;
  }

  public void setAmount(final BigDecimal amount) {
    this.amount = amount;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.currency == null) ? 0 : this.currency.hashCode());
    result = prime * result + ((this.amount == null) ? 0 : this.amount.hashCode());
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (this.getClass() != obj.getClass())
      return false;
    final MoneyEmbeddable other = (MoneyEmbeddable) obj;
    if (this.currency == null) {
      if (other.currency != null)
        return false;
    } else if (!this.currency.equals(other.currency))
      return false;
    if (this.amount == null) {
      if (other.amount != null)
        return false;
    } else if (!this.amount.equals(other.amount))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "MoneyEmbeddable [currency=" + this.currency + ", amount=" + this.amount + "]";
  }
}
