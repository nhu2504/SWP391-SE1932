///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
// */
//
import com.google.gson.Gson; 
import dal.ScheduleDAO; 
import entity.Option; 
import java.io.IOException;
import java.io.PrintWriter; 
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet; 
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest; 
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList; 
import java.util.Arrays;
import java.util.List;
import java.util.Map; 
import java.util.stream.Collectors;

@WebServlet("/ScheduleOptionsServlet")
public class ScheduleOptionsServlet extends HttpServlet {

    private final ScheduleDAO dao = new ScheduleDAO();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String teacherId = request.getParameter("teacherId");
        String day = request.getParameter("day");
        String shift = request.getParameter("shift");
        String courseIdRaw = request.getParameter("courseId");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            List<Option> list = new ArrayList<>();

            switch (action) {
                case "teachers":
                    // Lấy danh sách giáo viên theo khóa học
                    if (courseIdRaw == null) {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu courseId");
                        return;
                    }
                    int courseId = Integer.parseInt(courseIdRaw);
                    list = dao.getTeachersByCourse(courseId);
                    writeJson(response, "teachers", list);
                    break;

                case "days":
                    // Lấy danh sách ngày dạy còn trống theo giáo viên
                    if (teacherId == null || teacherId.isEmpty()) {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu teacherId");
                        return;
                    }
                    list = dao.getAvailableDays(teacherId);

                    String[] usedDays = request.getParameterValues("usedDays");
                    if (usedDays != null) {
                        List<String> usedDaysList = Arrays.asList(usedDays);
                        list = list.stream()
                                .filter(opt -> !usedDaysList.contains(opt.getValue()))
                                .collect(Collectors.toList());
                    }
                    writeJson(response, "days", list);
                    break;

                case "shifts":
                    // Lấy danh sách ca dạy còn trống theo giáo viên và ngày
                    if (teacherId == null || day == null) {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu teacherId hoặc day");
                        return;
                    }
                    list = dao.getAvailableShiftsForTeacher(teacherId, day);

                    String[] usedShifts = request.getParameterValues("usedShifts");
                    if (usedShifts != null) {
                        list.removeIf(opt -> Arrays.asList(usedShifts).contains(opt.getValue()));
                    }
                    writeJson(response, "shifts", list);
                    break;

                case "rooms":
                    // Lấy danh sách phòng còn trống theo ca và ngày
                    if (day == null || shift == null) {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu day hoặc shift");
                        return;
                    }
                    list = dao.getAvailableRooms(day, shift);

                    String[] usedRooms = request.getParameterValues("usedRooms");
                    if (usedRooms != null) {
                        list.removeIf(opt -> Arrays.asList(usedRooms).contains(opt.getValue()));
                    }
                    writeJson(response, "rooms", list);
                    break;

                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu hoặc sai action");
            }

        } catch (Exception e) {
            e.printStackTrace();
            writeJson(response, "error", e.getMessage());
        }
    }


    private void writeJson(HttpServletResponse response, String key, Object value) throws IOException {
        response.getWriter().write(gson.toJson(Map.of(key, value)));
    }
}
