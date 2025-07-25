<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!-- Văn Thị Như - HE181329 
Ngày update 3/7/2025-->
<!-- Main content -->
<div class="flex-1 flex flex-col overflow-hidden">

    <!-- Main dashboard content -->
    <main class="flex-1 overflow-y-auto p-6 bg-gray-100">
        <!-- Overview widgets -->
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
            <!-- Class Count -->
            <div class="card bg-white rounded-lg shadow p-6 hover:shadow-md transition duration-300">
                <div class="flex items-center justify-between">
                    <div>
                        <div class="text-gray-500 text-sm font-medium">Lớp học đang mở</div>
                        <div class="text-3xl font-bold mt-1">${openClassCount}</div>
                        <!--                                <div class="text-green-500 text-xs mt-2 flex items-center">
                                                            <i class="fas fa-arrow-up mr-1"></i>
                                                            <span>5 lớp mới tuần này</span>
                                                        </div>-->
                    </div>
                    <div class="bg-blue-100 p-3 rounded-full text-blue-600">
                        <i class="fas fa-chalkboard-teacher text-xl"></i>
                    </div>
                </div>
            </div>

            <!-- Teacher Count -->
            <div class="card bg-white rounded-lg shadow p-6 hover:shadow-md transition duration-300">
                <div class="flex items-center justify-between">
                    <div>
                        <div class="text-gray-500 text-sm font-medium">Giáo viên</div>
                        <div class="text-3xl font-bold mt-1">${totalTeachers}</div>
                        <!--                                <div class="text-blue-500 text-xs mt-2 flex items-center">
                                                            <i class="fas fa-user-plus mr-1"></i>
                                                            <span>3 yêu cầu mới</span>
                                                        </div>-->
                    </div>
                    <div class="bg-green-100 p-3 rounded-full text-green-600">
                        <i class="fas fa-user-tie text-xl"></i>
                    </div>
                </div>
            </div>

            <!-- Student Count -->
            <div class="card bg-white rounded-lg shadow p-6 hover:shadow-md transition duration-300">
                <div class="flex items-center justify-between">
                    <div>
                        <div class="text-gray-500 text-sm font-medium">Học sinh</div>
                        <div class="text-3xl font-bold mt-1">${studentCount}</div>
                        <!--                                <div class="text-yellow-500 text-xs mt-2 flex items-center">
                                                            <i class="fas fa-signal mr-1"></i>
                                                            <span>12.5% tăng trưởng</span>
                                                        </div>-->
                    </div>
                    <div class="bg-purple-100 p-3 rounded-full text-purple-600">
                        <i class="fas fa-user-graduate text-xl"></i>
                    </div>
                </div>
            </div>

            <!-- Course Count -->
            <div class="card bg-white rounded-lg shadow p-6 hover:shadow-md transition duration-300">
                <div class="flex items-center justify-between">
                    <div>
                        <div class="text-gray-500 text-sm font-medium">Khóa học</div>
                        <div class="text-3xl font-bold mt-1">${classesCount}</div>
                        <!--                                <div class="text-red-500 text-xs mt-2 flex items-center">
                                                            <i class="fas fa-tag mr-1"></i>
                                                            <span>2 khóa sắp kết thúc</span>
                                                        </div>-->
                    </div>
                    <div class="bg-yellow-100 p-3 rounded-full text-yellow-600">
                        <i class="fas fa-book-open text-xl"></i>
                    </div>
                </div>
            </div>
        </div>

        <!-- Second row widgets -->
        <div class="grid grid-cols-1 lg:grid-cols-3 gap-6 mb-8">
            <!-- Today's Schedule -->
            <div class="card bg-white rounded-lg shadow p-6 hover:shadow-md transition duration-300">
                <div class="flex items-center justify-between mb-4">
                    <h2 class="font-semibold text-lg">Lịch học hôm nay</h2>
                    <a href="${pageContext.request.contextPath}/admin?tab=todaySchedule" class="text-blue-500 text-sm">Xem tất cả</a>

                </div>
                <div class="space-y-4">
                    <c:forEach var="item" items="${todaySchedules}" varStatus="status">
                        <c:if test="${status.index < 3}">
                            <div class="flex items-start">
                                <div class="bg-blue-100 text-blue-800 p-2 rounded-lg text-center mr-3 w-16">
                                    <div class="font-bold">
                                        <fmt:formatDate value="${item[3]}" pattern="HH:mm" />
                                    </div>
                                    <div class="text-xs">- <fmt:formatDate value="${item[4]}" pattern="HH:mm" /></div>
                                </div>
                                <div>
                                    <div class="font-medium">${item[0]}</div>
                                    <div class="text-sm text-gray-500">GV: ${item[1]}</div>
                                    <div class="text-sm text-gray-500">Phòng ${item[2]}</div>
                                </div>
                            </div>
                        </c:if>
                    </c:forEach>
                </div>
            </div>





            <!-- New Reviews -->
