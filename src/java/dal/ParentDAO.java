package dal;

import dal.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParentDAO {
    // Lấy danh sách bình luận của phụ huynh
    public List<Map<String, Object>> getParentComments() {
        List<Map<String, Object>> parents = new ArrayList<>();
        try (Connection conn = new DBContext().connection;
             PreparedStatement ps = conn.prepareStatement(
                     // Câu SQL lấy bình luận phụ huynh
                     "SELECT c.CommentText, c.Rating, c.CommentDate, s.FullName AS StudentName "
                             + "FROM [User] u "
                             + "INNER JOIN Comment c ON u.UserID = c.UserID "
                             + "INNER JOIN ParentChild pc ON u.UserID = pc.ParentID "
                             + "INNER JOIN [User] s ON pc.StudentID = s.UserID "
                             + "WHERE u.roleID = 4 AND c.Rating >= 4 AND c.CommentText IS NOT NULL "
                             + "ORDER BY c.Rating DESC, c.CommentDate DESC");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> parent = new HashMap<>();
                parent.put("studentName", rs.getString("StudentName"));
                parent.put("commentText", rs.getString("CommentText"));
                parent.put("rating", rs.getInt("Rating"));
                parents.add(parent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return parents;
    }
}