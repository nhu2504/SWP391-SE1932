package dal;

import entity.HomeTutoringClass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entity.TutoringClass;
import java.sql.Time;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TutoringClassDAO {

    public ArrayList<TutoringClass> getClassesByUserID(int userID) {
        ArrayList<TutoringClass> classes = new ArrayList<>();
        String sql = "SELECT tc.TutoringClassID, tc.ClassName, tc.SubjectID, tc.userID AS TeacherID, "
                + "tc.StartDate, tc.EndDate, tc.roomID, tc.shiftID, tc.ThuID "
                + "FROM TutoringClass tc "
                + "JOIN TutoringRegistration tr ON tc.TutoringClassID = tr.TutoringClassID "
                + "WHERE tr.UserID = ?";
        SubjectDAO sd = new SubjectDAO();
        loginDAO ld = new loginDAO();
        RoomDAO rd = new RoomDAO();
        ThuDAO td = new ThuDAO();
        ShiftlearnDAO sld = new ShiftlearnDAO();
        try (Connection conn = new DBContext().connection; PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TutoringClass tc = new TutoringClass(
                        rs.getInt("TutoringClassID"),
                        rs.getString("ClassName"),
                        sd.getSubjectByID(rs.getInt("SubjectID")),
                        ld.getUserByID(rs.getInt("TeacherID")),
                        rs.getString("StartDate"),
                        rs.getString("EndDate"),
                        rd.getRoomByID(rs.getInt("roomID")),
                        sld.getShiftByID(rs.getInt("shiftID")),
                        td.getThuByID(rs.getInt("ThuID"))
                );
                classes.add(tc);
            }
            System.out.println("TutoringClassDAO: classes size for userID " + userID + " = " + classes.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classes;
    }

    public TutoringClass getTutoringClassByID(int id) {
        String query = "select * from TutoringClass\n"
                + "  where TutoringClassID = ?";
         SubjectDAO sd = new SubjectDAO();
        loginDAO ld = new loginDAO();
        RoomDAO rd = new RoomDAO();
        ThuDAO td = new ThuDAO();
        ShiftlearnDAO sld = new ShiftlearnDAO();
        try {
            Connection conn = new DBContext().connection;
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new TutoringClass(
                        rs.getInt("TutoringClassID"),
                        rs.getString("ClassName"),
                        sd.getSubjectByID(rs.getInt("SubjectID")),
                        ld.getUserByID(rs.getInt("TeacherID")),
                        rs.getString("StartDate"),
                        rs.getString("EndDate"),
                        rd.getRoomByID(rs.getInt("roomID")),
                        sld.getShiftByID(rs.getInt("shiftID")),
                        td.getThuByID(rs.getInt("ThuID"))
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public Map<String, List<Map<String, String>>> getAllClasses() {
        Map<String, List<Map<String, String>>> classes = new HashMap<>();
        try (Connection conn = new DBContext().connection;
             PreparedStatement ps = conn.prepareStatement(
                     // Câu SQL lấy ID, tên và SubjectId của khóa học
                     "SELECT TutoringClassID, ClassName, SubjectId FROM TutoringClass");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String subjectId = rs.getString("SubjectId");
                Map<String, String> cls = new HashMap<>();
                cls.put("id", rs.getString("TutoringClassID"));
                cls.put("name", rs.getString("ClassName"));
                classes.computeIfAbsent(subjectId, k -> new ArrayList<>()).add(cls);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classes;
    }

    // Lấy danh sách khóa học chi tiết
    public List<Map<String, Object>> getDetailedTutoringClasses() {
        List<Map<String, Object>> tutoringClasses = new ArrayList<>();
        try (Connection conn = new DBContext().connection;
             PreparedStatement ps = conn.prepareStatement(
                     // Câu SQL lấy thông tin chi tiết khóa học
                     "SELECT tc.TutoringClassID, tc.ClassName, tc.ImageTutoring, tc.Descrip, tc.isHot, "
                             + "tc.SubjectID, s.SubjectName, tc.StartDate, tc.EndDate, tc.Tuitionfee, "
                             + "tc.userID, u.FullName, tc.roomID, tc.shiftID, sl.Start_time, sl.End_time, "
                             + "tc.ThuID, t.NameThu, tc.MaxStudents "
                             + "FROM TutoringClass tc "
                             + "LEFT JOIN Subjects s ON tc.SubjectID = s.SubjectId "
                             + "LEFT JOIN [User] u ON tc.userID = u.userID "
                             + "LEFT JOIN Shiftlearn sl ON tc.shiftID = sl.ShiftID "
                             + "LEFT JOIN Thu t ON tc.ThuID = t.ThuID");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> course = new HashMap<>();
                // Lấy thời gian bắt đầu và kết thúc
                Time startTime = rs.getTime("Start_time");
                Time endTime = rs.getTime("End_time");
                String duration = "";
                if (startTime != null && endTime != null) {
                    // Tính thời lượng khóa học (giờ, phút)
                    long diffInMillis = endTime.getTime() - startTime.getTime();
                    long hours = TimeUnit.MILLISECONDS.toHours(diffInMillis);
                    long minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis) % 60;
                    duration = (hours > 0 ? hours + " giờ " : "") + (minutes > 0 ? minutes + " phút" : "");
                    if (duration.isEmpty()) {
                        duration = "0 phút";
                    }
                } else {
                    duration = "Chưa xác định";
                }
                // Lấy số học sinh tối đa, mặc định là 0 nếu null
                int maxStudents = rs.getObject("MaxStudents") != null ? rs.getInt("MaxStudents") : 0;

                // Tạo đối tượng HomeTutoringClass chứa thông tin khóa học
                course.put("tutoringClass", new HomeTutoringClass(
                        rs.getInt("TutoringClassID"),
                        rs.getString("ClassName"),
                        rs.getString("ImageTutoring"),
                        rs.getString("Descrip"),
                        rs.getBoolean("isHot"),
                        rs.getInt("SubjectID"),
                        rs.getDate("StartDate"),
                        rs.getDate("EndDate"),
                        rs.getBigDecimal("Tuitionfee"),
                        rs.getObject("userID") != null ? rs.getInt("userID") : null,
                        rs.getInt("roomID"),
                        rs.getInt("shiftID"),
                        rs.getObject("ThuID") != null ? rs.getInt("ThuID") : null,
                        maxStudents
                ));
                course.put("subjectName", rs.getString("SubjectName")); // Tên môn học
                course.put("teacherName", rs.getString("FullName")); // Tên giáo viên
                course.put("startTime", startTime); // Giờ bắt đầu
                course.put("endTime", endTime); // Giờ kết thúc
                course.put("duration", duration); // Thời lượng
                course.put("thuName", rs.getString("NameThu") != null ? rs.getString("NameThu") : "Chưa xác định"); // Thứ
                tutoringClasses.add(course);
            }
            // Sắp xếp khóa học, ưu tiên khóa học có isHot = true
            Collections.sort(tutoringClasses, new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> a, Map<String, Object> b) {
                    HomeTutoringClass courseA = (HomeTutoringClass) a.get("tutoringClass");
                    HomeTutoringClass courseB = (HomeTutoringClass) b.get("tutoringClass");
                    return Boolean.compare(courseB.isHot(), courseA.isHot());
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tutoringClasses;
    }
}
