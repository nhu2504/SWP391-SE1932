
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
//@WebServlet("/admin")
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
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static final String EXTERNAL_IMG_DIR = "D:/data/images";  // ÄÆ°á»ng dáº«n lÆ°u áº£nh khĂ³a há»c

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || !"1".equals(String.valueOf(session.getAttribute("userRoleID")))) {
            res.sendRedirect(req.getContextPath() + "/login");
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
        System.out.println("POST received: action=" + action + ", id=" + idStr);
        try {
            if ("ADD".equals(action)) {
                try {
                    TutoringClass tc = extractFromRequest(req);

                    // Kiá»ƒm tra ngĂ y báº¯t Ä‘áº§u
                    if (tc.getStartDate().before(new Date())) {
                        req.setAttribute("error", "NgĂ y báº¯t Ä‘áº§u pháº£i tá»« hĂ´m nay trá»Ÿ Ä‘i!");
                        req.setAttribute("tab", "courseManagement");
                        req.setAttribute("c", tc); // giá»¯ láº¡i object chá»©a image path
                        req.setAttribute("data", tutoringClassDAO.getClasses(null));
                        req.setAttribute("subjects", subjectDAO.getAllSubjects());
                        req.setAttribute("grades", gradeDAO.getAllGrades());
                        doGet(req, res);
                        return;
                    }

                    tutoringClassDAO.addTutoringClass(tc);
                    res.sendRedirect("admin?tab=courseManagement");
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    req.setAttribute("error", "CĂ³ lá»—i xáº£y ra khi thĂªm khĂ³a há»c!");
                    req.setAttribute("tab", "courseManagement");
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

        tc.setImage(imageFileName); // Chá»‰ lÆ°u tĂªn file
    } else {
        tc.setImage(req.getParameter("oldImage")); // Giá»¯ láº¡i áº£nh cÅ© náº¿u khĂ´ng upload má»›i
    }

    String isHotRaw = req.getParameter("isHot");
    tc.setIsHot("1".equals(isHotRaw));
    return tc;
}


}
