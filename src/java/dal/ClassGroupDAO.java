/*
 * Tác giả: Van Nhu
 * Ngày tạo: 23/06/2025
 * Mô tả:
 *  - DAO quản lý các thao tác liên quan đến bảng ClassGroup (nhóm lớp).
 *  - Bao gồm:
 *      + Lấy danh sách nhóm lớp của một lớp học thêm cụ thể
 *      + Lấy lịch học đầu tiên (ca học + phòng + giáo viên) cho mỗi nhóm lớp
 */

package dal;

import entity.ClassGroup;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
}
