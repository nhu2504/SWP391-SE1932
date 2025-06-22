package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManagerDAO {
    private Connection connection;

    public ManagerDAO() {
        this.connection = new DBContext().connection; // Kết nối từ DBContext
    }

    // Đếm số học sinh
    public int getStudentCount() {
        String sql = "SELECT COUNT(*) FROM [User] WHERE roleID = 3";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Đếm số giáo viên
    public int getTeacherCount() {
        String sql = "SELECT COUNT(*) FROM [User] WHERE roleID = 2";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Đếm số lớp học
    public int getClassCount() {
        String sql = "SELECT COUNT(*) FROM ClassGroup";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Đếm số lịch học hôm nay
    public int getTodayScheduleCount() {
        String sql = "SELECT COUNT(*) FROM Schedule WHERE CAST(DateLearn AS DATE) = CAST(GETDATE() AS DATE)";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}