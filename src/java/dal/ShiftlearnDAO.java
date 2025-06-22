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
    //lấy ra tất cả ca học từ bảng shiftlearn 
    public ArrayList<Shift> getAllShifts() {
        //tạo array list chứa các ca học được lấy ra
        ArrayList<Shift> shifts = new ArrayList<>();
        //lấy ra toàn bộ dữ liêuj từ bảng shiftlearn
        String sql = "SELECT * FROM Shiftlearn";
        //kết nối db 
        try (Connection conn = new DBContext().connection; 
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            //thực thi câu truy vấn kết quả trả về được chứa trong result set
            ResultSet rs = stmt.executeQuery();
             //lặp qua từng dòng, gán dữ liệu từ db vào đối tượng, thêm đối tượng vào danh sách
            while (rs.next()) {
                Shift s = new Shift();
                s.setId(rs.getInt("ShiftID"));
                s.setStartTime(rs.getTime("Start_time"));
                s.setEndTime(rs.getTime("End_time"));
                shifts.add(s);
            }
                   } catch (SQLException e) {
            System.out.println("Lỗi"+e.getMessage());
        }
        return shifts;
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
