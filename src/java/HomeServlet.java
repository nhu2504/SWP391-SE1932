/*
 * Tác giả: Van Nhu
 * Ngày tạo: 23/06/2025
 * Mô tả: Servlet xử lý logic cho các trang Home, About, Course, Teacher của hệ thống trung tâm dạy thêm Edura.
 */

import dal.*;
import entity.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.*;
import java.sql.Time;

@WebServlet({"/home", "/about", "/course", "/teacher"})
public class HomeServlet extends HttpServlet {

    // Khởi tạo các DAO để truy xuất dữ liệu
    private final CenterInfoDAO centerInfoDAO = new CenterInfoDAO();
    private final GradeDAO gradeDAO = new GradeDAO();
    private final SubjectDAO subjectDAO = new SubjectDAO();
    private final DocumentDAO documentDAO = new DocumentDAO();
    private final TutoringClassDAO tutoringClassDAO = new TutoringClassDAO();
    private final TeacherDAO teacherDAO = new TeacherDAO();
    private final StudentDAO studentDAO = new StudentDAO();
    private final SchoolDAO schoolDAO = new SchoolDAO();
    private final BannerDAO bannerDAO = new BannerDAO();
    private final RoomDAO roomDAO = new RoomDAO();
    private final ShiftDAO shiftDAO = new ShiftDAO();
    private final ClassGroupDAO classGroupDAO = new ClassGroupDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // 1. Lấy thông tin trung tâm và gán vào request
            Map<String, String> centerInfo = centerInfoDAO.getCenterInfo(1);
            if (centerInfo != null) {
                request.setAttribute("centerName", centerInfo.get("NameCenter"));
                request.setAttribute("address", centerInfo.get("AddressCenter"));
                request.setAttribute("email", centerInfo.get("Email"));
                request.setAttribute("phone", centerInfo.get("Phone"));
                request.setAttribute("descripCenter", centerInfo.get("DescripCenter"));
            }

            // 2. Lấy danh sách khối lớp và môn học
            List<Grade> grades = gradeDAO.getAllGrades();
            List<Subject> subjects = subjectDAO.getSubjectsWithClassCount();
            request.setAttribute("grades", grades);
            request.setAttribute("subjects", subjects);

            // Tạo map tra cứu tên khối và môn học theo ID
            Map<Integer, String> gradeNames = new HashMap<>();
            for (Grade g : grades) {
                gradeNames.put(g.getGradeID(), g.getGradeName());
            }
            request.setAttribute("gradeNames", gradeNames);

            Map<Integer, String> subjectNames = new HashMap<>();
            for (Subject s : subjects) {
                subjectNames.put(s.getSubjectId(), s.getSubjectName());
            }
            request.setAttribute("subjectNames", subjectNames);

            // 3. Lọc tài liệu theo khối và môn học nếu có
            int gradeId = parseIntOrDefault(request.getParameter("gradeId"), 0);
            int subjectId = parseIntOrDefault(request.getParameter("subjectId"), 0);
            request.setAttribute("documents", documentDAO.getDocumentsByGradeAndSubject(gradeId, subjectId));

            // 4. Lấy danh sách tất cả các lớp học
            request.setAttribute("classes", tutoringClassDAO.getAllClasses());

            // 5. Lấy banner và các thông tin hoạt động trung tâm
            request.setAttribute("banners", bannerDAO.getAllBanners());
            int establishmentYear = centerInfoDAO.getYearOfWork();
            int yearsActive = Year.now().getValue() - establishmentYear;
            request.setAttribute("yearsActive", yearsActive);
            request.setAttribute("studentCount", centerInfoDAO.getStudentCount());
            request.setAttribute("partnerSchoolsCount", centerInfoDAO.getPartnerSchoolsCount());

            // 6. Khóa học nổi bật và quanh năm
            List<TutoringClass> featuredTutoringClasses = tutoringClassDAO.getFeaturedTutoringClasses();
            List<TutoringClass> yearRoundTutoringClasses = tutoringClassDAO.getYearRoundTutoringClasses();
            request.setAttribute("featuredTutoringClasses", featuredTutoringClasses);
            request.setAttribute("yearRoundTutoringClasses", yearRoundTutoringClasses);

            // 7. Lấy danh sách ca học và format thời gian
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            List<Shift> allShifts = shiftDAO.getAllShifts();
            Map<Integer, String> shiftStartTimes = new HashMap<>();
            Map<Integer, String> shiftEndTimes = new HashMap<>();
            Map<Integer, Shift> shiftMap = new HashMap<>();
            for (Shift s : allShifts) {
                String formattedStart = s.getStartTime() != null ? timeFormat.format(s.getStartTime()) : "N/A";
                String formattedEnd = s.getEndTime() != null ? timeFormat.format(s.getEndTime()) : "N/A";
                shiftStartTimes.put(s.getId(), formattedStart);
                shiftEndTimes.put(s.getId(), formattedEnd);
                shiftMap.put(s.getId(), s);
            }
            request.setAttribute("shiftStartTimes", shiftStartTimes);
            request.setAttribute("shiftEndTimes", shiftEndTimes);
            request.setAttribute("shiftMap", shiftMap);

            // 8. Duyệt tất cả các lớp học nổi bật & quanh năm để xử lý group và thời lượng học
            List<TutoringClass> allTutoringClasses = new ArrayList<>();
            allTutoringClasses.addAll(featuredTutoringClasses);
            allTutoringClasses.addAll(yearRoundTutoringClasses);

            Map<Integer, String> groupStringMap = new HashMap<>();
            Map<Integer, String> durationMap = new HashMap<>();
            Set<Integer> addedClassIds = new HashSet<>();

