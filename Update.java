import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Update {

    Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/Customer","root","Muthujega@2001");

    public Update() throws SQLException {
    }

    public void updateCus(int choice) throws Exception {
        Scanner in = new Scanner(System.in);
        Scanner is = new Scanner(System.in);
        try {
            while (true) {
                System.out.print("Enter the Customer ID to update: ");
                int id = is.nextInt();
                PreparedStatement st = connection.prepareStatement("Select * FROM customer WHERE CusId = ? AND storeid = ? ");
                st.setInt(1, id);
                st.setInt(2, choice);
                ResultSet r = st.executeQuery();
                if (!r.next()) {
                    System.out.println("Enter Valid Customer Name / Customer doesn't Exists.. ");
                    break;
                }
                System.out.print("Enter the column to update: ");
                String col = in.nextLine();
                System.out.print("Enter the information want to update: ");
                String upt = in.nextLine();
                PreparedStatement pst = connection.prepareStatement("UPDATE customer SET ? =? WHERE CusId =? AND storeid = ? ");
                pst.setString(1, col);
                pst.setString(2, upt);
                pst.setInt(3, id);
                pst.setInt(4, choice);
                int ucus = pst.executeUpdate();
                if (ucus == 1) {
                    System.out.println("Customer Information updated Succesfully");
                } else {
                    System.out.println("Customer Information updated Failed");
                }
                System.out.print("Do you want to Update Customer [Yes/No]: ");
                String ch = in.nextLine();
                if (ch.equalsIgnoreCase("No")) {
                    break;
                }
                System.out.println();
            }
        }
        catch(InputMismatchException e){
            System.out.println();
            System.out.println("Enter valid Credentials..");
        }
    }

    public void updateIt(int choice) throws Exception {
        Scanner uit = new Scanner(System.in);
        Scanner in = new Scanner(System.in);
        try {
            while (true) {
                System.out.print("Enter the Product Id of the Item to update: ");
                int id = in.nextInt();
                PreparedStatement st = connection.prepareStatement("Select * FROM item WHERE ProductId = ? AND storeid = ? ");
                st.setInt(1, id);
                st.setInt(2, choice);
                ResultSet r = st.executeQuery();
                if (!r.next()) {
                    System.out.println("Enter Valid Customer / Customer don't Exists.. ");
                    break;
                }
                System.out.print("Enter the column to update: ");
                String col = uit.nextLine();
                System.out.print("Enter the information to update: ");
                String upt = uit.nextLine();
                PreparedStatement pst = connection.prepareStatement("UPDATE item SET ? = ? WHERE ProductId = ?A ND storeid = ? ");
                pst.setString(1, col);
                pst.setString(2, upt);
                pst.setInt(3, id);
                pst.setInt(4, choice);
                int upit = pst.executeUpdate();
                if (upit == 1) {
                    System.out.println("Item Information updated Succesfully");
                } else {
                    System.out.println("Item Information update Failed");
                }
                System.out.print("Do you want to Update Item [Yes/No]: ");
                String ch = uit.nextLine();
                if (ch.equalsIgnoreCase("No")) {
                    break;
                }
                System.out.println();
            }
        }
        catch(InputMismatchException e){
            System.out.println();
            System.out.println("Enter Valid Credentials..");
        }
    }

//    public void updateBill(int choice,int bno) throws Exception{
//        Scanner in = new Scanner(System.in);
////        System.out.println("    1. To add items in the Existing Order");
////        System.out.println("    2. To delete item in the Existing Order");
////        System.out.print("Enter the choice: ");
////        int choi = in.nextInt();
////        int bno;
////        switch(choi){
////            case 1:
////                Scanner is = new Scanner(System.in);
////                System.out.print("Enter the BillNo: ");
////                bno = in.nextInt();
////                PreparedStatement st = connection.prepareStatement("Select * FROM bill WHERE Billno = ? AND storeid= ? ");
////                st.setInt(1,bno);
////                st.setInt(2,choice);
////                ResultSet r = st.executeQuery();
////                if(!r.next()) {
////                    System.out.println("Enter Valid Bill NO / Bill No doesn't Exists...");
////                    break;
////                }
////                int value = 0 , price = 0 ,bpr = 0;
////                String name = null;
////                while (true) {
////                    System.out.print("Enter the Product id: ");
////                    int pna = is.nextInt();
////                    if (pna == 0) {
////                        break;
////                    }
////                    PreparedStatement pst = connection.prepareStatement("SELECT Product,price,bprice FROM item WHERE ProductId = ? AND storeid= ? ");
////                    pst.setInt(1,pna);
////                    pst.setInt(2,choice);
////                    ResultSet val = pst.executeQuery();
////                    if(val.next())
////                    {
////                        name = val.getString(1);
////                        price = val.getInt(2);
////                        bpr = val.getInt(3);
////                    }
////                    System.out.print("Enter the Quantity of the product: ");
////                    int qp = is.nextInt();
////                    PreparedStatement aa = connection.prepareStatement("SELECT stock FROM item WHERE ProductId = ? ");
////                    aa.setInt(1,pna);
////                    ResultSet sto = aa.executeQuery();
////                    while (sto.next()) {
////                        if (sto.getInt(1) < 1) {
////                            System.out.println(" Out of Stock..");
////
////                            System.out.println();
////                            qp = 0;
////                            break;
////                        }
////                    }
////                    PreparedStatement spt = connection.prepareStatement("SELECT * FROM item WHERE ProductId = ? AND storeid = ? ");
////                    spt.setInt(1,pna);
////                    spt.setInt(2,choice);
////                    ResultSet bill = spt.executeQuery();
////                    if (bill.next()) {
////                        if (qp < 0) {
////                            PreparedStatement psst = connection.prepareStatement("INSERT INTO orders VALUES(?,?,?,?,?,?,?)");
////                            psst.setInt(1,pna);
////                            psst.setString(2,name);
////                            psst.setInt(3,qp);
////                            psst.setInt(4,price);
////                            psst.setInt(5,(qp * price));
////                            psst.setInt(6,bno);
////                            psst.setInt(7,(price - bpr) * qp);
////                            value = psst.executeUpdate();
////                        }
////                    }
////                    if (value == 1) {
////                        System.out.println("Items Successfully added into Orders");
////                    }
////                    PreparedStatement sspt = connection.prepareStatement("UPDATE item SET stock = stock - ? WHERE Productid = ? AND storeid = ? ");
////                    sspt.setInt(1,qp);
////                    sspt.setInt(2,pna);
////                    sspt.setInt(3,choice);
////                    int ab =  sspt.executeUpdate();
////                    if(ab == 1){
////                        System.out.println("Stock Details Updated..");
////                    }
////                    System.out.println();
////                }
////                break;
//                while(true)
//                {
//                    Scanner i = new Scanner(System.in);
////                    PreparedStatement sst = connection.prepareStatement("Select * FROM bill cc WHERE Billno = ? AND storeid = ? ");
////                    sst.setInt(1,bno);
////                    sst.setInt(2,choice);
////                    ResultSet rr = sst.executeQuery();
////                    if(!rr.next()) {
////                        System.out.println("Enter Valid Bill NO / Bill No doesn't Exists...");
////                        break;
////                    }
//                    System.out.print("Enter the product Id to delete from bill: ");
//                    int pdb = i.nextInt();
//                    int qp =0;
//                    PreparedStatement sss = connection.prepareStatement("SELECT quantity FROM ORDERS WHERE proid = ? AND storei= ? ");
//                    sss.setInt(1,pdb);
//                    sss.setInt(2,choice);
//                    ResultSet sr = sss.executeQuery();
//                    if (sr.next()){
//                        qp = sr.getInt(1);
//                    }
//                    PreparedStatement ttt = connection.prepareStatement("DELETE FROM orders WHERE proid = ? " );
//                    ttt.setInt(1,pdb);
//                    int db = ttt.executeUpdate();
//                    if(db == 1)
//                    {
//                        System.out.println("Product Deleted from Bill Successfully");
//                    }
//                    else{
//                        System.out.println("Product Deletion From Bill Failed");
//                    }
//                    PreparedStatement aab = connection.prepareStatement("UPDATE item SET stock = stock + ? WHERE Productid = ? AND storeid = ? ");
//                    aab.setInt(1,qp);
//                    aab.setInt(2,pdb);
//                    aab.setInt(3,choice);
//                    int ab =  aab.executeUpdate();
//                    if(ab == 1){
//                        System.out.println("Stock Details Updated..");
//                    }
//                    System.out.print("Do you want to Delete Product from Bill [Yes/No]: ");
//                    String ch = i.nextLine();
//                    if(ch.equalsIgnoreCase("No"))
//                    {
//                        break;
//                    }
//                    System.out.println();
//                }
////                break;
////            default:
////                System.out.println("Enter valid Choice: ");
////                break;
//    //}
//    }
}
