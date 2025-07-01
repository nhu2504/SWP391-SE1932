//package dal;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import dal.DBContext;
//
///**
// * Lớp ManagerDAO dùng để lấy dữ liệu từ cơ sở dữ liệu cho trang dashboard của quản lý.
// * Lớp này sử dụng DBContext để kết nối với cơ sở dữ liệu SQL Server.
// */
//public class ManagerDAO {
//    private Connection connection; // Biến để lưu kết nối đến cơ sở dữ liệu
//
//    /**
//     * Constructor khởi tạo kết nối với cơ sở dữ liệu bằng DBContext.
//     */
//    public ManagerDAO() {
//       try {
//            this.connection = DBContext.getInstance().getConnection(); // ✅ dùng DBContext singleton
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("Kết nối cơ sở dữ liệu thất bại!");
//        }
//    }
//
//    /**
//     * Đếm số học sinh trong hệ thống (có roleID = 3).
//     * @return Số lượng học sinh.
//     */
//    public int getStudentCount() {
//        String sql = "SELECT COUNT(*) FROM [User] WHERE roleID = 3";
//        try (PreparedStatement ps = connection.prepareStatement(sql);
//             ResultSet rs = ps.executeQuery()) {
//            if (rs.next()) {
//                return rs.getInt(1);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }
//
//    /**
//     * Đếm số giáo viên trong hệ thống (có roleID = 2).
//     * @return Số lượng giáo viên.
//     */
//    public int getTeacherCount() {
//        String sql = "SELECT COUNT(*) FROM [User] WHERE roleID = 2";
//        try (PreparedStatement ps = connection.prepareStatement(sql);
//             ResultSet rs = ps.executeQuery()) {
//            if (rs.next()) {
//                return rs.getInt(1);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }
//
//    /**
//     * Đếm số lớp học trong hệ thống.
//     * @return Số lượng lớp học.
//     */
//    public int getClassCount() {
//        String sql = "SELECT COUNT(*) FROM ClassGroup";
//        try (PreparedStatement ps = connection.prepareStatement(sql);
//             ResultSet rs = ps.executeQuery()) {
//            if (rs.next()) {
//                return rs.getInt(1);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }
//
//    /**
//     * Đếm số lịch học trong ngày hiện tại.
//     * @return Số lượng lịch học hôm nay.
//     */
//    public int getTodayScheduleCount() {
//        String sql = "SELECT COUNT(*) FROM Schedule WHERE CAST(DateLearn AS DATE) = ?";
//        try (PreparedStatement ps = connection.prepareStatement(sql)) {
//            // Sử dụng ngày hiện tại thay vì ngày cố định
//            ps.setDate(1, new java.sql.Date(System.currentTimeMillis()));
//            try (ResultSet rs = ps.executeQuery()) {
//                if (rs.next()) {
//                    return rs.getInt(1);
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }
//
//    /**
//     * Đếm số đơn đăng ký đang chờ duyệt.
//     * @return Số lượng đơn chờ duyệt.
//     */
//    public int getPendingRequestCount() {
//        String sql = "SELECT COUNT(*) FROM TutoringRegistrationPending WHERE ApprovalStatus = 'Pending'";
//        try (PreparedStatement ps = connection.prepareStatement(sql);
//             ResultSet rs = ps.executeQuery()) {
//            if (rs.next()) {
//                return rs.getInt(1);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }
//
//    /**
//     * Lấy dữ liệu lịch học theo tuần để hiển thị trên bảng phân bố lịch học.
//     * @param weekRange Chuỗi định dạng "YYYY-MM-DD - YYYY-MM-DD".
//     * @return Danh sách các Map, mỗi Map chứa ngày và số lịch học cho từng ca.
//     */
//    public List<Map<String, Object>> getScheduleByWeek(String weekRange) {
//        List<Map<String, Object>> scheduleData = new ArrayList<>();
//        String[] dates = weekRange.split(" - ");
//        String startDate = dates[0];
//        String endDate = dates[1];
//
//        String[] days = {"Chủ Nhật", "Thứ Hai", "Thứ Ba", "Thứ Tư", "Thứ Năm", "Thứ Sáu", "Thứ Bảy"};
//        String[] shifts = {"1", "2", "3", "4", "5", "6"};
//
//        for (int i = 0; i < days.length; i++) {
//            Map<String, Object> row = new HashMap<>();
//            row.put("day", days[i]);
//            List<Integer> slots = new ArrayList<>();
//
//            for (String shift : shifts) {
//                String sql = "SELECT COUNT(*) FROM Schedule s " +
//                             "WHERE s.DateLearn BETWEEN ? AND ? AND s.ShiftID = ? AND DATEPART(WEEKDAY, s.DateLearn) = ?";
//                try (PreparedStatement ps = connection.prepareStatement(sql)) {
//                    ps.setDate(1, java.sql.Date.valueOf(startDate));
//                    ps.setDate(2, java.sql.Date.valueOf(endDate));
//                    ps.setInt(3, Integer.parseInt(shift));
//                    ps.setInt(4, i + 1);
//                    try (ResultSet rs = ps.executeQuery()) {
//                        slots.add(rs.next() ? rs.getInt(1) : 0);
//                    }
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                    slots.add(0);
//                }
//            }
//            row.put("slots", slots);
//            scheduleData.add(row);
//        }
//        return scheduleData;
//    }
//
//    /**
//     * Lấy số lượng lớp học theo môn học, lọc theo khối lớp.
//     * @param grade Khối lớp ("all", "10", "11", hoặc "12").
//     * @return Danh sách số lớp học cho 8 môn: Toán, Lý, Hóa, Văn, Anh, Sinh, Sử, Địa.
//     */
//    public List<Integer> getClassCountBySubject(String grade) {
//        List<Integer> data = new ArrayList<>();
//        String[] subjects = {"Toán", "Lý", "Hóa", "Văn", "Anh", "Sinh", "Sử", "Địa"};
//        String sql = "SELECT COUNT(*) FROM ClassGroup cg " +
//                     "JOIN TutoringClass tc ON cg.TutoringClassID = tc.TutoringClassID " +
//                     "WHERE tc.SubjectName = ?" +
//                     (grade.equals("all") ? "" : " AND tc.GradeID = ?");
//
//        for (String subject : subjects) {
//            try (PreparedStatement ps = connection.prepareStatement(sql)) {
//                ps.setString(1, subject);
//                if (!grade.equals("all")) {
//                    int gradeID = switch (grade) {
//                        case "10" -> 1;
//                        case "11" -> 2;
//                        case "12" -> 3;
//                        default -> 0;
//                    };
//                    ps.setInt(2, gradeID);
//                }
//                try (ResultSet rs = ps.executeQuery()) {
//                    data.add(rs.next() ? rs.getInt(1) : 0);
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//                data.add(0);
//            }
//        }
//        return data;
//    }
//}