<!--            <div class="card bg-white rounded-lg shadow p-6 hover:shadow-md transition duration-300">
                <div class="flex items-center justify-between mb-4">
                    <h2 class="font-semibold text-lg">Đánh giá mới</h2>
                    <button class="text-blue-500 text-sm">Xem tất cả</button>
                </div>
                <div class="space-y-4">
                    <div class="flex items-start">
                        <img src="https://placehold.co/40x40" alt="Ảnh đại diện phụ huynh - người trung niên với biểu cảm tích cực" class="rounded-full mr-3">
                        <div>
                            <div class="font-medium">Phụ huynh Nguyễn Văn D</div>
                            <div class="text-sm text-gray-500 mb-1">Lớp Toán A1</div>
                            <div class="flex items-center text-yellow-400 mb-1">
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                            </div>
                            <p class="text-sm text-gray-700">Giáo viên dạy rất nhiệt tình, con tôi tiến bộ rõ rệt sau 2 tháng.</p>
                        </div>
                    </div>
                    <div class="flex items-start">
                        <img src="https://placehold.co/40x40" alt="Ảnh đại diện học sinh - học sinh cấp 2 với nụ cười tươi" class="rounded-full mr-3">
                        <div>
                            <div class="font-medium">Học sinh Trần Thị E</div>
                            <div class="text-sm text-gray-500 mb-1">Lớp Văn B2</div>
                            <div class="flex items-center text-yellow-400 mb-1">
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                                <i class="far fa-star"></i>
                            </div>
                            <p class="text-sm text-gray-700">Lớp học vui nhưng đôi khi giáo viên giải thích hơi nhanh.</p>
                        </div>
                    </div>
                </div>
            </div>-->

            <!-- Payment Status -->
            <div class="card bg-white rounded-lg shadow p-6 hover:shadow-md transition duration-300">
                <!-- Tiêu đề -->
                <div class="flex items-center justify-between mb-4">
                    <h2 class="font-semibold text-lg">Tình trạng thu học phí</h2>
                    <a href="${pageContext.request.contextPath}/admin?tab=paymentReport" class="text-blue-500 text-sm">Xem báo cáo</a>
                </div>

                <!-- Thống kê tổng quan -->
                <div class="flex items-center justify-between mb-4">
                    <div>
                        <div class="text-gray-500 text-sm">Đã thanh toán</div>
                        <div class="text-2xl font-bold mt-1">${percentPaid}%</div>
                    </div>
                    <div>
                        <div class="text-gray-500 text-sm">Còn nợ</div>
                        <div class="text-2xl font-bold mt-1 text-red-500">${percentUnpaid}%</div>
                    </div>
                </div>

                <!-- Thanh tiến trình -->
                <div class="mb-4">
                    <div class="w-full bg-gray-200 rounded-full h-2">
                        <div class="bg-green-500 h-2 rounded-full" style="width: ${percentPaid}%"></div>
                    </div>
                </div>

                <!-- Danh sách lớp và tình trạng -->
                <div class="space-y-3">
                    <c:forEach var="summary" items="${paymentSummaries}">
                        <div class="flex justify-between items-center">
                            <div class="text-sm">${summary[0]}</div> <!-- Tên lớp -->

                            <c:choose>
                                <c:when test="${summary[1] == 0}">
                                    <div class="bg-green-100 text-green-800 text-xs px-2 py-1 rounded">Đã đủ</div>
                                </c:when>
                                <c:when test="${summary[1] <= 3}">
                                    <div class="bg-yellow-100 text-yellow-800 text-xs px-2 py-1 rounded">Còn ${summary[1]}</div>
                                </c:when>
                                <c:otherwise>
                                    <div class="bg-red-100 text-red-800 text-xs px-2 py-1 rounded">Còn ${summary[1]}</div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </c:forEach>



                </div>
            </div>
        </div>

        <!-- Charts Section -->
        <div class="grid grid-cols-1 lg:grid-cols-2 gap-6 mb-8">
            <!-- Students by Class Chart -->
            <div class="card bg-white rounded-lg shadow p-6 hover:shadow-md transition duration-300">
                <h2 class="font-semibold text-lg mb-4">Biểu đồ học sinh theo môn học</h2>
                <canvas id="studentsByClassChart" height="250"></canvas>
            </div>

            <!-- Growth Chart -->
