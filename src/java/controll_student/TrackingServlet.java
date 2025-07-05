package controll_student;

import dal.ScheduleDAO;
import entity.ScheduleStu;
import entity.Attendance;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login_register.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        int userID = user.getId(); // Lấy userID từ User object
        String userName = user.getName() != null ? user.getName() : "Người dùng";

        // Kiểm tra roleID
        if (user.getRoleID() != 3) {
            response.sendRedirect(request.getContextPath() + "/login_register.jsp");
            return;
        }

        // Xác định tuần hiện tại (dựa trên 04/07/2025)
        Date currentDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int daysUntilMonday = (dayOfWeek - Calendar.MONDAY + 7) % 7;
        cal.add(Calendar.DAY_OF_MONTH, -daysUntilMonday);
        Date startOfWeek = cal.getTime();
        cal.add(Calendar.DAY_OF_MONTH, 6);
        Date endOfWeek = cal.getTime();

        // Xử lý tham số weekStart
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String weekStartParam = request.getParameter("weekStart");
        if (weekStartParam != null && !weekStartParam.isEmpty()) {
            try {
                startOfWeek = sdf.parse(weekStartParam);
                cal.setTime(startOfWeek);
                cal.add(Calendar.DAY_OF_MONTH, 6);
                endOfWeek = cal.getTime();
            } catch (Exception e) {
                System.err.println("Error parsing weekStart: " + e.getMessage());
            }
        }

        // Truy vấn dữ liệu
        List<ScheduleStu> schedules = null;
        List<Attendance> attendances = null;
        try {
            schedules = scheduleDAO.getSchedulesByStudentAndWeek(userID, startOfWeek, endOfWeek);
            attendances = scheduleDAO.getAttendancesByStudentAndWeek(userID, startOfWeek, endOfWeek);
            // Log để debug
System.out.println("TrackingServlet - UserID: " + userID);
            System.out.println("Schedules: " + (schedules != null ? schedules.size() : 0));
            System.out.println("Attendances: " + (attendances != null ? attendances.size() : 0));
            System.out.println("Week: " + sdf.format(startOfWeek) + " to " + sdf.format(endOfWeek));
        } catch (SQLException e) {
            System.err.println("SQL Error in TrackingServlet: " + e.getMessage());
            request.setAttribute("errorMessage", "Lỗi truy vấn cơ sở dữ liệu: " + e.getMessage());
        }

        // Đặt dữ liệu vào request
        request.setAttribute("schedules", schedules != null ? schedules : new java.util.ArrayList<>());
        request.setAttribute("attendances", attendances != null ? attendances : new java.util.ArrayList<>());
        request.setAttribute("startOfWeek", startOfWeek);
        request.setAttribute("endOfWeek", endOfWeek);
        request.setAttribute("user", user); // Sử dụng user thay vì userName riêng

        // Chuyển đến JSP
        request.getRequestDispatcher("/tracking.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
