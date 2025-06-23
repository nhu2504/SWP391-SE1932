package dal;

import entity.Room;
import java.sql.*;
import java.util.*;

/**
 * RoomDAO - Quản lý truy xuất dữ liệu bảng Room (phòng học)
 *
 * - Lấy danh sách tất cả các phòng học từ cơ sở dữ liệu
 *
 * Ngày tạo: 23/06/2025  
 * Người viết: Van Nhu
 */
public class RoomDAO {

    /**
     * Lấy toàn bộ danh sách phòng học từ bảng Room
     *
     * @return Danh sách các đối tượng Room
     */
    public List<Room> getAllRooms() {
        List<Room> list = new ArrayList<>();
        String sql = "SELECT id, roomName FROM Room"; // Câu SQL lấy dữ liệu

        try (
            Connection conn = new DBContext().connection;               // Mở kết nối DB
            PreparedStatement ps = conn.prepareStatement(sql);          // Chuẩn bị câu lệnh
            ResultSet rs = ps.executeQuery()                            // Thực thi truy vấn
        ) {
            while (rs.next()) {
                Room r = new Room();                      // Tạo đối tượng Room mới
                r.setId(rs.getInt("id"));                 // Gán ID
                r.setName(rs.getString("roomName"));      // Gán tên phòng
                list.add(r);                              // Thêm vào danh sách
            }
        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi nếu có
        }

        return list; // Trả về danh sách các phòng
    }
}
