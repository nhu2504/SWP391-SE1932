/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
 *
 * @author i
 */
public class BannerDAO {
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

}
