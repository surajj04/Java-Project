package com.jdbc.balance;

import java.sql.*;

public class Balance {
    public int showBalance(String acNo, Statement st, ResultSet rs, Connection con) {
        try {
            String query = "SELECT Balance FROM account_info WHERE AccountNumber = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, acNo);
            rs = preparedStatement.executeQuery();

            rs.next();
            System.out.println("account balance: "+"$"+rs.getInt("balance")+"");
            return rs.getInt("balance");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
