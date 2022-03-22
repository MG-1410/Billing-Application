import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Print {
    Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/Customer","root","Muthujega@2001");

    public Print() throws SQLException {
    }

    public void printBill(int choice) throws Exception {
        Scanner in = new Scanner(System.in);
        Scanner is = new Scanner(System.in);
        int total = 0, cid = 0, pro = 0;
        try {
            while (true) {
                System.out.print("Enter the Bill No to print: ");
                int bno = in.nextInt();
                PreparedStatement pst = connection.prepareStatement("Select cusId FROM bill  WHERE billno = ? ");
                pst.setInt(1, bno);
                ResultSet r = pst.executeQuery();
                if (r.next()) {
                    cid = r.getInt(1);
                } else if (!r.next()) {
                    System.out.println("Enter Valid Bill NO...");
                    break;
                }
                PreparedStatement spt = connection.prepareStatement("SELECT sname,sadd FROM store WHERE storeid = ? ");
                spt.setInt(1, choice);
                ResultSet sto = spt.executeQuery();
                while (sto.next()) {
                    System.out.println("             " + sto.getString(1));
                    System.out.println("      " + sto.getString(2));
                }
                System.out.println("      Bill No: " + bno + "       Customer Id: " + cid);
                PreparedStatement stp = connection.prepareStatement(" SELECT Name,Address,Phone_Number FROM customer WHERE Cusid = ? ");
                stp.setInt(1, cid);
                ResultSet rrr = stp.executeQuery();
                if (rrr.next()) {
                    System.out.println("      Name: " + rrr.getString(1));
                    System.out.println("      Address: " + rrr.getString(2));
                    System.out.println("      Phone Number: " + rrr.getBigDecimal(3));
                }
                PreparedStatement sst = connection.prepareStatement("SELECT proid, name , quantity ,amount,profit FROM orders WHERE billno = ? ");
                sst.setInt(1, bno);
                System.out.println("Product Id     " + "Product Name " + "     " + "Quantity" + "     " + "Amount");
                ResultSet sss = sst.executeQuery();
                while (sss.next()) {
                    total += sss.getInt(4);
                    pro += sss.getInt(5);
                    System.out.printf("%-15d%-20s%-12d%-20d", sss.getInt(1), sss.getString(2), sss.getInt(3), sss.getInt(4));
                    System.out.println();
                }
                System.out.println("                                 Grand Total = " + total);
                PreparedStatement ss = connection.prepareStatement("SELECT price,dis  FROM bill WHERE billno = ? AND storeid = ? ");
                ss.setInt(1,bno);
                ss.setInt(2,choice);
                ResultSet rr = ss.executeQuery();
                rr.next();
                int amt = rr.getInt(1);
                int per = rr.getInt(2);

                System.out.println("                                 Discount    = " + per + " %");
                System.out.println("                                Total amount = " + amt);
                total = 0;
                pro = 0;
                System.out.print("Do you want to Print Bill [Yes/No]: ");
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

    public void cusId(int choice) throws Exception {
        Scanner in = new Scanner(System.in);
        Scanner is = new Scanner(System.in);
        try {
            while (true) {
                System.out.print("Enter the customer Phone Number: ");
                double ph = in.nextDouble();
                PreparedStatement st = connection.prepareStatement("SELECT CusID FROM customer WHERE Phone_Number = ? AND storeid = ? ");
                st.setDouble(1, ph);
                st.setInt(2, choice);
                ResultSet r = st.executeQuery();
                if (r.next()) {
                    System.out.println("The Customer Id is: " + r.getInt(1));
                } else {
                    System.out.println("Enter Valid Customer Phone Number..");
                }
                System.out.print("Do you want to Know Customer Id [Yes/No]: ");
                String s = is.nextLine();
                if (s.equalsIgnoreCase("No")) {
                    break;
                }
                System.out.println();
            }
        }catch(InputMismatchException e){
            System.out.println();
            System.out.println("Enter Valid Credentials...");
        }
    }

    public void addBill(int bno,int choice,int cid, int per)throws Exception{
        Scanner in = new Scanner(System.in);
        Scanner is = new Scanner(System.in);
        int total = 0 , pro = 0;
        PreparedStatement sst = connection.prepareStatement("SELECT amount,profit FROM orders WHERE billno = ? ");
        sst.setInt(1,bno);
        ResultSet sss = sst.executeQuery();
        while(sss.next())
        {
            total += sss.getInt(1);
            pro += sss.getInt(2);
        }
        System.out.println(" Grand Total = " + total);
        int amt = 0 ;
        System.out.print("Do you want to add Discount manually[Yes/No]: ");
        String s = in.nextLine();
        if(s.equalsIgnoreCase("Yes")){
            System.out.println("Enter the discount percentage : " );
            int a = is.nextInt();
            per = a ;
            amt = total - (total * per / 100);
            pro = pro - (pro * per / 100);
        }
        else if(total > 40000){
            per += 10;
            amt = total - (total * per / 100);
            pro = pro - (pro * per / 100);
        }
        else if (total > 20000 && choice == 1){
            per += 5;
            amt = total - (total * per / 100);
            pro = pro - (pro * per / 100);
        }
        else if (total > 10000 && choice == 1){
            per += 3;
            amt = total - (total * per / 100);
            pro = pro - (pro * per / 100);
        }
        else if (total > 2000 && choice != 1){
            per += 2;
            amt = total - (total * per / 100);
            pro = pro - (pro * per / 100);
        }
        else if (total > 2000 && choice != 1){
            per += 4;
            amt = total - (total * per / 100);
            pro = pro - (pro * per / 100);
        }
        else{
            amt = total ;
        }
        System.out.println(" Discount    = " + per +" %");
        System.out.println(" Total amount = " + amt);
        Date date = (Date) new java.util.Date();
        PreparedStatement bb = connection.prepareStatement("UPDATE bill SET price = ? ,profit = ?,dis = ?, orderdate = ?   WHERE billno = ? AND storeid = ? ");
        bb.setInt(1,amt);
        bb.setInt(2,pro);
        bb.setInt(1,per);
        bb.setDate(4,date);
        bb.setInt(5,bno);
        bb.setInt(6,choice);
        int a = bb.executeUpdate();

        if(a == 1){
            System.out.println("Bill added..");
        }
        total = 0 ;
        pro = 0;
    }
}