            for (TutoringClass tc : allTutoringClasses) {
                int tutoringClassId = tc.getTutoringClassID();
                if (addedClassIds.contains(tutoringClassId)) continue;
                addedClassIds.add(tutoringClassId);

                List<Object[]> groupList = classGroupDAO.getClassGroupsWithRoomAndShift(tutoringClassId);
                StringBuilder sb = new StringBuilder();
                String duration = "Chưa xác định";

                if (groupList != null && !groupList.isEmpty()) {
                    for (Object[] g : groupList) {
                        String thu;
                        if (g[6] != null) {
                            LocalDate date = ((java.sql.Date) g[6]).toLocalDate();
                            DayOfWeek dow = date.getDayOfWeek();
                            thu = dow.getDisplayName(TextStyle.FULL, new Locale("vi", "VN"));
                        } else {
                            thu = "null";
                        }

                        sb.append(g[0]).append("~")  // groupName
                          .append(g[1]).append("~")  // maxStudent
                          .append(g[2]).append("~")  // roomName
                          .append(g[3] != null ? g[3] : "Không xác định").append("~")  // teacherName
                          .append(thu).append("~")  // thứ
                          .append(g[4] != null ? g[4] : "N/A").append("~") // startTime
                          .append(g[5] != null ? g[5] : "N/A").append(";"); // endTime
                    }

                    // Tính thời lượng học từ shift đầu tiên
                    Object[] firstGroup = groupList.get(0);
                    String startStr = firstGroup[4] != null ? firstGroup[4].toString().trim() : null;
                    String endStr = firstGroup[5] != null ? firstGroup[5].toString().trim() : null;

                    if (startStr != null && startStr.contains(".")) startStr = startStr.split("\\.")[0];
                    if (endStr != null && endStr.contains(".")) endStr = endStr.split("\\.")[0];
                    if (startStr != null && startStr.matches("\\d{2}:\\d{2}")) startStr += ":00";
                    if (endStr != null && endStr.matches("\\d{2}:\\d{2}")) endStr += ":00";

                    try {
                        if (startStr != null && endStr != null &&
                            startStr.matches("\\d{2}:\\d{2}:\\d{2}") &&
                            endStr.matches("\\d{2}:\\d{2}:\\d{2}")) {
                            Time startSqlTime = Time.valueOf(startStr);
                            Time endSqlTime = Time.valueOf(endStr);
                            Shift shift = new Shift(startSqlTime, endSqlTime);
                            duration = shift.getDurationText();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                groupStringMap.put(tutoringClassId, sb.toString());
                durationMap.put(tutoringClassId, duration);
            }

            request.setAttribute("groupStringMap", groupStringMap);
            request.setAttribute("durationMap", durationMap);

            // 9. Map tên phòng học và tên giáo viên
            Map<Integer, String> roomNames = new HashMap<>();
            for (Room r : roomDAO.getAllRooms()) {
                roomNames.put(r.getId(), r.getName());
            }
            request.setAttribute("roomNames", roomNames);

            Map<Integer, String> teacherNames = new HashMap<>();
            for (User u : teacherDAO.getAllTeachers()) {
                teacherNames.put(u.getId(), u.getName());
            }
            request.setAttribute("teacherNames", teacherNames);

            // 10. Lấy danh sách giáo viên và trường liên kết
            List<User> teachers = teacherDAO.getAllTeachers();
            request.setAttribute("teachers", teachers);
            Map<Integer, String> teacherSchoolNames = new HashMap<>();
            for (User teacher : teachers) {
                String schoolName = schoolDAO.getSchoolNameById(teacher.getSchoolID());
                teacherSchoolNames.put(teacher.getId(), (schoolName == null || schoolName.trim().isEmpty()) ? "Giáo viên của Edura" : schoolName);
            }
            request.setAttribute("teacherSchoolNames", teacherSchoolNames);

            // 11. Lấy học sinh nổi bật và danh sách trường học
            request.setAttribute("students", studentDAO.getTopStudents());
            request.setAttribute("schools", schoolDAO.getAllSchools());

            // 12. Nếu có tham số courseId thì lấy chi tiết lớp học
            String courseIdParam = request.getParameter("courseId");
            TutoringClass selectedTutoringClass = null;
            List<ClassGroup> selectedClassGroups = new ArrayList<>();
            String selectedGradeName = "";

            if (courseIdParam != null) {
                int tutoringClassID = parseIntOrDefault(courseIdParam, -1);
                if (tutoringClassID > 0) {
                    selectedTutoringClass = tutoringClassDAO.getTutoringClassDetail(tutoringClassID);
                    if (selectedTutoringClass != null) {
                        selectedClassGroups = classGroupDAO.getClassGroupsByTutoringClassId(tutoringClassID);
                        selectedGradeName = gradeNames.get(selectedTutoringClass.getGradeID());
                    }
                }
            }
            request.setAttribute("selectedTutoringClass", selectedTutoringClass);
            request.setAttribute("selectedClassGroups", selectedClassGroups);
            request.setAttribute("selectedGradeName", selectedGradeName);

            // 13. Điều hướng đến JSP tương ứng
            forwardToJsp(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi: " + e.getMessage());
            forwardToJsp(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    // Chuyển tiếp đến trang JSP tương ứng với URL
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
            case "/admin":
                jspPage = "admin_dashboard.jsp";
                break;
            default:
                jspPage = "Home.jsp";
                break;
        }
        request.getRequestDispatcher("/" + jspPage).forward(request, response);
    }

    // Hàm parse số nguyên an toàn, nếu lỗi thì trả về mặc định
    private int parseIntOrDefault(String value, int def) {
        try {
            return value == null ? def : Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return def;
        }
    }
}
