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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet("/RemoveCartItemServlet")
public class RemoveCartItemServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(RemoveCartItemServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int cartID = Integer.parseInt(request.getParameter("cartID"));

        try {
            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/JavaIsoft_DB", "root", "root");

            // Query to remove the item from the cart
            String sql = "DELETE FROM cart WHERE CartID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, cartID);

            // Execute the query
            int rowsAffected = pstmt.executeUpdate();
            pstmt.close();
            conn.close();

            if (rowsAffected > 0) {
                response.getWriter().write("Item removed successfully!");
                logger.info("Cart item removed successfully. CartID: {}", cartID);
            } else {
                response.getWriter().write("No item found with the given CartID.");
                logger.warn("Attempted to remove a non-existent CartID: {}", cartID);
            }
        } catch (Exception e) {
            logger.error("Error removing cart item. CartID: {}", cartID, e);
            response.getWriter().write("Error removing item.");
        }
    }
}
