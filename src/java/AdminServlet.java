
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
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
/**
 * Ngày tạo: 28/06/2025  
 * ngày update: 30/6/2025
 * Người viết: Văn Thị Như
 */
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        // Kiểm tra quyền admin
        if (session == null || !"1".equals(String.valueOf(session.getAttribute("userRoleID")))) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

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
            // ✅ Thêm: Đếm số lớp đang mở → dùng trong tab "overview"
            List<Object[]> openClasses = classGroupDAO.getOpeningClassGroupsByCourseDate();

            int openClassCount = openClasses.size(); // số lớp đang mở
            req.setAttribute("openClassCount", openClassCount);

            // Số lượng teacher
            int totalTeachers = teacherDAO.getAllTeachers().size();
            req.setAttribute("totalTeachers", totalTeachers);

            // Số student
            int studentCount = studentDAO.getStudentCount();
            req.setAttribute("studentCount", studentCount);

            // Số khoá học
            req.setAttribute("classesCount", tutoringClassDAO.getClasses(null).size());
            // lịch học hôm nay

            List<Object[]> todaySchedules = scheduleDAO.getTodaySchedules();
            req.setAttribute("todaySchedules", todaySchedules);

            // số học sinh theo học từng môn
            List<Object[]> studentCounts = studentDAO.getStudentCountPerSubject();
            req.setAttribute("studentCounts", studentCounts);

            // lịch dạy của giáo viên
            List<Object[]> teacherSchedules = teacherDAO.getTeacherSchedules();
            req.setAttribute("teacherSchedules", teacherSchedules);
            // danh sách thứ trong tuần
            Map<Integer, String> weekdays = scheduleDAO.getWeekdayMap();
            req.setAttribute("weekdays", weekdays);
            // lịch học từng lớp
            List<Object[]> weeklySchedules = scheduleDAO.getWeeklySchedule();
            req.setAttribute("weeklySchedules", weeklySchedules);
            // 1. Tính phần trăm đã thanh toán
            int paidPercentage = paymentDAO.getPaidPercentage();
            int unpaidPercentage = 100 - paidPercentage;

            req.setAttribute("percentPaid", paidPercentage);       // 👈 tên trùng với JSP
            req.setAttribute("percentUnpaid", unpaidPercentage);   // 👈 tên trùng với JSP

            // 2. Danh sách lớp và số học sinh chưa thanh toán
            List<Object[]> unpaidList = paymentDAO.getUnpaidCountPerClassList();
            req.setAttribute("paymentSummaries", unpaidList); // ✅ tên trùng với JSP
            for (Object[] row : unpaidList) {
                System.out.println("Lớp: " + row[0] + ", Còn nợ: " + row[1]);
            }

        } else if ("todaySchedule".equals(tab)) {
            // Lấy tham số từ request
            String weekStartStr = req.getParameter("weekStart");
            String anyDateStr = req.getParameter("anyDate");
            LocalDate weekStartDate;

            if (anyDateStr != null && !anyDateStr.isEmpty()) {
                // Nếu nhập ngày tự do, ưu tiên ngày này
                LocalDate anyDate = LocalDate.parse(anyDateStr);
                weekStartDate = anyDate.with(java.time.DayOfWeek.MONDAY);
                req.setAttribute("anyDate", anyDateStr);
                req.setAttribute("selectedWeekStart", java.sql.Date.valueOf(weekStartDate));
            } else if (weekStartStr != null && !weekStartStr.isEmpty()) {
                // Nếu chọn trong dropdown
                weekStartDate = LocalDate.parse(weekStartStr);
                req.setAttribute("selectedWeekStart", java.sql.Date.valueOf(weekStartDate));
            } else {
                // Nếu không chọn gì, lấy tuần hiện tại
                LocalDate today = LocalDate.now();
                int dayOfWeek = today.getDayOfWeek().getValue();
                weekStartDate = today.minusDays(dayOfWeek - 1); // Thứ 2 tuần này
                req.setAttribute("selectedWeekStart", java.sql.Date.valueOf(weekStartDate));
            }

            Date weekStartSql = Date.valueOf(weekStartDate);

            // Danh sách các tuần (java.sql.Date), ví dụ từ DB hoặc sinh động
            List<Date> weekStartList = scheduleDAO.getWeekStartList();
            req.setAttribute("weekStartList", weekStartList);

            // Lấy shift
            List<Shift> shifts = shiftLearnDAO.getAllShifts();

            // Lấy lịch 1 tuần (theo weekStart)
            List<Object[]> weeklySchedules = scheduleDAO.getWeeklyScheduleByWeek(weekStartSql);

            req.setAttribute("shifts", shifts);
            req.setAttribute("weeklySchedules", weeklySchedules);

            // Map ngày trong tuần
            Map<Integer, Date> weekdayDates = new LinkedHashMap<>();
            for (int i = 2; i <= 7; i++) { // Thứ 2 tới Thứ 7
                weekdayDates.put(i, Date.valueOf(weekStartDate.plusDays(i - 2)));
            }
            // Chủ nhật (DB là 1) là Thứ 2 + 6 ngày
            weekdayDates.put(1, Date.valueOf(weekStartDate.plusDays(6)));
            req.setAttribute("weekdayDates", weekdayDates);

            // todayDate để highlight cột hôm nay
            LocalDate today = LocalDate.now();
            Date todayDate = Date.valueOf(today);
            req.setAttribute("todayDate", todayDate);

            // Map tên các ngày trong tuần
            req.setAttribute("weekdays", scheduleDAO.getWeekdayMap());
        } else if ("teacherSchedule".equals(tab)) {
            List<Object[]> teacherSchedules = teacherDAO.getTeacherSchedules();

            // nhóm lịch giáo viên theo tên
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
    }
}
