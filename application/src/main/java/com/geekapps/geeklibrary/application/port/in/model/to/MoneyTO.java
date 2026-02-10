package com.geekapps.geeklibrary.application.port.in.model.to;

import java.math.BigDecimal;

public record MoneyTO(String currency, BigDecimal amount) {
}
