package com.Retail;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet("/ClearCartServlet")
public class ClearCartServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ClearCartServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int userID = (int) session.getAttribute("userID"); // Retrieve userID from session

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/JavaIsoft_DB", "root", "root");

            // Query to clear the cart
            String sql = "DELETE FROM cart WHERE UserID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userID);

            // Execute the query
            int rowsAffected = pstmt.executeUpdate();
            pstmt.close();
            conn.close();

            if (rowsAffected > 0) {
                response.getWriter().write("Cart cleared successfully!");
                logger.info("Cart cleared successfully for UserID: {}", userID);
            } else {
                response.getWriter().write("Cart was already empty.");
                logger.warn("Attempted to clear an empty cart for UserID: {}", userID);
            }
        } catch (Exception e) {
            logger.error("Error clearing cart for UserID: {}", userID, e);
            response.getWriter().write("Error clearing cart.");
        }
    }
}