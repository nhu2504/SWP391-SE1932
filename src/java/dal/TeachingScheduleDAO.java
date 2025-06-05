/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 *
 * @author NGOC ANH
 */
public class TeachingScheduleDAO {
    public boolean checkTeaching(int shiftID, int dayID, int teacherID) {
        String sql = "SELECT * FROM TeachingSchedule WHERE ShiftID = ? AND ThuID = ? AND UserID = ?";
        try (Connection conn = new DBContext().connection; 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, shiftID);
            ps.setInt(2, dayID);
            ps.setInt(3, teacherID);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
