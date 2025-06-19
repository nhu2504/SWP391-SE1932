<%-- 
    Document   : login_register
    Created on : May 24, 2025, 10:50:54 PM
    Author     : DO NGOC ANH HE180661
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%--<%@page import="dal.UserDAO"%>
<%@page import="dal.RoleDAO"%>
<%@page import="entity.User"%>
<%@page import="entity.Roles"%>
<%@page import="java.util.*"%>--%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <style>


            .wrapper {
                display: flex;
                background: #fff;
                padding: 30px;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
                border-radius: 10px;
                gap: 50px;
            }
            .form-container {
                width: 600px;
            }
            h2 {
                margin-bottom: 17px;
                color: #FF6B6B;
                font-size: 40px;
            }
            .form-group {
                margin-bottom: 15px;
            }
            label {
                font-weight: bold;
            }
            input[type="text"],
            input[type="email"],
            input[type="password"],
            input[type="date"] {
                width: 100%;
                padding: 10px;
                margin-top: 5px;
                border-radius: 6px;
                border: 1px solid #ccc;
            }
            .gender-group {
                margin-top: 5px;
            }
            .gender-group input {
                margin-right: 5px;
            }
            .form-footer {
                font-size: 13px;
                color: #666;
                margin-top: 10px;
            }
            .form-footer a {
                color: #007bff;
                text-decoration: none;
            }
            .checkbox-group {
                display: flex;
                align-items: center;
                gap: 5px;
            }
            .form-options {
                display: flex;
                justify-content: space-between;
                align-items: center;
                font-size: 14px;
            }
            .cl{
                width: 30%;
            }
            button {
                background-color: #FF6B6B;
                color: white;
                border: none;
                padding: 12px 20px;
                width: 100%;
                border-radius: 6px;
                font-size: 16px;
                cursor: pointer;
            }

            button:hover {
                background-color: #FF3366;
            }
            .error-message {
                color: red;
                font-weight: bold;
                margin-bottom: 0.5px;
                display: block;
            }
            .position-relative {
                position: relative;
            }
            .toggle-password {
                position: absolute;
                top: 50%;
                right: 5px;
                transform: translateY(-35%);
                cursor: pointer;
                color: #666;
                font-size: 18px;
            }
            .google-login {
                text-align: center;
                margin-top: 20px;
            }
            .google-btn {
                display: inline-flex;
                align-items: center;
                background-color: white;
                border: 1px solid #ddd;
                border-radius: 4px;
                padding: 10px 15px;
                text-decoration: none;
                color: #444;
                font-weight: bold;
                transition: 0.3s;
            }
            .google-btn img {
                width: 20px;
                margin-right: 10px;
            }
            .google-btn:hover {
                background-color: #f5f5f5;
            }
            .main-container {
                display: flex;
                justify-content: center;
                align-items: flex-start;
                padding: 50px;
            }
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
                padding: 10px 0;
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
                margin-bottom: 1px;
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

            /* Hover link */
            .navbar .nav-link:hover {
                color: #EC6F69 !important;
            }
            .navbar{
                margin-bottom: 30px;
            }
            body {
                font-family: 'Segoe UI', sans-serif;
                background-color: #f5f5f5;
            }

            .sidebar {
                background-color: #fdeaf3;
                height: 100%;
                padding: 20px;
                text-align: center;
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

            .grid {
                display: grid;
                grid-template-columns: repeat(2, 1fr);
                gap: 20px;
                padding: 0 20px 40px;
            }

            .card {
                background-color: #fef1f6;
                border-radius: 12px;
                padding: 20px;
                display: flex;
                align-items: center;
                gap: 15px;
                font-size: 18px;
                font-weight: 600;
                transition: 0.3s ease;
                cursor: pointer;
                border: 2px solid transparent;
            }

            .card:hover {
                background-color: #fcd9e6;
            }

            .card i {
                font-size: 28px;
            }

            .highlight {
                border-color: #a58cf5;
            }
            body {
                background-color: #f5f5f5;
                font-family: Arial, sans-serif;
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
            .card.h-100.text-center.shadow-sm {
                border: 3px solid #FF6B6B !important; /* Viền đỏ hồng */
                transition: transform 0.3s ease, box-shadow 0.3s ease; /* Hiệu ứng mượt */
                position: relative; /* Đảm bảo phóng to không ảnh hưởng bố cục */
            }

            .card.h-100.text-center.shadow-sm:hover {
                transform: scale(1.05); /* Phóng to 5% */
                box-shadow: 0 6px 16px rgba(255, 107, 107, 0.4); /* Bóng đậm hơn */
            }
            .schedule-title {
                font-size: 40px;
                font-weight: 500;
            }
            .schedule-title-container {
                position: relative;
                height: 70px; /* Chiều cao container để dễ căn giữa */
                align-items: center;
                display: flex;
                justify-content: center;
            }
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
            body{
                background-color: white;
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
            .top-header .row {
                display: flex;
                align-items: center;/*                  căn giữa theo chiều dọc */
                justify-content: space-between; /*                 phân bố đều  */
                flex-wrap: nowrap; /*                 không cho xuống hàng  */
            }

            .top-header .col-lg-3 {
                flex: 1;
                text-align: center;
            }

            .logo-container {
                max-width: 50%;
                text-align: center;

            }

            .logo-image {
                max-width: 70%;
                margin-left: 50px;
            }

            .site-footer{
                width: 100%;
                display: flex;
                align-items: center;/*                  căn giữa theo chiều dọc */
                justify-content: space-between; /*                 phân bố đều  */
                flex-wrap: nowrap; /*                 không cho xuống hàng  */
            }
            .logo-container {
                max-width: 50%;
                margin-right: auto; /* Đẩy logo sang trái */
            }

            .logo-image {
                width: 100%;
                height: auto;
                object-fit: contain;
            }

            .slogan-group p {
                margin-bottom: 5px;
                font-weight: 500;
            }

            footer .row > div {
                display: flex;
                flex-direction: column;
                justify-content: flex-start;
            }

            @media (max-width: 767px) {
                footer .row > div {
                    margin-bottom: 20px;
                    align-items: center;
                    text-align: center;
                }

                .logo-container {
                    margin: 0 auto;
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


        <span class="close-btn" onclick="closeLoginPopup()" 
              style="position: absolute; top: 10px; right: 20px; font-size: 30px; color: white; cursor: pointer;">&times;</span>
        <div class="main-container">
            <div class="wrapper">
                <!-- Đăng nhập -->
                <div class="form-container">
                    <h2>Đăng nhập</h2>
                    <form action="login" method="get">
                        <div class="form-group">
                            <label>Email *</label>
                            <input type="text" name="loginEmail"   />
                        </div>
                        <div class="form-group">
                            <label>Mật khẩu *</label>
                            <div class="position-relative">
                                <input type="password" id="Password" name="loginPassword" class="form-control pe-5" required />
                                <i class="fa fa-eye-slash toggle-password" id="togglePass" onclick="togglePassword('Password', 'togglePass')"></i>
                            </div>
                        </div>
                        <div class="form-group">
                            <a href="#">Quên mật khẩu?</a>
                        </div>
                        <br />
                        <% if (request.getAttribute("error") != null) { %>
                        <span class="error-message"><%= request.getAttribute("error") %></span>
                        <% } %>
                        <br />
                        <button type="submit" class="log">Đăng nhập</button>
                    </form>
                        <form action="${pageContext.request.contextPath}/googlelogin" method="GET">
                        <div class="google-login">
                            <p>Hoặc đăng nhập bằng</p>
                            <a href="https://accounts.google.com/o/oauth2/v2/auth?scope=email%20profile%20openid&redirect_uri=http://localhost:9999/WebApplication3/googlelogin&response_type=code&client_id=462397547099-cog4espvmnlb8qcg8s34ikuum3hufpur.apps.googleusercontent.com&prompt=consent" 
                               class="google-btn">
                                <img src="https://developers.google.com/identity/images/g-logo.png" alt="Google Logo" />
                                Đăng nhập với Google
                            </a>
                        </div>
                    </form>

                </div>
                <!-- Đăng ký học -->
                <div class="form-container">
                    <h2>Đăng ký học</h2>
                    <form action="register" method="post"  enctype="multipart/form-data">
                        <div class="form-group">
                            <input type="text" name="fullName" placeholder="Họ và Tên"  required/>
                        </div>
                        <div class="form-group">
                            <input type="text" name="email" placeholder="Email" required />
                        </div>
                        <div class="form-group">
                            <input type="text" name="phone" placeholder="Số điện thoại" required />
                        </div>
                        <div class="form-group">
                            <label>Giới tính</label><br/>
                            <div class="gender-group">
                                <input type="radio" name="gender" value="Nữ" checked /> Nữ
                                <input type="radio" name="gender" value="Nam" /> Nam
                                <input type="radio" name="gender" value="Khác" /> Khác
                            </div>
                        </div>
                        <div class="form-group">
                            <label>Ngày sinh</label>
                            <input type="date" name="dob" />
                        </div>
                        <div class="form-group schoolclass sc">
                            <input type="text" name="school" placeholder="Trường" required/>
                        </div>
                        <div class="form-group schoolclass sc">
                            <input type="text" name="schoolAddress" placeholder="Địa Chỉ" required/>
                        </div>
                        <div class="form-group schoolclass cl">
                            <input type="text" name="classAtSchool" placeholder="Lớp" required/>
                        </div>
                        <div class="form-group">
                            <input type="text" name="phonepar" placeholder="Số điện thoại người giám hộ"  />
                        </div>
                        <div class="form-group">
                            <input type="text" name="emailpar" placeholder="Email người giám hộ"  />
                        </div>
                        <div class="form-footer">
                            <input type="checkbox" name="verifi" required/>
                            Cam kết rằng những thông tin bạn cung cấp phía trên là chính xác, nếu có gì sai sót bạn phải chịu trách nhiệm trước
                            <a href="#">Pháp Luật</a> theo 
                            <a href="https://chinhphu.vn/?pageid=27160&docid=212362&classid=1">Thông tư 29</a> về việc học thêm.
                        </div>
                        <br />
                        <% if (request.getAttribute("error1") != null) { %>
                        <span class="error-message"><%= request.getAttribute("error1") %></span>
                        <% } %>
                        <br />
                        <button type="submit">Gửi yêu cầu đăng kí học</button>
                    </form>
                </div>
            </div>
        </div>
        <footer class="site-footer">
            <!-- Footer Start -->
            <div class="container-fluid bg-dark text-white py-0 px-sm-3 px-lg-5" style="margin-top: 0px;">
                <div class="row pt-5">
                    <div class="col-lg-5">
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
                    <div class="col-lg-7">
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
        <script>
            function togglePassword(inputId, iconId) {
                let input = document.getElementById(inputId);
                let icon = document.getElementById(iconId);
                if (input.type === "password") {
                    input.type = "text";
                    icon.classList.remove("bi-eye-slash");
                    icon.classList.add("bi-eye");
                } else {
                    input.type = "password";
                    icon.classList.remove("bi-eye");
                    icon.classList.add("bi-eye-slash");
                }
            }
        </script>


    </body>
</html>