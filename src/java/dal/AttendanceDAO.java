/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
/**
 *
 * @author NGOC ANH
 */
public class AttendanceDAO {
     public void saveAttendance(int classGroupId, Map<Integer, Boolean> attendanceMap) {
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String sql = "INSERT INTO Attendance (ClassGroupID, UserID, AttendanceDate, IsPresent) VALUES (?, ?, ?, ?)";

        try (Connection conn = new DBContext().connection;
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (Map.Entry<Integer, Boolean> entry : attendanceMap.entrySet()) {
                ps.setInt(1, classGroupId);
                ps.setInt(2, entry.getKey());
                ps.setString(3, today);
                ps.setBoolean(4, entry.getValue());
                ps.addBatch();
            }

            ps.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     public static void main(String[] args) {
        AttendanceDAO dao = new AttendanceDAO(); // Thay tên DAO nếu khác

        int classGroupId = 1; // ID lớp học cần test, đảm bảo tồn tại trong DB

        // Giả sử có 3 học sinh với ID 101, 102, 103
        Map<Integer, Boolean> attendanceMap = new HashMap<>();
        attendanceMap.put(23, true);   // có mặt
        attendanceMap.put(26, false);  // vắng
       

        dao.saveAttendance(classGroupId, attendanceMap);

        System.out.println("Đã lưu điểm danh cho lớp học có ID: " + classGroupId);
    }
}
