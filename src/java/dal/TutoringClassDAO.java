package dal;
import dal.DBContext;
import entity.ClassGroup;
import entity.TutoringClass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;



/**
 * Văn Thị Như
 */
public class TutoringClassDAO {
    
    /**
     * Tính thời lượng ca học dựa trên thời gian bắt đầu và kết thúc.
     * @param startTime Thời gian bắt đầu (java.sql.Time).
     * @param endTime Thời gian kết thúc (java.sql.Time).
     * @return Thời lượng ca học dưới dạng chuỗi (VD: "2 giờ 30 phút").
     */
    private String calculateDuration(Time startTime, Time endTime) {
        if (startTime != null && endTime != null) {
            try {
                long diffInMillis = endTime.getTime() - startTime.getTime();
                long hours = TimeUnit.MILLISECONDS.toHours(diffInMillis);
                long minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis) % 60;
                String duration = (hours > 0 ? hours + " giờ " : "") + (minutes > 0 ? minutes + " phút" : "");
                return duration.isEmpty() ? "0 phút" : duration;
            } catch (Exception e) {
                
                return "Lỗi định dạng thời gian";
            }
        }
        return "Chưa xác định";
    }

    /**
     * Lấy danh sách tất cả lớp học, nhóm theo môn học (SubjectID) và khối
     * @return Map với key là SubjectID và value là danh sách lớp học.
     */
    public List<TutoringClass> getAllClasses() {
    List<TutoringClass> classes = new ArrayList<>();
    String sql = "SELECT tc.TutoringClassID, tc.ClassName, tc.ImageTutoring, tc.Descrip, tc.isHot, tc.SubjectID, tc.StartDate, tc.EndDate, tc.Tuitionfee, tc.userID, tc.roomID, tc.shiftID, tc.MaxStudent, tc.GradeID " +
                 "FROM TutoringClass tc";
    try (Connection conn = new DBContext().connection;
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            TutoringClass cls = new TutoringClass();
            cls.setTutoringClassID(rs.getInt("TutoringClassID"));
            cls.setClassName(rs.getString("ClassName"));
            cls.setImage(rs.getString("ImageTutoring"));
            cls.setDescrip(rs.getString("Descrip"));
            cls.setIsHot(rs.getBoolean("isHot"));
            cls.setSubjectID(rs.getInt("SubjectID"));
            cls.setStartDate(rs.getDate("StartDate"));
            cls.setEndDate(rs.getDate("EndDate"));
            cls.setPrice(rs.getDouble("Tuitionfee"));
//            cls.setTeacherID(rs.getInt("userID"));
//            cls.setRoomID(rs.getInt("roomID"));
//            cls.setShiftID(rs.getInt("shiftID"));
            cls.setMaxStudent(rs.getInt("MaxStudent"));
            cls.setGradeID(rs.getInt("GradeID"));
            classes.add(cls);
        }
    } catch (SQLException e) {
        e.getStackTrace();
    }
    return classes;
}

    /**
     * Lấy danh sách lớp học theo trạng thái nổi bật (isHot).
     * @param isHot True cho lớp nổi bật, False cho lớp quanh năm.
     * @return Danh sách lớp học với thông tin chi tiết.
     */
    public List<Map<String, Object>> getTutoringClasses(boolean isHot) {
    List<Map<String, Object>> tutoringClasses = new ArrayList<>();
    String sql = "SELECT tc.TutoringClassID, tc.ClassName, tc.ImageTutoring, tc.Descrip, tc.isHot, " +
                 "tc.SubjectID, s.SubjectName, s.ImageSubject, tc.StartDate, tc.EndDate, tc.Tuitionfee, " +
                 "tc.userID, u.FullName, u.email, u.roleID, r.roleName, " +
                 "tc.roomID, rm.name AS roomName, tc.shiftID, sl.Start_time, sl.End_time, tc.MaxStudent, " +
                 "tc.GradeID, g.GradeName, " +
                 "(SELECT COUNT(*) FROM ClassGroup cg " +
                 " LEFT JOIN ClassGroup_Student cgs ON cg.ClassGroupID = cgs.ClassGroupID " +
                 " WHERE cg.TutoringClassID = tc.TutoringClassID AND cgs.IsActive = 1) AS CurrentStudents " +
                 "FROM TutoringClass tc " +
                 "LEFT JOIN Subjects s ON tc.SubjectID = s.SubjectID " +
                 "LEFT JOIN [User] u ON tc.userID = u.UserID " +
                 "LEFT JOIN Roles r ON u.roleID = r.roleID " +
                 "LEFT JOIN Room rm ON tc.roomID = rm.id " +
                 "LEFT JOIN Shiftlearn sl ON tc.shiftID = sl.ShiftID " +
                 "LEFT JOIN Grade g ON tc.GradeID = g.GradeID " +
                 "WHERE tc.isHot = ?";

    try (Connection conn = new DBContext().connection;
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setBoolean(1, isHot);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Map<String, Object> course = new HashMap<>();

            Time startTime = rs.getTime("Start_time");
            Time endTime = rs.getTime("End_time");
            String duration = calculateDuration(startTime, endTime);

            boolean isHotValue = rs.getBoolean("isHot");
            course.put("isHot", rs.wasNull() ? false : isHotValue); // Xử lý NULL
            TutoringClass tutoringClass = new TutoringClass(
                    rs.getInt("TutoringClassID")
                    , rs.getString("ClassName") != null ? rs.getString("ClassName") : ""
                    , rs.getString("ImageTutoring") != null ? rs.getString("ImageTutoring") : ""
                    , rs.getString("Descrip") != null ? rs.getString("Descrip") : ""
                    , isHotValue
                    , rs.getInt("SubjectID")
                    , rs.getDate("StartDate")
                    , rs.getDate("EndDate")
                    , rs.getBigDecimal("Tuitionfee") != null ? rs.getBigDecimal("Tuitionfee").doubleValue() : 0.0
                    , rs.getInt("MaxStudent")
                    , rs.getInt("GradeID"));

           

            course.put("tutoringClass", tutoringClass);
            course.put("subjectName", rs.getString("SubjectName") != null ? rs.getString("SubjectName") : "Không xác định");
            course.put("teacherName", rs.getString("FullName") != null ? rs.getString("FullName") : "Không xác định");
            course.put("roomName", rs.getString("roomName") != null ? rs.getString("roomName") : "Không xác định");
            course.put("startTime", startTime != null ? startTime.toString() : "");
            course.put("endTime", endTime != null ? endTime.toString() : "");
            course.put("duration", duration);
            course.put("gradeName", rs.getString("GradeName") != null ? rs.getString("GradeName") : "Không xác định");
            course.put("imageSubject", rs.getString("ImageSubject") != null ? rs.getString("ImageSubject") : "");
            course.put("roleName", rs.getString("roleName") != null ? rs.getString("roleName") : "");
            course.put("email", rs.getString("email") != null ? rs.getString("email") : "");
            course.put("currentStudents", rs.getInt("CurrentStudents"));

            tutoringClasses.add(course);
        }
    } catch (SQLException e) {
        e.getStackTrace();
    }
    return tutoringClasses;
}

    /**
     * Lấy danh sách lớp học nổi bật.
     * @return Danh sách lớp học nổi bật.
     */
    public List<Map<String, Object>> getFeaturedTutoringClasses() {
        return getTutoringClasses(true);
    }

    /**
     * Lấy danh sách lớp học quanh năm.
     * @return Danh sách lớp học quanh năm.
     */
    public List<Map<String, Object>> getYearRoundTutoringClasses() {
        return getTutoringClasses(false);
    }
    /**
     * Lấy chi tiết khóa học dựa trên TutoringClassID, bao gồm danh sách ClassGroup và ScheduleTemplate.
     * @param tutoringClassID ID của khóa học.
     * @return Map chứa thông tin khóa học, danh sách ClassGroup và ScheduleTemplate.
     */
    public Map<String, Object> getTutoringClassDetail(int tutoringClassID) {
    Map<String, Object> courseDetail = new HashMap<>();
    ScheduleTemplateDAO scheduleTemplateDAO = new ScheduleTemplateDAO();
    String sqlTutoringClass = "SELECT tc.TutoringClassID, tc.ClassName, tc.ImageTutoring, tc.Descrip, tc.isHot, " +
                             "tc.SubjectID, s.SubjectName, s.ImageSubject, tc.StartDate, tc.EndDate, tc.Tuitionfee, " +
                             "tc.userID, u.FullName AS TeacherName, u.email, u.roleID, r.roleName, " +
                             "tc.roomID, rm.name AS roomName, tc.shiftID, sl.Start_time, sl.End_time, tc.MaxStudent, " +
                             "tc.GradeID, g.GradeName, " +
                             "(SELECT COUNT(*) FROM ClassGroup cg " +
                             " LEFT JOIN ClassGroup_Student cgs ON cg.ClassGroupID = cgs.ClassGroupID " +
                             " WHERE cg.TutoringClassID = tc.TutoringClassID AND cgs.IsActive = 1) AS CurrentStudents " +
                             "FROM TutoringClass tc " +
                             "LEFT JOIN Subjects s ON tc.SubjectID = s.SubjectID " +
                             "LEFT JOIN [User] u ON tc.userID = u.UserID " +
                             "LEFT JOIN Roles r ON u.roleID = r.roleID " +
                             "LEFT JOIN Room rm ON tc.roomID = rm.id " +
                             "LEFT JOIN Shiftlearn sl ON tc.shiftID = sl.ShiftID " +
                             "LEFT JOIN Grade g ON tc.GradeID = g.GradeID " +
                             "WHERE tc.TutoringClassID = ?";

    String sqlClassGroups = "SELECT cg.ClassGroupID, cg.TutoringClassID, cg.ClassGroupName AS ClassGroupName, cg.MaxStudent, \n" +
"       cg.RoomID, rm.name AS RoomName, cg.ShiftID, sl.Start_time, sl.End_time, \n" +
"       cg.TeacherID, u.FullName AS TeacherName, \n" +
"       (SELECT COUNT(*) FROM ClassGroup_Student cgs WHERE cgs.ClassGroupID = cg.ClassGroupID AND cgs.IsActive = 1) AS CurrentStudents \n" +
"FROM ClassGroup cg \n" +
"LEFT JOIN Room rm ON cg.RoomID = rm.id \n" +
"LEFT JOIN Shiftlearn sl ON cg.ShiftID = sl.ShiftID \n" +
"LEFT JOIN [User] u ON cg.TeacherID = u.UserID \n" +
"WHERE cg.TutoringClassID = ?";

    System.out.println("DAO - Starting getTutoringClassDetail for ID: " + tutoringClassID); // Log bắt đầu
    try (Connection conn = new DBContext().connection) {
        if (conn == null) {
            System.out.println("DAO - Connection is null");
            return null;
        }

        try (PreparedStatement ps = conn.prepareStatement(sqlTutoringClass)) {
            ps.setInt(1, tutoringClassID);
            System.out.println("DAO - Executing query for tutoringClassID: " + tutoringClassID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Time startTime = rs.getTime("Start_time");
                Time endTime = rs.getTime("End_time");
                String duration = calculateDuration(startTime, endTime);

                Object isHotObj = rs.getObject("isHot");
                boolean isHot = false;
                if (isHotObj != null) {
                    if (isHotObj instanceof Boolean) {
                        isHot = (Boolean) isHotObj;
                    } else if (isHotObj instanceof Number) {
                        isHot = ((Number) isHotObj).intValue() == 1;
                    } else if (isHotObj instanceof String) {
                        isHot = Integer.parseInt((String) isHotObj) == 1;
                    }
                }
                courseDetail.put("isHot", isHot);
                System.out.println("DAO - isHot for tutoringClassID " + tutoringClassID + ": " + isHot + " (Raw value: " + isHotObj + ", Type: " + (isHotObj != null ? isHotObj.getClass().getName() : "null") + ")");

                TutoringClass tutoringClass = new TutoringClass(
                    rs.getInt("TutoringClassID"),
                    rs.getString("ClassName"),
                    rs.getString("ImageTutoring"),
                    rs.getString("Descrip"),
                    isHot,
                    rs.getInt("SubjectID"),
                    rs.getDate("StartDate"),
                    rs.getDate("EndDate"),
                    rs.getBigDecimal("Tuitionfee") != null ? rs.getBigDecimal("Tuitionfee").doubleValue() : 0.0,
                    
                    rs.getInt("MaxStudent"),
                    rs.getInt("GradeID")
                );

                courseDetail.put("tutoringClass", tutoringClass);
                courseDetail.put("subjectName", rs.getString("SubjectName"));
                courseDetail.put("startTime", startTime != null ? startTime.toString() : "");
                courseDetail.put("endTime", endTime != null ? endTime.toString() : "");
                courseDetail.put("price", rs.getBigDecimal("Tuitionfee") != null ? rs.getBigDecimal("Tuitionfee").doubleValue() : 0.0);
                courseDetail.put("maxStudents", rs.getInt("MaxStudent"));
                courseDetail.put("currentStudents", rs.getInt("CurrentStudents"));
            } else {
                System.out.println("DAO - No record found for tutoringClassID: " + tutoringClassID);
                return null;
            }
        } catch (SQLException e) {
            System.out.println("DAO - SQLException: " + e.getMessage());
            
            return null;
        }

        List<Map<String, Object>> classGroups = new ArrayList<>();
try (PreparedStatement ps = conn.prepareStatement(sqlClassGroups)) {
    ps.setInt(1, tutoringClassID);
    ResultSet rs = ps.executeQuery();
    int count = 0;
    while (rs.next()) {
        Map<String, Object> classGroupDetail = new HashMap<>();
        Time startTime = rs.getTime("Start_time");
        Time endTime = rs.getTime("End_time");
        String duration = calculateDuration(startTime, endTime);

        ClassGroup classGroup = new ClassGroup(
            rs.getInt("ClassGroupID"),
            rs.getInt("TutoringClassID"),
            rs.getString("ClassGroupName"),
            rs.getInt("MaxStudent"),
            rs.getInt("RoomID"),
            rs.getInt("ShiftID"),
            rs.getInt("TeacherID")
        );

        classGroupDetail.put("classGroup", classGroup);
        classGroupDetail.put("roomName", rs.getString("RoomName"));
        classGroupDetail.put("teacherName", rs.getString("TeacherName"));
        classGroupDetail.put("startTime", startTime != null ? startTime.toString() : "");
        classGroupDetail.put("endTime", endTime != null ? endTime.toString() : "");
        classGroupDetail.put("duration", duration);
        classGroupDetail.put("currentStudents", rs.getInt("CurrentStudents"));

        // Lấy lịch học nếu có
        List<Map<String, Object>> scheduleTemplates = scheduleTemplateDAO.getScheduleTemplatesByClassGroup(rs.getInt("ClassGroupID"));
        classGroupDetail.put("scheduleTemplates", scheduleTemplates);

        classGroups.add(classGroupDetail);

        // Log thông tin từng group
        System.out.println("[DAO] ClassGroup: " + rs.getString("ClassGroupName")
            + ", Room: " + rs.getString("RoomName")
            + ", Teacher: " + rs.getString("TeacherName")
            + ", Start: " + rs.getString("Start_time")
            + ", End: " + rs.getString("End_time"));
        count++;
    }
    System.out.println("[DAO] Tổng số classGroup lấy được: " + count);
    if (count == 0) {
        System.out.println("[DAO] Không có classGroup nào cho TutoringClassID: " + tutoringClassID);
    }
}
courseDetail.put("classGroups", classGroups);
    } catch (SQLException e) {
        
        return null;
    }
    return courseDetail;
}
    public List<ClassGroup> getClassGroupsByTutoringClassId(int tutoringClassID) {
        List<ClassGroup> classGroups = new ArrayList<>();
        String sql = "SELECT classGroupId, toturID, name, maxStudent, roomId, shiftId, teachId " +
                     "FROM ClassGroup WHERE toturID = ?";
        try (Connection conn = new DBContext().connection;
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, tutoringClassID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ClassGroup cg = new ClassGroup();
                cg.setClassGroupId(rs.getInt("classGroupId"));
                cg.setToturID(rs.getInt("toturID"));
                cg.setName(rs.getString("name"));
                cg.setMaxStudent(rs.getInt("maxStudent"));
                cg.setRoomId(rs.getInt("roomId"));
                cg.setShiftId(rs.getInt("shiftId"));
                cg.setTeachId(rs.getInt("teachId"));
                classGroups.add(cg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classGroups;
    }
}