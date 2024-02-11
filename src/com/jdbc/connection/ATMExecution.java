package com.jdbc.connection;

import com.jdbc.vaild.ValidDetails;
import com.jdbc.withdraw.Withdraw;

import java.sql.*;
import java.util.Scanner;

public class ATMExecution {

    Scanner input = new Scanner(System.in);

    private String url = "jdbc:mysql://localhost:3306/ATMDB";
    private String user = "root";
    private String pass = "root";
    private Connection con;
    private Statement st;
    private ResultSet rs;


    public ATMExecution() {
        try {
            // Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            // Establish connection
            con = DriverManager.getConnection(url, user, pass);
            // Create statement
            st = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkAcc(String acNo) {
        try {
            ValidDetails validDetails = new ValidDetails();
            boolean cardStatus = validDetails.validCard(acNo, st, rs, con);
            return cardStatus;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean checkPin(String acNo, String pin) {
        try {
            ValidDetails validDetails = new ValidDetails();
            boolean pinStatus = validDetails.validPin(pin, acNo, st, rs, con);
            return pinStatus;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public void clearConsole() {
        for (int i = 0; i < 50; ++i) System.out.println(); // Print enough newlines to clear the console
    }


    public void menu() {
        clearConsole(); // Clear the console
        System.out.println("Welcome to the ATM");
        System.out.println("1. Check Balance");
        System.out.println("2. Withdraw");
        System.out.println("3. Deposit");
        System.out.println("4. Exit");

        System.out.print("Enter your choice: ");
        int choice = input.nextInt();
        System.out.println();

    }


    public void withdraw(String withdrawAmt, String acNo) {
        try {
            Withdraw withdraw = new Withdraw();
            withdraw.withdrawMoney(withdrawAmt, acNo, st, rs, con);
            System.out.print("Would you like to visit the menu? yes or no (y/n): ");
            String c = input.next();
            if (c != "y" || c != "yes" || c != "Y" || c != "Yes" || c != "YES") {
                System.out.println("Thank you for using our ATM services. Have a great day!");
            } else {
                clearConsole();
                menu();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void closeResources() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
