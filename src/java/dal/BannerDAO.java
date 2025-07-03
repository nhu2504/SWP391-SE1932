package dal;

import entity.Banner;
import java.util.ArrayList;
import java.util.List;
import dal.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Ngày update: 30/06/2025  
 * Người viết: Văn Thị Như
 * Mô tả: BannerDAO là lớp thực hiện các thao tác với bảng `banner` trong cơ sở dữ liệu:
 *       - Lấy danh sách banner (trừ banner có ID = 1)
 *       - Thêm banner mới
 *       - Xóa banner theo ID
 */
public class BannerDAO {

    /**
     *  Lấy danh sách tất cả banner từ bảng `banner`, trừ banner có ID = 1 (banner teacher).
     * 
     * @return Danh sách banner (List<Banner>)
     */
    public List<Banner> getAllBanners() {
        List<Banner> banners = new ArrayList<>();

        try (Connection conn = new DBContext().connection; // Kết nối CSDL
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT bannerID, bannerImg FROM banner WHERE bannerID <> 1 ORDER BY bannerID ASC");
             ResultSet rs = ps.executeQuery()) {

            // Duyệt kết quả và đưa vào danh sách banner
            while (rs.next()) {
                Banner banner = new Banner();
                banner.setBannerID(rs.getInt("bannerID"));           // Gán ID banner
                banner.setBannerImg(rs.getString("bannerImg"));      // Gán đường dẫn hình ảnh
                banners.add(banner);                                 // Thêm vào danh sách
            }

        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi nếu có
        }

        return banners; // Trả về danh sách banner
    }

    /**
     *  Thêm một banner mới vào bảng `banner`
     * 
     * @param fileName Tên file hình ảnh banner
     */
    public void addBanner(String fileName) {
        try (Connection conn = new DBContext().connection; // Kết nối CSDL
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO banner (bannerImg) VALUES (?)")) {

            ps.setString(1, fileName); // Gán tên ảnh vào truy vấn
            ps.executeUpdate();        // Thực thi truy vấn thêm mới

        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi nếu có
        }
    }

    /**
     *  Xóa banner dựa trên bannerID
     * 
     * @param bannerID ID của banner cần xóa
     */
    public void deleteBanner(int bannerID) {
        try (Connection conn = new DBContext().connection; // Kết nối CSDL
             PreparedStatement ps = conn.prepareStatement(
                     "DELETE FROM banner WHERE bannerID = ?")) {

            ps.setInt(1, bannerID); // Gán giá trị ID cần xóa
            ps.executeUpdate();     // Thực thi lệnh xóa

        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi nếu có
        }
    }
}
