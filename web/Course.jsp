
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.Subject" %>



<!DOCTYPE html>
<!-- Văn Thị Như - HE181329 
Ngày update 23/6/2025-->
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

            .header-container .navbar {
                padding: 1rem 0;
                background-color: #f8f9fa;
                border-bottom: 1px solid #dee2e6;
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
                top: 0; /* Đảm bảo bắt đầu từ đỉnh của content-container */
                left: 0;
                width: 250px;
                z-index: 1000;
                height: 320px; /* Chiều cao cố định cho 8 môn học (8 * 40px) */
                overflow-y: auto; /* Cho phép cuộn nếu cần */
                background-color: #FFF1F1 !important;
            }

            #navbar-vertical.show {
                display: block !important;
            }

            #navbar-vertical .nav-link {
                padding: 10px 15px;
                color: #333;
                height: 40px; /* Đảm bảo mỗi nav-link có chiều cao 40px */
                display: flex;
                align-items: center;
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
                    height: auto; /* Tự động điều chỉnh chiều cao trên mobile */
                }
                #navbar-vertical .nav-link {
                    height: auto; /* Tự động điều chỉnh chiều cao trên mobile */
                }
                .content-container #header-carousel {
                    margin-left: 0;
                    min-height: 177px; /* Khớp với chiều cao navbar-vertical trên mobile */
                    max-height: 177px;
                }
                .content-container #header-carousel .carousel-item img {
                    max-height: 177px; /* Khớp chiều cao mobile */

                }
                .content-container .course-panel-container {
                    margin-left: 0;
                    width: 100%;
                }
            }
            @media (max-width: 768px) {
                #course-content {
                    position: relative !important; /* Không dùng absolute trên mobile */
                    top: auto !important;
                    left: auto !important;
                    width: 100% !important;
                    margin-left: 0 !important;
                    margin-top: 10px;
                    z-index: auto;
                }

                #navbar-vertical {
                    width: 100%;
                }

                .content-container {
                    flex-direction: column !important;
                    align-items: stretch;
                }
            }



            .carousel-wrapper {
                max-height: 300px; /* Giữ ảnh không quá cao */
                overflow: hidden;
                background-color: #FFF1F1;
                position: relative;
                display: flex;
                flex-direction: column;
            }

            .carousel-inner {
                flex: 1;
            }

            .carousel-item img {
                max-height: 300px;
                object-fit: contain;
                width: 100%;
            }
            .edit-icon i {
                width: 24px;
                height: 24px;
                line-height: 24px;
                border-radius: 50%;
                background-color: #f0f0f0;  /* hoặc trắng (#fff) */
                text-align: center;
                font-size: 12px;
                color: #333;
                box-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
            }
            .group-section {
                margin-top: 20px;
            }
            .modal.inert * {
                pointer-events: none;
            } /* Hỗ trợ inert */
            /* Modal backdrop */
            .modal {
                display: none;
                position: fixed;
                z-index: 1050;
                left: 0;
                top: 0;
                width: 100vw;
                height: 100vh;
                overflow: auto;
                background: rgba(0,0,0,0.35);
                transition: opacity 0.15s linear;
            }

            /* Modal content */
            .modal-content {
                background: #fff;
                margin: 60px auto;
                border-radius: 12px;
                padding: 32px 28px 24px 28px;
                max-width: 70%;
                box-shadow: 0 8px 40px rgba(0,0,0,0.18);
                position: relative;
                animation: modal-appear 0.25s cubic-bezier(0.4,0,0.2,1);
            }

            /* Close button */
            .modal-content .close {
                position: absolute;
                right: 18px;
                top: 16px;
                font-size: 1.7rem;
                font-weight: bold;
                color: #FF6B6B !important ;
                background: transparent;
                border: none;
                cursor: pointer;
                transition: color 0.12s;
            }
            .modal-content .close:hover {
                color: #FF6B6B;
            }

            /* Modal title */
            #modalCourseName {
                margin: 0 0 8px 0;
                font-weight: 600;
                font-size: 1.4rem;
                color: #FF6B6B;
                text-align: center;
            }

            /* Modal info paragraphs */
            .modal-content p {
                margin: 8px 0;
                color: #444;
                font-size: 1rem;
            }
            .modal-content p strong {
                min-width: 120px;
                display: inline-block;
                color: #FF6B6B;
            }

            /* ClassGroup Table */
            #classGroupContent h5 {
                margin: 20px 0 10px 0;
                font-weight: 500;
            }
            #classGroupContent table {
                width: 100%;
                border-collapse: collapse;
                margin-bottom: 8px;
            }
            #classGroupContent th, #classGroupContent td {
                border: 1px solid #e0e0e0;
                padding: 7px 10px;
                font-size: 0.98rem;
            }
            #classGroupContent th {
                background: #f2f6fb;
                color: #FF6B6B;
                font-weight: bold;
                text-align: center;
            }
            #classGroupContent td {
                text-align: center;
            }

            /* Action button styles */
            .modal-content .btn-action {
                display: block;
                width: 95%;
                max-width: 230px;
                margin: 18px auto 0 auto;
                padding: 11px 0;
                font-size: 1.08rem;
                font-weight: 600;
                border-radius: 28px;
                border: none;
                background: #FF6B6B;
                color: #fff;
                text-align: center;
                box-shadow: 0 2px 10px rgba(25,103,210,0.10);
                cursor: pointer;
                transition: background 0.13s, box-shadow 0.13s;
            }
            .btn-action {
                color: #fff !important;
            }
            .modal-content .btn-action:hover {
                background: #0DCAF0;
                box-shadow: 0 3px 18px rgba(25,103,210,0.18);
            }
            .close:focus {
                outline: none;
                box-shadow: none;
            }
            .modal-header .modal-title {
                color: #fff !important;
            }

            .info-label {
                font-weight: bold;
                color: #FF6B6B !important; /* Đổi sang màu hồng như cũ */
                margin-right: 0.3em;
                min-width: 6.5em;
                display: inline-block;
            }
            .grade-link.selected-grade {
                font-weight: bold !important;
                color: #FF6B6B !important;
            }
            /* Đảm bảo a tag không ghi đè */
            .grade-link {
                color: inherit; /* Kế thừa màu từ cha */
            }
            .team-carousel .team-item {
                display: flex;
                flex-direction: column;
                height: 100%;
            }

            .bg-light.text-center.p-4 {
                flex: 1 1 auto;
                display: flex;
                flex-direction: column;
                justify-content: flex-start;
            }
            .teacher-title {
                margin-bottom: 0.5rem;
                font-weight: 600;
                font-size: 1.15rem;
                word-break: break-word;
            }
            .teacher-school {
                font-size: 1rem;
                color: #6c757d;
                margin-bottom: 1rem;
                word-break: break-word;
            }
            .btn.mt-auto {
                margin-top: auto !important;
            }
            .edit-icon {
                color: #007bff;
            }
            .edit-icon:hover {
                color: #0056b3;
            }
            #editDescription {
                width: 100%;
                min-height: 400px;
                font-size: 16px;
                padding: 12px;
                resize: vertical;
            }
            .modal-header2 {
                display: flex;
                justify-content: space-between; /* Tiêu đề bên trái, nút đóng bên phải */
                align-items: center;            /* Căn giữa theo chiều dọc */
                padding: 1rem 1rem;
                border-bottom: 1px solid #dee2e6;
                background-color: #f8f9fa;      /* Màu nền nhẹ giống Bootstrap */
                border-top-left-radius: 0.3rem;
                border-top-right-radius: 0.3rem;
            }
            .team-item .img-fluid {
                width: 200px;
                height: 350px;
                object-fit: cover; /* Đảm bảo ảnh không bị méo */
                border-style: none;
                vertical-align: middle;
            }


        </style>
    </head>
    <body id="top">
        <!--        Header-->
        <div class="container-fluid d-none d-lg-block top-header">
            <div class="row align-items-center py-0 px-xl-5">
                <!-- Logo -->
                <div class="col-lg-4 text-center">
                    <div class="logo-container d-flex align-items-center justify-content-center position-relative">
                        <img src="${pageContext.request.contextPath}/LogoServlet"
                             alt="Logo Trung Tâm"
                             class="logo-image"
                             style="max-height:120px;max-width:100%;"/>

                    </div>
                    <h6 class="slogan mb-0 ml-2 d-flex align-items-center small">
                        ${centerName}

                    </h6>
                </div>



                <!-- Địa chỉ -->
                <div class="col-lg-3 text-center">
                    <div class="info-container d-flex align-items-center">
                        <i class="fa fa-2x fa-map-marker-alt text-primary mr-3"></i>
                        <div class="text-left">
                            <h6 class="font-weight-semi-bold mb-1">Địa chỉ</h6>
                            <small class="field-value" data-field="address">${address}</small>

                        </div>
                    </div>
                </div>
                <!-- Email -->
                <div class="col-lg-3 text-center">
                    <div class="info-container d-flex align-items-center">
                        <i class="fa fa-2x fa-envelope text-primary mr-3"></i>
                        <div class="text-left">
                            <h6 class="font-weight-semi-bold mb-1">Email</h6>
                            <small class="field-value" data-field="email">${email}</small>

                        </div>
                    </div>
                </div>
                <!-- Số điện thoại -->
                <div class="col-lg-2 text-center">
                    <div class="info-container d-flex align-items-center">
                        <i class="fa fa-2x fa-phone text-primary mr-3"></i>
                        <div class="text-left">
                            <h6 class="font-weight-semi-bold mb-1">Điện thoại</h6>
                            <small class="field-value" data-field="phone">${phone}</small>

                        </div>
                    </div>
                </div>
            </div>
        </div>


        <!-- Phần Header -->
        <div class="header-container", style="background: linear-gradient(to right, #FF9AA2, #FFF1F1)">
            <nav class="navbar navbar-expand-lg">
                <div class="container-fluid">
                    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarNav">
                        <ul class="navbar-nav d-flex w-100 align-items-center">
                            <li class="nav-item flex-grow-1">
                                <button id="subjects-btn" class="nav-link d-flex align-items-center justify-content-center custom-btn btn-login"
                                        aria-expanded="true" style="padding: 8px 15px; border-radius: 5px; cursor: pointer; border: none;">
                                    <i class="fa fa-book-open mr-2"></i>Môn học
                                </button>
                            </li>
                            <li class="nav-item flex-grow-1">
                                <a class="nav-link" href="${pageContext.request.contextPath}/home">Trang chủ</a>
                            </li>
                            <li class="nav-item flex-grow-1">
                                <a class="nav-link" href="${pageContext.request.contextPath}/about">Giới thiệu</a>
                            </li>
                            <li class="nav-item flex-grow-1">
                                <a class="nav-link active" href="${pageContext.request.contextPath}/course">Khoá học</a>
                            </li>
                            <li class="nav-item flex-grow-1">
                                <a class="nav-link" href="${pageContext.request.contextPath}/teacher">Giáo viên</a>
                            </li>
                            <li class="nav-item flex-grow-1 d-flex justify-content-center align-items-center">                            
                                <a class="nav-link custom-btn btn-login" href="login_register.jsp">Đăng nhập/Đăng kí</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        </div>

        <!-- Phần Nội dung (bao gồm navbar-vertical, carousel, và course-panel-container) -->
        <div class="content-container d-flex align-items-start" style="position: relative;">

            <!-- Vertical Subjects Navbar (Grades + Subjects) -->
            <nav class="navbar navbar-vertical navbar-light p-0 border border-top-0 border-bottom-0 bg-light show"
                 id="navbar-vertical" style="z-index:9; height:320px;">
                <div class="container-fluid h-100">
                    <div class="row w-100 h-100">
                        <!-- Left Column: Grades -->
                        <div class="col-6 grade-container d-flex flex-column justify-content-around" style="height:100%;">
                            <c:choose>
                                <c:when test="${not empty grades}">
                                    <div class="d-flex flex-column justify-content-around flex-grow-1">
                                        <c:forEach var="grade" items="${grades}">
                                            <a href="#" class="grade-link d-block text-center py-2" 
                                               data-grade="${grade.gradeID}">${grade.gradeName}</a>
                                        </c:forEach>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <p style="color: red;">Không có khối lớp nào được lấy từ DB.</p>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <!-- Right Column: Subjects -->
                        <div class="col-6 subjects-list">
                            <c:choose>
                                <c:when test="${not empty subjects}">
                                    <div class="d-flex flex-column">
                                        <c:forEach var="subject" items="${subjects}" varStatus="loop">
                                            <c:set var="subjectId" value="${not empty subject.subjectId ? subject.subjectId : loop.count}" />
                                            <a href="#" class="nav-link d-block px-2 py-1"
                                               data-panel="panel-default-${subjectId}">${subject.subjectName}</a>
                                            <c:if test="${empty subject.subjectId}">
                                                <p style="color: red;">Cảnh báo: subject.subjectId trống cho ${subject.subjectName}, dùng index ${loop.count} thay thế.</p>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <p>Không có môn học nào.</p>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </nav>
            <div class="flex-grow-1 d-flex align-items-center justify-content-center bg-light p-3">
                <div class="position-relative w-100 rounded shadow overflow-hidden" style="max-width: 950px; height: 100%;">
                    <div id="header-carousel" class="carousel slide h-100" data-bs-ride="carousel">
                        <div class="carousel-inner h-100">

                            <!-- Hiển thị banner hoặc dòng thông báo nếu không có -->
                            <c:choose>
                                <c:when test="${not empty banners}">
                                    <c:forEach var="banner" items="${banners}" varStatus="loop">
                                        <div class="carousel-item h-100 ${loop.first ? 'active' : ''}" style="position: relative;">
                                            <a href="${pageContext.request.contextPath}/login_register.jsp">
                                                <img src="${pageContext.request.contextPath}/LogoServlet?type=banner&bannerID=${banner.bannerID}"
                                                     class="d-block w-100 h-100"
                                                     style="object-fit: contain;"
                                                     alt="Banner ${loop.index + 1}">
                                            </a>                                            
                                        </div>
                                    </c:forEach>
                                </c:when>

                                <c:otherwise>
                                    <div class="carousel-item active h-100 d-flex justify-content-center align-items-center bg-white"
                                         style="min-height: 300px; position: relative;">
                                        <p class="text-muted mb-0">Chưa có banner nào.</p>                                        
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>

                        <!-- Dấu chấm điều hướng chỉ hiện khi có nhiều banner -->
                        <c:if test="${fn:length(banners) > 1}">
                            <div class="carousel-indicators d-flex justify-content-center position-absolute" style="bottom: 10px;">
                                <c:forEach var="banner" items="${banners}" varStatus="loop">
                                    <button type="button"
                                            data-bs-target="#header-carousel"
                                            data-bs-slide-to="${loop.index}"
                                            class="${loop.first ? 'active' : ''}"
                                            <c:if test="${loop.first}">aria-current="true"</c:if>
                                            aria-label="Slide ${loop.index + 1}"></button>
                                </c:forEach>
                            </div>
                        </c:if>
                    </div>
                </div>                
            </div>
        </div>
        <!-- Tài liệu và khoá học -->
        <div class="course-panel-container" id="course-content" style="width: 400px; display: none;
             background: #f8f9fa; border: 1px solid #dee2e6; padding: 10px;">


            <c:forEach var="grade" items="${grades}">
                <c:forEach var="subject" items="${subjects}">
                    <div class="course-panel"
                         id="panel-${grade.gradeID}-${subject.subjectId}"
                         data-grade-id="${grade.gradeID}"
                         data-subject-id="${subject.subjectId}"
                         style="display: none;">
                        <div class="panel-header">
                            <a class="tab-link active" data-tab="documents">Tài liệu</a>
                            <c:set var="hasCourse" value="false" />
                            <c:forEach var="cls" items="${classes}">
                                <c:if test="${cls.gradeID == grade.gradeID && cls.subjectID == subject.subjectId}">
                                    <c:set var="hasCourse" value="true" />
                                </c:if>
                            </c:forEach>
                            <c:if test="${hasCourse}">
                                <a class="tab-link" data-tab="courses">Khóa học</a>
                            </c:if>
                        </div>
                        <div class="panel-content d-flex">
                            <div class="column documents active" style="flex: 1; margin-right: 10px; display: flex;">
                                <div class="content-list">
                                    <c:set var="hasDoc" value="false"/>
                                    <c:forEach var="doc" items="${documents}">
                                        <c:if test="${doc.gradeId == grade.gradeID && doc.subjectId == subject.subjectId}">
                                            <a href="${pageContext.request.contextPath}/login_register.jsp?redirect=document&docId=${doc.documentId}">${doc.title}</a>
                                            <c:set var="hasDoc" value="true"/>
                                        </c:if>
                                    </c:forEach>
                                    <c:if test="${!hasDoc}">
                                        <p>Không có tài liệu.</p>
                                    </c:if>
                                </div>
                            </div>
                            <c:set var="hasCourse" value="false"/>
                            <c:forEach var="cls" items="${classes}">
                                <c:if test="${cls.gradeID == grade.gradeID && cls.subjectID == subject.subjectId}">
                                    <c:set var="hasCourse" value="true" />
                                </c:if>
                            </c:forEach>
                            <c:if test="${hasCourse}">
                                <div class="column courses" style="flex: 1; display: none;">
                                    <div class="content-list">
                                        <c:set var="hasAnyCls" value="false"/>
                                        <c:forEach var="cls" items="${classes}">
                                            <c:if test="${cls.gradeID == grade.gradeID && cls.subjectID == subject.subjectId}">
                                                <a href="${pageContext.request.contextPath}/login_register.jsp?redirect=course&courseId=${cls.tutoringClassID}">${cls.className}</a>
                                                <c:set var="hasAnyCls" value="true"/>
                                            </c:if>
                                        </c:forEach>
                                        <c:if test="${!hasAnyCls}">
                                            <p>Không có khóa học.</p>
                                        </c:if>
                                    </div>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </c:forEach>
            </c:forEach>
        </div>
        <!-- Banner ngang kiểu như ảnh "18 năm giáo dục" -->
        <div class="info-banner d-flex justify-content-around align-items-center text-white px-5" style="background: linear-gradient(to right, #FF9AA2, #FFD3DC);
             height: 120px; border-radius: 8px; margin-top: 0px;">
            <div class="col-lg-4 text-center mb-3">
                <div class="d-flex align-items-center justify-content-center">
                    <i class="fa fa-award" style="font-size: 2.5rem; color: white; margin-right: 1rem;"></i>
                    <div class="text-start">
                        <h6 class="fw-bold mb-1" style="font-size: 1.1rem; color: white">${yearsActive} năm</h6>
                        <small style="font-size: 1rem;">Ươm mầm tri thức</small>
                    </div>
                </div>
            </div>

            <div class="col-lg-4 text-center mb-3">
                <div class="d-flex align-items-center justify-content-center">
                    <i class="fa fa-users" style="font-size: 2.5rem; color: white; margin-right: 1rem;"></i>
                    <div class="text-start">
                        <h6 class="fw-bold mb-1" style="font-size: 1.1rem; color: white">${studentCount}</h6>
                        <small style="font-size: 1rem;">Học viên tin tưởng</small>
                    </div>
                </div>
            </div>

            <div class="col-lg-4 text-center mb-3">
                <div class="d-flex align-items-center justify-content-center">
                    <i class="fa fa-trophy" style="font-size: 2.5rem; color: white; margin-right: 1rem;"></i>
                    <div class="text-start">
                        <h6 class="fw-bold mb-1" style="font-size: 1.1rem; color: white">${partnerSchoolsCount}</h6>
                        <small style="font-size: 1rem;">Trường liên kết</small>
                    </div>
                </div>
            </div>
        </div>

        <main>
            
            <!-- Category Start -->            
            <div class="container-fluid py-5">
                <div class="container">
                    <div class="text-center py-3">    
                        <h3>Khám Phá Các Môn Học</h3>
                    </div>
                    <div class="row" id="courseContainer">
                        <c:choose>
                            <c:when test="${not empty subjects}">
                                <c:forEach var="subject" items="${subjects}">
                                    <div class="col-lg-3 col-md-6 mb-4">
                                        <div class="cat-item position-relative overflow-hidden rounded mb-2">
                                            <img class="img-fluid" 
                                                 src="${pageContext.request.contextPath}/LogoServlet?type=subject&subjectId=${subject.subjectId}" 
                                                 alt="${subject.subjectName}" 
                                                 onerror="this.src='https://picsum.photos/200/200';">
                                            <a class="cat-overlay text-white text-decoration-none" href="#">
                                                <h6 class="text-white font-weight-medium">
                                                    ${subject.classCount} Khoá học
                                                </h6>
                                            </a>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <div class="alert alert-warning col-12">Không có môn học nào để hiển thị.</div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
            <!-- Category End -->

            <!-- Courses Start -->
            <div class="container py-0 " style="margin-top: -50px;">
                <div class="container py-0">
                    <!-- Featured Courses Section -->
                    <div class="text-center py-3">
                        <h3>Khoá Học Nổi Bật</h3>
                    </div>
                    <div class="row" id="featuredCourseRow" style="padding: 0 30px">
                        <c:choose>
                            <c:when test="${not empty featuredTutoringClasses}">
                                <c:set var="featuredCount" value="0"/>
                                <c:forEach var="tc" items="${featuredTutoringClasses}">
                                    <%-- Reset groupStrings for each class with scope="page" --%>
                                    <c:set var="groupStrings" value="" scope="page"/>
                                    <c:set var="classGroups" value="${featuredClassGroupsMap[tc.tutoringClassID]}" />
                                    <c:if test="${not empty classGroups}">
                                        <c:forEach var="group" items="${classGroups}">
                                            <c:set var="room" value="${roomNames[group.roomId]}" />
                                            <c:set var="teacher" value="${teacherNames[group.teachId]}" />
                                            <c:set var="start" value="${shiftStartTimes[group.shiftId]}" />
                                            <c:set var="end" value="${shiftEndTimes[group.shiftId]}" />
                                            <c:set var="groupString" value="${group.groupName}~${group.maxStudent}~${teacher}" />
                                            <c:set var="groupStrings" value="${groupStrings}${groupString};" scope="page"/>
                                        </c:forEach>
                                    </c:if>
                                    <div class="col-lg-4 col-md-4 mb-4 featured-course-item ${featuredCount >= 3 ? 'd-none' : ''}">
                                        <div class="rounded overflow-hidden">
                                            <img class="img-fluid course-img"
                                                 src="${pageContext.request.contextPath}/LogoServlet?type=tutoring&tutoringClassId=${tc.tutoringClassID}"
                                                 alt="${fn:escapeXml(tc.className)}"
                                                 onerror="this.src='${pageContext.request.contextPath}/images/fallback.png';">
                                            <div class="course-content">
                                                <a class="h5 course-title"
                                                   href="${pageContext.request.contextPath}/login_register.jsp?redirect=course&courseId=${tc.tutoringClassID}">
                                                    ${fn:escapeXml(tc.className)}
                                                </a>
                                                <div class="border-top mt-4 pt-4">
                                                    <div class="d-flex justify-content-between align-items-center">
                                                        <button type="button"
                                                                class="btn btn-primary py-md-2 px-md-3 font-weight-semi-bold"
                                                                data-classid="${tc.tutoringClassID}"
                                                                data-classname="${fn:escapeXml(tc.className)}"
                                                                data-descrip="${empty tc.descrip ? 'Chưa có mô tả' : fn:escapeXml(tc.descrip)}"
                                                                data-ishot="${tc.isHot}"
                                                                data-gradename="${empty gradeNames[tc.gradeID] ? 'Chưa xác định' : gradeNames[tc.gradeID]}"
                                                                data-subject="${empty subjectNames[tc.subjectID] ? 'Chưa xác định' : subjectNames[tc.subjectID]}"
                                                                data-timerange="${empty timerangeMap[tc.tutoringClassID] ? 'Chưa xác định' : timerangeMap[tc.tutoringClassID]}"                                                          
                                                                data-startdate="<fmt:formatDate value='${tc.startDate}' pattern='dd/MM/yyyy'/>"
                                                                data-enddate="<fmt:formatDate value='${tc.endDate}' pattern='dd/MM/yyyy'/>"
                                                                data-tuition="${tc.price}"
                                                                data-classgroups="${fn:escapeXml(groupStringMap[tc.tutoringClassID])}"
                                                                onclick="showCourseDetail(this)">
                                                            Chi tiết
                                                        </button>
                                                        <a href="${pageContext.request.contextPath}/login_register.jsp?redirect=course&courseId=${tc.tutoringClassID}"
                                                           class="btn btn-primary py-md-2 px-md-3 font-weight-semi-bold">Tham gia</a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <c:set var="featuredCount" value="${featuredCount + 1}"/>
                                </c:forEach>
                                <c:if test="${featuredCount == 0}">
                                    <div class="col-12">
                                        <p class="text-center">Không có khóa học nổi bật nào để hiển thị.</p>
                                    </div>
                                </c:if>
                            </c:when>
                            <c:otherwise>
                                <div class="col-12">
                                    <p class="text-center">Không có khóa học nổi bật nào để hiển thị.</p>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <c:if test="${featuredCount > 3}">
                        <div class="text-center mt-4">
                            <button id="loadMoreFeaturedBtn" class="btn btn-primary py-2 px-4">Xem thêm</button>
                        </div>
                    </c:if>

                    <!-- Year-round Courses Section -->
                    <div class="text-center py-3">
                        <h3>Khoá Học Quanh Năm</h3>
                    </div>
                    <div class="row" id="yearRoundCourseRow" style="padding: 0 30px">
                        <c:choose>
                            <c:when test="${not empty yearRoundTutoringClasses}">
                                <c:set var="yearRoundCount" value="0"/>
                                <c:forEach var="tc" items="${yearRoundTutoringClasses}">
                                    <%-- Reset groupStrings for each class with scope="page" --%>
                                    <c:set var="groupStrings" value="" scope="page"/>
                                    <c:set var="classGroups" value="${yearRoundClassGroupsMap[tc.tutoringClassID]}" />
                                    <c:if test="${not empty classGroups}">
                                        <c:forEach var="group" items="${classGroups}">
                                            <c:set var="room" value="${roomNames[group.roomId]}" />
                                            <c:set var="teacher" value="${teacherNames[group.teachId]}" />
                                            <c:set var="start" value="${shiftStartTimes[group.shiftId]}" />
                                            <c:set var="end" value="${shiftEndTimes[group.shiftId]}" />
                                            <c:set var="groupString" value="${group.groupName}~${group.maxStudent}~${teacher}" />
                                            <c:set var="groupStrings" value="${groupStrings}${groupString};" scope="page"/>
                                        </c:forEach>
                                    </c:if>
                                    <div class="col-lg-4 col-md-4 mb-4 year-round-course-item ${yearRoundCount >= 3 ? 'd-none' : ''}">
                                        <div class="rounded overflow-hidden">
                                            <img class="img-fluid course-img"
                                                 src="${pageContext.request.contextPath}/LogoServlet?type=tutoring&tutoringClassId=${tc.tutoringClassID}"
                                                 alt="${fn:escapeXml(tc.className)}"
                                                 onerror="this.src='${pageContext.request.contextPath}/images/fallback.png';">
                                            <div class="course-content">
                                                <a class="h5 course-title"
                                                   href="${pageContext.request.contextPath}/login_register.jsp?redirect=course&courseId=${tc.tutoringClassID}">
                                                    ${fn:escapeXml(tc.className)}
                                                </a>
                                                <div class="border-top mt-4 pt-4">
                                                    <div class="d-flex justify-content-between align-items-center">
                                                        <button type="button"
                                                                class="btn btn-primary py-md-2 px-md-3 font-weight-semi-bold"
                                                                data-classid="${tc.tutoringClassID}"
                                                                data-classname="${fn:escapeXml(tc.className)}"
                                                                data-descrip="${empty tc.descrip ? 'Chưa có mô tả' : fn:escapeXml(tc.descrip)}"
                                                                data-ishot="${tc.isHot}"
                                                                data-gradename="${empty gradeNames[tc.gradeID] ? 'Chưa xác định' : gradeNames[tc.gradeID]}"
                                                                data-subject="${empty subjectNames[tc.subjectID] ? 'Chưa xác định' : subjectNames[tc.subjectID]}"
                                                                data-timerange="${empty timerangeMap[tc.tutoringClassID] ? 'Chưa xác định' : timerangeMap[tc.tutoringClassID]}"                                                          
                                                                data-startdate="<fmt:formatDate value='${tc.startDate}' pattern='dd/MM/yyyy'/>"
                                                                data-enddate="<fmt:formatDate value='${tc.endDate}' pattern='dd/MM/yyyy'/>"
                                                                data-tuition="${tc.price}"
                                                                data-classgroups="${fn:escapeXml(groupStringMap[tc.tutoringClassID])}"
                                                                onclick="showCourseDetail(this)">
                                                            Chi tiết
                                                        </button>
                                                        <a href="${pageContext.request.contextPath}/login_register.jsp?redirect=course&courseId=${tc.tutoringClassID}"
                                                           class="btn btn-primary py-md-2 px-md-3 font-weight-semi-bold">Tham gia</a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <c:set var="yearRoundCount" value="${yearRoundCount + 1}"/>
                                </c:forEach>
                                <c:if test="${yearRoundCount == 0}">
                                    <div class="col-12">
                                        <p class="text-center">Không có khóa học quanh năm nào để hiển thị.</p>
                                    </div>
                                </c:if>
                            </c:when>
                            <c:otherwise>
                                <div class="col-12">
                                    <p class="text-center">Không có khóa học quanh năm nào để hiển thị.</p>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <c:if test="${yearRoundCount > 3}">
                        <div class="text-center mt-4">
                            <button id="loadMoreYearRoundBtn" class="btn btn-primary py-2 px-4">Xem thêm</button>
                        </div>
                    </c:if>

                    <!-- Modal chi tiết khoá học -->
                    <div id="courseDetailModal" class="modal" style="display: none;">
                        <div class="modal-content p-4" style="max-width: 900px; margin: auto; border-radius: 10px; background: #fff;">
                            <button type="button" class="close" onclick="closeModalClass()" aria-label="Đóng"
                                    style="position: absolute; right: 20px; top: 20px; font-size: 24px;">&times;</button>

                            <h4 id="modalCourseName" class="mb-3 font-weight-bold"></h4>

                            <!-- Mô tả riêng 1 dòng -->
                            <div class="mb-4">
                                <p class="text-muted mb-0" style="font-size: 1.1rem;">
                                    <strong>Mô tả:</strong> <span id="modalCourseDescrip"></span>
                                </p>
                            </div>

                            <!-- Thông tin chia 2 cột -->
                            <div class="row">
                                <div class="col-md-6">
                                    <p><i class="fas fa-fire text-danger mr-2"></i><strong>Loại khóa học:</strong> <span id="modalIsHot"></span></p>
                                    <p><i class="fas fa-layer-group text-primary mr-2"></i><strong>Lớp:</strong> <span id="modalgradeName"></span></p>
                                    <p><i class="fas fa-book text-info mr-2"></i><strong>Môn học:</strong> <span id="modalSubjectName"></span></p>
                                </div>
                                <div class="col-md-6">                
                                    <p><i class="fas fa-calendar-plus text-secondary mr-2"></i><strong>Ngày bắt đầu:</strong> <span id="modalStartDate"></span></p>
                                    <p><i class="fas fa-calendar-check text-secondary mr-2"></i><strong>Ngày kết thúc:</strong> <span id="modalEndDate"></span></p>
                                    <p><i class="fas fa-money-bill text-success mr-2"></i><strong>Học phí:</strong> <span id="modalTuitionFee"></span></p>
                                </div>
                            </div>

                            <!-- Danh sách nhóm lớp -->
                            <div class="mt-4" id="classGroupContent"></div>

                            <!-- Nút tham gia -->
                            <div class="text-right mt-4">
                                <a id="joinCourseBtn" href="#" class="btn btn-primary px-4 py-2">Tham gia</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Courses End -->                     
    </main>

    <footer class="site-footer" style="margin-top: 70px;">
        <!-- Footer Start -->
        <div class="container-fluid bg-dark text-white py-0 px-sm-3 px-lg-5" style="margin-top: 0px;">
            <div class="row pt-5">
                <div class="col-lg-5 col-md-12 mb-5">
                    <a href="" class="text-decoration-none">

                        <div class="logo-container">
                            <img src="${pageContext.request.contextPath}/LogoServlet" alt="Logo Trung Tâm" class="logo-image"/>
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
    <script src="js/owl.carousel.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/owl.carousel.min.js"></script>
    
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
    
    <script>
        $(document).ready(function () {
            // Debug tất cả grade-link
            $('.grade-link').each(function () {
                const gradeId = $(this).data('grade');
                console.log('Grade-link:', $(this).text().trim(), 'data-grade:', gradeId);
                if (!gradeId) {
                    console.warn('Grade-link thiếu data-grade:', $(this).prop('outerHTML'));
                }
            });

            // Debug tất cả nav-link trong #navbar-vertical
            $('#navbar-vertical .nav-link').each(function () {
                const panelId = $(this).attr('data-panel');
                console.log('Nav-link:', $(this).text().trim(), 'data-panel:', panelId);
                if (!panelId) {
                    console.warn('Nav-link thiếu data-panel:', $(this).prop('outerHTML'));
                }
            });

            // Debug tất cả panel để lấy danh sách gradeID khả dụng
            const availableGradeIds = [...new Set($('.course-panel').map(function () {
                    return $(this).data('grade-id');
                }).get())];
            console.log('GradeIDs khả dụng trong DOM:', availableGradeIds);

            // Debug tất cả panel
            $('.course-panel').each(function () {
                console.log('Panel tồn tại:', $(this).attr('id'), 'data-grade-id:', $(this).data('grade-id'), 'data-subject-id:', $(this).data('subject-id'));
            });

            // Khởi tạo trạng thái ban đầu
            let currentGradeId = null;
            $('.subjects-list').css('display', 'flex');
            $('#course-content').css('display', 'none');
            $('#navbar-vertical .nav-link').removeClass('active');
            console.log('Khởi tạo: Hiển thị danh sách môn học, ẩn nội dung panel, không active môn học nào');

            // Xử lý click vào nút Subjects
            $('#subjects-btn').click(function (e) {
                e.preventDefault();
                e.stopPropagation();
                $('#course-content').css('display', 'none');
                $('#navbar-vertical .nav-link').removeClass('active');
                currentGradeId = null;
                $('.grade-link').removeClass('active').removeClass('selected-grade');
                console.log('Click nút Subjects: Ẩn nội dung panel, giữ danh sách môn học, không active môn học nào, reset grade');
            });

            // Xử lý click vào grade-link
            $(document).on('click', '.grade-link', function (e) {
                e.preventDefault();
                e.stopPropagation();
                const gradeId = $(this).data('grade');
                if (!availableGradeIds.includes(gradeId)) {
                    alert('Khối lớp ' + $(this).text().trim() + ' không có nội dung. Vui lòng chọn khối khác!');
                    console.warn('GradeID không khả dụng:', gradeId, 'Danh sách khả dụng:', availableGradeIds);
                    return;
                }
                $('.grade-link').removeClass('active').removeClass('selected-grade');
                $(this).addClass('active').addClass('selected-grade');
                currentGradeId = gradeId;
                console.log('Clicked grade-link:', $(this).text().trim(), 'data-grade:', gradeId, 'currentGradeId:', currentGradeId);

                // Ẩn tất cả panel khi thay đổi grade
                $('#course-content').css('display', 'none');
                $('#navbar-vertical .nav-link').removeClass('active');
                // Cập nhật data-panel của nav-link dựa trên grade hiện tại
                $('#navbar-vertical .nav-link').each(function () {
                    const $link = $(this);
                    const originalPanel = $link.attr('data-panel');
                    console.log('Processing nav-link:', $link.text().trim(), 'Original data-panel:', originalPanel);
                    const originalPanelParts = originalPanel.split('-');
                    if (originalPanelParts.length < 3 || originalPanelParts[0] !== 'panel' || !originalPanelParts[2]) {
                        console.error('Định dạng data-panel không hợp lệ:', originalPanel, 'Element:', $link.prop('outerHTML'));
                        return; // Bỏ qua nếu không hợp lệ
                    }
                    const subjectId = originalPanelParts[2]; // Lấy subject.id
                    console.log('Extracted subjectId:', subjectId, 'currentGradeId:', currentGradeId);
                    const newPanelId = 'panel-' + currentGradeId + '-' + subjectId; // Sử dụng nối chuỗi thủ công
                    console.log('Generated newPanelId:', newPanelId); // Debug giá trị trước khi gán
                    $link.attr('data-panel', newPanelId);
                    console.log('Cập nhật data-panel cho:', $link.text().trim(), 'từ:', originalPanel, 'thành:', newPanelId);
                });
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

                // Kiểm tra nếu chưa chọn grade
                if (!currentGradeId) {
                    alert('Vui lòng chọn khối lớp trước!');
                    console.warn('Chưa chọn grade, hủy kích hoạt nav-link');
                    return;
                }

                const cleanPanelId = panelId.trim();
                console.log('Clean panelId:', cleanPanelId);

                const panelElement = document.getElementById(cleanPanelId);
                if (!panelElement) {
                    alert('Nội dung cho "' + $(this).text().trim() + '" hiện chưa khả dụng. Vui lòng thử lại sau!');
                    console.warn(`Panel ${cleanPanelId} không tồn tại trong DOM. Các panel hiện có:`, $('.course-panel').map(function () {
                        return $(this).attr('id');
                    }).get());
                    return;
                }

                // Hiển thị nội dung panel
                $('#course-content').css('display', 'block');
                console.log('Hiển thị nội dung panel');

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

                $panel.find('.tab-link').removeClass('active');
                $panel.find('.column').removeClass('active').css('display', 'none');

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

                $panel.find('.tab-link').removeClass('active');
                $panel.find('.column').removeClass('active').css('display', 'none');

                $(this).addClass('active');

                const $targetColumn = $panel.find('.column.' + tab);
                if ($targetColumn.length) {
                    $targetColumn.addClass('active').css('display', 'flex');
                    console.log('Cột', tab, 'active =', $targetColumn.hasClass('active'), 'display =', $targetColumn.css('display'));
                } else {
                    console.warn('Cột .' + tab + ' không tồn tại trong panel:', $panel.attr('id'));
                }
            });

            // Xử lý click ra ngoài
            $(document).click(function (e) {
                const $target = $(e.target);
                const isInsideNavbarVertical = $target.closest('#navbar-vertical').length > 0;
                const isInsideCourseContent = $target.closest('#course-content').length > 0;
                const isSubjectsButton = $target.closest('#subjects-btn').length > 0;

                if (!isInsideNavbarVertical && !isInsideCourseContent && !isSubjectsButton) {
                    $('#course-content').css('display', 'none');
                    $('#navbar-vertical .nav-link').removeClass('active');
                    $('.grade-link').removeClass('active').removeClass('selected-grade');
                    currentGradeId = null;
                    console.log('Click ra ngoài: Ẩn nội dung panel, giữ danh sách môn học, không active môn học nào, reset grade');
                }
            });
        });
    </script>
    
    <script>
        function clean(value) {
            return (value && value !== "false") ? value : '';
        }
        window.showCourseDetail = function (btn) {
            const get = key => btn.getAttribute('data-' + key) || '';

            // Cập nhật thông tin cơ bản
            document.getElementById('modalCourseName').textContent = get('classname') || 'Chưa xác định';
            document.getElementById('modalCourseDescrip').textContent = get('descrip') || 'Chưa có mô tả';
            document.getElementById('modalIsHot').textContent = (get('ishot') === "true" || get('ishot') === "1") ? "Nổi bật" : "Quanh năm";
            document.getElementById('modalgradeName').textContent = get('gradename') || 'Chưa xác định';
            document.getElementById('modalSubjectName').textContent = get('subject') || 'Chưa xác định';            
            document.getElementById('modalStartDate').textContent = get('startdate') || 'Chưa xác định';
            document.getElementById('modalEndDate').textContent = get('enddate') || 'Chưa xác định';            

            // Format học phí
            function formatMoneyVND(amount, isHot) {
                if (!amount || isNaN(amount))
                    return 'Chưa xác định';
                let formatted = Number(amount).toLocaleString('vi-VN') + " VNĐ";
                return isHot ? formatted : (formatted + " / 1 buổi");
            }

            let isHot = get('ishot') === "true" || get('ishot') === "1";
            document.getElementById('modalTuitionFee').textContent = formatMoneyVND(get('tuition'), isHot);

            // Gán link nút đăng ký
            document.getElementById('joinCourseBtn').href = 'login_register.jsp?redirect=course&courseId=' + (get('classid') || '');

            
            const container = document.getElementById('classGroupContent');
            const classGroupsStr = get('classgroups');            
            let html = '<p>Chưa có nhóm lớp nào.</p>';

            if (classGroupsStr) {
                const groupsArr = classGroupsStr.split(';').filter(Boolean);                
                if (groupsArr.length > 0) {
                    html = `
                <h6>Danh sách nhóm lớp:</h6>
                <table class="table table-bordered" style="width:100%">
                    <thead>
                        <tr>                        
                            <th>Tên nhóm</th>
                            <th>Giáo viên</th>
                            <th>Sĩ số tối đa</th>                                                                                                              
                        </tr>
                    </thead>
                    <tbody>`;

                    groupsArr.forEach((item, index) => {
                        const parts = item.split('~');
                        while (parts.length < 3)
                            parts.push('');
                        function formatTime(str) {
                            if (!str || typeof str !== 'string')
                                return '';
                            return str.length >= 5 ? str.substring(0, 5) : str;
                        }
                        const groupName = parts[0];
                        const maxStudent = parts[1];                       
                        const teacher = parts[2];                       
                        html += `
    <tr>
        
        <td>` + clean(groupName) + `</td>
                <td>` + clean(teacher) + `</td>
        <td>` + clean(maxStudent) + `</td>        
                
    </tr>`;
                    });

                    html += `
                    </tbody>
                </table>`;
                }
            }

            // Cập nhật vào DOM
            if (container) {
                container.innerHTML = html;
                console.log("HTML sinh ra:", html);
                console.log("ã cập nhật bảng nhóm lớp.");
            } else {
                console.warn("Không tìm thấy #classGroupContent");
            }

            // Hiển thị modal
            document.getElementById('courseDetailModal').style.display = 'block';
            document.body.style.overflow = 'hidden';
        };

