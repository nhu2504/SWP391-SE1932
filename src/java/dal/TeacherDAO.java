package dal;

import dal.DBContext;
import entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

/**
 * Ngày update: 30/06/2025 Người viết: Văn Thị Như
 *
 * Mô tả: DAO phục vụ truy vấn dữ liệu giáo viên trong hệ thống
 */
public class TeacherDAO {

    /**
     * Lấy danh sách tất cả giáo viên trong hệ thống (roleID = 2)
     *
     * @return List<User> danh sách đối tượng giáo viên
     */
    public List<User> getAllTeachers() {
        List<User> teachers = new ArrayList<>();
        String sql = """
            SELECT UserID, FullName, Gender, phone, email, avatar, Certi, Descrip, onlineStatus, SchoolID
            FROM [User]
            WHERE roleID = ?
        """;
        try (
                Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, 2); // roleID = 2 → giáo viên
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User teacher = new User();
                teacher.setId(rs.getInt("UserID"));
                teacher.setName(rs.getString("FullName"));
                teacher.setGender(rs.getString("Gender"));
                teacher.setPhone(rs.getString("phone"));
                teacher.setEmail(rs.getString("email"));
                teacher.setAvatar(
                        rs.getString("avatar") != null ? rs.getString("avatar") : "images/fallback.png"
                );
                teacher.setCerti(rs.getString("Certi"));
                teacher.setDescrip(rs.getString("Descrip"));
                teacher.setStatus(rs.getBoolean("onlineStatus") ? 1 : 0);
                teacher.setSchoolID(rs.getInt("SchoolID"));
                teachers.add(teacher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }

            // Trả về List<Object[]> gồm:
        // [0] = Teacher Name
        // [1] = Class Name
        // [2] = Room Name
        // [3] = DayOfWeek (int)
        // [4] = Start Time
        // [5] = End Time
    public List<Object[]> getTeacherSchedulesToday() {
        List<Object[]> list = new ArrayList<>();
        String sql = """
        SELECT u.FullName AS TeacherName, cg.ClassGroupName, r.roomName, 
               DATEPART(WEEKDAY, s.DateLearn) AS DayOfWeek, 
               sh.Start_time, sh.End_time, sj.SubjectName
        FROM Schedule s
        JOIN ClassGroup cg ON s.ClassGroupID = cg.ClassGroupID
        JOIN TutoringClass tc ON cg.TutoringClassID = tc.TutoringClassID
        JOIN Subjects sj ON tc.SubjectID = sj.SubjectID
        JOIN [User] u ON s.UserID = u.UserID
        JOIN Room r ON s.RoomID = r.id
        JOIN Shiftlearn sh ON s.ShiftID = sh.ShiftID
        WHERE s.DateLearn = ?
        GROUP BY u.FullName, cg.ClassGroupName, r.roomName, 
                 DATEPART(WEEKDAY, s.DateLearn), sh.Start_time, sh.End_time, sj.SubjectName
    """;

        LocalDate today = LocalDate.now();

        try (Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, java.sql.Date.valueOf(today));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Object[]{
                        rs.getString("TeacherName"), // 0
                        rs.getString("SubjectName"), // 1
                        rs.getString("ClassGroupName"), // 2
                        rs.getInt("DayOfWeek"), // 3
                        rs.getTime("Start_time"), // 4
                        rs.getTime("End_time") // 5
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Object[]> getTeacherSchedulesBetween(Date fromDate, Date toDate) {
        List<Object[]> list = new ArrayList<>();
        String sql = """
        SELECT u.FullName, c.ClassName, r.RoomName,
               DATEPART(WEEKDAY, s.DateLearn) AS DayOfWeek,
               sh.Start_time, sh.End_time
        FROM Schedule s
        JOIN ClassGroup cg ON s.ClassGroupID = cg.ClassGroupID
        JOIN TutoringClass c ON cg.TutoringClassID = c.TutoringClassID
        JOIN [User] u ON cg.TeacherID = u.UserID
        JOIN Room r ON s.RoomID = r.id
        JOIN Shiftlearn sh ON s.ShiftID = sh.ShiftID
        WHERE s.DateLearn BETWEEN ? AND ?
        ORDER BY u.FullName, s.DateLearn, sh.Start_time
    """;
        try (Connection con = new DBContext().connection; PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(fromDate.getTime()));
            ps.setDate(2, new java.sql.Date(toDate.getTime()));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Object[] row = new Object[]{
                    rs.getString(1), // FullName
                    rs.getString(2), // ClassName
                    rs.getString(3), // Room
                    rs.getInt(4), // DayOfWeek
                    rs.getTime(5), // Start_time
                    rs.getTime(6) // End_time
                };
                list.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
