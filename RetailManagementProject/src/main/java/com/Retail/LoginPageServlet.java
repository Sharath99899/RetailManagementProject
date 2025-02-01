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
    private static final Logger logger = LogManager.getLogger(LoginPageServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email").trim();
        String password = request.getParameter("password").trim();

        PrintWriter out = response.getWriter();
        String jsonResponse = "";

        try (Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/JavaIsoft_DB", "root", "root")) {
            logger.info("Database connection established successfully.");

            // Use BINARY keyword for case-sensitive comparison
            String query = "SELECT * FROM Users WHERE BINARY email = ? AND BINARY password = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, email);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                jsonResponse = "{\"status\": \"success\", \"message\": \"Login successful\"}";
                logger.info("User login successful for email: {}", email);
            } else {
                jsonResponse = "{\"status\": \"error\", \"message\": \"Incorrect email or password\"}";
                logger.warn("Failed login attempt for email: {}", email);
            }
        } catch (SQLException e) {
            logger.error("Database error occurred: ", e);
            jsonResponse = "{\"status\": \"error\", \"message\": \"Internal Server Error\"}";
        }

        out.print(jsonResponse);
        out.flush();
    }
}
