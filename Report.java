import java.sql.*;

public class Report {
    Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/Customer","root","Muthujega@2001");

    public Report() throws SQLException {
    }

    public void report(int choice) throws Exception{
        System.out.println();
        System.out.println();
        System.out.println("   Sales Report Generation");
        System.out.println();
        PreparedStatement st = connection.prepareStatement("SELECT sum(quantity) FROM orders o JOIN bill b ON o.billno = b.billno WHERE b.storeid = ? ");
        st.setInt(1,choice);
        ResultSet tp = st.executeQuery();
        while(tp.next())
        {
            System.out.println("Total products selled: " + tp.getInt(1));
            System.out.println();
        }

        PreparedStatement pst = connection.prepareStatement("SELECT sum(price) FROM bill WHERE storeid= ? ");
        pst.setInt(1,choice);
        ResultSet ta = pst.executeQuery();
        while (ta.next())
        {
            System.out.println("Total amount: " + ta.getInt(1));
            System.out.println();
        }
        PreparedStatement spt = connection.prepareStatement("SELECT sum(profit) FROM bill WHERE storeid= ? ");
        spt.setInt(1,choice);
        ResultSet pr = spt.executeQuery();
        while(pr.next()){
            System.out.println("Total profit: " + pr.getInt(1));
            System.out.println();
        }
        int cus = 0;
        PreparedStatement stp = connection.prepareStatement("SELECT CusId , sum(price) FROM bill WHERE storeid= ? GROUP BY CusId ORDER BY sum(price) DESC LIMIT 1");
        stp.setInt(1,choice);
        ResultSet oc = stp.executeQuery();
        while(oc.next()){
            System.out.println("Overall Top Buying Customer: " + oc.getInt(1) + "  Price: " + oc.getInt(2));
            cus = oc.getInt(1);
            System.out.println();
        }
        PreparedStatement a = connection.prepareStatement("SELECT price FROM bill WHERE storeid = ? ORDER BY price DESC LIMIT 1");
        a.setInt(1,choice);
        ResultSet bp = a.executeQuery();
        while(bp.next()){
            System.out.println("Top billing amount: " + bp.getInt(1));
            System.out.println();
        }
        System.out.println("Top 3 Selling product: ");
        PreparedStatement b = connection.prepareStatement("SELECT proid , name , sum(quantity) FROM orders o JOIN bill b ON o.billno = b.billno WHERE storeid = ? GROUP BY proid ORDER BY sum(quantity) DESC LIMIT 3");
        b.setInt(1,choice);
        ResultSet r = b.executeQuery();
        System.out.println( "Product Id     " + "Product Name " + "     " + "Quantity" );
        while(r.next())
        {
            System.out.printf("%-15d%-20s%-12d",r.getInt(1),r.getString(2),r.getInt(3));
            System.out.println();
        }
        System.out.println();
        System.out.println("Top 3 Non-Selling Items: ");
        PreparedStatement ab = connection.prepareStatement("SELECT proid , name , sum(quantity) FROM orders o JOIN bill b ON o.billno = b.billno WHERE storeid = ? GROUP BY proid ORDER BY sum(quantity) LIMIT 3");
        ab.setInt(1,choice);
        ResultSet rs = ab.executeQuery();
        System.out.println( "Product Id     " + "Product Name " + "     " + "Quantity" );
        while(rs.next())
        {
            System.out.printf("%-15d%-20s%-12d",rs.getInt(1),rs.getString(2),rs.getInt(3));
            System.out.println();
        }
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
