<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- Văn Thị Như - HE181329 
Ngày update 23/6/2025-->
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>EDURA</title>
        <script src="https://cdn.tailwindcss.com"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<!--        <link href="${pageContext.request.contextPath}/css/tooplate-gotto-job.css" rel="stylesheet">-->
        <link href="${pageContext.request.contextPath}/css/owl.carousel.min.css" rel="stylesheet">
        <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/owl.carousel.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <script src="${pageContext.request.contextPath}/js/home.js"></script>
        <style>
/*            :root {
                --primary: #4f46e5;
                --secondary: #6366f1;
                --dark: #1e293b;
                --light: #f8fafc;
                --success: #10b981;
                --warning: #f59e0b;
                --danger: #ef4444;
            }*/

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
                background-color: #FFF1F1 !important; /* Màu nền hồng nhạt */
            }

            .text-primary {
                color: #FF6B6B !important; /* Màu hồng đỏ */
            }

            .font-weight-semi-bold {
                font-weight: 600 !important; /* Đậm vừa */
            }
            small {
                font-size: 0.875rem; /* Kích thước chữ nhỏ */
                color: #333;
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
            /*            Cài đặt hệ thống*/

            .sidebar {
                position: relative;
                height: auto;
            }

            .sidebar .sidebar-main {
                flex-grow: 1;
            }
/*Menu*/
/* Đảm bảo header-container (menu ngang) luôn hiển thị ngang full width */
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
/* Đảm bảo navbar bên trong header-container hiển thị đúng */
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

.header-container .nav-link.active,
.header-container .btn-login.active,
.header-container .nav-link:hover,
.header-container .btn-login:hover {
    background: #ff6b6b22;
    color: #ff6b6b;
}

/* Nếu vùng nhúng hoặc vùng admin bị overflow: hidden, height: 100%, hãy sửa lại */
#setting-content, .main-content, .dashboard-content {
    overflow: visible !important;
    height: auto !important;
}

