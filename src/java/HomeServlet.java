import dal.DBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
// Văn Thị Như - HE181329

@WebServlet({"/home", "/about", "/course", "/teacher"})
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("HomeServlet được gọi tại: " + request.getRequestURI());

        try (Connection conn = new DBContext().connection) {
            if (conn == null) {
                System.out.println("Lỗi: Kết nối cơ sở dữ liệu là null");
                request.setAttribute("error", "Không thể kết nối đến cơ sở dữ liệu");
                forwardToJsp(request, response);
                return;
            }
            System.out.println("Kết nối cơ sở dữ liệu thành công");

            // 1. Lấy thông tin trung tâm (KHÔNG bao gồm logo nữa)
            try (PreparedStatement ps = conn.prepareStatement("SELECT Name, Address, Email, Phone FROM CenterInfo WHERE CenterID = 1");
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    request.setAttribute("centerName", rs.getString("Name"));
                    request.setAttribute("address", rs.getString("Address"));
                    request.setAttribute("email", rs.getString("Email"));
                    request.setAttribute("phone", rs.getString("Phone"));
                } else {
                    System.out.println("Không tìm thấy bản ghi với CenterID = 1");
                    request.setAttribute("error", "Không tìm thấy thông tin trung tâm");
                }
            }

            // 2. Lấy danh sách môn học
            try (PreparedStatement ps = conn.prepareStatement("SELECT SubjectId, SubjectName FROM Subject");
                 ResultSet rs = ps.executeQuery()) {
                List<Map<String, String>> subjects = new ArrayList<>();
                while (rs.next()) {
                    Map<String, String> subject = new HashMap<>();
                    subject.put("id", rs.getString("SubjectId"));
                    subject.put("name", rs.getString("SubjectName"));
                    subjects.add(subject);
                }
                request.setAttribute("subjects", subjects);
                System.out.println("Số môn học lấy được: " + subjects.size());
            }

            // 3. Lấy danh sách tài liệu
            try (PreparedStatement ps = conn.prepareStatement("SELECT DocumentId, Title, FilePath, SubjectId FROM Document");
                 ResultSet rs = ps.executeQuery()) {
                Map<String, List<Map<String, String>>> documents = new HashMap<>();
                while (rs.next()) {
                    String subjectId = rs.getString("SubjectId");
                    Map<String, String> doc = new HashMap<>();
                    doc.put("id", rs.getString("DocumentId"));
                    doc.put("name", rs.getString("Title"));
                    doc.put("url", rs.getString("FilePath"));
                    documents.computeIfAbsent(subjectId, k -> new ArrayList<>()).add(doc);
                }
                request.setAttribute("documents", documents);
                System.out.println("Số tài liệu lấy được: " + documents.values().stream().mapToInt(List::size).sum());
            }

            // 4. Lấy danh sách khóa học
            try (PreparedStatement ps = conn.prepareStatement("SELECT TutoringClassID, ClassName, SubjectId FROM TutoringClass");
                 ResultSet rs = ps.executeQuery()) {
                Map<String, List<Map<String, String>>> classes = new HashMap<>();
                while (rs.next()) {
                    String subjectId = rs.getString("SubjectId");
                    Map<String, String> cls = new HashMap<>();
                    cls.put("id", rs.getString("TutoringClassID"));
                    cls.put("name", rs.getString("ClassName"));
                    classes.computeIfAbsent(subjectId, k -> new ArrayList<>()).add(cls);
                }
                request.setAttribute("classes", classes);
                System.out.println("Số khóa học lấy được: " + classes.values().stream().mapToInt(List::size).sum());
            }

            // Chuyển tiếp đến JSP
            forwardToJsp(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi cơ sở dữ liệu: " + e.getMessage());
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
