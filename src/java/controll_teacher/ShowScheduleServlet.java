/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controll_teacher;

import dal.ScheduleDAO;
import dal.ShiftLearnDAO;
import entity.ScheduleJoin;
import entity.Shift;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author NGOC ANH
 */
public class ShowScheduleServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ShowScheduleServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ShowScheduleServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Get session without creating a new one
        if (session == null || session.getAttribute("userId") == null) {
            // No session or userId, redirect to login page
            response.sendRedirect("login_register.jsp");
            return;
        }

        int userId = Integer.parseInt(session.getAttribute("userId").toString());
        String weekOffsetRaw = request.getParameter("weekOffset");
        int weekOffset = 0;
        if (weekOffsetRaw != null) {
            try {
                weekOffset = Integer.parseInt(weekOffsetRaw);
            } catch (NumberFormatException e) {
                weekOffset = 0;
            }
        }
        try {
            // 1. Tính ngày đầu tuần (Thứ 2) và cuối tuần (Chủ nhật)
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            // Đặt về Thứ 2
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            cal.add(Calendar.WEEK_OF_YEAR, weekOffset);
            Date startOfWeek = new java.sql.Date(cal.getTimeInMillis());
            // Đặt về Chủ nhật
            cal.add(Calendar.DATE, 6);
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 999);
            
           Date endOfWeek = new java.sql.Date(cal.getTimeInMillis());
            
            //Date endOfWeek = new Date(cal.getTimeInMillis());

            // 2. Lấy danh sách ca học
            ShiftLearnDAO shiftDao = new ShiftLearnDAO();
            List<Shift> listShift = shiftDao.getAllShifts();

            // 3. Lấy lịch dạy tuần này của giáo viên
            ScheduleDAO scheduleDao = new ScheduleDAO();
            List<ScheduleJoin> schedules = scheduleDao.getSchedulesByWeek(userId, startOfWeek, endOfWeek);
            // 4. Map<ShiftID, Map<DayOfWeek, List<ScheduleJoin>>>
            Map<String, Map<Integer, List<ScheduleJoin>>> scheduleMap = new HashMap<>();
            for (ScheduleJoin s : schedules) {
                String shiftKey = s.getStartTime().toString() + "-" + s.getEndTime().toString();
                Calendar tmp = Calendar.getInstance();
                tmp.setTime(s.getDateLearn());
                int dayOfWeek = tmp.get(Calendar.DAY_OF_WEEK); // Chủ nhật=1, Thứ 2=2,..., Thứ 7=7

                scheduleMap
                        .computeIfAbsent(shiftKey, k -> new HashMap<>())
                        .computeIfAbsent(dayOfWeek, k -> new ArrayList<>())
                        .add(s);
            }

            // 5. Truyền dữ liệu lên JSP
            request.setAttribute("listShift", listShift);
            request.setAttribute("scheduleMap", scheduleMap);
            request.setAttribute("weekOffset", weekOffset);

            // Gửi thêm label tuần (ví dụ: "24/06/2025 - 30/06/2025")
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String weekLabel = sdf.format(startOfWeek) + " - " + sdf.format(endOfWeek);
            request.setAttribute("weekLabel", weekLabel);
            request.getRequestDispatcher("teachschedule.jsp").forward(request, response);

        } catch (Exception ex) {
            ex.printStackTrace();

        }

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
