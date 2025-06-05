<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Bảng điều khiển</title>
    <link rel="stylesheet" href="./css/style.css">
</head>
<body>
    <div class="container">
        <div class="sidebar">
            <div class="user-info">
                  <%
        String userName = (String) session.getAttribute("userName");
        String userAvatar = (String) session.getAttribute("userAvatar");
        if (userAvatar == null || userAvatar.isEmpty()) {
            userAvatar = "default-avatar.jpg";
        }
        if (userName == null || userName.isEmpty()) {
            userName = "Tên người dùng";
        }
                    %>

                    <div class="avatar">
                        <img src="images/<%= userAvatar %>" alt="Avatar" class="avatar-img">
                    </div>
                    <div class="username"><%= userName %></div>
                
                <ul>
                    <li><a href="DashboardServlet"><span class="icon">🏠</span> Trang chủ</a></li>
                    <li class="active"><a href="DashboardServlet"><span class="icon">📅</span> Bảng điều khiển</a></li>
                    <li><a href="#"><span class="icon">🔔</span> Thông báo</a></li>
                </ul>
            </div>
            <div class="sidebar-section">
                <ul>
                    <li><a href="#"><span class="icon">👤</span> Hồ sơ</a></li>
                    <li><a href="#"><span class="icon">⚙️</span> Cài đặt</a></li>
                    <li><a href="#"><span class="icon">🚪</span> Đăng xuất</a></li>
                </ul>
            </div>
            <div class="sidebar-help">
                <a href="#"><span class="icon">❓</span> Trợ giúp</a>
            </div>
        </div>
        <div class="main-content">
            <div class="header">
                <h2>Bảng điều khiển</h2>
                <div class="search-term">
                    <input type="text" placeholder="Search...">
                    <button>Khóa học: 2025-2026</button>
                </div>
            </div>
            <div class="dashboard-grid">
                <a href="ScheduleServlet" class="dashboard-item">
                    <span class="icon">📅</span>
                    <p>Xem lịch học</p>
                </a>
                <a href="GradeServlet" class="dashboard-item">
                    <span class="icon">📊</span>
                    <p>Xem điểm</p>
                </a>
                <a href="DocumentServlet" class="dashboard-item">
                    <span class="icon">📚</span>
                    <p>Tài liệu học tập</p>
                </a>
                <a href="AssignmentServlet" class="dashboard-item">
                    <span class="icon">☁️</span>
                    <p>Nộp bài tập</p>
                </a>
            </div>
            <a href="#" class="back-button">Quay lại</a>
        </div>
    </div>
    <footer>Footer</footer>
</body>
</html>