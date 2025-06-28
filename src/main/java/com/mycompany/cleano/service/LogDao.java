/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cleano.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author abdurraihan
 */
public class LogDao {
      private final String url = "jdbc:sqlite:log.db"; // akan buat file log.db di root folder

    public LogDao() {
        buatTabelJikaBelumAda();
    }

    private void buatTabelJikaBelumAda() {
        String sql = "CREATE TABLE IF NOT EXISTS log_login (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                     "username TEXT NOT NULL, " +
                     "login_time TEXT DEFAULT CURRENT_TIMESTAMP" +
                     ");";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void simpanLogLogin(String username) {
        String sql = "INSERT INTO log_login(username) VALUES(?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   
    public List<String[]> getAllLogs() {
    List<String[]> list = new ArrayList<>();
    String sql = "SELECT * FROM log_login";

    try (Connection conn = DriverManager.getConnection(url);
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            String id = rs.getString("id");
            String username = rs.getString("username");
            String waktu = rs.getString("login_time");
            list.add(new String[]{id, username, waktu});
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return list;
}

}
