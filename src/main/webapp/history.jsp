<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.selectedLanguage}" />
<fmt:setBundle basename="bundle/messages" />
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Conversion History</title>
    <style>
        body {
            background-color: #f1f1f1;
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }

        h1 {
            background-color: #35495e;
            color: #fff;
            padding: 20px;
            text-align: center;
        }

        table {
            width: 80%;
            margin: 20px auto;
            border-collapse: collapse;
            box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
            background: white;
        }

        table, th, td {
            border: 1px solid #ddd;
        }

        th, td {
            padding: 10px;
            text-align: left;
        }

        th {
            background-color: #35495e;
            color: #fff;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        a {
            align-items:center;
            text-align: center;
            margin-top: 20px;
            color: #35495e;
            text-decoration: none;
            font-weight:bolder;
            background-color:#35495e;
            color:white;
            margin:5px;
            padding:8px;
        }
        .pagination li {
                  display: inline;
              }
        .pagination{
          display:flex;
          justify-content:center;
        }
        .back{
          display:flex;
          width:20%;
          margin:0 auto;
          justify-content:center;
          background-color:#007bff;
          padding:10px 4px;
        }
    </style>
</head>
<body>
    <h1>
        <fmt:message key="conversionHistory.header" />
    </h1>
    <!-- Add the following code for filter options -->
    <div style="text-align: right;">
        <form id="filterForm" action="history" method="GET">
            <label for="fromCurrency"><fmt:message key="history.fromCurrency" /></label>
            <select id="fromCurrency" name="fromCurrency">
                <option value="">All</option>
                <option value="USD">USD</option>
                <option value="INR">INR</option>
                <option value="EUR">EUR</option>
                <option value="GBP">GBP</option>
                <option value="JPY">JPY</option>
                <option value="AUD">AUD</option>
                <option value="CAD">CAD</option>
            </select>

            <label for="toCurrency"><fmt:message key="history.toCurrency" /></label>
            <select id="toCurrency" name="toCurrency">
                <option value="">All</option>
                <option value="USD">USD</option>
                <option value="INR">INR</option>
                <option value="EUR">EUR</option>
                <option value="GBP">GBP</option>
                <option value="JPY">JPY</option>
                <option value="AUD">AUD</option>
                <option value="CAD">CAD</option>
               </select>

            <input type="submit" value="Filter">
        </form>
    </div>

    <table border="1">
        <tr>
            <th><fmt:message key="th.originalCurrency" /></th>
            <th><fmt:message key="th.targetCurrency" /></th>
            <th><fmt:message key="th.amount" /></th>
            <th><fmt:message key="th.conversionDate" /></th>
            <th><fmt:message key="th.exchangeRate" /></th>
            <th><fmt:message key="th.convertedAmount" /></th>
            <th><fmt:message key="th.timestamp" /></th>
        </tr>

        <c:forEach items="${conversionHistoryList}" var="conversion">
            <tr>
                <td>${conversion.originalCurrency}</td>
                <td>${conversion.targetCurrency}</td>
                <td>${conversion.amount}</td>
                <td>${conversion.conversionDate}</td>
                <td>${conversion.exchangeRate}</td>
                <td>${conversion.convertedAmount}</td>
                <td>${conversion.timestamp}</td>
            </tr>
        </c:forEach>
    </table>

 <div class="pagination ">
    <nav aria-label="Page navigation example">
        <ul class="pagination justify-content-center">
            <c:if test="${totalPages > 0 && totalPages >= p}">
                <li class="page-item">
                    <c:choose>
                        <c:when test="${p > 1}">
                            <a class="page-link" href="history?p=${p-1}" tabindex="-1">Previous</a>
                        </c:when>
                        <c:otherwise>
                            <a class="page-link disabled" tabindex="-1">Previous</a>
                        </c:otherwise>
                    </c:choose>
                </li>

                <c:choose>
                    <c:when test="${totalPages > 5 && p >= 3}">
                        <c:forEach var="i" begin="${p - 2}" end="${p + 2}">
                            <li class="page-item">
                                <a class="page-link" href="history?p=${i}">${i}</a>
                            </li>
                        </c:forEach>
                    </c:when>
                    <c:when test="${totalPages > 5 && p < 3}">
                        <c:forEach var="i" begin="1" end="5">
                            <li class="page-item">
                                <a class="page-link" href="history?p=${i}">${i}</a>
                            </li>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="i" begin="1" end="${totalPages}">
                            <li class="page-item">
                                <a class="page-link" href="history?p=${i}">${i}</a>
                            </li>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>

                <li class="page-item">
                    <c:choose>
                        <c:when test="${p < totalPages}">
                            <a class="page-link" href="history?p=${p + 1}">Next</a>
                        </c:when>
                        <c:otherwise>
                            <a class="page-link disabled">Next</a>
                        </c:otherwise>
                    </c:choose>
                </li>
            </c:if>
        </ul>
    </nav>
 </div>
    <a href="index.jsp" class="back"><fmt:message key="backToCurrencyConverter" />
</a>
</body>
</html>