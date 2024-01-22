package com.example.currency_converter;

public class CurrencyRecord {
    private String currency_code;
    private Double dollar_to_currency_ratio;
    private Double currency_to_dollar_ratio;

    public CurrencyRecord(String currency_code, Double dollar_to_currency_ratio, Double currency_to_dollar_ratio) {
        this.currency_code = currency_code;
        this.dollar_to_currency_ratio = dollar_to_currency_ratio;
        this.currency_to_dollar_ratio = currency_to_dollar_ratio;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

    public Double getDollar_to_currency_ratio() {
        return dollar_to_currency_ratio;
    }

    public void setDollar_to_currency_ratio(Double dollar_to_currency_ratio) {
        this.dollar_to_currency_ratio = dollar_to_currency_ratio;
    }

    public Double getCurrency_to_dollar_ratio() {
        return currency_to_dollar_ratio;
    }

    public void setCurrency_to_dollar_ratio(Double currency_to_dollar_ratio) {
        this.currency_to_dollar_ratio = currency_to_dollar_ratio;
    }

    @Override
    public String toString() {
        return "CurrencyRecord{" +
                "currency_code='" + currency_code + '\'' +
                ", dollar_to_currency_ratio=" + dollar_to_currency_ratio +
                ", currency_to_dollar_ratio=" + currency_to_dollar_ratio +
                '}';
    }
}
