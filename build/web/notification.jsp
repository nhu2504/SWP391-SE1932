<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Trang thông báo cho học sinh - Edura">
    <meta name="author" content="Edura Team">
    <title>Thông báo của học sinh - Edura</title>

    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=League+Spartan:wght@400;700&display=swap" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/owl.carousel.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/owl.theme.default.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/tooplate-gotto-job.css" rel="stylesheet">

    <style>
        body {
            font-family: 'League Spartan', 'Segoe UI Emoji', sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f5f5f5;
            color: #333;
        }
        .top-header {
            background-color: #FFF1F1;
            color: #000;
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
            font-size: 18px;
        }
        .top-header .contact-item i {
            margin-right: 10px;
            color: #FF6B6B;
            font-size: 35px;
        }
        .top-header .contact-item h6 {
            font-weight: bold;
            font-size: 22px;
            margin-bottom: 2px;
        }
        .top-header .contact-item small {
            font-size: 14px;
            color: #333;
        }
        @media (max-width: 992px) {
            .top-header { display: none; }
        }
        .main {
            padding: 20px;
        }
        h1.dashboard-title {
            text-align: center;
            background-color: #f0f0f0;
            padding: 15px;
            font-size: 50px;
            margin-top: 0;
            font-weight: bold;
            color: #000;
            position: relative;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        h1.dashboard-title .title-content {
            flex-grow: 1;
            text-align: center;
        }
        h1.dashboard-title .back-arrow {
            margin-left: 20px;
            color: #333;
            font-size: 24px;
            text-decoration: none;
        }
        h1.dashboard-title .back-arrow:hover {
            color: #FF6B6B;
        }
        h1.dashboard-title .header-icons {
            margin-right: 20px;
            display: flex;
            align-items: center;
            gap: 15px;
        }
        h1.dashboard-title .header-icons .user-dropdown {
            position: relative;
            display: flex;
            align-items: center;
        }
        h1.dashboard-title .header-icons .user-dropdown .dropbtn {
            background: none;
            border: none;
            padding: 0;
            font-size: 18px;
            color: #333;
            cursor: pointer;
            display: flex;
            align-items: center;
        }
        h1.dashboard-title .header-icons .user-dropdown .dropdown-content {
            display: none;
            position: absolute;
            right: 0;
            background-color: white;
            min-width: 120px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            border-radius: 8px;
            padding: 10px;
            z-index: 50;
            top: 100%;
        }
        h1.dashboard-title .header-icons .user-dropdown:hover .dropdown-content {
            display: block;
        }
        h1.dashboard-title .header-icons .user-dropdown .dropdown-content a {
            display: block;
            padding: 5px 10px;
            color: #333;
            text-decoration: none;
            font-size: 14px;
        }
        h1.dashboard-title .header-icons .user-dropdown .dropdown-content a:hover {
            color: #FF6B6B;
        }
        .container {
            padding: 20px;
        }
        .filter-bar {
            text-align: right;
            margin-bottom: 20px;
        }
        .filter-bar select {
            padding: 5px 10px;
            font-size: 14px;
            border-radius: 4px;
            border: 1px solid #ddd;
            height: 37px;
        }
        .notification {
            background-color: #fff;
            border-left: 5px solid #FF6B6B;
            margin-bottom: 15px;
            padding: 15px 20px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.05);
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
        .notification .status {
            float: right;
            font-size: 12px;
            color: #FF6B6B;
            font-weight: bold;
        }
        .site-footer {
            margin-top: 90px;
        }
        .container-fluid.bg-dark.text-white {
            background-color: #FFF1F1 !important;
            color: #333 !important;
            padding: 60px 45px;
            text-align: left;
        }
        .container-fluid.bg-dark.text-white h5.text-primary {
            color: #FF6B6B !important;
            letter-spacing: 5px;
            text-transform: uppercase;
            margin-bottom: 20px;
        }
        .container-fluid.bg-dark.text-white a.text-white {
            color: #333 !important;
            margin-bottom: 10px;
            display: block;
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
        }
        @media (max-width: 576px) {
            .back-top-icon {
                width: 35px;
                height: 35px;
                font-size: 18px;
            }
        }
        @media (max-width: 991px) {
            .container-fluid.bg-dark.text-white {
                padding: 60px 45px !important;
            }
        }
        @media (max-width: 768px) {
            .notification h4, .notification .meta, .notification .content {
                font-size: 14px;
            }
        }
    </style>

    <script>
        function toggleDropdown() {
            const dropdown = document.getElementById('userDropdown');
            dropdown.classList.toggle('show');
        }

        window.addEventListener('click', function(e) {
            const button = document.getElementById('userButton');
            const dropdown = document.getElementById('userDropdown');
            if (!button.contains(e.target) && !dropdown.contains(e.target)) {
                dropdown.classList.remove('show');
            }
        });

        document.addEventListener('DOMContentLoaded', () => {
            window.addEventListener('scroll', function () {
                const backTop = document.querySelector('.back-top-icon');
                if (window.scrollY > 300) {
                    backTop.classList.add('visible');
                } else {
                    backTop.classList.remove('visible');
                }
            });

            function filterNotifications() {
                const filter = document.getElementById("filter").value;
                const notis = document.querySelectorAll(".notification");

                notis.forEach(noti => {
                    const isRead = noti.dataset.read === "true";
                    const isImportant = noti.dataset.important === "true";
                    noti.style.display = "block";

                    switch (filter) {
                        case "all":
                            break;
                        case "unread":
                            if (isRead) noti.style.display = "none";
                            break;
                        case "read":
                            if (!isRead) noti.style.display = "none";
                            break;
                        case "important":
                            if (!isImportant) noti.style.display = "none";
                            break;
                        case "not-important":
                            if (isImportant) noti.style.display = "none";
                            break;
                    }
                });
            }

            document.getElementById("filter").addEventListener("change", filterNotifications);
        });
    </script>
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

    <h1 class="dashboard-title">
        <a href="${pageContext.request.contextPath}/DashboardServlet" class="back-arrow" title="Quay lại" onclick="console.log('Navigating to DashboardServlet')"><i class="fas fa-arrow-left"></i></a>
        <div class="title-content">Thông báo của học sinh</div>
        <div class="header-icons">
            <div class="user-dropdown">
                <button class="dropbtn" id="userButton">${not empty userName ? userName : 'Khách'} <span class="ml-1">▼</span></button>
                <div class="dropdown-content" id="userDropdown">
                    <a href="${pageContext.request.contextPath}/login" title="Đăng nhập">Đăng nhập</a>
                </div>
            </div>
        </div>
    </h1>

    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12 main">
                <div class="filter-bar">
                    <label for="filter">Lọc: </label>
                    <select id="filter">
                        <option value="all">Tất cả</option>
                        <option value="unread">Thư chưa đọc</option>
                        <option value="read">Thư đã đọc</option>
                        <option value="important">Thư quan trọng</option>
                        <option value="not-important">Thư không quan trọng</option>
                    </select>
                </div>

                <c:choose>
                    <c:when test="${empty notifications}">
                        <div class="error-message">Không có thông báo để hiển thị. Vui lòng thử lại sau.</div>
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
            </div>
        </div>
    </div>

    <footer class="site-footer">
        <div class="container-fluid bg-dark text-white py-0 px-sm-3 px-lg-5">
            <div class="row pt-5">
                <div class="col-lg-5 col-md-12 mb-5">
                    <a href="" class="text-decoration-none">
                        <div class="logo-container">
                            <img src="${pageContext.request.contextPath}/images/${centerInfo['Logo']}" alt="Logo Trung Tâm" class="logo-image" 
                                onerror="this.src='${pageContext.request.contextPath}/images/fallback.png';">
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
</body>
</html>