// Hàm đóng modal
        window.closeModalClass = function () {
            document.getElementById('courseDetailModal').style.display = 'none';
            document.body.style.overflow = '';
        };

        document.addEventListener('DOMContentLoaded', function () {
            const loadMoreFeaturedBtn = document.getElementById('loadMoreFeaturedBtn');
            if (loadMoreFeaturedBtn) {
                loadMoreFeaturedBtn.addEventListener('click', function () {
                    document.querySelectorAll('.featured-course-item.d-none').forEach(function (el) {
                        el.classList.remove('d-none');
                    });
                    this.style.display = 'none';
                });
            }

            const loadMoreYearRoundBtn = document.getElementById('loadMoreYearRoundBtn');
            if (loadMoreYearRoundBtn) {
                loadMoreYearRoundBtn.addEventListener('click', function () {
                    document.querySelectorAll('.year-round-course-item.d-none').forEach(function (el) {
                        el.classList.remove('d-none');
                    });
                    this.style.display = 'none';
                });
            }
        });
    </script>      
    
    <script>
        $(document).ready(function () {
            const $navbarVertical = $('#navbar-vertical');
            const $courseContent = $('#course-content');

            if ($navbarVertical.length && $courseContent.length) {
                const verticalOffset = $navbarVertical.offset();
                const verticalWidth = $navbarVertical.outerWidth();
                const verticalHeight = $navbarVertical.outerHeight();

                $courseContent.css({
                    position: 'absolute',
                    top: verticalOffset.top + 'px', // Ngay hàng với navbar dọc
                    left: (verticalOffset.left + verticalWidth) + 'px', // Ngay bên phải
                    zIndex: 9999
                });
            }
        });

    </script>

</body>
</html>