<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="utf-8"> <!-- Đặt mã hóa ký tự là UTF-8 để hỗ trợ tiếng Việt -->
    <meta name="viewport" content="width=device-width, initial-scale=1"> <!-- Hỗ trợ responsive -->
    <meta name="description" content="Trang dashboard cho quản lý trung tâm dạy văn hóa">
    <meta name="author" content="Edura Team">
    <title>Dashboard Báo Cáo - Trung Tâm Dạy Văn Hóa</title>

    <!-- Thư viện CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=League+Spartan:wght@100;300;400;600;700&display=swap" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/owl.carousel.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/owl.theme.default.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/tooplate-gotto-job.css" rel="stylesheet">
    
    <!-- Thư viện Chart.js -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

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
            font-size: 16px;
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
            gap: 15px;
            width: 100%;
        }
        .menu-group a {
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
        }
        .header-icons {
            position: absolute;
            top: 20px;
            right: 20px;
            display: flex;
            align-items: center;
            gap: 15px;
        }
        .header-icons a {
            color: #333;
            font-size: 24px;
            text-decoration: none;
            transition: color 0.3s;
        }
        .header-icons a:hover {
            color: #FF6B6B;
        }
        .header-icons .user-dropdown {
            position: relative;
            display: inline-block;
        }
        .header-icons .user-dropdown .dropbtn {
            background: none;
            border: none;
            padding: 0;
            font-size: 18px;
            color: #333;
            cursor: pointer;
            display: flex;
            align-items: center;
        }
        .header-icons .user-dropdown .dropdown-content {
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
        .header-icons .user-dropdown:hover .dropdown-content {
            display: block;
        }
        .header-icons .user-dropdown .dropdown-content a {
            display: block;
            padding: 5px 10px;
            color: #333;
            text-decoration: none;
            font-size: 14px;
        }
        .header-icons .user-dropdown .dropdown-content a:hover {
            color: #FF6B6B;
        }
        .stats {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            grid-template-rows: auto auto;
            gap: 20px;
            margin-bottom: 20px;
        }
        .stat-box {
            background-color: #FFF1F1;
            padding: 20px;
            border-radius: 8px;
            text-align: center;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            font-size: 16px;
            font-family: 'League Spartan', 'Segoe UI Emoji', sans-serif;
        }
        .stat-box:nth-child(1) { grid-column: 1; grid-row: 1; }
        .stat-box:nth-child(2) { grid-column: 2; grid-row: 1; }
        .stat-box:nth-child(3) { grid-column: 3; grid-row: 1; }
        .stat-box:nth-child(4) { grid-column: 1; grid-row: 2; }
        .stat-box:nth-child(5) { grid-column: 2; grid-row: 2; }
        @media (max-width: 768px) {
            .stats {
                grid-template-columns: 1fr;
                grid-template-rows: repeat(5, auto);
            }
            .stat-box:nth-child(1) { grid-column: 1; grid-row: 1; }
            .stat-box:nth-child(2) { grid-column: 1; grid-row: 2; }
            .stat-box:nth-child(3) { grid-column: 1; grid-row: 3; }
            .stat-box:nth-child(4) { grid-column: 1; grid-row: 4; }
            .stat-box:nth-child(5) { grid-column: 1; grid-row: 5; }
        }
        .charts {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 20px;
        }
        .chart-box {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            max-height: 500px; /* Tăng chiều cao để hiển thị nhãn rõ hơn */
            overflow: hidden;
        }
        .chart-box h2 {
            font-size: 14px;
            font-weight: bold;
            margin-bottom: 8px;
        }
        .chart-box p {
            font-size: 14px;
            font-weight: bold;
            margin-bottom: 8px;
        }
        .chart-box label {
            font-family: 'League Spartan';
        }
        .chart-placeholder {
            max-height: 450px; /* Tăng chiều cao để hiển thị nhãn rõ hơn */
            width: 100%;
            min-height: 350px; /* Đảm bảo chiều cao tối thiểu */
        }
        .status-circle {
            display: inline-block;
            width: 10px;
            height: 10px;
            border-radius: 50%;
            margin-right: 5px;
        }
        .status-circle.green { background-color: #4CAF50; }
        .status-circle.yellow { background-color: #FFC107; }
        .status-circle.orange { background-color: #FF9800; }
        .status-circle.red { background-color: #F44336; }
        .schedule-cell {
            padding: 2px;
            text-align: center;
        }
        .schedule-cell.green { background-color: #4CAF50; color: white; }
        .schedule-cell.yellow { background-color: #FFC107; }
        .schedule-cell.orange { background-color: #FF9800; color: white; }
        .schedule-cell.red { background-color: #F44336; color: white; }
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
            .charts {
                grid-template-columns: 1fr;
                grid-template-rows: auto auto;
            }
            .chart-box {
                max-height: 550px; /* Tăng chiều cao trên di động */
            }
            .chart-placeholder {
                max-height: 500px; /* Tăng chiều cao trên di động */
                min-height: 400px; /* Đảm bảo chiều cao tối thiểu */
            }
        }
    </style>

    <script>
        // Hàm mở/đóng menu dropdown
        function toggleDropdown() {
            const dropdown = document.getElementById('userDropdown');
            dropdown.classList.toggle('show');
        }

        // Đóng dropdown khi nhấp ra ngoài
        window.addEventListener('click', function(e) {
            const button = document.getElementById('userButton');
            const dropdown = document.getElementById('userDropdown');
            if (!button.contains(e.target) && !dropdown.contains(e.target)) {
                dropdown.classList.remove('show');
            }
        });

        // Biến lưu biểu đồ lớp học
        let classChart;

        // Hàm cập nhật biểu đồ lớp học
        function updateClassChart() {
            const grade = document.getElementById('gradeFilter').value;
            if (classChart) classChart.destroy();

            // Log dữ liệu để kiểm tra
            console.log('Grade:', grade);
            console.log('Labels:', ['Toán', 'Lý', 'Hóa', 'Văn', 'Anh', 'Sinh', 'Sử', 'Địa']);
            console.log('classDataAll:', [<c:forEach items="${classDataAll}" var="data">${data},</c:forEach>]);
            console.log('classData10:', [<c:forEach items="${classData10}" var="data">${data},</c:forEach>]);
            console.log('classData11:', [<c:forEach items="${classData11}" var="data">${data},</c:forEach>]);
            console.log('classData12:', [<c:forEach items="${classData12}" var="data">${data},</c:forEach>]);

            // Dữ liệu cho biểu đồ
            const data = {
                'all': [<c:forEach items="${classDataAll}" var="data">${data},</c:forEach>],
                '10': [<c:forEach items="${classData10}" var="data">${data},</c:forEach>],
                '11': [<c:forEach items="${classData11}" var="data">${data},</c:forEach>],
                '12': [<c:forEach items="${classData12}" var="data">${data},</c:forEach>]
            };

            // Tạo biểu đồ cột
            classChart = new Chart(document.getElementById('classChart'), {
                type: 'bar',
                data: {
                    labels: ['Toán', 'Lý', 'Hóa', 'Văn', 'Anh', 'Sinh', 'Sử', 'Địa'],
                    datasets: [{
                        label: 'Số Lớp Học',
                        data: data[grade] || [0, 0, 0, 0, 0, 0, 0, 0], // Mặc định 0 nếu dữ liệu lỗi
                        backgroundColor: ['#36A2EB', '#FF6384', '#FFCE56', '#4BC0C0', '#9966FF', '#FF9F40', '#FF5733', '#C70039'],
                        borderColor: ['#36A2EB', '#FF6384', '#FFCE56', '#4BC0C0', '#9966FF', '#FF9F40', '#FF5733', '#C70039'],
                        borderWidth: 1
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    scales: {
                        y: {
                            beginAtZero: true,
                            max: 20,
                            ticks: {
                                callback: function(value) {
                                    const ticks = [0, 5, 10, 15, 20];
                                    return ticks.includes(value) ? value : null;
                                },
                                stepSize: 1,
                                font: { size: 10, family: 'League Spartan' }
                            },
                            title: {
                                display: true,
                                text: 'Số Lớp',
                                font: { size: 12, family: 'League Spartan' }
                            }
                        },
                        x: {
                            title: {
                                display: true,
                                text: 'Môn Học',
                                font: { size: 12, family: 'League Spartan' }
                            },
                            ticks: {
                                font: { size: 10, family: 'League Spartan' }, // Tăng kích thước font
                                maxRotation: 0, // Nhãn nằm ngang
                                minRotation: 0,
                                autoSkip: false, // Đảm bảo hiển thị tất cả nhãn
                                padding: 15 // Tăng padding để chữ không bị cắt
                            }
                        }
                    },
                    plugins: {
                        legend: {
                            labels: { font: { size: 10, family: 'League Spartan' } }
                        },
                        tooltip: {
                            callbacks: {
                                label: function(context) {
                                    return `Số lớp: ${context.parsed.y} (${context.label})`;
                                }
                            }
                        }
                    }
                }
            });
        }

        // Khi trang tải
        document.addEventListener('DOMContentLoaded', () => {
            updateClassChart();
            window.addEventListener('scroll', function () {
                const backTop = document.querySelector('.back-top-icon');
                if (window.scrollY > 300) {
                    backTop.classList.add('visible');
                } else {
                    backTop.classList.remove('visible');
                }
            });
        });
    </script>
</head>
<body id="top">
    <!-- Header -->
    <div class="container-fluid top-header">
        <div class="row w-100 justify-content-around align-items-center">
            <div class="logo">
                <img src="${pageContext.request.contextPath}/LogoServlet" alt="Logo EDURA" class="logo-img">
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

    <!-- Tiêu đề -->
    <h1 class="dashboard-title">
        Tổng quan hệ thống
        <div class="header-icons">
            <a href="mailbox" title="Hộp thư"><i class="fas fa-envelope"></i></a>
            <div class="user-dropdown">
                <button class="dropbtn" id="userButton">${sessionScope.userName} <span class="ml-1">▼</span></button>
                <div class="dropdown-content" id="userDropdown">
                    <a href="profile" title="Hồ sơ">Hồ sơ</a>
                    <a href="logout" title="Đăng xuất">Đăng xuất</a>
                </div>
            </div>
            <a href="settings" title="Cài đặt"><i class="fas fa-cog"></i></a>
        </div>
    </h1>

    <!-- Nội dung chính -->
    <div class="container-fluid">
        <div class="row">
            <!-- Sidebar -->
            <div class="col-md-3 sidebar">
                <div class="avatar">
                    <img src="${sessionScope.userAvatar}" alt="Avatar" class="avatar-img" 
                         onerror="this.src='${pageContext.request.contextPath}/images/default-img.png';">
                </div>
                <div class="username">${sessionScope.userName}</div>
                <div class="menu-group">
                    <a href="${pageContext.request.contextPath}/home"><i class="fas fa-home"></i> Trang chủ</a>
                    <a href="managerUser"><i class="fas fa-users"></i> Quản lý người dùng</a>
                    <a href="managerSchedule"><i class="fas fa-calendar-alt"></i> Quản lý thời khóa biểu</a>
                    <a href="managerTutoringClass"><i class="fas fa-school"></i> Danh sách lớp học</a>
                    <a href="managerTuition"><i class="fas fa-file-invoice-dollar"></i> Báo cáo học phí</a>
                    <a href="managerPaymentHistory"><i class="fas fa-history"></i> Lịch sử thanh toán</a>
                    <a href="mailbox"><i class="fas fa-envelope"></i> Hộp thư</a>
                    <a href="logout" title="Đăng xuất"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a>
                </div>
            </div>

            <!-- Nội dung chính -->
            <div class="col-md-9 main">
                <!-- Hộp thống kê -->
                <div class="stats">
                    <div class="stat-box">🎓 Học sinh: ${studentCount}</div>
                    <div class="stat-box">🧑‍🏫 Giáo viên: ${teacherCount}</div>
                    <div class="stat-box">📅 Lịch học hôm nay: ${todayScheduleCount}</div>
                    <div class="stat-box">🏫 Số lớp học: ${classCount}</div>
                    <div class="stat-box">📥 Đơn chờ phản hồi: ${pendingRequestCount}</div>
                </div>

                <!-- Khu vực biểu đồ -->
                <div class="charts">
                    <!-- Biểu đồ phân bố lịch học -->
                    <div class="chart-box">
                        <div class="d-flex justify-content-between align-items-center mb-2">
                            <h2 class="text-xl font-weight-bold">Phân bố lịch học theo tuần</h2>
                            <form action="${pageContext.request.contextPath}/managerDashboard" method="get">
                                <select id="weekFilter" name="weekFilter" onchange="this.form.submit()" class="form-control form-control-sm">
                                    <option value="2025-06-02 - 2025-06-08" ${selectedWeek == '2025-06-02 - 2025-06-08' ? 'selected' : ''}>Tuần 1: 2/6 - 8/6</option>
                                    <option value="2025-06-10 - 2025-06-16" ${selectedWeek == '2025-06-10 - 2025-06-16' ? 'selected' : ''}>Tuần 2: 10/6 - 16/6</option>
                                    <option value="2025-06-17 - 2025-06-23" ${selectedWeek == '2025-06-17 - 2025-06-23' ? 'selected' : ''}>Tuần 3: 17/6 - 23/6</option>
                                    <option value="2025-06-24 - 2025-06-30" ${selectedWeek == '2025-06-24 - 2025-06-30' ? 'selected' : ''}>Tuần 4: 24/6 - 30/6</option>
                                </select>
                            </form>
                        </div>
                        <table class="table table-bordered text-center" id="scheduleTable">
                            <thead class="bg-light">
                                <tr>
                                    <th>Ngày</th>
                                    <th>7:00</th>
                                    <th>9:30</th>
                                    <th>13:30</th>
                                    <th>15:45</th>
                                    <th>17:30</th>
                                    <th>19:30</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="item" items="${schedule}">
                                    <tr>
                                        <td>${item.day}</td>
                                        <c:forEach var="slot" items="${item.slots}">
                                            <c:choose>
                                                <c:when test="${slot == 0}">
                                                    <td class="schedule-cell green">${slot}</td>
                                                </c:when>
                                                <c:when test="${slot >= 1 && slot <= 3}">
                                                    <td class="schedule-cell yellow">${slot}</td>
                                                </c:when>
                                                <c:when test="${slot >= 4 && slot <= 6}">
                                                    <td class="schedule-cell orange">${slot}</td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td class="schedule-cell red">${slot}</td>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <div class="mt-2 text-sm">
                            <strong>Chú thích:</strong>
                            <span class="status-circle green"></span> Trống |
                            <span class="status-circle yellow"></span> Trung bình |
                            <span class="status-circle orange"></span> Đông |
                            <span class="status-circle red"></span> Quá tải
                        </div>
                    </div>

                    <!-- Biểu đồ số lớp học theo môn học -->
                    <div class="chart-box">
                        <p class="font-weight-bold text-sm mb-2">Số Lớp Học Theo Môn Học</p>
                        <div class="mb-2">
                            <form action="${pageContext.request.contextPath}/managerDashboard" method="get">
                                <input type="hidden" name="weekFilter" value="${selectedWeek}">
                                <label class="text-xs mr-1">Khối:</label>
                                <select id="gradeFilter" name="gradeFilter" onchange="this.form.submit()" class="form-control form-control-sm d-inline-block w-auto">
                                    <option value="all" ${selectedGrade == 'all' ? 'selected' : ''}>Tất cả</option>
                                    <option value="10" ${selectedGrade == '10' ? 'selected' : ''}>10</option>
                                    <option value="11" ${selectedGrade == '11' ? 'selected' : ''}>11</option>
                                    <option value="12" ${selectedGrade == '12' ? 'selected' : ''}>12</option>
                                </select>
                            </form>
                        </div>
                        <canvas id="classChart" class="chart-placeholder"></canvas>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Footer -->
    <footer class="site-footer">
        <div class="container-fluid bg-dark text-white py-0 px-sm-3 px-lg-5">
            <div class="row pt-5">
                <div class="col-lg-5 col-md-12 mb-5">
                    <a href="" class="text-decoration-none">
                        <div class="logo-container">
                            <img src="${pageContext.request.contextPath}/LogoServlet" alt="Logo Trung Tâm" class="logo-image" 
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
                            <h5 class="text-primary text-uppercase mb-4" style="letter-spacing: 5px;">Thông Tin Liên Hệ</h5>
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
                            <h5 class="text-primary text-uppercase mb-4" style="letter-spacing: 5px;">Khám Phá EDURA</h5>
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

    <!-- Thư viện JavaScript -->
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>