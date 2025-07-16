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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DO NGOC ANH HE180661
 *
 */
public class UserDAO {
    private TeacherClassDAO tcDao = new TeacherClassDAO();
    private SubjectDAO subjectDao = new SubjectDAO();
    //lấy ra người dùng theo email và mật khẩu
    public User login(String emailOrPhone, String password) {
        //tìm người dùng có mail và mật khẩu trùng khớp
        String query = """
                       select * from [user] where (email = ? or phone = ?)
                       and pass = ? 
                       """;
        //kết nối db, gán giá trị mail và pass vào câu lệnh sql
        try {
            Connection conn = new DBContext().connection;
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, emailOrPhone);
            ps.setString(2, emailOrPhone);
            ps.setString(3, password);
            //truy vấn có kết quả thì tạo và trả về đối tượng user
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                int userId = rs.getInt("UserID");
                User user = new User(userId, 
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
                        null, 
                        null,
                        rs.getInt("roleID"),
                        rs.getBoolean("isHot"), 
                        rs.getString("ParentEmail"),
                        rs.getString("ParentPhone")
                );
               
                 user.setSchoolClasses(tcDao.getSchoolClassesByTeacherId(userId));
                 user.setSubjects(subjectDao.getSubjectsByTeacherId(userId));
            return user;
            }
            //xảy ra lỗi hoặc không có kết quả thì hiển thị ra lỗi và trả về null
        } catch (SQLException e) {
            System.out.println("Lỗi " + e.getMessage());
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
                int userId = rs.getInt("UserID");
                User user = new User(userId, 
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
                        null, 
                        null,
                        rs.getInt("roleID"),
                        rs.getBoolean("isHot"), 
                        rs.getString("ParentEmail"),
                        rs.getString("ParentPhone")
                );
               
                 user.setSchoolClasses(tcDao.getSchoolClassesByTeacherId(userId));
                 user.setSubjects(subjectDao.getSubjectsByTeacherId(userId));
            return user;
            }
            //xảy ra lỗi sẽ trả về null và hiển thị thông báo lỗi
        } catch (SQLException e) {
            System.out.println("Lỗi " + e.getMessage());

        }
        return null;
    }

    public String getCurrentAvatar(int userId) {
        String avatar = "images/default.jpg"; // fallback nếu không có
        String query = "SELECT avatar FROM [user] WHERE UserID = ?";
        try (Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                avatar = rs.getString("avatar");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return avatar;
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
                int userId = rs.getInt("UserID");
                User user = new User(userId, 
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
                        null, 
                        null,
                        rs.getInt("roleID"),
                        rs.getBoolean("isHot"), 
                        rs.getString("ParentEmail"),
                        rs.getString("ParentPhone")
                );
               
                 user.setSchoolClasses(tcDao.getSchoolClassesByTeacherId(userId));
                 user.setSubjects(subjectDao.getSubjectsByTeacherId(userId));
            return user;
            }
        } catch (SQLException e) {
            System.out.println("Lỗi " + e.getMessage());
        }
        return null;
    }

    public boolean updatePassword(int userId, String newPassword) {
        String sql = "UPDATE [User] SET pass = ? WHERE UserID = ?";
        try {
            Connection conn = new DBContext().connection;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newPassword); // Có thể hash password trước khi lưu!
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateUser(int userId, String email, String phone, String avatarFileName, String certi, String description, int schoolId) {
        String sql = "update [user] \n"
                + "set email = ?, phone = ?, avatar = ?,Certi =?,\n"
                + "descrip = ?,\n"
                + "SchoolID = ?\n"
                + "where userid = ?";
        try {
            Connection conn = new DBContext().connection;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, phone);
            ps.setString(3, avatarFileName);
            ps.setString(4, certi);
            ps.setString(5, description);
            ps.setInt(6, schoolId);
            ps.setInt(7, userId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean updateStudent(int userId, String email, String phone, String avatarFileName, String description, int schoolId) {
        String sql = "update [user] \n"
                + "set email = ?, phone = ?, avatar = ?,\n"
                + "descrip = ?,\n"
                + "SchoolID = ?\n"
                + "where userid = ?";
        try {
            Connection conn = new DBContext().connection;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, phone);
            ps.setString(3, avatarFileName);
            ps.setString(4, description);
            ps.setInt(5, schoolId);
            ps.setInt(6, userId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean updateAdmin(int userId, String email, String phone, String avatarFileName, String description) {
        String sql = "update [user] \n"
                + "set email = ?, phone = ?, avatar = ?,\n"
                + "descrip = ?\n"
                
                + "where userid = ?";
        try {
            Connection conn = new DBContext().connection;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, phone);
            ps.setString(3, avatarFileName);
            ps.setString(4, description);
           
            ps.setInt(5, userId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public List<Integer> getAllUserIds() {
    List<Integer> list = new ArrayList<>();
    String sql = "SELECT UserID FROM [User]";
    try (Connection conn = new DBContext().connection;
            PreparedStatement ps = conn.prepareStatement(sql)) {
        ResultSet rs = ps.executeQuery();
        while (rs.next()) list.add(rs.getInt("UserID"));
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}

public List<Integer> getUserIdsByRole(int roleId) {
    List<Integer> list = new ArrayList<>();
    String sql = "SELECT UserID FROM [User] WHERE RoleID = ?";
    try (Connection conn = new DBContext().connection;
            PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, roleId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) list.add(rs.getInt("UserID"));
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}


    //test thử xem phương thức đã lấy được dữ liệu từ db chưa
    public static void main(String[] args) {
//        UserDAO r = new UserDAO();
//
//        String email = "chuchegirl9@gmail.com";
//        User role = r.getUserByEmail(email);
//        if (role != null) {
//            System.out.println("Information user: " + role.toString());
//        } else {
//            System.out.println("Not found user with email: " + email);
//        }
//UserDAO dao = new UserDAO();
//        int testUserId = 3; // Thay bằng ID có thật trong DB của bạn
//        String newPassword = "12345678"; // Mật khẩu mới bạn muốn đặt
//
//        boolean success = dao.updatePassword(testUserId, newPassword);
//
//        if (success) {
//            System.out.println("✔️ Đặt lại mật khẩu thành công!");
//        } else {
//            System.out.println("❌ Đặt lại mật khẩu thất bại.");
//        }
//    }

//    UserDAO dao = new UserDAO();
//
//     //Thông tin test
//    int userId = 2;
//    String email = "tuanh@gmail.com";
//    String phone = "0987654321";
//    String avatarFileName = "team-1.jpg";
//    String certi = "Cử nhân Giáo dục";
//    String description = "Đang công tác tại trường ABC";
//    int schoolId = 1; // ID phải đúng với bảng school nếu có ràng buộc khóa ngoại
//    int classId = 2;  // ID phải đúng với bảng class nếu có ràng buộc
//
//    boolean success = dao.updateUser(userId, email, phone, avatarFileName, certi, description, schoolId, classId);
//
//    if (success) {
//        System.out.println("✅ Cập nhật người dùng thành công.");
//    } else {
//        System.out.println("❌ Cập nhật người dùng thất bại.");
//    }
// UserDAO dao = new UserDAO();
//
//    int userId = 2;
//    String avatar = dao.getCurrentAvatar(userId);
//
//    if (avatar != null) {
//        System.out.println("Ảnh đại diện hiện tại: " + avatar);
//    } else {
//        System.out.println("Không tìm thấy ảnh đại diện cho người dùng có ID: " + userId);
//    }
    }

}
