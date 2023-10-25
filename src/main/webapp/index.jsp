<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.selectedLanguage}" />
<fmt:setBundle basename="bundle/messages" />
<!DOCTYPE html>
<html>
<head>
     <meta charset="UTF-8">
    <title>Currency Converter</title>
 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>

    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">    <style>
        body {
                   background-color: #35495e;
                   font-family: Arial, sans-serif;
               }

               .container {
                   max-width: 500px;
                   max-height :700px;
                   margin: 0 auto;
                   background-color: #fff;
                   padding: 80px;
                   border: 1px solid #ddd;
                   border-radius: 5px;
                   box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
                   overflow: hidden;
               }

               h1 {
                   font-size: 20px;
                   margin-bottom: 10px;
                   margin-top: -70px;
                   font-weight:bold;
                   text-align:center;
               }

              .form-group label {
                          font-weight: bold;
                      }

                      .form-group select,
                      .form-group input[type="text"] {
                          width: 100%;
                          padding: 5px;
                          margin-bottom: 5px;
                          border: 1px solid #ddd;
                          border-radius: 5px;
                          font-size: 14px;
                      }

                      .form-group button {
                          background-color: #007bff;
                          color: #fff;
                          padding: 5px 10px;
                          border: none;
                          border-radius: 5px;
                          font-size: 14px;
                          cursor: pointer;
                          width: 100%;
                      }
               .btn-primary {
                   background-color: #007bff;
                   color: #fff;
                   padding: 5px 10px;
               }

               .btn-primary:hover {
                   background-color: #0056b3;
               }

               .btn-secondary {
                   background-color: #6c757d;
                   color: #fff;
                   padding: 5px 10px;
               }

               .btn-secondary:hover {
                   background-color: #535b62;
               }

               .success-message {
                   color: #28a745;
                   font-weight: bold;
               }

               .error-message {
                   color: #dc3545;
                   font-weight: bold;
               }

               #languageDropdown {
                   width: 100%;
                   margin-top:5px;
               }

               #timestamp {
                   display: none;
               }

               .top-right {
                   position: absolute;
                   top: 10px;
                   right: 10px;
                   text-align: right;
               }
               .header{
                  margin-top:-10px;
               }
    </style>
</head>
<body>
 <form action="languageChange" method="post" id="languageForm">
        <input type="hidden" name="language" id="selectedLanguage" />
    </form>
 <div class="top-right">
     <button id="changeLanguageBtn" class="btn btn-primary" onclick="changeLanguage()">
        <fmt:message key="language.button.change" />
     </button>
      <select id="languageDropdown" class="form-control">
        <option value="en"><fmt:message key="language.option.en" /></option>
        <option value="fr"><fmt:message key="language.option.fr" /></option>
        <option value="it"><fmt:message key="language.option.it" /></option>
        <option value="es"><fmt:message key="language.option.es" /></option>
        <option value="de"><fmt:message key="language.option.de" /></option>
        <option value="hi"><fmt:message key="language.option.hi" /></option>
        <option value="gu"><fmt:message key="language.option.gu" /></option>
        <option value="ta"><fmt:message key="language.option.ta" /></option>
        <option value="zh"><fmt:message key="language.option.zh" /></option>
      </select>
    </div>


    <div class="container">

<h1>
<i class="fa-solid fa-hand-holding-dollar fa-lg"></i>
    <fmt:message key="currencyConverterApp.label" />
    <i class="fa-solid fa-coins"></i>
