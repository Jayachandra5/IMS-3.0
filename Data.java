package projectbms;

import java.sql.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Data extends parentform {

    TextField searchBar = new TextField();

    private TableView<Vendour> vendourTable = new TableView<Vendour>();
    ObservableList<Vendour> vendourData;

    private TableView<Cust> customerTable = new TableView<Cust>();
    ObservableList<Cust> customerData;

    FontWeight fw = FontWeight.BOLD;
    FontPosture fp = FontPosture.REGULAR;

    Label number;

    Logger logger = LogManager.getLogger(Data.class);

    public void vendourTable(Stage stage) {

        VBox vb2 = super.addnew(stage, "product");

        searchBar.setPrefSize(325, 25);
        searchBar.setStyle("-fx-prompt-text-fill:slategray;-fx-text-fill:black;");
        searchBar.setFont(Font.font("Calibri", fw, fp, 13));
        searchBar.setPromptText("🔍 Enter Name Of The Person");

        Label label = new Label("VENDOR LIST");
        label.setFont(Font.font("Calibri", fw, fp, 20));

        Label count = new Label("Total Number of Vendors :");
        count.setFont(Font.font("Calibri", fw, fp, 20));

        number = new Label();
        number.setFont(Font.font("Calibri", fw, fp, 20));

        displayVendourTable();

        vendourTable.setEditable(true);
        vendourTable.setPrefHeight(380);
        vendourTable.setPrefWidth(400);
        vendourTable.getStylesheets().add("/projectbms/mycss.css");

        TableColumn Col1 = new TableColumn("Name");
        Col1.setMinWidth(200);
        Col1.setCellValueFactory(
                new PropertyValueFactory<Vendour, String>("firstName"));

        TableColumn Col2 = new TableColumn("Amount Purchased");
        Col2.setMinWidth(185);
        Col2.setCellValueFactory(
                new PropertyValueFactory<Vendour, String>("lastName"));

        vendourTable.getColumns().addAll(Col1, Col2);

        HBox hb1 = new HBox();
        hb1.setSpacing(5);
        hb1.setPadding(new Insets(10, 10, 10, 110));
        hb1.getChildren().addAll(label);

        HBox hb = new HBox();
        hb.setSpacing(5);
        hb.setPadding(new Insets(0, 10, 0, 40));
        hb.getChildren().addAll(count, number);

        VBox vb1 = new VBox();
        vb1.getChildren().addAll(hb1, hb, searchBar);
        vb1.setSpacing(15);
        vb1.setPadding(new Insets(10, 10, 10, 10));

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 170, 0, 170));
        vbox.getChildren().addAll(vb1, vendourTable);

        vendourTable.setItems(vendourData);

        vb2.getChildren().addAll(vbox);
        vb2.setSpacing(10);
        vb2.setPadding(new Insets(10, 10, 5, 10));

        searchBar.requestFocus();

        FilteredList<Vendour> filterCsDue = new FilteredList<>(vendourData, b -> true);

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {

            filterCsDue.setPredicate(Vendour -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (Vendour.getFirstName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<Vendour> sortedCsData = new SortedList<>(filterCsDue);

        sortedCsData.comparatorProperty().bind(vendourTable.comparatorProperty());

        vendourTable.setItems(sortedCsData);

        stage.show();
    }

    public static class Vendour {

        private final SimpleStringProperty firstName;
        private final SimpleStringProperty lastName;

        private Vendour(String fName, String lName) {
            this.firstName = new SimpleStringProperty(fName);
            this.lastName = new SimpleStringProperty(lName);
        }

        public String getFirstName() {
            return firstName.get();
        }

        public void setFirstName(String fName) {
            firstName.set(fName);
        }

        public String getLastName() {
            return lastName.get();
        }

        public void setLastName(String fName) {
            lastName.set(fName);
        }
    }

    public void displayVendourTable() {
        String sql = "SELECT * FROM " + Constants.vendourTable + " ORDER BY vName";
        String sql1 = "SELECT COUNT(*) FROM " + Constants.vendourTable;

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql); Statement stmt1 = conn.createStatement(); ResultSet rs1 = stmt1.executeQuery(sql1)) {

            number.setText(String.valueOf(rs1.getInt(1)));

            vendourData = FXCollections.observableArrayList();

            while (rs.next()) {
                String vendourName = rs.getString("vName");
                double amount = rs.getDouble("amount");

                vendourData.add(new Vendour(vendourName, String.valueOf(amount)));

            }
        } catch (SQLException e) {
            logger.error("An error occurred while Displaying Vendour table.", e);
        }
    }

    public static void createNewVendourTable() {
        // SQLite connection string
        //String url = "jdbc:sqlite:V://projectBms/bmsList1.db";

        String url = Constants.mangeDB;
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS " + Constants.vendourTable + "(\n"
                + "	id integer PRIMARY KEY,\n"
                + "	vName text NOT NULL,\n"
                + "	amount real \n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);

            Logger logger = LogManager.getLogger(Data.class);
            logger.info("Table created");
        } catch (SQLException e) {
            Logger logger = LogManager.getLogger(Data.class);
            logger.error("An error occurred while Creating vendour.", e);
        }
    }

    public int insertVendour(String vendourName, double amount) {

        String sql = "INSERT INTO " + Constants.vendourTable + " (vName,amount) VALUES(?,?)";

        String sql1 = "SELECT  vName "
                + "FROM " + Constants.vendourTable + " WHERE vName = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql); PreparedStatement pstmt1 = conn.prepareStatement(sql1)) {

            pstmt1.setString(1, vendourName);

            ResultSet rs = pstmt1.executeQuery();
            String name = null;
            while (rs.next()) {
                name = rs.getString("vName");
            }
            if (vendourName.equals(name)) {
                updateVendour(vendourName, amount);
                return 1;
            } else {
                pstmt.setString(1, vendourName);
                pstmt.setDouble(2, amount);

                pstmt.executeUpdate();
                // System.out.println("Vendour added");
                return 2;
            }
        } catch (SQLException e) {
            logger.error("An error occurred while Inserting Vendour.", e);
        }
        return 0;
    }

    public int updateVendour(String vendourName, double amount) {

        String sql = "SELECT  * "
                + "FROM " + Constants.vendourTable + " WHERE vName = ?";

        String sql2 = "UPDATE " + Constants.vendourTable + " SET amount = ?  "
                + "WHERE vName = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql); PreparedStatement pstmt1 = conn.prepareStatement(sql2)) {

            pstmt.setString(1, vendourName);

            ResultSet rs = pstmt.executeQuery();

            String pname = null;
            double prc = 0;
            while (rs.next()) {
                pname = rs.getString("vName");
                prc = rs.getDouble("amount");
            }
            if (pname == null) {
                //  System.out.println("Vendour not avalible please add the Vendour first");
                insertVendour(vendourName, amount);
                return 1;
            } else {
                pstmt1.setDouble(1, amount = prc + amount);
                pstmt1.setString(2, vendourName);

                pstmt1.executeUpdate();

                // System.out.println("amount added ");
            }
        } catch (SQLException e) {
            logger.error("An error occurred while updating vendour.", e);
        }
        return 2;
    }

    private Connection connect() {
        // SQLite connection string
        // String url = "jdbc:sqlite:V://projectBms/bmsList1.db";
        String url = Constants.mangeDB;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            logger.error("An error occurred while connecting to mangeDB.", e);
        }
        return conn;
    }

    /*                                   CUSTOMER TABLE                                      */
    public void customerTable(Stage stage) {

        VBox vb2 = super.addnew(stage, "product");

        searchBar.setPrefSize(325, 25);
        searchBar.setStyle("-fx-prompt-text-fill:slategray;-fx-text-fill:black;");
        searchBar.setFont(Font.font("Calibri", fw, fp, 13));
        searchBar.setPromptText("🔍 Enter Name Of The Person");

        Label label = new Label("CUSTOMER LIST");
        label.setFont(Font.font("Calibri", fw, fp, 20));

        Label count = new Label("Total Number of Customers :");
        count.setFont(Font.font("Calibri", fw, fp, 20));

        number = new Label();
        number.setFont(Font.font("Calibri", fw, fp, 20));

        customerTable.setEditable(true);
        customerTable.setPrefHeight(380);
        customerTable.setPrefWidth(500);
        customerTable.getStylesheets().add("/projectbms/mycss.css");

        displayCustomerTable();

        TableColumn Col1 = new TableColumn("Name");
        Col1.setMinWidth(140);
        Col1.setCellValueFactory(
                new PropertyValueFactory<Cust, String>("firstName"));

        TableColumn Col2 = new TableColumn("Amount ");
        Col2.setMinWidth(100);
        Col2.setCellValueFactory(
                new PropertyValueFactory<Cust, String>("lastName"));

        TableColumn Col3 = new TableColumn("Mobile Number");
        Col3.setMinWidth(100);
        Col3.setCellValueFactory(
                new PropertyValueFactory<Cust, String>("email"));
        
        TableColumn Col4 = new TableColumn("Mail ID");
        Col4.setMinWidth(155);
        Col4.setCellValueFactory(
                new PropertyValueFactory<Cust, String>("mail"));

        customerTable.getColumns().addAll(Col1, Col2, Col3,Col4);

        HBox hb1 = new HBox();
        hb1.setSpacing(5);
        hb1.setPadding(new Insets(10, 10, 10, 160));
        hb1.getChildren().addAll(label);

        HBox hb = new HBox();
        hb.setSpacing(5);
        hb.setPadding(new Insets(0, 10, 0, 100));
        hb.getChildren().addAll(count, number);

        VBox vb1 = new VBox();
        vb1.getChildren().addAll(hb1, hb, searchBar);
        vb1.setSpacing(15);
        vb1.setPadding(new Insets(10, 10, 10, 10));

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 120, 0, 120));
        vbox.getChildren().addAll(vb1, customerTable);

        customerTable.setItems(customerData);

        vb2.getChildren().addAll(vbox);
        vb2.setSpacing(10);
        vb2.setPadding(new Insets(10, 10, 5, 10));

        searchBar.requestFocus();
        FilteredList<Cust> filterCsDue = new FilteredList<>(customerData, b -> true);

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {

            filterCsDue.setPredicate(Cust -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (Cust.getFirstName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<Cust> sortedCsData = new SortedList<>(filterCsDue);

        sortedCsData.comparatorProperty().bind(customerTable.comparatorProperty());

        customerTable.setItems(sortedCsData);

        stage.show();
    }

    public static class Cust {

        private final SimpleStringProperty firstName;
        private final SimpleStringProperty lastName;
        private final SimpleStringProperty email;
        private final SimpleStringProperty mail;
        

        private Cust(String fName, String lName, String email,String mail) {
            this.firstName = new SimpleStringProperty(fName);
            this.lastName = new SimpleStringProperty(lName);
            this.email = new SimpleStringProperty(email);
            this.mail = new SimpleStringProperty(mail);
        }

        public String getFirstName() {
            return firstName.get();
        }

        public void setFirstName(String fName) {
            firstName.set(fName);
        }

        public String getLastName() {
            return lastName.get();
        }

        public void setLastName(String fName) {
            lastName.set(fName);
        }

        public String getEmail() {
            return email.get();
        }

        public void setEmail(String fName) {
            email.set(fName);
        }
        
         public String getMail() {
            return mail.get();
        }

        public void setMail(String fName) {
            mail.set(fName);
        }

    }

    public void displayCustomerTable() {

        String sql = "SELECT * FROM " + Constants.customerTable + " ORDER BY cName";
        String sql1 = "SELECT COUNT(*) FROM " + Constants.customerTable;

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql); Statement stmt1 = conn.createStatement(); ResultSet rs1 = stmt1.executeQuery(sql1)) {

            number.setText(String.valueOf(rs1.getInt(1)));

            customerData = FXCollections.observableArrayList();
            while (rs.next()) {
                String vendourName = rs.getString("cName");
                double amount = rs.getDouble("amount");
                String mobile = rs.getString("mobile");
                String mail = rs.getString("mail");

                customerData.add(new Cust(vendourName, String.valueOf(amount), mobile,mail));
            }
        } catch (SQLException e) {
            logger.error("An error occurred while displaying customer table", e);
        }
    }

    public static void createNewCustomerTable() {
        // SQLite connection string
        // String url = "jdbc:sqlite:V://projectBms/bmsList1.db";
        String url = Constants.mangeDB;

        // SQL statement for creating a new table with a timestamp column
        String sql = "CREATE TABLE IF NOT EXISTS " + Constants.customerTable + "(\n"
                + "    id INTEGER PRIMARY KEY,\n"
                + "    cName TEXT NOT NULL,\n"
                + "    amount REAL,\n"
                + "    mobile TEXT NOT NULL,\n"
                + "    mail TEXT NOT NULL,\n"
                + "    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP\n" // Added timestamp column
                + ");";

        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            Logger logger = LogManager.getLogger(Data.class);
            logger.info("customer table created");

        } catch (SQLException e) {
            Logger logger = LogManager.getLogger(Data.class);
            logger.error("An error occurred while Creating customer table.", e);
        }
    }

    public int insertCustomer(String vendourName, double amount, String mobile, String mail) {

        String sql = "INSERT INTO " + Constants.customerTable + " (cName,amount,mobile,mail) VALUES(?,?,?,?)";

        String sql1 = "SELECT  cName "
                + "FROM " + Constants.customerTable + " WHERE cName = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql); PreparedStatement pstmt1 = conn.prepareStatement(sql1)) {
            pstmt1.setString(1, vendourName);

            ResultSet rs = pstmt1.executeQuery();
            String name = null;
            while (rs.next()) {
                name = rs.getString("cName");
            }
            if (vendourName.equals(name)) {
                //    System.out.println("Customer already exits");
                updateCustomer(vendourName, amount, mobile, mail);
                return 1;
            } else {
                pstmt.setString(1, vendourName);
                pstmt.setDouble(2, amount);
                pstmt.setString(3, mobile);
                pstmt.setString(4, mail);

                pstmt.executeUpdate();
                logger.info("Customer added");
                return 2;
            }
        } catch (SQLException e) {
            logger.error("An error occurred while instering customer", e);
        }
        return 0;
    }

    public int updateCustomer(String vendorName, double amount, String mobile, String mail) {
        String sql = "SELECT * FROM " + Constants.customerTable + " WHERE cName = ?";
        String sql2 = "UPDATE " + Constants.customerTable + " SET amount = ?, mobile = ?, mail=?, timestamp = CURRENT_TIMESTAMP WHERE cName = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql); PreparedStatement pstmt1 = conn.prepareStatement(sql2)) {

            pstmt.setString(1, vendorName);
            ResultSet rs = pstmt.executeQuery();

            String pname = null;
            double prc = 0;

            while (rs.next()) {
                pname = rs.getString("cName");
                prc = rs.getDouble("amount");
            }

            if (pname == null) {
                logger.info("Customer not available. Please add the vendor first.");
                return 1;
            } else {
                pstmt1.setDouble(1, amount = prc + amount);
                pstmt1.setString(2, mobile);
                pstmt1.setString(3, mail);
                pstmt1.setString(4, vendorName);

                pstmt1.executeUpdate();
                logger.info("Amount added.");
            }
        } catch (SQLException e) {
            logger.error("An error occurred while updating customer amount", e);
        }
        return 2;
    }

    public String getLastUpdatedCustomerName() {
        String sql = "SELECT cName FROM " + Constants.customerTable + " ORDER BY timestamp DESC LIMIT 1";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("cName");
            }
        } catch (SQLException e) {
            logger.error("An error occurred while retivering last updated customer ", e);
        }
        return null; // or throw an exception indicating no records found
    }

}
