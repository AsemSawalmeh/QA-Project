package com.example.currency_converter;
import jakarta.persistence.*;
@Entity
@Table
public class ConversionRates {
    @Id
    @SequenceGenerator(
            name = "currency_sequence",
            sequenceName = "currency_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "currency_sequence"
    )

    private String currency_name;
    private Double dollar_to_currency;
    private Double currency_to_dollar;

    public ConversionRates(String currency_code, Double dollar_to_currency_ratio, Double currency_to_dollar_ratio) {
        this.currency_name = currency_code;
        this.dollar_to_currency = dollar_to_currency_ratio;
        this.currency_to_dollar = currency_to_dollar_ratio;
    }

    public String getCurrency_code() {
        return currency_name;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_name = currency_code;
    }

    public Double getDollar_to_currency_ratio() {
        return dollar_to_currency;
    }

    public void setDollar_to_currency_ratio(Double dollar_to_currency_ratio) {
        this.dollar_to_currency = dollar_to_currency_ratio;
    }

    public Double getCurrency_to_dollar_ratio() {
        return currency_to_dollar;
    }

    public void setCurrency_to_dollar_ratio(Double currency_to_dollar_ratio) {
        this.currency_to_dollar = currency_to_dollar_ratio;
    }

    @Override
    public String toString() {
        return "CurrencyRecord{" +
                "currency_code='" + currency_name + '\'' +
                ", dollar_to_currency_ratio=" + dollar_to_currency +
                ", currency_to_dollar_ratio=" + currency_to_dollar +
                '}';
    }
}
