package projectbms;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.function.UnaryOperator;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.apache.logging.log4j.LogManager;
import org.controlsfx.control.textfield.TextFields;

public class duesTable extends parentform {

    private TableView<CsDue> csduetable = new TableView<CsDue>();
    ObservableList<CsDue> csDueData;

    private TableView<VendourDue> VendourTable = new TableView<VendourDue>();
    ObservableList<VendourDue> vendourdata;

    private TableView<empdue> emptable = new TableView<empdue>();
    ObservableList<empdue> empduedata;

    List<String> vendourDues = new LinkedList<String>();
    List<String> customerDues = new LinkedList<String>();
    List<String> empDues = new LinkedList<String>();
    List<String> employees = new LinkedList<String>();

    FontWeight fw = FontWeight.BOLD;
    FontPosture fp = FontPosture.REGULAR;

    String product = "product";
    Label l2, l4, l6, l8,l10;
    TextField searchBar = new TextField();

    dues dueMange = new dues();

    org.apache.logging.log4j.Logger logger = LogManager.getLogger(duesTable.class);

    public void totalDue(Stage stage) {

        VBox vb2 = super.addnew(stage, product);

        Label label, text, l1, l3, l5, l7,l9;

        Button b1;
        label = new Label("DUE DATA");
        label.setFont(Font.font("Calibri", fw, fp, 22));

        text = new Label("");
        text.setFont(Font.font("Calibri", fw, fp, 20));

        l1 = new Label("Total Customer Due    :");
        l2 = new Label("");
        display_dues("totalcsdues");

        l3 = new Label("Total Vendor Due     :");
        l4 = new Label("");
        display_dues("totalourdues");

        l5 = new Label("Total Employee Due     :");
        l6 = new Label("");
        display_dues("totalempdues");

        l7 = new Label("Total We Should Get   :");
        l8 = new Label("");
        display_dues("totalDueToGet");
        
        l9 = new Label("Total We Should Pay   :");
        l10 = new Label("");
        display_dues("totalDueToPay");

        l1.setFont(Font.font("Calibri", fw, fp, 18));
        l2.setFont(Font.font("Calibri", fw, fp, 18));
        l3.setFont(Font.font("Calibri", fw, fp, 18));
        l4.setFont(Font.font("Calibri", fw, fp, 18));
        l5.setFont(Font.font("Calibri", fw, fp, 18));
        l6.setFont(Font.font("Calibri", fw, fp, 18));
        l7.setFont(Font.font("Calibri", fw, fp, 18));
        l8.setFont(Font.font("Calibri", fw, fp, 18));
         l9.setFont(Font.font("Calibri", fw, fp, 18));
        l10.setFont(Font.font("Calibri", fw, fp, 18));

        b1 = new Button("Done");
        b1.setFont(Font.font("Calibri", fw, fp, 18));

        HBox hb = new HBox();
        hb.getChildren().addAll(label);
        hb.setSpacing(15);
        hb.setPadding(new Insets(30, 30, 30, 250));

        HBox hb1 = new HBox();
        hb1.getChildren().addAll(l1, l2);
        hb1.setSpacing(15);
        hb1.setPadding(new Insets(10, 10, 10, 10));

        HBox hb2 = new HBox();
        hb2.getChildren().addAll(l3, l4);
        hb2.setSpacing(15);
        hb2.setPadding(new Insets(10, 10, 10, 10));

        HBox hb3 = new HBox();
        hb3.getChildren().addAll(l5, l6);
        hb3.setSpacing(15);
        hb3.setPadding(new Insets(10, 10, 10, 10));

        HBox hb4 = new HBox();
        hb4.getChildren().addAll(l7, l8);
        hb4.setSpacing(15);
        hb4.setPadding(new Insets(10, 10, 10, 10));
        
        HBox hbNew = new HBox();
        hbNew.getChildren().addAll(l9, l10);
        hbNew.setSpacing(15);
        hbNew.setPadding(new Insets(10, 10, 10, 10));

        HBox hb5 = new HBox();
        hb5.getChildren().addAll(b1);
        hb5.setSpacing(15);
        hb5.setPadding(new Insets(10, 10, 10, 130));

        HBox dis = new HBox();
        dis.getChildren().addAll(text);
        dis.setSpacing(15);
        dis.setPadding(new Insets(10, 10, 10, 200));

        VBox vb1 = new VBox();
        vb1.getChildren().addAll(hb1, hb2, hb3, hb4,hbNew);
        vb1.setSpacing(15);
        vb1.setPadding(new Insets(10, 10, 10, 200));

        VBox vb = new VBox();
        vb.getChildren().addAll(hb, vb1, dis);
        vb.setSpacing(10);
        vb.setPadding(new Insets(10, 220, 10, 50));

        vb2.getChildren().addAll(vb);
        vb2.setSpacing(10);
        vb2.setPadding(new Insets(10, 10, 5, 10));

        stage.show();
    }

    public void display_dues(String name) {
        String sql = "SELECT  * "
                + "FROM " + Constants.manage + " WHERE name = ?";

        try (Connection conn = this.connect_(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);

            ResultSet rs = pstmt.executeQuery();

            double price = 0;

            while (rs.next()) {
                price = rs.getDouble("amount");
            }

            String stockamount = String.valueOf(price);

            if (name.equals("totalcsdues")) {
                l2.setText(stockamount);
            }
            if (name.equals("totalourdues")) {
                l4.setText(stockamount);
            }
            if (name.equals("totalempdues")) {
                l6.setText(stockamount);
            }
            if (name.equals("totalDueToGet")) {
                double d1 = Double.parseDouble(l2.getText());
                double d3 = Double.parseDouble(l6.getText());

                double d4 = d1 + d3;
                String Total = String.valueOf(d4);

                l8.setText(Total);
            }
             if (name.equals("totalDueToPay")) {
                l10.setText(l4.getText());
            }
            

        } catch (SQLException e) {
            logger.error("An error occurred while displaying the total dues.", e);
        }
    }

