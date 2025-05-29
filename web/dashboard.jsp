<%-- 
    Document   : dashboard.jsp
    Created on : May 29, 2025, 6:46:10 PM
    Author     : Asus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>       
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Bảng điều khiển</title>
    <link rel="stylesheet"href="./css/style.css">
</head>
<body>
    <div class="container">
        <div class="sidebar">
            <div class="user-info">
                <img src="avatar.png" alt="Avatar">
                <p>Nguyễn Văn A</p>
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
                <div class="dashboard-item">
                    <span class="icon">📚</span>
                    <p>Tài liệu học tập</p>
                </div>
                <div class="dashboard-item">
                    <span class="icon">☁️</span>
                    <p>Nộp bài tập</p>
                </div>
            </div>
            <a href="#" class="back-button">Quay lại</a>
        </div>
    </div>
    <footer>Footer</footer>
</body>
</html>