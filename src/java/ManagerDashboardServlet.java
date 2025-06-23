

import dal.ManagerDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet xử lý yêu cầu cho Manager Dashboard.
 * Lấy dữ liệu thống kê từ database và chuyển tiếp đến managerdashboard.jsp.
 */
@WebServlet(name = "ManagerDashboardServlet", urlPatterns = {"/managerDashboard"})
public class ManagerDashboardServlet extends HttpServlet {

    /**
     * Xử lý yêu cầu GET để hiển thị dashboard của manager.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException nếu có lỗi liên quan đến servlet
     * @throws IOException nếu có lỗi I/O
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Khởi tạo DAO để lấy dữ liệu từ database
        ManagerDAO dao = new ManagerDAO();
        
        // Lấy số liệu thống kê
        int studentCount = dao.getStudentCount(); // Số học sinh
        int teacherCount = dao.getTeacherCount(); // Số giáo viên
        int classCount = dao.getClassCount(); // Số lớp học
        int todayScheduleCount = dao.getTodayScheduleCount(); // Số lịch học hôm nay
        
        // Gửi dữ liệu đến JSP qua request attributes
        request.setAttribute("studentCount", studentCount);
        request.setAttribute("teacherCount", teacherCount);
        request.setAttribute("classCount", classCount);
        request.setAttribute("todayScheduleCount", todayScheduleCount);
        
        // Chuyển tiếp đến trang managerdashboard.jsp
        request.getRequestDispatcher("managerdashboard.jsp").forward(request, response);
    }

    /**
     * Xử lý yêu cầu POST (chưa sử dụng trong trường hợp này).
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException nếu có lỗi liên quan đến servlet
     * @throws IOException nếu có lỗi I/O
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Hiện tại chưa xử lý POST, có thể mở rộng sau
        doGet(request, response); // Tạm thời gọi lại doGet
    }

    /**
     * Mô tả ngắn về servlet.
     * @return chuỗi mô tả servlet
     */
    @Override
    public String getServletInfo() {
        return "Servlet for Manager Dashboard";
    }
}