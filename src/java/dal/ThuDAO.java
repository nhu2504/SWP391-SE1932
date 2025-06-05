package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entity.Thu;

public class ThuDAO {

    public ArrayList<Thu> getAllThus() {
        ArrayList<Thu> thus = new ArrayList<>();
        String sql = "SELECT ThuID, nameThu FROM Thu";
        try (Connection conn = new DBContext().connection; PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Thu thu = new Thu(
                        rs.getInt("ThuID"),
                        rs.getString("NameThu")
                );
                thus.add(thu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return thus;
    }

    public Thu getThuByID(int id) {
        String query = "select * from Thu\n"
                + "  where ThuID = ?";
        try {
            Connection conn = new DBContext().connection;
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Thu(rs.getInt("ThuID"), rs.getString("NameThu"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
