package dal;

import entity.TutoringClass;
import java.sql.*;
import java.util.*;
/**
 * Ngày tạo: 23/06/2025  
 * Người viết: Van Nhu
 */
public class TutoringClassDAO {

    // Lấy tất cả các lớp học
    public List<TutoringClass> getAllClasses() {
        List<TutoringClass> classes = new ArrayList<>();
        String sql = "SELECT DISTINCT TutoringClassID, ClassName, ImageTutoring, Descrip, isHot, " +
                     "SubjectID, StartDate, EndDate, Tuitionfee, GradeID FROM TutoringClass";
        try (Connection conn = new DBContext().connection; 
             PreparedStatement ps = conn.prepareStatement(sql); 
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                TutoringClass cls = new TutoringClass();
                cls.setTutoringClassID(rs.getInt("TutoringClassID"));
                cls.setClassName(rs.getString("ClassName"));
                cls.setImage(rs.getString("ImageTutoring"));
                cls.setDescrip(rs.getString("Descrip"));
                cls.setIsHot(rs.getBoolean("isHot"));
                cls.setSubjectID(rs.getInt("SubjectID"));
                cls.setStartDate(rs.getDate("StartDate"));
                cls.setEndDate(rs.getDate("EndDate"));
                cls.setPrice(rs.getDouble("Tuitionfee"));
                cls.setGradeID(rs.getInt("GradeID"));
                classes.add(cls);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy danh sách tất cả lớp học: " + e.getMessage());
        }
        return classes;
    }

    // Lấy danh sách lớp học theo trạng thái nổi bật (isHot)
    public List<TutoringClass> getTutoringClassesByHotStatus(boolean isHot) {
        List<TutoringClass> classes = new ArrayList<>();
        String sql = "SELECT TutoringClassID, ClassName, ImageTutoring, Descrip, isHot, SubjectID, " +
                     "StartDate, EndDate, Tuitionfee, GradeID FROM TutoringClass WHERE isHot = ?";
        try (Connection conn = new DBContext().connection; 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, isHot);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TutoringClass cls = new TutoringClass();
                cls.setTutoringClassID(rs.getInt("TutoringClassID"));
                cls.setClassName(rs.getString("ClassName"));
                cls.setImage(rs.getString("ImageTutoring"));
                cls.setDescrip(rs.getString("Descrip"));
                cls.setIsHot(rs.getBoolean("isHot"));
                cls.setSubjectID(rs.getInt("SubjectID"));
                cls.setStartDate(rs.getDate("StartDate"));
                cls.setEndDate(rs.getDate("EndDate"));
                cls.setPrice(rs.getDouble("Tuitionfee"));
                cls.setGradeID(rs.getInt("GradeID"));
                classes.add(cls);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy danh sách lớp học theo trạng thái: " + e.getMessage());
        }
        return classes;
    }

    public List<TutoringClass> getFeaturedTutoringClasses() {
        return getTutoringClassesByHotStatus(true);
    }

    public List<TutoringClass> getYearRoundTutoringClasses() {
        return getTutoringClassesByHotStatus(false);
    }

    // Lấy chi tiết một lớp học
    public TutoringClass getTutoringClassDetail(int tutoringClassID) {
        String sql = "SELECT TutoringClassID, ClassName, ImageTutoring, Descrip, isHot, SubjectID, " +
                     "StartDate, EndDate, Tuitionfee, GradeID FROM TutoringClass WHERE TutoringClassID = ?";
        try (Connection conn = new DBContext().connection; 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, tutoringClassID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                TutoringClass cls = new TutoringClass();
                cls.setTutoringClassID(rs.getInt("TutoringClassID"));
                cls.setClassName(rs.getString("ClassName"));
                cls.setImage(rs.getString("ImageTutoring"));
                cls.setDescrip(rs.getString("Descrip"));
                cls.setIsHot(rs.getBoolean("isHot"));
                cls.setSubjectID(rs.getInt("SubjectID"));
                cls.setStartDate(rs.getDate("StartDate"));
                cls.setEndDate(rs.getDate("EndDate"));
                cls.setPrice(rs.getDouble("Tuitionfee"));
                cls.setGradeID(rs.getInt("GradeID"));
                return cls;
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy chi tiết lớp học: " + e.getMessage());
        }
        return null;
    }
}    