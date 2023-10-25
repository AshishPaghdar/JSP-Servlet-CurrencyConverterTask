package com.org.model;

import java.sql.Timestamp;

public class ConversionHistory {
    private long userId;
    private String originalCurrency;
    private String targetCurrency;
    private double amount;
    private String conversionDate;
    private double exchangeRate;
    private double convertedAmount;


    public ConversionHistory(String originalCurrency, String targetCurrency, double amount, String conversionDate, double exchangeRate, double convertedAmount, Timestamp timestamp) {
//        this.userId = userId;
        this.originalCurrency = originalCurrency;
        this.targetCurrency = targetCurrency;
        this.amount = amount;
        this.conversionDate = conversionDate;
        this.exchangeRate = exchangeRate;
        this.convertedAmount = convertedAmount;
        this.timestamp = timestamp;
    }

    public long getUserId() {
        return userId;
    }

//    public void setUserId(long userId) {
//        this.userId = userId;
//    }

    public String getOriginalCurrency() {
        return originalCurrency;
    }

    public void setOriginalCurrency(String originalCurrency) {
        this.originalCurrency = originalCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getConversionDate() {
        return conversionDate;
    }

    public void setConversionDate(String conversionDate) {
        this.conversionDate = conversionDate;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public double getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(double convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    private Timestamp timestamp;

}
