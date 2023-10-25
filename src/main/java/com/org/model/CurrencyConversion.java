package com.org.model;

import java.sql.Timestamp;

public class CurrencyConversion {
//    private final Timestamp timestamp;
    private final String fromCurrency;
    private final String toCurrency;
    private final double amount;
    private final double convertedAmount;
    private final double exchangeRate;
    private Timestamp timestamp;

    public CurrencyConversion(String fromCurrency, String toCurrency, double amount, double convertedAmount, double exchangeRate, Timestamp timestamp) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.amount = amount;
        this.convertedAmount = convertedAmount;
        this.exchangeRate = exchangeRate;
//        this.timestamp = timestamp;
        this.timestamp = timestamp;
    }
}
