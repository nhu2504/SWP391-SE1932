<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Course Management System</title>
<script src="https://cdn.tailwindcss.com"></script>
<style>
    .course-card {
        transition: all 0.3s ease;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    }
    .course-card:hover {
        transform: translateY(-5px);
        box-shadow: 0 10px 15px rgba(0, 0, 0, 0.1);
    }
    .pagination-btn {
        transition: all 0.2s ease;
    }
    .pagination-btn:hover:not(.disabled) {
        background-color: #3b82f6;
        color: white;
    }
</style>

<div class="bg-gray-50 min-h-screen">
    <div class="container mx-auto px-4 py-8">                
        <div>
            <h2 class="text-xl font-semibold text-gray-700 mb-6">Danh Sách Khóa Học</h2>
            <div class="flex flex-col md:flex-row gap-4 mb-6">
                <input type="text" id="searchClass" placeholder="🔍 Tìm khoá..." class="form-control w-full md:w-1/3">

                <select id="filterSubject" class="form-control w-full md:w-1/3">
                <option value="">📘 Tất cả môn học</option>
                <c:forEach var="s" items="${subjects}">
                    <option value="${s.subjectName}">${s.subjectName}</option>
                </c:forEach>
            </select>


            <select id="filterGrade" class="form-control w-full md:w-1/3">
                <option value="">🏷️ Tất cả khối</option>
                <c:forEach var="g" items="${grades}">
                    <option value="${g.gradeName}">${g.gradeName}</option>
                </c:forEach>
            </select>
            </div>

            <div class="overflow-x-auto">
                <table class="w-full border-collapse bg-white rounded-lg shadow-sm">
                    <thead>
                        <tr style="background-color: #FFF1F1; color: black;">
                            <th class="p-4 text-left w-24">Ảnh</th>
                            <th class="p-4 text-left w-48">Tên</th>
                            <th class="p-4 text-left w-64">Mô Tả</th>
                            <th class="p-4 text-left w-24">Khối</th>
                            <th class="p-4 text-left w-32">Môn</th>
                            <th class="p-4 text-left w-24">Loại</th>
                            <th class="p-4 text-left w-28">Bắt Đầu</th>
                            <th class="p-4 text-left w-28">Kết Thúc</th>
                            <th class="p-4 text-left w-28">Giá (VND)</th>
                            <th class="p-4 text-left w-24">Hành Động</th>
                        </tr>
                    </thead>
                    <tbody class="divide-y divide-gray-200">
                        <c:forEach items="${data}" var="item">
                            <tr class="hover:bg-gray-50 transition course-row">
                                <td class="p-4">
                                    <img src="${pageContext.request.contextPath}/LogoServlet?type=tutoring&tutoringClassId=${item.tutoringClassID}"      
                                         width="64" height="64" alt="${item.className}"      
                                         class="object-cover rounded-lg">
                                </td>
                                <td class="p-4">
                                    <a href="admin?tab=classManagement&id=${item.tutoringClassID}" class="text-blue-600 hover:underline">${item.className}</a>

                                </td>
                                <td class="p-4">
                                    <c:choose>
                                        <c:when test="${fn:length(item.descrip) > 50}">
                                            ${fn:substring(item.descrip, 0, 50)}...
                                        </c:when>
                                        <c:otherwise>
                                            ${item.descrip}
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="p-4">
                                    <c:forEach items="${grades}" var="g">
                                        <c:if test="${g.gradeID == item.gradeID}">
                                            ${g.gradeName}
                                        </c:if>
                                    </c:forEach>
                                </td>

                                <td class="p-4">
                                    <c:forEach items="${subjects}" var="s">
                                        <c:if test="${s.subjectId == item.subjectID}">
                                            ${s.subjectName}
                                        </c:if>
                                    </c:forEach>
                                </td>
                                <td class="p-4">
                                    <c:choose>
                                        <c:when test="${item.isHot}">Nổi bật</c:when>
                                        <c:otherwise>Quanh năm</c:otherwise>
                                    </c:choose>
                                </td>

                                <td class="p-4">
                                    <fmt:formatDate value="${item.startDate}" pattern="dd/MM/yyyy" />
                                </td>
                                <td class="p-4">
                                    <fmt:formatDate value="${item.endDate}" pattern="dd/MM/yyyy" />
                                </td>
                                <td class="p-4 text-right text-gray-700">
                                    <fmt:formatNumber value="${item.price}" type="number" groupingUsed="true"/> 
                                    <c:choose>
                                        <c:when test="${item.isHot}"> / 1 khoá</c:when>
                                        <c:otherwise> / 1 buổi</c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="p-4">
                                    <a href="admin?tab=classManagement&id=${item.tutoringClassID}" class="bg-green-500 text-white px-3 py-2 rounded-lg transition">Xem</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const searchClass = document.getElementById("searchClass");
        const filterSubject = document.getElementById("filterSubject");
        const filterGrade = document.getElementById("filterGrade");

        const rows = document.querySelectorAll("tbody tr");

        function normalize(str) {
            return (str || "")
                    .normalize("NFD")
                    .replace(/[\u0300-\u036f]/g, "")
                    .toLowerCase()
                    .trim();
        }

        function applyFilters() {
            const classKeyword = normalize(searchClass.value);
            const selectedSubject = normalize(filterSubject.value);
            const selectedGrade = normalize(filterGrade.value);

            rows.forEach(row => {
                const className = normalize(row.querySelector("td:nth-child(2)")?.textContent);
                const grade = normalize(row.querySelector("td:nth-child(4)")?.textContent);
                const subject = normalize(row.querySelector("td:nth-child(5)")?.textContent);

                const matchClass = className.includes(classKeyword);
                const matchSubject = !selectedSubject || subject === selectedSubject;
                const matchGrade = !selectedGrade || grade === selectedGrade;

                const showRow = matchClass && matchSubject && matchGrade;
                row.style.display = showRow ? "" : "none";
            });
        }

        searchClass.addEventListener("input", applyFilters);
        filterSubject.addEventListener("change", applyFilters);
        filterGrade.addEventListener("change", applyFilters);
    });
</script>
