import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class register extends HttpServlet {

    // doGet method to display the registration form
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head><title>User Registration</title></head>");
        out.println("<body>");
        out.println("<h2>Register to the Portal</h2>");
        out.println("<form action='register' method='POST'>");
        out.println("Username: <input type='text' name='username' required><br>");
        out.println("Password: <input type='password' name='password' required><br>");
        out.println("Confirm Password: <input type='password' name='confirmPassword' required><br>");
        out.println("<input type='submit' value='Register'>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
    }

    // doPost method to process the registration form
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        if (password.length() < 8) {
            out.println("<h3>Password must be at least 8 characters long!</h3>");
        } else if (password.equals(confirmPassword)) {
            out.println("<h3>Welcome, " + username + "!</h3>");
        } else {
            out.println("<h3>Passwords do not match!</h3>");
        }
    }
}
