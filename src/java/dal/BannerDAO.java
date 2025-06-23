/*
 * Tác giả: Van Nhu
 * Ngày tạo: 23/06/2025
 * Mô tả: DAO truy xuất và thao tác với bảng Banner trong cơ sở dữ liệu
 *  - Lấy danh sách banner
 *  - Thêm banner mới (ảnh)
 *  - Xóa banner theo ID
 */

package dal;

import entity.Banner;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BannerDAO {

    // Lấy danh sách tất cả các banner (trừ bannerID = 1)
    public List<Banner> getAllBanners() {
        List<Banner> banners = new ArrayList<>();
        try (Connection conn = new DBContext().connection;
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT bannerID, bannerImg FROM banner WHERE bannerID <> 1 ORDER BY bannerID ASC");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Banner banner = new Banner();
                banner.setBannerID(rs.getInt("bannerID"));
                banner.setBannerImg(rs.getString("bannerImg"));
                banners.add(banner);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return banners;
    }

    // Thêm banner mới vào cơ sở dữ liệu
    public void addBanner(String fileName) {
        try (Connection conn = new DBContext().connection;
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO banner (bannerImg) VALUES (?)")) {

            ps.setString(1, fileName); // tên file ảnh
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xóa banner theo ID
    public void deleteBanner(int bannerID) {
        try (Connection conn = new DBContext().connection;
             PreparedStatement ps = conn.prepareStatement(
                     "DELETE FROM banner WHERE bannerID = ?")) {

            ps.setInt(1, bannerID); // truyền ID cần xóa
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // (Tùy chọn) Lấy tên file ảnh banner theo ID (dùng khi cần xóa file trên ổ đĩa)
    public String getFileNameById(int bannerID) {
        try (Connection conn = new DBContext().connection;
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT bannerImg FROM banner WHERE bannerID = ?")) {

            ps.setInt(1, bannerID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("bannerImg");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
