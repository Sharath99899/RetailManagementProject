package com.Retail;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet("/AddToCartServlet")
public class AddToCartServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(AddToCartServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productID = Integer.parseInt(request.getParameter("productID"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        int userID = 1; // Replace with actual user ID from session

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/JavaIsoft_DB", "root", "root");

            // Check if the product already exists in the cart
            String checkSql = "SELECT Quantity FROM cart WHERE ProductID = ? AND UserID = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, productID);
            checkStmt.setInt(2, userID);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                // Update quantity if the product already exists in the cart
                int existingQuantity = rs.getInt("Quantity");
                int newQuantity = existingQuantity + quantity;

                String updateSql = "UPDATE cart SET Quantity = ? WHERE ProductID = ? AND UserID = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setInt(1, newQuantity);
                updateStmt.setInt(2, productID);
                updateStmt.setInt(3, userID);
                updateStmt.executeUpdate();
                updateStmt.close();

                logger.info("Updated product {} quantity to {} for UserID: {}", productID, newQuantity, userID);
            } else {
                // Insert new product into the cart
                String insertSql = "INSERT INTO cart (ProductID, Quantity, UserID) VALUES (?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setInt(1, productID);
                insertStmt.setInt(2, quantity);
                insertStmt.setInt(3, userID);
                insertStmt.executeUpdate();
                insertStmt.close();

                logger.info("Added product {} with quantity {} for UserID: {}", productID, quantity, userID);
            }

            rs.close();
            checkStmt.close();
            conn.close();

            response.getWriter().write("Product added to cart successfully!");
        } catch (Exception e) {
            logger.error("Error adding product {} to cart for UserID: {}", productID, userID, e);
            response.getWriter().write("Error adding product to cart.");
        }
    }
}
