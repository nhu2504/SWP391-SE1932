/*
 * Tác giả: Van Nhu
 
 * Update: 30/6/2025
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
    private final ShiftLearnDAO shiftDAO = new ShiftLearnDAO();
    private final ClassGroupDAO classGroupDAO = new ClassGroupDAO();
    private final ScheduleDAO scheduleDAO = new ScheduleDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // 1. Thông tin trung tâm
            CenterInfo info = centerInfoDAO.getCenterInfo(1);
            if (info != null) {
                request.setAttribute("centerName", info.getNameCenter());
                request.setAttribute("address", info.getAddress());
                request.setAttribute("email", info.getEmail());
                request.setAttribute("phone", info.getPhone());
                request.setAttribute("descripCenter", info.getDescrip());
            }

            // 2. Khối lớp và môn học
            List<Grade> grades = gradeDAO.getAllGrades();
            List<Subject> subjects = subjectDAO.getSubjectsWithClassCount();
            request.setAttribute("grades", grades);
            request.setAttribute("subjects", subjects);

            // Map tra cứu tên khối và tên môn
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

            // 3. Tài liệu theo khối và môn
            int gradeId = parseIntOrDefault(request.getParameter("gradeId"), 0);
            int subjectId = parseIntOrDefault(request.getParameter("subjectId"), 0);
            request.setAttribute("documents", documentDAO.getDocumentsByGradeAndSubject(gradeId, subjectId));

            // 4. Danh sách tất cả khóa học
            request.setAttribute("classes", tutoringClassDAO.getTutoringClassesByHotAndStatus(null));

            // 5. Banner và các thông số trung tâm
            request.setAttribute("banners", bannerDAO.getAllBanners());
            int establishmentYear = centerInfoDAO.getYearOfWork();
            int yearsActive = Year.now().getValue() - establishmentYear;
            request.setAttribute("yearsActive", yearsActive);
            request.setAttribute("studentCount", studentDAO.getStudentCount());
            request.setAttribute("partnerSchoolsCount", schoolDAO.getPartnerSchoolsCount());

            // 6. Khóa học nổi bật và quanh năm
            List<TutoringClass> featuredTutoringClasses = tutoringClassDAO.getFeaturedTutoringClasses();
            List<TutoringClass> yearRoundTutoringClasses = tutoringClassDAO.getYearRoundTutoringClasses();
            request.setAttribute("featuredTutoringClasses", featuredTutoringClasses);
            request.setAttribute("yearRoundTutoringClasses", yearRoundTutoringClasses);

            // Tạo formatter cho giờ phút
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

            // 7. Lấy shift list (giờ bắt đầu/kết thúc)
            List<Shift> allShifts = shiftDAO.getAllShifts();
            Map<Integer, String> shiftStartTimes = new HashMap<>();
            Map<Integer, String> shiftEndTimes = new HashMap<>();
            Map<Integer, Shift> shiftMap = new HashMap<>();

            for (Shift s : allShifts) {
                String formattedStart = s.getStartTime() != null ? timeFormat.format(s.getStartTime()) : "N/A";
                String formattedEnd = s.getEndTime() != null ? timeFormat.format(s.getEndTime()) : "N/A";

                shiftStartTimes.put(s.getId(), formattedStart);
                shiftEndTimes.put(s.getId(), formattedEnd);
                shiftMap.put(s.getId(), s); // vẫn giữ nguyên vì đây là đối tượng Shift
            }

            request.setAttribute("shiftStartTimes", shiftStartTimes);
            request.setAttribute("shiftEndTimes", shiftEndTimes);
            request.setAttribute("shiftMap", shiftMap);

            // DEBUG: Log shift map
            System.out.println("DEBUG: shiftMap:");
            for (Shift s : allShifts) {
                System.out.printf("  ShiftID=%d, Start=%s, End=%s, DurationText=%s\n", s.getId(), s.getStartTime(), s.getEndTime(), s.getDurationText());
            }
            //
            List<TutoringClass> allTutoringClasses = new ArrayList<>();
            allTutoringClasses.addAll(tutoringClassDAO.getFeaturedTutoringClasses());
            allTutoringClasses.addAll(tutoringClassDAO.getYearRoundTutoringClasses());

            Map<Integer, String> groupStringMap = new HashMap<>();
            Map<Integer, String> durationMap = new HashMap<>();
            Map<Integer, String> weekdayMap = scheduleDAO.getWeekdayMap(); // gọi hàm bạn đã viết
            request.setAttribute("weekdayMap", weekdayMap); // truyền cho JSP
            Set<Integer> addedClassIds = new HashSet<>();

//            for (TutoringClass tc : allTutoringClasses) {
//                int tutoringClassId = tc.getTutoringClassID();
//                if (addedClassIds.contains(tutoringClassId)) {
//                    continue;
//                }
//                addedClassIds.add(tutoringClassId);
//
//                // Sử dụng DAO mới đã cập nhật: mỗi group chỉ 1 dòng, có thêm trường ngày học                             
//                 List<Object[]> groupList = classGroupDAO.getClassGroupDetailsWithStudentCount(tutoringClassId);
//                StringBuilder sb = new StringBuilder();
//
//                String duration = "Chưa xác định";
//                if (groupList != null && !groupList.isEmpty()) {
//                    for (Object[] g : groupList) {
//                        // g[0]=ClassGroupName, g[1]=MaxStudent, g[2]=RoomName, 
//                        // g[3]=TeacherName, g[4]=StartTime, g[5]=EndTime, g[6]=StudyDate
//                        // Tính thứ từ ngày học
//                        String thu;
//                        if (g[6] != null) {
//                            int dayOfWeek = (int) g[6]; // giá trị từ DB
//                            thu = weekdayMap.getOrDefault(dayOfWeek, "Không xác định");
//                        } else {
//                            thu = "null";
//                        }
//
//                        sb//.append(g[0]).append("~") // groupId
//                                .append(g[0]).append("~") // groupName
//                                .append(g[1]).append("~") // maxStudent
//                                .append(g[2]).append("~") // roomName
//                                .append(g[3] != null ? g[3] : "Không xác định").append("~") // teacherName
//                                .append(thu).append("~") // thứ trong tuần
//                                .append(g[4] != null ? g[4] : "N/A").append("~") // startTime
//                                .append(g[5] != null ? g[5] : "N/A").append(";"); // endTime
//
//                        // DEBUG: Log group details
//                        System.out.println("DEBUG: TutoringClassID=" + tutoringClassId + ", GroupID=" + g[0]
//                                + ", GroupName=" + g[1] + ", TeacherName=" + g[4]
//                                + ", Thu=" + thu
//                                + ", StartTime=" + g[5] + ", EndTime=" + g[6]);
//                    }
//
//                    // Lấy duration từ ca đầu tiên
//                    Object[] firstGroup = groupList.get(0);
//
//                    String startStr = firstGroup[4] != null ? firstGroup[4].toString().trim() : null;
//                    String endStr = firstGroup[5] != null ? firstGroup[5].toString().trim() : null;
//
//                    try {
//                        // Làm sạch chuỗi thời gian nếu có định dạng lạ như "00.0000000"
//                        if (startStr != null && startStr.contains(".")) {
//                            startStr = startStr.split("\\.")[0]; // lấy phần trước dấu chấm
//                        }
//                        if (endStr != null && endStr.contains(".")) {
//                            endStr = endStr.split("\\.")[0];
//                        }
//
//                        // Thêm giây nếu thiếu
//                        if (startStr != null && startStr.matches("\\d{2}:\\d{2}")) {
//                            startStr += ":00";
//                        }
//                        if (endStr != null && endStr.matches("\\d{2}:\\d{2}")) {
//                            endStr += ":00";
//                        }
//
//                        // Kiểm tra lần cuối
//                        if (startStr != null && endStr != null
//                                && startStr.matches("\\d{2}:\\d{2}:\\d{2}")
//                                && endStr.matches("\\d{2}:\\d{2}:\\d{2}")) {
//
//                            Time startSqlTime = Time.valueOf(startStr);
//                            Time endSqlTime = Time.valueOf(endStr);
//
//                            Shift shift = new Shift(startSqlTime, endSqlTime);
//                            duration = shift.getDurationText();
//
//                            SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
//                            System.out.println("DEBUG: Shift đầu tiên: Start=" + fmt.format(startSqlTime)
//                                    + ", End=" + fmt.format(endSqlTime) + ", Duration=" + duration);
//                        } else {
//                            System.out.println("DEBUG ❌ Dữ liệu thời gian không hợp lệ: Start=" + startStr + ", End=" + endStr);
//                        }
//
//                    } catch (Exception e) {
//                        System.err.println("❌ Lỗi khi parse thời gian: " + e.getMessage());
//                        e.printStackTrace();
//                    }
//
//                } else {
//                    System.out.println("DEBUG: KHÔNG có group nào cho TutoringClassID = " + tutoringClassId);
//                }
//                groupStringMap.put(tutoringClassId, sb.toString());
//                durationMap.put(tutoringClassId, duration);
//                System.out.println("DEBUG: groupStringMap[" + tutoringClassId + "] = " + sb.toString());
//                System.out.println("DEBUG: durationMap[" + tutoringClassId + "] = " + duration);
//            }

for (TutoringClass tc : allTutoringClasses) {
    int tutoringClassId = tc.getTutoringClassID();
    if (addedClassIds.contains(tutoringClassId)) {
        continue;
    }
    addedClassIds.add(tutoringClassId);

    // Lấy danh sách class group (mỗi group 1 dòng, đã gộp lịch học)
    List<Object[]> groupList = classGroupDAO.getClassGroupDetailsWithStudentCount(tutoringClassId);
    StringBuilder sb = new StringBuilder();
    String duration = "Chưa xác định";

    if (groupList != null && !groupList.isEmpty()) {
        for (Object[] g : groupList) {
            sb
                .append(g[0]).append("~")                        // groupName
                .append(g[1]).append("~")                        // maxStudent
                .append(g[3] != null ? g[3] : "Không xác định").append(";"); // teacherName
        }

        // Tính duration từ ca đầu tiên (g[4], g[5])
        Object[] firstGroup = groupList.get(0);
        String startStr = firstGroup[4] != null ? firstGroup[4].toString().trim() : null;
        String endStr = firstGroup[5] != null ? firstGroup[5].toString().trim() : null;

        try {
            if (startStr != null && startStr.contains(".")) {
                startStr = startStr.split("\\.")[0];
            }
            if (endStr != null && endStr.contains(".")) {
                endStr = endStr.split("\\.")[0];
            }

            if (startStr != null && startStr.matches("\\d{2}:\\d{2}")) {
                startStr += ":00";
            }
            if (endStr != null && endStr.matches("\\d{2}:\\d{2}")) {
                endStr += ":00";
            }

            if (startStr != null && endStr != null
                    && startStr.matches("\\d{2}:\\d{2}:\\d{2}")
                    && endStr.matches("\\d{2}:\\d{2}:\\d{2}")) {

                Time startTime = Time.valueOf(startStr);
                Time endTime = Time.valueOf(endStr);
                Shift shift = new Shift(startTime, endTime);
                duration = shift.getDurationText();

                SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
                System.out.println("DEBUG: Shift đầu tiên: Start=" + fmt.format(startTime)
                        + ", End=" + fmt.format(endTime) + ", Duration=" + duration);
            } else {
                System.out.println("DEBUG ❌ Dữ liệu thời gian không hợp lệ: Start=" + startStr + ", End=" + endStr);
            }
        } catch (Exception e) {
            System.err.println("❌ Lỗi khi parse thời gian: " + e.getMessage());
            e.printStackTrace();
        }

    } else {
        System.out.println("DEBUG: KHÔNG có group nào cho TutoringClassID = " + tutoringClassId);
    }

    // Đưa dữ liệu vào map
    groupStringMap.put(tutoringClassId, sb.toString());
    durationMap.put(tutoringClassId, duration);
    System.out.println("DEBUG: groupStringMap[" + tutoringClassId + "] = " + sb.toString());
    System.out.println("DEBUG: durationMap[" + tutoringClassId + "] = " + duration);
}


            request.setAttribute("groupStringMap", groupStringMap);
            request.setAttribute("durationMap", durationMap);

            // 10. Các map tra cứu tên phòng, giáo viên
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

            // DEBUG: roomNames và teacherNames
            System.out.println("DEBUG: roomNames:");
            for (Map.Entry<Integer, String> entry : roomNames.entrySet()) {
                System.out.println("  RoomID=" + entry.getKey() + ", Name=" + entry.getValue());
            }
            System.out.println("DEBUG: teacherNames:");
            for (Map.Entry<Integer, String> entry : teacherNames.entrySet()) {
                System.out.println("  TeacherID=" + entry.getKey() + ", Name=" + entry.getValue());
            }

            // 11. Giáo viên & trường liên kết
            List<User> teachers = teacherDAO.getAllTeachers();
            request.setAttribute("teachers", teachers);
            Map<Integer, String> teacherSchoolNames = new HashMap<>();
            for (User teacher : teachers) {
                String schoolName = schoolDAO.getSchoolNameById(teacher.getSchoolID());
                teacherSchoolNames.put(teacher.getId(), (schoolName == null || schoolName.trim().isEmpty()) ? "Giáo viên của Edura" : schoolName);
            }
            request.setAttribute("teacherSchoolNames", teacherSchoolNames);

            // 12. Học sinh nổi bật và trường liên kết
            request.setAttribute("students", studentDAO.getTopStudents());
            request.setAttribute("schools", schoolDAO.getAllSchools());

            // Điều hướng đến JSP
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
        request.getRequestDispatcher("/" + jspPage).forward(request, response);
    }

    private int parseIntOrDefault(String value, int def) {
        try {
            return value == null ? def : Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return def;
        }
    }
}
