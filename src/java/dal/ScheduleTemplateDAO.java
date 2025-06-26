///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package dal;
//
//import entity.Room;
//import entity.ScheduleTemplate;
//import entity.Shift;
//import entity.TutoringClass;
//import entity.User;
//import java.lang.System.Logger;
//import java.lang.System.Logger.Level;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.util.ArrayList;
//import java.util.List;
//import java.sql.*;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//
///**
// *
// * @author DO NGOC ANH HE180661
// * Văn Thị Như
// */
//public class ScheduleTemplateDAO {
//    
//    public List<ScheduleTemplate> getAllScheduleTemplate() {
//        List<ScheduleTemplate> list = new ArrayList<>();
//        String sql = "SELECT * FROM ScheduleTemplate";
//
//        try (Connection conn = new DBContext().connection; 
//                PreparedStatement ps = conn.prepareStatement(sql)) {
//                ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                ScheduleTemplate st = new ScheduleTemplate();
//                st.setTempID(rs.getInt("TemplateID"));
//               st.setClassgroupID(rs.getInt("TutoringClassID"));
//                st.setDayOfWeek(rs.getInt("DayOfWeek"));
//                st.setShiftId(rs.getInt("ShiftID"));
//                st.setRoomId(rs.getInt("RoomID"));
//                st.setUserId(rs.getInt("TeacherID"));
//
//                list.add(st);
//            }
//
//        } catch (SQLException e) {
//            System.out.println("Lỗi "+e.getMessage());
//        }
//        return list;
//    }
//     
//
//    /**
//     * Tính thời lượng ca học dựa trên thời gian bắt đầu và kết thúc.
//     * @param startTime Thời gian bắt đầu (java.sql.Time).
//     * @param endTime Thời gian kết thúc (java.sql.Time).
//     * @return Thời lượng ca học dưới dạng chuỗi (VD: "2 giờ 30 phút").
//     */
//    private String calculateDuration(Time startTime, Time endTime) {
//        if (startTime != null && endTime != null) {
//            try {
//                long diffInMillis = endTime.getTime() - startTime.getTime();
//                long hours = TimeUnit.MILLISECONDS.toHours(diffInMillis);
//                long minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis) % 60;
//                String duration = (hours > 0 ? hours + " giờ " : "") + (minutes > 0 ? minutes + " phút" : "");
//                return duration.isEmpty() ? "0 phút" : duration;
//            } catch (Exception e) {
//                
//                return "Lỗi định dạng thời gian";
//            }
//        }
//        return "Chưa xác định";
//    }
//
//    /**
//     * Lấy danh sách lịch học mẫu theo ClassGroupID.
//     * @param classGroupID ID của nhóm lớp.
//     * @return Danh sách Map chứa thông tin lịch học mẫu và thông tin bổ sung.
//     */
//    public List<Map<String, Object>> getScheduleTemplatesByClassGroup(int classGroupID) {
//        List<Map<String, Object>> scheduleTemplates = new ArrayList<>();
//        String sql = "SELECT st.TemplateID, st.ClassGroupID, st.DayOfWeek, st.ShiftID, " +
//                     "sl.Start_time, sl.End_time, st.RoomID, rm.name AS RoomName, " +
//                     "st.TeacherID, u.FullName AS TeacherName " +
//                     "FROM ScheduleTemplate st " +
//                     "LEFT JOIN Shiftlearn sl ON st.ShiftID = sl.ShiftID " +
//                     "LEFT JOIN Room rm ON st.RoomID = rm.id " +
//                     "LEFT JOIN [User] u ON st.TeacherID = u.UserID " +
//                     "WHERE st.ClassGroupID = ?";
//
//        try (Connection conn = new DBContext().connection;
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setInt(1, classGroupID);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                Map<String, Object> templateDetail = new HashMap<>();
//                Time startTime = rs.getTime("Start_time");
//                Time endTime = rs.getTime("End_time");
//                String duration = calculateDuration(startTime, endTime);
//
//                ScheduleTemplate template = new ScheduleTemplate(
//                    rs.getInt("TemplateID"),
//                    rs.getInt("ClassGroupID"),
//                    rs.getInt("DayOfWeek"),
//                    rs.getInt("ShiftID"),
//                    rs.getInt("RoomID"),
//                    rs.getInt("TeacherID")
//                );
//
//                templateDetail.put("scheduleTemplate", template);
//                templateDetail.put("roomName", rs.getString("RoomName") != null ? rs.getString("RoomName") : "Không xác định");
//                templateDetail.put("teacherName", rs.getString("TeacherName") != null ? rs.getString("TeacherName") : "Không xác định");
//                templateDetail.put("startTime", startTime != null ? startTime.toString() : "");
//                templateDetail.put("endTime", endTime != null ? endTime.toString() : "");
//                templateDetail.put("duration", duration);
//                scheduleTemplates.add(templateDetail);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return scheduleTemplates;
//    }
//
//    /**
//     * Lấy một lịch học mẫu theo TemplateID.
//     * @param templateID ID của lịch học mẫu.
//     * @return Map chứa thông tin lịch học mẫu và thông tin bổ sung.
//     */
//    public Map<String, Object> getScheduleTemplateById(int templateID) {
//        Map<String, Object> templateDetail = new HashMap<>();
//        String sql = "SELECT st.TemplateID, st.ClassGroupID, st.DayOfWeek, st.ShiftID, " +
//                     "sl.Start_time, sl.End_time, st.RoomID, rm.name AS RoomName, " +
//                     "st.TeacherID, u.FullName AS TeacherName " +
//                     "FROM ScheduleTemplate st " +
//                     "LEFT JOIN Shiftlearn sl ON st.ShiftID = sl.ShiftID " +
//                     "LEFT JOIN Room rm ON st.RoomID = rm.id " +
//                     "LEFT JOIN [User] u ON st.TeacherID = u.UserID " +
//                     "WHERE st.TemplateID = ?";
//
//        try (Connection conn = new DBContext().connection;
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setInt(1, templateID);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                Time startTime = rs.getTime("Start_time");
//                Time endTime = rs.getTime("End_time");
//                String duration = calculateDuration(startTime, endTime);
//
//                ScheduleTemplate template = new ScheduleTemplate(
//                    rs.getInt("TemplateID"),
//                    rs.getInt("ClassGroupID"),
//                    rs.getInt("DayOfWeek"),
//                    rs.getInt("ShiftID"),
//                    rs.getInt("RoomID"),
//                    rs.getInt("TeacherID")
//                );
//
//                templateDetail.put("scheduleTemplate", template);
//                templateDetail.put("roomName", rs.getString("RoomName") != null ? rs.getString("RoomName") : "Không xác định");
//                templateDetail.put("teacherName", rs.getString("TeacherName") != null ? rs.getString("TeacherName") : "Không xác định");
//                templateDetail.put("startTime", startTime != null ? startTime.toString() : "");
//                templateDetail.put("endTime", endTime != null ? endTime.toString() : "");
//                templateDetail.put("duration", duration);
//            } else {
//                
//                return null;
//                
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//        return templateDetail;
//    }
//
//    /**
//     * Thêm mới một lịch học mẫu.
//     * @param template Đối tượng ScheduleTemplate.
//     * @return True nếu thêm thành công, False nếu thất bại.
//     */
//    public boolean addScheduleTemplate(ScheduleTemplate template) {
//        String sql = "INSERT INTO ScheduleTemplate (ClassGroupID, DayOfWeek, ShiftID, RoomID, TeacherID) " +
//                     "VALUES (?, ?, ?, ?, ?)";
//
//        try (Connection conn = new DBContext().connection;
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setInt(1, template.getClassgroupID());
//            ps.setInt(2, template.getDayOfWeek());
//            ps.setInt(3, template.getShiftId());
//            ps.setInt(4, template.getRoomId());
//            ps.setInt(5, template.getUserId());
//            int rowsAffected = ps.executeUpdate();
//            return rowsAffected > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * Cập nhật một lịch học mẫu.
//     * @param template Đối tượng ScheduleTemplate.
//     * @return True nếu cập nhật thành công, False nếu thất bại.
//     */
//    public boolean updateScheduleTemplate(ScheduleTemplate template) {
//        String sql = "UPDATE ScheduleTemplate SET ClassGroupID = ?, DayOfWeek = ?, ShiftID = ?, " +
//                     "RoomID = ?, TeacherID = ? WHERE TemplateID = ?";
//
//        try (Connection conn = new DBContext().connection;
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setInt(1, template.getClassgroupID());
//            ps.setInt(2, template.getDayOfWeek());
//            ps.setInt(3, template.getShiftId());
//            ps.setInt(4, template.getRoomId());
//            ps.setInt(5, template.getUserId());
//            ps.setInt(6, template.getTempID());
//            int rowsAffected = ps.executeUpdate();
//            return rowsAffected > 0;
//        } catch (SQLException e) {
//           e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * Xóa một lịch học mẫu theo TemplateID.
//     * @param templateID ID của lịch học mẫu.
//     * @return True nếu xóa thành công, False nếu thất bại.
//     */
//    public boolean deleteScheduleTemplate(int templateID) {
//        String sql = "DELETE FROM ScheduleTemplate WHERE TemplateID = ?";
//
//        try (Connection conn = new DBContext().connection;
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setInt(1, templateID);
//            int rowsAffected = ps.executeUpdate();
//            return rowsAffected > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//
////    public void insert(ScheduleTemplate st) {
////        String sql = "INSERT INTO ScheduleTemplate (TutoringClassID, DayOfWeek, ShiftID, RoomID, TeacherID) VALUES (?, ?, ?, ?, ?)";
////        try (Connection conn = new DBContext().connection;
////             PreparedStatement ps = conn.prepareStatement(sql)) {
////
////            ps.setInt(1, st.getTutor().getTutoringClassID());
////            ps.setInt(2, st.getDayOfWeek());
////            ps.setInt(3, st.getShift().getId());
////            ps.setInt(4, st.getRoom().getId());
////            ps.setInt(5, st.getUser().getId());
////
////            ps.executeUpdate();
////
////        } catch (SQLException e) {
////            e.printStackTrace();
////        }
////    }
//}
