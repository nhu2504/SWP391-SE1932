package dal;

import dal.DBContext;
import entity.Grade;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Ngày update: 30/06/2025  
 * Người viết: Văn Thị Như
 * Mục đích: Truy xuất dữ liệu các khối lớp từ cơ sở dữ liệu
 */
public class GradeDAO {

    /**
     *  Lấy danh sách tất cả các khối lớp từ bảng Grade trong DB
     *
     * @return List<Grade> - danh sách các khối lớp (GradeID + GradeName)
     */
    public List<Grade> getAllGrades() {
        List<Grade> grades = new ArrayList<>();

        // Truy vấn tất cả khối lớp, sắp xếp theo ID tăng dần
        String sql = "SELECT GradeID, GradeName FROM Grade ORDER BY GradeID ASC";

        try (Connection conn = new DBContext().connection;
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // Lặp qua từng dòng kết quả và thêm vào danh sách
            while (rs.next()) {
                Grade grade = new Grade();
                grade.setGradeID(rs.getInt("GradeID"));       // ID khối lớp
                grade.setGradeName(rs.getString("GradeName")); // Tên khối lớp
                grades.add(grade);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi nếu có
        }

        return grades; // Trả về danh sách khối lớp
    }

    /**
     *  Trả về tên khối lớp (GradeName) theo ID
     *
     * @param gradeId ID của khối lớp
     * @return Tên khối lớp (GradeName) hoặc null nếu không tìm thấy
     */
    public String getGradeNameById(int gradeId) {
        String gradeName = null;
        String sql = "SELECT GradeName FROM Grade WHERE GradeID = ?";

        try (Connection conn = new DBContext().connection;
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, gradeId);                      // Gán tham số ID
            ResultSet rs = ps.executeQuery();           // Thực thi truy vấn

            if (rs.next()) {
                gradeName = rs.getString("GradeName");  // Lấy tên lớp nếu có
            }

        } catch (Exception e) {
            e.printStackTrace(); // In lỗi nếu có
        }

        return gradeName; // Trả về tên khối lớp hoặc null nếu không tìm thấy
    }
}
