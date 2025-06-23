/*
 * Tác giả: Van Nhu
 * Ngày tạo: 23/06/2025
 * Mô tả: Servlet xử lý cập nhật thông tin trung tâm dạy thêm:
 *  - Cập nhật văn bản (tên, địa chỉ, email, mô tả, ...)
 *  - Upload ảnh logo và ảnh chính trung tâm
 *  - Cho phép xóa nội dung về trạng thái null
 */

import dal.CenterInfoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@WebServlet("/UpdateCenterInfoServlet")
@MultipartConfig(
    maxFileSize = 5 * 1024 * 1024,        // File tối đa: 5MB
    maxRequestSize = 10 * 1024 * 1024     // Tổng request tối đa: 10MB
)
public class UpdateCenterInfoServlet extends HttpServlet {

    // DAO để cập nhật dữ liệu bảng CenterInfo
    private final CenterInfoDAO centerInfoDAO = new CenterInfoDAO();

    // Đường dẫn thư mục chứa ảnh ngoài thư mục dự án
    private static final String EXTERNAL_IMG_DIR = "D:/data/images";

    // Danh sách các field được phép cập nhật trong bảng center_info
    private static final String[] VALID_FIELDS = {
        "centerName", "address", "email", "phone", "logo", "imageCenter", "descripCenter"
    };

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        // 1. Kiểm tra quyền Admin
        HttpSession session = req.getSession(false);
        if (session == null || !"1".equals(String.valueOf(session.getAttribute("userRoleID")))) {
            System.out.println("Không có quyền truy cập.");
            res.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        // ✅ 2. Đọc dữ liệu từ form gửi lên
        String contentType = req.getContentType();
        boolean isMultipart = contentType != null && contentType.toLowerCase().contains("multipart/form-data");

        String fieldName = req.getParameter("fieldName"); // tên field cần cập nhật
        String action = req.getParameter("action");       // "update" hoặc "delete"
        String fieldValue = req.getParameter("fieldValue");

        try {
            // 3. Trường hợp upload ảnh logo
            if (isMultipart && req.getPart("logoFile") != null && req.getPart("logoFile").getSize() > 0) {
                uploadAndSaveImage(req.getPart("logoFile"), "logo");

            // 4. Trường hợp upload ảnh imageCenter
            } else if (isMultipart && req.getPart("imageCenterFile") != null && req.getPart("imageCenterFile").getSize() > 0) {
                uploadAndSaveImage(req.getPart("imageCenterFile"), "imageCenter");

            // 5. Trường hợp cập nhật hoặc xóa trường văn bản (tên, địa chỉ,...)
            } else if (fieldName != null && action != null) {

                // Kiểm tra tên field có hợp lệ không
                boolean valid = false;
                for (String f : VALID_FIELDS) {
                    if (f.equals(fieldName)) {
                        valid = true;
                        break;
                    }
                }

                if (!valid) {
                    System.out.println("Trường không hợp lệ: " + fieldName);
                    res.sendRedirect(req.getContextPath() + "/home");
                    return;
                }

                // 5.1 Cập nhật văn bản
                if ("update".equals(action)) {
                    if (fieldValue == null || fieldValue.trim().isEmpty()) {
                        System.out.println("Giá trị cập nhật trống.");
                        res.sendRedirect(req.getContextPath() + "/home");
                        return;
                    }

                    boolean ok = centerInfoDAO.updateField(1, fieldName, fieldValue);
                    System.out.println(ok
                            ? "Cập nhật " + fieldName + " thành công: " + fieldValue
                            : "Cập nhật thất bại cho: " + fieldName);

                // 5.2 Xóa trường dữ liệu (set null)
                } else if ("delete".equals(action)) {
                    boolean ok = centerInfoDAO.deleteField(1, fieldName);
                    System.out.println(ok
                            ? "Xóa thành công (set null): " + fieldName
                            : "Xóa thất bại: " + fieldName);

                } else {
                    System.out.println("Action không hợp lệ: " + action);
                }

            } else {
                System.out.println("Dữ liệu form không hợp lệ.");
            }

            // 6. Sau khi xử lý xong, chuyển về trang chủ
            res.sendRedirect(req.getContextPath() + "/home");

        } catch (Exception e) {
            // 7. Bắt lỗi và chuyển hướng về home
            System.out.println("Lỗi xử lý: " + e.getMessage());
            res.sendRedirect(req.getContextPath() + "/home");
        }
    }

    // Hàm hỗ trợ upload ảnh và lưu tên file vào CSDL
    private void uploadAndSaveImage(Part filePart, String fieldName) throws IOException {
        // Lấy tên file và phần mở rộng
        String originalName = filePart.getSubmittedFileName();
        String extension = originalName.substring(originalName.lastIndexOf("."));
        String newFileName = fieldName + "_" + System.nanoTime() + extension;

        // Tạo thư mục chứa file nếu chưa có
        File dir = new File(EXTERNAL_IMG_DIR);
        if (!dir.exists()) dir.mkdirs();

        // Lưu ảnh lên ổ đĩa
        File savedFile = new File(dir, newFileName);
        try {
            filePart.write(savedFile.getAbsolutePath());
        } catch (Exception e) {
            Files.copy(filePart.getInputStream(), savedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        // Lưu tên file vào CSDL
        boolean ok = centerInfoDAO.updateField(1, fieldName, newFileName);
        System.out.println(ok
                ? "Cập nhật ảnh " + fieldName + " thành công: " + newFileName
                : "Lỗi khi cập nhật " + fieldName + " vào DB.");
    }
}
