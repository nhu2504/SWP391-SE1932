/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.ClassGroup;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author NGOC ANH
 */
public class ClassGroupDAO {

    public List<ClassGroup> getClassesByTeacher(int teacherId) {
        List<ClassGroup> list = new ArrayList<>();
        String sql = "select c.ClassGroupID, c.ClassGroupName, c.TeacherID from\n"
                + "ClassGroup c join ScheduleTemplate s on c.TeacherID = s.TeacherID where c.TeacherID = ?";
        try (Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, teacherId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ClassGroup c = new ClassGroup();
                c.setClassGroupId(rs.getInt("ClassGroupID"));
                c.setName(rs.getString("ClassGroupName"));
                c.setTeachId(rs.getInt("TeacherID"));
                list.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Object[]> getClassGroupsWithRoomAndShift(int tutoringClassID) {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT *\n"
                + "FROM (\n"
                + "    SELECT \n"
                //                + "        cg.ClassGroupID,\n"
                + "        cg.ClassGroupName,\n"
                + "        cg.MaxStudent,\n"
                + "        r.roomName AS RoomName,\n"
                + "        u.FullName AS TeacherName,\n"
                + "        s.Start_time,\n"
                + "        s.End_time,\n"
                + "        sc.DateLearn AS StudyDate,\n"
                + "        ROW_NUMBER() OVER (PARTITION BY cg.ClassGroupID ORDER BY sc.DateLearn, s.Start_time) AS rn\n"
                + "    FROM ClassGroup cg\n"
                + "    LEFT JOIN Schedule sc ON sc.ClassGroupID = cg.ClassGroupID\n"
                + "    LEFT JOIN Room r ON sc.RoomID = r.id\n"
                + "    LEFT JOIN Shiftlearn s ON sc.ShiftID = s.ShiftID\n"
                + "    LEFT JOIN [User] u ON cg.TeacherID = u.UserID\n"
                + "    WHERE cg.TutoringClassID = ?\n"
                + ") t\n"
                + "WHERE t.rn = 1";
        try (Connection conn = new DBContext().connection; PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, tutoringClassID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Object[] row = new Object[8];
                //row[0] = rs.getInt("ClassGroupID");
                row[0] = rs.getString("ClassGroupName");
                row[1] = rs.getInt("MaxStudent");
                row[2] = rs.getString("RoomName");
                row[3] = rs.getString("TeacherName");

                row[4] = rs.getString("Start_time");
                row[5] = rs.getString("End_time");
                row[6] = rs.getDate("StudyDate");
                list.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ClassGroup> getClassGroupsByTutoringClassId(int tutoringClassID) {
        List<ClassGroup> classGroups = new ArrayList<>();
        String sql = "SELECT ClassGroupID, TutoringClassID, ClassGroupName, MaxStudent, RoomID, ShiftID, TeacherID "
                + "FROM ClassGroup WHERE TutoringClassID = ?";
        try (Connection conn = new DBContext().connection; PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, tutoringClassID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ClassGroup cg = new ClassGroup();
                cg.setClassGroupId(rs.getInt("ClassGroupID"));
                cg.setToturID(rs.getInt("TutoringClassID"));
                cg.setName(rs.getString("ClassGroupName"));
                cg.setMaxStudent(rs.getInt("MaxStudent"));

                cg.setTeachId(rs.getInt("TeacherID"));
                classGroups.add(cg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classGroups;
    }

    public List<ClassGroup> getTodayClasses(int teacherId) throws SQLException {
        List<ClassGroup> list = new ArrayList<>();
        String query = "SELECT cg.ClassGroupName\n"
                + "FROM Schedule s\n"
                + "JOIN ClassGroup cg ON s.ClassGroupID = cg.ClassGroupID\n"
                + "WHERE s.UserID = ?\n"
                + "  AND s.DateLearn = CONVERT(date, GETDATE())\n"
                + "ORDER BY s.ShiftID ASC";
        try (Connection conn = new DBContext().connection; PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, teacherId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ClassGroup cg = new ClassGroup();
                cg.setName(rs.getString("ClassGroupName"));
                list.add(cg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
        ClassGroupDAO dao = new ClassGroupDAO(); // Đổi tên nếu class của bạn khác
        int teacherId = 2; // Nhập ID giáo viên bạn muốn test

        try {
            List<ClassGroup> todayClasses = dao.getTodayClasses(teacherId);
            if (todayClasses.isEmpty()) {
                System.out.println("Không có lớp học nào hôm nay.");
            } else {
                System.out.println("Danh sách lớp học hôm nay:");
                for (ClassGroup cg : todayClasses) {
                    System.out.println("ID: " + cg.getClassGroupId() + " | Tên lớp: " + cg.getName());
                }
            }
        } catch (SQLException e) {
            System.out.println("Đã xảy ra lỗi khi truy vấn lớp học hôm nay:");
            e.printStackTrace();
        }
    }
}
