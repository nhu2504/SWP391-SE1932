import dal.CenterInfoDAO;
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

/*
 * Tác giả: Van Nhu
 * Ngày update: 30/06/2025
 * Mô tả: Servlet xử lý cập nhật thông tin trung tâm dạy thêm:
 *  - Cập nhật văn bản (tên, địa chỉ, email, mô tả, ...)
 *  - Upload ảnh logo và ảnh chính trung tâm
 *  - Cho phép xóa nội dung về trạng thái null
 */

@WebServlet("/UpdateCenterInfoServlet")
@MultipartConfig(
    maxFileSize = 5 * 1024 * 1024,        // File tối đa: 5MB
    maxRequestSize = 10 * 1024 * 1024     // Tổng request tối đa: 10MB
)
public class UpdateCenterInfoServlet extends HttpServlet {

    private final CenterInfoDAO centerInfoDAO = new CenterInfoDAO();
    private static final String EXTERNAL_IMG_DIR = "D:/data/images"; // Tùy bạn

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        // Kiểm tra quyền admin
        HttpSession session = req.getSession(false);
        if (session == null || !"1".equals(String.valueOf(session.getAttribute("userRoleID")))) {
            res.setStatus(401);
            res.getWriter().write("LOGIN_REQUIRED");
            return;
        }

        try {
            // Lấy các trường từ form
            String centerName = req.getParameter("centerName");
            String descripCenter = req.getParameter("descripCenter");
            String address = req.getParameter("address");
            String phone = req.getParameter("phone");
            String email = req.getParameter("email");
            String website = req.getParameter("website");

            // Xử lý upload logo (nếu có)
            Part logoPart = req.getPart("logoFile");
            String logoFileName = null;
            if (logoPart != null && logoPart.getSize() > 0 && logoPart.getSubmittedFileName() != null && !logoPart.getSubmittedFileName().isEmpty()) {
                String originalName = logoPart.getSubmittedFileName();
                String extension = originalName.substring(originalName.lastIndexOf("."));
                logoFileName = "logo_" + System.nanoTime() + extension;

                File dir = new File(EXTERNAL_IMG_DIR);
                if (!dir.exists()) dir.mkdirs();

                File savedFile = new File(dir, logoFileName);
                try {
                    logoPart.write(savedFile.getAbsolutePath());
                } catch (Exception e) {
                    Files.copy(logoPart.getInputStream(), savedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
            }

            // Gọi DAO cập nhật thông tin trung tâm
            boolean ok = centerInfoDAO.updateAllFields(1, centerName, descripCenter, address, phone, email, website, logoFileName);

            if (ok) {
                res.setStatus(200);
            } else {
                res.setStatus(500);
                res.getWriter().write("UPDATE_FAILED");
            }
        } catch (Exception e) {
            res.setStatus(500);
            res.getWriter().write("ERROR");
        }
    }
}