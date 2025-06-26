package entity;

/**
 * Lớp roles đại diện cho vai trò của người dùng (ví dụ: học sinh, giáo viên, quản lý).
 * @author DO NGOC ANH HE180661
 */
public class roles {
    private int roleID; // ID của vai trò (ví dụ: 3 cho học sinh)
    private String roleName; // Tên vai trò (ví dụ: "Student")

    // Constructor mặc định
    public roles() {
    }

    // Constructor với roleID và roleName
    public roles(int roleID, String roleName) {
        this.roleID = roleID;
        this.roleName = roleName;
    }

    // Getter cho roleID
    public int getRoleID() {
        return roleID;
    }

    // Setter cho roleID
    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    // Getter cho roleName
    public String getRoleName() {
        return roleName;
    }

    // Setter cho roleName
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}