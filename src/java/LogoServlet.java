
import dal.DBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Văn Thị Như - HE181329
@WebServlet("/LogoServlet")
public class LogoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("LogoServlet được gọi tại: " + request.getRequestURI() + "?" + request.getQueryString());
        String type = request.getParameter("type");
        if (type == null || type.trim().isEmpty()) {
            type = "logo";
        }

        try (Connection conn = new DBContext().connection) {
            if (conn == null) {
                System.out.println("Lỗi: Kết nối cơ sở dữ liệu là null");
                response.sendRedirect(request.getContextPath() + "/images/fallback.png");
                return;
            }

            String imageUrl = null;
            try (PreparedStatement ps = createPreparedStatement(conn, type, request); ResultSet rs = ps != null ? ps.executeQuery() : null) {
                if (rs != null && rs.next()) {
                    imageUrl = rs.getString(1);
                    if (imageUrl == null || imageUrl.trim().isEmpty()) {
                        System.out.println("ImageTutoring null hoặc rỗng cho type=" + type);
                    }
                } else {
                    System.out.println("Không tìm thấy hình ảnh cho type=" + type);
                }
            }

            if (imageUrl != null && !imageUrl.trim().isEmpty()) {
                if (!imageUrl.startsWith("http://") && !imageUrl.startsWith("https://")) {
                    if (!imageUrl.startsWith("/images/")) {
                        imageUrl = "/images/" + imageUrl.replaceFirst("^/+", "");
                    }
                    imageUrl = request.getContextPath() + imageUrl;
                }
                System.out.println("Chuyển hướng đến hình ảnh URL (" + type + "): " + imageUrl);
                response.sendRedirect(imageUrl);
            } else {
                System.out.println("URL hình ảnh (" + type + ") là null hoặc rỗng");
                response.sendRedirect(request.getContextPath() + "/images/fallback.png");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Lỗi SQL trong LogoServlet: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/images/fallback.png");
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
                sql = "SELECT Banner FROM CenterInfo WHERE CenterID = 1";
                ps = conn.prepareStatement(sql);
                break;
            case "bannerteacher":
                sql = "SELECT BannerTeacher FROM CenterInfo WHERE CenterID = 1";
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
                } else {
                    System.out.println("subjectId không hợp lệ: " + subjectId);
                }
                break;
            case "tutoring":
                String tutoringClassId = request.getParameter("tutoringClassId");
                if (tutoringClassId != null && !tutoringClassId.trim().isEmpty()) {
                    sql = "SELECT ImageTutoring FROM TutoringClass WHERE TutoringClassID = ?";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, tutoringClassId);
                    System.out.println("Truy vấn ImageTutoring cho TutoringClassID: " + tutoringClassId);
                } else {
                    System.out.println("tutoringClassId không hợp lệ: " + tutoringClassId);
                }
                break;
            case "teacher":
                String userId = request.getParameter("userId");
                if (userId != null && !userId.trim().isEmpty()) {
                    sql = "SELECT avatar FROM [User] WHERE UserID = ?";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, userId);
                    System.out.println("Truy vấn avatar cho UserID: " + userId);
                } else {
                    System.out.println("userId không hợp lệ: " + userId);
                }
                break;
            case "student":
                String studentId = request.getParameter("userId");
                if (studentId != null && !studentId.trim().isEmpty()) {
                    sql = "SELECT avatar FROM [User] WHERE UserID = ?";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, studentId);
                    System.out.println("Truy vấn avatar cho Student UserID: " + studentId);
                } else {
                    System.out.println("studentId không hợp lệ: " + studentId);
                }
                break;
            default:
                System.out.println("Loại hình ảnh không hợp lệ: " + type);
                break;
        }
        return ps;
    }
}
