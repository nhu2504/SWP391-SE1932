package dal;

import entity.ClassGroup;
import entity.ScheduleTemplate;
import entity.TutoringClassStu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Ngày update: 30/06/2025 Người viết: Văn Thị Như Mục đích: Quản lý truy vấn
 * liên quan đến bảng ClassGroup (lớp học cụ thể)
 */
public class ClassGroupDAO {

    /**
     * Lấy danh sách các lớp học đang mở (đã bắt đầu và chưa kết thúc) Dựa trên
     * ngày bắt đầu và kết thúc của khóa học (TutoringClass)
     *
     * @return List<Object[]> gồm: - [0] ID lớp học - [1] Tên lớp học - [2] Tên
     * giáo viên - [3] Tên khóa học - [4] Ngày bắt đầu - [5] Ngày kết thúc
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
        try (Connection conn = new DBContext().connection; PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
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

    public List<Object[]> getClassGroupDetailsWithStudentCount(int tutoringClassID) {
        List<Object[]> list = new ArrayList<>();
        String sql = """
        SELECT 
            cg.ClassGroupID,
            cg.ClassGroupName,
            cg.MaxStudent,
            cg.minStudent,
            cg.isActive,
            r.roomName AS RoomName,
            u.FullName AS TeacherName,
            s.Start_time,
            s.End_time,
            st.DayOfWeek,
            (
                SELECT COUNT(*) 
                FROM ClassGroup_Student cgs 
                WHERE cgs.ClassGroupID = cg.ClassGroupID AND cgs.IsActive = 1
            ) AS CurrentStudentCount
        FROM ClassGroup cg
        LEFT JOIN ScheduleTemplate st ON st.ClassGroupID = cg.ClassGroupID
        LEFT JOIN Room r ON st.RoomID = r.id
        LEFT JOIN Shiftlearn s ON st.ShiftID = s.ShiftID
        LEFT JOIN [User] u ON cg.TeacherID = u.UserID
        WHERE cg.TutoringClassID = ?
    """;

        try (Connection conn = new DBContext().connection; PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, tutoringClassID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Object[] row = new Object[11];
                row[0] = rs.getString("ClassGroupName");
                row[1] = rs.getInt("MaxStudent");
                row[2] = rs.getString("RoomName");
                row[3] = rs.getString("TeacherName");
                row[4] = rs.getString("Start_time");
                row[5] = rs.getString("End_time");
                row[6] = rs.getInt("DayOfWeek"); // vì không còn DateLearn
                row[7] = rs.getInt("CurrentStudentCount");
                row[8] = rs.getInt("ClassGroupID");
                row[9] = rs.getInt("minStudent");
                row[10] = rs.getInt("isActive");

                list.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Văn Thị Như - thêm lớp học kèm lịch học mẫu
    public int addClassGroupWithTemplates(ClassGroup group, List<ScheduleTemplate> templates) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int classGroupId = -1;

        try {
            conn = new DBContext().connection;
            conn.setAutoCommit(false);

            String sql = "INSERT INTO ClassGroup (TutoringClassID, ClassGroupName, MaxStudent, TeacherID, minStudent) VALUES (?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, group.getToturID());
            ps.setString(2, group.getName());
            ps.setInt(3, group.getMaxStudent());
            ps.setInt(4, group.getTeachId());
            ps.setInt(5, group.getMinStudent());
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                classGroupId = rs.getInt(1);
            }

            if (templates != null && !templates.isEmpty()) {
                String sqlTemplate = "INSERT INTO ScheduleTemplate (ClassGroupID, DayOfWeek, ShiftID, RoomID, TeacherID) VALUES (?, ?, ?, ?, ?)";
                ps = conn.prepareStatement(sqlTemplate);
                for (ScheduleTemplate t : templates) {
                    ps.setInt(1, classGroupId);
                    ps.setInt(2, t.getDayOfWeek());
                    ps.setInt(3, t.getShiftId());
                    ps.setInt(4, t.getRoomId());
                    ps.setInt(5, t.getUserId());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }

        return classGroupId;
    }

    public ClassGroup getClassGroupById(int classGroupId) {
        String sql = "SELECT ClassGroupID, ClassGroupName, TutoringClassID FROM ClassGroup WHERE ClassGroupID = ?";
        try (Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, classGroupId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ClassGroup cg = new ClassGroup();
                cg.setClassGroupId(rs.getInt("ClassGroupID"));
                cg.setName(rs.getString("ClassGroupName"));
                cg.setToturID(rs.getInt("TutoringClassID"));
                return cg;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void activateClassGroup(int classGroupId) {
        String sql = "UPDATE ClassGroup SET isActive = 1 WHERE ClassGroupID = ?";
        try (Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, classGroupId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Ngọc Anh
    public List<ClassGroup> getAllClassGroupByUserId(int userId) {
        List<ClassGroup> list = new ArrayList<>();
        String sql = "SELECT cg.ClassGroupID, \n"
                + "       cg.ClassGroupName, \n"
                + "       cg.MaxStudent, \n"
                + "       cg.TutoringClassID,\n"
                + "       COUNT(DISTINCT cgs.StudentID) AS StudentCount,\n"
                + "       r.RoomName\n"
                + "FROM ClassGroup cg\n"
                + "LEFT JOIN ClassGroup_Student cgs ON cg.ClassGroupID = cgs.ClassGroupID AND cgs.IsActive = 1\n"
                + "LEFT JOIN Schedule s ON cg.ClassGroupID = s.ClassGroupID\n"
                + "LEFT JOIN Room r ON s.RoomID = r.id\n"
                + "WHERE cg.TeacherID = ?\n"
                + "GROUP BY cg.ClassGroupID, cg.ClassGroupName, cg.MaxStudent, cg.TutoringClassID, r.RoomName";

        try (Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ClassGroup cg = new ClassGroup();
                cg.setClassGroupId(rs.getInt("ClassGroupID"));
                cg.setToturID(rs.getInt("TutoringClassID"));
                cg.setName(rs.getString("ClassGroupName"));
                cg.setMaxStudent(rs.getInt("MaxStudent"));

                cg.setCurrentStudentCount(rs.getInt("StudentCount"));
                cg.setRoomName(rs.getString("RoomName"));
                list.add(cg);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
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

    // Nam
    public ArrayList<TutoringClassStu> getClassesByUserID(int userID) {
        ArrayList<TutoringClassStu> classes = new ArrayList<>();
        String query = "SELECT DISTINCT tc.TutoringClassID, tc.ClassName, tc.StartDate, tc.Tuitionfee, "
                + "CASE WHEN p.PaymentID IS NOT NULL THEN 1 ELSE 0 END AS isPaid "
                + "FROM TutoringClass tc "
                + "JOIN ClassGroup cg ON tc.TutoringClassID = cg.TutoringClassID "
                + "JOIN ClassGroup_Student cgs ON cg.ClassGroupID = cgs.ClassGroupID "
                + "LEFT JOIN Payment p ON tc.TutoringClassID = p.TutoringClassID AND p.UserID = ? "
                + "WHERE cgs.StudentID = ? AND cgs.IsActive = 1";
        try (Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(query)) {
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
        String query = "IF EXISTS (SELECT 1 FROM Payment WHERE TutoringClassID = ? AND UserID = ?) "
                + "UPDATE Payment SET PaymentDate = GETDATE() "
                + "ELSE "
                + "INSERT INTO Payment (UserID, TutoringClassID, Amount, PaymentDate) "
                + "SELECT ?, ?, Tuitionfee, GETDATE() FROM TutoringClass WHERE TutoringClassID = ?";
        try (Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, tutoringClassID);
            ps.setInt(2, userID);
            ps.setInt(3, userID);
            ps.setInt(4, tutoringClassID);
            ps.setInt(5, tutoringClassID);
            ps.executeUpdate();
        }
    }
}
