package projectbms;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

public class Reports extends parentform {

    private TableView<purchase> purchaseTable = new TableView<purchase>();
    ObservableList<purchase> purchaseDate;

    private TableView<Sales> salesTable = new TableView<Sales>();
    ObservableList<Sales> salesData;

    private TableView<Expenses> expensesTable = new TableView<Expenses>();
    ObservableList<Expenses> expensesData;

    private TableView<Report> reportTable = new TableView<Report>();
    ObservableList<Report> reportData;

    FontWeight fw = FontWeight.BOLD;
    FontPosture fp = FontPosture.REGULAR;

    TextField searchBar = new TextField();
    Purchase buy = new Purchase();
    SalesData sell = new SalesData();
    expenses expenses = new expenses();

    org.apache.logging.log4j.Logger logger = LogManager.getLogger(Reports.class);

    //  Label number; 
    Label numberOf;

    public void purchaseTable(Stage stage) {

        VBox vb2 = super.addnew(stage, "product");

        Label space = new Label();
        space.setPrefSize(200, 25);
        space.setStyle("-fx-background-color:DODGERBLUE;");

        searchBar.setPrefSize(570, 25);
        searchBar.setStyle("-fx-prompt-text-fill:slategray;-fx-text-fill:black;");
        searchBar.setFont(Font.font("Calibri", fw, fp, 13));
        searchBar.setPromptText("🔍 Enter Product Name Or Date (YYYY-MM-DD) Or Vendor Name");

        Label label = new Label("PURCHASE LIST");
        label.setFont(Font.font("Calibri", fw, fp, 20));

        Label count = new Label("Total Number of Purchases :");
        count.setFont(Font.font("Calibri", fw, fp, 20));

        numberOf = new Label();
        numberOf.setFont(Font.font("Calibri", fw, fp, 20));

        displayPurchase();

        purchaseTable.setEditable(true);
        purchaseTable.setPrefHeight(380);
        purchaseTable.setPrefWidth(600);
        purchaseTable.getStylesheets().add("/projectbms/mycss.css");

        TableColumn Col1 = new TableColumn("Product Name");
        Col1.setMinWidth(140);
        Col1.setCellValueFactory(
                new PropertyValueFactory<purchase, String>("firstName"));

        TableColumn Col2 = new TableColumn("Qnt Purchased");
        Col2.setMinWidth(80);
        Col2.setCellValueFactory(
                new PropertyValueFactory<purchase, String>("lastName"));

        TableColumn Col3 = new TableColumn("Amount");
        Col3.setMinWidth(70);
        Col3.setCellValueFactory(
                new PropertyValueFactory<purchase, String>("email"));

        TableColumn Col4 = new TableColumn("Qnt Available");
        Col4.setMinWidth(70);
        Col4.setCellValueFactory(
                new PropertyValueFactory<purchase, String>("Price"));

        TableColumn Col5 = new TableColumn("Date");
        Col5.setMinWidth(85);
        Col5.setCellValueFactory(
                new PropertyValueFactory<purchase, String>("Tax"));

        TableColumn Col6 = new TableColumn("Vendor");
        Col6.setMinWidth(90);
        Col6.setCellValueFactory(
                new PropertyValueFactory<purchase, String>("Vname"));

        purchaseTable.getColumns().addAll(Col1, Col2, Col3, Col4, Col5, Col6);

        HBox head = new HBox();
        head.setSpacing(5);
        head.setPadding(new Insets(10, 10, 10, 200));
        head.getChildren().addAll(label);

        HBox hb = new HBox();
        hb.setSpacing(5);
        hb.setPadding(new Insets(0, 10, 0, 130));
        hb.getChildren().addAll(count, numberOf);

        HBox hb1 = new HBox();
        hb1.setSpacing(5);
        hb1.setPadding(new Insets(0, 0, 0, 0));
        hb1.getChildren().addAll(searchBar);

        HBox hb3 = new HBox();
        hb3.setSpacing(5);
        hb3.setPadding(new Insets(0, 0, 0, 0));
        hb3.getChildren().addAll(hb1);

        VBox vb1 = new VBox();
        vb1.getChildren().addAll(head, hb, hb3);
        vb1.setSpacing(15);
        vb1.setPadding(new Insets(10, 10, 10, 10));

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 75, 0, 72));
        vbox.getChildren().addAll(vb1, purchaseTable);

        purchaseTable.setItems(purchaseDate);

        vb2.getChildren().addAll(vbox);
        vb2.setSpacing(10);
        vb2.setPadding(new Insets(10, 10, 5, 10));

        searchBar.requestFocus();

        FilteredList<purchase> filterCsDue = new FilteredList<>(purchaseDate, b -> true);

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {

            filterCsDue.setPredicate(CsDue -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (CsDue.getFirstName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (CsDue.getTax().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (CsDue.getVname().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<purchase> sortedCsData = new SortedList<>(filterCsDue);

        sortedCsData.comparatorProperty().bind(purchaseTable.comparatorProperty());

        purchaseTable.setItems(sortedCsData);

        stage.show();
    }

    public static class purchase {

        private final SimpleStringProperty firstName;
        private final SimpleStringProperty lastName;
        private final SimpleStringProperty email;
        private final SimpleStringProperty Price;
        private final SimpleStringProperty Tax;
        private final SimpleStringProperty Vname;

        private purchase(String fName, String lName, String email, String price, String tax, String Vname) {
            this.firstName = new SimpleStringProperty(fName);
            this.lastName = new SimpleStringProperty(lName);
            this.email = new SimpleStringProperty(email);
            this.Price = new SimpleStringProperty(price);
            this.Tax = new SimpleStringProperty(tax);
            this.Vname = new SimpleStringProperty(Vname);
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

        public String getPrice() {
            return Price.get();
        }

        public void setPrice(String fName) {
            Price.set(fName);
        }

        public String getTax() {
            return Tax.get();
        }

        public void setTax(String fName) {
            Tax.set(fName);
        }

        public String getVname() {
            return Vname.get();
        }

        public void setVname(String fName) {
            Vname.set(fName);
        }
    }

    public void displayPurchase() {
        String sql = "SELECT * FROM " + Constants.purchaseTable + " ORDER BY date";
        String sql1 = "SELECT COUNT(*) FROM " + Constants.purchaseTable;

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql); Statement stmt1 = conn.createStatement(); ResultSet rs1 = stmt1.executeQuery(sql1)) {

            numberOf.setText(String.valueOf(rs1.getInt(1)));

            purchaseDate = FXCollections.observableArrayList();

            while (rs.next()) {
                String stockname = rs.getString("stockName");
                double qnt = rs.getDouble("qnt");
                double amount = rs.getDouble("amount");
                double qntAvl1 = rs.getDouble("qntAvl");
                String date = rs.getString("date");
                String Vname = rs.getString("vendourName");

                purchaseDate.add(new purchase(stockname, String.valueOf(qnt), String.valueOf(amount),
                        String.valueOf(qntAvl1), date, Vname));
            }

        } catch (SQLException e) {
            logger.error("An error occurred in display function of Purchse table.", e);
        }
    }

    private Connection connect() {
        // SQLite connection string
        String url = Constants.productDB;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            logger.error("An error occurred while connecting to product DB in Reports class.", e);
        }
        return conn;
    }

    Label number;

    public void SalesTable(Stage stage) {

        VBox vb2 = super.addnew(stage, "product");

        searchBar.setPrefSize(480, 25);
        searchBar.setStyle("-fx-prompt-text-fill:slategray;-fx-text-fill:black;");
        searchBar.setFont(Font.font("Calibri", fw, fp, 13));
        searchBar.setPromptText("🔍 Enter Product Name Or Date (YYYY-MM-DD) Or Customer Name");

        Label label = new Label("SALES LIST");
        label.setFont(Font.font("Calibri", fw, fp, 20));

        Label count = new Label("Total Number of Sales :");
        count.setFont(Font.font("Calibri", fw, fp, 20));

        number = new Label();
        number.setFont(Font.font("Calibri", fw, fp, 20));

        displaySales();
        salesTable.setEditable(true);
        salesTable.setPrefHeight(380);
        salesTable.setPrefWidth(630);
        salesTable.getStylesheets().add("/projectbms/mycss.css");

        TableColumn Col1 = new TableColumn("Name");
        Col1.setMinWidth(135);
        Col1.setCellValueFactory(
                new PropertyValueFactory<Sales, String>("firstName"));

        TableColumn Col2 = new TableColumn("Qnt Sold");
        Col2.setMinWidth(90);
        Col2.setCellValueFactory(
                new PropertyValueFactory<Sales, String>("lastName"));

        TableColumn stock = new TableColumn("Stock");
        stock.setMinWidth(70);
        stock.setCellValueFactory(
                new PropertyValueFactory<Sales, String>("stock"));

        TableColumn Col3 = new TableColumn("Sales");
        Col3.setMinWidth(70);
        Col3.setCellValueFactory(
                new PropertyValueFactory<Sales, String>("email"));

        TableColumn Col4 = new TableColumn("Profit");
        Col4.setMinWidth(70);
        Col4.setCellValueFactory(
                new PropertyValueFactory<Sales, String>("Price"));

        TableColumn Col5 = new TableColumn("Date");
        Col5.setMinWidth(80);
        Col5.setCellValueFactory(
                new PropertyValueFactory<Sales, String>("Tax"));

        TableColumn Col6 = new TableColumn("Customer");
        Col6.setMinWidth(90);
        Col6.setCellValueFactory(
                new PropertyValueFactory<Sales, String>("Customer"));

        salesTable.getColumns().addAll(Col1, Col2, stock, Col3, Col4, Col5, Col6);

        HBox hb1 = new HBox();
        hb1.setSpacing(5);
        hb1.setPadding(new Insets(0, 10, 0, 230));
        hb1.getChildren().addAll(label);

        HBox hb = new HBox();
        hb.setSpacing(5);
        hb.setPadding(new Insets(0, 10, 0, 190));
        hb.getChildren().addAll(count, number);

        VBox vb1 = new VBox();
        vb1.getChildren().addAll(hb1, hb, searchBar);
        vb1.setSpacing(15);
        vb1.setPadding(new Insets(10, 10, 10, 10));

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 55, 0, 62));
        vbox.getChildren().addAll(vb1, salesTable);

        salesTable.setItems(salesData);

        vb2.getChildren().addAll(vbox);
        vb2.setSpacing(10);
        vb2.setPadding(new Insets(10, 10, 5, 10));

        searchBar.requestFocus();

        FilteredList<Sales> filterCsDue = new FilteredList<>(salesData, b -> true);

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {

            filterCsDue.setPredicate(CsDue -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (CsDue.getFirstName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (CsDue.getTax().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (CsDue.getCustomer().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<Sales> sortedCsData = new SortedList<>(filterCsDue);

        sortedCsData.comparatorProperty().bind(salesTable.comparatorProperty());

        salesTable.setItems(sortedCsData);

        stage.show();
    }

    public static class Sales {

        private final SimpleStringProperty firstName;
        private final SimpleStringProperty lastName;
        private final SimpleStringProperty email;
        private final SimpleStringProperty Price;
        private final SimpleStringProperty Tax;
        private final SimpleStringProperty Customer;
        private final SimpleStringProperty stock;

        private Sales(String fName, String lName, String stock, String email, String price, String tax, String Customer) {
            this.firstName = new SimpleStringProperty(fName);
            this.lastName = new SimpleStringProperty(lName);
            this.stock = new SimpleStringProperty(stock);
            this.email = new SimpleStringProperty(email);
            this.Price = new SimpleStringProperty(price);
            this.Tax = new SimpleStringProperty(tax);
            this.Customer = new SimpleStringProperty(Customer);
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

        public String getStock() {
            return stock.get();
        }

        public void setStock(String fName) {
            stock.set(fName);
        }

        public String getEmail() {
            return email.get();
        }

        public void setEmail(String fName) {
            email.set(fName);
        }

        public String getPrice() {
            return Price.get();
        }

        public void setPrice(String fName) {
            Price.set(fName);
        }

        public String getTax() {
            return Tax.get();
        }

        public void setTax(String fName) {
            Tax.set(fName);
        }

        public String getCustomer() {
            return Customer.get();
        }

        public void setCustomer(String fName) {
            Customer.set(fName);
        }
    }

    public void displaySales() {
        String sql = "SELECT * FROM " + Constants.salesTable + " ORDER BY date";
        String sql1 = "SELECT COUNT(*) FROM " + Constants.salesTable;

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql); Statement stmt1 = conn.createStatement(); ResultSet rs1 = stmt1.executeQuery(sql1)) {

            number.setText(String.valueOf(rs1.getInt(1)));

            salesData = FXCollections.observableArrayList();

            while (rs.next()) {
                String stockname = rs.getString("stockName");
                double qnt = rs.getDouble("qnt");
                double stock = rs.getDouble("stock");
                double amount = rs.getDouble("amount");
                double profit = rs.getDouble("profit");
                String date = rs.getString("date");
                String custName = rs.getString("customer");

                salesData.add(new Sales(stockname, String.valueOf(qnt), String.valueOf(stock),
                        String.valueOf(amount), String.valueOf(profit), date, custName));
            }
        } catch (SQLException e) {
            logger.error("An error occurred in display function of sales table.", e);
        }
    }

    public void expensesTable(Stage stage) {
        VBox vb2 = super.addnew(stage, "product");

        searchBar.setPrefSize(380, 25);
        searchBar.setStyle("-fx-prompt-text-fill:slategray;-fx-text-fill:black;");
        searchBar.setFont(Font.font("Calibri", fw, fp, 13));
        searchBar.setPromptText("🔍 Enter Expenses Name Or Date (YYYY-MM-DD)");

        Label label = new Label("EXPENSES LIST");
        label.setFont(Font.font("Calibri", fw, fp, 20));

        Label count = new Label("Total Number of Expenses :");
        count.setFont(Font.font("Calibri", fw, fp, 20));

        number = new Label("10");
        number.setFont(Font.font("Calibri", fw, fp, 20));

        expensesTable.setEditable(true);
        expensesTable.setPrefHeight(380);
        expensesTable.setPrefWidth(450);
        expensesTable.getStylesheets().add("/projectbms/mycss.css");

        displayExpenses();

        TableColumn Col1 = new TableColumn("Purpose");
        Col1.setMinWidth(150);
        Col1.setCellValueFactory(
                new PropertyValueFactory<Expenses, String>("firstName"));

        TableColumn Col2 = new TableColumn("Amount Paid");
        Col2.setMinWidth(150);
        Col2.setCellValueFactory(
                new PropertyValueFactory<Expenses, String>("lastName"));

        TableColumn stock = new TableColumn("Date");
        stock.setMinWidth(135);
        stock.setCellValueFactory(
                new PropertyValueFactory<Expenses, String>("email"));

        expensesTable.getColumns().addAll(Col1, Col2, stock);

        HBox hb1 = new HBox();
        hb1.setSpacing(5);
        hb1.setPadding(new Insets(10, 10, 10, 140));
        hb1.getChildren().addAll(label);

        HBox hb = new HBox();
        hb.setSpacing(5);
        hb.setPadding(new Insets(0, 10, 0, 80));
        hb.getChildren().addAll(count, number);

        VBox vb1 = new VBox();
        vb1.getChildren().addAll(hb1, hb, searchBar);
        vb1.setSpacing(15);
        vb1.setPadding(new Insets(10, 10, 10, 10));

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 145, 0, 145));
        vbox.getChildren().addAll(vb1, expensesTable);

        expensesTable.setItems(expensesData);

        vb2.getChildren().addAll(vbox);
        vb2.setSpacing(10);
        vb2.setPadding(new Insets(10, 10, 5, 10));

        searchBar.requestFocus();

        FilteredList<Expenses> filterCsDue = new FilteredList<>(expensesData, b -> true);

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {

            filterCsDue.setPredicate(CsDue -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (CsDue.getFirstName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (CsDue.getEmail().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<Expenses> sortedCsData = new SortedList<>(filterCsDue);

        sortedCsData.comparatorProperty().bind(expensesTable.comparatorProperty());

        expensesTable.setItems(sortedCsData);

        stage.show();
    }

    public static class Expenses {

        private final SimpleStringProperty firstName;
        private final SimpleStringProperty lastName;
        private final SimpleStringProperty email;

        private Expenses(String fName, String lName, String email) {
            this.firstName = new SimpleStringProperty(fName);
            this.lastName = new SimpleStringProperty(lName);
            this.email = new SimpleStringProperty(email);

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

    }

    public void displayExpenses() {

        String sql = "SELECT * FROM " + Constants.expensesTable + " ORDER BY date";
        String sql1 = "SELECT COUNT(*) FROM " + Constants.expensesTable;

        try (Connection conn = this.connectMange(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql); Statement stmt1 = conn.createStatement(); ResultSet rs1 = stmt1.executeQuery(sql1)) {

            rs1.next();
            number.setText(String.valueOf(rs1.getInt(1)));

            expensesData = FXCollections.observableArrayList();
            while (rs.next()) {
                String name = rs.getString("name");
                double amount = rs.getDouble("amount");
                String date = rs.getString("date");

                expensesData.add(new Expenses(name, String.valueOf(amount), date));

            }
        } catch (SQLException e) {
            logger.error("An error occurred in display function of exepenses table.", e);
        }
    }

    private Connection connectMange() {
        // SQLite connection string
        String url = Constants.mangeDB;
        //String url = "jdbc:sqlite:V://projectBms/managedb1.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            logger.error("An error occurred while connecting to mangeDB in Reports class", e);
        }
        return conn;
    }

    Button salesButton, expensesButton, profitButton, statsButton, downloadButton;
    private DatePicker checkInDatePicker;
    private DatePicker checkOutDatePicker;

    public void report(Stage stage) {
        VBox vb2 = super.addnew(stage, "product");

        Label label = new Label("REPORT ");
        label.setFont(Font.font("Calibri", fw, fp, 24));

        Label text = new Label();
        text.setFont(Font.font("Calibri", fw, fp, 18));

        Label downloded = new Label();
        downloded.setFont(Font.font("Calibri", fw, fp, 18));

        Label start = new Label("First Date:-");
        start.setFont(Font.font("Calibri", fw, fp, 18));

        Label end = new Label("  To   End Date:-");
        end.setFont(Font.font("Calibri", fw, fp, 18));

        Button b1 = new Button("Done");
        b1.setFont(Font.font("Calibri", fw, fp, 16));

        salesButton = new Button("Sales");
        salesButton.setFont(Font.font("Calibri", fw, fp, 20));

        expensesButton = new Button("Expenses");
        expensesButton.setFont(Font.font("Calibri", fw, fp, 20));

        profitButton = new Button("Profit");
        profitButton.setFont(Font.font("Calibri", fw, fp, 20));

        statsButton = new Button("Statics");
        statsButton.setFont(Font.font("Calibri", fw, fp, 20));

        downloadButton = new Button("Download");
        downloadButton.setFont(Font.font("Calibri", fw, fp, 20));

        TextField tf1 = new TextField();
        tf1.setPrefSize(150, 25);
        tf1.setStyle("-fx-prompt-text-fill:slategray;-fx-text-fill:black;");
        tf1.setFont(Font.font("Calibri", fw, fp, 13));
        tf1.setPromptText("Enter in (yyyy-mm-dd)");

        TextField tf2 = new TextField();
        tf2.setPrefSize(150, 25);
        tf2.setStyle("-fx-prompt-text-fill:slategray;-fx-text-fill:black;");
        tf2.setFont(Font.font("Calibri", fw, fp, 13));
        tf2.setPromptText("Enter in (yyyy-mm-dd)");

        reportTable.setPrefHeight(380);
        reportTable.setPrefWidth(500);
        reportTable.getStylesheets().add("/projectbms/mycss.css");

        TableColumn Col1 = new TableColumn("Product Name");
        Col1.setMinWidth(140);
        Col1.setCellValueFactory(
                new PropertyValueFactory<Report, String>("firstName"));

        TableColumn Col2 = new TableColumn("Qnt Available");
        Col2.setMinWidth(90);
        Col2.setCellValueFactory(
                new PropertyValueFactory<Report, String>("lastName"));

        TableColumn empty = new TableColumn();
        empty.setMinWidth(25);

        TableColumn Col3 = new TableColumn("Opening");
        Col3.setMinWidth(77);
        Col3.setCellValueFactory(
                new PropertyValueFactory<Report, String>("email"));

        TableColumn Col4 = new TableColumn("Purchased");
        Col4.setMinWidth(77);
        Col4.setCellValueFactory(
                new PropertyValueFactory<Report, String>("Price"));

        TableColumn Col5 = new TableColumn("Sold");
        Col5.setMinWidth(77);
        Col5.setCellValueFactory(
                new PropertyValueFactory<Report, String>("Tax"));

        Col1.setCellFactory(col -> createStringCell());
        Col2.setCellFactory(col -> createStringCell());
        Col3.setCellFactory(col -> createStringCell());
        Col4.setCellFactory(col -> createStringCell());
        Col5.setCellFactory(col -> createStringCell());
        empty.setCellFactory(col -> createStringCell());

        reportTable.getColumns().addAll(Col1, Col2, empty, Col3, Col4, Col5);

        checkInDatePicker = new DatePicker();
        checkOutDatePicker = new DatePicker();
        checkInDatePicker.setValue(LocalDate.now());
        checkInDatePicker.setPrefSize(10, 10);
        checkOutDatePicker.setPrefSize(10, 10);
        checkInDatePicker.setEditable(false);
        checkOutDatePicker.setEditable(false);

        StackPane root = new StackPane(tf1, checkInDatePicker);
        root.setAlignment(checkInDatePicker, Pos.CENTER_RIGHT);
        VBox datePick1 = new VBox(root);

        StackPane root2 = new StackPane(tf2, checkOutDatePicker);
        root2.setAlignment(checkOutDatePicker, Pos.CENTER_RIGHT);
        VBox datePick2 = new VBox(root2);

        HBox hb = new HBox();
        hb.setSpacing(5);
        hb.setPadding(new Insets(0, 10, 0, 10));
        hb.getChildren().addAll(start, datePick1, end, datePick2);

        HBox hb1 = new HBox();
        hb1.setSpacing(5);
        hb1.setPadding(new Insets(5, 10, 10, 225));
        hb1.getChildren().addAll(label);

        HBox hb2 = new HBox();
        hb2.setSpacing(5);
        hb2.setPadding(new Insets(0, 10, 0, 250));
        hb2.getChildren().addAll(b1);

        HBox hb3 = new HBox();
        hb3.setSpacing(5);
        hb3.setPadding(new Insets(-5, 10, -5, 100));
        hb3.getChildren().addAll(text);

        VBox vb1 = new VBox();
        vb1.getChildren().addAll(hb1, hb, hb2, hb3);
        vb1.setSpacing(15);
        vb1.setPadding(new Insets(10, 10, 10, 10));

        HBox hb11 = new HBox();
        hb11.setSpacing(5);
        hb11.setPadding(new Insets(5, 5, 5, 5));
        hb11.getChildren().addAll(salesButton);

        HBox hb12 = new HBox();
        hb12.setSpacing(5);
        hb12.setPadding(new Insets(5, 5, 5, 5));
        hb12.getChildren().addAll(profitButton);

        HBox hb14 = new HBox();
        hb14.setSpacing(5);
        hb14.setPadding(new Insets(5, 5, 5, 5));
        hb14.getChildren().addAll(expensesButton);

        HBox hb15 = new HBox();
        hb15.setSpacing(5);
        hb15.setPadding(new Insets(5, 5, 5, 5));
        hb15.getChildren().addAll(statsButton);

        HBox hb16 = new HBox();
        hb16.setSpacing(5);
        hb16.setPadding(new Insets(5, 10, 5, 10));
        hb16.getChildren().addAll(downloadButton);

        HBox hb13 = new HBox();
        hb13.setSpacing(5);
        hb13.setPadding(new Insets(5, 30, 5, 10));
        hb13.getChildren().addAll(hb11, hb12, hb16, hb14, hb15);

        HBox hb17 = new HBox();
        hb17.setSpacing(5);
        hb17.setPadding(new Insets(5, 80, 5, 10));
        hb17.getChildren().addAll(downloded);

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(0, 100, 0, 90));
        vbox.getChildren().addAll(vb1, reportTable, hb13, hb17);

        reportTable.setItems(reportData);

        vb2.getChildren().addAll(vbox);
        vb2.setSpacing(10);
        vb2.setPadding(new Insets(10, 10, 5, 10));

        tf1.requestFocus();

        tf1.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                tf2.requestFocus();
            }
        });

        tf2.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                b1.fire();
            }
        });

        tf1.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                if (!tf1.getText().matches("^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$")) {
                    text.setStyle("-fx-text-fill:red;");
                    text.setText("Please Enter Date In yyyy-mm-dd Format Only");
                    tf1.requestFocus();
                } else {
                    text.setText("");
                }
            }
        });

        tf2.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                if (!tf2.getText().matches("^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$")) {
                    text.setStyle("-fx-text-fill:red;");
                    text.setText("Please Enter Date In yyyy-mm-dd Format Only");
                    tf2.requestFocus();
                } else {
                    text.setText("");
                }
            }
        });

        b1.setOnAction((ActionEvent e) -> {

            displayReport(tf1.getText(), tf2.getText());

            double totalSales = sell.totalSales(tf1.getText(), tf2.getText());
            double stockAmount = sell.stockAmountSold(tf1.getText(), tf2.getText());
            double totalPurchase = buy.totalPurchase(tf1.getText(), tf2.getText());
            double totalProfit = sell.totalProfit(tf1.getText(), tf2.getText());
            double totalExpenses = expenses.totalExpenses(tf1.getText(), tf2.getText());
            double totalRevnue = totalProfit - totalExpenses;

            Report emt = new Report("", "", "", "", "");
            Report tSales = new Report("Total Sales", "", String.format("%.1f", totalSales), "", "");
            Report sAmount = new Report("Total Amount Sold ", "", String.format("%.1f", stockAmount), "", "");
            Report tPurchase = new Report("Total Purchase ", "", String.format("%.1f", totalPurchase), "", "");
            Report tProfit = new Report("Total Profit", "", String.format("%.1f", totalProfit), "", "");
            Report tExpenses = new Report("Total Expenses ", "", String.format("%.1f", totalExpenses), "", "");
            Report tRevnue = new Report("Total Revenue ", "", String.format("%.1f", totalRevnue), "", "");

            reportData.add(emt);
            reportData.add(tSales);
            reportData.add(sAmount);
            reportData.add(tPurchase);
            reportData.add(tProfit);
            reportData.add(tExpenses);
            reportData.add(tRevnue);
        });

        salesButton.setOnAction((ActionEvent e) -> {

            dataVisualisation vis = new dataVisualisation();

            vis.sales(stage, tf1.getText(), tf2.getText());
        });

        expensesButton.setOnAction((ActionEvent e) -> {

            dataVisualisation vis = new dataVisualisation();

            vis.expeses(stage, tf1.getText(), tf2.getText());
        });

        profitButton.setOnAction((ActionEvent e) -> {

            dataVisualisation vis = new dataVisualisation();

            vis.Profit(stage, tf1.getText(), tf2.getText());
        });

        statsButton.setOnAction((ActionEvent e) -> {

            dataVisualisation vis = new dataVisualisation();

            vis.Stats(stage, tf1.getText(), tf2.getText());
        });

        downloadButton.setOnAction((ActionEvent e) -> {
            try {
                String fileName = tf1.getText() + " to " + tf2.getText() + " report.pdf";
                downloadReportPdf(stage, fileName);
                downloded.setStyle("-fx-text-fill:black;");
                downloded.setText("Report Downloaded Successfully");

            } catch (IOException ex) {
                Logger.getLogger(Reports.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        // Set date format
        String pattern = "yyyy-MM-dd";
        checkInDatePicker.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
        checkOutDatePicker.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });

        final Callback<DatePicker, DateCell> dayCellFactory
                = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(
                                checkInDatePicker.getValue().plusDays(1))) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                        long p = ChronoUnit.DAYS.between(
                                checkInDatePicker.getValue(), item
                        );
                        setTooltip(new Tooltip(
                                "You are selecting report for " + p + " days")
                        );
                    }
                };
            }
        };
        checkOutDatePicker.setDayCellFactory(dayCellFactory);
        checkOutDatePicker.setValue(checkInDatePicker.getValue().plusDays(1));

        checkInDatePicker.setPromptText(pattern.toLowerCase());
        checkOutDatePicker.setPromptText(pattern.toLowerCase());
        checkInDatePicker.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
        checkOutDatePicker.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });

        tf1.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                checkInDatePicker.show();
            }
        });

        // When the user selects a date, update the text field
        checkInDatePicker.setOnAction(event -> {
            tf1.setText(checkInDatePicker.getValue().toString());
            checkInDatePicker.hide();
        });

        tf2.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                checkOutDatePicker.show();
            }
        });

        // When the user selects a date, update the text field
        checkOutDatePicker.setOnAction(event -> {
            tf2.setText(checkOutDatePicker.getValue().toString());
            checkOutDatePicker.hide();
        });

        stage.show();
    }

    private TableCell<Report, String> createStringCell() {
        return new TableCell<Report, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(item);
                    if (getIndex() == getTableView().getItems().size() - 8) {
                        setStyle("-fx-background-color: lightblue;");
                    }
                }
            }
        };
    }

    public void reportBack(Stage stage, String date1, String date2) {

        VBox vb2 = super.addnew(stage, "product");

        Label label = new Label("REPORT ");
        label.setFont(Font.font("Calibri", fw, fp, 24));

        Label text = new Label();
        text.setFont(Font.font("Calibri", fw, fp, 18));

        Label start = new Label("First Date:-");
        start.setFont(Font.font("Calibri", fw, fp, 18));

        Label end = new Label("  To   End Date:-");
        end.setFont(Font.font("Calibri", fw, fp, 18));

        Button b1 = new Button("Done");
        b1.setFont(Font.font("Calibri", fw, fp, 16));

        salesButton = new Button("Sales");
        salesButton.setFont(Font.font("Calibri", fw, fp, 20));

        expensesButton = new Button("Expenses");
        expensesButton.setFont(Font.font("Calibri", fw, fp, 20));

        profitButton = new Button("Profit");
        profitButton.setFont(Font.font("Calibri", fw, fp, 20));

        statsButton = new Button("Statics");
        statsButton.setFont(Font.font("Calibri", fw, fp, 20));

        downloadButton = new Button("Download");
        downloadButton.setFont(Font.font("Calibri", fw, fp, 20));

        TextField tf1 = new TextField();
        tf1.setPrefSize(150, 25);
        tf1.setStyle("-fx-prompt-text-fill:slategray;-fx-text-fill:black;");
        tf1.setFont(Font.font("Calibri", fw, fp, 13));
        tf1.setPromptText("Enter in (yyyy-mm-dd)");

        TextField tf2 = new TextField();
        tf2.setPrefSize(150, 25);
        tf2.setStyle("-fx-prompt-text-fill:slategray;-fx-text-fill:black;");
        tf2.setFont(Font.font("Calibri", fw, fp, 13));
        tf2.setPromptText("Enter in (yyyy-mm-dd)");

        tf1.setText(date1);
        tf2.setText(date2);

        reportTable.setPrefHeight(380);
        reportTable.setPrefWidth(500);
        reportTable.getStylesheets().add("/projectbms/mycss.css");

        TableColumn Col1 = new TableColumn("Product Name");
        Col1.setMinWidth(140);
        Col1.setCellValueFactory(
                new PropertyValueFactory<Report, String>("firstName"));

        TableColumn Col2 = new TableColumn("Qnt Available");
        Col2.setMinWidth(90);
        Col2.setCellValueFactory(
                new PropertyValueFactory<Report, String>("lastName"));

        TableColumn empty = new TableColumn();
        empty.setMinWidth(50);

        TableColumn Col3 = new TableColumn("Opening");
        Col3.setMinWidth(77);
        Col3.setCellValueFactory(
                new PropertyValueFactory<Report, String>("email"));

        TableColumn Col4 = new TableColumn("Purchased");
        Col4.setMinWidth(77);
        Col4.setCellValueFactory(
                new PropertyValueFactory<Report, String>("Price"));

        TableColumn Col5 = new TableColumn("Sold");
        Col5.setMinWidth(77);
        Col5.setCellValueFactory(
                new PropertyValueFactory<Report, String>("Tax"));

        Col1.setCellFactory(col -> createStringCell());
        Col2.setCellFactory(col -> createStringCell());
        Col3.setCellFactory(col -> createStringCell());
        Col4.setCellFactory(col -> createStringCell());
        Col5.setCellFactory(col -> createStringCell());
        empty.setCellFactory(col -> createStringCell());

        reportTable.getColumns().addAll(Col1, Col2, empty, Col3, Col4, Col5);

        checkInDatePicker = new DatePicker();
        checkOutDatePicker = new DatePicker();
        checkInDatePicker.setValue(LocalDate.now());
        checkInDatePicker.setPrefSize(10, 10);
        checkOutDatePicker.setPrefSize(10, 10);
        checkInDatePicker.setEditable(false);
        checkOutDatePicker.setEditable(false);

        StackPane root = new StackPane(tf1, checkInDatePicker);
        root.setAlignment(checkInDatePicker, Pos.CENTER_RIGHT);
        VBox datePick1 = new VBox(root);

        StackPane root2 = new StackPane(tf2, checkOutDatePicker);
        root2.setAlignment(checkOutDatePicker, Pos.CENTER_RIGHT);
        VBox datePick2 = new VBox(root2);

        HBox hb = new HBox();
        hb.setSpacing(5);
        hb.setPadding(new Insets(0, 10, 0, 10));
        hb.getChildren().addAll(start, datePick1, end, datePick2);

        HBox hb1 = new HBox();
        hb1.setSpacing(5);
        hb1.setPadding(new Insets(5, 10, 10, 225));
        hb1.getChildren().addAll(label);

        HBox hb2 = new HBox();
        hb2.setSpacing(5);
        hb2.setPadding(new Insets(0, 10, 0, 250));
        hb2.getChildren().addAll(b1);

        HBox hb3 = new HBox();
        hb3.setSpacing(5);
        hb3.setPadding(new Insets(-5, 10, -5, 100));
        hb3.getChildren().addAll(text);

        VBox vb1 = new VBox();
        vb1.getChildren().addAll(hb1, hb, hb2, hb3);
        vb1.setSpacing(15);
        vb1.setPadding(new Insets(10, 10, 10, 10));

        HBox hb11 = new HBox();
        hb11.setSpacing(5);
        hb11.setPadding(new Insets(5, 5, 5, 5));
        hb11.getChildren().addAll(salesButton);

        HBox hb12 = new HBox();
        hb12.setSpacing(5);
        hb12.setPadding(new Insets(5, 5, 5, 5));
        hb12.getChildren().addAll(profitButton);

        HBox hb14 = new HBox();
        hb14.setSpacing(5);
        hb14.setPadding(new Insets(5, 5, 5, 5));
        hb14.getChildren().addAll(expensesButton);

        HBox hb15 = new HBox();
        hb15.setSpacing(5);
        hb15.setPadding(new Insets(5, 5, 5, 5));
        hb15.getChildren().addAll(statsButton);

        HBox hb16 = new HBox();
        hb16.setSpacing(5);
        hb16.setPadding(new Insets(5, 10, 5, 10));
        hb16.getChildren().addAll(downloadButton);

        HBox hb13 = new HBox();
        hb13.setSpacing(5);
        hb13.setPadding(new Insets(5, 30, 5, 10));
        hb13.getChildren().addAll(hb11, hb12, hb16, hb14, hb15);

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(0, 100, 0, 90));
        vbox.getChildren().addAll(vb1, reportTable, hb13);

        reportTable.setItems(reportData);

        vb2.getChildren().addAll(vbox);
        vb2.setSpacing(10);
        vb2.setPadding(new Insets(10, 10, 5, 10));

        tf1.requestFocus();

        tf1.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                tf2.requestFocus();
            }
        });

        tf2.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                b1.fire();
            }
        });

        tf1.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                if (!tf1.getText().matches("^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$")) {
                    text.setStyle("-fx-text-fill:red;");
                    text.setText("Please Enter Date In yyyy-mm-dd Format Only");
                    tf1.requestFocus();
                } else {
                    text.setText("");
                }
            }
        });

        tf2.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                if (!tf2.getText().matches("^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$")) {
                    text.setStyle("-fx-text-fill:red;");
                    text.setText("Please Enter Date In yyyy-mm-dd Format Only");
                    tf2.requestFocus();
                } else {
                    text.setText("");
                }
            }
        });

        displayReport(date1, date2);

        double totalSalesBack = sell.totalSales(tf1.getText(), tf2.getText());
        double stockAmountBack = sell.stockAmountSold(tf1.getText(), tf2.getText());
        double totalPurchaseBack = buy.totalPurchase(tf1.getText(), tf2.getText());
        double totalProfitBack = sell.totalProfit(tf1.getText(), tf2.getText());
        double totalExpensesBack = expenses.totalExpenses(tf1.getText(), tf2.getText());
        double totalRevnueBack = totalProfitBack - totalExpensesBack;

        Report emtBack = new Report("", "", "", "", "");
        Report tSalesBack = new Report("Total Sales", "", String.format("%.1f", totalSalesBack), "", "");
        Report sAmountBack = new Report("Total Amount Sold ", "", String.format("%.1f", stockAmountBack), "", "");
        Report tPurchaseBack = new Report("Total Purchase ", "", String.format("%.1f", totalPurchaseBack), "", "");
        Report tProfitBack = new Report("Total Profit", "", String.format("%.1f", totalProfitBack), "", "");
        Report tExpensesBack = new Report("Total Expenses ", "", String.format("%.1f", totalExpensesBack), "", "");
        Report tRevnueBack = new Report("Total Revenue ", "", String.format("%.1f", totalRevnueBack), "", "");

        reportData.add(emtBack);
        reportData.add(tSalesBack);
        reportData.add(sAmountBack);
        reportData.add(tPurchaseBack);
        reportData.add(tProfitBack);
        reportData.add(tExpensesBack);
        reportData.add(tRevnueBack);

        b1.setOnAction((ActionEvent e) -> {

            displayReport(tf1.getText(), tf2.getText());

            double totalSales = sell.totalSales(tf1.getText(), tf2.getText());
            double stockAmount = sell.stockAmountSold(tf1.getText(), tf2.getText());
            double totalPurchase = buy.totalPurchase(tf1.getText(), tf2.getText());
            double totalProfit = sell.totalProfit(tf1.getText(), tf2.getText());
            double totalExpenses = expenses.totalExpenses(tf1.getText(), tf2.getText());
            double totalRevnue = totalProfit - totalExpenses;

            Report emt = new Report("", "", "", "", "");
            Report tSales = new Report("Total Sales", "", String.valueOf(totalSales), "", "");
            Report sAmount = new Report("Total Amount Sold ", "", String.valueOf(stockAmount), "", "");
            Report tPurchase = new Report("Total Purchase ", "", String.valueOf(totalPurchase), "", "");
            Report tProfit = new Report("Total Profit", "", String.valueOf(totalProfit), "", "");
            Report tExpenses = new Report("Total Expenses ", "", String.valueOf(totalExpenses), "", "");
            Report tRevnue = new Report("Total Revenue ", "", String.valueOf(totalRevnue), "", "");

            reportData.add(emt);
            reportData.add(tSales);
            reportData.add(sAmount);
            reportData.add(tPurchase);
            reportData.add(tProfit);
            reportData.add(tExpenses);
            reportData.add(tRevnue);
        });

        salesButton.setOnAction((ActionEvent e) -> {

            dataVisualisation vis = new dataVisualisation();

            vis.sales(stage, tf1.getText(), tf2.getText());
        });

        expensesButton.setOnAction((ActionEvent e) -> {

            dataVisualisation vis = new dataVisualisation();

            vis.expeses(stage, tf1.getText(), tf2.getText());
        });

        profitButton.setOnAction((ActionEvent e) -> {

            dataVisualisation vis = new dataVisualisation();

            vis.Profit(stage, tf1.getText(), tf2.getText());
        });

        statsButton.setOnAction((ActionEvent e) -> {

            dataVisualisation vis = new dataVisualisation();

            vis.Stats(stage, tf1.getText(), tf2.getText());
        });

        downloadButton.setOnAction((ActionEvent e) -> {
            try {
                String fileName = tf1.getText() + " to " + tf2.getText() + " report.pdf";
                downloadReportPdf(stage, fileName);
                text.setStyle("-fx-text-fill:black;");
                text.setText("Report Downloaded Sussufully");

            } catch (IOException ex) {
                Logger.getLogger(Reports.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        // Set date format
        String pattern = "yyyy-MM-dd";
        checkInDatePicker.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
        checkOutDatePicker.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });

        final Callback<DatePicker, DateCell> dayCellFactory
                = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(
                                checkInDatePicker.getValue().plusDays(1))) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                        long p = ChronoUnit.DAYS.between(
                                checkInDatePicker.getValue(), item
                        );
                        setTooltip(new Tooltip(
                                "You are selecting report for " + p + " days")
                        );
                    }
                };
            }
        };
        checkOutDatePicker.setDayCellFactory(dayCellFactory);
        checkOutDatePicker.setValue(checkInDatePicker.getValue().plusDays(1));

        checkInDatePicker.setPromptText(pattern.toLowerCase());
        checkOutDatePicker.setPromptText(pattern.toLowerCase());
        checkInDatePicker.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
        checkOutDatePicker.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });

        tf1.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                checkInDatePicker.show();
            }
        });

        // When the user selects a date, update the text field
        checkInDatePicker.setOnAction(event -> {
            tf1.setText(checkInDatePicker.getValue().toString());
            checkInDatePicker.hide();
        });

        tf2.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                checkOutDatePicker.show();
            }
        });

        // When the user selects a date, update the text field
        checkOutDatePicker.setOnAction(event -> {
            tf2.setText(checkOutDatePicker.getValue().toString());
            checkOutDatePicker.hide();
        });

        stage.show();
    }

    public static class Report {

        private final SimpleStringProperty firstName;
        private final SimpleStringProperty lastName;
        private final SimpleStringProperty email;
        private final SimpleStringProperty Price;
        private final SimpleStringProperty Tax;

        private Report(String fName, String lName, String email, String price, String tax) {
            this.firstName = new SimpleStringProperty(fName);
            this.lastName = new SimpleStringProperty(lName);
            this.email = new SimpleStringProperty(email);
            this.Price = new SimpleStringProperty(price);
            this.Tax = new SimpleStringProperty(tax);
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

        public String getPrice() {
            return Price.get();
        }

        public void setPrice(String fName) {
            Price.set(fName);
        }

        public String getTax() {
            return Tax.get();
        }

        public void setTax(String fName) {
            Tax.set(fName);
        }
    }

    public void displayReport(String date1, String date2) {

        String sql = "SELECT stockName,SUM(qnt) FROM " + Constants.salesTable
                + " WHERE date BETWEEN '" + date1 + "' and '" + date2 + "' GROUP BY stockName";

        double totalQntAvl = 0;
        double totalPurchase = 0;
        double totalOpening = 0;
        double totalSales = 0;
        int count = 0;

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            reportData = FXCollections.observableArrayList();

            while (rs.next()) {
                String stockname = rs.getString("stockName");
                double purchase = rs.getDouble("SUM(qnt)");
                double avlQnt = db.displayItem(stockname);
                double opeingQnt = buy.getOpen(stockname, date1);
                double sale = sell.oneItemQnt(stockname, date1, date2);

                totalQntAvl += avlQnt;
                totalPurchase += purchase;
                totalOpening += opeingQnt;
                totalSales += sale;
                count++;

                reportData.add(new Report(stockname, String.format("%.1f", avlQnt), String.format("%.1f", opeingQnt),
                        String.format("%.1f", purchase), String.format("%.1f", sale)));
            }

            String totnum = String.valueOf("Total Products : " + count);

            Report lastButOneRow = new Report("", "", "", "", "");
            Report lastRow = new Report(totnum, String.format("%.1f", totalQntAvl), String.format("%.1f", totalOpening),
                    String.format("%.1f", totalPurchase), String.format("%.1f", totalSales));

            reportData.add(lastButOneRow);
            reportData.add(lastRow);

            reportTable.setItems(reportData);

        } catch (SQLException e) {
            logger.error("An error occurred in display function of Reports table.", e);
        }
    }

    public void downloadReportPdf(Stage stage, String fileName) throws IOException {

        // Create the PDF document
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

// Create the content stream for the page
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        File fontFile = new File(Constants.calibarBold);
        PDType0Font font = PDType0Font.load(document, fontFile);

// Define the font and font size for the content stream
        contentStream.setFont(font, 14);

// Define the starting position for the table
        final int startX = 100;
        final int startY = 700;

// Define the row height and column widths for the table
        final int rowHeight = 20;
        final int[] colWidths = {150, 100, 100, 100, 100};

// Define the line width and color
        final float lineWidth = 0.5f;
        final float[] lineColor = {0, 0, 0}; // black color

// Get the data from the TableView and write it to the PDF
        List<Report> items = reportTable.getItems();
        int currentY = startY;

        // Define the heading font and font size for the content stream
        PDType0Font headingFont = PDType0Font.load(document, fontFile);
        float headingFontSize = 16;

        String date1 = "2023-01-01";
        String date2 = "2023-12-31";
// Define the heading text and calculate its width
        String headingText = "Report from " + date1 + " to " + date2;
        float headingWidth = headingFont.getStringWidth(headingText) / 1000 * headingFontSize;

// Define the position for the heading
        float headingX = (page.getMediaBox().getWidth() - headingWidth) / 2;
        float headingY = startY + rowHeight * 2;

// Draw the heading
        contentStream.beginText();
        contentStream.setFont(headingFont, headingFontSize);
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.newLineAtOffset(headingX, headingY);
        contentStream.showText(headingText);
        contentStream.endText();

        // Draw a line before the header row
        contentStream.setLineWidth(lineWidth);
        contentStream.setStrokingColor(Color.black);
        contentStream.moveTo(startX, currentY + 10);
        contentStream.lineTo(startX + Arrays.stream(colWidths).sum() - 20, currentY + 10);
        contentStream.stroke();

// Increase the current Y position to account for the line
        currentY -= 10;

// Draw the table headers
        contentStream.beginText();
        contentStream.setNonStrokingColor(Color.BLUE);
        contentStream.newLineAtOffset(startX, currentY);
        contentStream.showText("Product Name");
        contentStream.newLineAtOffset(colWidths[0] - 15, 0);
        contentStream.showText("Qnt Available");
        contentStream.newLineAtOffset(colWidths[1] + 20, 0);
        contentStream.showText("Opeing");
        contentStream.newLineAtOffset(colWidths[2] - 5, 0);
        contentStream.showText("Purchased");
        contentStream.newLineAtOffset(colWidths[3] - 5, 0);
        contentStream.showText("Sold");
        contentStream.endText();

        // Draw a line after the first row
        contentStream.setLineWidth(lineWidth);
        contentStream.setStrokingColor(Color.black);
        contentStream.moveTo(startX, currentY - rowHeight + 15);
        contentStream.lineTo(startX + Arrays.stream(colWidths).sum() - 20, currentY - rowHeight + 15);
        contentStream.stroke();

// Draw a line after each column
        contentStream.setLineWidth(lineWidth);
        contentStream.setStrokingColor(Color.black);
        contentStream.moveTo(startX + colWidths[0] - 20, currentY + 20);
        contentStream.lineTo(startX + colWidths[0] - 20, currentY - rowHeight - 20);
        contentStream.stroke();
        contentStream.moveTo(startX + colWidths[0] + colWidths[1] - 20, currentY + 20);
        contentStream.lineTo(startX + colWidths[0] + colWidths[1] - 20, currentY - rowHeight - 20);
        contentStream.stroke();
        contentStream.moveTo(startX + colWidths[0] + colWidths[1] + colWidths[2] - 20, currentY + 20);
        contentStream.lineTo(startX + colWidths[0] + colWidths[1] + colWidths[2] - 20, currentY - rowHeight - 20);
        contentStream.stroke();
        contentStream.moveTo(startX + colWidths[0] + colWidths[1] + colWidths[2] + colWidths[3] - 20, currentY + 20);
        contentStream.lineTo(startX + colWidths[0] + colWidths[1] + colWidths[2] + colWidths[3] - 20, currentY - rowHeight - 20);
        contentStream.stroke();

        // Draw a line before the 7th row from the last
        int numRows = items.size();
        int seventhRowFromLast = numRows - 7;
        if (seventhRowFromLast > 0) {
            contentStream.setLineWidth(lineWidth);
            // contentStream.setStrokingColor(Color.blue);
            contentStream.setStrokingColor(Color.black);
            int lineY = startY - (seventhRowFromLast) * rowHeight;
            contentStream.moveTo(startX, lineY + 10);
            contentStream.lineTo(startX + Arrays.stream(colWidths).sum(), lineY + 10);
            contentStream.stroke();

            int lineY2 = startY - (seventhRowFromLast + 1) * rowHeight;
            contentStream.moveTo(startX, lineY2);
            contentStream.lineTo(startX + Arrays.stream(colWidths).sum(), lineY2);
            contentStream.stroke();

            int lineY3 = startY - (numRows + 1) * rowHeight;
            contentStream.moveTo(startX, lineY3);
            contentStream.lineTo(startX + Arrays.stream(colWidths).sum(), lineY3);
            contentStream.stroke();
        }

// Draw the table data
        currentY -= rowHeight;
        for (Report item : items) {
            if (currentY < 50) { // If the current page is full
                contentStream.close(); // Close the current content stream
                page = new PDPage(PDRectangle.A4); // Create a new page
                document.addPage(page);
                contentStream = new PDPageContentStream(document, page); // Create a new content stream for the new page
                contentStream.setFont(font, 14); // Set the font for the new content stream
                currentY = startY; // Reset the current y position to the top of the new page
            }
            contentStream.setFont(font, 14);

            // Draw the first column
            contentStream.beginText();
            contentStream.setNonStrokingColor(Color.BLACK);
            contentStream.newLineAtOffset(startX, currentY);
            contentStream.showText(item.getFirstName());
            contentStream.endText();

            // Draw a line after the first column
            contentStream.moveTo(startX + colWidths[0] - 20, currentY + 2);
            contentStream.lineTo(startX + colWidths[0] - 20, currentY - rowHeight - 2);
            contentStream.stroke();

            // Draw the second column
            contentStream.beginText();
            contentStream.newLineAtOffset(startX + colWidths[0], currentY);
            contentStream.showText(item.getLastName());
            contentStream.endText();

            // Draw a line after the second column
            contentStream.moveTo(startX + colWidths[0] + colWidths[1] - 20, currentY + 2);
            contentStream.lineTo(startX + colWidths[0] + colWidths[1] - 20, currentY - rowHeight - 2);
            contentStream.stroke();

            // Draw the third column
            contentStream.beginText();
            contentStream.newLineAtOffset(startX + colWidths[0] + colWidths[1], currentY);
            contentStream.showText(item.getEmail());
            contentStream.endText();

            // Draw a line after the thrid column
            contentStream.moveTo(startX + colWidths[0] + colWidths[1] + colWidths[2] - 20, currentY + 2);
            contentStream.lineTo(startX + colWidths[0] + colWidths[1] + colWidths[2] - 20, currentY - rowHeight - 2);
            contentStream.stroke();

            // Draw the 4th column
            contentStream.beginText();
            contentStream.newLineAtOffset(startX + colWidths[0] + colWidths[1] + colWidths[2], currentY);
            contentStream.showText(item.getPrice());
            contentStream.endText();

            // Draw a line after the 4th column
            contentStream.moveTo(startX + colWidths[0] + colWidths[1] + colWidths[2] + colWidths[3] - 20, currentY + 2);
            contentStream.lineTo(startX + colWidths[0] + colWidths[1] + colWidths[2] + colWidths[3] - 20, currentY - rowHeight - 2);
            contentStream.stroke();

            // Draw the 4th column
            contentStream.beginText();
            contentStream.newLineAtOffset(startX + colWidths[0] + colWidths[1] + colWidths[2] + colWidths[3], currentY);
            contentStream.showText(item.getTax());
            contentStream.endText();

            currentY -= rowHeight;
        }

        /*  // Close the content stream and save the document in the given loc dicertely
        contentStream.close();
        document.save(new File("V:\\projectBMS\\Reports\\Report.pdf"));
        document.close(); */
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Report PDF");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.setInitialFileName(fileName);
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

        // Show the FileChooser dialog
        File selectedFile = fileChooser.showSaveDialog(stage);

        if (selectedFile != null) {
            // Close the content stream and save the document
            contentStream.close();
            document.save(selectedFile);
            document.close();

        }

    }
}
