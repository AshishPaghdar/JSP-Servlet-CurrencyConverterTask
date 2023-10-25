package com.org.service;

import com.org.model.ConversionHistory;

import java.util.List;

public interface ConversionHistoryService {
    void saveConversion(ConversionHistory conversionHistory);
    List<ConversionHistory> getConversionHistory() throws ClassNotFoundException;
    List<ConversionHistory> filterConversionHistory(String fromCurrency, String toCurrency) throws ClassNotFoundException;

}
