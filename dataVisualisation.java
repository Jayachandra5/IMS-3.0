package projectbms;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.apache.logging.log4j.LogManager;
import org.controlsfx.control.textfield.TextFields;

public class dataVisualisation extends parentform {

    ObservableList<PieChart.Data> pieChartData;

    ObservableList<PieChart.Data> expensestData;

    ObservableList<PieChart.Data> profitData;

    SalesData sell = new SalesData();

    org.apache.logging.log4j.Logger logger = LogManager.getLogger(dataVisualisation.class);

    public void sales(Stage stage, String date1, String date2) {

        VBox vb2 = super.addnew(stage, "product");

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);
        root.setPadding(new Insets(10));

        String getdata = displayReport(date1, date2);
        PieChart chart = new PieChart(pieChartData);
        chart.setTitle(" PRODUCT SALES" + "\n\n " + getdata);

        chart.setPrefSize(600, 600);
        final Label caption = new Label("");
        caption.setTextFill(Color.BLACK);
        caption.setStyle("-fx-font: bold 20 calibri;");

        // Set the label format to display the name and percentage
        chart.getData().forEach(data -> setupDataMouseClick(data, caption));

        for (PieChart.Data data : chart.getData()) {
            data.setName(String.format("%s %.1f%%", data.getName() + ", ",
                    (data.getPieValue() / getTotal(pieChartData)) * 100));
        }

        Button back = new Button("Back");
        back.setStyle("-fx-font: bold 18 calibri;");

        root.getChildren().addAll(chart, caption, back);

        vb2.getChildren().addAll(root);
        vb2.setSpacing(10);
        vb2.setPadding(new Insets(70, 10, 5, 130));

        stage.show();

