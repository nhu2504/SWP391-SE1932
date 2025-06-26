package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entity.Room;

public class RoomDAO {
    private DBContext dbContext;

    public RoomDAO() {
        dbContext = DBContext.getInstance();
    }

    public ArrayList<Room> getAllRooms() {
        ArrayList<Room> rooms = new ArrayList<>();
        String sql = "SELECT id, name FROM Room";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Room room = new Room(
                    rs.getInt("id"),
                    rs.getString("name")
                );
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }
}