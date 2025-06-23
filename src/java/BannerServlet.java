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
    maxFileSize = 5 * 1024 * 1024,         // Giới hạn kích thước file tải lên: 5MB
    maxRequestSize = 10 * 1024 * 1024      // Giới hạn tổng dung lượng toàn bộ request: 10MB
)
public class BannerServlet extends HttpServlet {

    // Đường dẫn lưu ảnh banner ngoài thư mục của project
    private static final String EXTERNAL_IMAGE_DIR = "D:/data/images";

    // DAO xử lý thao tác với cơ sở dữ liệu banner
    private final BannerDAO bannerDAO = new BannerDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        // Lấy session hiện tại (nếu có)
        HttpSession session = req.getSession(false);

        // Kiểm tra quyền truy cập: chỉ Admin (userRoleID = 1) mới được phép
        if (session == null || !"1".equals(String.valueOf(session.getAttribute("userRoleID")))) {
            res.sendError(HttpServletResponse.SC_FORBIDDEN, "Chỉ admin mới có quyền.");
            return;
        }

        // Lấy hành động từ form gửi lên: "add" để thêm ảnh, "delete" để xóa ảnh
        String action = req.getParameter("action");

        try {
            if ("add".equals(action)) {
                // -------- THÊM ẢNH BANNER --------

                // Lấy ảnh từ form
                Part filePart = req.getPart("bannerImage");

                // Kiểm tra file có tồn tại không
                if (filePart == null || filePart.getSize() == 0) {
                    res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ảnh không hợp lệ.");
                    return;
                }

                // Lấy tên file gốc
                String originalName = filePart.getSubmittedFileName();

                // Lấy phần đuôi mở rộng (ví dụ: .jpg, .png)
                String extension = originalName.substring(originalName.lastIndexOf('.'));

                // Tạo tên file mới tránh trùng lặp
                String fileName = "banner_" + System.nanoTime() + extension;

                // Tạo thư mục nếu chưa tồn tại
                File saveDir = new File(EXTERNAL_IMAGE_DIR);
                if (!saveDir.exists()) saveDir.mkdirs();

                // Tạo đường dẫn lưu ảnh mới
                File savedFile = new File(saveDir, fileName);

                // Ghi file lên ổ đĩa
                try {
                    filePart.write(savedFile.getAbsolutePath()); // Ghi trực tiếp
                } catch (Exception e) {
                    // Ghi bằng stream nếu cách 1 không thành công
                    Files.copy(filePart.getInputStream(), savedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }

                // Ghi tên ảnh vào cơ sở dữ liệu
                bannerDAO.addBanner(fileName);

            } else if ("delete".equals(action)) {
                // -------- XÓA ẢNH BANNER --------

                // Lấy ID của banner cần xóa
                int bannerID = Integer.parseInt(req.getParameter("bannerID"));

                // Xóa banner trong CSDL
                bannerDAO.deleteBanner(bannerID);

                // (Nếu muốn xóa file thật thì cần query tên file từ DB trước khi xóa, ví dụ:)
                // String fileName = bannerDAO.getFileNameById(bannerID);
                // new File(EXTERNAL_IMAGE_DIR, fileName).delete();

            } else {
                // Hành động không hợp lệ
                res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action không hợp lệ.");
                return;
            }

            // Sau khi xử lý xong, quay lại trang chủ
            res.sendRedirect(req.getContextPath() + "/home");

        } catch (Exception e) {
            // Nếu có lỗi, trả về lỗi 500 và in ra thông báo lỗi
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi: " + e.getMessage());
        }
    }
}
