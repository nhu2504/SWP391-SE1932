/*
 * Tác giả: Van Nhu
 * Ngày tạo: 23/06/2025
 * Mô tả:
 *  - DAO quản lý các thao tác với bảng CenterInfo (thông tin trung tâm).
 *  - Bao gồm: lấy thông tin trung tâm, cập nhật từng trường, xóa trường,
 *    và lấy thống kê như số năm hoạt động, số học sinh, số trường liên kết.
 */

package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CenterInfoDAO {

    // Lấy thông tin trung tâm theo CenterID, trả về dưới dạng Map<String, String>
    public Map<String, String> getCenterInfo(int centerId) {
        try (Connection conn = new DBContext().connection;
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT NameCenter, AddressCenter, Email, Phone, DescripCenter FROM CenterInfo WHERE CenterID = ?")) {

            ps.setInt(1, centerId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Map<String, String> centerInfo = new HashMap<>();
                centerInfo.put("NameCenter", rs.getString("NameCenter"));
                centerInfo.put("AddressCenter", rs.getString("AddressCenter"));
                centerInfo.put("Email", rs.getString("Email"));
                centerInfo.put("Phone", rs.getString("Phone"));
                centerInfo.put("DescripCenter", rs.getString("DescripCenter"));
                return centerInfo;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Cập nhật 1 trường thông tin cho trung tâm dựa theo tên trường (fieldName)
    public boolean updateField(int centerId, String fieldName, String fieldValue) {
        // Ánh xạ tên field trên giao diện thành tên cột trong database
        String dbColumn = switch (fieldName) {
            case "centerName" -> "NameCenter";
            case "address" -> "AddressCenter";
            case "email" -> "Email";
            case "phone" -> "Phone";
            case "descripCenter" -> "DescripCenter";
            case "logo" -> "Logo";
            case "imageCenter" -> "imageCenter";
            default -> null;
        };

        if (dbColumn == null) return false;

        String sql = "UPDATE CenterInfo SET " + dbColumn + " = ? WHERE CenterID = ?";
        try (Connection conn = new DBContext().connection;
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Nếu giá trị là chuỗi rỗng thì set null
            ps.setString(1, (fieldValue != null && fieldValue.trim().isEmpty()) ? null : fieldValue);
            ps.setInt(2, centerId);

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Đặt giá trị NULL cho 1 trường (xóa nội dung trường)
    public boolean deleteField(int centerId, String fieldName) {
        String dbColumn = switch (fieldName) {
            case "centerName" -> "NameCenter";
            case "address" -> "AddressCenter";
            case "email" -> "Email";
            case "phone" -> "Phone";
            case "descripCenter" -> "DescripCenter";
            case "logo" -> "Logo";
            case "imageCenter" -> "imageCenter";
            default -> null;
        };

        if (dbColumn == null) return false;

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

    // Trả về năm thành lập của trung tâm (YearOfWork)
    public int getYearOfWork() {
        String sql = "SELECT YearOfWork FROM CenterInfo WHERE CenterID = 1";
        try (Connection conn = new DBContext().connection;
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("YearOfWork");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 2000; // Giá trị mặc định nếu không có
    }

    // Trả về tổng số học sinh (roleID = 3)
    public int getStudentCount() {
        String sql = "SELECT COUNT(*) AS total FROM [User] WHERE roleID = 3";
        try (Connection conn = new DBContext().connection;
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    // Trả về tổng số trường đối tác (giả định tất cả bản ghi trong bảng School)
    public int getPartnerSchoolsCount() {
        String sql = "SELECT COUNT(*) AS total FROM School";
        try (Connection conn = new DBContext().connection;
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
