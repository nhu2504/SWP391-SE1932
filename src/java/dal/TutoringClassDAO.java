package dal;

import entity.TutoringClass;
import java.sql.*;
import java.util.*;

/**
 * Ngày update: 30/06/2025 Người viết: Văn Thị Như
 *
 * Mô tả: DAO xử lý truy vấn cho các lớp học (TutoringClass)
 */
public class TutoringClassDAO {

    /**
     * Lấy danh sách lớp học theo trạng thái nổi bật (isHot)
     *
     * @param isHot true: lớp nổi bật, false: lớp thường, null: tất cả
     * @return danh sách lớp học
     */
    public List<TutoringClass> getClasses(Boolean isHot) {
        List<TutoringClass> classes = new ArrayList<>();
        String sql = """
            SELECT TutoringClassID, ClassName, ImageTutoring, Descrip, isHot, SubjectID,
                   StartDate, EndDate, Tuitionfee, GradeID
            FROM TutoringClass
        """;

        if (isHot != null) {
            sql += " WHERE isHot = ?";
        }

        try (Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            if (isHot != null) {
                ps.setBoolean(1, isHot);
            }

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
            System.out.println("Lỗi khi lấy danh sách lớp học: " + e.getMessage());
        }

        return classes;
    }

    /**
     * Lấy danh sách lớp học nổi bật (isHot = true)
     */
    public List<TutoringClass> getFeaturedTutoringClasses() {
        return getClasses(true);
    }

    /**
     * Lấy danh sách lớp học dài hạn (isHot = false)
     */
    public List<TutoringClass> getYearRoundTutoringClasses() {
        return getClasses(false);
    }

    /**
     * Lấy chi tiết một lớp học theo ID
     *
     * @param tutoringClassID ID lớp học
     * @return đối tượng TutoringClass hoặc null nếu không tìm thấy
     */
    public TutoringClass getTutoringClassDetail(int tutoringClassID) {
        String sql = """
            SELECT TutoringClassID, ClassName, ImageTutoring, Descrip, isHot, SubjectID,
                   StartDate, EndDate, Tuitionfee, GradeID
            FROM TutoringClass
            WHERE TutoringClassID = ?
        """;

        try (Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {

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

    public int addTutoringClass(TutoringClass cls) {
        String sql = """
        INSERT INTO TutoringClass 
        (ClassName, ImageTutoring, Descrip, isHot, SubjectID, StartDate, EndDate, Tuitionfee, GradeID)
        OUTPUT INSERTED.TutoringClassID
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
    """;

        try (Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cls.getClassName());
            ps.setString(2, cls.getImage());
            ps.setString(3, cls.getDescrip());
            ps.setBoolean(4, cls.isIsHot());
            ps.setInt(5, cls.getSubjectID());
            ps.setDate(6, new java.sql.Date(cls.getStartDate().getTime()));
            ps.setDate(7, new java.sql.Date(cls.getEndDate().getTime()));
            ps.setDouble(8, cls.getPrice());
            ps.setInt(9, cls.getGradeID());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1); // trả về TutoringClassID mới
            }
        } catch (SQLException e) {
            System.out.println("Lỗi thêm lớp học: " + e.getMessage());
        }
        return -1; // hoặc ném exception nếu muốn kiểm soát lỗi nghiêm ngặt hơn
    }

    public void updateTutoringClass(TutoringClass cls) {
        String sql = """
        UPDATE TutoringClass
        SET ClassName = ?, ImageTutoring = ?, Descrip = ?, isHot = ?, SubjectID = ?, StartDate = ?, EndDate = ?, Tuitionfee = ?, GradeID = ?
        WHERE TutoringClassID = ?
    """;

        try (Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cls.getClassName());
            ps.setString(2, cls.getImage());
            ps.setString(3, cls.getDescrip());
            ps.setBoolean(4, cls.isIsHot());
            ps.setInt(5, cls.getSubjectID());
            ps.setDate(6, new java.sql.Date(cls.getStartDate().getTime()));
            ps.setDate(7, new java.sql.Date(cls.getEndDate().getTime()));
            ps.setDouble(8, cls.getPrice());
            ps.setInt(9, cls.getGradeID());
            ps.setInt(10, cls.getTutoringClassID());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Lỗi cập nhật lớp học: " + e.getMessage());
        }
    }

    public void deleteTutoringClass(int id) {
        String sql = "DELETE FROM TutoringClass WHERE TutoringClassID = ?";

        try (Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Lỗi xóa lớp học: " + e.getMessage());
        }
    }

    public List<TutoringClass> searchTutoringClassByName(String keyword) {
        List<TutoringClass> classes = new ArrayList<>();
        String sql = """
        SELECT TutoringClassID, ClassName, ImageTutoring, Descrip, isHot, SubjectID,
               StartDate, EndDate, Tuitionfee, GradeID
        FROM TutoringClass
        WHERE ClassName LIKE ?
    """;

        try (Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
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
            System.out.println("Lỗi tìm kiếm lớp học: " + e.getMessage());
        }

        return classes;
    }

}
