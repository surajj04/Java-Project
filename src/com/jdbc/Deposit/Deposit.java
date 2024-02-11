package com.jdbc.Deposit;

import java.sql.*;

public class Deposit {
    public boolean depositMoney(String depositAmt, String acNo, Connection con) {
        try {
            // Parse the deposit amount
            int depositAmount = Integer.parseInt(depositAmt);

            if (depositAmount <= 0) {
                System.out.println("Invalid deposit amount");
                return false; // Invalid deposit amount
            }

            String query = "SELECT Balance FROM account_info WHERE AccountNumber = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, acNo);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                int balance = rs.getInt("Balance");
                int newBalance = balance + depositAmount;

                // Update the balance in the database
                String updateQuery = "UPDATE account_info SET Balance = ? WHERE AccountNumber = ?";
                PreparedStatement updateStatement = con.prepareStatement(updateQuery);
                updateStatement.setInt(1, newBalance);
                updateStatement.setString(2, acNo);
                updateStatement.executeUpdate();
                System.out.println("Deposit successful");
                return true; // Deposit successful
            } else {
                System.out.println("Account number not found");
                return false; // Account number not found
            }
        } catch (SQLException | NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }
}
