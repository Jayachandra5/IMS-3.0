package projectbms;

import java.sql.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class empDatabase {

    manage mdb = new manage();

    org.apache.logging.log4j.Logger logger = LogManager.getLogger(empDatabase.class);

    public static void createNewDatabase() {

        //String url = "jdbc:sqlite:V:/projectBms/" + fileName;
        String url = Constants.employeeDB;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();

                Logger logger = LogManager.getLogger(empDatabase.class);
                logger.info("The driver name is {}", meta.getDriverName());
                logger.info("A new database has been created.");
            }

        } catch (SQLException e) {
            Logger logger = LogManager.getLogger(empDatabase.class);
            logger.error("An error occurred while creating the database.", e);
        }
    }

    public static void createNewTable() {
        // SQLite connection string
        //String url = "jdbc:sqlite:V://projectBms/bmsemplist1.db";

        String url = Constants.employeeDB;
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS " + Constants.employeeTable + " (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	empname text NOT NULL,\n"
                + "	salary real \n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);

            Logger logger = LogManager.getLogger(empDatabase.class);
            logger.info("Table created");
        } catch (SQLException e) {
            Logger logger = LogManager.getLogger(empDatabase.class);
            logger.error("An error occurred while creating the table.", e);
        }
    }

    private Connection connect() {
        // SQLite connection string
        // String url = "jdbc:sqlite:V://projectBms/bmsemplist1.db";
        String url = Constants.employeeDB;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            logger.error("Failed to connect to employee database.", e);
        }
        return conn;
    }

    public int insert(String empname, double salary) {
        String sql = "INSERT INTO " + Constants.employeeTable + "(empname,salary) VALUES(?,?)";

        String sql1 = "SELECT  empname "
                + "FROM " + Constants.employeeTable + " WHERE empname = ?";

        String sql2 = "INSERT INTO " + Constants.employeeAttendanceTable + "(empname,wd) VALUES(?,?) ";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql); PreparedStatement pstmt1 = conn.prepareStatement(sql1); PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {

            pstmt1.setString(1, empname);

            ResultSet rs = pstmt1.executeQuery();
            String name = null;
            while (rs.next()) {
                name = rs.getString("empname");
            }
            if (empname.equals(name)) {
                System.out.println(" employee already exists ");
                return 1;
            } else {
                pstmt.setString(1, empname);
                pstmt.setDouble(2, salary);

                pstmt.executeUpdate();

                double wd = 0;

                pstmt2.setString(1, empname);
                pstmt2.setDouble(2, wd);

                pstmt2.executeUpdate();

                System.out.println("Employee Inserted.");

            }
        } catch (SQLException e) {
            logger.error("An error occurred while inserting employee list.", e);
        }
        return 2;
    }

    public void display() {
        String sql = "SELECT id,empname,salary FROM " + Constants.employeeTable + " ORDER BY empname";
        String sql1 = "SELECT COUNT(*) FROM " + Constants.employeeTable + "";

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql); Statement stmt1 = conn.createStatement(); ResultSet rs1 = stmt1.executeQuery(sql1)) {

            rs1.next();

            int count = rs1.getInt(1);

            System.out.println("TOTAL NUMBER OF EMPLOYEES :" + count);
            System.out.println("\n");

            System.out.println("EMPLOYEENAME         DAILYSALARY");

            while (rs.next()) {
                String empname = rs.getString("empname");
                double salary = rs.getDouble("salary");

                System.out.printf("%-20s", empname);
                System.out.printf("     %.2f", salary);
                System.out.println("\n\n");

            }
        } catch (SQLException e) {
            logger.error("An error occurred while disaplying employee list.", e);
        }
    }

    public int delete(String empname) {
        String sql = "SELECT empname"
                + " FROM " + Constants.employeeTable + " WHERE empname =?";

        String sql1 = "DELETE FROM " + Constants.employeeTable + " WHERE empname = ?";

        String sql2 = "DELETE FROM " + Constants.employeeAttendanceTable + " WHERE empname =?";

        try (Connection conn = this.connect(); PreparedStatement pstmt1 = conn.prepareStatement(sql1); PreparedStatement pstmt = conn.prepareStatement(sql); PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {

            pstmt.setString(1, empname);
            ResultSet rs = pstmt.executeQuery();

            String name = null;
            while (rs.next()) {
                name = rs.getString("empname");
            }
            if (name == null) {
                System.out.println("Employee with entered name is not found");
                return 1;
            } else {
                pstmt1.setString(1, empname);
                pstmt1.executeUpdate();

                pstmt2.setString(1, empname);
                pstmt2.executeUpdate();
                System.out.println("employee deleted");
            }
        } catch (SQLException e) {
            logger.error("An error occurred while deleting employee.", e);
        }
        return 2;
    }

    public void calSal() {
        String sql = "SELECT empname,wd FROM " + Constants.employeeAttendanceTable + " ORDER BY empname";

        String sql1 = "SELECT * "
                + "FROM " + Constants.employeeTable + " WHERE empname=?";

        String sql2 = "UPDATE " + Constants.employeeAttendanceTable + " SET wd = ?  "
                + "WHERE empname = ?";

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql); PreparedStatement pstmt1 = conn.prepareStatement(sql1); PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {

            String empname = null;
            double wd = 0;
            double sal = 0;
            double salary = 0;
            while (rs.next()) {
                empname = rs.getString("empname");
                wd = rs.getDouble("wd");
                pstmt1.setString(1, empname);
                ResultSet rs1 = pstmt1.executeQuery();

                while (rs1.next()) {
                    sal = rs1.getDouble("salary");
                    salary = wd * sal;

                    System.out.println(empname + " working days =" + wd + "  salary =" + salary);

                    //insreses expenses for giving salarys
                    String expences = "expenses";
                    mdb.increaseAmount(expences, salary);

                    // makes working days of the employee 0 after givimg salary  
                    double empwd = 0;
                    pstmt2.setDouble(1, empwd);
                    pstmt2.setString(2, empname);
                    pstmt2.executeUpdate();
                }
            }

        } catch (SQLException e) {
            logger.error("An error occurred while caluclating employee salary", e);
        }
    }

    public void makeWd0(String empname) {
        String sql2 = "UPDATE " + Constants.employeeAttendanceTable + " SET wd = ?  "
                + "WHERE empname = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {
            double empwd = 0;
            pstmt2.setDouble(1, empwd);
            pstmt2.setString(2, empname);
            pstmt2.executeUpdate();

        } catch (SQLException e) {
            logger.error("An error occurred while makeing working days to 0.", e);

        }
    }

    public int updateSal(String empname, double sal) {
        String sql = "SELECT  * "
                + "FROM " + Constants.employeeTable + " WHERE empname = ?";

        String sql2 = "UPDATE " + Constants.employeeTable + " SET salary = ?  "
                + "WHERE empname = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql); PreparedStatement pstmt1 = conn.prepareStatement(sql2)) {
            pstmt.setString(1, empname);

            ResultSet rs = pstmt.executeQuery();

            double sal1 = 0;
            String name = null;
            while (rs.next()) {
                name = rs.getString("empname");
                sal1 = rs.getDouble("salary");
            }

            if (name == null) {
                System.out.println("There is no employee with that name ");
                return 1;
            } else {
                pstmt1.setDouble(1, sal);
                pstmt1.setString(2, empname);
            }
            pstmt1.executeUpdate();

            System.out.println(empname + " Before salary was " + sal1);
            System.out.println("Now " + empname + " salary was updated to " + sal);

        } catch (SQLException e) {
            logger.error("An error occurred while updateing employee salary.", e);
        }
        return 2;
    }
}
