<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="selectedCourseId" value="${param.id}" />

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Quản lý Lớp Học</title>
<script src="https://cdn.tailwindcss.com"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<style>
    .class-card:hover {
        transform: translateY(-5px);
        box-shadow: 0 10px 20px rgba(0,0,0,0.1);
    }
    .modal {
        transition: all 0.3s ease;
    }

    .modal-content-scrollable {
        max-height: 90vh;
        overflow-y: auto;
    }


</style>

<div class="bg-gray-100">
    <div class="container mx-auto px-4 py-8">

        <!-- Alert messages -->
        <c:if test="${not empty error}">
            <div class="mb-4 px-4 py-3 rounded bg-red-100 text-red-700 border border-red-400">
                <i class="fas fa-exclamation-circle mr-2"></i>${error}
            </div>
        </c:if>
        <c:if test="${not empty success}">
            <div class="mb-4 px-4 py-3 rounded bg-green-100 text-green-700 border border-green-400">
                <i class="fas fa-check-circle mr-2"></i>${success}
            </div>
        </c:if>

        <!-- Breadcrumb -->
        <nav class="flex mb-6" aria-label="Breadcrumb">
            <ol class="inline-flex items-center space-x-1 md:space-x-3">
                <li class="inline-flex items-center">
                    <a href="admin?tab=courseList" class="inline-flex items-center text-sm font-medium text-gray-700 hover:text-blue-600">
                        <i class="fas fa-home mr-2"></i>
                        Khóa học
                    </a>
                </li>
                <li aria-current="page">
                    <div class="flex items-center">
                        <i class="fas fa-chevron-right text-gray-400 mx-2"></i>
                        <span class="ml-1 text-sm font-medium text-gray-500">${selectedCourseName}</span>
                    </div>
                </li>
            </ol>
        </nav>

        <!-- Header -->
        <div class="flex flex-col md:flex-row justify-between items-start md:items-center mb-8">
            <div>
                <h1 class="text-3xl font-bold text-gray-800">Quản lý Lớp Học</h1>
                <p class="text-gray-600">Khóa học: <span class="font-semibold">${selectedCourseName}</span></p>
            </div>
            <button onclick="openAddClassModal()" class="mt-4 md:mt-0 bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-lg flex items-center">
                <i class="fas fa-plus mr-2"></i> Thêm lớp mới
            </button>
        </div>
        <div class="flex flex-col md:flex-row gap-4 mb-6">
            <input type="text" id="searchClass" placeholder="🔍 Tìm lớp..." class="form-control w-full md:w-1/2">

            <select id="filterTeacher" class="form-control w-full md:w-1/2">
                <option value="">🎓 Tất cả giáo viên</option>
                <c:forEach var="t" items="${teacher}">
                    <option value="${t.name}">${t.name}</option>
                </c:forEach>
            </select>            

        </div>

        <!-- Classes List -->
        <div class="bg-white rounded-lg shadow overflow-hidden">
            <div class="grid grid-cols-12 bg-blue-100 p-4 border-b font-semibold text-gray-700 hidden md:grid">
                <div class="col-span-2 flex items-center"><i class="fas fa-chalkboard mr-2 text-blue-600"></i> Tên lớp</div>
                <div class="col-span-3 flex items-center justify-center"><i class="fas fa-calendar-day mr-2 text-blue-600"></i>Lịch học</div>

                <div class="col-span-2 flex items-center justify-center"><i class="fas fa-user-tie mr-2 text-blue-600"></i> Giáo viên</div>
                <div class="col-span-1 flex items-center justify-center"><i class="fas fa-users mr-2 text-blue-600"></i> Số HS tối thiểu</div>

                <div class="col-span-1 flex items-center justify-center"><i class="fas fa-users mr-2 text-blue-600"></i> Số HS tối đa</div>                             
                <div class="col-span-1 flex items-center justify-center"><i class="fas fa-users mr-2 text-blue-600"></i> Số HS</div>
                <div class="col-span-1 flex items-center justify-center"><i class="fas fa-users mr-2 text-blue-600"></i> Trạng thái</div>
                <div class="col-span-1 flex items-center justify-center"><i class="fas fa-cogs mr-2 text-blue-600"></i> Thao tác</div>
            </div>


            <div id="classes-container">
                <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

                <c:forEach var="c" items="${classGroups}">
                    <div class="card grid grid-cols-1 md:grid-cols-12 border-b p-4 hover:bg-gray-50"
                         data-class="${fn:toLowerCase(c[0])}"
                         data-teacher="${fn:toLowerCase(c[3])}"
                         data-weekday="${weekdays[c[6]]}"
                         data-room="${fn:toLowerCase(c[2])}">

                        <!-- Tên lớp -->
                        <div class="col-span-2 font-medium mb-2 md:mb-0">${c[0]}</div>

                        <!-- Thời khóa biểu gộp: Thứ - Phòng - Ca -->
                        <div class="col-span-3 text-gray-600 mb-2 md:mb-0 flex justify-center items-center text-center">
                            <ul class="text-left list-disc ml-4">
                                <c:forTokens var="line" items="${c[2]}" delims=";">
                                    <c:set var="day" value="${fn:substringBefore(line, ' -')}"/>
                                    <c:set var="rest" value="${fn:substringAfter(line, ' -')}"/>
                                    <li>
                                        <c:choose>
                                            <c:when test="${day eq 'Thứ 1'}">Chủ nhật</c:when>
                                            <c:otherwise>${day}</c:otherwise>
                                        </c:choose>
                                        - ${rest}
                                    </li>
                                </c:forTokens>
                            </ul>

                        </div>
                        <!-- Giáo viên -->
                        <div class="col-span-2 text-gray-600 mb-2 md:mb-0 flex justify-center items-center">${c[3]}</div>

                        <!-- Số HS tối thiểu -->
                        <div class="col-span-1 text-gray-600 mb-2 md:mb-0 flex justify-center items-center">${c[9]}</div>

                        <!-- Số HS tối đa -->
                        <div class="col-span-1 text-gray-600 mb-2 md:mb-0 flex justify-center items-center">${c[1]}</div>

                        <!-- Số HS hiện tại -->
                        <div class="col-span-1 text-gray-600 mb-2 md:mb-0 flex justify-center items-center">${c[7]}</div>

                        <!-- Trạng thái -->
                        <div class="col-span-1 text-gray-600 mb-2 md:mb-0 flex justify-center items-center">
                            <c:choose>
                                <c:when test="${c[10] == 0}">Đang chờ</c:when>
                                <c:when test="${c[10] == 1}">Đang học</c:when>
                                <c:when test="${c[10] == 2}">Đã đóng</c:when>
                                <c:otherwise>Không rõ</c:otherwise>
                            </c:choose>
                        </div>


                        <!-- Thao tác -->
                        <div class="col-span-1 flex items-center justify-center space-x-2">
                            <button disabled class="text-blue-600 hover:text-blue-800" title="Sửa lớp học">
                                <i class="fas fa-edit"></i>
                            </button>
                            <button disabled class="text-red-600 hover:text-red-800" title="Xóa lớp học">
                                <i class="fas fa-trash"></i>
                            </button>
                            <a href="admin?tab=studentListInClass&groupId=${c[8]}&id=${selectedCourseId}"

                               class="text-green-600 hover:text-green-800" title="Xem danh sách học sinh">
                                <i class="fas fa-eye"></i>
                            </a>
                            <c:if test="${c[10] == 0 && c[7] >= c[9]}">
                                <a href="admin?tab=classManagement&action=activateGroup&groupId=${c[8]}&id=${selectedCourseId}"
                                   class="text-orange-600 hover:text-orange-800" title="Kích hoạt lớp học">
                                    <i class="fas fa-toggle-on"></i>
                                </a>
                            </c:if>

                            
                        </div>
                    </div>
                </c:forEach>

            </div>


        </div>
    </div>
