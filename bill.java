package projectbms;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class totalAmount {
    
     Logger logger = LogManager.getLogger(bill.class);
     
    public static void createNewtable() {
        String url = Constants.productDB;
        String sql = "CREATE TABLE IF NOT EXISTS " + Constants.totalAmount + " (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text NOT NULL,\n"
                + "	totalAmt real \n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            
            Logger logger = LogManager.getLogger(bill.class);
            logger.info("Table created");
        } catch (SQLException e) {
            Logger logger = LogManager.getLogger(bill.class);
            logger.error("An error occurred while creating the table.", e);
        }
    }

    private Connection connect() {
        // SQLite connection string
        //String url = "jdbc:sqlite:V://projectBms/bmsList1.db";
        String url = Constants.productDB;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
             logger.error("Failed to connect to the product DB in class bill.", e);
        }
        return conn;
    }

    public int insert(String name, double amount) {

        String sql = "INSERT INTO " + Constants.totalAmount + "(name,totalAmt)"
                + "VALUES(?,?)";

        String sql1 = "UPDATE " + Constants.totalAmount + " SET totalAmt = ?  "
                + "WHERE name = ?";

        String sql2 = "SELECT  * "
                + "FROM " + Constants.totalAmount + " WHERE name = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql); PreparedStatement pstmt1 = conn.prepareStatement(sql1); PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {

            pstmt2.setString(1, name);
            ResultSet rs = pstmt2.executeQuery();

            String csname = null;
            double getAmount = 0;

            while (rs.next()) {
                csname = rs.getString("name");
                getAmount = rs.getDouble("totalAmt");
            }
            if (name.equals(csname)) {
                pstmt1.setString(2, name);
                pstmt1.setDouble(1, getAmount + amount);
                pstmt1.executeUpdate();
                System.out.println("total amount updated");
            } else {
                pstmt.setString(1, name);
                pstmt.setDouble(2, amount);
                pstmt.executeUpdate();
                System.out.println("total amount craeted");
            }

        } catch (SQLException e) {
            logger.error("An error occurred while inserting the product in "
                    + "total Amount in class bill.", e);
        }
        return 0;
    }

    public double display(String name) {

        String sql = "SELECT  * "
                + "FROM " + Constants.totalAmount + " WHERE name = ?";

        double totalAmount = 0;
        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                totalAmount = rs.getDouble("totalAmt");
            }
        } catch (SQLException e) {
           logger.error("An error occurred while displaying total amount in "
                    + "total Amount in class bill.", e);
        }
        return totalAmount;
    }
    
public void delete() {
    String sql = "DELETE FROM " + Constants.totalAmount + ";";

    try (Connection conn = this.connect();
         Statement stmt = conn.createStatement()) {
        int rowsAffected = stmt.executeUpdate(sql);
        if (rowsAffected > 0) {
            System.out.println("Product(s) Deleted");
        }
    } catch (SQLException e) {
        logger.error("An error occurred while deleting totalAmount in " +
                "total Amount in class bill.", e);
    }
}

}

class BillData {
    
    Logger logger = LogManager.getLogger(BillData.class);

    public static void createNewTable() {

        // SQLite connection string
        // String url = "jdbc:sqlite:V://projectBms/bmsList1.db";
        String url = Constants.productDB;
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS " + Constants.bill + " (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text NOT NULL,\n"
                + "	quantity real, \n"
                + "     Mrp real, \n"
                + "     totMrp real, \n"
                + "     net real, \n"
                + "     dis real, \n"
                + "     netDis real, \n"
                + "     taxP real, \n"
                + "     taxA real, \n"
                + "     price real, \n"
                + "     totPrice real \n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            
            Logger logger = LogManager.getLogger(BillData.class);
            logger.info("Table created");
        } catch (SQLException e) {
            Logger logger = LogManager.getLogger(BillData.class);
            logger.error("An error occurred while creating the table.", e);
        }
    }

