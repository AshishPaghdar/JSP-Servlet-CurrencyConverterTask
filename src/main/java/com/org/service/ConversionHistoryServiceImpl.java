package com.org.service;

import com.org.dao.ConversionHistoryDAO;
import com.org.model.ConversionHistory;

import java.sql.SQLException;
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
    public List<ConversionHistory> getConversionHistory(int start, int pageSize) throws ClassNotFoundException {
        return conversionHistoryDAO.getConversionHistory(start,pageSize);
    }

    @Override
    public long totalcount() {
        try {
            return conversionHistoryDAO.totalCount();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            // You might want to handle the exceptions or return an appropriate value here
        }
        return 0; // Return a default value (0) or handle the exception appropriately
    }

    public List<ConversionHistory> getFilteredHistory(String fromCurrency, String toCurrency) {
        return conversionHistoryDAO.getFilteredHistory(fromCurrency, toCurrency);
    }
}