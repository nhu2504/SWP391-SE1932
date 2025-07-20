<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Quản Lý Khóa Học</title>
<script src="https://cdn.tailwindcss.com"></script>
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">

<div class="bg-gradient-to-br from-blue-50 to-gray-100 min-h-screen flex flex-col items-center p-6 font-inter">

    <div class="w-full max-w-6xl bg-white rounded-2xl shadow-xl p-8">


        <nav class="flex mb-6" aria-label="Breadcrumb">
            <ol class="inline-flex items-center space-x-1 md:space-x-3">
                <li class="inline-flex items-center">
                    <a href="admin?tab=courseList" class="inline-flex items-center text-sm font-medium text-gray-700 hover:text-blue-600">
                        <i class="fas fa-home mr-2"></i>
                        Khóa học
                    </a>
                </li>
                <li>
                    <div class="flex items-center">
                        <i class="fas fa-chevron-right text-gray-400 mx-2"></i>
                        <a href="admin?tab=classManagement&id=${selectedCourseId}" class="ml-1 text-sm font-medium text-gray-700 hover:text-blue-600">
                            ${selectedCourseName}
                        </a>
                    </div>
                </li>
                <li aria-current="page">
                    <div class="flex items-center">
                        <i class="fas fa-chevron-right text-gray-400 mx-2"></i>
                        <span class="ml-1 text-sm font-medium text-gray-500">${selectedClassGroupName}</span>
                    </div>
                </li>
            </ol>
        </nav>
        <div class="flex flex-col md:flex-row gap-4 mb-6">
            <input type="text" id="searchById" placeholder="🔍 Tìm ID..." class="form-control w-full md:w-1/3">
            <input type="text" id="searchByName" placeholder="🔍 Tìm tên..." class="form-control w-full md:w-1/3">
            <input type="text" id="searchByPhone" placeholder="🔍 Tìm số điện thoại..." class="form-control w-full md:w-1/3">
        </div>

        <!-- Danh sách khóa học -->
        <div>
           <div class="flex items-center justify-between mb-6">
    <h2 class="text-xl font-semibold text-gray-700">Danh Sách Học Sinh</h2>
    <a href="admin?tab=studentNotInClass&groupId=${param.groupId}&id=${param.id}"
   class="ml-auto bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-lg flex items-center">
    <i class="fas fa-user-plus mr-2"></i> Thêm học sinh mới
</a>

</div>

            <div class="overflow-x-auto">
                <table class="w-full border-collapse bg-white rounded-lg shadow-sm">
                    <thead>
                        <tr style="background-color: #FFF1F1; color: black;">
                            <th class="p-4 text-left w-[5%]">ID</th>
                            <th class="p-4 text-left w-[8%]">Avatar</th>
                            <th class="p-4 text-left w-[15%]">Tên</th>
                            <th class="p-4 text-left w-[8%]">Giới tính</th>
                            <th class="p-4 text-left w-[10%]">Ngày sinh</th>
                            <th class="p-4 text-left w-[12%]">Số điện thoại</th>
                            <th class="p-4 text-left w-[15%]">Email</th>
                            <th class="p-4 text-left w-[12%]">Trường</th>
                            <th class="p-4 text-left w-[10%]">Lớp</th>

                        </tr>
                    </thead>
                    <tbody class="divide-y divide-gray-200">
                        <c:forEach items="${students}" var="s">
                            <tr class="hover:bg-gray-50 transition">
                                <td class="p-4 w-[5%]">${s.id}</td>
                                <td class="p-4 w-[8%]">
                                    <img src="${pageContext.request.contextPath}/LogoServlet?type=student&userId=${s.id}"
                                         width="48" height="48" class="rounded-full object-cover border border-gray-300"
                                         alt="${s.name}">
                                </td>

                                <td class="p-4 w-[15%]">${s.name}</td>
                                <td class="p-4 w-[8%]">${s.gender}</td>
                                <td class="p-4 w-[10%]"><fmt:formatDate value="${s.birth}" pattern="dd/MM/yyyy"/></td>
                                <td class="p-4 w-[12%]">${s.phone}</td>
                                <td class="p-4 w-[15%] truncate">${s.email}</td>
                                <td class="p-4 w-[12%]">
                                    <c:forEach items="${schools}" var="school">
                                        <c:if test="${school.schoolID == s.schoolID}">
                                            ${school.name}
                                        </c:if>
                                    </c:forEach>
                                </td>
                                <td class="p-4 w-[10%]">
                                    <c:forEach items="${schoolClasses}" var="sc">
                                        <c:if test="${sc.schoolClassID == s.schoolClassId}">
                                            ${sc.className}
                                        </c:if>
                                    </c:forEach>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>

                </table>
            </div>
        </div>
    </div>
</div>

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
    document.addEventListener("DOMContentLoaded", function () {
    const idInput = document.getElementById("searchById");
    const nameInput = document.getElementById("searchByName");
    const phoneInput = document.getElementById("searchByPhone");
    const rows = document.querySelectorAll("tbody tr");

    function normalize(str) {
        return (str || "")
            .normalize("NFD")
            .replace(/[\u0300-\u036f]/g, "")
            .toLowerCase()
            .trim();
    }

    function applyFilters() {
    const idKeyword = normalize(idInput.value);
    const nameKeyword = normalize(nameInput.value);
    const phoneKeyword = normalize(phoneInput.value);

    rows.forEach(row => {
        const id = normalize(row.querySelector("td:nth-child(1)")?.textContent);     
        const name = normalize(row.querySelector("td:nth-child(3)")?.textContent);   
        const phone = normalize(row.querySelector("td:nth-child(6)")?.textContent);  

        const matchId = id.startsWith(idKeyword);
        const matchName = name.includes(nameKeyword);
        const matchPhone = phone.startsWith(phoneKeyword);

        const showRow =
            (!idKeyword || matchId) &&
            (!nameKeyword || matchName) &&
            (!phoneKeyword || matchPhone);

        row.style.display = showRow ? "" : "none";
    });
}


    idInput.addEventListener("input", applyFilters);
    nameInput.addEventListener("input", applyFilters);
    phoneInput.addEventListener("input", applyFilters);
});

</script>

<script>
function showToast(message, type = "success") {
    const toast = document.getElementById("toast");
    if (toast) {
        toast.textContent = message;
        toast.className = "fixed top-5 right-5 px-6 py-3 rounded-lg shadow-lg z-50 font-medium transition-all duration-300";
        if (type === "error") {
            toast.classList.add("bg-red-500", "text-white");
        } else {
            toast.classList.add("bg-green-500", "text-white");
        }
        toast.style.display = "block";
        setTimeout(() => {
            toast.style.display = "none";
        }, 3000); // Ẩn sau 3 giây
    } else {
        console.error("Toast element not found!");
    }
}
</script>




