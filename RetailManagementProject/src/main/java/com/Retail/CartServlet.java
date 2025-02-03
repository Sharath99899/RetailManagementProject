package com.Retail;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class CartItem {
    String productName;
    double price;
    int quantity;

    public CartItem(String productName, double price, int quantity) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }
}

@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Logger instance
    private static final Logger logger = LogManager.getLogger(CartServlet.class);

    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/Retail_DB";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "root";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productName = request.getParameter("productName");
        String priceStr = request.getParameter("price");
        String removeProduct = request.getParameter("removeProduct");
        String clearCart = request.getParameter("clearCart");

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {

            if (productName != null && priceStr != null) {
                double price = Double.parseDouble(priceStr);
                String checkQuery = "SELECT quantity FROM cart WHERE product_name=?";
                PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
                checkStmt.setString(1, productName);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    int quantity = rs.getInt("quantity") + 1;
                    String updateQuery = "UPDATE cart SET quantity=? WHERE product_name=?";
                    PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                    updateStmt.setInt(1, quantity);
                    updateStmt.setString(2, productName);
                    updateStmt.executeUpdate();
                    logger.info("Product {} updated in cart with new quantity: {}", productName, quantity);
                } else { 
                	
                    String insertQuery = "INSERT INTO cart (product_name, price, quantity) VALUES (?, ?, ?)";
                    PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                    insertStmt.setString(1, productName);
                    insertStmt.setDouble(2, price);
                    insertStmt.setInt(3, 1);
                    insertStmt.executeUpdate();
                    logger.info("Product {} added to cart with price: {} and quantity: 1", productName, price);
                }
            } else if (removeProduct != null) {
                String deleteQuery = "DELETE FROM cart WHERE product_name=?";
                PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery);
                deleteStmt.setString(1, removeProduct);
                deleteStmt.executeUpdate();
                logger.info("Product {} removed from cart", removeProduct);
            } else if (clearCart != null) {
                String clearQuery = "DELETE FROM cart";
                PreparedStatement clearStmt = conn.prepareStatement(clearQuery);
                clearStmt.executeUpdate();
                logger.info("Cart cleared");
            }
        } catch (SQLException e) {
            logger.error("Error while accessing the database: {}", e.getMessage(), e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<CartItem> cartItems = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String query = "SELECT product_name, price, quantity FROM cart";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                cartItems.add(new CartItem(
                        rs.getString("product_name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity")
                ));
            }
            logger.info("Cart retrieved successfully with {} items", cartItems.size());
        } catch (SQLException e) {
            logger.error("Error while retrieving cart items: {}", e.getMessage(), e);
        }

        String json = new Gson().toJson(cartItems);
        response.setContentType("application/json");
        response.getWriter().write(json);
    }
}
