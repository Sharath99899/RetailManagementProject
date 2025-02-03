package com.Retail;

import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet("/ProductListServlet") 
public class ProductListServlet extends HttpServlet {
	
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(ProductListServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        StringBuilder jsonOutput = new StringBuilder();
        jsonOutput.append("[");

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String jdbcURL = "jdbc:mysql://127.0.0.1:3306/Retail_DB";
            String dbUser = "root";
            String dbPassword = "root";

            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
            logger.info("Database connection established successfully.");

            String query = "SELECT * FROM ProductList";
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();

            boolean first = true;
            while (rs.next()) {
                if (!first) {
                    jsonOutput.append(",");
                }
                first = false;

                jsonOutput.append("{")
                          .append("\"ProductID\":").append(rs.getInt("ProductID")).append(",")
                          .append("\"ProductName\":\"").append(rs.getString("ProductName")).append("\",")
                          .append("\"Price\":").append(rs.getDouble("Price")).append(",")
                          .append("\"Quantity\":").append(rs.getInt("Quantity")).append(",")
                          .append("\"ExpireDate\":\"").append(rs.getString("ExpireDate")).append("\"")
                          .append("}");
            }
            logger.info("Product list fetched successfully.");

        } catch (Exception e) {
            logger.error("Error fetching product list: ", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
                logger.info("Database resources closed successfully.");
            } catch (Exception ex) {
                logger.error("Error closing database resources: ", ex);
            }
        }

        jsonOutput.append("]");
        out.print(jsonOutput.toString());
        out.flush();
    }
}
