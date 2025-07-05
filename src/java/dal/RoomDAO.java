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
    
    // Ngọc Anh
    public Room getRoomByID(int id) {
        //câu lệnh lấy thông tin phòng học theo id
        String query = """
                       select * from Room
                         where id = ?""";
        //mở kết nối
        try {
            Connection conn = new DBContext().connection;
            PreparedStatement ps = conn.prepareStatement(query);
            //gán giá trị id vào dấu ? 
            ps.setInt(1, id);
            //thực hiện truy vấn, nếu tìm thấy phòng thì tạo đối tượng room và trả về 
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return new Room(rs.getInt("id"), rs.getString("name"));
            }
        } catch (SQLException e) {
            e.getMessage();
        }
        return null;
    }
}
