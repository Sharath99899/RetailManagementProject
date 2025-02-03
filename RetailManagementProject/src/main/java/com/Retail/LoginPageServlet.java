package com.Retail;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet("/LoginPageServlet")
public class LoginPageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // Create a logger instance
    private static final Logger logger = LogManager.getLogger(LoginPageServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Get the login credentials from the request
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        

        
        // Initialize the response and print writer
        PrintWriter out = response.getWriter();
        String jsonResponse = "";

        try (Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Retail_DB", "root", "root")) {
            
            logger.info("Attempting to authenticate user with email: {}", email);

            // Query to validate login credentials
            String query = "SELECT * FROM Users WHERE email = ? AND password = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, email);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                // If login successful
                jsonResponse = "{\"status\": \"success\", \"message\": \"Login successful\"}";
                logger.info("Login successful for user: {}", email);
            } else {
                // If login fails
                jsonResponse = "{\"status\": \"error\", \"message\": \"Incorrect email or password\"}";
                logger.warn("Failed login attempt for email: {}", email);
            }
        } catch (SQLException e) {
            logger.error("SQL error occurred: {}", e.getMessage(), e);
            jsonResponse = "{\"status\": \"error\", \"message\": \"Internal Server Error\"}";
        }

        // Output the JSON response
        out.print(jsonResponse);
        out.flush();
    }
}
