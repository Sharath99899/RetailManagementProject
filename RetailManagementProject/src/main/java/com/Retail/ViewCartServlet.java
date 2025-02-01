package com.Retail;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

@WebServlet("/ViewCartServlet")
public class ViewCartServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ViewCartServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userID = 1; // Replace with actual user ID from session
        List<CartItem> cartItems = new ArrayList<>();

        try {
            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/JavaIsoft_DB", "root", "root");

            // Query to fetch cart items
            String sql = "SELECT c.CartID, p.ProductName, p.Price, c.Quantity " +
                         "FROM cart c " +
                         "JOIN ProductList p ON c.ProductID = p.ProductID " +
                         "WHERE c.UserID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userID);

            // Execute the query
            ResultSet rs = pstmt.executeQuery();

            // Process the result set
            while (rs.next()) {
                CartItem item = new CartItem();
                item.setCartID(rs.getInt("CartID"));
                item.setProductName(rs.getString("ProductName"));
                item.setPrice(rs.getDouble("Price"));
                item.setQuantity(rs.getInt("Quantity"));
                cartItems.add(item);
            }

            // Close resources
            rs.close();
            pstmt.close();
            conn.close();

            // Convert cart items to JSON and send the response
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String jsonResponse = new Gson().toJson(cartItems);
            response.getWriter().write(jsonResponse);

            logger.info("Cart items fetched successfully for UserID: {}", userID);
        } catch (Exception e) {
            logger.error("Error fetching cart items for UserID: {}", userID, e);
            response.getWriter().write("Error fetching cart items.");
        }
    }
}

class CartItem {
    private int cartID;
    private String productName;
    private double price;
    private int quantity;

    // Getters and setters
    public int getCartID() { return cartID; }
    public void setCartID(int cartID) { this.cartID = cartID; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
