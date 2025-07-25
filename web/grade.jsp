<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Kết quả học tập</title>
    <link rel="stylesheet" href="./css/style.css">
</head>
<body>
    <div class="container">
        <div class="sidebar">
            <div class="user-info">
                <img src="avatar.png" alt="Avatar">
                <div>
                    <p>${sessionScope.user.fullName}</p>
                    <ul>
                        <li><a href="DashboardServlet"><span class="icon">🏠</span> Trang chủ</a></li>
                        <li><a href="DashboardServlet"><span class="icon">📅</span> Bảng điều khiển</a></li>
                        <li><a href="#"><span class="icon">🔔</span> Thông báo</a></li>
                    </ul>
                </div>
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
                    <form action="GradeServlet" method="get" style="display: flex; align-items: center; gap: 10px;">
                        <input type="text" name="searchTestName" placeholder="Tìm kiếm bài kiểm tra..." value="${searchTestName}">
                        <select name="subjectID" onchange="this.form.submit()">
                            <option value="">Tất cả môn học</option>
                            <c:forEach var="subject" items="${subjects}">
                                <option value="${subject.subjectID}" <c:if test="${subject.subjectID == selectedSubjectID}">selected</c:if>>
                                    ${subject.subjectName}
                                </option>
                            </c:forEach>
                        </select>
                        <button type="submit">Khóa học: 2025-2026</button>
                    </form>
                </div>
            </div>
            <c:choose>
                <c:when test="${not empty grades}">
                    <table class="grade-table">
                        <thead>
                            <tr>
                                <th>Môn học</th>
                                <th>Tên bài kiểm tra</th>
                                <th>Điểm</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="grade" items="${grades}">
                                <tr>
                                    <td>
                                        <c:forEach var="cls" items="${classes}">
                                            <c:if test="${cls.tutoringClassID == grade.tutoringClassID}">
                                                <c:forEach var="subject" items="${subjects}">
                                                    <c:if test="${subject.subjectID == cls.subjectID}">
                                                        Môn ${subject.subjectName}
                                                    </c:if>
                                                </c:forEach>
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                    <td>${grade.nameOfTest}</td>
                                    <td>${grade.score}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <p>Chưa có điểm nào.</p>
                </c:otherwise>
            </c:choose>
            <a href="DashboardServlet" class="back-button">Quay lại</a>
        </div>
    </div>
    <footer>Footer</footer>
</body>
</html>