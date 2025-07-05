/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controll_teacher;

import dal.DocumentDAO;
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
import java.sql.SQLException;

/**
 *
 * @author NGOC ANH
 */
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 1,     
    maxFileSize = 1024 * 1024 * 10,         
    maxRequestSize = 1024 * 1024 * 15       
)
public class UploadServlet extends HttpServlet {


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
            out.println("<title>Servlet UploadServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UploadServlet at " + request.getContextPath() + "</h1>");
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
        Integer uploadedBy = (Integer) session.getAttribute("userId");

        if (uploadedBy == null) {
            response.sendRedirect("login_register.jsp");
            return;
        }

        String title = request.getParameter("title");
        String descrip = request.getParameter("descrip");
        String subjectIdStr = request.getParameter("subjectId");
        String gradeIdStr = request.getParameter("gradeId");
        String classifyStr = request.getParameter("classifyId");
        Part filePart = null;

        try {
            filePart = request.getPart("pdf");
        } catch (IllegalStateException ex) {
            response.sendRedirect("teacherdashboard.jsp?uploadError=size");
            return;

        }

        // Validate input
        if (title == null || title.isEmpty()
                || subjectIdStr == null || subjectIdStr.isEmpty()
                || gradeIdStr == null || gradeIdStr.isEmpty()
                || filePart == null || filePart.getSize() == 0
                || !filePart.getContentType().equals("application/pdf")) {
            response.sendRedirect("teacherdashboard.jsp?uploadError=missing");
            return;

        }

        int subjectId = Integer.parseInt(subjectIdStr);
        int gradeId = Integer.parseInt(gradeIdStr);
        int classifyId = Integer.parseInt(classifyStr);
        String uploadDirPath = "D:/MyUploads/Files";
        File uploadDir = new File(uploadDirPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Tạo tên file an toàn và duy nhất
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        fileName = fileName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
        String uniqueFileName = System.currentTimeMillis() + "_" + fileName;

        // Ghi file ra ổ đĩa
        File fileToSave = new File(uploadDir, uniqueFileName);
        filePart.write(fileToSave.getAbsolutePath());

        // Ghi đường dẫn ảo để truy cập lại 
        String dbFilePath = request.getContextPath() + "/file-loader/" + uniqueFileName;

        DocumentDAO documentDao = new DocumentDAO();
        // Lưu vào DB
        try {
            documentDao.saveDocument(title, descrip, uploadedBy, subjectId, gradeId, dbFilePath,classifyId);
            // Chuyển hướng 
            response.sendRedirect("teacherdashboard.jsp?uploadSuccess=true");
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi lưu vào database: " + e.getMessage());
        }

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
