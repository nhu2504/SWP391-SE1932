

import dal.CenterInfoDAO;
import dal.DocumentDAO;
import dal.TutoringClassStuDAO;
import dal.SubjectDAO;
import entity.TutoringClassStu;
import entity.Document;
import entity.Subject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "DashboardServlet", urlPatterns = {"/DashboardServlet"})
public class DashboardServlet extends HttpServlet {

    // Khởi tạo các DAO
    private TutoringClassStuDAO tutoringClassStuDAO = new TutoringClassStuDAO();
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
            List<TutoringClassStu> classes = tutoringClassStuDAO.getClassesByUserID(userID);
            // Lấy danh sách tài liệu (lấy tất cả tài liệu, không lọc theo grade/subject)
            List<Document> documents = documentDAO.getDocumentsByGradeAndSubject(0, 0);
            // Lấy tất cả môn học
            List<Subject> subjects = subjectDAO.getAllSubjects();
            // Lấy thông tin trung tâm
            Map<String, String> centerInfo = centerInfoDAO.getCenterInfo(1); // Sử dụng CenterID = 1

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