// now useing 
package projectbms;



//	deepskyblue
import java.io.File;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Formatter;
import java.util.LinkedList;
import java.util.List;
import java.util.function.UnaryOperator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
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
import javafx.geometry.HPos;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.textfield.TextFields;

public class bmsinterface extends Application {

    dataBase db = new dataBase();
    sellTable stb = new sellTable();
    Product pro = new Product();
    employee emp = new employee();
    expenses exp = new expenses();
    duesTable dueTable = new duesTable();
    Data dataTable = new Data();
    Create create = new Create();
    Reports report = new Reports();
    dataVisualisation vis = new dataVisualisation();
    //  totalAmount ta = new totalAmoutotalAmountnt();
    BillData billData = new BillData();

    TableView<Person> table = new TableView<Person>();
    ObservableList<Person> data;

    TableView<Sell> Selltable = new TableView<Sell>();
    ObservableList<Sell> Selldata;

    TextField searchBar1, searchBar2;

    int screenWidth = (int) Screen.getPrimary().getBounds().getWidth();
    int screenHeight = (int) Screen.getPrimary().getBounds().getHeight();

    List<String> products = new LinkedList<String>();

    List<String> customers = new LinkedList<String>();

    java.util.Date date = Calendar.getInstance().getTime();
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    Logger logger = LogManager.getLogger(dataBase.class);

    HBox hb1, hb3, hb4, hb5;
    Label label, b1, b2, b3, b4, b5, b6;
    Label b7, b8, b9, b10, b11, b12;
    Menu mu1, mu2, mu3, mu4, mu5, mu6, mu7;
    MenuBar mb1, mb2, mb3, mb4, mb5, mb6, mb7;
    MenuItem m11, m12, m13, m14;
    MenuItem m21, m22, m23, m24, m25;
    MenuItem m31, m32, m35, m36;
    MenuItem m41, m42, m43, m44, m45, m46;
    MenuItem m51, m52, m53, m54, m55, m56, m57, m58;
    MenuItem m61, m62;
    MenuItem m71, m72, m73, m74, m75;

    FontWeight fw = FontWeight.BOLD;
    FontPosture fp = FontPosture.REGULAR;

    int sceneWidth = screenWidth;
    int sceneHeight = screenHeight;

