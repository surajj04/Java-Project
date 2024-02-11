package com.jdbc.vaild;

import java.sql.*;

public class ValidDetails {

    public boolean validCard(String acNo, Statement st, ResultSet rs, Connection con) {
        try {
            String query = "SELECT AccountNumber FROM account_info WHERE AccountNumber = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, acNo);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean validPin(String pin, String acNo, Statement st, ResultSet rs, Connection con) {
        try {
            String query = "SELECT PIN FROM account_info WHERE AccountNumber = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, acNo);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                String actualPin = rs.getString("PIN");
                return pin.equals(actualPin);
            } else {
                return false; // Account number not found
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
