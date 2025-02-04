package com.Retail;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet("/OrderNowServlet")
public class OrderNowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(OrderNowServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        ArrayList<Order> orderList = new ArrayList<>();

        try {
            logger.info("doGet method started");

            // Database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Retail_DB", "root", "root");

            // Fetch order details
            String query = "SELECT id, product_name, price, quantity FROM cart";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Order order = new Order(
                    rs.getInt("id"),
                    rs.getString("product_name"),
                    rs.getDouble("price"),
                    rs.getInt("quantity")
                );
                orderList.add(order);
            }

            // Convert to JSON and send response
            Gson gson = new Gson();
            String orderJson = gson.toJson(orderList);
            out.print(orderJson);
            out.flush();

            logger.info("Successfully fetched orders from the database.");

            // Close resources
            rs.close();
            pst.close();
            con.close();
        } catch (Exception e) {
            logger.error("Failed to fetch orders", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"Failed to fetch orders\"}");
        }
    }

    // Inner Order class
    class Order {
        private int id;
        private String productName;
        private double price;
        private int quantity;

        public Order(int id, String productName, double price, int quantity) {
            this.id = id;
            this.productName = productName;
            this.price = price;
            this.quantity = quantity;
        }

        // Getters
        public int getId() { return id; }
        public String getProductName() { return productName; }
        public double getPrice() { return price; }
        public int getQuantity() { return quantity; }
    }
}
