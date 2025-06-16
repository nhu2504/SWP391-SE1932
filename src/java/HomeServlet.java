
import dal.*;
import entity.Banner;
import entity.Document;
import entity.Grade;
import entity.TutoringClass;
import entity.School;
import entity.Subject;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Văn Thị NHư - HE181329

// Định nghĩa các URL mà servlet này sẽ xử lý
@WebServlet({"/home", "/about", "/course", "/teacher"})
public class HomeServlet extends HttpServlet {

    // Khởi tạo các DAO để lấy dữ liệu từ database
    private final CenterInfoDAO centerInfoDAO = new CenterInfoDAO();
    private final GradeDAO gradeDAO = new GradeDAO();
    private final SubjectDAO subjectDAO = new SubjectDAO();
    private final DocumentDAO documentDAO = new DocumentDAO();
    private final TutoringClassDAO tutoringClassDAO = new TutoringClassDAO();
    private final TeacherDAO teacherDAO = new TeacherDAO();
    private final StudentDAO studentDAO = new StudentDAO();

    private final SchoolDAO schoolDAO = new SchoolDAO();
    private final BannerDAO dao = new BannerDAO();

    // Hàm này đảm bảo tất cả các phần tử trong list có "isHot" là boolean
    private List<Map<String, Object>> ensureIsHotBoolean(List<Map<String, Object>> courses, String listName) {
        if (courses == null) {
            return new ArrayList<>();
        }
        int idx = 0;
        for (Map<String, Object> course : courses) {
            Object tcObj = course.get("tutoringClass");
            Boolean isHot = null;
            if (tcObj instanceof TutoringClass) {
                isHot = ((TutoringClass) tcObj).isIsHot(); // hoặc .isHot() nếu bạn sửa entity
            } else {
                Object isHotObj = course.get("isHot");
                if (isHotObj instanceof Boolean) {
                    isHot = (Boolean) isHotObj;
                } else if (isHotObj instanceof Number) {
                    isHot = ((Number) isHotObj).intValue() == 1;
                } else if (isHotObj instanceof String) {
                    try {
                        isHot = Integer.parseInt((String) isHotObj) == 1;
                    } catch (Exception e) {
                        isHot = false;
                    }
                } else {
                    isHot = false;
                }
            }
            course.put("isHot", isHot != null ? isHot : false);

            // DEBUG: In giá trị và kiểu của isHot từng phần tử
            Object debugIsHot = course.get("isHot");
            System.out.println("[" + listName + "][" + idx + "] isHot = " + debugIsHot + ", class = " + (debugIsHot == null ? "null" : debugIsHot.getClass().getName()));
            idx++;
        }
        return courses;
    }

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
            List<Grade> grades = gradeDAO.getAllGrades();
            request.setAttribute("grades", grades);
            System.out.println("Số lượng khối lớp: " + grades.size());
            if (grades != null) {
                for (Grade g : grades) {
                    System.out.println("Khối lớp: " + g.getGradeID() + " - " + g.getGradeName());
                }
            } else {
                System.out.println("grades là null sau khi lấy từ GradeDAO.");
            }
            // 2. Lấy danh sách môn học
            List<Map<String, String>> subjects = subjectDAO.getAllSubjects();
            request.setAttribute("subjects", subjects);
            System.out.println("Số môn học lấy được: " + subjects.size());

            // 3. Lấy danh sách tài liệu
            String gradeIdRaw = request.getParameter("gradeId");
            String subjectIdRaw = request.getParameter("subjectId");

            int gradeId = 0;
            int subjectId = 0;
            try {
                if (gradeIdRaw != null && !gradeIdRaw.isEmpty()) {
                    gradeId = Integer.parseInt(gradeIdRaw);
                }
                if (subjectIdRaw != null && !subjectIdRaw.isEmpty()) {
                    subjectId = Integer.parseInt(subjectIdRaw);
                }
            } catch (NumberFormatException e) {
                // Xử lý lỗi nếu cần, ví dụ log hoặc set giá trị mặc định
                System.out.println("Tham số không hợp lệ: gradeId=" + gradeIdRaw + ", subjectId=" + subjectIdRaw);
                // Có thể set giá trị mặc định hoặc chuyển hướng lỗi
            }

            List<Document> documents = documentDAO.getDocumentsByGradeAndSubject(gradeId, subjectId);
            request.setAttribute("documents", documents);
            System.out.println("Số tài liệu lấy được: " + documents.size());
            // 4. Lấy danh sách khóa học
            List<TutoringClass> classes = tutoringClassDAO.getAllClasses();
            request.setAttribute("classes", classes);
            System.out.println("Số khóa học lấy được: " + classes.size());
            //
            List<Banner> banners = dao.getAllBanners(); // Lấy các banner có bannerID <> 1
            request.setAttribute("banners", banners);
            //
            int establishmentYear = centerInfoDAO.getYearOfWork();
        int currentYear = Year.now().getValue(); // 2025
        int yearsActive = currentYear - establishmentYear;
        int studentCount = centerInfoDAO.getStudentCount(); // Tổng số học sinh
        int partnerSchoolsCount = centerInfoDAO.getPartnerSchoolsCount(); // Tổng số trường liên kết
        request.setAttribute("yearsActive", yearsActive);
        request.setAttribute("studentCount", studentCount);
        request.setAttribute("partnerSchoolsCount", partnerSchoolsCount);

