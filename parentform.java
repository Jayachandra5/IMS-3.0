package projectbms;

import java.util.Formatter;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.*;
import javafx.geometry.HPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class parentform {

    dataBase db = new dataBase();
    Product pro = null;
    employee emp = null;
    expenses exp = null;
    sellTab sTable = null;
    duesTable dueTable = null;
    Data info = null;
    Create create = null;
    Reports report = null;
    dataVisualisation vis = null;

    TableView<Person> table = new TableView<Person>();
    ObservableList<Person> data;

    TableView<Sell> Selltable = new TableView<Sell>();
    ObservableList<Sell> Selldata;

    TableView<EMP> empTable = new TableView<EMP>();
    ObservableList<EMP> empData;

    TableView<empAttend> empAttendTable = new TableView<empAttend>();
    ObservableList<empAttend> empAttendData;

    Label label, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12;

    TextField searchBar1, searchBar2;

    SortedList<Sell> sortedSellData;
    SortedList<Person> sortedData;
    SortedList<EMP> sortedEMPData;
    SortedList<empAttend> sortedEmpAttandnce;

    public VBox addnew(Stage stage, String rightContent) {

        int screenWidth = (int) Screen.getPrimary().getBounds().getWidth();
        int screenHeight = (int) Screen.getPrimary().getBounds().getHeight();

        FontWeight fw = FontWeight.BOLD;
        FontPosture fp = FontPosture.REGULAR;

        int sceneWidth = screenWidth;
        int sceneHeight = screenHeight;

        HBox hb1, hb3, hb4, hb5;

        Menu mu1, mu2, mu3, mu4, mu5, mu6, mu7;
        MenuBar mb1, mb2, mb3, mb4, mb5, mb6, mb7;
        MenuItem m11, m12, m13,m14;
        MenuItem m21, m22, m23, m24, m25;
        MenuItem m31, m32, m35, m36;
        MenuItem m41, m42, m43, m44, m45, m46;
        MenuItem m51, m52, m53, m54, m55, m56, m57, m58;
        MenuItem m61, m62;
        MenuItem m71, m72, m73, m74, m75;

        label = new Label("                                                      "
                + "                                                             "
                + "     INVENTORY MANAGEMENT SYSTEM ( I.M.S)");

        label.setStyle("-fx-background-color:DODGERBLUE;");
        label.setPrefSize(sceneWidth, 20);
        label.setFont(Font.font("Calibri", fw, fp, 18));

        hb1 = new HBox(label);

        b1 = new Label("Stock Amount");
        b2 = new Label("Total Sales");
        b3 = new Label("Expenses");
        b4 = new Label("Revenue");
        b5 = new Label("Profit");
        b6 = new Label("Tax");

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));
        gridPane.setPrefWidth(sceneWidth);

// Add column headings
        gridPane.add(b1, 0, 0);
        gridPane.add(b2, 1, 0);
        gridPane.add(b3, 2, 0);
        gridPane.add(b4, 3, 0);
        gridPane.add(b5, 4, 0);
        gridPane.add(b6, 5, 0);

// Add labels under their respective headings
        b7 = new Label("");
        display("stockamount");
        gridPane.add(b7, 0, 1);

        b8 = new Label("");
        display("totalsales");
        gridPane.add(b8, 1, 1);

        b9 = new Label("");
        display("expenses");
        gridPane.add(b9, 2, 1);

        b10 = new Label("");
        display("profit");
        gridPane.add(b10, 3, 1);

        b11 = new Label("");
        revenue();
        gridPane.add(b11, 4, 1);

        b12 = new Label("");
        display("tax");
        gridPane.add(b12, 5, 1);

// Align labels in the center of their cells
        GridPane.setHalignment(b1, HPos.LEFT);
        GridPane.setHalignment(b2, HPos.CENTER);
        GridPane.setHalignment(b3, HPos.CENTER);
        GridPane.setHalignment(b4, HPos.CENTER);
        GridPane.setHalignment(b5, HPos.CENTER);
        GridPane.setHalignment(b6, HPos.RIGHT);
        GridPane.setHalignment(b7, HPos.LEFT);
        GridPane.setHalignment(b8, HPos.CENTER);
        GridPane.setHalignment(b9, HPos.CENTER);
        GridPane.setHalignment(b10, HPos.CENTER);
        GridPane.setHalignment(b11, HPos.CENTER);
        GridPane.setHalignment(b12, HPos.RIGHT);

