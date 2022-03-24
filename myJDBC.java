package Billing;

import java.util.InputMismatchException;
import java.util.Scanner;

public class myJDBC {
    public static void main(String[] args) throws Exception {
        customer c = new customer();
        item i = new item();
        bill b = new bill();
        Print p = new Print();
        Report r = new Report();
        Scanner in = new Scanner(System.in);
        Scanner cho = new Scanner(System.in);
        try {
            while (true) {
                System.out.println();
                System.out.println("1.Marina Electronics");
                System.out.println("2.Sk Maligai");
                System.out.println("3.Mk Super Market");
                System.out.println("4.CSK Shawarma");
                System.out.print("Enter the choice: ");
                int choice = cho.nextInt();
                if (choice < 5 && choice > 0) {
                } else {
                    System.out.println("Enter the valid Choice..");
                    System.out.println();
                    continue;
                }
                System.out.println("1. Customer");
                System.out.println("2. Item");
                System.out.println("3. Bill");
                System.out.println("4. Print");
                System.out.println("5. Sales Report");
                System.out.print("Enter the Operation: ");
                int ope = in.nextInt();
                switch (ope) {
                    case 1 -> {
                        System.out.println("  1.Add Customer");
                        System.out.println("  2.Delete Customer");
                        System.out.println("  3.Update Customer");
                        System.out.print("Enter the choice: ");
                        int ch = in.nextInt();
                        switch (ch) {
                            case 1 -> c.add(choice);
                            case 2 -> c.delete(choice);
                            case 3 -> c.update(choice);
                            default -> System.out.println("Enter the valid Choice");
                        }
                    }
                    case 2 -> {
                        System.out.println("  1.Delete Customer");
                        System.out.println("  2.Delete Item");
                        System.out.println("  3.Delete Bill");
                        System.out.print("Enter the choice: ");
                        int co = in.nextInt();
                        switch (co) {
                            case 1 -> i.add(choice);
                            case 2 -> i.delete(choice);
                            case 3 -> i.update(choice);
                            default -> System.out.println("Enter the valid Choice");
                        }
                    }
                    case 3 -> {
                        System.out.println("  1.Update Customer");
                        System.out.println("  2.Update Item");
                        System.out.print("Enter the choice: ");
                        int ci = in.nextInt();
                        switch (ci) {
                            case 1 -> b.add(choice);
                            case 2 -> b.delete(choice);
                            default -> System.out.println("Enter the valid Choice");
                        }
                    }
                    case 4 -> {
                        System.out.println("  1.PrintBill");
                        System.out.println("  2.Forgot CusId");
                        System.out.print("Enter the Choice: ");
                        int pb = in.nextInt();
                        switch (pb) {
                            case 1 -> p.printBill(choice);
                            case 2 -> p.cusId(choice);
                            default -> System.out.println("Enter the Valid Choice");
                        }
                    }
                    case 5 -> r.report(choice);
                    default -> System.out.println("Enter the vaild Choice...");
                }
                System.out.println();
            }
        }
        catch (InputMismatchException e){
            System.out.println();
            System.out.println("Enter Valid Credentials...");
        }
    }
}
