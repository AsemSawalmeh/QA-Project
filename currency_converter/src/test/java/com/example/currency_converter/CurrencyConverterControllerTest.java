package com.example.currency_converter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class CurrencyConverterControllerTest {
//    Correct Input Tests
    private static CurrencyConverterController controller;

    @BeforeAll
    static void setUpClass() {
        controller = new CurrencyConverterController();
    }
    @Test
    @DisplayName("Currency Empty Value Test")
    void isValid_emptyValue_returnsFalse() {
        String input = "";
        assertFalse(controller.isValidNumber(input));
    }

    @Test
    @DisplayName("Currency with invalid Characters")
    void isValid_invalidCharacters_returnsFalse() {
        String input = "#$123.I2A";
        assertFalse(controller.isValidNumber(input));
    }

    @Test
    @DisplayName("Currency with comma is treated as normal")
    void isValid_commaCurrency_returnsTrue() {
        String input = "4,1";
        assertTrue(controller.isValidNumber(input));
    }

    @Test
    @DisplayName("Currency less than 1")
    void isValid_lessThanOne_returnsTrue() {
        String input = ",1";
        assertTrue(controller.isValidNumber(input));
    }

    @Test
    @DisplayName("Currency with Integer Value")
    void isValid_integerValue_returnsTrue() {
        String input = "1";
        assertTrue(controller.isValidNumber(input));
    }

    @Test
    @DisplayName("Currency with Float Value")
    void isValid_floatValue_returnsTrue() {
        String input = "92.10";
        assertTrue(controller.isValidNumber(input));
    }
//__________________________________________________________
//    Currency List Retrieval Tests

    @Test
    @DisplayName("Get Currencies returns proper list")
    void getCurrencies_returnsProperly() {
        String[] currencies_array = {
                "USD", "EUR", "GBP", "INR", "AUD", "CAD", "SGD", "CHF", "MYR", "JPY", "CNY", "NZD", "THB", "HUF", "AED",
                "HKD", "MXN", "ZAR", "PHP", "SEK", "IDR", "BRL", "SAR", "TRY", "KES", "KRW", "EGP", "IQD", "NOK", "KWD",
                "RUB", "DKK", "PKR", "ILS", "PLN", "QAR", "XAU", "OMR", "COP", "CLP", "TWD", "ARS", "CZK", "VND", "MAD",
                "JOD", "BHD", "XOF", "LKR", "UAH", "NGN", "TND", "UGX", "RON", "BDT", "PEN", "GEL", "XAF", "FJD", "VEF",
                "VES", "BYN", "UZS", "BGN", "DZD", "IRR", "DOP", "ISK", "CRC", "XAG", "SYP", "JMD", "LYD", "GHS", "MUR",
                "AOA", "UYU", "AFN", "LBP", "XPF", "TTD", "TZS", "ALL", "XCD", "GTQ", "NPR", "BOB", "ZWD", "BBD", "CUC",
                "LAK", "BND", "BWP", "HNL", "PYG", "ETB", "NAD", "PGK", "SDG", "MOP", "BMD", "NIO", "BAM", "KZT", "PAB",
                "GYD", "YER", "MGA", "KYD", "MZN", "RSD", "SCR", "AMD", "AZN", "SBD", "SLL", "TOP", "BZD", "GMD", "MWK",
                "BIF", "HTG", "SOS", "GNF", "MNT", "MVR", "CDF", "STN", "TJS", "KPW", "KGS", "LRD", "LSL", "MMK", "GIP",
                "XPT", "MDL", "CUP", "KHR", "MKD", "VUV", "ANG", "MRU", "SZL", "CVE", "SRD", "SVC", "XPD", "BSD", "XDR",
                "RWF", "AWG", "BTN", "DJF", "KMF", "ERN", "FKP", "SHP", "SPL", "WST", "JEP", "TMT", "GGP", "IMP", "TVD",
                "ZMW", "ADA", "BCH", "BTC", "CLF", "CNH", "DOG", "DOT", "ETH", "LIN", "LTC", "LUN", "MXV", "SLE", "UNI",
                "VED", "XBT", "XLM", "XRP"
        };

        ArrayList<String> currencies_name_list = new ArrayList<>(List.of(currencies_array));
        assertEquals(currencies_name_list, controller.getCurrencies());
    }

    //__________________________________________________________
//    Valid Currency Selection Tests
    @Test
    @DisplayName("Valid Currency, same currency selected")
    void isValidChoice_sameCurrency_returnsFalse() {
        String currency_one = "ILS";
        String currency_two = "ILS";
        assertFalse(controller.isValidChoice(currency_one, currency_two));
    }

    @Test
    @DisplayName("Valid Currency, Empty Selection")
    void isValidChoice_emptySelection_returnsFalse() {
        String currency_one = null;
        String currency_two = "USD";
        assertFalse(controller.isValidChoice(currency_one, currency_two));
    }

    @Test
    @DisplayName("Valid Currency,Illegal Currency Selected")
    void isValidChoice_illegalCurrency_returnsFalse() {
        String currency_one = "AUD";
        String currency_two = "USD";
        assertFalse(controller.isValidChoice(currency_one, currency_two));
    }

    @Test
    @DisplayName("Valid Currency, Valid Choice")
    void isValidChoice_validChoice_returnsTrue() {
        String currency_one = "ILS";
        String currency_two = "USD";
        assertTrue(controller.isValidChoice(currency_one, currency_two));
    }

    //__________________________________________________________
//    Correct Conversions Tests
    @Test
    @DisplayName("Conversion to USD")
    void convertCurrency_toUSD() {
        String amount = "1";
        String currency_one = "EUR";
        String currency_two = "USD";
        assertAll(() -> assertTrue(controller.isValidNumber(amount)),
                () -> assertTrue(controller.isValidChoice(currency_one, currency_two))
        );
        String result = controller.convertCurrency(currency_one, currency_two, Double.parseDouble(amount))[0];
        String rate = controller.convertCurrency(currency_one, currency_two, Double.parseDouble(amount))[1];

        assertEquals("1.1",result);
        assertEquals("1.1",rate);
    }

    @Test
    @DisplayName("Conversion From USD")
    void convertCurrency_fromUSD() {
        String amount = "1";
        String currency_one = "USD";
        String currency_two = "EUR";
        assertAll(() -> assertTrue(controller.isValidNumber(amount)),
                () -> assertTrue(controller.isValidChoice(currency_one, currency_two))
        );
        String result = controller.convertCurrency(currency_one, currency_two, Double.parseDouble(amount))[0];
        String rate = controller.convertCurrency(currency_one, currency_two, Double.parseDouble(amount))[1];

        assertEquals(".91",result);
        assertEquals(".91",rate);
    }

    @Test
    @DisplayName("Non USD Conversion")
    void convertCurrency_nonUSD() {
        String amount = "1";
        String currency_one = "EUR";
        String currency_two = "ILS";
        assertAll(() -> assertTrue(controller.isValidNumber(amount)),
                () -> assertTrue(controller.isValidChoice(currency_one, currency_two))
        );
        String result = controller.convertCurrency(currency_one, currency_two, Double.parseDouble(amount))[0];
        String rate = controller.convertCurrency(currency_one, currency_two, Double.parseDouble(amount))[1];

        assertEquals("4.09",result);
        assertEquals("4.09",rate);
    }
}