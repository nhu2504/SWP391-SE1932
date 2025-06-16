import dal.DBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//Văn Thị NHư - HE181329

@WebServlet("/LogoServlet")
public class LogoServlet extends HttpServlet {

    private static final String DEFAULT_LOGO = "logo.jpg"; // Logo mặc định

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String type = request.getParameter("type");
        if (type == null || type.trim().isEmpty()) {
            type = "logo";
        }

        try (
            Connection conn = new DBContext().connection;
            PreparedStatement ps = createPreparedStatement(conn, type, request);
            ResultSet rs = (ps != null) ? ps.executeQuery() : null
        ) {
            if (rs != null && rs.next()) {
                String imageFileName = rs.getString(1); // ví dụ: logo_12345.jpg

                if (imageFileName != null && !imageFileName.trim().isEmpty()) {
                    File imageFile = new File(getServletContext().getRealPath("/Uploads"), imageFileName);

                    // Nếu không có trong uploads, thử tìm trong images
                    if (!imageFile.exists()) {
                        imageFile = new File(getServletContext().getRealPath("/images"), imageFileName);
                    }

                    System.out.println("📂 Đang kiểm tra ảnh tại: " + imageFile.getAbsolutePath());

                    if (imageFile.exists()) {
                        String mime = getServletContext().getMimeType(imageFile.getName());
                        if (mime == null) {
                            mime = "image/jpeg"; // fallback MIME type
                        }

                        response.setContentType(mime);
                        response.setContentLengthLong(imageFile.length());

                        Files.copy(imageFile.toPath(), response.getOutputStream());
                        response.getOutputStream().flush();
                        return;
                    } else {
                        System.out.println("❌ Không tìm thấy file: " + imageFile.getAbsolutePath());
                        // Nếu type là logo, cập nhật DB với logo mặc định
                        if ("logo".equalsIgnoreCase(type)) {
                            updateDefaultLogo(conn);
                            // Gửi logo mặc định từ /images/logo.jpg
                            sendDefaultLogoImage(response);
                            return;
                        }
                    }
                } else {
                    System.out.println("❌ Tên file rỗng hoặc null từ DB với type = " + type);
                    // Nếu type là logo, cập nhật DB với logo mặc định
                    if ("logo".equalsIgnoreCase(type)) {
                        updateDefaultLogo(conn);
                        // Gửi logo mặc định từ /images/logo.jpg
                        sendDefaultLogoImage(response);
                        return;
                    }
                }
            } else {
                System.out.println("❌ Không có kết quả từ DB với type = " + type);
                // Nếu type là logo, cập nhật DB với logo mặc định
                if ("logo".equalsIgnoreCase(type)) {
                    updateDefaultLogo(conn);
                    // Gửi logo mặc định từ /images/logo.jpg
                    sendDefaultLogoImage(response);
                    return;
                }
            }

            sendFallbackImage(response);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("❌ Lỗi SQL: " + e.getMessage());
            sendFallbackImage(response);
        }
    }

    private void updateDefaultLogo(Connection conn) throws SQLException {
        String updateSql = "UPDATE CenterInfo SET Logo = ? WHERE CenterID = 1";
        try (PreparedStatement updatePs = conn.prepareStatement(updateSql)) {
            updatePs.setString(1, DEFAULT_LOGO);
            int rowsAffected = updatePs.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("🟢 Đã cập nhật logo mặc định: " + DEFAULT_LOGO);
            } else {
                System.out.println("⚠️ Không có bản ghi nào được cập nhật cho logo mặc định");
            }
        }
    }

    private void sendDefaultLogoImage(HttpServletResponse response) throws IOException {
        File defaultLogoFile = new File(getServletContext().getRealPath("/images"), DEFAULT_LOGO);

        if (defaultLogoFile.exists()) {
            response.setContentType("image/jpeg");
            response.setContentLengthLong(defaultLogoFile.length());
            Files.copy(defaultLogoFile.toPath(), response.getOutputStream());
            response.getOutputStream().flush();
            System.out.println("🟢 Đã gửi logo mặc định: " + defaultLogoFile.getAbsolutePath());
        } else {
            System.out.println("❌ logo.jpg không tồn tại tại: " + defaultLogoFile.getAbsolutePath());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void sendFallbackImage(HttpServletResponse response) throws IOException {
        File fallbackFile = new File(getServletContext().getRealPath("/images/fallback.png"));

        if (fallbackFile.exists()) {
            response.setContentType("image/png");
            response.setContentLengthLong(fallbackFile.length());
            Files.copy(fallbackFile.toPath(), response.getOutputStream());
            response.getOutputStream().flush();
            System.out.println("🟢 Đã gửi fallback image: " + fallbackFile.getAbsolutePath());
        } else {
            System.out.println("⚠️ fallback.png không tồn tại");
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private PreparedStatement createPreparedStatement(Connection conn, String type, HttpServletRequest request) throws SQLException {
        String sql = null;
        PreparedStatement ps = null;

        switch (type.toLowerCase()) {
            case "logo":
                sql = "SELECT Logo FROM CenterInfo WHERE CenterID = 1";
                ps = conn.prepareStatement(sql);
                break;

            case "banner":
                String bannerId = request.getParameter("bannerID");
                if (bannerId != null && !bannerId.trim().isEmpty()) {
                    sql = "SELECT bannerImg FROM Banner WHERE bannerID = ?";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, bannerId);
                    System.out.println("🟢 Truy vấn banner có ID = " + bannerId);
                } else {
                    System.out.println("❌ Thiếu bannerID");
                }
                break;

            case "bannerteacher":
                sql = "SELECT bannerImg FROM Banner WHERE bannerID = 1";
                ps = conn.prepareStatement(sql);
                break;

            case "imagecenter":
                sql = "SELECT imageCenter FROM CenterInfo WHERE CenterID = 1";
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
            case "student": // dùng chung logic
                String userId = request.getParameter("userId");
                if (userId != null && !userId.trim().isEmpty()) {
                    sql = "SELECT avatar FROM [User] WHERE UserID = ?";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, userId);
                    System.out.println("🔍 Truy vấn avatar cho UserID: " + userId);
                } else {
                    System.out.println("❌ userId không hợp lệ");
                }
                break;

            default:
                System.out.println("⚠️ Type không hợp lệ: " + type);
                break;
        }

        return ps;
    }
}