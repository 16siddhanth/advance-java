<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Set Cookies</title>
</head>
<body>
    <h1>Setting Cookies</h1>
    <%
        Cookie cookie1 = new Cookie("cookie1", "value1");
        Cookie cookie2 = new Cookie("cookie2", "value2");
        Cookie cookie3 = new Cookie("cookie3", "value3");

        cookie1.setMaxAge(60 * 60); // 1 hour
        cookie2.setMaxAge(60 * 60 * 24); // 1 day
        cookie3.setMaxAge(60 * 60 * 24 * 7); // 1 week

        response.addCookie(cookie1);
        response.addCookie(cookie2);
        response.addCookie(cookie3);

        out.println("<p>Cookies have been set.</p>");
    %>
    <a href="index.html">Back to Home</a>
</body>
</html>