<!--            <div class="card bg-white rounded-lg shadow p-6 hover:shadow-md transition duration-300">
                <h2 class="font-semibold text-lg mb-4">Tăng trưởng số lượng</h2>
                <canvas id="growthChart" height="250"></canvas>
            </div>-->
        </div>

        <!-- Feedback Distribution Chart -->
<!--        <div class="card bg-white rounded-lg shadow p-6 hover:shadow-md transition duration-300 mb-8">
            <h2 class="font-semibold text-lg mb-4">Phản hồi đánh giá</h2>
            <div class="flex justify-center">
                <canvas id="feedbackChart" height="300" width="300"></canvas>
            </div>
            <div class="flex justify-center mt-4 space-x-6">
                <div class="flex items-center">
                    <div class="w-3 h-3 bg-green-500 rounded-full mr-2"></div>
                    <span class="text-sm">Hài lòng (75%)</span>
                </div>
                <div class="flex items-center">
                    <div class="w-3 h-3 bg-yellow-500 rounded-full mr-2"></div>
                    <span class="text-sm">Bình thường (20%)</span>
                </div>
                <div class="flex items-center">
                    <div class="w-3 h-3 bg-red-500 rounded-full mr-2"></div>
                    <span class="text-sm">Không hài lòng (5%)</span>
                </div>
            </div>
        </div>-->

        <!-- Schedule and Quick Actions -->
        <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
            <!-- Teacher Schedule -->
            <div class="card bg-white rounded-lg shadow p-6 hover:shadow-md transition duration-300">
                <div class="flex items-center justify-between mb-4">
                    <h2 class="font-semibold text-lg">Lịch dạy giáo viên</h2>
                    <a href="${pageContext.request.contextPath}/admin?tab=teacherSchedule" class="text-blue-500 text-sm">Xem tất cả</a>
                </div>

                <div class="space-y-4">
                    <c:if test="${not empty teacherSchedules}">
                        <c:set var="firstTeacher" value="${teacherSchedules[0][0]}" />
                        <div class="border-l-4 border-blue-500 pl-3 mb-2">
                            <div class="font-medium">${firstTeacher}</div>

                            <c:forEach var="entry" items="${teacherSchedules}">
                                <c:if test="${entry[0] eq firstTeacher}">
                                    <div class="text-sm text-gray-500">

                                        ${weekdays[entry[3]]}: ${entry[1]} ${entry[2]}
                                        (<fmt:formatDate value="${entry[4]}" pattern="HH:mm"/> - 
                                        <fmt:formatDate value="${entry[5]}" pattern="HH:mm"/>)
                                    </div>
                                </c:if>
                            </c:forEach>

                        </div> <!-- Đóng khối lịch của 1 giáo viên -->
                    </c:if>
                </div>
            </div>




            <!-- Class Schedule -->
            <div class="card bg-white rounded-lg shadow p-6 hover:shadow-md transition duration-300">
                <div class="flex items-center justify-between mb-4">
                    <h2 class="font-semibold text-lg">Lịch học lớp</h2>
                    <a href="${pageContext.request.contextPath}/admin?tab=scheduleClass" class="text-blue-500 text-sm">Xem tất cả</a>
                </div>

                <div class="space-y-4">
                    <c:forEach var="entry" items="${weeklySchedules}" varStatus="loop">
                        <c:if test="${param.showAll eq 'true' || loop.index lt 3}">
                            <div class="border-l-4 pl-3" style="border-color: #6366f1;">
                                <div class="font-medium text-base">${entry[2]}</div>
                                <div class="text-sm text-gray-500">
                                    ${weekdays[entry[0]]}: 
                                    <fmt:formatDate value="${entry[5]}" pattern="HH:mm" /> - 
                                    <fmt:formatDate value="${entry[6]}" pattern="HH:mm" />
                                </div>
                                <div class="text-sm text-gray-500">Giáo viên: ${entry[3]}</div>
                                <div class="text-sm text-gray-500">Phòng: ${entry[4]}</div>
                            </div>
                        </c:if>
                    </c:forEach>
                </div>
            </div>


            <!-- Quick Actions -->
