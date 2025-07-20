<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!-- Văn Thị Như - HE181329 
Ngày update 3/7/2025-->
<div class="p-6 bg-white shadow rounded-lg">
    <h2 class="text-2xl font-bold mb-6 text-gray-800 flex items-center gap-2">
        <i class="fas fa-chalkboard-teacher"></i> Lịch Dạy Giáo Viên
    </h2>

    <!-- Bộ lọc -->
    <div class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-6">
        <!-- Tìm theo giáo viên -->
        <div>
            <input type="text" id="searchInput" placeholder="🔍 Tìm giáo viên..." 
                   class="w-full border border-gray-300 rounded px-3 py-2 focus:ring-2 focus:ring-blue-400" />
        </div>
        
        <!-- Lọc theo lớp (loại trùng) -->
        <div>
            <input list="classOptions" id="classFilter" name="classFilter"
                   placeholder="🏫 Tìm kiếm lớp..."
                   class="w-full border border-gray-300 rounded px-3 py-2 focus:ring-2 focus:ring-blue-400" />
            <datalist id="classOptions">
                <c:forEach var="className" items="${classNames}">
                    <option value="${className}" />
                </c:forEach>
            </datalist>
        </div>

        <!-- Lọc theo môn -->
        <div>
            <select id="subjectFilter" 
                    class="w-full border border-gray-300 rounded px-3 py-2 focus:ring-2 focus:ring-blue-400 text-gray-500">
                <option value="">📂 Lọc theo môn học</option>
                <c:forEach var="subject" items="${subjectList}">
                    <option value="${subject.subjectName}">${subject.subjectName}</option>
                </c:forEach>
            </select>
        </div>        
    </div>

    <!-- Bảng dữ liệu -->
    <div class="overflow-x-auto bg-white shadow-md rounded-lg">
        <table class="min-w-full text-sm text-left text-gray-700">
            <thead class="bg-blue-100 text-blue-800">
                <tr>
                    <th class="px-4 py-2">👩‍🏫 Giáo viên</th>
                    <th class="px-4 py-2">📘 Môn</th>
                    <th class="px-4 py-2">🏫 Lớp</th>
                    <th class="px-4 py-2">📅 Thứ</th>
                    <th class="px-4 py-2">⏰ Thời gian</th>
                </tr>
            </thead>
            <c:forEach var="entry" items="${teacherScheduleMap}">
                <c:set var="teacherName" value="${entry.key}" />
                <c:set var="schedules" value="${entry.value}" />
                <tbody class="teacher-group" data-teacher="${fn:toLowerCase(teacherName)}">
                    <tr class="teacher-name-row">
                        <td colspan="5" class="px-4 py-2 font-bold text-gray-800">${teacherName}</td>
                    </tr>
                    <c:forEach var="item" items="${schedules}">
                        <tr class="schedule-row"
                            data-subject="${fn:toLowerCase(item[0])}"
                            data-class="${fn:toLowerCase(item[1])}">
                            <td class="px-4 py-2"></td>
                            <td class="px-4 py-2">${item[0]}</td> <!-- Môn -->
                            <td class="px-4 py-2">${item[1]}</td> <!-- Lớp -->
                            <td class="px-4 py-2">${weekdays[item[2]]}</td> <!-- Thứ -->
                            <td class="px-4 py-2">
                                <fmt:formatDate value="${item[3]}" pattern="HH:mm" /> -
                                <fmt:formatDate value="${item[4]}" pattern="HH:mm" />
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </c:forEach>
        </table>
    </div>
</div>

<!-- JavaScript lọc -->
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const searchInput = document.getElementById("searchInput");
        const subjectFilter = document.getElementById("subjectFilter");
        const classFilter = document.getElementById("classFilter");

        // Chuẩn hóa tiếng Việt: loại bỏ dấu và lowercase
        function normalize(str) {
            return (str || "")
                    .normalize("NFD")                   // Tách chữ và dấu
                    .replace(/[\u0300-\u036f]/g, "")   // Xóa dấu
                    .toLowerCase()
                    .trim();
        }

        function filterGroups() {
            const keyword = normalize(searchInput.value);
            const selectedSubject = normalize(subjectFilter.value);
            const selectedClass = normalize(classFilter.value);

            const groups = document.querySelectorAll(".teacher-group");

            groups.forEach(group => {
                const teacherName = normalize(group.dataset.teacher);
                const rows = group.querySelectorAll(".schedule-row");

                let matchFound = false;

                rows.forEach(row => {
                    const subject = normalize(row.dataset.subject);
                    const className = normalize(row.dataset.class);

                    const matchesTeacher = teacherName.includes(keyword);
                    const matchesSubject = !selectedSubject || subject === selectedSubject;
                    const matchesClass = !selectedClass || className.includes(selectedClass);

                    if (matchesTeacher && matchesSubject && matchesClass) {
                        row.style.display = "table-row";
                        matchFound = true;
                    } else {
                        row.style.display = "none";
                    }
                });

                group.style.display = matchFound ? "" : "none";
            });
        }

        // Gắn sự kiện lọc
        searchInput.addEventListener("input", filterGroups);
        subjectFilter.addEventListener("change", filterGroups);
        classFilter.addEventListener("input", filterGroups);
    });
</script>
