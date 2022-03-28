package Billing;

import java.sql.*;
import java.util.*;

public class bill implements store{
    Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/Customer","root","Muthujega@2001");

    public bill() throws SQLException {
    }

    // To add items to the bill
    
    public void add(int choice) throws Exception {
        Scanner addb = new Scanner(System.in);
        Scanner adb = new Scanner(System.in);
        Scanner abb = new Scanner(System.in);
        try {
            System.out.print("Enter the Customer Id: ");
            int cid = addb.nextInt();
            boolean c = true;
            PreparedStatement st = connection.prepareStatement("Select * FROM customer WHERE CusID = ? AND storeid = ?");
            st.setInt(1, cid);
            st.setInt(2, choice);
            ResultSet r = st.executeQuery( );
            if (!r.next()) {
                System.out.println("Enter Valid Customer Id..");
            } else {
                int bno = 0;
                int value = 0, price = 0, bpr = 0;
                String name = null;
                int pna = 0, qp = 0;
                LinkedHashMap<Integer, Integer> m = new LinkedHashMap<Integer, Integer>();
                while (c) {
                    System.out.print("Enter the Product id: ");
                    pna = adb.nextInt();
                    if (pna == 0) {
                        break;
                    }
                    System.out.print("Enter the Quantity of the product: ");
                    qp = addb.nextInt();
                    m.put(pna, qp);
                    System.out.print("Do you want to Delete Item [Yes/No]: ");
                    String ch = abb.nextLine();
                    if (ch.equalsIgnoreCase("Yes")) {
                        System.out.print("Enter the productId to delete: ");
                        int ac = adb.nextInt();
                        m.remove(ac);
                    }
                    System.out.print("Do you want to Finish Bill[Yes/No]: ");
                    String aa = abb.nextLine();
                    if (aa.equalsIgnoreCase("Yes")) {
                        break;
                    }
                }
                int per = 0;
                PreparedStatement x = connection.prepareStatement("INSERT INTO bill(cusid , storeid) VALUES (?,?) ");
                x.setInt(1,cid);
                x.setInt(2,choice);
                int a = x.executeUpdate();
                if(a == 1){
                    System.out.println("Bill initialized..");
                }
                PreparedStatement zz = connection.prepareStatement("SELECT billno FROM bill WHERE cusid = ? AND storeid = ? ORDER BY billno DESC LIMIT 1");
                zz.setInt(1,cid);
                zz.setInt(2,choice);
                ResultSet ss = zz.executeQuery();
                ss.next();
                bno = ss.getInt(1);
                Iterator itr = m.entrySet().iterator();
                while (itr.hasNext()) {
                    Object o = itr.next();
                    Map.Entry e = (Map.Entry) o;
                    pna = (Integer) e.getKey();
                    qp = (int) e.getValue();
                    PreparedStatement stp = connection.prepareStatement("SELECT Product,price,bprice,dis FROM item WHERE ProductId = ? AND storeid = ? ");
                    stp.setInt(1, pna);
                    stp.setInt(2, choice);
                    ResultSet val = stp.executeQuery();
                    if (val.next()) {
                        name = val.getString(1);
                        price = val.getInt(2);
                        bpr = val.getInt(3);
                        per += val.getInt(4);
                    }
                    PreparedStatement s = connection.prepareStatement("SELECT stock FROM item WHERE ProductId = ? AND storeid = ?");
                    s.setInt(1, pna);
                    s.setInt(2, choice);
                    ResultSet sto = s.executeQuery();
                    while (sto.next()) {
                        if (sto.getInt(1) < 1) {
                            System.out.println(pna + " is Out of Stock..");
                            System.out.println();
                            qp = 0;
                            break;
                        }
                    }
                    PreparedStatement zss = connection.prepareStatement("SELECT * FROM item c WHERE ProductId = ? AND storeid = ? ");
                    zss.setInt(1, pna);
                    zss.setInt(2, choice);
                    ResultSet bill = zss.executeQuery();
                    if (bill.next()) {
                        if (qp > 0) {
                            PreparedStatement sss = connection.prepareStatement("INSERT INTO orders VALUES(?,?,?,?,?,?,?)");
                            sss.setInt(1, pna);
                            sss.setString(2, name);
                            sss.setInt(3, qp);
                            sss.setInt(4, price);
                            sss.setInt(5, (qp * price));
                            sss.setInt(6, bno);
                            sss.setInt(7, (price - bpr) * qp);
                            value = sss.executeUpdate();
                        }
                    }
                    if (value == 1) {
                        System.out.println("Items Successfully added into Orders");
                    }
                    PreparedStatement sr = connection.prepareStatement("UPDATE item SET stock = stock - ? WHERE Productid = ? AND storeid = ? ");
                    sr.setInt(1, qp);
                    sr.setInt(2, pna);
                    sr.setInt(3, choice);
                    int ab = sr.executeUpdate();
                    if (ab == 1) {
                        System.out.println("Stock Details Updated..");
                    }
                }
                System.out.println();
                Print p = new Print();
                p.addBill(bno, choice, cid,per);
            }
        }catch(InputMismatchException e){
            System.out.println();
            System.out.println("Enter Valid credentials...");
        }
    }

    // To delete the bill from the store
    
    public void delete(int choice) throws Exception {
        Scanner in = new Scanner(System.in);
        Scanner is = new Scanner(System.in);
        try {
            while (true) {
                System.out.print("Enter the BillNo to delete: ");
                int bno = in.nextInt();
                PreparedStatement st = connection.prepareStatement("Select * FROM orders WHERE billno = ? AND storeid= ? ");
                st.setInt(1, bno);
                st.setInt(2, choice);
                ResultSet r = st.executeQuery();
                if (!r.next()) {
                    System.out.println("Enter Valid Bill NO / Bill No doesn't Exists..");
                    break;
                }
                PreparedStatement pst = connection.prepareStatement("DELETE FROM bill WHERE billno = ? AND storeid = ? ");
                pst.setInt(1, bno);
                pst.setInt(2, choice);
                int db = pst.executeUpdate();
                if (db == 1) {
                    System.out.println("Bill Deleted Successfully");
                } else {
                    System.out.println("Bill Deletion Failed");
                }
                System.out.print("Do you want to Delete Bill [Yes/No]: ");
                String ch = is.nextLine();
                if (ch.equalsIgnoreCase("No")) {
                    break;
                }
                System.out.println();
            }
        }catch(InputMismatchException e){
            System.out.println();
            System.out.println("Enter Valid Credentials...");
        }
    }

    // Updating the bill is not recommended after adding the items
    
    public void update(int choice) throws Exception {
        System.out.println("Enter valid choice..");
    }
}
