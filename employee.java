package projectbms;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.sql.*;
import java.util.*;
import java.util.function.UnaryOperator;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.logging.log4j.LogManager;
import org.controlsfx.control.textfield.TextFields;

public class employee extends parentform {

    empDatabase edb = new empDatabase();

    FontWeight fw = FontWeight.BOLD;
    FontPosture fp = FontPosture.REGULAR;

    List<String> employees = new LinkedList<String>();

    private TableView<EMP> attendTable = new TableView<EMP>();
    ObservableList<EMP> attendData;

    public VBox addnew(Stage stage) {

        VBox vb2 = super.addnew(stage, "employee");

        Label label, text, l1, l2;
        TextField tf1, tf2;
        Button b1;

        label = new Label("ENTERING NEW EMPLOYEE ");
        label.setFont(Font.font("Calibri", fw, fp, 22));

        text = new Label("");
        text.setFont(Font.font("Calibri", fw, fp, 20));

        l1 = new Label("Employee Name                 ");
        l2 = new Label("Employee Per Day Salary  ");
        l1.setFont(Font.font("Calibri", fw, fp, 20));
        l2.setFont(Font.font("Calibri", fw, fp, 20));

        tf1 = new TextField();
        tf2 = new TextField();
        tf1.setFont(Font.font("Calibri", fw, fp, 16));
        tf2.setFont(Font.font("Calibri", fw, fp, 16));

        b1 = new Button("Done");
        b1.setFont(Font.font("Calibri", fw, fp, 16));

        HBox hb = new HBox();
        hb.getChildren().addAll(label);
        hb.setSpacing(15);
        hb.setPadding(new Insets(50, 50, 20, 80));

        HBox hb1 = new HBox();
        hb1.getChildren().addAll(l1, tf1);
        hb1.setSpacing(15);
        hb1.setPadding(new Insets(10, 10, 10, 10));

        HBox hb2 = new HBox();
        hb2.getChildren().addAll(l2, tf2);
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
        vb1.getChildren().addAll(hb1, hb2, hb3, hb4);
        vb1.setSpacing(15);
        vb1.setPadding(new Insets(10, 10, 10, 130));

        VBox vb = new VBox();
        vb.getChildren().addAll(hb, vb1);
        vb.setSpacing(15);
        vb.setPadding(new Insets(10, 90, 100, 70));

        vb2.getChildren().addAll(vb);
        vb2.setSpacing(10);
        vb2.setPadding(new Insets(10, 10, 5, 10));

        stage.show();

        b1.setOnAction((ActionEvent e) -> {
            String text1 = tf1.getText().toLowerCase();
            String text2 = tf2.getText();
            if ((text1 == null || text1.matches("\\s*")) && (text2 == null || text2.matches("\\s*"))) {
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
            } else {
                double d1 = Double.parseDouble(tf2.getText());
                int x = edb.insert(tf1.getText().toLowerCase(), d1);

                if (x == 1) {
                    text.setStyle("-fx-text-fill:red;");
                    text.setText("       Employee Already Exist");
                } else if (x == 2) {
                    text.setStyle("-fx-text-fill:black;");
                    text.setText("     Employee Added Successfully");
                    display_EmpList();
                    empTable.setItems(empData);
                    tf1.clear();
                    tf2.clear();
                    tf1.requestFocus();

                   // empTable.setItems(sortedEMPData);
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
                b1.fire();
            }
            if (event.getCode() == KeyCode.UP) {
                tf1.requestFocus();
            }
        });

        tf2.setTextFormatter(new TextFormatter<>(getUnaryOperator()));

        /*  tf2.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost

                if (!tf2.getText().matches("-?\\d+(\\.\\d+)?")) {
                    text.setStyle("-fx-text-fill:red;");
                    text.setText("Input Mismatch (Please Enter Number not charater)");
                    tf2.requestFocus();
                } else {
                    text.setText("");
                }
            }
        });*/
        return null;

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

    Label number;
    Label p, hp, a, pl, hpl, al;
    Button done;
    int presntCount = 0;
    int halfPresntCount = 0;
    int absetCount = 0;

    public void attandnce(Stage stage) {

        VBox vb2 = super.addnew(stage, "employeeAttendnce");
        TextField searchBar = new TextField();

        searchBar.setPrefSize(325, 25);
        searchBar.setStyle("-fx-prompt-text-fill:slategray;-fx-text-fill:black;");
        searchBar.setFont(Font.font("Calibri", fw, fp, 13));
        searchBar.setPromptText("🔍 Enter Name Of The Person");

        attendTable.setEditable(true);
        attendTable.setPrefHeight(380);
        attendTable.setPrefWidth(400);
        attendTable.getStylesheets().add("/projectbms/mycss.css");

        Label label = new Label("ATTENDANCE");
        label.setFont(Font.font("Calibri", fw, fp, 20));

        Label count = new Label("Total Number of Employees :");
        count.setFont(Font.font("Calibri", fw, fp, 20));

        number = new Label();
        number.setFont(Font.font("Calibri", fw, fp, 20));

        done = new Button("Done");
        done.setFont(Font.font("Calibri", fw, fp, 18));

        pl = new Label();
        pl.setFont(Font.font("Calibri", fw, fp, 16));

        hpl = new Label();
        hpl.setFont(Font.font("Calibri", fw, fp, 16));

        al = new Label();
        al.setFont(Font.font("Calibri", fw, fp, 16));

        p = new Label();
        p.setFont(Font.font("Calibri", fw, fp, 16));

        hp = new Label();
        hp.setFont(Font.font("Calibri", fw, fp, 16));

        a = new Label();
        a.setFont(Font.font("Calibri", fw, fp, 16));

        getNames();
        TableColumn Col1 = new TableColumn("Name");
        Col1.setMinWidth(145);
        Col1.setCellValueFactory(
                new PropertyValueFactory<EMP, String>("firstName"));

        TableColumn Col2 = new TableColumn("Present");
        Col2.setMinWidth(80);
        Col2.setCellValueFactory(
                new PropertyValueFactory<EMP, String>("radiobutton1"));

        TableColumn Col3 = new TableColumn("Half Pres");
        Col3.setMinWidth(80);
        Col3.setCellValueFactory(
                new PropertyValueFactory<EMP, String>("radiobutton2"));

        TableColumn Col4 = new TableColumn("Absent");
        Col4.setMinWidth(80);
        Col4.setCellValueFactory(
                new PropertyValueFactory<EMP, String>("radiobutton"));

        attendTable.getColumns().addAll(Col1, Col2, Col3, Col4);

        attendTable.setItems(attendData);

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

        HBox text = new HBox();
        text.setSpacing(5);
        text.setPadding(new Insets(0, 0, 0, 0));
        text.getChildren().addAll(pl, p, hpl, hp, al, a);

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 190, 0, 150));
        vbox.getChildren().addAll(vb1, attendTable, hb1, text);

        attendTable.setItems(attendData);

        vb2.getChildren().addAll(vbox);
        vb2.setSpacing(10);
        vb2.setPadding(new Insets(10, 10, 5, 10));

        searchBar.requestFocus();

        FilteredList<EMP> filterCsDue = new FilteredList<>(attendData, b -> true);

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

        sortedCsData.comparatorProperty().bind(attendTable.comparatorProperty());

        attendTable.setItems(sortedCsData);

        stage.show();

        done.setOnAction((ActionEvent e) -> {
            for (EMP X : attendData) {
                if (X.getRadiobutton1().isSelected()) {
                    String name = X.getFirstName();
                    attandence(name, "p");
                    display_EmpAttendTable();
                    empAttendTable.setItems(empAttendData);
                    presntCount++;
                    X.getRadiobutton1().fire();
                } else if (X.getRadiobutton2().isSelected()) {
                    String name = X.getFirstName();
                    attandence(name, "hp");
                    display_EmpAttendTable();
                    empAttendTable.setItems(empAttendData);
                    halfPresntCount++;
                    X.getRadiobutton2().fire();
                } else if (X.getRadiobutton().isSelected()) {
                    // String name = X.getFirstName();
                    //attandence(name,"a");
                    absetCount++;
                    X.getRadiobutton().fire();
                }
                X.getRadiobutton().fire();
            }
            pl.setText("Total Present =");
            p.setText(String.valueOf(presntCount));
            hpl.setText("            HalfPresent =");
            hp.setText(String.valueOf(halfPresntCount));
            al.setText("              Absent =");
            a.setText(String.valueOf(absetCount));
            
            presntCount=0;
            halfPresntCount=0;
            absetCount=0;

            attendTable.setItems(attendData);

        });
    }

