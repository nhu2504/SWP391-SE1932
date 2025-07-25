/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package UserProfile;


import dal.UserDAO;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;

/**
 *
 * @author NGOC ANH
 */
@MultipartConfig
@WebServlet(name="UpdateStudentProfile", urlPatterns={"/updatestudentprofile"})
public class UpdateStudentProfile extends HttpServlet {
   
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
            out.println("<title>Servlet UpdateStudentProfile</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateStudentProfile at " + request.getContextPath () + "</h1>");
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
        response.sendRedirect("login");
        return;
    }

    User user = (User) session.getAttribute("user");
    int userId = user.getId();

    String email = request.getParameter("email");
    String phone = request.getParameter("phone");
    String description = request.getParameter("description");

    // Giữ nguyên nếu trống/null
    if (email == null || email.trim().isEmpty()) {
        email = user.getEmail();
    }
    if (phone == null || phone.trim().isEmpty()) {
        phone = user.getPhone();
    }
    if (description == null) {
        description = user.getDescrip();
    }

    UserDAO userDao = new UserDAO();

    // Upload ảnh
    Part part = request.getPart("avatarFile");
    String uploadDirPath = "D:/MyUploads/Images";
    File uploadDir = new File(uploadDirPath);
    if (!uploadDir.exists()) {
        uploadDir.mkdirs();
    }

    String avatarPath;
    if (part != null && part.getSize() > 0) {
        // Xóa ảnh cũ nếu có
        String oldAvatarPath = user.getAvatar();
        if (oldAvatarPath != null && oldAvatarPath.contains("/image-loader/")) {
            String oldFileName = oldAvatarPath.substring((request.getContextPath() + "/image-loader/").length());
            File oldFile = new File(uploadDir, oldFileName);
            if (oldFile.exists()) {
                oldFile.delete();
            }
        }

        // Lưu file mới
        String fileExtension = part.getSubmittedFileName().substring(part.getSubmittedFileName().lastIndexOf("."));
        String randomFileName = java.util.UUID.randomUUID().toString() + fileExtension;
        File newFile = new File(uploadDir, randomFileName);
        part.write(newFile.getAbsolutePath());

        avatarPath = request.getContextPath() + "/image-loader/" + randomFileName;
    } else {
        avatarPath = user.getAvatar(); // giữ nguyên ảnh cũ
    }

    boolean update = userDao.updateStudentProfile(userId, email, phone, avatarPath, description);

    if (update) {
        User updatedUser = userDao.getUserById(userId);
        session.setAttribute("user", updatedUser);
        session.setAttribute("SuccessMessage", "Đã lưu thay đổi thành công.");
    } else {
        session.setAttribute("FailMessage", "Thay đổi chưa được lưu. Đã xảy ra lỗi.");
    }

    response.sendRedirect("Studentprofile");
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
