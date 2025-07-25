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

    Connection conn = new DBContext().connection;

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
    public User getUserById(int userId) {
        String sql = "SELECT u.UserID, u.FullName, u.Gender, u.BirthDate, u.phone, u.email,\n"
                + "       u.avatar, u.Descrip, u.SchoolID, u.roleID,\n"
                + "       s.SchoolName,\n"
                + "       sc.SchoolClassID, sc.ClassName\n"
                + "FROM [User] u\n"
                + "LEFT JOIN School s ON u.SchoolID = s.SchoolID\n"
                
                + "LEFT JOIN SchoolClass sc ON u.SchoolClassID = sc.SchoolClassID\n"
                + "WHERE u.UserID = ?";

        try (Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("UserID"));
                u.setName(rs.getString("FullName"));
                u.setGender(rs.getString("Gender"));
                u.setBirth(rs.getDate("BirthDate"));
                u.setPhone(rs.getString("phone"));
                u.setEmail(rs.getString("email"));
                u.setAvatar(rs.getString("avatar"));
                u.setDescrip(rs.getString("Descrip"));
                u.setSchoolID(rs.getInt("SchoolID"));
                u.setRoleID(rs.getInt("roleID"));
                u.setSchoolClassId(rs.getInt("SchoolClassID"));
                u.setSchoolName(rs.getString("SchoolName"));
                u.setSchoolClassName(rs.getString("ClassName"));

                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

    public boolean updateStudentProfile(int userId, String email, String phone, String avatar, 
                                 String descrip) {
    String sql = "UPDATE [User] SET email = ?, phone = ?, avatar = ?, Descrip = ? WHERE UserID = ?";
    
    try (Connection conn = new DBContext().connection;
            PreparedStatement ps = conn.prepareStatement(sql)) {
         
        ps.setString(1, email);
        ps.setString(2, phone);
        ps.setString(3, avatar);
        ps.setString(4, descrip);
        ps.setInt(5, userId);
        
        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
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

    public List<Integer> getAllUserIDs() {
        List<Integer> list = new ArrayList<>();
        String sql = "SELECT userID FROM [User]";
        try (Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(rs.getInt("userID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Integer> getUserIdsByRole(int roleId) {
        List<Integer> list = new ArrayList<>();
        String sql = "SELECT UserID FROM [User] WHERE RoleID = ?";
        try (Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, roleId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getInt("UserID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<User> getUsersByRole(int roleID) {
        List<User> list = new ArrayList<>();
        String sql = "SELECT userID, fullName FROM [User] WHERE roleID = ?";
        try (Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, roleID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("userID"));
                user.setName(rs.getString("fullName"));
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public User getLatestUserInfo() {
        String sql = "SELECT TOP 1 FullName, email, pass FROM [User] ORDER BY UserID DESC";
        try (Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                User user = new User();
                user.setName(rs.getString("FullName"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("pass"));
                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getStudent() {
        List<User> list = new ArrayList<>();
        String sql = "select u.UserID, FullName,avatar,Gender,BirthDate,phone,email,s.SchoolName,sc.ClassName, onlineStatus\n"
                + "from [User] u join School s on u.SchoolID = s.SchoolID\n"
                
                + "join SchoolClass sc on u.SchoolClassID = sc.SchoolClassID\n"
                + "where roleID = 3";
        try (Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("userID"));
                u.setName(rs.getString("FullName"));
                u.setAvatar(rs.getString("avatar"));
                u.setGender(rs.getString("Gender"));
                u.setBirth(rs.getDate("BirthDate"));
                u.setPhone(rs.getString("phone"));
                u.setEmail(rs.getString("email"));
                u.setSchoolName(rs.getString("SchoolName"));
                u.setSchoolClassName(rs.getString("ClassName"));
                u.setStatus(rs.getInt("onlineStatus"));
                list.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updateStatusStudent(int userId, int newStatus) {
        String sql = "UPDATE [User] SET onlineStatus = ? WHERE UserID = ?";
        try {
            Connection conn = new DBContext().connection;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, newStatus);
            ps.setInt(2, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Lỗi cập nhật trạng thái: " + e.getMessage());
        }
    }
    


    //test thử xem phương thức đã lấy được dữ liệu từ db chưa
    public static void main(String[] args) {
UserDAO dao = new UserDAO();
    int testUserId = 78; // Thay bằng ID bạn muốn test, phải có trong DB

    User user = dao.getUserById(testUserId);

    if (user != null) {
        System.out.println("=== User Info ===");
        System.out.println("ID: " + user.getId());
        System.out.println("Name: " + user.getName());
        System.out.println("Gender: " + user.getGender());
        System.out.println("Birthdate: " + user.getBirth());
        System.out.println("Phone: " + user.getPhone());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Avatar: " + user.getAvatar());
        System.out.println("Description: " + user.getDescrip());
        System.out.println("SchoolID: " + user.getSchoolID());
        System.out.println("SchoolName: " + user.getSchoolName());
        System.out.println("ClassID: " + user.getSchoolClassId());
        System.out.println("ClassName: " + user.getSchoolClassName());
        System.out.println("RoleID: " + user.getRoleID());
    } else {
        System.out.println("User not found!");
    }
    }

}
