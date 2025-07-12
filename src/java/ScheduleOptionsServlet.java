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
//    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
//        String courseIdRaw = req.getParameter("courseId");
//        if (courseIdRaw == null || courseIdRaw.trim().isEmpty()) {
//            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu courseId");
//            return;
//        }
//
//        int tutoringClassId;
//        try {
//            tutoringClassId = Integer.parseInt(courseIdRaw);
//        } catch (NumberFormatException e) {
//            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "courseId không hợp lệ");
//            return;
//        }
//
//        // Lấy các param khác
//        String teacherId = req.getParameter("teacherId");
//        String day = req.getParameter("dayOfWeek");
//        String shift = req.getParameter("shiftId");
//        String room = req.getParameter("roomId");
//
//        ScheduleDAO dao = new ScheduleDAO();
//
//        List<Option> teachers = dao.getAvailableTeachers(tutoringClassId, day, shift, room);
//        List<Option> days = dao.getAvailableDays(teacherId, shift, room);
//        List<Option> shifts = dao.getAvailableShifts(teacherId, day, room);
//        List<Option> rooms = dao.getAvailableRooms(day, shift);
//
//        
//        Map<String, List<Option>> data = new HashMap<>();
//        data.put("teachers", teachers);
//        data.put("days", days);
//        data.put("shifts", shifts);
//        data.put("rooms", rooms);
//
//        res.setContentType("application/json");
//        res.getWriter().write(new Gson().toJson(data));
//        System.out.println("courseId: " + tutoringClassId);
//        System.out.println("teacherId: " + teacherId);
//        System.out.println("day: " + day);
//        System.out.println("shift: " + shift);
//        System.out.println("room: " + room);
//        System.out.println("Available Days:");
//        for (Option opt : days) {
//            System.out.println(opt.getValue() + " - " + opt.getLabel());
//        }
//
//        System.out.println("==> Lấy teacher với tutoringClassId=" + tutoringClassId + ", day=" + day + ", shift=" + shift + ", room=" + room);
//
//    }

//}
 

    
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