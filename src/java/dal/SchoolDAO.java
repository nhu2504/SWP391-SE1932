package dal;

import entity.School;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Ngày update: 30/06/2025  
 * Người viết: Văn Thị Như
 * 
 * Mô tả: Lớp DAO xử lý thao tác với bảng `School` (Trường liên kết).
 */
public class SchoolDAO {

    /**
     *  Lấy toàn bộ danh sách các trường liên kết
     * 
     * @return List<School> danh sách tất cả các trường
     */
    public List<School> getAllSchools() {
        List<School> schools = new ArrayList<>();
        String sql = "SELECT SchoolID, SchoolName, AddressSchool FROM School";
        try (Connection conn = new DBContext().connection;
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                School s = new School();
                s.setSchoolID(rs.getInt("SchoolID"));
                s.setName(rs.getString("SchoolName"));
                s.setAddress(rs.getString("AddressSchool"));
                schools.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schools;
    }

    /**
     *  Lấy thông tin chi tiết một trường theo ID
     * 
     * @param id ID của trường
     * @return đối tượng School nếu tìm thấy, ngược lại trả về null
     */
    public School getSchoolByID(int id) {
        String query = """
                       select * from School
                         where SchoolID = ?""";
        try {
            //mở kết nối,tạo truy vấn
            Connection conn = new DBContext().connection; 
                PreparedStatement ps = conn.prepareStatement(query); 
                //gán id vào tham số thứ nhất
                ps.setInt(1, id);
                //truy vấn và lấy kết quả
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    return new School(rs.getInt(1), 
                            rs.getString(2));
                }
        } catch (SQLException e) {
            System.out.println("Lỗi "+e.getMessage());
        }
        return null;
    }

    /**
     * Lấy tên trường theo SchoolID
     * 
     * @param schoolID ID của trường
     * @return tên trường nếu có, ngược lại là chuỗi rỗng
     */
    public String getSchoolNameById(int schoolID) {
        String schoolName = "";
        String sql = "SELECT SchoolName FROM School WHERE SchoolID = ?";
        try (Connection conn = new DBContext().connection;
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, schoolID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                schoolName = rs.getString("SchoolName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schoolName;
    }

    /**
     *  Đếm số lượng trường liên kết hiện có trong hệ thống
     * 
     * @return tổng số trường (int)
     */
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
