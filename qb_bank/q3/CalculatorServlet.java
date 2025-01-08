package weeek66;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class CalculatorServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set response content type
        response.setContentType("text/html");

        // Get the PrintWriter object to write the response
        PrintWriter out = response.getWriter();

        // Get input data from the form
        String num1Str = request.getParameter("num1");
        String num2Str = request.getParameter("num2");
        String operation = request.getParameter("operation");

        // Validate input
        if (num1Str == null || num2Str == null || operation == null || num1Str.isEmpty() || num2Str.isEmpty() || operation.isEmpty()) {
            out.println("<html><body>");
            out.println("<h3>Error: All fields are required!</h3>");
            out.println("<a href='calculator.html'>Go Back</a>");
            out.println("</body></html>");
            return;
        }

        double num1 = 0, num2 = 0;
        try {
            num1 = Double.parseDouble(num1Str);
            num2 = Double.parseDouble(num2Str);
        } catch (NumberFormatException e) {
            out.println("<html><body>");
            out.println("<h3>Error: Please enter valid numbers!</h3>");
            out.println("<a href='calculator.html'>Go Back</a>");
            out.println("</body></html>");
            return;
        }

        double result = 0;
        String errorMessage = "";

        // Perform the operation
        switch (operation.toLowerCase()) {
            case "add":
                result = num1 + num2;
                break;
            case "sub":
                result = num1 - num2;
                break;
            case "mul":
                result = num1 * num2;
                break;
            case "divide":
                if (num2 == 0) {
                    errorMessage = "Error: Division by zero is not allowed!";
                } else {
                    result = num1 / num2;
                }
                break;
            case "e^x":
                result = Math.exp(num1); // e^x
                break;
            default:
                errorMessage = "Error: Invalid operation!";
        }

        // Display result or error message
        out.println("<html><body>");
        if (!errorMessage.isEmpty()) {
            out.println("<h3>" + errorMessage + "</h3>");
        } else {
            out.println("<h3>Result: " + result + "</h3>");
        }
        out.println("<a href='calculator.html'>Go Back</a>");
        out.println("</body></html>");
    }
}
