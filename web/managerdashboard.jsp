<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Manager Dashboard</title>
    <style>
        /* CSS cho toàn bộ trang */
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
        .stats { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 20px; }
        .stat-box { background-color: #fee2e2; padding: 20px; border-radius: 8px; text-align: center; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        .charts { display: grid; grid-template-columns: 1fr 1fr; gap: 20px; margin-top: 20px; }
        .chart-box { background-color: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        .dropdown { position: relative; }
        .dropdown-content { display: none; position: absolute; right: 0; background-color: white; box-shadow: 0 2px 4px rgba(0,0,0,0.1); border-radius: 8px; padding: 10px; }
        .dropdown-content.show { display: block; }
        .dropdown-content a { display: block; padding: 5px; color: #333; text-decoration: none; }
        .dropdown-content a:hover { color: #2563eb; }
    </style>
    <script>
        // JavaScript để xử lý dropdown
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
                <h1>Tổng quan hệ thống</h1>
                <div class="dropdown">
                    <button class="dropdown-button" onclick="toggleDropdown()">Văn Thị Như ▼</button>
                    <div id="userDropdown" class="dropdown-content">
                        <a href="#">Hồ sơ</a>
                        <a href="#">Đăng xuất</a>
                    </div>
                </div>
            </div>

            <!-- Thống kê -->
            <div class="stats">
                <div class="stat-box">🎓 Học sinh: ${studentCount}</div>
                <div class="stat-box">🧑‍🏫 Giáo viên: ${teacherCount}</div>
                <div class="stat-box">🏫 Lớp học: ${classCount}</div>
                <div class="stat-box">📅 Lịch học hôm nay: ${todayScheduleCount}</div>
            </div>

            <!-- Biểu đồ (Placeholder) -->
            <div class="charts">
                <div class="chart-box">
                    <h2>Thống kê đăng ký khóa học</h2>
                    <p>Chưa triển khai biểu đồ</p>
                </div>
                <div class="chart-box">
                    <h2>Phân bố lịch học</h2>
                    <p>Chưa triển khai biểu đồ</p>
                </div>
            </div>
        </div>
    </div>
</body>
</html>