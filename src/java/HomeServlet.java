import dal.*;
import entity.HomeTutoringClass;
import entity.Subject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

// Định nghĩa các URL mà servlet này sẽ xử lý
@WebServlet({"/home", "/about", "/course", "/teacher"})
public class HomeServlet extends HttpServlet {
    // Khởi tạo các DAO để lấy dữ liệu từ database
    private final CenterInfoDAO centerInfoDAO = new CenterInfoDAO();
    private final SubjectDAO subjectDAO = new SubjectDAO();
    private final DocumentDAO documentDAO = new DocumentDAO();
    private final TutoringClassDAO tutoringClassDAO = new TutoringClassDAO();
    private final TeacherDAO teacherDAO = new TeacherDAO();
    private final StudentDAO studentDAO = new StudentDAO();
    private final ParentDAO parentCommentDAO = new ParentDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // In ra console để kiểm tra URL được gọi (ví dụ: /home, /about)
        System.out.println("HomeServlet được gọi tại: " + request.getRequestURI());

        try {
            // 1. Lấy thông tin trung tâm
            Map<String, String> centerInfo = centerInfoDAO.getCenterInfo(1);
            if (centerInfo != null) {
                request.setAttribute("centerName", centerInfo.get("NameCenter")); // Tên trung tâm
                request.setAttribute("address", centerInfo.get("AddressCenter")); // Địa chỉ
                request.setAttribute("email", centerInfo.get("Email")); // Email
                request.setAttribute("phone", centerInfo.get("Phone")); // Số điện thoại
                request.setAttribute("descripCenter", centerInfo.get("DescripCenter")); // Mô tả
            } else {
                request.setAttribute("error", "Không tìm thấy thông tin trung tâm");
            }

            // 2. Lấy danh sách môn học
            List<Map<String, String>> subjects = subjectDAO.getAllSubjects();
            request.setAttribute("subjects", subjects);
            System.out.println("Số môn học lấy được: " + subjects.size());

            // 3. Lấy danh sách tài liệu
            Map<String, List<Map<String, String>>> documents = documentDAO.getAllDocuments();
            request.setAttribute("documents", documents);
            System.out.println("Số tài liệu lấy được: " + documents.values().stream().mapToInt(List::size).sum());

            // 4. Lấy danh sách khóa học
            Map<String, List<Map<String, String>>> classes = tutoringClassDAO.getAllClasses();
            request.setAttribute("classes", classes);
            System.out.println("Số khóa học lấy được: " + classes.values().stream().mapToInt(List::size).sum());

            // 5. Lấy danh sách khóa học chi tiết
            List<Map<String, Object>> tutoringClasses = tutoringClassDAO.getDetailedTutoringClasses();
            request.setAttribute("tutoringClasses", tutoringClasses);
            System.out.println("Số khóa học chi tiết lấy được: " + tutoringClasses.size());

            // 6. Lấy danh sách ảnh môn học
            List<Subject> subjectImages = subjectDAO.getSubjectImages(request.getContextPath());
            request.setAttribute("subjectImages", subjectImages);
            System.out.println("Số môn học với ảnh lấy được: " + subjectImages.size());

            // 7. Lấy danh sách giáo viên
            List<Map<String, Object>> teachers = teacherDAO.getAllTeachers();
            request.setAttribute("teachers", teachers);
            System.out.println("Số giáo viên lấy được: " + teachers.size());

            // 8. Lấy danh sách học sinh nổi bật (top 10)
            List<Map<String, Object>> students = studentDAO.getTopStudents();
            request.setAttribute("students", students);
            System.out.println("Số học sinh lấy được: " + students.size());

            // 9. Lấy bình luận của phụ huynh
            List<Map<String, Object>> parents = parentCommentDAO.getParentComments();
            request.setAttribute("parents", parents);
            System.out.println("Số phụ huynh lấy được: " + parents.size());

            // Chuyển tiếp đến JSP
            forwardToJsp(request, response);
        } catch (Exception e) {
            // Nếu có lỗi (ví dụ: lỗi database), in lỗi và hiển thị thông báo
            e.printStackTrace();
            request.setAttribute("error", "Lỗi: " + e.getMessage());
            forwardToJsp(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Xử lý yêu cầu POST giống như GET
        doGet(request, response);
    }

    // Chuyển tiếp đến trang JSP phù hợp với URL
    private void forwardToJsp(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String servletPath = request.getServletPath(); // Lấy URL (ví dụ: /home)
        String jspPage;
        switch (servletPath) {
            case "/home":
                jspPage = "Home.jsp";
                break;
            case "/about":
                jspPage = "About.jsp";
                break;
            case "/course":
                jspPage = "Course.jsp";
                break;
            case "/teacher":
                jspPage = "Teacher.jsp";
                break;
            
            default:
                jspPage = "Home.jsp"; // Mặc định là trang chủ
                break;
        }
        System.out.println("Chuyển tiếp đến: " + jspPage);
        request.getRequestDispatcher("/" + jspPage).forward(request, response);
    }
}