// Set the width of the columns
        for (int i = 0; i < 6; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(sceneWidth / 6);
            gridPane.getColumnConstraints().add(column);
        }
        // Adjust spacing for b2 to b5 and b8 to b11
        int columnSpacing = (sceneWidth / 6) - 50;
        gridPane.setHgap(columnSpacing);

        hb3 = new HBox();
        hb3.getChildren().addAll(gridPane);
        hb3.setPrefWidth(sceneWidth);

        mu1 = new Menu("      Create      ");
        mu2 = new Menu("      Product      ");
        mu3 = new Menu("      Employee      ");
        mu4 = new Menu("      Expenses      ");
        mu5 = new Menu("      Dues      ");
        mu6 = new Menu("      Data      ");
        mu7 = new Menu("      Reports      ");

        m11 = new MenuItem("Date Base");
        m12 = new MenuItem("Table");
        m13 = new MenuItem("Help");
        m14 = new MenuItem("Bill Header");

        m21 = new MenuItem("Add New");
        m22 = new MenuItem("Purchase");
        m23 = new MenuItem("Sell");
        m24 = new MenuItem("Update");
        m25 = new MenuItem("Delete");

        m31 = new MenuItem("Add New");
        m32 = new MenuItem("Attandence");
        m35 = new MenuItem("Update");
        m36 = new MenuItem("Delete");

        m41 = new MenuItem("Rent");
        m42 = new MenuItem("Wages");
        m43 = new MenuItem("Tax");
        m44 = new MenuItem("Bills Paid");
        m45 = new MenuItem("Spoiled");
        m46 = new MenuItem("Others");

        m51 = new MenuItem("Total Due");
        m52 = new MenuItem("Customer List");
        m53 = new MenuItem("Vendor List");
        m54 = new MenuItem("Employee List");
        m55 = new MenuItem("Clear Vendor Due");
        m56 = new MenuItem("Clear Customer Due");
        m57 = new MenuItem("Add Emp Due");
        m58 = new MenuItem("Clear Emp Due");

        m61 = new MenuItem("Vendor List");
        m62 = new MenuItem("Customer List");

        m71 = new MenuItem("Purchase List");
        m72 = new MenuItem("Sales List");
        m73 = new MenuItem("Expenses List");
        m74 = new MenuItem("Report");
        m75 = new MenuItem("Visulaize");

        mu1.getItems().addAll(m11, m12, m13,m14);
        mu2.getItems().addAll(m21, m22, m23, m24, m25);
        mu3.getItems().addAll(m31, m32, m35, m36);
        mu4.getItems().addAll(m41, m42, m43, m44, m45, m46);
        mu5.getItems().addAll(m51, m52, m53, m54, m55, m56, m57, m58);
        mu6.getItems().addAll(m61, m62);
        mu7.getItems().addAll(m71, m72, m73, m74, m75);

        mb1 = new MenuBar();
        mb2 = new MenuBar();
        mb3 = new MenuBar();
        mb4 = new MenuBar();
        mb5 = new MenuBar();
        mb6 = new MenuBar();
        mb7 = new MenuBar();

        mb1.getMenus().add(mu1);
        mb2.getMenus().add(mu2);
        mb3.getMenus().add(mu3);
        mb4.getMenus().add(mu4);
        mb5.getMenus().add(mu5);
        mb6.getMenus().add(mu6);
        mb7.getMenus().add(mu7);

        hb4 = new HBox(mb1, mb2, mb3, mb4, mb5, mb6, mb7);

        searchBar1 = new TextField();
        searchBar2 = new TextField();

        int seter = sceneWidth - 800;
        int rightSeter = (seter / 2);
        int leftSeter = (seter / 2);

        searchBar1.setPrefSize(rightSeter, 25);
        searchBar2.setPrefSize(leftSeter, 25);

        searchBar1.setStyle("-fx-background-color:DODGERBLUE;-fx-prompt-text-fill:black;-fx-text-fill:black;");
        searchBar2.setStyle("-fx-background-color:DODGERBLUE;-fx-prompt-text-fill:black;-fx-text-fill:black;");

        searchBar1.setFont(Font.font("Calibri", fw, fp, 13));
        searchBar2.setFont(Font.font("Calibri", fw, fp, 13));

        searchBar1.setPromptText("🔍 Enter Product Name");
        searchBar2.setPromptText("🔍 Enter Product Name");

        hb5 = new HBox();
        hb5.getChildren().addAll(searchBar1, hb4, searchBar2);
        hb5.setPrefWidth(sceneWidth);

        proTable();

        table.setEditable(true);
        table.setPrefHeight(sceneHeight - 200);
        table.setPrefWidth(seter / 2);
        table.setStyle("-fx-background-color:white;");

        TableColumn firstNameCol = new TableColumn("Name");
        firstNameCol.setMinWidth((rightSeter / 5) + 50);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("firstName"));

        TableColumn lastNameCol = new TableColumn("Quantity");
        lastNameCol.setMinWidth((rightSeter / 5) - 15);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("lastName"));

        TableColumn emailCol = new TableColumn("MRP");
        emailCol.setMinWidth((rightSeter / 5) - 15);
        emailCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("email"));

        TableColumn PriceCol = new TableColumn("Price");
        PriceCol.setMinWidth((rightSeter / 5) - 15);
        PriceCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("Price"));

        TableColumn TaxCol = new TableColumn("TAX%");
        TaxCol.setMinWidth((rightSeter / 5) - 15);
        TaxCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("Tax"));

        table.getColumns().addAll(firstNameCol, lastNameCol, emailCol, PriceCol, TaxCol);

        Label label_, text, l_1, l_2, l_3, l_4;
        TextField tf1, tf2, tf3;
        CheckBox cb1;
        Button b_1, b_2;

        label_ = new Label("PRODUCTS SELLING ");
        label_.setFont(Font.font("Calibri", fw, fp, 20));

        text = new Label("");
        text.setFont(Font.font("Calibri", fw, fp, 20));

        l_1 = new Label("Enter Product Name               ");
        l_2 = new Label("Enter Quantity                         ");
        l_3 = new Label("Sell With Fedded Amount     ");
        l_4 = new Label("Enter Selling Price(For 1 unit)");
        l_1.setFont(Font.font("Calibri", fw, fp, 18));
        l_2.setFont(Font.font("Calibri", fw, fp, 18));
        l_3.setFont(Font.font("Calibri", fw, fp, 18));
        l_4.setFont(Font.font("Calibri", fw, fp, 18));

        tf1 = new TextField();
        tf2 = new TextField();
        tf3 = new TextField();
        tf1.setFont(Font.font("Calibri", fw, fp, 18));
        tf2.setFont(Font.font("Calibri", fw, fp, 18));
        tf3.setFont(Font.font("Calibri", fw, fp, 18));

        cb1 = new CheckBox();

        b_1 = new Button("Add Product");
        b_1.setFont(Font.font("Calibri", fw, fp, 18));
        b_2 = new Button("Proceed");
        b_2.setFont(Font.font("Calibri", fw, fp, 18));

        HBox hb = new HBox();
        hb.getChildren().add(label_);
        hb.setSpacing(15);
        hb.setPadding(new Insets(30, 30, 30, 100));

        HBox hb_1 = new HBox();
        hb_1.getChildren().addAll(l_1, tf1);
        hb_1.setSpacing(15);
        hb_1.setPadding(new Insets(10, 10, 10, 10));

        HBox hb_2 = new HBox();
        hb_2.getChildren().addAll(l_2, tf2);
        hb_2.setSpacing(15);
        hb_2.setPadding(new Insets(10, 10, 10, 10));

        HBox hb_3 = new HBox();
        hb_3.getChildren().addAll(l_3, cb1);
        hb_3.setSpacing(15);
        hb_3.setPadding(new Insets(10, 10, 10, 10));

        HBox hb_4 = new HBox();
        hb_4.getChildren().addAll(l_4, tf3);
        hb_4.setSpacing(15);
        hb_4.setPadding(new Insets(10, 10, 10, 10));

        HBox hb_5 = new HBox();
        hb_5.getChildren().addAll(b_1, b_2);
        hb_5.setSpacing(15);
        hb_5.setPadding(new Insets(10, 10, 10, 100));

        HBox hb_6 = new HBox();
        hb_6.getChildren().addAll(text);
        hb_6.setSpacing(15);
        hb_6.setPadding(new Insets(10, 10, 10, 200));

        VBox vb1 = new VBox();
        vb1.getChildren().addAll(hb, hb_1, hb_2, hb_3, hb_4, hb_5);
        vb1.setSpacing(15);
        vb1.setPadding(new Insets(10, 140, 10, 130));

        VBox v_b = new VBox();
        v_b.getChildren().addAll(hb, vb1, hb_6);
        v_b.setSpacing(15);
        v_b.setPadding(new Insets(10, 10, 10, 10));

        Selltable.setEditable(true);
        Selltable.setPrefHeight(sceneHeight - 200);
        Selltable.setPrefWidth(leftSeter);
        Selltable.setStyle("-fx-background-color:white;");

        empTable.setEditable(true);
        empTable.setPrefHeight(sceneHeight - 200);
        empTable.setPrefWidth(leftSeter);
        empTable.setStyle("-fx-background-color:white;");

        empAttendTable.setEditable(true);
        empAttendTable.setPrefHeight(sceneHeight - 200);
        empAttendTable.setPrefWidth(leftSeter);
        empAttendTable.setStyle("-fx-background-color:white;");

        VBox rightvboxST = new VBox();

        if (rightContent == "product") {
            display_sellTable();
            TableColumn Col1 = new TableColumn("Name");
            Col1.setMinWidth((leftSeter/4)+50);
            Col1.setCellValueFactory(
                    new PropertyValueFactory<Sell, String>("firstName"));

            TableColumn Col2 = new TableColumn("Price");
            Col2.setMinWidth((leftSeter/4)-20);
            Col2.setCellValueFactory(
                    new PropertyValueFactory<Sell, String>("lastName"));

            TableColumn Col3 = new TableColumn("Quantity");
            Col3.setMinWidth(70);
            Col3.setCellValueFactory(
                    new PropertyValueFactory<Sell, String>("email"));

            TableColumn Col4 = new TableColumn("MRP");
            Col4.setMinWidth((leftSeter / 4) - 20);
            Col4.setCellValueFactory(
                    new PropertyValueFactory<Sell, String>("mrp"));

            Selltable.getColumns().addAll(Col1, Col2, Col3,Col4);

            rightvboxST.setSpacing(5);
            rightvboxST.setPadding(new Insets(0, 0, 0, 0));
            rightvboxST.getChildren().addAll(Selltable);
            Selltable.setItems(Selldata);

            FilteredList<Sell> filterSellData = new FilteredList<>(Selldata, b -> true);

            searchBar2.textProperty().addListener((observable, oldValue, newValue) -> {
                filterSellData.setPredicate(Sell -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (Sell.getFirstName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else {
                        return false;
                    }
                });
            });

            sortedSellData = new SortedList<>(filterSellData);

            sortedSellData.comparatorProperty().bind(Selltable.comparatorProperty());

            Selltable.setItems(sortedSellData);

        } else if (rightContent == "employee") {

            searchBar2.setPromptText("🔍 Enter Employee Name");

            display_EmpList();
            TableColumn Col1 = new TableColumn("Name");
            Col1.setMinWidth(140);
            Col1.setCellValueFactory(
                    new PropertyValueFactory<EMP, String>("firstName"));

            TableColumn Col2 = new TableColumn("Daily Salary");
            Col2.setMinWidth(95);
            Col2.setCellValueFactory(
                    new PropertyValueFactory<EMP, String>("lastName"));

            empTable.getColumns().addAll(Col1, Col2);

            rightvboxST.setSpacing(5);
            rightvboxST.setPadding(new Insets(0, 0, 0, 0));
            rightvboxST.getChildren().addAll(empTable);
            empTable.setItems(empData);

            FilteredList<EMP> filterSellData = new FilteredList<>(empData, b -> true);

            searchBar2.textProperty().addListener((observable, oldValue, newValue) -> {
                filterSellData.setPredicate(EMP -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (EMP.getFirstName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else {
                        return false;
                    }
                });
            });

            sortedEMPData = new SortedList<>(filterSellData);

            sortedEMPData.comparatorProperty().bind(empTable.comparatorProperty());

            empTable.setItems(sortedEMPData);

        } else if (rightContent == "employeeAttendnce") {

            searchBar2.setPromptText("🔍 Enter Employee Name");
            display_EmpAttendTable();

            TableColumn Col1 = new TableColumn("Name");
            Col1.setMinWidth(140);
            Col1.setCellValueFactory(
                    new PropertyValueFactory<empAttend, String>("firstName"));

            TableColumn Col2 = new TableColumn("Working Days");
            Col2.setMinWidth(95);
            Col2.setCellValueFactory(
                    new PropertyValueFactory<empAttend, String>("lastName"));

            empAttendTable.getColumns().addAll(Col1, Col2);

            rightvboxST.setSpacing(5);
            rightvboxST.setPadding(new Insets(0, 0, 0, 0));
            rightvboxST.getChildren().addAll(empAttendTable);
            empAttendTable.setItems(empAttendData);

            FilteredList<empAttend> filterEmpData = new FilteredList<>(empAttendData, b -> true);

            searchBar2.textProperty().addListener((observable, oldValue, newValue) -> {

                filterEmpData.setPredicate(empAttend -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (empAttend.getFirstName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else {
                        return false;
                    }
                });
            });

            sortedEmpAttandnce = new SortedList<>(filterEmpData);

            sortedEmpAttandnce.comparatorProperty().bind(empAttendTable.comparatorProperty());

            empAttendTable.setItems(sortedEmpAttandnce);
        }

        VBox leftvbox = new VBox();
        leftvbox.setSpacing(5);
        leftvbox.setPadding(new Insets(0, 0, 0, 0));
        leftvbox.getChildren().addAll(table);
        table.setItems(data);

        VBox header = new VBox();
        header.setSpacing(10);
        header.setPadding(new Insets(10, 10, 10, 10));
        header.getChildren().addAll(hb1, hb3, hb5);

        VBox centerBox = new VBox();

        HBox downHbox = new HBox();
        downHbox.getChildren().addAll(leftvbox, centerBox, rightvboxST);
        downHbox.setPadding(new Insets(0, 0, 0, 0));

        VBox full = new VBox();
        full.setSpacing(10);
        full.setPadding(new Insets(10, 10, 10, 10));
        full.getChildren().addAll(hb1, hb3, hb5, downHbox);

        Scene scene = new Scene(full, sceneWidth, sceneHeight - 70);
        stage.setScene(scene);

        FilteredList<Person> filterdBuyData = new FilteredList<>(data, b -> true);

        searchBar1.textProperty().addListener((observable, oldValue, newValue) -> {
            filterdBuyData.setPredicate(Person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (Person.getFirstName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        sortedData = new SortedList<>(filterdBuyData);

        sortedData.comparatorProperty().bind(table.comparatorProperty());

        table.setItems(sortedData);

        // Create 
        m11.setOnAction((ActionEvent e) -> {
            getCreate().createDb(stage);
        });

        m12.setOnAction((ActionEvent e) -> {
            getCreate().createTable(stage);
        });

        m13.setOnAction((ActionEvent e) -> {

            getCreate().Help(stage);
        });
        
         m14.setOnAction((ActionEvent e) -> {

            getCreate().billData(stage);
        });

        // Product 
        m21.setOnAction((ActionEvent e) -> {
            getProduct().addnew(stage);
        });

        m22.setOnAction((ActionEvent e) -> {
            getProduct().purchase(stage);
        });

        m23.setOnAction((ActionEvent e) -> {
            getProduct().sell(stage);
        });

        m24.setOnAction((ActionEvent e) -> {
            getProduct().update(stage);
        });

        m25.setOnAction((ActionEvent e) -> {
            getProduct().delete(stage);
        });

        // employee
        m31.setOnAction((ActionEvent e) -> {
            getemployee().addnew(stage);
        });

        m32.setOnAction((ActionEvent e) -> {
            getemployee().attandnce(stage);
        });

        m35.setOnAction((ActionEvent e) -> {
            getemployee().update(stage);
        });

        m36.setOnAction((ActionEvent e) -> {
            getemployee().delete(stage);
        });

        // Expenses
        m41.setOnAction((ActionEvent e) -> {
            getexpenses().rent(stage);
        });

        m42.setOnAction((ActionEvent e) -> {
            getexpenses().wages(stage);
        });

        m43.setOnAction((ActionEvent e) -> {
            getexpenses().tax(stage);
        });

        m44.setOnAction((ActionEvent e) -> {
            getexpenses().bills(stage);
        });

        m45.setOnAction((ActionEvent e) -> {
            getexpenses().spoiled(stage);
        });

        m46.setOnAction((ActionEvent e) -> {
            getexpenses().others(stage);
        });

        // Dues
        m51.setOnAction((ActionEvent e) -> {
            getDueTable().totalDue(stage);
        });

        m52.setOnAction((ActionEvent e) -> {
            getDueTable().customerDueTable(stage);
        });

        m53.setOnAction((ActionEvent e) -> {
            getDueTable().VendourTable(stage);
        });

        m54.setOnAction((ActionEvent e) -> {
            getDueTable().EmpDueTable(stage);
        });

        m55.setOnAction((ActionEvent e) -> {
            getDueTable().VendourClearDue(stage);
        });

        m56.setOnAction((ActionEvent e) -> {
            getDueTable().CustomerClearDue(stage);
        });

        m57.setOnAction((ActionEvent e) -> {
            getDueTable().EmpAddDue(stage);
        });

        m58.setOnAction((ActionEvent e) -> {
            getDueTable().EmpClearDue(stage);
        });

        //DATA
        m61.setOnAction((ActionEvent e) -> {
            getData().vendourTable(stage);
        });

        m62.setOnAction((ActionEvent e) -> {
            getData().customerTable(stage);
        });

        //REPORTS 
        m71.setOnAction((ActionEvent e) -> {
            getReport().purchaseTable(stage);
        });

        m72.setOnAction((ActionEvent e) -> {
            getReport().SalesTable(stage);
        });

        m73.setOnAction((ActionEvent e) -> {
            getReport().expensesTable(stage);
        });

        m74.setOnAction((ActionEvent e) -> {
            getReport().report(stage);
        });

        m75.setOnAction((ActionEvent e) -> {
            getdataVisualisation().singleUnit(stage);
        });

        return centerBox;
    }

    public Product getProduct() {
        if (pro == null) {
            pro = new Product();
        }
        return pro;
    }

    public employee getemployee() {
        if (emp == null) {
            emp = new employee();
        }
        return emp;
    }

    public expenses getexpenses() {
        if (exp == null) {
            exp = new expenses();
        }
        return exp;
    }

    public sellTab getsellTab() {
        if (sTable == null) {
            sTable = new sellTab();
        }
        return sTable;
    }

    public duesTable getDueTable() {
        if (dueTable == null) {
            dueTable = new duesTable();
        }
        return dueTable;
    }

    public Data getData() {
        if (info == null) {
            info = new Data();
        }
        return info;
    }

    public Create getCreate() {
        if (create == null) {
            create = new Create();
        }
        return create;
    }

    public Reports getReport() {
        if (report == null) {
            report = new Reports();
        }
        return report;
    }

    public dataVisualisation getdataVisualisation() {
        if (vis == null) {
            vis = new dataVisualisation();
        }
        return vis;
    }

    public void display(String name) {
        String sql = "SELECT  * "
                + "FROM " + Constants.manage + " WHERE name = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);

            ResultSet rs = pstmt.executeQuery();

            double price;

            Formatter formatter = new Formatter();

            while (rs.next()) {
                price = rs.getDouble("amount");
                formatter.format("%.2f", price);
            }

            String stockamount = String.valueOf(formatter.toString());

            if (name.equals("stockamount")) {
                b7.setText(stockamount);
            }
            if (name.equals("totalsales")) {
                b8.setText(stockamount);
            }
            if (name.equals("expenses")) {
                b9.setText(stockamount);
            }
            if (name.equals("profit")) {
                b10.setText(stockamount);
            }
            if (name.equals("tax")) {
                b12.setText(stockamount);
            }

        } catch (SQLException e) {
            Logger logger = LogManager.getLogger(parentform.class);
            logger.error("An error occurred while displaying amounts.", e);
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

            Formatter formatter = new Formatter();

            while (rs.next()) {
                profit = rs.getDouble("amount");
            }

            double expenses = 0;
            while (rs1.next()) {
                expenses = rs1.getDouble("amount");
            }

            double revenue = profit - expenses;
            formatter.format("%.2f", revenue);

            String Rev = String.valueOf(formatter.toString());
            b11.setText(Rev);

        } catch (SQLException e) {
            Logger logger = LogManager.getLogger(parentform.class);
            logger.error("An error occurred while displaying revnue.", e);
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
            Logger logger = LogManager.getLogger(parentform.class);
            logger.error("An error occurred while conneting to mange db.", e);
        }
        return conn;
    }

    public void proTable() {

        String sql = "SELECT * FROM " + Constants.ProductTable + " ORDER BY stockname";
        String sql1 = "SELECT COUNT(*) FROM " + Constants.ProductTable + "";

        try (Connection conn = this.connect_2(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql); Statement stmt1 = conn.createStatement(); ResultSet rs1 = stmt1.executeQuery(sql1)) {

            rs1.next();

            int count = rs1.getInt(1);

            //  System.out.println("TOTAL NUMBER OF PRODUCTS :" + count);
            data = FXCollections.observableArrayList();

            while (rs.next()) {
                String stockname = rs.getString("stockname");
                double quantity = rs.getDouble("quantity");
                double mrp = rs.getDouble("mrp");
                double price = rs.getDouble("price");
                double tax = rs.getDouble("tax");

                String qnt = String.format("%.1f", quantity);
                String mrp1 = String.format("%.1f", mrp);
                String price1 = String.format("%.1f", price);
                String tax1 = String.format("%.1f", tax);

                data.add(new Person(stockname, qnt, mrp1, price1, tax1));
            }
        } catch (SQLException e) {
            Logger logger = LogManager.getLogger(parentform.class);
            logger.error("An error occurred while displaying product table.", e);
        }
    }

    public static class Person {

        private final SimpleStringProperty firstName;
        private final SimpleStringProperty lastName;
        private final SimpleStringProperty email;
        private final SimpleStringProperty Price;
        private final SimpleStringProperty Tax;

        private Person(String fName, String lName, String email, String price, String tax) {
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

    private Connection connect_2() {
        // SQLite connection string
        // String url = "jdbc:sqlite:V://projectBms/bmsList1.db";
        String url = Constants.productDB;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            Logger logger = LogManager.getLogger(parentform.class);
            logger.error("An error occurred while conneting to product DB.", e);
        }
        return conn;
    }

    public static class Sell {

        private final SimpleStringProperty firstName;
        private final SimpleStringProperty lastName;
        private final SimpleStringProperty email;
         private final SimpleStringProperty mrp;

        private Sell(String fName, String lName, String email,String mrp) {
            this.firstName = new SimpleStringProperty(fName);
            this.lastName = new SimpleStringProperty(lName);
            this.email = new SimpleStringProperty(email);
            this.mrp = new SimpleStringProperty(mrp);
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
        public String getMrp() {
            return mrp.get();
        }

        public void setMrp(String fName) {
            mrp.set(fName);
        }

    }

    public void display_sellTable() {
        String sql = "SELECT * FROM " + Constants.sellTable + " ORDER BY stockname";
        String sql1 = "SELECT COUNT(*) " + Constants.sellTable;

        try (Connection conn = this.connect_2(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql); Statement stmt1 = conn.createStatement(); ResultSet rs1 = stmt1.executeQuery(sql1)) {

            rs1.next();

            int count = rs1.getInt(1);

            //  System.out.println("TOTAL NUMBER OF PRODUCTS :" + count);
            Selldata = FXCollections.observableArrayList();

            while (rs.next()) {
                String stockname = rs.getString("stockname");
                double price = rs.getDouble("price");
                double qnt = rs.getDouble("qnt");
                double mrp = rs.getDouble("mrp");

                String price1 = String.format("%.1f", price);
                String qnt1 = String.format("%.1f", qnt);
                String mrp1 = String.format("%.1f", mrp);

                Selldata.add(new Sell(stockname, price1, qnt1,mrp1));

            }
        } catch (SQLException e) {
            Logger logger = LogManager.getLogger(parentform.class);
            logger.error("An error occurred while displaying sales table", e);
        }
    }

    public static class EMP {

        private final SimpleStringProperty firstName;
        private final SimpleStringProperty lastName;

        private EMP(String fName, String lName) {
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

    public void display_EmpList() {

        String sql = "SELECT id,empname,salary FROM " + Constants.employeeTable + " ORDER BY empname";
        String sql1 = "SELECT COUNT(*) FROM " + Constants.employeeTable;

        try (Connection conn = this.connectEmp(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql); Statement stmt1 = conn.createStatement(); ResultSet rs1 = stmt1.executeQuery(sql1)) {

            rs1.next();

            int count = rs1.getInt(1);

            // System.out.println("TOTAL NUMBER OF EMPLOYEES :"+count);
            empData = FXCollections.observableArrayList();

            while (rs.next()) {
                String empname = rs.getString("empname");
                double salary = rs.getDouble("salary");

                String empsalary = String.format("%.1f", salary);

                empData.add(new EMP(empname, empsalary));
            }
        } catch (SQLException e) {
            Logger logger = LogManager.getLogger(parentform.class);
            logger.error("An error occurred while displaying employees", e);
        }
    }

    private Connection connectEmp() {
        // SQLite connection string
        // String url = "jdbc:sqlite:V://projectBms/bmsemplist1.db";
        String url = Constants.employeeDB;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            Logger logger = LogManager.getLogger(parentform.class);
            logger.error("An error occurred connting to employee DB.", e);
        }
        return conn;
    }

    public static class empAttend {

        private final SimpleStringProperty firstName;
        private final SimpleStringProperty lastName;

        private empAttend(String fName, String lName) {
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

    public void display_EmpAttendTable() {
        String sql = "SELECT id,empname,wd FROM " + Constants.employeeAttendanceTable + " ORDER BY empname";

        try (Connection conn = this.connectEmpAttend(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            empAttendData = FXCollections.observableArrayList();

            while (rs.next()) {
                String empname = rs.getString("empname");
                double wd = rs.getDouble("wd");

                String empworkingdays = String.valueOf(wd);
                empAttendData.add(new empAttend(empname, empworkingdays));
            }
        } catch (SQLException e) {
            Logger logger = LogManager.getLogger(parentform.class);
            logger.error("An error occurred while displayig employee attandnce.", e);
        }
    }

    private Connection connectEmpAttend() {
        // SQLite connection string
        // String url = "jdbc:sqlite:V://projectBms/bmsemplist1.db";
        String url = Constants.employeeDB;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            Logger logger = LogManager.getLogger(parentform.class);
            logger.error("An error occurred while connecting to employee DB.", e);
        }
        return conn;
    }
}
