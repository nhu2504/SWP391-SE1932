/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package UserProfile;

import dal.SubjectDAO;
import dal.TeacherClassDAO;
import dal.UserDAO;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NGOC ANH
 */
@MultipartConfig
public class UploadProfileServlet extends HttpServlet {


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
            out.println("<title>Servlet UploadProfileServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UploadProfileServlet at " + request.getContextPath() + "</h1>");
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
 request.getRequestDispatcher("teacherprofile.jsp").forward(request, response);    }

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

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        User user = (User) session.getAttribute("user");
        int userId = user.getId();

        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        int schoolId = Integer.parseInt(request.getParameter("school"));
        String certi = request.getParameter("certi");
        String description = request.getParameter("description");

        // Lấy danh sách classIds và subjectIds từ form (checkbox/multiselect)
        String[] classIdsStr = request.getParameterValues("classIds");
        String[] subjectIdsStr = request.getParameterValues("subjectIds");
        List<Integer> classIds = new ArrayList<>();
        List<Integer> subjectIds = new ArrayList<>();
        if (classIdsStr != null) {
            for (String c : classIdsStr) {
                classIds.add(Integer.parseInt(c));
            }
        }
        if (subjectIdsStr != null) {
            for (String s : subjectIdsStr) {
                subjectIds.add(Integer.parseInt(s));
            }
        }
         UserDAO userDao = new UserDAO();

        // Xử lý upload ảnh đại diện
        Part part = request.getPart("avatarFile");

        // Thư mục lưu ảnh ngoài project
        String uploadDirPath = "D:/MyUploads/Images";
        File uploadDir = new File(uploadDirPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String avatarPath = null;
        String randomFileName = null;

        if (part != null && part.getSize() > 0) {
            // Xóa ảnh cũ nếu có
            String oldAvatarPath = userDao.getUserByID(userId).getAvatar(); 
            if (oldAvatarPath != null && oldAvatarPath.contains("/image-loader/")) {
                String oldFileName = oldAvatarPath.substring((request.getContextPath() + "/image-loader/").length());
                File oldFile = new File(uploadDir, oldFileName);
                if (oldFile.exists()) {
                    oldFile.delete();
                }
            }

            // Tạo tên ngẫu nhiên cho file
            String fileExtension = part.getSubmittedFileName().substring(part.getSubmittedFileName().lastIndexOf("."));
            randomFileName = java.util.UUID.randomUUID().toString() + fileExtension;

            // Ghi file
            File newFile = new File(uploadDir, randomFileName);
            part.write(newFile.getAbsolutePath());

            // Gán đường dẫn lưu DB
            avatarPath = request.getContextPath() + "/image-loader/" + randomFileName;
        } else {
            // Không upload ảnh mới → giữ nguyên ảnh cũ
            avatarPath = userDao.getUserByID(userId).getAvatar();
        }

        boolean ok1 = userDao.updateUser(userId, email, phone, avatarPath, certi, description, schoolId);

        // Cập nhật bảng trung gian
        TeacherClassDAO teacherClassDao = new TeacherClassDAO();
        SubjectDAO subjectDao = new SubjectDAO();
        boolean ok2 = teacherClassDao.updateSchoolClassDAO(userId, classIds);
        boolean ok3 = subjectDao.updateSubjectOfTeacherDAO(userId, subjectIds);

        if (ok1 && ok2 && ok3) {
            User updatedUser = userDao.getUserByID(userId);
           
            request.getSession().setAttribute("user", updatedUser);
            session.setAttribute("SuccessMessage", "Đã lưu thay đổi thành công.");
        } else {
            session.setAttribute("FailMessage", "Thay đổi chưa được lưu. Đã xảy ra lỗi.");
        }



        response.sendRedirect("profileservlet");
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
