/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.ScheduleJoin;
import entity.Schedule;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Văn Thị Như / Ngoc Anh
 */
public class ScheduleDAO {

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

    public List<ScheduleJoin> getSchedulesByWeek(int userId,Date startOfWeek, Date endOfWeek) throws SQLException {
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

            List<ScheduleJoin> schedules = dao.getSchedulesByWeek(testUserId,startOfWeek, endOfWeek);
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
