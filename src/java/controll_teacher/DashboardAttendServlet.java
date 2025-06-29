/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controll;

import dal.ClassGroupDAO;
import dal.GradeDAO;
import dal.ScheduleDAO;
import dal.SubjectDAO;
import entity.ClassGroup;
import entity.Grade;
import entity.ScheduleJoin;
import entity.Subject;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author NGOC ANH
 */
public class DashboardAttendServlet extends HttpServlet {

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
            out.println("<title>Servlet DashboardAttendServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DashboardAttendServlet at " + request.getContextPath() + "</h1>");
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
        if (session == null || session.getAttribute("user") == null) {
            // No session or userId, redirect to login page
            response.sendRedirect("login_register.jsp");
            return;
        }
        User sessionUser = (User) session.getAttribute("user");
        int userId = sessionUser.getId();

        ClassGroupDAO dao = new ClassGroupDAO();
        ArrayList<ClassGroup> todayClasses = new ArrayList<>();
        try {
            todayClasses = (ArrayList<ClassGroup>) dao.getTodayClasses(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ScheduleDAO daos = new ScheduleDAO();
        ScheduleJoin nextSchedule = null;
        try {
            nextSchedule = daos.getNextScheduleByTeacher(userId);
        } catch (SQLException e) {
            e.printStackTrace();
            // Optionally, set an error attribute or redirect to an error page
        }
       
        // Set the nextSchedule in session scope for EL access
        session.setAttribute("nextSchedule", nextSchedule);
        request.setAttribute("todayClasses", todayClasses);
        
        request.getRequestDispatcher("teacherdashboard.jsp").forward(request, response);
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
