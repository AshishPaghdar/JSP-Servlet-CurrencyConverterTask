package com.org.controller;

import com.org.dao.ConversionHistoryDAO;
import com.org.model.ConversionHistory;
import com.org.model.CurrencyConversion;
import com.org.service.ConversionHistoryService;
import com.org.service.ConversionHistoryServiceImpl;
import com.org.service.CurrencyConversionService;
import com.org.service.CurrencyConversionServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.xml.bind.ValidationException;

import java.io.IOException;
import java.sql.Timestamp;

public class CurrencyConversionController extends HttpServlet {
    private final CurrencyConversionService currencyConversionService = new CurrencyConversionServiceImpl();
    private final ConversionHistoryService conversionHistoryService = new ConversionHistoryServiceImpl(new ConversionHistoryDAO());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fromCurrency = request.getParameter("fromCurrency");
        String toCurrency = request.getParameter("toCurrency");
        String amountStr = request.getParameter("amount");
        String conversionDate = request.getParameter("conversionDate");

        try {
            // Validation: Check if amount is a valid number
            double amount = Double.parseDouble(amountStr);

            if (conversionDate == null || conversionDate.isEmpty()) {
                throw new ValidationException("Please select a date.");
            }

            double exchangeRate = currencyConversionService.getExchangeRate(fromCurrency, toCurrency, conversionDate, amount);
            double convertedAmount = currencyConversionService.convertCurrency(amount, exchangeRate);

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            CurrencyConversion conversion = new CurrencyConversion(fromCurrency, toCurrency, amount, convertedAmount, exchangeRate, timestamp);

            ConversionHistory conversionHistory = new ConversionHistory(fromCurrency, toCurrency, amount, conversionDate, exchangeRate, convertedAmount, timestamp);
            conversionHistoryService.saveConversion(conversionHistory);

            request.setAttribute("exchangeRate", exchangeRate);
            request.setAttribute("convertedAmount", convertedAmount);
            request.setAttribute("timestamp", timestamp);

            request.setAttribute("conversion", conversion);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            // Amount is not a valid number
            request.setAttribute("amountError", "Please enter a valid amount (e.g., 123.45) [Alphabets and special characters not allowed].");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (ValidationException e) {
            // Date is not selected
            request.setAttribute("dateError", e.getMessage());
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (Exception e) {
            // Other errors
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while performing the conversion.");
        }
    }
}
