import dal.ManagerDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Servlet xử lý yêu cầu cho Manager Dashboard.
 * Lấy dữ liệu thống kê từ database và chuyển tiếp đến managerdashboard.jsp.
 */
@WebServlet(name = "ManagerDashboardServlet", urlPatterns = {"/managerDashboard"})
public class ManagerDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Kiểm tra quyền truy cập
        if (request.getSession().getAttribute("userRoleID") == null ||
            !request.getSession().getAttribute("userRoleID").equals(1)) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Khởi tạo DAO để lấy dữ liệu từ database
        ManagerDAO dao = new ManagerDAO();

        // Lấy tham số lọc từ request
        String weekFilter = request.getParameter("weekFilter");
        String gradeFilter = request.getParameter("gradeFilter");
        if (weekFilter == null) weekFilter = "2025-06-24 - 2025-06-30"; // Mặc định tuần hiện tại
        if (gradeFilter == null) gradeFilter = "all"; // Mặc định tất cả khối

        // Lấy số liệu thống kê
        int studentCount = dao.getStudentCount();
        int teacherCount = dao.getTeacherCount();
        int classCount = dao.getClassCount();
        int todayScheduleCount = dao.getTodayScheduleCount();
        int pendingRequestCount = dao.getPendingRequestCount();

        // Lấy dữ liệu lịch học và số lớp học theo môn
        request.setAttribute("schedule", dao.getScheduleByWeek(weekFilter));
        request.setAttribute("classDataAll", dao.getClassCountBySubject("all"));
        request.setAttribute("classData10", dao.getClassCountBySubject("10"));
        request.setAttribute("classData11", dao.getClassCountBySubject("11"));
        request.setAttribute("classData12", dao.getClassCountBySubject("12"));
        request.setAttribute("selectedWeek", weekFilter);
        request.setAttribute("selectedGrade", gradeFilter);

        // Gửi dữ liệu đến JSP qua request attributes
        request.setAttribute("studentCount", studentCount);
        request.setAttribute("teacherCount", teacherCount);
        request.setAttribute("classCount", classCount);
        request.setAttribute("todayScheduleCount", todayScheduleCount);
        request.setAttribute("pendingRequestCount", pendingRequestCount);

        // Chuyển tiếp đến trang managerdashboard.jsp
        request.getRequestDispatcher("/WEB-INF/manager/managerdashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet for Manager Dashboard";
    }
}