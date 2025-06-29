/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controll_teacher;

import dal.AttendanceDAO;
import dal.ClassGroup_StudentDAO;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author NGOC ANH
 */
public class SubmitAttend extends HttpServlet {
   
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
            out.println("<title>Servlet SubmitAttend</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SubmitAttend at " + request.getContextPath () + "</h1>");
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
        processRequest(request, response);
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
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login_register.jsp");
            return;
        }
        int classGroupId = Integer.parseInt(request.getParameter("classGroupId"));
        ClassGroup_StudentDAO classGroupDAO = new ClassGroup_StudentDAO();
        AttendanceDAO attendanceDAO = new AttendanceDAO();

        List<User> students = classGroupDAO.getStudentsByClassGroupId(classGroupId);
        Map<Integer, Boolean> attendanceMap = new HashMap<>();

        for (User s : students) {
            String param = request.getParameter("status_" + s.getId());
            if (param != null) {
                boolean isPresent = "1".equals(param);
                attendanceMap.put(s.getId(), isPresent);
            }
        }
        try {
            attendanceDAO.saveAttendance(classGroupId, attendanceMap);
            // Chuyển hướng 
             response.sendRedirect("attendancestudent.jsp?attendSuccess=true");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi lưu vào database: " + e.getMessage());
        }

        
       
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
