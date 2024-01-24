// now useing
/*
package projectbms;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class bms {
    public static void main (String [] args)
    {
       try {
            String stockname;
            double price;
            double quantity;
            
            String expenses = "expenses";
            
            String empname;
            double salary;
            
         
            dataBase db = new dataBase();
            empDatabase edb = new empDatabase();
            manage mdb = new manage();
            sellTable stb =new sellTable();
            empAttandnce empattend = new empAttandnce();
            dues due = new dues ();
            
            Scanner sc = new Scanner (System.in);
            
             while(true)
        {
        System.out.println("\n\nPress 0 to exit ");
        System.out.println("Press 1 to creat a database and table in that data base ");
        System.out.println("Press 2 to enter products "); //Adiing product detiles (Name,price,qnt)
        System.out.println("Press 3 to enter selling ");  // selling goods (product name,code,price for sold,qnt)
        System.out.println("Press 4 to enter Purchase "); // Buying a goods (Product name,cod,price of purchace,qnt) 
        System.out.println("press 5 to update stockselling price");
        System.out.println("press 6 to delete a stock");
        System.out.println("Press 7 to enter spolied data");
        System.out.println("press 8 to enter expenses ");//dedcting(wages,bills paid ,and all expenises) for profit
        System.out.println("press 9 to enter employee details"); // to enter employee data
        System.out.println("press 10 to enter employee attandence"); // to enter emploeey working days and pro tells salrary
        System.out.println("Press 11 to calculate salary");
        System.out.println("Press 12 to update employee salary");
        System.out.println("Press 13 to delete employee");
        System.out.println("press 14 Display  ");
        System.out.println("press 15 for dues");
        
        System.out.print("Enter your choice :");
        int choice = sc.nextInt();
        
        switch (choice)
        {
            case 1 -> {
                System.out.println("Press 1 to create product Database and table in it");
                System.out.println("Press 2 to create database and table for storing data");
                System.out.println("Press 3 to create employee database");
                System.out.println("Press 4 to create elements ");
                
                while(true)
                {
                    System.out.print("Enter your choices :");
                    int choice4 = sc.nextInt();
                    
                    switch(choice4)
                    {
                        case 1 -> {
                            //dataBase.createNewDatabase("bmsList1.db");
                            //dataBase.createNewTable();
                            //sellTable.createNewTableSell();
                            //empAttandnce.createNewTable();
                            dues.createNewTable();
                        }
                        
                        case 2 -> {
                     //       manage.createNewDatabase("managedb1.db");
                            manage.createNewTable();
                        }
                        
                        case 3 -> {
                         //   empDatabase.createNewDatabase("bmsemplist1.db");
                            empDatabase.createNewTable();
                        }
                        
                        case 4 -> {
                            
                            String name1 ="stockamount";
                            String name2 ="profit";
                            String name3 ="expenses";
                            String name4="totalcsdues";
                            String name5="totalempdues";
                            String name6="totalourdues";
                            String name7="totaldue";
                            String name8="tax";
                            String name9 ="totalsales";
                            
                            double amount =0;
                            //mdb.insert(name1, amount);
                            //mdb.insert(name2, amount);
                            //mdb.insert(name3, amount);
                           // mdb.insert(name4, amount);
                            //mdb.insert(name5, amount);
                            //mdb.insert(name6, amount);
                            //mdb.insert(name7, amount);
                            //mdb.insert(name8, amount);
                           // mdb.insert(name9, amount);
                        }
                    }
                    break;
                }   
            }
                
            case 2 -> {
                sc.nextLine();
                System.out.print("Enter the name of stock buyed: ");
                stockname = sc.nextLine().toLowerCase().trim();
                
                System.out.print("Enter MRP of the product :");
                double mrp = sc.nextDouble();
                
                System.out.print("Enter buying price of the product:");
                price =sc.nextDouble();
                
                System.out.print("Enter the Qnt purchased:");
                quantity = sc.nextDouble();
                
                System.out.print("Enter % of tax ( CGST + SGST ) for the product ");
                double tax =sc.nextDouble();
                
                System.out.print("Enter selling price ");
                double sellPrice =sc.nextDouble();
              
                db.insert(stockname, quantity ,mrp ,price ,tax);
                stb.insert(stockname, sellPrice,quantity,mrp);
                    }
                
            case 3 -> {
                sc.nextLine();
                System.out.print("Enter name of the stock sold :");
                String sname = sc.nextLine().toLowerCase().trim();
                
                System.out.print("Enter qnt sold :");
                double qnt2 = sc.nextDouble();
                double sellingPrice =stb.getprice(sname);
                
                System.out.println("press 1 if u want to sell product with feeded price");
                System.out.println("press 2 if u want give selling price ");
                int input = sc.nextInt();
                
                if(input == 1){
              //  db.redcuceqnt(sname,qnt2,sellingPrice);
                }
                if(input == 2){
                System.out.print("Enter price of 1 unit of stock sold :");
                double price2 = sc.nextDouble();
               // db.redcuceqnt(sname,qnt2,price2);
                }
                    }
                
            case 4 -> {
                sc.nextLine();
                System.out.print("Enter product name :");
                String sname1=sc.nextLine().toLowerCase().trim();
                
                System.out.print("Enter qnt prachased :");
                double qnt1 = sc.nextDouble();
                sc.nextLine();
                
                System.out.print("Enter price of the product");
                double price1 = sc.nextDouble();
                
                db.increaseqnt(sname1, qnt1,price1);
                    }
            
            case 5 ->{
                sc.nextLine();
                System.out.println("Enter product name :");
                String sellname = sc.nextLine().toLowerCase().trim();
                
                System.out.println("Enter the updated selling price");
                double updated =sc.nextDouble();
                
                stb.update(sellname, updated);
            }
            
            case 6 -> {
                sc.nextLine();
                System.out.print("Enter product name to delete :");
                String product = sc.nextLine().toLowerCase().trim();
                db.delete(product);
                    }
            
            case 7 ->{
                sc.nextLine();
                System.out.print("Enter name of the product: ");
                String proname = sc.nextLine().toLowerCase().trim();
                
                System.out.println("Enter qnt spoiled");
                double qntt =sc.nextDouble();
                db.spoil(proname,qntt);
            }
                 
            
            case 8 -> {
                System.out.println("\n\n\nPress 1 to enter rent if any.");
                System.out.println("Press 2 to enter taxs paid.");
                System.out.println("press 3 to enter bills paid.");
                System.out.println("press 4 to enter other expenses.");
                
                while(true)
                {
                    System.out.print("Enter your choice :");
                    int choice2 = sc.nextInt();
                    
                    switch(choice2)
                    {
                        case 1 -> {
                            System.out.print("Enter the rent paid :");
                            double rent = sc.nextDouble();
                            mdb.increaseAmount(expenses, rent);
                            System.out.println("Expenses Incresed");
                        }
                    
                        case 2 -> {
                            System.out.print("Enter the tax paid :");
                            double tax = sc.nextDouble();
                            mdb.increaseAmount(expenses, tax);
                            System.out.println("Expenses Incresed");
                        }
                    
                        case 3 -> {
                            System.out.print("Enter bills paid :");
                            double bills = sc.nextDouble();
                            mdb.increaseAmount(expenses,bills);
                            System.out.println("Expenses Incresed");
                        }
                        
                        case 4 ->{
                            System.out.print("Enter other expenses :");
                            double expenses1 =sc.nextDouble();
                            mdb.increaseAmount(expenses,expenses1);
                            System.out.println("Expenses Incresed");
                        }
                    }
                    break;
                }   
            }
                
            case 9 -> {
                sc.nextLine();
                System.out.print("Enter employee name :");
                empname=sc.nextLine().toLowerCase().trim();
                
                System.out.print("Enter employee salary per day:");
                salary = sc.nextDouble();
                edb.insert(empname, salary);
            }
                
            case 10 ->{
                empattend.markattend();
            }
            
            case 11 ->{
                edb.calSal();
            }
            
            case 12-> {
                sc.nextLine();
                System.out.print("Enter employee name for whom salary should be updated :");
                String newname = sc.nextLine().toLowerCase().trim();
                
                System.out.print("Enter new salary to be updated ");
                double newsal = sc.nextDouble();
                edb.updateSal(newname, newsal);
            }
                
            case 13 -> {
                sc.nextLine();
                System.out.print("Enter employee name to delete :");
                String empname1 = sc.nextLine().toLowerCase().trim();
                
                edb.delete(empname1);
                    }
                
            case 14 -> {
                System.out.println("\n\n\nPress 1 to display proudcut list"); 
                System.out.println("press 2 to display sellingprice list");
                System.out.println("Press 3 to display one product details");
                System.out.println("press 4 to display stockamount");
                System.out.println("press 5 to dispaly the profit");// calulates profit 
                System.out.println("Press 6 to display expenses");
                System.out.println("press 7 to display revenue");
                System.out.println("Press 8 to display employee list");
                System.out.println("Enter 9 to display employee attandance List");
                System.out.println("Enter 10 to display customer total due amount ");
                System.out.println("Enter 11 to display employee total due amount");
                System.out.println("Enter 12 to display vendourd total due amount");
                System.out.println("Enter 13 to display total due amounts");
                System.out.println("Enter 14 to display total tax amount");
                
                while(true)
                {
                    System.out.print("Enter your choice : ");
                    int choice3 = sc.nextInt();
                    
                    switch(choice3)
                    {
                        case 1 -> {
                            System.out.println("THE PRODUCT LIST IS: ");
                            db.display();
                        }    
                        case 2 -> {
                             System.out.println("The PROUDCT SELLING LIST");
                            stb.display();
                        }
                        
                        case 3 ->{
                            sc.nextLine();
                            System.out.print("Enter product name to know its data: ");
                            String proname = sc.nextLine().toLowerCase().trim();
                            
                            db.displayItem(proname);
                            stb.displayItem(proname);
                        }
                            
                        case 4 -> {
                            System.out.print("THE STCOKAMOUNT IS : ");
                            String stockamount ="stockamount";
                            mdb.display(stockamount);
                        }
                            
                        case 5 -> {
                            System.out.print("PROFIT YOU GOT IS :");
                            String profit ="profit";
                            mdb.display(profit);
                        }
                            
                        case 6 -> {
                            System.out.print("EXPENSES YOU HAVE MADE :");
                            String expenses1 ="expenses";
                            mdb.display(expenses1);
                        }
                        
                        case 7 -> {
                            System.out.print("REVENUE YOU GOT TILL NOW :");
                            mdb.revenue();
                        }
                        
                        case 8 ->{
                            System.out.println("EMPLOYEE LIST IS :");
                            edb.display();
                        }
                        
                        case 9 ->{
                            System.out.println("EMPLOYEE ATTANDENCE LIST IS:");
                            empattend.display();
                        }
                        
                        case 10 ->{
                            System.out.print("TOTAL CUSTOMER DUE AMOUNT IS :");
                            String totalcsdue ="totalcsdues";
                            mdb.display(totalcsdue);
                        }
                        case 11 ->{
                            System.out.print("TOTAL EMPLOYEE DUE AMOUNT IS :");
                            String totalempdue ="totalempdues";
                            mdb.display(totalempdue);
                        }
                        case 12 ->{
                            System.out.print("TOTAL VENDOUR DUE AMOUNT IS :");
                            String totalourdue = "totalourdues";
                            mdb.display(totalourdue);
                        }
                        case 13 ->{
                            
                            String due1 ="totalcsdues";
                            String due2 ="totalempdues";
                            String due3 ="totalourdues";
                            
                            double value1 = mdb.getvalue(due1);
                            double value2 = mdb.getvalue(due2);
                            double value3 = mdb.getvalue(due3);
                            
                            double value4 = value1+value2;
                            double value5 = value3-value2+value3;
                            System.out.println("Total due we should get = "+value4);
                            System.out.println("Total due we should give - we should get = "+value3+" - "+value4+" = " +value5);
                        }
                        
                        case 14-> {
                            System.out.print("TOTAL TAX AMOUNT IS :");
                            String totaltax ="tax";
                            mdb.display(totaltax);
                        }
                    }
                    break;
                }   
            }
            
            case 15 ->{
                System.out.println("press 1 for entering coustmer due ");
                System.out.println("press 2 for entering employee due ");
                System.out.println("press 3 for entering our due");
                System.out.println("press 4 for display coustmer due");
                System.out.println("press 5 for display employee due");
                System.out.println("press 6 for display our due");
                System.out.println("press 7 for display costmer due list");
                System.out.println("press 8 for display employee due list");
                System.out.println("press 9 for display our due list");
                 while(true)
                {
                    System.out.print("Enter your choice : ");
                    int choice4 = sc.nextInt();
                            
                    switch(choice4){
                        case 1->{
                            sc.nextLine();
                            System.out.print("Enter coustmer name  :");
                            String csname = sc.nextLine().toLowerCase().trim();
                            
                            System.out.print("Enter coustmer due amount :");
                            double csdue = sc.nextDouble();
                            due.insertcs(csname, csdue);
                        }
                        
                        case 2->{
                            sc.nextLine();
                            System.out.print("Enter employee name :");
                            String emname = sc.nextLine().toLowerCase().trim();
                            
                            System.out.print("Enter employee due amount :");
                            double empdue = sc.nextDouble();
                            due.inssertemp(emname, empdue);
                        }
                        
                        case 3->{
                            sc.nextLine();
                             System.out.print("Enter vendour name amount :");
                            String ourname = sc.nextLine().toLowerCase().trim();
                            
                            System.out.print("Enter vendour due amount :");
                            double ourdue = sc.nextDouble();
                            due.insertour(ourname, ourdue);
                        }
                        
                        case 4->{
                            sc.nextLine();
                            System.out.print("Enter coustmer name :");
                            String cousname = sc.nextLine().toLowerCase().trim();
                            
                            due.displaycs(cousname);
                        }
                        
                        case 5->{
                            sc.nextLine();
                            System.out.print("Enter employee name :");
                            String eduename = sc.nextLine().toLowerCase().trim();
                            
                            due.displayemp(eduename);
                        }
                        
                        case 6->{
                            sc.nextLine();
                            System.out.print("Enter vendour name :");
                            String vendour = sc.nextLine().toLowerCase().trim();
                            
                            due.displayour(vendour);
                        }
                        
                        case 7->{
                            due.displayCsdueList();
                        }
                        
                        case 8->{
                            due.displayempdueList();
                        }
                        
                        case 9->{
                            due.displayOurdueList();
                        }
                    }
                   break;
                }
            }
            
                
            case 0 -> System.exit(0);
        }
    }
}
        catch(InputMismatchException e){
            Logger logger = LogManager.getLogger(bms.class);
            logger.error("Input miss match in clas bms.", e);
        }
        
    }
}
*/