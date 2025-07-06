import dal.BannerDAO;
import dal.CenterInfoDAO;
import dal.ClassGroupDAO;
import dal.PaymentDAO;
import dal.ScheduleDAO;
import dal.ShiftLearnDAO;
import dal.StudentDAO;
import dal.SubjectDAO;
import dal.TeacherDAO;
import dal.TutoringClassDAO;
import entity.CenterInfo;
import entity.Shift;
import entity.Subject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Ngày tạo: 28/06/2025 ngày update: 30/6/2025 Người viết: Văn Thị Như
 */
@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(AdminServlet.class.getName());

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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        // Kiểm tra quyền admin
        if (session == null || !"1".equals(String.valueOf(session.getAttribute("userRoleID")))) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        try {
            String tab = req.getParameter("tab");
            if (tab == null || tab.isEmpty()) {
                tab = "overview";
            }
            req.setAttribute("tab", tab);

            // Lấy thông tin trung tâm (hiện ở header)
            CenterInfo center = centerInfoDAO.getCenterInfo(1);
            if (center != null) {
                req.setAttribute("centerName", center.getNameCenter());
                req.setAttribute("descripCenter", center.getDescrip());
                req.setAttribute("address", center.getAddress());
                req.setAttribute("phone", center.getPhone());
                req.setAttribute("email", center.getEmail());
                req.setAttribute("website", center.getWebsite());
            } else {
                req.setAttribute("centerName", "");
                req.setAttribute("descripCenter", "");
                req.setAttribute("address", "");
                req.setAttribute("phone", "");
                req.setAttribute("email", "");
                req.setAttribute("website", "");
            }

            if ("overview".equals(tab)) {
                // Đếm số lớp đang mở
                List<Object[]> openClasses = classGroupDAO.getOpeningClassGroupsByCourseDate();
                int openClassCount = openClasses.size();
                req.setAttribute("openClassCount", openClassCount);

                // Số lượng teacher
                int totalTeachers = teacherDAO.getAllTeachers().size();
                req.setAttribute("totalTeachers", totalTeachers);

                // Số student
                int studentCount = studentDAO.getStudentCount();
                req.setAttribute("studentCount", studentCount);

                // Số khoá học
                req.setAttribute("classesCount", tutoringClassDAO.getClasses(null).size());

                // Lịch học hôm nay
                List<Object[]> todaySchedules = scheduleDAO.getTodaySchedules();
                req.setAttribute("todaySchedules", todaySchedules);

                // Số học sinh theo học từng môn
                List<Object[]> studentCounts = studentDAO.getStudentCountPerSubject();
                req.setAttribute("studentCounts", studentCounts);

                // Lịch dạy của giáo viên
                List<Object[]> teacherSchedules = teacherDAO.getTeacherSchedules();
                req.setAttribute("teacherSchedules", teacherSchedules);

                // Danh sách thứ trong tuần
                Map<Integer, String> weekdays = scheduleDAO.getWeekdayMap();
                req.setAttribute("weekdays", weekdays);

                // Lịch học từng lớp
                List<Object[]> weeklySchedules = scheduleDAO.getWeeklySchedule();
                req.setAttribute("weeklySchedules", weeklySchedules);

                // Tính phần trăm đã thanh toán
                int paidPercentage = paymentDAO.getPaidPercentage();
                int unpaidPercentage = 100 - paidPercentage;
                req.setAttribute("percentPaid", paidPercentage);
                req.setAttribute("percentUnpaid", unpaidPercentage);

                // Danh sách lớp và số học sinh chưa thanh toán
                List<Object[]> unpaidList = paymentDAO.getUnpaidCountPerClassList();
                req.setAttribute("paymentSummaries", unpaidList);
                for (Object[] row : unpaidList) {
                    System.out.println("Lớp: " + row[0] + ", Còn nợ: " + row[1]);
                }

             } else if ("todaySchedule".equals(tab)) {
    String weekStartStr = req.getParameter("weekStart");
    String anyDateStr = req.getParameter("anyDate");
    LocalDate weekStartDate;

    try {
        if (anyDateStr != null && !anyDateStr.isEmpty()) {
            LocalDate anyDate = LocalDate.parse(anyDateStr);
            weekStartDate = anyDate.with(java.time.DayOfWeek.MONDAY);
            req.setAttribute("anyDate", anyDateStr);
            req.setAttribute("selectedWeekStart", java.util.Date.from(weekStartDate.atStartOfDay().atZone(java.time.ZoneId.systemDefault()).toInstant()));
        } else if (weekStartStr != null && !weekStartStr.isEmpty()) {
            weekStartDate = LocalDate.parse(weekStartStr, java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            req.setAttribute("selectedWeekStart", java.util.Date.from(weekStartDate.atStartOfDay().atZone(java.time.ZoneId.systemDefault()).toInstant()));
        } else {
            LocalDate today = LocalDate.now();
            int dayOfWeek = today.getDayOfWeek().getValue();
            weekStartDate = today.minusDays(dayOfWeek - 1); // Thứ 2 tuần này
            req.setAttribute("selectedWeekStart", java.util.Date.from(weekStartDate.atStartOfDay().atZone(java.time.ZoneId.systemDefault()).toInstant()));
        }

        java.util.Date weekStartUtil = java.util.Date.from(weekStartDate.atStartOfDay().atZone(java.time.ZoneId.systemDefault()).toInstant());

        // Danh sách các tuần (java.util.Date)
        List<Date> weekStartList = scheduleDAO.getWeekStartList();
        req.setAttribute("weekStartList", weekStartList);

        // Lấy shift
        List<Shift> shifts = shiftLearnDAO.getAllShifts();
        if (shifts == null || shifts.isEmpty()) {
            LOGGER.warning("Shifts list is empty");
        }
        req.setAttribute("shifts", shifts);

        // Lấy lịch 1 tuần (theo weekStart)
        List<Object[]> weeklySchedules = scheduleDAO.getWeeklyScheduleByWeek(weekStartUtil);
        if (weeklySchedules == null || weeklySchedules.isEmpty()) {
            LOGGER.warning("weeklySchedules is empty for weekStart: " + weekStartUtil);
        }
        req.setAttribute("weeklySchedules", weeklySchedules);

        // Map ngày trong tuần
        Map<Integer, java.util.Date> weekdayDates = new LinkedHashMap<>();
        for (int i = 2; i <= 7; i++) {
            weekdayDates.put(i, java.util.Date.from(weekStartDate.plusDays(i - 2).atStartOfDay().atZone(java.time.ZoneId.systemDefault()).toInstant()));
        }
        weekdayDates.put(1, java.util.Date.from(weekStartDate.plusDays(6).atStartOfDay().atZone(java.time.ZoneId.systemDefault()).toInstant()));

        java.util.Date todayDate = java.util.Date.from(LocalDate.now().atStartOfDay().atZone(java.time.ZoneId.systemDefault()).toInstant());

        // Map tên các ngày trong tuần
        req.setAttribute("weekdays", scheduleDAO.getWeekdayMap());
        req.setAttribute("weekdayDates", weekdayDates);
        req.setAttribute("todayDate", todayDate);
    } catch (java.time.format.DateTimeParseException e) {
        LOGGER.severe("Invalid date format for weekStartStr: " + weekStartStr + ", error: " + e.getMessage());
        throw new ServletException("Invalid date format", e);
    }
} else if ("teacherSchedule".equals(tab)) {
                List<Object[]> teacherSchedules = teacherDAO.getTeacherSchedules();

                // Nhóm lịch giáo viên theo tên
                Map<String, List<Object[]>> teacherScheduleMap = new LinkedHashMap<>();
                for (Object[] row : teacherSchedules) {
                    String teacher = (String) row[0]; // giáo viên
                    Object[] scheduleData = new Object[]{
                        row[1], // Subject
                        row[2], // Class
                        row[3], // DayOfWeek
                        row[4], // StartTime
                        row[5] // EndTime
                    };

                    teacherScheduleMap.putIfAbsent(teacher, new ArrayList<>());
                    teacherScheduleMap.get(teacher).add(scheduleData);
                }

                req.setAttribute("teacherScheduleMap", teacherScheduleMap);

                // Môn học
                List<Subject> subjects = subjectDAO.getAllSubjects();
                req.setAttribute("subjectList", subjects);

                // Thứ
                Map<Integer, String> weekdays = scheduleDAO.getWeekdayMap();
                req.setAttribute("weekdays", weekdays);

            } else if ("scheduleClass".equals(tab)) {
                List<Object[]> weeklySchedules = scheduleDAO.getWeeklySchedule();
                Map<Integer, String> weekdays = scheduleDAO.getWeekdayMap();

                req.setAttribute("weeklySchedules", weeklySchedules);
                req.setAttribute("weekdays", weekdays);

            } else if ("paymentReport".equals(tab)) {
                PaymentDAO paymentDAO = new PaymentDAO();
                List<Object[]> paymentDetails = paymentDAO.getDetailedPaymentPerClass();
                req.setAttribute("paymentDetails", paymentDetails);
            }

            // Nếu là tab setting, set thêm banners
            if ("setting".equals(tab)) {
                req.setAttribute("banners", bannerDAO.getAllBanners());
            }

            // Nếu gọi bằng AJAX → trả về 1 phần page (setting.jsp)
            if ("setting".equals(tab) && "1".equals(req.getParameter("ajax"))) {
                req.getRequestDispatcher("/setting.jsp").forward(req, res);
                return;
            }

            // Trả về toàn bộ layout admin
            req.getRequestDispatcher("/admin_dashboard.jsp").forward(req, res);

        } catch (Exception e) {
            LOGGER.severe("Error in AdminServlet: " + e.getMessage());
            e.printStackTrace();
            throw new ServletException("Error processing request", e);
        }
    }
}