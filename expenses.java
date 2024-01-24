package projectbms;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
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
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.StackPane;
import javafx.util.StringConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.textfield.TextFields;

public class expenses extends parentform {

    manage mdb = new manage();
    empDatabase edb = new empDatabase();

    String expenses = "expenses";
    String product = "product";

    FontWeight fw = FontWeight.BOLD;
    FontPosture fp = FontPosture.REGULAR;

    private TableView<EMP> empTable = new TableView<EMP>();
    ObservableList<EMP> empData;

    List<String> products = new LinkedList<String>();

    java.util.Date date = Calendar.getInstance().getTime();
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String presentDate = dateFormat.format(date);

    public void rent(Stage stage) {

        VBox vb2 = super.addnew(stage, product);

        Label label, text, l1, l2, l3, todayDate;
        TextField tf1, tf2;
        Button b1;
        CheckBox cb1;

        label = new Label("EXPENSES(RENT) ");
        label.setFont(Font.font("Calibri", fw, fp, 22));

        text = new Label("");
        text.setFont(Font.font("Calibri", fw, fp, 20));

        l1 = new Label("Rent Paid             ");
        l3 = new Label("Paid Today           ");
        l2 = new Label("Else Date              ");
        todayDate = new Label("( " + presentDate + " )");
        l1.setFont(Font.font("Calibri", fw, fp, 20));
        l2.setFont(Font.font("Calibri", fw, fp, 20));
        l3.setFont(Font.font("Calibri", fw, fp, 20));

        tf1 = new TextField();
        tf2 = new TextField();
        tf1.setFont(Font.font("Calibri", fw, fp, 16));
        tf2.setFont(Font.font("Calibri", fw, fp, 16));
        tf2.setPromptText("YYYY-MM-DD Format");

        b1 = new Button("Done");
        b1.setFont(Font.font("Calibri", fw, fp, 16));

        cb1 = new CheckBox();

        DatePicker datePicker = new DatePicker();
        datePicker.setPrefSize(20, 10);
        datePicker.setEditable(false);
        StringConverter<LocalDate> converter = getDateConverter();
        datePicker.setConverter(converter);

        StackPane root = new StackPane(tf2, datePicker);
        root.setAlignment(datePicker, Pos.CENTER_RIGHT);

        VBox datePick = new VBox(root);

        HBox hb = new HBox();
        hb.getChildren().addAll(label);
        hb.setSpacing(15);
        hb.setPadding(new Insets(50, 50, 20, 100));

        HBox hb1 = new HBox();
        hb1.getChildren().addAll(l1, tf1);
        hb1.setSpacing(15);
        hb1.setPadding(new Insets(10, 10, 10, 10));

        HBox hb2 = new HBox();
        hb2.getChildren().addAll(l2, datePick);
        hb2.setSpacing(15);
        hb2.setPadding(new Insets(10, 10, 10, 10));

        HBox cb = new HBox();
        cb.getChildren().addAll(l3, cb1, todayDate);
        cb.setSpacing(15);
        cb.setPadding(new Insets(10, 10, 10, 10));

        HBox hb3 = new HBox();
        hb3.getChildren().addAll(b1);
        hb3.setSpacing(15);
        hb3.setPadding(new Insets(10, 10, 10, 100));

        HBox hb4 = new HBox();
        hb4.getChildren().addAll(text);
        hb4.setSpacing(15);
        hb4.setPadding(new Insets(10, 10, 10, 10));

        VBox vb1 = new VBox();
        vb1.getChildren().addAll(hb1, cb, hb2, hb3, hb4);
        vb1.setSpacing(15);
        vb1.setPadding(new Insets(10, 10, 10, 150));

        VBox vb = new VBox();
        vb.getChildren().addAll(hb, vb1);
        vb.setSpacing(15);
        vb.setPadding(new Insets(10, 160, 100, 50));

        vb2.getChildren().addAll(vb);
        vb2.setSpacing(10);
        vb2.setPadding(new Insets(10, 10, 5, 10));

        stage.show();

        b1.setOnAction((ActionEvent e) -> {

            String text1 = tf1.getText();
            String text2 = tf2.getText();

            if ((text1 == null || text1.matches("\\s*")) && (text2 == null || text2.matches("\\s*"))) {
                text.setStyle("-fx-text-fill:red;");
                text.setText("Please Enter Data Before Proceeding");
            } else if (text1 == null || text1.matches("\\s*")) {
                text.setStyle("-fx-text-fill:red;");
                text.setText("Please Enter Amount Before Proceeding");
            } else if (!text1.matches("-?\\d+(\\.\\d+)?")) {
                text.setStyle("-fx-text-fill:red;");
                text.setText("Input Mismatch (Please Enter Number, not character)");
            } else if (!cb1.isSelected()) {
                if (text2 == null || text2.matches("\\s*")) {
                    text.setStyle("-fx-text-fill:red;");
                    text.setText("Please Enter Date Before Proceeding");
                } else if (!text2.matches("^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$")) {
                    text.setStyle("-fx-text-fill:red;");
                    text.setText("Please Enter Date In yyyy-mm-dd Format Only");
                    tf2.requestFocus();
                } else {
                    double d1 = Double.parseDouble(text1);
                    mdb.increaseAmount(expenses, d1);
                    String currentDate;
                    if (cb1.isSelected()) {
                        currentDate = dateFormat.format(date);
                        insert("Rent of" + currentDate, d1, currentDate);
                    } else {
                        currentDate = text2;
                        insert("Rent of " + currentDate, d1, currentDate);
                    }
                    text.setStyle("-fx-text-fill:black;");
                    text.setText("               Expenses Increased");
                    display(expenses);
                    revenue();
                    tf1.clear();
                    tf2.clear();
                    tf1.requestFocus();
                }
            } else {
                double d1 = Double.parseDouble(text1);
                mdb.increaseAmount(expenses, d1);
                String currentDate;
                if (cb1.isSelected()) {

                    currentDate = dateFormat.format(date);
                    insert("Rent of " + currentDate, d1, currentDate);
                } else {
                    currentDate = text2;
                    insert("Rent of " + currentDate, d1, currentDate);
                }
                text.setStyle("-fx-text-fill:black;");
                text.setText("                 Expenses Increased");
                display(expenses);
                revenue();
                tf1.clear();
                tf2.clear();
                cb1.fire();
                tf1.requestFocus();
            }
        });

        tf1.requestFocus();

        tf1.setTextFormatter(new TextFormatter<>(change
                -> change.getControlNewText().length() <= 10 ? change : null));

        tf1.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                cb1.requestFocus();
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
                tf1.requestFocus();
            }
            if (event.getCode() == KeyCode.DOWN) {
                tf2.requestFocus();
            }
        });

        tf2.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                b1.fire();
            }
            if (event.getCode() == KeyCode.UP) {
                tf1.requestFocus();
            }
        });

        cb1.setOnAction(event -> {
            tf2.setDisable(cb1.isSelected());
        });

        tf2.textProperty().addListener((observable, oldValue, newValue) -> {
            cb1.setDisable(!newValue.trim().isEmpty());
        });

        // Show the DatePicker when the TextField is focused
        tf2.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                datePicker.show();
            }
        });

        // When the user selects a date, update the text field
        datePicker.setOnAction(event -> {
            tf2.setText(datePicker.getValue().toString());
            datePicker.hide();
        });

        tf1.setTextFormatter(new TextFormatter<>(getUnaryOperator()));
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

    Button done;
    Label number;

    public void wages(Stage stage) {

        VBox vb2 = super.addnew(stage, "employeeAttendnce");

        TextField searchBar = new TextField();

        searchBar.setPrefSize(325, 25);
        searchBar.setStyle("-fx-prompt-text-fill:slategray;-fx-text-fill:black;");
        searchBar.setFont(Font.font("Calibri", fw, fp, 13));
        searchBar.setPromptText("🔍 Enter Name Of The Person");

        empTable.setEditable(true);
        empTable.setPrefHeight(380);
        empTable.setPrefWidth(400);
        empTable.getStylesheets().add("/projectbms/mycss.css");

        Label label = new Label("WAGES LIST");
        label.setFont(Font.font("Calibri", fw, fp, 20));

        Label count = new Label("Total Number of Employees :");
        count.setFont(Font.font("Calibri", fw, fp, 20));

        number = new Label();
        number.setFont(Font.font("Calibri", fw, fp, 20));

        done = new Button("Done");
        done.setFont(Font.font("Calibri", fw, fp, 18));

        Label paid = new Label();
        paid.setFont(Font.font("Calibri", fw, fp, 18));

        calSal();
        TableColumn Col1 = new TableColumn("Name");
        Col1.setMinWidth(100);
        Col1.setCellValueFactory(
                new PropertyValueFactory<EMP, String>("firstName"));

        TableColumn Col2 = new TableColumn("Daily Salary");
        Col2.setMinWidth(80);
        Col2.setCellValueFactory(
                new PropertyValueFactory<EMP, String>("lastName"));

        TableColumn emailCol = new TableColumn("Working Days");
        emailCol.setMinWidth(90);
        emailCol.setCellValueFactory(
                new PropertyValueFactory<EMP, String>("email"));

        TableColumn PriceCol = new TableColumn("Salary");
        PriceCol.setMinWidth(80);
        PriceCol.setCellValueFactory(
                new PropertyValueFactory<EMP, String>("Price"));

        TableColumn Paid = new TableColumn("Paid");
        Paid.setMinWidth(47);
        Paid.setCellValueFactory(
                new PropertyValueFactory<EMP, String>("checkbox"));

        empTable.getColumns().addAll(Col1, Col2, emailCol, PriceCol, Paid);

        empTable.setItems(empData);

        HBox lab = new HBox();
        lab.setSpacing(5);
        lab.setPadding(new Insets(10, 10, 10, 120));
        lab.getChildren().addAll(label);

        HBox hb = new HBox();
        hb.setSpacing(5);
        hb.setPadding(new Insets(0, 10, 0, 40));
        hb.getChildren().addAll(count, number);

        VBox vb1 = new VBox();
        vb1.getChildren().addAll(lab, hb, searchBar);
        vb1.setSpacing(15);
        vb1.setPadding(new Insets(10, 10, 10, 10));

        HBox hb1 = new HBox();
        hb1.setSpacing(5);
        hb1.setPadding(new Insets(10, 10, 10, 170));
        hb1.getChildren().addAll(done);

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 190, 0, 150));
        vbox.getChildren().addAll(vb1, empTable, hb1, paid);

        empTable.setItems(empData);

        vb2.getChildren().addAll(vbox);
        vb2.setSpacing(10);
        vb2.setPadding(new Insets(10, 10, 5, 10));

        searchBar.requestFocus();

        FilteredList<EMP> filterCsDue = new FilteredList<>(empData, b -> true);

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

        SortedList<EMP> sortedCsData = new SortedList<>(filterCsDue);

        sortedCsData.comparatorProperty().bind(empTable.comparatorProperty());

        empTable.setItems(sortedCsData);
        //display(expenses);
        stage.show();

        done.setOnAction((ActionEvent e) -> {
            ObservableList<EMP> selecteData = FXCollections.observableArrayList();

            for (EMP bean : empData) {
                if (bean.getCheckbox().isSelected()) {
                    String name = bean.getFirstName();
                    String sal = bean.getPrice();
                    edb.makeWd0(name);
                    mdb.increaseAmount("expenses", Double.parseDouble(sal));
                    display(expenses);

                    String currentDate;
                    java.util.Date date = Calendar.getInstance().getTime();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    currentDate = dateFormat.format(date);
                    insert(name + " Salary", Double.parseDouble(sal), currentDate);

                    selecteData.add(bean);

                    display_EmpAttendTable();
                    empAttendTable.setItems(empAttendData);
                    paid.setStyle("-fx-text-fill:black;");
                    paid.setText("            Wages Entered Successfully");
                }
            }

            empData.removeAll(selecteData);
        });
    }

    public static class EMP {

        private final SimpleStringProperty firstName;
        private final SimpleStringProperty lastName;
        private final SimpleStringProperty email;
        private final SimpleStringProperty Price;
        private CheckBox checkbox;

        private EMP(String fName, String lName, String email, String price) {
            this.firstName = new SimpleStringProperty(fName);
            this.lastName = new SimpleStringProperty(lName);
            this.email = new SimpleStringProperty(email);
            this.Price = new SimpleStringProperty(price);
            this.checkbox = new CheckBox();
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

        public CheckBox getCheckbox() {
            return checkbox;
        }

        public void setSelect(CheckBox checkbox) {
            this.checkbox = checkbox;
        }
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
            int count = 0;

            empData = FXCollections.observableArrayList();

            while (rs.next()) {
                empname = rs.getString("empname");
                wd = rs.getDouble("wd");
                pstmt1.setString(1, empname);
                ResultSet rs1 = pstmt1.executeQuery();
                count++;

                while (rs1.next()) {
                    sal = rs1.getDouble("salary");
                    salary = wd * sal;
                    String empsalary = String.valueOf(salary);
                    String dailySal = String.valueOf(sal);
                    String workingDays = String.valueOf(wd);
                    if (wd == 0) {
                        //do nothing
                    } else {
                        empData.add(new EMP(empname, dailySal, workingDays, empsalary));
                    }
                }
            }
            number.setText(String.valueOf(count));

        } catch (SQLException e) {
            org.apache.logging.log4j.Logger logger = LogManager.getLogger(expenses.class);
            logger.error("An error occurred while calutating employee expenses.", e);
        }
    }

    private Connection connect() {
        // SQLite connection string
        //   String url = "jdbc:sqlite:V://projectBms/bmsemplist1.db";

        String url = Constants.employeeDB;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            org.apache.logging.log4j.Logger logger = LogManager.getLogger(employee.class);
            logger.error("An error occurred while connecting to employee DB.", e);
        }
        return conn;
    }

    public void tax(Stage stage) {

        // Inhertinace to Add this sense in middle in main stage 
        VBox vb2 = super.addnew(stage, product);

        // defining componets 
        Label label, text, l1, l2, today, todayDate;
        TextField tf1, tf2;
        Button b1;
        CheckBox cb1;

        label = new Label("EXPENSES(Tax) ");
        label.setFont(Font.font("Calibri", fw, fp, 22));

        text = new Label("");
        text.setFont(Font.font("Calibri", fw, fp, 20));

        l1 = new Label("Tax Paid               ");
        today = new Label("Paid Today         ");
        l2 = new Label("Else Date            ");
        todayDate = new Label("( " + presentDate + " )");
        l1.setFont(Font.font("Calibri", fw, fp, 20));
        l2.setFont(Font.font("Calibri", fw, fp, 20));
        today.setFont(Font.font("Calibri", fw, fp, 20));
        todayDate.setFont(Font.font("Calibri", fp, 16));

        tf1 = new TextField();
        tf2 = new TextField();
        tf1.setFont(Font.font("Calibri", fw, fp, 16));
        tf2.setFont(Font.font("Calibri", fw, fp, 16));
        tf2.setPromptText("YYYY-MM-DD Format");

        b1 = new Button("Done");
        b1.setFont(Font.font("Calibri", fw, fp, 16));

        cb1 = new CheckBox();

        DatePicker datePicker = new DatePicker();
        datePicker.setPrefSize(20, 10);
        datePicker.setEditable(false);
        StringConverter<LocalDate> converter = getDateConverter();
        datePicker.setConverter(converter);

        StackPane root = new StackPane(tf2, datePicker);
        root.setAlignment(datePicker, Pos.CENTER_RIGHT);

        VBox datePick = new VBox(root);

        //                     Adding componets into Interface via HBox And VBOX 
        HBox hb = new HBox();
        hb.getChildren().addAll(label);
        hb.setSpacing(15);
        hb.setPadding(new Insets(50, 50, 20, 100));

        HBox hb1 = new HBox();
        hb1.getChildren().addAll(l1, tf1);
        hb1.setSpacing(15);
        hb1.setPadding(new Insets(10, 10, 10, 10));

        HBox addCb = new HBox();
        addCb.getChildren().addAll(today, cb1, todayDate);
        addCb.setSpacing(15);
        addCb.setPadding(new Insets(10, 10, 10, 10));

        HBox hb2 = new HBox();
        hb2.getChildren().addAll(l2, datePick);
        hb2.setSpacing(15);
        hb2.setPadding(new Insets(10, 10, 10, 10));

        HBox hb3 = new HBox();
        hb3.getChildren().addAll(b1);
        hb3.setSpacing(15);
        hb3.setPadding(new Insets(10, 10, 10, 200));

        HBox hb4 = new HBox();
        hb4.getChildren().addAll(text);
        hb4.setSpacing(15);
        hb4.setPadding(new Insets(10, 10, 10, 10));

        VBox vb1 = new VBox();
        vb1.getChildren().addAll(hb1, addCb, hb2, hb3, hb4);
        vb1.setSpacing(15);
        vb1.setPadding(new Insets(10, 10, 10, 150));

        VBox vb = new VBox();
        vb.getChildren().addAll(hb, vb1);
        vb.setSpacing(15);
        vb.setPadding(new Insets(10, 160, 100, 50));

        vb2.getChildren().addAll(vb);
        vb2.setSpacing(10);
        vb2.setPadding(new Insets(10, 10, 5, 10));

        stage.show();

        //                                  Actions to be when Button is clicked 
        b1.setOnAction((ActionEvent e) -> {

            String text1 = tf1.getText();
            String text2 = tf2.getText();

            if ((text1 == null || text1.matches("\\s*")) && (text2 == null || text2.matches("\\s*"))) {
                text.setStyle("-fx-text-fill:red;");
                text.setText("Please Enter Data Before Proceeding");
            } else if (text1 == null || text1.matches("\\s*")) {
                text.setStyle("-fx-text-fill:red;");
                text.setText("Please Enter Amount Before Proceeding");
            } else if (!text1.matches("-?\\d+(\\.\\d+)?")) {
                text.setStyle("-fx-text-fill:red;");
                text.setText("Input Mismatch (Please Enter Number, not character)");
            } else if (!cb1.isSelected()) {
                if (text2 == null || text2.matches("\\s*")) {
                    text.setStyle("-fx-text-fill:red;");
                    text.setText("Please Enter Date Before Proceeding");
                } else if (!text2.matches("^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$")) {
                    text.setStyle("-fx-text-fill:red;");
                    text.setText("Please Enter Date In yyyy-mm-dd Format Only");
                    tf2.requestFocus();
                } else {
                    /*   java.util.Date utilDate = Calendar.getInstance().getTime();
                    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                    
                    SimpleDateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
                    dateFormat1.setLenient(false);
                    Date parsedDate = dateFormat1.parse(text2);
                    if (parsedDate != null) {
                        text.setText("");
                    } else {
                        text.setStyle("-fx-text-fill:red;");
                        text.setText("                  Invalid Date");
                        tf2.requestFocus();
                    }
                    /*  dateFormat.setLenient(false);
                    try {
                        dateFormat.parse(text2);
                        text.setText("");
                    } catch (ParseException fe) {
                        text.setStyle("-fx-text-fill:red;");
                        text.setText("                  Invalid Date");
                        tf2.requestFocus();
                    }*/
                    double d1 = Double.parseDouble(text1);
                    mdb.increaseAmount(expenses, d1);
                    String currentDate;
                    if (cb1.isSelected()) {
                        currentDate = dateFormat.format(date);
                        insert("Tax", d1, currentDate);
                    } else {
                        currentDate = text2;
                        insert("Tax", d1, currentDate);
                    }
                    text.setStyle("-fx-text-fill:black;");
                    text.setText("                 Expenses Increased");
                    display(expenses);
                    revenue();
                    tf1.clear();
                    tf2.clear();
                    tf1.requestFocus();
                }
            } else {
                double d1 = Double.parseDouble(text1);
                mdb.increaseAmount(expenses, d1);
                String currentDate;
                if (cb1.isSelected()) {

                    currentDate = dateFormat.format(date);
                    insert("Tax", d1, currentDate);
                } else {
                    currentDate = text2;
                    insert("Tax", d1, currentDate);
                }

                text.setStyle("-fx-text-fill:black;");
                text.setText("                 Expenses Increased");
                display(expenses);
                revenue();
                tf1.clear();
                tf2.clear();
                cb1.fire();
                tf1.requestFocus();
            }
        });

        //                     Setting Foucs Movements when key pressed 
        tf1.requestFocus();

        tf1.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                cb1.requestFocus();
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
                tf1.requestFocus();
            }
            if (event.getCode() == KeyCode.DOWN) {
                tf2.requestFocus();
            }
        });

        tf2.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                b1.fire();
                tf1.requestFocus();
            }
            if (event.getCode() == KeyCode.UP) {
                tf1.requestFocus();
            }
        });

        cb1.setOnAction(event -> {
            tf2.setDisable(cb1.isSelected());
        });

        tf2.textProperty().addListener((observable, oldValue, newValue) -> {
            cb1.setDisable(!newValue.trim().isEmpty());
        });

        // Show the DatePicker when the TextField is focused
        tf2.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                datePicker.show();
            }
        });

        // When the user selects a date, update the text field
        datePicker.setOnAction(event -> {
            tf2.setText(datePicker.getValue().toString());
            datePicker.hide();
        });

        tf1.setTextFormatter(new TextFormatter<>(getUnaryOperator()));

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

    public void bills(Stage stage) {

        VBox vb2 = super.addnew(stage, product);

        Label label, text, l1, l2, l3, l4, todayDate;
        TextField tf1, tf2, tf3;
        Button b1;
        CheckBox cb1;

        label = new Label("EXPENSES(BILLS) ");
        label.setFont(Font.font("Calibri", fw, fp, 22));

        text = new Label("");
        text.setFont(Font.font("Calibri", fw, fp, 20));

        l1 = new Label("Amount Paid         ");
        l2 = new Label("Purpose                 ");
        l3 = new Label("Paid Today          ");
        l4 = new Label("Else Date              ");
        todayDate = new Label("( " + presentDate + " )");
        l1.setFont(Font.font("Calibri", fw, fp, 20));
        l2.setFont(Font.font("Calibri", fw, fp, 20));
        l3.setFont(Font.font("Calibri", fw, fp, 20));
        l4.setFont(Font.font("Calibri", fw, fp, 20));

        tf1 = new TextField();
        tf2 = new TextField();
        tf3 = new TextField();
        tf1.setFont(Font.font("Calibri", fw, fp, 16));
        tf2.setFont(Font.font("Calibri", fw, fp, 16));
        tf3.setFont(Font.font("Calibri", fw, fp, 16));
        tf3.setPromptText("YYYY-MM-DD Format");

        b1 = new Button("Done");
        b1.setFont(Font.font("Calibri", fw, fp, 16));

        cb1 = new CheckBox();

        DatePicker datePicker = new DatePicker();
        datePicker.setPrefSize(20, 10);
        datePicker.setEditable(false);
        StringConverter<LocalDate> converter = getDateConverter();
        datePicker.setConverter(converter);

        StackPane root = new StackPane(tf3, datePicker);
        root.setAlignment(datePicker, Pos.CENTER_RIGHT);

        VBox datePick = new VBox(root);

        HBox hb = new HBox();
        hb.getChildren().addAll(label);
        hb.setSpacing(15);
        hb.setPadding(new Insets(50, 50, 20, 100));

        HBox hb1 = new HBox();
        hb1.getChildren().addAll(l1, tf1);
        hb1.setSpacing(15);
        hb1.setPadding(new Insets(10, 10, 10, 10));

        HBox hb2 = new HBox();
        hb2.getChildren().addAll(l2, tf2);
        hb2.setSpacing(15);
        hb2.setPadding(new Insets(10, 10, 10, 10));

        HBox current = new HBox();
        current.getChildren().addAll(l3, cb1, todayDate);
        current.setSpacing(15);
        current.setPadding(new Insets(10, 10, 10, 10));

        HBox newDate = new HBox();
        newDate.getChildren().addAll(l4, datePick);
        newDate.setSpacing(15);
        newDate.setPadding(new Insets(10, 10, 10, 10));

        HBox hb3 = new HBox();
        hb3.getChildren().addAll(b1);
        hb3.setSpacing(15);
        hb3.setPadding(new Insets(10, 10, 10, 200));

        HBox hb4 = new HBox();
        hb4.getChildren().addAll(text);
        hb4.setSpacing(15);
        hb4.setPadding(new Insets(10, 10, 10, 10));

        VBox vb1 = new VBox();
        vb1.getChildren().addAll(hb1, hb2, current, newDate, hb3, hb4);
        vb1.setSpacing(15);
        vb1.setPadding(new Insets(10, 10, 10, 150));

        VBox vb = new VBox();
        vb.getChildren().addAll(hb, vb1);
        vb.setSpacing(15);
        vb.setPadding(new Insets(10, 150, 100, 50));

        vb2.getChildren().addAll(vb);
        vb2.setSpacing(10);
        vb2.setPadding(new Insets(10, 10, 5, 10));

        stage.show();

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
                text.setText("Please Enter Amount Before Proceeding");
            } else if (text2 == null || text2.matches("\\s*")) {
                text.setStyle("-fx-text-fill:red;");
                text.setText("Please Enter purpose Before Proceeding");
            } else if (!text1.matches("-?\\d+(\\.\\d+)?")) {
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
                    double d1 = Double.parseDouble(text1);
                    mdb.increaseAmount(expenses, d1);
                    String currentDate;
                    currentDate = text3;
                    insert(text2, d1, currentDate);

                    text.setStyle("-fx-text-fill:black;");
                    text.setText("                 Expenses Increased");
                    display(expenses);
                    revenue();
                    tf1.clear();
                    tf2.clear();
                    tf3.clear();

                    tf1.requestFocus();
                }
            } else {
                double d1 = Double.parseDouble(text1);
                mdb.increaseAmount(expenses, d1);
                String currentDate;

                currentDate = dateFormat.format(date);
                insert(text2, d1, currentDate);

                text.setStyle("-fx-text-fill:black;");
                text.setText("                 Expenses Increased");
                display(expenses);
                revenue();
                tf1.clear();
                tf2.clear();
                tf3.clear();
                cb1.fire();
                tf1.requestFocus();
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

        tf1.setTextFormatter(new TextFormatter<>(getUnaryOperator()));

    }

    public void spoiled(Stage stage) {

        VBox vb2 = super.addnew(stage, product);

        Label label, text, l1, l2, l3, l4, todayDate;
        TextField tf1, tf2, tf3;
        Button b1;
        CheckBox cb1;

        label = new Label("EXPENSES(SPOIL)");
        label.setFont(Font.font("Calibri", fw, fp, 22));

        text = new Label("");
        text.setFont(Font.font("Calibri", fw, fp, 20));

        l1 = new Label("Product Name        ");
        l2 = new Label("Quantity Spoiled    ");
        l3 = new Label("Paid Today               ");
        l4 = new Label("Else Date                ");
        todayDate = new Label("( " + presentDate + " )");
        l1.setFont(Font.font("Calibri", fw, fp, 20));
        l2.setFont(Font.font("Calibri", fw, fp, 20));
        l3.setFont(Font.font("Calibri", fw, fp, 20));
        l4.setFont(Font.font("Calibri", fw, fp, 20));

        tf1 = new TextField();
        tf2 = new TextField();
        tf3 = new TextField();
        tf1.setFont(Font.font("Calibri", fw, fp, 16));
        tf2.setFont(Font.font("Calibri", fw, fp, 16));
        tf3.setFont(Font.font("Calibri", fw, fp, 16));
        tf3.setPromptText("YYYY-MM-DD Format");

        b1 = new Button("Done");
        b1.setFont(Font.font("Calibri", fw, fp, 16));

        cb1 = new CheckBox();

        DatePicker datePicker = new DatePicker();
        datePicker.setPrefSize(20, 10);
        datePicker.setEditable(false);
        StringConverter<LocalDate> converter = getDateConverter();
        datePicker.setConverter(converter);

        StackPane root = new StackPane(tf3, datePicker);
        root.setAlignment(datePicker, Pos.CENTER_RIGHT);

        VBox datePick = new VBox(root);

        HBox hb = new HBox();
        hb.getChildren().addAll(label);
        hb.setSpacing(15);
        hb.setPadding(new Insets(50, 50, 20, 100));

        HBox hb1 = new HBox();
        hb1.getChildren().addAll(l1, tf1);
        hb1.setSpacing(15);
        hb1.setPadding(new Insets(10, 10, 10, 10));

        HBox hb2 = new HBox();
        hb2.getChildren().addAll(l2, tf2);
        hb2.setSpacing(15);
        hb2.setPadding(new Insets(10, 10, 10, 10));

        HBox current = new HBox();
        current.getChildren().addAll(l3, cb1, todayDate);
        current.setSpacing(15);
        current.setPadding(new Insets(10, 10, 10, 10));

        HBox getDate = new HBox();
        getDate.getChildren().addAll(l4, datePick);
        getDate.setSpacing(15);
        getDate.setPadding(new Insets(10, 10, 10, 10));

        HBox hb3 = new HBox();
        hb3.getChildren().addAll(b1);
        hb3.setSpacing(15);
        hb3.setPadding(new Insets(10, 10, 10, 200));

        HBox hb4 = new HBox();
        hb4.getChildren().addAll(text);
        hb4.setSpacing(15);
        hb4.setPadding(new Insets(10, 10, 10, 10));

        VBox vb1 = new VBox();
        vb1.getChildren().addAll(hb1, hb2, current, getDate, hb3, hb4);
        vb1.setSpacing(15);
        vb1.setPadding(new Insets(10, 10, 10, 150));

        VBox vb = new VBox();
        vb.getChildren().addAll(hb, vb1);
        vb.setSpacing(15);
        vb.setPadding(new Insets(10, 150, 100, 50));

        vb2.getChildren().addAll(vb);
        vb2.setSpacing(10);
        vb2.setPadding(new Insets(10, 10, 5, 10));

        stage.show();

        b1.setOnAction((ActionEvent e) -> {

            String text1 = tf1.getText();
            String text2 = tf2.getText();
            String text3 = tf3.getText();

            String spoilName = "spoling of " + text1;

            if ((text1 == null || text1.matches("\\s*")) && (text2 == null || text2.matches("\\s*")
                    && (text3 == null || text3.matches("\\s*")))) {
                text.setStyle("-fx-text-fill:red;");
                text.setText("Please Enter Data Before Proceeding");
            } else if (text1 == null || text1.matches("\\s*")) {
                text.setStyle("-fx-text-fill:red;");
                text.setText("Please Enter Name Before Proceeding");
            } else if (text2 == null || text2.matches("\\s*")) {
                text.setStyle("-fx-text-fill:red;");
                text.setText("Please Enter Quantity Before Proceeding");
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
                    double d1 = Double.parseDouble(tf2.getText());
                    int x = db.spoil(tf1.getText(), d1);

                    switch (x) {
                        case 1 -> {
                            text.setStyle("-fx-text-fill:red;");
                            text.setText("         No Such Product Avabile");
                        }
                        case 2 -> {
                            text.setStyle("-fx-text-fill:red;");
                            text.setText("Quantity Entered Is More Than avalaible");
                        }
                        default -> {
                            String currentDate;
                            currentDate = text3;
                            insert(spoilName, d1, currentDate);
                            text.setStyle("-fx-text-fill:black;");
                            text.setText("                 Expenses Increased");
                            display(expenses);
                            display("stockamount");
                            revenue();
                            proTable();
                            table.setItems(data);
                            display_sellTable();
                            Selltable.setItems(Selldata);
                            tf1.clear();
                            tf2.clear();
                            tf3.clear();

                            tf1.requestFocus();
                        }
                    }
                }
            } else {
                double d1 = Double.parseDouble(tf2.getText());
                int x = db.spoil(tf1.getText(), d1);

                switch (x) {
                    case 1 -> {
                        text.setStyle("-fx-text-fill:red;");
                        text.setText("        No Such Product Avabile");
                    }
                    case 2 -> {
                        text.setStyle("-fx-text-fill:red;");
                        text.setText("Quantity Entered Is More Than avalaible");
                    }
                    default -> {
                        String currentDate;

                        currentDate = dateFormat.format(date);
                        insert(spoilName, d1, currentDate);

                        text.setStyle("-fx-text-fill:black;");
                        text.setText("                 Expenses Increased");
                        display(expenses);
                        display("stockamount");
                        revenue();
                        proTable();
                        table.setItems(data);
                        display_sellTable();
                        Selltable.setItems(Selldata);
                        tf1.clear();
                        tf2.clear();
                        tf3.clear();
                        cb1.fire();
                        tf1.requestFocus();
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

        tf2.setTextFormatter(new TextFormatter<>(getUnaryOperator()));

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

        displayProducts();
        TextFields.bindAutoCompletion(tf1, products);
    }

    public void displayProducts() {
        String sql = "SELECT * FROM " + Constants.ProductTable + " ORDER BY stockname";

        try (Connection conn = this.connectPro(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String stockname = rs.getString("stockname");
                products.add(stockname);
            }
        } catch (SQLException e) {
            org.apache.logging.log4j.Logger logger = LogManager.getLogger(employee.class);
            logger.error("An error occurred while adding products to products textfeild", e);
        }
    }

    private Connection connectPro() {
        // SQLite connection string
        //String url = "jdbc:sqlite:V://projectBms/bmsList1.db";
        String url = Constants.productDB;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            org.apache.logging.log4j.Logger logger = LogManager.getLogger(employee.class);
            logger.error("An error occurred while conceting to product DB.", e);
        }
        return conn;
    }

    public void others(Stage stage) {

        VBox vb2 = super.addnew(stage, product);

        Label label, text, l1, l2, l3, l4, todayDate;
        TextField tf1, tf2, tf3;
        Button b1;
        CheckBox cb1;

        label = new Label("EXPENSES(OTHERS) ");
        label.setFont(Font.font("Calibri", fw, fp, 22));

        text = new Label("");
        text.setFont(Font.font("Calibri", fw, fp, 20));

        l1 = new Label("Amount Paid          ");
        l2 = new Label("Purpose                   ");
        l3 = new Label("Paid Today               ");
        l4 = new Label("Else Date                ");
        todayDate = new Label("( " + presentDate + " )");
        l1.setFont(Font.font("Calibri", fw, fp, 20));
        l2.setFont(Font.font("Calibri", fw, fp, 20));
        l3.setFont(Font.font("Calibri", fw, fp, 20));
        l4.setFont(Font.font("Calibri", fw, fp, 20));

        tf1 = new TextField();
        tf2 = new TextField();
        tf3 = new TextField();
        tf1.setFont(Font.font("Calibri", fw, fp, 16));
        tf2.setFont(Font.font("Calibri", fw, fp, 16));
        tf3.setFont(Font.font("Calibri", fw, fp, 16));
        tf3.setPromptText("YYYY-MM-DD Format");

        b1 = new Button("Done");
        b1.setFont(Font.font("Calibri", fw, fp, 16));

        cb1 = new CheckBox();

        DatePicker datePicker = new DatePicker();
        datePicker.setPrefSize(20, 10);
        datePicker.setEditable(false);
        StringConverter<LocalDate> converter = getDateConverter();
        datePicker.setConverter(converter);

        StackPane root = new StackPane(tf3, datePicker);
        root.setAlignment(datePicker, Pos.CENTER_RIGHT);

        VBox datePick = new VBox(root);

        HBox hb = new HBox();
        hb.getChildren().addAll(label);
        hb.setSpacing(15);
        hb.setPadding(new Insets(50, 50, 20, 100));

        HBox hb1 = new HBox();
        hb1.getChildren().addAll(l1, tf1);
        hb1.setSpacing(15);
        hb1.setPadding(new Insets(10, 10, 10, 10));

        HBox hb2 = new HBox();
        hb2.getChildren().addAll(l2, tf2);
        hb2.setSpacing(15);
        hb2.setPadding(new Insets(10, 10, 10, 10));

        HBox current = new HBox();
        current.getChildren().addAll(l3, cb1, todayDate);
        current.setSpacing(15);
        current.setPadding(new Insets(10, 10, 10, 10));

        HBox getDate = new HBox();
        getDate.getChildren().addAll(l4, datePick);
        getDate.setSpacing(15);
        getDate.setPadding(new Insets(10, 10, 10, 10));

        HBox hb3 = new HBox();
        hb3.getChildren().addAll(b1);
        hb3.setSpacing(15);
        hb3.setPadding(new Insets(10, 10, 10, 200));

        HBox hb4 = new HBox();
        hb4.getChildren().addAll(text);
        hb4.setSpacing(15);
        hb4.setPadding(new Insets(10, 10, 10, 10));

        VBox vb1 = new VBox();
        vb1.getChildren().addAll(hb1, hb2, current, getDate, hb3, hb4);
        vb1.setSpacing(15);
        vb1.setPadding(new Insets(10, 10, 10, 150));

        VBox vb = new VBox();
        vb.getChildren().addAll(hb, vb1);
        vb.setSpacing(15);
        vb.setPadding(new Insets(10, 150, 100, 50));

        vb2.getChildren().addAll(vb);
        vb2.setSpacing(10);
        vb2.setPadding(new Insets(10, 10, 5, 10));

        stage.show();

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
                text.setText("Please Enter Amount Before Proceeding");
            } else if (text2 == null || text2.matches("\\s*")) {
                text.setStyle("-fx-text-fill:red;");
                text.setText("Please Enter purpose Before Proceeding");
            } else if (!text1.matches("-?\\d+(\\.\\d+)?")) {
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
                    double d1 = Double.parseDouble(text1);
                    mdb.increaseAmount(expenses, d1);
                    String currentDate;
                    currentDate = text3;
                    insert(text2, d1, currentDate);

                    text.setStyle("-fx-text-fill:black;");
                    text.setText("                 Expenses Increased");
                    display(expenses);
                    revenue();
                    tf1.clear();
                    tf2.clear();
                    tf3.clear();
                    tf1.requestFocus();
                }
            } else {
                double d1 = Double.parseDouble(text1);
                mdb.increaseAmount(expenses, d1);
                String currentDate;

                currentDate = dateFormat.format(date);
                insert(text2, d1, currentDate);

                text.setStyle("-fx-text-fill:black;");
                text.setText("                 Expenses Increased");
                display(expenses);
                revenue();
                tf1.clear();
                tf2.clear();
                tf3.clear();
                cb1.fire();
                tf1.requestFocus();
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

        tf1.setTextFormatter(new TextFormatter<>(getUnaryOperator()));
    }

    public static void createNewTable() {
        // SQLite connection string
        //  String url = "jdbc:sqlite:V://projectBms/managedb1.db";

        String url = Constants.mangeDB;
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS " + Constants.expensesTable + " (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text NOT NULL,\n"
                + "	amount real, \n"
                + "	date text NOT NULL \n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);

            Logger logger = LogManager.getLogger(expenses.class);
            logger.info("Table created");
        } catch (SQLException e) {
            Logger logger = LogManager.getLogger(expenses.class);
            logger.error("An error occurred while creating the table.", e);
        }
    }

    private Connection connectMange() {
        // SQLite connection string
        //  String url = "jdbc:sqlite:V://projectBms/managedb1.db";
        String url = Constants.mangeDB;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            org.apache.logging.log4j.Logger logger = LogManager.getLogger(employee.class);
            logger.error("An error occurred while conceting to Mange DB.", e);
        }
        return conn;
    }

    public int insert(String name, double amount, String date) {

        String sql = "INSERT INTO " + Constants.expensesTable + " (name,amount,date)"
                + " VALUES(?,?,?)";

        String sql1 = "SELECT * "
                + "FROM " + Constants.expensesTable + " WHERE (name,date) = (?,?)";

        String sql2 = "UPDATE " + Constants.expensesTable + " SET amount = ?"
                + "WHERE (name,date) = (?,?)";

        try (Connection conn = this.connectMange(); PreparedStatement pstmt = conn.prepareStatement(sql); PreparedStatement pstmt1 = conn.prepareStatement(sql1); PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {
            pstmt1.setString(1, name);
            pstmt1.setString(2, date);

            ResultSet rs = pstmt1.executeQuery();
            String getName = null;
            String getDate = null;
            double getAmount = 0;

            while (rs.next()) {
                getName = rs.getString("name");
                getAmount = rs.getDouble("amount");
                getDate = rs.getString("date");
            }
            if (name.equals(getName) && date.equals(getDate)) {
                pstmt2.setDouble(1, amount = getAmount + amount);
                pstmt2.setString(2, name);
                pstmt2.setString(3, getDate);

                pstmt2.executeUpdate();
                System.out.println("updated");
                return 1;
            } else {
                pstmt.setString(1, name);
                pstmt.setDouble(2, amount);
                pstmt.setString(3, date);

                pstmt.executeUpdate();
                System.out.println(" Expenses added");
                return 2;
            }
        } catch (SQLException e) {
            org.apache.logging.log4j.Logger logger = LogManager.getLogger(employee.class);
            logger.error("An error occurred while inserting into expenses table.", e);
        }
        return 0;
    }

    public void display() {
        String sql = "SELECT * FROM " + Constants.expensesTable + " ORDER BY date";
        String sql1 = "SELECT COUNT(*) FROM " + Constants.expensesTable;

        try (Connection conn = this.connectMange(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql); Statement stmt1 = conn.createStatement(); ResultSet rs1 = stmt1.executeQuery(sql1)) {

            rs1.next();

            int count = rs1.getInt(1);

            System.out.println("TOTAL NUMBER OF PRODUCTS :" + count);
            System.out.println("\n");

            System.out.println("STOCKNAME                 PRICE  ");

            while (rs.next()) {
                String name = rs.getString("name");
                double amount = rs.getDouble("amount");
                double date = rs.getDouble("date");

                System.out.printf("%-20s", name);
                System.out.printf("     %.2f", amount);
                System.out.printf("     %.2f", date);
                System.out.println();

            }
        } catch (SQLException e) {
          org.apache.logging.log4j.Logger logger = LogManager.getLogger(employee.class);
            logger.error("An error occurred while disaplying expenses list", e);
        }
    }

    public double totalExpenses(String date1, String date2) {

        String sql = "SELECT SUM(amount) FROM " + Constants.expensesTable
                + " WHERE date BETWEEN '" + date1 + "' and '" + date2 + "'";

        double totalExpenese = 0;

        try (Connection conn = this.connectMange(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            totalExpenese = rs.getDouble("SUM(amount)");

        } catch (SQLException e) {
            org.apache.logging.log4j.Logger logger = LogManager.getLogger(employee.class);
            logger.error("An error occurred while disaplying total expenses.", e);
        }
        return totalExpenese;
    }
}
