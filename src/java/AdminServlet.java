
import dal.BannerDAO;
import dal.CenterInfoDAO;
import dal.ClassGroupDAO;
import dal.ClassGroup_StudentDAO;
import dal.GradeDAO;
import dal.PaymentDAO;
import dal.ReportDAO;
import dal.RoomDAO;
import dal.ScheduleDAO;
import dal.SchoolClassDAO;
import dal.SchoolDAO;
import dal.ShiftLearnDAO;
import dal.StudentDAO;
import dal.SubjectDAO;
import dal.TeacherDAO;
import dal.TutoringClassDAO;
import entity.CenterInfo;
import entity.ClassGroup;
import entity.ScheduleTemplate;
import entity.TutoringClass;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import com.google.gson.Gson;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;

/**
 * Ngày update: 23/07/2025 Người viết: Văn Thị Như
 *
 */
@MultipartConfig
@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    private final CenterInfoDAO centerInfoDAO = new CenterInfoDAO();
    private final BannerDAO bannerDAO = new BannerDAO();
    private final ClassGroupDAO classGroupDAO = new ClassGroupDAO();
    private final TeacherDAO teacherDAO = new TeacherDAO();
    private final StudentDAO studentDAO = new StudentDAO();
    private final TutoringClassDAO tutoringClassDAO = new TutoringClassDAO();
    private final ScheduleDAO scheduleDAO = new ScheduleDAO();
    private final SubjectDAO subjectDAO = new SubjectDAO();
    private final ShiftLearnDAO shiftLearnDAO = new ShiftLearnDAO();
    private final GradeDAO gradeDAO = new GradeDAO();
    private final RoomDAO roomDAO = new RoomDAO();
    private final SchoolDAO schoolDAO = new SchoolDAO();
    private final SchoolClassDAO schoolClassDAO = new SchoolClassDAO();
    private final ClassGroup_StudentDAO classGroup_studentDAO = new ClassGroup_StudentDAO();
    private final ReportDAO reportDAO = new ReportDAO();

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static final String EXTERNAL_IMG_DIR = "D:/data/images";  // Đường dẫn lưu ảnh khóa học

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || !"1".equals(String.valueOf(session.getAttribute("userRoleID")))) {
            res.sendRedirect(req.getContextPath() + "/login_register.jsp");
            return;
        }
        // Gọi cập nhật trạng thái tự động
        tutoringClassDAO.updateAutoActiveStatus();

        // Tự động mở rộng lịch học nếu còn thiếu
        List<ClassGroup> activeGroups = classGroupDAO.getActiveClassGroups();
        for (ClassGroup group : activeGroups) {
            try {
                autoExtendScheduleIfNeeded(group.getClassGroupId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Lấy lại dữ liệu lỗi nếu có 
        HttpSession ses = req.getSession();
        if (ses.getAttribute("modalError") != null) {
            req.setAttribute("modalError", ses.getAttribute("modalError"));
            req.setAttribute("cModal", ses.getAttribute("cModal"));
            req.setAttribute("errorList", ses.getAttribute("errorList"));
            ses.removeAttribute("modalError");
            ses.removeAttribute("cModal");
            ses.removeAttribute("errorList");
        }

        try {
            String tab = Optional.ofNullable(req.getParameter("tab")).orElse("overview");
            req.setAttribute("tab", tab);

            CenterInfo center = centerInfoDAO.getCenterInfo(1);
            if (center != null) {
                req.setAttribute("centerName", center.getNameCenter());
                req.setAttribute("descripCenter", center.getDescrip());
                req.setAttribute("address", center.getAddress());
                req.setAttribute("phone", center.getPhone());
                req.setAttribute("email", center.getEmail());
                req.setAttribute("website", center.getWebsite());
            }
            switch (tab) {
                case "overview":

                    String selectedYearStr = req.getParameter("selectedYear");
                    int selectedYear = selectedYearStr != null ? Integer.parseInt(selectedYearStr) : LocalDate.now().getYear();
                    req.setAttribute("selectedYear", selectedYear);

                    // Lấy danh sách năm (năm hoạt động → hiện tại)
                    List<Integer> yearList = reportDAO.getAvailableYears();
                    req.setAttribute("yearList", yearList);

                    // Lấy dữ liệu tăng trưởng
                    Map<Integer, Integer> classGrowth = reportDAO.getClassCountPerMonth(selectedYear);
                    Map<Integer, Integer> studentGrowth = reportDAO.getStudentCountPerMonth(selectedYear);
                    req.setAttribute("classGrowth", classGrowth);
                    req.setAttribute("studentGrowth", studentGrowth);

                    /// Nếu là AJAX request
                    if ("XMLHttpRequest".equals(req.getHeader("X-Requested-With"))) {
                        res.setContentType("application/json");
                        res.setCharacterEncoding("UTF-8");

                        Gson gson = new Gson();
                        Map<String, Map<Integer, Integer>> responseMap = new HashMap<>();
                        responseMap.put("classGrowth", classGrowth);
                        responseMap.put("studentGrowth", studentGrowth);
                        String jsonResponse = gson.toJson(responseMap);

                        try (PrintWriter out = res.getWriter()) {
                            out.print(jsonResponse);
                            out.flush();
                        }
                        return;
                    }
                    LocalDate weekStart1 = LocalDate.now().with(DayOfWeek.MONDAY);

                    req.setAttribute("openClassCount", classGroupDAO.getOpeningClassGroupsByCourseDate().size());
                    req.setAttribute("totalTeachers", teacherDAO.getAllTeachers().size());
                    req.setAttribute("studentCount", studentDAO.getStudentCount());
                    req.setAttribute("classesCount", tutoringClassDAO.getTutoringClassesByHotAndStatus(null).size());
                    req.setAttribute("todaySchedules", scheduleDAO.getTodaySchedules());
                    req.setAttribute("studentCounts", studentDAO.getStudentCountPerSubject());
                    req.setAttribute("teacherSchedules", teacherDAO.getTeacherSchedulesToday());
                    req.setAttribute("weekdays", scheduleDAO.getWeekdayMap());
                    req.setAttribute("weeklySchedules", scheduleDAO.getWeeklySchedule(weekStart1));

                    break;
                case "courseManagement":
                    String idRaw = req.getParameter("id");

                    if (idRaw != null && !idRaw.isEmpty()) {
                        try {
                            int id = Integer.parseInt(idRaw);
                            TutoringClass c = tutoringClassDAO.getTutoringClassDetail(id);
                            req.setAttribute("c", c);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }

                    req.setAttribute("data", tutoringClassDAO.getClasses());
                    req.setAttribute("subjects", subjectDAO.getAllSubjects());
                    req.setAttribute("grades", gradeDAO.getAllGrades());

                    break;

                case "scheduleClass":
                    Map.Entry<List<LocalDate>, Map<LocalDate, String>> scheduleData = generateWeekStartListAndMap();
                    req.setAttribute("weekStartList", scheduleData.getKey());
                    req.setAttribute("weekDisplayMap", scheduleData.getValue());

                    String weekStartStr = req.getParameter("weekStart");
                    LocalDate weekStart = (weekStartStr != null && !weekStartStr.isEmpty())
                            ? LocalDate.parse(weekStartStr)
                            : LocalDate.now().with(DayOfWeek.MONDAY);

                    req.setAttribute("now", LocalDate.now());
                    req.setAttribute("selectedWeekStart", weekStart);
                    req.setAttribute("weeklySchedules", scheduleDAO.getWeeklySchedule(weekStart));
                    req.setAttribute("weekdays", scheduleDAO.getWeekdayMap());
                    break;
                case "teacherSchedule":
                try {
                    Map.Entry<List<LocalDate>, Map<LocalDate, String>> teacherData = generateWeekStartListAndMap();
                    req.setAttribute("weekStartList", teacherData.getKey());
                    req.setAttribute("weekDisplayMap", teacherData.getValue());

                    String anyDateStr = req.getParameter("anyDate");
                    weekStartStr = req.getParameter("weekStart");
                    LocalDate weekStartDate;

                    if (anyDateStr != null && !anyDateStr.isEmpty()) {
                        weekStartDate = LocalDate.parse(anyDateStr).with(DayOfWeek.MONDAY);
                        req.setAttribute("anyDate", anyDateStr);
                    } else if (weekStartStr != null && !weekStartStr.isEmpty()) {
                        weekStartDate = LocalDate.parse(weekStartStr);
                    } else {
                        weekStartDate = LocalDate.now().with(DayOfWeek.MONDAY);
                    }

                    Date weekStartUtil = java.sql.Date.valueOf(weekStartDate);
                    Date weekEndUtil = java.sql.Date.valueOf(weekStartDate.plusDays(6));
                    List<Object[]> teacherSchedules = teacherDAO.getTeacherSchedulesBetween(weekStartUtil, weekEndUtil);

                    Map<String, List<Object[]>> teacherScheduleMap = new LinkedHashMap<>();
                    for (Object[] row : teacherSchedules) {
                        String teacherName = (String) row[0];
                        Object[] schedule = {row[1], row[2], row[3], row[4], row[5]};
                        teacherScheduleMap.computeIfAbsent(teacherName, k -> new ArrayList<>()).add(schedule);
                    }

                    Map<Integer, Date> weekDates = new LinkedHashMap<>();
                    for (int i = 0; i < 7; i++) {
                        LocalDate date = weekStartDate.plusDays(i);
                        int weekday = date.getDayOfWeek().getValue(); // 1–7
                        int adjusted = (weekday == 7) ? 8 : weekday + 1; // 2–8
                        weekDates.put(adjusted, java.sql.Date.valueOf(date));
                    }

                    List<Integer> weekOrder = Arrays.asList(2, 3, 4, 5, 6, 7, 8);
                    LocalDate today = LocalDate.now();
                    Date todaySql = java.sql.Date.valueOf(today);
                    Integer highlightIndex = null;

                    for (Map.Entry<Integer, Date> entry : weekDates.entrySet()) {
                        if (entry.getValue().equals(todaySql)) {
                            highlightIndex = entry.getKey();
                            break;
                        }
                    }

                    req.setAttribute("highlightColumnIndex", highlightIndex);
                    req.setAttribute("todayDate", todaySql);
                    req.setAttribute("teacherScheduleMap", teacherScheduleMap);
                    req.setAttribute("weekDates", weekDates);
                    req.setAttribute("weekOrder", weekOrder);
                    req.setAttribute("selectedWeekStart", weekStartUtil);
                    req.setAttribute("weekdays", scheduleDAO.getWeekdayMap());
                    req.setAttribute("shifts", shiftLearnDAO.getAllShifts());
                    req.setAttribute("subjectList", subjectDAO.getAllSubjects());

                } catch (Exception e) {
                    throw new ServletException("Lỗi xử lý lịch giáo viên theo tuần/ngày", e);
                }
                break;

                case "todaySchedule":
                try {
                    Map.Entry<List<LocalDate>, Map<LocalDate, String>> todayData = generateWeekStartListAndMap();
                    req.setAttribute("weekStartList", todayData.getKey());
                    req.setAttribute("weekDisplayMap", todayData.getValue());

                    String anyDateStr = req.getParameter("anyDate");
                    weekStartStr = req.getParameter("weekStart");
                    LocalDate weekStartDate;

                    if (anyDateStr != null && !anyDateStr.isEmpty()) {
                        weekStartDate = LocalDate.parse(anyDateStr).with(DayOfWeek.MONDAY);
                        req.setAttribute("anyDate", anyDateStr);
                    } else if (weekStartStr != null && !weekStartStr.isEmpty()) {
                        weekStartDate = LocalDate.parse(weekStartStr);
                    } else {
                        weekStartDate = LocalDate.now().with(DayOfWeek.MONDAY);
                    }

                    Date weekStartUtil = java.sql.Date.valueOf(weekStartDate);
                    req.setAttribute("selectedWeekStart", weekStartUtil);
                    req.setAttribute("shifts", shiftLearnDAO.getAllShifts());
                    req.setAttribute("weeklySchedules", scheduleDAO.getWeeklyScheduleByWeek(weekStartUtil));

                    Map<Integer, Date> weekdayDates = new LinkedHashMap<>();
                    for (int i = 0; i < 7; i++) {
                        LocalDate date = weekStartDate.plusDays(i);
                        int weekday = i == 6 ? 1 : i + 2;
                        weekdayDates.put(weekday, java.sql.Date.valueOf(date));
                    }

                    req.setAttribute("weekdays", scheduleDAO.getWeekdayMap());
                    req.setAttribute("weekdayDates", weekdayDates);
                    req.setAttribute("todayDate", java.sql.Date.valueOf(LocalDate.now()));

                } catch (Exception e) {
                    throw new ServletException("Invalid date", e);
                }
                break;

                case "courseList":
                    req.setAttribute("data", tutoringClassDAO.getClasses());
                    req.setAttribute("subjects", subjectDAO.getAllSubjects());
                    req.setAttribute("grades", gradeDAO.getAllGrades());
                    break;
                case "classManagement":
                    String action = req.getParameter("action");

                    if (ses.getAttribute("modalError") != null) {
                        req.setAttribute("modalError", ses.getAttribute("modalError"));
                        req.setAttribute("groupModal", ses.getAttribute("groupModal"));
                        req.setAttribute("selectedTeacher", ses.getAttribute("selectedTeacher"));
                        req.setAttribute("selectedDay", ses.getAttribute("selectedDay"));
                        req.setAttribute("selectedShift", ses.getAttribute("selectedShift"));
                        req.setAttribute("selectedRoom", ses.getAttribute("selectedRoom"));
                        req.setAttribute("errorList", ses.getAttribute("errorList"));

                        // Dọn session
                        ses.removeAttribute("modalError");
                        ses.removeAttribute("groupModal");
                        ses.removeAttribute("selectedTeacher");
                        ses.removeAttribute("selectedDay");
                        ses.removeAttribute("selectedShift");
                        ses.removeAttribute("selectedRoom");
                        ses.removeAttribute("errorList");
                    }

                    if ("activateGroup".equals(action)) {
                        try {
                            int groupId = Integer.parseInt(req.getParameter("groupId"));
                            int courseId = Integer.parseInt(req.getParameter("id"));
                            
                            // Lấy ngày bắt đầu khoá học
                            TutoringClass course = tutoringClassDAO.getTutoringClassDetail(courseId);
                            Date startDate = course.getStartDate();
                            Date today = new Date();

                            if (startDate != null && startDate.after(today)) {
                                setSuccessMessage(req, "❌ Không thể kích hoạt lớp vì khóa học chưa bắt đầu!");
                            } else {
                                List<ScheduleTemplate> templates = scheduleDAO.getTemplatesByGroupId(groupId);
                                int sessionCount = 10;
                                scheduleDAO.insertSchedulesFromTemplate(groupId, templates, today, sessionCount);
                                classGroupDAO.activateClassGroup(groupId);
                                setSuccessMessage(req, "✔ Kích hoạt lớp học thành công!");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            setSuccessMessage(req, "❌ Kích hoạt thất bại!");
                        }
                    }
                    String courseIdRaw = req.getParameter("id");
                    if (courseIdRaw != null && !courseIdRaw.isEmpty()) {
                        try {
                            int courseId = Integer.parseInt(courseIdRaw);
                            prepareClassManagementData(req, courseId); //dùng lại hàm lấy dữu liệu
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    break;
                case "studentListInClass":
                    String groupIdRaw = req.getParameter("groupId");
                    String courseIdRaw1 = req.getParameter("id"); // ID khóa học để breadcrumb có tên khóa học

                    if (groupIdRaw != null && !groupIdRaw.isEmpty()) {
                        try {
                            int classGroupId = Integer.parseInt(groupIdRaw);
                            int courseId = courseIdRaw1 != null && !courseIdRaw1.isEmpty() ? Integer.parseInt(courseIdRaw1) : -1;

                            // Lấy danh sách học sinh của lớp
                            List<User> students = studentDAO.getStudentsByClassGroup(classGroupId);
                            req.setAttribute("students", students);

                            // Lấy tên lớp
                            ClassGroup group = classGroupDAO.getClassGroupById(classGroupId);
                            req.setAttribute("selectedClassGroupId", classGroupId);
                            req.setAttribute("selectedClassGroupName", group != null ? group.getName() : "Không rõ");

                            // Lấy thông tin khóa học nếu có ID
                            if (courseId != -1) {
                                TutoringClass course = tutoringClassDAO.getTutoringClassDetail(courseId);
                                if (course != null) {
                                    req.setAttribute("selectedCourseId", courseId);
                                    req.setAttribute("selectedCourseName", course.getClassName());
                                }
                            }

                            // Truyền danh sách trường và lớp học (dùng trong hiển thị tên trường/lớp)
                            req.setAttribute("schools", schoolDAO.getAllSchools());
                            req.setAttribute("schoolClasses", schoolClassDAO.getAllSchoolClasses());

                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                    break;

                case "studentNotInClass":
                    String groupIdRaw1 = req.getParameter("groupId");
                    String courseIdRaw2 = req.getParameter("id"); // ID khóa học để breadcrumb có tên khóa học

                    if (groupIdRaw1 != null && !groupIdRaw1.isEmpty()) {
                        try {
                            int classGroupId = Integer.parseInt(groupIdRaw1);
                            int courseId = courseIdRaw2 != null && !courseIdRaw2.isEmpty() ? Integer.parseInt(courseIdRaw2) : -1;

                            List<User> students = studentDAO.getStudentsNotInClassGroupFullInfo(classGroupId);
                            req.setAttribute("students", students);

                            // Lấy tên lớp
                            ClassGroup group = classGroupDAO.getClassGroupById(classGroupId);
                            req.setAttribute("selectedClassGroupId", classGroupId);
                            req.setAttribute("selectedClassGroupName", group != null ? group.getName() : "Không rõ");

                            // Lấy thông tin khóa học nếu có ID
                            if (courseId != -1) {
                                TutoringClass course = tutoringClassDAO.getTutoringClassDetail(courseId);
                                if (course != null) {
                                    req.setAttribute("selectedCourseId", courseId);
                                    req.setAttribute("selectedCourseName", course.getClassName());
                                }
                            }

                            int maxStudent = group.getMaxStudent();
                            int currentStudentCount = classGroup_studentDAO.countStudentsInGroup(classGroupId);

                            req.setAttribute("maxStudent", maxStudent);
                            req.setAttribute("currentCount", currentStudentCount);

                            // Truyền danh sách trường và lớp học
                            req.setAttribute("schools", schoolDAO.getAllSchools());
                            req.setAttribute("schoolClasses", schoolClassDAO.getAllSchoolClasses());
                            req.setAttribute("maxStudent", group.getMaxStudent());

                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                    break;

                case "setting":
                    req.setAttribute("banners", bannerDAO.getAllBanners());
                    if ("1".equals(req.getParameter("ajax"))) {
                        req.getRequestDispatcher("/setting.jsp").forward(req, res);
                        return;
                    }
                    break;
            }
            req.getRequestDispatcher("/admin_dashboard.jsp").forward(req, res);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error processing admin request", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        String idStr = req.getParameter("id");
        String fromModal = req.getParameter("fromModal");
        boolean isFromModal = "true".equals(fromModal);

        System.out.println("POST received: action=" + action + ", id=" + idStr);

        try {
            // Trong đoạn xử lý lỗi của doPost:
            CenterInfo center = centerInfoDAO.getCenterInfo(1);
            if (center != null) {
                req.setAttribute("centerName", center.getNameCenter());
                req.setAttribute("descripCenter", center.getDescrip());
                req.setAttribute("address", center.getAddress());
                req.setAttribute("phone", center.getPhone());
                req.setAttribute("email", center.getEmail());
                req.setAttribute("website", center.getWebsite());
            }
            if ("ADD".equals(action)) {
                try {
                    TutoringClass tc = extractFromRequest(req);
                    List<String> errors = new ArrayList<>();

                    // Validate học phí
                    if (tc.getPrice() == -1) {
                        errors.add("Vui lòng nhập học phí hợp lệ.");
                    } else if (tc.getPrice() <= 0) {
                        errors.add("Học phí phải lớn hơn 0.");
                    }

                    // Validate bắt buộc chọn ảnh
                    if (tc.getImage() == null || tc.getImage().trim().isEmpty()) {
                        errors.add("Vui lòng chọn ảnh khóa học.");
                    }

                    // Làm tròn ngày hiện tại về 00:00:00
                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.HOUR_OF_DAY, 0);
                    cal.set(Calendar.MINUTE, 0);
                    cal.set(Calendar.SECOND, 0);
                    cal.set(Calendar.MILLISECOND, 0);
                    Date today = cal.getTime();

                    // Làm tròn ngày bắt đầu
                    Date startDate = tc.getStartDate();
                    if (startDate != null) {
                        cal.setTime(startDate);
                        cal.set(Calendar.HOUR_OF_DAY, 0);
                        cal.set(Calendar.MINUTE, 0);
                        cal.set(Calendar.SECOND, 0);
                        cal.set(Calendar.MILLISECOND, 0);
                        startDate = cal.getTime();
                    }

                    // Làm tròn ngày kết thúc
                    Date endDate = tc.getEndDate();
                    if (endDate != null) {
                        cal.setTime(endDate);
                        cal.set(Calendar.HOUR_OF_DAY, 0);
                        cal.set(Calendar.MINUTE, 0);
                        cal.set(Calendar.SECOND, 0);
                        cal.set(Calendar.MILLISECOND, 0);
                        endDate = cal.getTime();
                    }

                    // Validate ngày bắt đầu
                    if (startDate == null || startDate.before(today)) {
                        errors.add("Ngày bắt đầu phải từ hôm nay trở đi.");
                    }

                    // Validate ngày kết thúc sau ngày bắt đầu
                    if (endDate != null && startDate != null && !endDate.after(startDate)) {
                        errors.add("Ngày kết thúc phải sau ngày bắt đầu.");
                    }

                    if (!errors.isEmpty()) {
                        // Lưu vào session thay vì request ---
                        HttpSession ses = req.getSession();
                        ses.setAttribute("modalError", true);
                        ses.setAttribute("cModal", tc);
                        ses.setAttribute("errorList", errors);

                        // Có thể lưu lại các list nếu cần, nhưng thường chỉ cần truyền lại ở doGet
                        res.sendRedirect("admin?tab=courseManagement");
                        return;
                    }

                    // Nếu hợp lệ thì thêm khóa học
                    int newId = tutoringClassDAO.addTutoringClass(tc);

                    setSuccessMessage(req, "✔ Thêm lớp học thành công!");
                    res.sendRedirect("admin?tab=courseManagement&id=" + newId);
                    return;

                } catch (Exception e) {
                    e.printStackTrace();
                    // Nếu lỗi hệ thống, cũng nên redirect như trên để tránh bị stuck ở POST
                    HttpSession ses = req.getSession();
                    ses.setAttribute("modalError", true);
                    ses.setAttribute("cModal", null); // hoặc tc nếu muốn giữ lại data
                    List<String> errors = new ArrayList<>();
                    errors.add("Có lỗi xảy ra khi thêm khóa học!");
                    ses.setAttribute("errorList", errors);
                    res.sendRedirect("admin?tab=courseManagement");
                    return;
                }
            } else if ("UPDATE".equals(action) && idStr != null) {
                int id = Integer.parseInt(idStr);
                TutoringClass tc = extractFromRequest(req);
                tc.setTutoringClassID(id);
                tutoringClassDAO.updateTutoringClass(tc);
            } else if ("DELETE".equals(action) && idStr != null) {
                tutoringClassDAO.deleteTutoringClass(Integer.parseInt(idStr));

            } else if ("ADD_CLASSGROUP".equals(action)) {
                int tutoringClassId = Integer.parseInt(req.getParameter("tutoringClassId"));

                TutoringClass tutoringClass = tutoringClassDAO.getTutoringClassDetail(tutoringClassId);

                if (tutoringClass.getIsActive() == 2) {
                    req.getSession().setAttribute("successMessage", "❌ Khóa học đã bị khóa. Không thể thêm lớp học.");
                    res.sendRedirect("admin?tab=classManagement&id=" + tutoringClassId);
                    return;
                }

                // Nếu là khóa học nổi bật và đã được tạo hơn 1 tháng trước thì không cho thêm lớp
                if (tutoringClass.isIsHot()) {
                    Date date = tutoringClass.getStartDate(); // java.util.Date
                    LocalDate startDate = ((java.sql.Date) date).toLocalDate();
                    // chuyển sang localdate
                    if (startDate.plusMonths(1).isBefore(LocalDate.now())) {
                        req.getSession().setAttribute("successMessage", "❌ Khóa học nổi bật đã mở hơn 1 tháng. Không thể thêm lớp học mới.");
                        res.sendRedirect("admin?tab=classManagement&id=" + tutoringClassId);
                        return;
                    }
                }

                int teacherId = Integer.parseInt(req.getParameter("teacherId"));
                ClassGroup group = new ClassGroup();

                // Lấy lại dữ liệu form đã nhập từ request
                String[] days = req.getParameterValues("dayOfWeek");
                String[] shifts = req.getParameterValues("shiftId");
                String[] rooms = req.getParameterValues("roomId");

                try {
                    String className = req.getParameter("classGroupName");
                    int maxStudent = Integer.parseInt(req.getParameter("maxStudent"));
                    int minStudent = Integer.parseInt(req.getParameter("minStudent"));

                    group.setName(className);
                    group.setMaxStudent(maxStudent);
                    group.setMinStudent(minStudent);
                    group.setTeachId(teacherId);
                    group.setToturID(tutoringClassId);

                    // Nếu lỗi sĩ số
                    if (maxStudent <= 0 || minStudent <= 0 || minStudent > maxStudent) {
                        prepareClassManagementData(req, tutoringClassId);
                        setFormData(req, group, days, shifts, rooms); // <--- sửa: luôn truyền lại days, shifts, rooms
                        req.setAttribute("errorAddClass", "Sĩ số không hợp lệ. Vui lòng kiểm tra lại.");
                        req.getRequestDispatcher("/admin_dashboard.jsp").forward(req, res);
                        return;
                    }

                    // Nếu lỗi lịch học
                    if (days == null || shifts == null || rooms == null || days.length != shifts.length || shifts.length != rooms.length || days.length == 0) {
                        prepareClassManagementData(req, tutoringClassId);
                        setFormData(req, group, days, shifts, rooms); // luôn truyền lại days, shifts, rooms
                        req.setAttribute("errorAddClass", "Vui lòng chọn ít nhất một buổi học hợp lệ.");
                        req.getRequestDispatcher("/admin_dashboard.jsp").forward(req, res);
                        return;
                    }

                    List<ScheduleTemplate> templates = new ArrayList<>();
                    for (int i = 0; i < days.length; i++) {
                        ScheduleTemplate temp = new ScheduleTemplate();
                        temp.setDayOfWeek(Integer.parseInt(days[i]));
                        temp.setShiftId(Integer.parseInt(shifts[i]));
                        temp.setRoomId(Integer.parseInt(rooms[i]));
                        temp.setUserId(teacherId);
                        templates.add(temp);
                    }
                    // Không cần validate lại vì ScheduleOptionsServlet đã xử lý
                    int classGroupId = classGroupDAO.addClassGroupWithTemplates(group, templates);
                    HttpSession ses = req.getSession();
                    ses.removeAttribute("modalError");
                    ses.removeAttribute("groupModal");
                    ses.removeAttribute("selectedTeacher");
                    ses.removeAttribute("selectedDay");
                    ses.removeAttribute("selectedShift");
                    ses.removeAttribute("selectedRoom");
                    ses.removeAttribute("errorList");

                    req.getSession().setAttribute("successMessage", "✔ Thêm lớp học thành công!");
                    res.sendRedirect("admin?tab=classManagement&id=" + tutoringClassId);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();

                    // Khi lỗi hệ thống cũng phải lưu lại dữ liệu đã nhập
                    HttpSession ses = req.getSession();
                    ses.setAttribute("modalError", true);
                    ses.setAttribute("groupModal", group);
                    ses.setAttribute("selectedTeacher", String.valueOf(teacherId));
                    ses.setAttribute("selectedDay", days != null ? String.join(",", days) : "");
                    ses.setAttribute("selectedShift", shifts != null ? String.join(",", shifts) : "");
                    ses.setAttribute("selectedRoom", rooms != null ? String.join(",", rooms) : "");

                    List<String> errors = new ArrayList<>();
                    errors.add("Lỗi khi thêm lớp học: " + e.getMessage());
                    ses.setAttribute("errorList", errors);

                    res.sendRedirect("admin?tab=classManagement&id=" + tutoringClassId);
                    return;
                }
            } else if ("ADD_STUDENT".equals(action)) {
                String[] studentIds = req.getParameterValues("studentIds");
                String classGroupIdRaw = req.getParameter("groupId");
                String courseIdRaw = req.getParameter("id");

                if (studentIds == null || studentIds.length == 0 || classGroupIdRaw == null) {
                    setSuccessMessage(req, "❌ Thiếu tham số yêu cầu!");
                    res.sendRedirect("admin?tab=studentListInClass&groupId=" + classGroupIdRaw + "&id=" + (courseIdRaw != null ? courseIdRaw : ""));
                    return;
                }
                
                // ✅ Kiểm tra ngày kết thúc của khóa học
    int courseId = Integer.parseInt(courseIdRaw);
    TutoringClass course = tutoringClassDAO.getTutoringClassDetail(courseId);
    if (course == null) {
        setSuccessMessage(req, "❌ Không tìm thấy khóa học!");
        res.sendRedirect("admin?tab=studentListInClass&groupId=" + classGroupIdRaw + "&id=" + courseIdRaw);
        return;
    }

    java.sql.Date endDate = (java.sql.Date) course.getEndDate();
    if (endDate != null && endDate.before(java.sql.Date.valueOf(LocalDate.now()))) {
        setSuccessMessage(req, "❌ Khóa học đã kết thúc, không thể thêm học sinh!");
        res.sendRedirect("admin?tab=studentListInClass&groupId=" + classGroupIdRaw + "&id=" + courseIdRaw);
        return;
    }

                int classGroupId;
                try {
                    classGroupId = Integer.parseInt(classGroupIdRaw);
                } catch (NumberFormatException e) {
                    setSuccessMessage(req, "❌ ID lớp không hợp lệ!");
                    res.sendRedirect("admin?tab=studentListInClass&groupId=" + classGroupIdRaw + "&id=" + (courseIdRaw != null ? courseIdRaw : ""));
                    return;
                }

                ClassGroup group = classGroupDAO.getClassGroupById(classGroupId);
                int maxStudent = group.getMaxStudent();
                int currentStudentCount = classGroup_studentDAO.countStudentsInGroup(classGroupId);

                if (currentStudentCount + studentIds.length > maxStudent) {
                    setSuccessMessage(req, "❌ Vượt quá số lượng học sinh tối đa! Hiện tại: " + currentStudentCount + ", thêm: " + studentIds.length + ", tối đa: " + maxStudent);
                    res.sendRedirect("admin?tab=studentListInClass&groupId=" + classGroupId + "&id=" + (courseIdRaw != null ? courseIdRaw : ""));
                    return;
                }

                boolean allSuccess = true;
                List<String> errors = new ArrayList<>();

                for (String sid : studentIds) {
                    try {
                        int studentId = Integer.parseInt(sid);
                        boolean success = classGroup_studentDAO.addStudentToClassGroup(classGroupId, studentId);
                        if (!success) {
                            allSuccess = false;
                            errors.add("Thêm học sinh ID " + studentId + " thất bại.");
                        }
                    } catch (NumberFormatException e) {
                        allSuccess = false;
                        errors.add("ID học sinh " + sid + " không hợp lệ.");
                    }
                }

                if (allSuccess) {
                    setSuccessMessage(req, "✔ Thêm học sinh thành công!");
                } else {
                    setSuccessMessage(req, "❌ Thêm học sinh thất bại: " + String.join(", ", errors));
                }

                String redirectUrl = "admin?tab=studentListInClass&groupId=" + classGroupId + "&id=" + (courseIdRaw != null ? courseIdRaw : "");
                res.sendRedirect(redirectUrl);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        res.sendRedirect("admin?tab=courseManagement");
    }
// hàm trả về dữ liệu course khi gửi form sai

    private TutoringClass extractFromRequest(HttpServletRequest req) throws Exception {
        TutoringClass tc = new TutoringClass();
        tc.setClassName(req.getParameter("name"));
        tc.setDescrip(req.getParameter("description"));
        tc.setGradeID(Integer.parseInt(req.getParameter("grade")));
        tc.setSubjectID(Integer.parseInt(req.getParameter("subject")));
        tc.setStartDate(sdf.parse(req.getParameter("startDate")));
        tc.setEndDate(sdf.parse(req.getParameter("endDate")));
        String priceRaw = req.getParameter("price");
        double price = -1;

        if (priceRaw != null && !priceRaw.trim().isEmpty()) {
            try {
                price = Double.parseDouble(priceRaw);
            } catch (NumberFormatException e) {
                price = -2; // Người dùng nhập chữ, không phải số
            }
        } else {
            price = -1; // Người dùng bỏ trống
        }

        tc.setPrice(price);

        Part imagePart = req.getPart("courseImageFile");
        String imageFileName = null;

        if (imagePart != null && imagePart.getSize() > 0
                && imagePart.getSubmittedFileName() != null && !imagePart.getSubmittedFileName().isEmpty()) {

            String originalName = imagePart.getSubmittedFileName();
            String extension = originalName.substring(originalName.lastIndexOf(".")); // .jpg/.png
            imageFileName = "course_" + System.nanoTime() + extension;

            File dir = new File(EXTERNAL_IMG_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File savedFile = new File(dir, imageFileName);
            try {
                imagePart.write(savedFile.getAbsolutePath());
            } catch (Exception e) {
                Files.copy(imagePart.getInputStream(), savedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

            tc.setImage(imageFileName); // Chỉ lưu tên file
        } else {
            tc.setImage(req.getParameter("oldImage")); // Giữ lại ảnh cũ nếu không upload mới
        }

        String isHotRaw = req.getParameter("isHot");
        tc.setIsHot("1".equals(isHotRaw));
        return tc;
    }

    // hàm gửi thông báo 
    private void setSuccessMessage(HttpServletRequest req, String message) {
        HttpSession session = req.getSession();
        session.setAttribute("successMessage", message);
    }

// hàm dùng chung gửi dữ liệu về classManagement
    private void prepareClassManagementData(HttpServletRequest req, int tutoringClassId) throws Exception {
        TutoringClass course = tutoringClassDAO.getTutoringClassDetail(tutoringClassId);
        List<Object[]> classGroups = classGroupDAO.getClassGroupDetailsWithStudentCount(tutoringClassId);

        classGroups.sort((a, b) -> {
            boolean canActivateA = (Integer) a[7] == 0 && (Integer) a[4] >= (Integer) a[6];
            boolean canActivateB = (Integer) b[7] == 0 && (Integer) b[4] >= (Integer) b[6];
            return Boolean.compare(canActivateB, canActivateA);
        });

        req.setAttribute("selectedCourseId", tutoringClassId);
        req.setAttribute("selectedCourseName", course.getClassName());
        req.setAttribute("classGroups", classGroups);
        req.setAttribute("teacher", teacherDAO.getAllTeachers());
        req.setAttribute("weekdays", scheduleDAO.getWeekdayMap());
        req.setAttribute("shifts", shiftLearnDAO.getAllShifts());
        req.setAttribute("rooms", roomDAO.getAllRooms());

        // Truyền dữ liệu lỗi từ session nếu có
        HttpSession ses = req.getSession();
        if (ses.getAttribute("modalError") != null) {
            req.setAttribute("modalError", ses.getAttribute("modalError"));
            req.setAttribute("groupModal", ses.getAttribute("groupModal"));
            req.setAttribute("selectedTeacher", ses.getAttribute("selectedTeacher"));
            req.setAttribute("selectedDay", ses.getAttribute("selectedDay"));
            req.setAttribute("selectedShift", ses.getAttribute("selectedShift"));
            req.setAttribute("selectedRoom", ses.getAttribute("selectedRoom"));
            req.setAttribute("errorList", ses.getAttribute("errorList"));
            req.setAttribute("openAddModal", true);

            // Xóa dữ liệu trong session sau khi truyền vào request
            ses.removeAttribute("modalError");
            ses.removeAttribute("groupModal");
            ses.removeAttribute("selectedTeacher");
            ses.removeAttribute("selectedDay");
            ses.removeAttribute("selectedShift");
            ses.removeAttribute("selectedRoom");
            ses.removeAttribute("errorList");
        }
    }

    private void setFormData(HttpServletRequest req, ClassGroup group, String[] days, String[] shifts, String[] rooms) {
        req.setAttribute("groupModal", group);
        req.setAttribute("selectedTeacher", group.getTeachId() != 0 ? String.valueOf(group.getTeachId()) : null);
        req.setAttribute("selectedDay", days != null ? String.join(",", days) : "");
        req.setAttribute("selectedShift", shifts != null ? String.join(",", shifts) : "");
        req.setAttribute("selectedRoom", rooms != null ? String.join(",", rooms) : "");
        req.setAttribute("openAddModal", true);
        req.setAttribute("tab", "classManagement");
        req.setAttribute("id", String.valueOf(group.getToturID()));
    }

    // tự động tạo thêm lịch học khi còn dưới 3 buổi và chưa hết thời gian học
    public void autoExtendScheduleIfNeeded(int groupId) throws Exception {
        Date today = new Date();
        int remaining = scheduleDAO.getRemainingScheduleCount(groupId, today);

        if (remaining < 3) {
            Date lastDate = scheduleDAO.getLastScheduleDate(groupId);
            if (lastDate == null) {
                lastDate = today;
            }

            ClassGroup cg = classGroupDAO.getClassGroupById(groupId);
            if (cg == null) {
                return;
            }

            int classId = cg.getToturID();
            TutoringClass cls = tutoringClassDAO.getTutoringClassDetail(classId);
            if (cls == null || cls.getEndDate() == null) {
                return;
            }

            // Tính ngày bắt đầu mới
            Calendar cal = Calendar.getInstance();
            cal.setTime(lastDate);
            cal.add(Calendar.DATE, 1);
            Date nextStart = cal.getTime();

            if (nextStart.after(cls.getEndDate())) {
                System.out.println("Không thể tạo thêm lịch vì đã quá EndDate.");
                return;
            }

            List<ScheduleTemplate> templates = scheduleDAO.getTemplatesByGroupId(groupId);
            scheduleDAO.insertSchedulesFromTemplate(groupId, templates, nextStart, 10); // nên để hàm này kiểm soát EndDate
            System.out.println("▶ Kiểm tra tự động mở rộng lịch học cho groupId = " + groupId);
            System.out.println("Số buổi còn lại: " + remaining);
            System.out.println("Ngày cuối lịch học hiện tại: " + lastDate);
            System.out.println("Ngày bắt đầu tạo mới: " + nextStart);
            System.out.println("EndDate của lớp: " + cls.getEndDate());
            System.out.println("Template có " + templates.size() + " buổi");

        }
    }

    // tự động sinh thêm tuần để chọn với tổng 9 tuần 4 tuần trước và 4 tuần sau tuần hiện tại
    private Map.Entry<List<LocalDate>, Map<LocalDate, String>> generateWeekStartListAndMap() {
        List<LocalDate> weekStartList = new ArrayList<>();
        Map<LocalDate, String> weekDisplayMap = new LinkedHashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate currentMonday = LocalDate.now().with(DayOfWeek.MONDAY);
        int weeksBefore = 4;
        int weeksAfter = 4;

        for (int i = -weeksBefore; i <= weeksAfter; i++) {
            LocalDate start = currentMonday.plusWeeks(i);
            LocalDate end = start.plusDays(6);
            weekStartList.add(start);
            weekDisplayMap.put(start, start.format(formatter) + " - " + end.format(formatter));
        }

        return Map.entry(weekStartList, weekDisplayMap);
    }

}
