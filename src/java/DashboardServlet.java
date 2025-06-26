

import dal.CenterInfoDAO;
import dal.DocumentDAO;
import dal.TutoringClassDAO;
import dal.SubjectDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import entity.Document;
import entity.TutoringClass;
import entity.Subject;

@WebServlet(name = "DashboardServlet", urlPatterns = {"/DashboardServlet"})
public class DashboardServlet extends HttpServlet {

    // Khởi tạo các DAO
    private TutoringClassDAO tutoringClassDAO = new TutoringClassDAO();
    private DocumentDAO documentDAO = new DocumentDAO();
    private SubjectDAO subjectDAO = new SubjectDAO();
    private CenterInfoDAO centerInfoDAO = new CenterInfoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Sử dụng userID mặc định
        int userID = 1;

        try {
            // Lấy danh sách lớp học của người dùng
            ArrayList<TutoringClass> classes = tutoringClassDAO.getClassesByUserID(userID);
            // Lấy danh sách tài liệu của người dùng
            ArrayList<Document> documents = documentDAO.getDocumentsByUserID(userID);
            // Lấy tất cả môn học
            ArrayList<Subject> subjects = subjectDAO.getAllSubjects();
            // Lấy thông tin trung tâm
            Map<String, String> centerInfo = centerInfoDAO.getCenterInfo();

            // Lưu dữ liệu vào request để gửi tới JSP
            request.setAttribute("classes", classes);
            request.setAttribute("documents", documents);
            request.setAttribute("subjects", subjects);
            request.setAttribute("centerInfo", centerInfo);
            request.setAttribute("userName", "Khách"); // Tên người dùng mặc định

            // Chuyển hướng tới dashboard.jsp
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            // In lỗi để debug
            e.printStackTrace();
            // Gửi lỗi 500 nếu có vấn đề
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi tải dữ liệu dashboard.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Gọi doGet để xử lý POST giống GET
        doGet(request, response);
    }
}