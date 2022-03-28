package Billing;

import java.sql.*;
import java.util.*;

public class customer implements store{
    Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/Customer","root","Muthujega@2001");

    public customer() throws SQLException {
    }
    
    // To add new customer into the store
    
    public void add(int choice) throws Exception {
        Scanner in = new Scanner(System.in);
        Scanner ss = new Scanner(System.in);
        try {
            while (true) {
                System.out.print("Enter Customer Name to add: ");
                String name = in.nextLine();
                System.out.print("Enter Customer Address: ");
                String add = in.nextLine();
                System.out.print("Enter Customer Phone number: ");
                double phn = ss.nextDouble();
                String cus = "INSERT INTO customer (Name,Address,Phone_Number,storeid) VALUES (?,?,?,?)";
                PreparedStatement st = connection.prepareStatement(cus);
                st.setString(1, name);
                st.setString(2, add);
                st.setDouble(3, phn);
                st.setInt(4, choice);
                int cus1 = st.executeUpdate();
                if(cus1 == 1) {
                    System.out.println("Customer Created Successfully");
                } else {
                    System.out.println("Customer Creation failed");
                }
                System.out.print("Do you want to Add Customer [Yes/No]: ");
                String ch = in.nextLine();
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

    // To delete the customer from the store
    
    public void delete(int choice) throws Exception {
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

    // To update the customer details
    
    public void update(int choice) throws Exception {
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
}
