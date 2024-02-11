package com.jdbc.withdraw;

import java.sql.*;

public class Withdraw {

    public boolean withdrawMoney(String withdrawAmt, String acNo, Statement st, ResultSet rs, Connection con) {
        try {
            String query = "SELECT Balance FROM account_info WHERE AccountNumber = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, acNo);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                int balance = rs.getInt("Balance");
                int withdrawAmount = Integer.parseInt(withdrawAmt);

                if (balance >= withdrawAmount) {
                    int newBalance = balance - withdrawAmount;

                    // Update the balance in the database
                    String updateQuery = "UPDATE account_info SET Balance = ? WHERE AccountNumber = ?";
                    PreparedStatement updateStatement = con.prepareStatement(updateQuery);
                    updateStatement.setInt(1, newBalance);
                    updateStatement.setString(2, acNo);
                    updateStatement.executeUpdate();
                    System.out.println("Withdrawal successful");
                    return true; // Withdrawal successful
                } else {
                    System.out.println("Insufficient funds");
                    return false; // Insufficient funds
                }
            } else {
                System.out.println("Account number not found");
                return false; // Account number not found
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
