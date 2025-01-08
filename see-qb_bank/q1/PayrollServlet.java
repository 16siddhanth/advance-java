import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/PayrollServlet")
public class PayrollServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set response content type
        response.setContentType("text/html");

        // Get input values from the request
        String employeeName = request.getParameter("employeeName");
        String hoursWorkedStr = request.getParameter("hoursWorked");
        String hourlyRateStr = request.getParameter("hourlyRate");

        // Parse numeric values
        double hoursWorked = Double.parseDouble(hoursWorkedStr);
        double hourlyRate = Double.parseDouble(hourlyRateStr);
        
        // Calculate gross pay and deductions
        double grossPay = hoursWorked * hourlyRate;
        double tax = grossPay * 0.20;
        double netPay = grossPay - tax;

        // Output payroll statement
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>Payroll Statement</title></head>");
        out.println("<body>");
        out.println("<h1>Employee Payroll Statement</h1>");
        out.println("<p><strong>Employee Name:</strong> " + employeeName + "</p>");
        out.println("<p><strong>Hours Worked:</strong> " + hoursWorked + "</p>");
        out.println("<p><strong>Hourly Pay Rate:</strong> $" + hourlyRate + "</p>");
        out.println("<p><strong>Gross Pay:</strong> $" + grossPay + "</p>");
        out.println("<p><strong>Tax (20%):</strong> $" + tax + "</p>");
        out.println("<p><strong>Net Pay:</strong> $" + netPay + "</p>");
        out.println("</body>");
        out.println("</html>");

        out.close();
    }
}