package controller;

import dal.CenterInfoDAO;
import dal.DocumentDAO;
import dal.SubjectDAO;
import dal.TutoringClassDAO;
import entity.Document;
import entity.Subject;
import entity.TutoringClass;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet({"/home", "/about", "/course", "/teacher"})
public class HomeServlet extends HttpServlet {

    // Khởi tạo các DAO
    private CenterInfoDAO centerInfoDAO = new CenterInfoDAO();
    private SubjectDAO subjectDAO = new SubjectDAO();
    private DocumentDAO documentDAO = new DocumentDAO();
    private TutoringClassDAO tutoringClassDAO = new TutoringClassDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("HomeServlet được gọi tại: " + request.getRequestURI());

        try {
            // Lấy thông tin người dùng từ session
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            String userName = (user == null) ? "Khách" : user.getName();
            String userAvatar = (user == null) ? "/images/default-avatar.png" : user.getAvatar();

            // 1. Lấy thông tin trung tâm
            Map<String, String> centerInfo = centerInfoDAO.getCenterInfo(1);
            if (centerInfo != null) {
                request.setAttribute("centerName", centerInfo.get("NameCenter"));
                request.setAttribute("address", centerInfo.get("AddressCenter"));
                request.setAttribute("email", centerInfo.get("Email"));
                request.setAttribute("phone", centerInfo.get("Phone"));
            } else {
                System.out.println("Không tìm thấy bản ghi với CenterID = 1");
                request.setAttribute("error", "Không tìm thấy thông tin trung tâm");
            }

            // 2. Lấy danh sách môn học
            List<Subject> subjectList = subjectDAO.getAllSubjects();
            List<Map<String, String>> subjects = new ArrayList<>();
            for (Subject subj : subjectList) {
                Map<String, String> subject = new HashMap<>();
                subject.put("id", String.valueOf(subj.getSubjectId()));
                subject.put("name", subj.getSubjectName());
                subjects.add(subject);
            }
            request.setAttribute("subjects", subjects);
            System.out.println("Số môn học lấy được: " + subjects.size());

            // 3. Lấy danh sách tài liệu
            List<Document> documentList = documentDAO.getDocumentsByGradeAndSubject(0, 0);
            Map<String, List<Map<String, String>>> documents = new HashMap<>();
            for (Document doc : documentList) {
                String subjectId = String.valueOf(doc.getSubjectId());
                Map<String, String> docMap = new HashMap<>();
                docMap.put("id", String.valueOf(doc.getDocumentId()));
                docMap.put("name", doc.getTitle());
                docMap.put("url", doc.getPdfPath()); // Sử dụng PDFPath thay vì FilePath
                documents.computeIfAbsent(subjectId, k -> new ArrayList<>()).add(docMap);
            }
            request.setAttribute("documents", documents);
            System.out.println("Số tài liệu lấy được: " + documents.values().stream().mapToInt(List::size).sum());

            // 4. Lấy danh sách khóa học
            List<TutoringClass> classList = tutoringClassDAO.getAllClasses();
            Map<String, List<Map<String, String>>> classes = new HashMap<>();
            for (TutoringClass cls : classList) {
                String subjectId = String.valueOf(cls.getSubjectID());
                Map<String, String> clsMap = new HashMap<>();
                clsMap.put("id", String.valueOf(cls.getTutoringClassID()));
                clsMap.put("name", cls.getClassName());
                classes.computeIfAbsent(subjectId, k -> new ArrayList<>()).add(clsMap);
            }
            request.setAttribute("classes", classes);
            System.out.println("Số khóa học lấy được: " + classes.values().stream().mapToInt(List::size).sum());

            // Lưu userName và userAvatar vào session
            session.setAttribute("userName", userName);
            session.setAttribute("userAvatar", userAvatar);

            // Chuyển tiếp đến JSP
            forwardToJsp(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi tải dữ liệu: " + e.getMessage());
            forwardToJsp(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private void forwardToJsp(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String servletPath = request.getServletPath();
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
                jspPage = "Home.jsp";
                break;
        }
        System.out.println("Chuyển tiếp đến: " + jspPage);
        request.getRequestDispatcher("/" + jspPage).forward(request, response);
    }
}