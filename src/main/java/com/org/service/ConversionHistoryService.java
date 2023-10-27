package com.org.service;

import com.org.model.ConversionHistory;

import java.sql.SQLException;
import java.util.List;

public interface ConversionHistoryService {
    void saveConversion(ConversionHistory conversionHistory);
    List<ConversionHistory> getConversionHistory(int start, int pageSize) throws ClassNotFoundException;

    long totalcount() throws SQLException, ClassNotFoundException; // Add this method
    List<ConversionHistory> getFilteredHistory(String fromCurrency, String toCurrency);

}