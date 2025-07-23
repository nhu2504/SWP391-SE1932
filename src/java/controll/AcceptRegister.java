/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controll;

import dal.RegisterDAO;
import dal.UserDAO;
import dal.reset;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author NGOC ANH
 */
@WebServlet(name = "AcceptRegister", urlPatterns = {"/acceptregister"})
public class AcceptRegister extends HttpServlet {

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
            out.println("<title>Servlet AcceptRegister</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AcceptRegister at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) {
            response.sendRedirect("login_register.jsp");
            return;
        }
        int userId = sessionUser.getId();
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserById(userId);
        if (user == null) {
            request.setAttribute("error", "Không tìm thấy thông tin người dùng");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        int regisID = Integer.parseInt(request.getParameter("regisID"));
   

        RegisterDAO dao = new RegisterDAO();
        boolean update = dao.updateStatus(regisID, "Accepted");
        boolean approve = dao.approveUserByProcedure(regisID);
        User newUser = userDAO.getLatestUserInfo();
        
        if(update&&approve){
            reset rs = new reset();
            boolean mail =rs.sendApprovalEmail(
                    
                    newUser.getEmail(),
                    newUser.getPassword(), 
                    newUser.getName());
            if(!mail){
                System.out.println("Gửi mail thất bại cho: "+newUser.getEmail());
            }
            session.setAttribute("SuccessMessage", "Tạo tài khoản thành công");
        }else{
            System.out.println("Duyệt thất bại");
            session.setAttribute("FailMessage", "Tạo tài khoản thất bại");
        }

        response.sendRedirect("listregister"); // quay lại trang danh sách
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
