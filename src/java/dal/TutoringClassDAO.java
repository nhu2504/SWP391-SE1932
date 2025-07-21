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
        String sql = "SELECT * FROM TutoringClass ORDER BY CASE isActive "
                + "WHEN 0 THEN 1 " +
                // Sắp mở
                "WHEN 1 THEN 2 "
                + // Đang mở             
                "WHEN 2 THEN 3 "
                + // Đã đóng
                "ELSE 3 END";

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
                cls.setIsActive(rs.getInt("isActive"));
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
                   StartDate, EndDate, Tuitionfee, GradeID, isActive
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
                cls.setIsActive(rs.getInt("isActive"));
                return cls;
            }

        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy chi tiết lớp học: " + e.getMessage());
        }

        return null;
    }

    // hàm tự động cập nhật khoá học từ sắp mở thành đang mở nếu đã đến ngày bắt đầu
    public void updateAutoActiveStatus() {
        System.out.println("Gọi updateAutoActiveStatus()");
        String sql = """
        UPDATE TutoringClass
        SET isActive = CASE
            WHEN isActive = 0 AND StartDate <= CAST(GETDATE() AS DATE) THEN 1
            WHEN isActive = 1 AND EndDate < CAST(GETDATE() AS DATE) THEN 2
            ELSE isActive
        END
        WHERE (isActive = 0 AND StartDate <= CAST(GETDATE() AS DATE))
           OR (isActive = 1 AND EndDate < CAST(GETDATE() AS DATE))
                 USE eduraFINALFINALFINAL;
                 SELECT * FROM TutoringClass WHERE isActive = 2
    """;
        try (Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
            System.out.println("DB URL: " + conn.getMetaData().getURL());
            int affected = ps.executeUpdate();
            System.out.println("Update TutoringClass: " + affected + " rows updated");

        } catch (Exception e) {
            e.printStackTrace();
        }
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
        String sql = "UPDATE TutoringClass SET isActive = 0 WHERE TutoringClassID = ?";

        try (Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Lỗi khi ẩn (xóa mềm) lớp học: " + e.getMessage());
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
