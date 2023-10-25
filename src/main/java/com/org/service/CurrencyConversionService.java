package com.org.service;

public interface CurrencyConversionService {
    double getExchangeRate(String fromCurrency, String toCurrency, String date, double amount);
    double convertCurrency(double amount, double exchangeRate);
}

