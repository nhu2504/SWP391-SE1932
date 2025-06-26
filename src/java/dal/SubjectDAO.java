package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entity.Subject;

public class SubjectDAO {
    private DBContext dbContext;

    public SubjectDAO() {
        dbContext = DBContext.getInstance();
    }

    public ArrayList<Subject> getAllSubjects() {
        ArrayList<Subject> subjects = new ArrayList<>();
        String sql = "SELECT SubjectID, SubjectName FROM Subjects";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Subject subject = new Subject(
                    rs.getInt("SubjectID"),
                    rs.getString("SubjectName")
                );
                subjects.add(subject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subjects;
    }
}