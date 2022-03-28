package Billing;

import java.sql.*;
import java.util.*;

public class Report {
    Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/Customer","root","Muthujega@2001");

    public Report() throws SQLException {
    }

    // To view the sales report of the store
    
    public void report(int choice) throws Exception{
        Scanner in = new Scanner(System.in);
        System.out.println();
        System.out.println();
        System.out.println("   Sales Report Generation");
        System.out.println();
        System.out.print("Enter the date from: ");
        String from = in.nextLine();
        System.out.print("Enter the date to: ");
        String to = in.nextLine();
        PreparedStatement st = connection.prepareStatement("SELECT sum(quantity) FROM orders o JOIN bill b ON o.billno = b.billno WHERE b.storeid = ? AND orderdate between ? and ? ");
        st.setInt(1,choice);
        st.setString(2,from);
        st.setString(3,to);
        ResultSet tp = st.executeQuery();
        while(tp.next())
        {
            System.out.println("Total products selled: " + tp.getInt(1));
            System.out.println();
        }
        
        // To view the total price of the products selled in the store
        
        PreparedStatement pst = connection.prepareStatement("SELECT sum(price) FROM bill WHERE storeid= ? AND orderdate between ? and ?");
        pst.setInt(1,choice);
        pst.setString(2,from);
        pst.setString(3,to);
        ResultSet ta = pst.executeQuery();
        while (ta.next())
        {
            System.out.println("Total amount: " + ta.getInt(1));
            System.out.println();
        }
        
        // To view the total profit of the store 
        
        PreparedStatement spt = connection.prepareStatement("SELECT sum(profit) FROM bill WHERE storeid= ? AND orderdate between ? and ?");
        spt.setInt(1,choice);
        spt.setString(2,from);
        spt.setString(3,to);
        ResultSet pr = spt.executeQuery();
        while(pr.next()){
            System.out.println("Total profit: " + pr.getInt(1));
            System.out.println();
        }
        
        // To view the overall top buying customer of the store based on the total buying price of the customer
        
        int cus = 0;
        PreparedStatement stp = connection.prepareStatement("SELECT CusId , sum(price) FROM bill WHERE storeid= ? AND orderdate between ? and ? GROUP BY CusId ORDER BY sum(price) DESC LIMIT 1");
        stp.setInt(1,choice);
        stp.setString(2,from);
        stp.setString(3,to);
        ResultSet oc = stp.executeQuery();
        while(oc.next()){
            System.out.println("Overall Top Buying Customer: " + oc.getInt(1) + "  Price: " + oc.getInt(2));
            cus = oc.getInt(1);
            System.out.println();
        }
        
        // To view the top billing amount of the store 
        
        PreparedStatement a = connection.prepareStatement("SELECT price FROM bill WHERE storeid = ? AND orderdate between ? and ? ORDER BY price DESC LIMIT 1");
        a.setInt(1,choice);
        a.setString(2,from);
        a.setString(3,to);
        ResultSet bp = a.executeQuery();
        while(bp.next()){
            System.out.println("Top billing amount: " + bp.getInt(1));
            System.out.println();
        }
        
        // To view the top three items sold in the store
        
        System.out.println("Top 3 Selling product: ");
        PreparedStatement b = connection.prepareStatement("SELECT proid , name , sum(quantity) FROM orders o JOIN bill b ON o.billno = b.billno WHERE storeid = ? AND orderdate between ? and ? GROUP BY proid ORDER BY sum(quantity) DESC LIMIT 3");
        b.setInt(1,choice);
        b.setString(2,from);
        b.setString(3,to);
        ResultSet r = b.executeQuery();
        System.out.println( "Product Id     " + "Product Name " + "     " + "Quantity" );
        while(r.next())
        {
            System.out.printf("%-15d%-20s%-12d",r.getInt(1),r.getString(2),r.getInt(3));
            System.out.println();
        }
        System.out.println();
        
        // To view the top three non-selling items in the store
        
        System.out.println("Top 3 Non-Selling Items: ");
        PreparedStatement ab = connection.prepareStatement("SELECT proid , name , sum(quantity) FROM orders o JOIN bill b ON o.billno = b.billno WHERE storeid = ? AND orderdate between ? and ? GROUP BY proid ORDER BY sum(quantity) LIMIT 3");
        ab.setInt(1,choice);
        ab.setString(2,from);
        ab.setString(3,to);
        ResultSet rs = ab.executeQuery();
        System.out.println( "Product Id     " + "Product Name " + "     " + "Quantity" );
        while(rs.next())
        {
            System.out.printf("%-15d%-20s%-12d",rs.getInt(1),rs.getString(2),rs.getInt(3));
            System.out.println();
        }
        
        // To view the available stocks of the items present in the store
        
        System.out.println();
        System.out.println("Stocks of Item Remaining: ");
        PreparedStatement ba = connection.prepareStatement("SELECT productId , product , stock FROM item WHERE storeid = ? ");
        ba.setInt(1,choice);
        ResultSet rss = ba.executeQuery();
        System.out.println( "Product Id     " + "Product Name " + "     " + "Remaining" );
        while(rss.next())
        {
            System.out.printf("%-15d%-20s%-12d",rss.getInt(1),rss.getString(2),rss.getInt(3));
            System.out.println();
        }
        
        // To view the out of items in the store
        
        System.out.println();
        System.out.println("Out of Stock Items: ");
        PreparedStatement cc = connection.prepareStatement("SELECT productId, product FROM item WHERE storeid= ? AND stock = 0");
        cc.setInt(1,choice);
        ResultSet sr = cc.executeQuery();
        if(sr.next())
        {
            System.out.println( "Product Id     " + "Product Name ");
            System.out.printf("%-15d%-20s",sr.getInt(1),sr.getString(2));
        }
        else if(!sr.next()){
            System.out.println("All items are in stock..");
        }
    }
}

