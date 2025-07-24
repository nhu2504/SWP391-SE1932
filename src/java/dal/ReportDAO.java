/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

/**
 * ngày update 20/7/2025
 * @author Như
 */
public class ReportDAO {
    public Map<Integer, Integer> getClassCountPerMonth(int year) {
    Map<Integer, Integer> map = new HashMap<>();
    String sql = "SELECT MONTH(cg.createdAt) AS month, COUNT(*) AS count " +
                 "FROM ClassGroup cg " +
                 "WHERE YEAR(cg.createdAt) = ? " +
                 "GROUP BY MONTH(cg.createdAt)";
    try (Connection con = new DBContext().connection;
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, year);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            map.put(rs.getInt("month"), rs.getInt("count"));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return map;
}

public Map<Integer, Integer> getStudentCountPerMonth(int year) {
    Map<Integer, Integer> map = new HashMap<>();
    String sql = "SELECT MONTH(created_at) AS month, COUNT(*) AS count " +
                 "FROM [User] " +
                 "WHERE roleID = 3 AND YEAR(created_at) = ? " +
                 "GROUP BY MONTH(created_at)";
    try (Connection con = new DBContext().connection;
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, year);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            map.put(rs.getInt("month"), rs.getInt("count"));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return map;
}


public List<Integer> getAvailableYears() {
    List<Integer> years = new ArrayList<>();
    String sql = "SELECT YearOfWork FROM CenterInfo WHERE CenterID = 1";

    try (Connection conn = new DBContext().connection;
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        if (rs.next()) {
            int startYear = rs.getInt("YearOfWork");
            int currentYear = Year.now().getValue();

            for (int y = startYear; y <= currentYear; y++) {
                years.add(y);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return years;
}



}
