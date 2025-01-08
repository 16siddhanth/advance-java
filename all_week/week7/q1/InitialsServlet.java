import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InitialsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Get the full name from the request parameter
        String fullName = request.getParameter("name");
        
        if (fullName == null || fullName.trim().isEmpty()) {
            response.getWriter().write("Please provide a valid name.");
            return;
        }

        // Call the initials() function
        String initials = initials(fullName);

        // Respond with the initials
        response.setContentType("text/plain");
        response.getWriter().write("Initials: " + initials);
    }

    // Function to extract initials
    public String initials(String name) {
        String[] parts = name.trim().split("\\s+");
        StringBuilder initialsBuilder = new StringBuilder();

        for (String part : parts) {
            // Take the first character of each part and make it uppercase
            if (!part.isEmpty()) {
                initialsBuilder.append(Character.toUpperCase(part.charAt(0)));
            }
        }

        return initialsBuilder.toString();
    }
}
