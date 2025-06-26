package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entity.Room;
import java.util.List;
/**
 *
 * @author DO NGOC ANH HE180661
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
    //tìm phòng học theo id
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
