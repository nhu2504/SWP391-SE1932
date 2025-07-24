/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controll_student;

import dal.ClassGroupDAO;
import dal.ClassifyDAO;
import dal.GradeDAO;
import dal.ScheduleDAO;
import dal.SubjectDAO;
import entity.ClassGroup;
import entity.Classify;
import entity.Grade;
import entity.ScheduleJoin;
import entity.Subject;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author NGOC ANH
 */
@WebServlet(name="StudentDashboardServlet", urlPatterns={"/studentdashboard"})
public class StudentDashboardServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet StudentDashboardServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StudentDashboardServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
        SubjectDAO subdao = new SubjectDAO();
        ArrayList<Subject> listsub = new ArrayList<>();
        try {
            listsub = (ArrayList<Subject>) subdao.getAllSubjects();
        } catch (Exception e) {
            e.printStackTrace();
        }
        GradeDAO gdao = new GradeDAO();
        ArrayList<Grade> listgrade = new ArrayList<>();
        try {
            listgrade = (ArrayList<Grade>) gdao.getAllGrades();
        } catch (Exception e) {
        }
        ClassifyDAO cdao = new ClassifyDAO();
        ArrayList<Classify> listclassify = new ArrayList<>();
        try {
            listclassify = (ArrayList<Classify>) cdao.getAllClassify();
        } catch (Exception e) {
        }
        
        // Set the nextSchedule in session scope for EL access
        session.setAttribute("nextSchedule", nextSchedule);
        session.setAttribute("todayClasses", todayClasses);
        session.setAttribute("listsub", listsub);
        session.setAttribute("listgrade", listgrade);
        session.setAttribute("listclassify", listclassify);
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
