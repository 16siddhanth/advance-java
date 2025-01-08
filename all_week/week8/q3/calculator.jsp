<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.lang.Math" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Scientific Calculator</title>
</head>
<body>

<h2>Scientific Calculator</h2>
<form action="calculator.jsp" method="post">
    <label for="number">Enter Number:</label>
    <input type="number" id="number" name="number" step="any" required>

    <label for="operation">Select Operation:</label>
    <select id="operation" name="operation" required>
        <option value="">Select...</option>
        <option value="sin">Sin</option>
        <option value="cos">Cos</option>
        <option value="tan">Tan</option>
        <option value="log">Log</option>
        <option value="sqrt">Square Root</option>
    </select>

    <input type="submit" value="Calculate">
</form>

<% 
    String numberStr = request.getParameter("number");
    String operation = request.getParameter("operation");
    double result = 0;
    boolean showResult = false;
    String errorMessage = "";

    if (numberStr != null && !numberStr.isEmpty() && operation != null && !operation.isEmpty()) {
        try {
            double number = Double.parseDouble(numberStr);
            
            if (operation.equals("sin")) {
                result = Math.sin(Math.toRadians(number)); // Convert to radians
                showResult = true;
            } else if (operation.equals("cos")) {
                result = Math.cos(Math.toRadians(number)); // Convert to radians
                showResult = true;
            } else if (operation.equals("tan")) {
                result = Math.tan(Math.toRadians(number)); // Convert to radians
                showResult = true;
            } else if (operation.equals("log")) {
                if (number <= 0) {
                    errorMessage = "Logarithm is undefined for zero or negative numbers.";
                } else {
                    result = Math.log(number); // Natural log
                    showResult = true;
                }
            } else if (operation.equals("sqrt")) {
                if (number < 0) {
                    errorMessage = "Square root is undefined for negative numbers.";
                } else {
                    result = Math.sqrt(number); // Square root
                    showResult = true;
                }
            }
        } catch (NumberFormatException e) {
            errorMessage = "Please enter a valid number.";
        }
    }

    if (showResult) {
%>
        <div class="result">
            <h3>Result:</h3>
            <p>The result of <strong><%= operation %></strong> for <%= numberStr %> is: <%= result %></p>
        </div>
<%  
    } else if (!errorMessage.isEmpty()) {
%>
        <div class="error">
            <h3>Error:</h3>
            <p><%= errorMessage %></p>
        </div>
<%  
    }
%>

</body>
</html>
