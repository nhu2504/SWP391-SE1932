/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package UserProfile;

import dal.RoleDAO;
import dal.SchoolClassDAO;
import dal.SchoolDAO;
import dal.SubjectDAO;
import dal.UserDAO;
import entity.School;
import entity.SchoolClass;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author NGOC ANH
 */
@WebServlet(name="StudentProfile", urlPatterns={"/Studentprofile"})
public class StudentProfile extends HttpServlet {
   
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
            out.println("<title>Servlet StudentProfile</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StudentProfile at " + request.getContextPath () + "</h1>");
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
        SchoolDAO schoolDAO = new SchoolDAO();
        SchoolClassDAO scdao = new SchoolClassDAO();
        

        // Lấy tất cả trường học để hiển thị select
        List<School> allSchools = schoolDAO.getAllSchools();

        // Lấy tất cả lớp của trường hiện tại hoặc toàn hệ thống tuỳ logic (ở đây lấy theo trường user đang thuộc)
        List<SchoolClass> allClasses = scdao.getAllClassesBySchoolId(user.getSchoolID());
        // Nếu muốn lấy toàn bộ lớp hệ thống: List<SchoolClass> allClasses = classDAO.getAllClasses();

        // Lấy danh sách lớp và chuyên môn của user (để tick checkbox)
        List<SchoolClass> userSchoolClasses = user.getSchoolClasses() != null ? user.getSchoolClasses() : new ArrayList<>();
        

        List<String> classIdsOfUser = userSchoolClasses.stream()
        .map(sc -> String.valueOf(sc.getSchoolClassID()))
        .collect(Collectors.toList());


        // Xâu tên lớp và chuyên môn cho hiển thị thông tin nhanh
        String classSchoolNames = (userSchoolClasses != null && !userSchoolClasses.isEmpty())
                ? userSchoolClasses.stream().map(SchoolClass::getClassName).reduce((a, b) -> a + ", " + b).orElse("")
                : "Tự do";

       
        // Lấy tên trường của user
        String schoolName = schoolDAO.getSchoolNameById(user.getSchoolID());

        // Lấy tên vai trò
        RoleDAO roleDAO = new RoleDAO();
        String roleName = roleDAO.getRoleNameByID(user.getRoleID());
        if (roleName == null) {
            roleName = "Unknown";
        }

        Map<String, String> roleNameViMap = Map.of(
                "admin", "Quản trị viên",
                "teacher", "Giáo viên",
                "student", "Học sinh",
                "manager", "Quản lý",
                "Unknown", "Không xác định"
        );
        String roleNameVi = roleNameViMap.getOrDefault(roleName, "Không xác định");

        // Set các attribute cho JSP
        request.setAttribute("user", user);
        request.setAttribute("schoolName", schoolName);
        request.setAttribute("classSchoolNames", classSchoolNames);
        
        request.setAttribute("roleNameVi", roleNameVi);

        request.setAttribute("allSchools", allSchools);
        request.setAttribute("allClasses", allClasses);
        
        request.setAttribute("classIdsOfUser", classIdsOfUser);
        
        request.setAttribute("schoolIdSelected", user.getSchoolID());

        request.getRequestDispatcher("studentprofile.jsp").forward(request, response);
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
