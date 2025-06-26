/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controll;

import dal.RoleDAO;
import dal.SchoolClassDAO;
import dal.SchoolDAO;
import dal.UserDAO;
import entity.Roles;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Map;

/**
 *
 * @author NGOC ANH
 */
public class ProfileServlet extends HttpServlet {

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
            out.println("<title>Servlet ProfileServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProfileServlet at " + request.getContextPath() + "</h1>");
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
    HttpSession session = request.getSession();
    User sessionUser = (User) session.getAttribute("user");

    if (sessionUser == null) {
        response.sendRedirect("login_register.jsp");
        return;
    }

    int userId = sessionUser.getId();

    UserDAO userDAO = new UserDAO();
    User user = userDAO.getUserByID(userId);

    if (user == null) {
        request.setAttribute("error", "Không tìm thấy thông tin người dùng");
        request.getRequestDispatcher("error.jsp").forward(request, response);
        return;
    }

    SchoolDAO sdao = new SchoolDAO();
    SchoolClassDAO scdao = new SchoolClassDAO();

    String schoolName = sdao.getSchoolNameById(user.getSchoolID());
    String classSchoolName = scdao.getSchoolClassNameById(user.getClassID());

    RoleDAO roleDAO = new RoleDAO();
    String roleName = roleDAO.getRoleNameByID(user.getRoleID());
    if (roleName == null) roleName = "Unknown";

    Map<String, String> roleNameViMap = Map.of(
            "admin", "Quản trị viên",
            "teacher", "Giáo viên",
            "student", "Học sinh",
            "manager", "Quản lý",
            "Unknown", "Không xác định"
    );

    String roleNameVi = roleNameViMap.getOrDefault(roleName, "Không xác định");

    request.setAttribute("user", user);
    request.setAttribute("schoolName", schoolName);
    request.setAttribute("classSchoolName", classSchoolName);
    request.setAttribute("roleNameVi", roleNameVi);

    request.getRequestDispatcher("teacherprofile.jsp").forward(request, response);
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
