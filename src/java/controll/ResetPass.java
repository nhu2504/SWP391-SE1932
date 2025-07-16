/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controll;

import dal.TokenForgetPassDAO;
import dal.UserDAO;
import entity.TokenForgetPass;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author NGOC ANH
 */
@WebServlet(name = "ResetPass", urlPatterns = {"/resetpass"})
public class ResetPass extends HttpServlet {
   
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
            out.println("<title>Servlet ResetPass</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ResetPass at " + request.getContextPath () + "</h1>");
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
        String token = request.getParameter("token");
        if (token == null || token.isEmpty()) {
            request.setAttribute("errorMessage", "Link đặt lại mật khẩu không hợp lệ!");
            request.getRequestDispatcher("resetPass.jsp").forward(request, response);
            return;
        }
        request.setAttribute("token", token); // giữ lại token cho form POST
        request.getRequestDispatcher("resetPass.jsp").forward(request, response);
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
       String token = request.getParameter("token");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (token == null || token.isEmpty()) {
            request.setAttribute("errorMessage", "Link đặt lại mật khẩu không hợp lệ!");
            request.getRequestDispatcher("resetPass.jsp").forward(request, response);
            return;
        }

        if (newPassword == null || confirmPassword == null || !newPassword.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Mật khẩu và mật khẩu xác nhận không trùng nhau!");
            request.setAttribute("token", token); // giữ lại token cho lần submit lại
            request.getRequestDispatcher("resetPass.jsp").forward(request, response);
            return;
        }

        // Kiểm tra token hợp lệ
        TokenForgetPassDAO tfpDAO = new TokenForgetPassDAO();
        TokenForgetPass tfp = tfpDAO.getByToken(token);
        if (tfp == null || tfp.isIsUsed() || tfp.isExpired()) {
            request.setAttribute("errorMessage", "Link đặt lại mật khẩu không hợp lệ hoặc đã hết hạn!");
            request.getRequestDispatcher("resetPass.jsp").forward(request, response);
            return;
        }

        // Lấy user và cập nhật mật khẩu
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByID(tfp.getUserId());
        if (user == null) {
            request.setAttribute("errorMessage", "Người dùng không tồn tại!");
            request.getRequestDispatcher("resetPass.jsp").forward(request, response);
            return;
        }

        // Nếu cần mã hóa mật khẩu, hãy hash tại đây!
        boolean updated = userDAO.updatePassword(user.getId(), newPassword);
        if (updated) {
            tfpDAO.markTokenUsed(token); // Đánh dấu token đã dùng
            request.setAttribute("successMessage", "Đặt lại mật khẩu thành công! Vui lòng đăng nhập lại.");
        } else {
            request.setAttribute("errorMessage", "Có lỗi xảy ra khi cập nhật mật khẩu.");
        }
        request.getRequestDispatcher("resetPass.jsp").forward(request, response);
    
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
