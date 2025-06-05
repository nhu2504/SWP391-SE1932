package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entity.Shift;
import java.util.List;

public class ShiftlearnDAO {

    public ArrayList<Shift> getAllShifts() {
        ArrayList<Shift> shifts = new ArrayList<>();
        String sql = "SELECT * FROM Shiftlearn";
        try (Connection conn = new DBContext().connection; 
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Shift s = new Shift();
                s.setId(rs.getInt("ShiftID"));
                s.setStartTime(rs.getString("Start_time"));
                s.setEndTime(rs.getString("End_time"));
                shifts.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shifts;
    }

    public Shift getShiftByID(int id) {
        String query = "select * from Shiftlearn\n"
                + "  where ShiftID=?";
        try {
            Connection conn = new DBContext().connection;
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Shift(rs.getInt("ShiftID"), 
                        rs.getString("Start_time"), 
                        rs.getString("End_time"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void main(String[] args) {
        ShiftlearnDAO r = new ShiftlearnDAO();
        List<Shift> rolesList = r.getAllShifts();
for (Shift role : rolesList) {
    System.out.println(role.toString());
}
       
//        int roleID = 2; 
//    roles role = r.getRoleByID(roleID);
//    if (role != null) {
//        System.out.println("Sản phẩm tìm thấy: " + role.toString());
//    } else {
//        System.out.println("Không tìm thấy sản phẩm với ID: " + roleID);
//    }
    }
}
