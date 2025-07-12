/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controll;

import dal.NotificationDAO;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import entity.User;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author NGOC ANH
 */
public class CreateNotificationServlet extends HttpServlet {
   
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
            out.println("<title>Servlet CreateNotificationServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateNotificationServlet at " + request.getContextPath () + "</h1>");
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

        User admin = (User) session.getAttribute("user");
        int createdBy = admin.getId();
        
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        boolean isImportant = request.getParameter("isImportant") != null;
        String targetType = request.getParameter("targetType");

        NotificationDAO dao = new NotificationDAO();
        UserDAO userDao = new UserDAO();

        List<Integer> userIds = new ArrayList<>();

        if ("all".equals(targetType)) {
            userIds = userDao.getAllUserIds();
        } else if ("role".equals(targetType)) {
            String[] roles = request.getParameterValues("roles");
            if (roles != null) {
                for (String r : roles) {
                    userIds.addAll(userDao.getUserIdsByRole(Integer.parseInt(r)));
                }
            }
        } else if ("user".equals(targetType)) {
            String[] recipients = request.getParameterValues("recipients");
            if (recipients != null) {
                for (String uid : recipients) {
                    userIds.add(Integer.parseInt(uid));
                }
            }
        }

        int notiId = dao.createNotification(title, content, createdBy, isImportant);
        for (int uid : userIds) {
            dao.addRecipient(notiId, uid);
        }

       request.setAttribute("tab", "createNotification");

        request.getRequestDispatcher("admin_dashboard.jsp").forward(request, response);
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
