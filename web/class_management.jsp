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
            <input type="text" id="searchClass" placeholder="🔍 Tìm lớp..." class="form-control w-full md:w-1/4">

            <select id="filterTeacher" class="form-control w-full md:w-1/4">
                <option value="">🎓 Tất cả giáo viên</option>
                <c:forEach var="t" items="${teacher}">
                    <option value="${t.name}">${t.name}</option>
                </c:forEach>
            </select>


            <select id="filterWeekday" class="form-control w-full md:w-1/4">
                <option value="">📅 Tất cả các ngày</option>
                <c:forEach var="i" begin="1" end="7">
                    <option value="${weekdays[i]}">${weekdays[i]}</option>
                </c:forEach>
            </select>

            <select id="filterRoom" class="form-control w-full md:w-1/4">
                <option value="">🏫 Tất cả phòng</option>
                <c:forEach var="r" items="${rooms}">
                    <option value="${r.name}">${r.name}</option>
                </c:forEach>
            </select>

        </div>

        <!-- Classes List -->
        <div class="bg-white rounded-lg shadow overflow-hidden">
            <div class="grid grid-cols-12 bg-blue-100 p-4 border-b font-semibold text-gray-700 hidden md:grid">
                <div class="col-span-2 flex items-center"><i class="fas fa-chalkboard mr-2 text-blue-600"></i> Tên lớp</div>
                <div class="col-span-1 flex items-center justify-center"><i class="fas fa-calendar-day mr-2 text-blue-600"></i> Thứ</div>
                <div class="col-span-1 flex items-center justify-center"><i class="fas fa-clock mr-2 text-blue-600"></i> Ca</div>
                <div class="col-span-1 flex items-center justify-center"><i class="fas fa-door-open mr-2 text-blue-600"></i> Phòng</div>
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

                        <!-- Thứ học -->
                        <div class="col-span-1 text-gray-600 mb-2 md:mb-0 flex justify-center items-center">
                            ${weekdays[c[6]]}
                        </div>

                        <!-- Ca học -->
                        <div class="col-span-1 text-gray-600 mb-2 md:mb-0 flex justify-center items-center">
                            ${fn:substring(c[4], 0, 5)} - ${fn:substring(c[5], 0, 5)}
                        </div>

                        <!-- Phòng -->
                        <div class="col-span-1 text-gray-600 mb-2 md:mb-0 flex justify-center items-center">${c[2]}</div>

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
        <form action="admin?tab=classManagement&id=${selectedCourseId}" method="post">
            <input type="hidden" name="action" value="ADD_CLASSGROUP">
            <input type="hidden" name="tutoringClassId" value="${selectedCourseId}" />

            <!-- Tên lớp -->
            <div class="mb-4">
                <label for="classGroupName" class="block font-medium mb-1">Tên lớp</label>
                <input type="text" id="classGroupName" name="classGroupName" class="w-full border rounded px-3 py-2" required
       value="${groupModal.name != null ? groupModal.name : ''}">

            </div>

            <!-- Sĩ số tối thiểu -->
            <div class="mb-4">
                <label for="minStudent" class="block font-medium mb-1">Sĩ số tối thiểu</label>
                <input type="number" id="minStudent" name="minStudent" class="w-full border rounded px-3 py-2" required
       value="${groupModal.minStudent != null ? groupModal.minStudent : ''}">


            </div>

            <!-- Sĩ số tối đa -->
            <div class="mb-4">
                <label for="maxStudent" class="block font-medium mb-1">Sĩ số tối đa</label>
                <input type="number" id="maxStudent" name="maxStudent" class="w-full border rounded px-3 py-2" required
       value="${groupModal.maxStudent != null ? groupModal.maxStudent : ''}">

            </div>

            <!-- Giáo viên -->
            <div class="mb-4">
                <label for="teacherId" class="block font-medium mb-1">Giáo viên</label>
                <select id="teacherId" name="teacherId" class="w-full border rounded px-3 py-2" required onchange="updateOptions()">
    <option value="">-- Chọn --</option>
    <c:forEach var="t" items="${teacher}">
        <option value="${t.id}" ${groupModal.teachId == t.id ? 'selected' : ''}>${t.name}</option>
    </c:forEach>
