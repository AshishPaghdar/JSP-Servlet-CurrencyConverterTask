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

    // Updated doGet method
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int pageNo=0;
            int start =0;

            String p1 = request.getParameter("p");
            int pageSize = 10;
            if (p1 == null) {
                pageNo = 1;
            } else {
                pageNo = Integer.parseInt(p1);
                start = (pageNo - 1) * pageSize;
            }

            String fromCurrency = request.getParameter("fromCurrency");
            String toCurrency = request.getParameter("toCurrency");
            if (fromCurrency == null) {
                fromCurrency = "";
            }

            if (toCurrency == null) {
                toCurrency = "";
            }

            List<ConversionHistory> conversionHistoryList;

            if (!fromCurrency.isEmpty() || !toCurrency.isEmpty()) {
                // Filter the history based on fromCurrency and toCurrency
//                System.out.println("Filtering by fromCurrency: " + fromCurrency + ", toCurrency: " + toCurrency);
                conversionHistoryList = conversionHistoryService.getFilteredHistory(fromCurrency, toCurrency);
            } else {
                // If no filter parameters, retrieve all history
                conversionHistoryList = conversionHistoryService.getConversionHistory(start, pageSize);
            }
//            System.out.println("Retrieved data: " + conversionHistoryList);


            double totalCount = conversionHistoryService.totalcount();
            long totalPages = (long) Math.ceil(totalCount / pageSize);
            request.setAttribute("conversionHistoryList", conversionHistoryList);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("pageSize", pageSize);
            request.setAttribute("p", pageNo);
            // Forward the request to the history.jsp page
            request.getRequestDispatcher("/history.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while fetching conversion history.");
        }
    }


}
