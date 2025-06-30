<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="teacherSet" value="" />
<c:set var="subjectSet" value="" />
<c:set var="roomSet" value="" />
<c:set var="gradeSet" value="" />

<div class="p-6 bg-white shadow rounded-lg">
    <h2 class="text-2xl font-bold mb-6 text-gray-800 flex items-center gap-2">
        <i class="fas fa-chalkboard"></i> Lịch Học Các Lớp
    </h2>

    <!-- Bộ lọc -->
    <div class="flex flex-col md:flex-row gap-4 mb-6">
        <input type="text" id="searchClass" placeholder="🔍 Tìm lớp..." class="form-control w-full md:w-1/6">

        <select id="filterTeacher" class="form-control w-full md:w-1/6">
            <option value="">🎓 Tất cả giáo viên</option>
            <c:forEach var="entry" items="${weeklySchedules}">
                <c:if test="${not fn:contains(teacherSet, entry[3])}">
                    <c:set var="teacherSet" value="${teacherSet}${entry[3]}," />
                    <option value="${entry[3]}">${entry[3]}</option>
                </c:if>
            </c:forEach>
        </select>

        <select id="filterWeekday" class="form-control w-full md:w-1/6">
            <option value="">📅 Tất cả các ngày</option>
            <c:forEach var="i" begin="1" end="7">
                <option value="${weekdays[i]}">${weekdays[i]}</option>
            </c:forEach>
        </select>

        <select id="filterSubject" class="form-control w-full md:w-1/6">
            <option value="">📘 Tất cả môn học</option>
            <c:forEach var="entry" items="${weeklySchedules}">
                <c:if test="${not fn:contains(subjectSet, entry[7])}">
                    <c:set var="subjectSet" value="${subjectSet}${entry[7]}," />
                    <option value="${entry[7]}">${entry[7]}</option>
                </c:if>
            </c:forEach>
        </select>

        <select id="filterRoom" class="form-control w-full md:w-1/6">
            <option value="">🏫 Tất cả phòng</option>
            <c:forEach var="entry" items="${weeklySchedules}">
                <c:if test="${not fn:contains(roomSet, entry[4])}">
                    <c:set var="roomSet" value="${roomSet}${entry[4]}," />
                    <option value="${entry[4]}">${entry[4]}</option>
                </c:if>
            </c:forEach>
        </select>

        <select id="filterGrade" class="form-control w-full md:w-1/6">
            <option value="">🏷️ Tất cả khối</option>
            <c:forEach var="entry" items="${weeklySchedules}">
                <c:if test="${not fn:contains(gradeSet, entry[9])}">
                    <c:set var="gradeSet" value="${gradeSet}${entry[9]}," />
                    <option value="${entry[9]}">${entry[9]}</option>
                </c:if>
            </c:forEach>
        </select>
    </div>

    <!-- Lịch học từng lớp -->
    <div class="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-6">
        <c:forEach var="entry" items="${weeklySchedules}">
            <div class="card bg-white rounded-lg shadow p-5 hover:shadow-md transition duration-300 border-l-4"
                 style="border-color: #3b82f6;"
                 data-class="${entry[2]}"
                 data-teacher="${entry[3]}"
                 data-weekday="${weekdays[entry[0]]}"
                 data-subject="${entry[7]}"
                 data-room="${entry[4]}"
                 data-grade="${entry[9]}">
                <div class="font-semibold text-blue-800 text-lg mb-1">
                    Lớp: ${entry[2]}
                </div>
                <div class="text-sm text-gray-700 mb-1">
                    📘 Môn học: ${entry[7]}
                </div>
                <div class="text-sm text-gray-700 mb-1">
                    🏷️ Khối: ${entry[9]}
                </div>
                <div class="text-sm text-gray-700 mb-1">
                    🧑‍🏫 Giáo viên: ${entry[3]}
                </div>
                <div class="text-sm text-gray-700 mb-1">
                    🏩 Phòng: ${entry[4]}
                </div>
                
                <div class="text-sm text-gray-700 mb-1">
                    📅 Thứ: ${weekdays[entry[0]]}
                </div>
                <div class="text-sm text-gray-700">
                    ⏰ <fmt:formatDate value="${entry[5]}" pattern="HH:mm"/> - <fmt:formatDate value="${entry[6]}" pattern="HH:mm"/>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<script>
document.addEventListener("DOMContentLoaded", function () {
    const searchClass = document.getElementById("searchClass");
    const filterTeacher = document.getElementById("filterTeacher");
    const filterWeekday = document.getElementById("filterWeekday");
    const filterSubject = document.getElementById("filterSubject");
    const filterRoom = document.getElementById("filterRoom");
    const filterGrade = document.getElementById("filterGrade");
    const cards = document.querySelectorAll(".card[data-class]");

    // Chuẩn hóa chuỗi: loại bỏ dấu tiếng Việt + lowercase
    function normalize(str) {
        return (str || "")
            .normalize("NFD")                   // Tách chữ + dấu
            .replace(/[\u0300-\u036f]/g, "")   // Xoá dấu
            .toLowerCase()
            .trim();
    }

    function applyFilters() {
        const classKeyword = normalize(searchClass.value);
        const teacher = normalize(filterTeacher.value);
        const weekday = normalize(filterWeekday.value);
        const subject = normalize(filterSubject.value);
        const room = normalize(filterRoom.value);
        const grade = normalize(filterGrade.value);

        cards.forEach(card => {
            const className = normalize(card.dataset.class);
            const teacherName = normalize(card.dataset.teacher);
            const day = normalize(card.dataset.weekday);
            const subjectName = normalize(card.dataset.subject);
            const roomName = normalize(card.dataset.room);
            const gradeName = normalize(card.dataset.grade);

            const matchClass = className.includes(classKeyword);
            const matchTeacher = !teacher || teacherName === teacher;
            const matchWeekday = !weekday || day === weekday;
            const matchSubject = !subject || subjectName === subject;
            const matchRoom = !room || roomName === room;
            const matchGrade = !grade || gradeName === grade;

            card.style.display = (matchClass && matchTeacher && matchWeekday && matchSubject && matchRoom && matchGrade)
                ? "block"
                : "none";
        });
    }

    searchClass.addEventListener("input", applyFilters);
    filterTeacher.addEventListener("change", applyFilters);
    filterWeekday.addEventListener("change", applyFilters);
    filterSubject.addEventListener("change", applyFilters);
    filterRoom.addEventListener("change", applyFilters);
    filterGrade.addEventListener("change", applyFilters);
});
</script>
