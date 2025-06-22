<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý người dùng</title>
    <style>
        /* CSS cho trang quản lý người dùng */
        body { font-family: Arial, sans-serif; background-color: #f3f4f6; margin: 0; }
        .container { display: flex; min-height: 100vh; }
        .sidebar { width: 250px; background-color: white; padding: 20px; box-shadow: 2px 0 5px rgba(0,0,0,0.1); }
        .sidebar img { width: 80px; height: 80px; border-radius: 50%; display: block; margin: 0 auto; }
        .sidebar p { text-align: center; font-weight: bold; margin: 10px 0; }
        .sidebar a { display: block; padding: 10px; color: #333; text-decoration: none; }
        .sidebar a:hover { color: #2563eb; }
        .main-content { flex: 1; padding: 20px; }
        .header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
        .header h1 { font-size: 24px; }
        .table-container { background-color: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        table { width: 100%; border-collapse: collapse; }
        th, td { padding: 10px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background-color: #f2f2f2; }
        .dropdown { position: relative; }
        .dropdown-content { display: none; position: absolute; right: 0; background-color: white; box-shadow: 0 2px 4px rgba(0,0,0,0.1); border-radius: 8px; padding: 10px; }
        .dropdown-content.show { display: block; }
        .dropdown-content a { display: block; padding: 5px; color: #333; text-decoration: none; }
        .dropdown-content a:hover { color: #2563eb; }
    </style>
    <script>
        function toggleDropdown() {
            document.getElementById('userDropdown').classList.toggle('show');
        }
        window.onclick = function(event) {
            if (!event.target.matches('.dropdown-button')) {
                var dropdowns = document.getElementsByClassName('dropdown-content');
                for (var i = 0; i < dropdowns.length; i++) {
                    var openDropdown = dropdowns[i];
                    if (openDropdown.classList.contains('show')) {
                        openDropdown.classList.remove('show');
                    }
                }
            }
        }
    </script>
</head>
<body>
    <div class="container">
        <!-- Sidebar -->
        <div class="sidebar">
            <img src="manager.jpg" alt="Avatar">
            <p>Văn Thị Như</p>
            <a href="managerDashboard">🏠 Trang chủ</a>
            <a href="managerUser">👥 Quản lý người dùng</a>
            <a href="managerTutoringClass">🏫 Quản lý khóa học</a>
            <a href="managerSchedule">🗓️ Quản lý lịch học</a>
            <a href="#">🔓 Đăng xuất</a>
        </div>

        <!-- Nội dung chính -->
        <div class="main-content">
            <!-- Header -->
            <div class="header">
                <h1>Quản lý người dùng</h1>
                <div class="dropdown">
                    <button class="dropdown-button" onclick="toggleDropdown()">Văn Thị Như ▼</button>
                    <div id="userDropdown" class="dropdown-content">
                        <a href="#">Hồ sơ</a>
                        <a href="#">Đăng xuất</a>
                    </div>
                </div>
            </div>

            <!-- Bảng danh sách người dùng -->
            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Họ tên</th>
                            <th>Email</th>
                            <th>Vai trò</th>
                            <th>Ngày sinh</th>
                            <th>Số điện thoại</th>
                            <th>Avatar</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="user" items="${users}">
                            <tr>
                                <td>${user.userID}</td>
                                <td>${user.fullName}</td>
                                <td>${user.email}</td>
                                <td>${user.roleName}</td>
                                <td>${user.birthDate}</td>
                                <td>${user.phone}</td>
                                <td><img src="${user.avatar}" alt="Avatar" style="width: 40px; height: 40px; border-radius: 50%;"></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>