package com.au.infosys.cashman.enumeration;

import static com.au.infosys.cashman.util.Constants.AUD;

import java.math.BigDecimal;
import java.util.Currency;

import com.au.infosys.cashman.interfaces.Money;

public enum Note implements Money {
    HUNDRED(AUD, 100),
    FIFTY(AUD, 50),
    TWENTY(AUD, 20),
    TEN(AUD, 10),
    FIVE(AUD, 5);

    private Currency currency;
    private BigDecimal value;

    Note(String currencyCode, int value) {
        this.currency = Currency.getInstance(currencyCode);
        this.value = new BigDecimal(value);
    }

    public BigDecimal getValue() {
        return value;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return String.format("%s %.2f", getCurrency().getCurrencyCode(), getValue());
    }

}
