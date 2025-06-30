

import dal.CenterInfoDAO;
import dal.TutoringClassStuDAO;
import dal.AttendanceStuDAO;
import entity.TutoringClassStu;
import entity.AttendanceStu;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

@WebServlet(name = "AttendanceStuServlet", urlPatterns = {"/AttendanceStuServlet"})
public class AttendanceStuServlet extends HttpServlet {

    // Khởi tạo các DAO để lấy dữ liệu từ cơ sở dữ liệu
    private TutoringClassStuDAO tutoringClassDAO = new TutoringClassStuDAO();
    private AttendanceStuDAO attendanceDAO = new AttendanceStuDAO();
    private CenterInfoDAO centerInfoDAO = new CenterInfoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy session để kiểm tra thông tin người dùng
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // Kiểm tra xem người dùng đã đăng nhập chưa
        if (user == null) {
            // Nếu chưa đăng nhập, chuyển hướng về trang đăng nhập
            response.sendRedirect(request.getContextPath() + "/login_register.jsp");
            return;
        }

        // Lấy ID và vai trò của người dùng
        int userID = user.getId();
        int roleID = user.getRoleID();

        try {
            // Lấy danh sách khóa học mà học sinh tham gia
            ArrayList<TutoringClassStu> courses = tutoringClassDAO.getClassesByUserID(userID);
            ArrayList<AttendanceStu> attendances = new ArrayList<>();

            // Lấy tham số courseId từ yêu cầu (khi người dùng chọn một khóa học)
            String courseId = request.getParameter("courseId");

            // Xử lý yêu cầu AJAX từ attendance.jsp
            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                PrintWriter out = response.getWriter();

                // Lấy điểm danh dựa trên courseId (nếu có) hoặc tất cả điểm danh
                if (courseId != null && !courseId.isEmpty()) {
                    attendances = attendanceDAO.getAttendanceByTutoringClassID(Integer.parseInt(courseId), userID);
                } else {
                    attendances = attendanceDAO.getAttendanceByUserID(userID);
                }

                // Tạo JSON thủ công thay vì dùng Gson
                StringBuilder json = new StringBuilder("[");
                for (int i = 0; i < attendances.size(); i++) {
                    AttendanceStu att = attendances.get(i);
                    json.append("{")
                        .append("\"classGroupID\":").append(att.getClassGroupID()).append(",")
                        .append("\"userID\":").append(att.getUserID()).append(",")
                        .append("\"date\":\"").append(att.getDate().replace("\"", "\\\"")).append("\",")
                        .append("\"shift\":\"").append(att.getShift().replace("\"", "\\\"")).append("\",")
                        .append("\"room\":\"").append(att.getRoom().replace("\"", "\\\"")).append("\",")
                        .append("\"teacherName\":\"").append(att.getTeacherName().replace("\"", "\\\"")).append("\",")
                        .append("\"status\":\"").append(att.getStatus().replace("\"", "\\\"")).append("\",")
                        .append("\"tutoringClassID\":").append(att.getTutoringClassID()).append(",")
                        .append("\"className\":\"").append(att.getClassName().replace("\"", "\\\"")).append("\"");
                    // Nếu có comment, thêm vào JSON (hiện tại comment là null vì bảng Attendance không có)
                    if (att.getComment() != null) {
                        json.append(",\"comment\":\"").append(att.getComment().replace("\"", "\\\"")).append("\"");
                    }
                    json.append("}");
                    if (i < attendances.size() - 1) {
                        json.append(",");
                    }
                }
                json.append("]");
                out.print(json.toString());
                out.flush();
                return;
            }

            // Lấy điểm danh dựa trên vai trò người dùng
            if (roleID == 3) { // Học sinh
                if (courseId != null && !courseId.isEmpty()) {
                    // Lấy điểm danh theo khóa học cụ thể
                    attendances = attendanceDAO.getAttendanceByTutoringClassID(Integer.parseInt(courseId), userID);
                } else {
                    // Lấy tất cả điểm danh của học sinh
                    attendances = attendanceDAO.getAttendanceByUserID(userID);
                }
            } else if (roleID == 2 || roleID == 4) { // Giáo viên hoặc Quản lý
                // Tạm thời dùng logic của học sinh, có thể mở rộng sau
                attendances = attendanceDAO.getAttendanceByUserID(userID);
            }

            // Lấy thông tin trung tâm từ CenterInfoDAO
            Map<String, String> centerInfo = centerInfoDAO.getCenterInfo(1);

            // Lưu dữ liệu vào request để gửi đến attendance.jsp
            request.setAttribute("courses", courses);
            request.setAttribute("attendances", attendances);
            request.setAttribute("centerInfo", centerInfo);
            session.setAttribute("userName", user.getName());
            session.setAttribute("userAvatar", user.getAvatar());

            // Chuyển hướng đến trang attendance.jsp
            request.getRequestDispatcher("/attendance.jsp").forward(request, response);
        } catch (Exception e) {
            // Ghi log lỗi và trả về lỗi server nếu có vấn đề
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi tải dữ liệu điểm danh.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Gọi lại doGet để xử lý yêu cầu POST (nếu có)
        doGet(request, response);
    }
}