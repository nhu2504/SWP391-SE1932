
import dal.DBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.*;

/**
 *
 *
 * Ngày tạo: 23/06/2025 Người viết: Van Nhu
 */
@WebServlet("/LogoServlet")
public class LogoServlet extends HttpServlet {

    // Đường dẫn thư mục ảnh ngoài project (thay đổi nếu deploy nơi khác)
    private static final String EXTERNAL_IMAGE_DIR = "D:/data/images";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String type = request.getParameter("type");
        if (type == null || type.trim().isEmpty()) {
            type = "logo"; // Mặc định là logo
        }
        if ("manual".equalsIgnoreCase(type)) {
            String filename = request.getParameter("filename");
            if (filename != null && !filename.trim().isEmpty()) {
                File imageFile = resolveImageFile(filename, request);
                if (imageFile != null && imageFile.exists()) {
                    String mime = getServletContext().getMimeType(imageFile.getName());
                    if (mime == null) {
                        mime = "image/jpeg";
                    }
                    response.setContentType(mime);
                    response.setContentLengthLong(imageFile.length());
                    Files.copy(imageFile.toPath(), response.getOutputStream());
                    response.getOutputStream().flush();
                    return;
                }
            }
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return;
        }
        // Đảm bảo thư mục chứa ảnh ngoài project tồn tại
        File externalDir = new File(EXTERNAL_IMAGE_DIR);
        if (!externalDir.exists() && !externalDir.mkdirs()) {
            System.err.println("Không thể tạo thư mục ảnh ngoài project: " + EXTERNAL_IMAGE_DIR);
        }

        try (
                Connection conn = new DBContext().connection; PreparedStatement ps = createPreparedStatement(conn, type, request); ResultSet rs = (ps != null) ? ps.executeQuery() : null) {
            if (rs != null && rs.next()) {
                String imageFileName = rs.getString(1);

                if (imageFileName != null && !imageFileName.trim().isEmpty()) {
                    File imageFile = resolveImageFile(imageFileName, request);

                    if (imageFile != null && imageFile.exists()) {
                        // Thiết lập MIME type và gửi ảnh
                        String mime = getServletContext().getMimeType(imageFile.getName());
                        if (mime == null) {
                            mime = "image/jpeg";
                        }
                        response.setContentType(mime);
                        response.setContentLengthLong(imageFile.length());
                        Files.copy(imageFile.toPath(), response.getOutputStream());
                        response.getOutputStream().flush();
                        return;
                    }
                }
            }

            // Không có ảnh hoặc ảnh đã bị xóa: trả về mã 204 No Content
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi cơ sở dữ liệu");
        }
    }

    /**
     * Tạo câu truy vấn phù hợp để lấy tên file ảnh từ DB
     * phía client gọi type -> servlet lấy type từ request, dựa vào type quyết định chạy case 
     */
    private PreparedStatement createPreparedStatement(Connection conn, String type, HttpServletRequest request) throws SQLException {
        String sql = null;
        PreparedStatement ps = null;

        switch (type.toLowerCase()) {
            case "logo":
                sql = "SELECT Logo FROM CenterInfo WHERE CenterID = 1";
                ps = conn.prepareStatement(sql);
                break;
            case "imagecenter":
                sql = "SELECT imageCenter FROM CenterInfo WHERE CenterID = 1";
                ps = conn.prepareStatement(sql);
                break;
            case "banner":
                String bannerId = request.getParameter("bannerID");
                if (bannerId != null && !bannerId.trim().isEmpty()) {
                    sql = "SELECT bannerImg FROM Banner WHERE bannerID = ?";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, bannerId);
                }
                break;
            case "bannerteacher":
                sql = "SELECT bannerImg FROM Banner WHERE bannerID = 1";
                ps = conn.prepareStatement(sql);
                break;
            case "subject":
                String subjectId = request.getParameter("subjectId");
                if (subjectId != null && !subjectId.trim().isEmpty()) {
                    sql = "SELECT ImageSubject FROM Subjects WHERE SubjectId = ?";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, subjectId);
                }
                break;
            case "tutoring":
                String tutoringClassId = request.getParameter("tutoringClassId");
                if (tutoringClassId != null && !tutoringClassId.trim().isEmpty()) {
                    sql = "SELECT ImageTutoring FROM TutoringClass WHERE TutoringClassID = ?";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, tutoringClassId);
                }
                break;
            case "teacher":
                String userId = request.getParameter("userId");
                if (userId != null && !userId.trim().isEmpty()) {
                    sql = "SELECT avatar FROM [User] WHERE UserID = ? and roleId = 2";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, userId);
                }
                break;
            case "student":
                String userId1 = request.getParameter("userId");
                if (userId1 != null && !userId1.trim().isEmpty()) {
                    sql = "SELECT avatar FROM [User] WHERE UserID = ? and roleId = 3";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, userId1);
                }
                break;
            // Không cần truy vấn DB nếu là manual
            case "manual":
                return null;

            default:
                break;
        }

        return ps;
    }

    /**
     * Tìm file ảnh theo tên file, ưu tiên từ thư mục ngoài project, sau đó thử
     * trong /images nội bộ
     */
    private File resolveImageFile(String imageFileName, HttpServletRequest request) {
        File imageFile = new File(EXTERNAL_IMAGE_DIR, imageFileName);
        if (!imageFile.exists()) {
            imageFile = new File(getServletContext().getRealPath("/images"), imageFileName);
        }

        return imageFile.exists() ? imageFile : null;
    }
}
