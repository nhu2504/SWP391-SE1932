package dal;

import entity.Shift;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Ngày tạo: 23/06/2025  
 * Người viết: Van Nhu
 */
public class ShiftLearnDAO {

    public List<Shift> getAllShifts() {
        List<Shift> list = new ArrayList<>();
        String sql = "SELECT ShiftID, Start_time, End_time FROM Shiftlearn";
        try (
                Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Shift s = new Shift();
                s.setId(rs.getInt("ShiftID"));

                // Đọc trực tiếp kiểu java.sql.Time từ ResultSet
                s.setStartTime(rs.getTime("Start_time"));
                s.setEndTime(rs.getTime("End_time"));

                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

//    public Map<Integer, String> getShiftDurationMap() {
//        Map<Integer, String> map = new HashMap<>();
//        List<Shift> shifts = getAllShifts(); // hoặc phương thức tương đương
//        for (Shift s : shifts) {
//            map.put(s.getId(), s.getDurationText());
//        }
//        return map;
//    }
}
