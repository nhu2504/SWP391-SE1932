<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!-- Văn Thị Như - HE181329 
Ngày update 3/7/2025-->
<c:set var="teacherSet" value="" />
<c:set var="subjectSet" value="" />
<c:set var="roomSet" value="" />
<c:set var="gradeSet" value="" />

<div class="p-6 bg-white shadow rounded-lg">    
    
    <form method="get" action="admin" class="mb-6">
    <input type="hidden" name="tab" value="${param.tab}" />
    
    <label for="weekStart" class="font-semibold mr-2">📅 Chọn tuần bắt đầu:</label>
    <select name="weekStart" onchange="this.form.submit()">
        <c:forEach var="week" items="${weekStartList}">
            <option value="${week}" <c:if test="${week eq selectedWeekStart}">selected</c:if>>
                ${weekDisplayMap[week]}
            </option>
        </c:forEach>
    </select>
</form>





    <!-- Bộ lọc -->
    <div class="flex flex-col md:flex-row gap-4 mb-6">
        <input type="text" id="searchClass" placeholder="🔍 Tìm lớp..." class="form-control w-full md:w-1/4">

        <select id="filterTeacher" class="form-control w-full md:w-1/4">
            <option value="">🎓 Tất cả giáo viên</option>
            <c:forEach var="entry" items="${weeklySchedules}">
                <c:if test="${not fn:contains(teacherSet, entry[1])}">
                    <c:set var="teacherSet" value="${teacherSet}${entry[1]}," />
                    <option value="${entry[1]}">${entry[1]}</option>
                </c:if>
            </c:forEach>
        </select>
        
        <select id="filterSubject" class="form-control w-full md:w-1/4">
            <option value="">📘 Tất cả môn học</option>
            <c:forEach var="entry" items="${weeklySchedules}">
                <c:if test="${not fn:contains(subjectSet, entry[2])}">
                    <c:set var="subjectSet" value="${subjectSet}${entry[2]}," />
                    <option value="${entry[2]}">${entry[2]}</option>
                </c:if>
            </c:forEach>
        </select>

        <select id="filterGrade" class="form-control w-full md:w-1/4">
            <option value="">🏷️ Tất cả khối</option>
            <c:forEach var="entry" items="${weeklySchedules}">
                <c:if test="${not fn:contains(gradeSet, entry[3])}">
                    <c:set var="gradeSet" value="${gradeSet}${entry[3]}," />
                    <option value="${entry[3]}">${entry[3]}</option>
                </c:if>
            </c:forEach>
        </select>
    </div>

    <!-- Lịch học từng lớp -->
    <div class="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-6">
        <c:forEach var="entry" items="${weeklySchedules}">
            <div class="card bg-white rounded-lg shadow p-5 hover:shadow-md transition duration-300 border-l-4"
                 style="border-color: #3b82f6;"
                 data-class="${entry[0]}"
                 data-teacher="${entry[1]}"
                 data-subject="${entry[2]}"
                 data-grade="${entry[3]}">
                <div class="font-semibold text-blue-800 text-lg mb-1">
                    Lớp: ${entry[0]}
                </div>
                <div class="text-sm text-gray-700 mb-1">
                    📘 Môn học: ${entry[2]}
                </div>
                <div class="text-sm text-gray-700 mb-1">
                    🏷️ Khối: ${entry[3]}
                </div>
                <div class="text-sm text-gray-700 mb-1">
                    🧑‍🏫 Giáo viên: ${entry[1]}
                </div>


                <div class="text-sm text-gray-700 mb-1 flex gap-1 flex-wrap">
                    📅 Lịch học:
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
        </c:forEach>
    </div>
</div>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const searchClass = document.getElementById("searchClass");
        const filterTeacher = document.getElementById("filterTeacher");        
        const filterSubject = document.getElementById("filterSubject");
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
            const subject = normalize(filterSubject.value);            
            const grade = normalize(filterGrade.value);
            cards.forEach(card => {
                const className = normalize(card.dataset.class);
                const teacherName = normalize(card.dataset.teacher);                
                const subjectName = normalize(card.dataset.subject);               
                const gradeName = normalize(card.dataset.grade);
                const matchClass = className.includes(classKeyword);
                const matchTeacher = !teacher || teacherName === teacher;                
                const matchSubject = !subject || subjectName === subject;               
                const matchGrade = !grade || gradeName === grade;
                card.style.display = (matchClass && matchTeacher && matchSubject && matchGrade)
                        ? "block"
                        : "none";
            });
        }

        searchClass.addEventListener("input", applyFilters);
        filterTeacher.addEventListener("change", applyFilters);        
        filterSubject.addEventListener("change", applyFilters);        
        filterGrade.addEventListener("change", applyFilters);
    });
</script>