    public static class EMP {

        private final SimpleStringProperty firstName;
        private ToggleGroup toggleGroup;
        private RadioButton radioButton1;
        private RadioButton radioButton2;
        private RadioButton radioButton3;

        private EMP(String fName) {
            this.firstName = new SimpleStringProperty(fName);
            this.toggleGroup = new ToggleGroup();
            this.radioButton1 = new RadioButton();
            this.radioButton2 = new RadioButton();
            this.radioButton3 = new RadioButton();

            // Set the toggle group for the radio buttons
            radioButton1.setToggleGroup(toggleGroup);
            radioButton2.setToggleGroup(toggleGroup);
            radioButton3.setToggleGroup(toggleGroup);

            // Set the default selection for the third column radio buttons
            radioButton3.setSelected(true);
        }

        public String getFirstName() {
            return firstName.get();
        }

        public void setFirstName(String fName) {
            firstName.set(fName);
        }

        public RadioButton getRadiobutton1() {
            return radioButton1;
        }

        public void setSelect1(RadioButton radiobutton) {
            this.radioButton1 = radiobutton;
        }

        public RadioButton getRadiobutton2() {
            return radioButton2;
        }

        public void setSelect2(RadioButton radiobutton) {
            this.radioButton2 = radiobutton;
        }

        public RadioButton getRadiobutton() {
            return radioButton3;
        }

