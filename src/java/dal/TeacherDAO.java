package dal;

import dal.DBContext;
import entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Ngày update: 30/06/2025  
 * Người viết: Văn Thị Như
 * 
 * Mô tả: DAO phục vụ truy vấn dữ liệu giáo viên trong hệ thống
 */
public class TeacherDAO {

    /**
     *  Lấy danh sách tất cả giáo viên trong hệ thống (roleID = 2)
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
            Connection conn = new DBContext().connection;
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
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

    /**
     *  Lấy lịch dạy của từng giáo viên (theo template cố định trong tuần)
     * 
     * Kết quả mỗi dòng là một buổi dạy của một giáo viên.
     * Dùng để hiển thị lịch giảng dạy của tất cả giáo viên.
     * 
     * @return List<Object[]> với các phần tử:
     * - [0] Tên giáo viên
     * - [1] Tên môn học
     * - [2] Tên lớp học
     * - [3] Thứ trong tuần (DayOfWeek)
     * - [4] Giờ bắt đầu
     * - [5] Giờ kết thúc
     */
    public List<Object[]> getTeacherSchedules() {
        List<Object[]> list = new ArrayList<>();
        String sql = """
            SELECT 
                u.FullName AS TeacherName,
                s.SubjectName,
                cg.ClassGroupName,
                st.DayOfWeek,
                sl.Start_time,
                sl.End_time
            FROM ScheduleTemplate st
            JOIN [User] u ON st.TeacherID = u.UserID
            JOIN ClassGroup cg ON st.ClassGroupID = cg.ClassGroupID
            JOIN TutoringClass tc ON cg.TutoringClassID = tc.TutoringClassID
            JOIN Subjects s ON tc.SubjectID = s.SubjectID
            JOIN Shiftlearn sl ON st.ShiftID = sl.ShiftID
            ORDER BY u.FullName, st.DayOfWeek, sl.Start_time
        """;

        try (
            Connection conn = new DBContext().connection;
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                list.add(new Object[]{
                    rs.getString("TeacherName"),     // 0
                    rs.getString("SubjectName"),     // 1
                    rs.getString("ClassGroupName"),  // 2
                    rs.getInt("DayOfWeek"),          // 3
                    rs.getTime("Start_time"),        // 4
                    rs.getTime("End_time")           // 5
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
