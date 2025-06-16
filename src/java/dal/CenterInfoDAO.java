package dal;

import dal.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
//Văn Thị NHư - HE181329
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
    // Cập nhật một trường của trung tâm
    public boolean updateField(int centerId, String fieldName, String fieldValue) {
        String dbColumn = switch (fieldName) {
            case "centerName" -> "NameCenter";
            case "address" -> "AddressCenter";
            case "email" -> "Email";
            case "phone" -> "Phone";
            case "descripCenter" -> "DescripCenter";
            default -> null;
        };

        if (dbColumn == null) {
            return false;
        }

        String sql = "UPDATE CenterInfo SET " + dbColumn + " = ? WHERE CenterID = ?";
        try (Connection conn = new DBContext().connection;
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fieldValue != null && fieldValue.trim().isEmpty() ? null : fieldValue);
            ps.setInt(2, centerId);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa một trường của trung tâm (đặt thành NULL)
    public boolean deleteField(int centerId, String fieldName) {
        String dbColumn = switch (fieldName) {
            case "centerName" -> "NameCenter";
            case "address" -> "AddressCenter";
            case "email" -> "Email";
            case "phone" -> "Phone";
            case "descripCenter" -> "DescripCenter";
            case "logo" -> "Logo";
            default -> null;
        };

        if (dbColumn == null) {
            return false;
        }

        String sql = "UPDATE CenterInfo SET " + dbColumn + " = NULL WHERE CenterID = ?";
        try (Connection conn = new DBContext().connection;
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, centerId);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public int getYearOfWork() {
        String sql = "SELECT YearOfWork FROM CenterInfo WHERE CenterID = 1"; // Giả định CenterID = 1
        try (Connection conn = new DBContext().connection;
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("YearOfWork");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 2000; // Giá trị mặc định nếu không có dữ liệu
    }

    public int getStudentCount() {
        String sql = "SELECT COUNT(*) AS total FROM [User] WHERE roleID = 3"; // Vai trò 3 là student
        try (Connection conn = new DBContext().connection;
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Giá trị mặc định nếu không có dữ liệu
    }

    public int getPartnerSchoolsCount() {
        String sql = "SELECT COUNT(*) AS total FROM School"; // Giả định số trường liên kết là tổng School
        try (Connection conn = new DBContext().connection;
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Giá trị mặc định nếu không có dữ liệu
    }
}