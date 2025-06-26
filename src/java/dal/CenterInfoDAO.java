package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CenterInfoDAO {
    // Phương thức lấy thông tin trung tâm từ bảng CenterInfo
    public Map<String, String> getCenterInfo() {
        // Tạo Map để lưu thông tin: key là tên cột, value là giá trị
        Map<String, String> centerInfo = new HashMap<>();
        // Câu truy vấn SQL để lấy Logo, AddressCenter, Phone, Email
        String sql = "SELECT Logo, AddressCenter, Phone, Email FROM CenterInfo WHERE CenterID = 1";
        
        // Sử dụng try-with-resources để tự động đóng tài nguyên
        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            // Nếu có dữ liệu (luôn chỉ có 1 dòng vì CenterID = 1)
            if (rs.next()) {
                // Lấy giá trị từ ResultSet và thêm vào Map
                centerInfo.put("Logo", rs.getString("Logo")); // ví dụ: logo.jpg
                centerInfo.put("AddressCenter", rs.getString("AddressCenter")); // ví dụ: Quận Hai Bà Trưng, Hà Nội
                centerInfo.put("Phone", rs.getString("Phone")); // ví dụ: 0912345678
                centerInfo.put("Email", rs.getString("Email")); // ví dụ: edura@gmail.com
            }
        } catch (SQLException e) {
            // In lỗi để debug, không ném lỗi ra ngoài để tránh làm gián đoạn ứng dụng
            e.printStackTrace();
        }
        
        // Trả về Map, nếu không có dữ liệu thì Map rỗng
        return centerInfo;
    }
}