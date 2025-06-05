package dal;

import dal.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CenterInfoDAO {
    // Lấy thông tin trung tâm theo CenterID
    public Map<String, String> getCenterInfo(int centerId) {
        try (Connection conn = new DBContext().connection; // Kết nối tới database
             PreparedStatement ps = conn.prepareStatement(
                     // Câu SQL lấy thông tin trung tâm
                     "SELECT NameCenter, AddressCenter, Email, Phone, DescripCenter FROM CenterInfo WHERE CenterID = ?")) {
            ps.setInt(1, centerId); // Gán CenterID vào câu SQL
            ResultSet rs = ps.executeQuery(); // Thực thi câu SQL
            if (rs.next()) { // Nếu có dữ liệu
                // Lưu thông tin vào một Map
                Map<String, String> centerInfo = new HashMap<>();
                centerInfo.put("NameCenter", rs.getString("NameCenter"));
                centerInfo.put("AddressCenter", rs.getString("AddressCenter"));
                centerInfo.put("Email", rs.getString("Email"));
                centerInfo.put("Phone", rs.getString("Phone"));
                centerInfo.put("DescripCenter", rs.getString("DescripCenter"));
                return centerInfo; // Trả về Map chứa thông tin
            }
        } catch (SQLException e) {
            // Nếu có lỗi SQL, in lỗi ra console
            e.printStackTrace();
        }
        return null; // Trả về null nếu không tìm thấy hoặc có lỗi
    }
}