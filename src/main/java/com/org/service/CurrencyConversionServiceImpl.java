package com.org.service;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CurrencyConversionServiceImpl implements CurrencyConversionService {
    private final String apiKey;
    private final String baseUrl;

    public CurrencyConversionServiceImpl() {
        Properties properties = new Properties();
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("properties/app.properties");
//            System.out.println("Properties file path: " + inputStream);
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Error loading app.properties, Unable to Read the Properties File. " + e.getMessage());
        }

        apiKey = properties.getProperty("api.key");
        baseUrl = properties.getProperty("api.base.url");
    }

    @Override
    public double getExchangeRate(String fromCurrency, String toCurrency, String date, double amount) {
        try {
            OkHttpClient client = new OkHttpClient();  //created instance for making http request

            String url = baseUrl + "?from=" + fromCurrency + "&to=" + toCurrency + "&amount=" + amount + "&date=" + date;

            Request request = new Request.Builder()  //constructing GET request
                    .url(url)
                    .addHeader("apikey", apiKey)
                    .method("GET", null)
                    .build();

            Response response = client.newCall(request).execute();
            String jsonResponse = response.body().string();

            // Parse the exchange rate from the JSON response
            double exchangeRate = parseExchangeRate(jsonResponse);

            return exchangeRate;
        } catch (IOException e) {
            throw new RuntimeException("Error getting exchange rate. Please try again later.");
        }
    }

    @Override
    public double convertCurrency(double amount, double exchangeRate) {
        return amount * exchangeRate;
    }

    // Method to parse the exchange rate from the JSON response
    private double parseExchangeRate(String jsonResponse) {
        try {
            // Parse the JSON response
            JSONObject json = new JSONObject(jsonResponse);

            if (json.getBoolean("success")) {
                JSONObject info = json.getJSONObject("info");
                return info.getDouble("rate");
            } else {
                throw new IOException("JSON response indicates failure.(Failed to extract Exchnage Rates)");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error parsing exchange rate. The response is not in the expected format.");
        }
    }
}
