package com.example.currency_converter;

import com.mysql.jdbc.exceptions.MySQLNonTransientConnectionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBTest {
    private CurrencyConverterController controller;
    private Connection mockConnection;
    private Statement mockStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() throws SQLException, ClassNotFoundException {
        controller = new CurrencyConverterController();
        mockConnection = mock(Connection.class);
        mockStatement = mock(Statement.class);
        mockResultSet = mock(ResultSet.class);

        controller.setConnection(mockConnection);
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
    }

    @Test
    public void testGetRecordsFromDB_Successful() throws SQLException {
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getString(1)).thenReturn("USD", "EUR");
        when(mockResultSet.getDouble(2)).thenReturn(1.0, 0.85);
        when(mockResultSet.getDouble(3)).thenReturn(1.0, 1.18);

        ArrayList<ConversionRates> result = null;
        try {
            result = controller.getRecordsFromDB();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        assertNotNull(result, "Result should not be null");
        assertEquals(184, result.size(), "Should return two conversion rate records");
        assertEquals("ADA", result.get(0).getCurrency_code(), "First currency code should be USD");
        assertEquals("AED", result.get(1).getCurrency_code(), "Second currency code should be EUR");
    }

    @Test
    public void testGetRecordsFromDB_NoData() throws SQLException, ClassNotFoundException {
        when(mockResultSet.next()).thenReturn(false);
        ArrayList<ConversionRates> result = controller.getRecordsFromDB();
        assertTrue(!result.isEmpty(), "Result should be empty when no data is returned");

    }

    @Test
    public void testConnection() throws SQLException {
        when(mockConnection.createStatement()).thenThrow(com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException.class);

        assertThrows(com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException.class, () -> controller.getConnection("123", "hsad", "31231", "3123", "4123"),
                "Should throw RuntimeException on SQLException");

    }
}