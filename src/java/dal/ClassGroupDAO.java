/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.ClassGroup;
import entity.TutoringClassStu;
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

       /**
     * Lấy thông tin 1 lịch học đầu tiên (phòng, giáo viên, ca học) cho mỗi nhóm lớp
     * thuộc lớp học thêm có ID tương ứng.
     * 
     * Mỗi nhóm lớp chỉ lấy duy nhất 1 bản ghi đầu tiên (sắp xếp theo ngày học và giờ bắt đầu).
     * Trả về danh sách Object[] với:
     *    [0] ClassGroupName
     *    [1] MaxStudent
     *    [2] RoomName
     *    [3] TeacherName
     *    [4] Start_time
     *    [5] End_time
     *    [6] StudyDate
     */
    public List<Object[]> getClassGroupsWithRoomAndShift(int tutoringClassID) {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT *\n"
                + "FROM (\n"
                + "    SELECT \n"
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

        try (Connection conn = new DBContext().connection;
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, tutoringClassID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Object[] row = new Object[7];
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

    /**
     * Lấy danh sách các nhóm lớp theo tutoringClassID (ID lớp học thêm)
     * Trả về danh sách ClassGroup có thông tin cơ bản: ID, tên nhóm, max student, giáo viên
     */
    public List<ClassGroup> getClassGroupsByTutoringClassId(int tutoringClassID) {
        List<ClassGroup> classGroups = new ArrayList<>();
        String sql = "SELECT ClassGroupID, TutoringClassID, ClassGroupName, MaxStudent, RoomID, ShiftID, TeacherID "
                   + "FROM ClassGroup WHERE TutoringClassID = ?";

        try (Connection conn = new DBContext().connection;
             PreparedStatement stmt = conn.prepareStatement(sql)) {

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
        try (Connection conn = new DBContext().connection; 
                PreparedStatement stmt = conn.prepareStatement(query)) {
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
    public ArrayList<TutoringClassStu> getClassesByUserID(int userID) {
        ArrayList<TutoringClassStu> classes = new ArrayList<>();
        String query = "SELECT DISTINCT tc.TutoringClassID, tc.ClassName, tc.StartDate, tc.Tuitionfee, " +
                      "CASE WHEN p.PaymentID IS NOT NULL THEN 1 ELSE 0 END AS isPaid " +
                      "FROM TutoringClass tc " +
                      "JOIN ClassGroup cg ON tc.TutoringClassID = cg.TutoringClassID " +
                      "JOIN ClassGroup_Student cgs ON cg.ClassGroupID = cgs.ClassGroupID " +
                      "LEFT JOIN Payment p ON tc.TutoringClassID = p.TutoringClassID AND p.UserID = ? " +
                      "WHERE cgs.StudentID = ? AND cgs.IsActive = 1";
        try (Connection conn = new DBContext().connection; 
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userID);
            ps.setInt(2, userID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TutoringClassStu tutoringClass = new TutoringClassStu();
                tutoringClass.setTutoringClassID(rs.getInt("TutoringClassID"));
                tutoringClass.setClassName(rs.getString("ClassName"));
                tutoringClass.setStartDate(rs.getDate("StartDate"));
                tutoringClass.setFee(rs.getDouble("Tuitionfee"));
                tutoringClass.setPaid(rs.getBoolean("isPaid"));
                classes.add(tutoringClass);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classes;
    }

    public void updatePaymentStatus(int tutoringClassID, int userID) throws SQLException {
        String query = "IF EXISTS (SELECT 1 FROM Payment WHERE TutoringClassID = ? AND UserID = ?) " +
                      "UPDATE Payment SET PaymentDate = GETDATE() " +
                      "ELSE " +
                      "INSERT INTO Payment (UserID, TutoringClassID, Amount, PaymentDate) " +
                      "SELECT ?, ?, Tuitionfee, GETDATE() FROM TutoringClass WHERE TutoringClassID = ?";
        try (Connection conn = new DBContext().connection; 
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, tutoringClassID);
            ps.setInt(2, userID);
            ps.setInt(3, userID);
            ps.setInt(4, tutoringClassID);
            ps.setInt(5, tutoringClassID);
            ps.executeUpdate();
        }
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
