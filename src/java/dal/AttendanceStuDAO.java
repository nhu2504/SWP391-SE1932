package dal;

import entity.AttendanceStu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class AttendanceStuDAO {

    public ArrayList<AttendanceStu> getAttendanceByUserID(int userID) {
        ArrayList<AttendanceStu> attendances = new ArrayList<>();
        String query = "SELECT a.ClassGroupID, a.UserID, a.AttendanceDate, a.IsPresent, " +
                      "s.ShiftID, sl.Start_time, sl.End_time, r.roomName, u.FullName AS TeacherName, " +
                      "tc.ClassName, tc.TutoringClassID " +
                      "FROM Attendance a " +
                      "JOIN Schedule s ON a.ClassGroupID = s.ClassGroupID AND CAST(a.AttendanceDate AS DATE) = s.DateLearn " +
                      "JOIN Shiftlearn sl ON s.ShiftID = sl.ShiftID " +
                      "JOIN Room r ON s.RoomID = r.id " +
                      "JOIN [User] u ON s.UserID = u.UserID " +
                      "JOIN ClassGroup cg ON a.ClassGroupID = cg.ClassGroupID " +
                      "JOIN TutoringClass tc ON cg.TutoringClassID = tc.TutoringClassID " +
                      "WHERE a.UserID = ?";
        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd/MM/yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            while (rs.next()) {
                AttendanceStu attendance = new AttendanceStu();
                attendance.setClassGroupID(rs.getInt("ClassGroupID"));
                attendance.setUserID(rs.getInt("UserID"));
                attendance.setDate(dateFormat.format(rs.getDate("AttendanceDate")));
                attendance.setShift(rs.getInt("ShiftID") + ". (" + 
                                   timeFormat.format(rs.getTime("Start_time")) + " - " + 
                                   timeFormat.format(rs.getTime("End_time")) + ")");
                attendance.setRoom(rs.getString("roomName"));
                attendance.setTeacherName(rs.getString("TeacherName"));
                attendance.setStatus(rs.getBoolean("IsPresent") ? "Có mặt" : "Vắng mặt");
                attendance.setTutoringClassID(rs.getInt("TutoringClassID"));
                attendance.setClassName(rs.getString("ClassName"));
                attendances.add(attendance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attendances;
    }

    public ArrayList<AttendanceStu> getAttendanceByTutoringClassID(int tutoringClassID, int userID) {
        ArrayList<AttendanceStu> attendances = new ArrayList<>();
        String query = "SELECT a.ClassGroupID, a.UserID, a.AttendanceDate, a.IsPresent, " +
                      "s.ShiftID, sl.Start_time, sl.End_time, r.roomName, u.FullName AS TeacherName, " +
                      "tc.ClassName, tc.TutoringClassID " +
                      "FROM Attendance a " +
                      "JOIN Schedule s ON a.ClassGroupID = s.ClassGroupID AND CAST(a.AttendanceDate AS DATE) = s.DateLearn " +
                      "JOIN Shiftlearn sl ON s.ShiftID = sl.ShiftID " +
                      "JOIN Room r ON s.RoomID = r.id " +
                      "JOIN [User] u ON s.UserID = u.UserID " +
                      "JOIN ClassGroup cg ON a.ClassGroupID = cg.ClassGroupID " +
                      "JOIN TutoringClass tc ON cg.TutoringClassID = tc.TutoringClassID " +
                      "WHERE tc.TutoringClassID = ? AND a.UserID = ?";
        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, tutoringClassID);
            ps.setInt(2, userID);
            ResultSet rs = ps.executeQuery();
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd/MM/yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            while (rs.next()) {
                AttendanceStu attendance = new AttendanceStu();
                attendance.setClassGroupID(rs.getInt("ClassGroupID"));
                attendance.setUserID(rs.getInt("UserID"));
                attendance.setDate(dateFormat.format(rs.getDate("AttendanceDate")));
                attendance.setShift(rs.getInt("ShiftID") + ". (" + 
                                   timeFormat.format(rs.getTime("Start_time")) + " - " + 
                                   timeFormat.format(rs.getTime("End_time")) + ")");
                attendance.setRoom(rs.getString("roomName"));
                attendance.setTeacherName(rs.getString("TeacherName"));
                attendance.setStatus(rs.getBoolean("IsPresent") ? "Có mặt" : "Vắng mặt");
                attendance.setTutoringClassID(rs.getInt("TutoringClassID"));
                attendance.setClassName(rs.getString("ClassName"));
                attendances.add(attendance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attendances;
    }
}