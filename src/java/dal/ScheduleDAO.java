package dal;

import entity.ScheduleStu;
import entity.Attendance;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleDAO {
    public List<ScheduleStu> getSchedulesByStudentAndWeek(int userID, Date startOfWeek, Date endOfWeek) throws SQLException {
        List<ScheduleStu> schedules = new ArrayList<>();
        String sql = "SELECT s.ScheduleID, s.ClassGroupID, s.ShiftID, s.RoomID, s.DateLearn, s.UserID AS TeacherID, " +
                     "sub.SubjectName, r.roomName " +
                     "FROM Schedule s " +
                     "JOIN ClassGroup cg ON s.ClassGroupID = cg.ClassGroupID " +
                     "JOIN TutoringClass tc ON cg.TutoringClassID = tc.TutoringClassID " +
                     "JOIN Subjects sub ON tc.SubjectID = sub.SubjectID " +
                     "JOIN Room r ON s.RoomID = r.id " +
                     "JOIN ClassGroup_Student cs ON cg.ClassGroupID = cs.ClassGroupID " +
                     "WHERE cs.StudentID = ? AND s.DateLearn BETWEEN ? AND ? AND cs.IsActive = 1 " +
                     "ORDER BY s.DateLearn, s.ShiftID";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = new DBContext().connection;
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userID);
            stmt.setDate(2, new java.sql.Date(startOfWeek.getTime()));
            stmt.setDate(3, new java.sql.Date(endOfWeek.getTime()));
            rs = stmt.executeQuery();
            while (rs.next()) {
                ScheduleStu schedule = new ScheduleStu();
                schedule.setScheduleID(rs.getInt("ScheduleID"));
                schedule.setClassGroupID(rs.getInt("ClassGroupID"));
                schedule.setShiftID(rs.getInt("ShiftID"));
                schedule.setRoomID(rs.getInt("RoomID"));
                schedule.setDateLearn(rs.getDate("DateLearn"));
                schedule.setTeacherID(rs.getInt("TeacherID"));
                schedule.setSubjectName(rs.getString("SubjectName"));
                schedule.setRoomName(rs.getString("roomName"));
                schedules.add(schedule);
            }
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return schedules;
    }

    public List<Attendance> getAttendancesByStudentAndWeek(int userID, Date startOfWeek, Date endOfWeek) throws SQLException {
        List<Attendance> attendances = new ArrayList<>();
        String sql = "SELECT ClassGroupID, UserID, AttendanceDate, IsPresent " +
                     "FROM Attendance " +
                     "WHERE UserID = ? AND AttendanceDate BETWEEN ? AND ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = new DBContext().connection;
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userID);
            stmt.setDate(2, new java.sql.Date(startOfWeek.getTime()));
            stmt.setDate(3, new java.sql.Date(endOfWeek.getTime()));
            rs = stmt.executeQuery();
            while (rs.next()) {
                Attendance attendance = new Attendance();
                attendance.setClassGroup(rs.getInt("ClassGroupID"));
                attendance.setUserID(rs.getInt("UserID"));
                attendance.setAttendanceDate(rs.getDate("AttendanceDate"));
                attendance.setIsPresent(rs.getBoolean("IsPresent"));
                attendances.add(attendance);
            }
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return attendances;
    }
}