</h1>

        <form action="convert" method="post" class="mt-2" onsubmit="return validateForm()" id="form">
            <div class="form-group">
                <label for="fromCurrency"><fmt:message key="currency.from.label"/></label>
                <select name="fromCurrency" id="fromCurrency" class="form-control" onchange="disableSameCurrency()">
                    <option value="USD">USD</option>
                    <option value="INR" disabled>INR</option>
                    <option value="EUR">EUR</option>
                    <option value="GBP">GBP</option>
                    <option value="JPY">JPY</option>
                    <option value="AUD">AUD</option>
                    <option value="CAD">CAD</option>
                </select>
            </div>

            <div class="form-group">
                <label for="toCurrency"><fmt:message key="currency.to.label"/></label>
                <select name="toCurrency" id="toCurrency" class="form-control" onchange="disableSameCurrency()">
                    <option value="INR">INR</option>
                    <option value="USD" disabled>USD</option>
                    <option value="EUR">EUR</option>
                    <option value="GBP">GBP</option>
                    <option value="JPY">JPY</option>
                    <option value="AUD">AUD</option>
                    <option value="CAD">CAD</option>
                </select>
            </div>

            <div class="form-group">
                <label for="amount"><fmt:message key="amount.label"/></label>
                <input type="text" name="amount" id="amount" class="form-control" />
                <span id="amountError" class="error-message" style="display: none;">
                        ${requestScope.amountError}
                </span>
            </div>

            <div class="form-group">
                <label for="conversionDate"><fmt:message key="conversionDate.label"/></label>
                <input type="date" name="conversionDate" id="conversionDate" class="form-control" placeholder="yyyy-mm-dd"
                pattern="\d{4}-\d{2}-\d{2}" max="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>"
                />
             <span id="dateError" class="error-message" style="display: none;">
                         ${requestScope.dateError}
             </span>
            </div>

            <button type="submit" class="btn btn-primary"><fmt:message key="convertButton.label"/></button>
        </form>

        <div class="mt-4">
            <h1 class="header"><fmt:message key="conversionDetails.label"/></h1>
                          <div class="form-group">
                              <label for="exchangeRate"><fmt:message key="exchangeRate.label"/></label>
                              <input type="text" id="exchangeRate" class="form-control" readonly value="${exchangeRate}" />
                          </div>

                          <div class="form-group">
                              <label for="convertedAmount"><fmt:message key="convertedAmount.label"/></label>
                              <input type="text" id="convertedAmount" class="form-control" readonly value="${convertedAmount}" />
                          </div>
           <div class="form-group">
               <input type="hidden" id="timestamp" class="form-control" readonly value="${timestamp}" />
           </div>
        </div>

        <form action="history" method="get" class="mt-2">
            <button type="submit" class="btn btn-secondary"><fmt:message key="showHistoryButton.label" /></button>
        </form>
    </div>
       <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
         <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.min.js"></script>
<script>
         function disableSameCurrency() {
             var fromCurrency = document.getElementById("fromCurrency").value;
             var toCurrency = document.getElementById("toCurrency").value;
             var fromCurrencyOptions = document.getElementById("fromCurrency").getElementsByTagName("option");
             var toCurrencyOptions = document.getElementById("toCurrency").getElementsByTagName("option");

             for (var i = 0; i < fromCurrencyOptions.length; i++) {
                 fromCurrencyOptions[i].removeAttribute("disabled");
              }

             for (var i = 0; i < toCurrencyOptions.length; i++) {
                 toCurrencyOptions[i].removeAttribute("disabled");
             }

             for (var i = 0; i < fromCurrencyOptions.length; i++) {
                 if (fromCurrencyOptions[i].value === toCurrency) {
                     fromCurrencyOptions[i].setAttribute("disabled", "disabled");
                 }
             }

             for (var i = 0; i < toCurrencyOptions.length; i++) {
                 if (toCurrencyOptions[i].value === fromCurrency) {
                     toCurrencyOptions[i].setAttribute("disabled", "disabled");
                 }
             }
         }


      function validateForm() {
          var conversionDate = document.getElementById("conversionDate").value;
          var amount = document.getElementById("amount").value;
          var dateError = document.getElementById("dateError");
          var amountError = document.getElementById("amountError");

          if (conversionDate === "") {
              dateError.innerHTML = "Please select a date.";
              dateError.style.color = "red";
              dateError.style.display = "block";
              return false;
          } else {
              dateError.innerHTML = "";
              dateError.style.display = "none";
          }

          var amountPattern = /^\d+(\.\d+)?$/;

          if (amount === "" || !amount.match(amountPattern)) {
              amountError.innerHTML = "Please enter a valid amount (e.g., 123.45) [Alphabets and special characters not allowed].";
              amountError.style.color = "red";
              amountError.style.display = "block"; // Show the error message
              return false;
          } else {
              amountError.innerHTML = "";
              amountError.style.display = "none"; // Hide the error message
          }
          return true;
      }
function changeLanguage() {
        var selectedLanguage = document.getElementById("languageDropdown").value;
        document.getElementById("selectedLanguage").value = selectedLanguage;
        document.getElementById("languageForm").submit();
    }
     </script>
</body>
</html>