        public void setSelect(RadioButton radiobutton) {
            this.radioButton3 = radiobutton;
        }

    }

    public void getNames() {
        String sql = "SELECT empname,wd FROM " + Constants.employeeAttendanceTable + " ORDER BY empname";
        String sql1 = "SELECT COUNT(*) FROM " + Constants.employeeAttendanceTable + "";

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql); Statement stmt1 = conn.createStatement(); ResultSet rs1 = stmt1.executeQuery(sql1)) {

            number.setText(String.valueOf(rs1.getInt(1)));

            attendData = FXCollections.observableArrayList();

            while (rs.next()) {
                String vendourName = rs.getString("empname");

                attendData.add(new EMP(vendourName));

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void attandence(String name, String state) {
        String sql = "SELECT empname,wd FROM " + Constants.employeeAttendanceTable + " ORDER BY empname";
        String sql1 = "UPDATE " + Constants.employeeAttendanceTable + " SET wd = ?  "
                + "WHERE empname = ?";

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql); PreparedStatement pstmt1 = conn.prepareStatement(sql1)) {

            while (rs.next()) {
                String empname = rs.getString("empname");
                double wd = rs.getDouble("wd");

                if (empname.equals(name)) {

                    if ("p".equals(state)) {
                        wd = wd + 1;
                        pstmt1.setDouble(1, wd);
                        pstmt1.setString(2, empname);
                        pstmt1.executeUpdate();
                    } else if ("hp".equals(state)) {
                        wd = wd + 0.5;
                        pstmt1.setDouble(1, wd);
                        pstmt1.setString(2, empname);
                        pstmt1.executeUpdate();

                    }
                }

            }
        } catch (SQLException e) {
            org.apache.logging.log4j.Logger logger = LogManager.getLogger(employee.class);
            logger.error("An error occurred while displaying the product purchase list.", e);
        }

    }

    private Connection connect() {
        // SQLite connection string
        //String url = "jdbc:sqlite:V://projectBms/bmsemplist1.db";
        String url = Constants.employeeDB;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            org.apache.logging.log4j.Logger logger = LogManager.getLogger(employee.class);
            logger.error("An error occurred while conneting to employee DB", e);
        }
        return conn;
    }

    public void update(Stage stage) {

        VBox vb2 = super.addnew(stage, "employee");

        Label label, text, l1, l2;
        TextField tf1, tf2;
        Button b1;

        label = new Label("UPDATING EMPLOYEE SALARY ");
        label.setFont(Font.font("Calibri", fw, fp, 22));

        text = new Label("");
        text.setFont(Font.font("Calibri", fw, fp, 20));

        l1 = new Label("Employee Name                  ");
        l2 = new Label("Updated Salary                   ");
        l1.setFont(Font.font("Calibri", fw, fp, 20));
        l2.setFont(Font.font("Calibri", fw, fp, 20));

        tf1 = new TextField();
        tf2 = new TextField();
        tf1.setFont(Font.font("Calibri", fw, fp, 16));
        tf2.setFont(Font.font("Calibri", fw, fp, 16));

        b1 = new Button("Done");
        b1.setFont(Font.font("Calibri", fw, fp, 16));

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

        HBox hb3 = new HBox();
        hb3.getChildren().addAll(b1);
        hb3.setSpacing(15);
        hb3.setPadding(new Insets(10, 10, 10, 200));

        HBox hb4 = new HBox();
        hb4.getChildren().addAll(text);
        hb4.setSpacing(15);
        hb4.setPadding(new Insets(10, 10, 10, 10));

        VBox vb1 = new VBox();
        vb1.getChildren().addAll(hb1, hb2, hb3, hb4);
        vb1.setSpacing(15);
        vb1.setPadding(new Insets(10, 10, 10, 150));

        VBox vb = new VBox();
        vb.getChildren().addAll(hb, vb1);
        vb.setSpacing(15);
        vb.setPadding(new Insets(10, 85, 100, 50));

        vb2.getChildren().addAll(vb);
        vb2.setSpacing(10);
        vb2.setPadding(new Insets(10, 10, 5, 10));

        stage.show();

        b1.setOnAction((ActionEvent e) -> {
            String text1 = tf1.getText().toLowerCase();
            String text2 = tf2.getText();
            if ((text1 == null || text1.matches("\\s*")) && (text2 == null || text2.matches("\\s*"))) {
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
            } else {
                double d1 = Double.parseDouble(tf2.getText());
                int x = edb.updateSal(tf1.getText().toLowerCase(), d1);

                if (x == 1) {
                    text.setStyle("-fx-text-fill:red;");
                    text.setText("No Such Employee Exist");
                } else if (x == 2) {
                    display_EmpList();
                    empTable.setItems(empData);
                    text.setStyle("-fx-text-fill:black;");
                    text.setText("Employee Salary Updated Successfully");
                    tf1.clear();
                    tf2.clear();
                    tf1.requestFocus();

                   // empTable.setItems(sortedEMPData);
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
                b1.fire();
            }
            if (event.getCode() == KeyCode.UP) {
                tf1.requestFocus();
            }
        });

        tf2.setTextFormatter(new TextFormatter<>(getUnaryOperator()));

        /*tf2.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost

                if (!tf2.getText().matches("-?\\d+(\\.\\d+)?")) {
                    text.setStyle("-fx-text-fill:red;");
                    text.setText("Input Mismatch (Please Enter Number not charater)");
                    tf2.requestFocus();
                } else {
                    text.setText("");
                }
            }
        });*/
        display();
        TextFields.bindAutoCompletion(tf1, employees);
    }

    public void delete(Stage stage) {

        VBox vb2 = super.addnew(stage, "employee");

        Label label, text, l1;
        TextField tf1;
        Button b1;

        label = new Label("DELETE EMPLOYEE ");
        label.setFont(Font.font("Calibri", fw, fp, 22));

        text = new Label("");
        text.setFont(Font.font("Calibri", fw, fp, 20));

        l1 = new Label("Employee Name to Delete   ");
        l1.setFont(Font.font("Calibri", fw, fp, 20));

        tf1 = new TextField();
        tf1.setFont(Font.font("Calibri", fw, fp, 20));

        b1 = new Button("Done");
        b1.setFont(Font.font("Calibri", fw, fp, 16));
        b1.setPrefSize(70, 30);

        tf1.setPrefSize(250, 20);
        tf1.setFont(Font.font("Calibri", fw, fp, 16));

        HBox hb = new HBox();
        hb.getChildren().addAll(label);
        hb.setSpacing(15);
        hb.setPadding(new Insets(50, 50, 20, 60));

        HBox hb1 = new HBox();
        hb1.getChildren().addAll(l1, tf1);
        hb1.setSpacing(15);
        hb1.setPadding(new Insets(10, 10, 10, 10));

        HBox hb2 = new HBox();
        hb2.getChildren().addAll(text);
        hb2.setSpacing(15);
        hb2.setPadding(new Insets(10, 10, 10, 70));

        HBox hb3 = new HBox();
        hb3.getChildren().addAll(b1);
        hb3.setSpacing(15);
        hb3.setPadding(new Insets(10, 10, 10, 200));

        VBox vb1 = new VBox();
        vb1.getChildren().addAll(hb1, hb3, hb2);
        vb1.setSpacing(15);
        vb1.setPadding(new Insets(10, 10, 10, 100));

        VBox vb = new VBox();
        vb.getChildren().addAll(hb, vb1);
        vb.setSpacing(15);
        vb.setPadding(new Insets(10, 70, 100, 50));

        vb2.getChildren().addAll(vb);
        vb2.setSpacing(10);
        vb2.setPadding(new Insets(10, 10, 5, 10));

        stage.show();

        b1.setOnAction((ActionEvent e) -> {
            String text1 = tf1.getText().toLowerCase();

            if (text1 == null || text1.matches("\\s*")) {
                text.setStyle("-fx-text-fill:red;");
                text.setText("Please Enter Name Before Proceeding");
            } else {
                int x = edb.delete(tf1.getText().toLowerCase());

                if (x == 1) {
                    text.setStyle("-fx-text-fill:red;");
                    text.setText("No Such Employee Exist");
                } else if (x == 2) {
                    text.setStyle("-fx-text-fill:black;");
                    text.setText("Employee Deleted Successfully");
                    display_EmpList();
                    empTable.setItems(empData);
                    tf1.clear();
                    tf1.requestFocus();

                   // empTable.setItems(sortedEMPData);
                }
            }
        });

        tf1.requestFocus();

        tf1.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                b1.fire();
            }
        });

        display();
        TextFields.bindAutoCompletion(tf1, employees);

    }

    public void display() {
        String sql = "SELECT empname FROM " + Constants.employeeTable + " ORDER BY empname";

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String empname = rs.getString("empname");
                employees.add(empname);

            }
        } catch (SQLException e) {
            org.apache.logging.log4j.Logger logger = LogManager.getLogger(employee.class);
            logger.error("An error occurred while adding employee to textfeild suggustions.", e);
        }
    }
}
