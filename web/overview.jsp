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
                        <div class="text-gray-500 text-sm font-medium">Khóa học đang mở</div>
                        <div class="text-3xl font-bold mt-1">${classesCount}</div>

                    </div>
                    <div class="bg-yellow-100 p-3 rounded-full text-yellow-600">
                        <i class="fas fa-book-open text-xl"></i>
                    </div>
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
            <div class="card bg-white rounded-lg shadow p-6 hover:shadow-md transition duration-300">
                <div class="flex justify-between items-center mb-4">
                    <h2 class="font-semibold text-lg">Tăng trưởng số lượng</h2>
                    <form id="yearFormGrowth" class="flex items-center gap-2">
                        <input type="hidden" name="tab" value="overview">
                        <label for="yearSelectGrowth" class="font-semibold">Chọn năm:</label>
                        <select name="selectedYear" id="yearSelectGrowth" class="border px-2 py-1 rounded" onchange="updateGrowthChart(this.value)">
                            <c:forEach var="year" items="${yearList}">
                                <option value="${year}" ${year == selectedYear ? 'selected' : ''}>${year}</option>
                            </c:forEach>
                        </select>
                    </form>
                </div>
                <canvas id="growthChart" height="250"></canvas>
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

            <!-- Teacher Schedule -->
            <div class="card bg-white rounded-lg shadow p-6 hover:shadow-md transition duration-300">
                <div class="flex items-center justify-between mb-4">
                    <h2 class="font-semibold text-lg">Lịch dạy giáo viên hôm nay</h2>
                    <a href="${pageContext.request.contextPath}/admin?tab=teacherSchedule" class="text-blue-500 text-sm">Xem tất cả</a>
                </div>

                <div class="space-y-4">
                    <c:if test="${not empty teacherSchedules}">
                        <c:set var="currentTeacher" value="" />
                        <c:forEach var="entry" items="${teacherSchedules}">
                            <c:if test="${entry[0] ne currentTeacher}">
                                <c:set var="currentTeacher" value="${entry[0]}" />
                                <!-- Hiển thị tên giáo viên -->
                                <div class="border-l-4 border-blue-500 pl-3">
                                    <div class="font-medium mb-1">${currentTeacher}</div>
                                    <!-- Lặp lại lịch cho giáo viên này -->
                                    <c:forEach var="subEntry" items="${teacherSchedules}">
                                        <c:if test="${subEntry[0] eq currentTeacher}">
                                            <div class="text-sm text-gray-500">
                                                ${weekdays[subEntry[3]]} - Môn ${subEntry[1]} - Lớp ${subEntry[2]} -
                                                Thời gian: <fmt:formatDate value="${subEntry[4]}" pattern="HH:mm"/> - 
                                                <fmt:formatDate value="${subEntry[5]}" pattern="HH:mm"/>
                                            </div>
                                        </c:if>
                                    </c:forEach>
                                </div>
                            </c:if>
                        </c:forEach>
                    </c:if>
                    <c:if test="${empty teacherSchedules}">
                        <div class="text-gray-500 text-sm">Không có lịch dạy hôm nay.</div>
                    </c:if>
                </div>
            </div>
            <!-- Class Schedule -->
            <div class="card bg-white rounded-lg shadow p-6 hover:shadow-md transition duration-300">
                <div class="flex items-center justify-between mb-4">
                    <h2 class="font-semibold text-lg">Lịch học lớp tuần này</h2>
                    <a href="${pageContext.request.contextPath}/admin?tab=scheduleClass" class="text-blue-500 text-sm">Xem tất cả</a>
                </div>

                <div class="space-y-4">
                    <c:forEach var="entry" items="${weeklySchedules}" varStatus="loop">
                        <c:if test="${param.showAll eq 'true' || loop.index lt 3}">
                            <div class="border-l-4 pl-3" style="border-color: #6366f1;">
                                <div class="font-medium text-base">${entry[0]}</div>                                
                                <div class="text-sm text-gray-500">Giáo viên: ${entry[1]}</div>
                                <div class="text-sm text-gray-500">Lịch học:
                                    <c:forTokens var="line" items="${entry[4]}" delims=";">
                                        <c:set var="day" value="${fn:substringBefore(line, ' -')}"/>
                                        <c:set var="temp" value="${fn:substringAfter(line, ' -')}"/>
                                        <c:set var="room" value="${fn:substringBefore(temp, ' -')}"/>
                                        <c:set var="time" value="${fn:substringAfter(temp, ' -')}"/>
                                        <li>
                                            <c:choose>
                                                <c:when test="${day eq 'Thứ 1'}">Chủ nhật</c:when>
                                                <c:otherwise>${day}</c:otherwise>
                                            </c:choose>
                                            - Phòng: ${room} - Thời gian: ${time}
                                        </li>
                                    </c:forTokens>
                                </div>

                            </div>
                        </c:if>
                    </c:forEach>
                </div>
            </div>            
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
    const growthCtx = document.getElementById('growthChart').getContext('2d');
    const growthChart = new Chart(growthCtx, {
    type: 'line',
            data: {
            labels: ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6', 'Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10', 'Tháng 11', 'Tháng 12'],
                    datasets: [
                    {
                    label: 'Số lớp học',
                            data: [
    <c:forEach begin="1" end="12" var="i">
        ${classGrowth[i] != null ? classGrowth[i] : 0}<c:if test="${i < 12}">,</c:if>
    </c:forEach>

                            ],
                            borderColor: 'rgba(54, 162, 235, 1)',
                            backgroundColor: 'rgba(54, 162, 235, 0.2)',
                            tension: 0.3,
                            fill: true
                    },
                    {
                    label: 'Số học sinh',
                            data: [
    <c:forEach begin="1" end="12" var="i">
        ${studentGrowth[i] != null ? studentGrowth[i] : 0}<c:if test="${i < 12}">,</c:if>
    </c:forEach>

                            ],
                            borderColor: 'rgba(75, 192, 192, 1)',
                            backgroundColor: 'rgba(75, 192, 192, 0.2)',
                            tension: 0.3,
                            fill: true
                    }
                    ]
            },
            options: {
            responsive: true,
                    scales: {
                    y: {
                    beginAtZero: true
                    }
                    }
            }
    });
// Hàm cập nhật biểu đồ Growth Chart
    function updateGrowthChart(selectedYear) {
    fetch('admin?tab=overview&selectedYear=' + selectedYear, {
    method: 'GET',
            headers: {
            'Content-Type': 'application/json',
                    'X-Requested-With': 'XMLHttpRequest'
            },
    })
            .then(response => response.json())
            .then(data => {
            if (data.classGrowth && data.studentGrowth) {
            growthChart.data.datasets[0].data = Array.from({ length: 12 }, (_, i) => data.classGrowth[i + 1] || 0);
            growthChart.data.datasets[1].data = Array.from({ length: 12 }, (_, i) => data.studentGrowth[i + 1] || 0);
            growthChart.update();
            } else {
            console.error('No growth data received:', data);
            }
            })
            .catch(error => console.error('Error fetching growth data:', error));
    }
</script>