    @Override
    public void start(Stage stage) throws Exception {

        String productDbPath = Constants.ProductDataBase;
        String manageDbPath = Constants.mangeDataBase;
        String employeeDbPath = Constants.employeeDataBase;

        if (checkDatabaseExists(productDbPath)) {
            System.out.println("Product Database exists.");
        } else {
            System.out.println("Database does not exist. Creating a new one...");
            db.createNewDatabase();

            db.createNewTable();
            stb.createNewTableSell();
            billData.createNewTable();
            Purchase pur = new Purchase();
            pur.createNewTable();
            SalesData test = new SalesData();
            test.createNewTable();
            totalAmount ta = new totalAmount();
            ta.createNewtable();
        }

        if (checkDatabaseExists(manageDbPath)) {
            System.out.println("mange Database exists.");
        } else {
            System.out.println("Database does not exist. Creating a new one...");
            manage mdb = new manage();
            mdb.createNewDatabase();

            dues due = new dues();
            due.createNewTable();
            mdb.createNewTable();
            exp.createNewTable();
            dataTable.createNewVendourTable();
            dataTable.createNewCustomerTable();
            create.createNewBillHeaders();

            String name1 = "stockamount";
            String name2 = "profit";
            String name3 = "expenses";
            String name4 = "totalcsdues";
            String name5 = "totalempdues";
            String name6 = "totalourdues";
            String name7 = "totaldue";
            String name8 = "tax";
            String name9 = "totalsales";

            double amount = 0;
            mdb.insert(name1, amount);
            mdb.insert(name2, amount);
            mdb.insert(name3, amount);
            mdb.insert(name4, amount);
            mdb.insert(name5, amount);
            mdb.insert(name6, amount);
            mdb.insert(name7, amount);
            mdb.insert(name8, amount);
            mdb.insert(name9, amount);
        }

        if (checkDatabaseExists(employeeDbPath)) {
            System.out.println("employee Database exists.");
        } else {
            System.out.println("Database does not exist. Creating a new one...");
            empDatabase edb = new empDatabase();
            edb.createNewDatabase();

            edb.createNewTable();
            empAttandnce empattend = new empAttandnce();
            empattend.createNewTable();
        }

        stage.setTitle("INVENTORY MANAGEMENT SYSTEM ( Version 3.0 )");

        label = new Label("                                                      "
                + "                                                             "
                + "     INVENTORY MANAGEMENT SYSTEM ( I.M.S )");

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

        m21 = new MenuItem("Add New ");
        m22 = new MenuItem("Purchase");
        m23 = new MenuItem("Sell");
        m24 = new MenuItem("Update");
        m25 = new MenuItem("Delete");

        m31 = new MenuItem("Add new");
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
        m73 = new MenuItem("Expneses List");
        m74 = new MenuItem("Report");
        m75 = new MenuItem("Visualise");

        mu1.getItems().addAll(m11, m12, m13, m14);
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
        table.setPrefWidth(rightSeter);
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
        table.setItems(data);

        Label label, text, l_1, l_2, l_3, l_4, name, phn, mail;
        Label avlQnt, buyPrice;
        TextField tf1, tf2, tf3, nametxt, phntxt, mailtxt;
        CheckBox cb1;
        Button b_1, b_2;

        label = new Label("PRODUCTS SELLING ");
        label.setFont(Font.font("Calibri", fw, fp, 20));

        text = new Label("");
        text.setFont(Font.font("Calibri", fw, fp, 18));

        name = new Label("Enter Customer Name           ");
        phn = new Label("Enter Mobile Number            ");
        mail = new Label("Enter Mail ID                           ");
        l_1 = new Label("Enter Product Name               ");
        l_2 = new Label("Enter Quantity                         ");
        l_3 = new Label("Sell With Feeded Amount     ");
        l_4 = new Label("Enter Selling Price(For 1 unit)");
        l_1.setFont(Font.font("Calibri", fw, fp, 18));
        l_2.setFont(Font.font("Calibri", fw, fp, 18));
        l_3.setFont(Font.font("Calibri", fw, fp, 18));
        l_4.setFont(Font.font("Calibri", fw, fp, 18));
        name.setFont(Font.font("Calibri", fw, fp, 18));
        phn.setFont(Font.font("Calibri", fw, fp, 18));
        mail.setFont(Font.font("Calibri", fw, fp, 18));

        nametxt = new TextField();
        phntxt = new TextField();
        mailtxt = new TextField();
        tf1 = new TextField();
        tf2 = new TextField();
        tf3 = new TextField();
        tf1.setFont(Font.font("Calibri", fw, fp, 18));
        tf2.setFont(Font.font("Calibri", fw, fp, 18));
        tf3.setFont(Font.font("Calibri", fw, fp, 18));
        nametxt.setFont(Font.font("Calibri", fw, fp, 18));
        phntxt.setFont(Font.font("Calibri", fw, fp, 18));
        mailtxt.setFont(Font.font("Calibri", fw, fp, 18));

        avlQnt = new Label("");
        avlQnt.setFont(Font.font("Calibri", fw, fp, 18));

        buyPrice = new Label("");
        buyPrice.setFont(Font.font("Calibri", fw, fp, 18));

        cb1 = new CheckBox();

        b_1 = new Button("Add Product");
        b_1.setFont(Font.font("Calibri", fw, fp, 18));
        b_2 = new Button("Proceed");
        b_2.setFont(Font.font("Calibri", fw, fp, 18));

        HBox hb = new HBox();
        hb.getChildren().add(label);
        hb.setSpacing(15);
        hb.setPadding(new Insets(0, 10, 0, 240));

        HBox cs = new HBox();
        cs.getChildren().addAll(name, nametxt);
        cs.setSpacing(15);
        cs.setPadding(new Insets(10, 10, 10, 10));

        HBox mob = new HBox();
        mob.getChildren().addAll(phn, phntxt);
        mob.setSpacing(15);
        mob.setPadding(new Insets(10, 10, 10, 10));

        HBox mailID = new HBox();
        mailID.getChildren().addAll(mail, mailtxt);
        mailID.setSpacing(15);
        mailID.setPadding(new Insets(10, 10, 10, 10));

        HBox hb_1 = new HBox();
        hb_1.getChildren().addAll(l_1, tf1);
        hb_1.setSpacing(15);
        hb_1.setPadding(new Insets(10, 10, 10, 10));

        HBox hb_2 = new HBox();
        hb_2.getChildren().addAll(l_2, tf2, avlQnt);
        hb_2.setSpacing(15);
        hb_2.setPadding(new Insets(10, 10, 10, 10));

        HBox hb_3 = new HBox();
        hb_3.getChildren().addAll(l_3, cb1);
        hb_3.setSpacing(15);
        hb_3.setPadding(new Insets(10, 10, 10, 10));

        HBox hb_4 = new HBox();
        hb_4.getChildren().addAll(l_4, tf3, buyPrice);
        hb_4.setSpacing(15);
        hb_4.setPadding(new Insets(10, 10, 10, 10));

        HBox hb_5 = new HBox();
        hb_5.getChildren().addAll(b_1, b_2);
        hb_5.setSpacing(15);
        hb_5.setPadding(new Insets(0, 10, 0, 100));

        HBox hb_6 = new HBox();
        hb_6.getChildren().addAll(text);
        hb_6.setSpacing(0);
        hb_6.setPadding(new Insets(-15, 10, -15, 160));

        VBox vb1 = new VBox();
        vb1.getChildren().addAll(hb, cs, mob, mailID, hb_1, hb_2, hb_3, hb_4, hb_5);
        vb1.setSpacing(15);
        vb1.setPadding(new Insets(10, 10, 10, 100));

        VBox v_b = new VBox();
        v_b.getChildren().addAll(hb, vb1, hb_6);
        v_b.setSpacing(15);
        v_b.setPadding(new Insets(10, 130, 10, 50));

        display_sellTable();

        Selltable.setEditable(true);
        Selltable.setPrefHeight(sceneHeight - 200);
        Selltable.setPrefWidth(leftSeter);
        Selltable.setStyle("-fx-color:white;");

        TableColumn Col1 = new TableColumn("Name");
        Col1.setMinWidth((leftSeter / 4) + 50);
        Col1.setCellValueFactory(
                new PropertyValueFactory<Sell, String>("firstName"));

        TableColumn Col2 = new TableColumn("Price");
        Col2.setMinWidth((leftSeter / 4) - 20);
        Col2.setCellValueFactory(
                new PropertyValueFactory<Sell, String>("lastName"));

        TableColumn Col3 = new TableColumn("Quantity");
        Col3.setMinWidth((leftSeter / 4) - 20);
        Col3.setCellValueFactory(
                new PropertyValueFactory<Sell, String>("email"));

        TableColumn Col4 = new TableColumn("MRP");
        Col4.setMinWidth((leftSeter / 4) - 20);
        Col4.setCellValueFactory(
                new PropertyValueFactory<Sell, String>("mrp"));

        Selltable.getColumns().addAll(Col1, Col2, Col3, Col4);

        VBox vboxST = new VBox();
        vboxST.setSpacing(5);
        vboxST.setPadding(new Insets(0, 0, 0, 0));
        vboxST.getChildren().addAll(Selltable);
        Selltable.setItems(Selldata);

        HBox hbox = new HBox();
        hbox.getChildren().addAll(table, v_b, Selltable);
        // hbox.setSpacing(15);
        hbox.setPadding(new Insets(0, 0, 0, 0));

        VBox vb = new VBox();
        vb.setSpacing(10);
        vb.setPadding(new Insets(10, 10, 10, 10));
        vb.getChildren().addAll(hb1, hb3, hb5, hbox);

        Scene sc = new Scene(new Group(), sceneWidth, sceneHeight - 70);

        Image image = new Image(Constants.splashScreen);
        ImageView imageView = new ImageView(image);
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(0), new KeyValue(imageView.opacityProperty(), 0.0)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000), new KeyValue(imageView.opacityProperty(), 1.0)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(5000), new KeyValue(imageView.opacityProperty(), 1.0)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(6000), new KeyValue(imageView.opacityProperty(), 0.0)));
        timeline.setCycleCount(1);
        timeline.setOnFinished(event -> {
            ((Group) sc.getRoot()).getChildren().addAll(vb);
            stage.setMaximized(true);
            stage.setScene(sc);
            stage.show();
        });
        timeline.play();
        StackPane root = new StackPane(imageView);

        File imageFile = new File(Constants.iconPath);
        Image logoImage = new Image(imageFile.toURI().toString());

        // Set the logo as the application icon
        stage.getIcons().add(logoImage);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        b_1.setOnAction((ActionEvent e) -> {
            String csname = nametxt.getText().toLowerCase();
            String csphn = phntxt.getText();
            String csmail = mailtxt.getText().toLowerCase();
            String text1 = tf1.getText().toLowerCase();
            String text2 = tf2.getText();
            String text3 = tf3.getText();

            if ((text1 == null || text1.matches("\\s*")) && (text2 == null || text2.matches("\\s*"))) {
                text.setStyle("-fx-text-fill:red;");
                text.setText("Please Enter Data Before Proceeding");
            } else if (csname == null || csname.matches("\\s*")) {
                text.setStyle("-fx-text-fill:red;");
                text.setText("Please Enter Customer Name Before Proceeding");
            } else if (csmail == null || csmail.matches("\\s*")) {
                text.setStyle("-fx-text-fill:red;");
                text.setText("Please Enter Customer Mail Before Proceeding");
            } else if (csphn == null || csphn.matches("\\s*")) {
                text.setStyle("-fx-text-fill:red;");
                text.setText("Please Enter Product Name Before Proceeding");
            } else if (text1 == null || text1.matches("\\s*")) {
                text.setStyle("-fx-text-fill:red;");
                text.setText("Please Enter Product Name Before Proceeding");
            } else if (text2 == null || text2.matches("\\s*")) {
                text.setStyle("-fx-text-fill:red;");
                text.setText("Please Enter Quantity Before Proceeding");
            } else if (!text2.matches("-?\\d+(\\.\\d+)?")) {
                text.setStyle("-fx-text-fill:red;");
                text.setText("Input Mismatch (Please Enter Number, not character)");
            } else if (!cb1.isSelected()) {
                if (text3 == null || text3.matches("\\s*")) {
                    text.setStyle("-fx-text-fill:red;");
                    text.setText("Please Enter Product Name Before Proceeding");
                } else {
                    double sellingPrice = Double.parseDouble(text3);
                    double d = Double.parseDouble(tf2.getText());

                    double feededMrp = pro.getMRP(text1);

                    if (feededMrp < sellingPrice) {
                        text.setStyle("-fx-text-fill:red;");
                        text.setText("Selling Price Cant Be Greater Than MRP");
                    }

                    int x = db.redcuceqnt(text1, d, sellingPrice, dateFormat.format(date), csname);
                    switch (x) {
                        case 1 -> {
                            text.setStyle("-fx-text-fill:red;");
                            text.setText("No Such Product Available");
                        }
                        case 2 -> {
                            text.setStyle("-fx-text-fill:red;");
                            text.setText("Selling Quantity is more than available");
                        }
                        case 3 -> {
                            text.setStyle("-fx-text-fill:black;");
                            text.setText("Selling Entered");
                            billData.insertIntoBill(text1, d, sellingPrice);
                            Data csData = new Data();
                            double onePrice = d * sellingPrice;
                            csData.insertCustomer(csname, onePrice, csphn, csmail);
                            totalAmount tat = new totalAmount();
                            tat.insert(csname, onePrice);
                            display("stockamount");
                            display("totalsales");
                            display("profit");
                            revenue();
                            proTable();
                            table.setItems(data);
                            display_sellTable();
                            Selltable.setItems(Selldata);
                            tf1.clear();
                            tf2.clear();
                            tf3.clear();

                            tf1.requestFocus();
                            double buyingPrice = pro.getBuyingPrice(text1);
                            if (sellingPrice < buyingPrice) {
                                text.setStyle("-fx-text-fill:black;");
                                text.setText("Selling Entered (Sold For Loss)");
                            }
                        }
                        default -> {

                        }
                    }
                }
            } else {
                //new
                double sellingPrice = stb.getprice(text1);
                double d = Double.parseDouble(tf2.getText());
                int x = db.redcuceqnt(text1, d, sellingPrice, dateFormat.format(date), csname);
                switch (x) {
                    case 1 -> {
                        text.setStyle("-fx-text-fill:red;");
                        text.setText("No Such Product Available");
                    }
                    case 2 -> {
                        text.setStyle("-fx-text-fill:red;");
                        text.setText("Selling Quantity is more than available");
                    }
                    case 3 -> {
                        text.setStyle("-fx-text-fill:black;");
                        text.setText("Selling Entered");
                        billData.insertIntoBill(text1, d, sellingPrice);
                        Data csData = new Data();
                        double onePrice = d * sellingPrice;
                        csData.insertCustomer(csname, onePrice, csphn, csmail);
                        totalAmount tat = new totalAmount();
                        tat.insert(csname, onePrice);
                        display("stockamount");
                        display("totalsales");
                        display("profit");
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
                    default -> {

                    }
                }
            }

        });

        b_2.setOnAction((ActionEvent e) -> {
            pro.proceed(stage, nametxt.getText());
        });

        nametxt.requestFocus();

        nametxt.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                phntxt.requestFocus();
            }
            if (event.getCode() == KeyCode.DOWN) {
                phntxt.requestFocus();
            }
        });

        phntxt.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                mailtxt.requestFocus();
            }
            if (event.getCode() == KeyCode.UP) {
                nametxt.requestFocus();
            }
            if (event.getCode() == KeyCode.DOWN) {
                mailtxt.requestFocus();
            }
        });

        mailtxt.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                tf1.requestFocus();
            }
            if (event.getCode() == KeyCode.UP) {
                phntxt.requestFocus();
            }
            if (event.getCode() == KeyCode.DOWN) {
                tf1.requestFocus();
            }
        });

        phntxt.focusedProperty().addListener((obs, oldVal, newVal) -> {
            last getphn = new last();
            if (newVal) {
                phntxt.setText(getphn.getPhnNum(nametxt.getText()));
            }
        });

        mailtxt.focusedProperty().addListener((obs, oldVal, newVal) -> {
            last getphn = new last();
            if (newVal) {
                mailtxt.setText(getphn.getMail(nametxt.getText()));
            }
        });

        tf2.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                avlQnt.setText(pro.getQnt(tf1.getText()));
            }
        });

        tf3.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                buyPrice.setText(String.valueOf((char) pro.getBuyingPrice(tf1.getText())));
            }
        });

        nametxt.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (billData.count() != 0) {
                nametxt.setText(dataTable.getLastUpdatedCustomerName());
                nametxt.setEditable(false);
            } else {
                nametxt.setEditable(true);
            }
        });

        tf1.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                tf2.requestFocus();
            }
            if (event.getCode() == KeyCode.DOWN) {
                tf2.requestFocus();
            }
            if (event.getCode() == KeyCode.UP) {
                mailtxt.requestFocus();
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
                    b_1.fire();
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
            if (event.getCode() == KeyCode.UP) {
                tf2.requestFocus();
            }
            if (event.getCode().equals(KeyCode.ENTER)) {
                b_1.fire();
            }
        });

        cb1.setOnAction(event -> {
            tf3.setDisable(cb1.isSelected());
        });

        tf3.textProperty().addListener((observable, oldValue, newValue) -> {
            cb1.setDisable(!newValue.trim().isEmpty());
        });

        phntxt.setTextFormatter(new TextFormatter<>(getUnaryOperator()));
        tf2.setTextFormatter(new TextFormatter<>(getUnaryOperator()));
        tf3.setTextFormatter(new TextFormatter<>(getUnaryOperator()));

        // proTable();
        TextFields.bindAutoCompletion(tf1, products);

        displayCustomerTable();
        TextFields.bindAutoCompletion(nametxt, customers);

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

        SortedList<Person> sortedData = new SortedList<>(filterdBuyData);

        sortedData.comparatorProperty().bind(table.comparatorProperty());

        table.setItems(sortedData);

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

        SortedList<Sell> sortedSellData = new SortedList<>(filterSellData);

        sortedSellData.comparatorProperty().bind(Selltable.comparatorProperty());

        Selltable.setItems(sortedSellData);

        TextFields.bindAutoCompletion(tf1, products);

        // Create 
        m11.setOnAction((ActionEvent e) -> {
            create.createDb(stage);
        });

        m12.setOnAction((ActionEvent e) -> {
            create.createTable(stage);
        });

        m13.setOnAction((ActionEvent e) -> {
            create.Help(stage);
        });

        m14.setOnAction((ActionEvent e) -> {
            create.billData(stage);
        });

        // Product
        m21.setOnAction((ActionEvent e) -> {
            pro.addnew(stage);
        });

        m22.setOnAction((ActionEvent e) -> {
            pro.purchase(stage);
        });

        m24.setOnAction((ActionEvent e) -> {
            pro.update(stage);
        });

        m25.setOnAction((ActionEvent e) -> {
            pro.delete(stage);
        });

        // employee
        m31.setOnAction((ActionEvent e) -> {
            emp.addnew(stage);
        });

        m32.setOnAction((ActionEvent e) -> {
            emp.attandnce(stage);
        });

        m35.setOnAction((ActionEvent e) -> {
            emp.update(stage);
        });

        m36.setOnAction((ActionEvent e) -> {
            emp.delete(stage);
        });

        // Expenses
        m41.setOnAction((ActionEvent e) -> {
            exp.rent(stage);
        });

        m42.setOnAction((ActionEvent e) -> {
            exp.wages(stage);
        });

        m43.setOnAction((ActionEvent e) -> {
            exp.tax(stage);
        });

        m44.setOnAction((ActionEvent e) -> {
            exp.bills(stage);
        });

        m45.setOnAction((ActionEvent e) -> {
            exp.spoiled(stage);
        });

        m46.setOnAction((ActionEvent e) -> {
            exp.others(stage);
        });

        // Dues
        m51.setOnAction((ActionEvent e) -> {
            dueTable.totalDue(stage);
        });

        m52.setOnAction((ActionEvent e) -> {
            dueTable.customerDueTable(stage);
        });

        m53.setOnAction((ActionEvent e) -> {
            dueTable.VendourTable(stage);
        });

        m54.setOnAction((ActionEvent e) -> {
            dueTable.EmpDueTable(stage);
        });

        m55.setOnAction((ActionEvent e) -> {
            dueTable.VendourClearDue(stage);
        });

        m56.setOnAction((ActionEvent e) -> {
            dueTable.CustomerClearDue(stage);
        });

        m57.setOnAction((ActionEvent e) -> {
            dueTable.EmpAddDue(stage);
        });

        m58.setOnAction((ActionEvent e) -> {
            dueTable.EmpClearDue(stage);
        });

        // DATA
        m61.setOnAction((ActionEvent e) -> {
            dataTable.vendourTable(stage);
        });

        m62.setOnAction((ActionEvent e) -> {
            dataTable.customerTable(stage);
        });

        // REPORTS
        m71.setOnAction((ActionEvent e) -> {
            report.purchaseTable(stage);
        });

        m72.setOnAction((ActionEvent e) -> {
            report.SalesTable(stage);
        });

        m73.setOnAction((ActionEvent e) -> {
            report.expensesTable(stage);
        });

        m74.setOnAction((ActionEvent e) -> {
            report.report(stage);
        });

        m75.setOnAction((ActionEvent e) -> {
            vis.singleUnit(stage);
        });
    }

    public static boolean checkDatabaseExists(String databasePath) {
        // Check if the database file exists
        File databaseFile = new File(databasePath);
        return databaseFile.exists();
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

    /*  public boolean doubleCheck(String check){
         int index = 0;
         boolean out=false;
         
            while ( index < check.length()){
                char abc = check.charAt(index);
                System.out.println(abc);
                if( abc == '.' ||(!(check.charAt(index) >= '0'&& check.charAt(index) <= '9'))){
                    out = true;
                }
                index++;
            }
            return out;
    }  */
    public void display(String name) {
        String sql = "SELECT  * "
                + "FROM " + Constants.manage + " WHERE name = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);

            ResultSet rs = pstmt.executeQuery();

            Formatter formatter = new Formatter();
            double price;

            while (rs.next()) {
                price = rs.getDouble("amount");
                formatter.format("%.2f", price);
            }

            String amount = String.valueOf(formatter.toString());

            if (name.equals("stockamount")) {
                b7.setText(amount);
            }
            if (name.equals("totalsales")) {
                b8.setText(amount);
            }
            if (name.equals("expenses")) {
                b9.setText(amount);
            }
            if (name.equals("profit")) {
                b10.setText(amount);
            }
            if (name.equals("tax")) {
                b12.setText(amount);
            }

        } catch (SQLException e) {
            logger.error("An error occurred while displaying amounts in class bmsInterface", e);
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

            Formatter formatter = new Formatter();
            double profit = 0;

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
            logger.error("An error occurred while displaying revenue in class bmsInterface", e);
        }
    }

    private Connection connect() {
        // SQLite connection string
        //   String url = "jdbc:sqlite:V://projectBms/managedb1.db";
        String url = Constants.mangeDB;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            logger.error("An error occurred while connecting to Mange DB"
                    + "in class bmsInterface", e);
        }
        return conn;
    }

    public void proTable() {
        String sql = "SELECT * FROM " + Constants.ProductTable + " ORDER BY stockname";
        String sql1 = "SELECT COUNT(*) FROM " + Constants.ProductTable + "";

        try (Connection conn = this.connect_2(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql); Statement stmt1 = conn.createStatement(); ResultSet rs1 = stmt1.executeQuery(sql1)) {

            rs1.next();
            int count = rs1.getInt(1);

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

                products.add(stockname);
                data.add(new Person(stockname, qnt, mrp1, price1, tax1));
            }
        } catch (SQLException e) {
            logger.error("An error occurred while displaying Product table in class bmsInterface", e);
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
            logger.error("An error occurred while conneting to Product BD "
                    + " in class bmsInterface", e);
        }
        return conn;
    }

    public static class Sell {

        private final SimpleStringProperty firstName;
        private final SimpleStringProperty lastName;
        private final SimpleStringProperty email;
        private final SimpleStringProperty mrp;

        private Sell(String fName, String lName, String email, String mrp) {
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
        String sql1 = "SELECT COUNT(*) FROM " + Constants.sellTable;

        try (Connection conn = this.connect_2(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql); Statement stmt1 = conn.createStatement(); ResultSet rs1 = stmt1.executeQuery(sql1)) {

            rs1.next();

            int count = rs1.getInt(1);

            //    System.out.println("TOTAL NUMBER OF PRODUCTS :" + count);
            Selldata = FXCollections.observableArrayList();

            while (rs.next()) {
                String stockname = rs.getString("stockname");
                double price = rs.getDouble("price");
                double qnt = rs.getDouble("qnt");
                double mrp = rs.getDouble("mrp");

                String price1 = String.format("%.1f", price);
                String qnt1 = String.format("%.1f", qnt);
                String mrp1 = String.format("%.1f", mrp);

                Selldata.add(new Sell(stockname, price1, qnt1, mrp1));

            }
        } catch (SQLException e) {
            logger.error("An error occurred while displaying Sales Table"
                    + " in class bmsInterface", e);
        }
    }

    public void displayCustomerTable() {

        String sql = "SELECT * FROM " + Constants.customerTable + " ORDER BY cName";

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String vendourName = rs.getString("cName");
                customers.add(vendourName);
            }
        } catch (SQLException e) {
            logger.error("An error occurred while adding customer names into "
                    + "textfeild suggustions class bmsInterface", e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
