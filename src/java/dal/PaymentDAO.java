package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * Ngày tạo: 30/06/2025  
 * Người viết: Văn Thị Như
 * Mô tả: DAO xử lý các truy vấn liên quan đến thanh toán học phí
 */
public class PaymentDAO extends DBContext {

    /**
     *  Trả về phần trăm học sinh đã thanh toán học phí.
     * Công thức: (số học sinh đã thanh toán / tổng số học sinh) * 100
     * Trong đó: isPaid = 1 nghĩa là đã thanh toán.
     *
     * @return phần trăm học sinh đã thanh toán (0-100)
     */
    public int getPaidPercentage() {
        String sql = """
            SELECT 
                SUM(CASE WHEN isPaid = 1 THEN 1 ELSE 0 END) * 100 / COUNT(*) AS PaidPercent 
            FROM Payment
        """;

        try (Connection con = new DBContext().connection;
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("PaidPercent"); // Trả về phần trăm
            }

        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra nếu có
        }

        return 0; // Nếu có lỗi, trả về 0%
    }

    /**
     *  Trả về danh sách lớp và số học sinh còn nợ học phí.
     * Bao gồm cả các lớp đã đủ (UnpaidCount = 0).
     * Dựa vào các bản ghi trong bảng Payment → nối với học sinh và lớp.
     *
     * @return List<Object[]> gồm:
     *   - [0]: Tên lớp (String)
     *   - [1]: Số học sinh chưa đóng học phí (Integer)
     */
    public List<Object[]> getUnpaidCountPerClassList() {
        List<Object[]> result = new ArrayList<>();

        String sql = """
            SELECT 
                cg.ClassGroupName,
                COUNT(CASE WHEN p.isPaid = 0 THEN 1 END) AS UnpaidCount
            FROM Payment p
            JOIN ClassGroup_Student cgs ON p.UserID = cgs.StudentID
            JOIN ClassGroup cg ON cgs.ClassGroupID = cg.ClassGroupID
            GROUP BY cg.ClassGroupName
            ORDER BY cg.ClassGroupName
        """;

        try (Connection con = new DBContext().connection;
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] row = new Object[2];
                row[0] = rs.getString("ClassGroupName"); // Tên lớp
                row[1] = rs.getInt("UnpaidCount");       // Số học sinh nợ
                result.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace(); // Ghi lỗi nếu xảy ra
        }

        return result;
    }

    /**
     *  Trả về thống kê chi tiết theo lớp:
     * - Tổng số học sinh
     * - Số học sinh đã đóng
     * - Số học sinh còn nợ
     * - Tỷ lệ phần trăm đã đóng
     * 
     * Các thông tin này có thể dùng để hiển thị bảng chi tiết hoặc vẽ biểu đồ.
     *
     * @return List<Object[]> gồm:
     *   - [0]: Tên lớp (String)
     *   - [1]: Tổng học sinh có hóa đơn (Integer)
     *   - [2]: Số đã đóng (Integer)
     *   - [3]: Số còn nợ (Integer)
     *   - [4]: Phần trăm đã đóng (Integer)
     */
    public List<Object[]> getDetailedPaymentPerClass() {
        List<Object[]> list = new ArrayList<>();

        String sql = """
            SELECT 
                cg.ClassGroupName,
                COUNT(p.PaymentID) AS TotalStudent,
                SUM(CASE WHEN p.isPaid = 1 THEN 1 ELSE 0 END) AS Paid,
                SUM(CASE WHEN p.isPaid = 0 THEN 1 ELSE 0 END) AS Unpaid,
                CASE 
                    WHEN COUNT(p.PaymentID) = 0 THEN 0
                    ELSE SUM(CASE WHEN p.isPaid = 1 THEN 1 ELSE 0 END) * 100 / COUNT(p.PaymentID)
                END AS PaidPercent
            FROM Payment p
            JOIN ClassGroup_Student cgs ON p.UserID = cgs.StudentID
            JOIN ClassGroup cg ON cgs.ClassGroupID = cg.ClassGroupID
            GROUP BY cg.ClassGroupName
        """;

        try (Connection con = new DBContext().connection;
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] row = new Object[5];
                row[0] = rs.getString("ClassGroupName");   // Tên lớp
                row[1] = rs.getInt("TotalStudent");        // Tổng học sinh
                row[2] = rs.getInt("Paid");                // Đã đóng
                row[3] = rs.getInt("Unpaid");              // Còn nợ
                row[4] = rs.getInt("PaidPercent");         // Tỷ lệ %
                list.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
