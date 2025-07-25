<%-- 
    Document   : teacherdashboard
    Created on : May 24, 2025, 11:31:10 PM
    Author     : DO NGOC ANH HE180661
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="entity.Shift" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="entity.Schedule" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>EDURA System</title>
        <!-- CSS FILES -->
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=League+Spartan:wght@100;300;400;600;700&display=swap" rel="stylesheet">
        <link href="css/bootstrap-icons.css" rel="stylesheet">
        <link href="css/owl.carousel.min.css" rel="stylesheet">
        <link href="css/owl.theme.default.min.css" rel="stylesheet">
        <link href="css/tooplate-gotto-job.css" rel="stylesheet">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <style>
            /* Ghi đè style cho btn-primary */
            .btn-primary {
                background-color: #FF6B6B !important;
                border-color: #FF6B6B !important;
                color: white !important;
                padding: 8px 28px;
                border-radius: 999px;
                font-weight: 600;
                transition: all 0.3s ease;
            }
            .btn-primary:hover {
                background-color: #0DCAF0 !important;
                border-color: #0DCAF0 !important;
                color: white !important;
            }
            /* Style cho nút Login */
            .navbar-nav {
                display: flex;
                flex-wrap: wrap;
                justify-content: space-between;
                width: 100%;
            }
            .btn-login:hover {
                background-color: #FFE66D;
                color: white !important;
            }
            /* Style cho dashboard-container */
            .dashboard-container {
                display: grid;
                grid-template-columns: repeat(2, 1fr);
                grid-gap: 30px;
                max-width: 800px;
                margin: 0 auto;
            }
            .dashboard-button {
                height: 100px;
                border: 2px solid black;
                border-radius: 15px;
                background-color: white;
                font-size: 18px;
                font-weight: 500;
                display: flex;
                align-items: center;
                justify-content: center;
                text-decoration: none;
                color: black;
                transition: background-color 0.3s ease;
            }
            .dashboard-button:hover {
                background-color: #FFC1C1;
            }
            @media (max-width: 768px) {
                .dashboard-container {
                    grid-template-columns: 1fr;
                }
            }
            /* Style cho footer */
            .container-fluid.bg-dark.text-white {
                background-color: #FFF1F1 !important;
                color: #333 !important;
            }
            .container-fluid.bg-dark.text-white h5.text-primary {
                color: #FF6B6B !important;
            }
            .container-fluid.bg-dark.text-white a.text-white {
                color: #333 !important;
            }
            .container-fluid.bg-dark.text-white a.text-white:hover {
                color: #FF6B6B !important;
            }
            .container-fluid.bg-dark.text-white .btn-outline-light {
                border-color: #333 !important;
                color: #333 !important;
            }
            .container-fluid.bg-dark.text-white .btn-outline-light:hover {
                background-color: #FF6B6B !important;
                color: #fff !important;
                border-color: #FF6B6B !important;
            }
            .container-fluid.bg-dark.text-white .form-control.border-light {
                background-color: #fff !important;
                border-color: #ccc !important;
                color: #333 !important;
            }
            .container-fluid.bg-dark.text-white .btn-primary {
                background-color: #FF6B6B !important;
                border-color: #FF6B6B !important;
            }
            .container-fluid.bg-dark.text-white .btn-primary:hover {
                background-color: #E55A5A !important;
                color: #fff !important;
            }
            /* Back to Top Button */
            .back-top-icon {
                position: fixed;
                bottom: 30px;
                right: 30px;
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
            @media (max-width: 576px) {
                .back-top-icon {
                    width: 35px;
                    height: 35px;
                    font-size: 18px;
                }
            }
            @media (max-width: 991px) {
                .container-fluid.bg-dark.text-white {
                    padding: 60px 45px !important; /* Padding footer */
                }
                .back-top-icon {
                    width: 40px;
                    height: 40px;
                    font-size: 20px;
                    bottom: 20px;
                    right: 20px;
                    top: auto !important; /* Vô hiệu hóa top */
                }
            }
            /* Phần header - top bar */
            .top-header {
                background-color: #FFF1F1; /* Hồng nhạt */
                color: #000 !important;
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                padding: 15px 0;
            }
            /* Màu đỏ cho biểu tượng */
            .top-header i.text-primary {
                color: #EC6F69 !important; /* Màu đỏ cam gần giống ảnh */
            }
            /* Logo EDURA: chỉ chữ E có màu */
            .top-header h1 span.text-primary {
                color: #EC6F69 !important;
                font-weight: 700;
                font-size: 60px;
            }
            /* Chữ còn lại của logo */
            .top-header h1 {
                font-size: 50px;
                color: #000;
                font-weight: 700;
            }
            /* Căn chỉnh icon và text sát nhau */
            .top-header .d-inline-flex i {
                margin-right: 12px;
                font-size: 50px;
            }
            .top-header small {
                font-size: 14px;
                color: #333;
            }
            .top-header h6 {
                font-weight: 600;
                font-size:20px;
                margin-bottom: 4px;
            }
            .logo-container {
                position: relative;
                width: 100px; /* Giữ kích thước cố định của div */
                height: 100px; /* Giữ tỷ lệ vuông */
                overflow: hidden; /* Ẩn phần vượt ra ngoài */
                display: flex;
                justify-content: center;
                align-items: center;
            }
            /* Logo image */
            .logo-image {
                max-width: 100%; /* Đảm bảo logo không vượt ra ngoài container */
                height: auto;
                transform: scale(2); /* Phóng to mặc định */
                transition: transform 0.3s ease; /* Hiệu ứng mượt khi phóng to */
            }
            /* Style cho navbar nền xám nhạt */
            .navbar {
                background-color: #f8f9fa !important; /* Xám nhạt */
                padding: 12px 24px;
                box-shadow: 0 2px 4px rgba(0,0,0,0.05); /* Nhẹ nhàng */
            }
            /* Style cho các mục menu */
            .navbar .nav-link {
                color: #555;
                font-weight: 500;
                transition: color 0.3s ease;
            }
            /* Mục đang active */
            .navbar .nav-link.active {
                color: #EC6F69 !important;
                font-weight: 700;
            }
            .table-time {
                width: 100%;
                margin: 20px auto; /* Add margin */
                border-collapse: collapse; /* Collapse borders */
            }
            .table-time th, .table-time td {
                padding: 10px; /* Add padding for better look */
                text-align: center;
            }
            .schedule-title-container {
                position: relative;
                height: 70px; /* Chiều cao container để dễ căn giữa */
                align-items: center;
                display: flex;
                justify-content: center;
            }
            .schedule-title {
                font-size: 40px;
                font-weight: 600;
            }
            /* Nhóm chứa các slogan */
            .slogan-group {
                display: flex;
                flex-direction: column;
                gap: 5px; /* Khoảng cách giữa các câu */
                align-items: flex-start; /* Căn trái để thẳng hàng với logo */
            }
            /* Định dạng từng slogan */
            .slogan {
                font-size: 1.2rem; /* Tăng kích thước chữ (trước đây là 0.9rem) */
                font-weight: 700; /* In đậm (trước đây là 500) */
                color: #333;
                margin: 0; /* Xóa margin mặc định */
                line-height: 1.4;
                transition: color 0.3s ease;
            }
            .sidebar {
                background-color: #fdeaf3;
                height: 100%;
                padding: 20px;
                text-align: center;
            }
            .avatar {
                font-size: 60px;
                margin-bottom: 10px;
            }
            .username {
                font-weight: bold;
                margin-bottom: 20px;
            }
            .sidebar a {
                display: block;
                color: #000;
                text-decoration: none;
                margin: 10px 0;
                font-size: 14px;
            }
            .main h1 {
                text-align: center;
                margin: 30px 0;
            }
            .sidebar {
                background-color: #FFF1F1;
                padding: 30px 20px;
                min-height: 70vh;
                text-align: center;
            }
            .sidebar .avatar {
                font-size: 80px;
                margin-bottom: 5px;
            }
            .sidebar .username {
                font-size: 18px;
                margin-bottom: 40px;
            }
            .sidebar a {
                display: block;
                font-size: 18px;
                padding: 15px 0;
                color: #000;
                text-decoration: none;
                text-align: left;
                padding-left: 40px;
                transition: background 0.3s;
            }
            .sidebar a i {
                margin-right: 10px;
            }
            .sidebar a:hover {
                background-color: #ffd8eb;
                border-radius: 10px;
            }
            .main .grid {
                display: grid;
                grid-template-columns: repeat(2, 1fr);
                gap: 30px;
                padding: 40px;
            }
            .main .card {
                background-color: #FFF1F1;
                padding: 40px;
                text-align: center;
                font-size: 20px;
                border-radius: 20px;
                font-weight: 500;
                transition: transform 0.2s;
            }
            .main .card i {
                font-size: 30px;
                display: block;
                margin-bottom: 10px;
            }
            .main .card:hover {
                transform: translateY(-5px);
                box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            }
            h1.dashboard-title {
                text-align: center;
                background-color: #f0f0f0;
                padding: 20px;
                font-size: 28px;
                margin-top: 0;
                font-weight: bold;
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
                border-radius: 50%; /* bo tròn ảnh */
                object-fit: cover;   /* đảm bảo ảnh không bị méo */
                border: 2px solid #ccc;
            }
            .table-time {
                width: 100%;
                margin: 20px auto;
                border-collapse: collapse;
            }
            .table-time th, .table-time td {
                padding: 12px;
                text-align: center;
                vertical-align: middle;
                border: 1px solid #ddd;
            }
            .table-time th {
                background-color: #f8f9fa;
                font-weight: 600;
            }
            .table-time td {
                min-width: 140px; /* đảm bảo rộng đều */
            }
            @media (max-width: 768px) {
                .table-time th, .table-time td {
                    padding: 8px;
                    font-size: 14px;
                }
            }
        </style>
    </head>
    <body id="top">
        <div class="container-fluid d-none d-lg-block top-header">
            <div class="row align-items-center py-0 px-xl-5">
                <!-- Logo -->
                <div class="col-lg-3 text-center">
                    <a href="Home.jsp" class="text-decoration-none">
                        <div class="logo-container">
                            <img src="${pageContext.request.contextPath}/LogoServlet" alt="Logo Trung Tâm" class="logo-image"
                                 onerror="this.src='${pageContext.request.contextPath}/images/fallback.png';" />
                        </div>
                    </a>
                </div>
                <!-- Địa chỉ -->
                <div class="col-lg-3 text-center">
                    <div class="d-inline-flex align-items-center">
                        <i class="fa fa-2x fa-map-marker-alt text-primary mr-3"></i>
                        <div class="text-left">
                            <h6 class="font-weight-semi-bold mb-1">Địa chỉ</h6>
                            <small>${address}</small>
                        </div>
                    </div>
                </div>
                <!-- Email -->
                <div class="col-lg-3 text-center">
                    <div class="d-inline-flex align-items-center">
                        <i class="fa fa-2x fa-envelope text-primary mr-3"></i>
                        <div class="text-left">
                            <h6 class="font-weight-semi-bold mb-1">Email</h6>
                            <small>${email}</small>
                        </div>
                    </div>
                </div>
                <!-- Số điện thoại -->
                <div class="col-lg-3 text-center">
                    <div class="d-inline-flex align-items-center">
                        <i class="fa fa-2x fa-phone text-primary mr-3"></i>
                        <div class="text-left">
                            <h6 class="font-weight-semi-bold mb-1">Điện thoại</h6>
                            <small>${phone}</small>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="container-fluid schedule-title-container navbar position-relative">
            <a href="teacherdashboard.jsp" class="btn btn-primary position-absolute" style="left: 20px; top: 50%; transform: translateY(-50%);">
                <i class="bi bi-arrow-left"></i>
            </a>
            <h3 class="schedule-title text-center w-100 m-0">Lịch Giảng Dạy</h3>
        </div>
        <div>
            <div class="row">
                <div class="col-md-3 sidebar">
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
                    <a href="#"><i class="fas fa-id-card"></i> Hồ sơ cá nhân</a>
                    <a href="#"><i class="fas fa-bell"></i> Thông báo</a>
                    <a href="#"><i class="fas fa-cog"></i> Cài đặt</a>
                    <a href="#"><i class="fas fa-question-circle"></i> Trợ giúp</a>
                </div>
                <!-- Main -->
                <div class="col-md-9 main">
                    <table border="1" class="table-time">
                        <tr>
                            <th>Ca / Thứ</th>
                            <th>Thứ 2</th>
                            <th>Thứ 3</th>
                            <th>Thứ 4</th>
                            <th>Thứ 5</th>
                            <th>Thứ 6</th>
                            <th>Thứ 7</th>
                            <th>Chủ nhật</th>
                        </tr>
                        
                        <c:forEach var="shift" items="${listShift}">
                            <tr>
                                <td>${shift.shiftNameProperty}</td>
                                
                                <c:forEach var="day" begin="1" end="7">
                                    <td>
                                        <c:forEach var="st" items="${templates}">
                                            <c:if test="${st.shift.id == shift.id && st.dayOfWeek == day}">
                                                Lớp: ${st.tutoringClass.className}<br/>
                                                GV: ${st.teacher.name}<br/>
                                                Phòng: ${st.room.name}
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                </c:forEach>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </div>
        <footer class="site-footer">
            <!-- Footer Start -->
            <div class="container-fluid bg-dark text-white py-0 px-sm-3 px-lg-5" style="margin-top: 0px;">
                <div class="row pt-5">
                    <div class="col-lg-5 col-md-12 mb-5">
                        <a href="" class="text-decoration-none">
                            <div class="logo-container">
                                <img src="${pageContext.request.contextPath}/LogoServlet" alt="Logo Trung Tâm" class="logo-image"
                                     onerror="this.src='${pageContext.request.contextPath}/images/fallback.png';" />
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
                                <h5 class="text-primary text-uppercase mb-4" style="letter-spacing: 5px;">Thông Tin Liên Hệ</h5>
                                <p><i class="fa fa-map-marker-alt mr-2"></i><small>${address}</small></p>
                                <p><i class="fa fa-phone-alt mr-2"></i><small>${phone}</small></p>
                                <p><i class="fa fa-envelope mr-2"></i><small>${email}</small></p>
                                <div class="d-flex justify-content-start mt-4">
                                    <a class="btn btn-outline-light btn-square mr-2" href="#"><i class="fab fa-twitter"></i></a>
                                    <a class="btn btn-outline-light btn-square mr-2" href="#"><i class="fab fa-facebook-f"></i></a>
                                    <a class="btn btn-outline-light btn-square mr-2" href="#"><i class="fab fa-linkedin-in"></i></a>
                                    <a class="btn btn-outline-light btn-square" href="#"><i class="fab fa-instagram"></i></a>
                                </div>
                            </div>
                            <div class="col-md-6 mb-5">
                                <h5 class="text-primary text-uppercase mb-4" style="letter-spacing: 5px;">Khám Phá EDURA</h5>
                                <div class="d-flex flex-column justify-content-start">
                                    <a class="text-white mb-2" href="${pageContext.request.contextPath}/home">
                                        <i class="fa fa-angle-right mr-2"></i>Trang Chủ
                                    </a>
                                    <a class="text-white mb-2" href="${pageContext.request.contextPath}/about">
                                        <i class="fa fa-angle-right mr-2"></i>Giới Thiệu
                                    </a>
                                    <a class="text-white mb-2" href="${pageContext.request.contextPath}/course">
                                        <i class="fa fa-angle-right mr-2"></i>Khoá Học
                                    </a>
                                    <a class="text-white mb-2" href="${pageContext.request.contextPath}/teacher">
                                        <i class="fa fa-angle-right mr-2"></i>Giáo Viên
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <a class="back-top-icon bi-arrow-up smoothscroll d-flex justify-content-center align-items-center" href="#top"></a> 
        </footer>
        <!-- JAVASCRIPT FILES -->
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>