<!--            <div class="card bg-white rounded-lg shadow p-6 hover:shadow-md transition duration-300">
                <div class="flex items-center justify-between mb-4">
                    <h2 class="font-semibold text-lg">Cần xử lý</h2>
                    <button class="text-blue-500 text-sm">Xem tất cả</button>
                </div>
                <div class="space-y-4">
                    <div class="border-l-4 border-red-500 pl-3">
                        <div class="font-medium">Phê duyệt giáo viên mới</div>
                        <div class="text-sm text-gray-500">3 hồ sơ chờ duyệt</div>
                        <button class="mt-1 text-sm text-blue-500 hover:text-blue-700">Xử lý ngay</button>
                    </div>
                    <div class="border-l-4 border-blue-500 pl-3">
                        <div class="font-medium">Cập nhật lớp sắp bắt đầu</div>
                        <div class="text-sm text-gray-500">Lớp Toán A2 bắt đầu 15/10</div>
                        <button class="mt-1 text-sm text-blue-500 hover:text-blue-700">Xử lý ngay</button>
                    </div>
                    <div class="border-l-4 border-green-500 pl-3">
                        <div class="font-medium">Duyệt phản hồi</div>
                        <div class="text-sm text-gray-500">5 phản hồi chờ kiểm duyệt</div>
                        <button class="mt-1 text-sm text-blue-500 hover:text-blue-700">Xử lý ngay</button>
                    </div>
                    <div class="border-l-4 border-yellow-500 pl-3">
                        <div class="font-medium">Duyệt lịch dạy</div>
                        <div class="text-sm text-gray-500">2 yêu cầu thay đổi lịch</div>
                        <button class="mt-1 text-sm text-blue-500 hover:text-blue-700">Xử lý ngay</button>
                    </div>
                </div>
            </div>-->
        </div>
    </main>
</div>

<script>
    // Students by Subject Chart

    const labels = [
    <c:forEach var="item" items="${studentCounts}" varStatus="loop">
    "${item[0]}"<c:if test="${!loop.last}">,</c:if>
    </c:forEach>
    ];
    const data = [
    <c:forEach var="item" items="${studentCounts}" varStatus="loop">
        ${item[1]}<c:if test="${!loop.last}">,</c:if>
    </c:forEach>
    ];
    const colors = [
        'rgba(255, 99, 132, 0.7)', 'rgba(54, 162, 235, 0.7)', 'rgba(255, 206, 86, 0.7)',
        'rgba(75, 192, 192, 0.7)', 'rgba(153, 102, 255, 0.7)', 'rgba(255, 159, 64, 0.7)',
        'rgba(100, 181, 246, 0.7)', 'rgba(244, 143, 177, 0.7)'
    ];
    const borderColors = colors.map(c => c.replace('0.7', '1'));

    const ctx = document.getElementById('studentsByClassChart').getContext('2d');
    const chart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                    label: 'Số học sinh',
                    data: data,
                    backgroundColor: colors,
                    borderColor: borderColors,
                    borderWidth: 1
                }]
        },
        options: {
            responsive: true,
            scales: {
                y: {beginAtZero: true}
            }
        }
    });

//    // Growth Chart
//    const growthCtx = document.getElementById('growthChart').getContext('2d');
//    const growthChart = new Chart(growthCtx, {
//        type: 'line',
//        data: {
//            labels: ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6', 'Tháng 7'],
//            datasets: [
//                {
//                    label: 'Số lớp học',
//                    data: [12, 15, 17, 18, 20, 22, 24],
//                    borderColor: 'rgba(54, 162, 235, 1)',
//                    backgroundColor: 'rgba(54, 162, 235, 0.2)',
//                    tension: 0.3,
//                    fill: true
//                },
//                {
//                    label: 'Số học sinh',
//                    data: [350, 410, 470, 520, 580, 630, 687],
//                    borderColor: 'rgba(75, 192, 192, 1)',
//                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
//                    tension: 0.3,
//                    fill: true
//                }
//            ]
//        },
//        options: {
//            responsive: true,
//            scales: {
//                y: {
//                    beginAtZero: true
//                }
//            }
//        }
//    });

    // Feedback Chart
    const feedbackCtx = document.getElementById('feedbackChart').getContext('2d');
    const feedbackChart = new Chart(feedbackCtx, {
        type: 'doughnut',
        data: {
            labels: ['Hài lòng', 'Bình thường', 'Không hài lòng'],
            datasets: [{
                    data: [75, 20, 5],
                    backgroundColor: [
                        'rgba(75, 192, 192, 0.7)',
                        'rgba(255, 206, 86, 0.7)',
                        'rgba(255, 99, 132, 0.7)'
                    ],
                    borderColor: [
                        'rgba(75, 192, 192, 1)',
                        'rgba(255, 206, 86, 1)',
                        'rgba(255, 99, 132, 1)'
                    ],
                    borderWidth: 1
                }]
        },
        options: {
            responsive: true,
            cutout: '70%',
            plugins: {
                legend: {
                    display: false
                }
            }
        }
    });
</script>

