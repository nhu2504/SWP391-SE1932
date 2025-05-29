<%-- 
    Document   : grade.jsp
    Created on : May 29, 2025, 10:42:06 AM
    Author     : Asus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Kết quả học tập</title>
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
                <h2>Kết quả học tập</h2>
                <div class="search-term">
                    <input type="text" placeholder="Search...">
                    <button>Khóa học: 2025-2026</button>
                </div>
            </div>
            <table class="grade-table">
                <thead>
                    <tr>
                        <th>Môn học</th>
                        <th>Bài kiểm tra số 1</th>
                        <th>Bài kiểm tra số 2</th>
                        <th>Trung bình</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="grade" items="${grades}">
                        <tr>
                            <td>${grade.note}</td>
                            <td>${grade.score}</td>
                            <td>-</td>
                            <td>-</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="dropdown">
                <label>Môn học:</label>
                <select>
                    <option>Toán</option>
                    <option>Văn</option>
                    <option>Anh</option>
                </select>
            </div>
            <a href="DashboardServlet" class="back-button">Quay lại</a>
        </div>
    </div>
    <footer>Footer</footer>
</body>
</html>