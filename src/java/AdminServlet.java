
import dal.BannerDAO;
import dal.CenterInfoDAO;
import dal.ClassGroupDAO;
import dal.GradeDAO;
import dal.PaymentDAO;
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
import entity.Shift;
import entity.Subject;
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
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

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
    private final PaymentDAO paymentDAO = new PaymentDAO();
    private final GradeDAO gradeDAO = new GradeDAO();
    private final RoomDAO roomDAO = new RoomDAO();
    private final SchoolDAO schoolDAO = new SchoolDAO();
    private final SchoolClassDAO schoolClassDAO = new SchoolClassDAO();
    

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static final String EXTERNAL_IMG_DIR = "D:/data/images";  // Đường dẫn lưu ảnh khóa học

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || !"1".equals(String.valueOf(session.getAttribute("userRoleID")))) {
            res.sendRedirect(req.getContextPath() + "/login_register.jsp");
            return;
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
                    req.setAttribute("openClassCount", classGroupDAO.getOpeningClassGroupsByCourseDate().size());
                    req.setAttribute("totalTeachers", teacherDAO.getAllTeachers().size());
                    req.setAttribute("studentCount", studentDAO.getStudentCount());
                    req.setAttribute("classesCount", tutoringClassDAO.getClasses(null).size());
                    req.setAttribute("todaySchedules", scheduleDAO.getTodaySchedules());
                    req.setAttribute("studentCounts", studentDAO.getStudentCountPerSubject());
                    req.setAttribute("teacherSchedules", teacherDAO.getTeacherSchedules());
                    req.setAttribute("weekdays", scheduleDAO.getWeekdayMap());
                    req.setAttribute("weeklySchedules", scheduleDAO.getWeeklySchedule());
                    int paidPercent = paymentDAO.getPaidPercentage();
                    req.setAttribute("percentPaid", paidPercent);
                    req.setAttribute("percentUnpaid", 100 - paidPercent);
                    List<Object[]> unpaidList = paymentDAO.getUnpaidCountPerClassList();
                    req.setAttribute("paymentSummaries", unpaidList);
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
                    req.setAttribute("data", tutoringClassDAO.getClasses(null));
                    req.setAttribute("subjects", subjectDAO.getAllSubjects());
                    req.setAttribute("grades", gradeDAO.getAllGrades());
                    break;
                case "paymentReport":
                    req.setAttribute("paymentDetails", paymentDAO.getDetailedPaymentPerClass());
                    break;
                case "scheduleClass":
                    req.setAttribute("weeklySchedules", scheduleDAO.getWeeklySchedule());
                    req.setAttribute("weekdays", scheduleDAO.getWeekdayMap());
                    break;
                case "teacherSchedule":
                    List<Object[]> teacherSchedules = teacherDAO.getTeacherSchedules();
                    Map<String, List<Object[]>> teacherScheduleMap = new LinkedHashMap<>();
                    for (Object[] row : teacherSchedules) {
                        String teacher = (String) row[0];
                        Object[] schedule = {row[1], row[2], row[3], row[4], row[5]};
                        teacherScheduleMap.computeIfAbsent(teacher, k -> new ArrayList<>()).add(schedule);
                    }
                    req.setAttribute("teacherScheduleMap", teacherScheduleMap);
                    req.setAttribute("subjectList", subjectDAO.getAllSubjects());
                    req.setAttribute("weekdays", scheduleDAO.getWeekdayMap());
                    break;
                case "todaySchedule":
                    try {
                    LocalDate weekStartDate;
                    String weekStartStr = req.getParameter("weekStart");
                    String anyDateStr = req.getParameter("anyDate");
                    if (anyDateStr != null && !anyDateStr.isEmpty()) {
                        LocalDate anyDate = LocalDate.parse(anyDateStr);
                        weekStartDate = anyDate.with(java.time.DayOfWeek.MONDAY);
                        req.setAttribute("anyDate", anyDateStr);
                    } else if (weekStartStr != null && !weekStartStr.isEmpty()) {
                        weekStartDate = LocalDate.parse(weekStartStr);
                    } else {
                        weekStartDate = LocalDate.now().with(java.time.DayOfWeek.MONDAY);
                    }
                    Date weekStartUtil = Date.from(weekStartDate.atStartOfDay().atZone(java.time.ZoneId.systemDefault()).toInstant());
                    req.setAttribute("selectedWeekStart", weekStartUtil);
                    req.setAttribute("weekStartList", scheduleDAO.getWeekStartList());
                    req.setAttribute("shifts", shiftLearnDAO.getAllShifts());
                    req.setAttribute("weeklySchedules", scheduleDAO.getWeeklyScheduleByWeek(weekStartUtil));
                    Map<Integer, Date> weekdayDates = new LinkedHashMap<>();
                    for (int i = 2; i <= 7; i++) {
                        weekdayDates.put(i, Date.from(weekStartDate.plusDays(i - 2).atStartOfDay().atZone(java.time.ZoneId.systemDefault()).toInstant()));
                    }
                    weekdayDates.put(1, Date.from(weekStartDate.plusDays(6).atStartOfDay().atZone(java.time.ZoneId.systemDefault()).toInstant()));
                    req.setAttribute("weekdays", scheduleDAO.getWeekdayMap());
                    req.setAttribute("weekdayDates", weekdayDates);
                    req.setAttribute("todayDate", Date.from(LocalDate.now().atStartOfDay().atZone(java.time.ZoneId.systemDefault()).toInstant()));
                } catch (Exception e) {
                    throw new ServletException("Invalid date", e);
                }
                break;
                case "courseList":
                    req.setAttribute("data", tutoringClassDAO.getClasses(null));
                    req.setAttribute("subjects", subjectDAO.getAllSubjects());
                    req.setAttribute("grades", gradeDAO.getAllGrades());
                    break;
                case "classManagement":
                    String courseIdRaw = req.getParameter("id");
                    if (courseIdRaw != null && !courseIdRaw.isEmpty()) {
                        try {
                            int courseId = Integer.parseInt(courseIdRaw);
                            List<Object[]> classGroups = classGroupDAO.getClassGroupDetailsWithStudentCount(courseId);
                            TutoringClass course = tutoringClassDAO.getTutoringClassDetail(courseId);
                            req.setAttribute("teacher", teacherDAO.getAllTeachers());
                            req.setAttribute("weekdays", scheduleDAO.getWeekdayMap());
                            req.setAttribute("shifts", shiftLearnDAO.getAllShifts());
                            req.setAttribute("rooms", roomDAO.getAllRooms());

                            req.setAttribute("selectedCourseId", courseId);
                            req.setAttribute("selectedCourseName", course.getClassName());
                            req.setAttribute("classGroups", classGroups);
                        } catch (NumberFormatException e) {
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
            if ("ADD".equals(action)) {
                try {
                    TutoringClass tc = extractFromRequest(req);

                    // Làm tròn ngày bắt đầu và ngày hôm nay về 00:00:00 để so sánh
                    Date now = new Date(); // ngày hiện tại
                    Calendar cal = Calendar.getInstance();

                    // Làm tròn today về 00:00:00
                    cal.setTime(now);
                    cal.set(Calendar.HOUR_OF_DAY, 0);
                    cal.set(Calendar.MINUTE, 0);
                    cal.set(Calendar.SECOND, 0);
                    cal.set(Calendar.MILLISECOND, 0);
                    Date today = cal.getTime();

                    // Làm tròn startDate về 00:00:00
                    Date startDate = tc.getStartDate();
                    cal.setTime(startDate);
                    cal.set(Calendar.HOUR_OF_DAY, 0);
                    cal.set(Calendar.MINUTE, 0);
                    cal.set(Calendar.SECOND, 0);
                    cal.set(Calendar.MILLISECOND, 0);
                    Date normalizedStart = cal.getTime();

                    if (normalizedStart.before(today)) {
                        // Ngày bắt đầu trong quá khứ
                        req.setAttribute("error", "Ngày bắt đầu phải từ hôm nay trở đi!");
                        req.setAttribute("tab", "courseManagement");
                        req.setAttribute("cModal", tc);
                        req.setAttribute("modalError", true);

                        String selectedId = req.getParameter("selectedClassId");
                        if (selectedId != null && !selectedId.isEmpty()) {
                            try {
                                int sid = Integer.parseInt(selectedId);
                                TutoringClass selectedClass = tutoringClassDAO.getTutoringClassDetail(sid);
                                req.setAttribute("c", selectedClass);
                            } catch (NumberFormatException e) {
                                // Bỏ qua nếu ID lỗi
                            }
                        }

                        req.setAttribute("data", tutoringClassDAO.getClasses(null));
                        req.setAttribute("subjects", subjectDAO.getAllSubjects());
                        req.setAttribute("grades", gradeDAO.getAllGrades());

                        req.getRequestDispatcher("/admin_dashboard.jsp").forward(req, res);
                        return;
                    }

                    // Nếu hợp lệ thì thêm khóa học
                    int newId = tutoringClassDAO.addTutoringClass(tc);

                    setSuccessMessage(req, "✔ Thêm lớp học thành công!");
                    res.sendRedirect("admin?tab=courseManagement&id=" + newId);
                    return;

                } catch (Exception e) {
                    e.printStackTrace();
                    req.setAttribute("error", "Có lỗi xảy ra khi thêm khóa học!");
                    req.setAttribute("tab", "courseManagement");

                    if (isFromModal) {
                        req.setAttribute("modalError", true);
                        req.setAttribute("cModal", null); // hoặc giữ lại tc nếu muốn hiển thị lại
                    }

                    req.getRequestDispatcher("/admin_dashboard.jsp").forward(req, res);
                    return;
                }
            } else if ("UPDATE".equals(action) && idStr != null) {
                int id = Integer.parseInt(idStr);
                TutoringClass tc = extractFromRequest(req);
                tc.setTutoringClassID(id);
                tutoringClassDAO.updateTutoringClass(tc);
            } else if ("DELETE".equals(action) && idStr != null) {
                tutoringClassDAO.deleteTutoringClass(Integer.parseInt(idStr));
            } else if ("SEARCH".equals(action)) {
                String name = req.getParameter("name");
                List<TutoringClass> result = tutoringClassDAO.searchTutoringClassByName(name);
                req.setAttribute("tab", "courseManagement");
                req.setAttribute("data", result);
                req.getRequestDispatcher("/admin_dashboard.jsp").forward(req, res);
                return;
            } else if ("ADD_CLASSGROUP".equals(action)) {
                int tutoringClassId = 0;
                try {
                    // 1. Lấy thông tin lớp học
                    String className = req.getParameter("classGroupName");
                    int maxStudent = Integer.parseInt(req.getParameter("maxStudent"));
                    int teacherId = Integer.parseInt(req.getParameter("teacherId"));
                    tutoringClassId = Integer.parseInt(req.getParameter("tutoringClassId"));

                    // 2. Tạo đối tượng ClassGroup
                    ClassGroup group = new ClassGroup();
                    group.setName(className);
                    group.setMaxStudent(maxStudent);
                    group.setTeachId(teacherId);
                    group.setToturID(tutoringClassId);

                    // 3. Lấy danh sách lịch học mẫu từ form
                    List<ScheduleTemplate> templates = new ArrayList<>();
                    String[] days = req.getParameterValues("dayOfWeek");
                    String[] shifts = req.getParameterValues("shiftId");
                    String[] rooms = req.getParameterValues("roomId");

                    if (days != null && shifts != null && rooms != null
                            && days.length == shifts.length && shifts.length == rooms.length) {
                        for (int i = 0; i < days.length; i++) {
                            ScheduleTemplate temp = new ScheduleTemplate();
                            temp.setDayOfWeek(Integer.parseInt(days[i]));
                            temp.setShiftId(Integer.parseInt(shifts[i]));
                            temp.setRoomId(Integer.parseInt(rooms[i]));
                            temp.setUserId(teacherId);
                            templates.add(temp);
                        }
                    }

                    // 4. Kiểm tra nếu là khoá cấp tốc (isHot=1) và đã bắt đầu thì không cho thêm lớp
                    TutoringClass tc = tutoringClassDAO.getTutoringClassDetail(tutoringClassId);
                    Date today = new Date();
                    if (tc.isIsHot() == true && tc.getStartDate().before(today)) {
                        req.setAttribute("error", "Không thể thêm lớp mới vì khóa cấp tốc đã bắt đầu.");
                        req.setAttribute("tab", "classManagement");
                        req.setAttribute("id", String.valueOf(tutoringClassId));
                        res.sendRedirect("admin?tab=classManagement&id=" + tutoringClassId);
                        return;
                    }

                    // 4. Thêm class group + template
                    int classGroupId = classGroupDAO.addClassGroupWithTemplates(group, templates);

                    // 5. Tạo lịch học tự động
                    //TutoringClass tc = tutoringClassDAO.getTutoringClassDetail(tutoringClassId);
                    int sessionCount = 10;
                    scheduleDAO.insertSchedulesFromTemplate(classGroupId, templates, tc.getStartDate(), sessionCount);
                    setSuccessMessage(req, "✔ Thêm lớp học thành công!");

                    res.sendRedirect("admin?tab=classManagement&id=" + tutoringClassId);

                    return;

                } catch (Exception e) {
                    e.printStackTrace();
                    req.setAttribute("error", "Có lỗi xảy ra khi thêm lớp học!");
                    req.setAttribute("tab", "classManagement");
                    req.setAttribute("id", String.valueOf(tutoringClassId));
                    req.getRequestDispatcher("/admin_dashboard.jsp").forward(req, res);
                    return;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        res.sendRedirect("admin?tab=courseManagement");
    }

    private TutoringClass extractFromRequest(HttpServletRequest req) throws Exception {
        TutoringClass tc = new TutoringClass();
        tc.setClassName(req.getParameter("name"));
        tc.setDescrip(req.getParameter("description"));
        tc.setGradeID(Integer.parseInt(req.getParameter("grade")));
        tc.setSubjectID(Integer.parseInt(req.getParameter("subject")));
        tc.setStartDate(sdf.parse(req.getParameter("startDate")));
        tc.setEndDate(sdf.parse(req.getParameter("endDate")));
        tc.setPrice(Double.parseDouble(req.getParameter("price")));

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

    private LocalDate getNextDate(LocalDate fromDate, int dow) {
        int javaDow = dow == 1 ? 7 : dow - 1; // Java: 1=Monday,...7=Sunday; DB: 1=Sunday
        int currentJavaDow = fromDate.getDayOfWeek().getValue(); // 1=Monday,...7=Sunday
        int daysToAdd = (javaDow - currentJavaDow + 7) % 7;
        return fromDate.plusDays(daysToAdd == 0 ? 7 : daysToAdd); // tránh chọn hôm nay
    }

    private void setSuccessMessage(HttpServletRequest req, String message) {
        HttpSession session = req.getSession();
        session.setAttribute("successMessage", message);
    }

}