</select>

            </div>

            <!-- Thứ học -->
            <div class="mb-4">
                <label for="dayOfWeek" class="block font-medium mb-1">Thứ học</label>
                <select id="dayOfWeek" name="dayOfWeek" class="w-full border rounded px-3 py-2" required onchange="updateOptions()">

                </select>
            </div>

            <!-- Ca học -->
            <div class="mb-4">
                <label for="shiftId" class="block font-medium mb-1">Ca học</label>
                <select id="shiftId" name="shiftId" class="w-full border rounded px-3 py-2" required onchange="updateOptions()">

                </select>
            </div>

            <!-- Phòng học -->
            <div class="mb-4">
                <label for="roomId" class="block font-medium mb-1">Phòng học</label>
                <select id="roomId" name="roomId" class="w-full border rounded px-3 py-2" required onchange="updateOptions()">

                </select>
            </div>
            <c:if test="${not empty errorAddClass}">
                <div class="mb-4 px-4 py-2 rounded bg-red-100 text-red-700 border border-red-400">
                    <i class="fas fa-exclamation-circle mr-2"></i>${errorAddClass}
                </div>
            </c:if>

            <!-- Nút hành động -->
            <div class="flex justify-end gap-2 mt-6">
                <button type="button" onclick="closeAddClassModal()" class="px-4 py-2 bg-gray-300 hover:bg-gray-400 rounded">Hủy</button>
                <button type="submit" class="px-4 py-2 bg-blue-600 hover:bg-blue-700 text-white rounded">Thêm</button>               
            </div>
            

            
        </form>
            
        <!-- Nút đóng -->
        <button onclick="closeAddClassModal()" class="absolute top-2 right-2 text-gray-600 hover:text-black text-xl">×</button>
    </div>
</div>

<div id="js-data"
     data-context-path="${pageContext.request.contextPath}"
     data-course-id="${selectedCourseId}"
     data-selected-teacher="${selectedTeacher}"
     data-selected-day="${selectedDay}"
     data-selected-shift="${selectedShift}"
     data-selected-room="${selectedRoom}">
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

<!-- Toast thông báo -->
<div id="toast" style="display: none;"
     class="fixed top-5 right-5 bg-green-500 text-white px-6 py-3 rounded-lg shadow-lg z-50 font-medium transition-all duration-300">
</div>

<script>
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

