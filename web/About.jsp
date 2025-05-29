
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!-- Văn Thị Như - HE181329 -->
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>EDURA</title>

        <!-- CSS FILES -->
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=League+Spartan:wght@100;300;400;600;700&display=swap" rel="stylesheet">
        <link href="css/bootstrap-icons.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.carousel.min.css" rel="stylesheet">

        <link href="css/tooplate-gotto-job.css" rel="stylesheet">
        <style>
            /* Ghi đè style cho btn-primary */
            .btn-primary {
                background-color: #FF6B6B !important; /* Màu hồng đỏ */
                border-color: #FF6B6B !important; /* Viền cùng màu */
                color: white !important; /* Văn bản màu trắng */
                padding: 8px 28px; /* Kích thước giống nút Login */
                border-radius: 999px; /* Bo góc giống nút Login */
                font-weight: 600; /* Độ đậm giống nút Login */
                transition: all 0.3s ease; /* Hiệu ứng mượt */
            }

            .btn-primary:hover {
                background-color: #0DCAF0 !important; /* Màu xanh dương khi hover */
                border-color: #0DCAF0 !important; /* Viền cùng màu */
                color: white !important; /* Giữ màu trắng */
            }

            /* Style cho about-section */
            .about-section .btn-primary {
                display: inline-block;
                text-align: center;
            }

            footer.text-primary {
                color: #FF6B6B !important;
            }

            /* Style cho header */
            .top-header {
                background-color: #FFF1F1;
            }

            /* Style cho nút Login */
            .btn-login {
                background-color: #FF6B6B;
                color: white !important;
                padding: 8px 28px;
                border-radius: 999px;
                border: none;
                font-weight: 600;
                display: inline-block;
                white-space: nowrap;
                text-align: center;
                transition: all 0.3s ease;
            }

            .btn-login:hover {
                background-color: #FFE66D;
                color: white !important;
            }

            /* Style cho course-panel và column */
            .course-panel {
                display: none !important;
            }

            .course-panel.active {
                display: block !important;
            }

            .course-panel .column {
                display: none !important;
                flex: 1;
                padding: 10px;
                border: 1px solid #ddd;
                border-radius: 4px;
                overflow-y: auto;
            }

            .course-panel .column.active {
                display: flex !important;
                flex-direction: column;
            }

            /* Style cho navbar-vertical */
            #navbar-vertical {
                display: none !important;
                position: absolute;
                top: 100%;
                left: 0;
                width: 600px;
                z-index: 1000;
            }

            #navbar-vertical.show {
                display: block !important;
            }

            #navbar-vertical .nav-link {
                padding: 10px 15px;
                color: #333;
            }

            #navbar-vertical .nav-link:hover {
                background-color: #e9ecef;
            }

            /* Style cho navbar-nav và nav-link */
            .navbar-nav {
                display: flex;
                flex-wrap: wrap;
                justify-content: space-between;
                width: 100%;
            }

            .nav-item {
                flex: 1;
                min-width: 0;
            }

            .nav-link {
                cursor: pointer;
                padding: 8px 15px;
                text-align: center;
                white-space: nowrap;
                font-size: 16px;
                color: #333;
            }

            .nav-link.active {
                color: #FF6B6B !important;
                font-weight: bold;
            }

            /* Vô hiệu hóa quy tắc ghi đè từ a */
            a, a:hover, a:active, a:focus, a.tab-link, a.tab-link:hover, a.tab-link.active {
                color: inherit !important;
                text-decoration: none !important;
            }

            /* Style cho tab-link */
            .course-panel .panel-header a.tab-link {
                padding: 5px 15px !important;
                cursor: pointer;
                color: #FF6B6B !important; /* Màu hồng đỏ mặc định */
                text-decoration: none !important;
                position: relative !important;
                transition: all 0.3s ease !important;
                display: inline-block !important;
                font-size: 14px !important;
                line-height: 1.5 !important;
                z-index: 10 !important;
            }

            /* Khi di chuột vào tab */
            .course-panel .panel-header a.tab-link:hover {
                color: #0DCAF0 !important; /* Màu xanh dương khi hover */
                text-decoration: underline !important;
            }

            /* Khi tab được chọn (click/active) */
            .course-panel .panel-header a.tab-link.active {
                color: #0DCAF0 !important; /* Màu xanh dương khi click */
                font-weight: 700 !important; /* Chữ in đậm */
                text-decoration: none !important;
            }

            /* Hình tam giác dưới tab active */
            .course-panel .panel-header a.tab-link.active::after {
                content: '' !important;
                position: absolute !important;
                bottom: -6px !important;
                left: 50% !important;
                transform: translateX(-50%) !important;
                width: 0 !important;
                height: 0 !important;
                border-left: 5px solid transparent !important;
                border-right: 5px solid transparent !important;
                border-bottom: 5px solid #0DCAF0 !important;
                z-index: 11 !important;
            }

            /* Đảm bảo panel-header không che khuất */
            .course-panel .panel-header {
                overflow: visible !important;
                position: relative !important;
                z-index: 9 !important;
            }
            /* Style cho panel-content và content-list */
            .panel-content {
                display: flex;
                justify-content: space-between;
            }

            .content-list a {
                display: block;
                padding: 5px 0;
                color: #333;
                text-decoration: none;
            }

            .content-list a:hover {
                color: #007bff;
                text-decoration: underline;
            }

            /* Style cho navbar */
            .navbar {
                padding: 1rem 0;
                background-color: #f8f9fa;
                border-bottom: 1px solid #dee2e6;
            }

            .navbar .container-fluid {
                display: flex;
                align-items: center;
                padding-left: 15px;
                padding-right: 15px;
            }

            .navbar-nav .nav-item {
                flex-grow: 1;
                text-align: center;
            }

            .nav-item.d-flex {
                gap: 10px;
                justify-content: center;
                align-items: center;
            }

            /* Style cho custom-btn */
            .custom-btn {
                padding: 8px 20px;
                border-radius: 20px;
                font-size: 16px;
                transition: all 0.3s ease;
            }

            .custom-btn:hover {
                background-color: #FFE66D;
                color: white !important;
            }

            .custom-btn.btn-login {
                background-color: #FF6B6B;
                color: white !important;
            }

            /* Style cho register-link */
            .register-link {
                font-size: 16px;
                padding: 8px 15px;
                color: #333;
                text-decoration: none;
            }

            .register-link:hover {
                color: #007bff;
                text-decoration: underline;
            }

            /* Style cho carousel-indicators */
            .carousel-indicators {
                position: relative !important;
                bottom: 0 !important;
                margin-top: 1rem !important;
                z-index: 2 !important;
            }

            .carousel-indicators button {
                width: 12px !important;
                height: 12px !important;
                border-radius: 50% !important;
                background-color: #bbb !important;
                border: none !important;
                margin: 0 5px !important;
                padding: 0 !important;
                cursor: pointer !important;
                transition: background-color 0.3s ease !important;
                box-sizing: content-box !important;
            }

            .carousel-indicators .active {
                background-color: #FF6B6B !important;
                opacity: 1 !important;
            }

            .carousel-indicators button:hover {
                background-color: #FF8787 !important;
            }

            .carousel-indicators button:active,
            .carousel-indicators button:focus {
                outline: none !important;
                box-shadow: none !important;
            }

            /* Style cho border-custom */
            .border-custom {
                border: 1px solid #FF6B6B !important;
                box-shadow: 0 4px 8px rgba(255, 107, 107, 0.2) !important;
                border-radius: 8px !important;
                padding: 15px !important;
                transition: box-shadow 0.3s ease !important;
            }

            .border-custom:hover {
                box-shadow: 0 6px 12px rgba(255, 107, 107, 0.3) !important;
            }
            .square-image-container {
                position: relative;
                width: 100%;
                padding-bottom: 100%; /* Tạo tỉ lệ 1:1 (hình vuông) */
                overflow: hidden;
            }

            .square-image-container img {
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                object-fit: cover; /* Đảm bảo ảnh lấp đầy mà không méo */
                border-radius: 8px; /* Bo góc nhẹ */
                transition: transform 0.3s ease, box-shadow 0.3s ease;
                border: 3px solid #f8f9fa; /* Viền nhẹ */
            }

            .square-image-container img:hover {
                transform: scale(1.05);
                box-shadow: 0 4px 12px rgba(255, 107, 107, 0.3); /* Bóng hồng đỏ */
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
            #gioithieu {
                background-color: #FFF1F1 !important; /* Màu nền hồng nhạt */
            }
            /* Footer Styling */
            .container-fluid.bg-dark.text-white {
                background-color: #FFF1F1 !important; /* Nền hồng nhạt */
                color: #333 !important; /* Chữ tối */
            }

            .container-fluid.bg-dark.text-white h5.text-primary {
                color: #FF6B6B !important; /* Tiêu đề xanh dương */
            }

            .container-fluid.bg-dark.text-white a.text-white {
                color: #333 !important; /* Link tối */
            }

            .container-fluid.bg-dark.text-white a.text-white:hover {
                color: #FF6B6B !important; /* Hover hồng đỏ */
            }

            .container-fluid.bg-dark.text-white .btn-outline-light {
                border-color: #333 !important; /* Viền icon mạng xã hội */
                color: #333 !important;
            }

            .container-fluid.bg-dark.text-white .btn-outline-light:hover {
                background-color: #FF6B6B !important; /* Nền hồng đỏ khi hover */
                color: #fff !important;
                border-color: #FF6B6B !important;
            }

            .container-fluid.bg-dark.text-white .form-control.border-light {
                background-color: #fff !important; /* Input trắng */
                border-color: #ccc !important;
                color: #333 !important;
            }

            .container-fluid.bg-dark.text-white .btn-primary {
                background-color: #FF6B6B !important; /* Button hồng đỏ */
                border-color: #FF6B6B !important;
            }

            .container-fluid.bg-dark.text-white .btn-primary:hover {
                background-color: #E55A5A !important; /* Button hover sáng hơn */
                color: #fff !important;
            }

            /* Back to Top Button */
            .back-top-icon {
                position: fixed;
                bottom: 30px;
                right: 30px;
                top: auto !important; /* Vô hiệu hóa top nếu bị ghi đè */
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
            /* Đảm bảo text-primary ở logo và About là đỏ hồng */
            .text-primary,
            h1 .text-primary,
            .about-section h5.text-primary {
                color: #FF6B6B !important;
            }
            .cat-item {
                transition: transform 0.3s ease;
                border-radius: 15px;
                overflow: hidden;
                height: 200px; /* Đặt chiều cao cố định cho khối */
            }
            .cat-item:hover {
                transform: scale(1.05);
            }
            .cat-item img {
                width: 100%;
                height: 100%; /* Đảm bảo ảnh lấp đầy khung */
                object-fit: cover; /* Cắt ảnh để vừa khung mà không méo */
                transition: transform 0.3s ease;
            }
            .cat-item:hover img {
                transform: scale(1.1);
            }
            .cat-overlay {
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background: rgba(0, 0, 0, 0.5);
                display: flex;
                flex-direction: column;
                justify-content: center;
                align-items: center;
                opacity: 1;
                transition: opacity 0.3s ease;
            }
            .cat-overlay h4 {
                font-size: 24px;
                font-weight: 600;
                margin-bottom: 10px;
            }
            .cat-overlay span {
                font-size: 18px;
                font-weight: 400;
            }
            .col-lg-3 {
                padding: 10px;
            }
            .pagination {
                justify-content: center;
                margin-top: 20px;
                display: flex;
            }
            .page-item {
                list-style: none;
            }
            .page-item .page-link {
                border-radius: 50%;
                width: 40px;
                height: 40px;
                line-height: 40px;
                text-align: center;
                margin: 0 5px;
                border: none;
                background-color: #e9ecef;
                color: #333;
                transition: background-color 0.3s ease;
                display: block;
                text-decoration: none;
            }
            .page-item.active .page-link {
                background-color: #FF6B6B;
                color: white;
            }
            .page-item .page-link:hover {
                background-color: #FF6B6B;
                color: white;
            }
            .hidden {
                display: none !important;
            }
            /* Container của logo */
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
            .course-img {
                width: 100%;
                height: 200px; /* Chiều cao cố định */
                object-fit: cover; /* Đảm bảo ảnh lấp đầy khung */
            }
            .course-title {
                font-weight: 700; /* Chữ đậm mặc định */
                text-decoration: none !important; /* Không gạch chân mặc định */
                color: inherit !important; /* Giữ màu mặc định của h5 */
                transition: color 0.3s ease, font-weight 0.3s ease, text-decoration 0.3s ease; /* Hiệu ứng mượt */
            }
            .course-title:hover {
                text-decoration: underline !important; /* Gạch chân khi hover */
                color: #FF6B6B !important; /* Màu đỏ cam */
                font-weight: 900; /* Đậm hơn khi hover */
            }
            .course-content {
                background-color: #FFF1F1 !important; /* Màu nền hồng nhạt */
                padding: 1.5rem; /* Thay cho p-4 để đồng nhất */
            }
            .rating {
                display: flex; /* Flex cho h6 */
                align-items: center; /* Căn giữa nội dung trong h6 theo dọc */
            }
            .rating .fa-star {
                line-height: 1; /* Đồng bộ chiều cao dòng */
                vertical-align: middle; /* Căn giữa dọc */
                margin-right: 0.25rem;
            }
            .rating small {
                line-height: 1; /* Đồng bộ chiều cao dòng cho small */
            }
            /* Hiển thị điều hướng */
            .owl-carousel.team-carousel .owl-nav {
                display: block !important;
                visibility: visible !important;
                opacity: 1 !important;
            }

            /* Nút điều hướng chính */
            .owl-carousel.team-carousel .owl-nav button.owl-prev,
            .owl-carousel.team-carousel .owl-nav button.owl-next {
                position: absolute;
                top: 50%;
                transform: translateY(-50%);
                width: 50px;
                height: 50px;
                background: #FF6B6B;
                color: white;
                font-size: 30px;
                text-align: center;
                line-height: 50px;
                border: none;
                border-radius: 0;
                padding: 0;
                margin: 0;
                opacity: 0.9;
                z-index: 10;
                cursor: pointer;
                transition: all 0.3s ease;
            }

            /* Vị trí trái và phải */
            .owl-carousel.team-carousel .owl-nav button.owl-prev {
                left: -50px;
            }
            .owl-carousel.team-carousel .owl-nav button.owl-next {
                right: -50px;
            }

            /* Hover đẹp */
            .owl-carousel.team-carousel .owl-nav button.owl-prev:hover,
            .owl-carousel.team-carousel .owl-nav button.owl-next:hover {
                background: #FF8787;
                opacity: 1;
                transform: translateY(-50%) scale(1.1);
                box-shadow: 0 5px 15px rgba(255, 107, 107, 0.5);
            }

            /* Dùng dấu mũi tên đơn giản */
            .owl-carousel.team-carousel .owl-nav button.owl-prev::before,
            .owl-carousel.team-carousel .owl-nav button.owl-next::before {
                content: '«'; /* mũi tên trái/phải đẹp */
                font-family: Arial, sans-serif;
                font-size: 28px;
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
            }

            /* Mũi tên phải */
            .owl-carousel.team-carousel .owl-nav button.owl-next::before {
                content: '»';
            }

            /* Loại bỏ hoàn toàn span & giả lập span */
            .owl-carousel.team-carousel .owl-nav button span,
            .owl-carousel.team-carousel .owl-nav button::after {
                display: none !important;
                content: none !important;
            }
            /* Style mặc định cho icon */
            .team-item .d-flex a i {
                color: inherit; /* Giữ màu mặc định ban đầu */
                transition: color 0.3s ease; /* Thêm hiệu ứng chuyển đổi mượt mà */
            }

            /* Thay đổi màu icon khi hover */
            .team-item .d-flex a:hover i {
                color: #FF6B6B !important; /* Đổi thành màu mong muốn */
            }
            /* Đảm bảo logo-container căn trái */
            .col-lg-5 .logo-container {
                width: 150px;
                height: 150px;
                overflow: hidden;
                display: flex;

                align-items: center;
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
            /* Responsive */
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


            @media (max-width: 576px) {
                .back-top-icon {
                    width: 35px;
                    height: 35px;
                    font-size: 18px;
                }
            }

            /* Responsive */
            @media (max-width: 992px) {
                .navbar-nav {
                    flex-direction: column;
                    text-align: center;
                }
                .navbar-nav .nav-item {
                    margin-bottom: 15px;
                    flex-grow: 0;
                }
                .nav-item.d-flex {
                    flex-direction: row;
                    justify-content: center;
                    gap: 10px;
                }
                .custom-btn {
                    margin-top: 0;
                }
                #navbar-vertical {
                    width: 100%;
                }
                #navbar-vertical .d-flex {
                    flex-direction: column;
                }
                #navbar-vertical .navbar-nav {
                    width: 100%;
                }
                .about-section h5 {
                    font-size: clamp(0.8rem, 2vw, 1.1rem);
                }
                .about-section h3 {
                    font-size: clamp(1.1rem, 2.5vw, 1.75rem);
                }
                .about-section .col-lg-7 {
                    overflow-x: hidden;
                    max-width: 100%;
                }
                .cat-item {
                    height: 150px;
                }
                .page-item .page-link {
                    width: 35px;
                    height: 35px;
                    line-height: 35px;
                }
            }
        </style>
    </head>
    <body id="top">
        <div class="container-fluid d-none d-lg-block top-header">
            <div class="row align-items-center py-0 px-xl-5">
                <!-- Logo -->
                <div class="col-lg-3 text-center">
                    <a href="" class="text-decoration-none">
                        <div class="logo-container">
                            <img src="${pageContext.request.contextPath}/LogoServlet" alt="Logo Trung Tâm" class="logo-image"
                                 onerror="this.src='${pageContext.request.contextPath}/images/fallback.png';" />

                        </div>
                    </a>
                </div>
                <!-- Địa chỉ -->
                <div class="col-lg-3 text-center">
                    <div class="d-inline-flex align-items-center">
                        <i class="fa fa-3x fa-map-marker-alt text-primary mr-3"></i>
                        <div class="text-left">
                            <h6 class="font-weight-semi-bold mb-1">Địa chỉ</h6>
                            <small>${address}</small>
                        </div>
                    </div>
                </div>
                <!-- Email -->
                <div class="col-lg-3 text-center">
                    <div class="d-inline-flex align-items-center">
                        <i class="fa fa-3x fa-envelope text-primary mr-3"></i>
                        <div class="text-left">
                            <h6 class="font-weight-semi-bold mb-1">Email</h6>
                            <small>${email}</small>
                        </div>
                    </div>
                </div>
                <!-- Số điện thoại -->
                <div class="col-lg-3 text-center">
                    <div class="d-inline-flex align-items-center">
                        <i class="fa fa-3x fa-phone text-primary mr-3"></i>
                        <div class="text-left">
                            <h6 class="font-weight-semi-bold mb-1">Điện thoại</h6>
                            <small>${phone}</small>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <nav class="navbar navbar-expand-lg">
            <div class="container-fluid">
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav d-flex w-100 align-items-center">
                        <li class="nav-item flex-grow-1">
                            <button id="subjects-btn" class="nav-link d-flex align-items-center justify-content-center custom-btn btn-login"
                                    aria-expanded="false" aria-controls="navbar-vertical"
                                    style="padding: 8px 15px; border-radius: 5px; cursor: pointer; border: none;">
                                <i class="fa fa-book-open mr-2"></i>Môn học
                            </button>
                        </li>
                        <li class="nav-item flex-grow-1">
                            <a class="nav-link" href="${pageContext.request.contextPath}/home">Trang chủ</a>
                        </li>
                        <li class="nav-item flex-grow-1">
                            <a class="nav-link  active" href="${pageContext.request.contextPath}/about">Giới thiệu</a>
                        </li>
                        <li class="nav-item flex-grow-1">
                            <a class="nav-link" href="${pageContext.request.contextPath}/course">Khoá học</a>

                        </li>
                        <li class="nav-item flex-grow-1">
                            <a class="nav-link" href="${pageContext.request.contextPath}/teacher">Giáo viên</a>
                        </li>
                        <li class="nav-item flex-grow-1 d-flex justify-content-center align-items-center">                            
                            <a class="nav-link custom-btn btn-login" href="login_register.jsp">Đăng nhập/Đăng kí</a>
                        </li>
                    </ul>
                </div>
                <nav class="collapse position-absolute navbar navbar-vertical navbar-light align-items-start p-0 border border-top-0 border-bottom-0 bg-light"
                     id="navbar-vertical" style="z-index: 9; width: 600px; top: 100%; left: 0;">
                    <div class="d-flex w-100">
                        <div class="navbar-nav" style="width: 250px; display: flex; flex-wrap: wrap;">
                            <div style="width: 50%; padding-right: 5px;">
                                <c:choose>
                                    <c:when test="${not empty subjects}">
                                        <c:forEach var="subject" items="${subjects}" varStatus="loop" begin="0" end="3">
                                            <a href="#" class="nav-link" data-panel="panel-${loop.index + 1}">${subject.name}</a>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <p>Không có môn học nào.</p>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div style="width: 50%; padding-left: 5px;">
                                <c:choose>
                                    <c:when test="${not empty subjects}">
                                        <c:forEach var="subject" items="${subjects}" varStatus="loop" begin="4" end="7">
                                            <a href="#" class="nav-link" data-panel="panel-${loop.index + 1}">${subject.name}</a>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <p>Không có môn học nào.</p>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        <div class="course-panel-container pl-3" style="flex: 1;">
                            <c:choose>
                                <c:when test="${not empty subjects}">
                                    <c:forEach var="subject" items="${subjects}" varStatus="loop">
                                        <div class="course-panel ${loop.index == 0 ? 'active' : ''}" id="panel-${loop.index + 1}">
                                            <div class="panel-header">
                                                <a class="tab-link active" data-tab="documents">Tài liệu</a>
                                                <c:if test="${not empty classes[subject.id]}">
                                                    <a class="tab-link" data-tab="courses">Khóa học</a>
                                                </c:if>
                                            </div>
                                            <div class="panel-content d-flex">
                                                <div class="column documents active" style="flex: 1; margin-right: 10px;">
                                                    <div class="content-list">
                                                        <c:choose>
                                                            <c:when test="${not empty documents[subject.id]}">
                                                                <c:forEach var="doc" items="${documents[subject.id]}">
                                                                    <a href="${pageContext.request.contextPath}/login_register.jsp?redirect=document&docId=${doc.id}">${doc.name}</a>
                                                                </c:forEach>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <p>Không có tài liệu.</p>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </div>
                                                </div>
                                                <c:if test="${not empty classes[subject.id]}">
                                                    <div class="column courses" style="flex: 1;">
                                                        <div class="content-list">
                                                            <c:choose>
                                                                <c:when test="${not empty classes[subject.id]}">
                                                                    <c:forEach var="cls" items="${classes[subject.id]}">
                                                                        <a href="${pageContext.request.contextPath}/login_register.jsp?redirect=course&courseId=${cls.id}">${cls.name}</a>
                                                                    </c:forEach>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <p>Không có khóa học.</p>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </div>
                                                    </div>
                                                </c:if>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <p>Không có nội dung nào để hiển thị.</p>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </nav>
            </div>
        </nav>

        <!-- Carousel Start -->
        <div class="container-fluid p-0 pb-5 mb-5">
            <div id="header-carousel" class="carousel slide carousel-fade" data-ride="carousel">

                <div class="carousel-inner">
                    <div class="carousel-item active" style="min-height: 300px;">
                        <img class="position-relative w-100" src="images/carousel-1.jpg" style="min-height: 300px; object-fit: cover;">

                    </div>                                       
                </div>
            </div>
        </div>
        <!-- Carousel End -->
        <main>
            <!-- About start -->
            <section class="about-section">
                <div class="container">
                    <div class="container-fluid py-3">
                        <div class="container py-0">
                            <div class="row align-items-center">
                                <div class="col-lg-5">
                                    <div class="square-image-container">
                                        <img class="img-fluid rounded mb-4 mb-lg-0" src="images/about-1.jpg" alt="">
                                    </div>
                                </div>
                                <div class="col-lg-7" style="text-align: justify;">
                                    <div class="text-left mb-4">
                                        <h5 class="text-primary text-uppercase mb-3" style="letter-spacing: 5px; text-align: justify; white-space: nowrap; font-size: clamp(0.9rem, 2.5vw, 1.25rem);">
                                            Chất Lượng Giáo Dục - Giá Trị Bền Vững
                                        </h5>
                                        <h3 style="white-space: nowrap; text-align: left; font-size: clamp(1.25rem, 3vw, 2rem); overflow-wrap: anywhere;">
                                            Khơi Nguồn Tri Thức – Dẫn Lối Thành Công
                                        </h3>
                                    </div>
                                    <p>Edura cam kết mang đến chất lượng giáo dục vượt trội với đội ngũ giáo viên tận tâm, phương pháp giảng dạy hiện đại và chương trình học bám sát thực tiễn.</p>
                                    <p>Edura không chỉ chú ý đến việc truyền đạt kiến thức, mà còn khơi dậy niềm đam mê học tập, phát triển tư duy và định hướng thành công cho mỗi học sinh.</p>
                                    <p>Chất lượng hôm nay là giá trị vững chắc cho ngày mai – đó là kim chỉ nam trong mọi hoạt động của Edura.</p>
                                    <a href="login_register.jsp" class="btn btn-primary py-md-2 px-md-4 font-weight-semi-bold mt-2">Xem thêm</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <!-- About end -->

            <!-- Ưu thế start -->
            <section id="gioithieu" class="py-5 bg-light">
                <div class="container">
                    <h3 class="text-center mb-4">Ưu Thế Vượt Trội Của EDURA</h3>
                    <div class="row g-4">
                        <div class="col-md-6 col-lg-3">
                            <div class="card h-100 text-center shadow-sm">
                                <div class="card-body">
                                    <div class="mb-3">
                                        <i class="bi bi-people-fill text-primary" style="font-size: 3rem;"></i>
                                    </div>
                                    <h5 class="card-title">Giáo viên chất lượng</h5>
                                    <p class="card-text">100% giáo viên có bằng cấp & kinh nghiệm giảng dạy lâu năm.</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6 col-lg-3">
                            <div class="card h-100 text-center shadow-sm">
                                <div class="card-body">
                                    <div class="mb-3">
                                        <i class="bi bi-book text-success" style="font-size: 3rem;"></i>
                                    </div>
                                    <h5 class="card-title">Giáo trình chuẩn</h5>
                                    <p class="card-text">Giáo trình được biên soạn theo chuẩn khung của Bộ Giáo dục.</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6 col-lg-3">
                            <div class="card h-100 text-center shadow-sm">
                                <div class="card-body">
                                    <div class="mb-3">
                                        <i class="bi bi-bank text-warning" style="font-size: 3rem;"></i>
                                    </div>
                                    <h5 class="card-title">Liên kết nhà trường</h5>
                                    <p class="card-text">Kết hợp với giáo viên chủ nhiệm theo dõi quá trình học tập.</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6 col-lg-3">
                            <div class="card h-100 text-center shadow-sm">
                                <div class="card-body">
                                    <div class="mb-3">
                                        <i class="bi bi-bar-chart-line text-danger" style="font-size: 3rem;"></i>
                                    </div>
                                    <h5 class="card-title">Tiến bộ vượt bậc</h5>
                                    <p class="card-text">98% học sinh cải thiện kết quả chỉ sau 3 tháng học.</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <!-- Ưu thế end -->

            <!-- PHẢN HỒI TỪ HỌC VIÊN -->
            <div class="container py-5">
                <div class="row">
                    <div class="col-md-5 py-3">
                        <div class="d-flex justify-content-center">
                            <h3 class="mb-4">Lắng Nghe Và Chia Sẻ 💬</h3>
                        </div>

                        <div id="testimonialWrapper" class="border-custom"> <!-- Thêm class "border-custom" -->
                            <div id="testimonialCarousel" class="carousel slide" data-bs-ride="carousel">
                                <div class="carousel-inner text-start">

                                    <!-- Slide 1 -->
                                    <div class="carousel-item active">
                                        <div class="card p-4"> <!-- Loại bỏ "shadow-custom" nếu không muốn viền từng khung -->
                                            <div class="d-flex align-items-center mb-3">
                                                <img src="images/hocvien-1.jpg" alt="Học viên" class="rounded-circle me-3" style="width: 120px; height: 120px; object-fit: cover;">
                                                <div>
                                                    <h6 class="text-primary mb-1">Phan Đức Kiên</h6>
                                                    <p class="mb-0 small">Tài khoản: kien****@gmail.com</p>
                                                    <p class="mb-0 small">🏆 90,15 điểm - Thi Đánh giá tư duy 2024</p>
                                                    <p class="mb-0 small">🥇 Học sinh giỏi cấp tỉnh Toán</p>
                                                    <p class="mb-0 small">🎓 Được tuyển thẳng vào Đại học Bách Khoa Hà Nội</p>
                                                </div>
                                            </div>
                                            <p class="fst-italic text-muted">
                                                <i class="bi bi-quote fs-4 me-1"></i>Em cảm ơn Edura và các thầy cô rất nhiều ạ.
                                            </p>
                                        </div>
                                    </div>

                                    <!-- Slide 2 -->
                                    <div class="carousel-item">
                                        <div class="card p-4"> <!-- Loại bỏ "shadow-custom" -->
                                            <div class="d-flex align-items-center mb-3">
                                                <img src="images/hocvien-2.jpg" alt="Học viên" class="rounded-circle me-3" style="width: 120px; height: 120px; object-fit: cover;">
                                                <div>
                                                    <h6 class="text-primary mb-1">Nguyễn Thị Hà My</h6>
                                                    <p class="mb-0 small">Tài khoản: hamy****@gmail.com</p>
                                                    <p class="mb-0 small">🏆 Đạt học bổng Edura Excellence 2024</p>
                                                    <p class="mb-0 small">🌟 Đạt thủ khoa kỳ thi ĐGNL toàn quốc 2024</p>
                                                    <p class="mb-0 small">📚 Đạt IELTS 7.5 khi đang học lớp 11</p>
                                                    <p class="mb-0 small">🎓 Tuyển thẳng vào Đại học Y Hà Nội</p>
                                                </div>
                                            </div>
                                            <p class="fst-italic text-muted">
                                                <i class="bi bi-quote fs-4 me-1"></i>Không khí lớp học thoải mái, thầy cô nhiệt tình, bạn bè thân thiện.
                                            </p>
                                        </div>
                                    </div>

                                    <!-- Slide 3 -->
                                    <div class="carousel-item">
                                        <div class="card p-4"> <!-- Loại bỏ "shadow-custom" -->
                                            <div class="d-flex align-items-center mb-3">
                                                <img src="images/hocvien-3.jpg" alt="Học viên" class="rounded-circle me-3" style="width: 120px; height: 120px; object-fit: cover;">
                                                <div>
                                                    <h6 class="text-primary mb-1">Trần Minh Quân</h6>
                                                    <p class="mb-0 small">Tài khoản: quan****@gmail.com</p>
                                                    <p class="mb-0 small">🥇 Giải Nhì HSG Quốc gia Vật lý</p>
                                                    <p class="mb-0 small">💻 9.5 điểm thi Tin học quốc tế MOS</p>
                                                    <p class="mb-0 small">🎓 Được học bổng toàn phần tại FPT University</p>
                                                </div>
                                            </div>
                                            <p class="fst-italic text-muted">
                                                <i class="bi bi-quote fs-4 me-1"></i>Em rất biết ơn vì những kiến thức bổ ích từ Edura.
                                            </p>
                                        </div>
                                    </div>

                                    <!-- Slide 4 -->
                                    <div class="carousel-item">
                                        <div class="card p-4"> <!-- Loại bỏ "shadow-custom" -->
                                            <div class="d-flex align-items-center mb-3">
                                                <img src="images/hocvien-4.jpg" alt="Học viên" class="rounded-circle me-3" style="width: 120px; height: 120px; object-fit: cover;">
                                                <div>
                                                    <h6 class="text-primary mb-1">Nguyễn Thị Thuỳ Linh</h6>
                                                    <p class="mb-0 small">Tài khoản: linh****@gmail.com</p>
                                                    <p class="mb-0 small">🥇 Học bổng toàn phần Edura 2024</p>
                                                    <p class="mb-0 small">📘 GPA 9.5 suốt 3 năm THPT</p>
                                                    <p class="mb-0 small">🏆 Đạt thủ khoa kỳ thi học sinh giỏi môn Văn 2024</p>
                                                </div>
                                            </div>
                                            <p class="fst-italic text-muted">
                                                <i class="bi bi-quote fs-4 me-1"></i>Chương trình học dễ hiểu, thầy cô tận tâm và nhiệt huyết.
                                            </p>
                                        </div>
                                    </div>

                                    <!-- Slide 5 -->
                                    <div class="carousel-item">
                                        <div class="card p-4"> <!-- Loại bỏ "shadow-custom" -->
                                            <div class="d-flex align-items-center mb-3">
                                                <img src="images/hocvien-5.jpg" alt="Học viên" class="rounded-circle me-3" style="width: 120px; height: 120px; object-fit: cover;">
                                                <div>
                                                    <h6 class="text-primary mb-1">Lê Hoàng Phúc</h6>
                                                    <p class="mb-0 small">Tài khoản: phuc****@gmail.com</p>
                                                    <p class="mb-0 small">📊 127 điểm - ĐGNL ĐHQG TP.HCM 2024</p>
                                                    <p class="mb-0 small">🌟 Thành viên đội tuyển HSG quốc gia môn Toán 2023</p>
                                                    <p class="mb-0 small">🏆 Học bổng tài năng Viettel</p>
                                                </div>
                                            </div>
                                            <p class="fst-italic text-muted">
                                                <i class="bi bi-quote fs-4 me-1"></i>Nhờ Edura, em đã cải thiện rõ rệt cả kiến thức lẫn kỹ năng làm bài.
                                            </p>
                                        </div>
                                    </div>

                                    <!-- Slide 6 -->
                                    <div class="carousel-item">
                                        <div class="card p-4"> <!-- Loại bỏ "shadow-custom" -->
                                            <div class="d-flex align-items-center mb-3">
                                                <img src="images/hocvien-6.jpg" alt="Học viên" class="rounded-circle me-3" style="width: 120px; height: 120px; object-fit: cover;">
                                                <div>
                                                    <h6 class="text-primary mb-1">Trần Minh Khôi</h6>
                                                    <p class="mb-0 small">Tài khoản: khoi****@gmail.com</p>
                                                    <p class="mb-0 small">💯 9.75 điểm thi tốt nghiệp Toán</p>
                                                    <p class="mb-0 small">📚 Đạt IELTS 7.5 khi đang học lớp 11</p>
                                                    <p class="mb-0 small">🥇 Top 10 thí sinh xuất sắc toàn quốc</p>
                                                    <p class="mb-0 small">🏆 Học bổng OISP Đại học Quốc gia TP.HCM</p>
                                                </div>
                                            </div>
                                            <p class="fst-italic text-muted">
                                                <i class="bi bi-quote fs-4 me-1"></i>Em chưa từng nghĩ mình sẽ thi tốt thế nếu không có Edura đồng hành.
                                            </p>
                                        </div>
                                    </div>

                                    <!-- Slide 7 -->
                                    <div class="carousel-item">
                                        <div class="card p-4"> <!-- Loại bỏ "shadow-custom" -->
                                            <div class="d-flex align-items-center mb-3">
                                                <img src="images/hocvien-7.jpg" alt="Học viên" class="rounded-circle me-3" style="width: 120px; height: 120px; object-fit: cover;">
                                                <div>
                                                    <h6 class="text-primary mb-1">Đỗ Thị Linh Chi</h6>
                                                    <p class="mb-0 small">Tài khoản: chi****@gmail.com</p>
                                                    <p class="mb-0 small">🏆 28.5/30 kỳ thi THPT Quốc gia</p>
                                                    <p class="mb-0 small">📚 Giải Nhì Quốc gia môn Tiếng Anh</p>
                                                    <p class="mb-0 small">🥇 Học bổng toàn phần Đại học FPT</p>
                                                    <p class="mb-0 small">🎓 Tuyển thẳng vào Đại học Y Hà Nội</p>
                                                </div>
                                            </div>
                                            <p class="fst-italic text-muted">
                                                <i class="bi bi-quote fs-4 me-1"></i>Lộ trình học rõ ràng, dễ theo sát và hiệu quả. Em cảm ơn trung tâm!
                                            </p>
                                        </div>
                                    </div>

                                    <!-- Slide 8 -->
                                    <div class="carousel-item">
                                        <div class="card p-4"> <!-- Loại bỏ "shadow-custom" -->
                                            <div class="d-flex align-items-center mb-3">
                                                <img src="images/hocvien-8.jpg" alt="Học viên" class="rounded-circle me-3" style="width: 120px; height: 120px; object-fit: cover;">
                                                <div>
                                                    <h6 class="text-primary mb-1">Ngô Tuấn Anh</h6>
                                                    <p class="mb-0 small">Tài khoản: anh****@gmail.com</p>
                                                    <p class="mb-0 small">🌟 9.8 điểm Vật lý kỳ thi tốt nghiệp THPT Quốc gia</p>
                                                    <p class="mb-0 small">🥇 Huy chương Bạc Olympic 30/4</p>
                                                    <p class="mb-0 small">🎓 Đỗ Đại học Y Hà Nội với 29.5 điểm</p>
                                                </div>
                                            </div>
                                            <p class="fst-italic text-muted">
                                                <i class="bi bi-quote fs-4 me-1"></i>Hệ thống bài tập và video rất chất lượng, giúp em tự tin thi cử.
                                            </p>
                                        </div>
                                    </div>

                                    <!-- Slide 9 -->
                                    <div class="carousel-item">
                                        <div class="card p-4"> <!-- Loại bỏ "shadow-custom" -->
                                            <div class="d-flex align-items-center mb-3">
                                                <img src="images/hocvien-9.jpg" alt="Học viên" class="rounded-circle me-3" style="width: 120px; height: 120px; object-fit: cover;">
                                                <div>
                                                    <h6 class="text-primary mb-1">Lý Gia Hưng</h6>
                                                    <p class="mb-0 small">Tài khoản: hung****@gmail.com</p>
                                                    <p class="mb-0 small">📈 131 điểm kỳ thi ĐGNL TP.HCM</p>
                                                    <p class="mb-0 small">🥇 Học sinh giỏi cấp tỉnh môn Hóa</p>
                                                    <p class="mb-0 small">📚 Giải Nhì Quốc gia môn Tiếng Anh</p>
                                                    <p class="mb-0 small">🏆 Nhận học bổng VietHope</p>
                                                </div>
                                            </div>
                                            <p class="fst-italic text-muted">
                                                <i class="bi bi-quote fs-4 me-1"></i>Chương trình học rất sát đề, dễ ôn, dễ nhớ và có hệ thống.
                                            </p>
                                        </div>
                                    </div>

                                    <!-- Slide 10 -->
                                    <div class="carousel-item">
                                        <div class="card p-4"> <!-- Loại bỏ "shadow-custom" -->
                                            <div class="d-flex align-items-center mb-3">
                                                <img src="images/hocvien-10.jpg" alt="Học viên" class="rounded-circle me-3" style="width: 120px; height: 120px; object-fit: cover;">
                                                <div>
                                                    <h6 class="text-primary mb-1">Phạm Mai Hương</h6>
                                                    <p class="mb-0 small">Tài khoản: huong****@gmail.com</p>
                                                    <p class="mb-0 small">🌟 GPA 9.8, đứng đầu lớp chuyên Văn</p>
                                                    <p class="mb-0 small">🥇 Giải Nhất cấp tỉnh môn Văn</p>
                                                    <p class="mb-0 small">🏆 Học bổng nữ sinh tiêu biểu 2024</p>
                                                    <p class="mb-0 small">🥇 Top 10 thí sinh xuất sắc toàn quốc</p>
                                                </div>
                                            </div>
                                            <p class="fst-italic text-muted">
                                                <i class="bi bi-quote fs-4 me-1"></i>Khóa học không chỉ giúp em học tốt mà còn tự tin thể hiện bản thân hơn.
                                            </p>
                                        </div>
                                    </div>

                                </div>

                                <!-- Chấm tròn điều hướng -->
                                <div class="carousel-indicators mt-3 d-flex justify-content-center" id="testimonial-indicators">
                                    <button type="button" data-bs-target="#testimonialCarousel" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
                                    <button type="button" data-bs-target="#testimonialCarousel" data-bs-slide-to="1" aria-label="Slide 2"></button>
                                    <button type="button" data-bs-target="#testimonialCarousel" data-bs-slide-to="2" aria-label="Slide 3"></button>
                                    <button type="button" data-bs-target="#testimonialCarousel" data-bs-slide-to="3" aria-label="Slide 4"></button>
                                    <button type="button" data-bs-target="#testimonialCarousel" data-bs-slide-to="4" aria-label="Slide 5"></button>
                                    <button type="button" data-bs-target="#testimonialCarousel" data-bs-slide-to="5" aria-label="Slide 6"></button>
                                    <button type="button" data-bs-target="#testimonialCarousel" data-bs-slide-to="6" aria-label="Slide 7"></button>
                                    <button type="button" data-bs-target="#testimonialCarousel" data-bs-slide-to="7" aria-label="Slide 8"></button>
                                    <button type="button" data-bs-target="#testimonialCarousel" data-bs-slide-to="8" aria-label="Slide 9"></button>
                                    <button type="button" data-bs-target="#testimonialCarousel" data-bs-slide-to="9" aria-label="Slide 10"></button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Cột phải sẽ thiết kế sau -->
                    <div class="col-md-7 py-3">
                        <h3 class="mb-4 font-weight-bold text-center">
                            👨‍👩‍👧‍👦 Góc Nhìn Phụ Huynh
                        </h3>

                        <!-- Vùng cuộn phản hồi với thanh cuộn đẹp -->
                        <div style="max-height: 400px; overflow-y: auto; padding-right: 10px;" class="custom-scrollbar">
                            <!-- Các phản hồi của phụ huynh như trước -->
                            <div class="border-left pl-3 border-primary mb-4 bg-light p-3 rounded shadow-sm">
                                <p class="mb-2">“Tôi đánh giá cao việc trung tâm có sổ liên lạc học tập online, giúp phụ huynh dễ dàng theo dõi kết quả từng tuần.”</p>
                                <p class="text-right text-primary font-weight-bold mb-0">– Chị Hương - phụ huynh lớp 10</p>
                            </div>

                            <div class="border-left pl-3 border-success mb-4 bg-light p-3 rounded shadow-sm">
                                <p class="mb-2">“Con tôi luôn háo hức đến lớp. Tôi thấy đây là môi trường lý tưởng cho học sinh phổ thông.”</p>
                                <p class="text-right text-success font-weight-bold mb-0">– Anh Minh - phụ huynh lớp 11</p>
                            </div>

                            <div class="border-left pl-3 border-info mb-4 bg-light p-3 rounded shadow-sm">
                                <p class="mb-2">“Con tôi trở nên tự giác và chủ động hơn nhiều sau khi học tại trung tâm. Cảm ơn thầy cô!”</p>
                                <p class="text-right text-info font-weight-bold mb-0">– Cô Lan - phụ huynh lớp 12</p>
                            </div>
                            <div class="border-left pl-3 border-secondary mb-4 bg-light p-3 rounded shadow-sm">
                                <p class="mb-2">“Môi trường học tập tại trung tâm rất tích cực, con tôi đã tìm thấy niềm đam mê với môn Hóa.”</p>
                                <p class="text-right text-secondary font-weight-bold mb-0">– Cô Phương - phụ huynh lớp 12</p>
                            </div>

                            <div class="border-left pl-3 border-success mb-4 bg-light p-3 rounded shadow-sm">
                                <p class="mb-2">“Giáo viên rất tận tâm và có phương pháp dạy phù hợp với từng học sinh. Con tôi tiến bộ rõ rệt chỉ sau 1 học kỳ.”</p>
                                <p class="text-right text-success font-weight-bold mb-0">– Anh Tuấn - phụ huynh lớp 11</p>
                            </div>

                            <div class="border-left pl-3 border-primary mb-4 bg-light p-3 rounded shadow-sm">
                                <p class="mb-2">“Con tôi rất thích đến lớp vì thầy cô thân thiện, môi trường học tập hiện đại và khuyến khích sự sáng tạo.”</p>
                                <p class="text-right text-primary font-weight-bold mb-0">– Chị Mai - phụ huynh lớp 10</p>
                            </div>

                            <div class="border-left pl-3 border-info mb-4 bg-light p-3 rounded shadow-sm">
                                <p class="mb-2">“Việc được gửi báo cáo học tập hàng tuần giúp tôi biết con mình đang gặp khó khăn ở đâu để hỗ trợ kịp thời.”</p>
                                <p class="text-right text-info font-weight-bold mb-0">– Anh Dũng - phụ huynh lớp 12</p>
                            </div>
                            <div class="border-left pl-3 border-danger mb-4 bg-light p-3 rounded shadow-sm">
                                <p class="mb-2">“Con tôi tự tin hơn khi làm bài thi nhờ phương pháp dạy sáng tạo của trung tâm.”</p>
                                <p class="text-right text-danger font-weight-bold mb-0">– Anh Hùng - phụ huynh lớp 10</p>
                            </div>

                            <div class="border-left pl-3 border-success mb-4 bg-light p-3 rounded shadow-sm">
                                <p class="mb-2">“Trung tâm có đội ngũ giáo viên giỏi, con tôi từ mất gốc Toán đã vươn lên được điểm khá chỉ sau 3 tháng.”</p>
                                <p class="text-right text-success font-weight-bold mb-0">– Cô Lan - phụ huynh lớp 10</p>
                            </div>

                            <div class="border-left pl-3 border-primary mb-4 bg-light p-3 rounded shadow-sm">
                                <p class="mb-2">“Con tôi học đều các môn, không còn lo lắng trước các kỳ kiểm tra lớn như trước nữa.”</p>
                                <p class="text-right text-primary font-weight-bold mb-0">– Chị Hạnh - phụ huynh lớp 12</p>
                            </div>

                            <!-- Phản hồi mới -->
                            <div class="border-left pl-3 border-warning mb-4 bg-light p-3 rounded shadow-sm">
                                <p class="mb-2">“Tôi rất hài lòng với lộ trình học tập rõ ràng, giúp con tôi cải thiện kỹ năng viết luận hiệu quả.”</p>
                                <p class="text-right text-warning font-weight-bold mb-0">– Chị Hoa - phụ huynh lớp 11</p>
                            </div>





                            <div class="border-left pl-3 border-dark mb-4 bg-light p-3 rounded shadow-sm">
                                <p class="mb-2">“Giáo viên luôn hỗ trợ tận tình, con tôi đã đạt điểm cao trong kỳ thi vừa qua.”</p>
                                <p class="text-right text-dark font-weight-bold mb-0">– Anh Việt - phụ huynh lớp 11</p>
                            </div>

                            <div class="border-left pl-3 border-light mb-4 bg-light p-3 rounded shadow-sm">
                                <p class="mb-2">“Tôi thích cách trung tâm tổ chức các buổi họp phụ huynh trực tuyến, rất tiện lợi.”</p>
                                <p class="text-right text-light font-weight-bold mb-0" style="color: #6c757d !important;">– Chị Linh - phụ huynh lớp 10</p>
                            </div>

                            <div class="border-left pl-3 border-info mb-4 bg-light p-3 rounded shadow-sm">
                                <p class="mb-2">“Con tôi đã cải thiện kỹ năng tiếng Anh đáng kể nhờ các lớp bổ trợ chất lượng.”</p>
                                <p class="text-right text-info font-weight-bold mb-0">– Anh Nam - phụ huynh lớp 12</p>
                            </div>

                            <div class="border-left pl-3 border-success mb-4 bg-light p-3 rounded shadow-sm">
                                <p class="mb-2">“Tôi rất ấn tượng với sự quan tâm của giáo viên đến từng học sinh.”</p>
                                <p class="text-right text-success font-weight-bold mb-0">– Chị Thảo - phụ huynh lớp 11</p>
                            </div>

                            <div class="border-left pl-3 border-warning mb-4 bg-light p-3 rounded shadow-sm">
                                <p class="mb-2">“Con tôi từ yếu môn Văn nay đã tự tin tham gia các cuộc thi viết.”</p>
                                <p class="text-right text-warning font-weight-bold mb-0">– Anh Quân - phụ huynh lớp 10</p>
                            </div>

                            <div class="border-left pl-3 border-danger mb-4 bg-light p-3 rounded shadow-sm">
                                <p class="mb-2">“Phương pháp dạy Toán tại trung tâm giúp con tôi hiểu bài nhanh hơn rất nhiều.”</p>
                                <p class="text-right text-danger font-weight-bold mb-0">– Cô Ngọc - phụ huynh lớp 12</p>
                            </div>                            
                        </div>
                    </div>
                </div>
            </div>
            <!-- Phản hồi end -->           
        </main>

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


        <script>
            document.addEventListener('DOMContentLoaded', () => {
                const backTopBtn = document.querySelector('.back-top-icon');

                if (backTopBtn) {
                    // Hiển thị/ẩn nút khi cuộn
                    window.addEventListener('scroll', () => {
                        if (window.scrollY > 300) {
                            backTopBtn.classList.add('visible');
                        } else {
                            backTopBtn.classList.remove('visible');
                        }
                    });

                    // Cuộn mượt khi bấm
                    backTopBtn.addEventListener('click', (e) => {
                        e.preventDefault();
                        window.scrollTo({top: 0, behavior: 'smooth'});
                    });
                } else {
                    console.error('Back to Top button not found');
                }
            });
        </script>

        <!-- JAVASCRIPT FILES -->
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script src="js/owl.carousel.min.js"></script>
        <!-- <script src="js/counter.js"></script> -->
        <!-- <script src="js/custom.js"></script> -->

        <script>
            let itemsPerPage = 8;
            let totalItems = 0;
            let totalPages = 0;
            let currentPage = 1;

            function displayPage(page) {
                page = parseInt(page);
                if (isNaN(page) || page < 1) {
                    console.warn(`Giá trị page không hợp lệ (${page}), mặc định về trang 1`);
                    page = 1;
                }
                if (page > totalPages && totalPages > 0) {
                    console.warn(`Trang ${page} vượt quá tổng số trang (${totalPages}), đặt về trang cuối`);
                    page = totalPages;
                }
                console.log("Đang hiển thị trang:", page, "currentPage:", currentPage);

                const items = document.querySelectorAll('#courseContainer .col-lg-3');
                if (!items.length) {
                    console.error("Không tìm thấy phần tử .col-lg-3 trong #courseContainer");
                    return;
                }

                if (itemsPerPage <= 0) {
                    console.error("itemsPerPage không hợp lệ:", itemsPerPage);
                    itemsPerPage = 8;
                }

                let visibleCount = 0;
                items.forEach((item, index) => {
                    const pageIndex = Math.floor(index / itemsPerPage) + 1;
                    console.log(`Item ${index + 1}, Page Index: ${pageIndex}, Current Page: ${page}`);
                    item.classList.add('hidden');
                    if (pageIndex === page) {
                        item.classList.remove('hidden');
                        visibleCount++;
                    }
                });
                console.log(`Số mục hiển thị trên trang ${page}: ${visibleCount}`);

                updatePagination(page);
            }

            function updatePagination(page) {
                const pagination = document.getElementById('pagination');
                if (!pagination) {
                    console.error('Không tìm thấy element #pagination');
                    return;
                }
                pagination.innerHTML = '';

                // Nút Previous
                const prevLi = document.createElement('li');
                prevLi.className = page === 1 ? 'page-item disabled' : 'page-item';
                const prevA = document.createElement('a');
                prevA.className = 'page-link';
                prevA.href = 'javascript:void(0)';
                prevA.textContent = '«'; // Mũi tên trái
                prevA.dataset.action = 'prev'; // Dùng dataset để xác định hành động
                prevLi.appendChild(prevA);
                pagination.appendChild(prevLi);

                // Các nút trang số
                for (let i = 1; i <= totalPages; i++) {
                    const li = document.createElement('li');
                    li.className = i === page ? 'page-item active' : 'page-item';
                    const a = document.createElement('a');
                    a.className = 'page-link';
                    a.href = 'javascript:void(0)';
                    a.textContent = i;
                    a.dataset.page = i; // Dùng dataset để lưu data-page
                    li.appendChild(a);
                    pagination.appendChild(li);
                }

                // Nút Next
                const nextLi = document.createElement('li');
                nextLi.className = page === totalPages ? 'page-item disabled' : 'page-item';
                const nextA = document.createElement('a');
                nextA.className = 'page-link';
                nextA.href = 'javascript:void(0)';
                nextA.textContent = '»'; // Mũi tên phải
                nextA.dataset.action = 'next'; // Dùng dataset để xác định hành động
                nextLi.appendChild(nextA);
                pagination.appendChild(nextLi);

                // Debug DOM
                const links = pagination.querySelectorAll('.page-link');
                links.forEach((link, index) => {
                    console.log(`Nút phân trang ${index + 1}:`, link.outerHTML);
                });
            }

            document.addEventListener('DOMContentLoaded', () => {
                // Kiểm tra các phần tử không mong muốn có class page-link
                const pageLinksOutsidePagination = document.querySelectorAll('.page-link:not(#pagination .page-link)');
                if (pageLinksOutsidePagination.length > 0) {
                    console.warn("Có các phần tử ngoài #pagination sử dụng class page-link:", pageLinksOutsidePagination);
                    pageLinksOutsidePagination.forEach(el => {
                        console.log("Phần tử:", el.outerHTML);
                    });
                }

                // Lấy trang hiện tại từ URL
                const urlParams = new URLSearchParams(window.location.search);
                currentPage = parseInt(urlParams.get('page')) || 1;
                console.log("Trang hiện tại từ URL:", currentPage);

                const items = document.querySelectorAll('#courseContainer .col-lg-3');
                totalItems = items.length;
                console.log("Tổng số phần tử:", totalItems);
                if (totalItems > 0) {
                    totalPages = Math.ceil(totalItems / itemsPerPage);
                    console.log("Tổng số trang:", totalPages);
                    displayPage(currentPage);
                } else {
                    console.error('Không tìm thấy phần tử .col-lg-3 trong #courseContainer');
                }

                // Sử dụng event delegation để xử lý click
                const pagination = document.getElementById('pagination');
                if (pagination) {
                    pagination.addEventListener('click', (e) => {
                        const link = e.target.closest('.page-link');
                        if (!link)
                            return;

                        e.preventDefault();
                        console.log("Phần tử được click:", link.outerHTML);

                        // Xử lý nút Previous/Next
                        if (link.dataset.action) {
                            if (link.dataset.action === 'prev' && currentPage > 1) {
                                currentPage--;
                            } else if (link.dataset.action === 'next' && currentPage < totalPages) {
                                currentPage++;
                            } else {
                                console.log("Không thể chuyển trang: Đã ở giới hạn (trang đầu/cuối)");
                                return;
                            }
                        } else {
                            // Xử lý nút trang số
                            const pageNum = link.dataset.page;
                            console.log("Nhấn vào trang (raw data-page):", pageNum);
                            if (!pageNum || isNaN(parseInt(pageNum))) {
                                console.error("data-page không hợp lệ hoặc không tồn tại:", pageNum);
                                return;
                            }
                            currentPage = parseInt(pageNum);
                        }

                        console.log("Cập nhật currentPage:", currentPage);

                        // Cập nhật URL
                        const url = new URL(window.location);
                        url.searchParams.set('page', currentPage);
                        window.history.pushState({}, '', url);

                        displayPage(currentPage);
                    });

                    // Kiểm tra nếu #pagination bị thay đổi
                    const observer = new MutationObserver((mutations) => {
                        mutations.forEach((mutation) => {
                            console.warn("Phần tử #pagination bị thay đổi:", mutation);
                        });
                    });
                    observer.observe(pagination, {childList: true, subtree: true});
                } else {
                    console.error("Không tìm thấy #pagination để gắn sự kiện");
                }
            });
        </script>
        <script>
            $(document).ready(function () {
                // Debug tất cả nav-link trong #navbar-vertical
                $('#navbar-vertical .nav-link').each(function () {
                    const panelId = $(this).attr('data-panel');
                    console.log('Nav-link trong #navbar-vertical:', $(this).text().trim(), 'data-panel:', panelId);
                    if (!panelId) {
                        console.warn('Nav-link thiếu data-panel:', $(this).prop('outerHTML'));
                    }
                });

                // Debug tất cả panel
                $('.course-panel').each(function () {
                    console.log('Panel tồn tại:', $(this).attr('id'));
                    console.log('Columns trong panel:', $(this).find('.column').length, $(this).find('.column').map(function () {
                        return $(this).attr('class');
                    }).get());
                });

                // Ẩn tất cả panel và cột khi load trang
                $('.course-panel').removeClass('active').css('display', 'none');
                $('.course-panel .column').removeClass('active').css('display', 'none');
                console.log('Khởi tạo: Tất cả panel và cột đã ẩn');

                // Ẩn navbar-vertical khi load trang
                $('#navbar-vertical').removeClass('show').css('display', 'none');
                console.log('Khởi tạo: Dropdown navbar-vertical đã ẩn');

                // Toggle dropdown Subjects
                $('#subjects-btn').click(function (e) {
                    e.preventDefault();
                    e.stopPropagation();
                    const $dropdown = $('#navbar-vertical');
                    const isShown = $dropdown.hasClass('show');
                    $dropdown.toggleClass('show').css('display', isShown ? 'none' : 'block');
                    $(this).attr('aria-expanded', !isShown);
                    console.log('Toggle dropdown: show =', !isShown);
                });

                // Xử lý click vào nav-link trong #navbar-vertical
                $('#navbar-vertical .nav-link').click(function (e) {
                    e.preventDefault();
                    e.stopPropagation();
                    const panelId = $(this).attr('data-panel');
                    console.log('Clicked nav-link:', $(this).text().trim(), 'data-panel:', panelId);

                    if (!panelId || typeof panelId !== 'string' || panelId.trim() === '') {
                        alert('Lỗi: Panel ID không hợp lệ. Vui lòng thử lại!');
                        console.error('Panel ID không hợp lệ:', panelId, 'Element:', $(this).prop('outerHTML'));
                        return;
                    }

                    const cleanPanelId = panelId.trim();
                    console.log('Clean panelId:', cleanPanelId);

                    const panelElement = document.getElementById(cleanPanelId);
                    if (!panelElement) {
                        alert('Nội dung cho "' + $(this).text().trim() + '" hiện chưa khả dụng. Vui lòng thử lại sau!');
                        console.warn(`Panel ${cleanPanelId} không tồn tại trong DOM`);
                        return;
                    }

                    const $newPanel = $(panelElement);
                    console.log('Panel được chọn:', $newPanel.attr('id'), 'tồn tại:', $newPanel.length);

                    $('.course-panel').removeClass('active').css('display', 'none');
                    $('.course-panel .column').removeClass('active').css('display', 'none');
                    console.log('Ẩn tất cả panel và cột');

                    $newPanel.addClass('active').css('display', 'block');
                    console.log('Hiển thị panel:', $newPanel.attr('id'), 'active =', $newPanel.hasClass('active'), 'display =', $newPanel.css('display'));

                    $newPanel.find('.tab-link').removeClass('active');
                    const $documentsTab = $newPanel.find('.tab-link[data-tab="documents"]');
                    $documentsTab.addClass('active');
                    $newPanel.find('.column').removeClass('active').css('display', 'none');
                    const $documentsColumn = $newPanel.find('.column.documents');
                    if ($documentsColumn.length) {
                        $documentsColumn.addClass('active').css('display', 'flex');
                        console.log('Cột Tài liệu active =', $documentsColumn.hasClass('active'), 'display =', $documentsColumn.css('display'));
                    } else {
                        console.warn('Cột Tài liệu không tồn tại trong panel:', $newPanel.attr('id'));
                    }

                    $('#navbar-vertical .nav-link').removeClass('active');
                    $(this).addClass('active');
                    console.log('Nav-link active:', $(this).text().trim());

                    console.log('Dropdown vẫn mở để chọn mục khác');
                });

                // Xử lý hover tab
                $('.tab-link').on('mouseenter', function () {
                    const $panel = $(this).closest('.course-panel');
                    let tab = $(this).data('tab');
                    console.log('Tab value:', tab, 'Element:', $(this).prop('outerHTML'));

                    if (typeof tab !== 'string') {
                        console.warn('Tab không phải chuỗi:', tab, 'Element:', $(this).prop('outerHTML'));
                        tab = 'documents';
                    }

                    const cleanTab = tab.replace(/[^\w\s-]/g, '').trim();
                    console.log('Cleaned tab value:', cleanTab, 'Char codes:', cleanTab.split('').map(char => char.charCodeAt(0)));

                    if (cleanTab === '') {
                        console.warn('Cleaned tab is empty, falling back to documents:', tab, 'Element:', $(this).prop('outerHTML'));
                        tab = 'documents';
                    }

                    console.log('Hover tab:', cleanTab, 'trong panel', $panel.attr('id'));

                    // Debug $panel
                    console.log('Panel element:', $panel.length, 'Classes:', $panel.attr('class'));
                    console.log('Columns trong panel:', $panel.find('.column').length, $panel.find('.column').map(function () {
                        return $(this).attr('class');
                    }).get());

                    $panel.find('.tab-link').removeClass('active');
                    $panel.find('.column').removeClass('active').css('display', 'none');

                    // Sử dụng selector an toàn hơn
                    const selector = '.column.' + cleanTab;
                    console.log('Selector:', selector);
                    const $targetColumn = $panel.find(selector);
                    if ($targetColumn.length) {
                        $targetColumn.addClass('active').css('display', 'flex');
                        console.log('Cột', cleanTab, 'active =', $targetColumn.hasClass('active'), 'display =', $targetColumn.css('display'));
                    } else {
                        console.warn('Cột ' + selector + ' không tồn tại trong panel:', $panel.attr('id'));
                        const $defaultColumn = $panel.find('.column.documents');
                        if ($defaultColumn.length) {
                            $panel.find('.tab-link[data-tab="documents"]').addClass('active');
                            $defaultColumn.addClass('active').css('display', 'flex');
                            console.log('Hiển thị cột Tài liệu mặc định');
                        } else {
                            console.warn('Cột .column.documents cũng không tồn tại trong panel:', $panel.attr('id'));
                        }
                    }
                });
                // Xử lý click vào tab-link
                $('.tab-link').on('click', function (e) {
                    e.preventDefault();
                    e.stopPropagation();

                    const $panel = $(this).closest('.course-panel');
                    const tab = $(this).data('tab');
                    console.log('Clicked tab:', tab, 'trong panel', $panel.attr('id'));

                    // Xóa active khỏi tất cả tab-link và column trong panel
                    $panel.find('.tab-link').removeClass('active');
                    $panel.find('.column').removeClass('active').css('display', 'none');

                    // Thêm active vào tab-link được click
                    $(this).addClass('active');

                    // Hiển thị column tương ứng
                    const $targetColumn = $panel.find('.column.' + tab);
                    if ($targetColumn.length) {
                        $targetColumn.addClass('active').css('display', 'flex');
                        console.log('Cột', tab, 'active =', $targetColumn.hasClass('active'), 'display =', $targetColumn.css('display'));
                    } else {
                        console.warn('Cột .' + tab + ' không tồn tại trong panel:', $panel.attr('id'));
                    }
                });

                // Đóng dropdown khi click ra ngoài
                $(document).click(function (e) {
                    if (!$(e.target).closest('#subjects-btn, #navbar-vertical').length) {
                        $('#navbar-vertical').removeClass('show').css('display', 'none');
                        $('#subjects-btn').attr('aria-expanded', 'false');
                        console.log('Đóng dropdown khi click ra ngoài');
                    }
                });
            });
        </script>

        <!-- Chấm tròn điều hướng -->
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const carousel = document.querySelector('#testimonialCarousel');
                const indicators = document.querySelectorAll('#testimonial-indicators button');

                // Lắng nghe sự kiện chuyển slide (tự động hoặc ấn nút)
                carousel.addEventListener('slid.bs.carousel', function (event) {
                    indicators.forEach(btn => btn.classList.remove('active'));
                    if (indicators[event.to]) {
                        indicators[event.to].classList.add('active');
                    }
                });
            });
        </script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            document.querySelectorAll('[class]').forEach(el => {
                if (el.className.includes('.')) {
                    console.error('❌ Có dấu chấm dư trong class:', el);
                }
            });
        </script>
        <!-- JavaScript để khởi tạo Owl Carousel -->
        <script>
            $(document).ready(function () {
                console.log("Khởi tạo Owl Carousel...");
                var $carousel = $('.owl-carousel.team-carousel');

                if ($carousel.length === 0) {
                    console.error("Không tìm thấy .owl-carousel.team-carousel trong DOM");
                    return;
                }

                $carousel.owlCarousel({
                    loop: true,
                    margin: 10,
                    nav: true,
                    navText: ['', '']
                    ,
                    // Xóa nội dung mặc định của <span>
                    responsive: {
                        0: {items: 1},
                        600: {items: 2},
                        1000: {items: 4}
                    }
                }).on('initialized.owl.carousel', function (event) {
                    console.log("Owl Carousel đã khởi tạo thành công!");

                    // Kiểm tra và xóa <span> trong nút
                    var $buttons = $('.owl-nav button');
                    if ($buttons.length === 0) {
                        console.error("Không tìm thấy .owl-nav button trong DOM sau khi khởi tạo");
                        return;
                    }

                    $buttons.each(function (index) {
                        var $span = $(this).find('span');
                        console.log(`Nút điều hướng ${index + 1} trước khi xử lý:`, $(this).html());
                        if ($span.length > 0) {
                            $span.remove(); // Xóa <span> khỏi DOM
                            console.log(`Nút điều hướng ${index + 1} sau khi xóa <span>:`, $(this).html());
                        } else {
                            console.log(`Nút điều hướng ${index + 1} không chứa <span>`);
                        }
                    });

                    // Đảm bảo .owl-nav hiển thị
                    $('.owl-nav').css({
                        'display': 'block !important',
                        'visibility': 'visible !important',
                        'opacity': '1 !important'
                    });

                    // Debug DOM
                    var $nav = $('.owl-nav');
                    if ($nav.length) {
                        console.log("Tìm thấy .owl-nav trong DOM:", $nav.html());
                    } else {
                        console.error("Không tìm thấy .owl-nav sau khi khởi tạo Owl Carousel");
                    }
                }).on('changed.owl.carousel', function (event) {
                    console.log("Carousel đã chuyển sang slide:", event.item.index);
                });
            });
        </script>
    </body>
</html>