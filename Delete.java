import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Delete {

    Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/Customer","root","Muthujega@2001");

    public Delete() throws Exception {
    }

    public void deleteCus(int choice) throws SQLException {
        Scanner in = new Scanner(System.in);
        try {
            while (true) {
                System.out.print("Enter the Customer name to delete: ");
                String s = in.nextLine();
                PreparedStatement st = connection.prepareStatement("Select * FROM customer WHERE name = ? AND storeid = ? ");
                st.setString(1, s);
                st.setInt(2, choice);
                ResultSet r = st.executeQuery();
                if (!r.next()) {
                    System.out.println("Enter Valid Customer / Customer doesn't Exists.. ");
                    break;
                }
                PreparedStatement pst = connection.prepareStatement("DELETE FROM customer WHERE Name = ? AND storeid = ? ");
                pst.setString(1, s);
                pst.setInt(2, choice);
                int dcus = pst.executeUpdate();
                if (dcus == 1) {
                    System.out.println("Customer Deleted Successfully");
                } else {
                    System.out.println("Customer Deletion failed");
                }
                System.out.print("Do you want to Delete Customer [Yes/No]: ");
                String ch = in.nextLine();
                if (ch.equalsIgnoreCase("No")) {
                    break;
                }
                System.out.println();
            }
        }catch(InputMismatchException e){
            System.out.println();
            System.out.println("Enter Valid credentials...");
        }
    }

    public void deleteIt(int choice) throws SQLException {
        Scanner dit = new Scanner(System.in);
        try {
            while (true) {
                System.out.print("Enter name of the item to delete: ");
                int itd = dit.nextInt();
                PreparedStatement st = connection.prepareStatement("Select * FROM item  WHERE ProductId = ? AND storeid= ? ");
                st.setInt(1, itd);
                st.setInt(2, choice);
                ResultSet r = st.executeQuery();
                if (!r.next()) {
                    System.out.println("Enter Valid Item / Item doesn't Exists.. ");
                    break;
                }
                PreparedStatement pst = connection.prepareStatement("DELETE FROM item WHERE ProductId = ? AND storeid = ? ");
                pst.setInt(1, itd);
                pst.setInt(2, choice);
                int ite = pst.executeUpdate();
                if (ite == 1) {
                    System.out.println("Item Deleted Successfully");
                } else {
                    System.out.println("Item Deletion failed");
                }
                System.out.print("Do you want to Delete Item [Yes/No]: ");
                String ch = dit.nextLine();
                if (ch.equalsIgnoreCase("No")) {
                    break;
                }
                System.out.println();
            }
        }catch(InputMismatchException e){
            System.out.println();
            System.out.println("Enter valid Credentials...");
        }
    }

    public void deleteBill(int choice) throws Exception {
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
}
 
