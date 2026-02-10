package com.geekapps.geeklibrary.domain.model.common;

import java.math.BigDecimal;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.geekapps.geeklibrary.domain.exception.InvalidAmountException;
import com.geekapps.geeklibrary.domain.exception.NullOrEmptyFieldException;

@DisplayName("Money Tests")
class MoneyTest {

  @Test
  @DisplayName("Should create money with valid currency and positive amount")
  void shouldCreateMoneyWithValidCurrencyAndPositiveAmount() {
    // Given
    final String currency = "USD";
    final BigDecimal amount = new BigDecimal("99.99");

    // When
    final Money money = new Money(currency, amount);

    // Then
    Assertions.assertThat(money.currency()).isEqualTo("USD");
    Assertions.assertThat(money.amount()).isEqualByComparingTo(new BigDecimal("99.99"));
  }

  @Test
  @DisplayName("Should throw exception when currency is null")
  void shouldThrowExceptionWhenCurrencyIsNull() {
    // Given
    final BigDecimal amount = new BigDecimal("100.00");

    // When & Then
    Assertions.assertThatThrownBy(() -> new Money(null, amount))
        .isInstanceOf(NullOrEmptyFieldException.class)
        .hasMessageContaining("Currency cannot be null or empty");
  }

  @Test
  @DisplayName("Should throw exception when currency is empty")
  void shouldThrowExceptionWhenCurrencyIsEmpty() {
    // Given
    final BigDecimal amount = new BigDecimal("100.00");

    // When & Then
    Assertions.assertThatThrownBy(() -> new Money("", amount))
        .isInstanceOf(NullOrEmptyFieldException.class)
        .hasMessageContaining("Currency cannot be null or empty");
  }

  @Test
  @DisplayName("Should throw exception when currency is blank")
  void shouldThrowExceptionWhenCurrencyIsBlank() {
    // Given
    final BigDecimal amount = new BigDecimal("100.00");

    // When & Then
    Assertions.assertThatThrownBy(() -> new Money("   ", amount))
        .isInstanceOf(NullOrEmptyFieldException.class)
        .hasMessageContaining("Currency cannot be null or empty");
  }

  @Test
  @DisplayName("Should throw exception when amount is null")
  void shouldThrowExceptionWhenAmountIsNull() {
    // Given
    final String currency = "EUR";

    // When & Then
    Assertions.assertThatThrownBy(() -> new Money(currency, null))
        .isInstanceOf(InvalidAmountException.class)
        .hasMessageContaining("Invalid amount for field 'Amount'")
        .hasMessageContaining("must be positive");
  }

  @Test
  @DisplayName("Should throw exception when amount is zero")
  void shouldThrowExceptionWhenAmountIsZero() {
    // Given
    final String currency = "EUR";
    final BigDecimal amount = BigDecimal.ZERO;

    // When & Then
    Assertions.assertThatThrownBy(() -> new Money(currency, amount))
        .isInstanceOf(InvalidAmountException.class)
        .hasMessageContaining("Invalid amount for field 'Amount'")
        .hasMessageContaining("must be positive");
  }

  @Test
  @DisplayName("Should throw exception when amount is negative")
  void shouldThrowExceptionWhenAmountIsNegative() {
    // Given
    final String currency = "GBP";
    final BigDecimal amount = new BigDecimal("-50.00");

    // When & Then
    Assertions.assertThatThrownBy(() -> new Money(currency, amount))
        .isInstanceOf(InvalidAmountException.class)
        .hasMessageContaining("Invalid amount for field 'Amount'")
        .hasMessageContaining("must be positive");
  }

  @Test
  @DisplayName("Should be equal when currency and amount match")
  void shouldBeEqualWhenCurrencyAndAmountMatch() {
    // Given
    final Money money1 = new Money("JPY", new BigDecimal("1500.00"));
    final Money money2 = new Money("JPY", new BigDecimal("1500.00"));

    // Then
    Assertions.assertThat(money1).isEqualTo(money2);
    Assertions.assertThat(money1.hashCode()).isEqualTo(money2.hashCode());
  }

  @Test
  @DisplayName("Should not be equal when currency differs")
  void shouldNotBeEqualWhenCurrencyDiffers() {
    // Given
    final Money money1 = new Money("USD", new BigDecimal("100.00"));
    final Money money2 = new Money("EUR", new BigDecimal("100.00"));

    // Then
    Assertions.assertThat(money1).isNotEqualTo(money2);
  }

  @Test
  @DisplayName("Should not be equal when amount differs")
  void shouldNotBeEqualWhenAmountDiffers() {
    // Given
    final Money money1 = new Money("USD", new BigDecimal("100.00"));
    final Money money2 = new Money("USD", new BigDecimal("200.00"));

    // Then
    Assertions.assertThat(money1).isNotEqualTo(money2);
  }

  @Test
  @DisplayName("Should not be equal to null")
  void shouldNotBeEqualToNull() {
    // Given
    final Money money = new Money("USD", new BigDecimal("100.00"));

    // Then
    Assertions.assertThat(money).isNotEqualTo(null);
  }

  @Test
  @DisplayName("Should be equal to itself")
  void shouldBeEqualToItself() {
    // Given
    final Money money = new Money("CAD", new BigDecimal("75.50"));

    // Then
    Assertions.assertThat(money).isEqualTo(money);
  }

  @Test
  @DisplayName("Should not be equal to different class")
  void shouldNotBeEqualToDifferentClass() {
    // Given
    final Money money = new Money("USD", new BigDecimal("100.00"));
    final String notMoney = "100.00 USD";

    // Then
    Assertions.assertThat(money).isNotEqualTo(notMoney);
  }

  @Test
  @DisplayName("Should generate same hashCode for equal objects")
  void shouldGenerateSameHashCodeForEqualObjects() {
    // Given
    final Money money1 = new Money("CHF", new BigDecimal("500.00"));
    final Money money2 = new Money("CHF", new BigDecimal("500.00"));

    // Then
    Assertions.assertThat(money1.hashCode()).isEqualTo(money2.hashCode());
  }

  @Test
  @DisplayName("Should include currency and amount in toString")
  void shouldIncludeCurrencyAndAmountInToString() {
    // Given
    final Money money = new Money("USD", new BigDecimal("123.45"));

    // When
    final String result = money.toString();

    // Then
    Assertions.assertThat(result).contains("123.45");
    Assertions.assertThat(result).contains("USD");
  }

  @Test
  @DisplayName("Should format toString as 'amount currency'")
  void shouldFormatToStringAsAmountCurrency() {
    // Given
    final Money money = new Money("EUR", new BigDecimal("99.99"));

    // When
    final String result = money.toString();

    // Then
    Assertions.assertThat(result).isEqualTo("99.99 EUR");
  }
}