            // Lấy danh sách khóa học nổi bật
            List<Map<String, Object>> featuredTutoringClasses = tutoringClassDAO.getFeaturedTutoringClasses();
            featuredTutoringClasses = ensureIsHotBoolean(featuredTutoringClasses, "featuredTutoringClasses");
            for (Map<String, Object> course : featuredTutoringClasses) {
                int tutoringClassID = ((TutoringClass) course.get("tutoringClass")).getTutoringClassID();
                Map<String, Object> detail = tutoringClassDAO.getTutoringClassDetail(tutoringClassID);
                course.put("classGroups", detail != null ? detail.get("classGroups") : new ArrayList<>());

            }
            request.setAttribute("featuredTutoringClasses", featuredTutoringClasses);
            System.out.println("Số khóa học nổi bật lấy được: " + featuredTutoringClasses.size());

            // Lấy danh sách khóa học quanh năm
            List<Map<String, Object>> yearRoundTutoringClasses = tutoringClassDAO.getYearRoundTutoringClasses();
            yearRoundTutoringClasses = ensureIsHotBoolean(yearRoundTutoringClasses, "yearRoundTutoringClasses");
            for (Map<String, Object> course : yearRoundTutoringClasses) {
                int tutoringClassID = ((TutoringClass) course.get("tutoringClass")).getTutoringClassID();
                Map<String, Object> detail = tutoringClassDAO.getTutoringClassDetail(tutoringClassID);
                course.put("classGroups", detail != null ? detail.get("classGroups") : new ArrayList<>());

            }
            request.setAttribute("yearRoundTutoringClasses", yearRoundTutoringClasses);
            System.out.println("Số khóa học quanh năm lấy được: " + yearRoundTutoringClasses.size());

            // 6. Lấy danh sách ảnh môn học
            List<Subject> subjectImages = subjectDAO.getSubjectImages(request.getContextPath());
            request.setAttribute("subjectImages", subjectImages);
            System.out.println("Số môn học với ảnh lấy được: " + subjectImages.size());

            // 7. Lấy danh sách giáo viên
            List<User> teachers = teacherDAO.getAllTeachers();
            Map<Integer, String> teacherSchoolNames = new HashMap<>();
            for (User teacher : teachers) {
                String schoolName = schoolDAO.getSchoolNameById(teacher.getSchoolID());
                if (schoolName == null || schoolName.trim().isEmpty()) {
                    schoolName = "Giáo viên của Edura";
                }
                teacherSchoolNames.put(teacher.getId(), schoolName);
                System.out.println("Teacher id: " + teacher.getId() + ", schoolName: " + schoolName);
            }
            request.setAttribute("teachers", teachers);
            request.setAttribute("teacherSchoolNames", teacherSchoolNames);
            

            // 8. Lấy danh sách học sinh nổi bật (top 10)
            List<Map<String, Object>> students = studentDAO.getTopStudents();
            request.setAttribute("students", students);
            System.out.println("Số học sinh lấy được: " + students.size());

            // 9. Lấy danh sách các trường liên kết
            List<School> schools = schoolDAO.getAllSchools();
            request.setAttribute("schools", schools);

            // 13. Lấy chi tiết khóa học nếu có courseId
            String courseIdParam = request.getParameter("courseId");
            Map<String, Object> courseDetail = new HashMap<>();
            courseDetail.put("classGroups", new ArrayList<>());
            if (courseIdParam != null) {
                try {
                    int tutoringClassID = Integer.parseInt(courseIdParam);
                    courseDetail = tutoringClassDAO.getTutoringClassDetail(tutoringClassID);
                    if (courseDetail != null && courseDetail.containsKey("tutoringClass") && courseDetail.get("tutoringClass") instanceof TutoringClass) {
                        TutoringClass tc = (TutoringClass) courseDetail.get("tutoringClass");

                        gradeId = tc.getGradeID();
                        String gradeName = gradeDAO.getGradeNameById(gradeId); // <-- Tra cứu tên khối

                        courseDetail.put("isHot", tc.isIsHot());
                        courseDetail.put("gradeName", gradeName);

                        courseDetail.put("subjectName", courseDetail.get("subjectName"));
                        courseDetail.put("maxStudents", tc.getMaxStudent());
                        courseDetail.put("startTime", courseDetail.get("startTime"));
                        courseDetail.put("endTime", courseDetail.get("endTime"));
                        courseDetail.put("price", tc.getPrice());
                        courseDetail.put("startDate", tc.getStartDate());
                        courseDetail.put("endDate", tc.getEndDate());
                        System.out.println("Servlet - selectedCourseDetail - isHot: " + courseDetail.get("isHot") + ", tutoringClass.isHot: " + tc.isIsHot() + ", gradeName: " + gradeName);
                    } else {
                        System.out.println("No tutoringClass found for ID: " + tutoringClassID);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid courseId: " + courseIdParam);
                }
            }
            request.setAttribute("selectedCourseDetail", courseDetail);            // Chuyển tiếp đến JSP
            forwardToJsp(request, response);
        } catch (Exception e) {
            // Nếu có lỗi (ví dụ: lỗi database), in lỗi và hiển thị thông báo
            e.printStackTrace();
            request.setAttribute("error", "Lỗi: " + e.getMessage());
            request.setAttribute("selectedCourseDetail", new HashMap<>());
            ((Map<String, Object>) request.getAttribute("selectedCourseDetail")).put("classGroups", new ArrayList<>());
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
