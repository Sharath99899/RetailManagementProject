package com.Retail;

import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet("/products")
public class ProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Logger instance
    private static final Logger logger = LogManager.getLogger(ProductServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Product> products = new ArrayList<>();

        // Database connection details
        String jdbcURL = "jdbc:mysql://127.0.0.1:3306/Retail_DB";
        String jdbcUsername = "root";
        String jdbcPassword = "root";

        try {
            logger.info("Connecting to the database...");
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);

            // Fetch products from the database
            String sql = "SELECT * FROM products";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                String imageUrl = resultSet.getString("image_url");

                Product product = new Product(id, name, price, imageUrl);
                products.add(product);
                logger.info("Product retrieved: {} - {}", name, price);
            }

            connection.close();
            logger.info("Database connection closed.");
        } catch (Exception e) {
            logger.error("Error while fetching products: {}", e.getMessage(), e);
        }

        // Convert products list to JSON
        Gson gson = new Gson();
        String json = gson.toJson(products);

        // Set response type to JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Write JSON to response
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        
 
        logger.info("Sent response with {} products", products.size());
    }
}

// Product class to hold product data
class Product {
    private int id;
    private String name;
    private double price;
    private String imageUrl;

    public Product(int id, String name, double price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }
}
