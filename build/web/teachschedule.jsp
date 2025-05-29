<%-- 
    Document   : teacherdashboard
    Created on : May 24, 2025, 11:31:10 PM
    Author     : DO NGOC ANH HE180661
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
            }

            /* Màu đỏ cho biểu tượng */
            .top-header i.text-primary {
                color: #EC6F69 !important; /* Màu đỏ cam gần giống ảnh */
            }

            /* Logo EDURA: chỉ chữ E có màu */
            .top-header h1 span.text-primary {
                color: #EC6F69 !important;
                font-weight: 700;
            }

            /* Chữ còn lại của logo */
            .top-header h1 {
                font-size: 36px;
                color: #000;
                font-weight: 700;
            }

            /* Căn chỉnh icon và text sát nhau */
            .top-header .d-inline-flex i {
                margin-right: 12px;
            }

            .top-header small {
                font-size: 14px;
                color: #333;
            }

            .top-header h6 {
                font-weight: 600;
                margin-bottom: 4px;
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

        </style>
    </head>

    <body id="top">
        <div class="container-fluid d-none d-lg-block top-header">
            <div class="row align-items-center py-4 px-xl-5">
                <div class="col-lg-3">
                    <a href="" class="text-decoration-none">
                        <h1 class="m-0"><span class="text-primary">E</span>DURA</h1>
                    </a>
                </div>
                <div class="col-lg-3 text-right">
                    <div class="d-inline-flex align-items-center">
                        <i class="fa fa-2x fa-map-marker-alt text-primary mr-3"></i>
                        <div class="text-left">
                            <h6 class="font-weight-semi-bold mb-1">Our Office</h6>
                            <small>123 Street, New York, USA</small>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 text-right">
                    <div class="d-inline-flex align-items-center">
                        <i class="fa fa-2x fa-envelope text-primary mr-3"></i>
                        <div class="text-left">
                            <h6 class="font-weight-semi-bold mb-1">Email Us</h6>
                            <small>info@example.com</small>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 text-right">
                    <div class="d-inline-flex align-items-center">
                        <i class="fa fa-2x fa-phone text-primary mr-3"></i>
                        <div class="text-left">
                            <h6 class="font-weight-semi-bold mb-1">Call Us</h6>
                            <small>+012 345 6789</small>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="container-fluid schedule-title-container navbar">

            <h3 class="schedule-title">Lịch Giảng Dạy</h3>
        </div>
        <form action="shiftservlet" method="get">



            <table border="1" class="table-time">
                <thead>
                    <tr>
                        <th>
                            <select name="time" required>
                                <option value="">--Chọn tuần--</option>
                                <option value="week1">Tuần 1</option>

                                <option value="week2">Tuần 2</option>
                                <option value="week3">Tuần 3</option>
                                <option value="week4">Tuần 4</option>

                            </select>
                        </th>
                        <th>Thứ 2</th>
                        <th>Thứ 3</th>
                        <th>Thứ 4</th>
                        <th>Thứ 5</th>
                        <th>Thứ 6</th>
                        <th>Thứ 7</th>
                        <th>Chủ nhật</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="shift" items="${SHIFT}">
                        <tr>
                            <td>${shift.startTime} - ${shift.endTime}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>


        </form>






        <footer class="site-footer">
            <!-- Footer Start -->
            <div class="container-fluid bg-dark text-white py-5 px-sm-3 px-lg-5" style="margin-top: 90px;">
                <div class="row pt-5">
                    <div class="col-lg-7 col-md-12">
                        <div class="row">
                            <div class="col-md-6 mb-5">
                                <h5 class="text-primary text-uppercase mb-4" style="letter-spacing: 5px;">Get In Touch</h5>
                                <p><i class="fa fa-map-marker-alt mr-2"></i>123 Street, New York, USA</p>
                                <p><i class="fa fa-phone-alt mr-2"></i>+012 345 67890</p>
                                <p><i class="fa fa-envelope mr-2"></i>info@example.com</p>
                                <div class="d-flex justify-content-start mt-4">
                                    <a class="btn btn-outline-light btn-square mr-2" href="#"><i class="fab fa-twitter"></i></a>
                                    <a class="btn btn-outline-light btn-square mr-2" href="#"><i class="fab fa-facebook-f"></i></a>
                                    <a class="btn btn-outline-light btn-square mr-2" href="#"><i class="fab fa-linkedin-in"></i></a>
                                    <a class="btn btn-outline-light btn-square" href="#"><i class="fab fa-instagram"></i></a>
                                </div>
                            </div>
                            <div class="col-md-6 mb-5">
                                <h5 class="text-primary text-uppercase mb-4" style="letter-spacing: 5px;">Our Courses</h5>
                                <div class="d-flex flex-column justify-content-start">
                                    <a class="text-white mb-2" href="#"><i class="fa fa-angle-right mr-2"></i>Web Design</a>
                                    <a class="text-white mb-2" href="#"><i class="fa fa-angle-right mr-2"></i>Apps Design</a>
                                    <a class="text-white mb-2" href="#"><i class="fa fa-angle-right mr-2"></i>Marketing</a>
                                    <a class="text-white mb-2" href="#"><i class="fa fa-angle-right mr-2"></i>Research</a>
                                    <a class="text-white" href="#"><i class="fa fa-angle-right mr-2"></i>SEO</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-5 col-md-12 mb-5">
                        <h5 class="text-primary text-uppercase mb-4" style="letter-spacing: 5px;">Newsletter</h5>
                        <p>Rebum labore lorem dolores kasd est, et ipsum amet et at kasd, ipsum sea tempor magna tempor. Accu kasd sed ea duo ipsum. Dolor duo eirmod sea justo no lorem est diam</p>
                        <div class="w-100">
                            <div class="input-group">
                                <input type="text" class="form-control border-light" style="padding: 30px;" placeholder="Your Email Address">
                                <div class="input-group-append">
                                    <button class="btn btn-primary px-4">Sign Up거리</button>
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
        <!-- <script src="js/owl.carousel.min.js"></script>
        <!-- <script src="js/counter.js"></script> -->
        <!-- <script src="js/custom.js"></script> -->

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    </body>
</html>