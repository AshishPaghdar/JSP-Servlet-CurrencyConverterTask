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
//    private static final String SELECT_HISTORY_BY_USER_ID_SQL = "SELECT * FROM curr_conversion_history ORDER BY timestamp DESC";

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


    public List<ConversionHistory> getConversionHistory(int start, int pageSize) throws ClassNotFoundException {
        Connection connection = null;
        List<ConversionHistory> conversionHistoryList = new ArrayList<>();


        try {
            String query = "select * from curr_conversion_history order by user_id limit ?,?";
            connection = DBConnection.getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//                preparedStatement.setLong(1, userId);
                preparedStatement.setInt(1,start);
                preparedStatement.setInt(2,pageSize);
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
                    conversionHistoryList.add(conversionHistory);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions here
        }

        return conversionHistoryList;
    }
    public long totalCount() throws SQLException, ClassNotFoundException {
        long count = 0;

        try {
            String query = "select count(*) as count from curr_conversion_history";
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getLong("count");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public List<ConversionHistory> getFilteredHistory(String fromCurrency, String toCurrency) {
        List<ConversionHistory> filteredHistory = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM curr_conversion_history WHERE 1=1");

            if (!fromCurrency.isEmpty()) {
                sqlBuilder.append(" AND original_currency = ?");
            }

            if (!toCurrency.isEmpty()) {
                sqlBuilder.append(" AND target_currency = ?");
            }

            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sqlBuilder.toString());

            int parameterIndex = 1;

            if (!fromCurrency.isEmpty()) {
                preparedStatement.setString(parameterIndex++, fromCurrency);
            }

            if (!toCurrency.isEmpty()) {
                preparedStatement.setString(parameterIndex, toCurrency);
            }

            // Execute the SQL query.
            resultSet = preparedStatement.executeQuery();

            // Process the query results and populate the filteredHistory list.
            while (resultSet.next()) {
                ConversionHistory history = new ConversionHistory();
                history.setOriginalCurrency(resultSet.getString("original_currency"));
                history.setTargetCurrency(resultSet.getString("target_currency"));
                history.setExchangeRate(resultSet.getDouble("exchange_rate"));
                history.setAmount(resultSet.getDouble("amount"));
                history.setConversionDate(resultSet.getString("conversion_date"));
                history.setConvertedAmount(resultSet.getDouble("converted_amount"));
                history.setTimestamp(resultSet.getTimestamp("timestamp"));

                filteredHistory.add(history);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately.
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return filteredHistory;
    }

}