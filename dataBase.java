package projectbms;

import java.sql.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dataBase {

    manage mdb = new manage();

    Logger logger = LogManager.getLogger(dataBase.class);

    public static void createNewDatabase() {

        //  String url = "jdbc:sqlite:V:/projectBms/" + fileName;
        String url = Constants.productDB;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                
                Logger logger = LogManager.getLogger(dataBase.class);
                logger.info("The driver name is {}", meta.getDriverName());
                logger.info("A new database has been created.");
            }

        } catch (SQLException ex) {
            Logger logger = LogManager.getLogger(dataBase.class);
            logger.error("An error occurred while creating the database.", ex);
        }
    }

    public static void createNewTable() {
        // SQLite connection string
        // String url = "jdbc:sqlite:V://projectBms/bmsList1.db";
        String url = Constants.productDB;
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS " + Constants.ProductTable + " (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	stockname text NOT NULL,\n"
                + "	quantity real, \n"
                + "     mrp real, \n"
                + "     price real, \n"
                + "     tax real \n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            Logger logger = LogManager.getLogger(dataBase.class);
            logger.info("Table created");

        } catch (SQLException ex) {
            Logger logger = LogManager.getLogger(dataBase.class);
            logger.error("An error occurred while creating the table.", ex);
        }
    }

    private static final String JDBC_URL = "jdbc:sqlserver://ims2023.database.windows.net:1433;database=IMS;";
    private static final String USER = "Jaya@ims2023";
    private static final String PASSWORD = "JcV@5818";

    private Connection connect() {
       Connection conn = null;
        try {
            // Load the JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Establish the connection
            conn = DriverManager.getConnection(JDBC_URL + "user=" + USER + ";password=" + PASSWORD + ";encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");

            System.out.println("Connected to the database");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
        return conn;
    }

    public int insert(String stockname, double mrp, double price, double quantity, double tax) {

        String sql = "INSERT INTO " + Constants.ProductTable + "(stockname,mrp,price,quantity,tax) VALUES(?,?,?,?,?)";

        String sql1 = "SELECT  stockname "
                + "FROM " + Constants.ProductTable + " WHERE stockname = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql); PreparedStatement pstmt1 = conn.prepareStatement(sql1)) {
            pstmt1.setString(1, stockname);

            ResultSet rs = pstmt1.executeQuery();
            String name = null;
            while (rs.next()) {
                name = rs.getString("stockname");
            }
            if (stockname.equals(name)) {
                logger.info("Product already exists. If you want to update quantity, please do so.");
                return 1;
            } else {
                pstmt.setString(1, stockname);
                pstmt.setDouble(2, mrp);
                pstmt.setDouble(3, price);
                pstmt.setDouble(4, quantity);
                pstmt.setDouble(5, tax);

                pstmt.executeUpdate();
                logger.info("Product added");

                double amount1 = price * quantity;
                String stockAmount = "stockamount";
                mdb.increaseAmount(stockAmount, amount1);
                return 2;
            }
        } catch (SQLException ex) {
            logger.error("An error occurred while inserting the product.", ex);
        }
        return 0;
    }

    public void display() {
        String sql = "SELECT * FROM " + Constants.ProductTable + " ORDER BY stockname";
        String sql1 = "SELECT COUNT(*) FROM " + Constants.ProductTable;

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql); Statement stmt1 = conn.createStatement(); ResultSet rs1 = stmt1.executeQuery(sql1)) {

            rs1.next();

            int count = rs1.getInt(1);

            System.out.println("TOTAL NUMBER OF PRODUCTS :" + count);
            System.out.println("\n");

            System.out.println("STOCKNAME                QUANTITY      MRP      PRICE AT BUYED      TAX%  ");

            while (rs.next()) {
                String stockname = rs.getString("stockname");
                double quantity = rs.getDouble("quantity");
                double mrp = rs.getDouble("mrp");
                double price = rs.getDouble("price");
                double tax = rs.getDouble("tax");

                System.out.printf("%-20s", stockname);
                System.out.printf("     %.2f", quantity);
                System.out.printf("       %.2f", mrp);
                System.out.printf("     %.2f", price);
                System.out.printf("                %.2f", tax);
                System.out.println();
            }
        } catch (SQLException ex) {
            logger.error("An error occurred while Displaying the product.", ex);
        }
    }

    public int increaseqnt(String stockname, double quantity, double price) {

        double temp = quantity * price;
        String stockamount = "stockamount";

        String sql = "SELECT  * "
                + "FROM " + Constants.ProductTable + " WHERE stockname = ?";

        String sql2 = "UPDATE " + Constants.ProductTable + " SET quantity = ?,price= ?  "
                + "WHERE stockname = ?";

        String sql3 = "UPDATE " + Constants.sellTable + " SET qnt = ?  "
                + "WHERE stockname = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql); PreparedStatement pstmt1 = conn.prepareStatement(sql2); PreparedStatement pstmt2 = conn.prepareStatement(sql3)) {

            pstmt.setString(1, stockname);

            ResultSet rs = pstmt.executeQuery();

            String pname = null;
            double prc = 0;
            double qnt = 0;
            double newPrice = 0;
            while (rs.next()) {
                pname = rs.getString("stockname");
                prc = rs.getDouble("price");
                qnt = rs.getDouble("quantity");
            }

            if (pname == null) {
                logger.info("Product not avalible please add the product first");
                return 1;
            } else if (prc != price) {
                newPrice = (qnt * prc + temp) / (qnt + quantity);
                double q = qnt + quantity;
                pstmt1.setDouble(1, q);
                pstmt1.setDouble(2, newPrice);
                pstmt1.setString(3, stockname);
                pstmt1.executeUpdate();

                pstmt2.setDouble(1, q);
                pstmt2.setString(2, stockname);
                pstmt2.executeUpdate();

                mdb.increaseAmount(stockamount, temp);

            } else {
                double q = qnt + quantity;
                pstmt1.setDouble(1, q);
                pstmt1.setDouble(2, prc);
                pstmt1.setString(3, stockname);
                pstmt1.executeUpdate();

                pstmt2.setDouble(1, q);
                pstmt2.setString(2, stockname);
                pstmt2.executeUpdate();

                logger.info("quantity added ");
                mdb.increaseAmount(stockamount, temp);
                logger.info("Stockamount incresed");
            }
        } catch (SQLException ex) {
            logger.error("An error occurred while incersing quanity of the product.", ex);
        }
        return 2;
    }

    public int redcuceqnt(String stockname, double quantity, double price, String presentDate, String csname) {
        double tempqnt = quantity;
        double tempprice = price;

        String sql = "SELECT  *"
                + "FROM " + Constants.ProductTable + " WHERE stockname = ?";

        String sql2 = "UPDATE " + Constants.ProductTable + " SET quantity = ?  "
                + "WHERE stockname = ?";

        String sql3 = "UPDATE  " + Constants.sellTable + "  SET qnt = ?  "
                + "WHERE stockname = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql); PreparedStatement pstmt1 = conn.prepareStatement(sql2); PreparedStatement pstmt2 = conn.prepareStatement(sql3)) {
            pstmt.setString(1, stockname);

            ResultSet rs = pstmt.executeQuery();

            double qnt = 0;
            double prc = 0;
            String name = null;
            while (rs.next()) {
                name = rs.getString("stockname");
                qnt = rs.getDouble("quantity");
                prc = rs.getDouble("price");
            }
            if (name == null) {
                return 1;
            }
            if (qnt < quantity) {
                logger.info("Orderd quantity is more then avaliblie");
                logger.info("Available qunantity is " + qnt + " only");
                return 2;
            } else {
                double q = qnt - quantity;
                pstmt1.setDouble(1, q);
                pstmt1.setString(2, stockname);

                pstmt1.executeUpdate();

                pstmt2.setDouble(1, q);
                pstmt2.setString(2, stockname);
                pstmt2.executeUpdate();

                logger.info("Qnt reduced");

                String stockamount = "stockamount";
                double temp = tempqnt * prc;
                mdb.decreaseAmount(stockamount, temp);
                logger.info("Stockamount decresed");

                String totalsales = "totalsales";
                double sales = tempqnt * tempprice;
                mdb.increaseAmount(totalsales, sales);
                logger.info("Sales value Increased ");

                double prc1 = tempprice - prc;
                double profit = prc1 * tempqnt;

                String varname = "profit";
                mdb.increaseAmount(varname, profit);
                logger.info("Profit Increased");
                SalesData sale = new SalesData();
                sale.insert(stockname, quantity, temp, sales, profit, presentDate, csname);
            }
        } catch (SQLException ex) {
            logger.error("An error occurred while Decersing quanity of the product.", ex);
        }
        return 3;
    }

    public int delete(String stockname) {
        String sql1 = "SELECT * "
                + "From " + Constants.ProductTable + " WHERE stockname = ?";

        String sql = "DELETE FROM " + Constants.ProductTable + " WHERE stockname = ?";

        String sql2 = "DELETE FROM " + Constants.sellTable + " WHERE stockname = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql); PreparedStatement pstmt1 = conn.prepareStatement(sql1); PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {
            pstmt1.setString(1, stockname);
            ResultSet rs = pstmt1.executeQuery();

            double qnt = 0;
            String name = null;
            while (rs.next()) {
                qnt = rs.getDouble("quantity");
                name = rs.getString("stockname");
            }

            if (name == null) {
                logger.info("No such product available");
                return 1;
            } else {
                if (qnt == 0) {
                    pstmt.setString(1, stockname);
                    pstmt.executeUpdate();
                    logger.info("Product deleted from product list");

                    pstmt2.setString(1, stockname);
                    pstmt2.executeUpdate();
                    logger.info("product delted from sell list");
                    return 2;

                } else if (qnt != 0) {
                    logger.info("Quantity avalabile is " + qnt + " sell all the qunatity before deleting");
                    logger.info("Are kindly add the product into spoiled data");
                }
            }
        } catch (SQLException ex) {
            logger.error("An error occurred while deleting the product.", ex);
        }
        return 3;
    }

    public double displayItem(String stockname) {
        String sql = "SELECT  * "
                + "FROM " + Constants.ProductTable + " WHERE stockname = ?";

        String temp1 = null;
        double temp2 = 0;
        double temp3 = 0;
        double temp4 = 0;
        double temp5 = 0;

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, stockname);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                temp1 = rs.getString("stockname");
                temp2 = rs.getDouble("quantity");
                temp3 = rs.getDouble("mrp");
                temp4 = rs.getDouble("price");
                temp5 = rs.getDouble("tax");
            }

            if (temp1 == null) {
                logger.info("No such product available");
            } else {
                /*  System.out.println("Product name            :"+temp1);
            System.out.println("Product quantity        :"+temp2);
            System.out.println("Product MRP             :"+temp3);
            System.out.println("product buying price    :"+temp4);
            System.out.println("product tax%(CGST+SGST) :"+temp5);*/
            }
        } catch (SQLException ex) {
            logger.error("An error occurred while disaplying single product.", ex);
        }
        return temp2;
    }

    public int spoil(String stockname, double quantity) {
        double tempqnt = quantity;
        String sql = "SELECT  * "
                + "FROM " + Constants.ProductTable + " WHERE stockname = ?";

        String sql2 = "UPDATE " + Constants.ProductTable + " SET quantity = ?  "
                + "WHERE stockname = ?";

        String sql3 = "UPDATE " + Constants.sellTable + " SET qnt = ?  "
                + "WHERE stockname = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql); PreparedStatement pstmt1 = conn.prepareStatement(sql2); PreparedStatement pstmt2 = conn.prepareStatement(sql3)) {

            pstmt.setString(1, stockname);

            ResultSet rs = pstmt.executeQuery();

            // loop through the result set
            double qnt = 0;
            double prc = 0;
            String name = null;
            while (rs.next()) {
                name = rs.getString("stockname");
                qnt = rs.getDouble("quantity");
                prc = rs.getDouble("price");
            }

            if (name == null) {
                logger.info("No such product available");
                return 1;
            } else if (qnt < quantity) {
                logger.info("Quantity enterd is more then available");
                logger.info("Available qunantity is " + qnt + " only");
                logger.info("Kindly try againg by "
                        + "entering quantity less then are equal to " + qnt);
                return 2;
            } else {
                double q = qnt - quantity;
                pstmt1.setDouble(1, q);
                pstmt1.setString(2, stockname);

                pstmt2.setDouble(1, q);
                pstmt2.setString(2, stockname);

                pstmt1.executeUpdate();
                pstmt2.executeUpdate();
                logger.info("Qnt reduced");
                double temp = tempqnt * prc;

                String stockamount = "stockamount";
                String expenses = "expenses";

                mdb.increaseAmount(expenses, temp);
                logger.info("Expenses Incresed ");
                mdb.decreaseAmount(stockamount, temp);
                logger.info("stockamount decresed");

            }
        } catch (SQLException ex) {
            logger.error("An error occurred while entering spoling product.", ex);
        }
        return 3;
    }
}
