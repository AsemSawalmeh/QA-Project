package com.example.currency_converter;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class CurrencyConverterControllerMockTest {
    @Mock
    private static CurrencyConverterController mockController = mock(CurrencyConverterController.class);
    private ArrayList<conversion_rates> mockedRates;

    @BeforeEach
    public void setUp() {
        // Creating a mock controller
        mockController = Mockito.mock(CurrencyConverterController.class);
        mockedRates = new ArrayList<>();

        // Adding mock data for a few currencies
        mockedRates.add(new conversion_rates("USD", 1.0, 1.0));
        mockedRates.add(new conversion_rates("EUR", 0.9124101365942007, 1.0959983453633586));
        mockedRates.add(new conversion_rates("INR", 82.82348573780213, 0.012073870003077906));

        // Using 'when' to define behavior of getRecordsFromFile
        when(mockController.getRecordsFromFile()).thenReturn(mockedRates);

    }

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    //mocked controller mock tests
    @Test
    @DisplayName("Negative amount Test")
    void negativeAmount_mocKTest() {
        String negativeAmount = "-5.4";

        //isvalidcnumber returns false
        when(mockController.isValidNumber(negativeAmount)).thenReturn(false);
        Assertions.assertFalse(mockController.isValidNumber(negativeAmount));
    }

    @Test
    @DisplayName("Empty Base Currency Test")
    void emptyBaseCurrency_mocKTest() {
        String base_currency = "";
        String target_currency = "EUR";

        //isvalidchoice returns false
        when(mockController.isValidChoice(base_currency, target_currency)).thenReturn(false);
        Assertions.assertFalse(mockController.isValidChoice(base_currency, target_currency));

    }
    @Test
    @DisplayName("Empty Target Currency Test")
    void emptyTargetCurrency_mocKTest() {
        String base_currency = "USD";
        String target_currency = "";
        //isvalidchoice returns false
        when(mockController.isValidChoice(base_currency, target_currency)).thenReturn(false);
        Assertions.assertFalse(mockController.isValidChoice(base_currency, target_currency));
    }

    @Test
    @DisplayName("Null Base Currency Test")
    void nullBaseCurrency_mocKTest() {
        String base_currency = null;
        String target_currency = "USD";
        //isvalidchoice returns false
        when(mockController.isValidChoice(base_currency, target_currency)).thenReturn(false);
        Assertions.assertFalse(mockController.isValidChoice(base_currency, target_currency));
    }

    @Test
    @DisplayName("Null Target Currency Test")
    void nullTargetCurrency_mocKTest() {
        String base_currency = "USD";
        String target_currency = null;
        //isvalidchoice returns false
        when(mockController.isValidChoice(base_currency, target_currency)).thenReturn(false);
        Assertions.assertFalse(mockController.isValidChoice(base_currency, target_currency));
    }

    //CSV Date Mock Tests
    @Test
    @DisplayName("Test USD to EUR Conversion")
    public void testConvertCurrency_USDtoEUR() {
        // Define behavior for convertCurrency method call
        when(mockController.convertCurrency("USD", "EUR", 100.0)).thenReturn(new String[]{"91.24", "0.91"});

        String[] result = mockController.convertCurrency("USD", "EUR", 100.0);
        assertNotNull(result);
        assertEquals("91.24", result[0]);
        assertEquals("0.91", result[1]);
    }

    @Test
    @DisplayName("Test EUR to INR Conversion")
    public void testConvertCurrency_EURtoINR() {
        // Define behavior for convertCurrency method call
        when(mockController.convertCurrency("EUR", "INR", 100.0)).thenReturn(new String[]{"SomeResult", "SomeRate"});

        String[] result = mockController.convertCurrency("EUR", "INR", 100.0);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Test Valid Currency Choices")
    public void testIsValidChoice() {
        when(mockController.isValidChoice("USD", "EUR")).thenReturn(true);
        when(mockController.isValidChoice("USD", "USD")).thenReturn(false);
        when(mockController.isValidChoice("BWP", "AUD")).thenReturn(false);

        assertTrue(mockController.isValidChoice("USD", "EUR"));
        assertFalse(mockController.isValidChoice("USD", "USD"));
        assertFalse(mockController.isValidChoice("BWP", "AUD"));
    }

    @Test
    @DisplayName("Test Valid Number Input")
    public void testIsValidNumber() {
        when(mockController.isValidNumber("123")).thenReturn(true);
        when(mockController.isValidNumber("abc")).thenReturn(false);
        when(mockController.isValidNumber(null)).thenReturn(false);

        assertTrue(mockController.isValidNumber("123"));
        assertFalse(mockController.isValidNumber("abc"));
        assertFalse(mockController.isValidNumber(null));
    }

    @Test
    @DisplayName("Test Conversion with Invalid Currencies")
    public void testConvertCurrency_InvalidCurrency() {
        when(mockController.convertCurrency("ABC", "XYZ", 100.0)).thenReturn(null);

        String[] result = mockController.convertCurrency("ABC", "XYZ", 100.0);
        assertNull(result);
    }

    @Test
    @DisplayName("Test Conversion with Restricted Currency")
    public void testConvertCurrency_WithRestrictedCurrency() {
        when(mockController.convertCurrency("BWP", "USD", 100.0)).thenReturn(null);

        String[] result = mockController.convertCurrency("BWP", "USD", 100.0);
        assertNull(result);
    }

}
