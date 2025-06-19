/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */


import dal.UserDAO;

import entity.User;

import java.io.IOException;

import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.List;

/**
 *
 * @author DO NGOC ANH HE180661
 */
public class LoginServlet extends HttpServlet {

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
            out.println("<title>Servlet RegisterServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegisterServlet at " + request.getContextPath() + "</h1>");
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
        
        String email = request.getParameter("loginEmail");
        String pass = request.getParameter("loginPassword");
        
       

        UserDAO ld = new UserDAO();
        User acc = ld.login(email, pass);
        if (acc == null) {
            request.setAttribute("error", "ÄÄƒng nháº­p khĂ´ng thĂ nh cĂ´ng. Kiá»ƒm tra láº¡i email, máº­t kháº©u vĂ  vai trĂ² cá»§a báº¡n");
            request.getRequestDispatcher("login_register.jsp").forward(request, response);
            return;
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("account", acc);
            session.setAttribute("userName", acc.getName());
            session.setAttribute("userAvatar", acc.getAvatar());
            session.setAttribute("userRoleID", acc.getRoleID());
            // Äiá»u hÆ°á»›ng theo vai trĂ²
            int roleId = acc.getRoleID();
            switch (roleId) {
                case 1:
                    response.sendRedirect(request.getContextPath() + "/home");
                    break;

                case 2:
                    response.sendRedirect("teacherdashboard.jsp");
                    break;
                case 3:
                    response.sendRedirect("dashboard.jsp");
                    break;
                
                case 4:
                    response.sendRedirect("manager_dashboard.jsp");
                    break;
                default:
                    response.sendRedirect("Home.jsp");
            }
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
