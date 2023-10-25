package com.org.controller;

import com.org.dao.ConversionHistoryDAO;
import com.org.service.ConversionHistoryService;
import com.org.service.ConversionHistoryServiceImpl;
import com.org.model.ConversionHistory;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ConversionHistoryController extends HttpServlet {
    private final ConversionHistoryService conversionHistoryService = new ConversionHistoryServiceImpl(new ConversionHistoryDAO());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int page = 1; // Default to page 1 if not provided in the URL
            if (request.getParameter("page") != null) { //indicates which page of conversion history records to be displayed
                page = Integer.parseInt(request.getParameter("page"));
            }
            int recordsPerPage = 10;

            String fromCurrency = request.getParameter("fromCurrency");
            String toCurrency = request.getParameter("toCurrency");
            if (fromCurrency == null) {
                fromCurrency = "";
            }

            if (toCurrency == null) {
                toCurrency = "";
            }

            List<ConversionHistory> conversionHistoryList = conversionHistoryService.getConversionHistory();

            if (!fromCurrency.isEmpty() || !toCurrency.isEmpty()) {
                conversionHistoryList = conversionHistoryService.filterConversionHistory(fromCurrency,toCurrency);
            }

            // Calculate total number of pages based on the number of records and records per page
            int totalRecords = conversionHistoryList.size();
            int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);

            // Calculate the start and end index for the current pagem to extract subset of records for current page
            int startIndex = (page - 1) * recordsPerPage;
            int endIndex = Math.min(startIndex + recordsPerPage, totalRecords);

            // Extract records for the current page, records to be displayed on current page
            List<ConversionHistory> currentPageRecords = conversionHistoryList.subList(startIndex, endIndex);

            // Set the conversion history list for the current page as an attribute
            request.setAttribute("conversionHistoryList", currentPageRecords);

            // Set pagination parameters as attributes
            request.setAttribute("page", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("recordsPerPage", recordsPerPage);

            // Forward the request to the history.jsp page
            request.getRequestDispatcher("/history.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while fetching conversion history.");
        }
    }
}
