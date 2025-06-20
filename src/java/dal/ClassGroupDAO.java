/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.ClassGroup;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author NGOC ANH
 */
public class ClassGroupDAO {

    public List<ClassGroup> getClassesByTeacher(int teacherId) {
        List<ClassGroup> list = new ArrayList<>();
        String sql = "select c.ClassGroupID, c.ClassGroupName, c.RoomID,c.ShiftID,c.TeacherID from\n"
                + "ClassGroup c join ScheduleTemplate s on c.TeacherID = s.TeacherID where c.TeacherID = ?";
        try (Connection conn = new DBContext().connection; 
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, teacherId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ClassGroup c = new ClassGroup();
                c.setClassGroupId(rs.getInt("ClassGroupID"));
                c.setName(rs.getString("ClassGroupName"));
                c.setRoomId(rs.getInt("RoomID"));
                c.setShiftId(rs.getInt("ShiftID")); 
                c.setTeachId(rs.getInt("TeacherID"));
                list.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
