package com.testing.piggybank.helper;

import com.testing.piggybank.model.Currency;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CurrencyConverterService {
    // One EUR is worth:
    BigDecimal USD_AMOUNT = new BigDecimal("1.10");
    BigDecimal GBP_AMOUNT = new BigDecimal("0.80");
    BigDecimal EUR_AMOUNT = new BigDecimal("1.00");
    BigDecimal AUD_AMOUNT = new BigDecimal("1.50");

    /**
     * Convert foreign currency to EURO.
     *
     * @param chosenCurrency chosen currency
     * @param amount         The given amount
     * @return {@link BigDecimal} the foreign currency converted to EURO.
     */
    public BigDecimal toEuro(final Currency chosenCurrency, final BigDecimal amount) {
        int scale = 2; // Define the scale as needed
        RoundingMode roundingMode = RoundingMode.HALF_UP;

        switch (chosenCurrency) {
            case USD -> {
                return amount.divide(USD_AMOUNT, scale, roundingMode);
            }
            case GBP -> {
                return amount.divide(GBP_AMOUNT, scale, roundingMode);
            }
            case AUD -> {
                return amount.divide(AUD_AMOUNT, scale, roundingMode);
            }
            default -> {
                return amount;
            }
        }
    }
}
