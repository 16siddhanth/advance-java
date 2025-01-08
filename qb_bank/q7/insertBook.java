<%@ page import="java.sql.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>Insert Book</title>
</head>
<body>
    <h1>Insert Book</h1>
    <%
        String bookNo = request.getParameter("bookNo");
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String publication = request.getParameter("publication");
        String price = request.getParameter("price");

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/BookStore", "root", "");

            String query = "INSERT INTO Books (Book_No, Title, Author, Publication, Price) VALUES (?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(bookNo));
            stmt.setString(2, title);
            stmt.setString(3, author);
            stmt.setString(4, publication);
            stmt.setDouble(5, Double.parseDouble(price));

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                out.println("<p>Book inserted successfully!</p>");
            } else {
                out.println("<p>Failed to insert book.</p>");
   
