/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controll;

import dal.DocumentDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
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
public class UploadServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "uploads";

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
        Part filePart = null;
        try {
            filePart = request.getPart("pdf");
        } catch (IllegalStateException ex) {
            response.getWriter().println("File upload vượt quá giới hạn kích thước!");
            return;
        }

        // Validate
        if (title == null || title.isEmpty()
                || subjectIdStr == null || subjectIdStr.isEmpty()
                || gradeIdStr == null || gradeIdStr.isEmpty()
                || filePart == null || filePart.getSize() == 0 || !filePart.getContentType().equals("application/pdf")) {
            response.getWriter().println("Vui lòng nhập đầy đủ thông tin và chọn file PDF hợp lệ!");
            return;
        }

        int subjectId = Integer.parseInt(subjectIdStr);
        int gradeId = Integer.parseInt(gradeIdStr);

        // Xử lý lưu file
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        fileName = fileName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_"); // Loại bỏ ký tự đặc biệt
        String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
        String applicationPath = request.getServletContext().getRealPath("");
        String uploadPath = applicationPath + File.separator + UPLOAD_DIR;

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String filePath = uploadPath + File.separator + uniqueFileName;
        filePart.write(filePath);

        String dbFilePath = UPLOAD_DIR + "/" + uniqueFileName;
        DocumentDAO documentDao = new DocumentDAO();
        // Lưu vào DB
        try {
            documentDao.saveDocument(title, descrip, uploadedBy, subjectId, gradeId, dbFilePath);
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
