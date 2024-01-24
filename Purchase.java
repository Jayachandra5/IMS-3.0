package projectbms;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Purchase {

    org.apache.logging.log4j.Logger logger = LogManager.getLogger(Purchase.class);

    private Connection connect() {
        // SQLite connection string
        String url = Constants.productDB;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            logger.error("Failed to connect to product database.", e);
        }
        return conn;
    }

    public static void createNewTable() {
        // SQLite connection string
        String url = Constants.productDB;

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS " + Constants.purchaseTable + "(\n"
                + "	id integer PRIMARY KEY,\n"
                + "	stockName text NOT NULL,\n"
                + "	qnt real, \n"
                + "     amount real, \n"
                + "     qntAvl real, \n"
                + "     date text NOT NULL, \n"
                + "     vendourName text NOT NULL \n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);

            Logger logger = LogManager.getLogger(Purchase.class);
            logger.info("Table created");

        } catch (SQLException e) {
            Logger logger = LogManager.getLogger(Purchase.class);
            logger.error("An error occurred while creating the table.", e);
        }
    }

    public int insert(String stockName, double qnt, double amount, double qntAvl, String date, String vendourName) {

        String sql = "INSERT INTO " + Constants.purchaseTable + " (stockName,qnt,amount,qntAvl,date,vendourName)"
                + " VALUES(?,?,?,?,?,?)";

        String sql1 = "SELECT * "
                + "FROM " + Constants.purchaseTable + " WHERE (stockName,date,vendourName) = (?,?,?)";

        String sql2 = "UPDATE " + Constants.purchaseTable + " SET (qnt,amount,qntAvl) = (?,?,?)"
                + "WHERE (stockName,date,vendourName) = (?,?,?)";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql); PreparedStatement pstmt1 = conn.prepareStatement(sql1); PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {
            pstmt1.setString(1, stockName);
            pstmt1.setString(2, date);
            pstmt1.setString(3, vendourName);

            ResultSet rs = pstmt1.executeQuery();
            String name = null;
            String getDate = null;
            double getQnt = 0;
            double getAmount = 0;
            String getVendourName = null;

            while (rs.next()) {
                name = rs.getString("stockName");
                getDate = rs.getString("date");
                getQnt = rs.getDouble("qnt");
                getAmount = rs.getDouble("amount");
                getVendourName = rs.getString("vendourName");
            }
            if (stockName.equals(name) && date.equals(getDate) && vendourName.equals(getVendourName)) {

                pstmt2.setDouble(1, qnt = getQnt + qnt);
                pstmt2.setDouble(2, amount = getAmount + amount);
                pstmt2.setDouble(3, qntAvl);
                pstmt2.setString(4, name);
                pstmt2.setString(5, getDate);
                pstmt2.setString(6, vendourName);

                pstmt2.executeUpdate();
                logger.info("updated");
                return 1;
            } else {
                pstmt.setString(1, stockName);
                pstmt.setDouble(2, qnt);
                pstmt.setDouble(3, amount);
                pstmt.setDouble(4, qntAvl);
                pstmt.setString(5, date);
                pstmt.setString(6, vendourName);

                pstmt.executeUpdate();
                logger.info("added");
                return 2;
            }
        } catch (SQLException e) {
            logger.error("An error occurred while inserting the product purchase.", e);
        }
        return 0;
    }

    public void display() {
        String sql = "SELECT * FROM " + Constants.purchaseTable + " ORDER BY date";
        String sql1 = "SELECT COUNT(*) FROM " + Constants.purchaseTable;

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql); Statement stmt1 = conn.createStatement(); ResultSet rs1 = stmt1.executeQuery(sql1)) {

            rs1.next();

            int count = rs1.getInt(1);

            System.out.println("TOTAL NUMBER OF PRODUCTS :" + count);
            System.out.println("\n");

            System.out.println("STOCKNAME                QUANTITY      AMOUNT      DATE     ");

            while (rs.next()) {
                String stockname = rs.getString("stockName");
                double qnt = rs.getDouble("qnt");
                double amount = rs.getDouble("amount");
                double qntAvl = rs.getDouble("qntAvl");
                String date = rs.getString("date");

                System.out.printf("%-20s", stockname);
                System.out.printf("     %.2f", qnt);
                System.out.printf("       %.2f", amount);
                System.out.printf("     %.2f", qntAvl);
                System.out.printf("     %s", date);

                System.out.println();
            }
        } catch (SQLException e) {
            logger.error("An error occurred while displaying the product purchase list.", e);
        }
    }

    public double totalPurchase(String date1, String date2) {

        String sql = "SELECT stockName,SUM(amount) FROM " + Constants.purchaseTable
                + " WHERE date BETWEEN '" + date1 + "' and '" + date2 + "'";

        double totalPurchase = 0;

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            totalPurchase = rs.getDouble("SUM(amount)");

            //  System.out.println(totalPurchase);
        } catch (SQLException e) {
            logger.error("An error occurred while returing"
                    + " totalpurchase between two dates.", e);
        }
        return totalPurchase;
    }

    public double getOpeingQnt(String stockname, String date1) {

        String sql = "SELECT stockName,qntAvl,date FROM " + Constants.purchaseTable
                + " WHERE (stockName,date) = (?,?)";

        double qntAvl = 0;
        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date givenDate = sdf.parse(date1);
            Calendar cal = Calendar.getInstance();
            cal.setTime(givenDate);
            cal.add(Calendar.DATE, -1);
            Date beforeDay = cal.getTime();
            String outputDate = sdf.format(beforeDay);

            pstmt.setString(1, stockname);
            pstmt.setString(2, outputDate);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                qntAvl = rs.getDouble("qntAvl");
            }
        } catch (SQLException e) {
            logger.error("An error occurred while geting opeing stock of the product.", e);
        } catch (ParseException ex) {
            logger.error("An error occurred while parseing opening quantity.", ex);
        }
        return qntAvl;
    }

    public double getOpen(String stockname, String date) {
        double qntAvl = 0;
        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(
                "SELECT qntAvl FROM " + Constants.purchaseTable
                + " WHERE stockName = ? AND date < ? ORDER BY date DESC LIMIT 1")) {

            pstmt.setString(1, stockname);
            pstmt.setString(2, date);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                qntAvl = rs.getDouble("qntAvl");
            }
        } catch (SQLException e) {
            logger.error("An error occurred while geting opeing stock of the product.", e);
        }
        return qntAvl;
    }

    public int delete(String stockname) {

        String sql = "DELETE FROM " + Constants.purchaseTable + " WHERE stockName = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, stockname);
            pstmt.executeUpdate();
            logger.info("delete");

        } catch (SQLException e) {
            logger.error("An error occurred while deleting product in purchase list.", e);
        }
        return 1;
    }
}
