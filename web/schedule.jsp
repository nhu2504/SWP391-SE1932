<%-- 
    Document   : schedule.jsp
    Created on : May 29, 2025, 6:45:25 PM
    Author     : Asus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>   
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lịch học của bạn</title>
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
                    <li><a href="DashboardServlet"><span class="icon">📅</span> Bảng điều khiển</a></li>
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
                <h2>Lịch học của bạn</h2>
                <div class="search-term">
                    <input type="text" placeholder="Search...">
                    <button>Khóa học: 2025-2026</button>
                </div>
            </div>
            <table class="schedule-table">
                <thead>
                    <tr>
                        <th>Thứ</th>
                        <th>Thời gian</th>
                        <th>Môn học</th>
                        <th>Phòng học</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="tutoringClass" items="${tutoringClasses}">
                        <tr>
                            <td>${tutoringClass.schedule.split(" - ")[0]}</td>
                            <td>${tutoringClass.schedule.split(" - ")[1]}</td>
                            <td>${tutoringClass.className}</td>
                            <td>A101</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <a href="DashboardServlet" class="back-button">Quay lại</a>
        </div>
    </div>
    <footer>Footer</footer>
</body>
</html>