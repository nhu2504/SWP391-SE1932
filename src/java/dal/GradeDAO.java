package dal;

import dal.DBContext;
import entity.Grade;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//Văn Thị NHư - HE181329
public class GradeDAO {
    // Lấy danh sách tất cả các khối lớp từ DB
    public List<Grade> getAllGrades() {
        List<Grade> grades = new ArrayList<>();
        try (Connection conn = new DBContext().connection;
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT GradeID, GradeName FROM grade ORDER BY GradeID ASC");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Grade grade = new Grade();
                grade.setGradeID(rs.getInt("GradeID"));
                grade.setGradeName(rs.getString("GradeName"));
                grades.add(grade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grades;
    }
    public String getGradeNameById(int gradeId) {
        String gradeName = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = new DBContext().connection; 
            String sql = "SELECT gradeName FROM Grade WHERE gradeId = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, gradeId);
            rs = ps.executeQuery();
            if (rs.next()) {
                gradeName = rs.getString("gradeName");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return gradeName;
    }

}