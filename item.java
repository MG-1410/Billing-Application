package Billing;

import java.sql.*;
import java.util.*;

public class item implements store{
    Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/Customer","root","Muthujega@2001");

    public item() throws SQLException {
    }

    // To add new item in the store
    
    public void add(int choice) throws Exception{
        Scanner it = new Scanner(System.in);
        Scanner pr = new Scanner(System.in);
        try {
            while (true) {
                System.out.print("Enter the Item to add: ");
                String id = it.nextLine();
                String item = "Select * FROM item cc WHERE Product = ? AND storeid = ?";
                PreparedStatement st = connection.prepareStatement(item);
                st.setString(1, id);
                st.setInt(2, choice);
                ResultSet r = st.executeQuery();
                if (r.next()) {
                    System.out.println("Enter Valid Item / Item Already Exists.. ");
                    break;
                }
                System.out.print("Enter the Price: ");
                int prc = pr.nextInt();
                System.out.print("Enter the buying price: ");
                int bprc = pr.nextInt();
                System.out.println("Enter the stock of the product: ");
                int stc = pr.nextInt();
                String str = "INSERT INTO item(Product,price,bprice,stock,storeid) VALUES(?,?,?,?,?)";
                PreparedStatement pst = connection.prepareStatement(str);
                pst.setString(1, id);
                pst.setInt(2, prc);
                pst.setInt(3, bprc);
                pst.setInt(4,stc);
                pst.setInt(5, choice);
                int ite = pst.executeUpdate();
                if (ite == 1) {
                    System.out.println("Item Created Successfully.");
                } else {
                    System.out.println("Item Creation failed.");
                }
                System.out.print("Do you want to Add Item [Yes/No]: ");
                String ch = it.nextLine();
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

    //To delete the existing item from the store
    
    public void delete(int choice) throws Exception {
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

    // To update the existing item from the store eg.stocks of the items
    
    public void update(int choice) throws Exception {
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
}
