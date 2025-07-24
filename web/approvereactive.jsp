<%-- 
    Document   : approvereactive
    Created on : Jul 22, 2025, 1:48:39 PM
    Author     : NGOC ANH
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=0.9, maximum-scale=2.0, user-scalable=yes">
        <title>EDURA - Admin</title>
        <script src="https://cdn.tailwindcss.com"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/owl.carousel.min.css" rel="stylesheet">
        <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/owl.carousel.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

        <style>
            /* Giữ nguyên CSS từ admin.jsp */
            .sidebar {
                transition: all 0.3s ease;
            }

            .sidebar-item:hover {
                background-color: rgba(79, 70, 229, 0.1);
            }

            .sidebar-item.active {
                background-color: rgba(79, 70, 229, 0.2);
                border-left: 4px solid var(--primary);
            }

            .card {
                transition: transform 0.3s ease, box-shadow 0.3s ease;
            }

            .card:hover {
                transform: translateY(-5px);
                box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -2px rgba(0, 0, 0, 0.05);
            }

            .chart-container {
                height: 300px;
            }

            /*Header and footer*/
            .text-center {
                text-align: center !important;
            }
            .top-header {
                background-color: #FFF1F1 !important;
            }

            .text-primary {
                color: #FF6B6B !important;
            }

            .font-weight-semi-bold {
                font-weight: 600 !important;
            }
            small {
                font-size: 0.875rem;
                color: #333;
            }

            /* Container của logo */
            .logo-container {
                position: relative;
                width: 100px;
                height: 100px;
                overflow: hidden;
                display: flex;
                justify-content: center;
                align-items: center;
            }

            /* Logo image */
            .logo-image {
                max-width: 100%;
                height: auto;
                transform: scale(2);
                transition: transform 0.3s ease;
                display: block;
                width: 65px;
                margin-right: 15px;
            }
            .slogan {
                font-size: 1.2rem;
                font-weight: 700;
                color: #333;
                margin: 0;
                line-height: 1.4;
                transition: color 0.3s ease;
            }
            footer.text-primary {
                color: #FF6B6B !important;
            }
            .container-fluid.bg-dark.text-white {
                background-color: #FFF1F1 !important;
                color: #333 !important;
            }

            .container-fluid.bg-dark.text-white .btn-outline-light {
                border-color: #333 !important;
                color: #333 !important;
            }
            .container-fluid.bg-dark.text-white a.text-white {
                color: #333 !important;
            }

            /*Menu*/
            .header-container {
                width: 100% !important;
                min-width: unset !important;
                max-width: 100% !important;
                margin-left: 0 !important;
                left: 0;
                right: 0;
                position: relative !important;
                z-index: 1001 !important;
                background: linear-gradient(to right, #FF9AA2, #FFF1F1);
                border-bottom: 1px solid #f3d9d9;
            }
            .header-container .navbar,
            .header-container .navbar-nav {
                width: 100% !important;
                min-width: 100% !important;
                flex-wrap: nowrap !important;
                justify-content: space-between !important;
            }

            .header-container .nav-item {
                flex-grow: 1 !important;
                min-width: 120px;
                text-align: center;
            }

            .header-container .nav-link,
            .header-container .btn-login {
                width: 100%;
                border-radius: 4px;
                font-weight: 600;
                font-size: 1.05rem;
                background: none;
                color: #ff6b6b;
                transition: background 0.2s, color 0.2s;
            }

            #setting-content, .main-content, .dashboard-content {
                overflow: visible !important;
                height: auto !important;
            }

            .header-container, .header-container * {
                visibility: visible !important;
                opacity: 1 !important;
            }

            /* Điều chỉnh cho nội dung nhúng từ home */
            .embedded-home #navbar-vertical {
                position: relative !important;
                top: auto !important;
                left: auto !important;
                width: auto !important;
                margin-top: 10px; /* Khoảng cách dưới header-container */
            }

            .embedded-home #course-content {
                position: absolute !important;
                top: 60px !important; /* Dưới header-container (60px là chiều cao ước lượng) */
                left: 250px !important; /* Bên phải #navbar-vertical, điều chỉnh dựa trên chiều rộng của #navbar-vertical */
                z-index: 9999 !important;
                width: 300px !important; /* Điều chỉnh chiều rộng theo nhu cầu */
                background: #fff;
                border: 1px solid #ddd;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                display: none; /* Ẩn ban đầu, hiển thị khi tương tác */
            }

            .embedded-home .course-panel {
                padding: 10px;
            }
            /*        //*/
            #setting-content, .content-container {
                position: relative !important;
            }
            #course-content {
                position: absolute !important;
                top: 280px; /* hoặc căn chỉnh theo .navbar-vertical */
                left: 273px; /* hoặc bằng width của #navbar-vertical */
                width: 400px;
                z-index: 100;
                background: #fff;
                box-shadow: 0 2px 10px rgba(0,0,0,0.06);
            }
            /*            //*/
            #setting-content {
                position: relative;
                overflow-y: auto;
            }
            .back-top-icon {
                position: absolute;
                right: 24px;
                bottom: 32px;
                width: 40px;
                height: 40px;
                font-size: 1.5rem;
                border-radius: 50%;
                background: #eee;
                color: #333;
                display: none;
                z-index: 999;
                cursor: pointer;
                align-items: center;
                justify-content: center;
                transition: all 0.2s;
            }
            .back-top-icon.visible {
                display: flex;
            }
        </style>
    </head>
    <body class="bg-gray-50">
        <div class="container-fluid d-none d-lg-block top-header">
            <div class="row align-items-center py-0 px-xl-5">
                <!-- Logo -->
                <div class="col-lg-4 text-start pl-0">
                    <div class="logo-container d-flex align-items-center justify-content-start position-relative">
                        <img src="${pageContext.request.contextPath}/LogoServlet"
                             alt="Logo Trung Tâm"
                             class="logo-image"
                             >
                    </div>
                    <h6 class="slogan mb-0 mt-2 d-flex align-items-center small">
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
                <!-- Điện thoại -->
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

        <div class="flex overflow-hidden">
            <!-- Sidebar -->
            <div class="sidebar w-64 bg-white shadow-md relative md:relative z-50 flex flex-col">
                <!-- Khu vực scrollable: toàn bộ menu -->
                <div class="p-4 overflow-y-auto flex-1">
                    <div class="mb-6">
                        <div class="relative">
                            <input type="text" placeholder="Tìm kiếm..." class="w-full pl-10 pr-4 py-2 rounded-lg border border-gray-300 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500 text-sm">
                            <i class="fas fa-search absolute left-3 top-2.5 text-gray-400"></i>
                        </div>
                    </div>

                    <div class="space-y-1">
                        <a href="${pageContext.request.contextPath}/admin" class="sidebar-item active flex items-center space-x-3 px-4 py-3 rounded-lg text-indigo-700">
                            <i class="fas fa-tachometer-alt w-5"></i>
                            <span>Tổng quan</span>
                        </a>
                        <a href="managerstudent" class="sidebar-item flex items-center space-x-3 px-4 py-3 rounded-lg text-gray-600 hover:text-indigo-700">
                            <i class="fas fa-users w-5"></i>
                            <span>Quản lý học sinh</span>
                        </a>
                        <a href="#" class="sidebar-item flex items-center space-x-3 px-4 py-3 rounded-lg text-gray-600 hover:text-indigo-700">
                            <i class="fas fa-chalkboard-teacher w-5"></i>
                            <span>Quản lý giáo viên</span>
                        </a>
                        <a href="${pageContext.request.contextPath}/admin?tab=courseManagement" class="sidebar-item flex items-center space-x-3 px-4 py-3 rounded-lg text-gray-600 hover:text-indigo-700">
                            <i class="fas fa-book w-5"></i>
                            <span>Quản lý khoá học</span>
                        </a>
                        <a href="#" class="sidebar-item flex items-center space-x-3 px-4 py-3 rounded-lg text-gray-600 hover:text-indigo-700">
                            <i class="fas fa-calendar-alt w-5"></i>
                            <span>Lịch học</span>
                        </a>
                        <a href="#" class="sidebar-item flex items-center space-x-3 px-4 py-3 rounded-lg text-gray-600 hover:text-indigo-700">
                            <i class="fas fa-money-bill-wave w-5"></i>
                            <span>Quản lý học phí</span>
                        </a>
                        <a href="#" class="sidebar-item flex items-center space-x-3 px-4 py-3 rounded-lg text-gray-600 hover:text-indigo-700">
                            <i class="fas fa-chart-bar w-5"></i>
                            <span>Báo cáo thống kê</span>
                        </a>
                        <a href="createnotification.jsp" class="sidebar-item flex items-center space-x-3 px-4 py-3 rounded-lg text-gray-600 hover:text-indigo-700">
                            <i class="fas fa-bell w-5"></i>
                            <span>Gửi thông báo</span>
                        </a>

                        <a href="listregister" class="sidebar-item flex items-center space-x-3 px-4 py-3 rounded-lg text-gray-600 hover:text-indigo-700">
                            <i class="fas fa-user-check w-5"></i>
                            <span>Tạo tài khoản</span>
                        </a>
                        <a href="approvereactive.jsp" class="sidebar-item flex items-center space-x-3 px-4 py-3 rounded-lg text-gray-600 hover:text-indigo-700">
                            <i class="fas fa-file w-5"></i>
                            <span>Duyệt mở lại tài khoản</span>
                        </a>

                        <!-- Cài đặt hệ thống -->
                        <a href="${pageContext.request.contextPath}/admin?tab=setting" class="sidebar-item flex items-center space-x-3 px-4 py-3 rounded-lg text-gray-600 hover:text-indigo-700">
                            <i class="fas fa-cog w-5"></i>
                            <span>Cài đặt</span>
                        </a>
                    </div>
                </div>

                <!-- Khu vực cố định phía dưới: admin -->
                <div class="p-4 border-t border-gray-200">
                    <div class="flex items-center space-x-3">
                        <div class="w-10 h-10 rounded-full bg-indigo-100 flex items-center justify-center">
                            <i class="fas fa-user-circle text-indigo-600"></i>
                        </div>
                        <div>
                            <h2 class="font-medium text-sm text-gray-800">Admin</h2>
                            <p class="text-xs text-gray-500">Quản trị viên</p>
                        </div>
                    </div>
                    <button class="w-full mt-3 px-4 py-2 bg-gray-100 hover:bg-gray-200 rounded-lg text-sm text-gray-700 flex items-center justify-center space-x-2">
                        <i class="fas fa-sign-out-alt"></i>
                        <span>Đăng xuất</span>
                    </button>
                </div>
            </div>

            <!-- Mobile overlay -->
            <div class="overlay"></div>

            <!-- Main content -->
            <div class="flex-1 overflow-auto">
                <!-- Top navbar -->
                <div class="bg-white shadow-sm sticky top-0 z-40">
                    <div class="px-6 py-3 flex items-center justify-between">
                        <div class="flex items-center space-x-4">
                            <button id="sidebarToggle" class="md:hidden text-gray-600 focus:outline-none">
                                <i class="fas fa-bars text-xl"></i>
                            </button>
                            <h1 id="main-title" class="text-xl font-bold text-gray-800">Tổng quan</h1>
                        </div>

                        <div class="flex items-center space-x-4">
                            <div class="relative">
                                <button class="text-gray-600 hover:text-gray-800 focus:outline-none">
                                    <i class="fas fa-bell text-xl"></i>
                                    <span class="absolute -top-1 -right-1 w-4 h-4 bg-red-500 rounded-full text-white text-xs flex items-center justify-center">3</span>
                                </button>
                            </div>
                            <div class="relative">
                                <button class="text-gray-600 hover:text-gray-800 focus:outline-none">
                                    <i class="fas fa-envelope text-xl"></i>
                                    <span class="absolute -top-1 -right-1 w-4 h-4 bg-blue-500 rounded-full text-white text-xs flex items-center justify-center">5</span>
                                </button>
                            </div>
                            <div class="w-px h-8 bg-gray-200"></div>
                            <div class="flex items-center space-x-2">
                                <div class="w-8 h-8 rounded-full bg-indigo-100 flex items-center justify-center">
                                    <i class="fas fa-user-circle text-indigo-600"></i>
                                </div>
                                <span class="font-medium text-sm hidden md:inline">Admin</span>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Main content area -->
                <div id="main-content">

                    <h1 class="text-3xl font-bold text-center text-gray-800 mb-10">Duyệt mở lại tài khoản</h1>


                    <c:choose>
                        <c:when test="${not empty listRequest}">
                            <table class="min-w-full border border-gray-300 text-center text-sm">
                                <thead class="bg-red-100 text-gray-700 font-semibold">
                                    <tr>
                                        <th class="py-2 px-4 border">STT</th>
                                        <th class="py-2 px-4 border">Tên học sinh</th>
                                        <th class="py-2 px-4 border">Email</th>
                                        <th class="py-2 px-4 border">Ngày sinh</th>
                                        <th class="py-2 px-4 border">Trường</th>
                                        <th class="py-2 px-4 border">Lớp</th>
                                        <th class="py-2 px-4 border">Ngày gửi yêu cầu</th>
                                        <th class="py-2 px-4 border">Trạng thái</th>

                                    </tr>
                                </thead>
                                <tbody class="bg-white text-gray-800">
                                    <c:forEach var="ap" items="${listRequest}" varStatus="loop">
                                        <tr class="hover:bg-gray-100">
                                            <td class="py-2 px-4 border">${loop.index + 1}</td>
                                            <td class="py-2 px-4 border">${ap.name}</td>
                                            <td class="py-2 px-4 border">${ap.email}</td>
                                            <td class="py-2 px-4 border">${ap.birth}</td>
                                            <td class="py-2 px-4 border">${ap.school}</td>
                                            <td class="py-2 px-4 border">${ap.classAtSchool}</td>
                                            <td class="py-2 px-4 border">${ap.dayRequest}</td>
                                            <td class="py-2 px-4 border">
                                                <c:choose>
                                                    <c:when test="${ap.status eq 'Pending'}">
                                                        <form action="approvereactive" method="post" onsubmit="return confirmApproval();">
                                                            <input type="hidden" name="requestId" value="${ap.raId}" />
                                                            <button type="submit" class="btn btn-success btn-sm">Phê duyệt</button>
                                                        </form>
                                                    </c:when>
                                                    <c:when test="${ap.status eq 'Accepted'}">
                                                        <span style="color: green; font-weight: 600;">Đã duyệt</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span>${ap.status}</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>



                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>

                        </c:when>
                        <c:otherwise>
                            <div class="alert alert-warning text-center" role="alert">
                                Không còn đơn nào.
                            </div>
                        </c:otherwise>
                    </c:choose>





                </div>
            </div>
        </div>

        <footer class="site-footer">
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

        </footer>

        <script>
            $(document).ready(function () {
//                $('.sidebar-settings-toggle').on('click', function (e) {
//                    e.preventDefault();
//                    var submenu = $(this).closest('.sidebar-item.group').find('.submenu-settings');
//                    submenu.slideToggle(200);
//                    $(this).find('.fa-chevron-down').toggleClass('fa-chevron-up');
//                });

                const tab = new URLSearchParams(window.location.search).get("tab") || "overview";

                // Set tiêu đề chính theo tab
                if (tab === "setting") {
                    $('#main-title').text("Cài đặt");

                } else if (tab === "todaySchedule") {
                    $('#main-title').text("Tổng quan > Lịch học hôm nay");
                } else {
                    $('#main-title').text("Tổng quan");
                }
            });

        </script>
        <script>
    function confirmApproval() {
        return confirm("Bạn có chắc chắn muốn phê duyệt yêu cầu này không?");
    }
</script>



    </body>
</html>