/* Nếu vẫn bị ẩn hoặc hiển thị sai, thử thêm: */
.header-container, .header-container * {
    visibility: visible !important;
    opacity: 1 !important;
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
                             style="max-height:120px; max-width:100%;">
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
                        <a href="#" class="sidebar-item active flex items-center space-x-3 px-4 py-3 rounded-lg text-indigo-700">
                            <i class="fas fa-tachometer-alt w-5"></i>
                            <span>Tổng quan</span>
                        </a>
                        <a href="#" class="sidebar-item flex items-center space-x-3 px-4 py-3 rounded-lg text-gray-600 hover:text-indigo-700">
                            <i class="fas fa-users w-5"></i>
                            <span>Quản lý học sinh</span>
                        </a>
                        <a href="#" class="sidebar-item flex items-center space-x-3 px-4 py-3 rounded-lg text-gray-600 hover:text-indigo-700">
                            <i class="fas fa-chalkboard-teacher w-5"></i>
                            <span>Quản lý giáo viên</span>
                        </a>
                        <a href="#" class="sidebar-item flex items-center space-x-3 px-4 py-3 rounded-lg text-gray-600 hover:text-indigo-700">
                            <i class="fas fa-book w-5"></i>
                            <span>Quản lý môn học</span>
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

                        <!-- Cài đặt hệ thống -->
                        <div class="sidebar-item group px-4 py-3 text-gray-600 hover:text-indigo-700 cursor-pointer">
                            <div class="flex items-center justify-between">
                                <div class="flex items-center space-x-3">
                                    <i class="fas fa-cog w-5"></i>
                                    <span>Cài đặt hệ thống</span>
                                </div>
                                <i class="fas fa-chevron-down text-sm transition-transform group-hover:rotate-180"></i>
                            </div>

                            <!-- Submenu -->
                            <div class="mt-2 space-y-1 hidden group-hover:block">
                                <a href="#" class="settings-tab block px-2 py-1 rounded hover:bg-indigo-100 text-sm text-gray-700" data-url="${pageContext.request.contextPath}/home">Thông tin chung</a>
                                <a href="#" class="settings-tab block px-2 py-1 rounded hover:bg-indigo-100 text-sm text-gray-700" data-tab="subjects">Môn học</a>
                                <a href="#" class="settings-tab block px-2 py-1 rounded hover:bg-indigo-100 text-sm text-gray-700" data-tab="shifts">Ca học</a>
                                <a href="#" class="settings-tab block px-2 py-1 rounded hover:bg-indigo-100 text-sm text-gray-700" data-tab="rooms">Phòng học</a>
                            </div>
                        </div>
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
                            <h1 class="text-xl font-bold text-gray-800">Tổng quan</h1>
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
                <div class="p-6">
                    <div id="setting-content" class="p-4 w-full">
                        <!-- Nội dung JSP sẽ được load vào đây -->
                    </div>

                    <!-- Stats cards -->
                    <!--                    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-6">
                                            <div class="card bg-white p-6 rounded-xl shadow-sm border border-gray-100">
                                                <div class="flex items-center justify-between">
                                                    <div>
                                                        <p class="text-sm font-medium text-gray-500">Tổng học sinh</p>
                                                        <h3 class="text-2xl font-bold text-gray-800 mt-1">245</h3>
                                                        <p class="text-xs text-green-500 mt-1 flex items-center">
                                                            <i class="fas fa-caret-up mr-1"></i>
                                                            <span>12% so với tháng trước</span>
                                                        </p>
                                                    </div>
                                                    <div class="w-12 h-12 rounded-full bg-green-100 flex items-center justify-center">
                                                        <i class="fas fa-users text-green-600"></i>
                                                    </div>
                                                </div>
                                            </div>
                    
                                            <div class="card bg-white p-6 rounded-xl shadow-sm border border-gray-100">
                                                <div class="flex items-center justify-between">
                                                    <div>
                                                        <p class="text-sm font-medium text-gray-500">Tổng giáo viên</p>
                                                        <h3 class="text-2xl font-bold text-gray-800 mt-1">18</h3>
                                                        <p class="text-xs text-green-500 mt-1 flex items-center">
                                                            <i class="fas fa-caret-up mr-1"></i>
                                                            <span>2% so với tháng trước</span>
                                                        </p>
                                                    </div>
                                                    <div class="w-12 h-12 rounded-full bg-blue-100 flex items-center justify-center">
                                                        <i class="fas fa-chalkboard-teacher text-blue-600"></i>
                                                    </div>
                                                </div>
                                            </div>
                    
                                            <div class="card bg-white p-6 rounded-xl shadow-sm border border-gray-100">
                                                <div class="flex items-center justify-between">
                                                    <div>
                                                        <p class="text-sm font-medium text-gray-500">Lớp học đang hoạt động</p>
                                                        <h3 class="text-2xl font-bold text-gray-800 mt-1">15</h3>
                                                        <p class="text-xs text-yellow-500 mt-1 flex items-center">
                                                            <i class="fas fa-caret-down mr-1"></i>
                                                            <span>1% so với tháng trước</span>
                                                        </p>
                                                    </div>
                                                    <div class="w-12 h-12 rounded-full bg-purple-100 flex items-center justify-center">
                                                        <i class="fas fa-school text-purple-600"></i>
                                                    </div>
                                                </div>
                                            </div>
                    
                                            <div class="card bg-white p-6 rounded-xl shadow-sm border border-gray-100">
                                                <div class="flex items-center justify-between">
                                                    <div>
                                                        <p class="text-sm font-medium text-gray-500">Doanh thu tháng</p>
                                                        <h3 class="text-2xl font-bold text-gray-800 mt-1">75,200,000đ</h3>
                                                        <p class="text-xs text-green-500 mt-1 flex items-center">
                                                            <i class="fas fa-caret-up mr-1"></i>
                                                            <span>8% so với tháng trước</span>
                                                        </p>
                                                    </div>
                                                    <div class="w-12 h-12 rounded-full bg-red-100 flex items-center justify-center">
                                                        <i class="fas fa-money-bill-wave text-red-600"></i>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                    
                                         Charts and recent activities 
                                        <div class="grid grid-cols-1 lg:grid-cols-3 gap-6 mb-6">
                                             Student registration chart 
                                            <div class="lg:col-span-2 bg-white p-6 rounded-xl shadow-sm border border-gray-100">
                                                <div class="flex items-center justify-between mb-4">
                                                    <h2 class="text-lg font-semibold text-gray-800">Thống kê đăng ký học sinh</h2>
                                                    <div class="flex space-x-2">
                                                        <button id="monthBtn" class="px-3 py-1 text-xs bg-indigo-100 text-indigo-700 rounded-md">Tháng</button>
                                                        <button id="quarterBtn" class="px-3 py-1 text-xs bg-gray-100 text-gray-600 rounded-md">Quý</button>
                                                        <button id="yearBtn" class="px-3 py-1 text-xs bg-gray-100 text-gray-600 rounded-md">Năm</button>
                                                    </div>
                                                </div>
                                                <div class="chart-container">
                                                    <canvas id="studentChart"></canvas>
                                                </div>
                                            </div>
                    
                                             Recent activities 
                                            <div class="bg-white p-6 rounded-xl shadow-sm border border-gray-100">
                                                <h2 class="text-lg font-semibold text-gray-800 mb-4">Hoạt động gần đây</h2>
                                                <div class="space-y-4">
                                                    <div class="flex items-start space-x-3">
                                                        <div class="w-8 h-8 rounded-full bg-green-100 flex items-center justify-center mt-1 flex-shrink-0">
                                                            <i class="fas fa-user-plus text-green-600 text-xs"></i>
                                                        </div>
                                                        <div>
                                                            <p class="text-sm font-medium text-gray-800">Học sinh mới đăng ký</p>
                                                            <p class="text-xs text-gray-500">Nguyễn Văn A đã đăng ký lớp Toán 12</p>
                                                            <p class="text-xs text-gray-400 mt-1">10 phút trước</p>
                                                        </div>
                                                    </div>
                    
                                                    <div class="flex items-start space-x-3">
                                                        <div class="w-8 h-8 rounded-full bg-blue-100 flex items-center justify-center mt-1 flex-shrink-0">
                                                            <i class="fas fa-money-bill-wave text-blue-600 text-xs"></i>
                                                        </div>
                                                        <div>
                                                            <p class="text-sm font-medium text-gray-800">Thanh toán học phí</p>
                                                            <p class="text-xs text-gray-500">Trần Thị B đã thanh toán học phí tháng 6</p>
                                                            <p class="text-xs text-gray-400 mt-1">45 phút trước</p>
                                                        </div>
                                                    </div>
                    
                                                    <div class="flex items-start space-x-3">
                                                        <div class="w-8 h-8 rounded-full bg-purple-100 flex items-center justify-center mt-1 flex-shrink-0">
                                                            <i class="fas fa-calendar-alt text-purple-600 text-xs"></i>
                                                        </div>
                                                        <div>
                                                            <p class="text-sm font-medium text-gray-800">Lịch học thay đổi</p>
                                                            <p class="text-xs text-gray-500">Lớp Văn 10 chuyển từ thứ 3 sang thứ 5</p>
                                                            <p class="text-xs text-gray-400 mt-1">2 giờ trước</p>
                                                        </div>
                                                    </div>
                    
                                                    <div class="flex items-start space-x-3">
                                                        <div class="w-8 h-8 rounded-full bg-yellow-100 flex items-center justify-center mt-1 flex-shrink-0">
                                                            <i class="fas fa-chalkboard-teacher text-yellow-600 text-xs"></i>
                                                        </div>
                                                        <div>
                                                            <p class="text-sm font-medium text-gray-800">Giáo viên mới</p>
                                                            <p class="text-xs text-gray-500">Thầy Nguyễn Văn C đã tham gia giảng dạy</p>
                                                            <p class="text-xs text-gray-400 mt-1">5 giờ trước</p>
                                                        </div>
                                                    </div>
                    
                                                    <div class="flex items-start space-x-3">
                                                        <div class="w-8 h-8 rounded-full bg-red-100 flex items-center justify-center mt-1 flex-shrink-0">
                                                            <i class="fas fa-exclamation-triangle text-red-600 text-xs"></i>
                                                        </div>
                                                        <div>
                                                            <p class="text-sm font-medium text-gray-800">Cảnh báo</p>
                                                            <p class="text-xs text-gray-500">Lớp Hóa 11 sắp đạt sĩ số tối đa</p>
                                                            <p class="text-xs text-gray-400 mt-1">8 giờ trước</p> 
                                                        </div> 
                                                    </div> 
                                                </div> 
                                            </div> 
                                        </div>-->
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

        <!--        <script>
                    // Mobile sidebar toggle
                    document.getElementById('sidebarToggle').addEventListener('click', function () {
                        document.querySelector('.sidebar').classList.toggle('active');
                        document.querySelector('.overlay').classList.toggle('active');
                    });
        
                    document.querySelector('.overlay').addEventListener('click', function () {
                        document.querySelector('.sidebar').classList.remove('active');
                        this.classList.remove('active');
                    });
        
        // Chart data and configuration
                    const monthLabels = ['Tuần 1', 'Tuần 2', 'Tuần 3', 'Tuần 4'];
                    const quarterLabels = ['Tháng 1', 'Tháng 2', 'Tháng 3'];
                    const yearLabels = ['Q1', 'Q2', 'Q3', 'Q4'];
        
                    const monthData = [15, 22, 18, 30];
                    const quarterData = [75, 82, 90];
                    const yearData = [250, 280, 300, 320];
        
                    const ctx = document.getElementById('studentChart').getContext('2d');
                    let studentChart = new Chart(ctx, {
                        type: 'bar',
                        data: {
                            labels: monthLabels,
                            datasets: [{
                                    label: 'Số học sinh đăng ký',
                                    data: monthData,
                                    backgroundColor: '#6366f1',
                                    borderColor: '#4f46e5',
                                    borderWidth: 1
                                }]
                        },
                        options: {
                            responsive: true,
                            maintainAspectRatio: false,
                            scales: {
                                y: {
                                    beginAtZero: true,
                                    ticks: {
                                        stepSize: 10
                                    }
                                }
                            }
                        }
                    });
        
        // Chart period toggle buttons
                    document.getElementById('monthBtn').addEventListener('click', function () {
                        updateChart(monthLabels, monthData);
                        setActiveButton(this);
                    });
        
                    document.getElementById('quarterBtn').addEventListener('click', function () {
                        updateChart(quarterLabels, quarterData);
                        setActiveButton(this);
                    });
        
                    document.getElementById('yearBtn').addEventListener('click', function () {
                        updateChart(yearLabels, yearData);
                        setActiveButton(this);
                    });
        
                    function updateChart(labels, data) {
                        studentChart.data.labels = labels;
                        studentChart.data.datasets[0].data = data;
                        studentChart.update();
                    }
        
                    function setActiveButton(activeButton) {
                        const buttons = document.querySelectorAll('#monthBtn, #quarterBtn, #yearBtn');
                        buttons.forEach(button => {
                            if (button === activeButton) {
                                button.classList.remove('bg-gray-100', 'text-gray-600');
                                button.classList.add('bg-indigo-100', 'text-indigo-700');
                            } else {
                                button.classList.remove('bg-indigo-100', 'text-indigo-700');
                                button.classList.add('bg-gray-100', 'text-gray-600');
                            }
                        });
                    }
        
        // Sidebar menu active state and dynamic content loading
                    const menuItems = document.querySelectorAll('.sidebar-item');
                    menuItems.forEach(item => {
                        item.addEventListener('click', function (e) {
                            e.preventDefault();
                            menuItems.forEach(i => i.classList.remove('active'));
                            this.classList.add('active');
                            const content = this.getAttribute('data-content');
                            let url = '';
        
                            switch (content) {
                                case 'overview':
                                    url = 'overview.jsp';
                                    break;
                                case 'students':
                                    url = 'students.jsp';
                                    break;
                                case 'teachers':
                                    url = 'teachers.jsp';
                                    break;
                                case 'subjects':
                                    url = 'subjects.jsp';
                                    break;
                                case 'schedule':
                                    url = 'schedule.jsp';
                                    break;
                                case 'fees':
                                    url = 'fees.jsp';
                                    break;
                                case 'reports':
                                    url = 'reports.jsp';
                                    break;
                                case 'settings':
                                    url = 'setting.jsp';
                                    break;
                            }
        
                            if (url) {
                                fetch(url)
                                        .then(response => response.text())
                                        .then(html => {
                                            document.getElementById('mainContent').innerHTML = html;
                                            if (content === 'settings') {
                                                const script = document.createElement('script');
                                                script.textContent = `
                                    document.addEventListener('DOMContentLoaded', () => {
                                        console.log('Reinitializing settings tabs');
                                        const initializeSettingsTabs = () => {
                                            const tabs = document.querySelectorAll('.settings-tab');
                                            if (tabs.length === 0) {
                                                console.error('No .settings-tab elements found!');
                                                return;
                                            }
                                            tabs.forEach(tab => {
                                                tab.addEventListener('click', (e) => {
                                                    e.preventDefault();
                                                    tabs.forEach(t => t.classList.remove('active'));
                                                    document.querySelectorAll('.tab-content').forEach(content => content.classList.remove('active'));
                                                    tab.classList.add('active');
                                                    const tabId = tab.dataset.tab;
                                                    const activeTabContent = document.getElementById(tabId + '-tab');
                                                    if (activeTabContent) activeTabContent.classList.add('active');
                                                    // Tải nội dung động cho tab
                                                    let subUrl = '';
                                                    switch (tabId) {
                                                        case 'general':
                                                            subUrl = '${pageContext.request.contextPath}/home';
                                                            break;
                                                        case 'security':
                                                            subUrl = 'security-settings.jsp';
                                                            break;
                                                        // Thêm các tab khác nếu cần
                                                        default:
                                                            subUrl = '';
                                                    }
                                                    if (subUrl) {
                                                        fetch(subUrl)
                                                            .then(resp => resp.text())
                                                            .then(subHtml => {
                                                                activeTabContent.innerHTML = subHtml;
                                                            })
                                                            .catch(err => console.error('Error loading sub-content:', err));
                                                    }
                                                });
                                            });
                                            if (tabs.length > 0) tabs[0].click(); // Mặc định chọn tab đầu tiên
                                        };
                                        initializeSettingsTabs();
                                    });
                                `;
                                                document.body.appendChild(script);
                                            }
                                        })
                                        .catch(error => console.error('Error loading content:', error));
                            }
                        });
                    });
                </script>-->
        <script>
    // ========== PHÂN TRANG KHÓA HỌC ==========
let itemsPerPage = 8, totalItems = 0, totalPages = 0, currentPage = 1;

function displayPage(page) {
    page = parseInt(page);
    if (isNaN(page) || page < 1) page = 1;
    if (page > totalPages && totalPages > 0) page = totalPages;
    const items = document.querySelectorAll('#courseContainer .col-lg-3');
    if (!items.length) return;
    items.forEach((item, index) => {
        const pageIndex = Math.floor(index / itemsPerPage) + 1;
        item.classList.toggle('hidden', pageIndex !== page);
    });
    updatePagination(page);
}

function updatePagination(page) {
    const pagination = document.getElementById('pagination');
    if (!pagination) return;
    pagination.innerHTML = '';
    // Previous
    const prevLi = document.createElement('li');
    prevLi.className = page === 1 ? 'page-item disabled' : 'page-item';
    const prevA = document.createElement('a');
    prevA.className = 'page-link';
    prevA.href = '#';
    prevA.textContent = '«';
    prevA.dataset.action = 'prev';
    prevLi.appendChild(prevA);
    pagination.appendChild(prevLi);
    // Pages
    for (let i = 1; i <= totalPages; i++) {
        const li = document.createElement('li');
        li.className = i === page ? 'page-item active' : 'page-item';
        const a = document.createElement('a');
        a.className = 'page-link';
        a.href = '#';
        a.textContent = i;
        a.dataset.page = i;
        li.appendChild(a);
        pagination.appendChild(li);
    }
    // Next
    const nextLi = document.createElement('li');
    nextLi.className = page === totalPages ? 'page-item disabled' : 'page-item';
    const nextA = document.createElement('a');
    nextA.className = 'page-link';
    nextA.href = '#';
    nextA.textContent = '»';
    nextA.dataset.action = 'next';
    nextLi.appendChild(nextA);
    pagination.appendChild(nextLi);
}

$(document).ready(function () {
    // Khởi tạo phân trang khi DOM ready
    const urlParams = new URLSearchParams(window.location.search);
    currentPage = parseInt(urlParams.get('page')) || 1;
    const items = document.querySelectorAll('#courseContainer .col-lg-3');
    totalItems = items.length;
    if (totalItems > 0) {
        totalPages = Math.ceil(totalItems / itemsPerPage);
        displayPage(currentPage);
    }
    const pagination = document.getElementById('pagination');
    if (pagination) {
        pagination.addEventListener('click', (e) => {
            const link = e.target.closest('.page-link');
            if (!link) return;
            e.preventDefault();
            if (link.dataset.action) {
                if (link.dataset.action === 'prev' && currentPage > 1) currentPage--;
                else if (link.dataset.action === 'next' && currentPage < totalPages) currentPage++;
                else return;
            } else {
                const pageNum = link.dataset.page;
                if (!pageNum || isNaN(parseInt(pageNum))) return;
                currentPage = parseInt(pageNum);
            }
            const url = new URL(window.location);
            url.searchParams.set('page', currentPage);
            window.history.pushState({}, '', url);
            displayPage(currentPage);
        });
    }

    // ========== MÔN HỌC/GRADE/NAV-LINK ==========
    let currentGradeId = null;
    $('.subjects-list').css('display', 'flex');
    $('#course-content').css('display', 'none');
    $('#navbar-vertical .nav-link').removeClass('active');

    $('#subjects-btn').click(function (e) {
        e.preventDefault();
        $('#course-content').css('display', 'none');
        $('#navbar-vertical .nav-link').removeClass('active');
        currentGradeId = null;
        $('.grade-link').removeClass('active selected-grade');
    });

    $(document).on('click', '.grade-link', function (e) {
        e.preventDefault();
        const gradeId = $(this).data('grade');
        $('.grade-link').removeClass('active selected-grade');
        $(this).addClass('active selected-grade');
        currentGradeId = gradeId;
        $('#course-content').css('display', 'none');
        $('#navbar-vertical .nav-link').removeClass('active');
        $('#navbar-vertical .nav-link').each(function () {
            const subjectId = ($(this).attr('data-panel') || '').split('-')[2];
            if (subjectId) $(this).attr('data-panel', 'panel-' + currentGradeId + '-' + subjectId);
        });
    });

    $(document).on('click', '#navbar-vertical .nav-link', function (e) {
        e.preventDefault();
        const panelId = $(this).attr('data-panel');
        if (!panelId || !currentGradeId) return;
        const $panel = $('#' + panelId);
        if (!$panel.length) return;
        $('#course-content').css('display', 'block');
        $('.course-panel').removeClass('active').css('display', 'none');
        $('.course-panel .column').removeClass('active').css('display', 'none');
        $panel.addClass('active').css('display', 'block');
        $panel.find('.tab-link').removeClass('active');
        const $documentsTab = $panel.find('.tab-link[data-tab="documents"]');
        $documentsTab.addClass('active');
        $panel.find('.column').removeClass('active').css('display', 'none');
        const $documentsColumn = $panel.find('.column.documents');
        if ($documentsColumn.length) $documentsColumn.addClass('active').css('display', 'flex');
        $('#navbar-vertical .nav-link').removeClass('active');
        $(this).addClass('active');
    });

    $(document).on('mouseenter', '.tab-link', function () {
        const $panel = $(this).closest('.course-panel');
        let tab = $(this).data('tab');
        tab = typeof tab === 'string' ? tab.replace(/[^\w\s-]/g, '').trim() : 'documents';
        $panel.find('.tab-link').removeClass('active');
        $panel.find('.column').removeClass('active').css('display', 'none');
        const $targetColumn = $panel.find('.column.' + tab);
        if ($targetColumn.length) $targetColumn.addClass('active').css('display', 'flex');
        else {
            const $defaultColumn = $panel.find('.column.documents');
            if ($defaultColumn.length) {
                $panel.find('.tab-link[data-tab="documents"]').addClass('active');
                $defaultColumn.addClass('active').css('display', 'flex');
            }
        }
    });

    $(document).on('click', '.tab-link', function (e) {
        e.preventDefault();
        const $panel = $(this).closest('.course-panel');
        const tab = $(this).data('tab');
        $panel.find('.tab-link').removeClass('active');
        $panel.find('.column').removeClass('active').css('display', 'none');
        $(this).addClass('active');
        const $targetColumn = $panel.find('.column.' + tab);
        if ($targetColumn.length) $targetColumn.addClass('active').css('display', 'flex');
    });

    $(document).click(function (e) {
        const $target = $(e.target);
        const isInsideNavbarVertical = $target.closest('#navbar-vertical').length > 0;
        const isInsideCourseContent = $target.closest('#course-content').length > 0;
        const isSubjectsButton = $target.closest('#subjects-btn').length > 0;
        if (!isInsideNavbarVertical && !isInsideCourseContent && !isSubjectsButton) {
            $('#course-content').css('display', 'none');
            $('#navbar-vertical .nav-link').removeClass('active');
            $('.grade-link').removeClass('active selected-grade');
            currentGradeId = null;
        }
    });

    // ========== OWL CAROUSEL & ĐỒNG BỘ CAO ==========
    $('.owl-carousel').owlCarousel({
        loop: true,
        margin: 20,
        nav: true,
        dots: true,
        responsive: {0: {items: 1}, 600: {items: 2}, 1000: {items: 3}}
    });
    $(".owl-carousel").on('initialized.owl.carousel refreshed.owl.carousel resized.owl.carousel changed.owl.carousel', function () {
        setTimeout(equalizeTeamItemHeights, 200);
    });
    $(window).on('resize', function () {
        setTimeout(equalizeTeamItemHeights, 200);
    });
    function equalizeTeamItemHeights() {
        var maxHeight = 0;
        $('.team-item').css('height', 'auto');
        $('.team-item').each(function () {
            var h = $(this).outerHeight();
            if (h > maxHeight) maxHeight = h;
        });
        $('.team-item').css('height', maxHeight + 'px');
    }
});

// ========== MODAL CHI TIẾT KHÓA HỌC ==========
window.showCourseDetail = function (btn) {
    const get = key => btn.getAttribute('data-' + key) || '';
    document.getElementById('modalCourseName').textContent = get('classname') || 'Chưa xác định';
    document.getElementById('modalCourseDescrip').textContent = get('descrip') || 'Chưa có mô tả';
    document.getElementById('modalIsHot').textContent = (get('ishot') === "true" || get('ishot') === "1") ? "Nổi bật" : "Quanh năm";
    document.getElementById('modalgradeName').textContent = get('gradename') || 'Chưa xác định';
    document.getElementById('modalSubjectName').textContent = get('subject') || 'Chưa xác định';
    document.getElementById('modalDuration').textContent = (get('duration') || 'Chưa xác định') + ' / 1 buổi';
    document.getElementById('modalStartDate').textContent = get('startdate') || 'Chưa xác định';
    document.getElementById('modalEndDate').textContent = get('enddate') || 'Chưa xác định';
    function formatMoneyVND(amount) {
        if (!amount || isNaN(amount)) return 'Chưa xác định';
        return Number(amount).toLocaleString('vi-VN') + " VNĐ / 1 buổi";
    }
    document.getElementById('modalTuitionFee').textContent = formatMoneyVND(get('tuition'));
    document.getElementById('joinCourseBtn').href = 'login_register.jsp?redirect=course&courseId=' + (get('classid') || '');
    const container = document.getElementById('classGroupContent');
    let html = '<p>Chưa có nhóm lớp nào.</p>';
    const classGroupsStr = get('classgroups');
    if (classGroupsStr) {
        const groupsArr = classGroupsStr.split(';').filter(Boolean);
        if (groupsArr.length > 0) {
            html = `<h6>Danh sách nhóm lớp:</h6>
            <table class="table table-bordered" style="width:100%">
                <thead>
                    <tr>
                        <th>Tên nhóm</th>
                        <th>Sĩ số tối đa</th>
                        <th>Phòng</th>
                        <th>Giáo viên</th>
                        <th>Thứ trong tuần</th>
                        <th>Bắt đầu</th>
                        <th>Kết thúc</th>
                    </tr>
                </thead>
                <tbody>`;
            groupsArr.forEach(item => {
                const parts = item.split('~');
                while (parts.length < 7) parts.push('');
                html += '<tr>'
    + '<td>' + (parts[0] || '') + '</td>'
    + '<td>' + (parts[1] || '') + '</td>'
    + '<td>' + (parts[2] || '') + '</td>'
    + '<td>' + (parts[3] || '') + '</td>'
    + '<td>' + ((!parts[4] || String(parts[4]).trim().toLowerCase() === "null") ? "Chưa xếp lịch" : parts[4]) + '</td>'
    + '<td>' + formatTime(parts[5]) + '</td>'
    + '<td>' + formatTime(parts[6]) + '</td>'
    + '</tr>';
            });
            html += `</tbody></table>`;
        }
    }
    if (container) container.innerHTML = html;
    document.getElementById('courseDetailModal').style.display = 'block';
    document.body.style.overflow = 'hidden';
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
window.closeModalClass = function () {
    document.getElementById('courseDetailModal').style.display = 'none';
    document.body.style.overflow = '';
};

// ========== MODAL GIÁO VIÊN ==========
window.showTeacherDetail = function (fullName, gender, phone, email, certi, descrip, onlineStatus, schoolName) {
    document.getElementById('modalTeacherFullName').textContent = fullName || 'Chưa xác định';
    document.getElementById('modalTeacherTitle').textContent = fullName || 'Chưa xác định';
    document.getElementById('modalTeacherGender').textContent = gender || 'Chưa xác định';
    document.getElementById('modalTeacherPhone').textContent = phone || 'Chưa xác định';
    document.getElementById('modalTeacherEmail').textContent = email || 'Chưa xác định';
    document.getElementById('modalTeacherCerti').textContent = certi || 'Chưa xác định';
    document.getElementById('modalTeacherDescrip').textContent = descrip || 'Chưa có mô tả';
    document.getElementById('modalTeacherOnlineStatus').textContent = onlineStatus || 'Không hoạt động';
    document.getElementById('modalTeacherSchool').textContent = schoolName || 'Giáo viên của Edura';
    $('#teacherDetailModal').modal('show');
};
window.closeTeacherModal = function () {
    $('#teacherDetailModal').modal('hide');
};
</script>
<script>
    $(document).on('click', '.settings-tab[data-url]', function(e){
    e.preventDefault();
    var url = $(this).data('url');
    $('#setting-content').load(url, function(){
        // Nếu home cần JS khởi tạo lại sau khi load xong, gọi ở đây
        if (typeof initHomeDashboardFeatures === 'function') {
            initHomeDashboardFeatures();
        }
    });
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
            top: verticalOffset.top + 'px',          // Ngay hàng với navbar dọc
            left: (verticalOffset.left + verticalWidth) + 'px', // Ngay bên phải
            zIndex: 9999
        });
    }
});

</script>


    </body>
</html>