    private Connection connect() {
        // SQLite connection string
        //String url = "jdbc:sqlite:V://projectBms/bmsList1.db";
        String url = Constants.productDB;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            logger.error("Failed to connect to the product db in class BillData.", e);
        }
        return conn;
    }

    public int insertIntoBill(String name, double qnt, double price) {

        String sql = "INSERT INTO " + Constants.bill + "(name,quantity,Mrp,totMrp,net,dis,netDis,taxP,taxA,price,totPrice) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?)";

        String sql1 = "SELECT  * "
                + "FROM " + Constants.bill + " WHERE name = ?";

        String sql2 = "SELECT  * "
                + "FROM " + Constants.ProductTable + " WHERE stockname = ?";

        String sql3 = "UPDATE " + Constants.bill + " SET name = ?, quantity = ?, Mrp = ?, totMrp = ?, net = ?, dis = ?, netDis = ?, taxP = ?, taxA = ?, price = ?, totPrice = ? "
                + "WHERE name = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql); PreparedStatement pstmt1 = conn.prepareStatement(sql1); PreparedStatement pstmt2 = conn.prepareStatement(sql2); PreparedStatement pstmt3 = conn.prepareStatement(sql3)) {
            pstmt1.setString(1, name);
            pstmt2.setString(1, name);

            ResultSet rs2 = pstmt2.executeQuery();
            double getMrp = 0;
            double getTax = 0;

            while (rs2.next()) {
                getMrp = rs2.getDouble("mrp");
                getTax = rs2.getDouble("tax");
            }

            double totPrice = price * qnt;
            double totMrp = getMrp * qnt;
            // double net = (price - (price * getTax / 100)) * qnt;
            double net = (totMrp / (100 + getTax)) * 100;

            double disPer = ((totMrp - totPrice) / totMrp) * 100;
            double dis = (net * disPer) / 100;
            double netDis = (net - dis);
            double taxAmt = (netDis * getTax / 100);
            double finalPrice = netDis + taxAmt;
            double finalUnitPrice = finalPrice / qnt;

            String stockName = null;
            double billQnt = 0;
            double billMrp = 0;
            double billTotalMrp = 0;
            double billNet = 0;
            double billDis = 0;
            double billNetDis = 0;
            double billTaxAmt = 0;
            double billPrice = 0;
            double billTotPrice = 0;

            ResultSet rs = pstmt1.executeQuery();

            while (rs.next()) {
                stockName = rs.getString("name");
                billQnt = rs.getDouble("quantity");
                billMrp = rs.getDouble("Mrp");
                billTotalMrp = rs.getDouble("totMrp");
                billNet = rs.getDouble("net");
                billDis = rs.getDouble("dis");
                billNetDis = rs.getDouble("netDis");
                billTaxAmt = rs.getDouble("taxA");
                billPrice = rs.getDouble("price");
                billTotPrice = rs.getDouble("totPrice");
            }
            if (name.equals(stockName)) {

                pstmt3.setString(1, name);
                pstmt3.setDouble(2, qnt + billQnt);
                pstmt3.setDouble(3, getMrp + billMrp);
                pstmt3.setDouble(4, totMrp + billTotalMrp);
                pstmt3.setDouble(5, net + billNet);
                pstmt3.setDouble(6, dis + billDis);
                pstmt3.setDouble(7, netDis + billNetDis);
                pstmt3.setDouble(8, getTax);
                pstmt3.setDouble(9, taxAmt + billTaxAmt);
                pstmt3.setDouble(10, price + billPrice);
                pstmt3.setDouble(11, totPrice + billTotPrice);
                pstmt3.setString(12, name);
                pstmt3.executeUpdate();
                logger.info(" Product alredady exist");

            } else {
                pstmt.setString(1, name);
                pstmt.setDouble(2, qnt);
                pstmt.setDouble(3, getMrp);
                pstmt.setDouble(4, totMrp);
                pstmt.setDouble(5, net);
                pstmt.setDouble(6, dis);
                pstmt.setDouble(7, netDis);
                pstmt.setDouble(8, getTax);
                pstmt.setDouble(9, taxAmt);
                pstmt.setDouble(10, finalUnitPrice);
                pstmt.setDouble(11, finalPrice);
                pstmt.executeUpdate();

                logger.info("Product added in inster bill db");

                return 2;
            }
        } catch (SQLException e) {
             logger.error("An error occurred while inserting the product in class billData.", e);
        }
        return 0;
    }

    public void display() {
        String sql = "SELECT * FROM " + Constants.bill + " ORDER BY name";

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("STOCKNAME                QUANTITY   MRP      TOTMRP        NET         DIS          NETDIS       TAXP       TAXA      PRICE     TOTPRICE  ");

            while (rs.next()) {
                String stockname = rs.getString("name");
                double qnt = rs.getDouble("quantity");
                double mrp = rs.getDouble("mrp");
                double totMrp = rs.getDouble("totMrp");
                double net = rs.getDouble("net");
                double dis = rs.getDouble("dis");
                double netDis = rs.getDouble("netDis");
                double taxP = rs.getDouble("taxP");
                double taxA = rs.getDouble("taxA");
                double price = rs.getDouble("price");
                double totPrice = rs.getDouble("totPrice");

                System.out.printf("%-20s", stockname);
                System.out.printf("     %.2f", qnt);
                System.out.printf("       %.2f", mrp);
                System.out.printf("     %.2f", totMrp);
                System.out.printf("        %.2f", net);
                System.out.printf("        %.2f", dis);
                System.out.printf("        %.2f", netDis);
                System.out.printf("        %.2f", taxP);
                System.out.printf("        %.2f", taxA);
                System.out.printf("        %.2f", price);
                System.out.printf("        %.2f", totPrice);
                System.out.println();
            }
        } catch (SQLException e) {
            logger.error("An error occurred while displaying the product in class billData.", e);
        }
    }

    public void totals() {
        String sql = "SELECT * FROM " + Constants.bill + " ORDER BY name";

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            int count = 0;
            double totalQnt = 0;
            double totalMrp = 0;
            double totalNet = 0;
            double totalDis = 0;
            double totalTaxAmount = 0;
            double totalPrice = 0;

            while (rs.next()) {

                double qnt = rs.getDouble("quantity");
                double totMrp = rs.getDouble("totMrp");
                double net = rs.getDouble("net");
                double dis = rs.getDouble("dis");
                double taxA = rs.getDouble("taxA");
                double totPrice = rs.getDouble("totPrice");

                count++;
                totalQnt = qnt + totalQnt;
                totalMrp = totMrp + totalMrp;
                totalNet = net + totalNet;
               totalDis = dis + totalDis;
                totalTaxAmount = taxA + totalTaxAmount;
                totalPrice = totPrice + totalPrice;

            }
           
             
            System.out.printf("  %d", count);
            System.out.printf("     %.2f", totalQnt);
            System.out.printf("       %.2f", totalMrp);
            System.out.printf("     %.2f", totalNet);
            System.out.printf("        %.2f", totalDis);
            System.out.printf("        %.2f", totalTaxAmount);
            System.out.printf("        %.2f", totalPrice);
            System.out.println();

        } catch (SQLException e) {
           logger.error("An error occurred while displaying totals in class billData.", e);
        }
    }

    public void taxes() {
        String sql = "SELECT * FROM " + Constants.bill + " ORDER BY name";

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            TreeMap<Double, Double> taxPercentMap = new TreeMap<>();
            Map<Double, Double> netAmountMap = new HashMap<>();

            while (rs.next()) {
                double taxPercent = rs.getDouble("taxP");
                double taxAmount = rs.getDouble("taxA");
                double net = rs.getDouble("netDis");

                taxPercentMap.put(taxPercent, taxPercentMap.getOrDefault(taxPercent, 0.0) + taxAmount);
                netAmountMap.put(taxPercent, netAmountMap.getOrDefault(taxPercent, 0.0) + net);
            }

            for (Map.Entry<Double, Double> entry : taxPercentMap.entrySet()) {
                double taxPercent = entry.getKey();
                double taxAmt = entry.getValue();
                double netAmt = netAmountMap.get(taxPercent);

                System.out.println("CGST Tax percentage: " + taxPercent / 2);
                System.out.printf(" CGST Total tax: %.2f\n", taxAmt / 2);
                System.out.printf(" CGST Total net amount: %.2f\n", netAmt);

                System.out.println("SGST Tax percentage: " + taxPercent / 2);
                System.out.printf(" SGST Total tax: %.2f\n", taxAmt / 2);
                System.out.printf(" SGST Total net amount: %.2f\n", netAmt);
            }

        } catch (SQLException e) {
           logger.error("An error occurred while dispalying taxes in class billData.", e);
        }
    }

    public String NumberToWords(int number) {

        String[] units = {
            "", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten",
            "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"
        };

        String[] tens = {
            "", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"
        };

        if (number == 0) {
            return "zero";
        }

        if (number < 0) {
            return "minus " + NumberToWords(Math.abs(number));
        }

        String words = "";

        if ((number / 1000000) > 0) {
            words += NumberToWords(number / 1000000) + " million ";
            number %= 1000000;
        }

        if ((number / 1000) > 0) {
            words += NumberToWords(number / 1000) + " thousand ";
            number %= 1000;
        }

        if ((number / 100) > 0) {
            words += NumberToWords(number / 100) + " hundred ";
            number %= 100;
        }

        if (number > 0) {
            if (number < 20) {
                words += units[number];
            } else {
                words += tens[number / 10] + " " + units[number % 10];
            }
        }

        return words.trim();
    }

    public String nameDis() {
        String sql = "SELECT * FROM " + Constants.bill + " ORDER BY name";
        String stockname = null;
        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                stockname = rs.getString("name");
            }
        } catch (SQLException e) {
           logger.error("An error occurred while displaying name in class billData.", e);
        }
        return stockname;
    }

    public int delete() {

        String sql = "DELETE FROM " + Constants.bill;

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            logger.info("Product Deleted");

        } catch (SQLException e) {
           logger.error("An error occurred while deleting products in class billData.", e);
        }
        return 3;
    }

    public int count() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM " + Constants.bill;
        
        try (Connection conn = this.connect(); Statement stmt1 = conn.createStatement(); ResultSet rs1 = stmt1.executeQuery(sql)) {

            rs1.next();

            count = rs1.getInt(1);

        } catch (SQLException e) {
            logger.error("An error occurred while counting the products in class billData.", e);
        }
        return count;
    }
}
    

public class bill {

    public static void main(String args[]) {
        totalAmount ta = new totalAmount();
        BillData bd = new BillData();
        // ta.createNewtable();
        //ta.insert("jaya", 100);
        //ta.delete();
        //  bd.insertWithOutPrice("cream", 10);
        //  bd.insert("jam", 2, 10);

        //  bd.delete();
        bd.display();
        //  bd.totals();
        //  bd.taxes();

        //String words= bd.NumberToWords(355555);
        //  System.out.print(words);
    }

    public void owner() {
        System.out.print("Enter Bussines name :");
        System.out.print("Enter Owner name :");
        System.out.print("Enter owner mobile number :");
        System.out.print("License ");
        System.out.print("GST number");
        System.out.print("Address");
        System.out.print("");
    }

    public void seller() {
        System.out.print("Date");
        System.out.print("Invoice Number");
        System.out.print("Coustmer name");
        System.out.print("payment mode");
        System.out.print("Due amount");
        System.out.print("amount in words");
    }
}
