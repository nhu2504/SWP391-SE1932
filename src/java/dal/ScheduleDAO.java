/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.Attendance;
import entity.ScheduleJoin;
import entity.ScheduleStu;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Văn Thị Như / Ngoc Anh
 */
public class ScheduleDAO {
// ngọc anh
    public List<ScheduleJoin> getScheduleByUserID(int userId) {
        List<ScheduleJoin> schedules = new ArrayList<>();
        String sql = "SELECT s.DateLearn,\n"
                + "        tc.ClassGroupName,\n"
                + "        sh.Start_time, sh.End_time,\n"
                + "        r.roomName \n"
                + "               FROM Schedule s \n"
                + "                JOIN ClassGroup tc ON s.ClassGroupID = tc.TutoringClassID \n"
                + "                JOIN Shiftlearn sh ON s.ShiftID = sh.ShiftID \n"
                + "               JOIN Room r ON s.RoomID = r.id \n"
                + "                JOIN [User] u ON s.UserID = u.UserID \n"
                + "                WHERE s.UserID = ?";

        try {
            Connection conn = new DBContext().connection;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ScheduleJoin s = new ScheduleJoin();

                s.setDateLearn(rs.getDate("DateLearn"));
                s.setClassGroupName(rs.getString("ClassGroupName"));

                s.setStartTime(rs.getTime("Start_time"));
                s.setEndTime(rs.getTime("End_time"));
                s.setRoomName(rs.getString("roomName"));

                schedules.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return schedules;

    }
// như
    public List<Object[]> getTodaySchedules() {
        List<Object[]> list = new ArrayList<>();
        String sql = """
            SELECT cg.ClassGroupName, u.FullName AS TeacherName, r.roomName,
                   s.Start_time, s.End_time, sc.DateLearn
            FROM Schedule sc
            JOIN ClassGroup cg ON sc.ClassGroupID = cg.ClassGroupID
            JOIN [User] u ON sc.UserID = u.UserID
            JOIN Room r ON sc.RoomID = r.id
            JOIN Shiftlearn s ON sc.ShiftID = s.ShiftID
            WHERE sc.DateLearn = CAST(GETDATE() AS DATE)
            ORDER BY s.Start_time
        """;

        try (Connection conn = new DBContext().connection; PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Object[] row = new Object[6];
                row[0] = rs.getString("ClassGroupName");
                row[1] = rs.getString("TeacherName");
                row[2] = rs.getString("roomName");
                row[3] = rs.getTime("Start_time");
                row[4] = rs.getTime("End_time");
                row[5] = rs.getDate("DateLearn");
                list.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Trả về Map tương ứng với thứ trong tuần (1 -> Chủ nhật, 2 -> Thứ 2, ...)
     */
    public Map<Integer, String> getWeekdayMap() {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "Chủ nhật");
        map.put(2, "Thứ 2");
        map.put(3, "Thứ 3");
        map.put(4, "Thứ 4");
        map.put(5, "Thứ 5");
        map.put(6, "Thứ 6");
        map.put(7, "Thứ 7");
        return map;
    }

    /**
     * Lấy danh sách lịch học của một tuần cụ thể (dựa vào ngày bắt đầu tuần)
     *
     * @param weekStart ngày bắt đầu của tuần cần lấy lịch (java.sql.Date)
     * @return Danh sách Object[] chứa thông tin lịch học từng lớp trong tuần đó
     */
    public List<Object[]> getWeeklyScheduleByWeek(Date weekStart) {
        List<Object[]> list = new ArrayList<>();
        String sql = """
            SELECT 
                st.DayOfWeek,
                sl.ShiftID,
                cg.ClassGroupName,
                u.FullName,
                r.RoomName,
                sl.Start_time,
                sl.End_time
            FROM ScheduleTemplate st
            JOIN ClassGroup cg ON st.ClassGroupID = cg.ClassGroupID
            JOIN TutoringClass tc ON cg.TutoringClassID = tc.TutoringClassID
            JOIN [User] u ON st.TeacherID = u.UserID
            JOIN Room r ON st.RoomID = r.id
            JOIN Shiftlearn sl ON st.ShiftID = sl.ShiftID
            WHERE (tc.StartDate <= ? AND tc.EndDate >= ?)
            ORDER BY st.DayOfWeek, sl.ShiftID
        """;

        try (Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, new java.sql.Date(weekStart.getTime()));

            Date weekEnd = new Date(weekStart.getTime() + 6L * 24 * 60 * 60 * 1000);
            ps.setDate(2, new java.sql.Date(weekEnd.getTime()));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Object[]{
                        rs.getInt("DayOfWeek"),
                        rs.getInt("ShiftID"),
                        rs.getString("ClassGroupName"),
                        rs.getString("FullName"),
                        rs.getString("RoomName"),
                        rs.getTime("Start_time"),
                        rs.getTime("End_time")
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Lấy danh sách ngày đầu tuần (từ tuần hiện tại, 4 tuần trước, 5 tuần sau)
     *
     * @return Danh sách các ngày bắt đầu của tuần
     */
    public List<Date> getWeekStartList() {
        List<Date> weekStartList = new ArrayList<>();
        Calendar cal = Calendar.getInstance();

        // Đặt về đầu tuần hiện tại (Thứ 2)
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int diff = (dayOfWeek - Calendar.MONDAY + 7) % 7;
        cal.add(Calendar.DAY_OF_MONTH, -diff);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        // Lưu lại ngày đầu tuần hiện tại
        Date firstMonday = cal.getTime();

        for (int i = -4; i <= 5; i++) {
            Calendar temp = (Calendar) cal.clone();
            temp.add(Calendar.WEEK_OF_YEAR, i);
            weekStartList.add(temp.getTime()); // Trả về java.util.Date
        }

        return weekStartList;
    }

    /**
     * Lấy lịch học toàn bộ lớp đang mở – đầy đủ thông tin môn, lớp, khối
     *
     * @return Danh sách Object[] chứa chi tiết từng lịch học
     */
    public List<Object[]> getWeeklySchedule() {
        List<Object[]> list = new ArrayList<>();
        String sql = """
            SELECT 
                st.DayOfWeek,
                sl.ShiftID,
                cg.ClassGroupName,
                u.FullName,
                r.RoomName,
                sl.Start_time,
                sl.End_time,
                s.SubjectName,
                tc.ClassName,
                g.GradeName
            FROM ScheduleTemplate st
            JOIN ClassGroup cg ON st.ClassGroupID = cg.ClassGroupID
            JOIN TutoringClass tc ON cg.TutoringClassID = tc.TutoringClassID
            JOIN Grade g ON tc.GradeID = g.GradeID
            JOIN Subjects s ON tc.SubjectID = s.SubjectID
            JOIN [User] u ON st.TeacherID = u.UserID
            JOIN Room r ON st.RoomID = r.id
            JOIN Shiftlearn sl ON st.ShiftID = sl.ShiftID
            ORDER BY cg.ClassGroupName, st.DayOfWeek, sl.ShiftID
        """;

        try (Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Object[]{
                    rs.getInt("DayOfWeek"), // 0
                    rs.getInt("ShiftID"), // 1
                    rs.getString("ClassGroupName"), // 2
                    rs.getString("FullName"), // 3
                    rs.getString("RoomName"), // 4
                    rs.getTime("Start_time"), // 5
                    rs.getTime("End_time"), // 6
                    rs.getString("SubjectName"), // 7
                    rs.getString("ClassName"), // 8
                    rs.getString("GradeName") // 9
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
//ngocah

    public List<ScheduleJoin> getSchedulesByWeek(int userId, Date startOfWeek, Date endOfWeek) throws SQLException {
        List<ScheduleJoin> list = new ArrayList<>();
        String sql = "SELECT s.ScheduleID, s.DateLearn, sl.Start_time,sl.End_time ,"
                + "s.ClassGroupID, \n"
                + "                cg.TutoringClassID, cg.ClassGroupName, "
                + "tc.StartDate, tc.EndDate, \n"
                + "                u.FullName , r.roomName \n"
                + "                FROM Schedule s \n"
                + "                JOIN ClassGroup cg ON s.ClassGroupID = cg.ClassGroupID \n"
                + "                JOIN TutoringClass tc ON cg.TutoringClassID = tc.TutoringClassID \n"
                + "                JOIN [User] u ON s.UserID = u.UserID \n"
                + "				JOIN Shiftlearn sl ON s.ShiftID = sl.ShiftID\n"
                + "                JOIN Room r ON s.RoomID = r.id \n"
                + "                WHERE s.DateLearn BETWEEN ? AND ?  and s.UserID =?";
        try (Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(startOfWeek.getTime()));
            ps.setDate(2, new java.sql.Date(endOfWeek.getTime()));
            ps.setInt(3, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ScheduleJoin sch = new ScheduleJoin();
                sch.setScheId(rs.getInt("ScheduleID"));
                sch.setDateLearn(rs.getDate("DateLearn"));
                sch.setStartTime(rs.getTime("Start_time"));
                sch.setEndTime(rs.getTime("End_time"));
                sch.setClassGroupId(rs.getInt("ClassGroupID"));
                sch.setTutorId(rs.getInt("TutoringClassID"));
                sch.setClassGroupName(rs.getString("ClassGroupName"));
                sch.setStartDate(rs.getDate("StartDate"));
                sch.setEndDate(rs.getDate("EndDate"));
                sch.setTeacherName(rs.getString("FullName"));
                sch.setRoomName(rs.getString("roomName"));

                list.add(sch);
            }
        }
        return list;
    }
//ngocanh

    public ScheduleJoin getNextScheduleByTeacher(int userId) throws SQLException {
        ScheduleJoin schedule = null;
        String sql = "SELECT TOP 1\n"
                + "    s.ScheduleID,\n"
                + "    cg.ClassGroupName,\n"
                + "    sh.Start_time,\n"
                + "    sh.End_time,\n"
                + "    r.roomName,\n"
                + "    s.DateLearn\n"
                + "FROM \n"
                + "    Schedule s\n"
                + "    JOIN ClassGroup cg ON s.ClassGroupID = cg.ClassGroupID\n"
                + "    JOIN Shiftlearn sh ON s.ShiftID = sh.ShiftID\n"
                + "    JOIN Room r ON s.RoomID = r.id\n"
                + "WHERE \n"
                + "    s.UserID = ?\n"
                + "    AND (s.DateLearn > GETDATE() OR (s.DateLearn = CAST(GETDATE() AS DATE) AND sh.Start_time > CAST(GETDATE() AS TIME)))\n"
                + "ORDER BY\n"
                + "    s.DateLearn ASC, sh.Start_time ASC";
        try (Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    schedule = new ScheduleJoin();
                    schedule.setScheId(rs.getInt("ScheduleID"));
                    schedule.setClassGroupName(rs.getString("ClassGroupName"));
                    schedule.setStartTime(rs.getTime("Start_time"));
                    schedule.setEndTime(rs.getTime("End_time"));
                    schedule.setRoomName(rs.getString("roomName"));
                    schedule.setDateLearn(rs.getDate("DateLearn"));
                }
            }
        }
        return schedule;
    }

    //nam
    public List<ScheduleStu> getSchedulesByStudentAndWeek(int userID, Date startOfWeek, Date endOfWeek) throws SQLException {
        List<ScheduleStu> schedules = new ArrayList<>();
        String sql = "SELECT s.ScheduleID, s.ClassGroupID, s.ShiftID, s.RoomID, s.DateLearn, s.UserID AS TeacherID, "
                + "sub.SubjectName, r.roomName "
                + "FROM Schedule s "
                + "JOIN ClassGroup cg ON s.ClassGroupID = cg.ClassGroupID "
                + "JOIN TutoringClass tc ON cg.TutoringClassID = tc.TutoringClassID "
                + "JOIN Subjects sub ON tc.SubjectID = sub.SubjectID "
                + "JOIN Room r ON s.RoomID = r.id "
                + "JOIN ClassGroup_Student cs ON cg.ClassGroupID = cs.ClassGroupID "
                + "WHERE cs.StudentID = ? AND s.DateLearn BETWEEN ? AND ? AND cs.IsActive = 1 "
                + "ORDER BY s.DateLearn, s.ShiftID";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = new DBContext().connection;
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userID);
            stmt.setDate(2, new java.sql.Date(startOfWeek.getTime()));
            stmt.setDate(3, new java.sql.Date(endOfWeek.getTime()));
            rs = stmt.executeQuery();
            while (rs.next()) {
                ScheduleStu schedule = new ScheduleStu();
                schedule.setScheduleID(rs.getInt("ScheduleID"));
                schedule.setClassGroupID(rs.getInt("ClassGroupID"));
                schedule.setShiftID(rs.getInt("ShiftID"));
                schedule.setRoomID(rs.getInt("RoomID"));
                schedule.setDateLearn(rs.getDate("DateLearn"));
                schedule.setTeacherID(rs.getInt("TeacherID"));
                schedule.setSubjectName(rs.getString("SubjectName"));
                schedule.setRoomName(rs.getString("roomName"));
                schedules.add(schedule);
            }
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return schedules;
    }
//nam

    public List<Attendance> getAttendancesByStudentAndWeek(int userID, Date startOfWeek, Date endOfWeek) throws SQLException {
        List<Attendance> attendances = new ArrayList<>();
        String sql = "SELECT ClassGroupID, UserID, AttendanceDate, IsPresent "
                + "FROM Attendance "
                + "WHERE UserID = ? AND AttendanceDate BETWEEN ? AND ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = new DBContext().connection;
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userID);
            stmt.setDate(2, new java.sql.Date(startOfWeek.getTime()));
            stmt.setDate(3, new java.sql.Date(endOfWeek.getTime()));
            rs = stmt.executeQuery();
            while (rs.next()) {
                Attendance attendance = new Attendance();
                attendance.setClassGroup(rs.getInt("ClassGroupID"));
                attendance.setUserID(rs.getInt("UserID"));
                attendance.setAttendanceDate(rs.getDate("AttendanceDate"));
                attendance.setIsPresent(rs.getBoolean("IsPresent"));
                attendances.add(attendance);
            }
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return attendances;
    }

    public static void main(String[] args) {
//        ScheduleDAO dao = new ScheduleDAO();

//
//        System.out.println("\n=== Lịch học kế tiếp của giáo viên có ID " + testUserId + " ===");
//        try {
//            ScheduleJoin nextSchedule = dao.getNextScheduleByTeacher(testUserId);
//            if (nextSchedule != null) {
//                System.out.println(nextSchedule);
//            } else {
//                System.out.println("Không có lịch học kế tiếp.");
//            }
//        } catch (SQLException e) {
//            System.out.println("Lỗi khi lấy lịch học kế tiếp:");
//            e.printStackTrace();
//        }
//    }
        //        List<Schedule> schedules = dao.getScheduleByUserID(testUserId);
//
//        if (schedules != null && !schedules.isEmpty()) {
//            for (Schedule s : schedules) {
//                System.out.println(s);
//            }
//        } else {
//            System.out.println("Not fount: " + testUserId);
//        }
        // Giả sử bạn đã tạo DAO tên là ScheduleDAO
//        ScheduleDAO dao = new ScheduleDAO();
//
//        int userId = 2; // Thay bằng ID hợp lệ trong database
//        List<ScheduleJoin> schedules = dao.getScheduleByUserID(userId);
//
//        if (schedules.isEmpty()) {
//            System.out.println("Không tìm thấy lịch học cho user ID: " + userId);
//        } else {
//            for (ScheduleJoin s : schedules) {
//                System.out.println("Schedule ID: " + s.getScheId());
//                System.out.println("Class Group ID: " + s.getClassGroupName());
//                 System.out.println("Shift start : " + s.getStart_time());
//                System.out.println("Shift end: " + s.getEnd_time());
//                System.out.println("Room ID: " + s.getRoomName());
//                System.out.println("Date Learn: " + s.getDateLearn());
//
//                System.out.println("---------------------------");
//            }
//        }
        int testUserId = 4;
        try {
            // Khởi tạo DAO
            ScheduleDAO dao = new ScheduleDAO();

            // Thiết lập ngày
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            Date startOfWeek = cal.getTime();

            cal.add(Calendar.DATE, 6); // +6 ngày sẽ là Chủ nhật cùng tuần
            Date endOfWeek = cal.getTime();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println("Tìm lịch từ " + sdf.format(startOfWeek) + " đến " + sdf.format(endOfWeek));

            List<ScheduleJoin> schedules = dao.getSchedulesByWeek(testUserId, startOfWeek, endOfWeek);
            System.out.println("Số lịch tìm thấy: " + schedules.size());

            for (ScheduleJoin sch : schedules) {
                System.out.println("Schedule ID: " + sch.getScheId());
                System.out.println("Date Learn: " + sdf.format(sch.getDateLearn()));
                System.out.println("Shift ID: " + sch.getStartTime());
                System.out.println("Room: " + sch.getRoomName());
                System.out.println("Teacher: " + sch.getTeacherName());
                System.out.println("Class Group: " + sch.getClassGroupName());
                System.out.println("-----------------------------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
