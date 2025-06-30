

import dal.ScheduleDAO;
import entity.ScheduleStu;
import entity.Attendance;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@WebServlet("/tracking")
public class TrackingServlet extends HttpServlet {
    private final ScheduleDAO scheduleDAO = new ScheduleDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Xác định tuần cần hiển thị
        Date startOfWeek, endOfWeek;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();

        String weekStartParam = request.getParameter("weekStart");
        if (weekStartParam != null && !weekStartParam.isEmpty()) {
            try {
                startOfWeek = sdf.parse(weekStartParam);
                cal.setTime(startOfWeek);
                cal.add(Calendar.DAY_OF_MONTH, 6);
                endOfWeek = cal.getTime();
            } catch (Exception e) {
                // Nếu lỗi, dùng tuần hiện tại
                cal.setTime(new Date());
                int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
                int daysUntilMonday = (dayOfWeek - Calendar.MONDAY + 7) % 7;
                cal.add(Calendar.DAY_OF_MONTH, -daysUntilMonday);
                startOfWeek = cal.getTime();
                cal.add(Calendar.DAY_OF_MONTH, 6);
                endOfWeek = cal.getTime();
            }
        } else {
            // Tuần hiện tại
            cal.setTime(new Date());
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            int daysUntilMonday = (dayOfWeek - Calendar.MONDAY + 7) % 7;
            cal.add(Calendar.DAY_OF_MONTH, -daysUntilMonday);
            startOfWeek = cal.getTime();
            cal.add(Calendar.DAY_OF_MONTH, 6);
            endOfWeek = cal.getTime();
        }

        // Sử dụng userID mặc định
        int userID = 1;

        // Truy vấn dữ liệu
        List<ScheduleStu> schedules;
        List<Attendance> attendances;
        try {
            schedules = scheduleDAO.getSchedulesByStudentAndWeek(userID, startOfWeek, endOfWeek);
            attendances = scheduleDAO.getAttendancesByStudentAndWeek(userID, startOfWeek, endOfWeek);
        } catch (SQLException e) {
            throw new ServletException("Lỗi truy vấn cơ sở dữ liệu", e);
        }

        // Đặt dữ liệu vào request
        request.setAttribute("schedules", schedules);
        request.setAttribute("attendances", attendances);
        request.setAttribute("startOfWeek", startOfWeek);
        request.setAttribute("endOfWeek", endOfWeek);
        request.setAttribute("userName", "Khách"); // Tên người dùng mặc định

        // Chuyển đến JSP
        request.getRequestDispatcher("/tracking.jsp").forward(request, response);
    }
}