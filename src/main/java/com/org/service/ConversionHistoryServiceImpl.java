package com.org.service;

import com.org.dao.ConversionHistoryDAO;
import com.org.model.ConversionHistory;

import java.util.ArrayList;
import java.util.List;

public class ConversionHistoryServiceImpl implements ConversionHistoryService {
    private final ConversionHistoryDAO conversionHistoryDAO; // Initialize this DAO either through constructor or setter injection

    public ConversionHistoryServiceImpl(ConversionHistoryDAO conversionHistoryDAO) {
        this.conversionHistoryDAO = conversionHistoryDAO;
    }

    @Override
    public void saveConversion(ConversionHistory conversionHistory) {
        conversionHistoryDAO.saveConversion(conversionHistory);
    }

    @Override
    public List<ConversionHistory> getConversionHistory() throws ClassNotFoundException {
        return conversionHistoryDAO.getConversionHistory();
    }
    public List<ConversionHistory> filterConversionHistory(String fromCurrency, String toCurrency) throws ClassNotFoundException {
        List<ConversionHistory> allHistory = conversionHistoryDAO.getConversionHistory();
        List<ConversionHistory> filteredHistory = new ArrayList<>();

        for (ConversionHistory history : allHistory) {
            if ((fromCurrency == null || fromCurrency.isEmpty() || history.getOriginalCurrency().equals(fromCurrency))
                    && (toCurrency == null || toCurrency.isEmpty() || history.getTargetCurrency().equals(toCurrency))) {
                filteredHistory.add(history);
            }
        }

        return filteredHistory;
    }
}