</script>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        // Gắn các sự kiện để lọc khi người dùng thay đổi input/select
        document.getElementById("searchClass").addEventListener("input", applyFilters);
        document.getElementById("filterTeacher").addEventListener("change", applyFilters);
        document.getElementById("filterWeekday").addEventListener("change", applyFilters);
        document.getElementById("filterRoom").addEventListener("change", applyFilters);
    });

    console.log("JS loaded!");

    const dataDiv = document.getElementById("js-data");
    const contextPath = dataDiv.dataset.contextPath || "";
    const courseId = dataDiv.dataset.courseId || "";

    if (!courseId || isNaN(courseId) || courseId === "0") {
        alert("Không tìm thấy courseId hợp lệ! Vui lòng tải lại trang hoặc kiểm tra URL.");
        throw new Error("courseId không hợp lệ");
    }

    function openAddClassModal() {
        console.log("Mở modal");
        document.getElementById("addClassModal").classList.remove("hidden");

        // Reset các select
        ["teacherId", "dayOfWeek", "shiftId", "roomId"].forEach(id => {
            const sel = document.getElementById(id);
            sel.innerHTML = '<option value="">-- Chọn --</option>';
        });

        // Load giáo viên đúng chuyên môn
        fetch(contextPath + "/ScheduleOptionsServlet?action=teachers&courseId=" + courseId)
                .then(res => res.json())
                .then(data => {
                    updateSelect("teacherId", data.teachers);
                    console.log("Loaded teachers:", data.teachers);
                })
                .catch(err => console.error("Lỗi load teacher:", err));
    }

    function closeAddClassModal() {
        document.getElementById("addClassModal").classList.add("hidden");
    }

    function updateOptions(trigger) {
        const teacherId = document.getElementById("teacherId").value;
        const dayOfWeek = document.getElementById("dayOfWeek").value;
        const shiftId = document.getElementById("shiftId").value;

        if (trigger === "teacherId" && teacherId) {
            // Chọn giáo viên xong → load thứ
            fetch(contextPath + "/ScheduleOptionsServlet?action=days&teacherId=" + teacherId)
                    .then(res => res.json())
                    .then(data => updateSelect("dayOfWeek", data.days))
                    .catch(err => console.error("Lỗi load days:", err));
        }

        if (trigger === "dayOfWeek" && teacherId && dayOfWeek) {
            // Chọn thứ → load ca rảnh theo giáo viên + thứ
            fetch(contextPath + "/ScheduleOptionsServlet?action=shifts&teacherId=" + teacherId + "&day=" + dayOfWeek)
                    .then(res => res.json())
                    .then(data => updateSelect("shiftId", data.shifts))
                    .catch(err => console.error("Lỗi load shifts:", err));
        }

        if (trigger === "shiftId" && dayOfWeek && shiftId) {
            // Chọn ca → load phòng trống theo thứ + ca
            fetch(contextPath + "/ScheduleOptionsServlet?action=rooms&day=" + dayOfWeek + "&shift=" + shiftId)
                    .then(res => res.json())
                    .then(data => updateSelect("roomId", data.rooms))
                    .catch(err => console.error("Lỗi load rooms:", err));
        }
    }

    function updateSelect(id, options, keepValue = false) {
        const select = document.getElementById(id);
        const oldValue = select.value;
        select.innerHTML = "";

        const emptyOption = document.createElement("option");
        emptyOption.value = "";
        emptyOption.text = "-- Chọn --";
        select.appendChild(emptyOption);

        options.forEach(opt => {
            const option = document.createElement("option");
            option.value = opt.value;
            option.text = opt.label;
            select.appendChild(option);
        });

        if (keepValue && options.some(opt => opt.value === oldValue)) {
            select.value = oldValue;
        } else {
            select.value = "";
    }
    }

    document.addEventListener("DOMContentLoaded", function () {
        document.getElementById("teacherId").addEventListener("change", () => updateOptions("teacherId"));
        document.getElementById("dayOfWeek").addEventListener("change", () => updateOptions("dayOfWeek"));
        document.getElementById("shiftId").addEventListener("change", () => updateOptions("shiftId"));
    });

    function confirmDeleteClass(classId) {
        if (confirm('Bạn có chắc chắn muốn xóa lớp học này?')) {
            alert('Xóa lớp: ' + classId);
        }
    }

    function normalize(str) {
        return (str || "")
                .normalize("NFD")
                .replace(/[\u0300-\u036f]/g, "")
                .toLowerCase()
                .trim();
    }

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

            const matchClass = className.includes(classKeyword);
            const matchTeacher = !teacher || teacherName === teacher;
            const matchWeekday = !weekday || day === weekday;
            const matchRoom = !room || roomName === room;

            card.style.display = matchClass && matchTeacher && matchWeekday && matchRoom ? "grid" : "none";
        });
    }
    // gửi lại dữ liệu phòng, ca, thứ vào form nếu form sai
    document.addEventListener("DOMContentLoaded", function () {
    const dataDiv = document.getElementById("js-data");
const teacherId = dataDiv.dataset.selectedTeacher;
const dayOfWeek = dataDiv.dataset.selectedDay;
const shiftId = dataDiv.dataset.selectedShift;
const roomId = dataDiv.dataset.selectedRoom;


    if ("${openAddModal}" === "true") {
        document.getElementById("addClassModal").classList.remove("hidden");

        // Gọi openAddClassModal nhưng giữ lại các giá trị
        fetch(contextPath + "/ScheduleOptionsServlet?action=teachers&courseId=" + courseId)
            .then(res => res.json())
            .then(data => {
                updateSelect("teacherId", data.teachers);
                if (teacherId) document.getElementById("teacherId").value = teacherId;

                if (teacherId) {
                    fetch(contextPath + "/ScheduleOptionsServlet?action=days&teacherId=" + teacherId)
                        .then(res => res.json())
                        .then(data => {
                            updateSelect("dayOfWeek", data.days);
                            if (dayOfWeek) document.getElementById("dayOfWeek").value = dayOfWeek;

                            if (dayOfWeek) {
                                fetch(contextPath + "/ScheduleOptionsServlet?action=shifts&teacherId=" + teacherId + "&day=" + dayOfWeek)
                                    .then(res => res.json())
                                    .then(data => {
                                        updateSelect("shiftId", data.shifts);
                                        if (shiftId) document.getElementById("shiftId").value = shiftId;

                                        if (shiftId) {
                                            fetch(contextPath + "/ScheduleOptionsServlet?action=rooms&day=" + dayOfWeek + "&shift=" + shiftId)
                                                .then(res => res.json())
                                                .then(data => {
                                                    updateSelect("roomId", data.rooms);
                                                    if (roomId) document.getElementById("roomId").value = roomId;
                                                });
                                        }
                                    });
                            }
                        });
                }
            });
    }
});

</script>
