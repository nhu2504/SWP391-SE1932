/*
 * Tác giả: Van Nhu
 * Ngày tạo: 23/06/2025
 * Mô tả: Servlet xử lý việc thêm và xóa ảnh banner cho trung tâm dạy thêm.
 *  - Cho phép Admin tải ảnh banner lên hệ thống và lưu vào ổ đĩa
 *  - Xóa banner khỏi cơ sở dữ liệu (có thể mở rộng để xóa cả ảnh thật)
 */

import dal.BannerDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@WebServlet("/BannerServlet")
@MultipartConfig(
        maxFileSize = 5 * 1024 * 1024, // Giới hạn kích thước file tải lên: 5MB
        maxRequestSize = 10 * 1024 * 1024 // Giới hạn tổng dung lượng toàn bộ request: 10MB
)
public class BannerServlet extends HttpServlet {

    // Đường dẫn lưu ảnh banner ngoài thư mục của project
    private static final String EXTERNAL_IMAGE_DIR = "D:/data/images";

    // DAO xử lý thao tác với cơ sở dữ liệu banner
    private final BannerDAO bannerDAO = new BannerDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        if (session == null || !"1".equals(String.valueOf(session.getAttribute("userRoleID")))) {
            res.sendError(HttpServletResponse.SC_FORBIDDEN, "Chỉ admin mới có quyền.");
            return;
        }

        String action = req.getParameter("action");

        try {
            if ("add".equals(action)) {
                Part filePart = req.getPart("bannerImage");
                if (filePart == null || filePart.getSize() == 0) {
                    res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ảnh không hợp lệ.");
                    return;
                }
                String originalName = filePart.getSubmittedFileName();
                String extension = originalName.substring(originalName.lastIndexOf('.'));
                String fileName = "banner_" + System.nanoTime() + extension;
                File saveDir = new File(EXTERNAL_IMAGE_DIR);
                if (!saveDir.exists()) {
                    saveDir.mkdirs();
                }
                File savedFile = new File(saveDir, fileName);
                try {
                    filePart.write(savedFile.getAbsolutePath());
                } catch (Exception e) {
                    Files.copy(filePart.getInputStream(), savedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
                bannerDAO.addBanner(fileName);

            } else if ("delete".equals(action)) {
                int bannerID = Integer.parseInt(req.getParameter("bannerID"));
                bannerDAO.deleteBanner(bannerID);

            } else {
                res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action không hợp lệ.");
                return;
            }

            // Xác định có phải AJAX không
            boolean isAjax = "XMLHttpRequest".equals(req.getHeader("X-Requested-With"));
            if (isAjax) {
                res.setStatus(HttpServletResponse.SC_OK);
                res.setContentType("text/plain; charset=UTF-8");
                res.getWriter().write("OK");
                res.getWriter().flush();
                return;
            } else {
                // Sau khi xử lý xong, redirect về trang setting (admin)
                res.sendRedirect(req.getContextPath() + "/admin?tab=setting");
                return;
            }

        } catch (Exception e) {
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi: " + e.getMessage());
        }
    }
}
