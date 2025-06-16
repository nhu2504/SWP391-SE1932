import dal.DBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

//Văn Thị NHư - HE181329

@WebServlet("/UpdateCenterInfoServlet")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,  // 1MB
        maxFileSize = 5 * 1024 * 1024,    // 5MB
        maxRequestSize = 10 * 1024 * 1024 // 10MB
)
public class UpdateCenterInfoServlet extends HttpServlet {

    private static final String[] VALID_FIELDS = {"centerName", "address", "email", "phone"};
    private static final Logger LOGGER = Logger.getLogger(UpdateCenterInfoServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOGGER.info("🔄 Bắt đầu xử lý POST request...");

        // Kiểm tra phân quyền
        HttpSession session = request.getSession(false);
        if (session == null || !"admin".equals(session.getAttribute("userRole"))) {
            LOGGER.warning("⚠️ Không có quyền admin.");
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Chỉ admin có thể chỉnh sửa thông tin.");
            return;
        }

        // Kiểm tra content type
        String contentType = request.getContentType();
        boolean isMultipart = contentType != null && contentType.toLowerCase().contains("multipart/form-data");
        String fieldName = request.getParameter("fieldName");

        try (Connection conn = new DBContext().connection) { // Giả sử DBContext có getConnection()
            if (conn == null) {
                LOGGER.severe("❌ Không thể kết nối đến cơ sở dữ liệu.");
                throw new SQLException("Không thể kết nối đến cơ sở dữ liệu.");
            }

            if (isMultipart) {
                // Xử lý upload file logo
                try {
                    Part filePart = request.getPart("logoFile");
                    if (filePart == null) {
                        LOGGER.warning("⚠️ Không tìm thấy field 'logoFile' trong request.");
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Không tìm thấy file logo.");
                        return;
                    }
                    if (filePart.getSize() == 0) {
                        LOGGER.warning("⚠️ File logo rỗng.");
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "File logo không hợp lệ.");
                        return;
                    }

                    String originalFileName = filePart.getSubmittedFileName();
                    if (originalFileName == null || originalFileName.isEmpty()) {
                        LOGGER.warning("⚠️ File name không hợp lệ.");
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tên file không hợp lệ.");
                        return;
                    }

                    String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
                    String newFileName = "logo_" + System.nanoTime() + extension; // Dùng nanoTime để tránh trùng

                    // Lưu file vào thư mục Uploads
                    String uploadsPath = getServletContext().getRealPath("/uploads");
                    File uploadsDir = new File(uploadsPath);
                    if (!uploadsDir.exists() && !uploadsDir.mkdirs()) {
                        LOGGER.severe("❌ Không thể tạo thư mục Uploads: " + uploadsPath);
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Không thể tạo thư mục lưu file.");
                        return;
                    }

                    File savedFile = new File(uploadsDir, newFileName);
                    filePart.write(savedFile.getAbsolutePath());
                    LOGGER.info("🖼️ File đã lưu tại: " + savedFile.getAbsolutePath());

                    // Sao lưu vào images
                    String imagesPath = getServletContext().getRealPath("/images");
                    File imagesDir = new File(imagesPath);
                    if (!imagesDir.exists() && !imagesDir.mkdirs()) {
                        LOGGER.severe("❌ Không thể tạo thư mục images: " + imagesPath);
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Không thể tạo thư mục sao lưu.");
                        return;
                    }

                    File backupFile = new File(imagesDir, newFileName);
                    Files.copy(savedFile.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    LOGGER.info("🗂️ Sao lưu logo vào /images thành công.");

                    // Cập nhật database
                    String sql = "UPDATE CenterInfo SET Logo = ? WHERE CenterID = 1";
                    try (PreparedStatement ps = conn.prepareStatement(sql)) {
                        ps.setString(1, newFileName);
                        int rows = ps.executeUpdate();
                        if (rows == 0) {
                            LOGGER.warning("⚠️ Không cập nhật được logo trong DB.");
                            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Không cập nhật được logo.");
                            return;
                        }
                        LOGGER.info("✅ Đã cập nhật logo mới vào DB: " + newFileName);
                    }
                } catch (IllegalStateException e) {
                    LOGGER.log(Level.SEVERE, "❌ Lỗi kích thước file: ", e);
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "File quá lớn hoặc không hợp lệ.");
                    return;
                } catch (ServletException | IOException e) {
                    LOGGER.log(Level.SEVERE, "❌ Lỗi khi xử lý file upload: ", e);
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi xử lý file: " + (e.getMessage() != null ? e.getMessage() : "Không xác định"));
                    return;
                }

            } else if (fieldName != null && !fieldName.trim().isEmpty()) {
                // Xử lý cập nhật thông tin văn bản
                String fieldValue = request.getParameter("fieldValue");
                if (fieldValue == null || fieldValue.trim().isEmpty()) {
                    LOGGER.warning("⚠️ Giá trị fieldValue không hợp lệ.");
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Giá trị không hợp lệ.");
                    return;
                }

                // Kiểm tra field hợp lệ
                boolean isValidField = false;
                for (String valid : VALID_FIELDS) {
                    if (valid.equals(fieldName)) {
                        isValidField = true;
                        break;
                    }
                }

                if (!isValidField) {
                    LOGGER.warning("⚠️ Tên trường không hợp lệ: " + fieldName);
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tên trường không hợp lệ.");
                    return;
                }

                String dbColumn = switch (fieldName) {
                    case "centerName" -> "NameCenter";
                    case "address" -> "AddressCenter";
                    case "email" -> "Email";
                    case "phone" -> "Phone";
                    default -> null;
                };

                if (dbColumn == null) {
                    LOGGER.warning("⚠️ Trường không được hỗ trợ: " + fieldName);
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Trường không được hỗ trợ.");
                    return;
                }

                String sql = "UPDATE CenterInfo SET " + dbColumn + " = ? WHERE CenterID = 1";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, fieldValue);
                    int rows = ps.executeUpdate();
                    if (rows == 0) {
                        LOGGER.warning("⚠️ Không cập nhật được " + dbColumn + ": " + fieldValue);
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Không cập nhật được thông tin.");
                        return;
                    }
                    LOGGER.info("✅ Đã cập nhật " + dbColumn + ": " + fieldValue);
                }

            } else {
                LOGGER.warning("⚠️ Yêu cầu không hợp lệ: Thiếu fieldName hoặc không phải multipart.");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Yêu cầu không hợp lệ.");
                return;
            }

            // Chuyển hướng về trang chủ
            response.sendRedirect(request.getContextPath() + "/home");

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "❌ Lỗi khi cập nhật cơ sở dữ liệu: ", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi cơ sở dữ liệu: " + (e.getMessage() != null ? e.getMessage() : "Không xác định"));
        }
    }
}