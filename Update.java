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
}
