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

@WebServlet("/RegisterPageServlet")
public class RegisterPageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String firstName = request.getParameter("FirstName");
        String lastName = request.getParameter("LastName");
        String gender = request.getParameter("Gender");
        String phoneNumber = request.getParameter("PhoneNumber");
        String email = request.getParameter("Email");
        String password = request.getParameter("Password");
        String city = request.getParameter("City");
        String state = request.getParameter("State");
        String country = request.getParameter("Country");
        String postalCode = request.getParameter("PostalCode");
        
        PrintWriter out = response.getWriter();
        String jsonResponse = "";

        try (Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Retail_DB", "root", "root")) {

            String checkEmailQuery = "SELECT * FROM Users WHERE Email = ?";
            PreparedStatement checkEmailStmt = con.prepareStatement(checkEmailQuery);
            checkEmailStmt.setString(1, email);
            ResultSet rs = checkEmailStmt.executeQuery();

            if (rs.next()) {

                jsonResponse = "{\"status\": \"error\", \"message\": \"Email already exists\"}";
            } else {

                String query = "INSERT INTO Users (FirstName, LastName, Gender, PhoneNumber, Email, Password, City, State, Country, PostalCode) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = con.prepareStatement(query);
                pst.setString(1, firstName);
                pst.setString(2, lastName);
                pst.setString(3, gender);
                pst.setString(4, phoneNumber);
                pst.setString(5, email);
                pst.setString(6, password);  
                pst.setString(7, city);
                pst.setString(8, state);
                pst.setString(9, country);
                pst.setString(10, postalCode);

                int result = pst.executeUpdate();
                if (result > 0) {
                  
                    jsonResponse = "{\"status\": \"success\", \"message\": \"Registration successful\"}";
                } else {
                   
                    jsonResponse = "{\"status\": \"error\", \"message\": \"Registration failed\"}";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            jsonResponse = "{\"status\": \"error\", \"message\": \"Internal Server Error\"}";
        }

       
        out.print(jsonResponse);
        out.flush();
    }
}