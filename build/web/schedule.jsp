<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lịch học của bạn</title>
    <link rel="stylesheet" href="./css/style.css">
</head>
<body>
    <div class="container">
        <div class="sidebar">
            <div class="user-info">
                <img src="avatar.png" alt="Avatar">
                <p>${sessionScope.user.fullName}</p>
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
            <c:choose>
                <c:when test="${not empty tutoringClasses}">
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
                                    <td>
                                        <c:forEach var="thu" items="${thus}">
                                            <c:if test="${thu.thuID == tutoringClass.thuID}">${thu.nameThu}</c:if>
                                        </c:forEach>
                                    </td>
                                    <td>
                                        <c:forEach var="shift" items="${shifts}">
                                            <c:if test="${shift.shiftID == tutoringClass.shiftID}">${shift.startTime} - ${shift.endTime}</c:if>
                                        </c:forEach>
                                    </td>
                                    <td>
                                        <c:forEach var="subject" items="${subjects}">
                                            <c:if test="${subject.subjectID == tutoringClass.subjectID}">${subject.subjectName}</c:if>
                                        </c:forEach>
                                    </td>
                                    <td>
                                        <c:forEach var="room" items="${rooms}">
                                            <c:if test="${room.id == tutoringClass.roomID}">${room.name}</c:if>
                                        </c:forEach>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <p>Chưa có lịch học nào.</p>
                </c:otherwise>
            </c:choose>
            <a href="DashboardServlet" class="back-button">Quay lại</a>
        </div>
    </div>
    <footer>Footer</footer>
</body>
</html>