    private Connection connect_() {
        // SQLite connection string
        // String url = "jdbc:sqlite:V://projectBms/managedb1.db";
        String url = Constants.mangeDB;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            logger.error("An error occurred while conneting to mange db.", e);
        }
        return conn;
    }

    Label number;

    public void customerDueTable(Stage stage) {

        VBox vb2 = super.addnew(stage, "product");

        searchBar.setPrefSize(325, 25);
        searchBar.setStyle("-fx-prompt-text-fill:slategray;-fx-text-fill:black;");
        searchBar.setFont(Font.font("Calibri", fw, fp, 13));
        searchBar.setPromptText("🔍 Enter Name Of The Person");

        Label label = new Label("CUSTOMER DUE LIST");
        label.setFont(Font.font("Calibri", fw, fp, 20));

        Label count = new Label("Total Number of Customers :");
        count.setFont(Font.font("Calibri", fw, fp, 20));

        number = new Label();
        number.setFont(Font.font("Calibri", fw, fp, 20));

        csduetable.setEditable(true);
        csduetable.setPrefHeight(380);
        csduetable.setPrefWidth(350);
        csduetable.getStylesheets().add("/projectbms/mycss.css");

        displayCsdueList();

        TableColumn Col1 = new TableColumn("Name");
        Col1.setMinWidth(190);
        Col1.setCellValueFactory(
                new PropertyValueFactory<CsDue, String>("firstName"));

        TableColumn Col2 = new TableColumn("Due Amount");
        Col2.setMinWidth(145);
        Col2.setCellValueFactory(
                new PropertyValueFactory<CsDue, String>("lastName"));

        csduetable.getColumns().addAll(Col1, Col2);

        HBox hb1 = new HBox();
        hb1.setSpacing(5);
        hb1.setPadding(new Insets(10, 10, 10, 70));
        hb1.getChildren().addAll(label);

        HBox hb = new HBox();
        hb.setSpacing(5);
        hb.setPadding(new Insets(0, 10, 0, 30));
        hb.getChildren().addAll(count, number);

        VBox vb1 = new VBox();
        vb1.getChildren().addAll(hb1, hb, searchBar);
        vb1.setSpacing(15);
        vb1.setPadding(new Insets(10, 10, 10, 10));

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 200, 0, 190));
        vbox.getChildren().addAll(vb1, csduetable);

        csduetable.setItems(csDueData);

        vb2.getChildren().addAll(vbox);
        vb2.setSpacing(10);
        vb2.setPadding(new Insets(10, 10, 5, 10));

        searchBar.requestFocus();

        FilteredList<CsDue> filterCsDue = new FilteredList<>(csDueData, b -> true);

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {

            filterCsDue.setPredicate(CsDue -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (CsDue.getFirstName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<CsDue> sortedCsData = new SortedList<>(filterCsDue);

        sortedCsData.comparatorProperty().bind(csduetable.comparatorProperty());

        csduetable.setItems(sortedCsData);

        stage.show();
    }

    public void VendourTable(Stage stage) {

        VBox vb2 = super.addnew(stage, "product");

        searchBar.setPrefSize(325, 25);
        searchBar.setStyle("-fx-prompt-text-fill:slategray;-fx-text-fill:black;");
        searchBar.setFont(Font.font("Calibri", fw, fp, 13));
        searchBar.setPromptText("🔍 Enter Name Of The Person");

        Label label = new Label("VENDOR DUE LIST");
        label.setFont(Font.font("Calibri", fw, fp, 20));

        Label count = new Label("Total Number of Vendors :");
        count.setFont(Font.font("Calibri", fw, fp, 20));

        number = new Label();
        number.setFont(Font.font("Calibri", fw, fp, 20));

        VendourTable.setEditable(true);
        VendourTable.setPrefHeight(380);
        VendourTable.setPrefWidth(350);
        VendourTable.getStylesheets().add("/projectbms/mycss.css");

        displayOurdueList();

        TableColumn Col1 = new TableColumn("Name");
        Col1.setMinWidth(190);
        Col1.setCellValueFactory(
                new PropertyValueFactory<VendourDue, String>("firstName"));

        TableColumn Col2 = new TableColumn("Due Amount");
        Col2.setMinWidth(140);
        Col2.setCellValueFactory(
                new PropertyValueFactory<VendourDue, String>("lastName"));

        VendourTable.getColumns().addAll(Col1, Col2);

        HBox hb1 = new HBox();
        hb1.setSpacing(5);
        hb1.setPadding(new Insets(10, 10, 10, 70));
        hb1.getChildren().addAll(label);

        HBox hb = new HBox();
        hb.setSpacing(5);
        hb.setPadding(new Insets(0, 10, 0, 30));
        hb.getChildren().addAll(count, number);

        VBox vb1 = new VBox();
        vb1.getChildren().addAll(hb1, hb, searchBar);
        vb1.setSpacing(15);
        vb1.setPadding(new Insets(10, 10, 10, 10));

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 200, 0, 190));
        vbox.getChildren().addAll(vb1, VendourTable);

        VendourTable.setItems(vendourdata);

        vb2.getChildren().addAll(vbox);
        vb2.setSpacing(10);
        vb2.setPadding(new Insets(10, 10, 5, 10));

        searchBar.requestFocus();

        FilteredList<VendourDue> filterCsDue = new FilteredList<>(vendourdata, b -> true);

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {

            filterCsDue.setPredicate(VendourDue -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (VendourDue.getFirstName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<VendourDue> sortedCsData = new SortedList<>(filterCsDue);

        sortedCsData.comparatorProperty().bind(VendourTable.comparatorProperty());

        VendourTable.setItems(sortedCsData);

        stage.show();
    }

    public void EmpDueTable(Stage stage) {

        VBox vb2 = super.addnew(stage, "employee");

        searchBar.setPrefSize(325, 25);
        searchBar.setStyle("-fx-prompt-text-fill:slategray;-fx-text-fill:black;");
        searchBar.setFont(Font.font("Calibri", fw, fp, 13));
        searchBar.setPromptText("🔍 Enter Name Of The Person");

        Label label = new Label("EMPLOYEE DUE LIST");
        label.setFont(Font.font("Calibri", fw, fp, 20));

        Label count = new Label("Total Number of Employees :");
        count.setFont(Font.font("Calibri", fw, fp, 20));

        number = new Label();
        number.setFont(Font.font("Calibri", fw, fp, 20));

        emptable.setEditable(true);
        emptable.setPrefHeight(380);
        emptable.setPrefWidth(350);
        emptable.getStylesheets().add("/projectbms/mycss.css");

        displayempdueList();

        TableColumn Col1 = new TableColumn("Name");
        Col1.setMinWidth(190);
        Col1.setCellValueFactory(
                new PropertyValueFactory<empdue, String>("firstName"));

        TableColumn Col2 = new TableColumn("Due Amount");
        Col2.setMinWidth(145);
        Col2.setCellValueFactory(
                new PropertyValueFactory<empdue, String>("lastName"));

        emptable.getColumns().addAll(Col1, Col2);

        HBox hb1 = new HBox();
        hb1.setSpacing(5);
        hb1.setPadding(new Insets(10, 10, 10, 70));
        hb1.getChildren().addAll(label);

        HBox hb = new HBox();
        hb.setSpacing(5);
        hb.setPadding(new Insets(0, 10, 0, 30));
        hb.getChildren().addAll(count, number);

        VBox vb1 = new VBox();
        vb1.getChildren().addAll(hb1, hb, searchBar);
        vb1.setSpacing(15);
        vb1.setPadding(new Insets(10, 10, 10, 10));

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 200, 0, 190));
        vbox.getChildren().addAll(vb1, emptable);

        emptable.setItems(empduedata);

        vb2.getChildren().addAll(vbox);
        vb2.setSpacing(10);
        vb2.setPadding(new Insets(10, 10, 5, 10));

        searchBar.requestFocus();

        FilteredList<empdue> filterCsDue = new FilteredList<>(empduedata, b -> true);

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {

            filterCsDue.setPredicate(empdue -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (empdue.getFirstName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<empdue> sortedCsData = new SortedList<>(filterCsDue);

        sortedCsData.comparatorProperty().bind(emptable.comparatorProperty());

        emptable.setItems(sortedCsData);

        stage.show();
    }

    public static class CsDue {

        private final SimpleStringProperty firstName;
        private final SimpleStringProperty lastName;

        private CsDue(String fName, String lName) {
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

    public static class VendourDue {

        private final SimpleStringProperty firstName;
        private final SimpleStringProperty lastName;

        private VendourDue(String fName, String lName) {
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

    public static class empdue {

        private final SimpleStringProperty firstName;
        private final SimpleStringProperty lastName;

        private empdue(String fName, String lName) {
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

    public void displayCsdueList() {
        String sql = "SELECT * FROM " + Constants.csDue + " ORDER BY customer";
        String sql1 = "SELECT COUNT(*) FROM " + Constants.csDue + "";

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql); Statement stmt1 = conn.createStatement(); ResultSet rs1 = stmt1.executeQuery(sql1)) {

            number.setText(String.valueOf(rs1.getInt(1)));

            csDueData = FXCollections.observableArrayList();

            while (rs.next()) {
                String name = rs.getString("customer");
                double dueamount = rs.getDouble("csdue");

                csDueData.add(new CsDue(name, String.valueOf(String.format("%.1f", dueamount))));
                customerDues.add(name);
            }

        } catch (SQLException e) {
            logger.error("An error occurred while displaying customer dur list.", e);
        }
    }

    public void displayOurdueList() {
        String sql = "SELECT * FROM " + Constants.vendourDue + " ORDER BY name";
        String sql1 = "SELECT COUNT(*) FROM " + Constants.vendourDue + "";

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql); Statement stmt1 = conn.createStatement(); ResultSet rs1 = stmt1.executeQuery(sql1)) {

            number.setText(String.valueOf(rs1.getInt(1)));

            vendourdata = FXCollections.observableArrayList();

            while (rs.next()) {
                String name = rs.getString("name");
                double dueamount = rs.getDouble("ourdue");

                vendourdata.add(new VendourDue(name, String.valueOf(String.format("%.1f", dueamount))));

                vendourDues.add(name);
            }
        } catch (SQLException e) {
            logger.error("An error occurred while displaying vendour dur list.", e);
        }
    }

    private Connection connectEMP() {
        // SQLite connection string
        //String url = "jdbc:sqlite:V://projectBms/bmsemplist1.db";
        String url = Constants.employeeDB;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            logger.error("An error occurred while connectin to employyeDB.", e);
        }
        return conn;
    }

    public void display() {
        String sql = "SELECT empname FROM " + Constants.employeeTable + " ORDER BY empname";

        try (Connection conn = this.connectEMP(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String empname = rs.getString("empname");
                employees.add(empname);

            }
        } catch (SQLException e) {
            logger.error("An error occurred while displaying empoyee due", e);
        }
    }

    public void displayempdueList() {
        String sql = "SELECT * FROM " + Constants.empDue + " ORDER BY empname";
        String sql1 = "SELECT COUNT(*) FROM " + Constants.empDue + "";

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql); Statement stmt1 = conn.createStatement(); ResultSet rs1 = stmt1.executeQuery(sql1)) {

            number.setText(String.valueOf(rs1.getInt(1)));

            empduedata = FXCollections.observableArrayList();

            while (rs.next()) {
                String name = rs.getString("empname");
                double dueamount = rs.getDouble("empdue");

                empduedata.add(new empdue(name, String.valueOf(String.format("%.1f", dueamount))));

                empDues.add(name);
            }

        } catch (SQLException e) {
            logger.error("An error occurred while displaying employee due list.", e);
        }
    }

    private Connection connect() {
        // SQLite connection string
        //  String url = "jdbc:sqlite:V://projectBms/bmsList1.db";
        String url = Constants.mangeDB;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            logger.error("An error occurred while conneting to mange DB.", e);
        }
        return conn;
    }

    java.util.Date date = Calendar.getInstance().getTime();
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String presentDate = dateFormat.format(date);

    public void VendourClearDue(Stage stage) {

        VBox vb2 = super.addnew(stage, "product");

        Label label, l1, l2, l3, l4, text, todayDate;
        TextField tf1, tf2, tf3;
        CheckBox cb1;
        Button b1;

        label = new Label("CLEARING VENDOR DUE ");
        label.setFont(Font.font("Calibri", fw, fp, 20));

        text = new Label("");
        text.setFont(Font.font("Calibri", fw, fp, 20));

        l1 = new Label("Vendor Name       ");
        l2 = new Label("Amount Paid          ");
        l3 = new Label("Paid Today               ");
        l4 = new Label("If Not Date              ");
        todayDate = new Label("( " + presentDate + " )");

        l1.setFont(Font.font("Calibri", fw, fp, 18));
        l2.setFont(Font.font("Calibri", fw, fp, 18));
        l3.setFont(Font.font("Calibri", fw, fp, 18));
        l4.setFont(Font.font("Calibri", fw, fp, 18));

        tf1 = new TextField();
        tf2 = new TextField();
        tf3 = new TextField();

        tf1.setFont(Font.font("Calibri", fw, fp, 18));
        tf2.setFont(Font.font("Calibri", fw, fp, 18));
        tf3.setFont(Font.font("Calibri", fw, fp, 18));
        tf3.setPromptText("YYYY-MM-DD Format");

        cb1 = new CheckBox();

        DatePicker datePicker = new DatePicker();
        datePicker.setPrefSize(20, 10);
        datePicker.setEditable(false);
        StringConverter<LocalDate> converter = getDateConverter();
        datePicker.setConverter(converter);

        StackPane root = new StackPane(tf3, datePicker);
        root.setAlignment(datePicker, Pos.CENTER_RIGHT);

        VBox datePick = new VBox(root);

        b1 = new Button("Done");
        b1.setFont(Font.font("Calibri", fw, fp, 16));

        HBox hb = new HBox();
        hb.getChildren().addAll(label);
        hb.setSpacing(5);
        hb.setPadding(new Insets(10, 10, 10, 40));

        HBox hb1 = new HBox();
        hb1.getChildren().addAll(l1, tf1);
        hb1.setSpacing(17);
        hb1.setPadding(new Insets(10, 10, 10, 10));

        HBox hb2 = new HBox();
        hb2.getChildren().addAll(l2, tf2);
        hb2.setSpacing(17);
        hb2.setPadding(new Insets(10, 10, 10, 10));

        HBox hb3 = new HBox();
        hb3.getChildren().addAll(l3, cb1, todayDate);
        hb3.setSpacing(17);
        hb3.setPadding(new Insets(10, 10, 10, 10));

        HBox hb4 = new HBox();
        hb4.getChildren().addAll(l4, datePick);
        hb4.setSpacing(17);
        hb4.setPadding(new Insets(10, 10, 10, 10));

        HBox hb5 = new HBox();
        hb5.getChildren().addAll(b1);
        hb5.setSpacing(17);
        hb5.setPadding(new Insets(10, 10, 10, 130));

        HBox dis = new HBox();
        dis.getChildren().addAll(text);
        dis.setSpacing(17);
        dis.setPadding(new Insets(10, 10, 10, 120));

        VBox vb1 = new VBox();
        vb1.getChildren().addAll(hb1, hb2, hb3, hb4, hb5);
        vb1.setSpacing(10);
        vb1.setPadding(new Insets(10, 10, 10, 100));

        VBox vb = new VBox();
        vb.getChildren().addAll(hb, vb1, dis);
        vb.setSpacing(10);
        vb.setPadding(new Insets(10, 150, 10, 80));

        vb2.getChildren().addAll(vb);
        vb2.setSpacing(10);
        vb2.setPadding(new Insets(10, 10, 5, 10));

        stage.show();

        tf1.requestFocus();

        b1.setOnAction((ActionEvent e) -> {

            String text1 = tf1.getText();
            String text2 = tf2.getText();
            String text3 = tf3.getText();

            if ((text1 == null || text1.matches("\\s*")) && (text2 == null || text2.matches("\\s*")
                    && (text3 == null || text3.matches("\\s*")))) {
                text.setStyle("-fx-text-fill:red;");
                text.setText("Please Enter Data Before Proceeding");
            } else if (text1 == null || text1.matches("\\s*")) {
                text.setStyle("-fx-text-fill:red;");
                text.setText("Please Enter Name Before Proceeding");
            } else if (text2 == null || text2.matches("\\s*")) {
                text.setStyle("-fx-text-fill:red;");
                text.setText("Please Enter Amount Before Proceeding");
            } else if (!text2.matches("-?\\d+(\\.\\d+)?")) {
                text.setStyle("-fx-text-fill:red;");
                text.setText("Input Mismatch (Please Enter Number, not character)");
            } else if (!cb1.isSelected()) {
                if (text3 == null || text3.matches("\\s*")) {
                    text.setStyle("-fx-text-fill:red;");
                    text.setText("Please Enter Date Before Proceeding");
                } else if (!text3.matches("^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$")) {
                    text.setStyle("-fx-text-fill:red;");
                    text.setText("Please Enter Date In yyyy-mm-dd Format Only");
                    tf2.requestFocus();
                } else {
                    double amount = Double.parseDouble(tf2.getText());

                    if (amount < 0) {
                        text.setStyle("-fx-text-fill:red;");
                        text.setText("Amount Cannot Be Negative");
                    } else {
                        amount = -1 * amount;

                        int x = dueMange.insertour(tf1.getText(), amount);

                        switch (x) {
                            case 4 -> {
                                text.setStyle("-fx-text-fill:black;");
                                text.setText("Vendor Due Updated");
                                tf1.clear();
                                tf2.clear();
                                tf3.clear();
                            }
                            case 3 -> {
                                text.setStyle("-fx-text-fill:red;");
                                text.setText("Amount Paying Is more than Due Amount");
                            }
                            case 2 -> {
                                text.setStyle("-fx-text-fill:black;");
                                text.setText("Vendor due totally cleared");
                                tf1.clear();
                                tf2.clear();
                                tf3.clear();
                            }
                            case 5 -> {
                                text.setStyle("-fx-text-fill:red;");
                                text.setText("Vendor Not Available");
                            }
                            default -> {
                            }
                        }
                    }
                }
            } else {
                double amount = Double.parseDouble(tf2.getText());

                if (amount < 0) {
                    text.setStyle("-fx-text-fill:red;");
                    text.setText("Amount Cannot Be Negative");
                } else {
                    amount = -1 * amount;

                    int x = dueMange.insertour(tf1.getText(), amount);

                    switch (x) {
                        case 4 -> {
                            text.setStyle("-fx-text-fill:black;");
                            text.setText("Vendor Due Updated");
                            tf1.clear();
                            tf2.clear();
                            tf3.clear();
                        }
                        case 3 -> {
                            text.setStyle("-fx-text-fill:red;");
                            text.setText("Amount Paying Is more than Due Amount");
                        }
                        case 2 -> {
                            text.setStyle("-fx-text-fill:black;");
                            text.setText("Vendor due totally cleared");
                            tf1.clear();
                            tf2.clear();
                            tf3.clear();
                        }
                        case 5 -> {
                            text.setStyle("-fx-text-fill:red;");
                            text.setText("No Due With This Vendour");
                        }
                        default -> {
                        }
                    }
                }
            }
        });

        tf1.requestFocus();

        tf1.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                tf2.requestFocus();
            }
            if (event.getCode() == KeyCode.DOWN) {
                tf2.requestFocus();
            }

        });

        tf2.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                cb1.requestFocus();
            }
            if (event.getCode() == KeyCode.UP) {
                tf1.requestFocus();
            }
            if (event.getCode() == KeyCode.DOWN) {
                cb1.requestFocus();
            }
        });

        cb1.setOnKeyPressed(event -> {
            if (cb1.isSelected()) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    b1.fire();
                }
            }
            if (event.getCode() == KeyCode.UP) {
                tf2.requestFocus();
            }
            if (event.getCode() == KeyCode.DOWN) {
                tf3.requestFocus();
            }
        });

        tf3.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                b1.fire();
            }
            if (event.getCode() == KeyCode.UP) {
                tf2.requestFocus();
            }
        });

        cb1.setOnAction(event -> {
            tf3.setDisable(cb1.isSelected());
        });

        tf3.textProperty().addListener((observable, oldValue, newValue) -> {
            cb1.setDisable(!newValue.trim().isEmpty());
        });

        // Show the DatePicker when the TextField is focused
        tf3.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                datePicker.show();
            }
        });

        // When the user selects a date, update the text field
        datePicker.setOnAction(event -> {
            tf3.setText(datePicker.getValue().toString());
            datePicker.hide();
        });

        tf2.setTextFormatter(new TextFormatter<>(getUnaryOperator()));

        number = new Label();
        number.setFont(Font.font("Calibri", fw, fp, 20));

        displayOurdueList();
        TextFields.bindAutoCompletion(tf1, vendourDues);
    }

    public UnaryOperator<TextFormatter.Change> getUnaryOperator() {
        return change -> {
            String newText = change.getControlNewText();
            if (newText.length() <= 10 && newText.matches("^\\d*\\.?\\d{0,1}$")) {
                return change;
            }
            return null;
        };
    }

    private StringConverter<LocalDate> getDateConverter() {
        return new StringConverter<LocalDate>() {
            private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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
        };
    }

    public void CustomerClearDue(Stage stage) {

        VBox vb2 = super.addnew(stage, "product");

        Label label, l1, l2, l3, l4, text;
        TextField tf1, tf2, tf3;
        CheckBox cb1;
        Button b1;

        label = new Label("CLEARING CUSTOMER DUE ");
        label.setFont(Font.font("Calibri", fw, fp, 20));

        text = new Label("");
        text.setFont(Font.font("Calibri", fw, fp, 20));

        l1 = new Label("Customer Name     ");
        l2 = new Label("Amount Paid          ");
        l3 = new Label("Paid Today               ");
        l4 = new Label("If Not Date              ");

        l1.setFont(Font.font("Calibri", fw, fp, 18));
        l2.setFont(Font.font("Calibri", fw, fp, 18));
        l3.setFont(Font.font("Calibri", fw, fp, 18));
        l4.setFont(Font.font("Calibri", fw, fp, 18));

        tf1 = new TextField();
        tf2 = new TextField();
        tf3 = new TextField();

        tf1.setFont(Font.font("Calibri", fw, fp, 18));
        tf2.setFont(Font.font("Calibri", fw, fp, 18));
        tf3.setFont(Font.font("Calibri", fw, fp, 18));

        DatePicker datePicker = new DatePicker();
        datePicker.setPrefSize(20, 10);
        datePicker.setEditable(false);
        StringConverter<LocalDate> converter = getDateConverter();
        datePicker.setConverter(converter);

        StackPane root = new StackPane(tf3, datePicker);
        root.setAlignment(datePicker, Pos.CENTER_RIGHT);

        VBox datePick = new VBox(root);

        cb1 = new CheckBox();

        b1 = new Button("Done");
        b1.setFont(Font.font("Calibri", fw, fp, 16));

        HBox hb = new HBox();
        hb.getChildren().addAll(label);
        hb.setSpacing(5);
        hb.setPadding(new Insets(10, 10, 10, 40));

        HBox hb1 = new HBox();
        hb1.getChildren().addAll(l1, tf1);
        hb1.setSpacing(17);
        hb1.setPadding(new Insets(10, 10, 10, 10));

        HBox hb2 = new HBox();
        hb2.getChildren().addAll(l2, tf2);
        hb2.setSpacing(17);
        hb2.setPadding(new Insets(10, 10, 10, 10));

        HBox hb3 = new HBox();
        hb3.getChildren().addAll(l3, cb1);
        hb3.setSpacing(17);
        hb3.setPadding(new Insets(10, 10, 10, 10));

        HBox hb4 = new HBox();
        hb4.getChildren().addAll(l4, datePick);
        hb4.setSpacing(17);
        hb4.setPadding(new Insets(10, 10, 10, 10));

        HBox hb5 = new HBox();
        hb5.getChildren().addAll(b1);
        hb5.setSpacing(17);
        hb5.setPadding(new Insets(10, 10, 10, 130));

        HBox dis = new HBox();
        dis.getChildren().addAll(text);
        dis.setSpacing(17);
        dis.setPadding(new Insets(10, 10, 10, 120));

        VBox vb1 = new VBox();
        vb1.getChildren().addAll(hb1, hb2, hb3, hb4, hb5);
        vb1.setSpacing(10);
        vb1.setPadding(new Insets(10, 10, 10, 100));

        VBox vb = new VBox();
        vb.getChildren().addAll(hb, vb1, dis);
        vb.setSpacing(10);
        vb.setPadding(new Insets(10, 150, 10, 80));

        vb2.getChildren().addAll(vb);
        vb2.setSpacing(10);
        vb2.setPadding(new Insets(10, 10, 5, 10));

        stage.show();

        tf1.requestFocus();

        b1.setOnAction((ActionEvent e) -> {

            String text1 = tf1.getText();
            String text2 = tf2.getText();
            String text3 = tf3.getText();

            if ((text1 == null || text1.matches("\\s*")) && (text2 == null || text2.matches("\\s*")
                    && (text3 == null || text3.matches("\\s*")))) {
                text.setStyle("-fx-text-fill:red;");
                text.setText("Please Enter Data Before Proceeding");
            } else if (text1 == null || text1.matches("\\s*")) {
                text.setStyle("-fx-text-fill:red;");
                text.setText("Please Enter Name Before Proceeding");
            } else if (text2 == null || text2.matches("\\s*")) {
                text.setStyle("-fx-text-fill:red;");
                text.setText("Please Enter Amount Before Proceeding");
            } else if (!text2.matches("-?\\d+(\\.\\d+)?")) {
                text.setStyle("-fx-text-fill:red;");
                text.setText("Input Mismatch (Please Enter Number, not character)");
            } else if (!cb1.isSelected()) {
                if (text3 == null || text3.matches("\\s*")) {
                    text.setStyle("-fx-text-fill:red;");
                    text.setText("Please Enter Date Before Proceeding");
                } else if (!text3.matches("^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$")) {
                    text.setStyle("-fx-text-fill:red;");
                    text.setText("Please Enter Date In yyyy-mm-dd Format Only");
                    tf2.requestFocus();
                } else {

                    double amount = Double.parseDouble(tf2.getText());

                    if (amount < 0) {
                        text.setStyle("-fx-text-fill:red;");
                        text.setText("Amount Cannot Be Negative");
                    } else {

                        double money = -1 * amount;

                        int x = dueMange.insertcs(tf1.getText(), money);

                        switch (x) {
                            case 4 -> {
                                text.setStyle("-fx-text-fill:black;");
                                text.setText("Customer Due Updated");
                                tf1.clear();
                                tf2.clear();
                                tf3.clear();
                            }
                            case 3 -> {
                                text.setStyle("-fx-text-fill:red;");
                                text.setText("Amount Paying is more than Due Amount");
                            }
                            case 2 -> {
                                text.setStyle("-fx-text-fill:black;");
                                text.setText("Customer due totally cleared");
                                tf1.clear();
                                tf2.clear();
                                tf3.clear();
                            }
                            case 5 -> {
                                text.setStyle("-fx-text-fill:red;");
                                text.setText("No Due With This is Customer");
                            }
                            default -> {
                            }
                        }
                    }
                }
            } else {

                double amount = Double.parseDouble(tf2.getText());

                if (amount < 0) {
                    text.setStyle("-fx-text-fill:red;");
                    text.setText("Amount Cannot Be Negative");
                } else {

                    double money = -1 * amount;

                    int x = dueMange.insertcs(tf1.getText(), money);

                    switch (x) {
                        case 4 -> {
                            text.setStyle("-fx-text-fill:black;");
                            text.setText("Customer Due Updated");
                            tf1.clear();
                            tf2.clear();
                            tf3.clear();
                        }
                        case 3 -> {
                            text.setStyle("-fx-text-fill:red;");
                            text.setText("Amount Paying is more than Due Amount");
                        }
                        case 2 -> {
                            text.setStyle("-fx-text-fill:black;");
                            text.setText("Customer due totally cleared");
                            tf1.clear();
                            tf2.clear();
                            tf3.clear();
                        }
                        case 5 -> {
                            text.setStyle("-fx-text-fill:red;");
                            text.setText("No Due With This is Customer");
                        }
                        default -> {
                        }
                    }
                }

            }
        });

        tf1.requestFocus();

        tf1.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                tf2.requestFocus();
            }
            if (event.getCode() == KeyCode.DOWN) {
                tf2.requestFocus();
            }

        });

        tf2.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                cb1.requestFocus();
            }
            if (event.getCode() == KeyCode.UP) {
                tf1.requestFocus();
            }
            if (event.getCode() == KeyCode.DOWN) {
                cb1.requestFocus();
            }
        });

        cb1.setOnKeyPressed(event -> {
            if (cb1.isSelected()) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    b1.fire();
                }
            }
            if (event.getCode() == KeyCode.UP) {
                tf2.requestFocus();
            }
            if (event.getCode() == KeyCode.DOWN) {
                tf3.requestFocus();
            }
        });

        tf3.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                b1.fire();
            }
            if (event.getCode() == KeyCode.UP) {
                tf2.requestFocus();
            }
        });

        cb1.setOnAction(event -> {
            tf3.setDisable(cb1.isSelected());
        });

        tf3.textProperty().addListener((observable, oldValue, newValue) -> {
            cb1.setDisable(!newValue.trim().isEmpty());
        });

        // Show the DatePicker when the TextField is focused
        tf3.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                datePicker.show();
            }
        });

        // When the user selects a date, update the text field
        datePicker.setOnAction(event -> {
            tf3.setText(datePicker.getValue().toString());
            datePicker.hide();
        });

        tf2.setTextFormatter(new TextFormatter<>(getUnaryOperator()));

        number = new Label();
        number.setFont(Font.font("Calibri", fw, fp, 20));
        displayCsdueList();
        TextFields.bindAutoCompletion(tf1, customerDues);
    }

    public void EmpAddDue(Stage stage) {

        VBox vb2 = super.addnew(stage, "employee");

        Label label, l1, l2, l3, l4, text;
        TextField tf1, tf2, tf3;
        CheckBox cb1;
        Button b1;

        label = new Label("ENTERING EMPLOYEE DUE ");
        label.setFont(Font.font("Calibri", fw, fp, 20));

        text = new Label("");
        text.setFont(Font.font("Calibri", fw, fp, 20));

        l1 = new Label("Employee Name       ");
        l2 = new Label("Amount Given         ");
        l3 = new Label("Paid Today               ");
        l4 = new Label("If Not Date              ");

        l1.setFont(Font.font("Calibri", fw, fp, 18));
        l2.setFont(Font.font("Calibri", fw, fp, 18));
        l3.setFont(Font.font("Calibri", fw, fp, 18));
        l4.setFont(Font.font("Calibri", fw, fp, 18));

        tf1 = new TextField();
        tf2 = new TextField();
        tf3 = new TextField();

        tf1.setFont(Font.font("Calibri", fw, fp, 18));
        tf2.setFont(Font.font("Calibri", fw, fp, 18));
        tf3.setFont(Font.font("Calibri", fw, fp, 18));

        cb1 = new CheckBox();

        DatePicker datePicker = new DatePicker();
        datePicker.setPrefSize(20, 10);
        datePicker.setEditable(false);
        StringConverter<LocalDate> converter = getDateConverter();
        datePicker.setConverter(converter);

        StackPane root = new StackPane(tf3, datePicker);
        root.setAlignment(datePicker, Pos.CENTER_RIGHT);

        VBox datePick = new VBox(root);

        b1 = new Button("Done");
        b1.setFont(Font.font("Calibri", fw, fp, 16));

        HBox hb = new HBox();
        hb.getChildren().addAll(label);
        hb.setSpacing(5);
        hb.setPadding(new Insets(10, 10, 10, 40));

        HBox hb1 = new HBox();
        hb1.getChildren().addAll(l1, tf1);
        hb1.setSpacing(17);
        hb1.setPadding(new Insets(10, 10, 10, 10));

        HBox hb2 = new HBox();
        hb2.getChildren().addAll(l2, tf2);
        hb2.setSpacing(17);
        hb2.setPadding(new Insets(10, 10, 10, 10));

        HBox hb3 = new HBox();
        hb3.getChildren().addAll(l3, cb1);
        hb3.setSpacing(17);
        hb3.setPadding(new Insets(10, 10, 10, 10));

        HBox hb4 = new HBox();
        hb4.getChildren().addAll(l4, datePick);
        hb4.setSpacing(17);
        hb4.setPadding(new Insets(10, 10, 10, 10));

        HBox hb5 = new HBox();
        hb5.getChildren().addAll(b1);
        hb5.setSpacing(17);
        hb5.setPadding(new Insets(10, 10, 10, 130));

        HBox dis = new HBox();
        dis.getChildren().addAll(text);
        dis.setSpacing(17);
        dis.setPadding(new Insets(10, 10, 10, 120));

        VBox vb1 = new VBox();
        vb1.getChildren().addAll(hb1, hb2, hb3, hb4, hb5);
        vb1.setSpacing(10);
        vb1.setPadding(new Insets(10, 10, 10, 100));

        VBox vb = new VBox();
        vb.getChildren().addAll(hb, vb1, dis);
        vb.setSpacing(10);
        vb.setPadding(new Insets(10, 150, 10, 80));

        vb2.getChildren().addAll(vb);
        vb2.setSpacing(10);
        vb2.setPadding(new Insets(10, 10, 5, 10));

        stage.show();

        tf1.requestFocus();

        b1.setOnAction((ActionEvent e) -> {

        });

        tf1.requestFocus();

        b1.setOnAction((ActionEvent e) -> {

            String text1 = tf1.getText();
            String text2 = tf2.getText();
            String text3 = tf3.getText();

            if ((text1 == null || text1.matches("\\s*")) && (text2 == null || text2.matches("\\s*")
                    && (text3 == null || text3.matches("\\s*")))) {
                text.setStyle("-fx-text-fill:red;");
                text.setText("Please Enter Data Before Proceeding");
            } else if (text1 == null || text1.matches("\\s*")) {
                text.setStyle("-fx-text-fill:red;");
                text.setText("Please Enter Name Before Proceeding");
            } else if (text2 == null || text2.matches("\\s*")) {
                text.setStyle("-fx-text-fill:red;");
                text.setText("Please Enter Amount Before Proceeding");
            } else if (!text2.matches("-?\\d+(\\.\\d+)?")) {
                text.setStyle("-fx-text-fill:red;");
                text.setText("Input Mismatch (Please Enter Number, not character)");
            } else if (!cb1.isSelected()) {
                if (text3 == null || text3.matches("\\s*")) {
                    text.setStyle("-fx-text-fill:red;");
                    text.setText("Please Enter Date Before Proceeding");
                } else if (!text3.matches("^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$")) {
                    text.setStyle("-fx-text-fill:red;");
                    text.setText("Please Enter Date In yyyy-mm-dd Format Only");
                    tf2.requestFocus();
                } else {

                    double amount = Double.parseDouble(tf2.getText());

                    if (amount < 0) {
                        text.setStyle("-fx-text-fill:red;");
                        text.setText("Amount Cannot Be Negative");
                    } else if (amount == 0) {
                        text.setStyle("-fx-text-fill:red;");
                        text.setText("Amount Cannot Be Zero");
                    } else {
                        int x = dueMange.inssertemp(tf1.getText(), amount);

                        switch (x) {
                            case 1 -> {
                                text.setStyle("-fx-text-fill:black;");
                                text.setText("Employee Due Updated");
                                tf1.clear();
                                tf2.clear();
                                tf3.clear();
                            }
                            case 3 -> {
                                text.setStyle("-fx-text-fill:red;");
                                text.setText("Amount Paying Is more than Due Amount");
                            }
                            case 2 -> {
                                text.setStyle("-fx-text-fill:black;");
                                text.setText("Employee Due Cleared");
                                tf1.clear();
                                tf2.clear();
                                tf3.clear();
                            }
                            case 6 -> {
                                text.setStyle("-fx-text-fill:black;");
                                text.setText("New Employee Due Added");
                                tf1.clear();
                                tf2.clear();
                                tf3.clear();
                            }
                            default -> {
                            }
                        }
                    }
                }
            } else {

                double amount = Double.parseDouble(tf2.getText());

                if (amount < 0) {
                    text.setStyle("-fx-text-fill:red;");
                    text.setText("Amount Cannot Be Negative");
                } else if (amount == 0) {
                    text.setStyle("-fx-text-fill:red;");
                    text.setText("Amount Cannot Be Zero");
                } else {
                    int x = dueMange.inssertemp(tf1.getText(), amount);

                    switch (x) {
                        case 1 -> {
                            text.setStyle("-fx-text-fill:black;");
                            text.setText("Employee Due Updated");
                            tf1.clear();
                            tf2.clear();
                            tf3.clear();
                        }
                        case 3 -> {
                            text.setStyle("-fx-text-fill:red;");
                            text.setText("Amount Paying Is more than Due Amount");
                        }
                        case 2 -> {
                            text.setStyle("-fx-text-fill:black;");
                            text.setText("Employee Due Cleared");
                            tf1.clear();
                            tf2.clear();
                            tf3.clear();
                        }
                        case 6 -> {
                            text.setStyle("-fx-text-fill:black;");
                            text.setText("New Employee Due Added");
                            tf1.clear();
                            tf2.clear();
                            tf3.clear();
                        }
                        default -> {
                        }
                    }
                }
            }
        }
        );

        tf1.requestFocus();

        tf1.setOnKeyPressed(event
                -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                tf2.requestFocus();
            }
            if (event.getCode() == KeyCode.DOWN) {
                tf2.requestFocus();
            }

        }
        );

        tf2.setOnKeyPressed(event
                -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                cb1.requestFocus();
            }
            if (event.getCode() == KeyCode.UP) {
                tf1.requestFocus();
            }
            if (event.getCode() == KeyCode.DOWN) {
                cb1.requestFocus();
            }
        }
        );

        cb1.setOnKeyPressed(event
                -> {
            if (cb1.isSelected()) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    b1.fire();
                }
            }
            if (event.getCode() == KeyCode.UP) {
                tf2.requestFocus();
            }
            if (event.getCode() == KeyCode.DOWN) {
                tf3.requestFocus();
            }
        }
        );

        tf3.setOnKeyPressed(event
                -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                b1.fire();
            }
            if (event.getCode() == KeyCode.UP) {
                tf2.requestFocus();
            }
        }
        );

        cb1.setOnAction(event
                -> {
            tf3.setDisable(cb1.isSelected());
        }
        );

        tf3.textProperty()
                .addListener((observable, oldValue, newValue) -> {
                    cb1.setDisable(!newValue.trim().isEmpty());
                }
                );

        // Show the DatePicker when the TextField is focused
        tf3.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                datePicker.show();
            }
        });

        // When the user selects a date, update the text field
        datePicker.setOnAction(event -> {
            tf3.setText(datePicker.getValue().toString());
            datePicker.hide();
        });

        tf2.setTextFormatter(new TextFormatter<>(getUnaryOperator()));

        number = new Label();
        number.setFont(Font.font("Calibri", fw, fp, 20));
        display();
        TextFields.bindAutoCompletion(tf1, employees);
    }

    public void EmpClearDue(Stage stage) {

        VBox vb2 = super.addnew(stage, "employee");

        Label label, l1, l2, l3, l4, text;
        TextField tf1, tf2, tf3;
        CheckBox cb1;
        Button b1;

        label = new Label("CLEARING EMPLOYEE DUE ");
        label.setFont(Font.font("Calibri", fw, fp, 20));

        text = new Label("");
        text.setFont(Font.font("Calibri", fw, fp, 20));

        l1 = new Label("Employee Name     ");
        l2 = new Label("Amount Paid          ");
        l3 = new Label("Paid Today               ");
        l4 = new Label("If Not Date              ");

        l1.setFont(Font.font("Calibri", fw, fp, 18));
        l2.setFont(Font.font("Calibri", fw, fp, 18));
        l3.setFont(Font.font("Calibri", fw, fp, 18));
        l4.setFont(Font.font("Calibri", fw, fp, 18));

        tf1 = new TextField();
        tf2 = new TextField();
        tf3 = new TextField();

        tf1.setFont(Font.font("Calibri", fw, fp, 18));
        tf2.setFont(Font.font("Calibri", fw, fp, 18));
        tf3.setFont(Font.font("Calibri", fw, fp, 18));

        cb1 = new CheckBox();

        DatePicker datePicker = new DatePicker();
        datePicker.setPrefSize(20, 10);
        datePicker.setEditable(false);
        StringConverter<LocalDate> converter = getDateConverter();
        datePicker.setConverter(converter);

        StackPane root = new StackPane(tf3, datePicker);
        root.setAlignment(datePicker, Pos.CENTER_RIGHT);

        VBox datePick = new VBox(root);

        b1 = new Button("Done");
        b1.setFont(Font.font("Calibri", fw, fp, 16));

        HBox hb = new HBox();
        hb.getChildren().addAll(label);
        hb.setSpacing(5);
        hb.setPadding(new Insets(10, 10, 10, 40));

        HBox hb1 = new HBox();
        hb1.getChildren().addAll(l1, tf1);
        hb1.setSpacing(17);
        hb1.setPadding(new Insets(10, 10, 10, 10));

        HBox hb2 = new HBox();
        hb2.getChildren().addAll(l2, tf2);
        hb2.setSpacing(17);
        hb2.setPadding(new Insets(10, 10, 10, 10));

        HBox hb3 = new HBox();
        hb3.getChildren().addAll(l3, cb1);
        hb3.setSpacing(17);
        hb3.setPadding(new Insets(10, 10, 10, 10));

        HBox hb4 = new HBox();
        hb4.getChildren().addAll(l4, datePick);
        hb4.setSpacing(17);
        hb4.setPadding(new Insets(10, 10, 10, 10));

        HBox hb5 = new HBox();
        hb5.getChildren().addAll(b1);
        hb5.setSpacing(17);
        hb5.setPadding(new Insets(10, 10, 10, 130));

        HBox dis = new HBox();
        dis.getChildren().addAll(text);
        dis.setSpacing(17);
        dis.setPadding(new Insets(10, 10, 10, 120));

        VBox vb1 = new VBox();
        vb1.getChildren().addAll(hb1, hb2, hb3, hb4, hb5);
        vb1.setSpacing(10);
        vb1.setPadding(new Insets(10, 10, 10, 100));

        VBox vb = new VBox();
        vb.getChildren().addAll(hb, vb1, dis);
        vb.setSpacing(10);
        vb.setPadding(new Insets(10, 150, 10, 80));

        vb2.getChildren().addAll(vb);
        vb2.setSpacing(10);
        vb2.setPadding(new Insets(10, 10, 5, 10));

        stage.show();

        tf1.requestFocus();

        b1.setOnAction((ActionEvent e) -> {

            String text1 = tf1.getText();
            String text2 = tf2.getText();
            String text3 = tf3.getText();

            if ((text1 == null || text1.matches("\\s*")) && (text2 == null || text2.matches("\\s*")
                    && (text3 == null || text3.matches("\\s*")))) {
                text.setStyle("-fx-text-fill:red;");
                text.setText("Please Enter Data Before Proceeding");
            } else if (text1 == null || text1.matches("\\s*")) {
                text.setStyle("-fx-text-fill:red;");
                text.setText("Please Enter Name Before Proceeding");
            } else if (text2 == null || text2.matches("\\s*")) {
                text.setStyle("-fx-text-fill:red;");
                text.setText("Please Enter Amount Before Proceeding");
            } else if (!text2.matches("-?\\d+(\\.\\d+)?")) {
                text.setStyle("-fx-text-fill:red;");
                text.setText("Input Mismatch (Please Enter Number, not character)");
            } else if (!cb1.isSelected()) {
                if (text3 == null || text3.matches("\\s*")) {
                    text.setStyle("-fx-text-fill:red;");
                    text.setText("Please Enter Date Before Proceeding");
                } else if (!text3.matches("^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$")) {
                    text.setStyle("-fx-text-fill:red;");
                    text.setText("Please Enter Date In yyyy-mm-dd Format Only");
                    tf2.requestFocus();
                } else {

                    double amount = Double.parseDouble(tf2.getText());

                    if (amount == 0) {
                        text.setStyle("-fx-text-fill:red;");
                        text.setText("Due Amount cant be 0");
                    } else if (amount < 0) {
                        text.setStyle("-fx-text-fill:red;");
                        text.setText("Due Amount can be negtive");
                    }
                    double money = -1 * amount;

                    int x = dueMange.inssertemp(tf1.getText(), money);

                    switch (x) {
                        case 4 -> {
                            text.setStyle("-fx-text-fill:black;");
                            text.setText("Employee Due Updated");
                            tf1.clear();
                            tf2.clear();
                            tf3.clear();
                        }
                        case 3 -> {
                            text.setStyle("-fx-text-fill:red;");
                            text.setText("Amount Paying Is more than Due Amount");
                        }
                        case 2 -> {
                            text.setStyle("-fx-text-fill:black;");
                            text.setText("Employee due totally cleared");
                            tf1.clear();
                            tf2.clear();
                            tf3.clear();
                        }
                        case 5 -> {
                            text.setStyle("-fx-text-fill:red;");
                            text.setText("Employee Not Available");
                        }
                        default -> {
                        }
                    }
                }
            } else {
                double amount = Double.parseDouble(tf2.getText());
                
                if (amount == 0) {
                        text.setStyle("-fx-text-fill:red;");
                        text.setText("Due Amount cant be 0");
                    } else if (amount < 0) {
                        text.setStyle("-fx-text-fill:red;");
                        text.setText("Due Amount can be negtive");
                    }
                
                double money = -1 * amount;

                int x = dueMange.inssertemp(tf1.getText(), money);

                switch (x) {
                    case 4 -> {
                        text.setStyle("-fx-text-fill:black;");
                        text.setText("Employee Due Updated");
                        tf1.clear();
                        tf2.clear();
                        tf3.clear();
                    }
                    case 3 -> {
                        text.setStyle("-fx-text-fill:red;");
                        text.setText("Amount Paying Is more than Due Amount");
                    }
                    case 2 -> {
                        text.setStyle("-fx-text-fill:black;");
                        text.setText("Employee due totally cleared");
                        tf1.clear();
                        tf2.clear();
                        tf3.clear();
                    }
                    case 5 -> {
                        text.setStyle("-fx-text-fill:red;");
                        text.setText("Employee Not Available");
                    }
                    default -> {
                    }
                }
            }
        }
        );

        tf1.requestFocus();

        tf1.setOnKeyPressed(event
                -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                tf2.requestFocus();
            }
            if (event.getCode() == KeyCode.DOWN) {
                tf2.requestFocus();
            }

        }
        );

        tf2.setOnKeyPressed(event
                -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                cb1.requestFocus();
            }
            if (event.getCode() == KeyCode.UP) {
                tf1.requestFocus();
            }
            if (event.getCode() == KeyCode.DOWN) {
                cb1.requestFocus();
            }
        }
        );

        cb1.setOnKeyPressed(event
                -> {
            if (cb1.isSelected()) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    b1.fire();
                }
            }
            if (event.getCode() == KeyCode.UP) {
                tf2.requestFocus();
            }
            if (event.getCode() == KeyCode.DOWN) {
                tf3.requestFocus();
            }
        }
        );

        tf3.setOnKeyPressed(event
                -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                b1.fire();
            }
            if (event.getCode() == KeyCode.UP) {
                tf2.requestFocus();
            }
        }
        );

        cb1.setOnAction(event
                -> {
            tf3.setDisable(cb1.isSelected());
        }
        );

        tf3.textProperty()
                .addListener((observable, oldValue, newValue) -> {
                    cb1.setDisable(!newValue.trim().isEmpty());
                }
                );

        // Show the DatePicker when the TextField is focused
        tf3.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                datePicker.show();
            }
        });

        // When the user selects a date, update the text field
        datePicker.setOnAction(event -> {
            tf3.setText(datePicker.getValue().toString());
            datePicker.hide();
        });

        tf2.setTextFormatter(
                new TextFormatter<>(getUnaryOperator()));

        number = new Label();

        number.setFont(Font.font("Calibri", fw, fp, 20));
        displayempdueList();

        TextFields.bindAutoCompletion(tf1, empDues);
    }
}
