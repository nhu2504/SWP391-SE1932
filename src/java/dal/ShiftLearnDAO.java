package dal;

import entity.Shift;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Ngày update: 30/06/2025  
 * Người viết: Văn Thị Như
 * 
 * Mô tả: DAO dùng để thao tác với bảng ca học (Shiftlearn), như lấy danh sách các ca học và tạo bản đồ thời gian học theo ca.
 */
public class ShiftLearnDAO {

    /**
     *  Lấy danh sách tất cả các ca học từ bảng Shiftlearn.
     *
     * @return List<Shift> danh sách các ca học với ID, thời gian bắt đầu và kết thúc
     */
    public List<Shift> getAllShifts() {
        List<Shift> list = new ArrayList<>();
        String sql = "SELECT ShiftID, Start_time, End_time FROM Shiftlearn";

        try (
            Connection conn = new DBContext().connection;
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Shift s = new Shift();
                s.setId(rs.getInt("ShiftID"));

                // Đọc dữ liệu thời gian bắt đầu và kết thúc dưới dạng java.sql.Time
                s.setStartTime(rs.getTime("Start_time"));
                s.setEndTime(rs.getTime("End_time"));

                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     *  Tạo một bản đồ (Map) từ ShiftID -> chuỗi thời gian (VD: "07:00 - 08:30")
     * 
     * Dùng để hiển thị thời gian ca học một cách dễ đọc trên giao diện.
     *
     * @return Map<Integer, String> ánh xạ ID ca học sang chuỗi thời gian tương ứng
     */
    public Map<Integer, String> getShiftDurationMap() {
        Map<Integer, String> map = new HashMap<>();
        List<Shift> shifts = getAllShifts(); // Lấy toàn bộ danh sách ca học

        for (Shift s : shifts) {
            map.put(s.getId(), s.getDurationText()); // durationText = "HH:mm - HH:mm"
        }
        return map;
    }
    
    // Ngọc Anh
    //lấy ra 1 ca học theo id
    public Shift getShiftByID(int id) {
        //truy vấn lấy ra ca học theo id
        String query = """
                       select * from Shiftlearn
                         where ShiftID=?""";
        //tạo kết nối, gán tham số id vào vị trí ?
        try {
            Connection conn = new DBContext().connection;
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            //nếu có kết quả thì trả về đối tượng shift đã khởi tạo bằng constructor có 3 tham số
            if (rs.next()) {
                return new Shift(rs.getInt("ShiftID"), 
                        rs.getTime("Start_time"), 
                        rs.getTime("End_time"));
            }
          
        } catch (SQLException e) {
             System.out.println("Lỗi"+e.getMessage());
        }
        return null;
    }
}
