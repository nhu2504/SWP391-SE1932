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
                <li>
                    <div class="flex items-center">
                        <i class="fas fa-chevron-right text-gray-400 mx-2"></i>
                        <a href="admin?tab=studentListInClass&groupId=${selectedClassGroupId}&id=${selectedCourseId}"
                           class="ml-1 text-sm font-medium text-gray-700 hover:text-blue-600">
                            ${selectedClassGroupName}
                        </a>
                    </div>
                </li>


                <li>
                    <div class="flex items-center">
                        <i class="fas fa-chevron-right text-gray-400 mx-2"></i>
                        <a href="admin?tab=studentNotInClass&groupId=${selectedClassGroupId}&id=${selectedCourseId}"
                           class="ml-1 text-sm font-medium text-gray-700 hover:text-blue-600">
                            Thêm học sinh mới
                        </a>
                    </div>
                </li>

            </ol>
        </nav>
        <div class="flex flex-col md:flex-row gap-4 mb-6">
            <input type="text" id="searchByName" placeholder="🔍 Tìm tên..." class="form-control w-full md:w-1/2">
            <input type="text" id="searchByPhone" placeholder="🔍 Tìm số điện thoại..." class="form-control w-full md:w-1/2">

        </div>  


        <div>
            <div class="flex items-center justify-between mb-6">
                <h2 class="text-xl font-semibold text-gray-700">Danh Sách Học Sinh</h2>
<span id="selectedCount" class="text-sm text-blue-600 font-medium">
        Đã chọn: 0 học sinh
    </span>
            </div>

            <div class="overflow-x-auto">
                <table class="w-full border-collapse bg-white rounded-lg shadow-sm">
                    <thead>
                        
                        <tr class="bg-blue-600 text-white">
                            <th class="p-4 text-left cursor-pointer" onclick="toggleCheckAll()">
    <span class="text-sm font-medium text-white">Chọn tất cả</span>
    <input type="checkbox" id="checkAll" class="hidden">
</th>


                            <th class="p-4 text-left">Avatar</th>
                            <th class="p-4 text-left">Tên</th>
                            <th class="p-4 text-left">Giới tính</th>
                            <th class="p-4 text-left">Ngày sinh</th>
                            <th class="p-4 text-left">Số điện thoại</th>
                            <th class="p-4 text-left">Email</th>
                            <th class="p-4 text-left">Trường</th>
                            <th class="p-4 text-left">Lớp</th>

                        </tr>
                    </thead>
                    <tbody class="divide-y divide-gray-200">
                        <c:forEach items="${students}" var="s">
                            <tr class="hover:bg-gray-50 transition">
                                <td class="p-4">
            <input type="checkbox" name="studentIds" value="${s.id}" class="row-checkbox student-checkbox form-checkbox accent-blue-500">
        </td>
                                <td class="p-4">
                                    <img src="${pageContext.request.contextPath}/LogoServlet?type=student&userId=${s.id}"
                                         width="48" height="48" class="rounded-full object-cover border border-gray-300"
                                         alt="${s.name}">
                                </td>
                                <td class="p-4">${s.name}</td>
                                <td class="p-4">${s.gender}</td>
                                <td class="p-4"><fmt:formatDate value="${s.birth}" pattern="dd/MM/yyyy"/></td>
                                <td class="p-4">${s.phone}</td>
                                <td class="p-4">${s.email}</td>
                                <td class="p-4">
                                    <c:forEach items="${schools}" var="school">
                                        <c:if test="${school.schoolID == s.schoolID}">
                                            ${school.name}
                                        </c:if>
                                    </c:forEach>
                                </td>
                                <td class="p-4">
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

<script>
    document.addEventListener("DOMContentLoaded", function () {
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
            const nameKeyword = normalize(nameInput.value);
            const phoneKeyword = normalize(phoneInput.value);

            rows.forEach(row => {
                const name = normalize(row.querySelector("td:nth-child(2)")?.textContent);
                const phone = normalize(row.querySelector("td:nth-child(5)")?.textContent);

                const matchName = name.includes(nameKeyword);
                const matchPhone = phone.startsWith(phoneKeyword);

                // Hiện nếu cả 2 điều kiện đúng hoặc bị bỏ trống
                const showRow =
                        (!nameKeyword || matchName) &&
                        (!phoneKeyword || matchPhone);

                row.style.display = showRow ? "" : "none";
            });
        }

        nameInput.addEventListener("input", applyFilters);
        phoneInput.addEventListener("input", applyFilters);
    });
</script>


<script>
document.addEventListener("DOMContentLoaded", function () {
    const checkAll = document.getElementById("checkAll");
    const countDisplay = document.getElementById("selectedCount");

    // Kiểm tra sự tồn tại của các phần tử
    if (!checkAll) {
        console.error("Không tìm thấy checkAll");
        return;
    }
    if (!countDisplay) {
        console.error("Không tìm thấy selectedCount");
        return;
    }

    function updateSelectedCount() {
        const checkedBoxes = document.querySelectorAll(".row-checkbox:checked");
        console.log("Checked boxes:", checkedBoxes.length); // Debug
        console.log("countDisplay:", countDisplay); // Debug
        countDisplay.textContent = "Đã chọn: " + checkedBoxes.length + " học sinh"; // Nối chuỗi
        console.log("Updated textContent:", countDisplay.textContent); // Debug
        setTimeout(() => {
            console.log("TextContent after 100ms:", countDisplay.textContent); // Debug sau 100ms
        }, 100);
    }

    function toggleCheckAll() {
        const isChecked = checkAll.checked;
        console.log("Toggle all, isChecked:", isChecked); // Debug
        document.querySelectorAll(".row-checkbox").forEach(cb => cb.checked = isChecked);
        updateSelectedCount();
    }

    // Gắn sự kiện cho checkAll
    document.querySelector("th[onclick='toggleCheckAll()']").onclick = function() {
        checkAll.checked = !checkAll.checked;
        toggleCheckAll();
    };
    checkAll.addEventListener("change", toggleCheckAll);

    // Gắn sự kiện cho từng checkbox
    document.querySelectorAll(".row-checkbox").forEach(cb => {
        cb.addEventListener("change", () => {
            console.log("Checkbox changed:", cb.value); // Debug
            updateSelectedCount();
        });
    });

    // Cập nhật lần đầu
    updateSelectedCount();
});
</script>







