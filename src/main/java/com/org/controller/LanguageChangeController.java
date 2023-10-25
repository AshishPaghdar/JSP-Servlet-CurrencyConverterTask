package com.org.controller;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LanguageChangeController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String selectedLanguage = request.getParameter("language");
            System.out.println("Selected language: " + selectedLanguage);

            if (selectedLanguage == null || selectedLanguage.isEmpty()) {
                //error if the language parameter is missing or empty
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Language parameter is missing or empty.");
                return;
            }

            ResourceBundle messages = ResourceBundle.getBundle("bundle/messages", new Locale(selectedLanguage));

            request.getSession().setAttribute("selectedLanguage", selectedLanguage);
            request.getSession().setAttribute("messages", messages);

            Locale locale = messages.getLocale();
            System.out.println("Locale: " + locale);

            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle other errors (HTTP 500 error)
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while changing the language.");
        }
    }
}
