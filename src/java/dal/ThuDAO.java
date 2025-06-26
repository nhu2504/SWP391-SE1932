package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entity.Thu;

public class ThuDAO {
    private DBContext dbContext;

    public ThuDAO() {
        dbContext = DBContext.getInstance();
    }

    public ArrayList<Thu> getAllThus() {
        ArrayList<Thu> thus = new ArrayList<>();
        String sql = "SELECT ThuID, nameThu FROM Thu";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
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
}