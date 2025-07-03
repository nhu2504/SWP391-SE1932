package dal;

import java.util.ArrayList;
import java.util.List;
import dal.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.Map;

/**
 * Ngày update: 30/06/2025  
 * Người viết: Văn Thị Như
 * 
 * Mô tả: Lớp DAO dùng để thao tác với dữ liệu liên quan đến lịch học (Schedule).
 */
public class ScheduleDAO {

    /**
     *  Lấy danh sách lịch học của ngày hiện tại (today).
     * 
     * @return Danh sách các lịch học (Object[]) trong ngày hiện tại
     */
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

        try (Connection conn = new DBContext().connection;
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

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
     *  Trả về Map tương ứng với thứ trong tuần (1 -> Chủ nhật, 2 -> Thứ 2, ...)
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
     *  Lấy danh sách lịch học của một tuần cụ thể (dựa vào ngày bắt đầu tuần)
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

        try (Connection conn = new DBContext().connection;
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, weekStart); // tc.StartDate <= weekStart
            Date weekEnd = new Date(weekStart.getTime() + 6L * 24 * 60 * 60 * 1000); // weekEnd = +6 ngày
            ps.setDate(2, weekEnd);   // tc.EndDate >= weekEnd

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
     *  Lấy danh sách ngày đầu tuần (từ tuần hiện tại, 4 tuần trước, 5 tuần sau)
     * 
     * @return Danh sách các ngày bắt đầu của tuần
     */
    public List<Date> getWeekStartList() {
        List<Date> weekStartList = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate firstMonday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        for (int i = -4; i <= 5; i++) {
            LocalDate weekStart = firstMonday.plusWeeks(i);
            weekStartList.add(Date.valueOf(weekStart));
        }

        return weekStartList;
    }

    /**
     *  Lấy lịch học toàn bộ lớp đang mở – đầy đủ thông tin môn, lớp, khối
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

        try (Connection conn = new DBContext().connection;
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Object[]{
                    rs.getInt("DayOfWeek"),          // 0
                    rs.getInt("ShiftID"),            // 1
                    rs.getString("ClassGroupName"),  // 2
                    rs.getString("FullName"),        // 3
                    rs.getString("RoomName"),        // 4
                    rs.getTime("Start_time"),        // 5
                    rs.getTime("End_time"),          // 6
                    rs.getString("SubjectName"),     // 7
                    rs.getString("ClassName"),       // 8
                    rs.getString("GradeName")        // 9
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
