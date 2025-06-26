package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entity.Shift;
import java.util.List;

/**
 *
 * @author DO NGOC ANH HE180661
 * 
 */

public class ShiftlearnDAO {
    public List<Shift> getAllShifts() {
        List<Shift> list = new ArrayList<>();
        String sql = "SELECT * FROM Shiftlearn";
        try (
                Connection conn = new DBContext().connection; 
                PreparedStatement ps = conn.prepareStatement(sql); 
                ResultSet rs = ps.executeQuery()) {
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
    
    //test xem đã lấy được dữ liệu từ db chưa
    public static void main(String[] args) {
        ShiftlearnDAO r = new ShiftlearnDAO();
        List<Shift> rolesList = r.getAllShifts();
for (Shift role : rolesList) {
    System.out.println(role.toString());
}

    }
}
