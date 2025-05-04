/*
package com.payment_order.com.payment_order;

import java.sql.*;
import java.util.Properties;

public class TestConnection {

    public static void main(String[] args) throws SQLException {
        new TestConnection().ts();

    }

    public void ts() throws SQLException {
        String urlStr = "jdbc:postgresql://localhost:5432/postgres";
        String login = "postgres";
        String password = "DOBlest147147";
        Properties pr = new Properties();
        pr.setProperty("user",login);
        pr.setProperty("password", password);


        try {
            Connection con = DriverManager.getConnection(urlStr,pr);
            System.out.println("получилось!");
            String q = "select * from payment_order";
            PreparedStatement ps = con.prepareStatement(q);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                System.out.println(rs.getString("id"));
                System.out.println(rs.getString("date"));
                System.out.println(rs.getString("recipient"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("не получилось");
        }
    }
}
*/
