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
            display: block;
            text-align: center;
            margin-top: 20px;
            color: #35495e;
            text-decoration: none;
            font-weight:bolder;
        }

        .pagination {
            text-align: center;
            margin-top: 10px;
        }

        .pagination a {
            color: #35495e;
            text-decoration: none;
            display: inline-block;
            padding: 8px 16px;
        }

        .pagination a:hover {
            background-color: #35495e;
            color: #fff;
        }

        .pagination .active {
            background-color: #35495e;
            color: white;
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

    <div class="pagination">
        <c:forEach begin="1" end="${totalPages}" var="pageNumber">
            <c:url var="pageUrl" value="history">
                <c:param name="page" value="${pageNumber}" />
                <c:param name="recordsPerPage" value="${recordsPerPage}" />
            </c:url>
            <c:choose>
                <c:when test="${pageNumber == page}">
                    <a class="active" href="${pageUrl}">${pageNumber}</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageUrl}">${pageNumber}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </div>

    <a href="index.jsp"><fmt:message key="backToCurrencyConverter" />
</a>
</body>
</html>
