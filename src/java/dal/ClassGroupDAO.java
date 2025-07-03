package dal;

import entity.ClassGroup;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Ngày update: 30/06/2025  
 * Người viết: Văn Thị Như
 * Mục đích: Quản lý truy vấn liên quan đến bảng ClassGroup (lớp học cụ thể)
 */
public class ClassGroupDAO {

    /**
     *  Lấy lịch học (buổi đầu tiên) đại diện cho mỗi ClassGroup thuộc một khóa học (TutoringClass)
     * Mục đích: Hiển thị thông tin tóm tắt lớp gồm tên lớp, giáo viên, phòng học, thời gian học, v.v.
     *
     * @param tutoringClassID ID của khóa học
     * @return Danh sách Object[] chứa thông tin từng lớp học
     *         - [0] Tên lớp
     *         - [1] Số học sinh tối đa
     *         - [2] Tên phòng học
     *         - [3] Tên giáo viên
     *         - [4] Thời gian bắt đầu
     *         - [5] Thời gian kết thúc
     *         - [6] Ngày học (đầu tiên)
     */
    public List<Object[]> getClassGroupsWithRoomAndShift(int tutoringClassID) {
        List<Object[]> list = new ArrayList<>();
        String sql = """
            SELECT *
            FROM (
                SELECT 
                    cg.ClassGroupName,
                    cg.MaxStudent,
                    r.roomName AS RoomName,
                    u.FullName AS TeacherName,
                    s.Start_time,
                    s.End_time,
                    sc.DateLearn AS StudyDate,
                    ROW_NUMBER() OVER (PARTITION BY cg.ClassGroupID ORDER BY sc.DateLearn, s.Start_time) AS rn
                FROM ClassGroup cg
                LEFT JOIN Schedule sc ON sc.ClassGroupID = cg.ClassGroupID
                LEFT JOIN Room r ON sc.RoomID = r.id
                LEFT JOIN Shiftlearn s ON sc.ShiftID = s.ShiftID
                LEFT JOIN [User] u ON cg.TeacherID = u.UserID
                WHERE cg.TutoringClassID = ?
            ) t
            WHERE t.rn = 1
        """;
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
     *  Lấy danh sách ClassGroup thuộc về một TutoringClass cụ thể
     *
     * @param tutoringClassID ID của khóa học
     * @return Danh sách đối tượng ClassGroup
     */
    public List<ClassGroup> getClassGroupsByTutoringClassId(int tutoringClassID) {
        List<ClassGroup> classGroups = new ArrayList<>();
        String sql = """
            SELECT ClassGroupID, TutoringClassID, ClassGroupName, MaxStudent, RoomID, ShiftID, TeacherID
            FROM ClassGroup
            WHERE TutoringClassID = ?
        """;
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

    /**
     *  Lấy danh sách các lớp học đang mở (đã bắt đầu và chưa kết thúc)
     * Dựa trên ngày bắt đầu và kết thúc của khóa học (TutoringClass)
     * 
     * @return List<Object[]> gồm:
     *         - [0] ID lớp học
     *         - [1] Tên lớp học
     *         - [2] Tên giáo viên
     *         - [3] Tên khóa học
     *         - [4] Ngày bắt đầu
     *         - [5] Ngày kết thúc
     */
    public List<Object[]> getOpeningClassGroupsByCourseDate() {
        List<Object[]> list = new ArrayList<>();
        String sql = """
            SELECT 
                cg.ClassGroupID,
                cg.ClassGroupName,
                u.FullName AS TeacherName,
                tc.ClassName AS CourseName,
                tc.StartDate,
                tc.EndDate
            FROM ClassGroup cg
            JOIN TutoringClass tc ON cg.TutoringClassID = tc.TutoringClassID
            LEFT JOIN [User] u ON cg.TeacherID = u.UserID
            WHERE tc.StartDate <= CAST(GETDATE() AS DATE)
              AND tc.EndDate >= CAST(GETDATE() AS DATE)
        """;
        try (Connection conn = new DBContext().connection;
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Object[] row = new Object[6];
                row[0] = rs.getInt("ClassGroupID");
                row[1] = rs.getString("ClassGroupName");
                row[2] = rs.getString("TeacherName");
                row[3] = rs.getString("CourseName");
                row[4] = rs.getDate("StartDate");
                row[5] = rs.getDate("EndDate");
                list.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
