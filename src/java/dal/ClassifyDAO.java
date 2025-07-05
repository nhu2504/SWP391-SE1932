/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;
import entity.Classify;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author NGOC ANH
 */
public class ClassifyDAO {
     public List<Classify> getAllClassify() {
        List<Classify> classifyList = new ArrayList<>();
        String sql = "select*from Classify";
        try (Connection conn = new DBContext().connection; 
                PreparedStatement ps = conn.prepareStatement(sql); 
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Classify classify = new Classify();
                classify.setClassifyId(rs.getInt("ClassifyID"));
                classify.setTypeDoc(rs.getString("TypeDoc"));
                classifyList.add(classify);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classifyList;
    }
}
