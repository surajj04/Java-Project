package com.jdbc.connection;

import com.jdbc.Deposit.Deposit;
import com.jdbc.balance.Balance;
import com.jdbc.user.ShowUser;
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
        for (int i = 0; i < 40; ++i) System.out.println(); // Print enough newlines to clear the console
    }


    public void menu(String acc) {


        clearConsole(); // Clear the console
        System.out.println("Welcome to the ATM");
        System.out.println("1. Account details");
        System.out.println("2. Check Balance");
        System.out.println("3. Withdraw");
        System.out.println("4. Deposit");
        System.out.println("5. Exit");

        System.out.print("Enter your choice: ");
        int choice = input.nextInt();
        System.out.println();

        switch (choice) {
            case 1:
                showDetails(acc);
                break;
            case 2:
                showBalance(acc);
                break;
            case 3:
                withdraw(acc);
                break;
            case 4:
                deposit(acc);
                break;
            case 5:
                exit();
                break;
            default:
                exit();
                break;
        }


    }

    void showDetails(String acc) {
        clearConsole();

        ShowUser showUser = new ShowUser();
        showUser.showDetails(acc, rs, con);
    }

    public void showBalance(String acc) {
        clearConsole();
        Balance balance = new Balance();
        balance.showBalance(acc, st, rs, con);

        System.out.print("\nWould you like to visit the menu? yes or no (y/n): ");
        String c = input.next();
        if (!c.equalsIgnoreCase("y") && !c.equalsIgnoreCase("yes")) {
            System.out.println("Thank you for using our ATM services. Have a great day!");
        } else {
            clearConsole();
            menu(acc);
        }
    }

    public void deposit(String acNo) {
        clearConsole();
        try {
            Deposit deposit = new Deposit();

            String depositAmt = "";
            System.out.print("Please enter the amount you wish to deposit in your ATM: ");
            depositAmt = input.next();

            if (deposit.depositMoney(depositAmt, acNo, con)) {
                System.out.print("\nWould you like to visit the menu? yes or no (y/n): ");
                String c = input.next();
                if (!c.equalsIgnoreCase("y") && !c.equalsIgnoreCase("yes")) {
                    System.out.println("Thank you for using our ATM services. Have a great day!");
                } else {
                    clearConsole();
                    menu(acNo);
                }
            } else {
                System.out.println("Deposit unsuccessful. Please try again.");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void exit() {
        System.out.println("Thank you for using our ATM services. Have a great day!");
    }


    public void withdraw(String acNo) {
        clearConsole();
        try {
            Withdraw withdraw = new Withdraw();

            String withdrawAmt = "";
            System.out.print("Please enter the amount you wish to withdraw from the ATM: ");
            withdrawAmt = input.next();

            if (Integer.parseInt(withdrawAmt) < 0) {
                System.out.print("Please enter an amount greater than 0 that you wish to withdraw from the ATM: ");
                withdrawAmt = input.next();
                if (Integer.parseInt(withdrawAmt) < 0) {
                    System.out.print("Please enter an amount greater than 0 that you wish to withdraw from the ATM: ");
                    withdrawAmt = input.next();
                } else {
                    withdraw.withdrawMoney(withdrawAmt, acNo, st, rs, con);
                }
            } else {
                withdraw.withdrawMoney(withdrawAmt, acNo, st, rs, con);
            }


            System.out.print("\nWould you like to visit the menu? yes or no (y/n): ");
            String c = input.next();
            if (!c.equalsIgnoreCase("y") && !c.equalsIgnoreCase("yes")) {
                System.out.println("Thank you for using our ATM services. Have a great day!");
            } else {
                clearConsole();
                menu(acNo);
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
