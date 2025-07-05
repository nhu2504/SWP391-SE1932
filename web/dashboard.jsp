<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>EDURA System</title>

    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=League+Spartan:wght@100;300;400;600;700&display=swap" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/owl.carousel.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/owl.theme.default.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/tooplate-gotto-job.css" rel="stylesheet">

    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f5f5f5;
            color: #333;
        }
        .top-header {
            background-color: #FFF1F1;
            color: #000;
            font-family: 'League Spartan', sans-serif;
            padding: 15px 0;
            display: flex;
            justify-content: space-around;
            align-items: center;
            text-align: left;
        }
        .top-header .logo img {
            max-width: 150px;
            height: auto;
        }
        .top-header .contact-item {
            display: flex;
            align-items: center;
            font-size: 16px;
        }
        .top-header .contact-item i {
            margin-right: 10px;
            color: #FF6B6B;
            font-size: 35px;
        }
        .top-header .contact-item h6 {
            font-weight: 600;
            font-size: 22px;
            margin-bottom: 2px;
        }
        .top-header .contact-item small {
            font-size: 14px;
            color: #333;
        }
        @media (max-width: 992px) {
            .top-header {
                display: none;
            }
        }
        .sidebar {
            background-color: #FFF1F1;
            padding: 30px 20px;
            min-height: 70vh;
            text-align: center;
            display: flex;
            flex-direction: column;
        }
        .avatar {
            display: flex;
            justify-content: center;
            align-items: center;
            margin: 20px 0;
        }
        .avatar-img {
            width: 100px;
            height: 100px;
            border-radius: 50%;
            object-fit: cover;
            border: 2px solid #ccc;
        }
        .username {
            font-weight: bold;
            font-size: 18px;
            margin-bottom: 40px;
            color: #000;
        }
        .menu-group {
            display: flex;
            flex-direction: column;
            gap: 30px;
            width: 100%;
        }
        .menu-group a {
            display: block;
            font-size: 18px;
            padding: 15px 0;
            color: #333;
            text-decoration: none;
            text-align: left;
            padding-left: 40px;
            transition: background 0.3s;
        }
        .sidebar a i {
            margin-right: 10px;
        }
        .menu-group a:hover {
            background-color: #ffd8eb;
            border-radius: 10px;
        }
        .main {
            padding: 0;
        }
        h1.dashboard-title {
            text-align: center;
            background-color: #f0f0f0;
            padding: 15px;
            font-size: 50px;
            margin-top: 0;
            font-weight: bold;
            color: #000;
        }
        .grid {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 60px;
            padding: 40px 20px;
            max-width: 100%;
            margin-left: 0;
            margin-right: auto;
        }
        .card {
            background-color: #FFF1F1;
            padding: 40px;
            text-align: center;
            font-size: 20px;
            border-radius: 20px;
            font-weight: 500;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            text-decoration: none;
            color: #333;
            width: 100%;
        }
        .card i, .card span.icon {
            font-size: 30px;
            display: block;
            margin-bottom: 10px;
        }
        .card:hover {
            transform: scale(1.05);
            box-shadow: 0 6px 16px rgba(255, 107, 107, 0.4);
            background-color: #fcd9e6;
        }
        .notification-section {
            margin: 40px 20px;
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
            padding: 20px;
        }
        .notification-section h3 {
            font-size: 24px;
            margin-bottom: 20px;
            color: #333;
        }
        .notification {
            background-color: #fff;
            border-left: 5px solid #FF6B6B;
            margin-bottom: 15px;
            padding: 15px 20px;
            border-radius: 4px;
        }
        .notification.unread {
            border-left-color: #ff3b3b;
            background-color: #fff8f8;
        }
        .notification.read {
            opacity: 0.85;
        }
        .notification h4 {
            margin: 0;
            font-size: 16px;
            color: #333;
        }
        .notification .meta {
            font-size: 13px;
            color: #777;
            margin-top: 4px;
        }
        .notification .content {
            margin-top: 10px;
            font-size: 14px;
            line-height: 1.5;
        }
        .view-all {
            text-align: right;
            margin-top: 20px;
        }
        .view-all a {
            color: #FF6B6B;
            text-decoration: none;
            font-weight: 600;
        }
        .view-all a:hover {
            text-decoration: underline;
        }
        @media (max-width: 768px) {
            .grid {
                grid-template-columns: 1fr;
                gap: 40px;
                padding: 20px;
            }
        }
        .schedule-table {
            margin: 40px 20px;
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
            overflow-x: auto;
        }
        .schedule-table table {
            width: 100%;
            border-collapse: collapse;
        }
        .schedule-table th, .schedule-table td {
            padding: 15px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        .schedule-table th {
            background-color: #FF6B6B;
            color: #fff;
            font-weight: 600;
        }
        .schedule-table td {
            font-size: 16px;
        }
        .schedule-table tr:hover {
            background-color: #f9f9f9;
        }
        .attendance-status {
            margin: 20px 20px;
            font-size: 18px;
            font-weight: bold;
            color: #dc3545;
        }
        .site-footer {
            margin-top: 90px;
            font-family: 'League Spartan', sans-serif;
        }
        .container-fluid.bg-dark.text-white {
            background-color: #FFF1F1 !important;
            color: #333 !important;
            padding: 60px 45px;
            text-align: left;
            font-family: 'League Spartan', sans-serif;
        }
        .container-fluid.bg-dark.text-white h5.text-primary {
            color: #FF6B6B !important;
            letter-spacing: 5px;
            text-transform: uppercase;
            margin-bottom: 20px;
            font-family: 'League Spartan', sans-serif;
        }
        .container-fluid.bg-dark.text-white a.text-white {
            color: #333 !important;
            margin-bottom: 10px;
            display: block;
            font-family: 'League Spartan', sans-serif;
        }
        .container-fluid.bg-dark.text-white a.text-white:hover {
            color: #FF6B6B !important;
        }
        .container-fluid.bg-dark.text-white .btn-outline-light {
            border-color: #333 !important;
            color: #333 !important;
            border-radius: 50%;
            width: 40px;
            height: 40px;
            display: flex;
            align-items: center;
            justify-content: center;
            margin-right: 10px;
        }
        .container-fluid.bg-dark.text-white .btn-outline-light:hover {
            background-color: #FF6B6B !important;
            color: #fff !important;
            border-color: #FF6B6B !important;
        }
        .logo-container {
            position: relative;
            width: 150px;
            height: 150px;
            overflow: hidden;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .logo-image {
            max-width: 100%;
            height: auto;
            transform: scale(2);
            transition: transform 0.3s ease;
        }
        .slogan-group {
            display: flex;
            flex-direction: column;
            gap: 5px;
            align-items: flex-start;
        }
        .slogan {
            font-size: 1.2rem;
            font-weight: 700;
            color: #333;
            margin: 0;
            line-height: 1.4;
            transition: color 0.3s ease;
        }
        .back-top-icon {
            position: fixed;
            bottom: 30px;
            right: 30px;
            top: auto !important;
            width: 50px;
            height: 50px;
            background-color: #FF6B6B;
            color: #fff;
            border-radius: 50%;
            font-size: 24px;
            text-decoration: none;
            z-index: 1000;
            transition: transform 0.3s ease, background-color 0.3s ease, box-shadow 0.3s ease, opacity 0.3s ease;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
            opacity: 0;
            visibility: hidden;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .back-top-icon.visible {
            opacity: 1;
            visibility: visible;
        }
        .back-top-icon:hover {
            background-color: #E55A5A;
            transform: scale(1.1);
            box-shadow: 0 4px 12px rgba(255, 107, 107, 0.4);
        }
        @media (max-width: 991px) {
            .back-top-icon {
                width: 40px;
                height: 40px;
                font-size: 20px;
                bottom: 20px;
                right: 20px;
                top: auto !important;
            }
            .container-fluid.bg-dark.text-white {
                padding: 60px 45px !important;
            }
        }
        @media (max-width: 576px) {
            .back-top-icon {
                width: 35px;
                height: 35px;
                font-size: 18px;
            }
        }
    </style>
</head>
<body id="top">
    <div class="container-fluid top-header">
        <div class="row w-100 justify-content-around align-items-center">
            <div class="logo">
                <img src="${pageContext.request.contextPath}/images/${centerInfo['Logo']}" alt="Logo EDURA" class="logo-img" onerror="this.src='${pageContext.request.contextPath}/images/fallback.png';">
            </div>
            <div class="contact-item">
                <i class="fas fa-map-marker-alt"></i>
                <div>
                    <h6>Địa chỉ</h6>
                    <small>${not empty centerInfo['AddressCenter'] ? centerInfo['AddressCenter'] : 'Chưa cập nhật'}</small>
                </div>
            </div>
            <div class="contact-item">
                <i class="fas fa-envelope"></i>
                <div>
                    <h6>Email</h6>
                    <small><a href="mailto:${not empty centerInfo['Email'] ? centerInfo['Email'] : ''}">${not empty centerInfo['Email'] ? centerInfo['Email'] : 'Chưa cập nhật'}</a></small>
                </div>
            </div>
            <div class="contact-item">
                <i class="fas fa-phone"></i>
                <div>
                    <h6>Điện thoại</h6>
                    <small>${not empty centerInfo['Phone'] ? centerInfo['Phone'] : 'Chưa cập nhật'}</small>
                </div>
            </div>
        </div>
    </div>

    <h1 class="dashboard-title">Bảng Điều Khiển</h1>

    <div class="container-fluid">
        <div class="row">
            <div class="col-md-3 sidebar">
                <div class="avatar">
                    <img src="${pageContext.request.contextPath}/images${not empty sessionScope.userAvatar ? sessionScope.userAvatar : '/default-avatar.png'}" alt="Avatar" class="avatar-img" onerror="this.src='${pageContext.request.contextPath}/images/default-avatar.png';">
                </div>
                <div class="username">${not empty sessionScope.userName ? sessionScope.userName : 'Khách'}</div>
                <div class="menu-group">
                    <a href="${pageContext.request.contextPath}/home"><i class="fas fa-home"></i> Trang chủ</a>
                    <a href="${pageContext.request.contextPath}/profile"><i class="fas fa-user"></i> Hồ sơ cá nhân</a>
                    <a href="${pageContext.request.contextPath}/notifications"><i class="fas fa-bell"></i> Thông báo</a>
                    <a href="${pageContext.request.contextPath}/submit-form"><i class="fas fa-paper-plane"></i> Gửi đơn</a>
                    <a href="${pageContext.request.contextPath}/logout"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a>
                </div>
            </div>

            <div class="col-md-9 main">
                <div class="grid">
                    <a href="${pageContext.request.contextPath}/tracking" class="card"><span class="icon">👥</span> Theo dõi lớp học</a>
                    <a href="${pageContext.request.contextPath}/attendance.jsp" class="card"><span class="icon">✅</span> Điểm danh</a>
                    <a href="${pageContext.request.contextPath}/PaymentServlet" class="card"><span class="icon">💳</span> Thanh toán</a>
                </div>
                <div class="attendance-status">
                    Tình trạng điểm danh: Đã nghỉ ${absentCount} buổi
                </div>
                <div class="schedule-table">
                    <table>
                        <thead>
                            <tr>
                                <th>Môn học</th>
                                <th>Ngày học</th>
                                <th>Ca học</th>
                                <th>Phòng học</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="schedule" items="${schedules}">
                                <tr>
                                    <td>${schedule.subjectName}</td>
                                    <td><fmt:formatDate value="${schedule.dateLearn}" pattern="dd-MM-yyyy"/></td>
                                    <td>${schedule.shiftID}</td>
                                    <td>${schedule.roomName}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="notification-section">
                    <h3>Thông báo gần đây</h3>
                    <c:choose>
                        <c:when test="${empty notifications}">
                            <div class="error-message">Không có thông báo để hiển thị.</div>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="notification" items="${notifications}">
                                <div class="notification ${notification.read ? 'read' : 'unread'}" data-read="${notification.read}" data-important="${notification.important}">
                                    <h4>
                                        ${notification.title}
                                        <span class="status">${notification.read ? '✅ Đã đọc' : '🔴 Chưa đọc'}${notification.important ? ' *' : ''}</span>
                                    </h4>
                                    <div class="meta">
                                        Gửi lúc: <fmt:formatDate value="${notification.createdAt}" pattern="dd/MM/yyyy - HH:mm"/>
                                    </div>
                                    <div class="content">
                                        ${notification.content}
                                    </div>
                                </div>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                    <div class="view-all">
                        <a href="${pageContext.request.contextPath}/notifications">Xem tất cả thông báo</a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <footer class="site-footer">
        <div class="container-fluid bg-dark text-white py-0 px-sm-3 px-lg-5">
            <div class="row pt-5">
                <div class="col-lg-5 col-md-12 mb-5">
                    <a href="" class="text-decoration-none">
                        <div class="logo-container">
                            <img src="${pageContext.request.contextPath}/images/${centerInfo['Logo']}" alt="Logo Trung Tâm" class="logo-image" onerror="this.src='${pageContext.request.contextPath}/images/fallback.png';">
                        </div>
                        <div class="slogan-group text-left mt-2">
                            <p class="slogan">Edura – Kết nối tri thức, chắp cánh tương lai.</p>
                            <p class="slogan">Edura – Hỗ trợ giáo viên, nâng tầm học sinh.</p>
                            <p class="slogan">Edura – Nơi tri thức hội tụ, ước mơ thăng hoa.</p>
                        </div>
                    </a>
                </div>
                <div class="col-lg-7 col-md-12">
                    <div class="row">
                        <div class="col-md-6 mb-5">
                            <h5 class="text-primary text-uppercase mb-4">Thông Tin Liên Hệ</h5>
                            <p><i class="fa fa-map-marker-alt mr-2"></i><small>${not empty centerInfo['AddressCenter'] ? centerInfo['AddressCenter'] : 'Chưa cập nhật'}</small></p>
                            <p><i class="fa fa-phone-alt mr-2"></i><small>${not empty centerInfo['Phone'] ? centerInfo['Phone'] : 'Chưa cập nhật'}</small></p>
                            <p><i class="fa fa-envelope mr-2"></i><small><a href="mailto:${centerInfo['Email']}">${not empty centerInfo['Email'] ? centerInfo['Email'] : 'Chưa cập nhật'}</a></small></p>
                            <div class="d-flex justify-content-start mt-4">
                                <a class="btn btn-outline-light btn-square mr-2" href="#"><i class="fab fa-twitter"></i></a>
                                <a class="btn btn-outline-light btn-square mr-2" href="#"><i class="fab fa-facebook-f"></i></a>
                                <a class="btn btn-outline-light btn-square mr-2" href="#"><i class="fab fa-linkedin-in"></i></a>
                                <a class="btn btn-outline-light btn-square" href="#"><i class="fab fa-instagram"></i></a>
                            </div>
                        </div>
                        <div class="col-md-6 mb-5">
                            <h5 class="text-primary text-uppercase mb-4">Khám Phá EDURA</h5>
                            <div class="d-flex flex-column justify-content-start">
                                <a class="text-white mb-2" href="${pageContext.request.contextPath}/home"><i class="fa fa-angle-right mr-2"></i>Trang Chủ</a>
                                <a class="text-white mb-2" href="${pageContext.request.contextPath}/about"><i class="fa fa-angle-right mr-2"></i>Giới Thiệu</a>
                                <a class="text-white mb-2" href="${pageContext.request.contextPath}/course"><i class="fa fa-angle-right mr-2"></i>Khoá Học</a>
                                <a class="text-white mb-2" href="${pageContext.request.contextPath}/teacher"><i class="fa fa-angle-right mr-2"></i>Giáo Viên</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <a class="back-top-icon bi-arrow-up smoothscroll d-flex justify-content-center align-items-center" href="#top"></a>
    </footer>

    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script>
        window.addEventListener('scroll', function () {
            const backTop = document.querySelector('.back-top-icon');
            if (window.scrollY > 300) {
                backTop.classList.add('visible');
            } else {
                backTop.classList.remove('visible');
            }
        });
    </script>
</body>
</html>