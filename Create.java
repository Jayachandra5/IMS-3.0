package projectbms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Create extends parentform {

    FontWeight fw = FontWeight.BOLD;
    FontPosture fp = FontPosture.REGULAR;

    public void Help(Stage stage) {

        VBox vb2 = super.addnew(stage, "product");

        Label label, l1, l2, l3, l4;

        label = new Label("For Any Queries & Help:-");
        label.setFont(Font.font("Calibri", fw, fp, 22));

        l1 = new Label("Created By K.Jayachandra ");
        l1.setFont(Font.font("Calibri", fw, fp, 20));

        l2 = new Label("Call: +91 8712173660  ");
        l2.setFont(Font.font("Calibri", fw, fp, 20));

        l3 = new Label("Mail: inventorymanagementsystem2023@gmail.com");
        l3.setFont(Font.font("Calibri", fw, fp, 20));

        l4 = new Label("Website: https://ims.unaux.com/");
        l4.setFont(Font.font("Calibri", fw, fp, 20));

        HBox hb = new HBox();
        hb.getChildren().addAll(label);
        hb.setSpacing(15);
        hb.setPadding(new Insets(50, 50, 10, 10));

        HBox hb1 = new HBox();
        hb1.getChildren().addAll(l1);
        hb1.setSpacing(15);
        hb1.setPadding(new Insets(10, 0, 10, 0));

        HBox hb2 = new HBox();
        hb2.getChildren().addAll(l2);
        hb2.setSpacing(15);
        hb2.setPadding(new Insets(10, 0, 10, 0));

        HBox hb3 = new HBox();
        hb3.getChildren().addAll(l3);
        hb3.setSpacing(15);
        hb3.setPadding(new Insets(10, 0, 10, 0));

        HBox hb4 = new HBox();
        hb4.getChildren().addAll(l4);
        hb4.setSpacing(15);
        hb4.setPadding(new Insets(10, 0, 10, 0));

        VBox vb1 = new VBox();
        vb1.getChildren().addAll(hb1, hb2, hb4, hb3);
        vb1.setSpacing(15);
        vb1.setPadding(new Insets(10, 10, 10, 10));

        VBox vb = new VBox();
        vb.getChildren().addAll(hb, vb1);
        vb.setSpacing(15);
        vb.setPadding(new Insets(10, 100, 100, 180));

        vb2.getChildren().addAll(vb);
        vb2.setSpacing(10);
        vb2.setPadding(new Insets(10, 10, 5, 10));

        stage.show();
    }

    Label text;

    public void createDb(Stage stage) {
        VBox vb2 = super.addnew(stage, "product");

        Label label, l1, l2;
        TextField tf1;
        RadioButton rb1;
        Button b1;

        label = new Label("Createing DataBase");
        label.setFont(Font.font("Calibri", fw, fp, 22));

        l1 = new Label("Enter Path :- ");
        l1.setFont(Font.font("Calibri", fw, fp, 20));

        tf1 = new TextField();
        tf1.setFont(Font.font("Calibri", fw, fp, 20));

        l2 = new Label("Create All DataBase  ");
        l2.setFont(Font.font("Calibri", fw, fp, 20));

        rb1 = new RadioButton();

        b1 = new Button("Done");
        b1.setFont(Font.font("Calibri", fw, fp, 20));

        text = new Label();
        text.setFont(Font.font("Calibri", fw, fp, 20));

        HBox hb = new HBox();
        hb.getChildren().addAll(label);
        hb.setSpacing(15);
        hb.setPadding(new Insets(50, 50, 10, 10));

        HBox hb1 = new HBox();
        hb1.getChildren().addAll(l1, tf1);
        hb1.setSpacing(15);
        hb1.setPadding(new Insets(10, 10, 10, 10));

        HBox hb2 = new HBox();
        hb2.getChildren().addAll(l2, rb1);
        hb2.setSpacing(15);
        hb2.setPadding(new Insets(10, 10, 10, 10));

        HBox hb4 = new HBox();
        hb4.getChildren().addAll(b1);
        hb4.setSpacing(15);
        hb4.setPadding(new Insets(10, 10, 10, 130));

        HBox hb5 = new HBox();
        hb5.getChildren().addAll(text);
        hb5.setSpacing(15);
        hb5.setPadding(new Insets(10, 10, 10, 10));

        VBox vb1 = new VBox();
        vb1.getChildren().addAll(hb1, hb2, hb4, hb5);
        vb1.setSpacing(15);
        vb1.setPadding(new Insets(10, 10, 10, 50));

        VBox vb = new VBox();
        vb.getChildren().addAll(hb, vb1);
        vb.setSpacing(15);
        vb.setPadding(new Insets(10, 100, 100, 180));

        vb2.getChildren().addAll(vb);
        vb2.setSpacing(10);
        vb2.setPadding(new Insets(10, 10, 5, 10));

        stage.show();

        b1.setOnAction((ActionEvent e) -> {
            if (rb1.isSelected()) {
                dataBase db = new dataBase();
                db.createNewDatabase();
                manage mdb = new manage();
                mdb.createNewDatabase();
                empDatabase edb = new empDatabase();
                edb.createNewDatabase();

                text.setStyle("-fx-text-fill:black;");
                text.setText("DataBases Created Successfully");
            } else {
                text.setStyle("-fx-text-fill:red;");
                text.setText("Something Went Worng Please Try Again");
            }
        });
    }

    public void createTable(Stage stage) {

        VBox vb2 = super.addnew(stage, "product");

        Label label, l1, l2;
        TextField tf1;
        RadioButton rb1;
        Button b1;

        label = new Label("Createing Tables");
        label.setFont(Font.font("Calibri", fw, fp, 22));

        l1 = new Label("Enter Path :- ");
        l1.setFont(Font.font("Calibri", fw, fp, 20));

        tf1 = new TextField();
        tf1.setFont(Font.font("Calibri", fw, fp, 20));

        l2 = new Label("Create All Tables  ");
        l2.setFont(Font.font("Calibri", fw, fp, 20));

        rb1 = new RadioButton();

        b1 = new Button("Done");
        b1.setFont(Font.font("Calibri", fw, fp, 20));

        text = new Label();
        text.setFont(Font.font("Calibri", fw, fp, 20));

        HBox hb = new HBox();
        hb.getChildren().addAll(label);
        hb.setSpacing(15);
        hb.setPadding(new Insets(50, 50, 10, 10));

        HBox hb1 = new HBox();
        hb1.getChildren().addAll(l1, tf1);
        hb1.setSpacing(15);
        hb1.setPadding(new Insets(10, 10, 10, 10));

        HBox hb2 = new HBox();
        hb2.getChildren().addAll(l2, rb1);
        hb2.setSpacing(15);
        hb2.setPadding(new Insets(10, 10, 10, 10));

        HBox hb4 = new HBox();
        hb4.getChildren().addAll(b1);
        hb4.setSpacing(15);
        hb4.setPadding(new Insets(10, 10, 10, 130));

        HBox hb5 = new HBox();
        hb5.getChildren().addAll(text);
        hb5.setSpacing(15);
        hb5.setPadding(new Insets(10, 10, 10, 10));

        VBox vb1 = new VBox();
        vb1.getChildren().addAll(hb1, hb2, hb4, hb5);
        vb1.setSpacing(15);
        vb1.setPadding(new Insets(10, 10, 10, 50));

        VBox vb = new VBox();
        vb.getChildren().addAll(hb, vb1);
        vb.setSpacing(15);
        vb.setPadding(new Insets(10, 100, 100, 180));

        vb2.getChildren().addAll(vb);
        vb2.setSpacing(10);
        vb2.setPadding(new Insets(10, 10, 5, 10));

        stage.show();

        b1.setOnAction((ActionEvent e) -> {
            if (rb1.isSelected()) {
                dataBase db = new dataBase();
                db.createNewTable();
                sellTable Cstb = new sellTable();
                Cstb.createNewTableSell();
                BillData BD = new BillData();
                BD.createNewTable();
                Purchase pur = new Purchase();
                pur.createNewTable();
                SalesData test = new SalesData();
                test.createNewTable();
                empDatabase edb = new empDatabase();
                edb.createNewTable();
                empAttandnce empattend = new empAttandnce();
                empattend.createNewTable();
                dues due = new dues();
                due.createNewTable();
                manage mdb = new manage();
                mdb.createNewTable();
                expenses exp = new expenses();
                exp.createNewTable();
                Data data = new Data();
                data.createNewVendourTable();
                data.createNewCustomerTable();

                mdb.insert("stockamount", 0);
                mdb.insert("profit", 0);
                mdb.insert("expenses", 0);
                mdb.insert("totalcsdues", 0);
                mdb.insert("totalempdues", 0);
                mdb.insert("totalourdues", 0);
                mdb.insert("totaldue", 0);
                mdb.insert("tax", 0);
                mdb.insert("totalsales", 0);

                text.setStyle("-fx-text-fill:black;");
                text.setText("DataBases Created Successfully");
            } else {
                text.setStyle("-fx-text-fill:red;");
                text.setText("Something Went Worng Please Try Again");
            }

        });
    }

    public void billData(Stage stage) {

        VBox vb2 = super.addnew(stage, "product");

        Label label, text, phnNum2, GSTNum, Address, bussinessName, ownerName, phnNum1;
        TextField phn2txt, gsttxt, addresstxt, bussinesstxt, ownertxt, phn1txt;
        Button b_1;

        label = new Label("BILL HEADER DATA ");
        label.setFont(Font.font("Calibri", fw, fp, 20));

        text = new Label("");
        text.setFont(Font.font("Calibri", fw, fp, 18));

        bussinessName = new Label("Enter Bussiness Name           ");
        ownerName = new Label("Enter Owner Number            ");
        phnNum1 = new Label("Enter phone Number(1)         ");
        phnNum2 = new Label("Enter Phone Number(2)         ");
        GSTNum = new Label("Enter GST Number                  ");
        Address = new Label("Enter Address                          ");

        phnNum2.setFont(Font.font("Calibri", fw, fp, 18));
        GSTNum.setFont(Font.font("Calibri", fw, fp, 18));
        Address.setFont(Font.font("Calibri", fw, fp, 18));
        bussinessName.setFont(Font.font("Calibri", fw, fp, 18));
        ownerName.setFont(Font.font("Calibri", fw, fp, 18));
        phnNum1.setFont(Font.font("Calibri", fw, fp, 18));

        bussinesstxt = new TextField();
        ownertxt = new TextField();
        phn1txt = new TextField();
        phn2txt = new TextField();
        gsttxt = new TextField();
        addresstxt = new TextField();
        phn2txt.setFont(Font.font("Calibri", fw, fp, 18));
        gsttxt.setFont(Font.font("Calibri", fw, fp, 18));
        addresstxt.setFont(Font.font("Calibri", fw, fp, 18));
        bussinesstxt.setFont(Font.font("Calibri", fw, fp, 18));
        ownertxt.setFont(Font.font("Calibri", fw, fp, 18));
        phn1txt.setFont(Font.font("Calibri", fw, fp, 18));

        b_1 = new Button("Done");
        b_1.setFont(Font.font("Calibri", fw, fp, 18));

        HBox hb = new HBox();
        hb.getChildren().add(label);
        hb.setSpacing(15);
        hb.setPadding(new Insets(0, 10, 0, 240));

        HBox cs = new HBox();
        cs.getChildren().addAll(bussinessName, bussinesstxt);
        cs.setSpacing(15);
        cs.setPadding(new Insets(10, 10, 10, 10));

        HBox mob = new HBox();
        mob.getChildren().addAll(ownerName, ownertxt);
        mob.setSpacing(15);
        mob.setPadding(new Insets(10, 10, 10, 10));

        HBox mailID = new HBox();
        mailID.getChildren().addAll(phnNum1, phn1txt);
        mailID.setSpacing(15);
        mailID.setPadding(new Insets(10, 10, 10, 10));

        HBox hb_1 = new HBox();
        hb_1.getChildren().addAll(phnNum2, phn2txt);
        hb_1.setSpacing(15);
        hb_1.setPadding(new Insets(10, 10, 10, 10));

        HBox hb_2 = new HBox();
        hb_2.getChildren().addAll(GSTNum, gsttxt);
        hb_2.setSpacing(15);
        hb_2.setPadding(new Insets(10, 10, 10, 10));

        HBox hb_4 = new HBox();
        hb_4.getChildren().addAll(Address, addresstxt);
        hb_4.setSpacing(15);
        hb_4.setPadding(new Insets(10, 10, 10, 10));

        HBox hb_5 = new HBox();
        hb_5.getChildren().addAll(b_1);
        hb_5.setSpacing(15);
        hb_5.setPadding(new Insets(0, 10, 0, 200));

        HBox hb_6 = new HBox();
        hb_6.getChildren().addAll(text);
        hb_6.setSpacing(0);
        hb_6.setPadding(new Insets(0, 10, 0, 160));

        VBox vb1 = new VBox();
        vb1.getChildren().addAll(hb, cs, mob, mailID, hb_1, hb_2, hb_4, hb_5);
        vb1.setSpacing(15);
        vb1.setPadding(new Insets(10, 10, 10, 80));

        VBox v_b = new VBox();
        v_b.getChildren().addAll(hb, vb1, hb_6);
        v_b.setSpacing(15);
        v_b.setPadding(new Insets(10, 100, 10, 50));

        vb2.getChildren().addAll(v_b);
        vb2.setSpacing(10);
        vb2.setPadding(new Insets(10, 10, 5, 10));

        stage.show();

        b_1.setOnAction((ActionEvent e) -> {

            String tf1 = bussinesstxt.getText();
            String tf2 = ownertxt.getText();
            String tf3 = phn1txt.getText();
            String tf4 = phn2txt.getText();
            String tf5 = gsttxt.getText();
            String tf6 = addresstxt.getText();

            if (tf1 == null || tf1.matches("\\s*")) {
                text.setStyle("-fx-text-fill:red;");
                text.setText("Please Enter Bussiness Name Before Proceeding");
            } else if (tf2 == null || tf2.matches("\\s*")) {
                text.setStyle("-fx-text-fill:red;");
                text.setText("Please Enter Owner Name Before Proceeding");
            } else if (tf3 == null || tf3.matches("\\s*")) {
                text.setStyle("-fx-text-fill:red;");
                text.setText("Please Enter phn num1 Before Proceeding");
            } else if (tf4 == null || tf4.matches("\\s*")) {
                text.setStyle("-fx-text-fill:red;");
                text.setText("Please Enter Phn num2 Before Proceeding");
            } else if (tf5 == null || tf5.matches("\\s*")) {
                text.setStyle("-fx-text-fill:red;");
                text.setText("Please Enter gst Num Before Proceeding");
            } else if (tf6 == null || tf6.matches("\\s*")) {
                text.setStyle("-fx-text-fill:red;");
                text.setText("Please Enter Address Before Proceeding");
            } else {

                insertItem("Title", tf1);
                insertItem("ownerName", tf2);
                insertItem("phnNum1", tf3);
                insertItem("phnNum2", tf4);
                insertItem("gstNum", tf5);
                insertItem("Address", tf6);

                text.setStyle("-fx-text-fill:black;");
                text.setText("Data Entered Sussucefuly");

                bussinesstxt.clear();
                ownertxt.clear();
                phn1txt.clear();
                phn2txt.clear();
                gsttxt.clear();
                addresstxt.clear();

            }
        });

        bussinesstxt.requestFocus();

        bussinesstxt.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                ownertxt.requestFocus();
            }
            if (event.getCode() == KeyCode.DOWN) {
                ownertxt.requestFocus();
            }
        });

        ownertxt.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                phn1txt.requestFocus();
            }
            if (event.getCode() == KeyCode.UP) {
                bussinesstxt.requestFocus();
            }
            if (event.getCode() == KeyCode.DOWN) {
                phn1txt.requestFocus();
            }
        });

        phn1txt.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                phn2txt.requestFocus();
            }
            if (event.getCode() == KeyCode.UP) {
                ownertxt.requestFocus();
            }
            if (event.getCode() == KeyCode.DOWN) {
                phn2txt.requestFocus();
            }
        });

        phn2txt.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                gsttxt.requestFocus();
            }
            if (event.getCode() == KeyCode.DOWN) {
                gsttxt.requestFocus();
            }
            if (event.getCode() == KeyCode.UP) {
                phn1txt.requestFocus();
            }
        });

        gsttxt.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                addresstxt.requestFocus();
            }
            if (event.getCode() == KeyCode.UP) {
                phn2txt.requestFocus();
            }
            if (event.getCode() == KeyCode.DOWN) {
                addresstxt.requestFocus();
            }
        });

        addresstxt.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                b_1.fire();
            }
            if (event.getCode() == KeyCode.UP) {
                gsttxt.requestFocus();
            }

        });
    }

    Logger logger = LogManager.getLogger(Create.class);

    public static void createNewBillHeaders() {
        // SQLite connection string
        //String url = "jdbc:sqlite:V://projectBms/bmsList1.db";

        String url = Constants.mangeDB;
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS " + Constants.BillHeaders + "(\n"
                + "	id integer PRIMARY KEY,\n"
                + "	Name text NOT NULL,\n"
                + "	Item text NOT NULL \n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);

            Logger logger = LogManager.getLogger(Create.class);
            logger.info("Table created");
            System.out.println("Table Created");
        } catch (SQLException e) {
            Logger logger = LogManager.getLogger(Create.class);
            logger.error("An error occurred while Creating vendour.", e);
        }
    }

    public int insertItem(String Name, String Item) {

        String sql = "INSERT INTO " + Constants.BillHeaders + " (Name,Item) VALUES(?,?)";

        String sql1 = "SELECT  Name "
                + "FROM " + Constants.BillHeaders + " WHERE Name = ?";

        String sql2 = "UPDATE " + Constants.BillHeaders + " SET Item = ?  "
                + "WHERE Name = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql); PreparedStatement pstmt1 = conn.prepareStatement(sql1); PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {

            pstmt1.setString(1, Name);

            ResultSet rs = pstmt1.executeQuery();
            String name = null;
            while (rs.next()) {
                name = rs.getString("Name");
            }
            if (Name.equals(name)) {
                pstmt2.setString(1, Item);
                pstmt2.setString(2, Name);

                pstmt2.executeUpdate();
                System.out.println("Updated");
            } else {
                pstmt.setString(1, Name);
                pstmt.setString(2, Item);

                pstmt.executeUpdate();
                System.out.println("Inserted");
            }
        } catch (SQLException e) {
            logger.error("An error occurred while Inserting Vendour.", e);
        }
        return 0;
    }

    public String getItem(String name) {
        String sql = "SELECT Item FROM " + Constants.BillHeaders + " WHERE Name = ?";

        String Item = null;
        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                Item = rs.getString("Item");
            }

        } catch (SQLException e) {

            logger.error("An error occurred while getting customer phone number.", e);
        }
        return Item;
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
}
