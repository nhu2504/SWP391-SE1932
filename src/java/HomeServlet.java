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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // 1. ThĂ´ng tin trung tĂ¢m
            CenterInfo info = centerInfoDAO.getCenterInfo(1);
            if (info != null) {
                request.setAttribute("centerName", info.getNameCenter());
                request.setAttribute("address", info.getAddress());
                request.setAttribute("email", info.getEmail());
                request.setAttribute("phone", info.getPhone());
                request.setAttribute("descripCenter", info.getDescrip());
            }

            // 2. Khá»‘i lá»›p vĂ  mĂ´n há»c
            List<Grade> grades = gradeDAO.getAllGrades();
            List<Subject> subjects = subjectDAO.getSubjectsWithClassCount();
            request.setAttribute("grades", grades);
            request.setAttribute("subjects", subjects);

            // Map tra cá»©u tĂªn khá»‘i vĂ  tĂªn mĂ´n
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

            // 3. TĂ i liá»‡u theo khá»‘i vĂ  mĂ´n
            int gradeId = parseIntOrDefault(request.getParameter("gradeId"), 0);
            int subjectId = parseIntOrDefault(request.getParameter("subjectId"), 0);
            request.setAttribute("documents", documentDAO.getDocumentsByGradeAndSubject(gradeId, subjectId));

            // 4. Danh sĂ¡ch táº¥t cáº£ khĂ³a há»c
            request.setAttribute("classes", tutoringClassDAO.getClasses(null));

            // 5. Banner vĂ  cĂ¡c thĂ´ng sá»‘ trung tĂ¢m
            request.setAttribute("banners", bannerDAO.getAllBanners());
            int establishmentYear = centerInfoDAO.getYearOfWork();
            int yearsActive = Year.now().getValue() - establishmentYear;
            request.setAttribute("yearsActive", yearsActive);
            request.setAttribute("studentCount", studentDAO.getStudentCount());
            request.setAttribute("partnerSchoolsCount", schoolDAO.getPartnerSchoolsCount());

            // 6. KhĂ³a há»c ná»•i báº­t vĂ  quanh nÄƒm
            List<TutoringClass> featuredTutoringClasses = tutoringClassDAO.getFeaturedTutoringClasses();
            List<TutoringClass> yearRoundTutoringClasses = tutoringClassDAO.getYearRoundTutoringClasses();
            request.setAttribute("featuredTutoringClasses", featuredTutoringClasses);
            request.setAttribute("yearRoundTutoringClasses", yearRoundTutoringClasses);

            // Táº¡o formatter cho giá» phĂºt
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

            // 7. Láº¥y shift list (giá» báº¯t Ä‘áº§u/káº¿t thĂºc)
            List<Shift> allShifts = shiftDAO.getAllShifts();
            Map<Integer, String> shiftStartTimes = new HashMap<>();
            Map<Integer, String> shiftEndTimes = new HashMap<>();
            Map<Integer, Shift> shiftMap = new HashMap<>();

            for (Shift s : allShifts) {
                String formattedStart = s.getStartTime() != null ? timeFormat.format(s.getStartTime()) : "N/A";
                String formattedEnd = s.getEndTime() != null ? timeFormat.format(s.getEndTime()) : "N/A";

                shiftStartTimes.put(s.getId(), formattedStart);
                shiftEndTimes.put(s.getId(), formattedEnd);
                shiftMap.put(s.getId(), s); // váº«n giá»¯ nguyĂªn vĂ¬ Ä‘Ă¢y lĂ  Ä‘á»‘i tÆ°á»£ng Shift
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
            Set<Integer> addedClassIds = new HashSet<>();

            for (TutoringClass tc : allTutoringClasses) {
                int tutoringClassId = tc.getTutoringClassID();
                if (addedClassIds.contains(tutoringClassId)) {
                    continue;
                }
                addedClassIds.add(tutoringClassId);

                // Sá»­ dá»¥ng DAO má»›i Ä‘Ă£ cáº­p nháº­t: má»—i group chá»‰ 1 dĂ²ng, cĂ³ thĂªm trÆ°á»ng ngĂ y há»c
                List<Object[]> groupList = classGroupDAO.getClassGroupsWithRoomAndShift(tutoringClassId);
                StringBuilder sb = new StringBuilder();

                String duration = "ChÆ°a xĂ¡c Ä‘á»‹nh";
                if (groupList != null && !groupList.isEmpty()) {
                    for (Object[] g : groupList) {
                        // g[0]=ClassGroupName, g[1]=MaxStudent, g[2]=RoomName, 
                        // g[3]=TeacherName, g[4]=StartTime, g[5]=EndTime, g[6]=StudyDate
                        // TĂ­nh thá»© tá»« ngĂ y há»c
                        String thu;
                        if (g[6] != null) {
                            LocalDate date = ((java.sql.Date) g[6]).toLocalDate();
                            DayOfWeek dow = date.getDayOfWeek();
                            thu = dow.getDisplayName(TextStyle.FULL, new Locale("vi", "VN"));
                        } else {
                            thu = "null";
                        }

                        sb//.append(g[0]).append("~") // groupId
                                .append(g[0]).append("~") // groupName
                                .append(g[1]).append("~") // maxStudent
                                .append(g[2]).append("~") // roomName
                                .append(g[3] != null ? g[3] : "KhĂ´ng xĂ¡c Ä‘á»‹nh").append("~") // teacherName
                                .append(thu).append("~") // thá»© trong tuáº§n
                                .append(g[4] != null ? g[4] : "N/A").append("~") // startTime
                                .append(g[5] != null ? g[5] : "N/A").append(";"); // endTime

                        // DEBUG: Log group details
                        System.out.println("DEBUG: TutoringClassID=" + tutoringClassId + ", GroupID=" + g[0]
                                + ", GroupName=" + g[1] + ", TeacherName=" + g[4]
                                + ", Thu=" + thu
                                + ", StartTime=" + g[5] + ", EndTime=" + g[6]);
                    }

                    // Láº¥y duration tá»« ca Ä‘áº§u tiĂªn
                    Object[] firstGroup = groupList.get(0);

                    String startStr = firstGroup[4] != null ? firstGroup[4].toString().trim() : null;
                    String endStr = firstGroup[5] != null ? firstGroup[5].toString().trim() : null;

                    try {
                        // LĂ m sáº¡ch chuá»—i thá»i gian náº¿u cĂ³ Ä‘á»‹nh dáº¡ng láº¡ nhÆ° "00.0000000"
                        if (startStr != null && startStr.contains(".")) {
                            startStr = startStr.split("\\.")[0]; // láº¥y pháº§n trÆ°á»›c dáº¥u cháº¥m
                        }
                        if (endStr != null && endStr.contains(".")) {
                            endStr = endStr.split("\\.")[0];
                        }

                        // ThĂªm giĂ¢y náº¿u thiáº¿u
                        if (startStr != null && startStr.matches("\\d{2}:\\d{2}")) {
                            startStr += ":00";
                        }
                        if (endStr != null && endStr.matches("\\d{2}:\\d{2}")) {
                            endStr += ":00";
                        }

                        // Kiá»ƒm tra láº§n cuá»‘i
                        if (startStr != null && endStr != null
                                && startStr.matches("\\d{2}:\\d{2}:\\d{2}")
                                && endStr.matches("\\d{2}:\\d{2}:\\d{2}")) {

                            Time startSqlTime = Time.valueOf(startStr);
                            Time endSqlTime = Time.valueOf(endStr);

                            Shift shift = new Shift(startSqlTime, endSqlTime);
                            duration = shift.getDurationText();

                            SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
                            System.out.println("DEBUG: Shift Ä‘áº§u tiĂªn: Start=" + fmt.format(startSqlTime)
                                    + ", End=" + fmt.format(endSqlTime) + ", Duration=" + duration);
                        } else {
                            System.out.println("DEBUG âŒ Dá»¯ liá»‡u thá»i gian khĂ´ng há»£p lá»‡: Start=" + startStr + ", End=" + endStr);
                        }

                    } catch (Exception e) {
                        System.err.println("âŒ Lá»—i khi parse thá»i gian: " + e.getMessage());
                        e.printStackTrace();
                    }

                } else {
                    System.out.println("DEBUG: KHĂ”NG cĂ³ group nĂ o cho TutoringClassID = " + tutoringClassId);
                }
                groupStringMap.put(tutoringClassId, sb.toString());
                durationMap.put(tutoringClassId, duration);
                System.out.println("DEBUG: groupStringMap[" + tutoringClassId + "] = " + sb.toString());
                System.out.println("DEBUG: durationMap[" + tutoringClassId + "] = " + duration);
            }

            request.setAttribute("groupStringMap", groupStringMap);
            request.setAttribute("durationMap", durationMap);

            // 10. CĂ¡c map tra cá»©u tĂªn phĂ²ng, giĂ¡o viĂªn
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

            // DEBUG: roomNames vĂ  teacherNames
            System.out.println("DEBUG: roomNames:");
            for (Map.Entry<Integer, String> entry : roomNames.entrySet()) {
                System.out.println("  RoomID=" + entry.getKey() + ", Name=" + entry.getValue());
            }
            System.out.println("DEBUG: teacherNames:");
            for (Map.Entry<Integer, String> entry : teacherNames.entrySet()) {
                System.out.println("  TeacherID=" + entry.getKey() + ", Name=" + entry.getValue());
            }

            // 11. GiĂ¡o viĂªn & trÆ°á»ng liĂªn káº¿t
            List<User> teachers = teacherDAO.getAllTeachers();
            request.setAttribute("teachers", teachers);
            Map<Integer, String> teacherSchoolNames = new HashMap<>();
            for (User teacher : teachers) {
                String schoolName = schoolDAO.getSchoolNameById(teacher.getSchoolID());
                teacherSchoolNames.put(teacher.getId(), (schoolName == null || schoolName.trim().isEmpty()) ? "GiĂ¡o viĂªn cá»§a Edura" : schoolName);
            }
            request.setAttribute("teacherSchoolNames", teacherSchoolNames);

            // 12. Há»c sinh ná»•i báº­t vĂ  trÆ°á»ng liĂªn káº¿t
            request.setAttribute("students", studentDAO.getTopStudents());
            request.setAttribute("schools", schoolDAO.getAllSchools());

            // 13. Náº¿u cĂ³ courseId: láº¥y chi tiáº¿t khĂ³a há»c (TutoringClass vĂ  List<ClassGroup>)
            String courseIdParam = request.getParameter("courseId");
            TutoringClass selectedTutoringClass = null;
            List<ClassGroup> selectedClassGroups = new ArrayList<>();
            String selectedGradeName = "";

            if (courseIdParam != null) {
                int tutoringClassID = parseIntOrDefault(courseIdParam, -1);
                if (tutoringClassID > 0) {
                    selectedTutoringClass = tutoringClassDAO.getTutoringClassDetail(tutoringClassID);
                    if (selectedTutoringClass != null) {
                        // Láº¤Y GROUP ÄĂNG Tá»ª CLASSGROUPDAO
                        selectedClassGroups = classGroupDAO.getClassGroupsByTutoringClassId(tutoringClassID);
                        selectedGradeName = gradeNames.get(selectedTutoringClass.getGradeID());
                    }
                }
            }
            request.setAttribute("selectedTutoringClass", selectedTutoringClass);
            request.setAttribute("selectedClassGroups", selectedClassGroups);
            request.setAttribute("selectedGradeName", selectedGradeName);
            // Äiá»u hÆ°á»›ng Ä‘áº¿n JSP
            forwardToJsp(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lá»—i: " + e.getMessage());
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
