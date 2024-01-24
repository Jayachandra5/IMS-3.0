package projectbms;

import java.sql.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SalesData {

    Logger logger = LogManager.getLogger(SalesData.class);

    private Connection connect() {
        // SQLite connection string
        String url = Constants.productDB;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            logger.error("Failed to connect to the product db in class SalesData.", e);
        }
        return conn;
    }

    public static void createNewTable() {
        // SQLite connection string
        String url = Constants.productDB;

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS " + Constants.salesTable + "(\n"
                + "	id integer PRIMARY KEY,\n"
                + "	stockName text NOT NULL,\n"
                + "	qnt real, \n"
                + "	stock real, \n"
                + "     amount real, \n"
                + "     profit real, \n"
                + "     date text NOT NULL, \n"
                + "     customer text NOT NULL \n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);

            Logger logger = LogManager.getLogger(SalesData.class);
            logger.info("Table created");
        } catch (SQLException e) {
            Logger logger = LogManager.getLogger(SalesData.class);
            logger.error("An error occurred while creating the sales table.", e);
        }
    }

    public int insert(String stockName, double qnt, double stock, double amount, double profit, String date, String customer) {

        String sql = "INSERT INTO " + Constants.salesTable + " (stockName,qnt,stock,amount,profit,date,customer)"
                + " VALUES(?,?,?,?,?,?,?)";

        String sql1 = "SELECT * "
                + "FROM " + Constants.salesTable + " WHERE (stockName,date,customer) = (?,?,?)";

        String sql2 = "UPDATE " + Constants.salesTable + " SET (qnt,stock,amount,profit) = (?,?,?,?)"
                + "WHERE (stockName,date,customer) = (?,?,?)";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql); PreparedStatement pstmt1 = conn.prepareStatement(sql1); PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {
            pstmt1.setString(1, stockName);
            pstmt1.setString(2, date);
            pstmt1.setString(3, customer);

            ResultSet rs = pstmt1.executeQuery();
            String name = null;
            String getDate = null;
            double getQnt = 0;
            double getAmount = 0;
            double getStock = 0;
            double getProfit = 0;
            String getCustomer = null;

            while (rs.next()) {
                name = rs.getString("stockName");
                getDate = rs.getString("date");
                getQnt = rs.getDouble("qnt");
                getStock = rs.getDouble("stock");
                getAmount = rs.getDouble("amount");
                getProfit = rs.getDouble("profit");
                getCustomer = rs.getString("customer");
            }
            if (stockName.equals(name) && date.equals(getDate) && customer.equals(getCustomer)) {
                pstmt2.setDouble(1, qnt = getQnt + qnt);
                pstmt2.setDouble(2, stock = getStock + stock);
                pstmt2.setDouble(3, amount = getAmount + amount);
                pstmt2.setDouble(4, profit = getProfit + profit);
                pstmt2.setString(5, name);
                pstmt2.setString(6, getDate);
                pstmt2.setString(7, customer);

                pstmt2.executeUpdate();
                logger.info("updated");
                return 1;
            } else {
                pstmt.setString(1, stockName);
                pstmt.setDouble(2, qnt);
                pstmt.setDouble(3, stock);
                pstmt.setDouble(4, amount);
                pstmt.setDouble(5, profit);
                pstmt.setString(6, date);
                pstmt.setString(7, customer);

                pstmt.executeUpdate();
                logger.info("added");
                return 2;
            }
        } catch (SQLException e) {
            logger.error("An error occurred while inserting the product in class Salesdata.", e);
        }
        return 0;
    }

    public void display() {

        String sql = "SELECT * FROM " + Constants.salesTable + " ORDER BY date";
        String sql1 = "SELECT COUNT(*) FROM " + Constants.salesTable;

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql); Statement stmt1 = conn.createStatement(); ResultSet rs1 = stmt1.executeQuery(sql1)) {

            rs1.next();

            int count = rs1.getInt(1);

            System.out.println("TOTAL NUMBER OF PRODUCTS :" + count);
            System.out.println("\n");

            System.out.println("STOCKNAME                QUANTITY      AMOUNT       PROFIT       DATE     ");

            while (rs.next()) {
                String stockname = rs.getString("stockName");
                double qnt = rs.getDouble("qnt");
                double stock = rs.getDouble("stock");
                double amount = rs.getDouble("amount");
                double profit = rs.getDouble("profit");
                String date = rs.getString("date");

                System.out.printf("%-20s", stockname);
                System.out.printf("     %.2f", qnt);
                System.out.printf("     %.2f", stock);
                System.out.printf("       %.2f", amount);
                System.out.printf("       %.2f", profit);
                System.out.printf("     %s", date);

                System.out.println();
            }
        } catch (SQLException e) {
            logger.error("An error occurred in display function in class SalesData.", e);
        }
    }

    public double stockAmountSold(String date1, String date2) {

        String sql = "SELECT stockName,SUM(stock) FROM " + Constants.salesTable
                + " WHERE date BETWEEN '" + date1 + "' and '" + date2 + "'";

        double totalSales = 0;

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            totalSales = rs.getDouble("SUM(stock)");

        } catch (SQLException e) {
            logger.error("An error occurred while returning TotalStockAmount Sold in "
                    + "class SalesData.", e);
        }
        return totalSales;
    }

    public double totalSales(String date1, String date2) {

        String sql = "SELECT stockName,SUM(amount) FROM " + Constants.salesTable
                + " WHERE date BETWEEN '" + date1 + "' and '" + date2 + "'";

        double totalSales = 0;

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            totalSales = rs.getDouble("SUM(amount)");

        } catch (SQLException e) {
            logger.error("An error occurred while returning TotalSalses in "
                    + "class SalesData.", e);
        }
        return totalSales;
    }

    public double totalProfit(String date1, String date2) {

        String sql = "SELECT stockName,SUM(profit) FROM " + Constants.salesTable
                + " WHERE date BETWEEN '" + date1 + "' and '" + date2 + "'";

        double totalProfit = 0;

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            totalProfit = rs.getDouble("SUM(profit)");

        } catch (SQLException e) {
            logger.error("An error occurred while returning Total Profit in "
                    + "class SalesData.", e);
        }
        return totalProfit;
    }

    public double oneItemQnt(String stockname, String date1, String date2) {

        String sql = "SELECT stockName, SUM(qnt) FROM " + Constants.salesTable
                + " WHERE date BETWEEN '" + date1 + "' AND '" + date2 + "' AND stockName=?";

        double sumQnt = 0;

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, stockname);
            ResultSet rs = pstmt.executeQuery();
            sumQnt = rs.getDouble("SUM(qnt)");

            logger.info("Sales Qnt" + sumQnt);

        } catch (SQLException e) {
            logger.error("An error occurred while returning one iteam qnt Sold in "
                    + "class SalesData.", e);
        }
        return sumQnt;
    }

    public int delete() {
        //String sql = "DELETE FROM "+Constants.salesTable+" WHERE stockName = ?";
        String sql = "DELETE FROM " + Constants.salesTable;
        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // pstmt.setString(1, stockname);
            pstmt.executeUpdate();
            logger.info("deleted");

        } catch (SQLException e) {
            logger.error("An error occurred while deleting in "
                    + "class SalesData.", e);
        }
        return 1;
    }
}
