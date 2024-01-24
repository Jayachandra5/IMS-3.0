package projectbms;

import java.sql.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class sellTable {

    public static void createNewTableSell() {
        // SQLite connection string
        // String url = "jdbc:sqlite:V://projectBms/bmsList1.db";

        String url = Constants.productDB;
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS " + Constants.sellTable + " (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	stockname text NOT NULL,\n"
                + "	price real, \n"
                + "	qnt real, \n"
                 + "	mrp real \n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);

            Logger logger = LogManager.getLogger(sellTable.class);
            logger.info("Table created");
        } catch (SQLException e) {
            Logger logger = LogManager.getLogger(sellTable.class);
            logger.error("An error occurred while creating the table.", e);
        }
    }

    private Connection connect() {
        // SQLite connection string
        // String url = "jdbc:sqlite:V://projectBms/bmsList1.db";
        String url = Constants.productDB;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            Logger logger = LogManager.getLogger(sellTable.class);
            logger.error("An error occurred while conneting to product DB.", e);
        }
        return conn;
    }

    public void insert(String stockname, double price, double qnt,double mrp) {

        String sql = "INSERT INTO " + Constants.sellTable + " (stockname,price,qnt,mrp) VALUES(?,?,?,?)";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, stockname);
            pstmt.setDouble(2, price);
            pstmt.setDouble(3, qnt);
            pstmt.setDouble(4, mrp);

            pstmt.executeUpdate();
            System.out.println("Product added");
        } catch (SQLException e) {
            Logger logger = LogManager.getLogger(sellTable.class);
            logger.error("An error occurred while insterting to sellTable.", e);
        }
    }

    public void display() {
        String sql = "SELECT * FROM " + Constants.sellTable + " ORDER BY stockname";
        String sql1 = "SELECT COUNT(*) FROM " + Constants.sellTable + "";

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql); Statement stmt1 = conn.createStatement(); ResultSet rs1 = stmt1.executeQuery(sql1)) {

            rs1.next();

            int count = rs1.getInt(1);

            System.out.println("TOTAL NUMBER OF PRODUCTS :" + count);
            System.out.println("\n");

            System.out.println("STOCKNAME                 PRICE  ");

            while (rs.next()) {
                String stockname = rs.getString("stockname");
                double price = rs.getDouble("price");
                double qnt = rs.getDouble("qnt");

                System.out.printf("%-20s", stockname);
                System.out.printf("     %.2f", price);
                System.out.printf("     %.2f", qnt);
                System.out.println();

            }
        } catch (SQLException e) {
            Logger logger = LogManager.getLogger(sellTable.class);
            logger.error("An error occurred while displaying sellTable.", e);
        }
    }

    public void displayItem(String stockname) {
        String sql = "SELECT  * "
                + "FROM " + Constants.sellTable + " WHERE stockname = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, stockname);

            ResultSet rs = pstmt.executeQuery();

            String temp1 = null;
            double temp2 = 0;
            double temp3 = 0;

            while (rs.next()) {
                temp1 = rs.getString("stockname");
                temp2 = rs.getDouble("price");
                temp3 = rs.getDouble("qnr");
            }

            if (temp1 == null) {
                System.out.println("No such product available");
            } else {
                System.out.println("Product name            :" + temp1);
                System.out.println("Product price           :" + temp2);
                System.out.println("Product qnt             :" + temp3);
                System.out.println();
            }
        } catch (SQLException e) {
            Logger logger = LogManager.getLogger(sellTable.class);
            logger.error("An error occurred while disaplying item in selltable.", e);
        }
    }

    public double getprice(String stockname) {
        String sql = "SELECT  * "
                + "FROM " + Constants.sellTable + " WHERE stockname = ?";

        double temp2 = 0;

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, stockname);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                temp2 = rs.getDouble("price");
            }

        } catch (SQLException e) {
            Logger logger = LogManager.getLogger(sellTable.class);
            logger.error("An error occurred while geting price.", e);
        } finally {
            return temp2;
        }
    }

    public int update(String stockname, double price) {

        String sql = "SELECT  * "
                + "FROM " + Constants.sellTable + " WHERE stockname = ?";

        String sql2 = "UPDATE " + Constants.sellTable + " SET price = ?  "
                + "WHERE stockname = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql); PreparedStatement pstmt1 = conn.prepareStatement(sql2)) {

            pstmt.setString(1, stockname);

            ResultSet rs = pstmt.executeQuery();

            String pname = null;
            double prc = 0;

            while (rs.next()) {
                pname = rs.getString("stockname");
                prc = rs.getDouble("price");
            }

            if (pname == null) {
                System.out.println("Product not avalible please add the product first");
                return 1;
            } else if (prc == price) {
                System.out.println("You have entered product price same the before");
                return 2;
            } else {
                pstmt1.setDouble(1, price);
                pstmt1.setString(2, stockname);

                pstmt1.executeUpdate();

                System.out.println("Previous price of " + stockname + " is " + prc);
                System.out.println("Now price of " + stockname + " changed to " + price);
            }
        } catch (SQLException e) {
            Logger logger = LogManager.getLogger(sellTable.class);
            logger.error("An error occurred while updateing product in selltable.", e);
        }
        return 3;
    }
}
