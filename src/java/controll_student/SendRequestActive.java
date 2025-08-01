/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controll_student;

import dal.RequestActiveDAO;
import dal.UserDAO;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author NGOC ANH
 */
@WebServlet(name = "SendRequestActive", urlPatterns = {"/sendrequestactive"})
public class SendRequestActive extends HttpServlet {

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
            out.println("<title>Servlet SendRequestActive</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SendRequestActive at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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
        UserDAO dao = new UserDAO();
        String email = request.getParameter("email");
        User u = dao.getUserByEmail(email);
        if (u == null) {
            request.setAttribute("mess", "Email không tồn tại");
            request.getRequestDispatcher("requestactive.jsp").forward(request, response);
            return;
        }
        String name = "";
    String email1 = "";
    String birthStr = "";
    String school = "";
    String classAtSchool = "";
        try {
            request.setCharacterEncoding("UTF-8");

            name = request.getParameter("name");
            email1 = request.getParameter("email");
            birthStr = request.getParameter("birth");
            school = request.getParameter("school");
            classAtSchool = request.getParameter("classAtSchool");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date birth = sdf.parse(birthStr);

            RequestActiveDAO ra = new RequestActiveDAO();
            boolean success = ra.insertRequest(name, email1, birth, school, classAtSchool);

            if (success) {
                request.setAttribute("message", "Gửi yêu cầu thành công!");
            } else {
                request.setAttribute("message", "Có lỗi xảy ra khi gửi yêu cầu.");
            }

        } catch (Exception e) {
            request.setAttribute("message", "Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
        request.setAttribute("name", name);
        request.setAttribute("email", email);
        request.setAttribute("birth", birthStr);
        request.setAttribute("school", school);
        request.setAttribute("classAtSchool", classAtSchool);

        request.getRequestDispatcher("requestactive.jsp").forward(request, response);

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
