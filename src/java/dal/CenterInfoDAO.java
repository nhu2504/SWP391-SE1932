package dal;

import entity.CenterInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Ngày update: 30/06/2025  
 * Người viết: Văn Thị Như
 * Mục đích: Thao tác với bảng `CenterInfo` để quản lý thông tin trung tâm dạy thêm.
 */
public class CenterInfoDAO {

    /**
     *  Lấy thông tin trung tâm dựa theo ID
     * 
     * @param centerId ID của trung tâm
     * @return Đối tượng CenterInfo chứa thông tin trung tâm hoặc null nếu không có
     */
    public CenterInfo getCenterInfo(int centerId) {
        String sql = "SELECT * FROM CenterInfo WHERE CenterID = ?";
        try (Connection conn = new DBContext().connection;
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, centerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                CenterInfo info = new CenterInfo();
                info.setCenterID(rs.getInt("CenterID"));
                info.setNameCenter(rs.getString("NameCenter"));
                info.setDescrip(rs.getString("DescripCenter"));
                info.setDescrip1(rs.getString("DescripCenter1")); // Mô tả phụ nếu có
                info.setAddress(rs.getString("AddressCenter"));
                info.setLogo(rs.getString("Logo"));
                info.setImageCenter(rs.getString("imageCenter"));
                info.setPhone(rs.getString("Phone"));
                info.setEmail(rs.getString("Email"));
                info.setWebsite(rs.getString("Website"));
                info.setFace(rs.getString("Facebook"));
                info.setZalo(rs.getString("Zalo"));
                info.setYoutube(rs.getString("Youtube"));
                info.setTiktok(rs.getString("Tiktok"));
                info.setLastUpdate(rs.getTimestamp("LastUpdated"));
                info.setUpdateBy(rs.getInt("UpdatedBy"));
                info.setYearOfWork(rs.getInt("YearOfWork"));
                return info;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Trả về null nếu không tìm thấy
    }

    /**
     *  Cập nhật một trường cụ thể của trung tâm theo tên trường
     *
     * @param centerId ID trung tâm
     * @param fieldName Tên trường cần cập nhật (theo tên logic trong hệ thống)
     * @param fieldValue Giá trị mới
     * @return true nếu cập nhật thành công
     */
    public boolean updateField(int centerId, String fieldName, String fieldValue) {
        // Ánh xạ tên field trong hệ thống sang tên cột trong CSDL
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
            // Nếu giá trị là chuỗi rỗng, cập nhật là null
            ps.setString(1, fieldValue != null && fieldValue.trim().isEmpty() ? null : fieldValue);
            ps.setInt(2, centerId);
            int rows = ps.executeUpdate();
            return rows > 0; // true nếu có bản ghi được cập nhật
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *  Xóa một trường cụ thể bằng cách cập nhật nó thành NULL
     *
     * @param centerId ID trung tâm
     * @param fieldName Tên trường cần xóa
     * @return true nếu xóa thành công
     */
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

    /**
     *  Cập nhật toàn bộ thông tin trung tâm từ một form chỉnh sửa
     * 
     * @param centerId ID trung tâm
     * @param name Tên trung tâm
     * @param descrip Mô tả
     * @param address Địa chỉ
     * @param phone SĐT
     * @param email Email
     * @param website Website
     * @param logoFileName Tên file logo nếu có cập nhật
     * @return true nếu cập nhật thành công
     */
    public boolean updateAllFields(int centerId, String name, String descrip, String address, String phone, String email, String website, String logoFileName) {
        // Tạo câu lệnh SQL cập nhật
        StringBuilder sql = new StringBuilder(
            "UPDATE CenterInfo SET NameCenter=?, DescripCenter=?, AddressCenter=?, Phone=?, Email=?, Website=?"
        );
        if (logoFileName != null && !logoFileName.isEmpty()) {
            sql.append(", Logo=?");
        }
        sql.append(" WHERE CenterID=?");

        try (Connection conn = new DBContext().connection;
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int idx = 1;
            ps.setString(idx++, name);
            ps.setString(idx++, descrip);
            ps.setString(idx++, address);
            ps.setString(idx++, phone);
            ps.setString(idx++, email);
            ps.setString(idx++, website);
            if (logoFileName != null && !logoFileName.isEmpty()) {
                ps.setString(idx++, logoFileName);
            }
            ps.setInt(idx, centerId);

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *  Trả về số năm hoạt động của trung tâm (năm thành lập)
     * 
     * @return Năm hoạt động hoặc 2000 nếu không tìm thấy
     */
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
        return 2000; // Mặc định nếu không có dữ liệu
    }
    //nam
    public Map<String, String> getCenterInfo1(int centerId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(
                    "SELECT NameCenter, AddressCenter, Email, Phone, DescripCenter FROM CenterInfo WHERE CenterID = ?");
            ps.setInt(1, centerId);
            rs = ps.executeQuery();
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
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return new HashMap<>();
    }
}
