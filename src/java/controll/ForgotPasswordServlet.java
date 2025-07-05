/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controll;

import dal.TokenForgetPassDAO;
import dal.UserDAO;
import dal.reset;
import entity.TokenForgetPass;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

/**
 *
 * @author NGOC ANH
 */
public class ForgotPasswordServlet extends HttpServlet {

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
            out.println("<title>Servlet ForgotPasswordServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ForgotPasswordServlet at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher("login_register.jsp").forward(request, response);
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
            request.getRequestDispatcher("requestPass.jsp").forward(request, response);
            return;
        }

        reset rs = new reset();
        String token = rs.generateToken();
        String linkReset = "http://localhost:9999/WebApplication3/resetpass?token=" + token;
        TokenForgetPass tfp = new TokenForgetPass(
                token,
                rs.expDateTime(),
                false,
                u.getId());
        //gửi link cho email này
        TokenForgetPassDAO tfpdao = new TokenForgetPassDAO();
        boolean isInsert = tfpdao.insertTokenForget(tfp);
        if (!isInsert) {
            request.setAttribute("mess", "Lỗi khi insert");
            request.getRequestDispatcher("requestPass.jsp").forward(request, response);
            return;
        }
        boolean isSend = rs.sendEmail(email, linkReset, u.getName());
        if (!isSend) {
            request.setAttribute("mess", "Lỗi khi gửi mail");
            request.getRequestDispatcher("requestPass.jsp").forward(request, response);
            return;
        }
        request.setAttribute("mess", "Đường link đặt lại mật khẩu đã được gửi tới email của bạn!");
        request.getRequestDispatcher("requestPass.jsp").forward(request, response);
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
