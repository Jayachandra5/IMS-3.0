package projectbms;

import java.sql.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class manage {

    public static void createNewDatabase() {

        //String url = "jdbc:sqlite:V:/projectBms/" + fileName;
        String url = Constants.mangeDB;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();

                Logger logger = LogManager.getLogger(manage.class);
                logger.info("The driver name is {}", meta.getDriverName());
                logger.info("A new database has been created.");
            }

        } catch (SQLException e) {
            Logger logger = LogManager.getLogger(manage.class);
            logger.error("An error occurred while creating the database.", e);
        }
    }

    public static void createNewTable() {
        // SQLite connection string
        //   String url = "jdbc:sqlite:V://projectBms/managedb1.db";
        String url = Constants.mangeDB;

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS " + Constants.manage + " (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text NOT NULL,\n"
                + "	amount real \n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);

            Logger logger = LogManager.getLogger(manage.class);
            logger.info("Table created");
        } catch (SQLException e) {
            Logger logger = LogManager.getLogger(manage.class);
            logger.error("An error occurred while creating the table.", e);
        }
    }

    private Connection connect() {
        // SQLite connection string
        //String url = "jdbc:sqlite:V://projectBms/managedb1.db";
        String url = Constants.mangeDB;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            Logger logger = LogManager.getLogger(manage.class);
            logger.error("An error occurred while conneting to mange db.", e);
        }
        return conn;
    }

    public void insert(String name, double amount) {
        String sql = "INSERT INTO " + Constants.manage + "(name,amount) VALUES(?,?)";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setDouble(2, amount);

            pstmt.executeUpdate();
            System.out.println("data Inserted.");

        } catch (SQLException e) {
            Logger logger = LogManager.getLogger(manage.class);
            logger.error("An error occurred while inserting into manage .", e);
        }
    }

    public void increaseAmount(String name, double amount) {
        String sql = "SELECT  name, amount "
                + "FROM " + Constants.manage + " WHERE name = ?";

        String sql2 = "UPDATE " + Constants.manage + " SET amount = ?  "
                + "WHERE name = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql); PreparedStatement pstmt1 = conn.prepareStatement(sql2)) {
            pstmt.setString(1, name);

            ResultSet rs = pstmt.executeQuery();

            // loop through the result set
            double price = 0;
            while (rs.next()) {
                price = rs.getDouble("amount");
            }

            pstmt1.setDouble(1, amount = price + amount);
            pstmt1.setString(2, name);

            pstmt1.executeUpdate();

        } catch (SQLException e) {
            Logger logger = LogManager.getLogger(manage.class);
            logger.error("An error occurred while incressing amount in mange.", e);
        }
    }

    public void decreaseAmount(String name, double amount) {
        String sql = "SELECT  name, amount "
                + "FROM " + Constants.manage + " WHERE name = ?";

        String sql2 = "UPDATE " + Constants.manage + " SET amount = ?  "
                + "WHERE name = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql); PreparedStatement pstmt1 = conn.prepareStatement(sql2)) {
            pstmt.setString(1, name);

            ResultSet rs = pstmt.executeQuery();

            double price = 0;
            while (rs.next()) {
                price = rs.getDouble("amount");
            }

            pstmt1.setDouble(1, amount = price - amount);
            pstmt1.setString(2, name);

            pstmt1.executeUpdate();

        } catch (SQLException e) {
            Logger logger = LogManager.getLogger(manage.class);
            logger.error("An error occurred while decersing amount in mange.", e);
        }
    }

    public void display(String name) {

        String sql = "SELECT  * "
                + "FROM " + Constants.manage + " WHERE name = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);

            ResultSet rs = pstmt.executeQuery();

            double price = 0;

            while (rs.next()) {
                price = rs.getDouble("amount");
            }

            System.out.println(price);
        } catch (SQLException e) {
            Logger logger = LogManager.getLogger(manage.class);
            logger.error("An error occurred while displaying amount in mange.", e);
        }
    }

    public void revenue() {
        String name1 = "profit";
        String name2 = "expenses";

        String sql = "SELECT  * "
                + "FROM " + Constants.manage + " WHERE name = ?";

        String sql1 = "SELECT  * "
                + "FROM " + Constants.manage + " WHERE name = ?";
        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql); PreparedStatement pstmt1 = conn.prepareStatement(sql1)) {
            pstmt.setString(1, name1);
            pstmt1.setString(1, name2);

            ResultSet rs = pstmt.executeQuery();
            ResultSet rs1 = pstmt1.executeQuery();

            double profit = 0;

            while (rs.next()) {
                profit = rs.getDouble("amount");
            }

            double expenses = 0;
            while (rs1.next()) {
                expenses = rs1.getDouble("amount");
            }

            double revenue = profit - expenses;

            System.out.println(revenue);
        } catch (SQLException e) {
            Logger logger = LogManager.getLogger(manage.class);
            logger.error("An error occurred while displaying revenue amount in mange.", e);
        }
    }

    public double getvalue(String name) {
        String sql = "SELECT  * "
                + "FROM " + Constants.manage + " WHERE name = ?";

        double price = 0;

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                price = rs.getDouble("amount");
            }
        } catch (SQLException e) {
            Logger logger = LogManager.getLogger(manage.class);
            logger.error("An error occurred while getting amount in mange.", e);
        }
        return price;
    }
}