</div>

<div id="addClassModal" class="fixed inset-0 z-50 hidden bg-black bg-opacity-50 flex items-center justify-center">
    <div class="bg-white rounded-lg shadow-lg w-full max-w-xl p-6 relative text-black modal-content-scrollable">
        <h2 class="text-xl font-bold mb-4">Thêm lớp học mới</h2>
        <form id="addClassForm" action="admin?tab=classManagement&id=${selectedCourseId}" method="post">
            <input type="hidden" name="action" value="ADD_CLASSGROUP">
            <input type="hidden" name="tutoringClassId" value="${selectedCourseId}" />
            <div class="mb-4">
                <label for="classGroupName" class="block font-medium mb-1">Tên lớp</label>
                <input type="text" id="classGroupName" name="classGroupName" class="w-full border rounded px-3 py-2" required
                       value="${groupModal.name != null ? groupModal.name : ''}">
            </div>
            <div class="mb-4">
                <label for="minStudent" class="block font-medium mb-1">Sĩ số tối thiểu</label>
                <input type="number" id="minStudent" name="minStudent" class="w-full border rounded px-3 py-2" required
                       value="${groupModal.minStudent != null ? groupModal.minStudent : ''}">
            </div>
            <div class="mb-4">
                <label for="maxStudent" class="block font-medium mb-1">Sĩ số tối đa</label>
                <input type="number" id="maxStudent" name="maxStudent" class="w-full border rounded px-3 py-2" required
                       value="${groupModal.maxStudent != null ? groupModal.maxStudent : ''}">
            </div>
            <div class="mb-4">
                <label for="teacherId" class="block font-medium mb-1">Giáo viên</label>
                <select id="teacherId" name="teacherId" class="w-full border rounded px-3 py-2" required onchange="loadScheduleOptions()">
                    <option value="">-- Chọn --</option>
                    <c:forEach var="t" items="${teacher}">
                        <option value="${t.id}" ${selectedTeacher == t.id ? 'selected' : ''}>${t.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div id="scheduleContainer" class="mb-4">
                <h3 class="font-medium mb-2">Lịch học</h3>
                <div id="scheduleRows">
                    <c:choose>
                        <c:when test="${not empty selectedDay}">
                            <c:set var="daysArr" value="${fn:split(selectedDay,',')}" />
                            <c:set var="shiftsArr" value="${fn:split(selectedShift,',')}" />
                            <c:set var="roomsArr" value="${fn:split(selectedRoom,',')}" />
                            <c:forEach var="i" begin="0" end="${fn:length(daysArr)-1}">
                                <div class="schedule-row flex gap-4 mb-2" data-index="${i}">
                                    <div class="w-1/3">
                                        <label class="block font-medium mb-1">Thứ</label>
                                        <select name="dayOfWeek" class="day-select w-full border rounded px-3 py-2" required>
                                            <option value="">-- Chọn --</option>
                                            <c:forEach var="day" items="${weekdays}">
                                                <option value="${day.key}" <c:if test="${daysArr[i] == day.key}">selected</c:if>>${day.value}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="w-1/3">
                                        <label class="block font-medium mb-1">Ca</label>
                                        <select name="shiftId" class="shift-select w-full border rounded px-3 py-2" required>
                                            <option value="">-- Chọn --</option>
                                            <c:forEach var="shift" items="${shifts}">
                                                <option value="${shift.id}" <c:if test="${shiftsArr[i] == shift.id}">selected</c:if>>${shift.startTime} - ${shift.endTime}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="w-1/3">
                                        <label class="block font-medium mb-1">Phòng</label>
                                        <select name="roomId" class="room-select w-full border rounded px-3 py-2" required>
                                            <option value="">-- Chọn --</option>
                                            <c:forEach var="room" items="${rooms}">
                                                <option value="${room.id}" <c:if test="${roomsArr[i] == room.id}">selected</c:if>>${room.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <button type="button" class="text-red-600 hover:text-red-800 mt-6" onclick="removeScheduleRow(this)">
                                        <i class="fas fa-trash"></i>
                                    </button>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <!-- Luôn render 1 dòng lịch học mặc định -->
                            <div class="schedule-row flex gap-4 mb-2" data-index="0">
                                <div class="w-1/3">
                                    <label class="block font-medium mb-1">Thứ</label>
                                    <select name="dayOfWeek" class="day-select w-full border rounded px-3 py-2" required onchange="loadShiftOptions(this)">
                                        <option value="">-- Chọn --</option>
                                        <c:forEach var="day" items="${weekdays}">
                                            <option value="${day.key}">${day.value}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="w-1/3">
                                    <label class="block font-medium mb-1">Ca</label>
                                    <select name="shiftId" class="shift-select w-full border rounded px-3 py-2" required onchange="loadRoomOptions(this)">
                                        <option value="">-- Chọn --</option>
                                        <c:forEach var="shift" items="${shifts}">
                                            <option value="${shift.id}">${shift.startTime} - ${shift.endTime}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="w-1/3">
                                    <label class="block font-medium mb-1">Phòng</label>
                                    <select name="roomId" class="room-select w-full border rounded px-3 py-2" required>
                                        <option value="">-- Chọn --</option>
                                        <c:forEach var="room" items="${rooms}">
                                            <option value="${room.id}">${room.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <button type="button" class="text-red-600 hover:text-red-800 mt-6" onclick="removeScheduleRow(this)">
                                    <i class="fas fa-trash"></i>
                                </button>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
                <button type="button" onclick="addScheduleRow()" class="mt-2 text-blue-600 hover:text-blue-800">
                    <i class="fas fa-plus mr-1"></i> Thêm buổi học
                </button>
            </div>
            <c:if test="${not empty errorAddClass}">
                <div class="mb-4 px-4 py-2 rounded bg-red-100 text-red-700 border border-red-400">
                    <i class="fas fa-exclamation-circle mr-2"></i>${errorAddClass}
                </div>
            </c:if>
            <c:if test="${not empty errorList}">
                <div class="mb-4 px-4 py-2 rounded bg-red-100 text-red-700 border border-red-400">
                    <i class="fas fa-exclamation-circle mr-2"></i>
                    <ul class="list-disc ml-6">
                        <c:forEach var="error" items="${errorList}">
                            <li>${error}</li>
                            </c:forEach>
                    </ul>
                </div>
            </c:if>
            <div class="flex justify-end gap-2 mt-6">
                <button type="button" onclick="closeAddClassModal()" class="px-4 py-2 bg-gray-300 hover:bg-gray-400 rounded">Hủy</button>
                <button type="submit" class="px-4 py-2 bg-blue-600 hover:bg-blue-700 text-white rounded">Thêm</button>
            </div>
        </form>
        <button onclick="closeAddClassModal()" class="absolute top-2 right-2 text-gray-600 hover:text-black text-xl">×</button>
    </div>
</div>

<div id="js-data"
     data-context-path="${pageContext.request.contextPath}"
     data-course-id="${selectedCourseId}"
     data-selected-teacher="${selectedTeacher}"
     data-selected-day="${selectedDay}"
     data-selected-shift="${selectedShift}"
     data-selected-room="${selectedRoom}"
     data-open-add="${openAddModal}">
</div>

<c:if test="${openAddModal == true}">
    <script>
        window.addEventListener("DOMContentLoaded", function () {
            document.getElementById("addClassModal").classList.remove("hidden");
        });
    </script>
</c:if>


<c:if test="${not empty sessionScope.successMessage}">
    <script>
        window.addEventListener('DOMContentLoaded', function () {
            showToast("<c:out value='${sessionScope.successMessage}'/>");
        });
    </script>
    <c:remove var="successMessage" scope="session" />
</c:if>

<c:if test="${modalError}">
    <script>
        window.addEventListener("DOMContentLoaded", function () {
            openAddClassModal(); // Hàm JavaScript bạn đã dùng để mở modal
        });
    </script>
</c:if>


<!-- Toast thông báo -->
<div id="toast" style="display: none;"
     class="fixed top-5 right-5 bg-green-500 text-white px-6 py-3 rounded-lg shadow-lg z-50 font-medium transition-all duration-300">
</div>

<!--<script>
    function showToast(message) {
        const toast = document.getElementById("toast");
        if (!toast)
            return;

        toast.textContent = message;
        toast.style.display = "block";

        setTimeout(() => {
            toast.style.display = "none";
        }, 3000);
    }

</script>-->

<script>
    // Biến toàn cục để theo dõi số hàng lịch học
    let scheduleIndex = 1;

// Hàm hiển thị thông báo toast
    function showToast(message) {
        const toast = document.getElementById("toast");
        if (!toast)
            return;
        toast.textContent = message;
        toast.style.display = "block";
        setTimeout(() => {
            toast.style.display = "none";
        }, 3000);
    }

// Hàm chuẩn hóa chuỗi (xóa dấu, chuyển thành chữ thường)
    function normalize(str) {
        return (str || "").normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().trim();
    }

// Hàm áp dụng bộ lọc tìm kiếm và lọc lớp học
    function applyFilters() {
        const classKeyword = normalize(document.getElementById("searchClass").value);
        const teacher = normalize(document.getElementById("filterTeacher").value);
        const weekday = normalize(document.getElementById("filterWeekday").value);
        const room = normalize(document.getElementById("filterRoom").value);
        const cards = document.querySelectorAll(".card[data-class]");

        cards.forEach(card => {
            const className = normalize(card.dataset.class);
            const teacherName = normalize(card.dataset.teacher);
            const day = normalize(card.dataset.weekday);
            const roomName = normalize(card.dataset.room);

            const matchClass = classKeyword === "" || className.includes(classKeyword);
            const matchTeacher = teacher === "" || teacherName === teacher;
            const matchWeekday = weekday === "" || day === weekday;
            const matchRoom = room === "" || roomName === room;

            card.style.display = matchClass && matchTeacher && matchWeekday && matchRoom ? "grid" : "none";
        });
    }

// Hàm thêm hàng lịch học mới
    function addScheduleRow() {
        const container = document.getElementById("scheduleRows");
        if (!container) {
            showToast("Không tìm thấy container lịch!");
            return;
        }

        const row = document.createElement("div");
        row.className = "schedule-row flex gap-4 mb-2";
        row.dataset.index = scheduleIndex;

        row.innerHTML = `
        <div class="w-1/3">
            <label class="block font-medium mb-1">Thứ</label>
            <select name="dayOfWeek" class="day-select w-full border rounded px-3 py-2" required onchange="loadShiftOptions(this)">
                <option value="">-- Chọn --</option>
            </select>
        </div>
        <div class="w-1/3">
            <label class="block font-medium mb-1">Ca</label>
            <select name="shiftId" class="shift-select w-full border rounded px-3 py-2" required onchange="loadRoomOptions(this)">
                <option value="">-- Chọn --</option>
            </select>
        </div>
        <div class="w-1/3">
            <label class="block font-medium mb-1">Phòng</label>
            <select name="roomId" class="room-select w-full border rounded px-3 py-2" required>
                <option value="">-- Chọn --</option>
            </select>
        </div>
        <button type="button" class="text-red-600 hover:text-red-800 mt-6" onclick="removeScheduleRow(this)">
            <i class="fas fa-trash"></i>
        </button>
    `;

        container.appendChild(row);
        loadOptionsForNewRow(row);
        scheduleIndex++;
    }

// Hàm xóa hàng lịch học
    function removeScheduleRow(button) {
        const row = button.closest(".schedule-row");
        if (document.querySelectorAll(".schedule-row").length > 1) {
            row.remove();
            updateAllDayOptions();
            applyFilters();
        } else {
            showToast("Phải có ít nhất một buổi học!");
        }
    }

// Hàm cập nhật tùy chọn cho dropdown
    function updateSelect(select, options) {
        if (!select)
            return;
        const currentValue = select.value;
        select.innerHTML = '<option value="">-- Chọn --</option>';
        options.forEach(opt => {
            const option = document.createElement("option");
            option.value = opt.value;
            option.text = opt.label;
            if (opt.value === currentValue) {
                option.selected = true;
            }
            select.appendChild(option);
        });
    }

// Hàm cập nhật tất cả tùy chọn ngày
    function updateAllDayOptions() {
        document.querySelectorAll(".schedule-row").forEach(row => {
            loadOptionsForNewRow(row);
        });
    }

// Hàm tải tùy chọn cho hàng lịch học mới
    function loadOptionsForNewRow(row) {
        const teacherId = document.getElementById("teacherId").value;
        const daySelect = row.querySelector(".day-select");
        const currentDay = daySelect.value;

        if (!teacherId) {
            daySelect.innerHTML = '<option value="">-- Chọn --</option>';
            row.querySelector(".shift-select").innerHTML = '<option value="">-- Chọn --</option>';
            row.querySelector(".room-select").innerHTML = '<option value="">-- Chọn --</option>';
            showToast("Vui lòng chọn giáo viên trước!");
            return;
        }

        const usedDays = Array.from(document.querySelectorAll(".schedule-row"))
                .filter(r => r !== row)
                .map(r => r.querySelector(".day-select").value)
                .filter(v => v);

        let url = contextPath + "/ScheduleOptionsServlet?action=days&teacherId=" + encodeURIComponent(teacherId);
        if (usedDays.length > 0) {
            url += "&" + usedDays.map(d => "usedDays=" + encodeURIComponent(d)).join('&');
        }

        fetch(url)
                .then(res => res.json())
                .then(data => {
                    if (!data.days || data.days.length === 0) {
                        showToast("Không còn ngày trống cho giáo viên!");
                        daySelect.innerHTML = '<option value="">-- Không có ngày --</option>';
                        row.querySelector(".shift-select").innerHTML = '<option value="">-- Chọn --</option>';
                        row.querySelector(".room-select").innerHTML = '<option value="">-- Chọn --</option>';
                        return;
                    }
                    updateSelect(daySelect, data.days);
                    if (currentDay && data.days.some(opt => opt.value === currentDay)) {
                        daySelect.value = currentDay;
                        loadShiftOptions(daySelect);
                    }
                })
                .catch(err => {
                    console.error("Lỗi load days:", err);
                    showToast("Lỗi khi tải danh sách ngày!");
                    daySelect.innerHTML = '<option value="">-- Lỗi tải ngày --</option>';
                });
    }

// Hàm tải tùy chọn ca học
    function loadShiftOptions(daySelect) {
        const teacherId = document.getElementById("teacherId").value;
        const day = daySelect.value;
        const row = daySelect.closest(".schedule-row");
        const shiftSelect = row.querySelector(".shift-select");
        const currentShift = shiftSelect.value;

        if (!teacherId || !day) {
            shiftSelect.innerHTML = '<option value="">-- Chọn --</option>';
            row.querySelector(".room-select").innerHTML = '<option value="">-- Chọn --</option>';
            return;
        }

        let url = contextPath + "/ScheduleOptionsServlet?action=shifts"
                + "&teacherId=" + encodeURIComponent(teacherId)
                + "&day=" + encodeURIComponent(day);

        fetch(url)
                .then(res => res.json())
                .then(data => {
                    if (!data.shifts || data.shifts.length === 0) {
                        showToast("Không còn ca trống cho thứ " + day + "!");
                        shiftSelect.innerHTML = '<option value="">-- Không có ca --</option>';
                        row.querySelector(".room-select").innerHTML = '<option value="">-- Chọn --</option>';
                        return;
                    }
                    updateSelect(shiftSelect, data.shifts);
                    if (currentShift && data.shifts.some(opt => opt.value === currentShift)) {
                        shiftSelect.value = currentShift;
                        loadRoomOptions(shiftSelect);
                    } else if (currentShift) {
                        row.querySelector(".room-select").innerHTML = '<option value="">-- Chọn --</option>';
                        showToast("Ca trước đó không còn khả dụng, vui lòng chọn ca khác!");
                    }
                })
                .catch(err => {
                    console.error("Lỗi load shifts:", err);
                    showToast("Lỗi khi tải danh sách ca!");
                    shiftSelect.innerHTML = '<option value="">-- Lỗi tải ca --</option>';
                });
    }

// Hàm tải tùy chọn phòng
    function loadRoomOptions(shiftSelect) {
        const row = shiftSelect.closest(".schedule-row");
        const day = row.querySelector(".day-select").value;
        const shift = shiftSelect.value;
        const roomSelect = row.querySelector(".room-select");
        const currentRoom = roomSelect.value;

        if (!day || !shift) {
            roomSelect.innerHTML = '<option value="">-- Chọn --</option>';
            return;
        }

        let url = contextPath + "/ScheduleOptionsServlet?action=rooms"
                + "&day=" + encodeURIComponent(day)
                + "&shift=" + encodeURIComponent(shift);

        fetch(url)
                .then(res => res.json())
                .then(data => {
                    if (!data.rooms || data.rooms.length === 0) {
                        showToast("Không còn phòng trống cho thứ " + day + " và ca " + shift + "!");
                        roomSelect.innerHTML = '<option value="">-- Không có phòng --</option>';
                        return;
                    }
                    updateSelect(roomSelect, data.rooms);
                    if (currentRoom && data.rooms.some(opt => opt.value === currentRoom)) {
                        roomSelect.value = currentRoom;
                    } else if (currentRoom) {
                        roomSelect.value = "";
                        showToast("Phòng trước đó không còn khả dụng, vui lòng chọn phòng khác!");
                    }
                })
                .catch(err => {
                    console.error("Lỗi load rooms:", err);
                    showToast("Lỗi khi tải danh sách phòng!");
                    roomSelect.innerHTML = '<option value="">-- Lỗi tải phòng --</option>';
                });
    }

// Hàm mở modal thêm lớp học
    async function openAddClassModal() {
        const modal = document.getElementById("addClassModal");
        modal.classList.remove("hidden");

        // Reset các hàng lịch học, giữ lại hàng đầu tiên nếu không có dữ liệu cũ
        const scheduleRows = document.querySelectorAll(".schedule-row[data-index]");
        scheduleRows.forEach((row, i) => {
            if (i !== 0)
                row.remove();
        });
        scheduleIndex = 1;

        const teacherSelect = document.getElementById("teacherId");
        const selectedTeacher = "${selectedTeacher}" || teacherSelect.dataset.selected;

        // Tải danh sách giáo viên
        try {
            const res = await fetch(contextPath + "/ScheduleOptionsServlet?action=teachers&courseId=" + encodeURIComponent(courseId));
            const data = await res.json();
            if (data.teachers) {
                updateSelect(teacherSelect, data.teachers);
                if (selectedTeacher) {
                    teacherSelect.value = selectedTeacher;
                    loadScheduleOptions();
                }
            }
        } catch (err) {
            console.error("Lỗi load teacher:", err);
            showToast("Lỗi khi tải danh sách giáo viên!");
        }

        // Khôi phục dữ liệu lịch học nếu có
        const selectedDays = "${selectedDay}".split(",").filter(Boolean);
        const selectedShifts = "${selectedShift}".split(",").filter(Boolean);
        const selectedRooms = "${selectedRoom}".split(",").filter(Boolean);

        if (selectedDays.length > 0) {
            // Đảm bảo chỉ có đúng số dòng lịch học
            const container = document.getElementById("scheduleRows");
            // Xóa hết các dòng hiện tại
            container.innerHTML = "";

            // Thêm đúng số dòng lịch học đã nhập
            for (let i = 0; i < selectedDays.length; i++) {
                addScheduleRow();
            }

            // Khôi phục giá trị cho từng dòng, đảm bảo đổ options đủ các dropdown
            const rows = document.querySelectorAll(".schedule-row");
            for (let index = 0; index < rows.length; index++) {
                const row = rows[index];
                const daySel = row.querySelector(".day-select");
                const shiftSel = row.querySelector(".shift-select");
                const roomSel = row.querySelector(".room-select");

                // Đổ options cho Thứ
                await loadOptionsForNewRowAsync(row); // Đảm bảo options đầy đủ

                // Chọn lại Thứ nếu có
                if (selectedDays[index]) {
                    daySel.value = selectedDays[index];
                }

                // Đổ options cho Ca
                await loadShiftOptionsAsync(daySel);

                // Chọn lại Ca nếu có
                if (selectedShifts[index]) {
                    shiftSel.value = selectedShifts[index];
                }

                // Đổ options cho Phòng
                await loadRoomOptionsAsync(shiftSel);

                // Chọn lại Phòng nếu có
                if (selectedRooms[index]) {
                    roomSel.value = selectedRooms[index];
                }
            }
        }
        document.querySelectorAll('.schedule-row').forEach(row => {
            const daySelect = row.querySelector('.day-select');
            if (daySelect && daySelect.value) {
                loadShiftOptions(daySelect); // fetch lại Ca
                const shiftSelect = row.querySelector('.shift-select');
                if (shiftSelect && shiftSelect.value) {
                    loadRoomOptions(shiftSelect); // fetch lại Phòng
                }
            }
        });
    }

// Các phiên bản async cho load options (cần chỉnh lại các hàm này nếu dùng fetch)
    async function loadOptionsForNewRowAsync(row) {
        // ... giống như loadOptionsForNewRow nhưng trả về Promise, gọi fetch và resolve khi xong
        // Ví dụ:
        return new Promise(resolve => {
            const teacherId = document.getElementById("teacherId").value;
            const daySelect = row.querySelector(".day-select");
            if (!teacherId) {
                daySelect.innerHTML = '<option value="">-- Chọn --</option>';
                row.querySelector(".shift-select").innerHTML = '<option value="">-- Chọn --</option>';
                row.querySelector(".room-select").innerHTML = '<option value="">-- Chọn --</option>';
                resolve();
                return;
            }
            const usedDays = Array.from(document.querySelectorAll(".schedule-row"))
                    .filter(r => r !== row)
                    .map(r => r.querySelector(".day-select").value)
                    .filter(v => v);

            let url = contextPath + "/ScheduleOptionsServlet?action=days&teacherId=" + encodeURIComponent(teacherId);
            if (usedDays.length > 0) {
                url += "&" + usedDays.map(d => "usedDays=" + encodeURIComponent(d)).join('&');
            }

            fetch(url)
                    .then(res => res.json())
                    .then(data => {
                        if (!data.days || data.days.length === 0) {
                            daySelect.innerHTML = '<option value="">-- Không có ngày --</option>';
                            row.querySelector(".shift-select").innerHTML = '<option value="">-- Chọn --</option>';
                            row.querySelector(".room-select").innerHTML = '<option value="">-- Chọn --</option>';
                            resolve();
                            return;
                        }
                        updateSelect(daySelect, data.days);
                        resolve();
                    })
                    .catch(err => {
                        daySelect.innerHTML = '<option value="">-- Lỗi tải ngày --</option>';
                        resolve();
                    });
        });
    }

    async function loadShiftOptionsAsync(daySelect) {
        return new Promise(resolve => {
            const teacherId = document.getElementById("teacherId").value;
            const day = daySelect.value;
            const row = daySelect.closest(".schedule-row");
            const shiftSelect = row.querySelector(".shift-select");
            if (!teacherId || !day) {
                shiftSelect.innerHTML = '<option value="">-- Chọn --</option>';
                row.querySelector(".room-select").innerHTML = '<option value="">-- Chọn --</option>';
                resolve();
                return;
            }

            let url = contextPath + "/ScheduleOptionsServlet?action=shifts"
                    + "&teacherId=" + encodeURIComponent(teacherId)
                    + "&day=" + encodeURIComponent(day);

            fetch(url)
                    .then(res => res.json())
                    .then(data => {
                        if (!data.shifts || data.shifts.length === 0) {
                            shiftSelect.innerHTML = '<option value="">-- Không có ca --</option>';
                            row.querySelector(".room-select").innerHTML = '<option value="">-- Chọn --</option>';
                            resolve();
                            return;
                        }
                        updateSelect(shiftSelect, data.shifts);
                        resolve();
                    })
                    .catch(err => {
                        shiftSelect.innerHTML = '<option value="">-- Lỗi tải ca --</option>';
                        resolve();
                    });
        });
    }

    async function loadRoomOptionsAsync(shiftSelect) {
        return new Promise(resolve => {
            const row = shiftSelect.closest(".schedule-row");
            const day = row.querySelector(".day-select").value;
            const shift = shiftSelect.value;
            const roomSelect = row.querySelector(".room-select");
            if (!day || !shift) {
                roomSelect.innerHTML = '<option value="">-- Chọn --</option>';
                resolve();
                return;
            }

            let url = contextPath + "/ScheduleOptionsServlet?action=rooms"
                    + "&day=" + encodeURIComponent(day)
                    + "&shift=" + encodeURIComponent(shift);

            fetch(url)
                    .then(res => res.json())
                    .then(data => {
                        if (!data.rooms || data.rooms.length === 0) {
                            roomSelect.innerHTML = '<option value="">-- Không có phòng --</option>';
                            resolve();
                            return;
                        }
                        updateSelect(roomSelect, data.rooms);
                        resolve();
                    })
                    .catch(err => {
                        roomSelect.innerHTML = '<option value="">-- Lỗi tải phòng --</option>';
                        resolve();
                    });
        });
    }
// Hàm đóng modal
    function closeAddClassModal() {
        document.getElementById("addClassModal").classList.add("hidden");
        // Xóa query param openAddModal nếu có
        const url = new URL(window.location.href);
        url.searchParams.delete('openAddModal');
        window.location.href = url.toString();
    }

// Hàm tải tùy chọn lịch học
    function loadScheduleOptions() {
        const teacherId = document.getElementById("teacherId").value;
        if (!teacherId) {
            document.querySelectorAll(".day-select, .shift-select, .room-select").forEach(select => {
                select.innerHTML = '<option value="">-- Chọn --</option>';
            });
            return;
        }

        document.querySelectorAll(".schedule-row").forEach(row => {
            loadOptionsForNewRow(row);
        });
    }

// Hàm kiểm tra form trước khi gửi (có thể mở rộng để kiểm tra lịch trùng lặp)
    function validateForm(callback) {
        // Hiện tại chỉ cho qua, có thể thêm logic kiểm tra lịch trùng lặp
        callback(true);
    }

// Sự kiện submit form
    document.getElementById("addClassForm")?.addEventListener("submit", function (e) {
        e.preventDefault();
        validateForm(isValid => {
            if (isValid)
                this.submit();
        });
    });

// Sự kiện khởi tạo
    document.addEventListener("DOMContentLoaded", () => {
        const dataDiv = document.getElementById("js-data");
        window.contextPath = dataDiv.dataset.contextPath || "";
        window.courseId = dataDiv.dataset.courseId || "";

        const shouldOpenModal = dataDiv.dataset.openAdd === "true";
        if (shouldOpenModal) {
            openAddClassModal();
        }

        // Gắn sự kiện cho các bộ lọc
        document.getElementById("searchClass")?.addEventListener("input", applyFilters);
        document.getElementById("filterTeacher")?.addEventListener("change", applyFilters);
        document.getElementById("filterWeekday")?.addEventListener("change", applyFilters);
        document.getElementById("filterRoom")?.addEventListener("change", applyFilters);

        // Gắn sự kiện cho dropdown giáo viên
        document.getElementById("teacherId")?.addEventListener("change", loadScheduleOptions);
    });
</script>
