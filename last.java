package projectbms;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import org.apache.logging.log4j.LogManager;

public class last extends parentform {
    Create getingData = new Create();

    String Title = getingData.getItem("Title");
    String subTitle = new String(Constants.subTitle);

    String name = getingData.getItem("ownerName");
    String phnNum1 = getingData.getItem("phnNum1");
    String PhnNum2 = getingData.getItem("phnNum2");
    String gstNum = getingData.getItem("gstNum");
    String address = getingData.getItem("Address");

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
    SimpleDateFormat formatterInBill = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
    Date date = new Date();

    String cName = new String("K Amar ");
    String cPhn = new String("9247830862");
    String setdate = new String(formatter.format(date));
    String setdateInBill = new String(formatterInBill.format(date));
    String InNum = new String("01");

    TableView<proData> ProTable = new TableView<proData>();
    ObservableList<proData> productData;

    TableView<taxData> taxTable = new TableView<taxData>();
    ObservableList<taxData> taxData;

    public String creatPdf(String custmoreName, double oldDue, double AmountPaid, String PaymentMethod) {

        display();
        taxes();

        ProTable.setItems(productData);
        taxTable.setItems(taxData);

        PDDocument invc;

        //Create Document
        invc = new PDDocument();
        //Create Blank Page
        PDPage newpage = new PDPage(PDRectangle.A4);
        //Add the blank page
        invc.addPage(newpage);

        BillData bd = new BillData();
        
        String filestr=null;
        try {

            File fontcr = new File(Constants.calibarRegular);
            PDFont font1 = PDType0Font.load(invc, fontcr);

            File fontcB = new File(Constants.calibarBold);
            PDFont font = PDType0Font.load(invc, fontcB);

            PDPage mypage = invc.getPage(0);
            PDPageContentStream cs = new PDPageContentStream(invc, mypage);

            cs.beginText();
            cs.setFont(font, 20);
            cs.newLineAtOffset(170, 750);
            cs.showText(Title);
            cs.endText();

            cs.beginText();
            cs.setFont(font, 18);
            cs.newLineAtOffset(270, 720);
            cs.showText(subTitle);
            cs.endText();

            cs.beginText();
            cs.setFont(font, 14);
            cs.setLeading(30f);
            cs.newLineAtOffset(60, 670);
            cs.showText("Name                   : ");
            cs.newLine();
            cs.showText("Phone Number1 : ");
            cs.newLine();
            cs.showText("Phone Number2 : ");
            cs.newLine();
            cs.showText("GST Number      : ");
            cs.newLine();
            cs.showText("Address               : ");
            cs.endText();

            cs.beginText();
            cs.setFont(font1, 14);
            cs.setLeading(30f);
            cs.newLineAtOffset(170, 670);
            cs.showText(name);
            cs.newLine();
            cs.showText(phnNum1);
            cs.newLine();
            cs.showText(PhnNum2);
            cs.newLine();
            cs.showText(gstNum);
            cs.newLine();
            cs.showText(address);
            cs.endText();

            cs.beginText();
            cs.setFont(font, 14);
            cs.setLeading(30f);
            cs.newLineAtOffset(330, 670);
            cs.showText("Customer Name : ");
            cs.newLine();
            cs.showText("Phone Number   : ");
            cs.newLine();
            cs.showText("Date                     : ");
            cs.newLine();
            cs.showText("Inovice Number : ");
            cs.endText();

            cs.beginText();
            cs.setFont(font1, 14);
            cs.setLeading(30f);

            cs.newLineAtOffset(440, 670);
            cs.showText(custmoreName);
            cs.newLine();
            cs.showText(getPhnNum(custmoreName));
            cs.newLine();
            cs.showText(setdateInBill);
            cs.newLine();
            cs.showText(InNum);
            cs.endText();

            cs.beginText();
            cs.setFont(font, 14);
            cs.newLineAtOffset(30, 500);
            cs.showText("NAME");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 14);
            cs.newLineAtOffset(140, 500);
            cs.showText("Qnt");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 14);
            cs.newLineAtOffset(180, 500);
            cs.showText("MRP");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 14);
            cs.newLineAtOffset(220, 500);
            cs.showText("TOT");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 14);
            cs.newLineAtOffset(270, 500);
            cs.showText("NET");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 14);
            cs.newLineAtOffset(320, 500);
            cs.showText("DIS");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 14);
            cs.newLineAtOffset(360, 500);
            cs.showText("NET ");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 14);
            cs.newLineAtOffset(410, 500);
            cs.showText("TAX");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 14);
            cs.newLineAtOffset(440, 500);
            cs.showText("TAX");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 14);
            cs.newLineAtOffset(490, 500);
            cs.showText("PRC");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 14);
            cs.newLineAtOffset(530, 500);
            cs.showText("TOTAL PRC");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 10);
            cs.newLineAtOffset(175, 480);
            cs.showText("(Of One)");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 10);
            cs.newLineAtOffset(217, 480);
            cs.showText("(Mrp)");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 10);
            cs.newLineAtOffset(270, 480);
            cs.showText("(Amt)");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 10);
            cs.newLineAtOffset(350, 480);
            cs.showText("(After dis)");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 10);
            cs.newLineAtOffset(415, 480);
            cs.showText("(%)");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 10);
            cs.newLineAtOffset(440, 480);
            cs.showText("(Amt)");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 10);
            cs.newLineAtOffset(490, 480);
            cs.showText("(Of One)");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 10);
            cs.newLineAtOffset(550, 480);
            cs.showText("(Incl Tax)");
            cs.endText();

            int n = 480;

            List<proData> items = ProTable.getItems();

            for (proData item : items) {

                if (n < 50) { // If the current page is full
                    cs.close(); // Close the current content stream
                    newpage = new PDPage(PDRectangle.A4); // Create a new page
                    invc.addPage(newpage);
                    cs = new PDPageContentStream(invc, newpage); // Create a new content stream for the new page
                    cs.setFont(font, 14); // Set the font for the new content stream
                    n = (int) PDRectangle.A4.getHeight() - 50; // Reset the current y position to the top of the new page
                }

                //    productname = bill.nameDis();
                cs.beginText();
                cs.setFont(font1, 12);
                cs.newLineAtOffset(30, n - 20);
                cs.showText(item.getFirstName());
                cs.endText();

                cs.beginText();
                cs.setFont(font1, 12);
                cs.newLineAtOffset(140, n - 20);
                cs.showText(item.getLastName());
                cs.endText();

                cs.beginText();
                cs.setFont(font1, 12);
                cs.newLineAtOffset(180, n - 20);
                cs.showText(item.getEmail());
                cs.endText();

                cs.beginText();
                cs.setFont(font1, 12);
                cs.newLineAtOffset(220, n - 20);
                cs.showText(item.getPrice());
                cs.endText();

                cs.beginText();
                cs.setFont(font1, 12);
                cs.newLineAtOffset(270, n - 20);
                cs.showText(item.getFive());
                cs.endText();

                cs.beginText();
                cs.setFont(font1, 12);
                cs.newLineAtOffset(320, n - 20);
                cs.showText(item.getSix());
                cs.endText();

                cs.beginText();
                cs.setFont(font1, 12);
                cs.newLineAtOffset(360, n - 20);
                cs.showText(item.getSeven());
                cs.endText();

                cs.beginText();
                cs.setFont(font1, 12);
                cs.newLineAtOffset(410, n - 20);
                cs.showText(item.getEight());
                cs.endText();

                cs.beginText();
                cs.setFont(font1, 12);
                cs.newLineAtOffset(440, n - 20);
                cs.showText(item.getNine());
                cs.endText();

                cs.beginText();
                cs.setFont(font1, 12);
                cs.newLineAtOffset(490, n - 20);
                cs.showText(item.getTen());
                cs.endText();

                cs.beginText();
                cs.setFont(font1, 12);
                cs.newLineAtOffset(540, n - 20);
                cs.showText(item.getEleven());
                cs.endText();

                n = n - 20;
            }
            cs.beginText();
            cs.setFont(font1, 12);
            cs.newLineAtOffset(40, n - 20);
            cs.showText("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 12);
            cs.newLineAtOffset(40, n - 35);
            cs.showText("Total :");
            cs.endText();

            Formatter formatter0 = new Formatter();
            Formatter formatter1 = new Formatter();
            Formatter formatter2 = new Formatter();
            Formatter formatter3 = new Formatter();
            Formatter formatter4 = new Formatter();
            Formatter formatter5 = new Formatter();
            Formatter formatter6 = new Formatter();
            Formatter formatterx1 = new Formatter();
            Formatter formatterx2 = new Formatter();

            cs.beginText();
            cs.setFont(font, 12);
            cs.newLineAtOffset(100, n - 35);
            cs.showText(String.valueOf(formatter0.format("%.1f", totals("count"))));
            cs.endText();

            cs.beginText();
            cs.setFont(font, 12);
            cs.newLineAtOffset(140, n - 35);
            cs.showText(String.valueOf(formatter1.format("%.1f", totals("totalQnt"))));
            cs.endText();

            cs.beginText();
            cs.setFont(font, 12);
            cs.newLineAtOffset(220, n - 35);
            cs.showText(String.valueOf(formatter2.format("%.1f", totals("totalMrp"))));
            cs.endText();

            cs.beginText();
            cs.setFont(font, 12);
            cs.newLineAtOffset(320, n - 35);
            cs.showText(String.valueOf(formatter4.format("%.1f", totals("totalDiscount"))));
            cs.endText();

            cs.beginText();
            cs.setFont(font, 12);
            cs.newLineAtOffset(360, n - 35);
            cs.showText(String.valueOf(formatter3.format("%.1f", totals("totalNet"))));
            cs.endText();

            cs.beginText();
            cs.setFont(font, 12);
            cs.newLineAtOffset(440, n - 35);
            cs.showText(String.valueOf(formatter5.format("%.1f", totals("totalTaxAmount"))));
            cs.endText();

            cs.beginText();
            cs.setFont(font, 12);
            cs.newLineAtOffset(540, n - 35);
            cs.showText(String.valueOf(formatter6.format("%.1f", totals("totalAmount"))));
            cs.endText();

            System.out.println(" n value =" + n);

            if (n < 235) { // If the current page is full
                cs.close(); // Close the current content stream
                newpage = new PDPage(PDRectangle.A4); // Create a new page
                invc.addPage(newpage);
                cs = new PDPageContentStream(invc, newpage); // Create a new content stream for the new page
                cs.setFont(font, 14); // Set the font for the new content stream
                n = (int) PDRectangle.A4.getHeight() - 10; // Reset the current y position to the top of the new page
            }
            System.out.println("h=" + PDRectangle.A4.getHeight() + "   W=" + PDRectangle.A4.getWidth());
            cs.beginText();
            cs.setFont(font, 14);
            cs.newLineAtOffset(40, n = n - 70);
            cs.showText("TAX DETAILS");
            cs.endText();

            cs.beginText();
            cs.setFont(font1, 12);
            cs.newLineAtOffset(40, n = n - 10);
            cs.showText("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 13);
            cs.newLineAtOffset(40, n = n - 15);
            cs.showText("SALE VALUE");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 13);
            cs.newLineAtOffset(120, n);
            cs.showText("SGST%");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 13);
            cs.newLineAtOffset(170, n);
            cs.showText("TAX");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 13);
            cs.newLineAtOffset(230, n);
            cs.showText("SALE VALUE");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 13);
            cs.newLineAtOffset(300, n);
            cs.showText("CGST%");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 13);
            cs.newLineAtOffset(350, n);
            cs.showText("TAX");
            cs.endText();
            
            cs.beginText();
            cs.setFont(font, 13);
            cs.newLineAtOffset(410, n);
            cs.showText("CESS%");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 13);
            cs.newLineAtOffset(460, n);
            cs.showText("CESS");
            cs.endText();

            List<taxData> taxitems = taxTable.getItems();

            for (taxData taxitem : taxitems) {

                cs.beginText();
                cs.setFont(font1, 12);
                cs.newLineAtOffset(40, n = n - 20);
                cs.showText(taxitem.getFirstName());
                cs.endText();

                cs.beginText();
                cs.setFont(font1, 12);
                cs.newLineAtOffset(120, n);
                cs.showText(taxitem.getLastName());
                cs.endText();

                cs.beginText();
                cs.setFont(font1, 12);
                cs.newLineAtOffset(170, n);
                cs.showText(taxitem.getEmail());
                cs.endText();

                cs.beginText();
                cs.setFont(font1, 12);
                cs.newLineAtOffset(230, n);
                cs.showText(taxitem.getPrice());
                cs.endText();

                cs.beginText();
                cs.setFont(font1, 12);
                cs.newLineAtOffset(300, n);
                cs.showText(taxitem.getFive());
                cs.endText();

                cs.beginText();
                cs.setFont(font1, 12);
                cs.newLineAtOffset(350, n);
                cs.showText(taxitem.getSix());
                cs.endText();
                
                cs.beginText();
                cs.setFont(font1, 12);
                cs.newLineAtOffset(410, n);
                cs.showText("0.0 ");
                cs.endText();

                cs.beginText();
                cs.setFont(font1, 12);
                cs.newLineAtOffset(460, n);
                cs.showText("0.0");
                cs.endText();

            }
            cs.beginText();
            cs.setFont(font1, 12);
            cs.newLineAtOffset(40, n = n - 20);
            cs.showText("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 13);
            cs.newLineAtOffset(40, n = n - 20);
            cs.showText("Total");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 13);
            cs.newLineAtOffset(120, n);
            cs.showText("SGST");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 13);
            cs.newLineAtOffset(170, n);
            cs.showText(String.valueOf(formatterx1.format("%.1f", totals("totalTaxAmount") / 2)));
            cs.endText();

            cs.beginText();
            cs.setFont(font, 13);
            cs.newLineAtOffset(300, n);
            cs.showText("CGST");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 13);
            cs.newLineAtOffset(350, n);
            cs.showText(String.valueOf(formatterx2.format("%.1f", totals("totalTaxAmount") / 2)));
            cs.endText();
            
             cs.beginText();
            cs.setFont(font, 13);
            cs.newLineAtOffset(410, n);
            cs.showText("CESS");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 13);
            cs.newLineAtOffset(460, n);
            cs.showText("0.0");
            cs.endText();

            if (n < 395) { // If the current page is full
                cs.close(); // Close the current content stream
                newpage = new PDPage(PDRectangle.A4); // Create a new page
                invc.addPage(newpage);
                cs = new PDPageContentStream(invc, newpage); // Create a new content stream for the new page
                cs.setFont(font, 14); // Set the font for the new content stream
                n = (int) PDRectangle.A4.getHeight() - 10; // Reset the current y position to the top of the new page
            }

            Formatter formatter7 = new Formatter();
            Formatter formatter8 = new Formatter();
            Formatter formatter9 = new Formatter();
            Formatter formatter10 = new Formatter();
            Formatter formatter11 = new Formatter();
            Formatter formatter12 = new Formatter();
            Formatter formatter13 = new Formatter();
            Formatter formatter14 = new Formatter();
            Formatter formatter15 = new Formatter();

            cs.beginText();
            cs.setFont(font, 14);
            cs.newLineAtOffset(350, n = n - 25);
            cs.showText("Total Net Amount   :");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 14);
            cs.newLineAtOffset(470, n);
            cs.showText(String.valueOf(formatter7.format("%.1f", totals("totalNet"))));
            cs.endText();

            cs.beginText();
            cs.setFont(font, 14);
            cs.newLineAtOffset(350, n = n - 25);
            cs.showText("Total Tax ");
            cs.endText();

            cs.beginText();
            cs.setFont(font1, 10);
            cs.newLineAtOffset(410, n);
            cs.showText("(CGST+SGST)");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 14);
            cs.newLineAtOffset(465, n);
            cs.showText(": ");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 14);
            cs.newLineAtOffset(470, n);
            cs.showText(String.valueOf(formatter8.format("%.1f", totals("totalTaxAmount"))));
            cs.endText();

            cs.beginText();
            cs.setFont(font, 14);
            cs.newLineAtOffset(350, n = n - 25);
            cs.showText("Total Amount          : ");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 14);
            cs.newLineAtOffset(470, n);
            cs.showText(String.valueOf(formatter10.format("%.1f", totals("totalAmount"))));
            cs.endText();

            cs.beginText();
            cs.setFont(font, 14);
            cs.newLineAtOffset(350, n = n - 25);
            cs.showText("Rounded to              : ");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 14);
            cs.newLineAtOffset(470, n);
            cs.showText(String.valueOf(formatter11.format("%.1f", Math.round(totals("totalAmount") * 100.0) / 100.0)));
            cs.endText();

            cs.beginText();
            cs.setFont(font, 14);
            cs.newLineAtOffset(350, n = n - 25);
            cs.showText("Total Discount         :");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 14);
            cs.newLineAtOffset(470, n);
            cs.showText(String.valueOf(formatter9.format("%.1f", totals("totalDiscount"))));
            cs.endText();

            cs.beginText();
            cs.setFont(font, 14);
            cs.newLineAtOffset(40, n = n - 20);
            cs.showText("Amount In Words : ");
            cs.endText();

            cs.beginText();
            cs.setFont(font1, 14);
            cs.newLineAtOffset(160, n);
            cs.showText(bd.NumberToWords((int) totals("totalAmount")) + " only");
            cs.endText();

            int gg = 0;
            cs.beginText();
            cs.setFont(font1, 12);
            cs.newLineAtOffset(40, n = n - 15);
            cs.showText("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
            cs.endText();

            System.out.println("after 1st data " + gg);
            if (n < 540) { // If the current page is full
                cs.close(); // Close the current content stream
                newpage = new PDPage(PDRectangle.A4); // Create a new page
                invc.addPage(newpage);
                cs = new PDPageContentStream(invc, newpage); // Create a new content stream for the new page
                cs.setFont(font, 14); // Set the font for the new content stream
                n = (int) PDRectangle.A4.getHeight() - 10; // Reset the current y position to the top of the new page
            }

            cs.beginText();
            cs.setFont(font, 14);
            cs.newLineAtOffset(350, n = n - 25);
            cs.showText("Pervious Due           : ");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 14);
            cs.newLineAtOffset(470, n);
            cs.showText(String.valueOf(formatter12.format("%.1f", Math.round(oldDue * 100.0) / 100.0)));
            cs.endText();

            cs.beginText();
            cs.setFont(font, 14);
            cs.newLineAtOffset(350, n = n - 25);
            cs.showText("Total Amt ");
            cs.endText();

            cs.beginText();
            cs.setFont(font1, 10);
            cs.newLineAtOffset(420, n);
            cs.showText("(Incl Due) ");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 14);
            cs.newLineAtOffset(465, n);
            cs.showText(":");
            cs.endText();

            double totAmount = oldDue+ totals("totalAmount");
                    
            cs.beginText();
            cs.setFont(font, 14);
            cs.newLineAtOffset(470, n);
            cs.showText(String.valueOf(formatter13.format("%.1f", Math.round(totAmount * 100.0) / 100.0)));
            cs.endText();

            cs.beginText();
            cs.setFont(font, 14);
            cs.newLineAtOffset(350, n = n - 25);
            cs.showText("Amount Paid            : ");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 14);
            cs.newLineAtOffset(470, n);
            cs.showText(String.valueOf(formatter14.format("%.1f", AmountPaid)));
            cs.endText();

            cs.beginText();
            cs.setFont(font, 14);
            cs.newLineAtOffset(350, n = n - 25);
            cs.showText("Amount Due             : ");
            cs.endText();

            double newDue = totAmount - AmountPaid;
            cs.beginText();
            cs.setFont(font, 14);
            cs.newLineAtOffset(470, n);
            cs.showText(String.valueOf(formatter15.format("%.1f", Math.round(newDue * 100.0) / 100.0)));
            cs.endText();

            cs.beginText();
            cs.setFont(font, 14);
            cs.newLineAtOffset(350, n = n - 25);
            cs.showText("Payment Mode        : ");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 14);
            cs.newLineAtOffset(470, n);
            cs.showText(PaymentMethod);
            cs.endText();

            cs.beginText();
            cs.setFont(font1, 12);
            cs.newLineAtOffset(40, n = n - 25);
            cs.showText("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
            cs.endText();

            cs.beginText();
            cs.setFont(font1, 14);
            cs.newLineAtOffset(200, n = n - 15);
            cs.showText("THANKYOU!! PLEASE VIST AGAIN. ");
            cs.endText();

            System.out.println(n);

            cs.close();

            //Save the PDF
            filestr = "C:\\IMS3\\Bills\\" + custmoreName + " " + setdate + ".pdf";
            invc.save(filestr);
            System.out.println("PDF GENRATED");

        } catch (IOException e) {
           org.apache.logging.log4j.Logger logger = LogManager.getLogger(last.class);
            logger.error("An error occurred while preparing bill pdf.", e);
        }
        return filestr;
    }

    public static class proData {

        private final SimpleStringProperty firstName;
        private final SimpleStringProperty lastName;
        private final SimpleStringProperty email;
        private final SimpleStringProperty Price;
        private final SimpleStringProperty five;
        private final SimpleStringProperty six;
        private final SimpleStringProperty seven;
        private final SimpleStringProperty eight;
        private final SimpleStringProperty nine;
        private final SimpleStringProperty ten;
        private final SimpleStringProperty eleven;

        private proData(String fName, String lName, String email, String price, String five, String six,
                String seven, String eight, String nine, String ten, String eleven) {
            this.firstName = new SimpleStringProperty(fName);
            this.lastName = new SimpleStringProperty(lName);
            this.email = new SimpleStringProperty(email);
            this.Price = new SimpleStringProperty(price);
            this.five = new SimpleStringProperty(five);
            this.six = new SimpleStringProperty(six);
            this.seven = new SimpleStringProperty(seven);
            this.eight = new SimpleStringProperty(eight);
            this.nine = new SimpleStringProperty(nine);
            this.ten = new SimpleStringProperty(ten);
            this.eleven = new SimpleStringProperty(eleven);
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

        public String getFive() {
            return five.get();
        }

        public void setFive(String fName) {
            five.set(fName);
        }

        public String getSix() {
            return six.get();
        }

        public void setSix(String fName) {
            six.set(fName);
        }

        public String getSeven() {
            return seven.get();
        }

        public void setSeven(String fName) {
            seven.set(fName);
        }

        public String getEight() {
            return eight.get();
        }

        public void setEight(String fName) {
            eight.set(fName);
        }

        public String getNine() {
            return nine.get();
        }

        public void setNine(String fName) {
            nine.set(fName);
        }

        public String getTen() {
            return ten.get();
        }

        public void setTen(String fName) {
            ten.set(fName);
        }

        public String getEleven() {
            return eleven.get();
        }

        public void setEleven(String fName) {
            eleven.set(fName);
        }
    }

    public void display() {
        String sql = "SELECT * FROM " + Constants.bill + " ORDER BY name";

        try ( Connection conn = this.connect();  Statement stmt = conn.createStatement();  ResultSet rs = stmt.executeQuery(sql)) {

            productData = FXCollections.observableArrayList();

            while (rs.next()) {
                String stockname = rs.getString("name");
                double qnt = rs.getDouble("quantity");
                double mrp = rs.getDouble("mrp");
                double totMrp = rs.getDouble("totMrp");
                double net = rs.getDouble("net");
                double dis = rs.getDouble("dis");
                double netDis = rs.getDouble("netDis");
                double taxP = rs.getDouble("taxP");
                double taxA = rs.getDouble("taxA");
                double price = rs.getDouble("price");
                double totPrice = rs.getDouble("totPrice");

                Formatter formatter0 = new Formatter();
                Formatter formatter1 = new Formatter();
                Formatter formatter2 = new Formatter();
                Formatter formatter3 = new Formatter();
                Formatter formatter4 = new Formatter();
                Formatter formatter5 = new Formatter();
                Formatter formatter6 = new Formatter();
                Formatter formatter7 = new Formatter();
                Formatter formatter8 = new Formatter();
                Formatter formatter9 = new Formatter();

                productData.add(new proData(stockname, String.valueOf(formatter0.format("%.1f", qnt)), String.valueOf(formatter1.format("%.1f", mrp)),
                        String.valueOf(formatter2.format("%.1f", totMrp)), String.valueOf(formatter3.format("%.1f", net)), String.valueOf(formatter4.format("%.1f", dis)),
                        String.valueOf(formatter5.format("%.1f", netDis)), String.valueOf(formatter6.format("%.1f", taxP)), String.valueOf(formatter7.format("%.1f", taxA)),
                        String.valueOf(formatter8.format("%.1f", price)), String.valueOf(formatter9.format("%.1f", totPrice))));
            }
        } catch (SQLException e) {
           org.apache.logging.log4j.Logger logger = LogManager.getLogger(last.class);
            logger.error("An error occurred while disaplying products into bill.", e);
        }
    }

    public double totals(String text) {
        String sql = "SELECT * FROM " + Constants.bill + " ORDER BY name";

        double Count = 0;
        double giveBack = 0;
        try ( Connection conn = this.connect();  Statement stmt = conn.createStatement();  ResultSet rs = stmt.executeQuery(sql)) {

            double totalQnt = 0;
            double totalMrp = 0;
            double totalNet = 0;
            double totalDis = 0;
            double totalTaxAmount = 0;
            double totalPrice = 0;

            while (rs.next()) {

                double qnt = rs.getDouble("quantity");
                double totMrp = rs.getDouble("totMrp");
                double net = rs.getDouble("netDis");
                double dis = rs.getDouble("dis");
                double taxA = rs.getDouble("taxA");
                double totPrice = rs.getDouble("totPrice");

                Count++;
                totalQnt = qnt + totalQnt;
                totalMrp = totMrp + totalMrp;
                totalNet = net + totalNet;
                totalDis = dis + totalDis;
                totalTaxAmount = taxA + totalTaxAmount;
                totalPrice = totPrice + totalPrice;
            }

            if ("count".equals(text)) {
                giveBack = Count++;
            } else if ("totalQnt".equals(text)) {
                giveBack = totalQnt;
            } else if ("totalMrp".equals(text)) {
                giveBack = totalMrp;
            } else if ("totalNet".equals(text)) {
                giveBack = totalNet;
            } else if ("totalDiscount".equals(text)) {
                giveBack = totalDis;
            } else if ("totalTaxAmount".equals(text)) {
                giveBack = totalTaxAmount;
            } else if ("totalAmount".equals(text)) {
                giveBack = totalPrice;
            } else {
                giveBack = 0;
            }

        } catch (SQLException e) {
           org.apache.logging.log4j.Logger logger = LogManager.getLogger(last.class);
            logger.error("An error occurred while disalying total amounts of bill.", e);
        }
        return giveBack;
    }

    public static class taxData {

        private final SimpleStringProperty firstName;
        private final SimpleStringProperty lastName;
        private final SimpleStringProperty email;
        private final SimpleStringProperty Price;
        private final SimpleStringProperty five;
        private final SimpleStringProperty six;

        private taxData(String fName, String lName, String email, String price, String five, String six) {
            this.firstName = new SimpleStringProperty(fName);
            this.lastName = new SimpleStringProperty(lName);
            this.email = new SimpleStringProperty(email);
            this.Price = new SimpleStringProperty(price);
            this.five = new SimpleStringProperty(five);
            this.six = new SimpleStringProperty(six);
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

        public String getFive() {
            return five.get();
        }

        public void setFive(String fName) {
            five.set(fName);
        }

        public String getSix() {
            return six.get();
        }

        public void setSix(String fName) {
            six.set(fName);
        }
    }

    public void taxes() {
        String sql = "SELECT * FROM " + Constants.bill + " ORDER BY name";

        try ( Connection conn = this.connect();  Statement stmt = conn.createStatement();  ResultSet rs = stmt.executeQuery(sql)) {

            TreeMap<Double, Double> taxPercentMap = new TreeMap<>();
            Map<Double, Double> netAmountMap = new HashMap<>();

            taxData = FXCollections.observableArrayList();

            while (rs.next()) {
                double taxPercent = rs.getDouble("taxP");
                double taxAmount = rs.getDouble("taxA");
                double net = rs.getDouble("netDis");

                taxPercentMap.put(taxPercent, taxPercentMap.getOrDefault(taxPercent, 0.0) + taxAmount);
                netAmountMap.put(taxPercent, netAmountMap.getOrDefault(taxPercent, 0.0) + net);
            }

            for (Map.Entry<Double, Double> entry : taxPercentMap.entrySet()) {
                double taxPercent = entry.getKey();
                double taxAmt = entry.getValue();
                double netAmt = netAmountMap.get(taxPercent);

                Formatter formatter0 = new Formatter();
                Formatter formatter1 = new Formatter();
                Formatter formatter2 = new Formatter();
                Formatter formatter3 = new Formatter();
                Formatter formatter4 = new Formatter();
                Formatter formatter5 = new Formatter();

                taxData.add(new taxData(String.valueOf(formatter0.format("%.1f", netAmt)), String.valueOf(formatter1.format("%.1f", taxPercent / 2)), String.valueOf(formatter2.format("%.1f", taxAmt / 2)),
                        String.valueOf(formatter3.format("%.1f", netAmt)), String.valueOf(formatter4.format("%.1f", taxPercent / 2)), String.valueOf(formatter5.format("%.1f", taxAmt / 2))));
            }

        } catch (SQLException e) {
            org.apache.logging.log4j.Logger logger = LogManager.getLogger(last.class);
            logger.error("An error occurred while calucting taxes of bill.", e);
        }
    }

    public String getPhnNum(String name) {
        String sql = "SELECT mobile FROM " + Constants.customerTable + " WHERE cNAME = ?";

        String phnNum = null;
        try ( Connection conn = this.connectMange();  PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                phnNum = rs.getString("mobile");
            }
            
        } catch (SQLException e) {
           org.apache.logging.log4j.Logger logger = LogManager.getLogger(last.class);
            logger.error("An error occurred while getting customer phone number.", e);
        }
        return phnNum;
    }
    
    public String getMail(String name) {
        String sql = "SELECT mail FROM " + Constants.customerTable + " WHERE cNAME = ?";

        String Mail = null;
        try ( Connection conn = this.connectMange();  PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                Mail = rs.getString("mail");
            }
            
        } catch (SQLException e) {
           org.apache.logging.log4j.Logger logger = LogManager.getLogger(last.class);
            logger.error("An error occurred while getting customer phone number.", e);
        }
        return Mail;
    }

    private Connection connect() {
        // SQLite connection string
        //String url = "jdbc:sqlite:V://projectBms/bmsList1.db";
        String url = Constants.productDB;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            org.apache.logging.log4j.Logger logger = LogManager.getLogger(last.class);
            logger.error("An error occurred while connecting to product db.", e);
        }
        return conn;
    }

    private Connection connectMange() {
        // SQLite connection string
        // String url = "jdbc:sqlite:V://projectBms/bmsList1.db";
        String url = Constants.mangeDB;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            org.apache.logging.log4j.Logger logger = LogManager.getLogger(last.class);
            logger.error("An error occurred while conneting to mange db.", e);
        }
        return conn;
    }
}