        back.setOnAction((ActionEvent e) -> {

            Reports report = new Reports();

            report.reportBack(stage, date1, date2);
        });

    }

    /*   private void setupDataMouseClick(PieChart.Data data, Label caption) {
        data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
            caption.setLayoutX(e.getSceneX());
            caption.setLayoutY(e.getSceneY());

            String[] nameParts = data.getName().split("\\s+");
            String name = nameParts[0].trim();
            caption.setText(name + ", " + "Qnt = " + data.getPieValue());
        });
    }*/
    private void setupDataMouseClick(PieChart.Data data, Label caption) {
        Node node = data.getNode();
        node.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            caption.setText(String.format("%s, Qnt = %.0f", data.getName(), data.getPieValue()));
            caption.setLayoutX(e.getX() + node.getLayoutX());
            caption.setLayoutY(e.getY() + node.getLayoutY());
        });

        node.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            caption.setText("");
        });
    }

    private double getTotal(ObservableList<PieChart.Data> data) {
        double total = 0;
        for (PieChart.Data d : data) {
            total += d.getPieValue();
        }
        return total;
    }

    public String displayReport(String date1, String date2) {

        String sql = "SELECT stockName,SUM(qnt) FROM " + Constants.salesTable
                + " WHERE date BETWEEN '" + date1 + "' and '" + date2 + "' GROUP BY stockName ";

        double totalSales = 0;
        int count = 0;
        String datapass = null;

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            pieChartData = FXCollections.observableArrayList();

            while (rs.next()) {
                String stockname = rs.getString("stockName");
                double sale = sell.oneItemQnt(stockname, date1, date2);
                if (sale > 0) {
                    totalSales += sale;
                    count++;
                    pieChartData.add(new PieChart.Data(stockname, sale));
                }
            }

            datapass = String.valueOf("Total Units Sold : " + count + "   \n") + String.valueOf("Total Units Sold : " + totalSales);

        } catch (SQLException e) {
            logger.error("An error occurred while displaying the product in the class dataVisulastion.", e);
        }
        return datapass;
    }

    private Connection connect() {
        // SQLite connection string
        String url = Constants.productDB;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            logger.error("An error occurred while connecting to Product DB"
                    + "in the class dataVisualztion.", e);
        }
        return conn;
    }

    public void expeses(Stage stage, String date1, String date2) {

        VBox vb2 = super.addnew(stage, "product");

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);
        root.setPadding(new Insets(10));

        String getdata = displayExpenses(date1, date2);
        PieChart chart = new PieChart(expensestData);
        chart.setPrefSize(600, 600);
        chart.setTitle("            EXPENSES " + "\n\n " + getdata);

        final Label caption = new Label("");
        caption.setTextFill(Color.BLACK);
        caption.setStyle("-fx-font: bold 20 calibri;");

        // Set the label format to display the name and percentage
        chart.getData().forEach(data1 -> setupDataMouseClickExp(data1, caption));

        for (PieChart.Data data1 : chart.getData()) {
            data1.setName(String.format("%s %.1f%%", data1.getName() + ", ",
                    (data1.getPieValue() / getTotalExp(expensestData)) * 100));
        }

        Button back = new Button("Back");
        back.setStyle("-fx-font: bold 18 calibri;");

        root.getChildren().addAll(chart, caption, back);

        vb2.getChildren().addAll(root);
        vb2.setSpacing(10);
        vb2.setPadding(new Insets(70, 10, 5, 130));

        stage.show();

        back.setOnAction((ActionEvent e) -> {

            Reports report = new Reports();

            report.reportBack(stage, date1, date2);
        });

    }

    private void setupDataMouseClickExp(PieChart.Data data, Label caption) {
        Node node = data.getNode();
        node.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            caption.setText(String.format("%s, Amount = %.0f", data.getName(), data.getPieValue()));
            caption.setLayoutX(e.getX() + node.getLayoutX());
            caption.setLayoutY(e.getY() + node.getLayoutY());
        });

        node.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            caption.setText("");
        });
    }

    /*  private void setupDataMouseClickExp(PieChart.Data data, Label caption) {
        data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
            caption.setLayoutX(e.getSceneX());
            caption.setLayoutY(e.getSceneY());

            String[] nameParts = data.getName().split("\\s+");
            String name = nameParts[0].trim();
            caption.setText(name + ", " + "Amount = " + data.getPieValue());
        });
    } */
    private double getTotalExp(ObservableList<PieChart.Data> data) {
        double total = 0;
        for (PieChart.Data d : data) {
            total += d.getPieValue();
        }
        return total;
    }

    public String displayExpenses(String date1, String date2) {

        String sql = "SELECT name,SUM(amount) FROM " + Constants.expensesTable
                + " WHERE date BETWEEN '" + date1 + "' and '" + date2 + "' GROUP BY name ";

        double totalExp = 0;
        double totalSalary = 0;
        int count = 0;
        String datapass = null;

        try (Connection conn = this.connectMange(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            expensestData = FXCollections.observableArrayList();

            while (rs.next()) {
                String name = rs.getString("name");
                double exp = rs.getDouble("SUM(amount)");

                if (name.contains("Salary") == true) {
                    totalSalary += exp;
                    totalExp += exp;
                } else {
                    totalExp += exp;
                    count++;
                    expensestData.add(new PieChart.Data(name, exp));
                }
            }
            if (totalSalary != 0) {
                expensestData.add(new PieChart.Data("Salary", totalSalary));
                count++;
            }
            datapass = String.valueOf("Total number of Expenses : " + count + "   \n") + String.valueOf("Total Amount on Expenses: " + totalExp);

        } catch (SQLException e) {
            logger.error("An error occurred while displaying expenses in the class dataVisulaisation.", e);
        }
        return datapass;
    }

    private Connection connectMange() {
        // SQLite connection string
        String url = Constants.mangeDB;
        //String url = "jdbc:sqlite:V://projectBms/managedb1.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            logger.error("An error occurred while connecting to mange DB.", e);
        }
        return conn;
    }

    public void Profit(Stage stage, String date1, String date2) {

        VBox vb2 = super.addnew(stage, "product");

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);
        root.setPadding(new Insets(10));

        String getdata = displayProfit(date1, date2);
        PieChart chart = new PieChart(profitData);
        chart.setPrefSize(600, 600);
        chart.setTitle("            PROFIT " + "\n\n " + getdata);

        final Label caption = new Label("");
        caption.setTextFill(Color.BLACK);
        caption.setStyle("-fx-font: bold 20 calibri;");

        // Set the label format to display the name and percentage
        chart.getData().forEach(data1 -> setupDataMouseClickProfit(data1, caption));

        for (PieChart.Data data1 : chart.getData()) {
            data1.setName(String.format("%s %.1f%%", data1.getName() + ", ",
                    (data1.getPieValue() / getTotalProfit(profitData)) * 100));
        }

        Button back = new Button("Back");
        back.setStyle("-fx-font: bold 18 calibri;");

        root.getChildren().addAll(chart, caption, back);

        vb2.getChildren().addAll(root);
        vb2.setSpacing(10);
        vb2.setPadding(new Insets(70, 10, 5, 130));

        stage.show();

        back.setOnAction((ActionEvent e) -> {

            Reports report = new Reports();

            report.reportBack(stage, date1, date2);
        });
    }

    private void setupDataMouseClickProfit(PieChart.Data data, Label caption) {
        Node node = data.getNode();
        node.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            caption.setText(String.format("%s, Profit = %.0f", data.getName(), data.getPieValue()));
            caption.setLayoutX(e.getX() + node.getLayoutX());
            caption.setLayoutY(e.getY() + node.getLayoutY());
        });

        node.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            caption.setText("");
        });
    }

    /*  private void setupDataMouseClickProfit(PieChart.Data data, Label caption) {
        data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
            caption.setLayoutX(e.getSceneX());
            caption.setLayoutY(e.getSceneY());

            String[] nameParts = data.getName().split("\\s+");
            String name = nameParts[0].trim();
            caption.setText(name + ", " + "Profit = " + data.getPieValue());
        });
    } */
    private double getTotalProfit(ObservableList<PieChart.Data> data) {
        double total = 0;
        for (PieChart.Data d : data) {
            total += d.getPieValue();
        }
        return total;
    }

    public String displayProfit(String date1, String date2) {

        String sql = "SELECT stockname,SUM(profit) FROM " + Constants.salesTable
                + " WHERE date BETWEEN '" + date1 + "' and '" + date2 + "' GROUP BY stockname ";

        double totalProfit = 0;
        int count = 0;
        String datapass = null;

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            profitData = FXCollections.observableArrayList();

            while (rs.next()) {
                String name = rs.getString("stockname");
                double profit = rs.getDouble("SUM(profit)");

                totalProfit += profit;
                count++;

                profitData.add(new PieChart.Data(name, profit));
            }
            datapass = String.valueOf("Total number of Products : " + count + "   \n") + String.valueOf("Total Profit: " + totalProfit);

        } catch (SQLException e) {
            logger.error("An error occurred while displaying profit"
                    + "in the class data Visualization.", e);
        }
        return datapass;
    }

    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    final LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis, yAxis);
    final Label label = new Label();

    public void Stats(Stage stage, String date1, String date2) {

        VBox vb2 = super.addnew(stage, "product");

        LocalDate startDate = LocalDate.parse(date1);
        LocalDate endDate = LocalDate.parse(date2);

        XYChart.Series series1 = new XYChart.Series();

        series1.setName("Revenue");

        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);

        // Divide the date range into 10 dates
        LocalDate partDate = startDate;
        for (int i = 1; i <= 10; i++) {
            LocalDate endDateOfIteration = startDate.plusDays(i * (daysBetween / 10));

            double profit = totalProfit(partDate.toString(), endDateOfIteration.toString());
            series1.getData().add(new XYChart.Data(endDateOfIteration.toString(), profit));
            partDate = endDateOfIteration.plusDays(1);
        }

        // Add data labels to the line chart
        /*    for (Iterator it = series1.getData().iterator(); it.hasNext();) {
            XYChart.Data<String, Number> data = (XYChart.Data<String, Number>) it.next();
            Label label = new Label(data.getYValue().toString());
            label.setStyle("-fx-font-size: 9pt;");
            data.setNode(label);
        } */
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Profit");

        for (int i = 1; i <= 10; i++) {
            LocalDate endDateOfIterationExp = startDate.plusDays(i * (daysBetween / 10));

            double revnue = totalProfit(partDate.toString(), endDateOfIterationExp.toString());

            double expenses = totalExpenses(partDate.toString(), endDateOfIterationExp.toString());

            double profit = revnue - expenses;

            series2.getData().add(new XYChart.Data(endDateOfIterationExp.toString(), profit));

            partDate = endDateOfIterationExp.plusDays(1);
        }
        // Add data labels to the line chart
        /*   for (Iterator it = series2.getData().iterator(); it.hasNext();) {
            XYChart.Data<String, Number> data = (XYChart.Data<String, Number>) it.next();
            Label label = new Label(data.getYValue().toString());
            label.setStyle("-fx-font-size: 9pt;");
            data.setNode(label);
        } */

        System.out.println(series1.getData());

        lineChart.getData().addAll(series1, series2);

        Button back = new Button("Back");
        back.setStyle("-fx-font: bold 18 calibri;");

        Pane root = new Pane();
        root.getChildren().addAll(lineChart, label, back);
        root.setPadding(new Insets(10, 10, 10, 10));

        GridPane gridPane = new GridPane();
        gridPane.add(root, 0, 0);

        VBox vb = new VBox();
        vb.getChildren().addAll(gridPane);
        vb.setSpacing(10);
        vb.setPadding(new Insets(120, 10, 100, 70));

        lineChart.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                label.setVisible(true);
                double mouseX = event.getX();
                double mouseY = event.getY();

                double chartX = xAxis.sceneToLocal(mouseX, mouseY).getX();
                double chartY = yAxis.sceneToLocal(mouseX, mouseY).getY();

                String xValue = "";
                if (xAxis.getValueForDisplay(chartX) != null) {
                    xValue = xAxis.getValueForDisplay(chartX).toString();
                }
                double yValue = (double) yAxis.getValueForDisplay(chartY);

                label.setText(String.format("%s, %.2f", xValue, yValue));
                label.setLayoutX(mouseX);
                label.setLayoutY(mouseY - 30);
            }
        });

        lineChart.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                label.setVisible(false);
            }
        });

        lineChart.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (label != null) {
                    label.setVisible(false);
                }
            }
        });

        vb2.getChildren().addAll(vb);
        vb2.setSpacing(10);
        vb2.setPadding(new Insets(10, 10, 10, 10));

        stage.show();

        back.setOnAction((ActionEvent e) -> {

            Reports report = new Reports();

            report.reportBack(stage, date1, date2);
        });
    }

    public double totalProfit(String date1, String date2) {

        String sql = "SELECT stockName,SUM(profit) FROM " + Constants.salesTable
                + " WHERE date BETWEEN '" + date1 + "' and '" + date2 + "'";

        double totalProfit = 0;

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            totalProfit = rs.getDouble("SUM(profit)");

        } catch (SQLException e) {
            logger.error("An error occurred while displaying total profit"
                    + "in the class data Visualization.", e);
        }
        return totalProfit;
    }

    public double totalExpenses(String date1, String date2) {

        String sql = "SELECT name,SUM(amount) FROM " + Constants.expensesTable
                + " WHERE date BETWEEN '" + date1 + "' and '" + date2 + "' GROUP BY name ";

        double totalExp = 0;
        try (Connection conn = this.connectMange(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // String name = rs.getString("name");
                double exp = rs.getDouble("SUM(amount)");

                totalExp += exp;
            }
        } catch (SQLException e) {
            logger.error("An error occurred while displaying total expenses"
                    + "in the class data Visualization.", e);
        }
        return totalExp;
    }

    Button profitButton, unitsButton;

    FontWeight fw = FontWeight.BOLD;
    FontPosture fp = FontPosture.REGULAR;

    List<String> products = new LinkedList<String>();

    DatePicker checkInDatePicker;
    DatePicker checkOutDatePicker;

    public void singleUnit(Stage stage) {

        VBox vb2 = super.addnew(stage, "product");

        Label label = new Label("REPORT ");
        label.setFont(Font.font("Calibri", fw, fp, 24));

        Label text = new Label();
        text.setFont(Font.font("Calibri", fw, fp, 18));

        Label productName = new Label("Enter Product Name   ");
        productName.setFont(Font.font("Calibri", fw, fp, 18));

        Label start = new Label("First Date:-");
        start.setFont(Font.font("Calibri", fw, fp, 18));

        Label end = new Label("  To   End Date:-");
        end.setFont(Font.font("Calibri", fw, fp, 18));

        profitButton = new Button("Profit");
        profitButton.setFont(Font.font("Calibri", fw, fp, 20));

        unitsButton = new Button("Sales");
        unitsButton.setFont(Font.font("Calibri", fw, fp, 20));

        TextField ProName = new TextField();
        ProName.setFont(Font.font("Calibri", fw, fp, 18));

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

        HBox PName = new HBox();
        PName.setSpacing(5);
        PName.setPadding(new Insets(10, 10, 10, 60));
        PName.getChildren().addAll(productName, ProName);

        HBox bb1 = new HBox();
        bb1.setSpacing(5);
        bb1.setPadding(new Insets(0, 10, 0, 200));
        bb1.getChildren().addAll(unitsButton);

        HBox bb2 = new HBox();
        bb2.setSpacing(5);
        bb2.setPadding(new Insets(0, 10, 0, 25));
        bb2.getChildren().addAll(profitButton);

        HBox hb2 = new HBox();
        hb2.setSpacing(5);
        hb2.setPadding(new Insets(0, 10, 0, 10));
        hb2.getChildren().addAll(bb1, bb2);

        HBox hb3 = new HBox();
        hb3.setSpacing(5);
        hb3.setPadding(new Insets(-5, 10, -5, 100));
        hb3.getChildren().addAll(text);

        VBox vb1 = new VBox();
        vb1.getChildren().addAll(hb1, PName, hb, hb2, hb3);
        vb1.setSpacing(15);
        vb1.setPadding(new Insets(10, 100, 10, 100));

        vb2.getChildren().addAll(vb1);
        vb2.setSpacing(10);
        vb2.setPadding(new Insets(10, 10, 10, 10));

        ProName.requestFocus();

        // stage.show();
        unitsButton.setOnAction((ActionEvent e) -> {
            CategoryAxis xAxis = new CategoryAxis();
            NumberAxis yAxis = new NumberAxis();

            LocalDate startDate = LocalDate.parse(tf1.getText());
            LocalDate endDate = LocalDate.parse(tf2.getText());

            // Create the bar chart
            BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

            // Define the data to be displayed in the chart
            XYChart.Series<String, Number> data1 = new XYChart.Series<>();

            long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);

            // Divide the date range into 10 dates
            LocalDate partDate = startDate;
            for (int i = 1; i <= 10; i++) {
                LocalDate endDateOfIteration = startDate.plusDays(i * (daysBetween / 10));

                double SalesUnits = displaySalesUnits(ProName.getText(), partDate.toString(), endDateOfIteration.toString());
                data1.getData().add(new XYChart.Data(endDateOfIteration.toString(), SalesUnits));
                partDate = endDateOfIteration.plusDays(1);
            }

            // Add data labels to the line chart
            for (Iterator it = data1.getData().iterator(); it.hasNext();) {
                XYChart.Data<String, Number> data = (XYChart.Data<String, Number>) it.next();
                Label label1 = new Label(data.getYValue().toString());
                label1.setStyle("-fx-font-size: 9pt;");
                data.setNode(label1);
            }

            // Add the data to the chart
            barChart.getData().add(data1);

            // Set the title of the chart
            barChart.setTitle("Sales");

            vb2.getChildren().addAll(barChart);
            vb2.setSpacing(10);
            vb2.setPadding(new Insets(10, 10, 10, 10));
        });

        profitButton.setOnAction((ActionEvent e) -> {
            CategoryAxis xAxisP = new CategoryAxis();
            NumberAxis yAxisP = new NumberAxis();

            LocalDate startDateP = LocalDate.parse(tf1.getText());
            LocalDate endDateP = LocalDate.parse(tf2.getText());

            // Create the bar chart
            BarChart<String, Number> barChartP = new BarChart<>(xAxisP, yAxisP);

            // Define the data to be displayed in the chart
            XYChart.Series<String, Number> data1P = new XYChart.Series<>();

            long daysBetweenP = ChronoUnit.DAYS.between(startDateP, endDateP);

            // Divide the date range into 10 dates
            LocalDate partDateP = startDateP;
            for (int i = 1; i <= 10; i++) {
                LocalDate endDateOfIterationP = startDateP.plusDays(i * (daysBetweenP / 10));
                double profit = displayProfitOfProduct(ProName.getText(), partDateP.toString(), endDateOfIterationP.toString());
                data1P.getData().add(new XYChart.Data(endDateOfIterationP.toString(), profit));
                partDateP = endDateOfIterationP.plusDays(1);
            }

            // Add data labels to the line chart
            for (Iterator it = data1P.getData().iterator(); it.hasNext();) {
                XYChart.Data<String, Number> dataP = (XYChart.Data<String, Number>) it.next();
                Label label2 = new Label(dataP.getYValue().toString());
                label2.setStyle("-fx-font-size: 9pt;");
                dataP.setNode(label2);
            }

            // Add the data to the chart
            barChartP.getData().add(data1P);

            // Set the title of the chart
            barChartP.setTitle("Profit");

            vb2.getChildren().addAll(barChartP);
            vb2.setSpacing(10);
            vb2.setPadding(new Insets(10, 10, 10, 10));

            //stage.show();
        });

        ProName.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                tf1.requestFocus();
            }
            if (event.getCode() == KeyCode.DOWN) {
                tf1.requestFocus();
            }

        });

        tf1.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                tf2.requestFocus();
            }
            if (event.getCode() == KeyCode.UP) {
                ProName.requestFocus();
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

        displayProducts();
        TextFields.bindAutoCompletion(ProName, products);

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

    public double displaySalesUnits(String stockName, String date1, String date2) {

        String sql = "SELECT stockName,SUM(qnt) FROM " + Constants.salesTable
                + " WHERE date BETWEEN '" + date1 + "' and '" + date2 + "' GROUP BY stockName ";

        double sale = 0;

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                String stockname = rs.getString("stockName");

                if (stockName.equals(stockname)) {
                    sale = sell.oneItemQnt(stockname, date1, date2);
                }
            }

        } catch (SQLException e) {
            logger.error("An error occurred while displaying sales units"
                    + "in the class data Visualization.", e);
        }
        return sale;
    }

    public double displayProfitOfProduct(String stockName, String date1, String date2) {
//
        String sql = "SELECT stockname,SUM(profit) FROM " + Constants.salesTable
                + " WHERE date BETWEEN '" + date1 + "' and '" + date2 + "' GROUP BY stockname ";

        double Profit = 0;

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                String stockname = rs.getString("stockname");

                if (stockName.equals(stockname)) {
                    Profit = rs.getDouble("SUM(profit)");
                }
            }

        } catch (SQLException e) {
            logger.error("An error occurred while displaying profit on the product"
                    + "in the class data Visualization.", e);
        }
        return Profit;
    }

    public void displayProducts() {
        String sql = "SELECT * FROM " + Constants.ProductTable + " ORDER BY stockname";

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String stockname = rs.getString("stockname");
                products.add(stockname);
            }
        } catch (SQLException e) {
            logger.error("An error occurred while displaying products"
                    + "in the class data Visualization.", e);
        }
    }
}
