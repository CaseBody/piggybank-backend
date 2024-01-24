package com.testing.piggybank;

import com.testing.piggybank.helper.CurrencyConverterService;
import com.testing.piggybank.model.Currency;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CurrencyConverterTests {
    @Test
    void usdConversionTest() {
        CurrencyConverterService service = new CurrencyConverterService();
        BigDecimal euro = service.toEuro(Currency.USD, BigDecimal.valueOf(10));

        Assertions.assertEquals(BigDecimal.valueOf(9.09), euro);
    }

    @Test
    void gbpConversionTest() {
        CurrencyConverterService service = new CurrencyConverterService();
        BigDecimal euro = service.toEuro(Currency.GBP, BigDecimal.valueOf(10));

        BigDecimal expected;

        expected = new BigDecimal("12.50");

        // set scale of bg1 to 2 in bg2 using floor as rounding mode
        expected = expected.setScale(2, RoundingMode.FLOOR);


        Assertions.assertEquals(expected, euro);
    }

    @Test
    void audConversionTest() {
        CurrencyConverterService service = new CurrencyConverterService();
        BigDecimal euro = service.toEuro(Currency.AUD, BigDecimal.valueOf(10));

        Assertions.assertEquals(BigDecimal.valueOf(6.67), euro);
    }
}
