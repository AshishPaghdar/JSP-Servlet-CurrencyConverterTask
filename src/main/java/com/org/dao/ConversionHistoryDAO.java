package com.org.dao;

import com.org.model.ConversionHistory;
import com.org.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConversionHistoryDAO {
    private static final String INSERT_HISTORY_SQL = "INSERT INTO curr_conversion_history (user_id, original_currency, target_currency, amount, conversion_date, exchange_rate, converted_amount, timestamp) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_HISTORY_BY_USER_ID_SQL = "SELECT * FROM curr_conversion_history ORDER BY timestamp DESC";

    public void saveConversion(ConversionHistory conversionHistory) {
        Connection connection;
        try {
            connection = DBConnection.getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_HISTORY_SQL)) {
                preparedStatement.setLong(1, conversionHistory.getUserId());
                preparedStatement.setString(2, conversionHistory.getOriginalCurrency());
                preparedStatement.setString(3, conversionHistory.getTargetCurrency());
                preparedStatement.setDouble(4, conversionHistory.getAmount());
                preparedStatement.setString(5, conversionHistory.getConversionDate());
                preparedStatement.setDouble(6, conversionHistory.getExchangeRate());
                preparedStatement.setDouble(7, conversionHistory.getConvertedAmount());
                preparedStatement.setTimestamp(8, new java.sql.Timestamp(conversionHistory.getTimestamp().getTime()));
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            // Handle exceptions here
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    public List<ConversionHistory> getConversionHistory() throws ClassNotFoundException {
        Connection connection = null;
        List<ConversionHistory> historyList = new ArrayList<>();
        try {
            connection = DBConnection.getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_HISTORY_BY_USER_ID_SQL)) {
//                preparedStatement.setLong(1, userId);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String originalCurrency = resultSet.getString("original_currency");
                    String targetCurrency = resultSet.getString("target_currency");
                    double amount = resultSet.getDouble("amount");
                    String conversionDate = resultSet.getString("conversion_date");
                    double exchangeRate = resultSet.getDouble("exchange_rate");
                    double convertedAmount = resultSet.getDouble("converted_amount");
                    java.sql.Timestamp timestamp = resultSet.getTimestamp("timestamp");

                    ConversionHistory conversionHistory = new ConversionHistory(originalCurrency, targetCurrency, amount, conversionDate, exchangeRate, convertedAmount, timestamp);
                    historyList.add(conversionHistory);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions here
        }

        return historyList;
    }
}
