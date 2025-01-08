import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Date;

public class CookieServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set the content type for the response
        response.setContentType("text/html");

        // Get the current time and set the expiry time
        long expiryTime = System.currentTimeMillis() + 60000; // 1 minute from now

        // Create the first two cookies that will expire in 1 minute
        Cookie cookie1 = new Cookie("cookie1", "FirstCookie");
        Cookie cookie2 = new Cookie("cookie2", "SecondCookie");

        cookie1.setMaxAge(60); // Expiry time of 60 seconds (1 minute)
        cookie2.setMaxAge(60);

        // Create two cookies that will not expire (default maxAge = -1)
        Cookie cookie3 = new Cookie("cookie3", "ThirdCookie");
        Cookie cookie4 = new Cookie("cookie4", "FourthCookie");

        // Add cookies to the response
        response.addCookie(cookie1);
        response.addCookie(cookie2);
        response.addCookie(cookie3);
        response.addCookie(cookie4);

        // Get all cookies from the request
        Cookie[] cookies = request.getCookies();

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>Cookies Display</h1>");

        // Display the current cookies
        if (cookies != null) {
            out.println("<h2>Cookies:</h2>");
            out.println("<table border='1'><tr><th>Cookie Name</th><th>Cookie Value</th><th>Expires</th></tr>");
            for (Cookie cookie : cookies) {
                out.println("<tr><td>" + cookie.getName() + "</td>");
                out.println("<td>" + cookie.getValue() + "</td>");
                out.println("<td>" + new Date(expiryTime) + "</td></tr>");
            }
            out.println("</table>");
        } else {
            out.println("<p>No cookies found!</p>");
        }

        out.println("</body></html>");
    }
}
