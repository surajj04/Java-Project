package com.jdbc.user;

import com.jdbc.connection.ATMExecution;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ShowUser {

    private ATMExecution atmExecution = new ATMExecution();
    public void clearConsole() {
        for (int i = 0; i < 40; ++i) System.out.println(); // Print enough newlines to clear the console
    }
    public void showDetails(String acNo, ResultSet rs, Connection con) {
        Scanner input = new Scanner(System.in);
        try {
            String query = "SELECT * FROM account_info WHERE AccountNumber = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, acNo);
            rs = preparedStatement.executeQuery();

            rs.next();
            System.out.println("User Details: \n");
            System.out.println("Account no: "+rs.getString(1) + "\nFull Name: "+ rs.getString(2)+"\nAccount Balance: $"+rs.getString(4));

            System.out.print("\nWould you like to visit the menu? yes or no (y/n): ");
            String c = input.next();
            if (!c.equalsIgnoreCase("y") && !c.equalsIgnoreCase("yes")) {
                System.out.println("Thank you for using our ATM services. Have a great day!");
            } else {
                clearConsole();
                atmExecution.menu(acNo);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
