/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author DO NGOC ANH HE180661
 * 
 */
public class UserDAO {
    
    //lấy ra người dùng theo email và mật khẩu
    public User login(String email, String password) {
        //tìm người dùng có mail và mật khẩu trùng khớp
        String query = """
                       select * from [User]
                       where email = ?
                       and pass = ? 
                       """
                ;
        //kết nối db, gán giá trị mail và pass vào câu lệnh sql
        try {
            Connection conn = new DBContext().connection;
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);
            //truy vấn có kết quả thì tạo và trả về đối tượng user
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return new User(rs.getInt("UserID"),
                        rs.getString("FullName"),
                        rs.getString("Gender"),
                        rs.getDate("BirthDate"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("pass"),
                        rs.getString("avatar"),
                        rs.getInt("onlineStatus"),
                        rs.getDate("created_at"),
                        rs.getString("Certi"),
                        rs.getString("Descrip"),
                        rs.getInt("SchoolID"),
                        rs.getInt("SchoolClassID"),
                        rs.getInt("roleID"),
                        rs.getString("ParentEmail"),
                        rs.getString("ParentPhone"));
            }
            //xảy ra lỗi hoặc không có kết quả thì hiển thị ra lỗi và trả về null
        } catch (SQLException e) {
            System.out.println("Lỗi "+e.getMessage());
        }
        return null;
    }
    //tìm thông tin người dùng theo id
    public User getUserByID(int id) {
        //câu lệnh sql lấy người dùng theo id
        String query = """
                         select * from [User]
                         where UserID = ?""";
        //gán giá trị id vào câu lệnh sql
        try {
            Connection conn = new DBContext().connection;
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            //tìm thấy user theo id đó thì trả về user
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("UserID"),
                        rs.getString("FullName"),
                        rs.getString("Gender"),
                        rs.getDate("BirthDate"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("pass"),
                        rs.getString("avatar"),
                        rs.getInt("onlineStatus"),
                        rs.getDate("created_at"),
                        rs.getString("Certi"),
                        rs.getString("Descrip"),
                        rs.getInt("SchoolID"),
                        rs.getInt("SchoolClassID"),
                        rs.getInt("roleID"),
                        rs.getString("ParentEmail"),
                        rs.getString("ParentPhone"));
            }
            //xảy ra lỗi sẽ trả về null và hiển thị thông báo lỗi
        } catch (SQLException e) {
            System.out.println("Lỗi "+e.getMessage());
                    
        }
        return null;
    }
    //tìm người dùng bằng địa chỉ email
    public User getUserByEmail(String email) {
        //truy vấn lấy người dùng theo địa chỉ email
    String query = "select * from [User] where email = ?";
   //gán giá trị email vào truy vấn
    try {
        Connection conn = new DBContext().connection;
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, email);
        //nếu có dữ liệu thì trả về đối tượng user
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new User(rs.getInt("UserID"),
                    rs.getString("FullName"),
                    rs.getString("Gender"),
                    rs.getDate("BirthDate"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getString("pass"),
                    rs.getString("avatar"),
                    rs.getInt("onlineStatus"),
                    rs.getDate("created_at"),
                    rs.getString("Certi"),
                    rs.getString("Descrip"),
                    rs.getInt("SchoolID"),
                    rs.getInt("SchoolClassID"),
                    rs.getInt("roleID"),
                    rs.getString("ParentEmail"),
                    rs.getString("ParentPhone"));
        }
    } catch (SQLException e) {
        System.out.println("Lỗi "+e.getMessage());
    }
    return null;
}
    //test thử xem phương thức đã lấy được dữ liệu từ db chưa
public static void main(String[] args) {
        UserDAO r = new UserDAO();

       
        String email = "chuchegirl9@gmail.com"; 
    User role = r.getUserByEmail(email);
    if (role != null) {
        System.out.println("Information user: " + role.toString());
    } else {
        System.out.println("Not found user with email: " + email);
    }
    }


}
