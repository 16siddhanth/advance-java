
<%@ page import="java.io.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Grade Result</title>
</head>
<body>
    <h1>Grade Result</h1>
    <%! 
        // Define the getJavaGrade method in the declaration section
        public String getJavaGrade(int marks) {
            if (marks > 90) return "S";
            else if (marks >= 80) return "A";
            else if (marks >= 70) return "B";
            else if (marks >= 60) return "C";
            else if (marks >= 50) return "D";
            else return "FAIL";
        }
    %>
    
    <%
        // Retrieve marks for 5 subjects from the request
        int javaMarks = Integer.parseInt(request.getParameter("java"));
        int subject2Marks = Integer.parseInt(request.getParameter("subject2"));
        int subject3Marks = Integer.parseInt(request.getParameter("subject3"));
        int subject4Marks = Integer.parseInt(request.getParameter("subject4"));
        int subject5Marks = Integer.parseInt(request.getParameter("subject5"));
        
        // Calculate the grade for Java using the getJavaGrade method
        String javaGrade = getJavaGrade(javaMarks);
        String subject2Grade = getJavaGrade(subject2Marks);
        String subject3Grade = getJavaGrade(subject3Marks);
        String subject4Grade = getJavaGrade(subject4Marks);
        String subject5Grade = getJavaGrade(subject5Marks);
        		
        
        // Display the grades
        out.println("<p><strong>Java:</strong> " + javaMarks + " - Grade: " + javaGrade + "</p>");
        out.println("<p><strong>Subject 2:</strong> " + subject2Marks + " - Grade: " + subject2Grade + "</p>");
        out.println("<p><strong>Subject 3:</strong> " + subject3Marks + " - Grade: " + subject3Grade + "</p>");
        out.println("<p><strong>Subject 4:</strong> " + subject4Marks + " - Grade: " + subject4Grade + "</p>");
        out.println("<p><strong>Subject 5:</strong> " + subject5Marks + " - Grade: " + subject5Grade + "</p>");
    %>
</body>
</html>
