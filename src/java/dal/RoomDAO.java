package dal;

import entity.Room;
import java.sql.*;
import java.util.*;

/**
 * Ngày update: 30/06/2025  
 * Người viết: Văn Thị Như
 * 
 * Mô tả: DAO để truy xuất danh sách phòng học từ cơ sở dữ liệu.
 */
public class RoomDAO {

    /**
     *  Lấy danh sách tất cả các phòng học từ bảng Room trong cơ sở dữ liệu.
     * 
     * @return Danh sách các đối tượng Room
     */
    public List<Room> getAllRooms() {
        List<Room> list = new ArrayList<>();
        String sql = "SELECT id, roomName FROM Room"; // Câu lệnh SQL lấy toàn bộ phòng

        try (
            Connection conn = new DBContext().connection;            // Mở kết nối đến DB
            PreparedStatement ps = conn.prepareStatement(sql);       // Tạo PreparedStatement
            ResultSet rs = ps.executeQuery()                         // Thực thi truy vấn
        ) {
            while (rs.next()) {
                Room r = new Room();
                r.setId(rs.getInt("id"));              // Gán ID phòng
                r.setName(rs.getString("roomName"));   // Gán tên phòng
                list.add(r);                           // Thêm vào danh sách
            }
        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi nếu xảy ra
        }

        return list; // Trả về danh sách phòng học
    }
}
