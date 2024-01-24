package projectbms;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.sql.*;
import javafx.stage.Screen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class sellTab extends parentform {
    
    private TableView<Sell> Selltable = new TableView<Sell>();
    ObservableList<Sell> Selldata;
    
    int screenWidth = (int) Screen.getPrimary().getBounds().getWidth();
    int screenHeight = (int) Screen.getPrimary().getBounds().getHeight();
    
    int sceneWidth = screenWidth;
    int sceneHeight = screenHeight;
   
    public void start(Stage stage) {
        
        VBox vb2 = super.addnew(stage,"sellTable");
        
        display_sellTable();
        Scene sc = new Scene(new Group(),sceneWidth,sceneHeight-70);
        
        stage.setTitle("Table View Sample");
 
        final Label label = new Label("Employee List");
        label.setFont(new Font("Arial", 20));
 
        Selltable.setEditable(true);
        Selltable.setPrefHeight(600);
        Selltable.setPrefWidth(220);
        Selltable.setStyle("-fx-color:white;");
       
        TableColumn Col1 = new TableColumn("Name");
        Col1.setMinWidth(100);
        Col1.setCellValueFactory(
                new PropertyValueFactory<Sell, String>("firstName"));
 
        TableColumn Col2 = new TableColumn("Price");
        Col2.setMinWidth(50);
        Col2.setCellValueFactory(
                new PropertyValueFactory<Sell, String>("lastName"));
 
        TableColumn Col3 = new TableColumn("Quantity");
        Col3.setMinWidth(50);
        Col3.setCellValueFactory(
                new PropertyValueFactory<Sell, String>("email"));
 
        Selltable.getColumns().addAll(Col1, Col2, Col3);
 
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 100));
        vbox.getChildren().addAll(Selltable);
        
        vb2.getChildren().addAll(vbox);
        vb2.setSpacing(10);
        vb2.setPadding(new Insets (10,10,10,10));
        
        Selltable.setItems(Selldata);
        
        ((Group) sc.getRoot()).getChildren().addAll(vb2);
        stage.setScene(sc);
        stage.show();
}
  
    public static class Sell {
 
        private final SimpleStringProperty firstName;
        private final SimpleStringProperty lastName;
        private final SimpleStringProperty email;
       
        private Sell(String fName, String lName, String email) {
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
    
     public void display_sellTable(){
        String sql = "SELECT * FROM "+Constants.sellTable+" ORDER BY stockname";
        String sql1 =            "SELECT COUNT(*) FROM "+Constants.sellTable;
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql);
             Statement stmt1  = conn.createStatement();
             ResultSet rs1 = stmt1.executeQuery(sql1)){
            
            rs1.next();
            
            int count = rs1.getInt(1);
            
            System.out.println("TOTAL NUMBER OF PRODUCTS :"+count);
            
            Selldata = FXCollections.observableArrayList( );
           
            while (rs.next()) {
                 String stockname = rs.getString("stockname");
                 double price     = rs.getDouble("price");
                 double qnt       =rs.getDouble("qnt");
                 
                  String price1 = String.valueOf(price);
                  String quantity = String.valueOf(qnt);
                 
                 Selldata.add(new Sell(stockname,price1,quantity));
                 
            }
        } catch (SQLException e) {
            Logger logger = LogManager.getLogger(sellTab.class);
            logger.error("An error occurred while displaying salestable.", e);
        }
    }
     
     private Connection connect() {
        // SQLite connection string
        String url = Constants.productDB;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            Logger logger = LogManager.getLogger(parentform.class);
            logger.error("An error occurred while connecting to profuct DB.", e);
        }
        return conn;
    }
} 