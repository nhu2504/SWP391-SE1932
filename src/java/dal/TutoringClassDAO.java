package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entity.TutoringClass;
import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TutoringClassDAO {

    public ArrayList<TutoringClass> getClassesByUserID(int userID) {
        ArrayList<TutoringClass> classes = new ArrayList<>();
        String sql = "SELECT * "
                + "FROM TutoringClass tc "
                + "JOIN TutoringRegistration tr ON tc.TutoringClassID = tr.TutoringClassID "
                + "WHERE tr.UserID = ?";
       
        
        ShiftlearnDAO sld = new ShiftlearnDAO();
        try (Connection conn = new DBContext().connection; PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TutoringClass tc = new TutoringClass(rs.getInt("TutoringClassID")
                        , rs.getString("ClassName")
                        , rs.getString("ImageTutoring")
                        , rs.getString("Descrip")
                        , rs.getInt("isHot")
                        , rs.getInt("SubjectID")
                        , rs.getDate("StartDate")
                        , rs.getDate("EndDate")
                        , rs.getDouble("Tuitionfee")
                        
                        , rs.getInt("MaxStudent"));
               
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
        
        try {
            Connection conn = new DBContext().connection;
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new TutoringClass(rs.getInt("TutoringClassID"), 
                        rs.getString("ClassName"), 
                        rs.getInt("SubjectID"), 
                        rs.getDate("StartDate"), 
                        rs.getDate("EndDate") 
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
                     // CÄ‚Â¢u SQL lĂ¡ÂºÂ¥y ID, tÄ‚Âªn vÄ‚Â  SubjectId cĂ¡Â»Â§a khÄ‚Â³a hĂ¡Â»Âc
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

    
    
    
    public List<Map<String, Object>> getFeaturedTutoringClasses() {
        List<Map<String, Object>> tutoringClasses = new ArrayList<>();
        try (Connection conn = new DBContext().connection;
             PreparedStatement ps = conn.prepareStatement(
                 "SELECT tc.TutoringClassID, tc.ClassName, tc.ImageTutoring, tc.Descrip, tc.isHot, " +
                 "tc.SubjectID, s.SubjectName, tc.StartDate, tc.EndDate, tc.Tuitionfee, " +
                 "tc.userID, u.FullName, tc.roomID, tc.shiftID, sl.Start_time, sl.End_time, " +
                 "tc.MaxStudent " +
                 "FROM TutoringClass tc " +
                 "LEFT JOIN Subjects s ON tc.SubjectID = s.SubjectID " +
                 "LEFT JOIN [User] u ON tc.userID = u.UserID " +
                 "LEFT JOIN Shiftlearn sl ON tc.shiftID = sl.ShiftID " +
                 "WHERE tc.isHot = ?")) {
            ps.setBoolean(1, true); // Lá»c khĂ³a há»c ná»•i báº­t
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> course = new HashMap<>();

                // Thá»i gian há»c
                Time startTime = rs.getTime("Start_time");
                Time endTime = rs.getTime("End_time");
                String duration = "";
                if (startTime != null && endTime != null) {
                    long diffInMillis = endTime.getTime() - startTime.getTime();
                    long hours = TimeUnit.MILLISECONDS.toHours(diffInMillis);
                    long minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis) % 60;
                    duration = (hours > 0 ? hours + " giá» " : "") + (minutes > 0 ? minutes + " phĂºt" : "");
                    if (duration.isEmpty()) {
                        duration = "0 phĂºt";
                    }
                } else {
                    duration = "ChÆ°a xĂ¡c Ä‘á»‹nh";
                }

                int maxStudents = rs.getObject("MaxStudent") != null ? rs.getInt("MaxStudent") : 0;

                course.put("tutoringClass", new TutoringClass(
                        rs.getInt("TutoringClassID"),
                        rs.getString("ClassName"),
                        rs.getString("ImageTutoring"),
                        rs.getString("Descrip"),
                        rs.getInt("isHot"),
                        rs.getInt("SubjectID"),
                        rs.getDate("StartDate"),
                        rs.getDate("EndDate"),
                        rs.getDouble("Tuitionfee"),
                        
                        maxStudents
                ));

                course.put("subjectName", rs.getString("SubjectName"));
                course.put("teacherName", rs.getString("FullName"));
                course.put("startTime", startTime);
                course.put("endTime", endTime);
                course.put("duration", duration);
                course.put("thuName", "KhĂ´ng xĂ¡c Ä‘á»‹nh"); // KhĂ´ng cĂ²n báº£ng Thu

                tutoringClasses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tutoringClasses;
    }
    
    // Láº¥y danh sĂ¡ch khĂ³a há»c quanh nÄƒm
    public List<Map<String, Object>> getYearRoundTutoringClasses() {
        List<Map<String, Object>> tutoringClasses = new ArrayList<>();
        try (Connection conn = new DBContext().connection;
             PreparedStatement ps = conn.prepareStatement(
                 "SELECT tc.TutoringClassID, tc.ClassName, tc.ImageTutoring, tc.Descrip, tc.isHot, " +
                 "tc.SubjectID, s.SubjectName, tc.StartDate, tc.EndDate, tc.Tuitionfee, " +
                 "tc.userID, u.FullName, tc.roomID, tc.shiftID, sl.Start_time, sl.End_time, " +
                 "tc.MaxStudent " +
                 "FROM TutoringClass tc " +
                 "LEFT JOIN Subjects s ON tc.SubjectID = s.SubjectID " +
                 "LEFT JOIN [User] u ON tc.userID = u.UserID " +
                 "LEFT JOIN Shiftlearn sl ON tc.shiftID = sl.ShiftID " +
                 "WHERE tc.isHot = ?")) {
            ps.setBoolean(1, false); // Lá»c khĂ³a há»c quanh nÄƒm
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> course = new HashMap<>();

                // Thá»i gian há»c
                Time startTime = rs.getTime("Start_time");
                Time endTime = rs.getTime("End_time");
                String duration = "";
                if (startTime != null && endTime != null) {
                    long diffInMillis = endTime.getTime() - startTime.getTime();
                    long hours = TimeUnit.MILLISECONDS.toHours(diffInMillis);
                    long minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis) % 60;
                    duration = (hours > 0 ? hours + " giá» " : "") + (minutes > 0 ? minutes + " phĂºt" : "");
                    if (duration.isEmpty()) {
                        duration = "0 phĂºt";
                    }
                } else {
                    duration = "ChÆ°a xĂ¡c Ä‘á»‹nh";
                }

                int maxStudents = rs.getObject("MaxStudent") != null ? rs.getInt("MaxStudent") : 0;

                course.put("tutoringClass", new TutoringClass(
                        rs.getInt("TutoringClassID"),
                        rs.getString("ClassName"),
                        rs.getString("ImageTutoring"),
                        rs.getString("Descrip"),
                        rs.getInt("isHot"),
                        rs.getInt("SubjectID"),
                        rs.getDate("StartDate"),
                        rs.getDate("EndDate"),
                        rs.getDouble("Tuitionfee"),
                        
                        maxStudents
                ));

                course.put("subjectName", rs.getString("SubjectName"));
                course.put("teacherName", rs.getString("FullName"));
                course.put("startTime", startTime);
                course.put("endTime", endTime);
                course.put("duration", duration);
                course.put("thuName", "KhĂ´ng xĂ¡c Ä‘á»‹nh"); // KhĂ´ng cĂ²n báº£ng Thu

                tutoringClasses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tutoringClasses;
    }
//    public static void main(String[] args) {
//        TutoringClassDAO dao = new TutoringClassDAO();
//        int testClassID = 1; // Ă¢â€ Â Ă„â€˜Ă¡Â»â€¢i ID phÄ‚Â¹ hĂ¡Â»Â£p vĂ¡Â»â€ºi dĂ¡Â»Â¯ liĂ¡Â»â€¡u trong DB
//
//        TutoringClass tc = dao.getTutoringClassByID(testClassID);
//
//        if (tc != null) {
//            System.out.println(tc.toString());
//        } else {
//            System.out.println("KhÄ‚Â´ng tÄ‚Â¬m thĂ¡ÂºÂ¥y lĂ¡Â»â€ºp hĂ¡Â»Âc vĂ¡Â»â€ºi ID = " + testClassID);
//        }
//    }
}
