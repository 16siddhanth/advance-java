<%@ page import="java.io.*,java.util.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"%>
<%
    // Retrieve the name and age from the request
    String name = request.getParameter("name");
    String ageString = request.getParameter("age");
    
    // Parse the age into an integer
    int age = Integer.parseInt(ageString);
    
    // Initialize ticket price variable
    int ticketPrice = 0;
    
    // Determine the ticket price based on the age
    if (age > 62) {
        ticketPrice = 50; // Senior citizens
    } else if (age < 10) {
        ticketPrice = 30; // Children
    } else {
        ticketPrice = 80; // Adults
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Movie Ticket Price</title>
</head>
<body>
    <h2>Movie Ticket Information</h2>
    <p>Hello <%= name %>,</p>
    <p>Your age is: <%= age %> years.</p>
    
    <p>The price of the movie ticket is Rs. <%= ticketPrice %>.</p>

    <br><br>
    <a href="index.html">Go back to the form</a>
</body>
</html>
