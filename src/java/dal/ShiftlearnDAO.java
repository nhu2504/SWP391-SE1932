package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entity.Shiftlearn;

public class ShiftlearnDAO {
    private DBContext dbContext;

    public ShiftlearnDAO() {
        dbContext = DBContext.getInstance();
    }

    public ArrayList<Shiftlearn> getAllShifts() {
        ArrayList<Shiftlearn> shifts = new ArrayList<>();
        String sql = "SELECT ShiftID, Start_time, End_time FROM Shiftlearn";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                // Lấy thời gian từ database
                String startTimeRaw = rs.getString("Start_time");
                String endTimeRaw = rs.getString("End_time");

                // Định dạng thời gian: từ "08:00:00.0000000" thành "08:00"
                String startTime = startTimeRaw != null ? startTimeRaw.substring(0, 5) : "";
                String endTime = endTimeRaw != null ? endTimeRaw.substring(0, 5) : "";

                Shiftlearn shift = new Shiftlearn(
                    rs.getInt("ShiftID"),
                    startTime,
                    endTime
                );
                shifts.add(shift);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shifts;
    }
}