/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/ScheduleOptionsServlet")
public class ScheduleOptionsServlet extends HttpServlet {

    @Override     
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    String action = request.getParameter("action");
    String teacherId = request.getParameter("teacherId");
    String day = request.getParameter("day");
    String shift = request.getParameter("shift");
    String courseIdRaw = request.getParameter("courseId");

    ScheduleDAO dao = new ScheduleDAO();
    List<Option> list = new ArrayList<>();

    switch (action) {
        case "teachers":
            if (courseIdRaw == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu courseId");
                return;
            }
            int courseId = Integer.parseInt(courseIdRaw);
            list = dao.getTeachersByCourse(courseId);
            writeJson(response, "teachers", list);
            break;

        case "days":
            if (teacherId == null || teacherId.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu teacherId");
                return;
            }
            list = dao.getAvailableDays(teacherId);
            writeJson(response, "days", list);
            break;

        case "shifts":
            if (teacherId == null || day == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu teacherId hoặc day");
                return;
            }
            list = dao.getAvailableShiftsForTeacher(teacherId, day);
            writeJson(response, "shifts", list);
            break;

        case "rooms":
            if (day == null || shift == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu day hoặc shift");
                return;
            }
            list = dao.getAvailableRooms(day, shift);
            writeJson(response, "rooms", list);
            break;

        default:
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu hoặc sai action");
    }
}

private void writeJson(HttpServletResponse response, String key, List<Option> options) throws IOException {
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    StringBuilder json = new StringBuilder();
    json.append("{\"").append(key).append("\":[");

    for (int i = 0; i < options.size(); i++) {
        Option opt = options.get(i);
        json.append("{\"value\":\"").append(opt.getValue())
            .append("\",\"label\":\"").append(opt.getLabel()).append("\"}");
        if (i < options.size() - 1) {
            json.append(",");
        }
    }

    json.append("]}");
    response.getWriter().write(json.toString());
}
}