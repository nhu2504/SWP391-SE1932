package dal;

import dal.DBContext;
import entity.Grade;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GradeDAO {
    public Grade getGradeByID(int id) {
        String query = "select * from Grade\n"
                + "where GradeID = ?";
        try {
            Connection conn = new DBContext().connection; 
                PreparedStatement ps = conn.prepareStatement(query); 
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    return new Grade(rs.getInt(1), rs.getString(2));
                   
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

     /**
     * Lấy toàn bộ danh sách khối lớp từ bảng Grade
     *
     * @return List chứa các đối tượng Grade
     */
    public List<Grade> getAllGrades() {
        List<Grade> grades = new ArrayList<>();

        try (
            Connection conn = new DBContext().connection;
            PreparedStatement ps = conn.prepareStatement(
                "SELECT GradeID, GradeName FROM Grade ORDER BY GradeID ASC"
            );
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Grade grade = new Grade();
                grade.setGradeID(rs.getInt("GradeID"));
                grade.setGradeName(rs.getString("GradeName"));
                grades.add(grade); // Thêm vào danh sách
            }
        } catch (SQLException e) {
            // In lỗi nếu có
            e.printStackTrace();
        }

        return grades;
    }

    /**
     * Lấy tên khối lớp theo ID (GradeID)
     *
     * @param gradeId ID của khối lớp
     * @return Tên khối lớp (GradeName) hoặc null nếu không tìm thấy
     */
    public String getGradeNameById(int gradeId) {
        String gradeName = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = new DBContext().connection;
            String sql = "SELECT GradeName FROM Grade WHERE GradeID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, gradeId);
            rs = ps.executeQuery();

            if (rs.next()) {
                gradeName = rs.getString("GradeName");
            }

        } catch (Exception e) {
            e.printStackTrace();

        
        }

        return gradeName;
    }
}