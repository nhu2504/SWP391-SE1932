/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.Room;
import entity.Schedule;
import entity.Shift;
import entity.TutoringClass;
import entity.User;
import entity.Roles;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Văn Thị Như
 */
public class ScheduleDAO {

    public List<Schedule> getScheduleByUserID(int userId) {
        List<Schedule> schedules = new ArrayList<>();
        String sql = "SELECT s.ScheduleID, s.DateLearn, \n"
                + "       tc.TutoringClassID, tc.ClassName, \n"
                + "       sh.ShiftID, sh.Start_time, sh.End_time, \n"
                + "       r.id AS RoomID, r.name AS RoomName, \n"
                + "       u.UserID, u.FullName, u.Email, u.RoleID \n"
                + "FROM Schedule s \n"
                + "JOIN TutoringClass tc ON s.TutoringClassID = tc.TutoringClassID \n"
                + "JOIN Shiftlearn sh ON s.ShiftID = sh.ShiftID \n"
                + "JOIN Room r ON s.RoomID = r.id \n"
                + "JOIN [User] u ON s.UserID = u.UserID \n"
                + "WHERE s.UserID = ?";

        try {
            Connection conn = new DBContext().connection;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Schedule s = new Schedule();

                s.setScheID(rs.getInt("ScheduleID"));
                s.setClassgroupID(rs.getInt("TutoringClassID"));
                
                s.setShiftId(rs.getInt("ShiftID"));
                
                s.setRoomId(rs.getInt("RoomID"));
                s.setDateLearn(rs.getDate("DateLearn"));
               s.setUserId(rs.getInt("UserID"));
                schedules.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return schedules;

    }

    public static void main(String[] args) {
        ScheduleDAO dao = new ScheduleDAO();
        int testUserId = 3;

        List<Schedule> schedules = dao.getScheduleByUserID(testUserId);

        if (schedules != null && !schedules.isEmpty()) {
            for (Schedule s : schedules) {
                System.out.println(s);  
            }
        } else {
            System.out.println("Not fount: " + testUserId);
        }
    }

}
