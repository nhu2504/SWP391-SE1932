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
        System.out.println("LogoServlet được gọi tại: " + request.getRequestURI());
        DBContext db = new DBContext();
        Connection conn = db.connection;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT Logo FROM CenterInfo WHERE CenterID = 1";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                byte[] imgData = rs.getBytes("Logo");
                if (imgData != null) {
                    // Đặt loại nội dung là image/png (hoặc image/jpeg tùy loại hình)
                    response.setContentType("image/png");
                    // Ghi dữ liệu hình ảnh vào output stream
                    response.getOutputStream().write(imgData);
                    System.out.println("Hình ảnh logo được phục vụ thành công");
                } else {
                    System.out.println("Logo là null trong cơ sở dữ liệu");
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Logo không tìm thấy");
                }
            } else {
                System.out.println("Không tìm thấy bản ghi với CenterID = 1");
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy trung tâm");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Lỗi SQL trong LogoServlet: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi cơ sở dữ liệu");
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Lỗi đóng kết nối: " + e.getMessage());
            }
        }
    }
}