package projectbms;

import java.sql.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dues {

    manage mdb = new manage();
    org.apache.logging.log4j.Logger logger = LogManager.getLogger(dues.class);

    public static void createNewTable() {
        // SQLite connection string
        // String url = "jdbc:sqlite:V://projectBms/bmsList1.db";
        String url = Constants.mangeDB;

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS " + Constants.csDue + " (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	customer text NOT NULL,\n"
                + "	csdue real \n"
                + ");";

        String sql1 = "CREATE TABLE IF NOT EXISTS " + Constants.vendourDue + " (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text NOT NULL,\n"
                + "	ourdue real \n"
                + ");";

        String sql2 = "CREATE TABLE IF NOT EXISTS " + Constants.empDue + " (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	empname text NOT NULL,\n"
                + "	empdue real \n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            stmt.execute(sql1);
            stmt.execute(sql2);

            Logger logger = LogManager.getLogger(dues.class);
            logger.info("Table created");

        } catch (SQLException e) {
            Logger logger = LogManager.getLogger(dues.class);
            logger.error("An error occurred while creating the dues table.", e);
        }
    }

    private Connection connect() {
        // SQLite connection string
        //String url = "jdbc:sqlite:V://projectBms/bmsList1.db";
        String url = Constants.mangeDB;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            logger.error("Failed to connect to mange database.", e);
        }
        return conn;
    }

    public int insertcs(String name, double amount) {

        String dueamount = "totalcsdues";

        String sql = "INSERT INTO " + Constants.csDue + "(customer,csdue) VALUES(?,?)";

        String sql1 = "SELECT  * "
                + "FROM " + Constants.csDue + " WHERE customer = ?";

        String sql2 = "UPDATE " + Constants.csDue + " SET csdue = ? "
                + "WHERE customer = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql); PreparedStatement pstmt1 = conn.prepareStatement(sql1); PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {
            pstmt1.setString(1, name);

            ResultSet rs = pstmt1.executeQuery();
            String getname = null;
            double getamount = 0;

            while (rs.next()) {
                getname = rs.getString("customer");
                getamount = rs.getDouble("csdue");
            }
            if (name.equals(getname)) {
                if (amount > 0) {
                    pstmt2.setDouble(1, amount);
                    pstmt2.setString(2, name);
                    pstmt2.executeUpdate();
                    System.out.println("customer due updated");
                    if (getamount > amount) {
                        mdb.decreaseAmount(dueamount, getamount - amount);
                    }
                    if (getamount < amount) {
                        mdb.increaseAmount(dueamount, amount - getamount);
                    }
                    return 1;
                } else if (amount == 0 || getamount + amount == 0) {
                    deletecs(name);
                    mdb.decreaseAmount(dueamount, getamount);
                    return 2;
                } else if (getamount + amount < 0) {
                    return 3;
                } else {
                    pstmt2.setDouble(1, getamount + amount);
                    pstmt2.setString(2, name);
                    pstmt2.executeUpdate();
                    System.out.println("customer due updated");

                    mdb.decreaseAmount(dueamount, -1 * amount);

                    return 4;
                }
            } else {
                if (amount < 0) {
                    System.err.println("You have entered -ve value for new customer ");
                    System.err.println("So customer due not enterd");
                } else if(amount > 0) {
                    pstmt.setString(1, name);
                    pstmt.setDouble(2, amount);
                    pstmt.executeUpdate();
                    System.out.println("customer name added");
                    mdb.increaseAmount(dueamount, amount);
                }
            }
        } catch (SQLException e) {
            logger.error("An error occurred while inserting the customer due.", e);
        }
        return 5;
    }

    public int insertour(String name, double amount) {

        String dueamount = "totalourdues";

        String sql = "INSERT INTO " + Constants.vendourDue + "(name,ourdue) VALUES(?,?)";

        String sql1 = "SELECT  * "
                + "FROM " + Constants.vendourDue + " WHERE name = ?";

        String sql2 = "UPDATE " + Constants.vendourDue + " SET ourdue = ?"
                + "WHERE name = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql); PreparedStatement pstmt1 = conn.prepareStatement(sql1); PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {
            pstmt1.setString(1, name);

            ResultSet rs = pstmt1.executeQuery();
            String getname = null;
            double getamount = 0;

            while (rs.next()) {
                getname = rs.getString("name");
                getamount = rs.getDouble("ourdue");
            }
            if (name.equals(getname)) {
                if (amount > 0) {
                    pstmt2.setDouble(1, amount + getamount);
                    pstmt2.setString(2, name);
                    pstmt2.executeUpdate();
                    System.out.println("vendour due updated");

                    mdb.increaseAmount(dueamount, amount);

                    return 1;
                } else if (amount == 0 || getamount + amount == 0) {
                    deleteour(name);
                    mdb.decreaseAmount(dueamount, getamount);
                    return 2;
                } else if (getamount + amount < 0) {
                    return 3;
                } else {
                    pstmt2.setDouble(1, getamount + amount);
                    pstmt2.setString(2, name);
                    pstmt2.executeUpdate();
                    System.out.println("vendour due updated");

                    mdb.decreaseAmount(dueamount, -1 * amount);

                    return 4;
                }
            } else {
                if (amount < 0) {
                    System.err.println("You have entered -ve value for new vendour ");
                    System.err.println("So vendour due not enterd");
                    
                    return 6;
                } else if (amount > 0){
                    pstmt.setString(1, name);
                    pstmt.setDouble(2, amount);
                    pstmt.executeUpdate();
                    mdb.increaseAmount(dueamount, amount);
                    System.out.println("vendour name,due added");
                }
            }

        } catch (SQLException e) {
            logger.error("An error occurred while inserting the vendour due.", e);
        }
        return 5;
    }

    public int inssertemp(String name, double amount) {

        String dueamount = "totalempdues";

        String sql = "INSERT INTO " + Constants.empDue + "(empname,empdue) VALUES(?,?)";

        String sql1 = "SELECT  * "
                + "FROM " + Constants.empDue + " WHERE empname = ?";

        String sql2 = "UPDATE " + Constants.empDue + " SET empdue = ?"
                + "WHERE empname = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql); PreparedStatement pstmt1 = conn.prepareStatement(sql1); PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {
            pstmt1.setString(1, name);

            ResultSet rs = pstmt1.executeQuery();
            String getname = null;
            double getamount = 0;

            while (rs.next()) {
                getname = rs.getString("empname");
                getamount = rs.getDouble("empdue");
            }
            if (name.equals(getname)) {
                if (amount > 0) {
                    pstmt2.setDouble(1, amount+getamount);
                    pstmt2.setString(2, name);
                    pstmt2.executeUpdate();
                    System.out.println("customer due updated");
                    
                    mdb.increaseAmount(dueamount, amount);
                    
                    return 1;
                } else if ( getamount + amount == 0) {
                    deleteemp(name);
                    mdb.decreaseAmount(dueamount, getamount);
                    return 2;
                } else if (getamount + amount < 0) {
                    return 3;
                } else {
                    pstmt2.setDouble(1, getamount + amount);
                    pstmt2.setString(2, name);
                    pstmt2.executeUpdate();
                    System.out.println("customer due updated");

                    mdb.decreaseAmount(dueamount, -1 * amount);

                    return 4;
                }
            } else {
                if (amount < 0) {
                    System.err.println("Ypu have entered -ve value for new employee ");
                    System.err.println("So employee due not enterd");
                } else if(amount>0) {
                    pstmt.setString(1, name);
                    pstmt.setDouble(2, amount);
                    pstmt.executeUpdate();
                    mdb.increaseAmount(dueamount, amount);
                    System.out.println("employee name added");
                    return 6;
                }
            }
        } catch (SQLException e) {
            logger.error("An error occurred while inserting the employee due.", e);
        }
        return 5;
    }

    public double displaycs(String name) {
        String sql = "SELECT  * "
                + "FROM " + Constants.csDue + " WHERE customer = ?";

        double price = 0;
        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);

            ResultSet rs = pstmt.executeQuery();

            String getname = null;
            while (rs.next()) {
                getname = rs.getString("customer");
                price = rs.getDouble("csdue");
            }
            if (getname == null) {
                System.out.println("Customer with mentioned name has no due ");
                price = 0;
            } else {
                System.out.println("coustumer " + getname + " due is " + price);
            }
        } catch (SQLException e) {
            logger.error("An error occurred while displaying customer due", e);
        }
        return price;
    }

    public void displayour(String name) {
        String sql = "SELECT  * "
                + "FROM " + Constants.vendourDue + " WHERE name = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);

            ResultSet rs = pstmt.executeQuery();

            double price = 0;
            String getname = null;
            while (rs.next()) {
                getname = rs.getString("name");
                price = rs.getDouble("ourdue");
            }
            if (getname == null) {
                System.out.println("vendpur with mentioned name has no due ");
            } else {
                System.out.println("Vendour " + getname + " due is " + price);
            }
        } catch (SQLException e) {
            logger.error("An error occurred while displaying vendour due", e);
        }
    }

    public void displayemp(String name) {
        String sql = "SELECT  * "
                + "FROM " + Constants.empDue + " WHERE empname = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);

            ResultSet rs = pstmt.executeQuery();

            double price = 0;
            String getname = null;
            while (rs.next()) {
                getname = rs.getString("empname");
                price = rs.getDouble("empdue");
            }
            if (getname == null) {
                System.out.println("Employee with mentioned name has no due ");
            } else {
                System.out.println("employee " + getname + " due is " + price);
            }
        } catch (SQLException e) {
            logger.error("An error occurred while displaying vendour due", e);
        }
    }

    public void displayCsdueList() {
        String sql = "SELECT * FROM " + Constants.csDue + " ORDER BY customer";

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("CUSTOMER NAME        DUE AMOUNT");

            while (rs.next()) {
                String name = rs.getString("customer");
                double dueamount = rs.getDouble("csdue");

                System.out.printf("%-20s", name);
                System.out.printf("     %.2f", dueamount);
                System.out.println();
            }

        } catch (SQLException e) {
            logger.error("An error occurred while displaying customer due list", e);
        }
    }

    public void displayOurdueList() {
        String sql = "SELECT * FROM " + Constants.vendourDue + " ORDER BY name";

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("VENDOR NAME        DUE AMOUNT");

            while (rs.next()) {
                String name = rs.getString("name");
                double dueamount = rs.getDouble("ourdue");

                System.out.printf("%-20s", name);
                System.out.printf("     %.2f", dueamount);
                System.out.println();
            }

        } catch (SQLException e) {
            logger.error("An error occurred while displaying vendour due list", e);
        }
    }

    public void displayempdueList() {
        String sql = "SELECT * FROM " + Constants.empDue + " ORDER BY empname";

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("EMPLOYEE NAME        DUE AMOUNT");

            while (rs.next()) {
                String name = rs.getString("empname");
                double dueamount = rs.getDouble("empdue");

                System.out.printf("%-20s", name);
                System.out.printf("     %.2f", dueamount);
                System.out.println();
            }

        } catch (SQLException e) {
            logger.error("An error occurred while displaying employee due list", e);
        }
    }

    public void deletecs(String name) {
        String sql = "SELECT *"
                + " FROM " + Constants.csDue + " WHERE customer =?";

        String sql1 = "DELETE FROM " + Constants.csDue + " WHERE customer = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt1 = conn.prepareStatement(sql1); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            String getname = null;
            while (rs.next()) {
                getname = rs.getString("customer");
            }
            if (getname == null) {
                System.out.println("customer with entered name is not found");
            } else {
                pstmt1.setString(1, name);
                pstmt1.executeUpdate();

                System.out.println("customer due become 0 so customer name deleted from customer list");
            }
        } catch (SQLException e) {
            logger.error("An error occurred while deleting customer name in due", e);
        }
    }

    public void deleteour(String name) {
        String sql = "SELECT *"
                + " FROM " + Constants.vendourDue + " WHERE name =?";

        String sql1 = "DELETE FROM " + Constants.vendourDue + " WHERE name = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt1 = conn.prepareStatement(sql1); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            String getname = null;
            while (rs.next()) {
                getname = rs.getString("name");
            }
            if (getname == null) {
                System.out.println("vendour with entered name is not found");
            } else {
                pstmt1.setString(1, name);
                pstmt1.executeUpdate();

                System.out.println("vendour due become 0 so vendour name deleted from customer list");
            }
        } catch (SQLException e) {
            logger.error("An error occurred while deleting vendour name in due", e);
        }
    }

    public void deleteemp(String name) {
        String sql = "SELECT *"
                + " FROM " + Constants.empDue + " WHERE empname =?";

        String sql1 = "DELETE FROM " + Constants.empDue + " WHERE empname = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt1 = conn.prepareStatement(sql1); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            String getname = null;
            while (rs.next()) {
                getname = rs.getString("empname");
            }
            if (getname == null) {
                System.out.println("employee name with entered name is not found");
            } else {
                pstmt1.setString(1, name);
                pstmt1.executeUpdate();

                System.out.println("employee due become 0 so employee name deleted from employee list");
            }
        } catch (SQLException e) {
            logger.error("An error occurred while deleting employee name in due", e);
        }
    }

}
