package com.example.currency_converter;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CurrencyConverterController {

    private static String dbUsername = "root"; // database username
    private static String dbPassword = "123123"; // database password
    private static String URL = "127.0.0.1"; // server location
    private static String port = "3306"; // port that mysql uses
    private static String dbName = "currency_converter"; //database on mysql to connect to
    private static Connection connection;

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
    String[] restricted_currencies = {"BWP", "AUD"};
    ArrayList<String> restricted_currencies_list = new ArrayList<>(List.of(restricted_currencies));
    ArrayList<conversion_rates> conversion_rates;

    {
        try {
            conversion_rates = getRecordsFromDB();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<conversion_rates> getRecordsFromJson() throws JsonProcessingException {
        ArrayList<conversion_rates> temp_currency_rates = new ArrayList<>();
        File currency_rates_file = new File("src/main/resources/data/conversion_rates.json");
        String currency_rates_string = "";

        try {
            Scanner scanner = new Scanner(currency_rates_file);
            while (scanner.hasNextLine()) {
                currency_rates_string += scanner.nextLine();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Gson gson = new Gson();
        temp_currency_rates = gson.fromJson(currency_rates_string, new TypeToken<ArrayList<conversion_rates>>() {
        }.getType());

        return temp_currency_rates;
    }

    public ArrayList<conversion_rates> getRecordsFromFile() {
        ArrayList<conversion_rates> temp_currency_rates = new ArrayList<>();
        File currency_rates_file = new File("src/main/resources/data/conversion_rates");

        try {
            Scanner scanner = new Scanner(currency_rates_file);
            while (scanner.hasNextLine()) {
                String currency_rates_string = scanner.nextLine();
                String[] current_record = currency_rates_string.split("\\s+");
                conversion_rates record = new conversion_rates(current_record[0], Double.parseDouble(current_record[1]), Double.parseDouble(current_record[2]));
                temp_currency_rates.add(record);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return temp_currency_rates;
    }

    public ArrayList<conversion_rates> getRecordsFromDB() throws SQLException, ClassNotFoundException {
        DBConn conn_string = new DBConn(URL, port, dbName, dbUsername, dbPassword);
        connection = conn_string.connectDB();

        ArrayList<conversion_rates> temp_currency_rates = new ArrayList<>();
        String selectVisitTXT = "select * from currency_records";
        Statement sql_stmt = connection.createStatement();
        ResultSet results = sql_stmt.executeQuery(selectVisitTXT);


        while (results.next()) {
            temp_currency_rates.add(new conversion_rates(results.getString(1), results.getDouble(2), results.getDouble(3)));
        }


        connection.close();
        return temp_currency_rates;
    }

    public ArrayList<String> getCurrencies() {
        return this.currencies_name_list;
    }

    public boolean isValidChoice(String currency_one, String currency_two) {
        if (currency_one == null || currency_two == null || currency_one.equals("") || currency_two.equals("")) {
            return false;
        } else if (currency_one == currency_two) {
            return false;
        } else if (restricted_currencies_list.contains(currency_one) || restricted_currencies_list.contains(currency_two)) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isValidNumber(String input) {
        if (input.contains("-")) {
            return false;
        } else if (input == null || input.trim().isEmpty()) {
            return false;
        }

        String sanitizedInput = input.replace(",", ".");
        try {

            Double.parseDouble(sanitizedInput);
            return true;
        } catch (NumberFormatException e1) {
            try {
                Integer.parseInt(sanitizedInput);
                return true;
            } catch (NumberFormatException e2) {
                return false;
            }
        }
    }

    public String[] convertCurrency(String base_currency, String target_currency, Double amount) {
        Double result;
        Double rate;
        DecimalFormat format = new DecimalFormat(".##");


        if (base_currency == "USD") {
            for (int i = 0; i < conversion_rates.size(); i++) {
                if (conversion_rates.get(i).getCurrency_code().equals(target_currency)) {
                    rate = conversion_rates.get(i).getDollar_to_currency_ratio();
                    result = amount * rate;
                    String[] result_array = {format.format(result).toString(), format.format(rate).toString()};
                    return result_array;
                }
            }
        } else if (target_currency == "USD") {
            for (int i = 0; i < conversion_rates.size(); i++) {
                if (conversion_rates.get(i).getCurrency_code().equals(base_currency)) {
                    rate = conversion_rates.get(i).getCurrency_to_dollar_ratio();
                    result = amount * rate;
                    String[] result_array = {format.format(result).toString(), format.format(rate).toString()};
                    return result_array;
                }
            }
        } else {
            Double temp_rate = null;

            for (int i = 0; i < conversion_rates.size(); i++) {
                if (conversion_rates.get(i).getCurrency_code().equals(base_currency)) {
                    rate = conversion_rates.get(i).getCurrency_to_dollar_ratio();
                    temp_rate = rate;
                    amount = amount * rate;
                }
            }
            for (int i = 0; i < conversion_rates.size(); i++) {
                if (conversion_rates.get(i).getCurrency_code().equals(target_currency)) {
                    rate = conversion_rates.get(i).getDollar_to_currency_ratio();
                    result = amount * rate;
                    Double final_rate = rate * temp_rate;
                    String[] result_array = {format.format(result).toString(), format.format(final_rate).toString()};
                    return result_array;
                }
            }
        }

        return null;
    }

}
