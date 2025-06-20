package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entity.Room;
/**
 *
 * @author DO NGOC ANH HE180661
 */
public class RoomDAO {
    //lấy ra tất cả phòng học
    public ArrayList<Room> getAllRooms() {
        //khởi tạo danh sách chứa các phòng lấy từ db
        ArrayList<Room> rooms = new ArrayList<>();
        //câu lệnh lấy toàn bộ dữ liệu trong bảng
        String sql = "SELECT id, name FROM Room";
        //mở kết nối, thực thi câu lệnh
        try (Connection conn = new DBContext().connection; PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            //lặp qua kết quả trả về từ bảng room
            while (rs.next()) {
                Room room = new Room(
                        rs.getInt("id"),
                        rs.getString("name")
                );
                //mỗi dòng tạo ra 1 đối tượng room rồi thêm vào danh sách
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.getMessage();
        }
        return rooms;
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
