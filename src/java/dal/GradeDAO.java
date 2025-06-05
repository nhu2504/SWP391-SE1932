package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entity.Grade;
import entity.TutoringClass;
import entity.account;

public class GradeDAO {
   

    public ArrayList<Grade> getGradesByUserID(int userID) {
        ArrayList<Grade> grades = new ArrayList<>();
        String sql = "SELECT TutoringClassID, UserID, nameoftest, Score FROM Grade WHERE UserID = ?";
        loginDAO ld = new loginDAO();
        TutoringClassDAO td = new TutoringClassDAO();
        try (Connection conn = new DBContext().connection;
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Grade grade = new Grade(
                    td.getTutoringClassByID(rs.getInt("TutoringClassID")),
                    ld.getUserByID(rs.getInt("UserID")),
                    rs.getString("nameoftest"),
                    rs.getFloat("Score")
                );
                grades.add(grade);
            }
            System.out.println("GradeDAO: grades size for userID " + userID + " = " + grades.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grades;
    }
//    public Grade getOneGradeByID(int tutorID, int userID){
//        String query = "select * from Grade\n"
//                + "where TutoringClassID = ?"
//                + "and UserID = ?";
//        TutoringClassDAO td = new TutoringClassDAO();
//        loginDAO ld = new loginDAO();
//        try {
//            Connection conn = new DBContext().connection; 
//                PreparedStatement ps = conn.prepareStatement(query); 
//                ps.setInt(1, td.getTutoringClassByID(tutorID));
//                ps.setInt(2, ld.getUserByID(userID));
//                ResultSet rs = ps.executeQuery();
//                if(rs.next()){
//                    return new Grade();
//                }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}